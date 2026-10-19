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

public record LightTrailParticleOptions(float r, float g, float b, float width, float height, int entityid) implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, LightTrailParticleOptions> STREAM_CODEC = StreamCodec.of((buf, option) -> {
      buf.writeFloat(option.r);
      buf.writeFloat(option.g);
      buf.writeFloat(option.b);
      buf.writeFloat(option.width);
      buf.writeFloat(option.height);
      buf.writeInt(option.entityid);
   }, buf -> new LightTrailParticleOptions(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt()));
   public static MapCodec<LightTrailParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(
            Codec.FLOAT.fieldOf("r").forGetter(p -> p.r),
            Codec.FLOAT.fieldOf("g").forGetter(p -> p.g),
            Codec.FLOAT.fieldOf("b").forGetter(p -> p.b),
            Codec.FLOAT.fieldOf("width").forGetter(p -> p.width),
            Codec.FLOAT.fieldOf("height").forGetter(p -> p.height),
            Codec.INT.fieldOf("entityid").forGetter(p -> p.entityid)
         )
         .apply(object, LightTrailParticleOptions::new)
   );

   @NotNull
   public ParticleType<LightTrailParticleOptions> getType() {
      return (ParticleType<LightTrailParticleOptions>)ModParticle.LIGHT_TRAIL.get();
   }
}
