package net.regions_unexplored.util;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import net.minecraft.util.RandomSource;

public class RUUtils {
   public static <T> List<T> shuffledCopy(ObjectArrayList<T> list, RandomSource random) {
      ObjectArrayList<T> copy = new ObjectArrayList(list);
      shuffle(copy, random);
      return copy;
   }

   public static <T> void shuffle(List<T> list, RandomSource random) {
      for (int size = list.size(); size > 1; size--) {
         int swapTo = random.nextInt(size);
         list.set(size - 1, list.set(swapTo, list.get(size - 1)));
      }
   }

   public static <T, U, R> BiFunction<T, U, R> memoize(BiFunction<T, U, R> function) {
      return new BiFunction<T, U, R>() {
         private final Map<Pair<T, U>, R> cache = new ConcurrentHashMap<>();

         @Override
         public R apply(T a, U b) {
            return this.cache.computeIfAbsent(Pair.of(a, b), args -> function.apply((T)args.getFirst(), (U)args.getSecond()));
         }

         @Override
         public String toString() {
            return "memoize/2[function=" + function + ", size=" + this.cache.size() + "]";
         }
      };
   }
}
