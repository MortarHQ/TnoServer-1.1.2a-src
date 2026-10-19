package com.foxablazeultimate.ability.skill.ciel;

import com.foxablazeultimate.config.CielConfig;
import com.foxablazeultimate.event.UltimateSkillProtector;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class CielAwakeningHandler {
   private CielAwakeningHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         int interval = Math.max(1, CielConfig.settings().acquisitionCheckIntervalTicks);
         if (player.tickCount % interval == 0) {
            if (!CielState.isAwakened(player) && CielAwakening.restoreAwakenedMarkerFromInstance(player)) {
               CielAwakening.maintainSoulBoundSkill(player);
            } else if (CielState.isAwakened(player) || !removeUnawakenedCiel(player)) {
               if (CielState.isAwakened(player)) {
                  CielAwakening.maintainSoulBoundSkill(player);
               } else if (CielState.isNamingUnlocked(player)) {
                  CielAwakening.tryAwaken(player, false);
               } else {
                  CielAwakening.tryUnlockNaming(player);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onDamage(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post event) {
      LivingEntity target = event.getEntity();
      Entity source = event.getSource().getEntity();
      if (source instanceof LivingEntity attacker && source != target && !target.isAlliedTo(attacker) && !attacker.isAlliedTo(target)) {
         if (target instanceof ServerPlayer victim) {
            recordCombatAndCheck(victim);
         }

         if (attacker instanceof ServerPlayer player) {
            recordCombatAndCheck(player);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerClone(Clone event) {
      CielState.copyToClone(event.getOriginal(), event.getEntity());
   }

   @SubscribeEvent
   public static void onPlayerLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (!CielState.isAwakened(player) && CielAwakening.restoreAwakenedMarkerFromInstance(player)) {
            CielAwakening.maintainSoulBoundSkill(player);
         } else if (!CielState.isAwakened(player) && removeUnawakenedCiel(player)) {
            return;
         }

         if (!CielState.isAwakened(player) && CielState.isNamingUnlocked(player)) {
            CielAwakening.tryAwaken(player, false);
         }

         if (CielState.isAwakened(player)) {
            CielAwakening.maintainSoulBoundSkill(player);
         }

         CielEmergencyController.forceStop(player, false);
      }
   }

   @SubscribeEvent
   public static void onPlayerLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         CielEmergencyController.forceStop(player, false);
      }
   }

   @SubscribeEvent
   public static void onPlayerDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         CielEmergencyController.forceStop(player, false);
      }
   }

   private static void recordCombatAndCheck(ServerPlayer player) {
      CielState.recordCombat(player);
      CielAwakening.tryUnlockNaming(player);
   }

   private static boolean removeUnawakenedCiel(ServerPlayer player) {
      ResourceLocation cielId = FoxAblazeUltimateSkills.CIEL.getId();
      if (SkillAPI.getSkillsFrom(player).getSkill(cielId).isEmpty()) {
         return false;
      }

      UltimateSkillProtector.withEvolutionRemoval(player, List.of(cielId), () -> SkillAPI.getSkillsFrom(player).forgetSkill(cielId));
      return true;
   }
}
