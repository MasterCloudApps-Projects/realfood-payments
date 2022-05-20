package es.urjc.realfood.payments.unit

import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.payments.application.UpdateBalance
import es.urjc.realfood.payments.application.UpdateBalanceTest
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validBalanceDouble
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.payments.domain.PaymentObjectProvider.Companion.validQuantityDouble
import es.urjc.realfood.payments.domain.repository.ClientRepository
import es.urjc.realfood.payments.domain.repository.PaymentRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        ClientRepository::class,
        PaymentRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateOrderStatusUnitaryTest : UpdateBalanceTest() {

    lateinit var clientRepository: ClientRepository
    lateinit var paymentRepository: PaymentRepository
    lateinit var updateBalance: UpdateBalance

    @BeforeAll
    fun init() {
        clientRepository = mock(ClientRepository::class.java)
        paymentRepository = mock(PaymentRepository::class.java)
        updateBalance = UpdateBalance(
            clientRepository = clientRepository,
            paymentRepository = paymentRepository
        )
    }

    @Test
    fun `given valid request when add valid balance then return updated balance`() {
        val client = validClient()

        `when`(clientRepository.findById(validClientId()))
            .thenReturn(client)

        val response = updateBalance(validAddBalanceRequest())

        assertEquals(validBalanceDouble() + validQuantityDouble(), client.balance.value())
        verify(clientRepository, atLeastOnce()).save(client)
        assertEquals(validAddBalanceResponse(), response)
    }

    @Test
    fun `given valid request when remove valid balance then return updated balance`() {
        val client = validClient()

        `when`(clientRepository.findById(validClientId()))
            .thenReturn(client)

        val response = updateBalance(validRemoveBalanceRequest())

        assertEquals(validBalanceDouble() - validQuantityDouble(), client.balance.value())
        verify(clientRepository, atLeastOnce()).save(client)
        assertEquals(validRemoveBalanceResponse(), response)
    }

    @Test
    fun `given invalid client id request when update balance then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            updateBalance(invalidClientIdRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request but non existent client request when update balance then return entity not found exception`() {
        `when`(clientRepository.findById(validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            updateBalance(validAddBalanceRequest())
        }

        assertEquals("Client not found", exc.message)
    }

    @Test
    fun `given invalid quantity request when update balance then return illegal argument exception`() {
        `when`(clientRepository.findById(validClientId()))
            .thenReturn(validClient())

        val exc = assertThrows(IllegalArgumentException::class.java) {
            updateBalance(invalidQuantityRequest())
        }

        assertEquals("Quantity cannot be negative", exc.message)
    }

    @Test
    fun `given valid request when update balance for user without enough balance then return illegal argument exception`() {
        `when`(clientRepository.findById(validClientId()))
            .thenReturn(validClient())

        val exc = assertThrows(IllegalArgumentException::class.java) {
            updateBalance(invalidBalanceRequest())
        }

        assertEquals("Balance cannot be negative", exc.message)
    }

}