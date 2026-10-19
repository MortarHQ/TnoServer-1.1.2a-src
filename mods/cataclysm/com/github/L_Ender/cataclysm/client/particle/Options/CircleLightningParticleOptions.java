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

public record CircleLightningParticleOptions(float size, int r, int g, int b) implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, CircleLightningParticleOptions> STREAM_CODEC = StreamCodec.of((buf, option) -> {
      buf.writeFloat(option.size);
      buf.writeInt(option.r);
      buf.writeInt(option.g);
      buf.writeInt(option.b);
   }, buf -> new CircleLightningParticleOptions(buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt()));
   public static MapCodec<CircleLightningParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(
            Codec.FLOAT.fieldOf("size").forGetter(p -> p.size),
            Codec.INT.fieldOf("r").forGetter(p -> p.r),
            Codec.INT.fieldOf("g").forGetter(p -> p.g),
            Codec.INT.fieldOf("b").forGetter(p -> p.b)
         )
         .apply(object, CircleLightningParticleOptions::new)
   );

   @NotNull
   public ParticleType<CircleLightningParticleOptions> getType() {
      return (ParticleType<CircleLightningParticleOptions>)ModParticle.CIRCLE_LIGHTNING.get();
   }
}
