package team.creative.creativecore.common.config.premade;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import team.creative.creativecore.common.config.api.CreativeConfig;
import team.creative.creativecore.common.config.premade.registry.RegistryObjectConfig;

public class MobEffectConfig {
   @CreativeConfig
   public RegistryObjectConfig<MobEffect> effect;
   @CreativeConfig
   public int amplifier;
   @CreativeConfig
   public int duration;
   @CreativeConfig
   public boolean hideParticles;

   public MobEffectConfig(Registry<MobEffect> registry, ResourceLocation effect, int amplifier, int duration, boolean hideParticles) {
      this.effect = new RegistryObjectConfig<>(registry, effect);
      this.amplifier = amplifier;
      this.duration = duration;
      this.hideParticles = hideParticles;
   }

   public MobEffectInstance create() {
      return new MobEffectInstance(this.effect.registry.wrapAsHolder(this.effect.value), this.duration, this.amplifier, false, this.hideParticles);
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof MobEffectConfig mob)
         ? super.equals(obj)
         : mob.effect.equals(this.effect) && mob.amplifier == this.amplifier && mob.duration == this.duration && mob.hideParticles == this.hideParticles;
   }
}
