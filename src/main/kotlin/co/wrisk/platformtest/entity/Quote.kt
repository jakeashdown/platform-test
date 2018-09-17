package co.wrisk.platformtest.entity

class Quote(val bundles: List<BundleSelection>, val namedItems: List<NamedItemSelection>) {

    // Check if all bundles and named items are contained in a QuoteMatrix
    fun isValid(matrix: QuoteMatrix) : Boolean {
        if (bundles.any { !it.isValid(matrix) }) return false
        if (namedItems.any { !it.isValid(matrix) }) return false
        return true
    }
}