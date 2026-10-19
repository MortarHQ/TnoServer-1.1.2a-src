package com.foxablazeultimate.event;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import com.foxablazeultimate.ability.skill.velgrynd.VelgryndSkill;
import com.foxablazeultimate.config.VelgryndConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class VelgryndAcquisitionHandler {
   private VelgryndAcquisitionHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player && player.isAlive() && !player.isSpectator()) {
         VelgryndConfig.VelgryndSettings config = VelgryndConfig.settings();
         if (config.acquisitionEnabled && player.tickCount % Math.max(1, config.acquisitionCheckIntervalTicks) == 0) {
            tryAcquire(player, config);
         }
      }
   }

   public static Optional<ManasSkillInstance> prerequisite(ServerPlayer player, ManasSkill skill, boolean requireMastery) {
      return skill == null
         ? Optional.empty()
         : SkillAPI.getSkillsFrom(player)
            .getSkill(skill)
            .filter(instance -> instance.getMastery() >= 0.0)
            .filter(instance -> !instance.isTemporarySkill() && !instance.isSubInstance())
            .filter(instance -> !requireMastery || instance.isMastered(player));
   }

   private static boolean tryAcquire(ServerPlayer player, VelgryndConfig.VelgryndSettings config) {
      SkillStorage storage = SkillAPI.getSkillsFrom(player);
      VelgryndSkill target = (VelgryndSkill)FoxAblazeUltimateSkills.VELGRYND.get();
      if (!storage.getSkill(target).isPresent() && UltimateSkillProtector.canAcquireUnique(player, target.getRegistryName())) {
         Map<ResourceLocation, ManasSkillInstance> originals = new LinkedHashMap<>();
         if (config.acquisitionRequiredSkills == null) {
            return false;
         }

         for (String value : config.acquisitionRequiredSkills) {
            ResourceLocation id = value == null ? null : ResourceLocation.tryParse(value);
            if (id == null || id.equals(target.getRegistryName())) {
               return false;
            }

            Optional<ManasSkillInstance> instance = prerequisite(player, (ManasSkill)SkillAPI.getSkillRegistry().get(id), config.acquisitionRequiresMastery);
            if (instance.isEmpty()) {
               return false;
            }

            originals.put(id, instance.get());
         }

         AttributeInstance maximum = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
         if (maximum != null && TensuraAttributes.MAX_MAGICULE.value() instanceof RangedAttribute ranged) {
            double var27 = VelgryndSkill.acquisitionMagiculeCost();
            double startingCapacity = maximum.getBaseValue();
            if (Double.isFinite(startingCapacity) && !(startingCapacity - ranged.getMinValue() < var27)) {
               IExistence existence = TensuraStorages.getExistenceFrom(player);
               if (existence != null && Double.isFinite(existence.getMagicule())) {
                  double startingMagicule = existence.getMagicule();
                  Map<ResourceLocation, CompoundTag> backups = new LinkedHashMap<>();
                  List<ResourceLocation> attemptedRemovals = new ArrayList<>();
                  List<VelgryndAcquisitionHandler.PresetEntry> presets = new ArrayList<>();
                  boolean capacityCharged = false;
                  boolean learningStarted = false;

                  try {
                     if (config.acquisitionConsumeRequiredSkills) {
                        if (player.containerMenu != player.inventoryMenu) {
                           player.closeContainer();
                        }

                        originals.forEach((id, instancex) -> backups.put(id, instancex.toNBT().copy()));
                        IAbility ability = TensuraStorages.getAbilityFrom(player);

                        for (int preset = 0; preset < ability.getPresets().size(); preset++) {
                           List<AbilitySlot> slots = ability.getAbilitySlots(preset);

                           for (int slot = 0; slot < slots.size(); slot++) {
                              AbilitySlot entry = slots.get(slot);
                              if (entry.getSkill() != null && originals.containsKey(entry.getSkill().getRegistryName())) {
                                 presets.add(new VelgryndAcquisitionHandler.PresetEntry(preset, slot, entry.getSkill(), entry.getMode()));
                              }
                           }
                        }
                     }

                     TensuraSkillInstance evolved = new TensuraSkillInstance(target);
                     evolved.setMastery(Math.min(evolved.getMaxMastery(), Math.max(0, config.acquisitionGrantMastery)));
                     evolved.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                     evolved.getOrCreateTag().putBoolean("GiftedInstance", true);
                     VelgryndSkill.markConsumedSkills(evolved, backups.keySet());
                     learningStarted = true;
                     if (!SkillHelper.learnSkill(player, evolved, -1, null)) {
                        return false;
                     }

                     UltimateSkillProtector.withEvolutionRemoval(player, backups.keySet(), () -> {
                        for (ResourceLocation id : backups.keySet()) {
                           attemptedRemovals.add(id);
                           storage.forgetSkill(id, null);
                           if (storage.getSkill(id).isPresent()) {
                              break;
                           }
                        }
                     });
                     if (backups.keySet().stream().anyMatch(id -> storage.getSkill(id).isPresent())) {
                        rollback(player, target, backups, attemptedRemovals, presets);
                        return false;
                     }

                     double capacity = maximum.getBaseValue();
                     if (Double.isFinite(capacity) && !(capacity - ranged.getMinValue() < var27)) {
                        maximum.setBaseValue(capacity - var27);
                        capacityCharged = true;
                        double newMaximum = EnergyHelper.getMaxMagicule(player);
                        if (!Double.isFinite(newMaximum)) {
                           throw new IllegalStateException("Invalid new magicule capacity");
                        }

                        existence.setMagicule(Math.max(0.0, Math.min(existence.getMagicule(), newMaximum)));
                        existence.markDirty();
                        sync(player);
                        return true;
                     } else {
                        throw new IllegalStateException("Permanent magicule capacity changed during evolution");
                     }
                  } catch (RuntimeException failure) {
                     FoxAblazeUltimateMod.LOGGER.warn("Velgrynd evolution failed for {}", player.getGameProfile().getName(), failure);
                     if (learningStarted) {
                        rollback(player, target, backups, attemptedRemovals, presets);
                     }

                     if (capacityCharged) {
                        maximum.setBaseValue(startingCapacity);
                        existence.setMagicule(startingMagicule);
                        existence.markDirty();
                        sync(player);
                     }

                     return false;
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
      } else {
         return false;
      }
   }

   private static void rollback(
      ServerPlayer player,
      ManasSkill target,
      Map<ResourceLocation, CompoundTag> backups,
      List<ResourceLocation> attemptedRemovals,
      List<VelgryndAcquisitionHandler.PresetEntry> presets
   ) {
      SkillStorage storage = SkillAPI.getSkillsFrom(player);

      try {
         UltimateSkillProtector.withEvolutionRemoval(player, List.of(target.getRegistryName()), () -> storage.forgetSkill(target.getRegistryName(), null));
      } catch (RuntimeException failure) {
         FoxAblazeUltimateMod.LOGGER.error("Failed to remove incomplete Velgrynd evolution", failure);
      }

      for (ResourceLocation id : attemptedRemovals) {
         try {
            ManasSkillInstance backup = ManasSkillInstance.fromNBT(backups.get(id).copy());
            Optional<ManasSkillInstance> existing = storage.getSkill(id);
            ManasSkillInstance restored;
            if (existing.isPresent()) {
               restored = existing.get();
               restored.deserialize(backup.toNBT());
            } else {
               CompoundTag originalTag = backup.getTag() == null ? null : backup.getTag().copy();
               backup.getOrCreateTag().putBoolean("NoMagiculeCost", true);
               backup.getOrCreateTag().putBoolean("GiftedInstance", true);
               if (!SkillHelper.learnSkill(player, backup, -1, null)) {
                  FoxAblazeUltimateMod.LOGGER.error("Failed to restore {} during Velgrynd rollback", id);
                  continue;
               }

               backup.setTag(originalTag);
               restored = backup;
            }

            if (restored.isToggled()) {
               restored.onToggleOn(player);
            }

            restored.markDirty();
         } catch (RuntimeException failure) {
            FoxAblazeUltimateMod.LOGGER.error("Failed to restore {} during Velgrynd rollback", id, failure);
         }
      }

      IAbility ability = TensuraStorages.getAbilityFrom(player);

      for (VelgryndAcquisitionHandler.PresetEntry preset : presets) {
         if (storage.getSkill(preset.skill()).isPresent()) {
            ability.setAbilitySlot(preset.preset(), preset.slot(), preset.skill(), preset.mode());
         }
      }

      sync(player);
   }

   private static void sync(ServerPlayer player) {
      SkillAPI.getSkillsFrom(player).markDirty();
      TensuraStorages.getAbilityFrom(player).markDirty();
      ((StorageHolder)player).manasCore$sync(player);
   }

   private record PresetEntry(int preset, int slot, ManasSkill skill, int mode) {
   }
}
