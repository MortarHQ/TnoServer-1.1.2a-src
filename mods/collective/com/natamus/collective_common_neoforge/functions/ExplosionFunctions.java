package com.natamus.collective_common_neoforge.functions;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;

public class ExplosionFunctions {
   public static List<BlockPos> getAffectedBlockPositions(Explosion explosion) {
      return explosion.getToBlow();
   }

   public static void clearExplosion(Explosion explosion) {
      explosion.getToBlow().clear();
      explosion.getHitPlayers().clear();
   }
}
