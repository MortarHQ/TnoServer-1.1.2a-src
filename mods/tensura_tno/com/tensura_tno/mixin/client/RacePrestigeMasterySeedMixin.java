package com.tensura_tno.mixin.client;

import com.tensura_tno.ftb.MasterySeedHelper;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "org.crypticdev.stextras.quest.assignment.RacePrestigeAssignmentService", remap = false)
public class RacePrestigeMasterySeedMixin {
   @Inject(method = "ensureForCurrentRace(Lnet/minecraft/server/level/ServerPlayer;ZZ)V", at = @At("RETURN"), remap = false)
   private static void tno$seedMasteryOnActivation(ServerPlayer player, boolean statFirst, boolean sync, CallbackInfo ci) {
      MasterySeedHelper.seedMasteryQuests(player);
   }

   @Inject(
      method = "applyRaceQuests(Lnet/minecraft/server/level/ServerPlayer;Lorg/crypticdev/stextras/storage/quest/STExtrasQuestStorage;Ljava/util/List;Ljava/util/Set;I)Z",
      at = @At("RETURN"),
      remap = false
   )
   private static void tno$seedMasteryOnEventActivation(
      ServerPlayer player,
      @Coerce Object store,
      List<?> racePool,
      Set<ResourceLocation> triggeredQuestIds,
      int currentActionAmount,
      CallbackInfoReturnable<Boolean> cir
   ) {
      if (cir.getReturnValueZ()) {
         MasterySeedHelper.seedMasteryQuests(player);
      }
   }
}
