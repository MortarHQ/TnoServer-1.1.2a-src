package net.tslat.smartbrainlib.api.core.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import org.jetbrains.annotations.Nullable;

public class SmoothAmphibiousPathNavigation extends AmphibiousPathNavigation implements ExtendedNavigator {
   public SmoothAmphibiousPathNavigation(Mob mob, Level level) {
      super(mob, level);
   }

   public boolean prefersShallowSwimming() {
      return false;
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
      this.nodeEvaluator = new AmphibiousNodeEvaluator(this.prefersShallowSwimming());
      this.nodeEvaluator.setCanPassDoors(true);
      return this.createSmoothPathFinder(this.nodeEvaluator, maxVisitedNodes);
   }
}
