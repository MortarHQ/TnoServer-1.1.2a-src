package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.registry.TenaddexItems;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record ArthurAfkRequestPayload(boolean active) implements CustomPacketPayload {
   public static final Type<ArthurAfkRequestPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "arthur_afk_request"));
   public static final StreamCodec<FriendlyByteBuf, ArthurAfkRequestPayload> STREAM_CODEC = CustomPacketPayload.codec(
      ArthurAfkRequestPayload::encode, ArthurAfkRequestPayload::new
   );

   public ArthurAfkRequestPayload(FriendlyByteBuf buf) {
      this(buf.readBoolean());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeBoolean(this.active);
   }

   public static void send(boolean active) {
      NetworkManager.sendToServer(new ArthurAfkRequestPayload(active));
   }

   public static void handle(ArthurAfkRequestPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(
            () -> {
               if (context.getPlayer() instanceof ServerPlayer player) {
                  boolean start = payload.active()
                     && player.isAlive()
                     && !player.isSpectator()
                     && player.getMainHandItem().is((Item)TenaddexItems.TRUE_EXCALIBUR.get());
                  ArthurAfkVfxPayload.send(player, start);
               }
            }
         );
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
