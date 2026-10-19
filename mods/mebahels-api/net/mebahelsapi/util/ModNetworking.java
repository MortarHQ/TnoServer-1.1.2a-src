package net.mebahelsapi.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.mebahelsapi.block.entity.base.BaseChestBlockEntity;
import net.mebahelsapi.network.UpdateChestPayload;
import net.minecraft.class_2338;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public class ModNetworking {
   public static void registerReceivers() {
      ServerPlayNetworking.registerGlobalReceiver(UpdateChestPayload.ID, (payload, context) -> {
         class_3222 player = context.player();
         class_3218 world = player.method_51469();
         class_2338 pos = payload.pos();
         boolean shouldDoSpawnAnimation = payload.shouldDoSpawnAnimation();
         world.method_8503().execute(() -> {
            if (world.method_8321(pos) instanceof BaseChestBlockEntity chest) {
               chest.shouldDoSpawnAnimation = shouldDoSpawnAnimation;
               chest.method_5431();
               chest.sync();
            }
         });
      });
   }
}
