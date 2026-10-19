package it.hurts.sskirillss.relics.items.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.level.ItemLike;

public class CreativeContentConstructor {
   private final List<CreativeContentConstructor.CreativeContentData> entries = new ArrayList<>();

   public void entry(CreativeModeTab tab, TabVisibility visibility, ItemStack... items) {
      this.entries.add(new CreativeContentConstructor.CreativeContentData(tab, visibility, Arrays.asList(items)));
   }

   public void entry(CreativeModeTab tab, TabVisibility visibility, ItemLike... items) {
      this.entry(tab, visibility, Arrays.stream(items).map(ItemStack::new).toArray(ItemStack[]::new));
   }

   public List<CreativeContentConstructor.CreativeContentData> getEntries() {
      return this.entries;
   }

   public static class CreativeContentData {
      private final CreativeModeTab tab;
      private final TabVisibility visibility;
      private final List<ItemStack> stacks;

      public CreativeModeTab getTab() {
         return this.tab;
      }

      public TabVisibility getVisibility() {
         return this.visibility;
      }

      public List<ItemStack> getStacks() {
         return this.stacks;
      }

      public CreativeContentData(CreativeModeTab tab, TabVisibility visibility, List<ItemStack> stacks) {
         this.tab = tab;
         this.visibility = visibility;
         this.stacks = stacks;
      }
   }
}
