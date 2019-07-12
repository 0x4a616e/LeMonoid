package de.jangassen.lemonoid.util;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class MapUtils {
  public static <K,V> Map<K, V> mapOf(K k1, V v1) {
    Map<K,V> result = new HashMap<>();
    result.put(k1, v1);
    return unmodifiableMap(result);
  }

  public static <K,V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
    Map<K,V> result = new HashMap<>(mapOf(k1, v1));
    result.put(k2, v2);
    return unmodifiableMap(result);
  }

  public static <K,V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3 ) {
    Map<K,V> result =  new HashMap<>(mapOf(k1, v1, k2, v2));
    result.put(k3, v3);
    return unmodifiableMap(result);
  }

  public static <K,V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
    Map<K,V> result =  new HashMap<>(mapOf(k1, v1, k2, v2, k3, v3));
    result.put(k4, v4);
    return unmodifiableMap(result);
  }
}
