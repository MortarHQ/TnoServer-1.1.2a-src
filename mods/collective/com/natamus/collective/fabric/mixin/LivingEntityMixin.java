package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import net.minecraft.class_1268;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_3218;
import net.minecraft.class_3483;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_1309.class, priority = 1001)
public abstract class LivingEntityMixin {
   @Shadow
   protected class_1799 field_6277;

   @Shadow
   public abstract class_1268 method_6058();

   @Inject(method = "tick()V", at = @At("HEAD"))
   public void LivingEntity_tick(CallbackInfo ci) {
      class_1297 entity = (class_1297)this;
      class_1937 world = entity.method_37908();
      ((CollectiveEntityEvents.Living_Tick)CollectiveEntityEvents.LIVING_TICK.invoker()).onTick(world, entity);
   }

   @Inject(method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("HEAD"), cancellable = true)
   public void LivingEntity_die(class_1282 damageSource, CallbackInfo ci) {
      class_1297 entity = (class_1297)this;
      if (!((CollectiveEntityEvents.Living_Entity_Death)CollectiveEntityEvents.LIVING_ENTITY_DEATH.invoker())
         .onDeath(entity.method_37908(), entity, damageSource)) {
         ci.cancel();
      }
   }

   @Inject(method = "calculateFallDamage(FF)I", at = @At("RETURN"), cancellable = true)
   protected void LivingEntity_calculateFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> ci) {
      class_1309 livingEntity = (class_1309)this;
      if (!livingEntity.method_5864().method_20210(class_3483.field_42971)) {
         if (((CollectiveEntityEvents.Entity_Fall_Damage_Calc)CollectiveEntityEvents.ON_FALL_DAMAGE_CALC.invoker())
               .onFallDamageCalc(livingEntity.method_37908(), livingEntity, fallDistance, damageMultiplier)
            == 0) {
            ci.setReturnValue(0);
         }
      }
   }

   @ModifyVariable(
      method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V",
      at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;max(FF)F", ordinal = 0),
      ordinal = 0,
      argsOnly = true
   )
   private float LivingEntity_actuallyHurt(float f, class_1282 damageSource, float damage) {
      class_1309 livingEntity = (class_1309)this;
      class_1937 world = livingEntity.method_37908();
      float newDamage = ((CollectiveEntityEvents.Entity_Living_Damage)CollectiveEntityEvents.ON_LIVING_DAMAGE_CALC.invoker())
         .onLivingDamageCalc(world, livingEntity, damageSource, f);
      return newDamage != -1.0F && newDamage != f ? newDamage : f;
   }

   @Inject(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
   public void LivingEntity_hurt(class_1282 damageSource, float f, CallbackInfoReturnable<Boolean> ci) {
      class_1309 livingEntity = (class_1309)this;
      class_1937 world = livingEntity.method_37908();
      if (!((CollectiveEntityEvents.Entity_Living_Attack)CollectiveEntityEvents.ON_LIVING_ATTACK.invoker())
         .onLivingAttack(world, livingEntity, damageSource, f)) {
         ci.setReturnValue(false);
      }
   }

   @Inject(method = "dropAllDeathLoot(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("TAIL"))
   protected void dropAllDeathLoot(class_3218 serverLevel, class_1282 damageSource, CallbackInfo ci) {
      class_1309 livingEntity = (class_1309)this;
      class_1937 world = livingEntity.method_37908();
      ((CollectiveEntityEvents.Entity_Is_Dropping_Loot)CollectiveEntityEvents.ON_ENTITY_IS_DROPPING_LOOT.invoker())
         .onDroppingLoot(world, livingEntity, damageSource);
   }

   @Inject(method = "jumpFromGround", at = @At("TAIL"))
   protected void LivingEntity_jumpFromGround(CallbackInfo ci) {
      class_1309 livingEntity = (class_1309)this;
      class_1937 world = livingEntity.method_37908();
      ((CollectiveEntityEvents.Entity_Is_Jumping)CollectiveEntityEvents.ON_ENTITY_IS_JUMPING.invoker()).onLivingJump(world, livingEntity);
   }
}
