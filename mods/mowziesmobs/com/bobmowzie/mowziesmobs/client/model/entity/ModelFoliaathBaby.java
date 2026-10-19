package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.server.entity.foliaath.EntityBabyFoliaath;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class ModelFoliaathBaby<T extends EntityBabyFoliaath> extends MowzieEntityModel<T> {
   public AdvancedModelRenderer infantBase;
   public AdvancedModelRenderer juvenileBase;
   public AdvancedModelRenderer infantLeaf1;
   public AdvancedModelRenderer infantLeaf2;
   public AdvancedModelRenderer infantLeaf3;
   public AdvancedModelRenderer infantLeaf4;
   public AdvancedModelRenderer juvenileLeaf1;
   public AdvancedModelRenderer juvenileLeaf2;
   public AdvancedModelRenderer juvenileLeaf3;
   public AdvancedModelRenderer juvenileLeaf4;
   public AdvancedModelRenderer mouthBase;
   public AdvancedModelRenderer mouth1;
   public AdvancedModelRenderer mouth2;
   public AdvancedModelRenderer mouthCover;
   public AdvancedModelRenderer teeth1;
   public AdvancedModelRenderer teeth2;

   public ModelFoliaathBaby() {
      this.textureWidth = 64;
      this.textureHeight = 16;
      this.juvenileLeaf3 = new AdvancedModelRenderer(this, 27, 0);
      this.juvenileLeaf3.setRotationPoint(-1.0F, 0.0F, 1.0F);
      this.juvenileLeaf3.addBox(-2.0F, 0.0F, -7.0F, 4.0F, 0.0F, 7.0F, 0.0F);
      setRotateAngle(this.juvenileLeaf3, (float) (-Math.PI / 9), (float) (Math.PI * 3.0 / 4.0), 0.0F);
      this.mouthBase = new AdvancedModelRenderer(this, 13, 0);
      this.mouthBase.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.mouthBase.addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F);
      this.mouth1 = new AdvancedModelRenderer(this, 20, 0);
      this.mouth1.setRotationPoint(0.5F, -1.0F, 0.0F);
      this.mouth1.addBox(0.0F, -5.0F, -2.5F, 2.0F, 5.0F, 5.0F, 0.0F);
      setRotateAngle(this.mouth1, 0.0F, 0.0F, 0.0F);
      this.infantLeaf3 = new AdvancedModelRenderer(this, -3, 0);
      this.infantLeaf3.setRotationPoint(0.2F, 0.0F, 0.2F);
      this.infantLeaf3.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 0.0F);
      setRotateAngle(this.infantLeaf3, (float) (-Math.PI / 6), (float) (-Math.PI * 3.0 / 4.0), 0.0F);
      this.infantBase = new AdvancedModelRenderer(this, 0, 0);
      this.infantBase.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.infantBase.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      this.infantLeaf2 = new AdvancedModelRenderer(this, -3, 0);
      this.infantLeaf2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.infantLeaf2.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 0.0F);
      setRotateAngle(this.infantLeaf2, (float) (-Math.PI / 6), (float) (Math.PI / 4), 0.0F);
      this.juvenileLeaf2 = new AdvancedModelRenderer(this, 27, 0);
      this.juvenileLeaf2.setRotationPoint(-1.0F, 0.0F, -1.0F);
      this.juvenileLeaf2.addBox(-2.0F, 0.0F, -7.0F, 4.0F, 0.0F, 7.0F, 0.0F);
      setRotateAngle(this.juvenileLeaf2, (float) (-Math.PI / 9), (float) (Math.PI / 4), 0.0F);
      this.juvenileBase = new AdvancedModelRenderer(this, 0, 0);
      this.juvenileBase.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.juvenileBase.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      this.teeth1 = new AdvancedModelRenderer(this, 49, 2);
      this.teeth1.setRotationPoint(-1.0F, 0.0F, 0.0F);
      this.teeth1.addBox(0.0F, -5.0F, -2.5F, 1.0F, 5.0F, 5.0F, 0.0F);
      this.juvenileLeaf4 = new AdvancedModelRenderer(this, 27, 0);
      this.juvenileLeaf4.setRotationPoint(1.0F, 0.0F, 1.0F);
      this.juvenileLeaf4.addBox(-2.0F, 0.0F, -7.0F, 4.0F, 0.0F, 7.0F, 0.0F);
      setRotateAngle(this.juvenileLeaf4, (float) (-Math.PI / 9), (float) (Math.PI * 5.0 / 4.0), 0.0F);
      this.juvenileLeaf1 = new AdvancedModelRenderer(this, 27, 0);
      this.juvenileLeaf1.setRotationPoint(1.0F, 0.0F, -1.0F);
      this.juvenileLeaf1.addBox(-2.0F, 0.0F, -7.0F, 4.0F, 0.0F, 7.0F, 0.0F);
      setRotateAngle(this.juvenileLeaf1, (float) (-Math.PI / 9), (float) (-Math.PI / 4), 0.0F);
      this.teeth2 = new AdvancedModelRenderer(this, 37, 2);
      this.teeth2.setRotationPoint(-1.0F, 0.0F, 0.0F);
      this.teeth2.addBox(0.0F, -5.0F, -2.5F, 1.0F, 5.0F, 5.0F, 0.0F);
      this.mouth2 = new AdvancedModelRenderer(this, 20, 0);
      this.mouth2.setRotationPoint(-0.5F, -1.0F, 0.0F);
      this.mouth2.addBox(0.0F, -5.0F, -2.5F, 2.0F, 5.0F, 5.0F, 0.0F);
      setRotateAngle(this.mouth2, 0.0F, (float) Math.PI, 0.0F);
      this.infantLeaf1 = new AdvancedModelRenderer(this, -3, 0);
      this.infantLeaf1.setRotationPoint(0.2F, 0.0F, -0.2F);
      this.infantLeaf1.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 0.0F);
      setRotateAngle(this.infantLeaf1, (float) (-Math.PI / 6), (float) (-Math.PI / 4), 0.0F);
      this.infantLeaf4 = new AdvancedModelRenderer(this, -3, 0);
      this.infantLeaf4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.infantLeaf4.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 0.0F);
      setRotateAngle(this.infantLeaf4, (float) (-Math.PI / 6), (float) (-Math.PI * 5.0 / 4.0), 0.0F);
      this.mouthCover = new AdvancedModelRenderer(this, 0, 0);
      this.mouthCover.setRotationPoint(0.0F, -1.0F, 0.0F);
      this.mouthCover.addBox(-2.0F, -1.0F, -2.5F, 4.0F, 1.0F, 5.0F, 0.0F);
      this.juvenileBase.addChild(this.juvenileLeaf3);
      this.juvenileBase.addChild(this.mouthBase);
      this.mouthBase.addChild(this.mouth1);
      this.infantBase.addChild(this.infantLeaf3);
      this.infantBase.addChild(this.infantLeaf2);
      this.juvenileBase.addChild(this.juvenileLeaf2);
      this.mouth1.addChild(this.teeth1);
      this.juvenileBase.addChild(this.juvenileLeaf4);
      this.juvenileBase.addChild(this.juvenileLeaf1);
      this.mouth2.addChild(this.teeth2);
      this.mouthBase.addChild(this.mouth2);
      this.infantBase.addChild(this.infantLeaf1);
      this.infantBase.addChild(this.infantLeaf4);
      this.mouthBase.addChild(this.mouthCover);
      this.updateDefaultPose();
   }

   public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
      this.infantBase.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      this.juvenileBase.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
   }

   public void setDefaultAngles(EntityBabyFoliaath entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float delta) {
      this.resetToDefaultPose();
      float frame = entity.frame + delta;
      float openMouthProgress = entity.activate.getAnimationProgressSinSqrt();
      this.mouth1.rotateAngleZ = (float)(this.mouth1.rotateAngleZ + 0.5 * openMouthProgress);
      this.mouth2.rotateAngleZ = (float)(this.mouth2.rotateAngleZ - 0.5 * openMouthProgress);
      this.walk(this.juvenileLeaf1, 1.0F, 0.07F * openMouthProgress, false, 0.0F, 0.0F, frame, 1.0F);
      this.walk(this.juvenileLeaf2, 1.0F, 0.07F * openMouthProgress, false, 0.0F, 0.0F, frame, 1.0F);
      this.walk(this.juvenileLeaf3, 1.0F, 0.07F * openMouthProgress, false, 0.0F, 0.0F, frame, 1.0F);
      this.walk(this.juvenileLeaf4, 1.0F, 0.07F * openMouthProgress, false, 0.0F, 0.0F, frame, 1.0F);
      this.flap(this.mouth1, 1.0F, 0.07F * openMouthProgress, false, -1.0F, 0.0F, frame, 1.0F);
      this.flap(this.mouth2, 1.0F, -0.07F * openMouthProgress, false, -1.0F, 0.0F, frame, 1.0F);
      this.infantBase.showModel = !(this.juvenileBase.showModel = !entity.getInfant());
   }

   protected void animate(EntityBabyFoliaath entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float delta) {
      this.setDefaultAngles(entity, limbSwing, limbSwingAmount, headYaw, headPitch, delta);
      this.animator.setAnimation(EntityBabyFoliaath.EAT_ANIMATION);
      this.animator.startKeyframe(2);
      this.animator.rotate(this.mouth1, 0.0F, 0.0F, 0.5F);
      this.animator.rotate(this.mouth2, 0.0F, 0.0F, -0.5F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(2);
      this.animator.startKeyframe(2);
      this.animator.rotate(this.mouth1, 0.0F, 0.0F, 0.5F);
      this.animator.rotate(this.mouth2, 0.0F, 0.0F, -0.5F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(2);
      this.animator.startKeyframe(2);
      this.animator.rotate(this.mouth1, 0.0F, 0.0F, 0.5F);
      this.animator.rotate(this.mouth2, 0.0F, 0.0F, -0.5F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(2);
      this.animator.startKeyframe(2);
      this.animator.rotate(this.mouth1, 0.0F, 0.0F, 0.5F);
      this.animator.rotate(this.mouth2, 0.0F, 0.0F, -0.5F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(2);
      this.animator.startKeyframe(2);
      this.animator.rotate(this.mouth1, 0.0F, 0.0F, 0.5F);
      this.animator.rotate(this.mouth2, 0.0F, 0.0F, -0.5F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(2);
   }
}
