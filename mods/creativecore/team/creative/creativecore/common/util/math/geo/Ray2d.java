package team.creative.creativecore.common.util.math.geo;

import org.joml.Vector3d;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.vec.Vec2d;
import team.creative.creativecore.common.util.math.vec.Vec2f;
import team.creative.creativecore.common.util.math.vec.Vec3d;
import team.creative.creativecore.common.util.math.vec.Vec3f;
import team.creative.creativecore.common.util.math.vec.VectorUtils;

public class Ray2d {
   public double originOne;
   public double originTwo;
   public double directionOne;
   public double directionTwo;
   public Axis one;
   public Axis two;

   public Ray2d(Axis one, Axis two, Vec3d origin, double directionOne, double directionTwo) {
      this.one = one;
      this.two = two;
      this.originOne = origin.get(one);
      this.originTwo = origin.get(two);
      this.directionOne = directionOne;
      this.directionTwo = directionTwo;
   }

   public Ray2d(Axis one, Axis two, double startOne, double startTwo, double endOne, double endTwo) {
      this.set(one, two, startOne, startTwo, endOne, endTwo);
   }

   public double getOrigin(Axis axis) {
      return this.one == axis ? this.originOne : this.originTwo;
   }

   public double getDirection(Axis axis) {
      return this.one == axis ? this.directionOne : this.directionTwo;
   }

   public Axis getOther(Axis axis) {
      return this.one == axis ? this.two : this.one;
   }

   public void set(Axis one, Axis two, double startOne, double startTwo, double endOne, double endTwo) {
      this.one = one;
      this.two = two;
      this.originOne = startOne;
      this.originTwo = startTwo;
      this.directionOne = endOne - startOne;
      this.directionTwo = endTwo - startTwo;
   }

   public void set(Axis one, Axis two, Vec3f first, Vec3f second) {
      this.one = one;
      this.two = two;
      this.originOne = first.get(one);
      this.originTwo = first.get(two);
      this.directionOne = second.get(one) - first.get(one);
      this.directionTwo = second.get(two) - first.get(two);
   }

   public double getT(Axis axis, double value) {
      return (value - this.getOrigin(axis)) / this.getDirection(axis);
   }

   public double get(Axis axis, double value) {
      Axis other = this.getOther(axis);
      return this.getOrigin(other) + this.getDirection(other) * (value - this.getOrigin(axis)) / this.getDirection(axis);
   }

   public Vec2d get(double t) {
      return new Vec2d(this.originOne + this.directionOne * t, this.originTwo + this.directionTwo * t);
   }

   public Vec2f getFloat(double t) {
      return new Vec2f((float)(this.originOne + this.directionOne * t), (float)(this.originTwo + this.directionTwo * t));
   }

   public Double getWithLimits(Axis axis, double value) {
      return this.getWithLimits(axis, value, 0.0, 1.0);
   }

   public Double getWithLimits(Axis axis, double value, double min, double max) {
      Axis other = this.getOther(axis);
      double position = (value - this.getOrigin(axis)) / this.getDirection(axis);
      return !(position < min) && !(position > max) ? this.getOrigin(other) + this.getDirection(other) * position : null;
   }

   public boolean isCoordinateOnLine(int one, int two) {
      return this.get(this.one, one) == two;
   }

   public boolean isCoordinateOnLine(double one, double two) {
      if (this.directionOne == 0.0) {
         return VectorUtils.equals(this.originOne, one);
      } else {
         return this.directionTwo == 0.0 ? VectorUtils.equals(this.originTwo, two) : VectorUtils.equals(this.get(this.one, one), two);
      }
   }

   public boolean isCoordinateToTheRight(int one, int two) {
      double tempOne = one - this.originOne;
      double tempTwo = two - this.originTwo;
      return this.directionOne * tempTwo - this.directionTwo * tempOne < 0.0;
   }

   public Boolean isCoordinateToTheRight(double one, double two) {
      double tempOne = one - this.originOne;
      double tempTwo = two - this.originTwo;
      double result = this.directionOne * tempTwo - this.directionTwo * tempOne;
      return result > -1.0E-4F && result < 1.0E-4F ? null : result < 0.0;
   }

   public Vec3f intersect(Vec3f start, Vec3f end, float thirdValue) {
      float lineOriginOne = start.get(this.one);
      float lineOriginTwo = start.get(this.two);
      float lineDirectionOne = end.get(this.one) - start.get(this.one);
      float lineDirectionTwo = end.get(this.two) - start.get(this.two);
      if (VectorUtils.isZero(this.directionOne * lineDirectionTwo - this.directionTwo * lineDirectionOne)) {
         return null;
      }

      Vec3f vec = new Vec3f(thirdValue, thirdValue, thirdValue);
      double t = ((lineOriginTwo - this.originTwo) * lineDirectionOne + this.originOne * lineDirectionTwo - lineOriginOne * lineDirectionTwo)
         / (lineDirectionOne * this.directionTwo - this.directionOne * lineDirectionTwo);
      vec.set(this.one, (float)(this.originOne + t * this.directionOne));
      vec.set(this.two, (float)(this.originTwo + t * this.directionTwo));
      return vec;
   }

   public double intersectWhen(Ray2d line) throws VectorFan.ParallelException {
      if (VectorUtils.isZero(this.directionOne * line.directionTwo - this.directionTwo * line.directionOne)) {
         if (this.isCoordinateOnLine(line.originOne, line.originTwo)) {
            throw new VectorFan.ParallelException();
         } else {
            return -1.0;
         }
      } else {
         return ((line.originTwo - this.originTwo) * line.directionOne + this.originOne * line.directionTwo - line.originOne * line.directionTwo)
            / (line.directionOne * this.directionTwo - this.directionOne * line.directionTwo);
      }
   }

   public Vector3d intersect(Ray2d line, int thirdValue) {
      if (VectorUtils.isZero(this.directionOne * line.directionTwo - this.directionTwo * line.directionOne)) {
         return null;
      }

      Vector3d vec = new Vector3d(thirdValue, thirdValue, thirdValue);
      double t = ((line.originTwo - this.originTwo) * line.directionOne + this.originOne * line.directionTwo - line.originOne * line.directionTwo)
         / (line.directionOne * this.directionTwo - this.directionOne * line.directionTwo);
      VectorUtils.set(vec, this.originOne + t * this.directionOne, this.one);
      VectorUtils.set(vec, this.originTwo + t * this.directionTwo, this.two);
      return vec;
   }

   @Override
   public String toString() {
      return this.one + "," + this.two + ",[" + this.originOne + "," + this.originTwo + "],[" + this.directionOne + "," + this.directionTwo + "]";
   }
}
