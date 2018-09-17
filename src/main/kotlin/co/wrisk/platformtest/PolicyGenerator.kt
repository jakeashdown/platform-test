package co.wrisk.platformtest

import co.wrisk.platformservice.contracts.BundleContract
import co.wrisk.platformservice.contracts.NamedItemContract
import co.wrisk.platformservice.contracts.PolicyContract
import co.wrisk.platformservice.contracts.SectionContract
import co.wrisk.platformtest.entity.BundleSelection
import co.wrisk.platformtest.entity.Category
import co.wrisk.platformtest.entity.NamedItemSelection
import co.wrisk.platformtest.entity.Quote
import java.math.BigDecimal
import kotlin.streams.toList
import java.math.RoundingMode


class PolicyGenerator{

    private val WRISK_SCORE_MAX = BigDecimal(1000)
    private val WRISK_FACTOR = BigDecimal(0.0015)

    private fun calculatePrice(wriskScore: Int, sumInsured: BigDecimal, excess: BigDecimal, category: Category) : BigDecimal {
        if (sumInsured.equals(BigDecimal.ZERO)) return BigDecimal.ZERO

        val multiplier = when(category) {
            Category.GENERAL -> BigDecimal(0.1)
            Category.JEWELRY -> BigDecimal(2.0)
            Category.ELECTRONICS -> BigDecimal(1.0)
            Category.BICYCLES -> BigDecimal(0.8)
        }
        return (WRISK_SCORE_MAX.divide(BigDecimal(wriskScore), 2, RoundingMode.UP))
                .multiply(sumInsured)
                .multiply(BigDecimal.ONE.minus(excess.divide(sumInsured, 2, RoundingMode.UP)))
                .multiply(WRISK_FACTOR).multiply(multiplier)
    }

    // Since we've already extended BundleSelection and NamedItemSelection from classes which can't implement the getPrice method,
    // add this functionality as an extension function to avoid duplicating code
    private fun BundleSelection.getPrice(wriskScore: Int) : BigDecimal {
        return calculatePrice(wriskScore, this.value.toBigDecimal(), this.excess.toBigDecimal(), this.category)
    }
    private fun NamedItemSelection.getPrice(wriskScore: Int) : BigDecimal {
        return calculatePrice(wriskScore, this.value.toBigDecimal(), this.excess.toBigDecimal(), this.category)
    }

    fun generatePolicy(quote: Quote, wriskScore: Int): PolicyContract {
        // Get the list of sections needed for the output
        val allSections = quote.bundles.stream().map { it.category }.toList().union(quote.namedItems.stream().map { it.category }.toList())
        val sectionContracts: HashMap<Category, SectionContract> = HashMap()

        var totalPrice = BigDecimal.ZERO
        var highestExcess = BigDecimal.ZERO
        var totalSumInsured = BigDecimal.ZERO

        for (section in allSections) {
            val bundle: BundleSelection? = quote.bundles.find { it.category == section }
            val namedItems = quote.namedItems.filter { it.category == section }
            val sumInsured = bundle?.value?:0.0 + namedItems.fold(0.0) { sum, namedItem -> sum + namedItem.value }

            val sectionContract = SectionContract(section.toString(),
                    sumInsured.toBigDecimal(),
                    if (bundle == null) { null } else { BundleContract(bundle.value.toBigDecimal(), bundle.excess.toBigDecimal()) },
                    namedItems.map { NamedItemContract(it.name, it.value.toBigDecimal(), it.excess.toBigDecimal()) }
            )

            sectionContracts.put(section, sectionContract)

            // Calculate the total price
            if (bundle != null) totalPrice = totalPrice + bundle.getPrice(wriskScore)
            for (namedItem in sectionContract.namedItems) {
                totalPrice = totalPrice
                        .add(calculatePrice(wriskScore, namedItem.sumInsured, namedItem.excess, section))
            }

            // Calculate the highest excess
            if (sectionContract.bundle?.excess ?: BigDecimal.ZERO > highestExcess) highestExcess = sectionContract.bundle?.excess
            for (namedItem in sectionContract.namedItems) {
                if (namedItem.excess > highestExcess) highestExcess = namedItem.excess
            }

            // Calculate the total sum insured
            totalSumInsured = totalSumInsured.add(sectionContract.bundle?.sumInsured ?: BigDecimal.ZERO)
            for (namedItem in sectionContract.namedItems) {
                totalSumInsured = totalSumInsured.add(namedItem.sumInsured)
            }
        }

        return PolicyContract(totalPrice, highestExcess, totalSumInsured, sectionContracts.values.toList())
    }

}
