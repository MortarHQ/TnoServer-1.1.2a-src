package com.shin.tensura_kumodesu.mixin;

import com.shin.tensura_kumodesu.client.renderer.layers.TaratectLayer;
import com.shin.tensura_kumodesu.races.other.SmallLesserTaratectRace;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import java.util.Optional;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRenderMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   public PlayerRenderMixin(Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
      super(context, entityModel, f);
   }

   @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V", at = @At("RETURN"))
   private void addTaratectLayer(Context context, boolean slim, CallbackInfo ci) {
      this.addLayer(new TaratectLayer(context));
   }

   @Inject(method = "setModelProperties(Lnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At("TAIL"))
   private void renderConditional(AbstractClientPlayer player, CallbackInfo ci) {
      if (player != null) {
         PlayerRenderer renderer = (PlayerRenderer)this;
         PlayerModel<AbstractClientPlayer> model = (PlayerModel<AbstractClientPlayer>)renderer.getModel();

         Optional<ManasRaceInstance> raceInstance;
         try {
            Races raceHolder = RaceAPI.getRaceFrom(player);
            if (raceHolder == null) {
               return;
            }

            raceInstance = raceHolder.getRace();
         } catch (Exception e) {
            return;
         }

         if (!raceInstance.isEmpty()) {
            ManasRaceInstance race = raceInstance.get();
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
