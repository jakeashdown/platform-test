package co.wrisk.platformtest

import java.math.BigDecimal

data class Policy(
        val highestExcess: BigDecimal,
        val totalSumInsured: BigDecimal,
        val sections: List<Section>
)