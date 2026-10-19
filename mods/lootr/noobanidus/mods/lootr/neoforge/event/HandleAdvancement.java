package noobanidus.mods.lootr.neoforge.event;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent.AdvancementEarnEvent;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;

@EventBusSubscriber(modid = "lootr")
public class HandleAdvancement {
   @SubscribeEvent
   public static void onAdvancement(AdvancementEarnEvent event) {
      if (!event.getEntity().level().isClientSide()) {
         LootrRegistry.getAdvancementTrigger().trigger((ServerPlayer)event.getEntity(), event.getAdvancement().id());
      }
   }
}
