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

public record RingParticleOptions(float yaw, float pitch, int duration, int r, int g, int b, float a, float scale, boolean facesCamera, int behavior)
   implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, RingParticleOptions> STREAM_CODEC = StreamCodec.of(
      (buf, option) -> {
         buf.writeFloat(option.yaw);
         buf.writeFloat(option.pitch);
         buf.writeInt(option.duration);
         buf.writeInt(option.r);
         buf.writeInt(option.g);
         buf.writeInt(option.b);
         buf.writeFloat(option.a);
         buf.writeFloat(option.scale);
         buf.writeBoolean(option.facesCamera);
         buf.writeInt(option.behavior);
      },
      buf -> new RingParticleOptions(
         buf.readFloat(),
         buf.readFloat(),
         buf.readInt(),
         buf.readInt(),
         buf.readInt(),
         buf.readInt(),
         buf.readFloat(),
         buf.readFloat(),
         buf.readBoolean(),
         buf.readInt()
      )
   );
   public static MapCodec<RingParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(
            Codec.FLOAT.fieldOf("yaw").forGetter(p -> p.yaw),
            Codec.FLOAT.fieldOf("pitch").forGetter(p -> p.pitch),
            Codec.INT.fieldOf("duration").forGetter(p -> p.duration),
            Codec.INT.fieldOf("r").forGetter(p -> p.r),
            Codec.INT.fieldOf("g").forGetter(p -> p.g),
            Codec.INT.fieldOf("b").forGetter(p -> p.b),
            Codec.FLOAT.fieldOf("a").forGetter(p -> p.a),
            Codec.FLOAT.fieldOf("scale").forGetter(p -> p.scale),
            Codec.BOOL.fieldOf("facescamera").forGetter(p -> p.facesCamera),
            Codec.INT.fieldOf("behavior").forGetter(p -> p.behavior)
         )
         .apply(object, RingParticleOptions::new)
   );

   @NotNull
   public ParticleType<RingParticleOptions> getType() {
      return (ParticleType<RingParticleOptions>)ModParticle.RING.get();
   }
}
