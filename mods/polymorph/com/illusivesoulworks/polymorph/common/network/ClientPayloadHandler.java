package com.illusivesoulworks.polymorph.common.network;

import com.illusivesoulworks.polymorph.common.network.server.SPacketHighlightRecipe;
import com.illusivesoulworks.polymorph.common.network.server.SPacketPlayerRecipeSync;
import com.illusivesoulworks.polymorph.common.network.server.SPacketRecipeHandshake;
import com.illusivesoulworks.polymorph.common.network.server.SPacketRecipesList;
import com.illusivesoulworks.polymorph.common.network.server.SPacketUpdatePreview;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
   private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

   public static ClientPayloadHandler getInstance() {
      return INSTANCE;
   }

   private static void handleData(IPayloadContext context, Runnable handler) {
      context.enqueueWork(handler).exceptionally(e -> {
         context.disconnect(Component.translatable("polymorph.networking.failed", new Object[]{e.getMessage()}));
         return null;
      });
   }

   public void handlePacket(SPacketUpdatePreview packet, IPayloadContext ctx) {
      handleData(ctx, () -> SPacketUpdatePreview.handle(packet));
   }

   public void handlePacket(SPacketHighlightRecipe packet, IPayloadContext ctx) {
      handleData(ctx, () -> SPacketHighlightRecipe.handle(packet));
   }

   public void handlePacket(SPacketPlayerRecipeSync packet, IPayloadContext ctx) {
      handleData(ctx, () -> SPacketPlayerRecipeSync.handle(packet));
   }

   public void handlePacket(SPacketRecipesList packet, IPayloadContext ctx) {
      handleData(ctx, () -> SPacketRecipesList.handle(packet));
   }

   public void handlePacket(SPacketRecipeHandshake packet, IPayloadContext ctx) {
      handleData(ctx, () -> SPacketRecipeHandshake.handle(packet));
   }
}
