package com.cerbon.bosses_of_mass_destruction.particle;

import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import net.minecraft.core.particles.ParticleOptions;

public class ParticleFactories {
   public static ClientParticleBuilder cometTrail() {
      return new ClientParticleBuilder((ParticleOptions)BMDParticles.DISAPPEARING_SWIRL.get())
         .color(f -> MathUtils.lerpVec(f, Vec3Colors.COMET_BLUE, Vec3Colors.FADED_COMET_BLUE))
         .brightness(15728880)
         .scale(f -> 0.5F + f * 0.3F);
   }

   public static ClientParticleBuilder soulFlame() {
      return new ClientParticleBuilder((ParticleOptions)BMDParticles.SOUL_FLAME.get()).brightness(15728880);
   }
}
