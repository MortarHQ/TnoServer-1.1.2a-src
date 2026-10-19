package com.teamresourceful.resourcefullib.common.item.tabs.neoforge;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeModeTab;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;

public class ResourcefulCreativeModeTabImpl {
   public static CreativeModeTab create(ResourcefulCreativeModeTab tab) {
      Builder creativeTab = CreativeModeTab.builder()
         .icon(() -> tab.icon.get())
         .title(Component.translatable("itemGroup." + tab.id.getNamespace() + "." + tab.id.getPath()));
      if (tab.hideScrollBar) {
         creativeTab.noScrollBar();
      }

      if (tab.hideTitle) {
         creativeTab.hideTitle();
      }

      creativeTab.displayItems((params, output) -> tab.contents.stream().flatMap(Supplier::get).forEach(output::accept));
      return creativeTab.build();
   }
}
