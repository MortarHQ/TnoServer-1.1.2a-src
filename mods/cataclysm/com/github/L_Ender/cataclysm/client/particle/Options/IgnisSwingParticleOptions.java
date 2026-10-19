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

public record IgnisSwingParticleOptions(int lifetime, float scale, float yaw, float pitch, float roll) implements ParticleOptions {
   public static StreamCodec<? super ByteBuf, IgnisSwingParticleOptions> STREAM_CODEC = StreamCodec.of((buf, option) -> {
      buf.writeInt(option.lifetime);
      buf.writeFloat(option.scale);
      buf.writeFloat(option.yaw);
      buf.writeFloat(option.pitch);
      buf.writeFloat(option.roll);
   }, buf -> new IgnisSwingParticleOptions(buf.readInt(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat()));
   public static MapCodec<IgnisSwingParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(
      object -> object.group(
            Codec.INT.fieldOf("lifetime").forGetter(p -> p.lifetime),
            Codec.FLOAT.fieldOf("scale").forGetter(p -> p.scale),
            Codec.FLOAT.fieldOf("yaw").forGetter(p -> p.yaw),
            Codec.FLOAT.fieldOf("pitch").forGetter(p -> p.pitch),
            Codec.FLOAT.fieldOf("roll").forGetter(p -> p.roll)
         )
         .apply(object, IgnisSwingParticleOptions::new)
   );

   @NotNull
   public ParticleType<IgnisSwingParticleOptions> getType() {
      return (ParticleType<IgnisSwingParticleOptions>)ModParticle.IGNIS_SWING.get();
   }
}
