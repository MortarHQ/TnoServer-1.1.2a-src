package com.foxablazeultimate.event;

import com.foxablazeultimate.ability.skill.mammon.MammonSkill;
import com.foxablazeultimate.config.MammonConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class MammonAcquisitionHandler {
   private static final String SIX_MODE_LAYOUT_MIGRATED = "FoxAblazeUltimateMammonSixModeLayoutMigrated";

   private MammonAcquisitionHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player && !player.level().isClientSide()) {
         migrateLegacyModeLayout(player);
         MammonConfig.MammonSettings config = MammonConfig.settings();
         int interval = Math.max(1, config.acquisitionCheckIntervalTicks);
         if (player.tickCount % interval == 0) {
            tryAcquire(player, config);
         }
      }
   }

   private static void migrateLegacyModeLayout(ServerPlayer player) {
      if (!player.getPersistentData().getBoolean("FoxAblazeUltimateMammonSixModeLayoutMigrated")) {
         Optional<ManasSkillInstance> mammonInstance = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.MAMMON.get());
         if (!mammonInstance.isEmpty()) {
            ManasSkillInstance instance = mammonInstance.get();
            List<Integer> oldCooldowns = new ArrayList<>(instance.getCooldownList());
            boolean legacySevenModeLayout = oldCooldowns.size() >= 7;
            List<Integer> migratedCooldowns;
            if (legacySevenModeLayout) {
               migratedCooldowns = new ArrayList<>(6);

               for (int mode = 0; mode < 5; mode++) {
                  migratedCooldowns.add(oldCooldowns.get(mode));
               }

               migratedCooldowns.add(oldCooldowns.get(6));
            } else {
               migratedCooldowns = new ArrayList<>(oldCooldowns);

               while (migratedCooldowns.size() < instance.getModes()) {
                  migratedCooldowns.add(0);
               }

               if (migratedCooldowns.size() > instance.getModes()) {
                  migratedCooldowns = new ArrayList<>(migratedCooldowns.subList(0, instance.getModes()));
               }
            }

            boolean changed = !migratedCooldowns.equals(oldCooldowns);
            if (changed) {
               instance.setCoolDownList(migratedCooldowns);
               instance.markDirty();
               SkillAPI.getSkillsFrom(player).markDirty();
            }

            if (legacySevenModeLayout) {
               IAbility ability = TensuraStorages.getAbilityFrom(player);
               ManasSkill mammon = (ManasSkill)FoxAblazeUltimateSkills.MAMMON.get();
               boolean abilityChanged = false;

               for (int preset = 0; preset < ability.getPresets().size(); preset++) {
                  List<AbilitySlot> slots = ability.getAbilitySlots(preset);

                  for (int slotIndex = 0; slotIndex < slots.size(); slotIndex++) {
                     AbilitySlot slot = slots.get(slotIndex);
                     if (slot.getSkill() == mammon) {
                        int migratedMode = slot.getMode() == 5 ? 0 : (slot.getMode() == 6 ? 5 : slot.getMode());
                        if (migratedMode != slot.getMode()) {
                           ability.setAbilitySlot(preset, slotIndex, mammon, migratedMode);
                           abilityChanged = true;
                        }
                     }
                  }
               }

               if (abilityChanged) {
                  ability.markDirty();
                  changed = true;
               }
            }

            player.getPersistentData().putBoolean("FoxAblazeUltimateMammonSixModeLayoutMigrated", true);
            if (changed) {
               ((StorageHolder)player).manasCore$sync(player);
            }
         }
      }
   }

   private static void tryAcquire(ServerPlayer player, MammonConfig.MammonSettings config) {
      if (!SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.MAMMON.get()).isPresent()) {
         Optional<ManasSkillInstance> greed = SkillAPI.getSkillsFrom(player)
            .getSkill((ManasSkill)UniqueSkills.GREED.get())
            .filter(instance -> !instance.isTemporarySkill())
            .filter(instance -> !instance.isSubInstance())
            .filter(instance -> instance.isMastered(player));
         if (!greed.isEmpty()) {
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            if (existence.isTrueDemonLord() || existence.isTrueHero()) {
               AttributeInstance maximumMagicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
               if (maximumMagicule != null) {
                  double minimum = ((RangedAttribute)TensuraAttributes.MAX_MAGICULE.value()).getMinValue();
                  double cost = Math.max(0.0, config.acquisitionMaximumMagiculeCost);
                  if (!(maximumMagicule.getBaseValue() - minimum < cost)) {
                     TensuraSkillInstance mammon = new TensuraSkillInstance((ManasSkill)FoxAblazeUltimateSkills.MAMMON.get());
                     mammon.setMastery(Math.max(0, config.acquisitionGrantMastery));
                     mammon.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                     mammon.getOrCreateTag().putBoolean("GiftedInstance", true);
                     mammon.markDirty();
                     if (SkillHelper.learnSkill(player, mammon)) {
                        maximumMagicule.setBaseValue(Math.max(minimum, maximumMagicule.getBaseValue() - cost));
                        existence.setMagicule(Math.min(existence.getMagicule(), EnergyHelper.getMaxMagicule(player)));
                        existence.markDirty();
                        player.sendSystemMessage(
                           Component.translatable(
                                 "foxablazeultimate.skill.mammon.acquisition.success",
                                 new Object[]{((MammonSkill)FoxAblazeUltimateSkills.MAMMON.get()).getColoredName()}
                              )
                              .withStyle(ChatFormatting.DARK_PURPLE)
                        );
                        player.level()
                           .playSound(
                              null,
                              player.getX(),
                              player.getY(),
                              player.getZ(),
                              (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                              SoundSource.PLAYERS,
                              1.0F,
                              0.8F
                           );
                     }
                  }
               }
            }
         }
      }
   }
}
