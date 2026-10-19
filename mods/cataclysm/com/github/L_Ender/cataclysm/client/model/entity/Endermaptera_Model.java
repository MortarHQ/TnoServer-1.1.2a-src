package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.Endermaptera_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import com.google.common.collect.ImmutableList;

public class Endermaptera_Model extends AdvancedEntityModel<Endermaptera_Entity> {
   public final AdvancedModelBox root;
   public final AdvancedModelBox body;
   public final AdvancedModelBox abdomen;
   public final AdvancedModelBox head;
   public final AdvancedModelBox head_left;
   public final AdvancedModelBox head_right;
   public final AdvancedModelBox head_top;
   public final AdvancedModelBox left_antenna;
   public final AdvancedModelBox right_antenna;
   public final AdvancedModelBox right_jaw;
   public final AdvancedModelBox left_jaw;
   public final AdvancedModelBox right_leg_front;
   public final AdvancedModelBox left_leg_front;
   public final AdvancedModelBox right_leg_mid;
   public final AdvancedModelBox left_leg_mid;
   public final AdvancedModelBox right_leg_back;
   public final AdvancedModelBox left_leg_back;
   private ModelAnimator animator;

   public Endermaptera_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0F, -1.0F, 0.0F);
      this.root.addChild(this.body);
      this.body.setTextureOffset(22, 15).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 4.0F, 4.0F, 0.0F, false);
      this.body.setTextureOffset(0, 0).addBox(0.0F, -7.0F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, false);
      this.abdomen = new AdvancedModelBox(this);
      this.abdomen.setRotationPoint(0.0F, -2.0F, 1.0F);
      this.body.addChild(this.abdomen);
      this.setRotationAngle(this.abdomen, 0.2618F, 0.0F, 0.0F);
      this.abdomen.setTextureOffset(0, 15).addBox(-3.0F, -2.5F, 0.5F, 6.0F, 5.0F, 9.0F, 0.5F, false);
      this.abdomen.setTextureOffset(0, 0).addBox(-3.0F, -2.5F, 0.5F, 6.0F, 5.0F, 9.0F, 0.25F, false);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -0.85F, -4.5F);
      this.body.addChild(this.head);
      this.head.setTextureOffset(29, 34).addBox(-1.5F, -2.15F, -0.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
      this.head_left = new AdvancedModelBox(this);
      this.head_left.setRotationPoint(2.0F, -0.15F, 1.0F);
      this.head.addChild(this.head_left);
      this.setRotationAngle(this.head_left, 0.2618F, 0.2618F, 0.2618F);
      this.head_left.setTextureOffset(0, 37).addBox(-1.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, 0.25F, false);
      this.head_right = new AdvancedModelBox(this);
      this.head_right.setRotationPoint(-2.0F, -0.15F, 1.0F);
      this.head.addChild(this.head_right);
      this.setRotationAngle(this.head_right, 0.2618F, -0.2618F, -0.2618F);
      this.head_right.setTextureOffset(0, 37).addBox(-0.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, 0.25F, true);
      this.head_top = new AdvancedModelBox(this);
      this.head_top.setRotationPoint(0.0F, -3.95F, 1.0F);
      this.head.addChild(this.head_top);
      this.setRotationAngle(this.head_top, 0.5236F, 0.0F, 0.0F);
      this.head_top.setTextureOffset(0, 30).addBox(-2.0F, 0.8F, -3.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
      this.left_antenna = new AdvancedModelBox(this);
      this.left_antenna.setRotationPoint(-1.0F, -1.15F, -0.5F);
      this.head.addChild(this.left_antenna);
      this.setRotationAngle(this.left_antenna, 0.0F, 0.48F, 0.0F);
      this.left_antenna.setTextureOffset(24, 24).addBox(-1.0F, -2.0F, -6.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);
      this.right_antenna = new AdvancedModelBox(this);
      this.right_antenna.setRotationPoint(1.0F, -1.15F, -0.5F);
      this.head.addChild(this.right_antenna);
      this.setRotationAngle(this.right_antenna, 0.0F, -0.48F, 0.0F);
      this.right_antenna.setTextureOffset(24, 24).addBox(0.0F, -2.0F, -6.0F, 1.0F, 2.0F, 7.0F, 0.0F, true);
      this.right_jaw = new AdvancedModelBox(this);
      this.right_jaw.setRotationPoint(-1.5F, 0.65F, 1.5F);
      this.head.addChild(this.right_jaw);
      this.right_jaw.setTextureOffset(12, 32).addBox(-2.0F, 0.0F, -5.0F, 3.0F, 0.0F, 5.0F, 0.0F, false);
      this.left_jaw = new AdvancedModelBox(this);
      this.left_jaw.setRotationPoint(1.5F, 0.65F, 1.5F);
      this.head.addChild(this.left_jaw);
      this.left_jaw.setTextureOffset(31, 9).addBox(-1.0F, 0.0F, -5.0F, 3.0F, 0.0F, 5.0F, 0.0F, false);
      this.right_leg_front = new AdvancedModelBox(this);
      this.right_leg_front.setRotationPoint(-3.0F, -1.0F, -1.0F);
      this.body.addChild(this.right_leg_front);
      this.setRotationAngle(this.right_leg_front, 0.0F, -0.5672F, 0.0F);
      this.right_leg_front.setTextureOffset(34, 24).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 6.0F, 0.0F, 0.0F, true);
      this.left_leg_front = new AdvancedModelBox(this);
      this.left_leg_front.setRotationPoint(3.0F, -1.0F, -1.0F);
      this.body.addChild(this.left_leg_front);
      this.setRotationAngle(this.left_leg_front, 0.0F, 0.5672F, 0.0F);
      this.left_leg_front.setTextureOffset(34, 24).addBox(0.0F, -4.0F, 0.0F, 7.0F, 6.0F, 0.0F, 0.0F, false);
      this.right_leg_mid = new AdvancedModelBox(this);
      this.right_leg_mid.setRotationPoint(-3.0F, -1.0F, 0.0F);
      this.body.addChild(this.right_leg_mid);
      this.right_leg_mid.setTextureOffset(34, 24).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 6.0F, 0.0F, 0.0F, true);
      this.left_leg_mid = new AdvancedModelBox(this);
      this.left_leg_mid.setRotationPoint(3.0F, -1.0F, 0.0F);
      this.body.addChild(this.left_leg_mid);
      this.left_leg_mid.setTextureOffset(34, 24).addBox(0.0F, -4.0F, 0.0F, 7.0F, 6.0F, 0.0F, 0.0F, false);
      this.right_leg_back = new AdvancedModelBox(this);
      this.right_leg_back.setRotationPoint(-3.0F, -1.0F, 1.0F);
      this.body.addChild(this.right_leg_back);
      this.setRotationAngle(this.right_leg_back, 0.0F, 0.5672F, 0.0F);
      this.right_leg_back.setTextureOffset(22, 0).addBox(-10.0F, -6.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, true);
      this.left_leg_back = new AdvancedModelBox(this);
      this.left_leg_back.setRotationPoint(3.0F, -1.0F, 1.0F);
      this.body.addChild(this.left_leg_back);
      this.setRotationAngle(this.left_leg_back, 0.0F, -0.5672F, 0.0F);
      this.left_leg_back.setTextureOffset(22, 0).addBox(0.0F, -6.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(Endermaptera_Entity.JAW_ATTACK);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.right_jaw, 0.0F, (float)Math.toRadians(30.0), 0.0F);
      this.animator.rotate(this.left_jaw, 0.0F, (float)Math.toRadians(-30.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(5);
      this.animator.resetKeyframe(3);
      this.animator.setAnimation(Endermaptera_Entity.HEADBUTT_ATTACK);
      this.animator.startKeyframe(5);
      this.animator.move(this.body, 0.0F, 0.0F, -5.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.abdomen, (float)Math.toRadians(25.0), 0.0F, 0.0F);
      this.animator.rotate(this.left_antenna, (float)Math.toRadians(-25.0), (float)Math.toRadians(-25.0), 0.0F);
      this.animator.rotate(this.right_antenna, (float)Math.toRadians(-25.0), (float)Math.toRadians(25.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(5);
      this.animator.move(this.body, 0.0F, 0.0F, 2.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(25.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(3);
   }

   public void setupAnim(Endermaptera_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float idleSpeed = 0.25F;
      float idleDegree = 0.25F;
      float walkSpeed = 1.0F;
      float walkDegree = 1.0F;
      float offsetleft = 2.0F;
      this.swing(this.left_antenna, idleSpeed, idleDegree, true, 1.0F, 0.1F, ageInTicks, 1.0F);
      this.swing(this.right_antenna, idleSpeed, idleDegree, false, 1.0F, 0.1F, ageInTicks, 1.0F);
      this.walk(this.left_antenna, idleSpeed, idleDegree * 0.25F, false, -1.0F, -0.05F, ageInTicks, 1.0F);
      this.walk(this.right_antenna, idleSpeed, idleDegree * 0.25F, false, -1.0F, -0.05F, ageInTicks, 1.0F);
      this.swing(this.left_leg_back, walkSpeed, walkDegree, false, 0.0F, -0.5F, limbSwing, limbSwingAmount);
      this.swing(this.right_leg_front, walkSpeed, walkDegree, false, 0.0F, -0.3F, limbSwing, limbSwingAmount);
      this.flap(this.right_leg_front, walkSpeed, walkDegree * 0.8F, false, -1.5F, 0.4F, limbSwing, limbSwingAmount);
      this.swing(this.left_leg_mid, walkSpeed, walkDegree, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.flap(this.left_leg_mid, walkSpeed, walkDegree * 0.8F, false, -1.5F, -0.4F, limbSwing, limbSwingAmount);
      this.swing(this.right_leg_back, walkSpeed, -walkDegree, false, offsetleft, 0.5F, limbSwing, limbSwingAmount);
      this.swing(this.left_leg_front, walkSpeed, -walkDegree, false, offsetleft, 0.3F, limbSwing, limbSwingAmount);
      this.flap(this.left_leg_front, walkSpeed, walkDegree * 0.8F, false, offsetleft + 1.5F, -0.4F, limbSwing, limbSwingAmount);
      this.swing(this.right_leg_mid, walkSpeed, -walkDegree, false, offsetleft, 0.0F, limbSwing, limbSwingAmount);
      this.flap(this.right_leg_mid, walkSpeed, walkDegree * 0.8F, false, offsetleft - 1.5F, 0.4F, limbSwing, limbSwingAmount);
      this.swing(this.abdomen, walkSpeed, walkDegree * 0.2F, false, 3.0F, 0.0F, limbSwing, limbSwingAmount);
      this.faceTarget(netHeadYaw, headPitch, 1.0F, new AdvancedModelBox[]{this.head});
      this.right_jaw.showModel = entity.getHasJaws();
      this.left_jaw.showModel = entity.getHasJaws();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.head,
         this.body,
         this.abdomen,
         this.left_antenna,
         this.right_antenna,
         this.left_jaw,
         this.right_jaw,
         this.left_leg_front,
         this.right_leg_front,
         this.left_leg_mid,
         this.right_leg_mid,
         new AdvancedModelBox[]{this.left_leg_back, this.right_leg_back}
      );
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
