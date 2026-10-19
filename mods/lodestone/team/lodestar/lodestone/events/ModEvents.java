package team.lodestar.lodestone.events;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import team.lodestar.lodestone.handlers.ThrowawayBlockDataHandler;
import team.lodestar.lodestone.registry.common.LodestoneCommandArgumentTypes;
import team.lodestar.lodestone.systems.item.LodestoneItemProperties;

@EventBusSubscriber(bus = Bus.MOD)
public class ModEvents {
   @SubscribeEvent
   public static void registerCommon(FMLCommonSetupEvent event) {
      LodestoneCommandArgumentTypes.registerArgumentTypes();
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void lateSetup(InterModEnqueueEvent event) {
      ThrowawayBlockDataHandler.wipeCache(event);
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
      LodestoneItemProperties.populateItemGroups(event);
   }
}
