package io.github.Memoires.mysticism.mixin.races;

import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.race.demon.ArchDaemonRace;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArchDaemonRace.class)
public class MixinArchDaemon {
   @Inject(
      method = "getNextEvolutions(Lio/github/manasmods/manascore/race/api/ManasRaceInstance;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/List;",
      at = @At("RETURN"),
      cancellable = true
   )
   private void addEvolution(ManasRaceInstance instance, LivingEntity entity, CallbackInfoReturnable<List<ManasRace>> cir) {
      List<ManasRace> original = (List<ManasRace>)cir.getReturnValue();
      List<ManasRace> modified = new ArrayList<>(original);
      ManasRace archDoll = (ManasRace)MysticismRaces.ARCH_DOLL.get();
      if (!modified.contains(archDoll)) {
         modified.add(archDoll);
      }

      cir.setReturnValue(modified);
   }
}
