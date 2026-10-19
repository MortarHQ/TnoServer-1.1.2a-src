package com.notunanancyowen.spears.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.components.AttackRange;
import com.notunanancyowen.spears.components.SwingAnimation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin {
   @Shadow
   private int ticksUntilNextAttack;
   @Shadow
   @Final
   protected PathfinderMob mob;

   @Inject(method = "resetAttackCooldown()V", at = @At("TAIL"))
   private void longerAttackCooldownForSpears(CallbackInfo ci) {
      if (this.mob.getMainHandItem().get(Spears.PIERCING_WEAPON) != null) {
         this.mob.setYBodyRot(this.mob.getYHeadRot());
      }

      if (this.mob.getMainHandItem().get(Spears.SWING_ANIMATION) instanceof SwingAnimation s) {
         this.ticksUntilNextAttack = this.ticksUntilNextAttack + Math.max(s.swingTicks() - 6, 0);
      }
   }

   @WrapOperation(
      method = "tick()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/navigation/PathNavigation;moveTo(Lnet/minecraft/world/entity/Entity;D)Z")
   )
   private boolean applyMinimumAttackDistanceForSpears(PathNavigation instance, Entity entity, double speed, Operation<Boolean> original) {
      if (!(this.mob.getMainHandItem().get(Spears.ATTACK_RANGE) instanceof AttackRange a)) {
         return (Boolean)original.call(new Object[]{instance, entity, speed});
      } else {
         Path path = instance.createPath(entity, (int)a.minReach());
         return path != null && instance.moveTo(path, speed);
      }
   }
}
