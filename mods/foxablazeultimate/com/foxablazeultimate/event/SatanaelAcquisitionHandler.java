package com.foxablazeultimate.event;

import com.foxablazeultimate.ability.skill.satanael.SatanaelSkill;
import com.foxablazeultimate.config.SatanaelConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import com.foxablazeultimate.world.SatanaelAcquisitionData;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber
public final class SatanaelAcquisitionHandler {
   private SatanaelAcquisitionHandler() {
   }

   @SubscribeEvent
   public static void onNamedEntityDeath(LivingDeathEvent event) {
      LivingEntity dead = event.getEntity();
      if (dead.level() instanceof ServerLevel level) {
         IExistence existence = TensuraStorages.getExistenceFrom(dead);
         UUID namingPlayerId = existence.getPermanentOwner();
         String tensuraName = existence.getName();
         if (namingPlayerId != null && tensuraName != null && !tensuraName.isBlank()) {
            Entity killer = event.getSource().getEntity();
            if (killer != null && !namingPlayerId.equals(killer.getUUID())) {
               SatanaelAcquisitionData acquisitionData = SatanaelAcquisitionData.get(level);
               acquisitionData.clearInactivityReclaimed(namingPlayerId);
               acquisitionData.markWitnessedNamedDeath(namingPlayerId);
               ServerPlayer namingPlayer = level.getServer().getPlayerList().getPlayer(namingPlayerId);
               if (namingPlayer != null) {
                  tryGrantSatanael(namingPlayer);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         int interval = Math.max(1, SatanaelConfig.get().Satanael.acquisitionCheckIntervalTicks);
         if (player.tickCount % interval == 0) {
            tryGrantSatanael(player);
         }
      }
   }

   private static void tryGrantSatanael(ServerPlayer player) {
      if (!SkillUtils.hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.SATANAEL.get())) {
         SatanaelAcquisitionData acquisitionData = SatanaelAcquisitionData.get(player.serverLevel());
         if (!acquisitionData.isInactivityReclaimed(player.getUUID())) {
            if (hasMasteredWrath(player)) {
               if (acquisitionData.hasWitnessedNamedDeath(player.getUUID())) {
                  IExistence existence = TensuraStorages.getExistenceFrom(player);
                  if (existence.getSoulPoints() >= Math.max(0, SatanaelConfig.get().Satanael.requiredSoulPoints)) {
                     ResourceLocation satanaelId = ((SatanaelSkill)FoxAblazeUltimateSkills.SATANAEL.get()).getRegistryName();
                     if (!isTrulyUniqueEnabled(player) || !isOwnedByOther(player, satanaelId)) {
                        TensuraSkillInstance instance = new TensuraSkillInstance((ManasSkill)FoxAblazeUltimateSkills.SATANAEL.get());
                        instance.setMastery(Math.max(0, SatanaelConfig.get().Satanael.acquisitionGrantMastery));
                        CompoundTag tag = instance.getOrCreateTag();
                        tag.putBoolean("NoMagiculeCost", true);
                        tag.putBoolean("GiftedInstance", true);
                        instance.markDirty();
                        if (SkillHelper.learnSkill(player, instance)) {
                           if (isTrulyUniqueEnabled(player)) {
                              claimOwnership(player, satanaelId);
                           }

                           SkillAPI.getSkillsFrom(player).forgetSkill((ManasSkill)UniqueSkills.WRATH.get());
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void markInactivityReclaimed(ServerPlayer player) {
      if (player != null) {
         SatanaelAcquisitionData.get(player.serverLevel()).markInactivityReclaimed(player.getUUID());
      }
   }

   private static boolean hasMasteredWrath(ServerPlayer player) {
      Optional<ManasSkillInstance> wrath = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)UniqueSkills.WRATH.get());
      return wrath.isPresent() && wrath.get().isMastered(player);
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
            if (owner == null || owner.equals(player.getUUID())) {
               unique.addSkill(skillId, player.getUUID());
               unique.markDirty();
            }
         }
      }
   }

   private static boolean isTrulyUniqueEnabled(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(FoxAblazeGameRules.SATANAEL_TRULY_UNIQUE);
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }
}
