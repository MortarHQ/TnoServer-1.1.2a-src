package org.crypticdev.stextras.mixin.world.level;

import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Key;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRules.class)
public class GameRulesMixin {
   @Inject(method = "getInt(Lnet/minecraft/world/level/GameRules$Key;)I", at = @At("HEAD"), cancellable = true)
   private void stextras$forceResetPerSkillLockToZero(Key<IntegerValue> key, CallbackInfoReturnable<Integer> cir) {
      if (key == TensuraGameRules.RESET_PER_SKILL_LOCK) {
         cir.setReturnValue(0);
      }
   }
}
