package com.tensura_tno.mixin.client;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import io.github.manasmods.tensura.race.TensuraRace;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResetScrollItem.class)
public abstract class ResetScrollHumanBaseStatsFixMixin {
   @Inject(
      method = "resetEverything",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/tensura/item/misc/ResetScrollItem;resetRaceFailsafe(Lnet/minecraft/world/entity/player/Player;)V",
         shift = Shift.AFTER
      ),
      require = 0,
      remap = false
   )
   private static void tensuraTno$applyHumanBaseStatsAfterFailsafe(ServerPlayer player, CallbackInfo ci) {
      ManasRace human = (ManasRace)TensuraRaces.HUMAN.get();
      if (human instanceof TensuraRace tensuraRace) {
         tensuraRace.resetExistenceData(player);
      }
   }
}
