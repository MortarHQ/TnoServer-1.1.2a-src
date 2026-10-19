package com.mongoose.royalvariations.client.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.StreamCodec;

public class MarkParticleOption implements ParticleOptions {
   public static final MapCodec<MarkParticleOption> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.INT.fieldOf("ownerId").forGetter(d -> d.ownerId)).apply(instance, MarkParticleOption::new)
   );
   private final int ownerId;
   public static StreamCodec<? super ByteBuf, MarkParticleOption> STREAM_CODEC = StreamCodec.of(
      (buf, option) -> buf.writeInt(option.ownerId), buf -> new MarkParticleOption(buf.readInt())
   );

   public MarkParticleOption(int ownerId) {
      this.ownerId = ownerId;
   }

   public ParticleType<MarkParticleOption> getType() {
      return (ParticleType<MarkParticleOption>)RVParticleTypes.MARK.get();
   }

   public int getOwnerId() {
      return this.ownerId;
   }
}
