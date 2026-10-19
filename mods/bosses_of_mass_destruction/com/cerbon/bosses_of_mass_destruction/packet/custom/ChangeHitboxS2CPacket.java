package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEntity;
import com.cerbon.cerbons_api.api.network.data.PacketContext;
import com.cerbon.cerbons_api.api.network.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public class ChangeHitboxS2CPacket {
   public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath("cerbons_api", "change_hitbox_s2c_packet");
   public static final StreamCodec<FriendlyByteBuf, ChangeHitboxS2CPacket> STREAM_CODEC = StreamCodec.ofMember(
      ChangeHitboxS2CPacket::write, ChangeHitboxS2CPacket::new
   );
   private final int entityId;
   private final boolean open;

   public ChangeHitboxS2CPacket(int entityId, boolean open) {
      this.entityId = entityId;
      this.open = open;
   }

   public ChangeHitboxS2CPacket(FriendlyByteBuf buf) {
      this.entityId = buf.readInt();
      this.open = buf.readBoolean();
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeBoolean(this.open);
   }

   public static void handle(PacketContext<ChangeHitboxS2CPacket> ctx) {
      if (!ctx.side().equals(Side.SERVER)) {
         ChangeHitboxS2CPacket packet = (ChangeHitboxS2CPacket)ctx.message();
         Minecraft client = Minecraft.getInstance();
         ClientLevel level = client.level;
         if (level != null) {
            client.execute(() -> {
               if (level.getEntity(packet.entityId) instanceof GauntletEntity gauntletEntity) {
                  if (packet.open) {
                     gauntletEntity.hitboxHelper.setOpenHandHitbox();
                  } else {
                     gauntletEntity.hitboxHelper.setClosedFistHitbox();
                  }
               }
            });
         }
      }
   }

   public static Type<CustomPacketPayload> type() {
      return new Type(CHANNEL);
   }
}
