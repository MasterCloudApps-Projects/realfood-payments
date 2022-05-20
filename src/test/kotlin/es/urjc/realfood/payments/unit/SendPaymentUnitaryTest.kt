package es.urjc.realfood.payments.unit

import es.urjc.realfood.payments.application.SendPayment
import es.urjc.realfood.payments.application.SendPaymentTest
import es.urjc.realfood.payments.domain.PaymentObjectProvider.Companion.validPayedEvent
import es.urjc.realfood.payments.domain.services.PayedEventPublisher
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        PayedEventPublisher::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SendPaymentUnitaryTest : SendPaymentTest() {

    lateinit var payedEventPublisher: PayedEventPublisher
    lateinit var sendPayment: SendPayment

    @BeforeAll
    fun init() {
        payedEventPublisher = mock(PayedEventPublisher::class.java)
        sendPayment = SendPayment(
            payedEventPublisher = payedEventPublisher
        )
    }

    @Test
    fun `given valid request when send payed event then send message to payed event publisher`() {
        sendPayment(validSendPaymentRequest())

        verify(payedEventPublisher, atLeastOnce()).invoke(validPayedEvent())
    }

}