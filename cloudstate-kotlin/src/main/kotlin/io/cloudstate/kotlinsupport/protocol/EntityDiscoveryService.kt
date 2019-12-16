package io.cloudstate.kotlinsupport.protocol

import akka.stream.ActorMaterializer
import akka.stream.javadsl.Sink
import akka.stream.javadsl.Source
import com.google.protobuf.DescriptorProtos
import com.google.protobuf.Empty
import io.cloudstate.kotlinsupport.EntityType
import io.cloudstate.kotlinsupport.getProjectVersion
import io.cloudstate.kotlinsupport.initializers.CloudStateInitializer
import io.cloudstate.kotlinsupport.logger
import io.cloudstate.protocol.EntityDiscovery
import io.cloudstate.protocol.EntityProto
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

class EntityDiscoveryService(private val initializer: CloudStateInitializer, val materializer: ActorMaterializer): EntityDiscovery {

    private val log = logger()

    override fun discover(proxyInfo: EntityProto.ProxyInfo?): CompletionStage<EntityProto.EntitySpec> =
            Source.single(proxyInfo)
                    .map{
                        log.info("Received discovery call from sidecar [{} {}] supporting CloudState {}.{}",
                                it?.proxyName, it?.proxyVersion, it?.protocolMajorVersion, it?.protocolMinorVersion);
                        log.info("Supported sidecar entity types: {}", it?.supportedEntityTypesList);

                        val fileDescriptor = initializer.eventSourcedInit.descriptor?.file
                        val builder = DescriptorProtos.FileDescriptorSet.newBuilder()
                        builder.addFile(fileDescriptor?.toProto())

                        initializer.eventSourcedInit.additionalDescriptors?.forEach { descriptor ->
                            builder.addFile(descriptor.toProto())
                        }

                        val fileDescriptorSet = builder.build().toByteString()

                        val entityType = if (EntityType.EventSourced == initializer?.type) {
                                    "cloudstate.eventsourced.EventSourced"
                        } else if (EntityType.Crdt == initializer?.type) {
                                    "ccloudstate.crdt.Crdt"
                        } else {
                            "unknown"
                        }

                        EntityProto.EntitySpec.newBuilder()
                                .setProto(fileDescriptorSet)
                                .addEntities(
                                        EntityProto.Entity.newBuilder()
                                                .setEntityType(entityType)
                                                .setServiceName(initializer.eventSourcedInit.descriptor?.fullName)
                                                .setPersistenceId(initializer.eventSourcedInit.persistenceId)
                                                .build())
                                .setServiceInfo(
                                        EntityProto.ServiceInfo.newBuilder()
                                                .setSupportLibraryName("cloudstate-kotlin-support")
                                                .setSupportLibraryVersion(getProjectVersion())
                                                .setServiceRuntime(KotlinVersion.CURRENT.toString())
                                                .setServiceName(initializer.serviceName)
                                                .setServiceVersion(initializer.serviceVersion)
                                                .build())
                                .build()
                    }
                    .runWith(Sink.head(), materializer)

    override fun reportError(userFunctionError: EntityProto.UserFunctionError?): CompletionStage<Empty> {
        log.error("Error reported from sidecar: {}", userFunctionError?.message)
        return CompletableFuture.completedFuture(Empty.getDefaultInstance())
    }
}