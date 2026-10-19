package com.notunanancyowen.spears.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SwingAnimation(int swingTicks, String swingType) {
   public static final Codec<SwingAnimation> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.optionalFieldOf("duration", 6).forGetter(SwingAnimation::swingTicks),
            Codec.STRING.optionalFieldOf("type", "whack").forGetter(SwingAnimation::swingType)
         )
         .apply(instance, SwingAnimation::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, SwingAnimation> PACKET_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, SwingAnimation::swingTicks, ByteBufCodecs.STRING_UTF8, SwingAnimation::swingType, SwingAnimation::new
   );
}
