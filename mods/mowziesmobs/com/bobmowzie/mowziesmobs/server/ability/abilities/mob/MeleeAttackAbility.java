package com.bobmowzie.mowziesmobs.server.ability.abilities.mob;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animation.RawAnimation;

public class MeleeAttackAbility<T extends MowzieGeckoEntity> extends Ability<T> {
   protected SoundEvent attackSound;
   protected float knockBackMultiplier = 0.0F;
   protected float range;
   protected float damageMultiplier;
   protected SoundEvent hitSound;
   protected boolean hurtInterrupts;
   protected RawAnimation[] animations;

   public MeleeAttackAbility(
      AbilityType<T, ? extends MeleeAttackAbility<T>> abilityType,
      T user,
      RawAnimation[] animations,
      SoundEvent attackSound,
      SoundEvent hitSound,
      float knockBackMultiplier,
      float range,
      float damageMultiplier,
      int startup,
      int recovery,
      boolean hurtInterrupts
   ) {
      this(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, startup),
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, recovery)
         },
         animations,
         attackSound,
         hitSound,
         knockBackMultiplier,
         range,
         damageMultiplier,
         hurtInterrupts
      );
   }

   public MeleeAttackAbility(
      AbilityType<T, ? extends MeleeAttackAbility<T>> abilityType,
      T user,
      AbilitySection[] sectionTrack,
      RawAnimation[] animations,
      SoundEvent attackSound,
      SoundEvent hitSound,
      float knockBackMultiplier,
      float range,
      float damageMultiplier,
      boolean hurtInterrupts
   ) {
      super(abilityType, user, sectionTrack, 0);
      this.attackSound = attackSound;
      this.hitSound = hitSound;
      this.knockBackMultiplier = knockBackMultiplier;
      this.damageMultiplier = damageMultiplier;
      this.range = range;
      this.hurtInterrupts = hurtInterrupts;
      this.animations = animations;
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getUser().getTarget() != null) {
         this.getUser().lookAt(this.getUser().getTarget(), 30.0F, 30.0F);
         this.getUser().getLookControl().setLookAt(this.getUser().getTarget(), 30.0F, 30.0F);
      }

      this.getUser().getNavigation().stop();
      this.getUser().getMoveControl().strafe(0.0F, 0.0F);
      this.getUser().setStrafing(false);
   }

   @Override
   public void start() {
      super.start();
      RawAnimation animation = this.animations[this.getUser().getRandom().nextInt(this.animations.length)];
      this.playAnimation(animation);
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         LivingEntity entityTarget = this.getUser().getTarget();
         if (entityTarget != null && this.getUser().targetDistance <= this.range) {
            this.getUser().doHurtTarget(entityTarget, this.damageMultiplier, this.knockBackMultiplier);
            this.onAttack(entityTarget, this.damageMultiplier, this.knockBackMultiplier);
            if (this.hitSound != null) {
               this.getUser().playSound(this.hitSound, 1.0F, 1.0F);
            }
         }

         if (this.attackSound != null) {
            this.getUser().playSound(this.attackSound, 1.0F, 1.0F);
         }
      }
   }

   protected void onAttack(LivingEntity entityTarget, float damageMultiplier, float applyKnockbackMultiplier) {
   }

   @Override
   public boolean damageInterrupts() {
      return this.hurtInterrupts;
   }
}
