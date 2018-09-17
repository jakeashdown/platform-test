package co.wrisk.platformtest.entity

import java.util.*
import kotlin.streams.toList

class BundleSelection(category: Category, val value: Double, val excess: Double) : Bundle(category) {

    // Check if this bundle selection is contained in a QuoteMatrix
    fun isValid(quoteMatrix: QuoteMatrix) : Boolean {
        val bundleOption = quoteMatrix.bundleOptions.stream().filter { it.category == this.category }.findAny()
        if (!bundleOption.isPresent) return false

        return bundleOption.get().valueOptions.contains(value) && bundleOption.get().excessOptions.contains(excess)
    }
}