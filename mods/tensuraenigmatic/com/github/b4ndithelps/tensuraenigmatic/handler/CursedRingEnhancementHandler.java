package com.github.b4ndithelps.tensuraenigmatic.handler;

import auviotre.enigmatic.legacy.registries.EnigmaticItems;
import com.github.b4ndithelps.tensuraenigmatic.config.TensuraEnigmaticConfig;
import com.github.b4ndithelps.tensuraenigmatic.util.CurioHelper;
import com.github.b4ndithelps.tensuraenigmatic.util.TensuraEnergyHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public class CursedRingEnhancementHandler {
   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (!event.getEntity().level().isClientSide()
         && event.getEntity().tickCount % 10 == 0
         && CurioHelper.hasCurio(event.getEntity(), EnigmaticItems.CURSED_RING)) {
         double maxMagicule = event.getEntity().getAttributeValue(TensuraAttributes.MAX_MAGICULE);
         TensuraEnergyHelper.restoreMagicule(event.getEntity(), maxMagicule * getMagiculeRecoveryPercentage() / 100.0);
      }
   }

   public static double getMagiculeRecoveryPercentage() {
      return (Double)TensuraEnigmaticConfig.CURSED_RING_MAGICULE_RECOVERY_PERCENT.get();
   }
}
