package com.mortal_cultivation.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddItemLootModifier extends LootModifier {
   public static final MapCodec<AddItemLootModifier> CODEC = RecordCodecBuilder.mapCodec(
      inst -> codecStart(inst)
         .and(ItemStack.CODEC.fieldOf("item").forGetter(m -> m.item))
         .and(Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance))
         .apply(inst, AddItemLootModifier::new)
   );
   private final ItemStack item;
   private final float chance;

   public AddItemLootModifier(LootItemCondition[] conditionsIn, ItemStack item, float chance) {
      super(conditionsIn);
      this.item = item;
      this.chance = chance;
   }

   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      if (context.getRandom().nextFloat() < this.chance) {
         generatedLoot.add(this.item.copy());
      }

      return generatedLoot;
   }

   public MapCodec<? extends IGlobalLootModifier> codec() {
      return CODEC;
   }
}
