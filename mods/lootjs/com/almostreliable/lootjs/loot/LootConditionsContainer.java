package com.almostreliable.lootjs.loot;

import com.almostreliable.lootjs.LootJS;
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
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue.Constant;
import net.minecraft.world.item.enchantment.LevelBasedValue.Lookup;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.TimeCheck.Builder;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public interface LootConditionsContainer<C> {
   default C matchTool(ItemPredicate predicate) {
      return this.addCondition(new MatchTool(Optional.of(predicate)));
   }

   default C matchMainHand(ItemFilter filter) {
      return this.addCondition(new MatchEquipmentSlot(EquipmentSlot.MAINHAND, filter));
   }

   default C matchOffHand(ItemFilter filter) {
      return this.addCondition(new MatchEquipmentSlot(EquipmentSlot.OFFHAND, filter));
   }

   default C matchHead(ItemFilter filter) {
      return this.addCondition(new MatchEquipmentSlot(EquipmentSlot.HEAD, filter));
   }

   default C matchChest(ItemFilter filter) {
      return this.addCondition(new MatchEquipmentSlot(EquipmentSlot.CHEST, filter));
   }

   default C matchLegs(ItemFilter filter) {
      return this.addCondition(new MatchEquipmentSlot(EquipmentSlot.LEGS, filter));
   }

   default C matchFeet(ItemFilter filter) {
      return this.addCondition(new MatchEquipmentSlot(EquipmentSlot.FEET, filter));
   }

   default C matchEquip(EquipmentSlot slot, ItemFilter filter) {
      return this.addCondition(new MatchEquipmentSlot(slot, filter));
   }

   default C matchAnyInventorySlot(ItemFilter filter) {
      return this.addCondition(new MatchAnyInventorySlot(filter, false));
   }

   default C matchAnyHotbarSlot(ItemFilter filter) {
      return this.addCondition(new MatchAnyInventorySlot(filter, true));
   }

   default C survivesExplosion() {
      return this.addCondition(ExplosionCondition.survivesExplosion().build());
   }

   default C matchTime(long period, int min, int max) {
      return this.addCondition(new Builder(IntRange.range(min, max)).setPeriod(period).build());
   }

   default C matchTime(int min, int max) {
      return this.matchTime(24000L, min, max);
   }

   default C matchWeather(@Nullable Boolean raining, @Nullable Boolean thundering) {
      net.minecraft.world.level.storage.loot.predicates.WeatherCheck.Builder builder = new net.minecraft.world.level.storage.loot.predicates.WeatherCheck.Builder();
      if (raining != null) {
         builder.setRaining(raining);
      }

      if (thundering != null) {
         builder.setThundering(thundering);
      }

      return this.addCondition(builder.build());
   }

   default C randomChance(NumberProvider value) {
      return this.addCondition(LootItemRandomChanceCondition.randomChance(value).build());
   }

   default C randomChanceWithEnchantment(Holder<Enchantment> enchantment, float[] chances) {
      Preconditions.checkArgument(chances.length > 0, "There must be at least one chance");
      float first = chances[0];
      List<Float> enchantmentChances = new ArrayList<>();

      for (int i = 1; i < chances.length; i++) {
         enchantmentChances.add(chances[i]);
      }

      Lookup lookup = new Lookup(enchantmentChances, new Constant(0.0F));
      return this.addCondition(new LootItemRandomChanceWithEnchantedBonusCondition(first, lookup, enchantment));
   }

   default C randomTableBonus(Holder<Enchantment> enchantment, float[] chances) {
      return this.addCondition(BonusLevelTableCondition.bonusLevelFlatChance(enchantment, chances).build());
   }

   default C matchLocation(LocationPredicate predicate) {
      return this.matchLocation(BlockPos.ZERO, predicate);
   }

   default C matchLocation(BlockPos offset, LocationPredicate predicate) {
      return this.addCondition(new LocationCheck(Optional.of(predicate), offset));
   }

   default C matchBiome(HolderSet<Biome> biomes) {
      return this.addCondition(new MatchBiome(biomes));
   }

   default C matchDimension(ResourceLocation... dimensions) {
      return this.addCondition(new MatchDimension(dimensions));
   }

   default C matchStructure(HolderSet<Structure> structures) {
      return this.addCondition(new MatchStructure(structures, true));
   }

   default C matchStructure(HolderSet<Structure> structures, boolean exact) {
      return this.addCondition(new MatchStructure(structures, exact));
   }

   default C isLightLevel(int min, int max) {
      return this.addCondition(new IsLightLevel(min, max));
   }

   default C luck(Doubles bounds) {
      return this.matchPlayerCustom(serverPlayer -> bounds.matches(serverPlayer.getLuck()));
   }

   default C killedByPlayer() {
      return this.addCondition(LootItemKilledByPlayerCondition.killedByPlayer().build());
   }

   default C matchBlock(Block block) {
      return this.addCondition(new LootItemBlockStatePropertyCondition(block.builtInRegistryHolder(), Optional.empty()));
   }

   default C matchBlock(Block block, StatePropertiesPredicate properties) {
      return this.addCondition(new LootItemBlockStatePropertyCondition(block.builtInRegistryHolder(), Optional.of(properties)));
   }

   default C matchEntity(EntityPredicate entityPredicate) {
      return this.addCondition(LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, entityPredicate).build());
   }

   default C matchAttacker(EntityPredicate entityPredicate) {
      return this.addCondition(LootItemEntityPropertyCondition.hasProperties(EntityTarget.ATTACKER, entityPredicate).build());
   }

   default C matchDirectAttacker(EntityPredicate entityPredicate) {
      return this.addCondition(LootItemEntityPropertyCondition.hasProperties(EntityTarget.DIRECT_ATTACKER, entityPredicate).build());
   }

   default C matchPlayer(EntityPredicate entityPredicate) {
      return this.addCondition(new MatchPlayer(entityPredicate));
   }

   default C matchDamageSource(DamageSourcePredicate predicate) {
      return this.addCondition(new DamageSourceCondition(Optional.of(predicate)));
   }

   default C matchDistance(DistancePredicate distancePredicate) {
      return this.addCondition(new MatchKillerDistance(distancePredicate));
   }

   default C matchPlayerCustom(Predicate<ServerPlayer> predicate) {
      return this.addCondition(new PlayerParamPredicate(predicate));
   }

   default C matchEntityCustom(Predicate<Entity> predicate) {
      return this.addCondition(new CustomParamPredicate<>(LootContextParams.THIS_ENTITY, predicate));
   }

   default C matchAttackerCustom(Predicate<Entity> predicate) {
      return this.addCondition(new CustomParamPredicate<>(LootContextParams.ATTACKING_ENTITY, predicate));
   }

   default C matchDirectAttackerCustom(Predicate<Entity> predicate) {
      return this.addCondition(new CustomParamPredicate<>(LootContextParams.DIRECT_ATTACKING_ENTITY, predicate));
   }

   default C blockEntity(Predicate<BlockEntity> predicate) {
      return this.addCondition(new CustomParamPredicate<>(LootContextParams.BLOCK_ENTITY, predicate));
   }

   default C hasAnyStage(String... stages) {
      if (stages.length == 1) {
         String stage = stages[0];
         PlayerParamPredicate condition = new PlayerParamPredicate(player -> player.getTags().contains(stage));
         return this.addCondition(condition);
      } else {
         PlayerParamPredicate condition = new PlayerParamPredicate(player -> {
            for (String stage : stages) {
               if (player.getTags().contains(stage)) {
                  return true;
               }
            }

            return false;
         });
         return this.addCondition(condition);
      }
   }

   default C matchAnyOf(LootItemCondition... conditions) {
      return this.addCondition(new AnyOfCondition(Arrays.asList(conditions)));
   }

   default C matchAllOf(LootItemCondition... conditions) {
      return this.addCondition(new AllOfCondition(Arrays.asList(conditions)));
   }

   default C matchCustomCondition(JsonObject json) {
      RegistryOps<JsonElement> regOps = RegistryOps.create(JsonOps.INSTANCE, LootJS.lookup());
      LootItemCondition condition = (LootItemCondition)LootItemCondition.DIRECT_CODEC.parse(regOps, json).getOrThrow();
      return this.addCondition(condition);
   }

   C addCondition(LootItemCondition var1);
}
