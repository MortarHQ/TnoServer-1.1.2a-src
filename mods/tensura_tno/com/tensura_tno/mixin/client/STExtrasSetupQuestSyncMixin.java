package com.tensura_tno.mixin.client;

import com.tensura_tno.ftb.STExtrasHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "org.crypticdev.stextras.quest.assignment.PlayerQuestAssignmentService", remap = false)
public class STExtrasSetupQuestSyncMixin {
   @Inject(
      method = "setupQuests(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V",
      at = @At("TAIL"),
      remap = false,
      require = 0
   )
   private static void tensuraTno$syncAfterSetupQuests(MinecraftServer server, ServerPlayer player, CallbackInfo ci) {
      STExtrasHelper.syncQuestState(player);
   }

   @Inject(
      method = "ensurePlayerMatchesSchedule(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V",
      at = @At("TAIL"),
      remap = false,
      require = 0
   )
   private static void tensuraTno$syncAfterScheduleCheck(MinecraftServer server, ServerPlayer player, CallbackInfo ci) {
      STExtrasHelper.syncQuestState(player);
   }
}
