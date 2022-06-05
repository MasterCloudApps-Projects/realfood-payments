package es.urjc.realfood.payments.unit

import es.urjc.realfood.payments.application.RegisterClient
import es.urjc.realfood.payments.application.RegisterClientTest
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validNewClient
import es.urjc.realfood.payments.domain.repository.ClientRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        ClientRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegisterClientUnitaryTest : RegisterClientTest() {

    lateinit var clientRepository: ClientRepository
    lateinit var registerClient: RegisterClient

    @BeforeAll
    fun init() {
        clientRepository = mock(ClientRepository::class.java)
        registerClient = RegisterClient(
            clientRepository = clientRepository
        )
    }

    @Test
    fun `given valid request when register client then return the new registered user id`() {
        `when`(clientRepository.findById(validClientId())).thenReturn(null)

        val response = registerClient(validRegisterClientRequest())

        verify(clientRepository, atLeastOnce()).save(validNewClient())
        assertEquals(validRegisterClientResponse(), response)
    }

    @Test
    fun `given invalid id request when register client then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(invalidRegisterClientRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request when register existent client then return illegal argument exception`() {
        `when`(clientRepository.findById(validClientId())).thenReturn(validClient())

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(validRegisterClientRequest())
        }

        assertEquals("User already registered with id ${validClientIdString()}", exc.message)
    }

}