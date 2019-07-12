package de.jangassen.lemonoid;

import org.junit.Assert;
import org.junit.Test;
import tuple.Tuple2;
import tuple.Tuple2Monoid;
import tuple.Tuple3;
import tuple.Tuple3Monoid;
import tuple.Tuple4;
import tuple.Tuple4Monoid;
import tuple.Tuples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static de.jangassen.lemonoid.Monoids.combineAll;
import static de.jangassen.lemonoid.Monoids.concatList;
import static de.jangassen.lemonoid.Monoids.concatString;
import static de.jangassen.lemonoid.Monoids.foldMap;
import static de.jangassen.lemonoid.Monoids.map;
import static de.jangassen.lemonoid.Monoids.maxDouble;
import static de.jangassen.lemonoid.Monoids.maxFloat;
import static de.jangassen.lemonoid.Monoids.maxInt;
import static de.jangassen.lemonoid.Monoids.maxLong;
import static de.jangassen.lemonoid.Monoids.minDouble;
import static de.jangassen.lemonoid.Monoids.minFloat;
import static de.jangassen.lemonoid.Monoids.minInt;
import static de.jangassen.lemonoid.Monoids.minLong;
import static de.jangassen.lemonoid.Monoids.optional;
import static de.jangassen.lemonoid.Monoids.sumDouble;
import static de.jangassen.lemonoid.Monoids.sumFloat;
import static de.jangassen.lemonoid.Monoids.sumInt;
import static de.jangassen.lemonoid.Monoids.sumLong;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class MonoidsTest {
  @Test
  public void testMaxMonoidOnIntStream() {
    int max = combineAll(Stream.of(1, 4, 2, 3), maxInt());

    Assert.assertEquals(4, max);
  }

  @Test
  public void testMinMonoidOnIntStream() {
    int min = combineAll(Stream.of(1, 4, 2, 3), minInt());

    Assert.assertEquals(1, min);
  }

  @Test
  public void testSumMonoidOnIntStream() {
    int sum = combineAll(Stream.of(1, 4, 2, 3), sumInt());

    Assert.assertEquals(10, sum);
  }

  @Test
  public void testMaxMonoidOnLongStream() {
    long max = combineAll(Stream.of(1L, 4L, 2L, 3L), maxLong());

    Assert.assertEquals(4, max);
  }

  @Test
  public void testMinMonoidOnLongStream() {
    long min = combineAll(Stream.of(1L, 4L, 2L, 3L), minLong());

    Assert.assertEquals(1, min);
  }

  @Test
  public void testSumMonoidOnLongStream() {
    long sum = combineAll(Stream.of(1L, 4L, 2L, 3L), sumLong());

    Assert.assertEquals(10, sum);
  }

  @Test
  public void testMaxMonoidOnFloatStream() {
    float max = combineAll(Stream.of(1f, 4f, 2f, 3f), maxFloat());

    Assert.assertEquals(4, max, .1);
  }

  @Test
  public void testMinMonoidOnFloatStream() {
    float min = combineAll(Stream.of(1f, 4f, 2f, 3f), minFloat());

    Assert.assertEquals(1, min, .1);
  }

  @Test
  public void testSumMonoidOnFloatStream() {
    float sum = combineAll(Stream.of(1f, 4f, 2f, 3f), sumFloat());

    Assert.assertEquals(10, sum, .1);
  }

  @Test
  public void testMaxMonoidOnDoubleStream() {
    double max = combineAll(Stream.of(1d, 4d, 2d, 3d), maxDouble());

    Assert.assertEquals(4, max, .1);
  }

  @Test
  public void testMinMonoidOnDoubleStream() {
    double min = combineAll(Stream.of(1d, 4d, 2d, 3d), minDouble());

    Assert.assertEquals(1, min, .1);
  }

  @Test
  public void testSumMonoidOnDoubleStream() {
    double sum = combineAll(Stream.of(1d, 4d, 2d, 3d), sumDouble());

    Assert.assertEquals(10, sum, .1);
  }

  @Test
  public void testConcatStringStream() {
    String result = combineAll(Stream.of("a", "b", "c", "d"), concatString());

    Assert.assertEquals("abcd", result);
  }

  @Test
  public void testConcatListsStream() {
    List<String> result =
        combineAll(Stream.of(singletonList("a"), singletonList("b"), singletonList("c"), singletonList("d")),
            concatList());

    Assert.assertEquals(asList("a", "b", "c", "d"), result);
  }

  @Test
  public void testSumOptional() {
    Optional<Integer> result = optional(sumInt()).combine(Optional.of(1), Optional.of(2));

    Assert.assertEquals(Optional.of(3), result);
  }

  @Test
  public void testFoldMapMaxLength() {
    List<String> words = Arrays.asList("abcde", "xyz", "lala", "xyz");

    int result = foldMap(words, maxInt(), String::length);

    Assert.assertEquals(5, result);
  }

  @Test
  public void testFoldMapWithTuple2() {
    List<String> words = Arrays.asList("abcde", "xyz", "lala", "xyz");

    Tuple2Monoid<Integer, Integer> tuple2Monoid = Tuple2Monoid.of(sumInt(), maxInt());

    Tuple2<Integer, Integer> result =
        foldMap(words, tuple2Monoid, word -> Tuples.of(1, word.length()));

    Assert.assertEquals(4, (int)result._1);
    Assert.assertEquals(5, (int)result._2);
  }

  @Test
  public void testFoldMapWithTuple3() {
    List<String> words = Arrays.asList("abcde", "xyz", "lala", "xyz");

    Tuple3Monoid<Integer, Integer, Map<String, Integer>> tuple3Monoid = Tuple3Monoid.of(sumInt(), maxInt(), map(sumInt()));

    Tuple3<Integer, Integer, Map<String, Integer>> result =
        foldMap(words, tuple3Monoid, word -> Tuples.of(1, word.length(), mapOf(word)));

    Map<String, Integer> wordCount = new HashMap<>();
    wordCount.put("abcde", 1);
    wordCount.put("xyz", 2);
    wordCount.put("lala", 1);

    Assert.assertEquals(4, (int)result._1);
    Assert.assertEquals(5, (int)result._2);
    Assert.assertEquals(wordCount, result._3);
  }

  @Test
  public void testFoldMapWithTuple4() {
    List<String> words = Arrays.asList("abcde", "xyz", "lala", "xyz");

    Tuple4Monoid<Integer, Integer, Map<String, Integer>, String> tuple4Monoid = Tuple4Monoid
        .of(sumInt(), maxInt(), map(sumInt()), concatString());

    Tuple4<Integer, Integer, Map<String, Integer>, String> result =
        foldMap(words, tuple4Monoid, word -> Tuples.of(1, word.length(), mapOf(word), word));

    Map<String, Integer> wordCount = new HashMap<>();
    wordCount.put("abcde", 1);
    wordCount.put("xyz", 2);
    wordCount.put("lala", 1);

    Assert.assertEquals(4, (int)result._1);
    Assert.assertEquals(5, (int)result._2);
    Assert.assertEquals(wordCount, result._3);
    Assert.assertEquals("abcdexyzlalaxyz", result._4);
  }

  private static Map<String, Integer> mapOf(String value) {
    Map<String, Integer> map = new HashMap<>();
    map.put(value, 1);
    return map;
  }
}
