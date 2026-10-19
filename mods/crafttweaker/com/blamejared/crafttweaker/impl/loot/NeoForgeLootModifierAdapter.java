package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.natives.item.ExpandItemStack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

final class NeoForgeLootModifierAdapter implements ILootModifier {
   private final IGlobalLootModifier modifier;

   private NeoForgeLootModifierAdapter(IGlobalLootModifier modifier) {
      this.modifier = modifier;
   }

   static ILootModifier adapt(IGlobalLootModifier modifier) {
      return modifier == null ? null : new NeoForgeLootModifierAdapter(modifier);
   }

   @Override
   public List<IItemStack> modify(List<IItemStack> loot, LootContext context) {
      return this.doApply(loot.stream().map(IItemStack::getInternal).collect(Collectors.toCollection(ObjectArrayList::new)), context)
         .stream()
         .map(ExpandItemStack::asIItemStack)
         .collect(Collectors.toList());
   }

   @Override
   public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext context) {
      return this.modifier.apply(loot, context);
   }

   IGlobalLootModifier modifier() {
      return this.modifier;
   }
}
