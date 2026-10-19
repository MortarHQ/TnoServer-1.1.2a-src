package team.lodestar.lodestone.systems.particle.world.type;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

public abstract class AbstractLodestoneParticleType<T extends WorldParticleOptions> extends ParticleType<T> {
   protected AbstractLodestoneParticleType() {
      super(false);
   }

   public AbstractLodestoneParticleType<T> getType() {
      return this;
   }

   public MapCodec<T> codec() {
      throw new UnsupportedOperationException();
   }

   public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
      throw new UnsupportedOperationException();
   }
}
