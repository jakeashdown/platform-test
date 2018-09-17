package co.wrisk.platformservice

import co.wrisk.platformtest.PolicyGenerator
import co.wrisk.platformtest.QuoteGenerator
import co.wrisk.platformtest.entity.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class PolicyGeneratorTest {

    var quoteGenerator = QuoteGenerator()
    var policyGenerator = PolicyGenerator()

    private fun assertWithinTenPercent(expected: BigDecimal, actual: BigDecimal) {
        val deviation = expected.multiply(BigDecimal(0.1))
        assert(expected.minus(deviation) < actual && actual < expected.plus(deviation))
    }

    @Test
    fun testSingleBundle() {
        val matrix = quoteGenerator.generateQuoteMatrix(listOf(Bundle(Category.GENERAL)), listOf())
        val quote = Quote(listOf(BundleSelection(Category.GENERAL, 2500.0, 200.0)), listOf())
        assert(quote.bundles.first().isValid(matrix))

        val policy = policyGenerator.generatePolicy(quote, 500)

        // Price is 0.69, but we need to assert on an approximation since we're doing floating point maths
        assertWithinTenPercent(BigDecimal(0.69), policy.price)

        assert(policy.totalSumInsured.compareTo(BigDecimal(2500)) == 0)
        assert(policy.highestExcess.compareTo(BigDecimal(200)) == 0)
    }

    @Test
    fun testSingleNamedItem() {
        val matrix = quoteGenerator.generateQuoteMatrix(listOf(), listOf(NamedItem(Category.ELECTRONICS, "Space ship", 15000.0)))
        val quote = Quote(listOf(), listOf(NamedItemSelection(Category.ELECTRONICS, "Space ship", 15000.0, 500.0)))
        assert(quote.namedItems.first().isValid(matrix))

        val policy = policyGenerator.generatePolicy(quote, 800)

        // Price is 0.69, but we need to assert on an approximation since we're doing floating point maths
        assertWithinTenPercent(BigDecimal(27), policy.price)

        assert(policy.totalSumInsured.compareTo(BigDecimal(15000.0)) == 0)
        assert(policy.highestExcess.compareTo(BigDecimal(500)) == 0)

    }

}