package com.github.L_Ender.cataclysm.client.particle.Options;

import com.github.L_Ender.cataclysm.init.ModParticle;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record NotSpinTrailParticleOptions(float r, float g, float b, float gravity, float reduction, float acceleration, double direction, int life)
   implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, NotSpinTrailParticleOptions> STREAM_CODEC = StreamCodec.of(
      (buf, option) -> {
         buf.writeFloat(option.r);
         buf.writeFloat(option.g);
         buf.writeFloat(option.b);
         buf.writeFloat(option.gravity);
         buf.writeFloat(option.reduction);
         buf.writeFloat(option.acceleration);
         buf.writeDouble(option.direction);
         buf.writeInt(option.life);
      },
      buf -> new NotSpinTrailParticleOptions(
         buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt()
      )
   );
   public static MapCodec<NotSpinTrailParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(
            Codec.FLOAT.fieldOf("r").forGetter(p -> p.r),
            Codec.FLOAT.fieldOf("g").forGetter(p -> p.g),
            Codec.FLOAT.fieldOf("b").forGetter(p -> p.b),
            Codec.FLOAT.fieldOf("gravity").forGetter(p -> p.gravity),
            Codec.FLOAT.fieldOf("reduction").forGetter(p -> p.reduction),
            Codec.FLOAT.fieldOf("acceleration").forGetter(p -> p.acceleration),
            Codec.DOUBLE.fieldOf("direction").forGetter(p -> p.direction),
            Codec.INT.fieldOf("life").forGetter(p -> p.life)
         )
         .apply(object, NotSpinTrailParticleOptions::new)
   );

   @NotNull
   public ParticleType<NotSpinTrailParticleOptions> getType() {
      return (ParticleType<NotSpinTrailParticleOptions>)ModParticle.NOT_SPIN_PARTICLE.get();
   }
}
