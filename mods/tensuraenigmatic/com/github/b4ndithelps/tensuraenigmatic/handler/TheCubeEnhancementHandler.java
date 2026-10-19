package com.github.b4ndithelps.tensuraenigmatic.handler;

import auviotre.enigmatic.legacy.registries.EnigmaticItems;
import com.github.b4ndithelps.tensuraenigmatic.util.CurioHelper;
import com.github.b4ndithelps.tensuraenigmatic.util.TensuraEnergyHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public class TheCubeEnhancementHandler {
   private static final double MAGICULE_RECOVERY_PERCENTAGE = 0.01;

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (!event.getEntity().level().isClientSide()
         && event.getEntity().tickCount % 20 == 0
         && CurioHelper.hasCurio(event.getEntity(), EnigmaticItems.THE_CUBE)) {
         double maxMagicule = event.getEntity().getAttributeValue(TensuraAttributes.MAX_MAGICULE);
         TensuraEnergyHelper.restoreMagicule(event.getEntity(), maxMagicule * 0.01);
      }
   }

   public static double getMagiculeRecoveryPercentage() {
      return 0.01;
   }
}
