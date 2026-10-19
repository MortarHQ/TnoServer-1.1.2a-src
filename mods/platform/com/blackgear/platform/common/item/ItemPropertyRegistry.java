package com.blackgear.platform.common.item;

import com.blackgear.platform.common.item.neoforge.ItemPropertyRegistryImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemPropertyRegistry {
   @ExpectPlatform
   @Transformed
   public static ClampedItemPropertyFunction registerGeneric(ResourceLocation name, ClampedItemPropertyFunction property) {
      return ItemPropertyRegistryImpl.registerGeneric(name, property);
   }

   @ExpectPlatform
   @Transformed
   public static ItemPropertyFunction registerGeneric(ResourceLocation name, ItemPropertyFunction property) {
      return ItemPropertyRegistryImpl.registerGeneric(name, property);
   }

   @ExpectPlatform
   @Transformed
   public static void registerCustomModelData(ItemPropertyFunction property) {
      ItemPropertyRegistryImpl.registerCustomModelData(property);
   }

   @ExpectPlatform
   @Transformed
   public static void register(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
      ItemPropertyRegistryImpl.register(item, name, property);
   }

   @Nullable
   public static ItemPropertyFunction getProperty(Item item, ResourceLocation name) {
      return ItemProperties.getProperty(new ItemStack(item), name);
   }
}
