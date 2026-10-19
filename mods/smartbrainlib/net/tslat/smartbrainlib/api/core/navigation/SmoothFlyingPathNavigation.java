package net.tslat.smartbrainlib.api.core.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import org.jetbrains.annotations.Nullable;

public class SmoothFlyingPathNavigation extends FlyingPathNavigation implements ExtendedNavigator {
   public SmoothFlyingPathNavigation(Mob mob, Level level) {
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
      this.nodeEvaluator = new FlyNodeEvaluator();
      this.nodeEvaluator.setCanPassDoors(true);
      return this.createSmoothPathFinder(this.nodeEvaluator, maxVisitedNodes);
   }
}
