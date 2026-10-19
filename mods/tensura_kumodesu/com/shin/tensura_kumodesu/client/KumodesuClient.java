package com.shin.tensura_kumodesu.client;

import com.shin.tensura_kumodesu.client.renderer.KumodesuEntityRenderer;
import com.shin.tensura_kumodesu.registry.skill.KumoIntrinsicSkills;
import dev.architectury.event.events.client.ClientTickEvent;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class KumodesuClient {
   public static void init() {
      KumodesuEntityRenderer.register();
      ClientTickEvent.CLIENT_POST.register(KumodesuClient::onClientTick);
   }

   private static void onClientTick(Minecraft mc) {
      Player player = mc.player;
      if (player != null && !player.isSpectator()) {
         if (SkillUtils.isSkillToggled(player, (ManasSkill)KumoIntrinsicSkills.SPIDERMOVEMENT.get())) {
            AABB box = player.getBoundingBox();
            boolean touchingWall = false;
            int minX = (int)Math.floor(box.minX - 0.01);
            int maxX = (int)Math.floor(box.maxX + 0.01);
            int minY = (int)Math.floor(box.minY);
            int maxY = (int)Math.floor(box.maxY + 0.1);
            int minZ = (int)Math.floor(box.minZ - 0.01);
            int maxZ = (int)Math.floor(box.maxZ + 0.01);

            label75:
            for (int y = minY; y <= maxY; y++) {
               for (int x = minX; x <= maxX; x++) {
                  for (int z = minZ; z <= maxZ; z++) {
                     BlockPos pos = new BlockPos(x, y, z);
                     BlockState state = player.level().getBlockState(pos);
                     if (!state.isAir() && !state.getCollisionShape(player.level(), pos).isEmpty() && !box.intersects(new AABB(pos))) {
                        touchingWall = true;
                        break label75;
                     }
                  }
               }
            }

            if (touchingWall) {
               boolean movingIntoWall = player.getDeltaMovement().x != 0.0 || player.getDeltaMovement().z != 0.0;
               if (movingIntoWall || player.isCrouching()) {
                  AABB boxAbove = player.getBoundingBox();
                  boolean canMoveUp = player.level().noCollision(player, boxAbove);
                  double yMotion = player.getDeltaMovement().y;
                  if (!player.isCrouching() && !mc.options.keyJump.isDown()) {
                     yMotion = canMoveUp ? Math.max(yMotion, 0.2) : 0.0;
                  } else {
                     yMotion = Math.min(yMotion, 0.0);
                  }

                  player.setDeltaMovement(player.getDeltaMovement().x, yMotion, player.getDeltaMovement().z);
                  player.fallDistance = 0.0F;
               }
            }
         }
      }
   }
}
