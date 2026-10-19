package com.iafenvoy.iceandfire.particle;

import com.iafenvoy.iceandfire.registry.IafParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class DragonFlameParticleType extends DragonParticleType<DragonFlameParticleType> {
   private static final MapCodec<DragonFlameParticleType> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(Codec.FLOAT.fieldOf("scale").forGetter(DragonParticleType::getScale)).apply(i, DragonFlameParticleType::new)
   );

   public DragonFlameParticleType() {
      this(1.0F);
   }

   public DragonFlameParticleType(float scale) {
      super(scale);
   }

   public ParticleType<?> getType() {
      return (ParticleType<?>)IafParticles.DRAGON_FLAME.get();
   }

   public MapCodec<DragonFlameParticleType> codec() {
      return CODEC;
   }

   public StreamCodec<? super RegistryFriendlyByteBuf, DragonFlameParticleType> streamCodec() {
      return StreamCodec.composite(ByteBufCodecs.FLOAT, DragonParticleType::getScale, DragonFlameParticleType::new);
   }
}
