package com.tensura_tno.mixin.client;

import com.tensura_tno.TensuraTNOCompatConfig;
import com.tensura_tno.ftb.STExtrasHelper;
import com.tensura_tno.util.PrestigeGuard;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "org.crypticdev.stextras.quest.assignment.RacePrestigeAssignmentService", remap = false)
public class RacePrestigeSoulGradeMixin {
   @Redirect(
      method = "tryCompleteForCurrentRace",
      at = @At(value = "INVOKE", target = "Lorg/crypticdev/stextras/storage/STExtarsStorage$Player;setSoulGrade(Lnet/minecraft/server/level/ServerPlayer;I)V"),
      remap = false
   )
   private static void tno$redirectRacePrestigeSoulGrade(ServerPlayer player, int ignoredValue) {
      if (PrestigeGuard.IN_PROGRESS.get()) {
         int current = STExtrasHelper.getSoulGrade(player);
         STExtrasHelper.setSoulGrade(player, current + TensuraTNOCompatConfig.getSoulGradePerPrestige());
      }
   }
}
