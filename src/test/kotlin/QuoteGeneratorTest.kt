import co.wrisk.platformtest.*
import co.wrisk.platformtest.entity.Bundle
import co.wrisk.platformtest.entity.BundleOption
import co.wrisk.platformtest.entity.Category
import co.wrisk.platformtest.entity.NamedItem
import org.junit.jupiter.api.Test

class QuoteGeneratorTest  {

    val generator : QuoteGenerator = QuoteGenerator()

    @Test
    fun testEmptyQuote() {
        val matrix = generator.generateQuoteMatrix(listOf(), listOf())
        assert(matrix.bundleOptions.isEmpty())
        assert(matrix.namedItemOptions.isEmpty())
    }

    @Test
    fun testSingleBundle() {
        val matrix = generator.generateQuoteMatrix(listOf(Bundle(Category.GENERAL)), listOf())
        assert(matrix.bundleOptions.size == 1)
        assert(matrix.bundleOptions.first().category == Category.GENERAL)
        assert(matrix.bundleOptions.first().valueOptions.contains(5000.0))
        assert(matrix.bundleOptions.first().excessOptions.contains(300.0))
        assert(!matrix.bundleOptions.first().valueOptions.contains(5050.0))
        assert(!matrix.bundleOptions.first().excessOptions.contains(600.0))

        assert(matrix.namedItemOptions.isEmpty())
    }

    @Test
    fun testSingleNamedItem() {
        val matrix = generator.generateQuoteMatrix(listOf(), listOf(NamedItem(Category.ELECTRONICS, "Space ship", 15000.0)))
        assert(matrix.namedItemOptions.size == 1)
        assert(matrix.namedItemOptions.first().category == Category.ELECTRONICS)
        assert(matrix.namedItemOptions.first().excessOptions.contains(500.0))
        assert(!matrix.namedItemOptions.first().excessOptions.contains(50.0))

        assert(matrix.bundleOptions.isEmpty())
    }

    @Test
    fun testSingleBundleAndSingleNamedItem() {
        val matrix = generator.generateQuoteMatrix(listOf(Bundle(Category.JEWELRY)), listOf(NamedItem(Category.BICYCLES, "Langster single speed", 200.0)))
        assert(matrix.bundleOptions.size == 1)
        assert(matrix.bundleOptions.first().category == Category.JEWELRY)
        assert(matrix.bundleOptions.first().valueOptions.contains(1000.0))
        assert(matrix.bundleOptions.first().excessOptions.contains(200.0))
        assert(!matrix.bundleOptions.first().valueOptions.contains(6000.0))
        assert(!matrix.bundleOptions.first().excessOptions.contains(500.0))

        assert(matrix.namedItemOptions.size == 1)
        assert(matrix.namedItemOptions.first().category == Category.BICYCLES)
        assert(matrix.namedItemOptions.first().excessOptions.contains(400.0))
        assert(!matrix.namedItemOptions.first().excessOptions.contains(450.0))
    }

    @Test
    fun testMultipleBundles() {
        val matrix = generator.generateQuoteMatrix(listOf(Bundle(Category.GENERAL), Bundle(Category.JEWELRY)), listOf())
        assert(matrix.bundleOptions.stream().anyMatch { it.category == Category.GENERAL })
        assert(matrix.bundleOptions.stream().anyMatch { it.category == Category.JEWELRY })
        assert(matrix.bundleOptions.stream().noneMatch { it.category == Category.ELECTRONICS })
    }

    @Test
    fun testMultipleNamedItems() {
        val matrix = generator.generateQuoteMatrix(listOf(), listOf(NamedItem(Category.JEWELRY, "Decoder ring", 5.0), NamedItem(Category.ELECTRONICS, "MacBook Pro", 1500.0)))
        assert(matrix.namedItemOptions.stream().anyMatch { it.category == Category.JEWELRY })
        assert(matrix.namedItemOptions.stream().anyMatch { it.category == Category.ELECTRONICS })
        assert(matrix.namedItemOptions.stream().anyMatch { it.category == Category.ELECTRONICS })
        assert(matrix.namedItemOptions.stream().noneMatch { it.category == Category.GENERAL })
    }

}