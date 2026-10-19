package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.server.entity.lantern.EntityLantern;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class ModelLantern<T extends EntityLantern> extends MowzieEntityModel<T> {
   public AdvancedModelRenderer body;
   public AdvancedModelRenderer center;
   public AdvancedModelRenderer bubbles;
   public AdvancedModelRenderer bubble1;
   public AdvancedModelRenderer bubble2;
   public AdvancedModelRenderer bubble3;
   public AdvancedModelRenderer bubble4;
   public AdvancedModelRenderer bottomBits;
   public AdvancedModelRenderer stem;
   public AdvancedModelRenderer bottomBit1;
   public AdvancedModelRenderer bottomBit2;
   public AdvancedModelRenderer bottomBit3;
   public AdvancedModelRenderer bottomBit4;
   public AdvancedModelRenderer leaf1;
   public AdvancedModelRenderer leaf2;
   public AdvancedModelRenderer leaf3;
   public AdvancedModelRenderer leaf4;
   public AdvancedModelRenderer stem1;
   public AdvancedModelRenderer stem2;
   public AdvancedModelRenderer scaleController;

   public ModelLantern() {
      this(false);
   }

   public ModelLantern(boolean isGelLayer) {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.leaf1 = new AdvancedModelRenderer(this, -16, 42);
      this.leaf1.mirror = true;
      this.leaf1.setRotationPoint(0.0F, 0.0F, 2.0F);
      this.leaf1.addBox(-6.0F, 0.0F, -16.0F, 12.0F, 0.0F, 16.0F, 0.0F);
      setRotateAngle(this.leaf1, (float) (-Math.PI / 12), (float) Math.PI, 0.0F);
      this.stem2 = new AdvancedModelRenderer(this, 0, 20);
      this.stem2.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.stem2.addBox(0.0F, -10.0F, -5.0F, 0.0F, 10.0F, 10.0F, 0.0F);
      setRotateAngle(this.stem2, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.body = new AdvancedModelRenderer(this, 1, 0);
      this.body.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.body.addBox(-7.5F, -7.5F, -7.5F, 15.0F, 15.0F, 15.0F, 0.0F);
      this.center = new AdvancedModelRenderer(this, 40, 51);
      this.center.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.center.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F);
      this.bottomBit4 = new AdvancedModelRenderer(this, 46, 0);
      this.bottomBit4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bottomBit4.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F);
      setRotateAngle(this.bottomBit4, (float) (Math.PI / 3), (float) (Math.PI * 3.0 / 2.0), 0.0F);
      this.leaf4 = new AdvancedModelRenderer(this, 8, 30);
      this.leaf4.setRotationPoint(2.0F, 0.0F, 0.0F);
      this.leaf4.addBox(0.0F, 0.0F, -6.0F, 16.0F, 0.0F, 12.0F, 0.0F);
      setRotateAngle(this.leaf4, 0.0F, 0.0F, (float) (-Math.PI / 12));
      this.leaf2 = new AdvancedModelRenderer(this, 8, 30);
      this.leaf2.setRotationPoint(-2.0F, 0.0F, 0.0F);
      this.leaf2.addBox(0.0F, 0.0F, -6.0F, 16.0F, 0.0F, 12.0F, 0.0F);
      setRotateAngle(this.leaf2, 0.0F, (float) Math.PI, (float) (Math.PI / 12));
      this.bubble3 = new AdvancedModelRenderer(this, 0, 0);
      this.bubble3.setRotationPoint(-2.0F, 4.0F, -2.9F);
      this.bubble3.addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.bubble3, -0.091106184F, 1.775698F, 0.4098033F);
      this.bubble4 = new AdvancedModelRenderer(this, 0, 0);
      this.bubble4.setRotationPoint(3.0F, -1.8F, -2.4F);
      this.bubble4.addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.bubble4, -0.7740535F, 0.13665928F, 0.4098033F);
      this.bubble1 = new AdvancedModelRenderer(this, 0, 7);
      this.bubble1.setRotationPoint(2.6F, 2.5F, 2.8F);
      this.bubble1.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F);
      setRotateAngle(this.bubble1, 0.27314404F, 0.68294734F, 0.5462881F);
      this.bottomBit3 = new AdvancedModelRenderer(this, 46, 0);
      this.bottomBit3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bottomBit3.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F);
      setRotateAngle(this.bottomBit3, (float) (Math.PI / 3), (float) Math.PI, 0.0F);
      this.stem1 = new AdvancedModelRenderer(this, 40, 42);
      this.stem1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.stem1.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F);
      this.bubble2 = new AdvancedModelRenderer(this, 0, 7);
      this.bubble2.setRotationPoint(-2.8F, -3.0F, 1.8F);
      this.bubble2.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F);
      setRotateAngle(this.bubble2, 1.3203416F, 1.5025539F, 0.5462881F);
      this.bottomBit2 = new AdvancedModelRenderer(this, 46, 0);
      this.bottomBit2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bottomBit2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F);
      setRotateAngle(this.bottomBit2, (float) (Math.PI / 3), (float) (Math.PI / 2), 0.0F);
      this.bottomBit1 = new AdvancedModelRenderer(this, 46, 0);
      this.bottomBit1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bottomBit1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F);
      setRotateAngle(this.bottomBit1, (float) (Math.PI / 3), 0.0F, 0.0F);
      this.leaf3 = new AdvancedModelRenderer(this, -16, 42);
      this.leaf3.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.leaf3.addBox(-6.0F, 0.0F, -16.0F, 12.0F, 0.0F, 16.0F, 0.0F);
      setRotateAngle(this.leaf3, (float) (-Math.PI / 12), 0.0F, 0.0F);
      this.bottomBits = new AdvancedModelRenderer(this, 0, 0);
      this.bottomBits.setRotationPoint(0.0F, 7.5F, 0.0F);
      this.bottomBits.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.bottomBits, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.stem = new AdvancedModelRenderer(this, 0, 0);
      this.stem.setRotationPoint(0.0F, -7.51F, 0.0F);
      this.stem.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.stem, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.bubbles = new AdvancedModelRenderer(this, 0, 0);
      this.bubbles.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.bubbles.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      this.scaleController = new AdvancedModelRenderer(this, 0, 0);
      this.scaleController.setRotationPoint(1.0F, 1.0F, 1.0F);
      this.stem.addChild(this.leaf1);
      this.stem1.addChild(this.stem2);
      this.bottomBits.addChild(this.bottomBit4);
      this.stem.addChild(this.leaf4);
      this.stem.addChild(this.leaf2);
      this.bottomBits.addChild(this.bottomBit3);
      this.stem.addChild(this.stem1);
      this.bottomBits.addChild(this.bottomBit2);
      this.bottomBits.addChild(this.bottomBit1);
      this.stem.addChild(this.leaf3);
      this.body.addChild(this.bottomBits);
      this.body.addChild(this.stem);
      this.bubbles.addChild(this.bubble1);
      this.bubbles.addChild(this.bubble2);
      this.bubbles.addChild(this.bubble3);
      this.bubbles.addChild(this.bubble4);
      this.leaf1.rotationPointX = (float)(this.leaf1.rotationPointX + 0.5);
      this.leaf2.rotationPointZ = (float)(this.leaf2.rotationPointZ - 0.5);
      this.leaf3.rotationPointX = (float)(this.leaf3.rotationPointX + 0.5);
      this.leaf4.rotationPointZ = (float)(this.leaf4.rotationPointZ + 0.5);
      this.stem2.rotationPointX = (float)(this.stem2.rotationPointX + 0.3536);
      this.stem2.rotationPointZ = (float)(this.stem2.rotationPointZ + 0.3536);
      this.updateDefaultPose();
      this.bubbles.setShouldScaleChildren(true);
      this.body.setOpacity(0.6F);
      this.center.setOpacity(0.5F);
      this.center.setScale(2.0F, 2.0F, 2.0F);
      this.bottomBit1.setOpacity(0.7F);
      this.bottomBit2.setOpacity(0.7F);
      this.bottomBit3.setOpacity(0.7F);
      this.bottomBit4.setOpacity(0.7F);
      if (isGelLayer) {
         this.stem.setIsHidden(true);
      } else {
         this.stem.showModel = true;
         this.body.setIsHidden(true);
         this.bubbles.showModel = false;
         this.bottomBits.showModel = false;
         this.center.showModel = false;
      }
   }

   public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
      this.bubbles.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      this.center.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
   }

   public void setDefaultAngles() {
      this.resetToDefaultPose();
      this.body.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.center.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.bottomBit2.setRotationPoint(0.0F, 0.0F, 0.0F);
      setRotateAngle(this.bottomBit2, (float) (Math.PI / 3), (float) (Math.PI / 2), 0.0F);
      this.bottomBit1.setRotationPoint(0.0F, 0.0F, 0.0F);
      setRotateAngle(this.bottomBit1, (float) (Math.PI / 3), 0.0F, 0.0F);
      this.bottomBit3.setRotationPoint(0.0F, 0.0F, 0.0F);
      setRotateAngle(this.bottomBit3, (float) (Math.PI / 3), (float) Math.PI, 0.0F);
      this.bottomBit4.setRotationPoint(0.0F, 0.0F, 0.0F);
      setRotateAngle(this.bottomBit4, (float) (Math.PI / 3), (float) (Math.PI * 3.0 / 2.0), 0.0F);
      this.body.rotateAngleX = 0.0F;
      this.bubbles.rotateAngleX = 0.0F;
      this.center.rotateAngleX = 0.0F;
   }

   protected void animate(EntityLantern entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float delta) {
      this.setDefaultAngles();
      float frame = entity.frame + delta;
      if (entity.getAnimation() == EntityLantern.PUFF_ANIMATION) {
         this.animator.setAnimation(EntityLantern.PUFF_ANIMATION);
         this.animator.startKeyframe(7);
         this.animator.move(this.scaleController, 0.4F, -0.4F, 0.4F);
         this.animator.move(this.body, 0.0F, -3.0F, 0.0F);
         this.animator.move(this.bubbles, 0.0F, -3.0F, 0.0F);
         this.animator.move(this.center, 0.0F, -3.0F, 0.0F);
         this.animator.move(this.stem, 0.0F, 3.0F, 0.0F);
         this.animator.move(this.bottomBits, 0.0F, -3.0F, 0.0F);
         this.animator.rotate(this.leaf1, -0.2F, 0.0F, 0.0F);
         this.animator.rotate(this.leaf2, 0.0F, 0.0F, 0.2F);
         this.animator.rotate(this.leaf3, -0.2F, 0.0F, 0.0F);
         this.animator.rotate(this.leaf4, 0.0F, 0.0F, -0.2F);
         this.animator.endKeyframe();
         this.animator.startKeyframe(3);
         this.animator.move(this.scaleController, -0.45F, 0.6F, -0.45F);
         this.animator.move(this.body, 0.0F, 4.5F, 0.0F);
         this.animator.move(this.bubbles, 0.0F, 4.5F, 0.0F);
         this.animator.move(this.center, 0.0F, 4.5F, 0.0F);
         this.animator.move(this.stem, 0.0F, -4.5F, 0.0F);
         this.animator.move(this.bottomBits, 0.0F, 4.0F, 0.0F);
         this.animator.rotate(this.leaf1, 0.3F, 0.0F, 0.0F);
         this.animator.rotate(this.leaf2, 0.0F, 0.0F, -0.3F);
         this.animator.rotate(this.leaf3, 0.3F, 0.0F, 0.0F);
         this.animator.rotate(this.leaf4, 0.0F, 0.0F, 0.3F);
         this.animator.rotate(this.bottomBit1, -0.9F, 0.0F, 0.0F);
         this.animator.rotate(this.bottomBit2, -0.9F, 0.0F, 0.0F);
         this.animator.rotate(this.bottomBit3, -0.9F, 0.0F, 0.0F);
         this.animator.rotate(this.bottomBit4, -0.9F, 0.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.resetKeyframe(7);
      }

      if (entity.getAnimation() == EntityLantern.DIE_ANIMATION) {
         this.animator.setAnimation(EntityLantern.DIE_ANIMATION);
         this.animator.startKeyframe(3);
         this.animator.move(this.scaleController, -0.5F, -0.5F, -0.5F);
         this.animator.move(this.bottomBits, 0.0F, -3.0F, 0.0F);
         this.animator.move(this.stem, 0.0F, 3.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.startKeyframe(3);
         this.animator.move(this.scaleController, 1.3F, 1.3F, 1.3F);
         this.animator.move(this.bottomBits, 0.0F, 3.0F, 0.0F);
         this.animator.move(this.stem, 0.0F, -3.0F, 0.0F);
         this.animator.endKeyframe();
      }

      this.body.setScale(this.scaleController.rotationPointX, this.scaleController.rotationPointY, this.scaleController.rotationPointZ);
      this.bubbles.setShouldScaleChildren(true);
      this.bubbles.setScale(this.scaleController.rotationPointX, this.scaleController.rotationPointY, this.scaleController.rotationPointZ);
      this.center.setScale(this.scaleController.rotationPointX * 2.0F, this.scaleController.rotationPointY * 2.0F, this.scaleController.rotationPointZ * 2.0F);
   }
}
