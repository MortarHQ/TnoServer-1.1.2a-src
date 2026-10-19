package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.screen.GreedCasinoClientState;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GreedCasinoResultPayload(boolean ok, String title, int chipsNow, int epGain, int mpGain, String skillName) implements CustomPacketPayload {
   public static final Type<GreedCasinoResultPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "greed_casino_result"));
   public static final StreamCodec<FriendlyByteBuf, GreedCasinoResultPayload> STREAM_CODEC = CustomPacketPayload.codec(
      GreedCasinoResultPayload::encode, GreedCasinoResultPayload::new
   );

   public GreedCasinoResultPayload(FriendlyByteBuf buf) {
      this(buf.readBoolean(), buf.readUtf(), buf.readVarInt(), buf.readVarInt(), buf.readVarInt(), buf.readUtf());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeBoolean(this.ok);
      buf.writeUtf(this.title);
      buf.writeVarInt(this.chipsNow);
      buf.writeVarInt(this.epGain);
      buf.writeVarInt(this.mpGain);
      buf.writeUtf(this.skillName == null ? "" : this.skillName);
   }

   public static void handle(GreedCasinoResultPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
               GreedCasinoClientState.setLast(payload);
               mc.player.displayClientMessage(Component.literal("[Casino] " + payload.title() + " | Chips: " + payload.chipsNow()), false);
            }
         });
      }
   }

   @NotNull
   public Type<GreedCasinoResultPayload> type() {
      return TYPE;
   }
}
