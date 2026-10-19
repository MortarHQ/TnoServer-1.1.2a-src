package com.blackgear.vanillabackport.client.api.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class BundledTabs {
   private final Component tooltip;
   private final ItemStack icon;
   private final List<ItemStack> displayItems;
   @Nullable
   private final BiConsumer<Provider, BundledTabs.Output> provider;
   private boolean populated;
   @Nullable
   private BundledTabSelector.Tab tab;
   private boolean selected;

   private BundledTabs(Component tooltip, ItemStack icon, List<ItemStack> staticItems, @Nullable BiConsumer<Provider, BundledTabs.Output> provider) {
      this.tooltip = tooltip;
      this.icon = icon;
      this.displayItems = staticItems;
      this.provider = provider;
   }

   public static BundledTabs.Builder builder() {
      return new BundledTabs.Builder();
   }

   public Component getTooltip() {
      return this.tooltip;
   }

   public ItemStack getIcon() {
      return this.icon;
   }

   public List<ItemStack> getDisplayItems() {
      return Collections.unmodifiableList(this.displayItems);
   }

   public boolean contains(ItemStack stack) {
      return this.displayItems.contains(stack);
   }

   public void select() {
      this.selected = true;
   }

   public void deselect() {
      this.selected = false;
   }

   public boolean isSelected() {
      return this.selected;
   }

   public void setContentTab(@Nullable BundledTabSelector.Tab tab) {
      this.tab = tab;
   }

   public void setVisible(boolean visible) {
      if (this.tab != null) {
         this.tab.visible = visible;
      }
   }

   public void setY(int y) {
      if (this.tab != null) {
         this.tab.setY(y);
      }
   }

   public void populate(Provider provider) {
      if (!this.populated && this.provider != null) {
         this.provider.accept(provider, new BundledTabs.Output() {
            @Override
            public void accept(ItemLike item) {
               BundledTabs.this.displayItems.add(new ItemStack(item));
            }

            @Override
            public void accept(ItemStack stack) {
               BundledTabs.this.displayItems.add(stack);
            }
         });
         this.populated = true;
      }
   }

   public static class Builder {
      private Component title;
      private ItemStack icon;
      private BiConsumer<Provider, BundledTabs.Output> provider;

      public BundledTabs.Builder title(Component title) {
         this.title = title;
         return this;
      }

      public BundledTabs.Builder icon(ItemStack icon) {
         this.icon = icon;
         return this;
      }

      public BundledTabs.Builder displayItems(BiConsumer<Provider, BundledTabs.Output> provider) {
         this.provider = provider;
         return this;
      }

      public BundledTabs build() {
         if (this.title == null) {
            this.title = Component.empty();
         }

         if (this.icon == null) {
            this.icon = ItemStack.EMPTY;
         }

         return new BundledTabs(this.title, this.icon, new ArrayList<>(), this.provider);
      }
   }

   public interface Output {
      void accept(ItemLike var1);

      void accept(ItemStack var1);
   }
}
