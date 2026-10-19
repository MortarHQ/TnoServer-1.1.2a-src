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

public record LightningParticleOptions(int r, int g, int b) implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, LightningParticleOptions> STREAM_CODEC = StreamCodec.of((buf, option) -> {
      buf.writeInt(option.r);
      buf.writeInt(option.g);
      buf.writeInt(option.b);
   }, buf -> new LightningParticleOptions(buf.readInt(), buf.readInt(), buf.readInt()));
   public static MapCodec<LightningParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(Codec.INT.fieldOf("r").forGetter(p -> p.r), Codec.INT.fieldOf("g").forGetter(p -> p.g), Codec.INT.fieldOf("b").forGetter(p -> p.b))
         .apply(object, LightningParticleOptions::new)
   );

   @NotNull
   public ParticleType<LightningParticleOptions> getType() {
      return (ParticleType<LightningParticleOptions>)ModParticle.LIGHTNING.get();
   }
}
