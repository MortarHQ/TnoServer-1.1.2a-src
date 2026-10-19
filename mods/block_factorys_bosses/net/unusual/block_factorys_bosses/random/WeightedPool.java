package net.unusual.block_factorys_bosses.random;

import java.util.List;

public class WeightedPool<T> {
   private final List<WeightedPool.PoolOption<T>> options;
   private final int totalWeight;

   public WeightedPool(List<WeightedPool.PoolOption<T>> options) {
      if (options.isEmpty()) {
         throw new IllegalArgumentException("WeightedPool must have at least one option");
      }

      this.options = options;
      this.totalWeight = options.stream().mapToInt(WeightedPool.PoolOption::weight).sum();
   }

   public T selectRandom() {
      int randomWeight = (int)(Math.random() * this.totalWeight);
      int weightSum = 0;

      for (WeightedPool.PoolOption<T> option : this.options) {
         weightSum += option.weight();
         if (randomWeight < weightSum) {
            return option.entry();
         }
      }

      return null;
   }

   @SafeVarargs
   public static <T> WeightedPool<T> of(WeightedPool.PoolOption<T>... entries) {
      return new WeightedPool<>(List.of(entries));
   }

   public static <T> WeightedPool.PoolOption<T> option(int weight, T entry) {
      return new WeightedPool.PoolOption<>(weight, entry);
   }

   public record PoolOption<T>(int weight, T entry) {
      public PoolOption {
         if (weight < 1) {
            throw new IllegalArgumentException("PoolOption must have a weight of at least 1");
         }
      }
   }
}
