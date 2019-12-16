
// Generated by Akka gRPC. DO NOT EDIT.
package io.cloudstate.protocol;

import akka.grpc.ProtobufSerializer;
import akka.grpc.javadsl.GoogleProtobufSerializer;

/**
 * The Entity service
 */
public interface EventSourced {
  
  /**
   * The stream. One stream will be established per active entity.
   * Once established, the first message sent will be Init, which contains the entity ID, and,
   * if the entity has previously persisted a snapshot, it will contain that snapshot. It will
   * then send zero to many event messages, one for each event previously persisted. The entity
   * is expected to apply these to its state in a deterministic fashion. Once all the events
   * are sent, one to many commands are sent, with new commands being sent as new requests for
   * the entity come in. The entity is expected to reply to each command with exactly one reply
   * message. The entity should reply in order, and any events that the entity requests to be
   * persisted the entity should handle itself, applying them to its own state, as if they had
   * arrived as events when the event stream was being replayed on load.
   */
  akka.stream.javadsl.Source<io.cloudstate.protocol.EventSourcedProto.EventSourcedStreamOut, akka.NotUsed> handle(akka.stream.javadsl.Source<io.cloudstate.protocol.EventSourcedProto.EventSourcedStreamIn, akka.NotUsed> in);
  

  static String name = "cloudstate.eventsourced.EventSourced";

  public static class Serializers {
    
      public static ProtobufSerializer<io.cloudstate.protocol.EventSourcedProto.EventSourcedStreamIn> EventSourcedStreamInSerializer = new GoogleProtobufSerializer<>(io.cloudstate.protocol.EventSourcedProto.EventSourcedStreamIn.class);
    
      public static ProtobufSerializer<io.cloudstate.protocol.EventSourcedProto.EventSourcedStreamOut> EventSourcedStreamOutSerializer = new GoogleProtobufSerializer<>(io.cloudstate.protocol.EventSourcedProto.EventSourcedStreamOut.class);
    
  }
}
