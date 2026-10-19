package com.foxablazeultimate.ability.skill.raphael;

import com.foxablazeultimate.ability.skill.ciel.CielState;
import com.foxablazeultimate.config.FoxAblazeUltimateConfig;
import com.foxablazeultimate.config.RaphaelConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.registry.FoxAblazeUltimateSounds;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public final class RaphaelFusion {
   private static final String INACTIVITY_RECLAIMED_TAG = "FoxAblazeUltimateRaphaelInactivityReclaimed";
   private static final Map<UUID, RaphaelFusion.FusionCeremony> PENDING = new ConcurrentHashMap<>();

   private RaphaelFusion() {
   }

   public static void tryFuse(LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         ManasSkill greatSage = (ManasSkill)UniqueSkills.GREAT_SAGE.get();
         ManasSkill degenerate = (ManasSkill)UniqueSkills.DEGENERATE.get();
         ManasSkill raphael = (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get();
         if (!SkillUtils.hasSkill(entity, raphael)
            && !SkillUtils.hasSkill(entity, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get())
            && !(entity instanceof ServerPlayer player && CielState.isAwakened(player))) {
            boolean greatSageMastered = SkillUtils.isSkillMastered(entity, greatSage);
            boolean degenerateMastered = SkillUtils.isSkillMastered(entity, degenerate);
            if (entity instanceof ServerPlayer player && player.getPersistentData().getBoolean("FoxAblazeUltimateRaphaelInactivityReclaimed")) {
               if (greatSageMastered && degenerateMastered) {
                  return;
               }

               player.getPersistentData().remove("FoxAblazeUltimateRaphaelInactivityReclaimed");
            }

            if (greatSageMastered) {
               if (degenerateMastered) {
                  if (isEligibleRace(entity)) {
                     if (!(entity instanceof ServerPlayer player && isTrulyUniqueEnabled(player) && isOwnedByOther(player, raphael.getRegistryName()))) {
                        if (!(entity instanceof ServerPlayer player)) {
                           doGrant(entity, raphael, greatSage, degenerate);
                        } else if (!PENDING.containsKey(player.getUUID())) {
                           PENDING.put(player.getUUID(), new RaphaelFusion.FusionCeremony());
                           MutableComponent declaration = Component.translatable("foxablazeultimate.skill.raphael.fusion_declaration");
                           player.sendSystemMessage(declaration);
                           if (FoxAblazeUltimateConfig.playSkillAcquisitionSounds()) {
                              SoundEvent voice = (SoundEvent)FoxAblazeUltimateSounds.RAPHAEL_FUSION_DECLARATION.get();
                              if (voice != null) {
                                 player.playNotifySound(voice, SoundSource.VOICE, 1.0F, 1.0F);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void tickCeremony(ServerPlayer player) {
      RaphaelFusion.FusionCeremony ceremony = PENDING.get(player.getUUID());
      if (ceremony != null) {
         RaphaelConfig.RaphaelSettings c = RaphaelConfig.get().Raphael;
         ceremony.ticksSinceStart++;
         int messageAt = Math.max(0, c.fusionDeclarationDelayTicks);
         if (!ceremony.fusionMessageSent && ceremony.ticksSinceStart >= messageAt) {
            MutableComponent msg = Component.translatable("foxablazeultimate.skill.raphael.fusion_message").setStyle(Style.EMPTY.withColor(ChatFormatting.RED));
            player.sendSystemMessage(msg);
            ceremony.fusionMessageSent = true;
         }

         int grantAt = messageAt + Math.max(0, c.fusionGrantDelayTicks);
         if (ceremony.fusionMessageSent && ceremony.ticksSinceStart >= grantAt) {
            ManasSkill greatSage = (ManasSkill)UniqueSkills.GREAT_SAGE.get();
            ManasSkill degenerate = (ManasSkill)UniqueSkills.DEGENERATE.get();
            ManasSkill raphael = (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get();
            if (CielState.isAwakened(player) || SkillUtils.hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get())) {
               PENDING.remove(player.getUUID());
               return;
            }

            if (isTrulyUniqueEnabled(player) && isOwnedByOther(player, raphael.getRegistryName())) {
               PENDING.remove(player.getUUID());
               return;
            }

            if (doGrant(player, raphael, greatSage, degenerate)) {
               PENDING.remove(player.getUUID());
            }
         }
      }
   }

   private static boolean doGrant(LivingEntity entity, ManasSkill raphael, ManasSkill greatSage, ManasSkill degenerate) {
      RaphaelSkill raphaelSkill = (RaphaelSkill)raphael;
      TensuraSkillInstance instance = new TensuraSkillInstance(raphaelSkill);
      int grantMastery = Math.max(0, Math.min(100, RaphaelConfig.get().Raphael.fusionGrantMastery));
      instance.setMastery(grantMastery);
      CompoundTag tag = instance.getOrCreateTag();
      tag.putBoolean("NoMagiculeCost", true);
      tag.putBoolean("GiftedInstance", true);
      instance.markDirty();
      if (!SkillHelper.learnSkill(entity, instance)) {
         return false;
      }

      SkillAPI.getSkillsFrom(entity).forgetSkill(greatSage);
      SkillAPI.getSkillsFrom(entity).forgetSkill(degenerate);
      return true;
   }

   public static void clearCeremony(UUID playerId) {
      if (playerId != null) {
         PENDING.remove(playerId);
      }
   }

   public static void markInactivityReclaimed(ServerPlayer player) {
      if (player != null) {
         clearCeremony(player.getUUID());
         player.getPersistentData().putBoolean("FoxAblazeUltimateRaphaelInactivityReclaimed", true);
      }
   }

   static void copyInactivityReclaimed(LivingEntity original, LivingEntity clone) {
      if (original.getPersistentData().getBoolean("FoxAblazeUltimateRaphaelInactivityReclaimed")) {
         clone.getPersistentData().putBoolean("FoxAblazeUltimateRaphaelInactivityReclaimed", true);
      }
   }

   private static boolean isOwnedByOther(ServerPlayer player, ResourceLocation raphaelId) {
      ServerLevel overworld = overworldOf(player);
      if (overworld == null) {
         return false;
      }

      ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
      return isOwnedByOther(unique, player, raphaelId) || isOwnedByOther(unique, player, FoxAblazeUltimateSkills.CIEL.getId());
   }

   private static boolean isOwnedByOther(ITrulyUnique unique, ServerPlayer player, ResourceLocation skillId) {
      if (skillId != null && unique.hasSkill(skillId)) {
         UUID owner = unique.getOwner(skillId);
         return owner != null && !owner.equals(player.getUUID());
      } else {
         return false;
      }
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }

   static boolean isTrulyUniqueEnabled(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(FoxAblazeGameRules.RAPHAEL_TRULY_UNIQUE);
   }

   private static boolean isEligibleRace(LivingEntity entity) {
      if (!RaphaelConfig.get().Raphael.fusionRequireSpecialRace) {
         return true;
      }

      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      return existence == null ? false : existence.isHeroEgg() || existence.isTrueHero() || existence.isDemonLordSeed() || existence.isTrueDemonLord();
   }

   private static final class FusionCeremony {
      int ticksSinceStart = 0;
      boolean fusionMessageSent = false;
   }
}
