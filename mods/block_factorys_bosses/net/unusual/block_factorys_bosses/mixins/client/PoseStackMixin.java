package net.unusual.block_factorys_bosses.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.geckolib.CommonPoseStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(PoseStack.class)
public abstract class PoseStackMixin implements CommonPoseStack {
   @Shadow
   public abstract void pushPose();

   @Shadow
   public abstract void popPose();

   @Shadow
   public abstract void translate(float var1, float var2, float var3);

   @Shadow
   public abstract void translate(double var1, double var3, double var5);

   @Shadow
   public abstract void scale(float var1, float var2, float var3);

   @Shadow
   public abstract void mulPose(Matrix4f var1);

   @Shadow
   public abstract void mulPose(Quaternionf var1);

   @Shadow
   public abstract Pose last();

   @Override
   public void bosses_rise_java$push() {
      this.pushPose();
   }

   @Override
   public void bosses_rise_java$pop() {
      this.popPose();
   }

   @Override
   public void bosses_rise_java$translate(double x, double y, double z) {
      this.translate(x, y, z);
   }

   @Override
   public void bosses_rise_java$translate(float x, float y, float z) {
      this.translate(x, y, z);
   }

   @Override
   public void bosses_rise_java$scale(float x, float y, float z) {
      this.scale(x, y, z);
   }

   @Override
   public void bosses_rise_java$mulPose(Matrix4f pose) {
      this.mulPose(pose);
   }

   @Override
   public void bosses_rise_java$mulPose(Quaternionf quaternion) {
      this.mulPose(quaternion);
   }

   @Override
   public Matrix4f bosses_rise_java$last() {
      return this.last().pose();
   }
}
