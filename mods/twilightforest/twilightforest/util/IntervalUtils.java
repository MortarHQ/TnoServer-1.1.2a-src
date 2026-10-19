package twilightforest.util;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class IntervalUtils {
   public static List<Pair<Integer, Integer>> mergeAndSortIntervals(List<Pair<Integer, Integer>> intervals) {
      if (intervals.isEmpty()) {
         return intervals;
      }

      intervals.sort(Comparator.comparingInt(Pair::getFirst));
      List<Pair<Integer, Integer>> merged = new ArrayList<>();
      Pair<Integer, Integer> current = intervals.getFirst();

      for (int i = 1; i < intervals.size(); i++) {
         Pair<Integer, Integer> next = intervals.get(i);
         if ((Integer)current.getSecond() >= (Integer)next.getFirst()) {
            current = Pair.of((Integer)current.getFirst(), Math.max((Integer)current.getSecond(), (Integer)next.getSecond()));
         } else {
            merged.add(current);
            current = next;
         }
      }

      merged.add(current);
      return merged;
   }

   public static List<Pair<Integer, Integer>> subtractIntervals(List<Pair<Integer, Integer>> sortedA, List<Pair<Integer, Integer>> sortedB) {
      List<Pair<Integer, Integer>> result = new ArrayList<>();
      int i = 0;
      int j = 0;

      while (i < sortedA.size() && j < sortedB.size()) {
         Pair<Integer, Integer> a = sortedA.get(i);
         Pair<Integer, Integer> b = sortedB.get(j);
         if ((Integer)a.getSecond() <= (Integer)b.getFirst()) {
            result.add(a);
            i++;
         } else if ((Integer)a.getFirst() >= (Integer)b.getSecond()) {
            j++;
         } else {
            if ((Integer)a.getFirst() < (Integer)b.getFirst()) {
               result.add(Pair.of((Integer)a.getFirst(), (Integer)b.getFirst()));
            }

            if ((Integer)a.getSecond() <= (Integer)b.getSecond()) {
               i++;
            } else {
               sortedA.set(i, Pair.of((Integer)b.getSecond(), (Integer)a.getSecond()));
               j++;
            }
         }
      }

      while (i < sortedA.size()) {
         result.add(sortedA.get(i++));
      }

      return result;
   }
}
