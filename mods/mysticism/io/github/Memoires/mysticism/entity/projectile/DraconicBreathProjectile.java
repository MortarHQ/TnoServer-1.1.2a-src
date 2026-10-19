package io.github.Memoires.mysticism.entity.projectile;

import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.skill.intrinsic.ParalysingBreathSkill;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.magic.breath.BreathEntity;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DraconicBreathProjectile extends BreathEntity {
   public DraconicBreathProjectile(EntityType<? extends DraconicBreathProjectile> enttyType, Level level) {
      super(enttyType, level);
      this.setNoGravity(true);
      this.setBurnTicks(100);
   }

   public DraconicBreathProjectile(Level level, LivingEntity entity) {
      this((EntityType<? extends DraconicBreathProjectile>)MysticismProjectileEntityTypes.DRACONIC_BREATH_PROJECTILE.get(), level);
      this.setOwner(entity);
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.MAGIC_GENERIC;
   }

   protected boolean applyMobEffects(LivingEntity entity) {
      ManasSkill skill = this.getSkill() != null ? this.getSkill().getSkill() : null;
      if (this.getMobEffect() != null) {
         TensuraMobEffect.addEffect(entity, this.getMobEffect(), this.getOwner(), skill);
      }

      MobEffectInstance instance = new MobEffectInstance(
         TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS),
         ParalysingBreathSkill.CONFIG.paralysisDuration,
         ParalysingBreathSkill.CONFIG.paralysisLevel - 1,
         true,
         true,
         true
      );
      TensuraMobEffect.addEffect(entity, instance, this.getOwner(), skill);
      return false;
   }

   public void spawnParticle() {
      if (this.getOwner() instanceof LivingEntity owner) {
         Vec3 var21 = owner.getLookAngle().normalize();
         Vec3 pos = owner.position().add(var21.scale(1.6));
         double x = pos.x;
         double y = pos.y + owner.getEyeHeight() * 0.9F;
         double z = pos.z;
         double speed = owner.getRandom().nextDouble() * 0.35 + 0.35;

         for (int i = 0; i < 20; i++) {
            double ox = Math.random() * 0.3 - 0.15;
            double oy = Math.random() * 0.3 - 0.15;
            double oz = Math.random() * 0.3 - 0.15;
            Vec3 randomVec = new Vec3(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).normalize();
            Vec3 result = var21.scale(3.0).add(randomVec).normalize().scale(speed);
            owner.level().addParticle((ParticleOptions)TensuraParticleTypes.PLASMA_FIRE.get(), x + ox, y + oy, z + oz, result.x, result.y, result.z);
         }
      }
   }
}
