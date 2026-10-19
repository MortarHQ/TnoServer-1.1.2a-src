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

public record RoarParticleOptions(int duration, int r, int g, int b, float a, float startsize, float increase, float endsize) implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, RoarParticleOptions> STREAM_CODEC = StreamCodec.of(
      (buf, option) -> {
         buf.writeInt(option.duration);
         buf.writeInt(option.r);
         buf.writeInt(option.g);
         buf.writeInt(option.b);
         buf.writeFloat(option.a);
         buf.writeFloat(option.startsize);
         buf.writeFloat(option.increase);
         buf.writeFloat(option.endsize);
      },
      buf -> new RoarParticleOptions(
         buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat()
      )
   );
   public static MapCodec<RoarParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(
            Codec.INT.fieldOf("duration").forGetter(p -> p.duration),
            Codec.INT.fieldOf("r").forGetter(p -> p.r),
            Codec.INT.fieldOf("g").forGetter(p -> p.g),
            Codec.INT.fieldOf("b").forGetter(p -> p.b),
            Codec.FLOAT.fieldOf("a").forGetter(p -> p.a),
            Codec.FLOAT.fieldOf("startsize").forGetter(p -> p.startsize),
            Codec.FLOAT.fieldOf("increase").forGetter(p -> p.increase),
            Codec.FLOAT.fieldOf("endsize").forGetter(p -> p.endsize)
         )
         .apply(object, RoarParticleOptions::new)
   );

   @NotNull
   public ParticleType<RoarParticleOptions> getType() {
      return (ParticleType<RoarParticleOptions>)ModParticle.ROAR.get();
   }
}
