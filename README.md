# LeMonoid

A while ago, I attended a [functional programming workshop](https://github.com/LukaJCB/typelevel-workshop) by 
[@LukaJCB](https://github.com/LukaJCB). One part of the workshop was to use monad transformers in Scala to to perform
all kinds of aggregations on lists or maps.

One example that really impressed me was a `foldMap` operation on a list of strings, that could extract number of words, 
the number of characters and the number of occurrences of each word all in one (once you've gotten used to it) simple and
elegant operation.

Since I am a Java guy and the workshop was in Scala, I wanted to do something similar in Java. So here it is: number of 
words, the number of characters and the number of occurrences of each word in a short Java snippet using monad 
transformers!

```Java
void foldMapListOfStrings() {
  List<String> words = Arrays.asList("abcde", "xyz", "lala", "xyz");

  Tuple3Monoid<Integer, Integer, Map<String, Integer>> tuple3Monoid = 
    Tuple3Monoid.of(sumInt(), maxInt(), map(sumInt()));

  Tuple3<Integer, Integer, Map<String, Integer>> result =
    foldMap(words, tuple3Monoid, word -> Tuples.of(1, word.length(), mapOf(word)));

  System.out.println(result._1); // 4
  System.out.println(result._2); // 5
  System.out.println(result._3); // {abcde=1, xyz=2, lala=1}
}

private static Map<String, Integer> mapOf(String value) {
  Map<String, Integer> map = new HashMap<>();
  map.put(value, 1);
  return map;
}
```
