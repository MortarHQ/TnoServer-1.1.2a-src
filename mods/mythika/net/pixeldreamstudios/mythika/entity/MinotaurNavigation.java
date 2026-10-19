package net.pixeldreamstudios.mythika.entity;

import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class MinotaurNavigation extends GroundPathNavigation {
   public MinotaurNavigation(MinotaurEntity minotaur, Level level) {
      super(minotaur, level);
   }

   protected PathFinder createPathFinder(int maxVisitedNodes) {
      this.nodeEvaluator = new MinotaurNavigation.LabyrinthBoundEvaluator();
      this.nodeEvaluator.setCanPassDoors(true);
      return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
   }

   private static class LabyrinthBoundEvaluator extends WalkNodeEvaluator {
      public PathType getPathType(PathfindingContext context, int x, int y, int z) {
         return this.mob instanceof MinotaurEntity minotaur && shutOut(minotaur, x, y, z) ? PathType.BLOCKED : super.getPathType(context, x, y, z);
      }

      private static boolean shutOut(MinotaurEntity minotaur, int x, int y, int z) {
         return minotaur.guardsLabyrinth() && !minotaur.isInsideLabyrinth(x, y, z) && minotaur.isInsideLabyrinth(minotaur.blockPosition());
      }
   }
}
