package team.lodestar.lodestone.systems.rendering.trail;

import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class TrailPoint {
   protected Vec3 oldPosition;
   protected Vec3 position;
   protected int age;

   public TrailPoint(Vec3 position, int age) {
      this.oldPosition = position;
      this.position = position;
      this.age = age;
   }

   public TrailPoint(Vec3 position) {
      this(position, 0);
   }

   public Vector4f getMatrixPosition(Matrix4f pose) {
      return getMatrixPosition(this.getPosition(), pose);
   }

   public Vector4f getInterpolatedMatrixPosition(Matrix4f pose, float partialTicks) {
      return getMatrixPosition(this.getInterpolatedPosition(partialTicks), pose);
   }

   public Vec3 getPosition() {
      return this.position;
   }

   public Vec3 getInterpolatedPosition(float partialTicks) {
      return this.oldPosition == null ? this.position : this.oldPosition.lerp(this.position, partialTicks);
   }

   public void setPosition(Vec3 position) {
      this.oldPosition = this.position;
      this.position = position;
   }

   public void move(Vec3 offset) {
      this.setPosition(this.getPosition().add(offset));
   }

   public int getAge() {
      return this.age;
   }

   public void tick() {
      this.age++;
   }

   public static Vector4f getMatrixPosition(Vec3 position, Matrix4f pose) {
      return new Vector4f((float)position.x, (float)position.y, (float)position.z, 1.0F).mul(pose);
   }
}
