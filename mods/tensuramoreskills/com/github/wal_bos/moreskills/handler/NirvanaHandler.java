package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.NirvanaSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.NirvanaActionPayload;
import com.github.wal_bos.moreskills.net.msg.NirvanaToastPayload;
import com.github.wal_bos.moreskills.nirvana.NirvanaData;
import com.github.wal_bos.moreskills.nirvana.NirvanaMenuProvider;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.skill.api.SkillEvents.RemoveSkillEvent;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.unique.CookSkill;
import io.github.manasmods.tensura.ability.subclass.ISubAbilityModeHolder;
import io.github.manasmods.tensura.advancement.TensuraAdvancements;
import io.github.manasmods.tensura.entity.variant.BoneGolemVariant;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import io.github.manasmods.tensura.race.TensuraRace;
import io.github.manasmods.tensura.registry.TensuraStats;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class NirvanaHandler {
   private static final String HANDLED_DEATH = "NirvanaHandledDeath";

   private NirvanaHandler() {
   }

   public static void init() {
      EntityEvents.DEATH_EVENT_FIRST.register((LivingDeath)(entity, source) -> {
         if (entity instanceof ServerPlayer player) {
            if (!hasNirvana(player)) {
               return EventResult.pass();
            }

            if (NirvanaData.root(player).getBoolean("NirvanaHandledDeath")) {
               return EventResult.pass();
            }

            NirvanaData.root(player).putBoolean("NirvanaHandledDeath", true);
            removeAllActiveEffects(player);
            archiveAndReincarnate(player);
            teleportToRespawn(player);
            removeAllActiveEffects(player);
            NirvanaData.root(player).putBoolean("NirvanaHandledDeath", false);
            player.setHealth(Math.max(1.0F, player.getMaxHealth()));
            player.invulnerableTime = 100;
            player.setInvulnerable(false);
            player.displayClientMessage(Component.translatable("skill.moreskills.nirvana.reincarnated").withStyle(ChatFormatting.LIGHT_PURPLE), false);
            return EventResult.interruptFalse();
         } else {
            return EventResult.pass();
         }
      });
   }

   public static boolean hasNirvana(LivingEntity entity) {
      return SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)AllSkills.NIRVANA.get()).isPresent();
   }

   public static void openArchive(ServerPlayer player) {
      NirvanaMenuProvider.open(player);
   }

   public static void openArchive(ServerPlayer player, NirvanaData.Sort sort) {
      NirvanaMenuProvider.open(player, sort);
   }

   public static void handleClientAction(ServerPlayer player, NirvanaActionPayload payload) {
      switch (payload.action()) {
         case RENAME:
            NirvanaData.renameLife(player, payload.lifeId(), payload.name());
            openArchive(player, payload.sort());
            break;
         case DELETE:
            NirvanaData.deleteLife(player, payload.lifeId());
            openArchive(player, payload.sort());
            break;
         case SORT:
            openArchive(player, payload.sort());
            break;
         case UNLOCK:
            unlockArchivedMode(player, payload.lifeId(), payload.skill(), payload.mode(), payload.sort());
      }
   }

   public static boolean unlockArchivedMode(ServerPlayer player, UUID lifeId, ResourceLocation skillId, int mode) {
      return unlockArchivedMode(player, lifeId, skillId, mode, NirvanaData.Sort.NEWEST);
   }

   public static boolean unlockArchivedMode(ServerPlayer player, UUID lifeId, ResourceLocation skillId, int mode, NirvanaData.Sort sort) {
      if (NirvanaData.isUnlocked(player, lifeId, skillId, mode)) {
         sendMigrationPopup(player, false, "Already Migrated", "This mode already belongs to this reincarnation.");
         return false;
      }

      CompoundTag life = NirvanaData.findLife(player, lifeId);
      if (life == null) {
         sendMigrationPopup(player, false, "Life Missing", "That archive record could not be found.");
         return false;
      }

      CompoundTag archivedSkill = findArchivedSkill(life, skillId);
      if (archivedSkill == null) {
         sendMigrationPopup(player, false, "Skill Missing", "That skill was not stored in this archived life.");
         return false;
      }

      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
      if (skill == null) {
         sendMigrationPopup(player, false, "Lost Record", "The skill registry no longer contains " + skillId + ".");
         return false;
      }

      ManasSkillInstance nirvana = (ManasSkillInstance)SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.NIRVANA.get()).orElse(null);
      if (nirvana != null && nirvana.getSkill() instanceof NirvanaSkill nirvanaSkill) {
         int cost = getMigrationCost(skill);
         if (cost == Integer.MAX_VALUE) {
            sendMigrationPopup(player, false, "Forbidden Migration", "This skill type is disabled in the config.");
            return false;
         }

         if (!NirvanaData.spendKarma(player, cost)) {
            sendMigrationPopup(player, false, "Insufficient Karma", "You need " + cost + " Karma to migrate this record.");
            return false;
         }

         if (shouldGrantDirectly(skill, archivedSkill)) {
            grantPermanentMigratedSkill(player, skill);
            markAllArchivedModesUnlocked(player, lifeId, archivedSkill, skillId);
            removeNirvanaSubModes(nirvana, player, skill);
            nirvana.markDirty();
            SkillAPI.getSkillsFrom(player).markDirty();
            syncManasCore(player);
            openArchive(player, sort);
            sendMigrationPopup(player, true, "Direct Manifestation", skill.getName().getString() + " was restored as a full skill.");
            return true;
         }

         if (!nirvanaSkill.learnSubSkill(nirvana, player, skill, mode, 100.0)) {
            NirvanaData.addKarma(player, cost);
            sendMigrationPopup(player, false, "Migration Failed", "Nirvana rejected that sub-mode. Karma was refunded.");
            return false;
         }

         NirvanaData.markUnlocked(player, lifeId, skillId, mode);
         boolean completed = allArchivedModesUnlocked(player, lifeId, archivedSkill, skillId);
         if (completed) {
            grantPermanentMigratedSkill(player, skill);
            markAllArchivedModesUnlocked(player, lifeId, archivedSkill, skillId);
            removeNirvanaSubModes(nirvana, player, skill);
         }

         nirvana.markDirty();
         SkillAPI.getSkillsFrom(player).markDirty();
         syncManasCore(player);
         openArchive(player, sort);
         if (completed) {
            sendMigrationPopup(player, true, "Archive Completed", skill.getName().getString() + " is now a full skill. Its Nirvana echoes were released.");
         } else {
            sendMigrationPopup(player, true, "Sub-Mode Migrated", skill.getName().getString() + " mode " + mode + " was bound to Nirvana.");
         }

         return true;
      } else {
         sendMigrationPopup(player, false, "Nirvana Missing", "The archive cannot bind without Nirvana being learned.");
         return false;
      }
   }

   private static void sendMigrationPopup(ServerPlayer player, boolean success, String title, String body) {
      try {
         NetworkManager.sendToPlayer(player, new NirvanaToastPayload(success, title, body));
      } catch (Throwable ignored) {
         player.displayClientMessage(Component.literal(title + ": " + body).withStyle(success ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.RED), true);
      }
   }

   private static boolean shouldGrantDirectly(ManasSkill skill, CompoundTag archivedSkill) {
      if (skill instanceof Skill tensuraSkill && tensuraSkill.getType() == SkillType.RESISTANCE) {
         return true;
      } else {
         ListTag modes = archivedSkill.getList("ModeList", 10);
         return modes.size() <= 1;
      }
   }

   private static void grantPermanentMigratedSkill(ServerPlayer player, ManasSkill skill) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      if (skills.getSkill(skill).isEmpty()) {
         SkillHelper.learnSkill(player, skill.createDefaultInstance());
      }

      if (skill instanceof Skill tensuraSkill && tensuraSkill.getType() == SkillType.RESISTANCE && !isNullificationSkill(skill)) {
         maxResistanceMastery(player, skill);
      }

      NirvanaData.markNoKarmaSkill(player, skill);
      skills.markDirty();
   }

   private static void maxResistanceMastery(ServerPlayer player, ManasSkill skill) {
      try {
         ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(player).getSkill(skill).orElse(null);
         if (instance == null) {
            return;
         }

         boolean mastered = instance.isMastered(player);
         instance.setMastery(instance.getMaxMastery());
         if (!mastered) {
            instance.onSkillMastered(player);
         }

         instance.markDirty();
         SkillAPI.getSkillsFrom(player).markDirty();
      } catch (Throwable var4) {
      }
   }

   private static void markAllArchivedModesUnlocked(ServerPlayer player, UUID lifeId, CompoundTag archivedSkill, ResourceLocation skillId) {
      ListTag modes = archivedSkill.getList("ModeList", 10);
      if (modes.isEmpty()) {
         NirvanaData.markUnlocked(player, lifeId, skillId, 0);
      } else {
         for (int i = 0; i < modes.size(); i++) {
            NirvanaData.markUnlocked(player, lifeId, skillId, modes.getCompound(i).getInt("Mode"));
         }
      }
   }

   private static boolean allArchivedModesUnlocked(ServerPlayer player, UUID lifeId, CompoundTag archivedSkill, ResourceLocation skillId) {
      ListTag modes = archivedSkill.getList("ModeList", 10);
      if (modes.isEmpty()) {
         return NirvanaData.isUnlocked(player, lifeId, skillId, 0);
      }

      for (int i = 0; i < modes.size(); i++) {
         if (!NirvanaData.isUnlocked(player, lifeId, skillId, modes.getCompound(i).getInt("Mode"))) {
            return false;
         }
      }

      return true;
   }

   private static void removeNirvanaSubModes(ManasSkillInstance nirvana, ServerPlayer player, ManasSkill skill) {
      try {
         if (nirvana.getSkill() instanceof ISubAbilityModeHolder holder) {
            for (AbilitySlot slot : new ArrayList(ISubAbilityModeHolder.getSubSlots(nirvana))) {
               if (slot != null && sameSkill(slot.getSkill(), skill)) {
                  holder.removeSubSkill(nirvana, player, skill, slot.getMode());
               }
            }
         }
      } catch (Throwable var10) {
      }

      try {
         List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(nirvana);
         slots.removeIf(slotx -> slotx != null && sameSkill(slotx.getSkill(), skill));
      } catch (Throwable var9) {
      }

      try {
         nirvana.getSubInstances().entrySet().removeIf(entry -> sameSkill((ManasSkill)entry.getKey(), skill));
      } catch (Throwable var8) {
      }
   }

   private static boolean sameSkill(ManasSkill a, ManasSkill b) {
      if (a == b) {
         return true;
      } else {
         return a != null && b != null && a.getRegistryName() != null && b.getRegistryName() != null ? a.getRegistryName().equals(b.getRegistryName()) : false;
      }
   }

   public static int getMigrationCost(ManasSkill skill) {
      if (skill instanceof Skill tensuraSkill) {
         return switch (tensuraSkill.getType()) {
            case COMMON -> MoreSkillsConfigs.GRAND.nirvana.commonModeCost.get();
            case INTRINSIC -> MoreSkillsConfigs.GRAND.nirvana.intrinsicModeCost.get();
            case EXTRA -> MoreSkillsConfigs.GRAND.nirvana.extraModeCost.get();
            case RESISTANCE -> isNullificationSkill(skill)
               ? (Integer)MoreSkillsConfigs.GRAND.nirvana.nullificationModeCost.get()
               : (Integer)MoreSkillsConfigs.GRAND.nirvana.resistanceModeCost.get();
            case UNIQUE -> MoreSkillsConfigs.GRAND.nirvana.uniqueModeCost.get();
            case ULTIMATE -> MoreSkillsConfigs.GRAND.nirvana.allowUltimateMigration.get()
               ? MoreSkillsConfigs.GRAND.nirvana.ultimateModeCost.get()
               : Integer.MAX_VALUE;
            default -> MoreSkillsConfigs.GRAND.nirvana.uniqueModeCost.get();
         };
      } else {
         return (Integer)MoreSkillsConfigs.GRAND.nirvana.extraModeCost.get();
      }
   }

   private static boolean isNullificationSkill(ManasSkill skill) {
      if (skill != null && skill.getRegistryName() != null) {
         String id = skill.getRegistryName().toString().toLowerCase(Locale.ROOT);
         String name = skill.getName().getString().toLowerCase(Locale.ROOT);
         return id.contains("nullification") || id.contains("nullify") || name.contains("nullification") || name.contains("nullify");
      } else {
         return false;
      }
   }

   private static CompoundTag findArchivedSkill(CompoundTag life, ResourceLocation skillId) {
      ListTag skills = life.getList("Skills", 10);

      for (int i = 0; i < skills.size(); i++) {
         CompoundTag skill = skills.getCompound(i);
         if (skill.getString("Skill").equals(skillId.toString())) {
            return skill;
         }
      }

      return null;
   }

   private static void archiveAndReincarnate(ServerPlayer player) {
      NirvanaHandler.RespawnTarget respawnTarget = captureRespawnTarget(player);
      NirvanaData.ensureCurrentLife(player);
      boolean deathFarmLife = NirvanaData.currentLifeAgeTicks(player) < ((Integer)MoreSkillsConfigs.GRAND.nirvana.minimumKarmaLifeTicks.get()).intValue();
      double ep = EnergyHelper.getMaxEP(player);
      long timeKarma = deathFarmLife ? 0L : getTimeKarma(player);
      long epKarma = deathFarmLife
         ? 0L
         : Math.max(
            ((Integer)MoreSkillsConfigs.GRAND.nirvana.minimumEPKarma.get()).intValue(),
            Math.min(
               ((Integer)MoreSkillsConfigs.GRAND.nirvana.maximumEPKarma.get()).intValue(),
               (long)(ep / ((Integer)MoreSkillsConfigs.GRAND.nirvana.epDivisor.get()).intValue())
            )
         );
      long achievementKarma = deathFarmLife ? 0L : getAchievementKarma(player);
      long bossKarma = deathFarmLife ? 0L : getBossKarma(player);
      CompoundTag life = NirvanaData.createLife(player, ep, timeKarma, epKarma, achievementKarma, bossKarma);
      NirvanaData.addLife(player, life);
      NirvanaData.addKarma(player, life.getLong("KarmaGained"));
      fullCharacterResetKeepingNirvana(player);
      restoreRespawnTarget(player, respawnTarget);
      NirvanaData.beginNewLife(player);
      giveNirvana(player);
      reincarnateRandomlyFromConfig(player);
      ReincarnationMenu.grantLearningResistance(player);
      if (player.getRandom().nextInt(100) < (Integer)MoreSkillsConfigs.GRAND.nirvana.bonusUniqueChance.get()) {
         ReincarnationMenu.randomUniqueSkill(player, true);
      }

      NirvanaData.markCurrentSkillsNoKarma(player);
      ResetScrollItem.resetFlight(player);
      ResetScrollItem.resetWarpPoints(player);
      ResetScrollItem.resetLiquidCapacity(player);
      removeAllActiveEffects(player);
      resetVanillaMovementState(player);
      player.setHealth(player.getMaxHealth());
      syncAttributes(player);
      syncManasCore(player);
   }

   private static void fullCharacterResetKeepingNirvana(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      if (server != null) {
         TensuraStorages.getUniqueStorageFrom(server.overworld()).removeOwner(player.getUUID());
      }

      removeAllActiveEffects(player);
      removeAllSkills(player);
      CookSkill.removeCookedHP(player);
      TensuraStorages.resetPlayerData(player);
      TensuraStorages.resetExistence(player);
      TensuraStorages.resetEffect(player);
      TensuraStorages.resetSpirit(player);
      resetAllStats(player);
      resetPlayerDataExtras(player);
      EnergyHelper.removeSpiritualEPLimit(player);
      RaceAPI.getRaceFrom(player).getRace().ifPresent(raceInstance -> BoneGolemVariant.removeBoneGolemFromRace(player, raceInstance));
      removeRaceAttributeModifiers(player);
      resetAllAttributeBases(player);
      clearRace(player);
      removeRaceAttributeModifiers(player);
      resetAllAttributeBases(player);
      resetVanillaMovementState(player);
      TensuraAdvancements.revokeAllTensuraAdvancements(player);
      syncAttributes(player);
   }

   private static void removeAllSkills(ServerPlayer player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      Iterator<ManasSkillInstance> iterator = skills.getLearnedSkills().iterator();

      while (iterator.hasNext()) {
         ManasSkillInstance instance = iterator.next();
         if (instance == null) {
            iterator.remove();
         } else if (!((RemoveSkillEvent)SkillEvents.REMOVE_SKILL.invoker()).removeSkill(instance, player, Changeable.of(null)).isFalse()) {
            if (instance instanceof TensuraSkillInstance tensuraSkillInstance) {
               tensuraSkillInstance.onForgetSkill(player);
               tensuraSkillInstance.markDirty();
            } else {
               instance.markDirty();
            }

            iterator.remove();
         }
      }

      skills.markDirty();
   }

   private static void resetAllStats(ServerPlayer player) {
      ServerStatsCounter stats = player.getStats();

      for (StatType<?> type : BuiltInRegistries.STAT_TYPE) {
         for (Object value : type.getRegistry()) {
            stats.setValue(player, type.get(value), 0);
         }
      }
   }

   private static void resetPlayerDataExtras(ServerPlayer player) {
      try {
         Class<?> slime = Class.forName("io.github.manasmods.tensura.ability.skill.intrinsic.AbsorbDissolveSkill");
         Method method = slime.getMethod("resetSlimeCoreBoost", Player.class);
         method.invoke(null, player);
      } catch (Throwable var3) {
      }

      ITensuraPlayer playerData = TensuraStorages.getPlayerDataFrom(player);
      playerData.clearSchematics();
      playerData.clearReputation();
      playerData.markDirty();
   }

   private static void resetAllAttributeBases(ServerPlayer player) {
      for (AttributeInstance attribute : copiedAttributeInstances(player)) {
         hardResetAttribute(attribute, ((Attribute)attribute.getAttribute().value()).getDefaultValue());
      }

      resetVanillaPlayerAttributeBases(player);
      resetAllAttributesFromClass(player, TensuraAttributes.class);
      resetMovementSpeedAbsolute(player);
      player.setHealth(Math.min(player.getHealth(), player.getMaxHealth()));
   }

   private static void resetVanillaPlayerAttributeBases(ServerPlayer player) {
      resetAttributeBase(player, Attributes.MAX_HEALTH, 20.0);
      resetAttributeBase(player, Attributes.FOLLOW_RANGE, 32.0);
      resetAttributeBase(player, Attributes.KNOCKBACK_RESISTANCE, 0.0);
      resetAttributeBase(player, Attributes.MOVEMENT_SPEED, 0.1);
      resetAttributeBase(player, Attributes.FLYING_SPEED, 0.4);
      resetAttributeBase(player, Attributes.ATTACK_DAMAGE, 1.0);
      resetAttributeBase(player, Attributes.ATTACK_KNOCKBACK, 0.0);
      resetAttributeBase(player, Attributes.ATTACK_SPEED, 4.0);
      resetAttributeBase(player, Attributes.ARMOR, 0.0);
      resetAttributeBase(player, Attributes.ARMOR_TOUGHNESS, 0.0);
      resetAttributeBase(player, Attributes.LUCK, 0.0);
      resetAttributeBase(player, Attributes.BLOCK_INTERACTION_RANGE, 4.5);
      resetAttributeBase(player, Attributes.ENTITY_INTERACTION_RANGE, 3.0);
      resetAttributeBase(player, Attributes.BLOCK_BREAK_SPEED, 1.0);
      resetAttributeBase(player, Attributes.MINING_EFFICIENCY, 0.0);
      resetAttributeBase(player, Attributes.SNEAKING_SPEED, 0.3);
      resetAttributeBase(player, Attributes.SUBMERGED_MINING_SPEED, 0.2);
      resetAttributeBase(player, Attributes.SWEEPING_DAMAGE_RATIO, 0.0);
      resetAttributeBase(player, Attributes.OXYGEN_BONUS, 0.0);
      resetAttributeBase(player, Attributes.WATER_MOVEMENT_EFFICIENCY, 0.0);
      resetAttributeBase(player, Attributes.MOVEMENT_EFFICIENCY, 0.0);
      resetAttributeBase(player, Attributes.SAFE_FALL_DISTANCE, 3.0);
      resetAttributeBase(player, Attributes.FALL_DAMAGE_MULTIPLIER, 1.0);
      resetAttributeBase(player, Attributes.GRAVITY, 0.08);
      resetAttributeBase(player, Attributes.JUMP_STRENGTH, 0.42);
      resetAttributeBase(player, Attributes.SCALE, 1.0);
      resetAttributeBase(player, Attributes.STEP_HEIGHT, 0.6);
      resetAttributeBase(player, Attributes.BURNING_TIME, 1.0);
      resetAttributeBase(player, Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 0.0);
   }

   private static void resetAttributeBase(ServerPlayer player, Holder<Attribute> holder) {
      try {
         AttributeInstance instance = player.getAttribute(holder);
         if (instance != null) {
            hardResetAttribute(instance, ((Attribute)holder.value()).getDefaultValue());
         }
      } catch (Throwable var3) {
      }
   }

   private static void resetAttributeBase(ServerPlayer player, Holder<Attribute> holder, double value) {
      try {
         AttributeInstance instance = player.getAttribute(holder);
         if (instance != null) {
            hardResetAttribute(instance, value);
         }
      } catch (Throwable var5) {
      }
   }

   private static void hardResetAttribute(AttributeInstance attribute, double value) {
      try {
         attribute.removeModifier(TensuraRace.DEFAULT_RACE_ID);
      } catch (Throwable var7) {
      }

      try {
         attribute.removeModifiers();
      } catch (Throwable var6) {
      }

      try {
         attribute.setBaseValue(value);
      } catch (Throwable var5) {
      }

      try {
         attribute.removeModifier(TensuraRace.DEFAULT_RACE_ID);
      } catch (Throwable var4) {
      }
   }

   private static void resetAllAttributesFromClass(ServerPlayer player, Class<?> type) {
      for (Field field : type.getDeclaredFields()) {
         try {
            field.setAccessible(true);
            if (field.get(null) instanceof Holder<?> holder && holder.value() instanceof Attribute) {
               resetAttributeBase(player, (Holder<Attribute>)holder);
            }
         } catch (Throwable var8) {
         }
      }
   }

   private static void resetMovementSpeedAbsolute(ServerPlayer player) {
      try {
         AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
         if (speed != null) {
            hardResetAttribute(speed, 0.1);
         }
      } catch (Throwable var2) {
      }
   }

   private static void resetVanillaMovementState(ServerPlayer player) {
      player.setSprinting(false);
      player.setShiftKeyDown(false);
      player.fallDistance = 0.0F;
      player.setDeltaMovement(0.0, 0.0, 0.0);
      player.getAbilities().setWalkingSpeed(0.1F);
      player.getAbilities().setFlyingSpeed(0.05F);
      player.onUpdateAbilities();
      player.hurtMarked = true;
   }

   private static void removeAllActiveEffects(ServerPlayer player) {
      for (MobEffectInstance effect : new ArrayList(player.getActiveEffects())) {
         player.removeEffect(effect.getEffect());
      }
   }

   private static List<AttributeInstance> copiedAttributeInstances(ServerPlayer player) {
      List<AttributeInstance> result = new ArrayList<>();

      try {
         for (Field field : AttributeMap.class.getDeclaredFields()) {
            if (Map.class.isAssignableFrom(field.getType())) {
               field.setAccessible(true);
               if (field.get(player.getAttributes()) instanceof Map<?, ?> attributes) {
                  for (Object object : attributes.values()) {
                     if (object instanceof AttributeInstance attribute && !result.contains(attribute)) {
                        result.add(attribute);
                     }
                  }
               }
            }
         }
      } catch (Throwable var11) {
      }

      return result;
   }

   private static void removeRaceAttributeModifiers(ServerPlayer player) {
      for (AttributeInstance attribute : copiedAttributeInstances(player)) {
         try {
            attribute.removeModifier(TensuraRace.DEFAULT_RACE_ID);
         } catch (Throwable var4) {
         }
      }
   }

   private static void clearRace(ServerPlayer player) {
      Races races = RaceAPI.getRaceFrom(player);
      Optional<?> optional = races.getRace();
      if (optional.isPresent()) {
         races.setRace((ManasRace)TensuraRaces.HUMAN.get(), true);
         races.markDirty();
      }
   }

   private static void giveNirvana(ServerPlayer player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      if (skills.getSkill((ManasSkill)AllSkills.NIRVANA.get()).isEmpty()) {
         ManasSkillInstance instance = ((ManasSkill)AllSkills.NIRVANA.get()).createDefaultInstance();
         SkillHelper.learnSkill(player, instance);
         skills.markDirty();
      }
   }

   private static void reincarnateRandomlyFromConfig(ServerPlayer player) {
      ManasRace race = pickRandomReincarnationRace(player);
      if (race != null) {
         ReincarnationMenu.setRace(player, race, true, false);
         RaceAPI.getRaceFrom(player).markDirty();
      }
   }

   private static ManasRace pickRandomReincarnationRace(ServerPlayer player) {
      List<ManasRace> randomRaces = ReincarnationMenu.CONFIG
         .Races
         .randomRaces
         .stream()
         .map(id -> (ManasRace)RaceAPI.getRaceRegistry().get(ResourceLocation.parse(id)))
         .filter(Objects::nonNull)
         .toList();
      if (!randomRaces.isEmpty()) {
         return randomRaces.get(player.getRandom().nextInt(randomRaces.size()));
      }

      List<ManasRace> startingRaces = ReincarnationMenu.CONFIG
         .Races
         .startingRaces
         .stream()
         .map(id -> (ManasRace)RaceAPI.getRaceRegistry().get(ResourceLocation.parse(id)))
         .filter(Objects::nonNull)
         .toList();
      return !startingRaces.isEmpty() ? startingRaces.get(player.getRandom().nextInt(startingRaces.size())) : null;
   }

   private static void teleportToRespawn(ServerPlayer player) {
      try {
         MinecraftServer server = player.getServer();
         if (server == null) {
            return;
         }

         ResourceKey<Level> dimension = player.getRespawnDimension();
         BlockPos respawn = player.getRespawnPosition();
         float yaw = player.getRespawnAngle();
         ServerLevel targetLevel = server.getLevel(dimension);
         if (targetLevel == null) {
            targetLevel = player.serverLevel();
         }

         if (respawn == null) {
            respawn = targetLevel.getSharedSpawnPos();
            yaw = targetLevel.getSharedSpawnAngle();
         }

         invokeTeleport(player, targetLevel, respawn.getX() + 0.5, respawn.getY() + 0.1, respawn.getZ() + 0.5, yaw, player.getXRot());
      } catch (Throwable var6) {
      }
   }

   private static NirvanaHandler.RespawnTarget captureRespawnTarget(ServerPlayer player) {
      BlockPos pos = player.getRespawnPosition();
      return pos == null ? null : new NirvanaHandler.RespawnTarget(player.getRespawnDimension(), pos.immutable(), player.getRespawnAngle());
   }

   private static void restoreRespawnTarget(ServerPlayer player, NirvanaHandler.RespawnTarget target) {
      if (target != null) {
         player.setRespawnPosition(target.dimension(), target.pos(), target.angle(), false, false);
      }
   }

   private static void syncAttributes(ServerPlayer player) {
      try {
         player.connection.send(new ClientboundUpdateAttributesPacket(player.getId(), player.getAttributes().getSyncableAttributes()));
      } catch (Throwable var2) {
      }
   }

   private static void invokeTeleport(ServerPlayer player, ServerLevel level, double x, double y, double z, float yaw, float pitch) {
      try {
         for (Method method : ServerPlayer.class.getMethods()) {
            if (method.getName().equals("teleportTo")) {
               Class<?>[] types = method.getParameterTypes();
               if (types.length == 6 && types[0].isAssignableFrom(ServerLevel.class)) {
                  method.invoke(player, level, x, y, z, yaw, pitch);
                  return;
               }

               if (types.length == 7 && types[0].isAssignableFrom(ServerLevel.class) && Set.class.isAssignableFrom(types[4])) {
                  method.invoke(player, level, x, y, z, Set.of(), yaw, pitch);
                  return;
               }
            }
         }
      } catch (Throwable var16) {
      }

      try {
         player.teleportTo(x, y, z);
      } catch (Throwable var15) {
      }
   }

   private static void syncManasCore(ServerPlayer player) {
      try {
         for (Method method : player.getClass().getMethods()) {
            if (method.getName().equals("manasCore$sync") && method.getParameterCount() == 1) {
               Class<?> parameter = method.getParameterTypes()[0];
               if (parameter.isAssignableFrom(player.getClass()) || parameter.isAssignableFrom(ServerPlayer.class) || parameter.isAssignableFrom(Player.class)) {
                  method.invoke(player, player);
                  return;
               }
            }
         }
      } catch (Throwable var6) {
      }
   }

   private static long getTimeKarma(ServerPlayer player) {
      long ticks = NirvanaData.currentLifeAgeTicks(player);
      long days = Math.max(0L, ticks / 24000L);
      if (days >= 365L) {
         return ((Integer)MoreSkillsConfigs.GRAND.nirvana.timeKarma365Days.get()).intValue();
      } else if (days >= 240L) {
         return ((Integer)MoreSkillsConfigs.GRAND.nirvana.timeKarma240Days.get()).intValue();
      } else if (days >= 120L) {
         return ((Integer)MoreSkillsConfigs.GRAND.nirvana.timeKarma120Days.get()).intValue();
      } else if (days >= 60L) {
         return ((Integer)MoreSkillsConfigs.GRAND.nirvana.timeKarma60Days.get()).intValue();
      } else if (days >= 30L) {
         return ((Integer)MoreSkillsConfigs.GRAND.nirvana.timeKarma30Days.get()).intValue();
      } else if (days >= 14L) {
         return ((Integer)MoreSkillsConfigs.GRAND.nirvana.timeKarma14Days.get()).intValue();
      } else if (days >= 7L) {
         return ((Integer)MoreSkillsConfigs.GRAND.nirvana.timeKarma7Days.get()).intValue();
      } else if (days >= 3L) {
         return ((Integer)MoreSkillsConfigs.GRAND.nirvana.timeKarma3Days.get()).intValue();
      } else {
         return days >= 1L ? ((Integer)MoreSkillsConfigs.GRAND.nirvana.timeKarma1Day.get()).intValue() : 0L;
      }
   }

   private static long getAchievementKarma(ServerPlayer player) {
      long karma = 0L;

      for (ManasSkillInstance instance : SkillAPI.getSkillsFrom(player).getLearnedSkills()) {
         ManasSkill skill = instance.getSkill();
         if (!NirvanaData.isNoKarmaSkill(player, skill) && skill instanceof Skill tensuraSkill) {
            boolean mastered = instance.isMastered(player);
            switch (tensuraSkill.getType()) {
               case COMMON:
                  karma += MoreSkillsConfigs.GRAND.nirvana.commonAcquireKarma.get() + (mastered ? MoreSkillsConfigs.GRAND.nirvana.commonMasterKarma.get() : 0);
                  break;
               case INTRINSIC:
                  karma += MoreSkillsConfigs.GRAND.nirvana.intrinsicAcquireKarma.get()
                     + (mastered ? MoreSkillsConfigs.GRAND.nirvana.intrinsicMasterKarma.get() : 0);
                  break;
               case EXTRA:
                  karma += MoreSkillsConfigs.GRAND.nirvana.extraAcquireKarma.get() + (mastered ? MoreSkillsConfigs.GRAND.nirvana.extraMasterKarma.get() : 0);
                  break;
               case RESISTANCE:
                  karma += (isNullificationSkill(skill)
                        ? (Integer)MoreSkillsConfigs.GRAND.nirvana.nullificationAcquireKarma.get()
                        : (Integer)MoreSkillsConfigs.GRAND.nirvana.resistanceAcquireKarma.get())
                     .intValue();
                  break;
               case UNIQUE:
                  karma += MoreSkillsConfigs.GRAND.nirvana.uniqueAcquireKarma.get() + (mastered ? MoreSkillsConfigs.GRAND.nirvana.uniqueMasterKarma.get() : 0);
                  break;
               case ULTIMATE:
                  karma += MoreSkillsConfigs.GRAND.nirvana.ultimateAcquireKarma.get()
                     + (mastered ? MoreSkillsConfigs.GRAND.nirvana.ultimateMasterKarma.get() : 0);
            }
         }
      }

      return karma;
   }

   private static long getBossKarma(ServerPlayer player) {
      long karma = 0L;
      karma += boss(
         player, ResourceLocation.fromNamespaceAndPath("tensura", "orc_lord"), ((Integer)MoreSkillsConfigs.GRAND.nirvana.orcLordBossKarma.get()).intValue()
      );
      karma += boss(
         player,
         ResourceLocation.fromNamespaceAndPath("tensura", "supermassive_slime"),
         ((Integer)MoreSkillsConfigs.GRAND.nirvana.supermassiveSlimeBossKarma.get()).intValue()
      );
      karma += boss(
         player,
         ResourceLocation.fromNamespaceAndPath("tensura", "greater_spirit"),
         ((Integer)MoreSkillsConfigs.GRAND.nirvana.greaterSpiritBossKarma.get()).intValue()
      );
      karma += boss(
         player,
         ResourceLocation.fromNamespaceAndPath("tensura", "orc_disaster"),
         ((Integer)MoreSkillsConfigs.GRAND.nirvana.orcDisasterBossKarma.get()).intValue()
      );
      karma += boss(
         player,
         ResourceLocation.fromNamespaceAndPath("tensura", "ogre_rimuru"),
         ((Integer)MoreSkillsConfigs.GRAND.nirvana.ogreRimuruBossKarma.get()).intValue()
      );
      karma += boss(
         player, ResourceLocation.fromNamespaceAndPath("tensura", "shizu"), ((Integer)MoreSkillsConfigs.GRAND.nirvana.shizuBossKarma.get()).intValue()
      );
      karma += boss(
         player,
         ResourceLocation.fromNamespaceAndPath("tensura", "elemental_colossus"),
         ((Integer)MoreSkillsConfigs.GRAND.nirvana.elementalColossusBossKarma.get()).intValue()
      );
      karma += boss(player, ResourceLocation.withDefaultNamespace("wither"), ((Integer)MoreSkillsConfigs.GRAND.nirvana.witherBossKarma.get()).intValue());
      karma += boss(player, ResourceLocation.withDefaultNamespace("warden"), ((Integer)MoreSkillsConfigs.GRAND.nirvana.wardenBossKarma.get()).intValue());
      karma += boss(
         player, ResourceLocation.withDefaultNamespace("ender_dragon"), ((Integer)MoreSkillsConfigs.GRAND.nirvana.enderDragonBossKarma.get()).intValue()
      );
      karma += boss(
         player,
         ResourceLocation.fromNamespaceAndPath("tensura", "hinata_sakaguchi"),
         ((Integer)MoreSkillsConfigs.GRAND.nirvana.hinataBossKarma.get()).intValue()
      );
      karma += boss(
         player, ResourceLocation.fromNamespaceAndPath("tensura", "carrion"), ((Integer)MoreSkillsConfigs.GRAND.nirvana.carrionBossKarma.get()).intValue()
      );
      return karma
         + boss(
            player,
            ResourceLocation.fromNamespaceAndPath("tensura", "luminous_valentine"),
            ((Integer)MoreSkillsConfigs.GRAND.nirvana.luminousBossKarma.get()).intValue()
         );
   }

   private static long boss(ServerPlayer player, ResourceLocation id, long amount) {
      EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
      if (type == null) {
         return 0L;
      }

      int killed = player.getStats().getValue(((StatType)TensuraStats.BOSS_KILLED.get()).get(type));
      return killed > 0 ? amount : 0L;
   }

   private record RespawnTarget(ResourceKey<Level> dimension, BlockPos pos, float angle) {
   }
}
