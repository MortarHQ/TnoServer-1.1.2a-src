package uk.co.extraspecialstudio.lootr_liaison.context;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class StructureContextEngine {
   public static boolean isInStructure(Level level, BlockPos pos) {
      return false;
   }

   public static Optional<ResourceLocation> getStructureAt(Level level, BlockPos pos) {
      return Optional.empty();
   }

   private StructureContextEngine() {
   }
}
