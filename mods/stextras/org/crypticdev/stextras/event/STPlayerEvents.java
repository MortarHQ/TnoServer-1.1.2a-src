package org.crypticdev.stextras.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public final class STPlayerEvents {
   public static final Event<STPlayerEvents.CraftCallback> CRAFT = EventFactory.createLoop(STPlayerEvents.CraftCallback.class);
   public static final Event<STPlayerEvents.CookCallback> COOK = EventFactory.createLoop(STPlayerEvents.CookCallback.class);
   public static final Event<STPlayerEvents.TradeCallback> TRADE = EventFactory.createLoop(STPlayerEvents.TradeCallback.class);
   public static final Event<STPlayerEvents.SmithCallback> SMITH = EventFactory.createLoop(STPlayerEvents.SmithCallback.class);
   public static final Event<STPlayerEvents.BreedCallback> BREED = EventFactory.createLoop(STPlayerEvents.BreedCallback.class);
   public static final Event<STPlayerEvents.PrestigeCallback> PRESTIGE = EventFactory.createLoop(STPlayerEvents.PrestigeCallback.class);
   public static final Event<STPlayerEvents.PrestigeLevelCallback> PRESTIGE_LEVEL = EventFactory.createLoop(STPlayerEvents.PrestigeLevelCallback.class);
   public static final Event<STPlayerEvents.SoulGradeChangedCallback> SOUL_GRADE_CHANGED = EventFactory.createLoop(
      STPlayerEvents.SoulGradeChangedCallback.class
   );
   public static final Event<STPlayerEvents.ReincarnationEssenceChangedCallback> REINCARNATION_ESSENCE_CHANGED = EventFactory.createLoop(
      STPlayerEvents.ReincarnationEssenceChangedCallback.class
   );
   public static final Event<STPlayerEvents.SkillLockChangedCallback> SKILL_LOCK_CHANGED = EventFactory.createLoop(
      STPlayerEvents.SkillLockChangedCallback.class
   );
   public static final Event<STPlayerEvents.QuestCompletedCallback> QUEST_COMPLETED = EventFactory.createLoop(STPlayerEvents.QuestCompletedCallback.class);
   public static final Event<STPlayerEvents.RacePrestigeCompletedCallback> RACE_PRESTIGE_COMPLETED = EventFactory.createLoop(
      STPlayerEvents.RacePrestigeCompletedCallback.class
   );
   public static final Event<STPlayerEvents.MasteryPointCallback> MASTERY_POINT = EventFactory.createLoop(STPlayerEvents.MasteryPointCallback.class);
   public static final Event<STPlayerEvents.SkillMasteredCallback> SKILL_MASTERED = EventFactory.createLoop(STPlayerEvents.SkillMasteredCallback.class);
   public static final Event<STPlayerEvents.SkillInteractionCallback> SKILL_INTERACTION = EventFactory.createLoop(STPlayerEvents.SkillInteractionCallback.class);
   public static final Event<STPlayerEvents.TRSmithCallback> TR_SMITH = EventFactory.createLoop(STPlayerEvents.TRSmithCallback.class);
   public static final Event<STPlayerEvents.RaceEvolveCallback> RACE_EVOLVE = EventFactory.createLoop(STPlayerEvents.RaceEvolveCallback.class);
   public static final Event<STPlayerEvents.ConsumeCallback> CONSUME = EventFactory.createLoop(STPlayerEvents.ConsumeCallback.class);
   public static final Event<STPlayerEvents.EnchantCallback> ENCHANT = EventFactory.createLoop(STPlayerEvents.EnchantCallback.class);
   public static final Event<STPlayerEvents.MiningStationCallback> MINING_STATION = EventFactory.createLoop(STPlayerEvents.MiningStationCallback.class);
   public static final Event<STPlayerEvents.WoodcutCallback> WOODCUT = EventFactory.createLoop(STPlayerEvents.WoodcutCallback.class);
   public static final Event<STPlayerEvents.SpellBindingCallback> SPELL_BINDING = EventFactory.createLoop(STPlayerEvents.SpellBindingCallback.class);
   public static final Event<STPlayerEvents.EquipEvolveCallback> EQUIP_EVOLVE = EventFactory.createLoop(STPlayerEvents.EquipEvolveCallback.class);

   private STPlayerEvents() {
   }

   public static void fireCraft(ServerPlayer player, ItemStack result) {
      ((STPlayerEvents.CraftCallback)CRAFT.invoker()).onCraft(player, result);
   }

   public static void fireCook(ServerPlayer player, ItemStack result) {
      ((STPlayerEvents.CookCallback)COOK.invoker()).onCook(player, result);
   }

   public static void fireTrade(ServerPlayer player, MerchantOffer offer, ItemStack result) {
      ((STPlayerEvents.TradeCallback)TRADE.invoker()).onTrade(player, offer, result);
   }

   public static void fireSmith(ServerPlayer player, ItemStack result) {
      ((STPlayerEvents.SmithCallback)SMITH.invoker()).onSmith(player, result);
   }

   public static void fireBreed(ServerPlayer player, AgeableMob baby, AgeableMob parentA, AgeableMob parentB) {
      ((STPlayerEvents.BreedCallback)BREED.invoker()).onBreed(player, baby, parentA, parentB);
   }

   public static void firePrestige(ServerPlayer player) {
      ((STPlayerEvents.PrestigeCallback)PRESTIGE.invoker()).onPrestige(player);
   }

   public static void firePrestigeLevel(ServerPlayer player, int prestigeLevel) {
      ((STPlayerEvents.PrestigeLevelCallback)PRESTIGE_LEVEL.invoker()).onPrestigeLevel(player, prestigeLevel);
   }

   public static void fireSoulGradeChanged(ServerPlayer player, int before, int after) {
      ((STPlayerEvents.SoulGradeChangedCallback)SOUL_GRADE_CHANGED.invoker()).onSoulGradeChanged(player, before, after);
   }

   public static void fireReincarnationEssenceChanged(ServerPlayer player, int before, int after) {
      ((STPlayerEvents.ReincarnationEssenceChangedCallback)REINCARNATION_ESSENCE_CHANGED.invoker()).onReincarnationEssenceChanged(player, before, after);
   }

   public static void fireSkillLockChanged(ServerPlayer player, ResourceLocation skillId, boolean locked) {
      ((STPlayerEvents.SkillLockChangedCallback)SKILL_LOCK_CHANGED.invoker()).onSkillLockChanged(player, skillId, locked);
   }

   public static void fireQuestCompleted(ServerPlayer player, ResourceLocation questId) {
      ((STPlayerEvents.QuestCompletedCallback)QUEST_COMPLETED.invoker()).onQuestCompleted(player, questId);
   }

   public static void fireRacePrestigeCompleted(ServerPlayer player, ResourceLocation raceId) {
      ((STPlayerEvents.RacePrestigeCompletedCallback)RACE_PRESTIGE_COMPLETED.invoker()).onRacePrestigeCompleted(player, raceId);
   }

   public static void fireMasteryPoint(LivingEntity entity, ManasSkillInstance skill, double before, double after) {
      ((STPlayerEvents.MasteryPointCallback)MASTERY_POINT.invoker()).onMasteryPoint(entity, skill, before, after);
   }

   public static void fireSkillMastered(LivingEntity entity, ManasSkillInstance skill) {
      ((STPlayerEvents.SkillMasteredCallback)SKILL_MASTERED.invoker()).onSkillMastered(entity, skill);
   }

   public static void fireSkillInteraction(LivingEntity entity, ManasSkillInstance skill, STPlayerEvents.SkillInteractionType type) {
      ((STPlayerEvents.SkillInteractionCallback)SKILL_INTERACTION.invoker()).onSkillInteraction(entity, skill, type);
   }

   public static void fireTRSmith(ServerPlayer player, ItemStack result) {
      ((STPlayerEvents.TRSmithCallback)TR_SMITH.invoker()).onTRSmith(player, result);
   }

   public static void fireRaceEvolve(ServerPlayer player, ManasRace newRace) {
      ((STPlayerEvents.RaceEvolveCallback)RACE_EVOLVE.invoker()).onEvolve(player, newRace);
   }

   public static void fireConsume(LivingEntity entity, ItemStack stack) {
      ((STPlayerEvents.ConsumeCallback)CONSUME.invoker()).onConsume(entity, stack);
   }

   public static void fireEnchant(ServerPlayer player, ItemStack stack) {
      ((STPlayerEvents.EnchantCallback)ENCHANT.invoker()).onEnchant(player, stack);
   }

   public static void fireMiningStation(ServerPlayer player, ItemStack stack) {
      ((STPlayerEvents.MiningStationCallback)MINING_STATION.invoker()).onMiningStation(player, stack);
   }

   public static void fireWoodcut(ServerPlayer player, ItemStack stack) {
      ((STPlayerEvents.WoodcutCallback)WOODCUT.invoker()).onWoodcut(player, stack);
   }

   public static void fireSpellBinding(ServerPlayer player, ResourceLocation skillId) {
      ((STPlayerEvents.SpellBindingCallback)SPELL_BINDING.invoker()).onSpellBinding(player, skillId);
   }

   public static void fireEquipEvolve(LivingEntity entity, ItemStack stack) {
      ((STPlayerEvents.EquipEvolveCallback)EQUIP_EVOLVE.invoker()).onEquipEvolve(entity, stack);
   }

   @FunctionalInterface
   public interface BreedCallback {
      void onBreed(ServerPlayer var1, AgeableMob var2, AgeableMob var3, AgeableMob var4);
   }

   @FunctionalInterface
   public interface ConsumeCallback {
      void onConsume(LivingEntity var1, ItemStack var2);
   }

   @FunctionalInterface
   public interface CookCallback {
      void onCook(ServerPlayer var1, ItemStack var2);
   }

   @FunctionalInterface
   public interface CraftCallback {
      void onCraft(ServerPlayer var1, ItemStack var2);
   }

   @FunctionalInterface
   public interface EnchantCallback {
      void onEnchant(ServerPlayer var1, ItemStack var2);
   }

   @FunctionalInterface
   public interface EquipEvolveCallback {
      void onEquipEvolve(LivingEntity var1, ItemStack var2);
   }

   @FunctionalInterface
   public interface MasteryPointCallback {
      void onMasteryPoint(LivingEntity var1, ManasSkillInstance var2, double var3, double var5);
   }

   @FunctionalInterface
   public interface MiningStationCallback {
      void onMiningStation(ServerPlayer var1, ItemStack var2);
   }

   @FunctionalInterface
   public interface PrestigeCallback {
      void onPrestige(ServerPlayer var1);
   }

   @FunctionalInterface
   public interface PrestigeLevelCallback {
      void onPrestigeLevel(ServerPlayer var1, int var2);
   }

   @FunctionalInterface
   public interface QuestCompletedCallback {
      void onQuestCompleted(ServerPlayer var1, ResourceLocation var2);
   }

   @FunctionalInterface
   public interface RaceEvolveCallback {
      void onEvolve(ServerPlayer var1, ManasRace var2);
   }

   @FunctionalInterface
   public interface RacePrestigeCompletedCallback {
      void onRacePrestigeCompleted(ServerPlayer var1, ResourceLocation var2);
   }

   @FunctionalInterface
   public interface ReincarnationEssenceChangedCallback {
      void onReincarnationEssenceChanged(ServerPlayer var1, int var2, int var3);
   }

   @FunctionalInterface
   public interface SkillInteractionCallback {
      void onSkillInteraction(LivingEntity var1, ManasSkillInstance var2, STPlayerEvents.SkillInteractionType var3);
   }

   public enum SkillInteractionType {
      PRESS,
      RELEASE,
      TOGGLE_ON,
      BEING_DAMAGED,
      DAMAGE_ENTITY,
      TOUCH_ENTITY,
      TAKEN_DAMAGE,
      EFFECT_ADDED,
      BEING_TARGETED,
      PROJECTILE_HIT;
   }

   @FunctionalInterface
   public interface SkillLockChangedCallback {
      void onSkillLockChanged(ServerPlayer var1, ResourceLocation var2, boolean var3);
   }

   @FunctionalInterface
   public interface SkillMasteredCallback {
      void onSkillMastered(LivingEntity var1, ManasSkillInstance var2);
   }

   @FunctionalInterface
   public interface SmithCallback {
      void onSmith(ServerPlayer var1, ItemStack var2);
   }

   @FunctionalInterface
   public interface SoulGradeChangedCallback {
      void onSoulGradeChanged(ServerPlayer var1, int var2, int var3);
   }

   @FunctionalInterface
   public interface SpellBindingCallback {
      void onSpellBinding(ServerPlayer var1, ResourceLocation var2);
   }

   @FunctionalInterface
   public interface TRSmithCallback {
      void onTRSmith(ServerPlayer var1, ItemStack var2);
   }

   @FunctionalInterface
   public interface TradeCallback {
      void onTrade(ServerPlayer var1, MerchantOffer var2, ItemStack var3);
   }

   @FunctionalInterface
   public interface WoodcutCallback {
      void onWoodcut(ServerPlayer var1, ItemStack var2);
   }
}
