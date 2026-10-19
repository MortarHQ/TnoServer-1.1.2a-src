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

public class EndSplashParticleOption implements ParticleOptions {
   public static final MapCodec<EndSplashParticleOption> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.FLOAT.fieldOf("size").forGetter(d -> d.size), Codec.INT.fieldOf("speed").forGetter(d -> d.speed))
         .apply(instance, EndSplashParticleOption::new)
   );
   public static StreamCodec<? super ByteBuf, EndSplashParticleOption> STREAM_CODEC = StreamCodec.of((buf, option) -> {
      buf.writeFloat(option.size);
      buf.writeInt(option.speed);
   }, buf -> new EndSplashParticleOption(buf.readFloat(), buf.readInt()));
   private final float size;
   private final int speed;

   public EndSplashParticleOption() {
      this.size = 10.0F;
      this.speed = 0;
   }

   public EndSplashParticleOption(float size, int speed) {
      this.size = size;
      this.speed = speed;
   }

   public void writeToNetwork(FriendlyByteBuf p_235956_) {
      p_235956_.writeFloat(this.size);
      p_235956_.writeInt(this.speed);
   }

   public String writeToString() {
      return String.format(Locale.ROOT, "%s %.2f %s", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.size, this.speed);
   }

   public ParticleType<EndSplashParticleOption> getType() {
      return (ParticleType<EndSplashParticleOption>)RVParticleTypes.END_SPLASH.get();
   }

   public float getSize() {
      return this.size;
   }

   public int getSpeed() {
      return this.speed;
   }
}
