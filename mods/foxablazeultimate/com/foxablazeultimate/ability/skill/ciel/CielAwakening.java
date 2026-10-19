package com.foxablazeultimate.ability.skill.ciel;

import com.foxablazeultimate.compat.stextras.STExtrasCompat;
import com.foxablazeultimate.config.CielConfig;
import com.foxablazeultimate.config.FoxAblazeUltimateConfig;
import com.foxablazeultimate.event.UltimateSkillProtector;
import com.foxablazeultimate.event.WisdomCrystalSyncHandler;
import com.foxablazeultimate.item.CrystalLockState;
import com.foxablazeultimate.network.OpenRaphaelNamingPayload;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.registry.FoxAblazeUltimateSounds;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public final class CielAwakening {
   private static final String SOUL_BOUND_TAG = "CielSoulBound";
   private static final String NO_MAGICULE_COST_TAG = "NoMagiculeCost";

   private CielAwakening() {
   }

   public static boolean hasAllPrerequisites(ServerPlayer player) {
      CielConfig.CielSettings config = CielConfig.settings();
      return hasRequiredRaphael(player, config.requireMasteredRaphael)
         && (
            !config.requireTrueDemonLord || TensuraStorages.getExistenceFrom(player).isTrueDemonLord() || TensuraStorages.getExistenceFrom(player).isTrueHero()
         )
         && CielState.getIntegratedCharybdisCores(player) >= Math.max(0, config.requiredCharybdisCores)
         && CielState.getRefinedItemCount(player) >= Math.max(0, config.requiredRefinedItemCount);
   }

   public static CielAwakening.RequirementReport getRequirementReport(ServerPlayer player) {
      CielConfig.CielSettings config = CielConfig.settings();
      Optional<ManasSkillInstance> raphael = getPermanentRaphael(player);
      boolean raphaelPresent = raphael.isPresent();
      boolean raphaelMastered = raphael.filter(instance -> instance.isMastered(player)).isPresent();
      boolean raphaelRequirement = raphaelPresent && (!config.requireMasteredRaphael || raphaelMastered);
      boolean trueDemonLordOrHero = TensuraStorages.getExistenceFrom(player).isTrueDemonLord() || TensuraStorages.getExistenceFrom(player).isTrueHero();
      boolean raceRequirement = !config.requireTrueDemonLord || trueDemonLordOrHero;
      int integratedCores = CielState.peekIntegratedCharybdisCores(player);
      int requiredCores = Math.max(0, config.requiredCharybdisCores);
      boolean coresRequirement = integratedCores >= requiredCores;
      int refinedItems = CielState.peekRefinedItemCount(player);
      int requiredRefinedItems = Math.max(0, config.requiredRefinedItemCount);
      boolean refinedRequirement = refinedItems >= requiredRefinedItems;
      double healthPercent = player.getMaxHealth() <= 0.0F ? 100.0 : player.getHealth() * 100.0 / player.getMaxHealth();
      double requiredHealthPercent = Math.max(0.0, config.awakeningHealthPercent);
      boolean healthRequirement = player.isAlive() && healthPercent < requiredHealthPercent;
      int combatTimeoutTicks = Math.max(0, config.combatTimeoutTicks);
      boolean combatRequirement = CielState.peekInCombat(player, combatTimeoutTicks);
      return new CielAwakening.RequirementReport(
         raphaelPresent,
         raphaelMastered,
         config.requireMasteredRaphael,
         raphaelRequirement,
         trueDemonLordOrHero,
         config.requireTrueDemonLord,
         raceRequirement,
         integratedCores,
         requiredCores,
         coresRequirement,
         refinedItems,
         requiredRefinedItems,
         refinedRequirement,
         healthPercent,
         requiredHealthPercent,
         healthRequirement,
         combatTimeoutTicks,
         combatRequirement,
         CielState.peekNamingUnlocked(player),
         CielState.peekAwakened(player)
      );
   }

   public static boolean canSelect(ServerPlayer player) {
      return !CielState.isAwakened(player)
         && CielState.isNamingUnlocked(player)
         && hasAllPrerequisites(player)
         && getPermanentRaphael(player).isPresent()
         && SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get()).isEmpty();
   }

   public static boolean tryUnlockNaming(ServerPlayer player) {
      if (CielState.isAwakened(player) || CielState.isNamingUnlocked(player)) {
         return false;
      }

      if (!player.isAlive()) {
         return false;
      }

      if (!hasAllPrerequisites(player)) {
         return false;
      }

      if (!isAwakeningTriggerMet(player)) {
         return false;
      }

      if (!CielState.unlockNaming(player)) {
         return false;
      }

      player.sendSystemMessage(Component.translatable("foxablazeultimate.skill.ciel.naming_unlocked").withStyle(ChatFormatting.GOLD));
      tryAwaken(player, true);
      return true;
   }

   public static void sendNamingPrompt(ServerPlayer player) {
      if (canSelect(player)) {
         player.nextContainerCounter();
         NetworkManager.sendToPlayer(
            player, new OpenRaphaelNamingPayload(player.containerCounter, "foxablazeultimate.skill.ciel", FoxAblazeUltimateSkills.CIEL.getId())
         );
      }
   }

   public static boolean tryAwaken(ServerPlayer player) {
      return tryAwaken(player, true);
   }

   public static boolean tryAwaken(ServerPlayer player, boolean notifyUniqueFailure) {
      if (!canSelect(player)) {
         return false;
      }

      if (!isAwakeningTriggerMet(player)) {
         return false;
      }

      Optional<ManasSkillInstance> raphaelOptional = getPermanentRaphael(player);
      if (raphaelOptional.isEmpty()) {
         return false;
      }

      ResourceLocation raphaelId = FoxAblazeUltimateSkills.RAPHAEL.getId();
      ResourceLocation cielId = FoxAblazeUltimateSkills.CIEL.getId();
      if (isTrulyUniqueEnabled(player) && isFamilyOwnedByOther(player, raphaelId, cielId)) {
         if (notifyUniqueFailure) {
            player.displayClientMessage(Component.translatable("foxablazeultimate.skill.ciel.awakening.unique_taken").withStyle(ChatFormatting.RED), true);
         }

         return false;
      } else {
         ManasSkillInstance raphael = raphaelOptional.get();
         double originalRaphaelMastery = raphael.getMastery();
         closeOpenContainer(player);
         TensuraSkillInstance ciel = migrateInstance(raphael);
         List<CielAwakening.PresetEntry> presetEntries = captureRaphaelPresets(player);
         CielAwakening.LockSnapshot locks = captureLocks(player, raphaelId);
         if (!UltimateSkillProtector.withCielAcquisition(player, () -> SkillHelper.learnSkill(player, ciel))) {
            return false;
         }

         transferSpatialStorage(player, raphael, ciel);
         replacePresets(player, presetEntries, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get());
         UltimateSkillProtector.withEvolutionRemoval(player, List.of(raphaelId), () -> SkillAPI.getSkillsFrom(player).forgetSkill(raphaelId));
         if (SkillAPI.getSkillsFrom(player).getSkill(raphaelId).isPresent()) {
            transferSpatialStorage(player, ciel, raphael);
            replacePresets(player, presetEntries, (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get());
            UltimateSkillProtector.withEvolutionRemoval(player, List.of(cielId), () -> SkillAPI.getSkillsFrom(player).forgetSkill(cielId));
            if (Double.compare(raphael.getMastery(), originalRaphaelMastery) != 0) {
               raphael.setMastery(originalRaphaelMastery);
               raphael.markDirty();
            }

            if (raphael.isToggled()) {
               raphael.onToggleOn(player);
            }

            return false;
         } else {
            CielState.setAwakened(player, true);
            CielState.setEmergencyTakeover(player, false);
            CielState.saveSkillBackup(player, ciel);
            migrateLocks(player, locks, raphaelId, cielId);
            claimFamilyOwnership(player, raphaelId, cielId);
            if (ciel.isToggled()) {
               ciel.onToggleOn(player);
            }

            SkillAPI.getSkillsFrom(player).markDirty();
            TensuraStorages.getAbilityFrom(player).markDirty();
            sync(player);
            if (FoxAblazeUltimateConfig.playSkillAcquisitionSounds()) {
               SoundEvent voice = (SoundEvent)FoxAblazeUltimateSounds.CIEL_AWAKENING_DECLARATION.get();
               if (voice != null) {
                  player.playNotifySound(voice, SoundSource.VOICE, 1.0F, 1.0F);
               }
            }

            player.sendSystemMessage(Component.translatable("foxablazeultimate.skill.ciel.awakening.success").withStyle(ChatFormatting.GOLD));
            return true;
         }
      }
   }

   private static boolean isAwakeningTriggerMet(ServerPlayer player) {
      CielConfig.CielSettings config = CielConfig.settings();
      double healthPercent = player.getMaxHealth() <= 0.0F ? 100.0 : player.getHealth() * 100.0 / player.getMaxHealth();
      return player.isAlive()
         && healthPercent < Math.max(0.0, config.awakeningHealthPercent)
         && CielState.isInCombat(player, Math.max(0, config.combatTimeoutTicks));
   }

   public static void maintainSoulBoundSkill(ServerPlayer player) {
      if (CielState.isAwakened(player)) {
         if (CrystalLockState.migrateSkillState(player, FoxAblazeUltimateSkills.RAPHAEL.getId(), FoxAblazeUltimateSkills.CIEL.getId())) {
            WisdomCrystalSyncHandler.syncTo(player);
         }

         Optional<ManasSkillInstance> existing = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get());
         if (existing.isEmpty() && !UltimateSkillProtector.isCielResetProtected(player)) {
            CielState.setAwakened(player, false);
         } else {
            if (existing.isPresent()) {
               ManasSkillInstance instance = existing.get();
               if (!instance.isTemporarySkill() && !instance.isSubInstance()) {
                  boolean removedRaphael = removeResidualRaphael(player);
                  if (removedRaphael && instance.isToggled()) {
                     instance.onToggleOn(player);
                  }

                  instance.setRemoveTime(-1);
                  normalizeCielTag(instance);
                  CielState.saveSkillBackup(player, instance);
                  claimFamilyOwnership(player, FoxAblazeUltimateSkills.RAPHAEL.getId(), FoxAblazeUltimateSkills.CIEL.getId());
                  return;
               }

               ResourceLocation cielId = FoxAblazeUltimateSkills.CIEL.getId();
               UltimateSkillProtector.withEvolutionRemoval(player, List.of(cielId), () -> SkillAPI.getSkillsFrom(player).forgetSkill(cielId));
               if (SkillAPI.getSkillsFrom(player).getSkill(cielId).isPresent()) {
                  return;
               }
            }

            ResourceLocation raphaelId = FoxAblazeUltimateSkills.RAPHAEL.getId();
            ResourceLocation cielId = FoxAblazeUltimateSkills.CIEL.getId();
            if (!isTrulyUniqueEnabled(player) || !isFamilyOwnedByOther(player, raphaelId, cielId)) {
               TensuraSkillInstance restored = restoreInstance(player);
               if (UltimateSkillProtector.withCielAcquisition(player, () -> SkillHelper.learnSkill(player, restored))) {
                  removeResidualRaphael(player);
                  if (restored.isToggled()) {
                     restored.onToggleOn(player);
                  }

                  CielState.setEmergencyTakeover(player, false);
                  CielState.saveSkillBackup(player, restored);
                  claimFamilyOwnership(player, raphaelId, cielId);
                  SkillAPI.getSkillsFrom(player).markDirty();
                  sync(player);
               }
            }
         }
      }
   }

   public static boolean restoreAwakenedMarkerFromInstance(ServerPlayer player) {
      Optional<ManasSkillInstance> existing = SkillAPI.getSkillsFrom(player)
         .getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get())
         .filter(instancex -> !instancex.isTemporarySkill())
         .filter(instancex -> !instancex.isSubInstance())
         .filter(CielAwakening::isSoulBoundInstance);
      if (existing.isEmpty()) {
         return false;
      }

      ManasSkillInstance instance = existing.get();
      CielState.setAwakened(player, true);
      instance.setRemoveTime(-1);
      normalizeCielTag(instance);
      CielState.saveSkillBackup(player, instance);
      return true;
   }

   public static boolean isSoulBoundInstance(ManasSkillInstance instance) {
      return instance != null && instance.getTag() != null ? instance.getTag().getBoolean("CielSoulBound") : false;
   }

   private static Optional<ManasSkillInstance> getPermanentRaphael(ServerPlayer player) {
      return SkillAPI.getSkillsFrom(player)
         .getSkill((ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get())
         .filter(instance -> !instance.isTemporarySkill() && !instance.isSubInstance());
   }

   private static boolean removeResidualRaphael(ServerPlayer player) {
      Optional<ManasSkillInstance> residual = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get());
      if (residual.isEmpty()) {
         return false;
      } else {
         ResourceLocation raphaelId = FoxAblazeUltimateSkills.RAPHAEL.getId();
         ResourceLocation cielId = FoxAblazeUltimateSkills.CIEL.getId();
         closeOpenContainer(player);
         Optional<ManasSkillInstance> ciel = SkillAPI.getSkillsFrom(player)
            .getSkill(cielId)
            .filter(instance -> !instance.isTemporarySkill() && !instance.isSubInstance());
         ciel.ifPresent(target -> transferResidualSpatialStorage(player, residual.get(), target));
         List<CielAwakening.PresetEntry> presets = captureRaphaelPresets(player);
         CielAwakening.LockSnapshot locks = captureLocks(player, raphaelId);
         replacePresets(player, presets, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get());
         UltimateSkillProtector.withEvolutionRemoval(player, List.of(raphaelId), () -> SkillAPI.getSkillsFrom(player).forgetSkill(raphaelId));
         if (SkillAPI.getSkillsFrom(player).getSkill(raphaelId).isEmpty()) {
            migrateLocks(player, locks, raphaelId, cielId);
            return true;
         } else {
            return false;
         }
      }
   }

   private static boolean hasRequiredRaphael(ServerPlayer player, boolean requireMastered) {
      return getPermanentRaphael(player).filter(instance -> !requireMastered || instance.isMastered(player)).isPresent();
   }

   private static TensuraSkillInstance migrateInstance(ManasSkillInstance raphael) {
      CompoundTag serialized = raphael.toNBT();
      if (serialized.contains("tag", 10)) {
         serialized.getCompound("tag").remove("SpatialStorage");
      }

      serialized.putString("skill", FoxAblazeUltimateSkills.CIEL.getId().toString());
      TensuraSkillInstance ciel = new TensuraSkillInstance((ManasSkill)FoxAblazeUltimateSkills.CIEL.get());
      ciel.deserialize(serialized);
      normalizeCooldowns(ciel);
      ciel.setRemoveTime(-1);
      normalizeCielTag(ciel);
      ciel.getOrCreateTag().putBoolean("NoMagiculeCost", true);
      ciel.markDirty();
      return ciel;
   }

   private static void closeOpenContainer(ServerPlayer player) {
      if (player.containerMenu != player.inventoryMenu) {
         player.closeContainer();
      }
   }

   private static void transferSpatialStorage(ServerPlayer player, ManasSkillInstance source, ManasSkillInstance target) {
      if (source.getSkill() instanceof ISpatialStorage sourceStorage && target.getSkill() instanceof ISpatialStorage) {
         sourceStorage.moveItemsToSpatialStorage(source, target, player, false);
      } else {
         clearSpatialStorage(source);
      }
   }

   private static void transferResidualSpatialStorage(ServerPlayer player, ManasSkillInstance source, ManasSkillInstance target) {
      if (source.getSkill() instanceof ISpatialStorage sourceStorage && target.getSkill() instanceof ISpatialStorage) {
         CompoundTag sourceTag = source.getOrCreateTag();
         CompoundTag targetTag = target.getOrCreateTag();
         if (sourceTag.getList("SpatialStorage", 10).equals(targetTag.getList("SpatialStorage", 10))) {
            clearSpatialStorage(source);
         } else if (targetTag.getList("SpatialStorage", 10).isEmpty()) {
            sourceStorage.moveItemsToSpatialStorage(source, target, player, false);
         }
      } else {
         clearSpatialStorage(source);
      }
   }

   private static void clearSpatialStorage(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.contains("SpatialStorage", 9)) {
         tag.remove("SpatialStorage");
         instance.markDirty();
      }
   }

   private static TensuraSkillInstance restoreInstance(ServerPlayer player) {
      TensuraSkillInstance restored = new TensuraSkillInstance((ManasSkill)FoxAblazeUltimateSkills.CIEL.get());
      Optional<CompoundTag> backup = CielState.getSkillBackup(player);
      if (backup.isPresent()) {
         restored.deserialize(backup.get());
      } else {
         restored.setMastery(100.0);
      }

      if (restored.getMastery() < 0.0) {
         restored.setMastery(100.0);
      }

      normalizeCooldowns(restored);
      restored.setRemoveTime(-1);
      normalizeCielTag(restored);
      restored.getOrCreateTag().putBoolean("NoMagiculeCost", true);
      restored.markDirty();
      return restored;
   }

   private static void normalizeCielTag(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putBoolean("CielSoulBound", true);
      instance.markDirty();
   }

   private static void normalizeCooldowns(ManasSkillInstance instance) {
      int modes = Math.max(1, instance.getModes());
      List<Integer> cooldowns = new ArrayList<>(instance.getCooldownList());

      while (cooldowns.size() < modes) {
         cooldowns.add(0);
      }

      if (cooldowns.size() > modes) {
         cooldowns = new ArrayList<>(cooldowns.subList(0, modes));
      }

      instance.setCoolDownList(cooldowns);
   }

   private static List<CielAwakening.PresetEntry> captureRaphaelPresets(ServerPlayer player) {
      IAbility ability = TensuraStorages.getAbilityFrom(player);
      List<CielAwakening.PresetEntry> entries = new ArrayList<>();
      ManasSkill raphael = (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get();

      for (int presetIndex = 0; presetIndex < ability.getPresets().size(); presetIndex++) {
         List<AbilitySlot> slots = ability.getAbilitySlots(presetIndex);

         for (int slotIndex = 0; slotIndex < slots.size(); slotIndex++) {
            AbilitySlot slot = slots.get(slotIndex);
            if (slot.getSkill() == raphael) {
               entries.add(new CielAwakening.PresetEntry(presetIndex, slotIndex, slot.getMode()));
            }
         }
      }

      return entries;
   }

   private static void replacePresets(ServerPlayer player, List<CielAwakening.PresetEntry> entries, ManasSkill skill) {
      IAbility ability = TensuraStorages.getAbilityFrom(player);

      for (CielAwakening.PresetEntry entry : entries) {
         ability.setAbilitySlot(entry.preset(), entry.slot(), skill, entry.mode());
      }

      if (!entries.isEmpty()) {
         ability.markDirty();
      }
   }

   private static CielAwakening.LockSnapshot captureLocks(ServerPlayer player, ResourceLocation raphaelId) {
      ITensuraPlayer playerData = TensuraStorages.getPlayerDataFrom(player);
      return new CielAwakening.LockSnapshot(
         playerData != null && playerData.getLockedSkills().contains(raphaelId),
         STExtrasCompat.isRegularSkillLocked(player, raphaelId),
         STExtrasCompat.isAdminSkillLocked(player, raphaelId)
      );
   }

   private static void migrateLocks(ServerPlayer player, CielAwakening.LockSnapshot locks, ResourceLocation raphaelId, ResourceLocation cielId) {
      ITensuraPlayer playerData = TensuraStorages.getPlayerDataFrom(player);
      if (locks.tensura() && playerData != null) {
         playerData.removeLockedSkill(raphaelId);
         if (!playerData.getLockedSkills().contains(cielId)) {
            playerData.addLockedSkill(cielId);
         }

         playerData.markDirty();
      }

      CrystalLockState.migrateSkillState(player, raphaelId, cielId);
      if (locks.stExtrasRegular() || locks.stExtrasAdmin()) {
         STExtrasCompat.removeSkillLocks(player, raphaelId);
      }

      if (locks.stExtrasRegular() && !STExtrasCompat.ensureRegularSkillLocked(player, cielId)) {
         STExtrasCompat.ensureRegularSkillLocked(player, raphaelId);
      }

      if (locks.stExtrasAdmin() && !STExtrasCompat.ensureAdminSkillLocked(player, cielId)) {
         STExtrasCompat.ensureAdminSkillLocked(player, raphaelId);
      }

      STExtrasCompat.syncPlayerData(player);
      WisdomCrystalSyncHandler.syncTo(player);
   }

   private static boolean isFamilyOwnedByOther(ServerPlayer player, ResourceLocation... ids) {
      ServerLevel overworld = overworldOf(player);
      if (overworld == null) {
         return false;
      }

      ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);

      for (ResourceLocation id : ids) {
         if (id != null) {
            UUID owner = unique.getOwner(id);
            if (owner != null && !owner.equals(player.getUUID())) {
               return true;
            }
         }
      }

      return false;
   }

   private static void claimFamilyOwnership(ServerPlayer player, ResourceLocation... ids) {
      if (isTrulyUniqueEnabled(player)) {
         ServerLevel overworld = overworldOf(player);
         if (overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            boolean changed = false;

            for (ResourceLocation id : ids) {
               if (id != null) {
                  UUID owner = unique.getOwner(id);
                  if (owner == null || owner.equals(player.getUUID())) {
                     unique.addSkill(id, player.getUUID());
                     changed = true;
                  }
               }
            }

            if (changed) {
               unique.markDirty();
            }
         }
      }
   }

   private static boolean isTrulyUniqueEnabled(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(FoxAblazeGameRules.RAPHAEL_TRULY_UNIQUE);
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }

   private static void sync(ServerPlayer player) {
      ((StorageHolder)player).manasCore$sync(player);
   }

   private record LockSnapshot(boolean tensura, boolean stExtrasRegular, boolean stExtrasAdmin) {
   }

   private record PresetEntry(int preset, int slot, int mode) {
   }

   public record RequirementReport(
      boolean raphaelPresent,
      boolean raphaelMastered,
      boolean requireMasteredRaphael,
      boolean raphaelRequirement,
      boolean trueDemonLordOrHero,
      boolean requireTrueDemonLord,
      boolean raceRequirement,
      int integratedCores,
      int requiredCores,
      boolean coresRequirement,
      int refinedItems,
      int requiredRefinedItems,
      boolean refinedRequirement,
      double healthPercent,
      double requiredHealthPercent,
      boolean healthRequirement,
      int combatTimeoutTicks,
      boolean combatRequirement,
      boolean namingUnlocked,
      boolean awakened
   ) {
      public boolean baseRequirementsMet() {
         return this.raphaelRequirement && this.raceRequirement && this.coresRequirement && this.refinedRequirement;
      }

      public boolean allRequirementsMet() {
         return this.baseRequirementsMet() && this.healthRequirement && this.combatRequirement;
      }
   }
}
