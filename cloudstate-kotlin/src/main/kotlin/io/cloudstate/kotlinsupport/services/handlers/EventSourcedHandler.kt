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
                .match(EventSourcedProto.EventSourcedStreamIn::class.java) {

                    if (it.hasInit()) {
                        self.tell(it.init, self)
                    }

                    if (it.hasCommand()) {
                        self.tell(it.command, self)
                    }

                    if (it.hasEvent()) {
                        self.tell(it.event, self)
                    }

                }
                .match(EventSourcedProto.EventSourcedInit::class.java) {
                    log.info("Receive EventSourcedInit {}", it)
                    sender.tell(EventSourcedProto.EventSourcedStreamOut.newBuilder().build() ,self)
                }
                .match(EventSourcedProto.EventSourcedEvent::class.java) {
                    log.info("Receive EventSourcedEvent {}", it)
                    sender.tell(EventSourcedProto.EventSourcedStreamOut.newBuilder().build() ,self)
                }
                .match(EntityProto.Command::class.java) {
                    log.info("Receive Command {}", it)
                    sender.tell(EventSourcedProto.EventSourcedStreamOut.newBuilder().build() ,self)
                }
                .build()

}