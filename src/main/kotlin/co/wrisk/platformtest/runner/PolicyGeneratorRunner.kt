package co.wrisk.platformtest.runner

import co.wrisk.platformtest.PolicyGenerator
import co.wrisk.platformtest.QuoteGenerator
import co.wrisk.platformtest.entity.*

fun main(args: Array<String>) {

    val quoteGenerator = QuoteGenerator()
    val policyGenerator = PolicyGenerator()

    println("Generating quote price for Rick Sanchez")
    val rickMatrix = quoteGenerator.generateQuoteMatrix(listOf(Bundle(Category.GENERAL)), listOf(NamedItem(Category.ELECTRONICS, "Portal gun", 2000.0)))
    val rickQuote = Quote(listOf(BundleSelection(Category.GENERAL, 2500.0, 200.0)),
            listOf(NamedItemSelection(Category.ELECTRONICS, "Portal gun", 2000.0, 300.0)))
    exitIfQuoteIsInvalid(rickQuote, rickMatrix)
    println(policyGenerator.generatePolicy(rickQuote, 1000))

    println("Generating quote price for Morty Smith")
    val mortyMatrix  = quoteGenerator.generateQuoteMatrix(listOf(Bundle(Category.GENERAL)), listOf(NamedItem(Category.BICYCLES, "Schwinn bike", 500.0)))
    val mortyQuote = Quote(listOf(BundleSelection(Category.GENERAL, 5000.0, 300.0)),
            listOf(NamedItemSelection(Category.BICYCLES, "Schwinn bike", 500.0, 300.0)))
    exitIfQuoteIsInvalid(mortyQuote, mortyMatrix)
    println(policyGenerator.generatePolicy(mortyQuote, 20))

    println("Generating quote price for Jerry Smith")
    val jerryMatrix = quoteGenerator.generateQuoteMatrix(listOf(Bundle(Category.GENERAL)), listOf(NamedItem(Category.JEWELRY, "Wedding ring", 400.0)))
    val jerryQuote = Quote(listOf(BundleSelection(Category.GENERAL, 15000.0, 400.0)),
            listOf(NamedItemSelection(Category.JEWELRY, "Wedding ring", 400.0, 300.0)))
    exitIfQuoteIsInvalid(jerryQuote, jerryMatrix)
    println(policyGenerator.generatePolicy(jerryQuote, 1))
}

private fun exitIfQuoteIsInvalid(quote: Quote, matrix: QuoteMatrix) {
    if (!quote.isValid(matrix)) {
        println("Quote is invalid! Exiting")
        System.exit(1)
    } else println("Quote is valid")
}