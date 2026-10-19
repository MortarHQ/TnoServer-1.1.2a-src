package com.foxablazeultimate.network;

import com.foxablazeultimate.client.AzathothChargeVisuals;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record AzathothChargeVisualPayload(int entityId, UUID ownerId, boolean active, boolean charging, UUID weaponToken) implements CustomPacketPayload {
   public static final Type<AzathothChargeVisualPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "azathoth_charge_visual"));
   public static final StreamCodec<FriendlyByteBuf, AzathothChargeVisualPayload> STREAM_CODEC = CustomPacketPayload.codec(
      AzathothChargeVisualPayload::encode, AzathothChargeVisualPayload::new
   );

   public AzathothChargeVisualPayload(FriendlyByteBuf buffer) {
      this(buffer.readVarInt(), buffer.readUUID(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean() ? buffer.readUUID() : null);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeVarInt(this.entityId);
      buffer.writeUUID(this.ownerId);
      buffer.writeBoolean(this.active);
      buffer.writeBoolean(this.charging);
      buffer.writeBoolean(this.weaponToken != null);
      if (this.weaponToken != null) {
         buffer.writeUUID(this.weaponToken);
      }
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> AzathothChargeVisuals.update(this));
      }
   }

   public Type<AzathothChargeVisualPayload> type() {
      return TYPE;
   }
}
