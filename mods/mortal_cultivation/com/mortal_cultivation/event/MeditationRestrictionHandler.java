package com.mortal_cultivation.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class MeditationRestrictionHandler {
   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onMovementInput(MovementInputUpdateEvent event) {
      if (CultivationAnimationHandler.isMeditating()) {
         Input input = event.getInput();
         input.up = false;
         input.down = false;
         input.left = false;
         input.right = false;
         input.jumping = false;
         input.shiftKeyDown = false;
         input.forwardImpulse = 0.0F;
         input.leftImpulse = 0.0F;
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onLivingUpdate(Post event) {
      if (event.getEntity() instanceof AbstractClientPlayer) {
         AbstractClientPlayer player = (AbstractClientPlayer)event.getEntity();
         Minecraft mc = Minecraft.getInstance();
         if (player == mc.player) {
            if (CultivationAnimationHandler.isMeditating()) {
               player.setDeltaMovement(0.0, 0.0, 0.0);
               if (player.isNoGravity()) {
                  Vec3 currentPos = player.position();
                  player.setPos(currentPos.x, currentPos.y, currentPos.z);
                  player.setOnGround(false);
                  player.fallDistance = 0.0F;
               }

               player.setSprinting(false);
               player.setShiftKeyDown(false);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onRightClickItem(RightClickItem event) {
      if (event.getEntity() instanceof AbstractClientPlayer) {
         AbstractClientPlayer player = (AbstractClientPlayer)event.getEntity();
         Minecraft mc = Minecraft.getInstance();
         if (player == mc.player) {
            if (CultivationAnimationHandler.isMeditating()) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onRightClickBlock(RightClickBlock event) {
      if (event.getEntity() instanceof AbstractClientPlayer) {
         AbstractClientPlayer player = (AbstractClientPlayer)event.getEntity();
         Minecraft mc = Minecraft.getInstance();
         if (player == mc.player) {
            if (CultivationAnimationHandler.isMeditating()) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onLeftClickBlock(LeftClickBlock event) {
      if (event.getEntity() instanceof AbstractClientPlayer) {
         AbstractClientPlayer player = (AbstractClientPlayer)event.getEntity();
         Minecraft mc = Minecraft.getInstance();
         if (player == mc.player) {
            if (CultivationAnimationHandler.isMeditating()) {
               event.setCanceled(true);
            }
         }
      }
   }
}
