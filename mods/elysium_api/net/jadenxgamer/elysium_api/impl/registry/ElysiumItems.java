package net.jadenxgamer.elysium_api.impl.registry;

import java.util.function.Supplier;
import net.jadenxgamer.elysium_api.impl.core.item.PanoramaScreenshotItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElysiumItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, "elysium_api");
   public static final Supplier<Item> PANORAMA_CAMERA = ITEMS.register(
      "panorama_camera",
      () -> new PanoramaScreenshotItem(new Properties().stacksTo(1).rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true))
   );

   public static void init(IEventBus eventBus) {
      ITEMS.register(eventBus);
   }
}
