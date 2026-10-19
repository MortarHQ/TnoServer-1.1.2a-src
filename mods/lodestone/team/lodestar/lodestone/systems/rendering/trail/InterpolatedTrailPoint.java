package team.lodestar.lodestone.systems.rendering.trail;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class InterpolatedTrailPoint extends TrailPoint {
   protected Vec3 endPosition;
   protected final int lerpDuration;

   public InterpolatedTrailPoint(Vec3 position, int age, Vec3 endPosition, int lerpDuration) {
      super(position, age);
      this.endPosition = endPosition;
      this.lerpDuration = lerpDuration;
   }

   public InterpolatedTrailPoint(Vec3 position, Vec3 endPosition, int lerpDuration) {
      super(position);
      this.endPosition = endPosition;
      this.lerpDuration = lerpDuration;
   }

   public void setEndPosition(Vec3 endPosition) {
      this.endPosition = endPosition;
   }

   @Override
   public Vec3 getPosition() {
      return this.position.lerp(this.endPosition, Mth.clamp((float)this.age / this.lerpDuration, 0.0F, 1.0F));
   }
}
