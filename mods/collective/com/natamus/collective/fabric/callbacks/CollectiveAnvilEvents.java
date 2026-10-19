package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1657;
import net.minecraft.class_1706;
import net.minecraft.class_1799;
import oshi.util.tuples.Triplet;

public class CollectiveAnvilEvents {
   public static final Event<CollectiveAnvilEvents.Anvil_Change> ANVIL_CHANGE = EventFactory.createArrayBacked(
      CollectiveAnvilEvents.Anvil_Change.class, callbacks -> (anvilmenu, left, right, output, itemName, baseCost, player) -> {
         for (CollectiveAnvilEvents.Anvil_Change callback : callbacks) {
            Triplet<Integer, Integer, class_1799> triple = callback.onAnvilChange(anvilmenu, left, right, output, itemName, baseCost, player);
            if (triple != null) {
               return triple;
            }
         }

         return null;
      }
   );

   private CollectiveAnvilEvents() {
   }

   @FunctionalInterface
   public interface Anvil_Change {
      Triplet<Integer, Integer, class_1799> onAnvilChange(
         class_1706 var1, class_1799 var2, class_1799 var3, class_1799 var4, String var5, int var6, class_1657 var7
      );
   }
}
