package net.tslat.smartbrainlib.api.core.navigation;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import org.jetbrains.annotations.Nullable;

public class SmoothWaterBoundPathNavigation extends WaterBoundPathNavigation implements ExtendedNavigator {
   public SmoothWaterBoundPathNavigation(Mob mob, Level level) {
      super(mob, level);
   }

   public boolean canBreach() {
      return this.mob.getType() == EntityType.DOLPHIN;
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
      this.nodeEvaluator = new SwimNodeEvaluator(this.allowBreaching = this.canBreach());
      this.nodeEvaluator.setCanPassDoors(true);
      return this.createSmoothPathFinder(this.nodeEvaluator, maxVisitedNodes);
   }
}
