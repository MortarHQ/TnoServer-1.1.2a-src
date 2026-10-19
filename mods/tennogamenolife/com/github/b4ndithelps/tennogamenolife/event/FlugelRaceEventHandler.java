package com.github.b4ndithelps.tennogamenolife.event;

import com.github.b4ndithelps.tennogamenolife.ability.skill.extra.BlackArrowSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.extra.HeavenlyShiftSkill;
import com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate.TengekiUltimateSkill;
import com.github.b4ndithelps.tennogamenolife.config.TenreincarnationConfig;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.PrueferRace;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.FlugelRace;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.IFlugelRace;
import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import com.github.b4ndithelps.tennogamenolife.registry.skill.TenreincarnationSkill;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.RaceEvents;
import io.github.manasmods.manascore.race.api.RaceEvents.SetRaceEvent;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.data.TensuraTags.Enchantments;
import io.github.manasmods.tensura.enchantment.EngravingHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.handler.GearHandler;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife")
public class FlugelRaceEventHandler {
   private static final String SICKLE_GIVEN_TAG = "tennogamenolife:flugel_sickle_given";
   private static final String SICKLE_LAST_EP_TAG = "tennogamenolife:sickle_last_ep";
   private static final String SICKLE_ENGRAVING_STAGE_TAG = "tennogamenolife:sickle_engraving_stage";
   private static final int ORIGINAL_ENGRAVING_LOCK_LEVEL = 3;
   private static final double[] SICKLE_ENGRAVING_THRESHOLDS = new double[]{100000.0, 250000.0, 400000.0, 550000.0, 700000.0, 850000.0, 1000000.0};
   private static final List<ResourceKey<Enchantment>> COMMON_ENGRAVINGS = buildEnchantmentKeys(EngravingHelper.CONFIG.commonEngraving);
   private static final List<ResourceKey<Enchantment>> UNCOMMON_ENGRAVINGS = buildEnchantmentKeys(EngravingHelper.CONFIG.uncommonEngraving);
   private static final List<ResourceKey<Enchantment>> RARE_ENGRAVINGS = buildEnchantmentKeys(EngravingHelper.CONFIG.rareEngraving);
   private static final List<ResourceKey<Enchantment>> EPIC_ENGRAVINGS = buildEnchantmentKeys(EngravingHelper.CONFIG.epicEngraving);

   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         checkAndGiveSickle(serverPlayer);
         checkAndGrantPrueferSkills(serverPlayer);
         checkAndUnlockHeavenlyShift(serverPlayer);
         checkAndUnlockBlackArrow(serverPlayer);
         ensureFlugelIntrinsicSkills(serverPlayer);
         updateSickleProgress(serverPlayer);
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide && player.tickCount % 100 == 50 && player instanceof ServerPlayer serverPlayer) {
         if (serverPlayer.containerMenu instanceof ReincarnationMenu) {
            return;
         }

         checkAndGiveSickle(serverPlayer);
         checkAndGrantPrueferSkills(serverPlayer);
         checkAndUnlockHeavenlyShift(serverPlayer);
         checkAndUnlockBlackArrow(serverPlayer);
         ensureFlugelIntrinsicSkills(serverPlayer);
      }
   }

   private static void checkAndGiveSickle(ServerPlayer player) {
      if (isFlugelRace(player)) {
         giveSickleIfNeeded(player);
      }
   }

   private static void checkAndGrantPrueferSkills(ServerPlayer player) {
      if (isPrueferRace(player)) {
         grantPrueferSkills(player);
      }
   }

   private static void checkAndUnlockHeavenlyShift(ServerPlayer player) {
      ManasSkill skill = (ManasSkill)TenreincarnationSkill.HEAVENLY_SHIFT.get();
      if (HeavenlyShiftSkill.meetsUnlockRequirement(player) && SkillAPI.getSkillsFrom(player).getSkill(skill).isEmpty()) {
         SkillHelper.learnSkill(player, ((HeavenlyShiftSkill)TenreincarnationSkill.HEAVENLY_SHIFT.get()).createLearningInstance(player));
      }
   }

   private static void checkAndUnlockBlackArrow(ServerPlayer player) {
      ManasSkill skill = (ManasSkill)TenreincarnationSkill.BLACK_ARROW.get();
      if (BlackArrowSkill.meetsUnlockRequirement(player) && SkillAPI.getSkillsFrom(player).getSkill(skill).isEmpty()) {
         SkillHelper.learnSkill(player, ((BlackArrowSkill)TenreincarnationSkill.BLACK_ARROW.get()).createLearningInstance(player));
      }
   }

   private static void ensureFlugelIntrinsicSkills(ServerPlayer player) {
      RaceAPI.getRaceFrom(player).getRace().ifPresent(race -> ensureFlugelIntrinsicSkills(player, race));
   }

   private static void ensureFlugelIntrinsicSkills(ServerPlayer player, ManasRaceInstance race) {
      ManasSkill tengeki = (ManasSkill)TenreincarnationSkill.TENGEKI.get();
      if (!(race.getRace() instanceof IFlugelRace)) {
         if (SkillAPI.getSkillsFrom(player).getSkill(tengeki).isPresent()) {
            SkillAPI.getSkillsFrom(player).forgetSkill(tengeki);
         }
      } else {
         ManasSkill protection = (ManasSkill)TenreincarnationSkill.ETERNAL_FOURTH_PROTECTION.get();
         boolean shouldHaveTengeki = !(race.getRace() instanceof FlugelRace);
         boolean dirty = false;
         if (!shouldHaveTengeki) {
            if (SkillAPI.getSkillsFrom(player).getSkill(tengeki).isPresent()) {
               SkillAPI.getSkillsFrom(player).forgetSkill(tengeki);
            }

            dirty |= race.removeIntrinsicSkill(tengeki);
         } else if (SkillAPI.getSkillsFrom(player).getSkill(tengeki).filter(instance -> !TengekiUltimateSkill.usesCurrentLearningRules(instance)).isPresent()) {
            SkillAPI.getSkillsFrom(player).forgetSkill(tengeki);
            dirty |= race.removeIntrinsicSkill(tengeki);
         }

         if (SkillAPI.getSkillsFrom(player).getSkill(protection).isEmpty() || shouldHaveTengeki && SkillAPI.getSkillsFrom(player).getSkill(tengeki).isEmpty()) {
            race.learnIntrinsicSkills(player);
         }

         if (shouldHaveTengeki
            && SkillAPI.getSkillsFrom(player).getSkill(tengeki).isEmpty()
            && SkillHelper.learnSkill(player, ((TengekiUltimateSkill)TenreincarnationSkill.TENGEKI.get()).createLearningInstance(player))) {
            dirty |= race.addIntrinsicSkill(tengeki);
         }

         if (SkillAPI.getSkillsFrom(player).getSkill(protection).isPresent() && !race.getObtainedIntrinsicSkills().contains(protection)) {
            dirty |= race.addIntrinsicSkill(protection);
         }

         if (shouldHaveTengeki && SkillAPI.getSkillsFrom(player).getSkill(tengeki).isPresent() && !race.getObtainedIntrinsicSkills().contains(tengeki)) {
            dirty |= race.addIntrinsicSkill(tengeki);
         }

         if (dirty) {
            race.markDirty();
         }
      }
   }

   private static void grantPrueferSkills(ServerPlayer player) {
      learnSkill(player, (ManasSkill)UniqueSkills.CREATOR.get());
      learnSkill(player, (ManasSkill)ExtraSkills.SPATIAL_MANIPULATION.get());
      learnSkill(player, (ManasSkill)ExtraSkills.SPATIAL_DOMINATION.get());
      learnSkill(player, (ManasSkill)TenreincarnationSkill.REISEN.get());
   }

   private static void learnSkill(ServerPlayer player, ManasSkill skill) {
      SkillHelper.learnSkill(player, skill);
   }

   private static void giveSickleIfNeeded(ServerPlayer player) {
      if (TenreincarnationConfig.shouldGiveFlugelSickleOnStart()) {
         CompoundTag persistentData = getEquipmentPersistentData(player, "tennogamenolife:flugel_sickle_given");
         if (!persistentData.getBoolean("tennogamenolife:flugel_sickle_given")) {
            if (hasSickle(player)) {
               persistentData.putBoolean("tennogamenolife:flugel_sickle_given", true);
            } else {
               ItemStack sickle = new ItemStack((ItemLike)Tenreincarnationitems.SICKLE.get());
               GearHandler.initiateGearExistence(player.level(), sickle);
               initializeSickleTracking(sickle);
               boolean added = player.getInventory().add(sickle);
               if (!added) {
                  player.drop(sickle, false);
               }

               persistentData.putBoolean("tennogamenolife:flugel_sickle_given", true);
            }
         }
      }
   }

   private static void updateSickleProgress(ServerPlayer player) {
      for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
         ItemStack stack = player.getInventory().getItem(slot);
         if (stack.is((Item)Tenreincarnationitems.SICKLE.get())) {
            updateSingleSickle(player, stack);
         }
      }
   }

   private static void updateSingleSickle(ServerPlayer player, ItemStack stack) {
      if (!stack.has((DataComponentType)TensuraDataComponents.MAX_EP.get())) {
         GearHandler.initiateGearExistence(player.level(), stack);
      }

      initializeSickleTracking(stack);
      if (stack.has((DataComponentType)TensuraDataComponents.EP.get())) {
         double currentEP = (Double)stack.getOrDefault((DataComponentType)TensuraDataComponents.EP.get(), 0.0);
         double lastEP = getTrackedEP(stack);
         if (!(currentEP <= lastEP)) {
            grantCustomRandomEngraving(player, stack, currentEP);
            setTrackedEP(stack, currentEP);
         }
      }
   }

   private static void grantCustomRandomEngraving(ServerPlayer player, ItemStack stack, double currentEP) {
      int currentStage = getCustomEngravingStage(stack);
      if (currentStage < SICKLE_ENGRAVING_THRESHOLDS.length) {
         if (!(currentEP < SICKLE_ENGRAVING_THRESHOLDS[currentStage])) {
            int nextStage = currentStage + 1;
            setCustomEngravingStage(stack, nextStage);
            Rarity rarity = getRarity(nextStage, player);
            Holder<Enchantment> engraving = getRandomEngraving(player, stack, rarity);
            if (engraving != null) {
               int baseLevel = getBaseLevel(rarity);
               EngravingHelper.increaseEngraving(player, stack, engraving, baseLevel);
               if (player.getRandom().nextFloat() * 100.0F < EngravingHelper.CONFIG.curseChance) {
                  EngravingHelper.applyCurseEngraving(player, stack, baseLevel);
               }

               if (nextStage == SICKLE_ENGRAVING_THRESHOLDS.length && !EnchantmentHelper.hasTag(stack, Enchantments.TSUKUMOGAMI)) {
                  stack.enchant(player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(TensuraEnchantments.TSUKUMOGAMI), 1);
               }
            }
         }
      }
   }

   private static boolean isFlugelRace(ServerPlayer player) {
      return RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace() instanceof IFlugelRace).orElse(false);
   }

   private static boolean isPrueferRace(ServerPlayer player) {
      return RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace() instanceof PrueferRace).orElse(false);
   }

   private static boolean hasSickle(Player player) {
      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         if (player.getInventory().getItem(i).is((Item)Tenreincarnationitems.SICKLE.get())) {
            return true;
         }
      }

      return player.getMainHandItem().is((Item)Tenreincarnationitems.SICKLE.get()) || player.getOffhandItem().is((Item)Tenreincarnationitems.SICKLE.get());
   }

   private static CompoundTag getEquipmentPersistentData(Player player, String key) {
      CompoundTag root = player.getPersistentData();
      CompoundTag persisted = root.getCompound("PlayerPersisted");
      if (root.contains(key)) {
         if (!persisted.contains(key)) {
            persisted.putBoolean(key, root.getBoolean(key));
         }

         root.remove(key);
      }

      root.put("PlayerPersisted", persisted);
      return persisted;
   }

   private static void initializeSickleTracking(ItemStack stack) {
      int originalLevel = stack.has((DataComponentType)TensuraDataComponents.RANDOM_ENGRAVING_LEVEL.get())
         ? (Integer)stack.get((DataComponentType)TensuraDataComponents.RANDOM_ENGRAVING_LEVEL.get())
         : 0;
      if (originalLevel < 3) {
         stack.set((DataComponentType)TensuraDataComponents.RANDOM_ENGRAVING_LEVEL.get(), 3);
      }

      CompoundTag tag = getSickleDataTag(stack);
      if (!tag.contains("tennogamenolife:sickle_last_ep")) {
         tag.putDouble("tennogamenolife:sickle_last_ep", (Double)stack.getOrDefault((DataComponentType)TensuraDataComponents.EP.get(), 0.0));
      }

      if (!tag.contains("tennogamenolife:sickle_engraving_stage")) {
         tag.putInt("tennogamenolife:sickle_engraving_stage", 0);
      }

      setSickleDataTag(stack, tag);
   }

   private static double getTrackedEP(ItemStack stack) {
      return getSickleDataTag(stack).getDouble("tennogamenolife:sickle_last_ep");
   }

   private static void setTrackedEP(ItemStack stack, double ep) {
      CompoundTag tag = getSickleDataTag(stack);
      tag.putDouble("tennogamenolife:sickle_last_ep", ep);
      setSickleDataTag(stack, tag);
   }

   private static int getCustomEngravingStage(ItemStack stack) {
      return getSickleDataTag(stack).getInt("tennogamenolife:sickle_engraving_stage");
   }

   private static void setCustomEngravingStage(ItemStack stack, int stage) {
      CompoundTag tag = getSickleDataTag(stack);
      tag.putInt("tennogamenolife:sickle_engraving_stage", stage);
      setSickleDataTag(stack, tag);
   }

   private static CompoundTag getSickleDataTag(ItemStack stack) {
      return ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
   }

   private static void setSickleDataTag(ItemStack stack, CompoundTag tag) {
      if (tag.isEmpty()) {
         stack.remove(DataComponents.CUSTOM_DATA);
      } else {
         stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      }
   }

   private static Holder<Enchantment> getRandomEngraving(LivingEntity entity, ItemStack stack, Rarity rarity) {
      List<ResourceKey<Enchantment>> pool = switch (rarity) {
         case COMMON -> COMMON_ENGRAVINGS;
         case UNCOMMON -> UNCOMMON_ENGRAVINGS;
         case RARE -> RARE_ENGRAVINGS;
         case EPIC -> EPIC_ENGRAVINGS;
         default -> List.of();
      };
      List<Holder<Enchantment>> filtered = pool.stream()
         .map(key -> TensuraEnchantmentHelper.getEnchantment(entity.level(), key))
         .filter(holder -> ((Enchantment)holder.value()).canEnchant(stack))
         .toList();
      return filtered.isEmpty() ? null : filtered.get(entity.getRandom().nextInt(filtered.size()));
   }

   private static Rarity getRarity(int stage, LivingEntity entity) {
      int roll = entity.getRandom().nextInt(100);
      List<FlugelRaceEventHandler.RarityWeight> weights;
      if (stage == 1) {
         weights = List.of(
            new FlugelRaceEventHandler.RarityWeight(Rarity.EPIC, EngravingHelper.CONFIG.uniqueEpic),
            new FlugelRaceEventHandler.RarityWeight(Rarity.RARE, EngravingHelper.CONFIG.uniqueRare),
            new FlugelRaceEventHandler.RarityWeight(Rarity.UNCOMMON, EngravingHelper.CONFIG.uniqueUncommon),
            new FlugelRaceEventHandler.RarityWeight(Rarity.COMMON, 100)
         );
      } else if (stage == 2) {
         weights = List.of(
            new FlugelRaceEventHandler.RarityWeight(Rarity.EPIC, EngravingHelper.CONFIG.legendEpic),
            new FlugelRaceEventHandler.RarityWeight(Rarity.RARE, EngravingHelper.CONFIG.legendRare),
            new FlugelRaceEventHandler.RarityWeight(Rarity.UNCOMMON, 100)
         );
      } else {
         weights = List.of(
            new FlugelRaceEventHandler.RarityWeight(Rarity.EPIC, EngravingHelper.CONFIG.godEpic), new FlugelRaceEventHandler.RarityWeight(Rarity.RARE, 100)
         );
      }

      return getRandomRarity(roll, weights);
   }

   private static Rarity getRandomRarity(int roll, List<FlugelRaceEventHandler.RarityWeight> weights) {
      double cumulative = 0.0;

      for (FlugelRaceEventHandler.RarityWeight weight : weights) {
         cumulative += weight.weight();
         if (roll < cumulative) {
            return weight.rarity();
         }
      }

      return Rarity.COMMON;
   }

   private static int getBaseLevel(Rarity rarity) {
      return switch (rarity) {
         case COMMON, UNCOMMON -> 1;
         case RARE -> 2;
         case EPIC -> 3;
         default -> 1;
      };
   }

   private static List<ResourceKey<Enchantment>> buildEnchantmentKeys(List<String> ids) {
      return ids.stream().<ResourceLocation>map(ResourceLocation::parse).map(location -> ResourceKey.create(Registries.ENCHANTMENT, location)).toList();
   }

   static {
      RaceEvents.SET_RACE.register((SetRaceEvent)(oldRace, entity, newRace, evolution, teleport, message) -> {
         if (entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.containerMenu instanceof ReincarnationMenu) {
               return EventResult.pass();
            }

            if (newRace.getRace() instanceof IFlugelRace) {
               ensureFlugelIntrinsicSkills(serverPlayer, newRace);
               giveSickleIfNeeded(serverPlayer);
               checkAndUnlockBlackArrow(serverPlayer);
            }

            if (newRace.getRace() instanceof PrueferRace) {
               grantPrueferSkills(serverPlayer);
            }
         }

         return EventResult.pass();
      });
      EntityEvents.DEATH_EVENT_LAST.register((LivingDeath)(entity, source) -> {
         if (source.getEntity() instanceof ServerPlayer serverPlayer) {
            updateSickleProgress(serverPlayer);
            return EventResult.pass();
         } else {
            return EventResult.pass();
         }
      });
   }

   private record RarityWeight(Rarity rarity, int weight) {
   }
}
