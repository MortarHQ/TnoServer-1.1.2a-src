package team.creative.creativecore.common.util.math.matrix;

import team.creative.creativecore.common.util.math.collision.MatrixUtils;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public class VecOrigin implements IVecOrigin {
   protected boolean rotated = false;
   private final Vec3d center;
   private Vec3d translation = new Vec3d(0.0, 0.0, 0.0);
   private Matrix3 rotation = MatrixUtils.createIdentityMatrix();
   private final Matrix3 rotationInv = this.rotation;
   protected double rotX;
   protected double rotY;
   protected double rotZ;
   protected double rotXLast;
   protected double rotYLast;
   protected double rotZLast;
   protected double offsetX;
   protected double offsetY;
   protected double offsetZ;
   protected double offsetXLast;
   protected double offsetYLast;
   protected double offsetZLast;

   public VecOrigin(Vec3d center) {
      this.center = center;
   }

   @Override
   public double offX() {
      return this.offsetX;
   }

   @Override
   public double offY() {
      return this.offsetY;
   }

   @Override
   public double offZ() {
      return this.offsetZ;
   }

   @Override
   public double rotX() {
      return this.rotX;
   }

   @Override
   public double rotY() {
      return this.rotY;
   }

   @Override
   public double rotZ() {
      return this.rotZ;
   }

   @Override
   public boolean isRotated() {
      return this.rotated;
   }

   protected void updateRotated() {
      this.rotated = this.rotX % 360.0 != 0.0 || this.rotY % 360.0 != 0.0 || this.rotZ % 360.0 != 0.0;
      this.rotation = MatrixUtils.createRotationMatrix(this.rotX, this.rotY, this.rotZ);
      this.rotationInv.invert(this.rotation);
   }

   protected void updateTranslation() {
      this.translation.set(this.offsetX, this.offsetY, this.offsetZ);
   }

   @Override
   public void offX(double value) {
      this.offsetX = value;
      this.updateTranslation();
   }

   @Override
   public void offY(double value) {
      this.offsetY = value;
      this.updateTranslation();
   }

   @Override
   public void offZ(double value) {
      this.offsetZ = value;
      this.updateTranslation();
   }

   @Override
   public void off(double x, double y, double z) {
      this.offsetX = x;
      this.offsetY = y;
      this.offsetZ = z;
      this.updateTranslation();
   }

   @Override
   public void rotX(double value) {
      this.rotX = value;
      this.updateRotated();
   }

   @Override
   public void rotY(double value) {
      this.rotY = value;
      this.updateRotated();
   }

   @Override
   public void rotZ(double value) {
      this.rotZ = value;
      this.updateRotated();
   }

   @Override
   public void rot(double x, double y, double z) {
      this.rotX = x;
      this.rotY = y;
      this.rotZ = z;
      this.updateRotated();
   }

   @Override
   public Vec3d center() {
      return this.center;
   }

   @Override
   public Matrix3 rotation() {
      return this.rotation;
   }

   @Override
   public Matrix3 rotationInv() {
      return this.rotationInv;
   }

   @Override
   public Vec3d translation() {
      return this.translation;
   }

   @Override
   public void setCenter(Vec3d vec) {
      this.center.set(vec);
   }

   @Override
   public double offXLast() {
      return this.offsetXLast;
   }

   @Override
   public double offYLast() {
      return this.offsetYLast;
   }

   @Override
   public double offZLast() {
      return this.offsetZLast;
   }

   @Override
   public double rotXLast() {
      return this.rotXLast;
   }

   @Override
   public double rotYLast() {
      return this.rotYLast;
   }

   @Override
   public double rotZLast() {
      return this.rotZLast;
   }

   @Override
   public void tick() {
      this.rotXLast = this.rotX;
      this.rotYLast = this.rotY;
      this.rotZLast = this.rotZ;
      this.offsetXLast = this.offsetX;
      this.offsetYLast = this.offsetY;
      this.offsetZLast = this.offsetZ;
   }

   protected VecOrigin createInternalCopy() {
      return new VecOrigin(new Vec3d(this.center));
   }

   @Override
   public IVecOrigin copy() {
      VecOrigin copy = this.createInternalCopy();
      copy.translation = new Vec3d(this.translation);
      copy.rotated = this.rotated;
      copy.rotX = this.rotX;
      copy.rotY = this.rotY;
      copy.rotZ = this.rotZ;
      copy.rotXLast = this.rotXLast;
      copy.rotYLast = this.rotYLast;
      copy.rotZLast = this.rotZLast;
      copy.offsetX = this.offsetX;
      copy.offsetY = this.offsetY;
      copy.offsetZ = this.offsetZ;
      copy.offsetXLast = this.offsetXLast;
      copy.offsetYLast = this.offsetYLast;
      copy.offsetZLast = this.offsetZLast;
      copy.updateRotated();
      copy.updateTranslation();
      return copy;
   }

   @Override
   public IVecOrigin getParent() {
      return null;
   }
}
