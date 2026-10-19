package com.faboslav.variantsandventures.common.entity.mob;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.init.VariantsAndVenturesSoundEvents;
import com.faboslav.variantsandventures.common.util.AdvancementHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public final class ThicketEntity extends Zombie {
   public ThicketEntity(EntityType<? extends Zombie> entityType, Level world) {
      super(entityType, world);
   }

   protected SoundEvent getAmbientSound() {
      return VariantsAndVenturesSoundEvents.ENTITY_THICKET_AMBIENT.get();
   }

   public void playAmbientSound() {
      SoundEvent soundEvent = this.getAmbientSound();
      if (soundEvent != null) {
         this.playSound(soundEvent, 0.35F, this.getVoicePitch());
      }
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return VariantsAndVenturesSoundEvents.ENTITY_THICKET_HURT.get();
   }

   protected void playHurtSound(DamageSource source) {
      SoundEvent soundEvent = this.getHurtSound(source);
      if (soundEvent != null) {
         this.playSound(soundEvent, 0.6F, this.getVoicePitch());
      }
   }

   protected SoundEvent getDeathSound() {
      return VariantsAndVenturesSoundEvents.ENTITY_THICKET_DEATH.get();
   }

   protected SoundEvent getStepSound() {
      return VariantsAndVenturesSoundEvents.ENTITY_THICKET_STEP.get();
   }

   public void tick() {
      if (!VariantsAndVentures.getConfig().enableThicket) {
         this.discard();
      }

      super.tick();
   }

   public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
      boolean isPoison = mobEffectInstance.is(MobEffects.POISON);
      return !isPoison && super.canBeAffected(mobEffectInstance);
   }

   public boolean doHurtTarget(Entity source) {
      this.level().broadcastEntityEvent(this, (byte)4);
      this.playSound(VariantsAndVenturesSoundEvents.ENTITY_THICKET_ATTACK.get(), 0.6F, this.getVoicePitch());
      boolean attackResult = super.doHurtTarget(source);
      if (attackResult && this.getMainHandItem().isEmpty() && source instanceof LivingEntity) {
         ((LivingEntity)source).addEffect(new MobEffectInstance(MobEffects.POISON, 100), this);
      }

      return attackResult;
   }

   public void die(DamageSource damageSource) {
      super.die(damageSource);
      AdvancementHelper.triggerMonsterHunter(this.level(), damageSource);
   }
}
