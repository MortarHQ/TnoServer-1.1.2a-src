package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Priest_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;

public class Deepling_Priest_Model extends AdvancedEntityModel<Deepling_Priest_Entity> {
   public final AdvancedModelBox root;
   public final AdvancedModelBox left_leg;
   public final AdvancedModelBox right_leg;
   public final AdvancedModelBox body;
   public final AdvancedModelBox body_coral1;
   public final AdvancedModelBox body_coral2;
   public final AdvancedModelBox head;
   public final AdvancedModelBox head2;
   public final AdvancedModelBox head_coral;
   public final AdvancedModelBox fin;
   public final AdvancedModelBox light;
   public final AdvancedModelBox r_fin;
   public final AdvancedModelBox l_fin;
   public final AdvancedModelBox headwear;
   public final AdvancedModelBox right_arm;
   public final AdvancedModelBox left_arm;
   private ModelAnimator animator;

   public Deepling_Priest_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.left_leg = new AdvancedModelBox(this);
      this.left_leg.setRotationPoint(2.0F, -20.0F, 0.0F);
      this.root.addChild(this.left_leg);
      this.left_leg.setTextureOffset(40, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
      this.right_leg = new AdvancedModelBox(this);
      this.right_leg.setRotationPoint(-2.0F, -20.0F, 0.0F);
      this.root.addChild(this.right_leg);
      this.right_leg.setTextureOffset(44, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0F, -20.0F, 0.0F);
      this.root.addChild(this.body);
      this.body.setTextureOffset(0, 32).addBox(-5.0F, -11.0F, -2.0F, 10.0F, 11.0F, 4.0F, 0.0F, false);
      this.body.setTextureOffset(96, 0).addBox(-6.0F, -11.0F, -2.0F, 12.0F, 27.0F, 4.0F, 0.3F, false);
      this.body.setTextureOffset(92, 43).addBox(-6.5F, -11.0F, -2.5F, 13.0F, 17.0F, 5.0F, 0.0F, false);
      this.body.setTextureOffset(52, 29).addBox(0.0F, -11.0F, 2.0F, 0.0F, 11.0F, 4.0F, 0.0F, false);
      this.body_coral1 = new AdvancedModelBox(this);
      this.body_coral1.setRotationPoint(-6.3F, 3.6F, -2.3F);
      this.body.addChild(this.body_coral1);
      this.setRotationAngle(this.body_coral1, 0.0F, -0.1745F, 0.0F);
      this.body_coral1.setTextureOffset(89, 7).addBox(-5.0F, -3.5F, 0.0F, 5.0F, 7.0F, 0.0F, 0.0F, false);
      this.body_coral2 = new AdvancedModelBox(this);
      this.body_coral2.setRotationPoint(6.3F, -3.5F, -2.3F);
      this.body.addChild(this.body_coral2);
      this.setRotationAngle(this.body_coral2, 0.0F, 0.48F, 0.0F);
      this.body_coral2.setTextureOffset(89, 0).addBox(0.0F, -3.6F, 0.0F, 5.0F, 7.0F, 0.0F, 0.0F, false);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -11.0F, 0.0F);
      this.body.addChild(this.head);
      this.head2 = new AdvancedModelBox(this);
      this.head2.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.head.addChild(this.head2);
      this.head2.setTextureOffset(0, 16).addBox(-6.0F, -4.0F, -4.0F, 12.0F, 8.0F, 8.0F, 0.0F, false);
      this.head2.setTextureOffset(80, 14).addBox(-6.0F, -9.0F, 0.0F, 7.0F, 5.0F, 0.0F, 0.0F, false);
      this.head2.setTextureOffset(34, 87).addBox(-6.0F, -4.0F, -4.0F, 12.0F, 8.0F, 8.0F, -0.1F, false);
      this.head_coral = new AdvancedModelBox(this);
      this.head_coral.setRotationPoint(6.0F, 0.0F, -4.0F);
      this.head2.addChild(this.head_coral);
      this.setRotationAngle(this.head_coral, 0.0F, 0.2618F, 0.0F);
      this.head_coral.setTextureOffset(80, 19).addBox(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, false);
      this.fin = new AdvancedModelBox(this);
      this.fin.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.head2.addChild(this.fin);
      this.fin.setTextureOffset(28, 47).addBox(0.0F, -6.0F, -10.0F, 0.0F, 6.0F, 11.0F, 0.0F, false);
      this.light = new AdvancedModelBox(this);
      this.light.setRotationPoint(0.0F, 1.0F, -8.5F);
      this.fin.addChild(this.light);
      this.light.setTextureOffset(44, 54).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
      this.r_fin = new AdvancedModelBox(this);
      this.r_fin.setRotationPoint(-6.0F, 0.0F, 0.0F);
      this.head2.addChild(this.r_fin);
      this.r_fin.setTextureOffset(44, 44).addBox(-6.0F, -4.0F, 0.0F, 6.0F, 8.0F, 0.0F, 0.0F, false);
      this.l_fin = new AdvancedModelBox(this);
      this.l_fin.setRotationPoint(6.0F, 0.0F, 0.0F);
      this.head2.addChild(this.l_fin);
      this.l_fin.setTextureOffset(0, 47).addBox(0.0F, -4.0F, 0.0F, 6.0F, 8.0F, 0.0F, 0.0F, false);
      this.headwear = new AdvancedModelBox(this);
      this.headwear.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.head.addChild(this.headwear);
      this.headwear.setTextureOffset(0, 0).addBox(-6.0F, -7.0F, -4.0F, 12.0F, 8.0F, 8.0F, -0.5F, false);
      this.headwear.setTextureOffset(26, 71).addBox(-6.0F, -7.0F, -4.0F, 12.0F, 8.0F, 8.0F, -0.6F, false);
      this.right_arm = new AdvancedModelBox(this);
      this.right_arm.setRotationPoint(-6.0F, -10.0F, 0.0F);
      this.body.addChild(this.right_arm);
      this.right_arm.setTextureOffset(36, 32).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 24.0F, 2.0F, 0.0F, false);
      this.right_arm.setTextureOffset(52, 58).addBox(-6.0F, 2.0F, 0.0F, 5.0F, 12.0F, 0.0F, 0.0F, false);
      this.left_arm = new AdvancedModelBox(this);
      this.left_arm.setRotationPoint(6.0F, -10.0F, 0.0F);
      this.body.addChild(this.left_arm);
      this.left_arm.setTextureOffset(28, 32).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 24.0F, 2.0F, 0.0F, false);
      this.left_arm.setTextureOffset(52, 58).addBox(1.0F, 2.0F, 0.0F, 5.0F, 12.0F, 0.0F, 0.0F, true);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.left_leg,
         this.right_leg,
         this.body,
         this.headwear,
         this.head,
         this.head2,
         this.r_fin,
         this.l_fin,
         this.right_arm,
         this.left_arm,
         this.fin,
         new AdvancedModelBox[]{this.light}
      );
   }

   public void animate(Deepling_Priest_Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(Deepling_Priest_Entity.DEEPLING_MELEE);
      if (entity.isLeftHanded()) {
         this.animator.startKeyframe(4);
         this.animator.rotate(this.right_arm, (float)Math.toRadians(12.5), 0.0F, (float)Math.toRadians(10.0));
         this.animator.rotate(this.left_arm, 0.0F, 0.0F, (float)Math.toRadians(-75.0));
         this.animator.rotate(this.body, (float)Math.toRadians(-12.5), (float)Math.toRadians(-10.0), (float)Math.toRadians(12.5));
         this.animator.endKeyframe();
         this.animator.startKeyframe(2);
         this.animator.rotate(this.right_arm, (float)Math.toRadians(15.0), 0.0F, (float)Math.toRadians(10.0));
         this.animator.rotate(this.left_arm, (float)Math.toRadians(-107.5), (float)Math.toRadians(12.5), (float)Math.toRadians(-77.5));
         this.animator.rotate(this.body, (float)Math.toRadians(30.0), (float)Math.toRadians(30.0), (float)Math.toRadians(7.5));
         this.animator.endKeyframe();
         this.animator.resetKeyframe(14);
      } else {
         this.animator.startKeyframe(4);
         this.animator.rotate(this.left_arm, (float)Math.toRadians(12.5), 0.0F, (float)Math.toRadians(-10.0));
         this.animator.rotate(this.right_arm, 0.0F, 0.0F, (float)Math.toRadians(75.0));
         this.animator.rotate(this.body, (float)Math.toRadians(-12.5), (float)Math.toRadians(10.0), (float)Math.toRadians(-12.5));
         this.animator.endKeyframe();
         this.animator.startKeyframe(2);
         this.animator.rotate(this.left_arm, (float)Math.toRadians(15.0), 0.0F, (float)Math.toRadians(-10.0));
         this.animator.rotate(this.right_arm, (float)Math.toRadians(-107.5), (float)Math.toRadians(-12.5), (float)Math.toRadians(77.5));
         this.animator.rotate(this.body, (float)Math.toRadians(30.0), (float)Math.toRadians(-30.0), (float)Math.toRadians(-7.5));
         this.animator.endKeyframe();
         this.animator.resetKeyframe(14);
      }

      this.animator.setAnimation(Deepling_Priest_Entity.DEEPLING_BLIND);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.body, (float)Math.toRadians(37.5), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-35.0), (float)Math.toRadians(12.5), (float)Math.toRadians(-17.5));
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-35.0), (float)Math.toRadians(-12.5), (float)Math.toRadians(17.5));
      this.animator.rotate(this.fin, (float)Math.toRadians(12.5), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(4);
      this.animator.rotate(this.body, (float)Math.toRadians(-30.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-20.0), 0.0F, 0.0F);
      this.animator.move(this.head2, 0.0F, -7.0F, 0.0F);
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-160.0), (float)Math.toRadians(30.0), (float)Math.toRadians(-50.0));
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-160.0), (float)Math.toRadians(-30.0), (float)Math.toRadians(50.0));
      this.animator.rotate(this.fin, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(28);
      this.animator.resetKeyframe(10);
   }

   public void setupAnim(Deepling_Priest_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.faceTarget(netHeadYaw, headPitch, 1.0F, new AdvancedModelBox[]{this.head});
      float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      float swim = entity.getSwimAmount(partialTick);
      float walkSpeed = 1.0F;
      float walkDegree = 1.0F;
      float swimSpeed = 0.25F;
      float swimDegree = 0.5F;
      float swimAmount = limbSwingAmount * swim;
      this.walk(this.left_leg, walkSpeed, walkDegree * 1.2F, true, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.walk(this.right_leg, walkSpeed, walkDegree * 1.2F, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.walk(this.left_arm, walkSpeed, walkDegree * 1.2F, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.walk(this.right_arm, walkSpeed, walkDegree * 1.2F, true, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.progressRotationPrev(this.left_arm, swim, 0.0F, 0.0F, (float)Math.toRadians(-37.5), 10.0F);
      this.progressRotationPrev(this.right_arm, swim, 0.0F, 0.0F, (float)Math.toRadians(37.5), 10.0F);
      this.progressRotationPrev(this.root, swim, (float)Math.toRadians(80.0), 0.0F, 0.0F, 1.0F);
      this.progressRotationPrev(this.head, swim, (float)Math.toRadians(-70.0), 0.0F, 0.0F, 1.0F);
      this.progressPositionPrev(this.root, swim, 0.0F, -5.0F, 18.0F, 1.0F);
      this.flap(this.root, swimSpeed, swimDegree * 1.0F, true, 0.0F, 0.0F, limbSwing, swimAmount);
      this.swing(this.head, swimSpeed, swimDegree * 1.0F, false, 0.5F, 0.0F, limbSwing, swimAmount);
      this.flap(this.left_arm, swimSpeed, swimDegree * 2.75F, true, -0.5F, 1.5F, limbSwing, swimAmount);
      this.swing(this.left_arm, swimSpeed, swimDegree, true, -1.5F, 0.0F, limbSwing, swimAmount);
      this.walk(this.left_arm, swimSpeed, swimDegree, true, -2.0F, -0.2F, limbSwing, swimAmount);
      this.flap(this.right_arm, swimSpeed, swimDegree * 2.75F, false, -0.5F, 1.5F, limbSwing, swimAmount);
      this.swing(this.right_arm, swimSpeed, swimDegree, false, -1.5F, 0.0F, limbSwing, swimAmount);
      this.walk(this.right_arm, swimSpeed, swimDegree, false, -4.5F, -0.2F, limbSwing, swimAmount);
      this.walk(this.right_leg, swimSpeed * 1.5F, swimDegree * 1.0F, true, 2.0F, 0.0F, limbSwing, swimAmount);
      this.walk(this.left_leg, swimSpeed * 1.5F, swimDegree * 1.0F, false, 2.0F, 0.0F, limbSwing, swimAmount);
      if (this.riding) {
         this.root.rotationPointY += 13.0F;
         this.right_arm.rotateAngleX += (float) (-Math.PI / 5);
         this.left_arm.rotateAngleX += (float) (-Math.PI / 5);
         this.right_leg.rotateAngleX = -1.4137167F;
         this.right_leg.rotateAngleY = (float) (Math.PI / 10);
         this.right_leg.rotateAngleZ = 0.07853982F;
         this.left_leg.rotateAngleX = -1.4137167F;
         this.left_leg.rotateAngleY = (float) (-Math.PI / 10);
         this.left_leg.rotateAngleZ = -0.07853982F;
      }
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
