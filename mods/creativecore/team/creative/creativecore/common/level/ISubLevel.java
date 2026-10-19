package team.creative.creativecore.common.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ISubLevel extends IOrientatedLevel {
   Level getParent();

   Level getRealLevel();

   default BlockPos transformToRealWorld(BlockPos pos) {
      return this.getOrigin().transformPointToWorld(pos);
   }
}
