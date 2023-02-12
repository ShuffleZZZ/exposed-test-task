# Task

Write a function with the following description:

```kotlin
/**
* Checks if the [value] matches regex [pattern].
*
* The [pattern] is written as a subset of the following regular expression syntax:
*
* Matcher:
* "ABC" - matches the string "ABC".  // exact
* "[abc123]" - matches any character from the set "abc123".  anyOF
*
* "." - matches any character once.
* "a|b" - matches either "a" or "b".
*
* "a+" - matches one or more occurrences of the character "a".
* "a*" - matches zero or more occurrences of the character "a".
* "a?" - matches zero or one occurrence of the character "a".
*
* To group multiple characters into a single token, use parentheses.
* For example:
* "(abc)|(cde)" - matches either "abc" or "cde".
*/
```
