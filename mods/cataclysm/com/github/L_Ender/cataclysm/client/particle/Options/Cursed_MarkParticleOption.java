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

public record Cursed_MarkParticleOption(float size) implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, Cursed_MarkParticleOption> STREAM_CODEC = StreamCodec.of(
      (buf, option) -> buf.writeFloat(option.size), buf -> new Cursed_MarkParticleOption(buf.readFloat())
   );
   public static MapCodec<Cursed_MarkParticleOption> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(Codec.FLOAT.fieldOf("size").forGetter(p -> p.size)).apply(object, Cursed_MarkParticleOption::new)
   );

   @NotNull
   public ParticleType<Cursed_MarkParticleOption> getType() {
      return (ParticleType<Cursed_MarkParticleOption>)ModParticle.CURSED_MARK.get();
   }
}
