package com.foxablazeultimate.ability.skill.veldora;

import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class VeldoraDomainTickHandler {
   private VeldoraDomainTickHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide()) {
         if (player instanceof ServerPlayer serverPlayer) {
            Skills storage = SkillAPI.getSkillsFrom(serverPlayer);
            storage.getSkill((ManasSkill)FoxAblazeUltimateSkills.VELDORA.get()).ifPresent(instance -> {
               VeldoraSkill skill = (VeldoraSkill)FoxAblazeUltimateSkills.VELDORA.get();
               boolean slashHeld = storage.getHeldSkills().stream().anyMatch(held -> held.getSkill() == skill && VeldoraSkill.isSlashMode(held.getMode()));
               skill.validateSlashState(instance, serverPlayer, slashHeld);
               skill.tickDomain(instance, serverPlayer);
               storage.checkAndMarkDirty(instance);
            });
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         stopDomain(player);
      }
   }

   @SubscribeEvent
   public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         stopDomain(player);
      }
   }

   private static void stopDomain(ServerPlayer player) {
      Skills storage = SkillAPI.getSkillsFrom(player);
      ManasSkillInstance instance = (ManasSkillInstance)storage.getSkill((ManasSkill)FoxAblazeUltimateSkills.VELDORA.get()).orElse(null);
      if (instance != null) {
         ((VeldoraSkill)FoxAblazeUltimateSkills.VELDORA.get()).stopSlashState(instance, player);
         ((VeldoraSkill)FoxAblazeUltimateSkills.VELDORA.get()).stopDomainState(instance, player);
         storage.checkAndMarkDirty(instance);
      }
   }
}
