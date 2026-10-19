package team.creative.creativecore.common.util.math.matrix;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.box.ABB;
import team.creative.creativecore.common.util.math.box.BoxCorner;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public interface IVecOrigin {
   double offX();

   double offY();

   double offZ();

   double rotX();

   double rotY();

   double rotZ();

   double offXLast();

   double offYLast();

   double offZLast();

   double rotXLast();

   double rotYLast();

   double rotZLast();

   boolean isRotated();

   void offX(double var1);

   void offY(double var1);

   void offZ(double var1);

   void off(double var1, double var3, double var5);

   void rotX(double var1);

   void rotY(double var1);

   void rotZ(double var1);

   void rot(double var1, double var3, double var5);

   Vec3d center();

   void setCenter(Vec3d var1);

   Matrix3 rotation();

   Matrix3 rotationInv();

   Vec3d translation();

   void tick();

   IVecOrigin getParent();

   default void set(IVecOrigin origin) {
      this.off(origin.offXLast(), origin.offYLast(), origin.offZLast());
      this.rot(origin.rotXLast(), origin.rotYLast(), origin.rotZLast());
      this.tick();
      this.off(origin.offX(), origin.offY(), origin.offZ());
      this.rot(origin.rotX(), origin.rotY(), origin.rotZ());
   }

   default double translationCombined(Axis axis) {
      return this.translation().get(axis);
   }

   default void onlyRotateWithoutCenter(Vec3d vec) {
      this.rotation().transform(vec);
   }

   default BlockPos transformPointToWorld(BlockPos pos) {
      Vec3d vec = new Vec3d(pos);
      this.transformPointToWorld(vec);
      return vec.toBlockPos();
   }

   default BlockPos transformPointToFakeWorld(BlockPos pos) {
      Vec3d vec = new Vec3d(pos);
      this.transformPointToFakeWorld(vec);
      return vec.toBlockPos();
   }

   default void transformPointToWorld(Vec3d vec) {
      vec.sub(this.center());
      this.rotation().transform(vec);
      vec.add(this.center());
      vec.add(this.translation());
   }

   default void transformPointToFakeWorld(Vec3d vec) {
      vec.sub(this.translation());
      vec.sub(this.center());
      this.rotationInv().transform(vec);
      vec.add(this.center());
   }

   default Vector3d transformPointToWorld(Vector3d vec) {
      Vec3d real = new Vec3d(vec);
      this.transformPointToWorld(real);
      return new Vector3d(real.x, real.y, real.z);
   }

   default Vector3d transformPointToFakeWorld(Vector3d vec) {
      Vec3d real = new Vec3d(vec);
      this.transformPointToFakeWorld(real);
      return new Vector3d(real.x, real.y, real.z);
   }

   default Vec3 transformPointToWorld(Vec3 vec) {
      Vec3d real = new Vec3d(vec);
      this.transformPointToWorld(real);
      return new Vec3(real.x, real.y, real.z);
   }

   default Vec3 transformPointToFakeWorld(Vec3 vec) {
      Vec3d real = new Vec3d(vec);
      this.transformPointToFakeWorld(real);
      return new Vec3(real.x, real.y, real.z);
   }

   default ABB getAABB(AABB box) {
      ABB bb = ABB.createEmptyBox();
      Vec3d vec = new Vec3d();

      for (int i = 0; i < BoxCorner.values().length; i++) {
         BoxCorner.values()[i].set(box, vec);
         this.transformPointToWorld(vec);
         bb.minX = Math.min(bb.minX, vec.x);
         bb.minY = Math.min(bb.minY, vec.y);
         bb.minZ = Math.min(bb.minZ, vec.z);
         bb.maxX = Math.max(bb.maxX, vec.x);
         bb.maxY = Math.max(bb.maxY, vec.y);
         bb.maxZ = Math.max(bb.maxZ, vec.z);
      }

      return bb;
   }

   default ABB getOBB(AABB box) {
      ABB bb = ABB.createEmptyBox();
      Vec3d vec = new Vec3d();

      for (int i = 0; i < BoxCorner.values().length; i++) {
         BoxCorner.values()[i].set(box, vec);
         this.transformPointToFakeWorld(vec);
         bb.minX = Math.min(bb.minX, vec.x);
         bb.minY = Math.min(bb.minY, vec.y);
         bb.minZ = Math.min(bb.minZ, vec.z);
         bb.maxX = Math.max(bb.maxX, vec.x);
         bb.maxY = Math.max(bb.maxY, vec.y);
         bb.maxZ = Math.max(bb.maxZ, vec.z);
      }

      return bb;
   }

   default ABB getAABB(ABB box) {
      ABB bb = ABB.createEmptyBox();
      Vec3d vec = new Vec3d();

      for (int i = 0; i < BoxCorner.values().length; i++) {
         BoxCorner.values()[i].set(box, vec);
         this.transformPointToWorld(vec);
         bb.minX = Math.min(bb.minX, vec.x);
         bb.minY = Math.min(bb.minY, vec.y);
         bb.minZ = Math.min(bb.minZ, vec.z);
         bb.maxX = Math.max(bb.maxX, vec.x);
         bb.maxY = Math.max(bb.maxY, vec.y);
         bb.maxZ = Math.max(bb.maxZ, vec.z);
      }

      return bb;
   }

   default ABB getOBB(ABB box) {
      ABB bb = ABB.createEmptyBox();
      Vec3d vec = new Vec3d();

      for (int i = 0; i < BoxCorner.values().length; i++) {
         BoxCorner.values()[i].set(box, vec);
         this.transformPointToFakeWorld(vec);
         bb.minX = Math.min(bb.minX, vec.x);
         bb.minY = Math.min(bb.minY, vec.y);
         bb.minZ = Math.min(bb.minZ, vec.z);
         bb.maxX = Math.max(bb.maxX, vec.x);
         bb.maxY = Math.max(bb.maxY, vec.y);
         bb.maxZ = Math.max(bb.maxZ, vec.z);
      }

      return bb;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   default void setupRenderingInternal(PoseStack matrixStack, double camX, double camY, double camZ, float partialTicks) {
      double rotX = this.rotXLast() + (this.rotX() - this.rotXLast()) * partialTicks;
      double rotY = this.rotYLast() + (this.rotY() - this.rotYLast()) * partialTicks;
      double rotZ = this.rotZLast() + (this.rotZ() - this.rotZLast()) * partialTicks;
      double offX = this.offXLast() + (this.offX() - this.offXLast()) * partialTicks;
      double offY = this.offYLast() + (this.offY() - this.offYLast()) * partialTicks;
      double offZ = this.offZLast() + (this.offZ() - this.offZLast()) * partialTicks;
      Vec3d rotationCenter = this.center();
      matrixStack.translate(offX, offY, offZ);
      matrixStack.translate(rotationCenter.x - camX, rotationCenter.y - camY, rotationCenter.z - camZ);
      matrixStack.mulPose(new Quaternionf().rotationXYZ((float)Math.toRadians(rotX), (float)Math.toRadians(rotY), (float)Math.toRadians(rotZ)));
      matrixStack.translate(-rotationCenter.x + camX, -rotationCenter.y + camY, -rotationCenter.z + camZ);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   default void setupRendering(PoseStack matrixStack, double camX, double camY, double camZ, float partialTicks) {
      this.setupRenderingInternal(matrixStack, camX, camY, camZ, partialTicks);
   }

   default boolean hasChanged() {
      return this.offXLast() != this.offX()
         || this.offYLast() != this.offY()
         || this.offZLast() != this.offZ()
         || this.rotXLast() != this.rotX()
         || this.rotYLast() != this.rotY()
         || this.rotZLast() != this.rotZ();
   }

   IVecOrigin copy();
}
