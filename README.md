# Wrisk unattended platform test

*We’re building the future of insurance by offering a new perspective on risk that makes insuring you, and your lifestyle, easy. The future of insurance is mobile first. The future of insurance is Wrisk. It’s a big claim, sure, but we mean it, and it’s a big job, which is why we need your help.*

## Scenario

At Wrisk we like to be transparent with our customers and make everything as easy as possible. In order to be transparent, we calculate a Wrisk score (think credit score for insurance) which is fed into our pricing models.

Your task is to write a program which can produce a quote and policy for a customer given their individual Wrisk score.

The solution should ideally use gradle and kotlin. You don’t need to be a kotlin expert, none of us were before we started using it. We believe good programmers can easily pick up any language. Here is a resource [https://learnxinyminutes.com/docs/kotlin/](https://learnxinyminutes.com/docs/kotlin/).

If you feel uncomfortable using kotlin feel free to use the language of your choice. Ideally JVM based so that you can use the provide data contracts (see later).

If you feel something is ambiguous please get in-touch with your contact @ Wrisk or leave an `ASSUMPTIONS.md` file listing your assumptions.

Find the initial project setup here: [https://github.com/WriskHQ/platform-test](https://github.com/WriskHQ/platform-test). It has no external dependencies added - feel free to add some if you want.

## Background

### Insurance product

Our contents insurance product is broken down into **Sections** such as general contents, electronics, jewelry, etc. Each section represents a broad category of goods containing a set of **Named Items** and/or a **Bundle**. Named items allow our customers to add individual items that they would like to insure, e.g. a wedding ring with a value of £500, whereas a bundle represents a collection of unnamed items. We provide a provide a discrete set of value options for customers to choose from for each bundle as follows:

| Section | Bundle value options (GBP)   |
|---------|------------------------------|
| General | 2500, 5000, 10000, 15000     |
| Jewelry | 1000, 2000, 3000, 4000, 5000 |


Note that in our current insurance product, each section can have a set of named items, a bundle, or both, which is configured as follows:

| Section     | Item options           |
|-------------|------------------------|
| General     | Bundle only            |
| Jewelry     | Named items and bundle |
| Electronics | Named items only       |
| Bicycles    | Named items only       |


### Pricing model

The price that a customer is quoted for each named item/ bundle is calculated as

*(W<sup>max</sup>/W) × V × (1 - E/V) × 0.0015 × M*

where *1 <= W <= W<sup>max</sup>* is the Wrisk score with *W<sup>max</sup> = 1000*, *V > 0*  is the value (or sum insured) of the item/bundle, *E* is the excess, and *M* is a section multiplier. As with the value of bundles, we provide a discrete set of excess options for customer to choose from when receiving a quote.

The following table lists the excess options and multiplier for each section: 

| Section     | Excess options          | Multiplier |
|-------------|-------------------------|------------|
| General     | 200, 300, 400           | 0.1        |
| Jewelry     | 100, 200, 300           | 2          |
| Electronics | 100, 200, 300, 400, 500 | 1          |
| Bicycles    | 300, 400, 500           | 0.8        |


## Task

### Part 1: QuoteGenerator

In order to optimise performance in our mobile app, we generate a quote for all the possible options for our users. This means that we generate a quote with upfront prices calculated for all value/excess combinations.

You should write a solution which calculates a quote for the following customers

| Name         | Wrisk Score | Bundles Selected          | Named Items (Name:Category:Value) |
|--------------|-------------|---------------------------|-----------------------------------|
| Rick Sanchez | 1000        | General Contents          | Portal Gun: Electronics: 2000     |
| Morty Smith  | 20          | General Contents, Jewelry | Schwinn Bike: Bicycles: 500       |
| Jerry Smith  | 1           | General Contents          | Wedding Ring: Jewelry: 400        |

The **QuoteGenerator** should take in a list of which bundles were chosen and a list of named items to produce a price quote matrix with all potential price options for each item. 

### Part 2: PolicyGenerator

After we provide the quote for the customer, they can choose the excess for each named item and the value/excess combination for each bundle. They should then see the total price before taking a policy with Wrisk.

At Wrisk we use a microservices based architecture, with services communicating via HTTP and known contracts between the services. In the template project, the policy-contracts have been included for you to use as the output from the PolicyGenerator.

You should write a solution which takes the quotes generated in part 1 to produce a policy for each customer’s selection as provided below.

**Rick Sanchez:**

| Name (Bundle/Item) | Value Choice | Excess |
|--------------------|--------------|--------|
| General Contents   | 2500         | 200    |
| Portal Gun         | 2000         | 300    |


**Morty Smith:**

| Name (Bundle/Item) | Value Choice | Excess |
|--------------------|--------------|--------|
| General Contents   | 5000         | 300    |
| Schwinn Bike       | 500          | 300    |


**Jerry Smith:**

| Name (Bundle/Item) | Value Choice | Excess |
|--------------------|--------------|--------|
| General Contents   | 15000        | 400    |
| Wedding Ring       | 400          | 300    |


## What we look at

* Clean
* Simple design - don’t over-engineer your solution
* Performant
* It works! (Hint: This is the 2nd most important.)
* Well tested

