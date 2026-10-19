package com.blackgear.vanillabackport.client.registries;

import com.blackgear.platform.core.CoreRegistry;
import com.blackgear.vanillabackport.client.api.tabs.BundledTabs;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.CreativeModeTab.Row;

public interface ModCreativeTabs {
   CoreRegistry<CreativeModeTab> TABS = CoreRegistry.create(Registries.CREATIVE_MODE_TAB, "vanillabackport");
   Supplier<CreativeModeTab> VANILLA_BACKPORT = TABS.register(
      "vanilla_backport",
      () -> CreativeModeTab.builder(Row.TOP, 3)
         .title(Component.literal("Vanilla Backport"))
         .icon(() -> new ItemStack(Items.BUNDLE))
         .displayItems((parameters, output) -> {
            synchronized (ModCreativeTabs.class) {
               Provider provider = parameters.holders();
               List<BundledTabs> tabs = ModBundledTabs.getTabs();
               tabs.forEach(tab -> tab.populate(provider));
               tabs.stream().flatMap(tab -> tab.getDisplayItems().stream()).forEach(output::accept);
            }
         })
         .build()
   );
}
