(1)

From the specification:

"The QuoteGenerator should take in a list of which bundles were chosen and a list of named items to produce a price quote matrix with all potential price options for each item."

Since the no wrisk score input was specified, it would not be possible to calculate the price of each quote. Therefore each 'price quote' in the output should simply be
one of the possible combinations of value (for a bundle) and excess.
Also, although the output was described as a matrix, I wasn't sure what exactly this meant since a the mathematical definition of a matrix (i.e. a linear function) didn't seem appropriate.
After experimenting with outputting a list of quotes covering all possible excess and value combinations, which was combinatorially hard to do,
I decided to create a QuoteMatrix class holding the possibilities for each quote.