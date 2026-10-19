package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Warlock_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;

public class Deepling_Warlock_Model extends AdvancedEntityModel<Deepling_Warlock_Entity> {
   public final AdvancedModelBox root;
   private final AdvancedModelBox left_leg;
   private final AdvancedModelBox right_leg;
   public final AdvancedModelBox body;
   private final AdvancedModelBox right_shoulder;
   private final AdvancedModelBox left_shoulder;
   private final AdvancedModelBox neck_back;
   private final AdvancedModelBox right_rib;
   private final AdvancedModelBox left_rib;
   private final AdvancedModelBox neck_forward;
   public final AdvancedModelBox right_arm;
   private final AdvancedModelBox right_finger1;
   private final AdvancedModelBox right_finger2;
   private final AdvancedModelBox right_finger3;
   private final AdvancedModelBox right_finger4;
   public final AdvancedModelBox left_arm;
   private final AdvancedModelBox left_finger1;
   private final AdvancedModelBox left_finger2;
   private final AdvancedModelBox left_finger3;
   private final AdvancedModelBox left_finger4;
   private final AdvancedModelBox head;
   private final AdvancedModelBox head2;
   private final AdvancedModelBox r_fin;
   private final AdvancedModelBox l_fin;
   private final AdvancedModelBox headwear;
   private ModelAnimator animator;

   public Deepling_Warlock_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.left_leg = new AdvancedModelBox(this);
      this.left_leg.setRotationPoint(2.0F, -20.0F, 0.0F);
      this.root.addChild(this.left_leg);
      this.left_leg.setTextureOffset(41, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F, 0.0F, true);
      this.right_leg = new AdvancedModelBox(this);
      this.right_leg.setRotationPoint(-2.0F, -20.0F, 0.0F);
      this.root.addChild(this.right_leg);
      this.right_leg.setTextureOffset(41, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0F, -20.0F, 0.0F);
      this.root.addChild(this.body);
      this.body.setTextureOffset(0, 34).addBox(-5.0F, -11.0F, -2.0F, 10.0F, 11.0F, 4.0F, 0.0F, false);
      this.body.setTextureOffset(33, 0).addBox(-5.0F, 0.0F, -2.0F, 10.0F, 3.0F, 4.0F, 0.0F, false);
      this.body.setTextureOffset(50, 8).addBox(0.0F, -11.0F, 2.0F, 0.0F, 11.0F, 4.0F, 0.0F, false);
      this.right_shoulder = new AdvancedModelBox(this);
      this.right_shoulder.setRotationPoint(-5.0F, -11.0F, 0.0F);
      this.body.addChild(this.right_shoulder);
      this.setRotationAngle(this.right_shoulder, 0.0F, 0.0F, -1.0036F);
      this.right_shoulder.setTextureOffset(50, 24).addBox(0.0F, -4.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, true);
      this.left_shoulder = new AdvancedModelBox(this);
      this.left_shoulder.setRotationPoint(5.0F, -11.0F, 0.0F);
      this.body.addChild(this.left_shoulder);
      this.setRotationAngle(this.left_shoulder, 0.0F, 0.0F, 1.0036F);
      this.left_shoulder.setTextureOffset(50, 24).addBox(0.0F, -4.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, false);
      this.neck_back = new AdvancedModelBox(this);
      this.neck_back.setRotationPoint(0.0F, -11.0F, 2.0F);
      this.body.addChild(this.neck_back);
      this.setRotationAngle(this.neck_back, 0.7854F, 0.0F, 0.0F);
      this.neck_back.setTextureOffset(38, 46).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, false);
      this.right_rib = new AdvancedModelBox(this);
      this.right_rib.setRotationPoint(-5.0F, -5.5F, -2.0F);
      this.body.addChild(this.right_rib);
      this.setRotationAngle(this.right_rib, 0.0F, 0.3927F, 0.0F);
      this.right_rib.setTextureOffset(13, 50).addBox(0.0F, -2.5F, 0.0F, 5.0F, 11.0F, 0.0F, 0.0F, true);
      this.left_rib = new AdvancedModelBox(this);
      this.left_rib.setRotationPoint(5.0F, -5.5F, -2.0F);
      this.body.addChild(this.left_rib);
      this.setRotationAngle(this.left_rib, 0.0F, -0.3927F, 0.0F);
      this.left_rib.setTextureOffset(13, 50).addBox(-5.0F, -2.5F, 0.0F, 5.0F, 11.0F, 0.0F, 0.0F, false);
      this.neck_forward = new AdvancedModelBox(this);
      this.neck_forward.setRotationPoint(0.0F, -11.0F, -2.0F);
      this.body.addChild(this.neck_forward);
      this.setRotationAngle(this.neck_forward, -0.6109F, 0.0F, 0.0F);
      this.neck_forward.setTextureOffset(38, 46).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 6.0F, 0.0F, 0.0F, false);
      this.right_arm = new AdvancedModelBox(this);
      this.right_arm.setRotationPoint(-6.0F, -10.0F, 0.0F);
      this.body.addChild(this.right_arm);
      this.right_arm.setTextureOffset(29, 46).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 24.0F, 2.0F, 0.0F, false);
      this.right_arm.setTextureOffset(47, 53).addBox(-4.0F, 1.0F, 0.0F, 3.0F, 12.0F, 0.0F, 0.0F, false);
      this.right_finger1 = new AdvancedModelBox(this);
      this.right_finger1.setRotationPoint(0.0F, 16.0F, -1.0F);
      this.right_arm.addChild(this.right_finger1);
      this.setRotationAngle(this.right_finger1, -0.1745F, 0.0F, 0.0F);
      this.right_finger1.setTextureOffset(0, 17).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 7.0F, 0.0F, 0.0F, false);
      this.right_finger2 = new AdvancedModelBox(this);
      this.right_finger2.setRotationPoint(-1.0F, 16.0F, 0.0F);
      this.right_arm.addChild(this.right_finger2);
      this.setRotationAngle(this.right_finger2, 0.0F, 0.0F, 0.1745F);
      this.right_finger2.setTextureOffset(54, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, true);
      this.right_finger3 = new AdvancedModelBox(this);
      this.right_finger3.setRotationPoint(0.0F, 16.0F, 1.0F);
      this.right_arm.addChild(this.right_finger3);
      this.setRotationAngle(this.right_finger3, 0.1745F, 0.0F, 0.0F);
      this.right_finger3.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 7.0F, 0.0F, 0.0F, false);
      this.right_finger4 = new AdvancedModelBox(this);
      this.right_finger4.setRotationPoint(1.0F, 16.0F, 0.0F);
      this.right_arm.addChild(this.right_finger4);
      this.setRotationAngle(this.right_finger4, 0.0F, 0.0F, -0.1745F);
      this.right_finger4.setTextureOffset(24, 50).addBox(0.0F, 0.0F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, true);
      this.left_arm = new AdvancedModelBox(this);
      this.left_arm.setRotationPoint(6.0F, -10.0F, 0.0F);
      this.body.addChild(this.left_arm);
      this.left_arm.setTextureOffset(29, 46).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 24.0F, 2.0F, 0.0F, true);
      this.left_arm.setTextureOffset(47, 53).addBox(1.0F, 1.0F, 0.0F, 3.0F, 12.0F, 0.0F, 0.0F, true);
      this.left_finger1 = new AdvancedModelBox(this);
      this.left_finger1.setRotationPoint(0.0F, 16.0F, -1.0F);
      this.left_arm.addChild(this.left_finger1);
      this.setRotationAngle(this.left_finger1, -0.1745F, 0.0F, 0.0F);
      this.left_finger1.setTextureOffset(0, 17).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 7.0F, 0.0F, 0.0F, true);
      this.left_finger2 = new AdvancedModelBox(this);
      this.left_finger2.setRotationPoint(1.0F, 16.0F, 0.0F);
      this.left_arm.addChild(this.left_finger2);
      this.setRotationAngle(this.left_finger2, 0.0F, 0.0F, -0.1745F);
      this.left_finger2.setTextureOffset(54, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, false);
      this.left_finger3 = new AdvancedModelBox(this);
      this.left_finger3.setRotationPoint(0.0F, 16.0F, 1.0F);
      this.left_arm.addChild(this.left_finger3);
      this.setRotationAngle(this.left_finger3, 0.1745F, 0.0F, 0.0F);
      this.left_finger3.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 7.0F, 0.0F, 0.0F, true);
      this.left_finger4 = new AdvancedModelBox(this);
      this.left_finger4.setRotationPoint(-1.0F, 16.0F, 0.0F);
      this.left_arm.addChild(this.left_finger4);
      this.setRotationAngle(this.left_finger4, 0.0F, 0.0F, 0.1745F);
      this.left_finger4.setTextureOffset(24, 50).addBox(0.0F, 0.0F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, false);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -11.0F, 0.0F);
      this.body.addChild(this.head);
      this.head2 = new AdvancedModelBox(this);
      this.head2.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.head.addChild(this.head2);
      this.head2.setTextureOffset(0, 17).addBox(-6.0F, -5.0F, -4.0F, 12.0F, 8.0F, 8.0F, 0.0F, false);
      this.head2.setTextureOffset(38, 53).addBox(0.0F, -5.0F, 4.0F, 0.0F, 8.0F, 4.0F, 0.0F, false);
      this.head2.setTextureOffset(29, 34).addBox(-6.0F, -5.0F, -7.0F, 12.0F, 8.0F, 3.0F, 0.0F, false);
      this.r_fin = new AdvancedModelBox(this);
      this.r_fin.setRotationPoint(-6.0F, 0.0F, 0.0F);
      this.head2.addChild(this.r_fin);
      this.r_fin.setTextureOffset(0, 50).addBox(-6.0F, -11.0F, 0.0F, 6.0F, 12.0F, 0.0F, 0.0F, false);
      this.l_fin = new AdvancedModelBox(this);
      this.l_fin.setRotationPoint(6.0F, 0.0F, 0.0F);
      this.head2.addChild(this.l_fin);
      this.l_fin.setTextureOffset(0, 50).addBox(0.0F, -11.0F, 0.0F, 6.0F, 12.0F, 0.0F, 0.0F, true);
      this.headwear = new AdvancedModelBox(this);
      this.headwear.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.head.addChild(this.headwear);
      this.headwear.setTextureOffset(0, 0).addBox(-6.0F, -7.0F, -4.0F, 12.0F, 8.0F, 8.0F, -0.5F, false);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root, this.left_leg, this.right_leg, this.body, this.headwear, this.head, this.head2, this.r_fin, this.l_fin, this.right_arm, this.left_arm
      );
   }

   public void animate(Deepling_Warlock_Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(Deepling_Warlock_Entity.DEEPLING_MAGIC);
      this.animator.startKeyframe(10);
      this.animator.rotate(this.body, (float)Math.toRadians(47.5), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(15.0), 0.0F, 0.0F);
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-55.0), (float)Math.toRadians(-12.5), (float)Math.toRadians(5.0));
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-54.0), (float)Math.toRadians(6.0), (float)Math.toRadians(3.5));
      this.animator.endKeyframe();
      this.animator.startKeyframe(10);
      this.animator.rotate(this.body, (float)Math.toRadians(-17.5), (float)Math.toRadians(2.5), 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-40.0), 0.0F, 0.0F);
      this.animator.move(this.head2, 0.0F, -6.0F, 0.0F);
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-137.5), (float)Math.toRadians(40.0), (float)Math.toRadians(-12.5));
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-137.5), (float)Math.toRadians(-40.0), (float)Math.toRadians(12.5));
      this.animator.endKeyframe();
      this.animator.startKeyframe(20);
      this.animator.rotate(this.body, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-32.5), (float)Math.toRadians(7.5), (float)Math.toRadians(-5.0));
      this.animator.move(this.head2, 0.0F, -5.0F, 0.0F);
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-115.0), (float)Math.toRadians(32.5), (float)Math.toRadians(-10.0));
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-100.0), (float)Math.toRadians(-32.5), (float)Math.toRadians(10.0));
      this.animator.endKeyframe();
      this.animator.startKeyframe(20);
      this.animator.rotate(this.body, (float)Math.toRadians(-17.5), (float)Math.toRadians(-7.5), (float)Math.toRadians(2.5));
      this.animator.rotate(this.head, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.move(this.head2, 0.0F, -6.0F, 0.0F);
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-110.0), (float)Math.toRadians(40.0), (float)Math.toRadians(-12.5));
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-110.0), (float)Math.toRadians(-40.0), (float)Math.toRadians(12.5));
      this.animator.endKeyframe();
      this.animator.startKeyframe(20);
      this.animator.rotate(this.body, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-12.5), (float)Math.toRadians(-7.5), (float)Math.toRadians(2.5));
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-55.0), (float)Math.toRadians(20.0), (float)Math.toRadians(-7.5));
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-55.0), (float)Math.toRadians(-20.0), (float)Math.toRadians(7.5));
      this.animator.endKeyframe();
      this.animator.resetKeyframe(10);
      this.animator.setAnimation(Deepling_Warlock_Entity.DEEPLING_MELEE);
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
   }

   public void setupAnim(Deepling_Warlock_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
