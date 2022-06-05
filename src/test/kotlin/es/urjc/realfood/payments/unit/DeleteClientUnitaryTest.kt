package es.urjc.realfood.payments.unit

import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.payments.application.DeleteClient
import es.urjc.realfood.payments.application.DeleteClientTest
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientId
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
class DeleteClientUnitaryTest : DeleteClientTest() {

    lateinit var clientRepository: ClientRepository
    lateinit var deleteClient: DeleteClient

    @BeforeAll
    fun init() {
        clientRepository = mock(ClientRepository::class.java)
        deleteClient = DeleteClient(
            clientRepository = clientRepository
        )
    }

    @Test
    fun `given valid request when delete client then return the new Deleteed user id`() {
        `when`(clientRepository.findById(validClientId())).thenReturn(validClient())

        val response = deleteClient(validDeleteClientRequest())

        verify(clientRepository, atLeastOnce()).delete(validClient())
        assertEquals(validDeleteClientResponse(), response)
    }

    @Test
    fun `given invalid id request when delete client then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            deleteClient(invalidDeleteClientRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request when delete existent client then return illegal argument exception`() {
        `when`(clientRepository.findById(validClientId())).thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            deleteClient(validDeleteClientRequest())
        }

        assertEquals("Client not found", exc.message)
    }

}