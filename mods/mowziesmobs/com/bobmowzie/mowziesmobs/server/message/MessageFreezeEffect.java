package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MessageFreezeEffect(int entityId, boolean isFrozen) implements CustomPacketPayload {
   public static final Type<MessageFreezeEffect> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "message_freeze_effect"));
   public static final StreamCodec<ByteBuf, MessageFreezeEffect> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, MessageFreezeEffect::entityId, ByteBufCodecs.BOOL, MessageFreezeEffect::isFrozen, MessageFreezeEffect::new
   );

   public static void handleClient(MessageFreezeEffect packet, IPayloadContext context) {
      context.enqueueWork(() -> {
         Level level = MMCommon.PROXY.getClientLevel();
         if (level != null && level.getEntity(packet.entityId()) instanceof LivingEntity living) {
            FrozenData data = DataHandler.getData(living, DataHandler.FROZEN_DATA);
            if (packet.isFrozen()) {
               data.onFreeze(living);
            } else {
               data.onUnfreeze(living);
            }
         }
      });
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
