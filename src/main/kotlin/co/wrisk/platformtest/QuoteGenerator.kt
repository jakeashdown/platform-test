package co.wrisk.platformtest

import co.wrisk.platformtest.entity.*

class QuoteGenerator {

    private fun getBundleValueOptions(category: Category) : List<Double> {
        return when (category) {
            Category.GENERAL -> listOf(2500.0, 5000.0, 10000.0, 15000.0)
            Category.JEWELRY -> listOf(1000.0, 2000.0, 3000.0, 4000.0, 5000.0)
            else -> listOf()
        }
    }

    private fun getExcessOptions(category: Category) : List<Double> {
        return when (category) {
            Category.GENERAL -> listOf(200.0, 300.0, 400.0)
            Category.JEWELRY -> listOf(100.0, 200.0, 300.0)
            Category.ELECTRONICS -> listOf(100.0, 200.0, 300.0, 400.0, 500.0)
            Category.BICYCLES -> listOf(300.0, 400.0, 500.0)
        }
    }

    /*
    * This method takes a list of bundles and a list of named items, and returns a quote matrix containing
    * all possible values of the insured value and excess for each bundle/item
    * */
    fun generateQuoteMatrix(bundles: List<Bundle>, namedItems: List<NamedItem>) : QuoteMatrix {
        return QuoteMatrix(bundles.map { BundleOption(it.category, getBundleValueOptions(it.category), getExcessOptions(it.category)) },
                namedItems.map { NamedItemOption(it.category, it.name, it.value, getExcessOptions(it.category)) })
    }
}