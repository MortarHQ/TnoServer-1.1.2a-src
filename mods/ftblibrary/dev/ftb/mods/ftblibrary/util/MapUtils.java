package dev.ftb.mods.ftblibrary.util;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MapUtils {
   public static <K, V> Map<K, V> sortMapByKey(Map<K, V> map, Comparator<K> comparator) {
      return map.entrySet()
         .stream()
         .sorted(Entry.comparingByKey(comparator))
         .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, LinkedHashMap::new));
   }

   public static <K extends Comparable<? super K>, V> Map<K, V> sortMapByKey(Map<K, V> map) {
      return map.entrySet().stream().sorted(Entry.comparingByKey()).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, LinkedHashMap::new));
   }
}
