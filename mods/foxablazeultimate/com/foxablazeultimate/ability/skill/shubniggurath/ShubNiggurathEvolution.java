package com.foxablazeultimate.ability.skill.shubniggurath;

import com.foxablazeultimate.config.ShubNiggurathConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public final class ShubNiggurathEvolution {
   private ShubNiggurathEvolution() {
   }

   public static boolean canSelect(LivingEntity player) {
      return SkillUtils.hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get())
         ? false
         : (
               hasPermanentSkill(player, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get())
                  || hasPermanentSkill(player, (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get())
            )
            && hasPermanentSkill(player, (ManasSkill)FoxAblazeUltimateSkills.URIEL.get())
            && hasPermanentSkill(player, (ManasSkill)FoxAblazeUltimateSkills.BEELZEBUB.get());
   }

   public static boolean tryEvolve(ServerPlayer player) {
      ShubNiggurathConfig.ShubNiggurathSettings config = ShubNiggurathConfig.get().ShubNiggurath;
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      if (SkillUtils.hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get())) {
         return fail(player, "foxablazeultimate.skill.shub_niggurath.evolution.already_owned");
      }

      ResourceLocation shubId = FoxAblazeUltimateSkills.SHUB_NIGGURATH.getId();
      if (isTrulyUniqueEnabled(player) && isOwnedByOther(player, shubId)) {
         return fail(player, "foxablazeultimate.skill.shub_niggurath.evolution.unique_taken");
      }

      if (!existence.isTrueDemonLord()) {
         return fail(player, "foxablazeultimate.skill.shub_niggurath.evolution.requires_true_demon_lord");
      }

      Optional<ManasSkillInstance> raphael = getPermanentMastered(player, (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get())
         .or(() -> getPermanentMastered(player, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get()));
      if (raphael.isEmpty()) {
         return fail(player, "foxablazeultimate.skill.shub_niggurath.evolution.requires_raphael");
      }

      Optional<ManasSkillInstance> uriel = getPermanentMastered(player, (ManasSkill)FoxAblazeUltimateSkills.URIEL.get());
      if (uriel.isEmpty()) {
         return fail(player, "foxablazeultimate.skill.shub_niggurath.evolution.requires_uriel");
      }

      Optional<ManasSkillInstance> beelzebub = getPermanentMastered(player, (ManasSkill)FoxAblazeUltimateSkills.BEELZEBUB.get());
      if (beelzebub.isEmpty()) {
         return fail(player, "foxablazeultimate.skill.shub_niggurath.evolution.requires_beelzebub");
      }

      long mastered = SkillAPI.getSkillsFrom(player)
         .getLearnedSkills()
         .stream()
         .filter(instance -> instance.getMastery() >= 0.0)
         .filter(instance -> instance.isMastered(player))
         .count();
      if (mastered < Math.max(0, config.requiredMasteredSkillCount)) {
         player.displayClientMessage(
            Component.translatable(
                  "foxablazeultimate.skill.shub_niggurath.evolution.requires_mastered_count",
                  new Object[]{mastered, Math.max(0, config.requiredMasteredSkillCount)}
               )
               .withStyle(ChatFormatting.RED),
            true
         );
         return false;
      }

      if (EnergyHelper.getMaxEP(player) < Math.max(0.0, config.requiredMaxEP)) {
         player.displayClientMessage(
            Component.translatable(
                  "foxablazeultimate.skill.shub_niggurath.evolution.requires_ep",
                  new Object[]{Math.round(EnergyHelper.getMaxEP(player)), Math.round(config.requiredMaxEP)}
               )
               .withStyle(ChatFormatting.RED),
            true
         );
         return false;
      }

      if (existence.getMagicule() < Math.max(0.0, config.requiredMagicule)) {
         player.displayClientMessage(
            Component.translatable(
                  "foxablazeultimate.skill.shub_niggurath.evolution.requires_magicule",
                  new Object[]{Math.round(existence.getMagicule()), Math.round(config.requiredMagicule)}
               )
               .withStyle(ChatFormatting.RED),
            true
         );
         return false;
      }

      TensuraSkillInstance shub = new TensuraSkillInstance((ManasSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get());
      shub.setMastery(Math.max(0, config.acquisitionGrantMastery));
      shub.getOrCreateTag().putBoolean("NoMagiculeCost", true);
      shub.getOrCreateTag().putBoolean("GiftedInstance", true);
      ShubNiggurathWarehouse.archiveMasteredSkills(shub, player);
      shub.markDirty();
      if (!SkillHelper.learnSkill(player, shub)) {
         return fail(player, "foxablazeultimate.skill.shub_niggurath.evolution.failed");
      }

      if (isTrulyUniqueEnabled(player)) {
         claimOwnership(player, shubId);
      }

      existence.setMagicule(Math.max(0.0, existence.getMagicule() - Math.max(0.0, config.requiredMagicule)));
      existence.markDirty();
      player.sendSystemMessage(
         Component.translatable(
               "foxablazeultimate.skill.shub_niggurath.evolution.success",
               new Object[]{((ShubNiggurathSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get()).getColoredName()}
            )
            .withStyle(ChatFormatting.RED)
      );
      return true;
   }

   private static Optional<ManasSkillInstance> getPermanentMastered(ServerPlayer player, ManasSkill skill) {
      return SkillAPI.getSkillsFrom(player).getSkill(skill).filter(instance -> !instance.isTemporarySkill()).filter(instance -> instance.isMastered(player));
   }

   private static boolean hasPermanentSkill(LivingEntity owner, ManasSkill skill) {
      return SkillAPI.getSkillsFrom(owner).getSkill(skill).filter(instance -> !instance.isTemporarySkill()).isPresent();
   }

   private static boolean isOwnedByOther(ServerPlayer player, ResourceLocation skillId) {
      if (skillId == null) {
         return false;
      }

      ServerLevel overworld = overworldOf(player);
      if (overworld == null) {
         return false;
      }

      ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
      UUID owner = unique.getOwner(skillId);
      return owner != null && !owner.equals(player.getUUID());
   }

   private static void claimOwnership(ServerPlayer player, ResourceLocation skillId) {
      if (skillId != null) {
         ServerLevel overworld = overworldOf(player);
         if (overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            UUID owner = unique.getOwner(skillId);
            if (owner == null) {
               unique.addSkill(skillId, player.getUUID());
               unique.markDirty();
            }
         }
      }
   }

   private static boolean isTrulyUniqueEnabled(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(FoxAblazeGameRules.SHUB_NIGGURATH_TRULY_UNIQUE);
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }

   private static boolean fail(ServerPlayer player, String key) {
      player.displayClientMessage(Component.translatable(key).withStyle(ChatFormatting.RED), true);
      return false;
   }
}
