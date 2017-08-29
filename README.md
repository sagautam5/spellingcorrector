# Spelling Corrector
Spelling correction with Naive Bayes approach

1. Get a word from users input
2. Search word in the collection or Bag of words
3. If word is found, just return the same word otherwise

   A. Generate a list of close and probable words by 
       i.   single letter deletion
       ii.  single letter insertion
       iii. single letter replacement
       iv.  swap of two letters 
   B. Get frequency of each probable word from the bag of words
   C. Return most frequent probable word as spell correct word
   
