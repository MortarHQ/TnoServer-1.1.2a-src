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

public record AfterImageParticleOptions(int entityid, int r, int g, int b, boolean ghost, int lifeticks) implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, AfterImageParticleOptions> STREAM_CODEC = StreamCodec.of((buf, option) -> {
      buf.writeInt(option.entityid);
      buf.writeInt(option.r);
      buf.writeInt(option.g);
      buf.writeInt(option.b);
      buf.writeBoolean(option.ghost);
      buf.writeInt(option.lifeticks);
   }, buf -> new AfterImageParticleOptions(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readBoolean(), buf.readInt()));
   public static MapCodec<AfterImageParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(
            Codec.INT.fieldOf("entityid").forGetter(p -> p.entityid),
            Codec.INT.fieldOf("r").forGetter(p -> p.r),
            Codec.INT.fieldOf("g").forGetter(p -> p.g),
            Codec.INT.fieldOf("b").forGetter(p -> p.b),
            Codec.BOOL.fieldOf("ghost").forGetter(p -> p.ghost),
            Codec.INT.fieldOf("lifeticks").forGetter(p -> p.lifeticks)
         )
         .apply(object, AfterImageParticleOptions::new)
   );

   @NotNull
   public ParticleType<AfterImageParticleOptions> getType() {
      return (ParticleType<AfterImageParticleOptions>)ModParticle.AFTER_IMAGE.get();
   }
}
