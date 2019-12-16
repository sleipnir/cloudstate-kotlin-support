package io.cloudstate.kotlinsupport.initializers

import io.cloudstate.kotlinsupport.initializers.eventsourced.EventSourcedEntityInitializer
import io.cloudstate.kotlinsupport.services.StatefulService

class CloudStateInitializer {
    val services = mapOf<String, StatefulService>()

    var serviceName: String? = null

    var serviceVersion: String? = null

    var host: String = "0.0.0.0"

    var port: Int = 8088

    internal var eventSourcedInit = EventSourcedEntityInitializer()

    fun registerEventSourcedEntity(eventSourcedInitializer: EventSourcedEntityInitializer.() -> Unit) {
        eventSourcedInit.eventSourcedInitializer()
    }

    internal var crdtSourcedInit = CrdtEntityInitializer()

    fun registerCrdtEntity(crdtInitializer: CrdtEntityInitializer.() -> Unit) {
        crdtSourcedInit.crdtInitializer()
    }

}