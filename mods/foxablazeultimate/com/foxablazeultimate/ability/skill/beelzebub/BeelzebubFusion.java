package com.foxablazeultimate.ability.skill.beelzebub;

import com.foxablazeultimate.config.BeelzebubConfig;
import com.foxablazeultimate.config.FoxAblazeUltimateConfig;
import com.foxablazeultimate.event.UltimateSkillProtector;
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

public final class BeelzebubFusion {
   private static final String INACTIVITY_RECLAIMED_TAG = "FoxAblazeUltimateBeelzebubInactivityReclaimed";
   private static final Map<UUID, BeelzebubFusion.FusionCeremony> PENDING = new ConcurrentHashMap<>();

   private BeelzebubFusion() {
   }

   public static void tryFuse(LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         ManasSkill gluttony = (ManasSkill)UniqueSkills.GLUTTONY.get();
         ManasSkill merciless = (ManasSkill)UniqueSkills.MERCILESS.get();
         ManasSkill beelzebub = (ManasSkill)FoxAblazeUltimateSkills.BEELZEBUB.get();
         if (!SkillUtils.hasSkill(entity, beelzebub)) {
            boolean gluttonyMastered = SkillUtils.isSkillMastered(entity, gluttony);
            boolean mercilessMastered = SkillUtils.isSkillMastered(entity, merciless);
            if (entity instanceof ServerPlayer player && player.getPersistentData().getBoolean("FoxAblazeUltimateBeelzebubInactivityReclaimed")) {
               if (gluttonyMastered && mercilessMastered) {
                  return;
               }

               player.getPersistentData().remove("FoxAblazeUltimateBeelzebubInactivityReclaimed");
            }

            if (gluttonyMastered) {
               if (mercilessMastered) {
                  if (isEligibleRace(entity)) {
                     if (!(entity instanceof ServerPlayer player && isTrulyUniqueEnabled(player) && isOwnedByOther(player, beelzebub.getRegistryName()))) {
                        if (entity instanceof ServerPlayer player) {
                           if (!PENDING.containsKey(player.getUUID())) {
                              PENDING.put(player.getUUID(), new BeelzebubFusion.FusionCeremony());
                              MutableComponent declaration = Component.translatable("foxablazeultimate.skill.beelzebub.fusion_declaration");
                              player.sendSystemMessage(declaration);
                              if (FoxAblazeUltimateConfig.playSkillAcquisitionSounds()) {
                                 SoundEvent voice = (SoundEvent)FoxAblazeUltimateSounds.BEELZEBUB_FUSION_DECLARATION.get();
                                 if (voice != null) {
                                    player.playNotifySound(voice, SoundSource.VOICE, 1.0F, 1.0F);
                                 }
                              }
                           }
                        } else {
                           doGrant(entity, beelzebub, gluttony, merciless);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void tickCeremony(ServerPlayer player) {
      BeelzebubFusion.FusionCeremony ceremony = PENDING.get(player.getUUID());
      if (ceremony != null) {
         BeelzebubConfig.BeelzebubSettings c = BeelzebubConfig.get().Beelzebub;
         ceremony.ticksSinceStart++;
         int messageAt = Math.max(0, c.fusionDeclarationDelayTicks);
         if (!ceremony.fusionMessageSent && ceremony.ticksSinceStart >= messageAt) {
            MutableComponent msg = Component.translatable("foxablazeultimate.skill.beelzebub.fusion_message")
               .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE));
            player.sendSystemMessage(msg);
            ceremony.fusionMessageSent = true;
         }

         int grantAt = messageAt + Math.max(0, c.fusionGrantDelayTicks);
         if (ceremony.fusionMessageSent && ceremony.ticksSinceStart >= grantAt) {
            ManasSkill gluttony = (ManasSkill)UniqueSkills.GLUTTONY.get();
            ManasSkill merciless = (ManasSkill)UniqueSkills.MERCILESS.get();
            ManasSkill beelzebub = (ManasSkill)FoxAblazeUltimateSkills.BEELZEBUB.get();
            if (isTrulyUniqueEnabled(player) && isOwnedByOther(player, beelzebub.getRegistryName())) {
               PENDING.remove(player.getUUID());
               return;
            }

            if (doGrant(player, beelzebub, gluttony, merciless)) {
               PENDING.remove(player.getUUID());
            }
         }
      }
   }

   private static boolean doGrant(LivingEntity entity, ManasSkill beelzebub, ManasSkill gluttony, ManasSkill merciless) {
      BeelzebubSkill bSkill = (BeelzebubSkill)beelzebub;
      TensuraSkillInstance instance = new TensuraSkillInstance(bSkill);
      int grantMastery = Math.max(0, Math.min(100, BeelzebubConfig.get().Beelzebub.fusionGrantMastery));
      instance.setMastery(grantMastery);
      CompoundTag tag = instance.getOrCreateTag();
      tag.putBoolean("NoMagiculeCost", true);
      tag.putBoolean("GiftedInstance", true);
      instance.markDirty();
      if (!SkillHelper.learnSkill(entity, instance)) {
         return false;
      }

      if (entity instanceof ServerPlayer player && isTrulyUniqueEnabled(player)) {
         ResourceLocation rid = beelzebub.getRegistryName();
         if (rid != null) {
            ServerLevel overworld = overworldOf(player);
            if (overworld != null) {
               ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
               UUID owner = unique.getOwner(rid);
               if (owner == null || !owner.equals(player.getUUID())) {
                  unique.addSkill(rid, player.getUUID());
                  unique.markDirty();
               }
            }
         }
      }

      SkillAPI.getSkillsFrom(entity).forgetSkill(gluttony);
      SkillAPI.getSkillsFrom(entity).forgetSkill(merciless);
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
         player.getPersistentData().putBoolean("FoxAblazeUltimateBeelzebubInactivityReclaimed", true);
      }
   }

   static void copyInactivityReclaimed(LivingEntity original, LivingEntity clone) {
      if (original.getPersistentData().getBoolean("FoxAblazeUltimateBeelzebubInactivityReclaimed")) {
         clone.getPersistentData().putBoolean("FoxAblazeUltimateBeelzebubInactivityReclaimed", true);
      }
   }

   private static boolean isOwnedByOther(ServerPlayer player, ResourceLocation beelzebubId) {
      return !UltimateSkillProtector.canAcquireUnique(player, beelzebubId);
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }

   static boolean isTrulyUniqueEnabled(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(FoxAblazeGameRules.BEELZEBUB_TRULY_UNIQUE);
   }

   private static boolean isEligibleRace(LivingEntity entity) {
      if (!BeelzebubConfig.get().Beelzebub.fusionRequireSpecialRace) {
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
