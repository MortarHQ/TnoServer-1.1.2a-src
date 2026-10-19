package com.shin.tensura_kumodesu.mixin;

import com.shin.tensura_kumodesu.races.other.SmallLesserTaratectRace;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.client.entity.human.PlayerLikeRenderer;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import java.util.Optional;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerLikeRenderer.class)
public abstract class PlayerLikeRendererMixin<T extends TamableAnimal> {
   @Inject(
      method = "setModelProperties(Lnet/minecraft/world/entity/TamableAnimal;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;setAllVisible(Z)V", shift = Shift.AFTER)
   )
   private void kumodesu$hideParts(T entity, CallbackInfo ci) {
      if (entity instanceof CloneEntity) {
         Player player = (Player)entity.getOwner();
         if (player != null) {
            Optional<ManasRaceInstance> raceInstance = RaceAPI.getRaceFrom(player).getRace();
            if (!raceInstance.isEmpty()) {
               ManasRaceInstance race = raceInstance.get();
               PlayerModel<T> model = (PlayerModel<T>)((PlayerLikeRenderer)this).getModel();
               if (race.getRace().equals(KumodesuRaces.ARACHNE.get())) {
                  model.leftLeg.visible = false;
                  model.rightLeg.visible = false;
                  model.leftPants.visible = false;
                  model.rightPants.visible = false;
               } else if (race.getRace() instanceof SmallLesserTaratectRace) {
                  model.setAllVisible(false);
               }
            }
         }
      }
   }
}
