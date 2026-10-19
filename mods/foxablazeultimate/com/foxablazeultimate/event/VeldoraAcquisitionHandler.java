package com.foxablazeultimate.event;

import com.foxablazeultimate.ability.skill.veldora.VeldoraSkill;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import java.util.UUID;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber
public class VeldoraAcquisitionHandler {
   private static final String DRAGON_KILLED_TAG = "FoxAblazeUltimateVeldoraDragonKilled";
   private static final String INACTIVITY_RECLAIMED_TAG = "FoxAblazeUltimateVeldoraInactivityReclaimed";
   private static final ResourceLocation END_DRAGON_ADVANCEMENT = ResourceLocation.fromNamespaceAndPath("minecraft", "end/kill_dragon");
   private static final double REQUIRED_EP = 400000.0;
   private static final double REQUIRED_MAGICULE = 400000.0;

   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof EnderDragon) {
         if (event.getSource().getEntity() instanceof ServerPlayer player) {
            player.getPersistentData().remove("FoxAblazeUltimateVeldoraInactivityReclaimed");
            markDragonKilled(player);
            tryGrantVeldora(player);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player entity = event.getEntity();
      if (!entity.level().isClientSide()) {
         if (entity instanceof ServerPlayer player) {
            if (player.tickCount % 20 == 0) {
               tryGrantVeldora(player);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerClone(Clone event) {
      if (event.getOriginal().getPersistentData().getBoolean("FoxAblazeUltimateVeldoraDragonKilled")) {
         event.getEntity().getPersistentData().putBoolean("FoxAblazeUltimateVeldoraDragonKilled", true);
      }

      if (event.getOriginal().getPersistentData().getBoolean("FoxAblazeUltimateVeldoraInactivityReclaimed")) {
         event.getEntity().getPersistentData().putBoolean("FoxAblazeUltimateVeldoraInactivityReclaimed", true);
      }
   }

   private static void tryGrantVeldora(ServerPlayer player) {
      if (!SkillUtils.hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.VELDORA.get())) {
         if (hasKilledDragon(player)) {
            ResourceLocation veldoraId = ((VeldoraSkill)FoxAblazeUltimateSkills.VELDORA.get()).getRegistryName();
            if (!isTrulyUniqueEnabled(player) || !isOwnedByOther(player, veldoraId)) {
               IExistence existence = TensuraStorages.getExistenceFrom(player);
               if (!(existence.getEP() < 400000.0)) {
                  if (!(existence.getMagicule() < 400000.0)) {
                     if (hasMasteredWindSkill(player)) {
                        TensuraSkillInstance instance = new TensuraSkillInstance((ManasSkill)FoxAblazeUltimateSkills.VELDORA.get());
                        instance.setMastery(0.0);
                        instance.markDirty();
                        SkillHelper.learnSkill(player, instance);
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean hasMasteredWindSkill(ServerPlayer player) {
      return SkillAPI.getSkillsFrom(player).getLearnedSkills().stream().anyMatch(skill -> {
         if (!skill.isMastered(player)) {
            return false;
         }

         ResourceLocation id = skill.getSkill().getRegistryName();
         if (id == null) {
            return false;
         }

         String path = id.getPath();
         return path.contains("wind") && (path.contains("manipulation") || path.contains("domination"));
      });
   }

   private static boolean hasKilledDragon(ServerPlayer player) {
      if (player.getPersistentData().getBoolean("FoxAblazeUltimateVeldoraInactivityReclaimed")) {
         return false;
      }

      if (player.getPersistentData().getBoolean("FoxAblazeUltimateVeldoraDragonKilled")) {
         return true;
      }

      if (!hasEndDragonAdvancement(player)) {
         return false;
      }

      markDragonKilled(player);
      return true;
   }

   private static boolean hasEndDragonAdvancement(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      if (server == null) {
         return false;
      }

      AdvancementHolder advancement = server.getAdvancements().get(END_DRAGON_ADVANCEMENT);
      return advancement != null && player.getAdvancements().getOrStartProgress(advancement).isDone();
   }

   private static void markDragonKilled(ServerPlayer player) {
      player.getPersistentData().putBoolean("FoxAblazeUltimateVeldoraDragonKilled", true);
   }

   public static void markInactivityReclaimed(ServerPlayer player) {
      player.getPersistentData().remove("FoxAblazeUltimateVeldoraDragonKilled");
      player.getPersistentData().putBoolean("FoxAblazeUltimateVeldoraInactivityReclaimed", true);
   }

   private static boolean isOwnedByOther(ServerPlayer player, ResourceLocation veldoraId) {
      if (veldoraId == null) {
         return false;
      }

      ServerLevel overworld = overworldOf(player);
      if (overworld == null) {
         return false;
      }

      ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
      if (!unique.hasSkill(veldoraId)) {
         return false;
      }

      UUID owner = unique.getOwner(veldoraId);
      return owner != null && !owner.equals(player.getUUID());
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }

   private static boolean isTrulyUniqueEnabled(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(FoxAblazeGameRules.VELDORA_TRULY_UNIQUE);
   }
}
