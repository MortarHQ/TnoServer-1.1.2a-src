package net.pixeldreamstudios.mythika.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class CyclopsNavigation extends GroundPathNavigation {
   public CyclopsNavigation(Mob mob, Level level) {
      super(mob, level);
   }

   protected PathFinder createPathFinder(int maxVisitedNodes) {
      this.nodeEvaluator = new CyclopsNavigation.BreakableAwareEvaluator();
      this.nodeEvaluator.setCanPassDoors(true);
      return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
   }

   private static class BreakableAwareEvaluator extends WalkNodeEvaluator {
      public PathType getPathType(PathfindingContext context, int x, int y, int z) {
         return this.mob instanceof CyclopsEntity cyclops
               && (cyclops.getTarget() != null || cyclops.isStuck())
               && cyclops.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
               && CyclopsEntity.canSmash(context.getBlockState(new BlockPos(x, y, z)))
            ? PathType.OPEN
            : super.getPathType(context, x, y, z);
      }
   }
}
