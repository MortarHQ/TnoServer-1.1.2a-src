package fzzyhmstrs.emi_loot.server;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2FloatMap.Entry;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SimpleLootPoolBuilder extends AbstractLootPoolBuilder {
   final Object2IntMap<ItemStack> map = new Object2IntOpenHashMap();
   Integer totalWeight = 0;
   Object2FloatMap<ItemStack> builtMap = new Object2FloatOpenHashMap();

   public SimpleLootPoolBuilder(float rollWeight) {
      super(rollWeight);
   }

   @Override
   public void addItem(LootTableParser.ItemEntryResult result) {
      ItemStack item = result.item();
      int weight = result.weight();
      this.totalWeight = this.totalWeight + weight;
      if (this.map.containsKey(item)) {
         int oldWeight = this.map.getOrDefault(item, 0);
         this.map.put(item, oldWeight + weight);
      } else {
         this.map.put(item, weight);
      }
   }

   @Override
   public void build() {
      if (this.map.isEmpty()) {
         this.isEmpty = true;
      } else {
         Object2FloatMap<ItemStack> floatMap = new Object2FloatOpenHashMap();
         this.map.forEach((item, itemWeight) -> {
            if (!item.is(Items.AIR)) {
               floatMap.put(item, itemWeight.floatValue() / this.totalWeight.intValue() * 100.0F * this.rollWeight);
            }
         });
         this.builtMap = floatMap;
         if (this.builtMap.size() == 1) {
            this.isSimple = true;
            ObjectIterator var2 = this.builtMap.object2FloatEntrySet().iterator();

            while (var2.hasNext()) {
               Entry<ItemStack> entry = (Entry<ItemStack>)var2.next();
               this.simpleStack = (ItemStack)entry.getKey();
               if (entry.getFloatValue() != 100.0F || ((ItemStack)entry.getKey()).getCount() != 1) {
                  this.isSimple = false;
                  break;
               }
            }
         }
      }
   }

   @Override
   public List<LootTableParser.ItemEntryResult> revert() {
      List<LootTableParser.ItemEntryResult> list = new LinkedList<>();
      this.map.forEach((stack, weight) -> list.add(new LootTableParser.ItemEntryResult(stack, weight, new LinkedList<>(), new LinkedList<>())));
      return list;
   }
}
