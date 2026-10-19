package com.iafenvoy.uranus.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record AnimationPayload(int entityID, int index) implements CustomPacketPayload {
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("uranus", "animation");
   public static final Type<AnimationPayload> ID = new Type(ANIMATION);
   public static final StreamCodec<ByteBuf, AnimationPayload> CODEC = ByteBufCodecs.fromCodec(
      RecordCodecBuilder.create(
         i -> i.group(Codec.INT.fieldOf("entityID").forGetter(AnimationPayload::entityID), Codec.INT.fieldOf("index").forGetter(AnimationPayload::index))
            .apply(i, AnimationPayload::new)
      )
   );

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }
}
