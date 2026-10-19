package com.tensura_tno.mixin.client;

import com.tensura_tno.util.RandomUniqueSkillGuard;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "org.crypticdev.stextras.storage.STExtarsStorage$Player", remap = false)
public class STExtrasLockBypassDuringRandomUniqueSkillMixin {
   @Inject(method = "getLockedSkills(Lnet/minecraft/server/level/ServerPlayer;)Ljava/util/List;", at = @At("HEAD"), cancellable = true, remap = false)
   private static void tno$bypassLockedSkillsDuringRandomUnique(ServerPlayer player, CallbackInfoReturnable<List<ResourceLocation>> cir) {
      if (RandomUniqueSkillGuard.ACTIVE.get()) {
         cir.setReturnValue(List.of());
      }
   }

   @Inject(method = "getAdminLockedSkills(Lnet/minecraft/server/level/ServerPlayer;)Ljava/util/List;", at = @At("HEAD"), cancellable = true, remap = false)
   private static void tno$bypassAdminLockedSkillsDuringRandomUnique(ServerPlayer player, CallbackInfoReturnable<List<ResourceLocation>> cir) {
      if (RandomUniqueSkillGuard.ACTIVE.get()) {
         cir.setReturnValue(List.of());
      }
   }
}
