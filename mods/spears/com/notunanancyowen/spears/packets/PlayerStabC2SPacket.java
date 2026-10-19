package com.notunanancyowen.spears.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record PlayerStabC2SPacket(int entityId) implements CustomPacketPayload {
   public static final StreamCodec<FriendlyByteBuf, PlayerStabC2SPacket> PACKET_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_INT, PlayerStabC2SPacket::entityId, PlayerStabC2SPacket::new
   );
   public static final Type<PlayerStabC2SPacket> ID = new Type(ResourceLocation.fromNamespaceAndPath("spears", "send_stab_attack_to_server"));

   @Nullable
   public Entity getEntity(Level world) {
      return world.getEntity(this.entityId);
   }

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }
}
