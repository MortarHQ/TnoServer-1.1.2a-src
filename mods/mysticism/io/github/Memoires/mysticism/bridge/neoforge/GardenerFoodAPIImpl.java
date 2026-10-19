package io.github.Memoires.mysticism.bridge.neoforge;

import io.github.Memoires.mysticism.handler.skill.GardenerHandler;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.common.NeoForge;

public class GardenerFoodAPIImpl {
   public static void register() {
      NeoForge.EVENT_BUS.addListener(e -> {
         if (e.getHand() == InteractionHand.MAIN_HAND) {
            GardenerHandler.onInteract(e.getEntity(), e.getHand(), e.getTarget());
         }
      });
   }
}
