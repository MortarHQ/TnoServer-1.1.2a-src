package com.notunanancyowen.spears.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.notunanancyowen.spears.goals.UseSpearGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Zombie.class)
public abstract class ZombieEntityMixin {
   @WrapOperation(
      method = "addBehaviourGoals()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 0)
   )
   private void addSpearAttack(GoalSelector instance, int priority, Goal goal, Operation<Void> original) {
      instance.addGoal(priority, new UseSpearGoal((Zombie)this, 1.0, 1.0, 10.0F, 2.0F));
      original.call(new Object[]{instance, priority + 1, goal});
   }
}
