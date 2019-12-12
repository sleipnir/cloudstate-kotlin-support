package io.cloudstate.kotlinsupport.examples.shopping.cart

import com.example.shoppingcart.Shoppingcart
import io.cloudstate.kotlinsupport.EntityType
import io.cloudstate.kotlinsupport.cloudstate

class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            cloudstate {

                type = EntityType.EventSourced

                port = 8090

                eventSourced {
                    descriptor = Shoppingcart.getDescriptor().findServiceByName("ShoppingCart")
                    additionalDescriptors = arrayOf(
                            com.example.shoppingcart.persistence.Domain.getDescriptor() )
                }

            }.start()

        }
    }

}