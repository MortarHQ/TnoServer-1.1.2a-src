package org.crypticdev.stextras.mixin.manascore.skill.api;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.EntityHitResult;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.event.SkillInteractionSupport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ManasSkillInstance.class)
public abstract class ManasSkillInstanceMixin {
   @Unique
   private double stextras$masteryBefore;

   @Inject(method = "addMasteryPoint(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"))
   private void stextras$captureBefore(LivingEntity entity, CallbackInfo ci) {
      this.stextras$masteryBefore = ((ManasSkillInstance)this).getMastery();
   }

   @Inject(method = "addMasteryPoint(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("TAIL"))
   private void stextras$afterAdd(LivingEntity entity, CallbackInfo ci) {
      if (!entity.level().isClientSide) {
         ManasSkillInstance inst = (ManasSkillInstance)this;
         STPlayerEvents.fireMasteryPoint(entity, inst, this.stextras$masteryBefore, inst.getMastery());
      }
   }

   @Inject(method = "onSkillMastered(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("TAIL"))
   private void stextras$onMastered(LivingEntity entity, CallbackInfo ci) {
      if (!entity.level().isClientSide) {
         STPlayerEvents.fireSkillMastered(entity, (ManasSkillInstance)this);
      }
   }

   @Inject(method = "onPressed(Lnet/minecraft/world/entity/LivingEntity;II)V", at = @At("TAIL"))
   private void stextras$onPressed(LivingEntity entity, int keyNumber, int mode, CallbackInfo ci) {
      this.stextras$fireActiveInteraction(entity, STPlayerEvents.SkillInteractionType.PRESS);
   }

   @Inject(method = "onRelease(Lnet/minecraft/world/entity/LivingEntity;III)V", at = @At("TAIL"))
   private void stextras$onRelease(LivingEntity entity, int heldTicks, int keyNumber, int mode, CallbackInfo ci) {
      this.stextras$fireActiveInteraction(entity, STPlayerEvents.SkillInteractionType.RELEASE);
   }

   @Inject(method = "onToggleOn(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("TAIL"))
   private void stextras$onToggleOn(LivingEntity entity, CallbackInfo ci) {
      this.stextras$fireActiveInteraction(entity, STPlayerEvents.SkillInteractionType.TOGGLE_ON);
   }

   @Inject(method = "onBeingDamaged(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("TAIL"))
   private void stextras$onBeingDamaged(LivingEntity entity, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
      this.stextras$firePassiveInteraction(entity, SkillInteractionSupport.PassiveCallback.BEING_DAMAGED, STPlayerEvents.SkillInteractionType.BEING_DAMAGED);
   }

   @Inject(
      method = "onDamageEntity(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;Lio/github/manasmods/manascore/network/api/util/Changeable;)Z",
      at = @At("TAIL")
   )
   private void stextras$onDamageEntity(
      LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount, CallbackInfoReturnable<Boolean> cir
   ) {
      this.stextras$firePassiveInteraction(owner, SkillInteractionSupport.PassiveCallback.DAMAGE_ENTITY, STPlayerEvents.SkillInteractionType.DAMAGE_ENTITY);
   }

   @Inject(
      method = "onTouchEntity(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;Lio/github/manasmods/manascore/network/api/util/Changeable;)Z",
      at = @At("TAIL")
   )
   private void stextras$onTouchEntity(
      LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount, CallbackInfoReturnable<Boolean> cir
   ) {
      this.stextras$firePassiveInteraction(owner, SkillInteractionSupport.PassiveCallback.TOUCH_ENTITY, STPlayerEvents.SkillInteractionType.TOUCH_ENTITY);
   }

   @Inject(
      method = "onTakenDamage(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;Lio/github/manasmods/manascore/network/api/util/Changeable;)Z",
      at = @At("TAIL")
   )
   private void stextras$onTakenDamage(LivingEntity owner, DamageSource source, Changeable<Float> amount, CallbackInfoReturnable<Boolean> cir) {
      this.stextras$firePassiveInteraction(owner, SkillInteractionSupport.PassiveCallback.TAKEN_DAMAGE, STPlayerEvents.SkillInteractionType.TAKEN_DAMAGE);
   }

   @Inject(
      method = "onEffectAdded(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;Lio/github/manasmods/manascore/network/api/util/Changeable;)Z",
      at = @At("TAIL")
   )
   private void stextras$onEffectAdded(LivingEntity entity, Entity source, Changeable<MobEffectInstance> instance, CallbackInfoReturnable<Boolean> cir) {
      this.stextras$firePassiveInteraction(entity, SkillInteractionSupport.PassiveCallback.EFFECT_ADDED, STPlayerEvents.SkillInteractionType.EFFECT_ADDED);
   }

   @Inject(method = "onBeingTargeted(Lio/github/manasmods/manascore/network/api/util/Changeable;Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("TAIL"))
   private void stextras$onBeingTargeted(Changeable<LivingEntity> owner, LivingEntity mob, CallbackInfoReturnable<Boolean> cir) {
      if (owner != null) {
         this.stextras$firePassiveInteraction(
            (LivingEntity)owner.get(), SkillInteractionSupport.PassiveCallback.BEING_TARGETED, STPlayerEvents.SkillInteractionType.BEING_TARGETED
         );
      }
   }

   @Inject(
      method = "onProjectileHit(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/phys/EntityHitResult;Lnet/minecraft/world/entity/projectile/Projectile;Lio/github/manasmods/manascore/network/api/util/Changeable;Lio/github/manasmods/manascore/network/api/util/Changeable;)V",
      at = @At("TAIL")
   )
   private void stextras$onProjectileHit(
      LivingEntity living,
      EntityHitResult hitResult,
      Projectile projectile,
      Changeable<ProjectileDeflection> deflection,
      Changeable<ProjectileHitResult> result,
      CallbackInfo ci
   ) {
      this.stextras$firePassiveInteraction(living, SkillInteractionSupport.PassiveCallback.PROJECTILE_HIT, STPlayerEvents.SkillInteractionType.PROJECTILE_HIT);
   }

   @Unique
   private void stextras$fireActiveInteraction(LivingEntity entity, STPlayerEvents.SkillInteractionType type) {
      if (!entity.level().isClientSide) {
         STPlayerEvents.fireSkillInteraction(entity, (ManasSkillInstance)this, type);
      }
   }

   @Unique
   private void stextras$firePassiveInteraction(LivingEntity entity, SkillInteractionSupport.PassiveCallback callback, STPlayerEvents.SkillInteractionType type) {
      if (entity != null && !entity.level().isClientSide) {
         ManasSkillInstance instance = (ManasSkillInstance)this;
         if (SkillInteractionSupport.overridesPassiveCallback(instance, callback)) {
            STPlayerEvents.fireSkillInteraction(entity, instance, type);
         }
      }
   }
}
