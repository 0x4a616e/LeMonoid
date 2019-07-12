package de.jangassen.lemonoid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Monoids {
  private static <T extends Comparable<T>> Monoid<T> max(T minValue) {
    return new Max<>(minValue);
  }

  private static <T extends Comparable<T>> Monoid<T> min(T maxValue) {
    return new Min<>(maxValue);
  }

  public static Monoid<Integer> maxInt() {
    return max(Integer.MIN_VALUE);
  }

  public static Monoid<Integer> minInt() {
    return min(Integer.MAX_VALUE);
  }

  public static Monoid<Long> maxLong() {
    return max(Long.MIN_VALUE);
  }

  public static Monoid<Long> minLong() {
    return min(Long.MAX_VALUE);
  }

  public static Monoid<Float> maxFloat() {
    return max(Float.MIN_VALUE);
  }

  public static Monoid<Float> minFloat() {
    return min(Float.MAX_VALUE);
  }

  public static Monoid<Double> maxDouble() {
    return max(Double.MIN_VALUE);
  }

  public static Monoid<Double> minDouble() {
    return min(Double.MAX_VALUE);
  }

  public static Monoid<Integer> sumInt() {
    return new EmptyMonoid<Integer>(0) {
      @Override
      public Integer combine(Integer a, Integer b) {
        return a + b;
      }
    };
  }

  public static Monoid<Long> sumLong() {
    return new EmptyMonoid<Long>(0l) {
      @Override
      public Long combine(Long a, Long b) {
        return a + b;
      }
    };
  }

  public static Monoid<Float> sumFloat() {
    return new EmptyMonoid<Float>(0f) {
      @Override
      public Float combine(Float a, Float b) {
        return a + b;
      }
    };
  }

  public static Monoid<Double> sumDouble() {
    return new EmptyMonoid<Double>(0d) {
      @Override
      public Double combine(Double a, Double b) {
        return a + b;
      }
    };
  }

  public static Monoid<String> concatString() {
    return new EmptyMonoid<String>("") {
      @Override
      public String combine(String a, String b) {
        return a + b;
      }
    };
  }

  public static <T> Monoid<List<T>> concatList() {
    return new EmptyMonoid<List<T>>(new ArrayList<>()) {
      @Override
      public List<T> combine(List<T> a, List<T> b) {
        return Stream.concat(a.stream(), b.stream()).collect(Collectors.toList());
      }
    };
  }

  public static <T> Monoid<Optional<T>> optional(Monoid<T> monoid) {
    return new EmptyMonoid<Optional<T>>(Optional.empty()) {
      @Override
      public Optional<T> combine(Optional<T> a, Optional<T> b) {
        return Optional.of(monoid.combine(a.get(), b.get()));
      }
    };
  }

  public static <K, V> Monoid<Map<K, V>> map(Monoid<V> monoid) {
    return new EmptyMonoid<Map<K, V>>(new HashMap<>()) {
      @Override
      public Map<K, V> combine(Map<K, V> a, Map<K, V> b) {
        return Stream.concat(a.entrySet().stream(), b.entrySet().stream()).collect(Collectors
            .toMap(Map.Entry::getKey, Map.Entry::getValue, (value1, value2) -> monoid.combine(value1, value2)));
      }
    };
  }

  public static <A> A combineAll(List<A> list, Monoid<A> monoid) {
    return combineAll(list.stream(), monoid);
  }

  public static <A, B> B foldMap(List<A> list, Monoid<B> monoid, Function<A, B> mapper) {
    return foldMap(list.stream(), monoid, mapper);
  }

  public static <A> A combineAll(Stream<A> stream, Monoid<A> monoid) {
    return stream.reduce(monoid.empty(), monoid::combine);
  }

  public static <A, B> B foldMap(Stream<A> stream, Monoid<B> monoid, Function<A, B> mapper) {
    return stream.reduce(monoid.empty(), (l, r) -> monoid.combine(l, mapper.apply(r)), monoid::combine);
  }
}
