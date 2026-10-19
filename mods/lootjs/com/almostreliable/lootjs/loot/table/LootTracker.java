package com.almostreliable.lootjs.loot.table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class LootTracker implements Consumer<ItemStack> {
   private final List<ItemStack> loot = new ArrayList<>();
   private final Consumer<ItemStack> originConsumer;
   @Nullable
   private final ResourceLocation tableId;

   public LootTracker(Consumer<ItemStack> originConsumer, @Nullable ResourceLocation tableId) {
      this.originConsumer = originConsumer;
      this.tableId = tableId;
   }

   public void accept(ItemStack itemStack) {
      this.loot.add(itemStack);
   }

   public List<ItemStack> getLoot() {
      return this.loot;
   }

   public void resolve() {
      this.loot.forEach(this.originConsumer);
   }

   @Nullable
   public ResourceLocation getTableId() {
      return this.tableId;
   }

   @Override
   public String toString() {
      return "LootTracker[" + this.tableId + "] " + this.loot.size() + "x items";
   }
}
