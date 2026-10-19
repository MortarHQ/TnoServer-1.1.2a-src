package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.client.ClientProxy;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MessageUpdateBossBar(UUID bossId, boolean remove, ResourceLocation registryName) implements CustomPacketPayload {
   public static final Type<MessageUpdateBossBar> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "message_update_boss_bar"));
   public static final StreamCodec<ByteBuf, MessageUpdateBossBar> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC,
      MessageUpdateBossBar::bossId,
      ByteBufCodecs.BOOL,
      MessageUpdateBossBar::remove,
      ResourceLocation.STREAM_CODEC,
      MessageUpdateBossBar::registryName,
      MessageUpdateBossBar::new
   );
   private static final ResourceLocation NULL = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "null");

   public static MessageUpdateBossBar fromEntity(UUID bossId, LivingEntity entity) {
      if (entity == null) {
         return new MessageUpdateBossBar(bossId, true, NULL);
      }

      ResourceLocation registryName = entity.level().registryAccess().registryOrThrow(Registries.ENTITY_TYPE).getKey(entity.getType());
      return new MessageUpdateBossBar(bossId, false, registryName);
   }

   public static void handleClient(MessageUpdateBossBar packet, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (packet.remove()) {
            ClientProxy.bossBarRegistryNames.remove(packet.bossId());
         } else {
            ClientProxy.bossBarRegistryNames.put(packet.bossId(), packet.registryName());
         }
      });
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
