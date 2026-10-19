package com.nododiiiii.ponderer.blueprint;

import com.nododiiiii.ponderer.Config;
import com.nododiiiii.ponderer.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class BlueprintFeature {
   private static final String BUILTIN_BLUEPRINT = "ponderer:blueprint";
   private static final String DEFAULT_CARRIER = "minecraft:paper";

   private BlueprintFeature() {
   }

   public static boolean isBuiltinBlueprintItemEnabled() {
      try {
         return (Boolean)Config.ENABLE_BLUEPRINT_ITEM.get();
      } catch (Exception e) {
         return false;
      }
   }

   public static String getCarrierId() {
      try {
         String carrierId = (String)Config.BLUEPRINT_CARRIER_ITEM.get();
         return carrierId != null && !carrierId.isBlank() ? carrierId.trim() : "minecraft:paper";
      } catch (Exception e) {
         return "minecraft:paper";
      }
   }

   public static Item resolveCarrierItem() {
      String id = getCarrierId();
      ResourceLocation loc = ResourceLocation.tryParse(id);
      if (loc == null) {
         return Items.PAPER;
      } else if ("ponderer:blueprint".equals(id)) {
         return !isBuiltinBlueprintItemEnabled() ? Items.AIR : ModItems.BLUEPRINT.get();
      } else {
         return BuiltInRegistries.ITEM.getOptional(loc).orElse(Items.PAPER);
      }
   }

   public static ItemStack getCarrierStack() {
      Item item = resolveCarrierItem();
      return item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
   }

   public static boolean matchesCarrierStack(ItemStack stack) {
      return stack != null && !stack.isEmpty() ? stack.is(resolveCarrierItem()) : false;
   }

   public static boolean shouldShowBlueprintInCreativeTab() {
      return isBuiltinBlueprintItemEnabled();
   }
}
