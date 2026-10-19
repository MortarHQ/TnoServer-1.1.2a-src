package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonComponent;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.damage.DamageUtil;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntitySuperNova extends EntityMagicEffect {
   public static int DURATION = 40;

   public EntitySuperNova(EntityType<? extends EntitySuperNova> type, Level world) {
      super(type, world);
   }

   public EntitySuperNova(EntityType<? extends EntitySuperNova> type, Level world, LivingEntity caster, double x, double y, double z) {
      super(type, world, caster);
      this.setPos(x, y, z);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && (this.getCaster() == null || this.getCaster().isRemoved() || !this.getCaster().isAlive())) {
         this.discard();
      }

      if (this.tickCount == 1) {
         EntityCameraShake.cameraShake(this.level(), this.position(), 30.0F, 0.05F, 10, 30);
         this.playSound((SoundEvent)MMSounds.ENTITY_SUPERNOVA_END.get(), 3.0F, 1.0F);
         if (this.level().isClientSide) {
            float scale = 8.2F;

            for (int i = 0; i < 15; i++) {
               float phaseOffset = this.random.nextFloat();
               AdvancedParticleBase.spawnParticle(
                  this.level(),
                  ParticleHandler.ARROW_HEAD,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  0.0,
                  0.0,
                  0.0,
                  false,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  8.0,
                  0.95,
                  0.9,
                  0.35,
                  1.0,
                  1.0,
                  30.0,
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.Orbit(
                        new Vec3[]{this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0)},
                        ParticleComponent.KeyTrack.startAndEnd(0.0F + phaseOffset, 1.6F + phaseOffset),
                        new ParticleComponent.KeyTrack(
                           new float[]{0.2F * scale, 0.63F * scale, 0.87F * scale, 0.974F * scale, 0.998F * scale, 1.0F * scale},
                           new float[]{0.0F, 0.15F, 0.3F, 0.45F, 0.6F, 0.75F}
                        ),
                        ParticleComponent.KeyTrack.startAndEnd(this.random.nextFloat() * 2.0F - 1.0F, this.random.nextFloat() * 2.0F - 1.0F),
                        ParticleComponent.KeyTrack.startAndEnd(this.random.nextFloat() * 2.0F - 1.0F, this.random.nextFloat() * 2.0F - 1.0F),
                        ParticleComponent.KeyTrack.startAndEnd(this.random.nextFloat() * 2.0F - 1.0F, this.random.nextFloat() * 2.0F - 1.0F),
                        false
                     ),
                     new RibbonComponent(
                        ParticleHandler.RIBBON_FLAT,
                        10,
                        0.0,
                        0.0,
                        0.0,
                        0.2F,
                        0.95,
                        0.9,
                        0.35,
                        1.0,
                        true,
                        true,
                        new ParticleComponent[]{
                           new RibbonComponent.PropertyOverLength(
                              RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F)
                           ),
                           new ParticleComponent.PropertyControl(
                              ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                           )
                        }
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                     ),
                     new ParticleComponent.FaceMotion()
                  }
               );
            }
         }
      }

      if (this.getCaster() != null) {
         float ageFrac = (float)this.tickCount / DURATION;
         float scale = (float)Math.pow(ageFrac, 0.5) * 5.0F;
         this.setBoundingBox(this.getBoundingBox().inflate(scale));
         this.setPos(this.xo, this.yo, this.zo);

         for (Entity entity : this.getEntitiesNearbyCube(Entity.class, scale)) {
            if (!(entity instanceof ItemEntity)
               && this.getCaster() != entity
               && !(entity instanceof ItemEntity)
               && (!(this.getCaster() instanceof EntityUmvuthi) || !(entity instanceof LeaderSunstrikeImmune))) {
               if (entity instanceof LivingEntity livingEntity) {
                  if (this.getCaster().canAttack(livingEntity)) {
                     float damageFire = 4.0F;
                     float damageMob = 4.0F;
                     if (this.getCaster() instanceof EntityUmvuthi) {
                        damageFire = (float)(damageFire * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
                        damageMob = (float)(damageMob * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
                     }

                     if (this.getCaster() instanceof Player) {
                        damageFire = (float)(
                           damageFire * ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get() * 0.8)
                        );
                        damageMob = (float)(
                           damageMob * ((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get() * 0.8)
                        );
                     }

                     boolean hitWithFire = (Boolean)DamageUtil.dealMixedDamage(
                           livingEntity, this.damageSources().mobProjectile(this, this.getCaster()), damageMob, this.damageSources().onFire(), damageFire
                        )
                        .getRight();
                     if (hitWithFire) {
                        Vec3 diff = livingEntity.position().subtract(this.position());
                        diff = diff.normalize();
                        livingEntity.knockback(0.4F, -diff.x, -diff.z);
                        livingEntity.igniteForSeconds(5.0F);
                     }
                  }
               } else {
                  entity.hurt(this.damageSources().mobProjectile(this, this.getCaster()), 4.0F);
               }
            }
         }
      }

      if (this.tickCount > DURATION) {
         this.discard();
      }
   }

   public float getLightLevelDependentMagicValue() {
      return 1.572888E7F;
   }
}
