package es.urjc.realfood.payments

import org.springframework.amqp.core.Queue
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class RealfoodPaymentsApplication {

    @Bean
    fun payments(): Queue {
        return Queue("checkout-cart", false)
    }

    @Bean
    fun registers(): Queue {
        return Queue("register-queue", false)
    }

    @Bean
    fun deletions(): Queue {
        return Queue("delete-client-queue", false)
    }

}

fun main(args: Array<String>) {
    runApplication<RealfoodPaymentsApplication>(*args)
}
