package io.github.Memoires.mysticism.entity.item;

import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LightningEssenceItemEntity extends ItemEntity {
   public LightningEssenceItemEntity(Level level, double x, double y, double z, ItemStack itemStack) {
      super(level, x, y, z, itemStack);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide && this.tickCount >= 600) {
         TensuraParticleHelper.addServerParticlesAroundSelf(this, (ParticleOptions)TensuraParticleTypes.LIGHTNING_EFFECT.get());
         this.discard();
      }
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      return damageSource.is(DamageTypeTags.IS_LIGHTNING) ? false : super.hurt(damageSource, amount);
   }
}
