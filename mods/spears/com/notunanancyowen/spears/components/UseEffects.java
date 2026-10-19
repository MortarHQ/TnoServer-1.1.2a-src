package com.notunanancyowen.spears.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.notunanancyowen.spears.Spears;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record UseEffects(float strafeSpeed, boolean allowSprinting, boolean interactVibrations) {
   public static final Codec<UseEffects> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Spears.rangedInclusiveFloat(0.0F, 1.0F).optionalFieldOf("speed_multiplier", 0.2F).forGetter(UseEffects::strafeSpeed),
            Codec.BOOL.optionalFieldOf("can_sprint", false).forGetter(UseEffects::allowSprinting),
            Codec.BOOL.optionalFieldOf("interact_vibrations", true).forGetter(UseEffects::interactVibrations)
         )
         .apply(instance, UseEffects::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, UseEffects> PACKET_CODEC = StreamCodec.composite(
      ByteBufCodecs.FLOAT,
      UseEffects::strafeSpeed,
      ByteBufCodecs.BOOL,
      UseEffects::allowSprinting,
      ByteBufCodecs.BOOL,
      UseEffects::interactVibrations,
      UseEffects::new
   );
}
