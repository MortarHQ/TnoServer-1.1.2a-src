package com.teamresourceful.resourcefullib.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class CloseablePoseStack extends PoseStack implements AutoCloseable {
   private final PoseStack stack;

   public CloseablePoseStack(PoseStack stack) {
      this.stack = stack;
      this.stack.pushPose();
   }

   public CloseablePoseStack(GuiGraphics graphics) {
      this(graphics.pose());
   }

   public CloseablePoseStack() {
      this(new PoseStack());
   }

   public void translate(double d, double e, double f) {
      this.stack.translate(d, e, f);
   }

   public void translate(float f, float g, float h) {
      this.stack.translate(f, g, h);
   }

   public void scale(float f, float g, float h) {
      this.stack.scale(f, g, h);
   }

   public void mulPose(@NotNull Quaternionf quaternion) {
      this.stack.mulPose(quaternion);
   }

   public void rotateAround(Quaternionf quaternionf, float f, float g, float h) {
      this.stack.rotateAround(quaternionf, f, g, h);
   }

   @NotNull
   public Pose last() {
      return this.stack.last();
   }

   public boolean clear() {
      return this.stack.clear();
   }

   public void setIdentity() {
      this.stack.setIdentity();
   }

   public void mulPose(Matrix4f matrix4f) {
      this.stack.mulPose(matrix4f);
   }

   @Override
   public void close() {
      this.stack.popPose();
   }

   public void pushPose() {
      this.stack.pushPose();
   }

   public void popPose() {
      this.stack.popPose();
   }
}
