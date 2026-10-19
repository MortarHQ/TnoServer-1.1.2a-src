package net.tslat.smartbrainlib.api.core.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.Nullable;

public class SmoothWallClimberNavigation extends WallClimberNavigation implements ExtendedNavigator {
   public SmoothWallClimberNavigation(Mob mob, Level level) {
      super(mob, level);
   }

   @Override
   public Mob getMob() {
      return this.mob;
   }

   @Nullable
   @Override
   public Path getPath() {
      return super.getPath();
   }

   protected PathFinder createPathFinder(int maxVisitedNodes) {
      this.nodeEvaluator = new WalkNodeEvaluator();
      this.nodeEvaluator.setCanPassDoors(true);
      return this.createSmoothPathFinder(this.nodeEvaluator, maxVisitedNodes);
   }

   public int getSurfaceY() {
      return super.getSurfaceY();
   }
}
