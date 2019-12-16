package io.cloudstate.kotlinsupport.protocol

import akka.NotUsed
import akka.stream.javadsl.Source
import io.cloudstate.kotlinsupport.initializers.CloudStateInitializer
import io.cloudstate.protocol.EventSourced
import io.cloudstate.protocol.EventSourcedProto

class EventSourcedService(initializer: CloudStateInitializer) : EventSourced {

    override fun handle(streamIn: Source<EventSourcedProto.EventSourcedStreamIn, NotUsed>?): Source<EventSourcedProto.EventSourcedStreamOut, NotUsed> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}