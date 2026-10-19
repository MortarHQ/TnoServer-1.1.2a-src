package com.github.b4ndithelps.tennogamenolife.client;

import com.github.b4ndithelps.tennogamenolife.race.ExMachina.ExMachinaDecorativeManager;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.IExMachinaDecorativeRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife", value = Dist.CLIENT)
public final class ClientEvents {
   private ClientEvents() {
   }

   @SubscribeEvent
   public static void onClientPlayerTick(Post event) {
      if (event.getEntity().level().isClientSide()) {
         boolean isExMachina = RaceAPI.getRaceFrom(event.getEntity())
            .getRace()
            .map(instance -> instance.getRace() instanceof IExMachinaDecorativeRace)
            .orElse(false);
         if (isExMachina) {
            ExMachinaDecorativeManager.updateDecorativeState(event.getEntity());
         }
      }
   }
}
