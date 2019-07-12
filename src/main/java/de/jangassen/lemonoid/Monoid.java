package de.jangassen.lemonoid;

public interface Monoid<T> {
  T empty();

  T combine(T a, T b);
}
