package io.cloudstate.kotlinsupport;

import akka.actor.ActorSystem;
import akka.grpc.javadsl.ServiceHandler;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.japi.Function;
import akka.stream.ActorMaterializer;
import io.cloudstate.kotlinsupport.initializers.CloudStateInitializer;
import io.cloudstate.kotlinsupport.protocol.EntityDiscoveryService;
import io.cloudstate.kotlinsupport.protocol.EventSourcedService;
import io.cloudstate.kotlinsupport.services.StatefulService;
import io.cloudstate.protocol.EntityDiscoveryHandlerFactory;
import io.cloudstate.protocol.EventSourcedHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class CloudStateRunner {
    private static Logger log = LoggerFactory.getLogger(CloudStateRunner.class);

    private CloudStateInitializer initializer;
    private ActorSystem system;
    private ActorMaterializer materializer;

    private Map<String, StatefulService> services = new HashMap<>();

    public CloudStateRunner(CloudStateInitializer initializer, ActorSystem system, ActorMaterializer materializer) {
        this.initializer = initializer;
        this.system = system;
        this.materializer = materializer;
    }

    public CloudStateInitializer getInitializer() {
        return initializer;
    }

    public void setInitializer(CloudStateInitializer initializer) {
        this.initializer = initializer;
    }

    public ActorSystem getSystem() {
        return system;
    }

    public void setSystem(ActorSystem system) {
        this.system = system;
    }

    public ActorMaterializer getMaterializer() {
        return materializer;
    }

    public void setMaterializer(ActorMaterializer materializer) {
        this.materializer = materializer;
    }

    public CompletionStage<ServerBinding> start() throws Exception {

        Function<HttpRequest, CompletionStage<HttpResponse>> entityDiscoveryService =
                EntityDiscoveryHandlerFactory.create(new EntityDiscoveryService(initializer, materializer), materializer, system);

        Function<HttpRequest, CompletionStage<HttpResponse>> eventSourcedService =
                EventSourcedHandlerFactory.create(new EventSourcedService(initializer), materializer, system);

        Function<HttpRequest, CompletionStage<HttpResponse>> serviceHandlers =
                ServiceHandler.concatOrNotFound(entityDiscoveryService, eventSourcedService);

        CompletionStage<ServerBinding> bound = Http.get(system).bindAndHandleAsync(
                serviceHandlers,
                ConnectHttp.toHost(initializer.getHost(), initializer.getPort()),
                materializer);

        bound.thenAccept(binding -> log.info("gRPC server bound to: {}", binding.localAddress()) );

        return bound;
    }

}
