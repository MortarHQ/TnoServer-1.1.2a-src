package com.foxablazeultimate.network;

import com.foxablazeultimate.ability.skill.yogsothoth.YogTimeStopManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record YogTimeStopStatePayload(boolean active, UUID ownerId) implements CustomPacketPayload {
   public static final Type<YogTimeStopStatePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "yog_time_stop_state"));
   public static final StreamCodec<FriendlyByteBuf, YogTimeStopStatePayload> STREAM_CODEC = CustomPacketPayload.codec(
      YogTimeStopStatePayload::encode, YogTimeStopStatePayload::new
   );

   public YogTimeStopStatePayload(boolean active) {
      this(active, null);
   }

   public YogTimeStopStatePayload(FriendlyByteBuf buffer) {
      this(buffer.readBoolean(), buffer.readBoolean() ? buffer.readUUID() : null);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.active);
      buffer.writeBoolean(this.ownerId != null);
      if (this.ownerId != null) {
         buffer.writeUUID(this.ownerId);
      }
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> YogTimeStopManager.setClientState(this.active, this.ownerId));
      }
   }

   public Type<YogTimeStopStatePayload> type() {
      return TYPE;
   }
}
