package it.hurts.sskirillss.relics.init;

import it.hurts.sskirillss.relics.items.misc.CreativeContentConstructor;
import it.hurts.sskirillss.relics.items.misc.ICreativeTabContent;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(bus = Bus.MOD)
public class CreativeTabRegistry {
   public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "relics");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RELICS_TAB = CREATIVE_TABS.register(
      "relics",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("itemGroup.relics"))
         .icon(() -> ((RelicItem)ItemRegistry.BASTION_RING.get()).getDefaultInstance())
         .build()
   );

   public static void register(IEventBus bus) {
      CREATIVE_TABS.register(bus);
   }

   @SubscribeEvent
   public static void fillCreativeTabs(BuildCreativeModeTabContentsEvent event) {
      for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
         if (item instanceof ICreativeTabContent entry) {
            CreativeContentConstructor constructor = new CreativeContentConstructor();
            entry.gatherCreativeTabContent(constructor);

            for (CreativeContentConstructor.CreativeContentData content : constructor.getEntries()) {
               if (event.getTab() == content.getTab()) {
                  event.acceptAll(content.getStacks(), content.getVisibility());
               }
            }
         }
      }
   }
}
