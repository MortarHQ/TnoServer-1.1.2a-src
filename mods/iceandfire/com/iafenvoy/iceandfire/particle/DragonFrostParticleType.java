package com.iafenvoy.iceandfire.particle;

import com.iafenvoy.iceandfire.registry.IafParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class DragonFrostParticleType extends DragonParticleType<DragonFrostParticleType> {
   private static final MapCodec<DragonFrostParticleType> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(Codec.FLOAT.fieldOf("scale").forGetter(DragonParticleType::getScale)).apply(i, DragonFrostParticleType::new)
   );

   public DragonFrostParticleType() {
      this(1.0F);
   }

   public DragonFrostParticleType(float scale) {
      super(scale);
   }

   public ParticleType<?> getType() {
      return (ParticleType<?>)IafParticles.DRAGON_FROST.get();
   }

   public MapCodec<DragonFrostParticleType> codec() {
      return CODEC;
   }

   public StreamCodec<? super RegistryFriendlyByteBuf, DragonFrostParticleType> streamCodec() {
      return StreamCodec.composite(ByteBufCodecs.FLOAT, DragonParticleType::getScale, DragonFrostParticleType::new);
   }
}
