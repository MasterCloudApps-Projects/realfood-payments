package es.urjc.realfood.payments.infrastructure.api.rest

import es.urjc.realfood.payments.application.GetClientBalanceResponse
import es.urjc.realfood.payments.application.UpdateBalanceResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Clients rest API")
@RequestMapping("/api")
interface ClientRestApi {

    @GetMapping("/balance")
    fun getBalance(@RequestHeader headers: Map<String, String>): GetClientBalanceResponse

    @PostMapping("/balance")
    fun addBalance(
        @RequestHeader headers: Map<String, String>,
        @RequestBody addBalanceRequest: AddBalanceRequest
    ): UpdateBalanceResponse

}

data class AddBalanceRequest(
    val balance: Double
)