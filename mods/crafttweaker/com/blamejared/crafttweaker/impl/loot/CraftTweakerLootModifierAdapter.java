package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

final class CraftTweakerLootModifierAdapter implements IGlobalLootModifier {
   private final ILootModifier modifier;

   private CraftTweakerLootModifierAdapter(ILootModifier modifier) {
      this.modifier = modifier;
   }

   static IGlobalLootModifier adapt(ILootModifier modifier) {
      return modifier == null ? null : new CraftTweakerLootModifierAdapter(modifier);
   }

   public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      return this.modifier.doApply(generatedLoot, context);
   }

   public MapCodec<? extends IGlobalLootModifier> codec() {
      throw new UnsupportedOperationException("Data generation of loot modifiers is not supported");
   }
}
