package com.ilexiconn.llibrary.client.model.tools;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.client.render.MowzieRenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.model.Model;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class AdvancedModelRenderer extends BasicModelRenderer {
   private static final float MINIMUM_SCALE = 1.0E-6F;
   public float defaultRotationX;
   public float defaultRotationY;
   public float defaultRotationZ;
   public float defaultPositionX;
   public float defaultPositionY;
   public float defaultPositionZ;
   public float scaleX = 1.0F;
   public float scaleY = 1.0F;
   public float scaleZ = 1.0F;
   public float opacity = 1.0F;
   public boolean scaleChildren;
   private final Model model;
   private AdvancedModelRenderer parent;
   private boolean doubleSided = true;
   private boolean hasLighting = true;
   private boolean isHidden = false;
   public ObjectList<AdvancedModelRenderer.ModelBox> cubeList;
   public ObjectList<BasicModelRenderer> childModels;
   public int textureOffsetX;
   public int textureOffsetY;
   private float textureWidth;
   private float textureHeight;
   private Matrix3f mat3Override;
   private Matrix4f mat4Override;

   public AdvancedModelRenderer(BasicModelBase model) {
      this(model, 0, 0);
   }

   public AdvancedModelRenderer(BasicModelBase model, int textureOffsetX, int textureOffsetY) {
      super(model, textureOffsetX, textureOffsetY);
      this.model = model;
      this.textureWidth = model.textureWidth;
      this.textureHeight = model.textureHeight;
      this.textureOffsetX = textureOffsetX;
      this.textureOffsetY = textureOffsetY;
      this.cubeList = new ObjectArrayList();
      this.childModels = new ObjectArrayList();
   }

   public AdvancedModelRenderer(AdvancedModelRenderer copyFrom) {
      this(copyFrom.getAdvancedModel(), copyFrom.textureOffsetX, copyFrom.textureOffsetY);
      this.rotationPointX = copyFrom.rotationPointX;
      this.rotationPointY = copyFrom.rotationPointY;
      this.rotationPointZ = copyFrom.rotationPointZ;
      this.rotateAngleX = copyFrom.rotateAngleX;
      this.rotateAngleY = copyFrom.rotateAngleY;
      this.rotateAngleZ = copyFrom.rotateAngleZ;
      this.scaleX = copyFrom.scaleX;
      this.scaleY = copyFrom.scaleY;
      this.scaleZ = copyFrom.scaleZ;
      this.defaultPositionX = copyFrom.defaultPositionX;
      this.defaultPositionY = copyFrom.defaultPositionY;
      this.defaultPositionZ = copyFrom.defaultPositionZ;
      this.defaultRotationX = copyFrom.defaultRotationX;
      this.defaultRotationY = copyFrom.defaultRotationY;
      this.defaultRotationZ = copyFrom.defaultRotationZ;
      this.scaleChildren = copyFrom.scaleChildren;
      this.cubeList.addAll(copyFrom.cubeList);
      this.childModels = copyFrom.childModels;
   }

   @Override
   public BasicModelRenderer setTextureSize(int textureWidthIn, int textureHeightIn) {
      this.textureWidth = textureWidthIn;
      this.textureHeight = textureHeightIn;
      return super.setTextureSize(textureWidthIn, textureHeightIn);
   }

   @Override
   public BasicModelRenderer setTextureOffset(int x, int y) {
      this.textureOffsetX = x;
      this.textureOffsetY = y;
      return super.setTextureOffset(x, y);
   }

   public AdvancedModelRenderer addBox(String partName, float x, float y, float z, int width, int height, int depth, float delta, int texX, int texY) {
      this.setTextureSize(texX, texY);
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, this.mirror, false);
      return this;
   }

   public AdvancedModelRenderer addBox(float x, float y, float z, float width, float height, float depth) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, this.mirror, false);
      return this;
   }

   public AdvancedModelRenderer addBox(float x, float y, float z, float width, float height, float depth, boolean mirrorIn) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, mirrorIn, false);
      return this;
   }

   @Override
   public void addBox(float x, float y, float z, float width, float height, float depth, float delta) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, this.mirror, false);
   }

   @Override
   public void addBox(float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, this.mirror, false);
   }

   @Override
   public void addBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirrorIn) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, mirrorIn, false);
   }

   private void addBox(
      int texOffX,
      int texOffY,
      float x,
      float y,
      float z,
      float width,
      float height,
      float depth,
      float deltaX,
      float deltaY,
      float deltaZ,
      boolean mirror,
      boolean p_228305_13_
   ) {
      this.cubeList
         .add(
            new AdvancedModelRenderer.ModelBox(
               texOffX, texOffY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, mirror, this.textureWidth, this.textureHeight
            )
         );
   }

   public void setShouldScaleChildren(boolean scaleChildren) {
      this.scaleChildren = scaleChildren;
   }

   public void setScale(float scaleX, float scaleY, float scaleZ) {
      this.setScaleX(scaleX);
      this.setScaleY(scaleY);
      this.setScaleZ(scaleZ);
   }

   public void setScale(float scale) {
      this.setScaleX(scale);
      this.setScaleY(scale);
      this.setScaleZ(scale);
   }

   public void setScaleX(float scaleX) {
      this.scaleX = Math.max(1.0E-6F, scaleX);
   }

   public void setScaleY(float scaleY) {
      this.scaleY = Math.max(1.0E-6F, scaleY);
   }

   public void setScaleZ(float scaleZ) {
      this.scaleZ = Math.max(1.0E-6F, scaleZ);
   }

   public void setOpacity(float opacity) {
      this.opacity = opacity;
   }

   public void setHasLighting(boolean hasLighting) {
      this.hasLighting = hasLighting;
   }

   public void setDoubleSided(boolean doubleSided) {
      this.doubleSided = doubleSided;
   }

   public void setIsHidden(boolean isHidden) {
      this.isHidden = isHidden;
   }

   public boolean isHidden() {
      return this.isHidden;
   }

   public float getTextureWidth() {
      return this.textureWidth;
   }

   public float getTextureHeight() {
      return this.textureHeight;
   }

   public void updateDefaultPose() {
      this.defaultRotationX = this.rotateAngleX;
      this.defaultRotationY = this.rotateAngleY;
      this.defaultRotationZ = this.rotateAngleZ;
      this.defaultPositionX = this.rotationPointX;
      this.defaultPositionY = this.rotationPointY;
      this.defaultPositionZ = this.rotationPointZ;
   }

   public void resetToDefaultPose() {
      this.rotateAngleX = this.defaultRotationX;
      this.rotateAngleY = this.defaultRotationY;
      this.rotateAngleZ = this.defaultRotationZ;
      this.rotationPointX = this.defaultPositionX;
      this.rotationPointY = this.defaultPositionY;
      this.rotationPointZ = this.defaultPositionZ;
   }

   @Override
   public void addChild(BasicModelRenderer renderer) {
      super.addChild(renderer);
      this.childModels.add(renderer);
      if (renderer instanceof AdvancedModelRenderer advancedChild) {
         advancedChild.setParent(this);
      }
   }

   public AdvancedModelRenderer getParent() {
      return this.parent;
   }

   public void setParent(AdvancedModelRenderer parent) {
      this.parent = parent;
   }

   @Override
   public void translateRotate(PoseStack matrixStackIn) {
      AdvancedModelRenderer parent = this.getParent();
      if (parent != null && !parent.scaleChildren) {
         matrixStackIn.scale(1.0F / parent.scaleX, 1.0F / parent.scaleY, 1.0F / parent.scaleZ);
      }

      super.translateRotate(matrixStackIn);
      matrixStackIn.scale(this.scaleX, this.scaleY, this.scaleZ);
   }

   @Override
   public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
      if (this.showModel && (!this.cubeList.isEmpty() || !this.childModels.isEmpty())) {
         matrixStackIn.pushPose();
         this.translateRotate(matrixStackIn);
         if (!this.isHidden) {
            if (this.opacity < 1.0F) {
               float alpha = ARGB32.alpha(color) / 255.0F * this.opacity;
               float red = ARGB32.red(color) / 255.0F;
               float green = ARGB32.green(color) / 255.0F;
               float blue = ARGB32.blue(color) / 255.0F;
               color = ARGB32.colorFromFloat(alpha, red, green, blue);
            }

            this.doRender(matrixStackIn.last(), bufferIn, packedLightIn, packedOverlayIn, color);
         }

         ObjectListIterator var10 = this.childModels.iterator();

         while (var10.hasNext()) {
            BasicModelRenderer modelrenderer = (BasicModelRenderer)var10.next();
            modelrenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
         }

         matrixStackIn.popPose();
      }
   }

   @Override
   protected void doRender(Pose matrixEntryIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
      Matrix4f matrix4f = matrixEntryIn.pose();
      Matrix3f matrix3f = matrixEntryIn.normal();
      if (this.mat3Override != null) {
         matrix3f = this.mat3Override;
      }

      if (this.mat4Override != null) {
         matrix4f = this.mat4Override;
      }

      ObjectListIterator var8 = this.cubeList.iterator();

      while (var8.hasNext()) {
         AdvancedModelRenderer.ModelPart modelrenderer$modelbox = (AdvancedModelRenderer.ModelPart)var8.next();
         modelrenderer$modelbox.render(matrix4f, matrix3f, bufferIn, packedLightIn, packedOverlayIn, color);
      }
   }

   public AdvancedModelBase getAdvancedModel() {
      return (AdvancedModelBase)this.model;
   }

   private float calculateRotation(float speed, float degree, boolean invert, float offset, float weight, float f, float f1) {
      float movementScale = this.model instanceof AdvancedModelBase ? ((AdvancedModelBase)this.model).getMovementScale() : 1.0F;
      float rotation = Mth.cos(f * (speed * movementScale) + offset) * (degree * movementScale) * f1 + weight * f1;
      return invert ? -rotation : rotation;
   }

   public void walk(float speed, float degree, boolean invert, float offset, float weight, float walk, float walkAmount) {
      this.rotateAngleX = this.rotateAngleX + this.calculateRotation(speed, degree, invert, offset, weight, walk, walkAmount);
   }

   public void flap(float speed, float degree, boolean invert, float offset, float weight, float flap, float flapAmount) {
      this.rotateAngleZ = this.rotateAngleZ + this.calculateRotation(speed, degree, invert, offset, weight, flap, flapAmount);
   }

   public void swing(float speed, float degree, boolean invert, float offset, float weight, float swing, float swingAmount) {
      this.rotateAngleY = this.rotateAngleY + this.calculateRotation(speed, degree, invert, offset, weight, swing, swingAmount);
   }

   public void bob(float speed, float degree, boolean bounce, float f, float f1) {
      float movementScale = this.model instanceof AdvancedModelBase ? ((AdvancedModelBase)this.model).getMovementScale() : 1.0F;
      degree *= movementScale;
      speed *= movementScale;
      float bob = (float)(Math.sin(f * speed) * f1 * degree - f1 * degree);
      if (bounce) {
         bob = (float)(-Math.abs(Math.sin(f * speed) * f1 * degree));
      }

      this.rotationPointY += bob;
   }

   public void transitionTo(AdvancedModelRenderer to, float timer, float maxTime) {
      this.rotateAngleX = this.rotateAngleX + (to.rotateAngleX - this.rotateAngleX) / maxTime * timer;
      this.rotateAngleY = this.rotateAngleY + (to.rotateAngleY - this.rotateAngleY) / maxTime * timer;
      this.rotateAngleZ = this.rotateAngleZ + (to.rotateAngleZ - this.rotateAngleZ) / maxTime * timer;
      this.rotationPointX = this.rotationPointX + (to.rotationPointX - this.rotationPointX) / maxTime * timer;
      this.rotationPointY = this.rotationPointY + (to.rotationPointY - this.rotationPointY) / maxTime * timer;
      this.rotationPointZ = this.rotationPointZ + (to.rotationPointZ - this.rotationPointZ) / maxTime * timer;
   }

   public void setMatrixOverrides(Matrix3f mat3Override, Matrix4f mat4Override) {
      this.mat4Override = mat4Override;
      this.mat3Override = mat3Override;
   }

   public void clearMatrixOverrides() {
      this.mat3Override = null;
      this.mat4Override = null;
   }

   public void getMatrixStack(PoseStack matrixStack) {
      AdvancedModelRenderer parent = this.getParent();
      if (parent != null) {
         parent.getMatrixStack(matrixStack);
      }

      this.translateRotate(matrixStack);
   }

   public Vec3 getWorldPos(Entity entity, float delta) {
      PoseStack matrixStack = new PoseStack();
      float dx = (float)(entity.xOld + (entity.getX() - entity.xOld) * delta);
      float dy = (float)(entity.yOld + (entity.getY() - entity.yOld) * delta);
      float dz = (float)(entity.zOld + (entity.getZ() - entity.zOld) * delta);
      matrixStack.translate(dx, dy, dz);
      float dYaw = Mth.rotLerp(delta, entity.yRotO, entity.getYRot());
      matrixStack.mulPose(MathUtils.quatFromRotationXYZ(0.0F, -dYaw + 180.0F, 0.0F, true));
      matrixStack.scale(-1.0F, -1.0F, 1.0F);
      matrixStack.translate(0.0F, -1.5F, 0.0F);
      MowzieRenderUtils.matrixStackFromModel(matrixStack, this);
      Pose matrixEntry = matrixStack.last();
      Matrix4f matrix4f = matrixEntry.pose();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.mul(matrix4f);
      return new Vec3(vec.x(), vec.y(), vec.z());
   }

   public void setWorldPos(Entity entity, Vec3 worldPos, float delta) {
      PoseStack matrixStack = new PoseStack();
      float dx = (float)(entity.xOld + (entity.getX() - entity.xOld) * delta);
      float dy = (float)(entity.yOld + (entity.getY() - entity.yOld) * delta);
      float dz = (float)(entity.zOld + (entity.getZ() - entity.zOld) * delta);
      matrixStack.translate(dx, dy, dz);
      float dYaw = Mth.rotLerp(delta, entity.yRotO, entity.getYRot());
      matrixStack.mulPose(MathUtils.quatFromRotationXYZ(0.0F, -dYaw + 180.0F, 0.0F, true));
      matrixStack.scale(-1.0F, -1.0F, 1.0F);
      matrixStack.translate(0.0F, -1.5F, 0.0F);
      Pose matrixEntry = matrixStack.last();
      Matrix4f matrix4f = matrixEntry.pose();
      matrix4f.invert();
      Vector4f vec = new Vector4f((float)worldPos.x(), (float)worldPos.y(), (float)worldPos.z(), 1.0F);
      vec.mul(matrix4f);
      this.rotationPointX = vec.x() * 16.0F;
      this.rotationPointY = vec.y() * 16.0F;
      this.rotationPointZ = vec.z() * 16.0F;
   }

   public static class ModelBox extends AdvancedModelRenderer.ModelPart {
      protected final AdvancedModelRenderer.TexturedQuad[] quads;
      public final float posX1;
      public final float posY1;
      public final float posZ1;
      public final float posX2;
      public final float posY2;
      public final float posZ2;

      public ModelBox(
         int texOffX,
         int texOffY,
         float x,
         float y,
         float z,
         float width,
         float height,
         float depth,
         float deltaX,
         float deltaY,
         float deltaZ,
         boolean mirror,
         float texWidth,
         float texHeight
      ) {
         this.posX1 = x;
         this.posY1 = y;
         this.posZ1 = z;
         this.posX2 = x + width;
         this.posY2 = y + height;
         this.posZ2 = z + depth;
         this.quads = new AdvancedModelRenderer.TexturedQuad[6];
         float f = x + width;
         float f1 = y + height;
         float f2 = z + depth;
         x -= deltaX;
         y -= deltaY;
         z -= deltaZ;
         f += deltaX;
         f1 += deltaY;
         f2 += deltaZ;
         if (mirror) {
            float f3 = f;
            f = x;
            x = f3;
         }

         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex7 = new AdvancedModelRenderer.PositionTextureVertex(x, y, z, 0.0F, 0.0F);
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = new AdvancedModelRenderer.PositionTextureVertex(f, y, z, 0.0F, 8.0F);
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex1 = new AdvancedModelRenderer.PositionTextureVertex(
            f, f1, z, 8.0F, 8.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex2 = new AdvancedModelRenderer.PositionTextureVertex(
            x, f1, z, 8.0F, 0.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex3 = new AdvancedModelRenderer.PositionTextureVertex(
            x, y, f2, 0.0F, 0.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex4 = new AdvancedModelRenderer.PositionTextureVertex(
            f, y, f2, 0.0F, 8.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex5 = new AdvancedModelRenderer.PositionTextureVertex(
            f, f1, f2, 8.0F, 8.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex6 = new AdvancedModelRenderer.PositionTextureVertex(
            x, f1, f2, 8.0F, 0.0F
         );
         float f4 = texOffX;
         float f5 = texOffX + depth;
         float f6 = texOffX + depth + width;
         float f7 = texOffX + depth + width + width;
         float f8 = texOffX + depth + width + depth;
         float f9 = texOffX + depth + width + depth + width;
         float f10 = texOffY;
         float f11 = texOffY + depth;
         float f12 = texOffY + depth + height;
         this.quads[2] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex4,
               modelrenderer$positiontexturevertex3,
               modelrenderer$positiontexturevertex7,
               modelrenderer$positiontexturevertex
            },
            f5,
            f10,
            f6,
            f11,
            texWidth,
            texHeight,
            mirror,
            Direction.DOWN
         );
         this.quads[3] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex1,
               modelrenderer$positiontexturevertex2,
               modelrenderer$positiontexturevertex6,
               modelrenderer$positiontexturevertex5
            },
            f6,
            f11,
            f7,
            f10,
            texWidth,
            texHeight,
            mirror,
            Direction.UP
         );
         this.quads[1] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex7,
               modelrenderer$positiontexturevertex3,
               modelrenderer$positiontexturevertex6,
               modelrenderer$positiontexturevertex2
            },
            f4,
            f11,
            f5,
            f12,
            texWidth,
            texHeight,
            mirror,
            Direction.WEST
         );
         this.quads[4] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex,
               modelrenderer$positiontexturevertex7,
               modelrenderer$positiontexturevertex2,
               modelrenderer$positiontexturevertex1
            },
            f5,
            f11,
            f6,
            f12,
            texWidth,
            texHeight,
            mirror,
            Direction.NORTH
         );
         this.quads[0] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex4,
               modelrenderer$positiontexturevertex,
               modelrenderer$positiontexturevertex1,
               modelrenderer$positiontexturevertex5
            },
            f6,
            f11,
            f8,
            f12,
            texWidth,
            texHeight,
            mirror,
            Direction.EAST
         );
         this.quads[5] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex3,
               modelrenderer$positiontexturevertex4,
               modelrenderer$positiontexturevertex5,
               modelrenderer$positiontexturevertex6
            },
            f8,
            f11,
            f9,
            f12,
            texWidth,
            texHeight,
            mirror,
            Direction.SOUTH
         );
      }

      @Override
      public void render(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
         for (AdvancedModelRenderer.TexturedQuad modelrenderer$texturedquad : this.quads) {
            Vector3f vector3f = new Vector3f(modelrenderer$texturedquad.normal);
            vector3f.mul(matrix3f);
            float f = vector3f.x();
            float f1 = vector3f.y();
            float f2 = vector3f.z();

            for (int i = 0; i < 4; i++) {
               AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = modelrenderer$texturedquad.vertexPositions[i];
               float f3 = modelrenderer$positiontexturevertex.position.x() / 16.0F;
               float f4 = modelrenderer$positiontexturevertex.position.y() / 16.0F;
               float f5 = modelrenderer$positiontexturevertex.position.z() / 16.0F;
               Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
               vector4f.mul(matrix4f);
               bufferIn.addVertex(
                  vector4f.x(),
                  vector4f.y(),
                  vector4f.z(),
                  color,
                  modelrenderer$positiontexturevertex.textureU,
                  modelrenderer$positiontexturevertex.textureV,
                  packedOverlayIn,
                  packedLightIn,
                  f,
                  f1,
                  f2
               );
            }
         }
      }
   }

   public abstract static class ModelPart {
      public void render(Matrix4f mat4, Matrix3f mat3, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
      }
   }

   static class PositionTextureVertex {
      public final Vector3f position;
      public final float textureU;
      public final float textureV;

      public PositionTextureVertex(float x, float y, float z, float texU, float texV) {
         this(new Vector3f(x, y, z), texU, texV);
      }

      public AdvancedModelRenderer.PositionTextureVertex setTextureUV(float texU, float texV) {
         return new AdvancedModelRenderer.PositionTextureVertex(this.position, texU, texV);
      }

      public PositionTextureVertex(Vector3f posIn, float texU, float texV) {
         this.position = posIn;
         this.textureU = texU;
         this.textureV = texV;
      }
   }

   static class TexturedQuad {
      public final AdvancedModelRenderer.PositionTextureVertex[] vertexPositions;
      public final Vector3f normal;

      public TexturedQuad(
         AdvancedModelRenderer.PositionTextureVertex[] positionsIn,
         float u1,
         float v1,
         float u2,
         float v2,
         float texWidth,
         float texHeight,
         boolean mirrorIn,
         Direction directionIn
      ) {
         this.vertexPositions = positionsIn;
         float f = 0.0F / texWidth;
         float f1 = 0.0F / texHeight;
         positionsIn[0] = positionsIn[0].setTextureUV(u2 / texWidth - f, v1 / texHeight + f1);
         positionsIn[1] = positionsIn[1].setTextureUV(u1 / texWidth + f, v1 / texHeight + f1);
         positionsIn[2] = positionsIn[2].setTextureUV(u1 / texWidth + f, v2 / texHeight - f1);
         positionsIn[3] = positionsIn[3].setTextureUV(u2 / texWidth - f, v2 / texHeight - f1);
         if (mirrorIn) {
            int i = positionsIn.length;

            for (int j = 0; j < i / 2; j++) {
               AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = positionsIn[j];
               positionsIn[j] = positionsIn[i - 1 - j];
               positionsIn[i - 1 - j] = modelrenderer$positiontexturevertex;
            }
         }

         this.normal = directionIn.step();
         if (mirrorIn) {
            this.normal.mul(-1.0F, 1.0F, 1.0F);
         }
      }
   }
}
