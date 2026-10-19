package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerUmvuthiTrade;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MessageUmvuthiTrade(int entityId) implements CustomPacketPayload {
   public static final Type<MessageUmvuthiTrade> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "message_umvuthi_trade"));
   public static final StreamCodec<ByteBuf, MessageUmvuthiTrade> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, MessageUmvuthiTrade::entityId, MessageUmvuthiTrade::new
   );

   public static void handleServer(MessageUmvuthiTrade packet, IPayloadContext context) {
      context.enqueueWork(() -> {
         Player player = context.player();
         if (player.level().getEntity(packet.entityId()) instanceof EntityUmvuthi umvuthi) {
            if (umvuthi.getCustomer() == player) {
               if (player.containerMenu instanceof ContainerUmvuthiTrade trade) {
                  boolean hasTradedWith = umvuthi.hasTradedWith(player);
                  if (!hasTradedWith && umvuthi.fulfillDesire(trade.getSlot(0))) {
                     umvuthi.rememberTrade(player);
                     trade.returnItems();
                     trade.broadcastChanges();
                  } else if (hasTradedWith) {
                     player.addEffect(new MobEffectInstance(EffectHandler.SUNS_BLESSING, -1, 0, false, false));
                     if (umvuthi.getActiveAbilityType() != EntityUmvuthi.BLESS_ABILITY) {
                        umvuthi.sendAbilityMessage(EntityUmvuthi.BLESS_ABILITY);
                        umvuthi.playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_BLESS.get(), 2.0F, 1.0F);
                     }
                  }
               }
            }
         }
      });
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
