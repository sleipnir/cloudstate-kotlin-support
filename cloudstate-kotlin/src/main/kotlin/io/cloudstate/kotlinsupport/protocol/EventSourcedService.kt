package io.cloudstate.kotlinsupport.protocol

import akka.NotUsed
import akka.actor.ActorRef
import akka.event.Logging
import akka.stream.Attributes
import akka.stream.javadsl.Source
import akka.util.Timeout
import io.cloudstate.kotlinsupport.initializers.CloudStateInitializer
import io.cloudstate.protocol.EventSourced
import io.cloudstate.protocol.EventSourcedProto
import java.time.Duration

class EventSourcedService(
        private val initializer: CloudStateInitializer,
        private val handler: ActorRef) : EventSourced {


    override fun handle(streamIn: Source<EventSourcedProto.EventSourcedStreamIn, NotUsed>?): Source<EventSourcedProto.EventSourcedStreamOut, NotUsed> =
        streamIn!!.log("CloudState-User")
                .withAttributes(
                        Attributes.createLogLevels(
                                Logging.InfoLevel(), // onElement
                                Logging.WarningLevel(), // onFinish
                                Logging.DebugLevel() // onFailure
                        ))
                .ask( handler, EventSourcedProto.EventSourcedStreamOut::class.java, Timeout.create(Duration.ofMillis(initializer.functionTimeout)) )!!

}