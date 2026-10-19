package com.foxablazeultimate.client;

import com.foxablazeultimate.ability.skill.veldora.VeldoraSkill;
import com.foxablazeultimate.ability.skill.veldora.VeldoraSlashViewLock;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent.Pre;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate", value = Dist.CLIENT)
public final class VeldoraSlashViewLockClient {
   private VeldoraSlashViewLockClient() {
   }

   @SubscribeEvent
   public static void onMovementInput(MovementInputUpdateEvent event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (event.getEntity() instanceof LocalPlayer player && player == minecraft.player) {
         if (getLockedTarget(player) != null) {
            Input input = event.getInput();
            input.leftImpulse = 0.0F;
            input.forwardImpulse = 0.0F;
            input.up = false;
            input.down = false;
            input.left = false;
            input.right = false;
            input.jumping = false;
            input.shiftKeyDown = false;
            player.setSprinting(false);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (event.getEntity() instanceof LocalPlayer player && player == minecraft.player) {
         Vec3 target = getLockedTarget(player);
         if (target != null) {
            VeldoraSlashViewLock.apply(player, target);
         }
      }
   }

   @SubscribeEvent
   public static void beforeRenderFrame(Pre event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         Vec3 target = getLockedTarget(player);
         if (target != null) {
            VeldoraSlashViewLock.applyAim(player, target);
         }
      }
   }

   private static Vec3 getLockedTarget(LocalPlayer player) {
      if (!player.isAlive()) {
         return null;
      }

      ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.VELDORA.get()).orElse(null);
      return instance != null && VeldoraSkill.isSlashAnimationActive(instance) ? VeldoraSkill.getSlashLockedTarget(instance) : null;
   }
}
