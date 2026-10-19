package com.foxablazeultimate.network;

import com.foxablazeultimate.menu.BeelzebubStorageMenu;
import dev.architectury.networking.NetworkManager.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record RequestBeelzebubFluidExtractPayload(int containerId, byte fluidType) implements CustomPacketPayload {
   public static final byte FLUID_WATER = 0;
   public static final byte FLUID_LAVA = 1;
   public static final Type<RequestBeelzebubFluidExtractPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "request_beelzebub_fluid_extract")
   );
   public static final StreamCodec<FriendlyByteBuf, RequestBeelzebubFluidExtractPayload> STREAM_CODEC = CustomPacketPayload.codec(
      RequestBeelzebubFluidExtractPayload::encode, RequestBeelzebubFluidExtractPayload::new
   );

   public RequestBeelzebubFluidExtractPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readByte());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.containerId);
      buf.writeByte(this.fluidType);
   }

   public static void handle(RequestBeelzebubFluidExtractPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         if (payload.fluidType == 0 || payload.fluidType == 1) {
            context.queue(() -> {
               if (player.containerMenu instanceof BeelzebubStorageMenu menu) {
                  if (menu.containerId == payload.containerId) {
                     menu.extractFluid(player, payload.fluidType);
                  }
               }
            });
         }
      }
   }

   public Type<RequestBeelzebubFluidExtractPayload> type() {
      return TYPE;
   }
}
