package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.packets;

import com.finderfeed.fdlib.FDClientPacketExecutables;
import com.finderfeed.fdlib.network.FDPacket;
import com.finderfeed.fdlib.network.RegisterFDPacket;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationTicker;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@RegisterFDPacket("fdbosses:start_item_animation_in_hand_packet")
public class StartItemAnimationInHandPacket extends FDPacket {
   private String layer;
   private int entityId;
   private AnimationTicker animation;
   private InteractionHand hand;

   public StartItemAnimationInHandPacket(String layer, LivingEntity serverPlayer, AnimationTicker animationTicker, InteractionHand hand) {
      this.layer = layer;
      this.animation = animationTicker;
      this.hand = hand;
      this.entityId = serverPlayer.getId();
   }

   public StartItemAnimationInHandPacket(FriendlyByteBuf buf) {
      this.hand = InteractionHand.values()[buf.readInt()];
      this.animation = (AnimationTicker)AnimationTicker.NETWORK_CODEC.decode(buf);
      this.entityId = buf.readInt();
      this.layer = buf.readUtf();
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.hand.ordinal());
      AnimationTicker.NETWORK_CODEC.encode(buf, this.animation);
      buf.writeInt(this.entityId);
      buf.writeUtf(this.layer);
   }

   @Override
   public void clientAction(IPayloadContext context) {
      FDClientPacketExecutables.startItemAnimationInHand(this.entityId, this.animation, this.hand, this.layer);
   }

   @Override
   public void serverAction(IPayloadContext context) {
   }
}
