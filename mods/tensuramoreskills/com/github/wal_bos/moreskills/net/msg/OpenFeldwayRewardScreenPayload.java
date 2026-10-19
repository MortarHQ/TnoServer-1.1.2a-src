package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.net.client.ClientPayloadExecutor;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record OpenFeldwayRewardScreenPayload(List<ResourceLocation> skills) implements CustomPacketPayload {
   public static final Type<OpenFeldwayRewardScreenPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "open_feldway_reward_screen")
   );
   public static final StreamCodec<FriendlyByteBuf, OpenFeldwayRewardScreenPayload> STREAM_CODEC = CustomPacketPayload.codec(
      OpenFeldwayRewardScreenPayload::encode, OpenFeldwayRewardScreenPayload::new
   );

   public OpenFeldwayRewardScreenPayload(FriendlyByteBuf buf) {
      this(buf.readList(FriendlyByteBuf::readResourceLocation));
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeCollection(this.skills, FriendlyByteBuf::writeResourceLocation);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ClientPayloadExecutor.openFeldwayRewardScreen(this.skills));
      }
   }

   public @NotNull Type<OpenFeldwayRewardScreenPayload> type() {
      return TYPE;
   }
}
