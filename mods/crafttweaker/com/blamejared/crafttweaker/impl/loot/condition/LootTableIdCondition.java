package com.blamejared.crafttweaker.impl.loot.condition;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;

public record LootTableIdCondition(ResourceLocation tableId) implements LootItemCondition {
   public static final MapCodec<LootTableIdCondition> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(ResourceLocation.CODEC.fieldOf("table_id").forGetter(LootTableIdCondition::tableId))
         .apply(instance, LootTableIdCondition::new)
   );
   public static final LootItemConditionType LOOT_TABLE_ID = new LootItemConditionType(CODEC);

   public static Builder builder(ResourceLocation id) {
      return () -> new LootTableIdCondition(id);
   }

   public LootItemConditionType getType() {
      return LOOT_TABLE_ID;
   }

   public boolean test(LootContext lootContext) {
      return this.tableId().equals(GenericUtil.<ILootTableIdHolder>uncheck(lootContext).crafttweaker$tableId());
   }
}
