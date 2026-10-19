package net.p3pp3rf1y.sophisticatedbackpacks.client.init;

import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

public class ModItemColors {
   private ModItemColors() {
   }

   public static void registerItemColorHandlers(Item event) {
      event.register(
         (backpack, layer) -> {
            if (layer <= 1 && backpack.getItem() instanceof BackpackItem) {
               IBackpackWrapper backpackWrapper = BackpackWrapper.fromStack(backpack);
               if (layer == 0) {
                  return backpackWrapper.getMainColor();
               } else {
                  return layer == 1 ? backpackWrapper.getAccentColor() : -1;
               }
            } else {
               return -1;
            }
         },
         new ItemLike[]{
            (ItemLike)ModItems.BACKPACK.get(),
            (ItemLike)ModItems.COPPER_BACKPACK.get(),
            (ItemLike)ModItems.IRON_BACKPACK.get(),
            (ItemLike)ModItems.GOLD_BACKPACK.get(),
            (ItemLike)ModItems.DIAMOND_BACKPACK.get(),
            (ItemLike)ModItems.NETHERITE_BACKPACK.get()
         }
      );
   }
}
