package com.github.wal_bos.moreskills.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderPlayerEvent.Pre;

public final class MorphRenderHandler {
   private static final Map<UUID, Entity> CACHE = new HashMap<>();

   private MorphRenderHandler() {
   }

   public static void onRenderPlayerPre(Pre event) {
      Player player = event.getEntity();
      EntityType<?> type = MorphClientState.getMorphType(player.getUUID());
      if (type != null && type != EntityType.PLAYER) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null) {
            Entity morph = CACHE.get(player.getUUID());
            if (morph == null || morph.getType() != type || morph.level() != minecraft.level) {
               morph = type.create(minecraft.level);
               if (morph == null) {
                  return;
               }

               morph.noPhysics = true;
               morph.setNoGravity(true);
               if (morph instanceof Mob mob) {
                  mob.setNoAi(true);
               }

               CACHE.put(player.getUUID(), morph);
            }

            float partialTick = event.getPartialTick();
            copyPlayerState(player, morph, partialTick);
            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource buffer = event.getMultiBufferSource();
            int packedLight = event.getPackedLight();
            poseStack.pushPose();
            EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();
            float headYaw = Mth.rotLerp(partialTick, player.yHeadRotO, player.yHeadRot);
            dispatcher.render(morph, 0.0, 0.0, 0.0, headYaw, partialTick, poseStack, buffer, packedLight);
            poseStack.popPose();
            event.setCanceled(true);
         }
      } else {
         CACHE.remove(player.getUUID());
      }
   }

   private static void copyPlayerState(Player player, Entity morph, float partialTick) {
      double x = Mth.lerp(partialTick, player.xo, player.getX());
      double y = Mth.lerp(partialTick, player.yo, player.getY());
      double z = Mth.lerp(partialTick, player.zo, player.getZ());
      float headYaw = Mth.rotLerp(partialTick, player.yHeadRotO, player.yHeadRot);
      float pitch = Mth.rotLerp(partialTick, player.xRotO, player.getXRot());
      morph.tickCount = player.tickCount;
      morph.setPos(x, y, z);
      morph.xo = x;
      morph.yo = y;
      morph.zo = z;
      morph.setYRot(headYaw);
      morph.yRotO = headYaw;
      morph.setXRot(pitch);
      morph.xRotO = pitch;
      morph.setYHeadRot(headYaw);
      morph.setOnGround(player.onGround());
      morph.setDeltaMovement(player.getDeltaMovement());
      morph.hurtMarked = false;
      morph.setInvisible(false);
      morph.setPose(player.getPose());
      morph.noPhysics = true;
      morph.setNoGravity(true);
      if (morph instanceof LivingEntity living) {
         living.yHeadRot = headYaw;
         living.yHeadRotO = headYaw;
         living.yBodyRot = headYaw;
         living.yBodyRotO = headYaw;
         living.setYHeadRot(headYaw);
         living.setYRot(headYaw);
         living.yRotO = headYaw;
         living.setXRot(pitch);
         living.xRotO = pitch;
         living.setOnGround(player.onGround());
         living.setHealth(Math.max(1.0F, living.getMaxHealth()));
         living.hurtTime = player.hurtTime;
         living.deathTime = 0;
         living.swinging = player.swinging;
         living.swingTime = player.swingTime;
         living.attackAnim = player.attackAnim;
         living.oAttackAnim = player.attackAnim;
         living.walkAnimation.setSpeed(player.walkAnimation.speed());
         living.walkAnimation.update(player.walkAnimation.position(), 1.0F);
      }

      if (morph instanceof Mob mob) {
         mob.setNoAi(true);
         mob.setTarget(null);
      }
   }
}
