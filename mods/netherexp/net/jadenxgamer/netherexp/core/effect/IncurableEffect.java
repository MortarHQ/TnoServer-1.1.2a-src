package net.jadenxgamer.netherexp.core.effect;

import java.util.Set;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.common.EffectCure;

public class IncurableEffect extends JNEMobEffect {
   public IncurableEffect(MobEffectCategory category, int color) {
      super(category, color);
   }

   public IncurableEffect(MobEffectCategory category, int color, ParticleOptions particle) {
      super(category, color, particle);
   }

   public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
      cures.clear();
   }
}
