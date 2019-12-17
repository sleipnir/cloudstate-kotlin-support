package io.cloudstate.kotlinsupport.services.handlers

import akka.actor.AbstractActor
import io.cloudstate.kotlinsupport.initializers.CloudStateInitializer
import io.cloudstate.kotlinsupport.logger
import io.cloudstate.protocol.EntityProto
import io.cloudstate.protocol.EventSourcedProto

class EventSourcedHandler(val functions: List<CloudStateInitializer.EntityFunction>): AbstractActor() {
    private val log = logger()

    override fun createReceive(): Receive =
        receiveBuilder()
                .match(EventSourcedProto.EventSourcedInit::class.java) {
                    log.info("Receive EventSourcedInit \n{}", it)
                    sender.tell(EventSourcedProto.EventSourcedStreamOut.newBuilder().build() ,self)
                }
                .match(EventSourcedProto.EventSourcedEvent::class.java) {
                    log.info("Receive EventSourcedEvent \n{}", it)
                    sender.tell(EventSourcedProto.EventSourcedStreamOut.newBuilder().build() ,self)
                }
                .match(EntityProto.Command::class.java) {
                    log.info("Receive Command \n{}", it)
                    sender.tell(EventSourcedProto.EventSourcedStreamOut.newBuilder().build() ,self)
                }
                .build()

}