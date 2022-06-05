package es.urjc.realfood.payments.integration

import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.payments.application.GetClientBalance
import es.urjc.realfood.payments.application.GetClientBalanceResponse
import es.urjc.realfood.payments.application.UpdateBalance
import es.urjc.realfood.payments.application.UpdateBalanceResponse
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validBalanceDouble
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validJwt
import es.urjc.realfood.payments.infrastructure.api.rest.ClientRestController
import es.urjc.realfood.payments.infrastructure.api.rest.ClientRestControllerTest
import es.urjc.realfood.payments.infrastructure.api.security.JWTValidatorService
import io.restassured.RestAssured
import io.restassured.config.JsonConfig.jsonConfig
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.path.json.config.JsonPathConfig
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientRestControllerIntegrationTest : ClientRestControllerTest() {

    @LocalServerPort
    var port = 0

    @MockBean
    lateinit var getClientBalance: GetClientBalance

    @MockBean
    lateinit var updateBalance: UpdateBalance

    @MockBean
    lateinit var jwtValidatorService: JWTValidatorService

    lateinit var clientRestController: ClientRestController

    @BeforeAll
    fun setUp() {
        clientRestController = ClientRestController(
            getClientBalance = getClientBalance,
            updateBalance = updateBalance,
            jwtValidatorService = jwtValidatorService
        )

        RestAssured.config =
            RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE))
        RestAssured.port = Integer.parseInt(System.getProperty("port", "$port"))
        RestAssuredMockMvc.standaloneSetup(clientRestController)
    }


    @Test
    fun `given client endpoint when get client balance then return status ok`() {
        Mockito.`when`(jwtValidatorService.getSubjectFromHeaders(Mockito.anyMap()))
            .thenReturn(validClientIdString())

        Mockito.`when`(getClientBalance(validGetClientBalanceRequest()))
            .thenReturn(validGetClientBalanceResponse())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/balance")
            .then()
            .assertThat()
            .statusCode(200)
            .body("clientId", Matchers.equalTo(validClientIdString()))
            .body("balance", Matchers.equalTo(validBalanceDouble()))
            .extract().`as`(GetClientBalanceResponse::class.java)
    }

    @Test
    fun `given client endpoint when get client balance from non existent client then return status code 404`() {
        Mockito.`when`(jwtValidatorService.getSubjectFromHeaders(Mockito.anyMap()))
            .thenReturn(validClientIdString())

        Mockito.`when`(getClientBalance(validGetClientBalanceRequest()))
            .thenThrow(EntityNotFoundException("NOT FOUND"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/balance")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("NOT FOUND"))
    }

    @Test
    fun `given client endpoint when get client balance from wrong id then return status code 400`() {
        Mockito.`when`(jwtValidatorService.getSubjectFromHeaders(Mockito.anyMap()))
            .thenReturn(validClientIdString())

        Mockito.`when`(getClientBalance(validGetClientBalanceRequest()))
            .thenThrow(IllegalArgumentException("ILLEGAL"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/balance")
            .then()
            .assertThat()
            .statusCode(400)
            .body("reason", Matchers.equalTo("ILLEGAL"))
    }

    @Test
    fun `given client endpoint when add client balance then return status ok`() {
        Mockito.`when`(jwtValidatorService.getSubjectFromHeaders(Mockito.anyMap()))
            .thenReturn(validClientIdString())

        Mockito.`when`(updateBalance(validAddBalanceRequest()))
            .thenReturn(validAddBalanceResponse())

        RestAssured.given()
            .request()
            .body(validAddBalanceRequestJson())
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .post("/api/balance")
            .then()
            .assertThat()
            .statusCode(200)
            .body("clientId", Matchers.equalTo(validClientIdString()))
            .body("actualBalance", Matchers.equalTo(validBalanceDouble()))
            .extract().`as`(UpdateBalanceResponse::class.java)
    }

    @Test
    fun `given client endpoint when add client balance from non existent client then return status code 404`() {
        Mockito.`when`(jwtValidatorService.getSubjectFromHeaders(Mockito.anyMap()))
            .thenReturn(validClientIdString())

        Mockito.`when`(updateBalance(validAddBalanceRequest()))
            .thenThrow(EntityNotFoundException("NOT FOUND"))

        RestAssured.given()
            .request()
            .body(validAddBalanceRequestJson())
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .post("/api/balance")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("NOT FOUND"))
    }

}