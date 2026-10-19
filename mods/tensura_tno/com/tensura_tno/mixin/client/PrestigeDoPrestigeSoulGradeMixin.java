package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tensura_tno.TensuraTNOCompatConfig;
import com.tensura_tno.util.PrestigeGuard;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "org.crypticdev.stextras.utils.PrestigeUtils", remap = false)
public class PrestigeDoPrestigeSoulGradeMixin {
   @WrapMethod(method = "doPrestige(Lnet/minecraft/server/level/ServerPlayer;)V", remap = false)
   private static void tno$withPrestigeFlag(ServerPlayer player, Operation<Void> original) {
      if (!PrestigeGuard.BLOCKED.get()) {
         boolean wasInProgress = PrestigeGuard.IN_PROGRESS.get();
         PrestigeGuard.IN_PROGRESS.set(true);

         try {
            original.call(new Object[]{player});
         } finally {
            PrestigeGuard.IN_PROGRESS.set(wasInProgress);
         }
      }
   }

   @ModifyArg(
      method = "doPrestige",
      at = @At(value = "INVOKE", target = "Lorg/crypticdev/stextras/storage/STExtarsStorage$Player;setSoulGrade(Lnet/minecraft/server/level/ServerPlayer;I)V"),
      index = 1,
      remap = false
   )
   private static int tno$modifySoulGradeGain(int original) {
      return original - 1 + TensuraTNOCompatConfig.getSoulGradePerPrestige();
   }
}
