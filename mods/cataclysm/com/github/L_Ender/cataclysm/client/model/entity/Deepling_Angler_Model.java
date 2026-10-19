package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Angler_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;

public class Deepling_Angler_Model extends AdvancedEntityModel<Deepling_Angler_Entity> {
   public final AdvancedModelBox root;
   public final AdvancedModelBox left_leg;
   public final AdvancedModelBox right_leg;
   public final AdvancedModelBox body;
   private final AdvancedModelBox thorns;
   private final AdvancedModelBox thorns2;
   private final AdvancedModelBox thorns3;
   private final AdvancedModelBox thorns4;
   public final AdvancedModelBox head;
   public final AdvancedModelBox head2;
   public final AdvancedModelBox r_fin;
   public final AdvancedModelBox l_fin;
   public final AdvancedModelBox headwear;
   public final AdvancedModelBox right_arm;
   public final AdvancedModelBox left_arm;
   public ModelAnimator animator;

   public Deepling_Angler_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.left_leg = new AdvancedModelBox(this);
      this.left_leg.setRotationPoint(2.0F, -20.0F, 0.0F);
      this.root.addChild(this.left_leg);
      this.left_leg.setTextureOffset(40, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
      this.left_leg.setTextureOffset(44, 58).addBox(0.0F, 1.0F, -2.0F, 0.0F, 17.0F, 1.0F, 0.0F, false);
      this.left_leg.setTextureOffset(44, 58).addBox(0.0F, 0.0F, 1.0F, 0.0F, 17.0F, 1.0F, 0.0F, false);
      this.right_leg = new AdvancedModelBox(this);
      this.right_leg.setRotationPoint(-2.0F, -20.0F, 0.0F);
      this.root.addChild(this.right_leg);
      this.right_leg.setTextureOffset(44, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
      this.right_leg.setTextureOffset(44, 58).addBox(0.0F, 1.0F, -2.0F, 0.0F, 17.0F, 1.0F, 0.0F, false);
      this.right_leg.setTextureOffset(44, 58).addBox(0.0F, 0.0F, 1.0F, 0.0F, 17.0F, 1.0F, 0.0F, false);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0F, -20.0F, 0.0F);
      this.root.addChild(this.body);
      this.body.setTextureOffset(0, 32).addBox(-5.0F, -11.0F, -2.0F, 10.0F, 11.0F, 4.0F, 0.0F, false);
      this.body.setTextureOffset(52, 25).addBox(0.0F, -11.0F, 2.0F, 0.0F, 11.0F, 8.0F, 0.0F, false);
      this.thorns = new AdvancedModelBox(this);
      this.thorns.setRotationPoint(-3.0F, -5.5F, 2.0F);
      this.body.addChild(this.thorns);
      this.setRotationAngle(this.thorns, 0.0F, -0.6545F, 0.0F);
      this.thorns.setTextureOffset(52, 37).addBox(0.0F, -5.5F, 0.0F, 0.0F, 11.0F, 8.0F, 0.0F, false);
      this.thorns2 = new AdvancedModelBox(this);
      this.thorns2.setRotationPoint(3.0F, -5.5F, 2.0F);
      this.body.addChild(this.thorns2);
      this.setRotationAngle(this.thorns2, 0.0F, 0.6545F, 0.0F);
      this.thorns2.setTextureOffset(52, 37).addBox(0.0F, -5.5F, 0.0F, 0.0F, 11.0F, 8.0F, 0.0F, false);
      this.thorns3 = new AdvancedModelBox(this);
      this.thorns3.setRotationPoint(-5.0F, -4.5F, -2.0F);
      this.body.addChild(this.thorns3);
      this.setRotationAngle(this.thorns3, 0.0F, -0.9599F, 0.0F);
      this.thorns3.setTextureOffset(18, 57).addBox(0.0F, -5.5F, -4.0F, 0.0F, 11.0F, 4.0F, 0.0F, false);
      this.thorns4 = new AdvancedModelBox(this);
      this.thorns4.setRotationPoint(5.0F, -4.5F, -2.0F);
      this.body.addChild(this.thorns4);
      this.setRotationAngle(this.thorns4, 0.0F, 0.9599F, 0.0F);
      this.thorns4.setTextureOffset(18, 57).addBox(0.0F, -5.5F, -4.0F, 0.0F, 11.0F, 4.0F, 0.0F, false);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -11.0F, 0.0F);
      this.body.addChild(this.head);
      this.head2 = new AdvancedModelBox(this);
      this.head2.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.head.addChild(this.head2);
      this.head2.setTextureOffset(25, 94).addBox(-6.0F, -4.0F, -4.0F, 12.0F, 8.0F, 8.0F, -0.2F, false);
      this.head2.setTextureOffset(0, 16).addBox(-6.0F, -4.0F, -4.0F, 12.0F, 8.0F, 8.0F, 0.0F, false);
      this.head2.setTextureOffset(37, 74).addBox(-6.0F, -8.0F, -4.0F, 2.0F, 4.0F, 0.0F, 0.0F, false);
      this.head2.setTextureOffset(37, 74).addBox(4.0F, -8.0F, -4.0F, 2.0F, 4.0F, 0.0F, 0.0F, true);
      this.r_fin = new AdvancedModelBox(this);
      this.r_fin.setRotationPoint(-6.0F, 0.0F, 0.0F);
      this.head2.addChild(this.r_fin);
      this.r_fin.setTextureOffset(44, 44).addBox(-6.0F, -7.0F, 0.0F, 6.0F, 8.0F, 0.0F, 0.0F, false);
      this.l_fin = new AdvancedModelBox(this);
      this.l_fin.setRotationPoint(6.0F, 0.0F, 0.0F);
      this.head2.addChild(this.l_fin);
      this.l_fin.setTextureOffset(0, 47).addBox(0.0F, -7.0F, 0.0F, 6.0F, 8.0F, 0.0F, 0.0F, false);
      this.headwear = new AdvancedModelBox(this);
      this.headwear.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.head.addChild(this.headwear);
      this.headwear.setTextureOffset(0, 0).addBox(-6.0F, -7.0F, -4.0F, 12.0F, 8.0F, 8.0F, -0.5F, false);
      this.headwear.setTextureOffset(17, 78).addBox(-6.0F, -7.0F, -4.0F, 12.0F, 8.0F, 8.0F, -0.7F, false);
      this.right_arm = new AdvancedModelBox(this);
      this.right_arm.setRotationPoint(-6.0F, -10.0F, 0.0F);
      this.body.addChild(this.right_arm);
      this.right_arm.setTextureOffset(36, 32).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 24.0F, 2.0F, 0.0F, false);
      this.right_arm.setTextureOffset(52, 24).addBox(-0.5F, 23.0F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
      this.right_arm.setTextureOffset(44, 58).addBox(0.0F, 1.0F, -2.0F, 0.0F, 17.0F, 1.0F, 0.0F, false);
      this.right_arm.setTextureOffset(44, 58).addBox(0.0F, 1.0F, 1.0F, 0.0F, 17.0F, 1.0F, 0.0F, false);
      this.right_arm.setTextureOffset(36, 58).addBox(0.0F, 20.0F, 1.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
      this.right_arm.setTextureOffset(36, 56).addBox(0.0F, 20.0F, -3.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
      this.right_arm.setTextureOffset(38, 64).addBox(-3.0F, 20.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);
      this.right_arm.setTextureOffset(38, 62).addBox(1.0F, 20.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);
      this.right_arm.setTextureOffset(28, 58).addBox(-5.0F, 13.0F, 0.0F, 4.0F, 7.0F, 0.0F, 0.0F, true);
      this.right_arm.setTextureOffset(18, 47).addBox(-6.0F, -1.0F, 0.0F, 5.0F, 14.0F, 0.0F, 0.0F, true);
      this.left_arm = new AdvancedModelBox(this);
      this.left_arm.setRotationPoint(6.0F, -10.0F, 0.0F);
      this.body.addChild(this.left_arm);
      this.left_arm.setTextureOffset(28, 32).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 24.0F, 2.0F, 0.0F, false);
      this.left_arm.setTextureOffset(52, 24).addBox(-0.5F, 23.0F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
      this.left_arm.setTextureOffset(44, 58).addBox(0.0F, 1.0F, 1.0F, 0.0F, 17.0F, 1.0F, 0.0F, false);
      this.left_arm.setTextureOffset(44, 58).addBox(0.0F, 1.0F, -2.0F, 0.0F, 17.0F, 1.0F, 0.0F, false);
      this.left_arm.setTextureOffset(36, 56).addBox(0.0F, 20.0F, -3.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
      this.left_arm.setTextureOffset(38, 62).addBox(1.0F, 20.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);
      this.left_arm.setTextureOffset(38, 64).addBox(-3.0F, 20.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);
      this.left_arm.setTextureOffset(36, 58).addBox(0.0F, 20.0F, 1.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
      this.left_arm.setTextureOffset(18, 47).addBox(1.0F, -1.0F, 0.0F, 5.0F, 14.0F, 0.0F, 0.0F, false);
      this.left_arm.setTextureOffset(28, 58).addBox(1.0F, 13.0F, 0.0F, 4.0F, 7.0F, 0.0F, 0.0F, false);
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
         this.thorns,
         new AdvancedModelBox[]{this.thorns2, this.thorns3, this.thorns4}
      );
   }

   public void animate(Deepling_Angler_Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(Deepling_Angler_Entity.DEEPLING_MELEE);
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

      this.animator.setAnimation(Deepling_Angler_Entity.DEEPLING_HUG);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.body, (float)Math.toRadians(-17.5), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-37.5), 0.0F, (float)Math.toRadians(55.0));
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-37.5), 0.0F, (float)Math.toRadians(-55.0));
      this.animator.endKeyframe();
      this.animator.startKeyframe(5);
      this.animator.rotate(this.body, (float)Math.toRadians(40.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(82.5), 0.0F, 0.0F);
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-130.0), (float)Math.toRadians(-37.5), (float)Math.toRadians(80.0));
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-130.0), (float)Math.toRadians(37.5), (float)Math.toRadians(-80.0));
      this.animator.endKeyframe();
      this.animator.resetKeyframe(10);
   }

   public void setupAnim(Deepling_Angler_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
