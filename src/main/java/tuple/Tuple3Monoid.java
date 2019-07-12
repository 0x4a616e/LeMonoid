package tuple;

import de.jangassen.lemonoid.Monoid;

public class Tuple3Monoid<T1, T2, T3> implements Monoid<Tuple3<T1, T2, T3>> {

  private final Monoid<T1> t1Monoid;
  private final Monoid<T2> t2Monoid;
  private final Monoid<T3> t3Monoid;

  private Tuple3Monoid(Monoid<T1> t1Monoid, Monoid<T2> t2Monoid, Monoid<T3> t3Monoid) {
    this.t1Monoid = t1Monoid;
    this.t2Monoid = t2Monoid;
    this.t3Monoid = t3Monoid;
  }

  public static <V1> Tuple3Monoid<V1, V1, V1> of(Monoid<V1> monoid) {
    return new Tuple3Monoid<>(monoid, monoid, monoid);
  }

  public static <V1, V2, V3> Tuple3Monoid<V1, V2, V3> of(Monoid<V1> v1Monoid, Monoid<V2> v2Monoid,
      Monoid<V3> v3Monoid) {
    return new Tuple3Monoid<>(v1Monoid, v2Monoid, v3Monoid);
  }

  @Override
  public Tuple3<T1, T2, T3> empty() {
    return Tuple3.of(t1Monoid.empty(), t2Monoid.empty(), t3Monoid.empty());
  }

  @Override
  public Tuple3<T1, T2, T3> combine(Tuple3<T1, T2, T3> a, Tuple3<T1, T2, T3> b) {
    return Tuple3.of(t1Monoid.combine(a._1, b._1), t2Monoid.combine(a._2, b._2), t3Monoid.combine(a._3, b._3));
  }
}
