package com.github.L_Ender.cataclysm.message;

import com.github.L_Ender.cataclysm.client.sound.MeatShredderSound;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageMeatShredder(int entityID, boolean play) implements CustomPacketPayload {
   public static final Type<MessageMeatShredder> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("cataclysm", "meatshredder_sound"));
   public static final StreamCodec<RegistryFriendlyByteBuf, MessageMeatShredder> STREAM_CODEC = CustomPacketPayload.codec(
      MessageMeatShredder::write, MessageMeatShredder::new
   );

   public MessageMeatShredder(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readBoolean());
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.entityID());
      buf.writeBoolean(this.play());
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(final MessageMeatShredder message, final IPayloadContext ctx) {
      if (ctx.flow().isClientbound()) {
         ctx.enqueueWork(new Runnable() {
            @Override
            public void run() {
               if (ctx.player().level().getEntity(message.entityID()) instanceof LivingEntity living) {
                  Minecraft.getInstance().getSoundManager().queueTickingSound(new MeatShredderSound(living));
               }
            }
         });
      }
   }
}
