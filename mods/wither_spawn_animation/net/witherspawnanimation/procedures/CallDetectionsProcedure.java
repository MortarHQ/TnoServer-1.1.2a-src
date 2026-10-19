package net.witherspawnanimation.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;

public class CallDetectionsProcedure {
   public static String execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
      String altar_type = "";
      altar_type = "null";
      if (DetectAltarProcedure.execute(world, x, y, z, blockstate)) {
         altar_type = "sand";
      } else if (DetectAltarSoilProcedure.execute(world, x, y, z, blockstate)) {
         altar_type = "soil";
      }

      if (ModList.get().isLoaded("bygonenether") && DetectAltarBygoneNetherProcedure.execute(world, x, y, z, blockstate)) {
         altar_type = "bygone";
      }

      if (ModList.get().isLoaded("witherstormmod")) {
         if (WitherStormModFixProcedure.execute(world, x, y, z, blockstate)) {
            altar_type = "storm";
         } else if (WitherStormModFixSoilProcedure.execute(world, x, y, z, blockstate)) {
            altar_type = "storm_soil";
         }
      }

      return altar_type;
   }
}
