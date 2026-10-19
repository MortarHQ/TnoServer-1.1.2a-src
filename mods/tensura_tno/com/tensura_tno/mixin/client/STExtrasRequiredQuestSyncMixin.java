package com.tensura_tno.mixin.client;

import com.tensura_tno.ftb.STExtrasHelper;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "org.crypticdev.stextras.quest.assignment.RequiredQuestSelector", remap = false)
public class STExtrasRequiredQuestSyncMixin {
   @Inject(method = "applyRequiredOnCharacterReset(Lnet/minecraft/server/level/ServerPlayer;I)V", at = @At("TAIL"), remap = false, require = 0)
   private static void tensuraTno$syncRequiredQuests(ServerPlayer player, int prestige, CallbackInfo ci) {
      STExtrasHelper.syncQuestState(player);
   }
}
