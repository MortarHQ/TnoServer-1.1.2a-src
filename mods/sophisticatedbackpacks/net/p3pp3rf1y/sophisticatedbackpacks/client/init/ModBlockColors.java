package net.p3pp3rf1y.sophisticatedbackpacks.client.init;

import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public class ModBlockColors {
   private ModBlockColors() {
   }

   public static void registerBlockColorHandlers(Block event) {
      event.register(
         (state, blockDisplayReader, pos, tintIndex) -> tintIndex >= 0 && tintIndex <= 1 && pos != null
            ? WorldHelper.getBlockEntity(blockDisplayReader, pos, BackpackBlockEntity.class)
               .map(be -> tintIndex == 0 ? be.getBackpackWrapper().getMainColor() : be.getBackpackWrapper().getAccentColor())
               .orElse(getDefaultColor(tintIndex))
            : -1,
         new net.minecraft.world.level.block.Block[]{
            ModBlocks.BACKPACK.get(),
            ModBlocks.COPPER_BACKPACK.get(),
            ModBlocks.IRON_BACKPACK.get(),
            ModBlocks.GOLD_BACKPACK.get(),
            ModBlocks.DIAMOND_BACKPACK.get(),
            ModBlocks.NETHERITE_BACKPACK.get()
         }
      );
   }

   private static int getDefaultColor(int tintIndex) {
      return tintIndex == 0 ? -3382982 : -10342886;
   }
}
