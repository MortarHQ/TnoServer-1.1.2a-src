package com.almostreliable.lootjs;

import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.loot.condition.CustomParamPredicate;
import com.almostreliable.lootjs.loot.condition.IsLightLevel;
import com.almostreliable.lootjs.loot.condition.MatchAnyInventorySlot;
import com.almostreliable.lootjs.loot.condition.MatchBiome;
import com.almostreliable.lootjs.loot.condition.MatchDimension;
import com.almostreliable.lootjs.loot.condition.MatchEquipmentSlot;
import com.almostreliable.lootjs.loot.condition.MatchKillerDistance;
import com.almostreliable.lootjs.loot.condition.MatchPlayer;
import com.almostreliable.lootjs.loot.condition.MatchStructure;
import com.almostreliable.lootjs.loot.condition.PlayerParamPredicate;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LootJSConditions {
   static final DeferredRegister<LootItemConditionType> CONDITIONS = DeferredRegister.create(BuiltInRegistries.LOOT_CONDITION_TYPE, "lootjs");
   public static Holder<LootItemConditionType> MATCH_EQUIP = CONDITIONS.register(
      "match_equip", () -> create(new MatchEquipmentSlot(EquipmentSlot.MAINHAND, ItemFilter.NONE))
   );
   public static Holder<LootItemConditionType> MATCH_ANY_INVENTORY_SLOT = CONDITIONS.register(
      "match_any_inventory_slot", () -> create(new MatchAnyInventorySlot(ItemFilter.NONE, false))
   );
   public static Holder<LootItemConditionType> DISTANCE = CONDITIONS.register(
      "match_distance", () -> create(new MatchKillerDistance(DistancePredicate.vertical(Doubles.ANY)))
   );
   public static Holder<LootItemConditionType> ANY_STRUCTURE = CONDITIONS.register(
      "match_structure", () -> create(new MatchStructure(HolderSet.direct(new Holder[0]), true))
   );
   public static Holder<LootItemConditionType> BIOME = CONDITIONS.register("match_biome", () -> create(new MatchBiome(HolderSet.direct(new Holder[0]))));
   public static Holder<LootItemConditionType> LIGHT_LEVEL = CONDITIONS.register("light_level", () -> create(new IsLightLevel(-1, -1)));
   public static Holder<LootItemConditionType> ANY_DIMENSION = CONDITIONS.register("match_dimension", () -> create(new MatchDimension(new ResourceLocation[0])));
   public static Holder<LootItemConditionType> PARAM = CONDITIONS.register(
      "param", () -> create(new CustomParamPredicate<>(LootContextParams.THIS_ENTITY, entity -> false))
   );
   public static Holder<LootItemConditionType> PLAYER_PARAM = CONDITIONS.register("player_param", () -> create(new PlayerParamPredicate(p -> false)));
   public static Holder<LootItemConditionType> MATCH_PLAYER = CONDITIONS.register("match_player", () -> create(new MatchPlayer(Builder.entity().build())));

   private static LootItemConditionType create(LootItemCondition unit) {
      return new LootItemConditionType(MapCodec.unit(unit));
   }
}
