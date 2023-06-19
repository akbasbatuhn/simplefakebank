package com.fakedevbankapp.account.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class CreateAccountRequest(
        @field:NotBlank(message = "Customer id must be given")
        val customerId: String,
        @field:Min(0, message = "Initial credit value must be non negative value")
        val initialCredit: BigDecimal
)
