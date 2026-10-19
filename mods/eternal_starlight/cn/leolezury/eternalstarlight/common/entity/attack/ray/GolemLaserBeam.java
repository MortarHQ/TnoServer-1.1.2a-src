package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GolemLaserBeam extends RayAttack {
   public GolemLaserBeam(EntityType<? extends GolemLaserBeam> type, Level world) {
      super(type, world);
   }

   public GolemLaserBeam(EntityType<? extends GolemLaserBeam> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch) {
      super(type, world, caster, x, y, z, yaw, pitch);
   }

   @Override
   public float getAttackDamage() {
      return this.getCaster().isPresent() && this.getCaster().get() instanceof StarlightGolem
         ? 4.0F
         : 3.0F
            + (
               this.getCaster().isPresent() && this.getCaster().get() instanceof SpellCaster
                  ? ESDataAttachments.SPELL_CAST_DATA.getData(this.getCaster().get()).strength() * 0.5F
                  : 0.0F
            );
   }

   @Override
   public void updatePosition() {
      if (this.tickCount % 15 == 0) {
         this.playSound(ESSoundEvents.LASER_BEAM_HUM.get());
      }

      this.getCaster()
         .ifPresentOrElse(
            caster -> {
               this.setPos(this.getPositionForCaster(caster, caster.position()));
               if (caster instanceof StarlightGolem golem) {
                  if (golem.getBehaviorState() != 1 || !golem.isAlive()) {
                     this.discard();
                  }
               } else if (caster instanceof SpellCaster
                  && (
                     !ESDataAttachments.SPELL_CAST_DATA.getData(caster).hasSpell()
                        || ESDataAttachments.SPELL_CAST_DATA.getData(caster).spell() != ESSpells.LASER_BEAM.get()
                  )) {
                  this.discard();
               }
            },
            this::discard
         );
   }

   @Override
   public void addEndParticles(Vec3 endPos) {
      Vec3 offset = endPos.subtract(this.position());
      Vec3 particlePos = this.position().add(offset.normalize().scale(offset.length() - 0.5));

      for (int i = 0; i < 3; i++) {
         this.level()
            .addParticle(
               ExplosionShockParticleOptions.ENERGY,
               particlePos.x,
               particlePos.y,
               particlePos.z,
               (this.getRandom().nextDouble() - 0.5) * 0.2,
               (this.getRandom().nextDouble() - 0.5) * 0.2,
               (this.getRandom().nextDouble() - 0.5) * 0.2
            );
      }
   }

   public boolean shouldRender(double x, double y, double z) {
      return this.shouldRenderAtSqrDistance(this.distanceToSqr(x, y, z) / 16.0);
   }

   @Override
   public Vec3 getPositionForCaster(Entity caster, Vec3 casterPos) {
      return caster instanceof StarlightGolem
         ? casterPos.add(0.0, caster.getBbHeight() / 2.5F, 0.0)
         : casterPos.add(0.0, caster.getEyeHeight(caster.getPose()), 0.0);
   }
}
