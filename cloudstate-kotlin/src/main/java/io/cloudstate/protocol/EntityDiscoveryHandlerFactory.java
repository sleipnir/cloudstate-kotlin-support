
// Generated by Akka gRPC. DO NOT EDIT.
package io.cloudstate.protocol;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import akka.japi.Function;
import akka.http.javadsl.model.*;
import akka.actor.ActorSystem;
import akka.stream.Materializer;

import akka.grpc.Codec;
import akka.grpc.Codecs;
import akka.grpc.javadsl.GrpcMarshalling;
import akka.grpc.javadsl.GrpcExceptionHandler;
import akka.grpc.javadsl.package$;
import io.cloudstate.protocol.EntityDiscovery;


import static io.cloudstate.protocol.EntityDiscovery.Serializers.*;


public class EntityDiscoveryHandlerFactory {

  private static final CompletionStage<HttpResponse> notFound = CompletableFuture.completedFuture(
    HttpResponse.create().withStatus(StatusCodes.NOT_FOUND));

  /**
   * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example `Http().bindAndHandleAsync`
   * for the generated partial function handler and ends with `StatusCodes.NotFound` if the request is not matching.
   *
   * Use `akka.grpc.scaladsl.ServiceHandler.concatOrNotFound` with `EntityDiscoveryHandler.partial` when combining
   * several services.
   */
  public static Function<HttpRequest, CompletionStage<HttpResponse>> create(EntityDiscovery implementation, Materializer mat, ActorSystem system) {
    return create(implementation, EntityDiscovery.name, mat, system);
  }

  /**
   * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example `Http().bindAndHandleAsync`
   * for the generated partial function handler and ends with `StatusCodes.NotFound` if the request is not matching.
   *
   * Use `akka.grpc.scaladsl.ServiceHandler.concatOrNotFound` with `EntityDiscoveryHandler.partial` when combining
   * several services.
   */
  public static Function<HttpRequest, CompletionStage<HttpResponse>> create(EntityDiscovery implementation, Materializer mat, Function<ActorSystem, Function<Throwable, io.grpc.Status>> eHandler, ActorSystem system) {
    return create(implementation, EntityDiscovery.name, mat, eHandler, system);
  }

  /**
   * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example `Http().bindAndHandleAsync`
   * for the generated partial function handler and ends with `StatusCodes.NotFound` if the request is not matching.
   *
   * Use `akka.grpc.scaladsl.ServiceHandler.concatOrNotFound` with `EntityDiscoveryHandler.partial` when combining
   * several services.
   *
   * Registering a gRPC service under a custom prefix is not widely supported and strongly discouraged by the specification.
   */
  public static Function<HttpRequest, CompletionStage<HttpResponse>> create(EntityDiscovery implementation, String prefix, Materializer mat, ActorSystem system) {
    return partial(implementation, prefix, mat, GrpcExceptionHandler.defaultMapper(), system);
  }

  /**
   * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example `Http().bindAndHandleAsync`
   * for the generated partial function handler and ends with `StatusCodes.NotFound` if the request is not matching.
   *
   * Use `akka.grpc.scaladsl.ServiceHandler.concatOrNotFound` with `EntityDiscoveryHandler.partial` when combining
   * several services.
   *
   * Registering a gRPC service under a custom prefix is not widely supported and strongly discouraged by the specification.
   */
  public static Function<HttpRequest, CompletionStage<HttpResponse>> create(EntityDiscovery implementation, String prefix, Materializer mat, Function<ActorSystem, Function<Throwable, io.grpc.Status>> eHandler, ActorSystem system) {
    return partial(implementation, prefix, mat, eHandler, system);
  }

  /**
   * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example
   * `Http.get(system).bindAndHandleAsync`. It ends with `StatusCodes.NotFound` if the request is not matching.
   *
   * Use `akka.grpc.javadsl.ServiceHandler.concatOrNotFound` when combining several services.
   */
  public static Function<HttpRequest, CompletionStage<HttpResponse>> partial(EntityDiscovery implementation, String prefix, Materializer mat, ActorSystem system) {
    return partial(implementation, prefix, mat, GrpcExceptionHandler.defaultMapper(), system);
  }

  /**
   * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example
   * `Http.get(system).bindAndHandleAsync`. It ends with `StatusCodes.NotFound` if the request is not matching.
   *
   * Use `akka.grpc.javadsl.ServiceHandler.concatOrNotFound` when combining several services.
   */
  public static Function<HttpRequest, CompletionStage<HttpResponse>> partial(EntityDiscovery implementation, String prefix, Materializer mat, Function<ActorSystem, Function<Throwable, io.grpc.Status>> eHandler, ActorSystem system) {
    return (req -> {
      Iterator<String> segments = req.getUri().pathSegments().iterator();
      if (segments.hasNext() && segments.next().equals(prefix) && segments.hasNext()) {
        String method = segments.next();
        if (segments.hasNext()) return notFound; // we don't allow any random `/prefix/Method/anything/here
        else return handle(req, method, implementation, mat, eHandler, system).exceptionally(e -> GrpcExceptionHandler.standard(e, eHandler, system));
      } else {
        return notFound;
      }
    });
  }

    public String getServiceName() {
      return EntityDiscovery.name;
    }

  private static CompletionStage<HttpResponse> handle(HttpRequest request, String method, EntityDiscovery implementation, Materializer mat, Function<ActorSystem, Function<Throwable, io.grpc.Status>> eHandler, ActorSystem system) {
    Codec responseCodec = Codecs.negotiate(request);

    switch(method) {

      case "discover":
        return GrpcMarshalling.unmarshal(request, ProxyInfoSerializer, mat)
          .thenCompose(e -> implementation.discover(e))
          .thenApply(e -> GrpcMarshalling.marshal(e, EntitySpecSerializer, mat, responseCodec, system, package$.MODULE$.scalaAnonymousPartialFunction(eHandler)));

      case "reportError":
        return GrpcMarshalling.unmarshal(request, UserFunctionErrorSerializer, mat)
          .thenCompose(e -> implementation.reportError(e))
          .thenApply(e -> GrpcMarshalling.marshal(e, EmptySerializer, mat, responseCodec, system, package$.MODULE$.scalaAnonymousPartialFunction(eHandler)));

      default:
        CompletableFuture<HttpResponse> result = new CompletableFuture<>();
        result.completeExceptionally(new UnsupportedOperationException("Not implemented: " + method));
        return result;
    }
  }
}

