package de.jangassen.lemonoid;

public abstract class EmptyMonoid<T> implements Monoid<T> {
  private final T empty;

  EmptyMonoid(T empty) {
    this.empty = empty;
  }

  @Override
  public T empty() {
    return empty;
  }
}
