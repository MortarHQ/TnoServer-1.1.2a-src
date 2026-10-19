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

public record GatheringWaterParticleOptions(float r, float g, float b) implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, GatheringWaterParticleOptions> STREAM_CODEC = StreamCodec.of((buf, option) -> {
      buf.writeFloat(option.r);
      buf.writeFloat(option.g);
      buf.writeFloat(option.b);
   }, buf -> new GatheringWaterParticleOptions(buf.readFloat(), buf.readFloat(), buf.readFloat()));
   public static MapCodec<GatheringWaterParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(
            Codec.FLOAT.fieldOf("r").forGetter(p -> p.r), Codec.FLOAT.fieldOf("g").forGetter(p -> p.g), Codec.FLOAT.fieldOf("b").forGetter(p -> p.b)
         )
         .apply(object, GatheringWaterParticleOptions::new)
   );

   @NotNull
   public ParticleType<GatheringWaterParticleOptions> getType() {
      return (ParticleType<GatheringWaterParticleOptions>)ModParticle.GATHERING_WATER.get();
   }
}
