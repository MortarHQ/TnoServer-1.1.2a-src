package net.regions_unexplored.module.platform;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.regions_unexplored.RegionsUnexploredNeo;

public class Registrar {
   public static <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value) {
      return RegionsUnexploredNeo.REGISTER_CACHE
         .computeIfAbsent(registry.key(), key -> DeferredRegister.create(registry.key().location(), "regions_unexplored"))
         .register(name, value);
   }

   public static Supplier<CreativeModeTab> registerCreativeModeTab(String name, Supplier<ItemStack> icon, Supplier<DisplayItemsGenerator> items) {
      return RegionsUnexploredNeo.REGISTER_CACHE
         .computeIfAbsent(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), resourceKey -> DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, "regions_unexplored")
         )
         .register(
            name,
            () -> CreativeModeTab.builder()
               .title(Component.translatable("itemGroup.regions_unexplored." + name))
               .hideTitle()
               .backgroundTexture(
                  ResourceLocation.fromNamespaceAndPath("regions_unexplored", "textures/gui/container/creative_inventory/tab_regions_unexplored_search.png")
               )
               .withSearchBar(58)
               .icon(icon)
               .displayItems(items.get())
               .withSearchBar()
               .build()
         );
   }
}
