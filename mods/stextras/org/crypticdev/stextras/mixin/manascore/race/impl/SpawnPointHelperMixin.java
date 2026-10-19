package org.crypticdev.stextras.mixin.manascore.race.impl;

import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.SpawnPointHelper;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.race.wispfolk.WispfolkRace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnPointHelper.class)
public abstract class SpawnPointHelperMixin {
   @Inject(method = "teleportToNewSpawn(Lnet/minecraft/server/level/ServerPlayer;)V", at = @At("HEAD"), cancellable = true)
   private static void stextras$overrideWispfolkSpawn(ServerPlayer player, CallbackInfo ci) {
      RaceAPI.getRaceFrom(player).getRace().ifPresent(race -> {
         if (WispfolkRace.usesLabyrinthStructureSpawn(race, player)) {
            WispfolkRace.teleportToLabyrinthStructure(player);
            ci.cancel();
         }
      });
   }
}
