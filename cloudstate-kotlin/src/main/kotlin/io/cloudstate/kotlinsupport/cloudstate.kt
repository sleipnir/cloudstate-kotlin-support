package io.cloudstate.kotlinsupport

import com.google.protobuf.Descriptors
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

fun cloudstate(paramsInitializer: CloudStateInitializer.() -> Unit): CloudStateRunner {
    val cloudStateInitializer = CloudStateInitializer()
    cloudStateInitializer.paramsInitializer()

    val type = cloudStateInitializer.type
            ?: throw IllegalArgumentException("type must be set")

    val port = cloudStateInitializer.port

    val eventSourcedInit = cloudStateInitializer.eventSourcedInit

    /*BlockBuilder
            .configuration(ConfigurationParameters(jsonResource, remoteFileUrl))
            .init(context)*/

    return CloudStateRunner();
}

class CloudStateInitializer {

    var type: EntityType? = null

    var port: Int = 8080

    internal var eventSourcedInit = EventSourcedEntityInitializer()

    fun eventSourced(eventSourcedInitializer: EventSourcedEntityInitializer.() -> Unit) {
        eventSourcedInit.eventSourcedInitializer()
    }

    class EventSourcedEntityInitializer {

        var entityClass: KClass<Any>? = null
        var descriptor: Descriptors.ServiceDescriptor? = null
        var additionalDescriptors: Array<Descriptors.FileDescriptor>? = null
        var persistenceId: String? = null
        var snapshotEvery: Int? = null
    }


}