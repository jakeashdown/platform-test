package co.wrisk.platformtest.entity

import java.util.*

class NamedItemSelection(category: Category, name: String, value:Double, val excess: Double) : NamedItem(category, name, value) {

    // Check if this named item selection is contained in a QuoteMatrix
    fun isValid(quoteMatrix: QuoteMatrix) : Boolean {
        val namedItemOption = quoteMatrix.namedItemOptions.stream().filter { it.category == this.category }.findAny()
        if (!namedItemOption.isPresent) return false

        return namedItemOption.get().name.equals(this.name)
                && namedItemOption.get().value == this.value
                && namedItemOption.get().excessOptions.contains(excess)
    }
}
