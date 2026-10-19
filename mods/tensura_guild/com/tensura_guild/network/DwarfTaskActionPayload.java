package com.tensura_guild.network;

import com.tensura_guild.data.DwarfTaskService;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DwarfTaskActionPayload(DwarfTaskActionPayload.Action action, int entityId) implements CustomPacketPayload {
   public static final Type<DwarfTaskActionPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "dwarf_task_action"));
   public static final StreamCodec<ByteBuf, DwarfTaskActionPayload> STREAM_CODEC = new StreamCodec<ByteBuf, DwarfTaskActionPayload>() {
      public DwarfTaskActionPayload decode(ByteBuf buffer) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         return new DwarfTaskActionPayload((DwarfTaskActionPayload.Action)buf.readEnum(DwarfTaskActionPayload.Action.class), buf.readVarInt());
      }

      public void encode(ByteBuf buffer, DwarfTaskActionPayload value) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         buf.writeEnum(value.action);
         buf.writeVarInt(value.entityId);
      }
   };

   public Type<DwarfTaskActionPayload> type() {
      return TYPE;
   }

   public static void handle(DwarfTaskActionPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (context.player() instanceof ServerPlayer player) {
            switch (payload.action) {
               case ACCEPT:
                  DwarfTaskService.acceptTask(player, payload.entityId);
                  break;
               case SUBMIT:
                  DwarfTaskService.submitTask(player, payload.entityId);
                  break;
               case CLAIM_GUILD_MAP:
                  DwarfTaskService.claimGuildMapReward(player, payload.entityId);
            }
         }
      });
   }

   public enum Action {
      ACCEPT,
      SUBMIT,
      CLAIM_GUILD_MAP;
   }
}
