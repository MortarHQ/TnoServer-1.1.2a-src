package net.pixeldreamstudios.mythika.entity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.AABB;
import net.pixeldreamstudios.mythika.Mythika;
import org.jetbrains.annotations.Nullable;

public class FollowFireBuilderGoal extends Goal {
   private static final double SEARCH_RADIUS = 24.0;
   private static final double LEASH_RADIUS = 48.0;
   private static final double TRAIL_DISTANCE = 5.0;
   private static final double SPEED = 0.9;
   private static final int REPATH_TICKS = 20;
   private final SatyrEntity satyr;
   @Nullable
   private SatyrEntity builder;
   private int repathTicks;

   public FollowFireBuilderGoal(SatyrEntity satyr) {
      this.satyr = satyr;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if (!Mythika.config.satyrPlaysMusic || !Mythika.config.satyrLightsCampfires) {
         return false;
      }

      if (this.satyr.getTarget() != null || this.satyr.isBuildingFire()) {
         return false;
      }

      if (this.satyr.nearestCampfire() != null) {
         return false;
      }

      this.builder = this.nearestBuilder();
      return this.builder != null;
   }

   public boolean canContinueToUse() {
      return this.builder != null
         && this.builder.isAlive()
         && this.builder.isBuildingFire()
         && this.satyr.getTarget() == null
         && this.satyr.nearestCampfire() == null
         && this.satyr.distanceToSqr(this.builder) < 2304.0;
   }

   public void stop() {
      this.builder = null;
      this.satyr.getNavigation().stop();
   }

   public void tick() {
      this.satyr.getLookControl().setLookAt(this.builder, 30.0F, 30.0F);
      if (this.satyr.distanceToSqr(this.builder) <= 25.0) {
         this.satyr.getNavigation().stop();
      } else {
         if (--this.repathTicks <= 0) {
            this.repathTicks = 20;
            this.satyr.getNavigation().moveTo(this.builder, 0.9);
         }
      }
   }

   @Nullable
   private SatyrEntity nearestBuilder() {
      List<SatyrEntity> searching = this.satyr
         .level()
         .getEntitiesOfClass(
            SatyrEntity.class, AABB.ofSize(this.satyr.position(), 48.0, 48.0, 48.0), other -> other != this.satyr && other.isAlive() && other.isBuildingFire()
         );
      return searching.stream().min(Comparator.comparingDouble(this.satyr::distanceToSqr)).orElse(null);
   }
}
