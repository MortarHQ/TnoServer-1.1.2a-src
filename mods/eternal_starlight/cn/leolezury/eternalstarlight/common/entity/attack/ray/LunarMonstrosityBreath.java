package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LunarMonstrosityBreath extends RayAttack {
   public LunarMonstrosityBreath(EntityType<? extends LunarMonstrosityBreath> type, Level level) {
      super(type, level);
   }

   public LunarMonstrosityBreath(
      EntityType<? extends LunarMonstrosityBreath> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch
   ) {
      super(type, world, caster, x, y, z, yaw, pitch);
   }

   @Override
   public float getAttackDamage() {
      return 3.0F;
   }

   @Override
   public float getRotationSpeed() {
      return 0.9F;
   }

   @Override
   public void doHurtTarget(LivingEntity target) {
      this.getCaster()
         .ifPresent(
            caster -> {
               if (ESEntityUtil.shouldHarm(caster, target)
                  && target.hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.POISON, this, caster), this.getAttackDamage())
                  && !target.hasEffect(MobEffects.POISON)) {
                  target.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
               }
            }
         );
   }

   @Override
   public void updatePosition() {
      this.getCaster().ifPresentOrElse(caster -> {
         if (caster instanceof LunarMonstrosity monstrosity) {
            this.setPos(caster.position().add(0.0, caster.getBbHeight(), 0.0));
            if (monstrosity.getBehaviorState() != 1 || monstrosity.isDeadOrDying()) {
               this.discard();
            }
         }
      }, this::discard);
   }

   @Override
   public void addEndParticles(Vec3 endPos) {
      Vec3 from = this.getCaster().isPresent() && this.getCaster().get() instanceof LunarMonstrosity monstrosity ? monstrosity.headPos : this.position();
      Vec3 delta = endPos.subtract(from);

      for (int i = 0; i < 10; i++) {
         double dx = delta.x();
         double dy = delta.y();
         double dz = delta.z();
         double spread = 5.0 + this.random.nextFloat() * 2.5;
         double velocity = 3.0 + this.random.nextFloat() * 0.15;
         dx += this.random.nextGaussian() * 0.0075 * spread;
         dy += this.random.nextGaussian() * 0.0075 * spread;
         dz += this.random.nextGaussian() * 0.0075 * spread;
         dx *= velocity;
         dy *= velocity;
         dz *= velocity;
         endPos.add((this.random.nextFloat() - 0.5F) * 2.5F, (this.random.nextFloat() - 0.5F) * 2.5F, (this.random.nextFloat() - 0.5F) * 2.5F);
         this.level().addParticle(ESSmokeParticleOptions.LUNAR_BREATH, from.x, from.y, from.z, dx, dy, dz);
      }
   }
}
