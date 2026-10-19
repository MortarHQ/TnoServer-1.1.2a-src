package com.iafenvoy.iceandfire.loot;

import com.iafenvoy.iceandfire.entity.SeaSerpentEntity;
import com.iafenvoy.iceandfire.item.SeaSerpentScaleItem;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafLoots;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SeaSerpentLootFunction extends LootItemConditionalFunction {
   public static final MapCodec<SeaSerpentLootFunction> CODEC = RecordCodecBuilder.mapCodec(
      instance -> commonFields(instance).apply(instance, SeaSerpentLootFunction::new)
   );

   public SeaSerpentLootFunction(List<LootItemCondition> conditionsIn) {
      super(conditionsIn);
   }

   public ItemStack run(ItemStack stack, LootContext context) {
      if (!stack.isEmpty() && context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof SeaSerpentEntity seaSerpent) {
         int ancientModifier = seaSerpent.isAncient() ? 2 : 1;
         if (stack.getItem() instanceof SeaSerpentScaleItem) {
            stack.setCount(1 + seaSerpent.getRandom().nextInt(1 + (int)Math.ceil(seaSerpent.getSeaSerpentScale() * 3.0F * ancientModifier)));
            return new ItemStack((ItemLike)seaSerpent.getEnum().scale.get(), stack.getCount());
         }

         if (stack.getItem() == IafItems.SERPENT_FANG.get()) {
            stack.setCount(1 + seaSerpent.getRandom().nextInt(1 + (int)Math.ceil(seaSerpent.getSeaSerpentScale() * 2.0F * ancientModifier)));
            return stack;
         }
      }

      return stack;
   }

   public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
      return (LootItemFunctionType<? extends LootItemConditionalFunction>)IafLoots.SEA_SERPENT_LOOT.get();
   }
}
