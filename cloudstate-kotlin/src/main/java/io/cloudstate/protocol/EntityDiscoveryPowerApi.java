
// Generated by Akka gRPC. DO NOT EDIT.
package io.cloudstate.protocol;

import akka.grpc.ProtobufSerializer;
import akka.grpc.javadsl.GoogleProtobufSerializer;
import akka.grpc.javadsl.Metadata;
import akka.grpc.GrpcServiceException;
import io.cloudstate.protocol.EntityDiscovery;
import io.grpc.Status;

public interface EntityDiscoveryPowerApi extends EntityDiscovery {
  
  java.util.concurrent.CompletionStage<io.cloudstate.protocol.EntityProto.EntitySpec> discover(io.cloudstate.protocol.EntityProto.ProxyInfo in, Metadata metadata);
  
  java.util.concurrent.CompletionStage<com.google.protobuf.Empty> reportError(io.cloudstate.protocol.EntityProto.UserFunctionError in, Metadata metadata);
  

  
  default java.util.concurrent.CompletionStage<io.cloudstate.protocol.EntityProto.EntitySpec> discover(io.cloudstate.protocol.EntityProto.ProxyInfo in) {
    throw new GrpcServiceException(Status.UNIMPLEMENTED);
  }
  
  default java.util.concurrent.CompletionStage<com.google.protobuf.Empty> reportError(io.cloudstate.protocol.EntityProto.UserFunctionError in) {
    throw new GrpcServiceException(Status.UNIMPLEMENTED);
  }
  

  static String name = "cloudstate.EntityDiscovery";

  public static class Serializers {
    
      public static ProtobufSerializer<io.cloudstate.protocol.EntityProto.ProxyInfo> ProxyInfoSerializer = new GoogleProtobufSerializer<>(io.cloudstate.protocol.EntityProto.ProxyInfo.class);
    
      public static ProtobufSerializer<io.cloudstate.protocol.EntityProto.UserFunctionError> UserFunctionErrorSerializer = new GoogleProtobufSerializer<>(io.cloudstate.protocol.EntityProto.UserFunctionError.class);
    
      public static ProtobufSerializer<io.cloudstate.protocol.EntityProto.EntitySpec> EntitySpecSerializer = new GoogleProtobufSerializer<>(io.cloudstate.protocol.EntityProto.EntitySpec.class);
    
      public static ProtobufSerializer<com.google.protobuf.Empty> EmptySerializer = new GoogleProtobufSerializer<>(com.google.protobuf.Empty.class);
    
  }
}
