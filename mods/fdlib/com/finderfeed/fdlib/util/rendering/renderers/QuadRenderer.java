package com.finderfeed.fdlib.util.rendering.renderers;

import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class QuadRenderer {
   private Vec3 direction = new Vec3(0.0, 1.0, 0.0);
   private Vector3f translation = new Vector3f(0.0F, 0.0F, 0.0F);
   private VertexConsumer vertexConsumer;
   private PoseStack poseStack = new PoseStack();
   private float directionOffset = 0.0F;
   private float rotation = 0.0F;
   private float sizeX = 0.5F;
   private float sizeY = 0.5F;
   private int animationFrames = 0;
   private int currentAnimationFrame = 0;
   private int light = 15728880;
   private int overlay = OverlayTexture.NO_OVERLAY;
   private Vector4f color1 = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
   private Vector4f color2 = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
   private Vector4f color3 = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
   private Vector4f color4 = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
   private boolean verticalRendering = false;
   private boolean renderBack = false;

   private QuadRenderer(VertexConsumer vertexConsumer) {
      this.vertexConsumer = vertexConsumer;
   }

   public static QuadRenderer start(VertexConsumer vertexConsumer) {
      return new QuadRenderer(vertexConsumer);
   }

   public void render() {
      this.poseStack.pushPose();
      this.poseStack.translate(this.translation.x, this.translation.y, this.translation.z);
      FDRenderUtil.applyMovementMatrixRotations(this.poseStack, this.direction);
      this.poseStack.mulPose(Axis.YP.rotationDegrees(this.rotation));
      this.poseStack.translate(0.0F, this.directionOffset, 0.0F);
      float u1 = 0.0F;
      float v1 = 0.0F;
      float u2 = 1.0F;
      float v2 = 1.0F;
      if (this.animationFrames != 0) {
         float voffset = 1.0F / this.animationFrames;
         float vstart = (float)this.currentAnimationFrame / this.animationFrames;
         float vend = vstart + voffset;
         v1 = vstart;
         v2 = vend;
      }

      Matrix4f matrix4f = this.poseStack.last().pose();
      if (!this.verticalRendering) {
         this.vertexConsumer
            .addVertex(matrix4f, -this.sizeX, 0.0F, this.sizeY)
            .setColor(this.color4.x, this.color4.y, this.color4.z, this.color4.w)
            .setUv(u1, v2)
            .setLight(this.light)
            .setOverlay(this.overlay)
            .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         this.vertexConsumer
            .addVertex(matrix4f, this.sizeX, 0.0F, this.sizeY)
            .setColor(this.color3.x, this.color3.y, this.color3.z, this.color3.w)
            .setUv(u2, v2)
            .setLight(this.light)
            .setOverlay(this.overlay)
            .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         this.vertexConsumer
            .addVertex(matrix4f, this.sizeX, 0.0F, -this.sizeY)
            .setColor(this.color2.x, this.color2.y, this.color2.z, this.color2.w)
            .setUv(u2, v1)
            .setLight(this.light)
            .setOverlay(this.overlay)
            .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         this.vertexConsumer
            .addVertex(matrix4f, -this.sizeX, 0.0F, -this.sizeY)
            .setColor(this.color1.x, this.color1.y, this.color1.z, this.color1.w)
            .setUv(u1, v1)
            .setLight(this.light)
            .setOverlay(this.overlay)
            .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         if (this.renderBack) {
            this.vertexConsumer
               .addVertex(matrix4f, -this.sizeX, 0.0F, -this.sizeY)
               .setColor(this.color1.x, this.color1.y, this.color1.z, this.color1.w)
               .setUv(u1, v1)
               .setLight(this.light)
               .setOverlay(this.overlay)
               .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
            this.vertexConsumer
               .addVertex(matrix4f, this.sizeX, 0.0F, -this.sizeY)
               .setColor(this.color2.x, this.color2.y, this.color2.z, this.color2.w)
               .setUv(u2, v1)
               .setLight(this.light)
               .setOverlay(this.overlay)
               .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
            this.vertexConsumer
               .addVertex(matrix4f, this.sizeX, 0.0F, this.sizeY)
               .setColor(this.color3.x, this.color3.y, this.color3.z, this.color3.w)
               .setUv(u2, v2)
               .setLight(this.light)
               .setOverlay(this.overlay)
               .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
            this.vertexConsumer
               .addVertex(matrix4f, -this.sizeX, 0.0F, this.sizeY)
               .setColor(this.color4.x, this.color4.y, this.color4.z, this.color4.w)
               .setUv(u1, v2)
               .setLight(this.light)
               .setOverlay(this.overlay)
               .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         }
      } else {
         this.vertexConsumer
            .addVertex(matrix4f, -this.sizeX, this.sizeY, 0.0F)
            .setColor(this.color4.x, this.color4.y, this.color4.z, this.color4.w)
            .setUv(u1, v2)
            .setLight(this.light)
            .setOverlay(this.overlay)
            .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         this.vertexConsumer
            .addVertex(matrix4f, this.sizeX, this.sizeY, 0.0F)
            .setColor(this.color3.x, this.color3.y, this.color3.z, this.color3.w)
            .setUv(u2, v2)
            .setLight(this.light)
            .setOverlay(this.overlay)
            .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         this.vertexConsumer
            .addVertex(matrix4f, this.sizeX, -this.sizeY, 0.0F)
            .setColor(this.color2.x, this.color2.y, this.color2.z, this.color2.w)
            .setUv(u2, v1)
            .setLight(this.light)
            .setOverlay(this.overlay)
            .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         this.vertexConsumer
            .addVertex(matrix4f, -this.sizeX, -this.sizeY, 0.0F)
            .setColor(this.color1.x, this.color1.y, this.color1.z, this.color1.w)
            .setUv(u1, v1)
            .setLight(this.light)
            .setOverlay(this.overlay)
            .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         if (this.renderBack) {
            this.vertexConsumer
               .addVertex(matrix4f, -this.sizeX, -this.sizeY, 0.0F)
               .setColor(this.color1.x, this.color1.y, this.color1.z, this.color1.w)
               .setUv(u1, v1)
               .setLight(this.light)
               .setOverlay(this.overlay)
               .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
            this.vertexConsumer
               .addVertex(matrix4f, this.sizeX, -this.sizeY, 0.0F)
               .setColor(this.color2.x, this.color2.y, this.color2.z, this.color2.w)
               .setUv(u2, v1)
               .setLight(this.light)
               .setOverlay(this.overlay)
               .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
            this.vertexConsumer
               .addVertex(matrix4f, this.sizeX, this.sizeY, 0.0F)
               .setColor(this.color3.x, this.color3.y, this.color3.z, this.color3.w)
               .setUv(u2, v2)
               .setLight(this.light)
               .setOverlay(this.overlay)
               .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
            this.vertexConsumer
               .addVertex(matrix4f, -this.sizeX, this.sizeY, 0.0F)
               .setColor(this.color4.x, this.color4.y, this.color4.z, this.color4.w)
               .setUv(u1, v2)
               .setLight(this.light)
               .setOverlay(this.overlay)
               .setNormal((float)this.direction.x, (float)this.direction.y, (float)this.direction.z);
         }
      }

      this.poseStack.popPose();
   }

   public QuadRenderer setAnimated(int currentAnimationFrame, int animationFrameCount) {
      if (animationFrameCount < 2) {
         System.out.println("Cannot have less than 2 animation frames! It won't even be an animation...");
      }

      this.animationFrames = animationFrameCount;
      this.currentAnimationFrame = currentAnimationFrame;
      return this;
   }

   public QuadRenderer renderBack() {
      this.renderBack = true;
      return this;
   }

   public QuadRenderer verticalRendering() {
      this.verticalRendering = true;
      return this;
   }

   public QuadRenderer direction(Vec3 direction) {
      this.direction = direction;
      return this;
   }

   public QuadRenderer size(float size) {
      this.sizeX = size;
      this.sizeY = size;
      return this;
   }

   public QuadRenderer sizeX(float size) {
      this.sizeX = size;
      return this;
   }

   public QuadRenderer sizeY(float size) {
      this.sizeY = size;
      return this;
   }

   public QuadRenderer light(int light) {
      this.light = light;
      return this;
   }

   public QuadRenderer overlay(int overlay) {
      this.overlay = overlay;
      return this;
   }

   public QuadRenderer translate(float x, float y, float z) {
      this.translation = new Vector3f(x, y, z);
      return this;
   }

   public QuadRenderer pose(PoseStack poseStack) {
      this.poseStack = poseStack;
      return this;
   }

   public QuadRenderer rotationDegrees(float rotation) {
      this.rotation = rotation;
      return this;
   }

   public QuadRenderer offsetOnDirection(float offset) {
      this.directionOffset = offset;
      return this;
   }

   public QuadRenderer color1(float r, float g, float b, float a) {
      this.color1.set(r, g, b, a);
      return this;
   }

   public QuadRenderer color2(float r, float g, float b, float a) {
      this.color2.set(r, g, b, a);
      return this;
   }

   public QuadRenderer color3(float r, float g, float b, float a) {
      this.color3.set(r, g, b, a);
      return this;
   }

   public QuadRenderer color4(float r, float g, float b, float a) {
      this.color4.set(r, g, b, a);
      return this;
   }

   public QuadRenderer color(float r, float g, float b, float a) {
      return this.color1(r, g, b, a).color2(r, g, b, a).color3(r, g, b, a).color4(r, g, b, a);
   }
}
