package es.urjc.realfood.payments.unit

import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.payments.application.GetClientBalance
import es.urjc.realfood.payments.application.GetClientBalanceTest
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.payments.domain.repository.ClientRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        ClientRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetClientBalanceUnitaryTest : GetClientBalanceTest() {

    lateinit var clientRepository: ClientRepository
    lateinit var getClientBalance: GetClientBalance

    @BeforeAll
    fun init() {
        clientRepository = mock(ClientRepository::class.java)
        getClientBalance = GetClientBalance(
            clientRepository = clientRepository
        )
    }

    @Test
    fun `given valid request when get client balance then return the actual user balance`() {
        `when`(clientRepository.findById(validClientId())).thenReturn(validClient())

        val response = getClientBalance(validGetClientBalanceRequest())

        assertEquals(validGetClientBalanceResponse(), response)
    }

    @Test
    fun `given invalid id request when get client balance then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            getClientBalance(invalidGetClientBalanceRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request when get non existent client balance then return entity not found exception`() {
        `when`(clientRepository.findById(validClientId())).thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            getClientBalance(validGetClientBalanceRequest())
        }

        assertEquals("Client not found", exc.message)
    }

}