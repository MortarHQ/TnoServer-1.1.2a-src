package fzzyhmstrs.emi_loot.util;

import com.google.common.base.Suppliers;
import fzzyhmstrs.emi_loot.client.ClientBuiltPool;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap.Entry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public class WidgetRowBuilder {
   private final int maxWidth;
   private final List<ClientBuiltPool> poolList = new ArrayList<>();
   private final Supplier<List<ConditionalStack>> stacks = Suppliers.memoize(
      () -> {
         List<ConditionalStack> list = new ArrayList<>();

         for (ClientBuiltPool pool : this.poolList) {
            list.addAll(
               pool.stacks()
                  .stream()
                  .map(
                     stack -> stack.getRawStacks()
                        .stream()
                        .sorted(Comparator.comparingInt(s -> BuiltInRegistries.ITEM.getId(s.getItem())))
                        .map(s -> new ConditionalStack(stack.conditions(), stack.weight(), List.of(s)))
                        .toList()
                  )
                  .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll)
            );
         }

         return list;
      }
   );
   private int width = 0;

   public WidgetRowBuilder(int maxWidth) {
      this.maxWidth = maxWidth;
   }

   public List<ClientBuiltPool> getPoolList() {
      return this.poolList;
   }

   public int ingredientCount() {
      int count = 0;

      for (ClientBuiltPool pool : this.poolList) {
         count += pool.stacks().size();
      }

      return count;
   }

   public List<ConditionalStack> ingredients() {
      List<ConditionalStack> list = new ArrayList<>();

      for (ClientBuiltPool pool : this.poolList) {
         list.addAll(pool.stacks());
      }

      return list;
   }

   public List<ConditionalStack> stacks() {
      return this.stacks.get();
   }

   public int getWidth() {
      return this.width;
   }

   private boolean add(ClientBuiltPool newPool) {
      int newWidth = this.getNewWidth(newPool);
      if (this.width + newWidth > this.maxWidth) {
         return false;
      }

      this.width += newWidth;
      this.poolList.add(newPool);
      return true;
   }

   public void addSimple(ClientBuiltPool newPool) {
      this.width = this.getNewWidth(newPool);
      this.poolList.add(newPool);
   }

   public Optional<ClientBuiltPool> addAndTrim(ClientBuiltPool newPool) {
      if (this.add(newPool)) {
         return Optional.empty();
      } else if (this.width == 0) {
         List<ConditionalStack> madeItIn = new ArrayList<>();
         List<ConditionalStack> leftOvers = new ArrayList<>();
         AtomicInteger newWidth = new AtomicInteger(14 + 11 * ((newPool.conditions().size() - 1) / 2 - 1));
         newPool.stacks().forEach(s -> {
            float weight = s.weight();
            List<ItemStack> stacks = s.getRawStacks().stream().sorted(Comparator.comparingInt(s1 -> BuiltInRegistries.ITEM.getId(s1.getItem()))).toList();
            if (newWidth.addAndGet(20) <= this.maxWidth) {
               madeItIn.add(new ConditionalStack(s.conditions(), weight, stacks));
            } else {
               leftOvers.add(new ConditionalStack(s.conditions(), weight, stacks));
            }
         });
         this.add(new ClientBuiltPool(newPool.conditions(), madeItIn));
         return Optional.of(new ClientBuiltPool(newPool.conditions(), leftOvers));
      } else {
         return Optional.of(newPool);
      }
   }

   private int getNewWidth(ClientBuiltPool newPool) {
      int newWidth;
      if (this.poolList.isEmpty()) {
         newWidth = 14 + 11 * ((newPool.conditions().size() - 1) / 2) + 20 * newPool.stacks().size();
      } else {
         newWidth = 20 + 11 * ((newPool.conditions().size() - 1) / 2) + 20 * newPool.stacks().size();
      }

      return newWidth;
   }

   public boolean canAddPool(ClientBuiltPool newPool) {
      int newWidth = this.getNewWidth(newPool);
      return this.width + newWidth <= this.maxWidth;
   }

   public boolean canAddOther(WidgetRowBuilder other) {
      return this.width + other.width + 6 <= this.maxWidth;
   }

   public void addOther(WidgetRowBuilder other) {
      this.poolList.addAll(other.poolList);
   }

   public static void collate(List<WidgetRowBuilder> builders) {
      if (!builders.isEmpty()) {
         Int2IntMap collateMap = new Int2IntOpenHashMap();

         for (int i = 0; i < builders.size(); i++) {
            for (int j = 0; j < builders.size(); j++) {
               if (i != j && builders.get(j).canAddOther(builders.get(i))) {
                  collateMap.put(j, i);
               }
            }
         }

         if (!collateMap.isEmpty()) {
            Entry var4 = (Entry)collateMap.int2IntEntrySet().stream().toList().get(0);
         }
      }
   }
}
