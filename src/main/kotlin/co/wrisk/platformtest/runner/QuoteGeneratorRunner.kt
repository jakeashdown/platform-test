package co.wrisk.platformtest.runner

import co.wrisk.platformtest.QuoteGenerator
import co.wrisk.platformtest.entity.Bundle
import co.wrisk.platformtest.entity.Category
import co.wrisk.platformtest.entity.NamedItem
import co.wrisk.platformtest.entity.QuoteMatrix

fun main(args: Array<String>) {

    val generator = QuoteGenerator()

    println("Generating quote matrix for Rick Sanchez")
    printQuote(generator.generateQuoteMatrix(listOf(Bundle(Category.GENERAL)), listOf(NamedItem(Category.ELECTRONICS, "Portal gun", 2000.0))))

    println("Generating quote matrix for Morty Smith")
    printQuote(generator.generateQuoteMatrix(listOf(Bundle(Category.GENERAL), Bundle(Category.JEWELRY)), listOf(NamedItem(Category.BICYCLES, "Schwinn bike", 500.0))))

    println("Generating quote matrix for Jerry Smith")
    printQuote(generator.generateQuoteMatrix(listOf(Bundle(Category.GENERAL)), listOf(NamedItem(Category.JEWELRY, "Wedding ring", 400.0))))
}

fun printQuote(quoteMatrix: QuoteMatrix) {
    println("Bundle options:")
    for (bundleOption in quoteMatrix.bundleOptions) {
        println("Category [${bundleOption.category}], value options ${bundleOption.valueOptions}, excess options ${bundleOption.excessOptions}")
    }
    println("Named item options:")
    for (namedItemOption in quoteMatrix.namedItemOptions) {
        println("Category [${namedItemOption.category}], name [${namedItemOption.name}], value [${namedItemOption.value}], excess options ${namedItemOption.excessOptions}")
    }
    println()
}