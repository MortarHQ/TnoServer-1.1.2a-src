package com.tensura_tno.compat;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class SophisticatedBackpacksCompat {
   private static final String BACKPACK_ITEM_CLASS_NAME = "net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem";
   private static final String BACKPACK_WRAPPER_CLASS_NAME = "net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper";
   private static volatile Class<?> backpackItemClass;
   private static volatile Method getNumberOfSlotsMethod;
   private static volatile Method getNumberOfUpgradeSlotsMethod;
   private static volatile Method fromStackMethod;
   private static volatile Method setSlotNumbersMethod;
   private static volatile boolean reflectionInitialized;
   private static volatile boolean reflectionUnavailable;
   private static volatile boolean refreshFailureLogged;

   private SophisticatedBackpacksCompat() {
   }

   public static void refreshEvolvedBackpackSlots(ItemStack stack) {
      if (!stack.isEmpty() && !reflectionUnavailable && initializeReflection()) {
         Item item = stack.getItem();
         if (backpackItemClass.isInstance(item)) {
            try {
               int inventorySlots = (Integer)getNumberOfSlotsMethod.invoke(item);
               int upgradeSlots = (Integer)getNumberOfUpgradeSlotsMethod.invoke(item);
               Object wrapper = fromStackMethod.invoke(null, stack);
               setSlotNumbersMethod.invoke(wrapper, inventorySlots, upgradeSlots);
            } catch (IllegalAccessException | InvocationTargetException exception) {
               logRefreshFailure(exception);
            }
         }
      }
   }

   private static boolean initializeReflection() {
      if (reflectionInitialized) {
         return true;
      }

      if (reflectionUnavailable) {
         return false;
      }

      synchronized (SophisticatedBackpacksCompat.class) {
         if (reflectionInitialized) {
            return true;
         }

         if (reflectionUnavailable) {
            return false;
         }

         boolean var10000;
         try {
            Class<?> resolvedBackpackItemClass = Class.forName("net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem");
            Class<?> backpackWrapperClass = Class.forName("net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper");
            Class<?> backpackWrapperInterface = Class.forName("net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper");
            backpackItemClass = resolvedBackpackItemClass;
            getNumberOfSlotsMethod = resolvedBackpackItemClass.getMethod("getNumberOfSlots");
            getNumberOfUpgradeSlotsMethod = resolvedBackpackItemClass.getMethod("getNumberOfUpgradeSlots");
            fromStackMethod = backpackWrapperClass.getMethod("fromStack", ItemStack.class);
            setSlotNumbersMethod = backpackWrapperInterface.getMethod("setSlotNumbers", int.class, int.class);
            reflectionInitialized = true;
            var10000 = true;
         } catch (ClassNotFoundException exception) {
            reflectionUnavailable = true;
            return false;
         } catch (ReflectiveOperationException exception) {
            reflectionUnavailable = true;
            logRefreshFailure(exception);
            return false;
         }

         return var10000;
      }
   }

   private static void logRefreshFailure(Exception exception) {
      if (!refreshFailureLogged) {
         refreshFailureLogged = true;
         TensuraTNOMod.LOGGER.warn("[TensuraTNO] Failed to refresh Sophisticated Backpacks slots after evolution", exception);
      }
   }
}
