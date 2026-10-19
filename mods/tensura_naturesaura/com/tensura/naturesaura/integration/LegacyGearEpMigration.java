package com.tensura.naturesaura.integration;

import com.mojang.logging.LogUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import org.slf4j.Logger;

public final class LegacyGearEpMigration {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final String TENSURA_MOD_ID = "tensura";
   private static final String GEAR_HANDLER_CLASS = "io.github.manasmods.tensura.handler.GearHandler";
   private static final ResourceLocation EXISTENCE_POINT = tensuraComponent("existence_point");
   private static final ResourceLocation EXISTENCE_GAIN = tensuraComponent("existence_gain");
   private static final ResourceLocation EXISTENCE_DURABILITY = tensuraComponent("existence_durability");
   private static final ResourceLocation MAX_EXISTENCE_POINT = tensuraComponent("max_existence_point");
   private static final Set<ResourceLocation> SUPPORTED_ARMOR = Set.of(
      naturesAuraItem("infused_iron_helmet"),
      naturesAuraItem("infused_iron_chest"),
      naturesAuraItem("infused_iron_pants"),
      naturesAuraItem("infused_iron_shoes"),
      naturesAuraItem("sky_helmet"),
      naturesAuraItem("sky_chest"),
      naturesAuraItem("sky_pants"),
      naturesAuraItem("sky_shoes"),
      naturesAuraItem("depth_helmet"),
      naturesAuraItem("depth_chest"),
      naturesAuraItem("depth_pants"),
      naturesAuraItem("depth_shoes")
   );
   private static LegacyGearEpMigration.TensuraComponents components;
   private static Method gearInitializer;
   private static boolean gearInitializerResolved;
   private static boolean invocationFailureLogged;

   private LegacyGearEpMigration() {
   }

   public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
      if (!event.getEntity().level().isClientSide && isTensuraLoaded()) {
         migrateStack(event.getEntity().level(), event.getTo());
      }
   }

   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide && isTensuraLoaded()) {
         Inventory inventory = player.getInventory();
         boolean changed = false;

         for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            changed |= migrateStack(player.level(), inventory.getItem(slot));
         }

         if (changed) {
            inventory.setChanged();
         }
      }
   }

   private static boolean migrateStack(Level level, ItemStack stack) {
      if (!stack.isEmpty() && SUPPORTED_ARMOR.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()))) {
         LegacyGearEpMigration.TensuraComponents resolvedComponents = resolveComponents();
         Method initializer = resolveGearInitializer();
         if (resolvedComponents != null && initializer != null) {
            Double maxEp = (Double)stack.get(resolvedComponents.maxEp());
            if (maxEp == null || Double.compare(maxEp, 0.0) != 0) {
               return false;
            }

            if (!stack.has(resolvedComponents.ep()) && !stack.has(resolvedComponents.epGain()) && !stack.has(resolvedComponents.epDurability())) {
               stack.remove(resolvedComponents.maxEp());

               try {
                  initializer.invoke(null, level, stack);
                  return true;
               } catch (IllegalAccessException | InvocationTargetException exception) {
                  stack.set(resolvedComponents.maxEp(), 0.0);
                  if (!invocationFailureLogged) {
                     invocationFailureLogged = true;
                     LOGGER.warn("Unable to initialize legacy Nature's Aura armor EP data", exception);
                  }

                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean isTensuraLoaded() {
      return ModList.get().isLoaded("tensura");
   }

   private static LegacyGearEpMigration.TensuraComponents resolveComponents() {
      if (components != null) {
         return components;
      } else {
         DataComponentType<Double> ep = getDoubleComponent(EXISTENCE_POINT);
         DataComponentType<Double> epGain = getDoubleComponent(EXISTENCE_GAIN);
         DataComponentType<Double> epDurability = getDoubleComponent(EXISTENCE_DURABILITY);
         DataComponentType<Double> maxEp = getDoubleComponent(MAX_EXISTENCE_POINT);
         if (ep != null && epGain != null && epDurability != null && maxEp != null) {
            components = new LegacyGearEpMigration.TensuraComponents(ep, epGain, epDurability, maxEp);
            return components;
         } else {
            return null;
         }
      }
   }

   private static synchronized Method resolveGearInitializer() {
      if (gearInitializerResolved) {
         return gearInitializer;
      }

      gearInitializerResolved = true;

      try {
         gearInitializer = Class.forName("io.github.manasmods.tensura.handler.GearHandler").getMethod("initiateGearExistence", Level.class, ItemStack.class);
      } catch (ReflectiveOperationException | LinkageError exception) {
         LOGGER.warn("Unable to locate Tensura's gear EP initializer", exception);
      }

      return gearInitializer;
   }

   private static DataComponentType<Double> getDoubleComponent(ResourceLocation id) {
      return !BuiltInRegistries.DATA_COMPONENT_TYPE.containsKey(id) ? null : (DataComponentType)BuiltInRegistries.DATA_COMPONENT_TYPE.get(id);
   }

   private static ResourceLocation naturesAuraItem(String path) {
      return ResourceLocation.fromNamespaceAndPath("naturesaura", path);
   }

   private static ResourceLocation tensuraComponent(String path) {
      return ResourceLocation.fromNamespaceAndPath("tensura", path);
   }

   private record TensuraComponents(
      DataComponentType<Double> ep, DataComponentType<Double> epGain, DataComponentType<Double> epDurability, DataComponentType<Double> maxEp
   ) {
   }
}
