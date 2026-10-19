package com.mongoose.royalvariations.utils;

import com.mongoose.royalvariations.compat.curios.CuriosLoaded;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class CuriosFinder {
   public static ItemStack findCurio(LivingEntity livingEntity, Predicate<ItemStack> filter) {
      ItemStack foundStack = ItemStack.EMPTY;
      if (livingEntity instanceof Player && CuriosLoaded.CURIOS.isLoaded()) {
         Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(livingEntity).flatMap(inv -> inv.findFirstCurio(filter));
         if (slotResult.isPresent()) {
            foundStack = slotResult.get().stack();
         }
      }

      return foundStack;
   }

   public static boolean hasCurio(LivingEntity livingEntity, Predicate<ItemStack> filter) {
      return !findCurio(livingEntity, filter).isEmpty();
   }

   public static boolean hasCurio(LivingEntity livingEntity, Item item) {
      return !findCurio(livingEntity, item).isEmpty();
   }

   public static ItemStack findCurio(LivingEntity livingEntity, Item item) {
      ItemStack foundStack = ItemStack.EMPTY;
      if (livingEntity instanceof Player && CuriosLoaded.CURIOS.isLoaded()) {
         Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(livingEntity).flatMap(inv -> inv.findFirstCurio(item));
         if (slotResult.isPresent()) {
            foundStack = slotResult.get().stack();
         }
      }

      return foundStack;
   }
}
