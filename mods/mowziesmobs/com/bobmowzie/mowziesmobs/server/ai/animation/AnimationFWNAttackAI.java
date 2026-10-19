package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class AnimationFWNAttackAI extends AnimationAI<EntityWroughtnaut> {
   protected float applyKnockback = 1.0F;
   protected float range;
   private final float arc;

   public AnimationFWNAttackAI(EntityWroughtnaut entity, float applyKnockback, float range, float arc) {
      super(entity);
      this.applyKnockback = applyKnockback;
      this.range = range;
      this.arc = arc;
   }

   @Override
   protected boolean test(Animation animation) {
      return animation == EntityWroughtnaut.ATTACK_ANIMATION
         || animation == EntityWroughtnaut.ATTACK_TWICE_ANIMATION
         || animation == EntityWroughtnaut.ATTACK_THRICE_ANIMATION;
   }

   @Override
   public void start() {
      super.start();
      if (this.entity.getAnimation() == EntityWroughtnaut.ATTACK_ANIMATION) {
         this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_PRE_SWING_1.get(), 1.5F, 1.0F);
      }
   }

   @Override
   public void stop() {
      super.stop();
   }

   private boolean shouldFollowUp(float bonusRange) {
      LivingEntity entityTarget = this.entity.getTarget();
      if (entityTarget != null && entityTarget.isAlive()) {
         Vec3 targetMoveVec = entityTarget.getDeltaMovement();
         Vec3 betweenEntitiesVec = this.entity.position().subtract(entityTarget.position());
         boolean targetComingCloser = targetMoveVec.dot(betweenEntitiesVec) > 0.0;
         return this.entity.targetDistance < this.range + bonusRange || this.entity.targetDistance < this.range + 5.0F + bonusRange && targetComingCloser;
      } else {
         return false;
      }
   }

   public void tick() {
      LivingEntity entityTarget = this.entity.getTarget();
      this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
      if (this.entity.getAnimation() == EntityWroughtnaut.ATTACK_ANIMATION) {
         if (this.entity.getAnimationTick() < 23 && entityTarget != null) {
            this.entity.lookAt(entityTarget, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.getAnimationTick() == 6) {
            this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_CREAK.get(), 0.5F, 1.0F);
         } else if (this.entity.getAnimationTick() == 25) {
            this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.2F, 1.0F);
         } else if (this.entity.getAnimationTick() == 27) {
            this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_SWING_1.get(), 1.5F, 1.0F);
            List<LivingEntity> entitiesHit = this.entity.getEntityLivingBaseNearby(this.range, 3.0, this.range, this.range);
            float damage = (float)this.entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
            boolean hit = false;

            for (LivingEntity entityHit : entitiesHit) {
               float entityHitAngle = (float)(
                  (Math.atan2(entityHit.getZ() - this.entity.getZ(), entityHit.getX() - this.entity.getX()) * (180.0 / Math.PI) - 90.0) % 360.0
               );
               float entityAttackingAngle = this.entity.yBodyRot % 360.0F;
               if (entityHitAngle < 0.0F) {
                  entityHitAngle += 360.0F;
               }

               if (entityAttackingAngle < 0.0F) {
                  entityAttackingAngle += 360.0F;
               }

               float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
               float entityHitDistance = (float)Math.sqrt(
                     (entityHit.getZ() - this.entity.getZ()) * (entityHit.getZ() - this.entity.getZ())
                        + (entityHit.getX() - this.entity.getX()) * (entityHit.getX() - this.entity.getX())
                  )
                  - entityHit.getBbWidth() / 2.0F;
               if (entityHitDistance <= this.range && entityRelativeAngle <= this.arc / 2.0F && entityRelativeAngle >= -this.arc / 2.0F
                  || entityRelativeAngle >= 360.0F - this.arc / 2.0F
                  || entityRelativeAngle <= -360.0F + this.arc / 2.0F) {
                  entityHit.hurt(this.entity.damageSources().mobAttack(this.entity), damage);
                  if (entityHit.isBlocking()) {
                     entityHit.getUseItem().hurtAndBreak(400, entityHit, LivingEntity.getSlotForHand(entityHit.getUsedItemHand()));
                  }

                  entityHit.setDeltaMovement(
                     entityHit.getDeltaMovement().x * this.applyKnockback, entityHit.getDeltaMovement().y, entityHit.getDeltaMovement().z * this.applyKnockback
                  );
                  hit = true;
               }
            }

            if (hit) {
               this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 1.0F, 0.5F);
            }
         } else if (this.entity.getAnimationTick() == 37
            && this.shouldFollowUp(2.5F)
            && this.entity.getHealthRatio() <= 0.9
            && this.entity.getRandom().nextFloat() < 0.6F) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, EntityWroughtnaut.ATTACK_TWICE_ANIMATION);
         }
      } else if (this.entity.getAnimation() == EntityWroughtnaut.ATTACK_TWICE_ANIMATION) {
         if (this.entity.getAnimationTick() < 7 && entityTarget != null) {
            this.entity.lookAt(entityTarget, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.getAnimationTick() == 10) {
            this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.2F, 1.0F);
         } else if (this.entity.getAnimationTick() == 12) {
            this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_SWING_3.get(), 1.5F, 1.0F);
            List<LivingEntity> entitiesHit = this.entity.getEntityLivingBaseNearby(this.range - 0.3, 3.0, this.range - 0.3, this.range - 0.3);
            float damage = (float)this.entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
            boolean hit = false;

            for (LivingEntity entityHit : entitiesHit) {
               float entityHitAngle = (float)(
                  (Math.atan2(entityHit.getZ() - this.entity.getZ(), entityHit.getX() - this.entity.getX()) * (180.0 / Math.PI) - 90.0) % 360.0
               );
               float entityAttackingAngle = this.entity.yBodyRot % 360.0F;
               if (entityHitAngle < 0.0F) {
                  entityHitAngle += 360.0F;
               }

               if (entityAttackingAngle < 0.0F) {
                  entityAttackingAngle += 360.0F;
               }

               float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
               float entityHitDistance = (float)Math.sqrt(
                  (entityHit.getZ() - this.entity.getZ()) * (entityHit.getZ() - this.entity.getZ())
                     + (entityHit.getX() - this.entity.getX()) * (entityHit.getX() - this.entity.getX())
               );
               if (entityHitDistance <= this.range - 0.3 && entityRelativeAngle <= this.arc / 2.0F && entityRelativeAngle >= -this.arc / 2.0F
                  || entityRelativeAngle >= 360.0F - this.arc / 2.0F
                  || entityRelativeAngle <= -360.0F + this.arc / 2.0F) {
                  entityHit.hurt(this.entity.damageSources().mobAttack(this.entity), damage);
                  if (entityHit.isBlocking()) {
                     entityHit.getUseItem().hurtAndBreak(400, entityHit, LivingEntity.getSlotForHand(entityHit.getUsedItemHand()));
                  }

                  entityHit.setDeltaMovement(
                     entityHit.getDeltaMovement().x * this.applyKnockback, entityHit.getDeltaMovement().y, entityHit.getDeltaMovement().z * this.applyKnockback
                  );
                  hit = true;
               }
            }

            if (hit) {
               this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 1.0F, 0.5F);
            }
         } else if (this.entity.getAnimationTick() == 23
            && this.shouldFollowUp(3.5F)
            && this.entity.getHealthRatio() <= 0.6
            && this.entity.getRandom().nextFloat() < 0.6F) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, EntityWroughtnaut.ATTACK_THRICE_ANIMATION);
         }
      } else if (this.entity.getAnimation() == EntityWroughtnaut.ATTACK_THRICE_ANIMATION) {
         if (this.entity.getAnimationTick() == 1) {
            this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_PRE_SWING_3.get(), 1.2F, 1.0F);
         }

         if (this.entity.getAnimationTick() < 22 && entityTarget != null) {
            this.entity.lookAt(entityTarget, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (this.entity.getAnimationTick() == 20) {
            this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.2F, 0.9F);
         } else if (this.entity.getAnimationTick() == 24) {
            this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_GRUNT_3.get(), 1.5F, 1.13F);
            this.entity
               .move(
                  MoverType.SELF,
                  new Vec3(Math.cos(Math.toRadians(this.entity.getYRot() + 90.0F)), 0.0, Math.sin(Math.toRadians(this.entity.getYRot() + 90.0F)))
               );
            List<LivingEntity> entitiesHit = this.entity.getEntityLivingBaseNearby(this.range + 0.2, 3.0, this.range + 0.2, this.range + 0.2);
            float damage = (float)this.entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
            boolean hit = false;

            for (LivingEntity entityHit : entitiesHit) {
               float entityHitDistance = (float)Math.sqrt(
                  (entityHit.getZ() - this.entity.getZ()) * (entityHit.getZ() - this.entity.getZ())
                     + (entityHit.getX() - this.entity.getX()) * (entityHit.getX() - this.entity.getX())
               );
               if (entityHitDistance <= this.range + 0.2) {
                  entityHit.hurt(this.entity.damageSources().mobAttack(this.entity), damage);
                  if (entityHit.isBlocking()) {
                     entityHit.getUseItem().hurtAndBreak(400, entityHit, LivingEntity.getSlotForHand(entityHit.getUsedItemHand()));
                  }

                  entityHit.setDeltaMovement(
                     entityHit.getDeltaMovement().x * this.applyKnockback, entityHit.getDeltaMovement().y, entityHit.getDeltaMovement().z * this.applyKnockback
                  );
                  hit = true;
               }
            }

            if (hit) {
               this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 1.0F, 0.5F);
            }
         }
      }
   }
}
