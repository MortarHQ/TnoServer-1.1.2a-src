package com.mongoose.royalvariations.client.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.StreamCodec;

public class RoyalGlowParticleOption implements ParticleOptions {
   public static final MapCodec<RoyalGlowParticleOption> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Codec.FLOAT.fieldOf("red").forGetter(d -> d.red),
            Codec.FLOAT.fieldOf("green").forGetter(d -> d.green),
            Codec.FLOAT.fieldOf("blue").forGetter(d -> d.blue),
            Codec.FLOAT.fieldOf("size").forGetter(d -> d.size),
            Codec.INT.fieldOf("speed").forGetter(d -> d.speed)
         )
         .apply(instance, RoyalGlowParticleOption::new)
   );
   public static StreamCodec<? super ByteBuf, RoyalGlowParticleOption> STREAM_CODEC = StreamCodec.of((buf, option) -> {
      buf.writeFloat(option.red);
      buf.writeFloat(option.green);
      buf.writeFloat(option.blue);
      buf.writeFloat(option.size);
      buf.writeInt(option.speed);
   }, buf -> new RoyalGlowParticleOption(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt()));
   private final float red;
   private final float green;
   private final float blue;
   private final float size;
   private final int speed;

   public RoyalGlowParticleOption(float r, float g, float b) {
      this.red = r;
      this.green = g;
      this.blue = b;
      this.size = 10.0F;
      this.speed = 0;
   }

   public RoyalGlowParticleOption(float r, float g, float b, float size, int speed) {
      this.red = r;
      this.green = g;
      this.blue = b;
      this.size = size;
      this.speed = speed;
   }

   public ParticleType<RoyalGlowParticleOption> getType() {
      return (ParticleType<RoyalGlowParticleOption>)RVParticleTypes.ROYAL_GLOW.get();
   }

   public float getRed() {
      return this.red;
   }

   public float getGreen() {
      return this.green;
   }

   public float getBlue() {
      return this.blue;
   }

   public float getSize() {
      return this.size;
   }

   public int getSpeed() {
      return this.speed;
   }
}
