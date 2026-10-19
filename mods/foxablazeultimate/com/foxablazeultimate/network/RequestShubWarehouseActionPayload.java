package com.foxablazeultimate.network;

import com.foxablazeultimate.ability.skill.shubniggurath.ShubNiggurathWarehouse;
import dev.architectury.networking.NetworkManager.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record RequestShubWarehouseActionPayload(int action, int abilitySlot, ResourceLocation primarySkill, ResourceLocation secondarySkill)
   implements CustomPacketPayload {
   public static final int ACTION_COPY_SELF = 0;
   public static final int ACTION_SYNTHESIZE = 1;
   public static final int ACTION_PREPARE_GIFT = 2;
   public static final Type<RequestShubWarehouseActionPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "request_shub_warehouse_action")
   );
   public static final StreamCodec<FriendlyByteBuf, RequestShubWarehouseActionPayload> STREAM_CODEC = CustomPacketPayload.codec(
      RequestShubWarehouseActionPayload::encode, RequestShubWarehouseActionPayload::new
   );

   public RequestShubWarehouseActionPayload(FriendlyByteBuf buf) {
      this(buf.readVarInt(), buf.readVarInt(), buf.readResourceLocation(), buf.readBoolean() ? buf.readResourceLocation() : null);
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeVarInt(this.action);
      buf.writeVarInt(this.abilitySlot);
      buf.writeResourceLocation(this.primarySkill);
      buf.writeBoolean(this.secondarySkill != null);
      if (this.secondarySkill != null) {
         buf.writeResourceLocation(this.secondarySkill);
      }
   }

   public static void handle(RequestShubWarehouseActionPayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         context.queue(
            () -> ShubNiggurathWarehouse.handleWarehouseAction(
               player, payload.action(), payload.abilitySlot(), payload.primarySkill(), payload.secondarySkill()
            )
         );
      }
   }

   public Type<RequestShubWarehouseActionPayload> type() {
      return TYPE;
   }
}
