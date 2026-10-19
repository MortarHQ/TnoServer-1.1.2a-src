package dev.ftb.mods.ftbfiltersystem.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.ftb.mods.ftbfiltersystem.registry.item.SmartFilterItem;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;

public class ModItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("ftbfiltersystem", Registries.ITEM);
   public static final RegistrySupplier<Item> SMART_FILTER = ITEMS.register("smart_filter", SmartFilterItem::new);

   public static void init() {
      CreativeTabRegistry.appendStack(FTBLibrary.getCreativeModeTab(), () -> new ItemStack((ItemLike)SMART_FILTER.get()));
   }

   public static Properties defaultProps() {
      return new Properties();
   }
}
