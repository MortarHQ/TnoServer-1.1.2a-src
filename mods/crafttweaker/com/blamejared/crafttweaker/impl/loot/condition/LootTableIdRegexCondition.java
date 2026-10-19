package com.blamejared.crafttweaker.impl.loot.condition;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.regex.Pattern;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;

public record LootTableIdRegexCondition(Pattern regex) implements LootItemCondition {
   public static final MapCodec<LootTableIdRegexCondition> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(ExtraCodecs.PATTERN.fieldOf("pattern").forGetter(LootTableIdRegexCondition::regex))
         .apply(instance, LootTableIdRegexCondition::new)
   );
   public static final LootItemConditionType LOOT_TABLE_ID_REGEX = new LootItemConditionType(CODEC);

   public static Builder builder(Pattern id) {
      return () -> new LootTableIdRegexCondition(id);
   }

   public static Builder builder(String id) {
      return builder(Pattern.compile(id));
   }

   public LootItemConditionType getType() {
      return LOOT_TABLE_ID_REGEX;
   }

   public boolean test(LootContext lootContext) {
      return this.regex().matcher(GenericUtil.<ILootTableIdHolder>uncheck(lootContext).crafttweaker$tableId().toString()).matches();
   }
}
