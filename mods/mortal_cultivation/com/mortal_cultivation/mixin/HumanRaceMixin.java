package com.mortal_cultivation.mixin;

import com.mortal_cultivation.registry.race.CultivationRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.race.human.HumanRace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HumanRace.class)
public abstract class HumanRaceMixin {
   @Inject(
      method = "getNextEvolutions(Lio/github/manasmods/manascore/race/api/ManasRaceInstance;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/List;",
      at = @At("RETURN"),
      cancellable = true,
      remap = false
   )
   private void addFoundationEstablishmentRaceToEvolutions(ManasRaceInstance instance, LivingEntity entity, CallbackInfoReturnable<List<ManasRace>> cir) {
      List<ManasRace> evolutions = new ArrayList<>();
      if (cir.getReturnValue() != null) {
         evolutions.addAll((Collection<? extends ManasRace>)cir.getReturnValue());
      }

      evolutions.add((ManasRace)CultivationRaces.FOUNDATION_ESTABLISHMENT_RACE.get());
      cir.setReturnValue(evolutions);
   }
}
