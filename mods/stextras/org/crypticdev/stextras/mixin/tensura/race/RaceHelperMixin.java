package org.crypticdev.stextras.mixin.tensura.race;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.tensura.race.RaceHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RaceHelper.class)
public class RaceHelperMixin {
   @Inject(method = "evolveRace(Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/manascore/race/api/ManasRace;ZZ)Z", at = @At("RETURN"))
   private static void onEvolveRace(LivingEntity entity, ManasRace race, boolean triggerRewards, boolean skipNextEvoCheck, CallbackInfoReturnable<Boolean> cir) {
      if (cir.getReturnValueZ() && entity instanceof ServerPlayer player) {
         STPlayerEvents.fireRaceEvolve(player, race);
      }
   }
}
