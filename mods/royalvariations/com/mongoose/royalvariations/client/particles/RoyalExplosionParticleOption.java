package com.mongoose.royalvariations.client.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Locale;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class RoyalExplosionParticleOption implements ParticleOptions {
   public static final MapCodec<RoyalExplosionParticleOption> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.FLOAT.fieldOf("size").forGetter(d -> d.size)).apply(instance, RoyalExplosionParticleOption::new)
   );
   public static StreamCodec<? super ByteBuf, RoyalExplosionParticleOption> STREAM_CODEC = StreamCodec.of(
      (buf, option) -> buf.writeFloat(option.size), buf -> new RoyalExplosionParticleOption(buf.readFloat())
   );
   private final float size;

   public RoyalExplosionParticleOption(float size) {
      this.size = size;
   }

   public void writeToNetwork(FriendlyByteBuf p_235956_) {
      p_235956_.writeFloat(this.size);
   }

   public String writeToString() {
      return String.format(Locale.ROOT, "%s %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.size);
   }

   public ParticleType<RoyalExplosionParticleOption> getType() {
      return (ParticleType<RoyalExplosionParticleOption>)RVParticleTypes.ROYAL_EXPLOSION.get();
   }

   public float getSize() {
      return this.size;
   }
}
