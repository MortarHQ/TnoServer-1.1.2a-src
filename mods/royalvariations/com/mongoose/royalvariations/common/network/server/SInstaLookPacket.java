package com.mongoose.royalvariations.common.network.server;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.utils.MobUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SInstaLookPacket implements CustomPacketPayload {
   public static final Type<SInstaLookPacket> TYPE = new Type(RoyalVariations.location("insta_look"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SInstaLookPacket> STREAM_CODEC = CustomPacketPayload.codec(
      SInstaLookPacket::encode, SInstaLookPacket::new
   );
   public int looker;
   public int target;

   public SInstaLookPacket(int lookerId, int targetId) {
      this.looker = lookerId;
      this.target = targetId;
   }

   public SInstaLookPacket(Mob looker, Entity target) {
      this.looker = looker.getId();
      this.target = target.getId();
   }

   public SInstaLookPacket(FriendlyByteBuf buffer) {
      this(buffer.readInt(), buffer.readInt());
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.looker);
      buffer.writeInt(this.target);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void consume(SInstaLookPacket packet, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         if (RoyalVariations.PROXY.getLevel() instanceof ClientLevel clientWorld) {
            Entity looker = clientWorld.getEntity(packet.looker);
            Entity target = clientWorld.getEntity(packet.target);
            if (looker instanceof Mob mob && target != null) {
               MobUtil.instaLook(mob, target);
            }
         }
      });
   }
}
