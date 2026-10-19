package com.faboslav.friendsandfoes.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Bee.BeeGoToKnownFlowerGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BeeGoToKnownFlowerGoal.class)
public class BeeGoToKnownFlowerGoalMixin {
   @Final
   @Shadow(aliases = {"field_226508_a_", "field_20372", "f_28009_"})
   private Bee this$0;

   @ModifyReceiver(
      method = "<init>(Lnet/minecraft/world/entity/animal/Bee;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I")
   )
   private RandomSource friendsandfoes$fixGoalRandomSourceUsage2(RandomSource randomSource, int range) {
      return this.this$0.getRandom();
   }
}
