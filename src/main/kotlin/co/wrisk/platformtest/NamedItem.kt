package co.wrisk.platformtest

import java.math.BigDecimal

data class NamedItem(
        val name: String,
        val sumInsured: BigDecimal,
        val excess: BigDecimal
)