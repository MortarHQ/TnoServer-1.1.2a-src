package com.mongoose.royalvariations.common.network.server;

import com.mongoose.royalvariations.RoyalVariations;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SRemoveEffectPacket implements CustomPacketPayload {
   public static final Type<SRemoveEffectPacket> TYPE = new Type(RoyalVariations.location("remove_effect"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SRemoveEffectPacket> STREAM_CODEC = CustomPacketPayload.codec(
      SRemoveEffectPacket::encode, SRemoveEffectPacket::new
   );
   private final int mob;
   private final ResourceLocation effect;

   public SRemoveEffectPacket(int mob, Holder<MobEffect> effect) {
      this.mob = mob;
      this.effect = BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)effect.value());
   }

   public SRemoveEffectPacket(int mob, ResourceLocation effect) {
      this.mob = mob;
      this.effect = effect;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.mob);
      buffer.writeResourceLocation(this.effect);
   }

   public SRemoveEffectPacket(FriendlyByteBuf buffer) {
      this(buffer.readInt(), buffer.readResourceLocation());
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void consume(SRemoveEffectPacket packet, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         if (RoyalVariations.PROXY.getLevel() instanceof ClientLevel clientWorld && clientWorld.getEntity(packet.mob) instanceof LivingEntity livingEntity) {
            MobEffect mobEffect = (MobEffect)BuiltInRegistries.MOB_EFFECT.get(packet.effect);
            if (mobEffect != null) {
               Holder<MobEffect> holder = Holder.direct(mobEffect);
               livingEntity.removeEffect(holder);
            }
         }
      });
   }
}
