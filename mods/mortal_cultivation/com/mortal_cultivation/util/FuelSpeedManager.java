package com.mortal_cultivation.util;

import com.mortal_cultivation.config.AlchemyFurnaceConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class FuelSpeedManager {
   private static final Map<ResourceLocation, Double> fuelSpeedMap = new HashMap<>();
   private static boolean initialized = false;

   public static void initialize() {
      fuelSpeedMap.clear();

      for (String entry : (List)AlchemyFurnaceConfig.FUEL_SPEED_MODIFIERS.get()) {
         try {
            String[] parts = entry.split(",");
            if (parts.length == 2) {
               String itemId = parts[0].trim();
               double speedMultiplier = Double.parseDouble(parts[1].trim());
               if (speedMultiplier > 0.0) {
                  ResourceLocation itemLocation = ResourceLocation.parse(itemId);
                  fuelSpeedMap.put(itemLocation, speedMultiplier);
               }
            }
         } catch (Exception e) {
            System.err.println("Invalid fuel speed modifier entry: " + entry);
         }
      }

      initialized = true;
   }

   public static double getSpeedMultiplier(ItemStack itemStack) {
      if (!initialized) {
         initialize();
      }

      if (itemStack.isEmpty()) {
         return 1.0;
      }

      ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
      return fuelSpeedMap.getOrDefault(itemId, 1.0);
   }

   public static void reload() {
      initialized = false;
      initialize();
   }

   public static boolean hasSpeedModifier(ItemStack itemStack) {
      if (!initialized) {
         initialize();
      }

      if (itemStack.isEmpty()) {
         return false;
      }

      ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
      return fuelSpeedMap.containsKey(itemId);
   }

   public static Map<ResourceLocation, Double> getAllFuelSpeeds() {
      if (!initialized) {
         initialize();
      }

      return new HashMap<>(fuelSpeedMap);
   }
}
