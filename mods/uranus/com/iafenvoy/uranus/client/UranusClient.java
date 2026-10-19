package com.iafenvoy.uranus.client;

import com.iafenvoy.uranus.animation.IAnimatedEntity;
import com.iafenvoy.uranus.network.AnimationPayload;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class UranusClient {
   public static void process() {
      NetworkManager.registerReceiver(Side.S2C, AnimationPayload.ID, AnimationPayload.CODEC, (payload, ctx) -> {
         Level world = Minecraft.getInstance().level;
         if (world != null && world.getEntity(payload.entityID()) instanceof IAnimatedEntity entity) {
            if (payload.index() == -1) {
               entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
            } else {
               entity.setAnimation(entity.getAnimations()[payload.index()]);
            }

            entity.setAnimationTick(0);
         }
      });
   }
}
