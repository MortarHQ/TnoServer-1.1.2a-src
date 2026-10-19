package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.The_Watcher_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;

public class The_Watcher_Model extends AdvancedEntityModel<The_Watcher_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox pivot;
   private final AdvancedModelBox head;
   private final AdvancedModelBox jaw;
   private final AdvancedModelBox cannon;
   private final AdvancedModelBox bone;
   private final AdvancedModelBox headgear;
   private final AdvancedModelBox right_wing;
   private final AdvancedModelBox sail;
   private final AdvancedModelBox left_wing;
   private final AdvancedModelBox sail2;
   private final AdvancedModelBox booster;
   private final AdvancedModelBox upper_sub_booster;
   private final AdvancedModelBox lower_sub_booster;
   private ModelAnimator animator;

   public The_Watcher_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.pivot = new AdvancedModelBox(this);
      this.pivot.setRotationPoint(0.0F, -5.0F, 0.0F);
      this.root.addChild(this.pivot);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.pivot.addChild(this.head);
      this.head.setTextureOffset(32, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
      this.head.setTextureOffset(0, 8).addBox(-2.0F, -6.0F, -4.0F, 4.0F, 4.0F, 8.0F, 0.0F, false);
      this.head.setTextureOffset(0, 0).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);
      this.head.setTextureOffset(0, 20).addBox(-3.0F, -3.0F, -3.5F, 6.0F, 2.0F, 1.0F, 0.0F, false);
      this.jaw = new AdvancedModelBox(this);
      this.jaw.setRotationPoint(0.0F, -1.0F, 2.5F);
      this.head.addChild(this.jaw);
      this.setRotationAngle(this.jaw, 0.3054F, 0.0F, 0.0F);
      this.jaw.setTextureOffset(37, 17).addBox(-4.0F, -1.0F, -6.0F, 8.0F, 3.0F, 8.0F, 0.0F, false);
      this.jaw.setTextureOffset(0, 0).addBox(-4.0F, 1.0F, -6.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);
      this.cannon = new AdvancedModelBox(this);
      this.cannon.setRotationPoint(0.0F, -5.0F, 3.0F);
      this.head.addChild(this.cannon);
      this.cannon.setTextureOffset(56, 28).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
      this.cannon.setTextureOffset(56, 34).addBox(-1.5F, -1.5F, -5.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
      this.bone = new AdvancedModelBox(this);
      this.bone.setRotationPoint(0.0F, 0.0F, -3.0F);
      this.cannon.addChild(this.bone);
      this.bone.setTextureOffset(13, 54).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
      this.headgear = new AdvancedModelBox(this);
      this.headgear.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.head.addChild(this.headgear);
      this.headgear.setTextureOffset(0, 25).addBox(0.0F, -15.0F, -10.0F, 0.0F, 9.0F, 15.0F, 0.0F, false);
      this.headgear.setTextureOffset(45, 10).addBox(-3.0F, -7.0F, -7.0F, 6.0F, 3.0F, 3.0F, 0.0F, false);
      this.headgear.setTextureOffset(0, 25).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 5.0F, 10.0F, 0.0F, false);
      this.right_wing = new AdvancedModelBox(this);
      this.right_wing.setRotationPoint(0.0F, -4.0F, 4.0F);
      this.headgear.addChild(this.right_wing);
      this.right_wing.setTextureOffset(14, 20).addBox(-6.0F, -1.0F, -5.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
      this.sail = new AdvancedModelBox(this);
      this.sail.setRotationPoint(-5.0F, 0.0F, -4.0F);
      this.right_wing.addChild(this.sail);
      this.setRotationAngle(this.sail, 0.0F, 0.3491F, -0.4363F);
      this.sail.setTextureOffset(0, 0).addBox(-7.0F, 0.0F, -15.0F, 10.0F, 0.0F, 25.0F, 0.0F, false);
      this.left_wing = new AdvancedModelBox(this);
      this.left_wing.setRotationPoint(0.0F, -4.0F, 4.0F);
      this.headgear.addChild(this.left_wing);
      this.left_wing.setTextureOffset(14, 20).addBox(3.0F, -1.0F, -5.0F, 3.0F, 2.0F, 2.0F, 0.0F, true);
      this.sail2 = new AdvancedModelBox(this);
      this.sail2.setRotationPoint(5.0F, 0.0F, -4.0F);
      this.left_wing.addChild(this.sail2);
      this.setRotationAngle(this.sail2, 0.0F, -0.3491F, 0.4363F);
      this.sail2.setTextureOffset(0, 0).addBox(-3.0F, 0.0F, -15.0F, 10.0F, 0.0F, 25.0F, 0.0F, true);
      this.booster = new AdvancedModelBox(this);
      this.booster.setRotationPoint(0.0F, -4.0F, 4.0F);
      this.pivot.addChild(this.booster);
      this.booster.setTextureOffset(43, 56).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
      this.booster.setTextureOffset(27, 48).addBox(-3.0F, -3.0F, 2.0F, 6.0F, 6.0F, 3.0F, 0.0F, false);
      this.booster.setTextureOffset(45, 0).addBox(-3.0F, -3.0F, 2.75F, 6.0F, 6.0F, 4.0F, 0.3F, false);
      this.booster.setTextureOffset(0, 0).addBox(0.0F, -6.0F, 1.0F, 0.0F, 12.0F, 4.0F, 0.0F, false);
      this.upper_sub_booster = new AdvancedModelBox(this);
      this.upper_sub_booster.setRotationPoint(0.0F, -5.0F, 3.0F);
      this.booster.addChild(this.upper_sub_booster);
      this.setRotationAngle(this.upper_sub_booster, 0.3054F, 0.0F, 0.0F);
      this.upper_sub_booster.setTextureOffset(0, 49).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);
      this.lower_sub_booster = new AdvancedModelBox(this);
      this.lower_sub_booster.setRotationPoint(0.0F, 5.0F, 3.0F);
      this.booster.addChild(this.lower_sub_booster);
      this.setRotationAngle(this.lower_sub_booster, -0.3054F, 0.0F, 0.0F);
      this.lower_sub_booster.setTextureOffset(45, 48).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void animate(The_Watcher_Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(The_Watcher_Entity.WATCHER_BITE);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(-5.0), 0.0F);
      this.animator.move(this.sail, 0.0F, 0.0F, -1.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(5.0), 0.0F);
      this.animator.move(this.sail2, 0.0F, 0.0F, -1.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.move(this.pivot, 0.0F, 0.0F, 3.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(5.0), 0.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(30.0), 0.0F);
      this.animator.move(this.sail, 0.0F, 0.0F, 3.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-30.0), 0.0F);
      this.animator.move(this.sail2, 0.0F, 0.0F, 3.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(5);
      this.animator.move(this.pivot, 0.0F, 0.0F, 3.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(5.0), 0.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(20.0), 0.0F);
      this.animator.move(this.sail, 0.0F, 0.0F, 2.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-20.0), 0.0F);
      this.animator.move(this.sail2, 0.0F, 0.0F, 2.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.move(this.pivot, 0.0F, 0.0F, -2.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-20.0), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.sail, (float)Math.toRadians(7.5), (float)Math.toRadians(-30.0), 0.0F);
      this.animator.rotate(this.sail2, (float)Math.toRadians(7.5), (float)Math.toRadians(30.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(5);
      this.animator.setStaticKeyframe(3);
      this.animator.setAnimation(The_Watcher_Entity.WATCHER_EXTRA_SHOT);
      this.animator.startKeyframe(0);
      this.animator.move(this.bone, 0.0F, 0.0F, -1.5F);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.move(this.pivot, 0.0F, -4.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(7.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, 0.5F, 0.0F);
      this.animator.move(this.cannon, 0.0F, 3.5F, -1.0F);
      this.animator.rotate(this.cannon, (float)Math.toRadians(17.5), 0.0F, 0.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(5.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(5);
      this.animator.startKeyframe(2);
      this.animator.move(this.bone, 0.0F, 0.0F, 0.5F);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.move(this.pivot, 0.0F, -4.0F, 3.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(17.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, 0.5F, 0.0F);
      this.animator.move(this.cannon, 0.0F, 3.5F, -1.0F);
      this.animator.rotate(this.cannon, (float)Math.toRadians(17.5), 0.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(12.5), 0.0F);
      this.animator.move(this.sail, 0.0F, 0.0F, 1.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-7.5), 0.0F);
      this.animator.move(this.sail2, 0.0F, 0.0F, 1.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.move(this.bone, 0.0F, 0.0F, -1.5F);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.move(this.pivot, 0.0F, -4.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(7.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, 0.5F, 0.0F);
      this.animator.move(this.cannon, 0.0F, 3.5F, -1.0F);
      this.animator.rotate(this.cannon, (float)Math.toRadians(17.5), 0.0F, 0.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(5.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(3);
      this.animator.resetKeyframe(5);
      this.animator.setAnimation(The_Watcher_Entity.WATCHER_SHOT);
      this.animator.startKeyframe(7);
      this.animator.rotate(this.pivot, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(10);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-372.5), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(10.0), 0.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-10.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(0);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-12.5), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(10.0), 0.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-10.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(5);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(15.0), 0.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-15.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(3);
      this.animator.startKeyframe(2);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(10.0), 0.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-10.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(30.0), 0.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-32.5), 0.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(5);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.cannon, (float)Math.toRadians(17.5), 0.0F, 0.0F);
      this.animator.move(this.cannon, 0.0F, 4.0F, 0.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(35.0), 0.0F);
      this.animator.move(this.sail, 0.0F, 0.0F, 2.5F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-33.3F), 0.0F);
      this.animator.move(this.sail2, 0.0F, 0.0F, 2.5F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.cannon, (float)Math.toRadians(17.5), 0.0F, 0.0F);
      this.animator.move(this.cannon, 0.0F, 5.0F, -2.0F);
      this.animator.move(this.bone, 0.0F, 0.0F, -2.0F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(37.5), 0.0F);
      this.animator.move(this.sail, 0.0F, 0.0F, 4.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-35.0), 0.0F);
      this.animator.move(this.sail2, 0.0F, 0.0F, 4.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.cannon, (float)Math.toRadians(17.5), 0.0F, 0.0F);
      this.animator.move(this.cannon, 0.0F, 3.5F, -1.0F);
      this.animator.move(this.bone, 0.0F, 0.0F, -1.5F);
      this.animator.rotate(this.sail, 0.0F, (float)Math.toRadians(37.5), 0.0F);
      this.animator.move(this.sail, 0.0F, 0.0F, -2.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(-35.0), 0.0F);
      this.animator.move(this.sail2, 0.0F, 0.0F, -2.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.move(this.bone, 0.0F, 0.0F, -1.5F);
      this.animator.rotate(this.pivot, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.move(this.pivot, 0.0F, -4.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(7.5), 0.0F, 0.0F);
      this.animator.move(this.jaw, 0.0F, 0.5F, 0.0F);
      this.animator.move(this.cannon, 0.0F, 3.5F, -1.0F);
      this.animator.rotate(this.cannon, (float)Math.toRadians(17.5), 0.0F, 0.0F);
      this.animator.rotate(this.sail2, 0.0F, (float)Math.toRadians(5.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.resetKeyframe(5);
   }

   public void setupAnim(The_Watcher_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.faceTarget(netHeadYaw, headPitch, 1.0F, new AdvancedModelBox[]{this.pivot});
      float globalSpeed = 0.15F;
      float globalDegree = 0.5F;
      this.bob(this.pivot, globalSpeed, globalDegree * 5.0F, false, ageInTicks, 1.0F);
      this.bob(this.pivot, globalSpeed, globalDegree * 5.0F, false, limbSwing, limbSwingAmount);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.pivot,
         this.head,
         this.jaw,
         this.cannon,
         this.bone,
         this.headgear,
         this.right_wing,
         this.sail,
         this.head,
         this.left_wing,
         this.sail2,
         new AdvancedModelBox[]{this.booster, this.upper_sub_booster, this.lower_sub_booster}
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
