package com.chronodawn.neoforge.anvil;

import com.chronodawn.anvil.AnvilRepairHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = "chronodawn")
public class AnvilRepairEventHandler {
   @SubscribeEvent
   public static void onAnvilUpdate(AnvilUpdateEvent event) {
      ItemStack leftInput = event.getLeft();
      ItemStack rightInput = event.getRight();
      ItemStack result = AnvilRepairHandler.calculateTimeCrystalRepair(leftInput, rightInput);
      if (result != null) {
         event.setOutput(result);
         int cost = AnvilRepairHandler.calculateRepairCost(leftInput, result);
         event.setCost(cost);
         int leftWorkCost = (Integer)leftInput.getOrDefault(DataComponents.REPAIR_COST, 0);
         int newWorkCost = leftWorkCost * 2 + 1;
         result.set(DataComponents.REPAIR_COST, newWorkCost);
         event.setMaterialCost(1);
      }
   }
}
