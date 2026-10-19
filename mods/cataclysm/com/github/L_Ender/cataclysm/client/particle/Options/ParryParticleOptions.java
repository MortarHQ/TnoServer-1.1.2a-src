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

public record ParryParticleOptions(float r, float g, float b) implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, ParryParticleOptions> STREAM_CODEC = StreamCodec.of((buf, option) -> {
      buf.writeFloat(option.r);
      buf.writeFloat(option.g);
      buf.writeFloat(option.b);
   }, buf -> new ParryParticleOptions(buf.readFloat(), buf.readFloat(), buf.readFloat()));
   public static MapCodec<ParryParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(
            Codec.FLOAT.fieldOf("r").forGetter(p -> p.r), Codec.FLOAT.fieldOf("g").forGetter(p -> p.g), Codec.FLOAT.fieldOf("b").forGetter(p -> p.b)
         )
         .apply(object, ParryParticleOptions::new)
   );

   @NotNull
   public ParticleType<ParryParticleOptions> getType() {
      return (ParticleType<ParryParticleOptions>)ModParticle.PARRY.get();
   }
}
