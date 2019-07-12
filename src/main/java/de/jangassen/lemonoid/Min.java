package de.jangassen.lemonoid;

public class Min<T extends Comparable<T>> extends EmptyMonoid<T> {

  public Min(T empty) {
    super(empty);
  }

  @Override
  public T combine(T a, T b) {
    return a.compareTo(b) < 0 ? a : b;
  }
}
