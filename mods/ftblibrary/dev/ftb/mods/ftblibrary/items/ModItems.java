package dev.ftb.mods.ftblibrary.items;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.Row;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;

public class ModItems {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create("ftblibrary", Registries.ITEM);
   private static final DeferredSupplier<Item> ICON_ITEM = ITEMS.register("icon_item", () -> new Item(new Properties()));
   private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create("ftblibrary", Registries.CREATIVE_MODE_TAB);
   public static final DeferredSupplier<CreativeModeTab> FTB_LIBRARY_TAB = CREATIVE_MODE_TABS.register(
      "ftb_library",
      () -> CreativeModeTab.builder(Row.TOP, 0)
         .title(Component.translatable("itemGroup.ftbsuite.creative_tab"))
         .icon(() -> new ItemStack((ItemLike)ICON_ITEM.get()))
         .build()
   );

   public static void init() {
      CREATIVE_MODE_TABS.register();
      ITEMS.register();
   }
}
