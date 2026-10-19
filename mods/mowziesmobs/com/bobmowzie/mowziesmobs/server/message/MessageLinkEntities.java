package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.entity.ILinkedEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MessageLinkEntities(int sourceId, int targetId) implements CustomPacketPayload {
   public static final Type<MessageLinkEntities> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "message_link_entities"));
   public static final StreamCodec<ByteBuf, MessageLinkEntities> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, MessageLinkEntities::sourceId, ByteBufCodecs.INT, MessageLinkEntities::targetId, MessageLinkEntities::new
   );

   public static MessageLinkEntities fromEntity(Entity source, Entity target) {
      return source instanceof ILinkedEntity ? new MessageLinkEntities(source.getId(), target.getId()) : new MessageLinkEntities(-1, -1);
   }

   public static void handleClient(MessageLinkEntities packet, IPayloadContext context) {
      context.enqueueWork(() -> {
         Level level = MMCommon.PROXY.getClientLevel();
         if (level != null) {
            Entity entitySource = level.getEntity(packet.sourceId());
            Entity entityTarget = level.getEntity(packet.targetId());
            if (entitySource instanceof ILinkedEntity linked && entityTarget != null) {
               linked.link(entityTarget);
            }
         }
      });
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
