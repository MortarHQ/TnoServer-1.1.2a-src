package team.lodestar.lodestone.systems.rendering.trail;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class TrailPointBuilder {
   private final List<TrailPoint> trailPoints = new ArrayList<>();
   private Vec3 origin;
   protected final int trailLength;

   public TrailPointBuilder(int trailLength) {
      this.trailLength = trailLength;
   }

   public static TrailPointBuilder create(int trailLength) {
      return new TrailPointBuilder(trailLength);
   }

   public List<TrailPoint> getTrailPoints() {
      return this.trailPoints;
   }

   public TrailPointBuilder addTrailPoint(Vec3 point) {
      return this.addTrailPoint(new TrailPoint(point, 0));
   }

   public TrailPointBuilder addTrailPoint(TrailPoint point) {
      this.trailPoints.add(point);
      return this.setOrigin(point.getPosition());
   }

   public TrailPointBuilder setOrigin(Vec3 origin) {
      this.origin = origin;
      return this;
   }

   public Vec3 getOrigin() {
      return this.origin;
   }

   public Integer getTrailLength() {
      return this.trailLength;
   }

   public TrailPointBuilder tickTrailPoints() {
      int lifespan = this.trailLength;
      this.trailPoints.forEach(TrailPoint::tick);
      this.trailPoints.removeIf(p -> p.getAge() > lifespan);
      return this;
   }

   public TrailPointBuilder run(Consumer<TrailPoint> consumer) {
      this.trailPoints.forEach(consumer);
      return this;
   }

   public List<Vector4f> build(Matrix4f pose) {
      return this.trailPoints.stream().map(p -> p.getMatrixPosition(pose)).collect(Collectors.toList());
   }

   public List<Vector4f> build(Matrix4f pose, float partialTicks) {
      return this.trailPoints.stream().map(p -> p.getInterpolatedMatrixPosition(pose, partialTicks)).collect(Collectors.toList());
   }
}
