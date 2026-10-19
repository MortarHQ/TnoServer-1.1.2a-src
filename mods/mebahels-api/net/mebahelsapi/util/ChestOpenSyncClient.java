package net.mebahelsapi.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.mebahelsapi.block.entity.base.BaseChestBlockEntity;
import net.mebahelsapi.network.ChestOpenPayload;
import net.minecraft.class_2338;
import net.minecraft.class_310;

@Environment(EnvType.CLIENT)
public final class ChestOpenSyncClient {
   private ChestOpenSyncClient() {
   }

   public static void registerClientReceiver() {
      ClientPlayNetworking.registerGlobalReceiver(ChestOpenPayload.ID, (payload, context) -> {
         class_2338 pos = payload.pos();
         boolean open = payload.open();
         class_310 client = context.client();
         client.execute(() -> {
            if (client.field_1687 != null) {
               if (client.field_1687.method_8321(pos) instanceof BaseChestBlockEntity chest) {
                  chest.isOpened = open;
                  chest.hasBeenOpened = true;
                  chest.method_5431();
                  if (open) {
                     chest.playOpenSound();
                  } else {
                     chest.playCloseSound();
                  }
               }
            }
         });
      });
   }
}
