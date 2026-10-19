package com.foxablazeultimate.event;

import com.foxablazeultimate.ability.skill.fortuna.FortunaSkill;
import com.foxablazeultimate.config.FortunaConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class FortunaAcquisitionHandler {
   private static final String LEGACY_MODE_MIGRATED = "FoxAblazeUltimateFortunaLegacyModeMigrated";

   private FortunaAcquisitionHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player && !player.level().isClientSide()) {
         FortunaConfig config = FortunaConfig.get();
         if (config != null) {
            SkillAPI.getSkillsFrom(player)
               .getSkill((ManasSkill)FoxAblazeUltimateSkills.FORTUNA.get())
               .filter(instance -> instance.getSkill() instanceof FortunaSkill)
               .ifPresent(instance -> ((FortunaSkill)instance.getSkill()).tickServerMaintenance(instance, player));
            migrateLegacyModeAndState(player);
            int interval = Math.max(1, config.Fortuna.acquisitionCheckIntervalTicks);
            if (player.tickCount % interval == 0) {
               tryEvolve(player);
            }
         }
      }
   }

   private static void migrateLegacyModeAndState(ServerPlayer player) {
      if (!player.getPersistentData().getBoolean("FoxAblazeUltimateFortunaLegacyModeMigrated")) {
         SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.FORTUNA.get()).ifPresent(instance -> {
            CompoundTag tag = instance.getTag();
            if (tag != null) {
               tag.remove("FortunaAnalysisActive");
               tag.remove("fortuna_analysis");
               instance.markDirty();
            }

            List<Integer> cooldowns = new ArrayList<>(instance.getCooldownList());

            while (cooldowns.size() < instance.getModes()) {
               cooldowns.add(0);
            }

            if (cooldowns.size() > instance.getModes()) {
               cooldowns = new ArrayList<>(cooldowns.subList(0, instance.getModes()));
            }

            if (!cooldowns.equals(instance.getCooldownList())) {
               instance.setCoolDownList(cooldowns);
               instance.markDirty();
            }
         });
         IAbility ability = TensuraStorages.getAbilityFrom(player);
         ManasSkill fortuna = (ManasSkill)FoxAblazeUltimateSkills.FORTUNA.get();
         boolean changed = false;

         for (int preset = 0; preset < ability.getPresets().size(); preset++) {
            List<AbilitySlot> slots = ability.getAbilitySlots(preset);

            for (int slotIndex = 0; slotIndex < slots.size(); slotIndex++) {
               AbilitySlot slot = slots.get(slotIndex);
               if (slot.getSkill() == fortuna) {
                  int migratedMode = slot.getMode() == 3 ? 2 : (slot.getMode() == 2 ? 0 : slot.getMode());
                  if (migratedMode != slot.getMode()) {
                     ability.setAbilitySlot(preset, slotIndex, fortuna, migratedMode);
                     changed = true;
                  }
               }
            }
         }

         if (changed) {
            ability.markDirty();
         }

         player.getPersistentData().putBoolean("FoxAblazeUltimateFortunaLegacyModeMigrated", true);
      }
   }

   private static void tryEvolve(ServerPlayer player) {
      if (!SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.FORTUNA.get()).isPresent()) {
         ManasSkill mathematician = (ManasSkill)UniqueSkills.MATHEMATICIAN.get();
         ManasSkill usurper = (ManasSkill)UniqueSkills.USURPER.get();
         Optional<ManasSkillInstance> mathematicianInstance = getPermanentMastered(player, mathematician);
         Optional<ManasSkillInstance> usurperInstance = getPermanentMastered(player, usurper);
         if (!mathematicianInstance.isEmpty() && !usurperInstance.isEmpty()) {
            ManasSkillInstance mathematicianBackup = mathematicianInstance.get().copy();
            ManasSkillInstance usurperBackup = usurperInstance.get().copy();
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            if (existence.isTrueHero() || existence.isTrueDemonLord()) {
               FortunaConfig.FortunaSettings settings = FortunaConfig.get().Fortuna;
               double magiculeCost = Math.max(0.0, settings.acquisitionMagiculeCost);
               if (!(existence.getMagicule() < magiculeCost)) {
                  TensuraSkillInstance fortuna = new TensuraSkillInstance((ManasSkill)FoxAblazeUltimateSkills.FORTUNA.get());
                  fortuna.setMastery(Math.max(0, settings.acquisitionGrantMastery));
                  fortuna.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                  fortuna.getOrCreateTag().putBoolean("GiftedInstance", true);
                  fortuna.markDirty();
                  boolean learned = SkillHelper.learnSkill(player, fortuna);
                  if (learned) {
                     UltimateSkillProtector.withEvolutionRemoval(player, List.of(mathematician.getRegistryName(), usurper.getRegistryName()), () -> {
                        SkillAPI.getSkillsFrom(player).forgetSkill(mathematician);
                        SkillAPI.getSkillsFrom(player).forgetSkill(usurper);
                     });
                     boolean prerequisitesRemoved = SkillAPI.getSkillsFrom(player).getSkill(mathematician).isEmpty()
                        && SkillAPI.getSkillsFrom(player).getSkill(usurper).isEmpty();
                     if (!prerequisitesRemoved) {
                        rollbackEvolution(player, mathematicianBackup, usurperBackup, mathematician.getRegistryName(), usurper.getRegistryName());
                     } else {
                        existence.setMagicule(Math.max(0.0, existence.getMagicule() - magiculeCost));
                        existence.markDirty();
                        player.sendSystemMessage(
                           Component.translatable(
                                 "foxablazeultimate.skill.fortuna.acquisition.success",
                                 new Object[]{((FortunaSkill)FoxAblazeUltimateSkills.FORTUNA.get()).getColoredName()}
                              )
                              .withStyle(ChatFormatting.RED)
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
                              1.0F
                           );
                     }
                  }
               }
            }
         }
      }
   }

   private static void rollbackEvolution(
      ServerPlayer player,
      ManasSkillInstance mathematicianBackup,
      ManasSkillInstance usurperBackup,
      ResourceLocation mathematicianId,
      ResourceLocation usurperId
   ) {
      UltimateSkillProtector.withEvolutionRemoval(
         player,
         List.of(FoxAblazeUltimateSkills.FORTUNA.getId()),
         () -> SkillAPI.getSkillsFrom(player).forgetSkill((ManasSkill)FoxAblazeUltimateSkills.FORTUNA.get())
      );
      if (SkillAPI.getSkillsFrom(player).getSkill(mathematicianId).isEmpty()) {
         SkillHelper.learnSkill(player, mathematicianBackup);
      }

      if (SkillAPI.getSkillsFrom(player).getSkill(usurperId).isEmpty()) {
         SkillHelper.learnSkill(player, usurperBackup);
      }
   }

   private static Optional<ManasSkillInstance> getPermanentMastered(ServerPlayer player, ManasSkill skill) {
      return SkillAPI.getSkillsFrom(player)
         .getSkill(skill)
         .filter(instance -> !instance.isTemporarySkill())
         .filter(instance -> !instance.isSubInstance())
         .filter(instance -> instance.isMastered(player));
   }
}
