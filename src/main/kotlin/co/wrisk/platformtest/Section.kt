package co.wrisk.platformtest

import java.math.BigDecimal

data class Section(
        val name: String,
        val sumInsured: BigDecimal,
        val unNamedExcess: BigDecimal,
        val unNamedSumInsured: BigDecimal,
        val namedItems: List<NamedItem>
)

