package uk.co.extraspecialstudio.lootr_liaison.lootr;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.neoforged.fml.ModList;

public final class LootrAdapter {
   public static void finalize(Level level, BlockPos pos, RandomizableContainerBlockEntity be) {
      if (ModList.get().isLoaded("lootr")) {
         ;
      }
   }
}
