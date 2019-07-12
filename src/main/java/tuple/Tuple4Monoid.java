package tuple;

import de.jangassen.lemonoid.Monoid;

public class Tuple4Monoid<T1, T2, T3, T4> implements Monoid<Tuple4<T1, T2, T3, T4>> {

  private final Monoid<T1> t1Monoid;
  private final Monoid<T2> t2Monoid;
  private final Monoid<T3> t3Monoid;
  private final Monoid<T4> t4Monoid;

  private Tuple4Monoid(Monoid<T1> t1Monoid, Monoid<T2> t2Monoid, Monoid<T3> t3Monoid, Monoid<T4> t4Monoid) {
    this.t1Monoid = t1Monoid;
    this.t2Monoid = t2Monoid;
    this.t3Monoid = t3Monoid;
    this.t4Monoid = t4Monoid;
  }

  public static <V1> Tuple4Monoid<V1, V1, V1, V1> of(Monoid<V1> monoid) {
    return new Tuple4Monoid<>(monoid, monoid, monoid, monoid);
  }

  public static <V1, V2, V3, V4> Tuple4Monoid<V1, V2, V3, V4> of(Monoid<V1> v1Monoid, Monoid<V2> v2Monoid,
      Monoid<V3> v3Monoid, Monoid<V4> v4Monoid) {
    return new Tuple4Monoid<>(v1Monoid, v2Monoid, v3Monoid, v4Monoid);
  }

  @Override
  public Tuple4<T1, T2, T3, T4> empty() {
    return Tuple4.of(t1Monoid.empty(), t2Monoid.empty(), t3Monoid.empty(), t4Monoid.empty());
  }

  @Override
  public Tuple4<T1, T2, T3, T4> combine(Tuple4<T1, T2, T3, T4> a, Tuple4<T1, T2, T3, T4> b) {
    return Tuple4.of(t1Monoid.combine(a._1, b._1), t2Monoid.combine(a._2, b._2), t3Monoid.combine(a._3, b._3),
        t4Monoid.combine(a._4, b._4));
  }
}
