
// Generated by Akka gRPC. DO NOT EDIT.
package io.cloudstate.protocol;

import akka.grpc.internal.*;
import akka.grpc.GrpcClientSettings;
import akka.grpc.javadsl.AkkaGrpcClient;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

import io.cloudstate.protocol.EntityDiscovery;
import io.cloudstate.protocol.EntityDiscoveryClientPowerApi;
import io.grpc.ManagedChannel;
import io.grpc.MethodDescriptor;

import static io.cloudstate.protocol.EntityDiscovery.Serializers.*;

import scala.concurrent.ExecutionContext;


import akka.grpc.javadsl.SingleResponseRequestBuilder;


public abstract class EntityDiscoveryClient extends EntityDiscoveryClientPowerApi implements EntityDiscovery, AkkaGrpcClient {
  public static final io.cloudstate.protocol.EntityDiscoveryClient create(GrpcClientSettings settings, Materializer mat, ExecutionContext ec) {
    return new DefaultEntityDiscoveryClient(settings, mat, ec);
  }

  protected final static class DefaultEntityDiscoveryClient extends io.cloudstate.protocol.EntityDiscoveryClient {

      private final ClientState clientState;
      private final GrpcClientSettings settings;
      private final io.grpc.CallOptions options;
      private final Materializer mat;
      private final ExecutionContext ec;

      private DefaultEntityDiscoveryClient(GrpcClientSettings settings, Materializer mat, ExecutionContext ec) {
        this.settings = settings;
        this.mat = mat;
        this.ec = ec;
        this.clientState = new ClientState(settings, mat, ec);
        this.options = NettyClientUtils.callOptions(settings);

        if (mat instanceof ActorMaterializer) {
          ((ActorMaterializer) mat).system().getWhenTerminated().whenComplete((v, e) -> close());
        }
      }

  
    
      private final SingleResponseRequestBuilder<io.cloudstate.protocol.EntityProto.ProxyInfo, io.cloudstate.protocol.EntityProto.EntitySpec> discoverRequestBuilder(scala.concurrent.Future<ManagedChannel> channel){
        return new JavaUnaryRequestBuilder<>(discoverDescriptor, channel, options, settings, ec);
      }
    
  
    
      private final SingleResponseRequestBuilder<io.cloudstate.protocol.EntityProto.UserFunctionError, com.google.protobuf.Empty> reportErrorRequestBuilder(scala.concurrent.Future<ManagedChannel> channel){
        return new JavaUnaryRequestBuilder<>(reportErrorDescriptor, channel, options, settings, ec);
      }
    
  

      

        /**
         * For access to method metadata use the parameterless version of discover
         */
        public java.util.concurrent.CompletionStage<io.cloudstate.protocol.EntityProto.EntitySpec> discover(io.cloudstate.protocol.EntityProto.ProxyInfo request) {
          return discover().invoke(request);
        }

        /**
         * Lower level "lifted" version of the method, giving access to request metadata etc.
         * prefer discover(io.cloudstate.protocol.EntityProto.ProxyInfo) if possible.
         */
        
          public SingleResponseRequestBuilder<io.cloudstate.protocol.EntityProto.ProxyInfo, io.cloudstate.protocol.EntityProto.EntitySpec> discover()
        
        {
          return clientState.withChannel( this::discoverRequestBuilder);
        }
      

        /**
         * For access to method metadata use the parameterless version of reportError
         */
        public java.util.concurrent.CompletionStage<com.google.protobuf.Empty> reportError(io.cloudstate.protocol.EntityProto.UserFunctionError request) {
          return reportError().invoke(request);
        }

        /**
         * Lower level "lifted" version of the method, giving access to request metadata etc.
         * prefer reportError(io.cloudstate.protocol.EntityProto.UserFunctionError) if possible.
         */
        
          public SingleResponseRequestBuilder<io.cloudstate.protocol.EntityProto.UserFunctionError, com.google.protobuf.Empty> reportError()
        
        {
          return clientState.withChannel( this::reportErrorRequestBuilder);
        }
      

      
        private static MethodDescriptor<io.cloudstate.protocol.EntityProto.ProxyInfo, io.cloudstate.protocol.EntityProto.EntitySpec> discoverDescriptor =
          MethodDescriptor.<io.cloudstate.protocol.EntityProto.ProxyInfo, io.cloudstate.protocol.EntityProto.EntitySpec>newBuilder()
            .setType(
   MethodDescriptor.MethodType.UNARY 
  
  
  
)
            .setFullMethodName(MethodDescriptor.generateFullMethodName("cloudstate.EntityDiscovery", "discover"))
            .setRequestMarshaller(new ProtoMarshaller<io.cloudstate.protocol.EntityProto.ProxyInfo>(ProxyInfoSerializer))
            .setResponseMarshaller(new ProtoMarshaller<io.cloudstate.protocol.EntityProto.EntitySpec>(EntitySpecSerializer))
            .setSampledToLocalTracing(true)
            .build();
        
        private static MethodDescriptor<io.cloudstate.protocol.EntityProto.UserFunctionError, com.google.protobuf.Empty> reportErrorDescriptor =
          MethodDescriptor.<io.cloudstate.protocol.EntityProto.UserFunctionError, com.google.protobuf.Empty>newBuilder()
            .setType(
   MethodDescriptor.MethodType.UNARY 
  
  
  
)
            .setFullMethodName(MethodDescriptor.generateFullMethodName("cloudstate.EntityDiscovery", "reportError"))
            .setRequestMarshaller(new ProtoMarshaller<io.cloudstate.protocol.EntityProto.UserFunctionError>(UserFunctionErrorSerializer))
            .setResponseMarshaller(new ProtoMarshaller<com.google.protobuf.Empty>(EmptySerializer))
            .setSampledToLocalTracing(true)
            .build();
        

      /**
       * Initiates a shutdown in which preexisting and new calls are cancelled.
       */
      public java.util.concurrent.CompletionStage<akka.Done> close() {
        return clientState.closeCS() ;
      }

     /**
      * Returns a CompletionState that completes successfully when shutdown via close()
      * or exceptionally if a connection can not be established after maxConnectionAttempts.
      */
      public java.util.concurrent.CompletionStage<akka.Done> closed() {
        return clientState.closedCS();
      }
  }

}



