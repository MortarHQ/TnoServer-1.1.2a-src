package it.hurts.sskirillss.relics.init;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = "relics", bus = Bus.MOD, value = Dist.CLIENT)
public class HotkeyRegistry {
   private static final String CATEGORY = "Relics";
   public static final KeyMapping ACTIVE_ABILITIES_LIST = new KeyMapping("key.relics.active_abilities_list", 342, "Relics");
   public static final KeyMapping RESEARCH_RELIC = new KeyMapping("key.relics.research_relic", 340, "Relics");

   @SubscribeEvent
   public static void onKeybindingRegistry(RegisterKeyMappingsEvent event) {
      event.register(ACTIVE_ABILITIES_LIST);
      event.register(RESEARCH_RELIC);
   }
}
