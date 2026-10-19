package com.blackgear.platform.common;

import com.blackgear.platform.common.neoforge.CreativeTabsImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.level.ItemLike;

public class CreativeTabs {
   @ExpectPlatform
   @Transformed
   public static CreativeModeTab create(Consumer<Builder> consumer) {
      return CreativeTabsImpl.create(consumer);
   }

   @ExpectPlatform
   @Transformed
   public static void modify(ResourceKey<CreativeModeTab> key, CreativeTabs.Modifier modifier) {
      CreativeTabsImpl.modify(key, modifier);
   }

   public interface Modifier {
      void accept(FeatureFlagSet var1, CreativeTabs.Output var2, boolean var3);
   }

   public interface Output extends net.minecraft.world.item.CreativeModeTab.Output {
      default void accept(ItemStack stack, TabVisibility tabVisibility) {
         this.addAfter(ItemStack.EMPTY, stack, tabVisibility);
      }

      void addAfter(ItemStack var1, ItemStack var2, TabVisibility var3);

      default void addAfter(ItemStack target, ItemStack stack) {
         this.addAfter(target, stack, TabVisibility.PARENT_AND_SEARCH_TABS);
      }

      default void addAfter(ItemLike target, ItemLike stack) {
         this.addAfter(target.asItem().getDefaultInstance(), stack.asItem().getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
      }

      default void addAllAfter(ItemStack target, Collection<ItemStack> stacks, TabVisibility visibility) {
         List<ItemStack> list = List.copyOf(stacks);

         for (int i = list.size() - 1; i >= 0; i--) {
            this.addAfter(target, list.get(i), visibility);
         }
      }

      default void addAllAfter(ItemStack target, Collection<ItemStack> stacks) {
         List<ItemStack> list = List.copyOf(stacks);

         for (int i = list.size() - 1; i >= 0; i--) {
            this.addAfter(target, list.get(i));
         }
      }

      default void addAllAfter(ItemLike target, Collection<ItemLike> stacks) {
         List<ItemLike> list = List.copyOf(stacks);

         for (int i = list.size() - 1; i >= 0; i--) {
            this.addAfter(target, list.get(i));
         }
      }

      void addBefore(ItemStack var1, ItemStack var2, TabVisibility var3);

      default void addBefore(ItemStack target, ItemStack stack) {
         this.addBefore(target, stack, TabVisibility.PARENT_AND_SEARCH_TABS);
      }

      default void addBefore(ItemLike target, ItemLike stack) {
         this.addBefore(target.asItem().getDefaultInstance(), stack.asItem().getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
      }

      default void addAllBefore(ItemStack target, Collection<ItemStack> stacks, TabVisibility visibility) {
         List<ItemStack> list = List.copyOf(stacks);

         for (int i = list.size() - 1; i >= 0; i--) {
            this.addBefore(target, list.get(i), visibility);
         }
      }

      default void addAllBefore(ItemStack target, Collection<ItemStack> stacks) {
         List<ItemStack> list = List.copyOf(stacks);

         for (int i = list.size() - 1; i >= 0; i--) {
            this.addBefore(target, list.get(i));
         }
      }

      default void addAllBefore(ItemLike target, Collection<ItemLike> stacks) {
         List<ItemLike> list = List.copyOf(stacks);

         for (int i = list.size() - 1; i >= 0; i--) {
            this.addBefore(target, list.get(i));
         }
      }
   }
}
