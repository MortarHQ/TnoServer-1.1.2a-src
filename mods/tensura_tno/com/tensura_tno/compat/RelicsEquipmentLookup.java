package com.tensura_tno.compat;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Method;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class RelicsEquipmentLookup {
   private static final String ENTITY_UTILS = "it.hurts.sskirillss.relics.utils.EntityUtils";
   private static volatile boolean lookupAttempted;
   private static volatile Method findEquippedCurio;
   private static volatile boolean invocationFailureLogged;

   public static Optional<Boolean> isEquipped(Entity entity, ResourceLocation itemId) {
      if (entity != null && itemId != null) {
         Item item = (Item)BuiltInRegistries.ITEM.get(itemId);
         if (item == Items.AIR) {
            return Optional.of(false);
         }

         Method lookup = findLookupMethod();
         if (lookup == null) {
            return Optional.empty();
         }

         try {
            if (lookup.invoke(null, entity, item) instanceof ItemStack stack) {
               return Optional.of(!stack.isEmpty() && stack.is(item));
            }
         } catch (ReflectiveOperationException | LinkageError exception) {
            if (!invocationFailureLogged) {
               invocationFailureLogged = true;
               TensuraTNOMod.LOGGER.warn("[TNO] Relics equipped-curio lookup failed; compatibility guard will fail open", exception);
            }
         }

         return Optional.empty();
      } else {
         return Optional.of(false);
      }
   }

   private static Method findLookupMethod() {
      if (lookupAttempted) {
         return findEquippedCurio;
      }

      synchronized (RelicsEquipmentLookup.class) {
         if (lookupAttempted) {
            return findEquippedCurio;
         }

         try {
            Class<?> entityUtils = Class.forName("it.hurts.sskirillss.relics.utils.EntityUtils");
            findEquippedCurio = entityUtils.getMethod("findEquippedCurio", Entity.class, Item.class);
         } catch (ReflectiveOperationException | LinkageError ignored) {
            findEquippedCurio = null;
         } finally {
            lookupAttempted = true;
         }

         return findEquippedCurio;
      }
   }

   private RelicsEquipmentLookup() {
   }
}
