package com.foxablazeultimate.ability.skill.yogsothoth;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import com.foxablazeultimate.config.YogSothothConfig;
import com.foxablazeultimate.event.UltimateSkillProtector;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public final class YogSothothEvolution {
   private static final List<ResourceLocation> PREREQUISITES = List.of(
      YogSothothSkill.INFINITY_PRISON, YogSothothSkill.ABSOLUTE_SEVERANCE, YogSothothSkill.USURPER, YogSothothSkill.TIME_TRAVELER
   );

   private YogSothothEvolution() {
   }

   public static boolean canSelect(LivingEntity owner) {
      Optional<YogSothothSkill> yog = FoxAblazeUltimateSkills.yogSothoth();
      if (owner != null && !yog.isEmpty() && !SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)yog.get()).isPresent()) {
         if (owner instanceof ServerPlayer player && UltimateSkillProtector.canAcquireUnique(player, yog.get().getRegistryName())) {
            for (ResourceLocation id : PREREQUISITES) {
               ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
               if (skill == null || getPermanentMastered(owner, skill).isEmpty()) {
                  return false;
               }
            }

            if (TensuraStorages.getExistenceFrom(owner) == null) {
               return false;
            } else {
               AttributeInstance maximumMagicule = owner.getAttribute(TensuraAttributes.MAX_MAGICULE);
               if (TensuraAttributes.MAX_MAGICULE.value() instanceof RangedAttribute ranged && maximumMagicule != null) {
                  double cost = YogSothothSkill.acquisitionMagiculeCost();
                  double baseCapacity = maximumMagicule.getBaseValue();
                  return Double.isFinite(baseCapacity) && baseCapacity - ranged.getMinValue() >= cost;
               } else {
                  return false;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean tryEvolve(ServerPlayer player) {
      Optional<YogSothothSkill> optionalYog = FoxAblazeUltimateSkills.yogSothoth();
      if (player != null && !optionalYog.isEmpty()) {
         YogSothothSkill yogSkill = optionalYog.get();
         Map<ResourceLocation, ManasSkillInstance> originals = new LinkedHashMap<>();
         Map<ResourceLocation, ManasSkillInstance> backups = new LinkedHashMap<>();
         double cost = YogSothothSkill.acquisitionMagiculeCost();
         IExistence existence = null;
         double startingMagicule = 0.0;
         AttributeInstance maximumMagicule = null;
         double startingMaximumMagicule = 0.0;
         double minimumMaximumMagicule = 0.0;
         List<YogSothothEvolution.PresetEntry> presetEntries = List.of();
         List<ResourceLocation> toggledPrerequisites = List.of();
         TensuraSkillInstance yog = null;
         boolean storageMoved = false;
         boolean costCharged = false;

         try {
            if (SkillAPI.getSkillsFrom(player).getSkill(yogSkill).isPresent()) {
               return false;
            }

            if (!UltimateSkillProtector.canAcquireUnique(player, yogSkill.getRegistryName())) {
               return false;
            }

            for (ResourceLocation id : PREREQUISITES) {
               ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
               Optional<ManasSkillInstance> instance = skill == null ? Optional.empty() : getPermanentMastered(player, skill);
               if (instance.isEmpty()) {
                  return false;
               }

               originals.put(id, instance.get());
               backups.put(id, copyPermanent(instance.get()));
            }

            existence = TensuraStorages.getExistenceFrom(player);
            if (existence != null && Double.isFinite(existence.getMagicule())) {
               startingMagicule = existence.getMagicule();
               maximumMagicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
               if (TensuraAttributes.MAX_MAGICULE.value() instanceof RangedAttribute ranged && maximumMagicule != null) {
                  minimumMaximumMagicule = ranged.getMinValue();
                  startingMaximumMagicule = maximumMagicule.getBaseValue();
                  if (Double.isFinite(startingMaximumMagicule) && !(startingMaximumMagicule - minimumMaximumMagicule < cost)) {
                     closeOpenContainer(player);
                     presetEntries = capturePrerequisitePresets(player);
                     toggledPrerequisites = originals.values()
                        .stream()
                        .filter(ManasSkillInstance::isToggled)
                        .<ResourceLocation>map(ManasSkillInstance::getSkillId)
                        .toList();
                     yog = new TensuraSkillInstance(yogSkill);
                     yog.setMastery(Math.max(0.0, YogSothothConfig.settings().acquisitionGrantMastery));
                     yog.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                     yog.getOrCreateTag().putBoolean("GiftedInstance", true);
                     int parentMode = 0;

                     for (ManasSkillInstance source : originals.values()) {
                        YogSothothDelegateHelper.importPrerequisite(yog, source);

                        for (int childMode = 0; childMode < source.getModes() && parentMode < 8; childMode++) {
                           yog.setCoolDown(source.getCoolDown(childMode), parentMode++);
                        }
                     }

                     yog.markDirty();
                     if (!SkillHelper.learnSkill(player, yog)) {
                        return false;
                     }

                     ManasSkillInstance prison = originals.get(YogSothothSkill.INFINITY_PRISON);
                     if (prison.getSkill() instanceof ISpatialStorage oldStorage) {
                        storageMoved = true;
                        oldStorage.moveItemsToSpatialStorage(prison, yog, player, false);
                        replacePresets(player, presetEntries, yogSkill);
                        UltimateSkillProtector.withEvolutionRemoval(
                           player, PREREQUISITES, () -> PREREQUISITES.forEach(idx -> SkillAPI.getSkillsFrom(player).forgetSkill(idx))
                        );
                        boolean removed = PREREQUISITES.stream().allMatch(idx -> SkillAPI.getSkillsFrom(player).getSkill(idx).isEmpty());
                        if (!removed) {
                           throw new IllegalStateException("one or more prerequisite skills could not be removed");
                        }

                        for (ResourceLocation id : toggledPrerequisites) {
                           ManasSkill childSkill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
                           if (childSkill != null && yog.getSubInstances().get(childSkill) != null) {
                              ((ManasSkillInstance)yog.getSubInstances().get(childSkill)).onToggleOn(player);
                           }
                        }

                        double currentMaximumMagicule = maximumMagicule.getBaseValue();
                        if (Double.isFinite(currentMaximumMagicule) && !(currentMaximumMagicule - minimumMaximumMagicule < cost)) {
                           maximumMagicule.setBaseValue(Math.max(minimumMaximumMagicule, currentMaximumMagicule - cost));
                           costCharged = true;
                           double currentMagicule = existence.getMagicule();
                           if (!Double.isFinite(currentMagicule)) {
                              throw new IllegalStateException("magicule became non-finite during evolution");
                           }

                           double newMaximumMagicule = EnergyHelper.getMaxMagicule(player);
                           if (!Double.isFinite(newMaximumMagicule)) {
                              throw new IllegalStateException("maximum magicule became non-finite during evolution");
                           }

                           currentMagicule = Math.min(currentMagicule, newMaximumMagicule);
                           existence.setMagicule(Math.max(0.0, currentMagicule));
                           existence.markDirty();
                           sync(player);
                           return true;
                        } else {
                           throw new IllegalStateException("maximum magicule capacity changed during evolution");
                        }
                     } else {
                        throw new IllegalStateException("Infinity Prison does not expose spatial storage");
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } catch (Throwable failure) {
            FoxAblazeUltimateMod.LOGGER.warn("Yog-Sothoth evolution failed for {}", player.getGameProfile().getName(), failure);
            if (yog != null) {
               rollback(
                  player,
                  yogSkill,
                  yog,
                  backups,
                  storageMoved,
                  presetEntries,
                  existence,
                  startingMagicule,
                  maximumMagicule,
                  startingMaximumMagicule,
                  costCharged
               );
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private static void rollback(
      ServerPlayer player,
      YogSothothSkill yogSkill,
      ManasSkillInstance yog,
      Map<ResourceLocation, ManasSkillInstance> backups,
      boolean storageWasMoved,
      List<YogSothothEvolution.PresetEntry> presetEntries,
      IExistence existence,
      double startingMagicule,
      AttributeInstance maximumMagicule,
      double startingMaximumMagicule,
      boolean costCharged
   ) {
      List<ResourceLocation> restored = new ArrayList<>();

      for (Entry<ResourceLocation, ManasSkillInstance> entry : backups.entrySet()) {
         try {
            ResourceLocation id = entry.getKey();
            if (SkillAPI.getSkillsFrom(player).getSkill(id).isPresent()) {
               restored.add(id);
            } else {
               ManasSkillInstance backup = entry.getValue();
               if (SkillHelper.learnSkill(player, backup)) {
                  restored.add(id);
               }
            }
         } catch (Throwable failure) {
            FoxAblazeUltimateMod.LOGGER.error("Failed to restore prerequisite during Yog-Sothoth rollback", failure);
         }
      }

      if (storageWasMoved) {
         try {
            SkillAPI.getSkillsFrom(player)
               .getSkill(YogSothothSkill.INFINITY_PRISON)
               .filter(restoredPrison -> restoredPrison.getSkill() instanceof ISpatialStorage)
               .ifPresent(restoredPrison -> yogSkill.moveItemsToSpatialStorage(yog, restoredPrison, player, false));
         } catch (Throwable failure) {
            FoxAblazeUltimateMod.LOGGER.error("Failed to restore Infinity Prison storage during Yog-Sothoth rollback", failure);
         }
      }

      if (restored.size() == PREREQUISITES.size()) {
         try {
            restorePresets(player, presetEntries);
         } catch (Throwable failure) {
            FoxAblazeUltimateMod.LOGGER.error("Failed to restore ability presets during Yog-Sothoth rollback", failure);
         }

         try {
            UltimateSkillProtector.withEvolutionRemoval(player, List.of(yogSkill.getRegistryName()), () -> SkillAPI.getSkillsFrom(player).forgetSkill(yogSkill));
         } catch (Throwable failure) {
            FoxAblazeUltimateMod.LOGGER.error("Failed to remove partially learned Yog-Sothoth during rollback", failure);
         }
      } else {
         FoxAblazeUltimateMod.LOGGER
            .error(
               "Yog-Sothoth rollback incomplete for {}; restored {}/{} prerequisites",
               new Object[]{player.getGameProfile().getName(), restored.size(), PREREQUISITES.size()}
            );
      }

      if (costCharged && maximumMagicule != null && Double.isFinite(startingMaximumMagicule)) {
         try {
            maximumMagicule.setBaseValue(startingMaximumMagicule);
         } catch (Throwable failure) {
            FoxAblazeUltimateMod.LOGGER.error("Failed to restore maximum magicule capacity during Yog-Sothoth rollback", failure);
         }
      }

      if (costCharged && existence != null && Double.isFinite(startingMagicule)) {
         try {
            existence.setMagicule(startingMagicule);
            existence.markDirty();
         } catch (Throwable failure) {
            FoxAblazeUltimateMod.LOGGER.error("Failed to refund magicules during Yog-Sothoth rollback", failure);
         }
      }

      try {
         sync(player);
      } catch (Throwable failure) {
         FoxAblazeUltimateMod.LOGGER.error("Failed to synchronize player storage after Yog-Sothoth rollback", failure);
      }
   }

   private static List<YogSothothEvolution.PresetEntry> capturePrerequisitePresets(ServerPlayer player) {
      IAbility ability = TensuraStorages.getAbilityFrom(player);
      List<YogSothothEvolution.PresetEntry> entries = new ArrayList<>();

      for (int preset = 0; preset < ability.getPresets().size(); preset++) {
         List<AbilitySlot> slots = ability.getAbilitySlots(preset);

         for (int slot = 0; slot < slots.size(); slot++) {
            AbilitySlot entry = slots.get(slot);
            if (entry.getSkill() != null && PREREQUISITES.contains(entry.getSkill().getRegistryName())) {
               entries.add(new YogSothothEvolution.PresetEntry(preset, slot, entry.getSkill(), entry.getMode()));
            }
         }
      }

      return entries;
   }

   private static void replacePresets(ServerPlayer player, List<YogSothothEvolution.PresetEntry> entries, YogSothothSkill yogSkill) {
      if (!entries.isEmpty()) {
         IAbility ability = TensuraStorages.getAbilityFrom(player);

         for (YogSothothEvolution.PresetEntry entry : entries) {
            ability.setAbilitySlot(entry.preset(), entry.slot(), yogSkill, mapParentMode(entry.skill().getRegistryName(), entry.mode()));
         }

         ability.markDirty();
      }
   }

   private static void restorePresets(ServerPlayer player, List<YogSothothEvolution.PresetEntry> entries) {
      if (!entries.isEmpty()) {
         IAbility ability = TensuraStorages.getAbilityFrom(player);

         for (YogSothothEvolution.PresetEntry entry : entries) {
            ability.setAbilitySlot(entry.preset(), entry.slot(), entry.skill(), entry.mode());
         }

         ability.markDirty();
      }
   }

   private static int mapParentMode(ResourceLocation skillId, int childMode) {
      if (YogSothothSkill.INFINITY_PRISON.equals(skillId)) {
         return childMode;
      } else if (YogSothothSkill.ABSOLUTE_SEVERANCE.equals(skillId)) {
         return 2 + childMode;
      } else if (YogSothothSkill.USURPER.equals(skillId)) {
         return 4 + childMode;
      } else {
         return YogSothothSkill.TIME_TRAVELER.equals(skillId) ? 7 : 0;
      }
   }

   private static void closeOpenContainer(ServerPlayer player) {
      if (player.containerMenu != player.inventoryMenu) {
         player.closeContainer();
      }
   }

   private static ManasSkillInstance copyPermanent(ManasSkillInstance source) {
      TensuraSkillInstance copy = new TensuraSkillInstance(source.getSkill());
      copy.deserialize(source.toNBT());
      copy.setRemoveTime(-1);
      if (source.getSkillId().equals(YogSothothSkill.INFINITY_PRISON)) {
         CompoundTag tag = copy.getTag();
         if (tag != null) {
            tag.remove("SpatialStorage");
         }
      }

      return copy;
   }

   private static Optional<ManasSkillInstance> getPermanentMastered(LivingEntity owner, ManasSkill skill) {
      return SkillAPI.getSkillsFrom(owner)
         .getSkill(skill)
         .filter(instance -> instance.getMastery() >= 0.0)
         .filter(instance -> !instance.isTemporarySkill())
         .filter(instance -> !instance.isSubInstance())
         .filter(instance -> instance.isMastered(owner));
   }

   private static void sync(ServerPlayer player) {
      SkillAPI.getSkillsFrom(player).markDirty();
      TensuraStorages.getAbilityFrom(player).markDirty();
      ((StorageHolder)player).manasCore$sync(player);
   }

   private record PresetEntry(int preset, int slot, ManasSkill skill, int mode) {
   }
}
