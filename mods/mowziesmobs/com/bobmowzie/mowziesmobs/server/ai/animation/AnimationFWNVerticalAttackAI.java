package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AnimationFWNVerticalAttackAI extends AnimationAttackAI<EntityWroughtnaut> {
   private final float arc;

   public AnimationFWNVerticalAttackAI(EntityWroughtnaut entity, Animation animation, SoundEvent sound, float applyKnockback, float range, float arc) {
      super(entity, animation, sound, null, applyKnockback, range, 0.0F, 0);
      this.arc = arc;
   }

   @Override
   public void start() {
      super.start();
      this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_PRE_SWING_2.get(), 1.5F, 1.0F);
   }

   @Override
   public void tick() {
      this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
      if (this.entity.getAnimationTick() < 21 && this.entityTarget != null) {
         this.entity.lookAt(this.entityTarget, 30.0F, 30.0F);
      } else {
         this.entity.setYRot(this.entity.yRotO);
      }

      if (this.entity.getAnimationTick() == 6) {
         this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_CREAK.get(), 0.5F, 1.0F);
      } else if (this.entity.getAnimationTick() == 25) {
         this.entity.playSound(this.attackSound, 1.2F, 1.0F);
      } else if (this.entity.getAnimationTick() == 27) {
         this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_SWING_2.get(), 1.5F, 1.0F);
         List<LivingEntity> entitiesHit = this.entity.getEntityLivingBaseNearby(this.range, 3.0, this.range, this.range);
         float damage = (float)this.entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();

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
            if (entityHitDistance <= this.range && entityRelativeAngle <= this.arc / 2.0F && entityRelativeAngle >= -this.arc / 2.0F
               || entityRelativeAngle >= 360.0F - this.arc / 2.0F
               || entityRelativeAngle <= -360.0F + this.arc / 2.0F) {
               entityHit.hurt(this.entity.damageSources().mobAttack(this.entity), damage * 1.5F);
               if (entityHit.isBlocking()) {
                  entityHit.getUseItem().hurtAndBreak(400, entityHit, LivingEntity.getSlotForHand(entityHit.getUsedItemHand()));
               }

               entityHit.setDeltaMovement(
                  entityHit.getDeltaMovement().x * this.applyKnockbackMultiplier,
                  entityHit.getDeltaMovement().y,
                  entityHit.getDeltaMovement().z * this.applyKnockbackMultiplier
               );
            }
         }
      } else if (this.entity.getAnimationTick() == 28) {
         this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_LAND.get(), 1.0F, 0.5F);
         EntityCameraShake.cameraShake(this.entity.level(), this.entity.position(), 20.0F, 0.3F, 0, 10);
      } else if (this.entity.getAnimationTick() == 44) {
         this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_PULL_1.get(), 1.0F, 1.0F);
         this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_CREAK.get(), 0.5F, 1.0F);
      } else if (this.entity.getAnimationTick() == 75) {
         this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_PULL_5.get(), 1.0F, 1.0F);
      } else if (this.entity.getAnimationTick() == 83) {
         this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_RELEASE_2.get(), 1.0F, 1.0F);
      }

      if (this.entity.getAnimationTick() > 26 && this.entity.getAnimationTick() < 85) {
         this.entity.vulnerable = true;
         this.entity.setYRot(this.entity.yRotO);
         this.entity.yBodyRot = this.entity.yBodyRotO;
      } else {
         this.entity.vulnerable = false;
      }
   }

   @Override
   public void stop() {
      super.stop();
      this.entity.vulnerable = false;
   }
}
