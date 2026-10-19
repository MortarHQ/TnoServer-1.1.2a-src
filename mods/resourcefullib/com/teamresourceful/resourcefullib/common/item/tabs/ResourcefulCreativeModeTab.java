package com.teamresourceful.resourcefullib.common.item.tabs;

import com.teamresourceful.resourcefullib.common.item.tabs.neoforge.ResourcefulCreativeModeTabImpl;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ResourcefulCreativeModeTab {
   public final ResourceLocation id;
   public Supplier<ItemStack> icon;
   public boolean hideScrollBar;
   public boolean hideTitle;
   public final List<Supplier<Stream<ItemStack>>> contents = new ArrayList<>();

   public ResourcefulCreativeModeTab(ResourceLocation id) {
      this.id = id;
   }

   public ResourcefulCreativeModeTab setItemIcon(Supplier<? extends ItemLike> icon) {
      return this.setStackIcon(() -> new ItemStack(icon.get()));
   }

   public ResourcefulCreativeModeTab setStackIcon(Supplier<ItemStack> icon) {
      this.icon = icon;
      return this;
   }

   public ResourcefulCreativeModeTab hideTitle() {
      this.hideTitle = true;
      return this;
   }

   public ResourcefulCreativeModeTab hideScrollBar() {
      this.hideScrollBar = true;
      return this;
   }

   public <I extends ItemLike, T extends ResourcefulRegistry<I>> ResourcefulCreativeModeTab addRegistry(T registry) {
      return this.addContent(() -> registry.boundStream().map(ItemStack::new));
   }

   public ResourcefulCreativeModeTab addStack(Supplier<ItemStack> stack) {
      return this.addContent(() -> Stream.of(stack.get()));
   }

   public ResourcefulCreativeModeTab addStack(ItemStack stack) {
      return this.addStack(() -> stack);
   }

   public ResourcefulCreativeModeTab addStack(ItemLike item) {
      return this.addStack(new ItemStack(item));
   }

   public ResourcefulCreativeModeTab addContent(Supplier<Stream<ItemStack>> content) {
      this.contents.add(content);
      return this;
   }

   public CreativeModeTab build() {
      return create(this);
   }

   @ExpectPlatform
   @Transformed
   private static CreativeModeTab create(ResourcefulCreativeModeTab tab) {
      return ResourcefulCreativeModeTabImpl.create(tab);
   }
}
