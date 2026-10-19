package com.tensura_tno.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tensura_tno.registry.TensuraTNOItems;
import com.tensura_tno.registry.TensuraTNOLootModifiers;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddForbiddenTomeLootModifier extends LootModifier {
   public static final MapCodec<AddForbiddenTomeLootModifier> CODEC = RecordCodecBuilder.mapCodec(
      inst -> codecStart(inst).apply(inst, AddForbiddenTomeLootModifier::new)
   );

   public AddForbiddenTomeLootModifier(LootItemCondition[] conditions) {
      super(conditions);
   }

   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      generatedLoot.add(new ItemStack((ItemLike)TensuraTNOItems.FORBIDDEN_SUMMON_TOME.get()));
      return generatedLoot;
   }

   public MapCodec<? extends IGlobalLootModifier> codec() {
      return (MapCodec<? extends IGlobalLootModifier>)TensuraTNOLootModifiers.ADD_FORBIDDEN_TOME.get();
   }
}
