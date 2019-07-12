package de.jangassen.lemonoid.tuple;

import de.jangassen.lemonoid.Monoid;

public class Tuple2Monoid<T1, T2> implements Monoid<Tuple2<T1, T2>> {

  private final Monoid<T1> t1Monoid;
  private final Monoid<T2> t2Monoid;

  private Tuple2Monoid(Monoid<T1> t1Monoid, Monoid<T2> t2Monoid) {
    this.t1Monoid = t1Monoid;
    this.t2Monoid = t2Monoid;
  }

  public static <A> Tuple2Monoid<A, A> of(Monoid<A> monoid) {
    return new Tuple2Monoid<>(monoid, monoid);
  }

  public static <A, B> Tuple2Monoid<A, B> of(Monoid<A> lMonoid, Monoid<B> rMonoid) {
    return new Tuple2Monoid<>(lMonoid, rMonoid);
  }

  @Override
  public Tuple2<T1, T2> empty() {
    return Tuple2.of(t1Monoid.empty(), t2Monoid.empty());
  }

  @Override
  public Tuple2<T1, T2> combine(Tuple2<T1, T2> a, Tuple2<T1, T2> b) {
    return Tuple2.of(t1Monoid.combine(a._1, b._1), t2Monoid.combine(a._2, b._2));
  }
}
