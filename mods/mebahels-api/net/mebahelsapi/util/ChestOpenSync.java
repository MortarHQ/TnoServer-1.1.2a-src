package net.mebahelsapi.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.mebahelsapi.network.ChestOpenPayload;
import net.minecraft.class_2338;
import net.minecraft.class_3218;

public final class ChestOpenSync {
   public static void sendToAll(class_3218 world, class_2338 pos, boolean open) {
      ChestOpenPayload payload = new ChestOpenPayload(pos, open);
      world.method_18766(p -> true).forEach(player -> ServerPlayNetworking.send(player, payload));
   }
}
