package dev.foxboss.network;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record MilimBossBarPayload(UUID bossId, boolean tracked) implements CustomPacketPayload {
   public static final Type<MilimBossBarPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("foxboss", "milim_boss_bar"));
   public static final StreamCodec<FriendlyByteBuf, MilimBossBarPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MilimBossBarPayload::encode, MilimBossBarPayload::new
   );

   private MilimBossBarPayload(FriendlyByteBuf buffer) {
      this(buffer.readUUID(), buffer.readBoolean());
   }

   private void encode(FriendlyByteBuf buffer) {
      buffer.writeUUID(this.bossId);
      buffer.writeBoolean(this.tracked);
   }

   public Type<MilimBossBarPayload> type() {
      return TYPE;
   }
}
