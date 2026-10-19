package noobanidus.mods.lootr.common.api.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

public class AdapterMap<A extends ILootrAdapter<?>> {
   public static final ILootrDataAdapter<Object> NONE_DATA_ADAPTER = new ILootrDataAdapter<Object>() {
      @Nullable
      @Override
      public ResourceKey<LootTable> getLootTable(Object entity) {
         return null;
      }

      @Override
      public long getLootSeed(Object entity) {
         return 0L;
      }

      @Override
      public void setLootTable(Object entity, ResourceKey<LootTable> table, long seed) {
      }

      @Override
      public Class<Object> getAssignableClass() {
         return Object.class;
      }

      @Override
      public int priority() {
         return Integer.MIN_VALUE;
      }
   };
   public static final ILootrItemFrameAdapter<Object> NONE_ITEM_FRAME_ADAPTER = new ILootrItemFrameAdapter<Object>() {
      @Override
      public Class<Object> getAssignableClass() {
         return Object.class;
      }

      @Override
      public Direction getDirection(Object object) {
         return Direction.NORTH;
      }

      @Override
      public ItemStack getItem(Object object) {
         return ItemStack.EMPTY;
      }

      @Override
      public int getRotation(Object object) {
         return 0;
      }

      @Override
      public BlockPos getPos(Object object) {
         return BlockPos.ZERO;
      }

      @Override
      public boolean isFixed(Object object) {
         return false;
      }

      @Override
      public boolean isInvisible(Object object) {
         return false;
      }

      @Override
      public int priority() {
         return Integer.MIN_VALUE;
      }
   };
   private final A NONE;
   private final Map<Class<?>, A> byClass = new ConcurrentHashMap<>();
   private final List<A> allAdapters = new ArrayList<>();

   public AdapterMap(A none) {
      this.NONE = none;
   }

   public void register(A adapter) {
      this.allAdapters.add(adapter);
      this.byClass.clear();
   }

   @Nullable
   public A getAdapter(@Nullable Object type) {
      if (type == null) {
         return null;
      }

      Class<?> clazz = type.getClass();
      A potentialAdapter = this.byClass.computeIfAbsent(clazz, clazz2 -> {
         A best = null;
         int bestDistance = Integer.MAX_VALUE;
         int bestPriority = Integer.MIN_VALUE;

         for (A adapter : this.allAdapters) {
            if (adapter.getAssignableClass().isAssignableFrom((Class<?>)clazz2)) {
               int d = distance((Class<?>)clazz2, adapter);
               int p = adapter.priority();
               if (d < bestDistance || d == bestDistance && p > bestPriority) {
                  best = adapter;
                  bestDistance = d;
                  bestPriority = p;
               }
            }
         }

         return best == null ? this.NONE : best;
      });
      return potentialAdapter == this.NONE ? null : potentialAdapter;
   }

   private static int distance(Class<?> runtime, ILootrAdapter<?> target) {
      Class<?> targetClass = target.getAssignableClass();
      int d = 0;

      for (Class<?> c = runtime; c != null; c = c.getSuperclass()) {
         if (c == targetClass) {
            return d;
         }

         d++;
      }

      return Integer.MAX_VALUE;
   }
}
