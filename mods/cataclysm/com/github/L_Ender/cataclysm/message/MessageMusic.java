package com.github.L_Ender.cataclysm.message;

import com.github.L_Ender.cataclysm.client.sound.BossMusicPlayer;
import com.github.L_Ender.cataclysm.entity.etc.Animation_Monsters;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MessageMusic(int entityID, boolean play) implements CustomPacketPayload {
   public static final Type<MessageMusic> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("cataclysm", "music"));
   public static final StreamCodec<RegistryFriendlyByteBuf, MessageMusic> STREAM_CODEC = CustomPacketPayload.codec(MessageMusic::write, MessageMusic::new);

   public MessageMusic(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readBoolean());
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.entityID());
      buf.writeBoolean(this.play());
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(final MessageMusic message, final IPayloadContext ctx) {
      if (ctx.flow().isClientbound()) {
         ctx.enqueueWork(new Runnable() {
            @Override
            public void run() {
               if (ctx.player().level().getEntity(message.entityID) instanceof Animation_Monsters am) {
                  if (message.play) {
                     BossMusicPlayer.playBossMusic(am);
                  } else {
                     BossMusicPlayer.stopBossMusic(am);
                  }
               }
            }
         });
      }
   }
}
