package io.cloudstate.kotlinsupport.examples.shopping.cart

import com.example.shoppingcart.Shoppingcart
import io.cloudstate.kotlinsupport.EntityType
import io.cloudstate.kotlinsupport.cloudstate
import io.cloudstate.kotlinsupport.services.StatefulService

class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            cloudstate {

                serviceName = "shopping-cart"
                serviceVersion = "1.0.0"

                //host = "0.0.0.0"
                //port = 8088

                registerEventSourcedEntity {
                    statefulService = ShoppingCartService()

                    descriptor = Shoppingcart.getDescriptor().findServiceByName("ShoppingCart")
                    additionalDescriptors = arrayOf(
                            com.example.shoppingcart.persistence.Domain.getDescriptor() )

                    //snapshotEvery = 1
                    persistenceId = "shopping-cart"
                }

                // registerCrdtEntity {  }

            }.start()
                .toCompletableFuture()
                .get()

        }
    }

}



class ShoppingCartService : StatefulService {

    var entityKeyId: String? = null

    override fun getEntityId(): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
