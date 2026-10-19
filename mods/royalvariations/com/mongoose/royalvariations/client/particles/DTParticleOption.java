package com.mongoose.royalvariations.client.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.StreamCodec;

public class DTParticleOption implements ParticleOptions {
   public static final MapCodec<DTParticleOption> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.INT.fieldOf("ownerId").forGetter(d -> d.ownerId)).apply(instance, DTParticleOption::new)
   );
   public static StreamCodec<? super ByteBuf, DTParticleOption> STREAM_CODEC = StreamCodec.of(
      (buf, option) -> buf.writeInt(option.ownerId), buf -> new DTParticleOption(buf.readInt())
   );
   private final int ownerId;

   public DTParticleOption(int ownerId) {
      this.ownerId = ownerId;
   }

   public ParticleType<DTParticleOption> getType() {
      return (ParticleType<DTParticleOption>)RVParticleTypes.DETONATION_TIMER.get();
   }

   public int getOwnerId() {
      return this.ownerId;
   }
}
