package com.shin.tensura_kumodesu.mixin;

import com.shin.tensura_kumodesu.wrapper.PreviewRacesWrapper;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.manascore.race.impl.RaceStorage;
import io.github.manasmods.tensura.client.screen.ReincarnationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RaceAPI.class)
public abstract class RaceAPIMixin {
   @Inject(
      method = "getRaceFrom(Lnet/minecraft/world/entity/LivingEntity;)Lio/github/manasmods/manascore/race/api/Races;",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void kumodesu$previewRace(LivingEntity entity, CallbackInfoReturnable<Races> cir) {
      if (entity instanceof Player) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.screen instanceof ReincarnationScreen screen) {
            ManasRace selected = ((ReincarnationScreenAccessor)screen).kumodesu$getSelectedRace();
            if (selected != null) {
               Races original = (Races)entity.manasCore$getStorage(RaceStorage.getKey());
               cir.setReturnValue(new PreviewRacesWrapper(original, selected));
            }
         }
      }
   }
}
