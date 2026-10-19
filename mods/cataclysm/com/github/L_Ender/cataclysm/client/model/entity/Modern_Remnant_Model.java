package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.Pet.Modern_Remnant_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;

public class Modern_Remnant_Model extends AdvancedEntityModel<Modern_Remnant_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox body;
   private final AdvancedModelBox upper_body;
   private final AdvancedModelBox right_arm;
   private final AdvancedModelBox right_hand;
   private final AdvancedModelBox left_arm;
   private final AdvancedModelBox left_hand;
   private final AdvancedModelBox neck;
   private final AdvancedModelBox bandage;
   private final AdvancedModelBox headjoint;
   private final AdvancedModelBox head;
   private final AdvancedModelBox helmet;
   private final AdvancedModelBox helmet2;
   private final AdvancedModelBox jaw;
   private final AdvancedModelBox tail;
   private final AdvancedModelBox tail2;
   private final AdvancedModelBox tail3;
   private final AdvancedModelBox right_leg;
   private final AdvancedModelBox right_leg_armor;
   private final AdvancedModelBox right_leg2;
   private final AdvancedModelBox right_leg_armor2;
   private final AdvancedModelBox right_leg_armor3;
   private final AdvancedModelBox left_leg;
   private final AdvancedModelBox left_leg_armor;
   private final AdvancedModelBox left_leg2;
   private final AdvancedModelBox left_leg_armor2;
   private final AdvancedModelBox left_leg_armor3;
   private ModelAnimator animator;

   public Modern_Remnant_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0F, -8.0F, -1.0F);
      this.root.addChild(this.body);
      this.body.setTextureOffset(0, 0).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 5.0F, 7.0F, 0.0F, false);
      this.body.setTextureOffset(14, 14).addBox(0.0F, -6.0F, -3.0F, 0.0F, 4.0F, 7.0F, 0.0F, false);
      this.upper_body = new AdvancedModelBox(this);
      this.upper_body.setRotationPoint(0.0F, -1.9F, -2.4F);
      this.body.addChild(this.upper_body);
      this.setRotationAngle(this.upper_body, -0.1745F, 0.0F, 0.0F);
      this.upper_body.setTextureOffset(0, 30).addBox(-2.5F, 0.0F, -3.3F, 5.0F, 4.0F, 3.0F, 0.0F, false);
      this.upper_body.setTextureOffset(25, 0).addBox(-2.5F, 0.0F, -3.3F, 5.0F, 4.0F, 3.0F, 0.3F, false);
      this.upper_body.setTextureOffset(0, 0).addBox(0.0F, -3.0F, -3.3F, 0.0F, 3.0F, 3.0F, 0.0F, false);
      this.right_arm = new AdvancedModelBox(this);
      this.right_arm.setRotationPoint(-3.0F, 2.5F, -2.8F);
      this.upper_body.addChild(this.right_arm);
      this.setRotationAngle(this.right_arm, 0.1745F, 0.0F, 0.0F);
      this.right_arm.setTextureOffset(16, 13).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
      this.right_hand = new AdvancedModelBox(this);
      this.right_hand.setRotationPoint(0.0F, 2.0F, -0.5F);
      this.right_arm.addChild(this.right_hand);
      this.right_hand.setTextureOffset(18, 0).addBox(0.0F, -0.5F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, false);
      this.left_arm = new AdvancedModelBox(this);
      this.left_arm.setRotationPoint(3.0F, 2.5F, -2.8F);
      this.upper_body.addChild(this.left_arm);
      this.setRotationAngle(this.left_arm, 0.1745F, 0.0F, 0.0F);
      this.left_arm.setTextureOffset(16, 13).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, true);
      this.left_hand = new AdvancedModelBox(this);
      this.left_hand.setRotationPoint(0.0F, 2.0F, -0.5F);
      this.left_arm.addChild(this.left_hand);
      this.left_hand.setTextureOffset(18, 0).addBox(0.0F, -0.5F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, true);
      this.neck = new AdvancedModelBox(this);
      this.neck.setRotationPoint(0.0F, 0.0F, -3.3F);
      this.upper_body.addChild(this.neck);
      this.neck.setTextureOffset(22, 8).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);
      this.neck.setTextureOffset(48, 7).addBox(0.0F, -1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 0.0F, false);
      this.bandage = new AdvancedModelBox(this);
      this.bandage.setRotationPoint(0.0F, 0.0F, -2.5F);
      this.neck.addChild(this.bandage);
      this.setRotationAngle(this.bandage, 0.1745F, 0.0F, 0.0F);
      this.bandage.setTextureOffset(30, 30).addBox(-1.5F, -1.1F, -1.0F, 3.0F, 8.0F, 2.0F, 0.2F, false);
      this.headjoint = new AdvancedModelBox(this);
      this.headjoint.setRotationPoint(0.0F, 0.0F, -4.6F);
      this.neck.addChild(this.headjoint);
      this.setRotationAngle(this.headjoint, 0.1745F, 0.0F, 0.0F);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.headjoint.addChild(this.head);
      this.head.setTextureOffset(37, 37).addBox(-1.0F, -1.0F, -8.0F, 2.0F, 3.0F, 4.0F, 0.1F, false);
      this.head.setTextureOffset(50, 40).addBox(0.0F, -4.0F, -6.3F, 0.0F, 4.0F, 2.0F, 0.0F, false);
      this.head.setTextureOffset(15, 40).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 8.0F, 2.0F, 0.2F, false);
      this.head.setTextureOffset(24, 41).addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 4.0F, 0.1F, false);
      this.head.setTextureOffset(25, 22).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
      this.helmet = new AdvancedModelBox(this);
      this.helmet.setRotationPoint(0.0F, -2.0F, -2.0F);
      this.head.addChild(this.helmet);
      this.setRotationAngle(this.helmet, -0.1745F, 0.0F, 0.0F);
      this.helmet.setTextureOffset(0, 13).addBox(-2.5F, -1.4F, -2.5F, 5.0F, 2.0F, 5.0F, 0.0F, false);
      this.helmet.setTextureOffset(0, 50).addBox(-4.0F, -1.4F, 0.5F, 2.0F, 7.0F, 0.0F, 0.0F, false);
      this.helmet.setTextureOffset(0, 50).addBox(2.0F, -1.4F, 0.5F, 2.0F, 7.0F, 0.0F, 0.0F, true);
      this.helmet2 = new AdvancedModelBox(this);
      this.helmet2.setRotationPoint(0.0F, -1.3F, -2.7F);
      this.helmet.addChild(this.helmet2);
      this.setRotationAngle(this.helmet2, -0.2618F, 0.0F, 0.0F);
      this.helmet2.setTextureOffset(0, 13).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, false);
      this.jaw = new AdvancedModelBox(this);
      this.jaw.setRotationPoint(0.0F, 1.0F, -1.5F);
      this.head.addChild(this.jaw);
      this.jaw.setTextureOffset(0, 38).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 1.0F, 3.0F, 0.0F, false);
      this.jaw.setTextureOffset(41, 26).addBox(-1.0F, 0.0F, -6.5F, 2.0F, 2.0F, 4.0F, 0.0F, false);
      this.jaw.setTextureOffset(38, 20).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 1.0F, 4.0F, 0.0F, false);
      this.tail = new AdvancedModelBox(this);
      this.tail.setRotationPoint(0.0F, 0.0F, 4.0F);
      this.body.addChild(this.tail);
      this.tail.setTextureOffset(0, 21).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
      this.tail.setTextureOffset(32, 45).addBox(0.0F, -2.0F, 1.0F, 0.0F, 1.0F, 5.0F, 0.0F, false);
      this.tail.setTextureOffset(45, 0).addBox(0.0F, 1.0F, 1.0F, 0.0F, 1.0F, 5.0F, 0.0F, false);
      this.tail2 = new AdvancedModelBox(this);
      this.tail2.setRotationPoint(0.0F, 0.5F, 6.0F);
      this.tail.addChild(this.tail2);
      this.tail2.setTextureOffset(34, 12).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 5.0F, 0.0F, false);
      this.tail2.setTextureOffset(0, 43).addBox(0.0F, -2.0F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, false);
      this.tail2.setTextureOffset(42, 10).addBox(0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, false);
      this.tail3 = new AdvancedModelBox(this);
      this.tail3.setRotationPoint(0.0F, 0.0F, 5.0F);
      this.tail2.addChild(this.tail3);
      this.tail3.setTextureOffset(37, 3).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);
      this.tail3.setTextureOffset(47, 17).addBox(0.0F, -1.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, false);
      this.tail3.setTextureOffset(7, 47).addBox(0.0F, 0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, false);
      this.right_leg = new AdvancedModelBox(this);
      this.right_leg.setRotationPoint(-3.5F, -8.0F, -1.0F);
      this.root.addChild(this.right_leg);
      this.right_leg.setTextureOffset(17, 30).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F, 0.0F, false);
      this.right_leg_armor = new AdvancedModelBox(this);
      this.right_leg_armor.setRotationPoint(0.0F, 2.5F, -1.0F);
      this.right_leg.addChild(this.right_leg_armor);
      this.setRotationAngle(this.right_leg_armor, 0.3054F, 0.0F, 0.0F);
      this.right_leg_armor.setTextureOffset(43, 45).addBox(-1.0F, -3.5F, -1.3F, 2.0F, 4.0F, 2.0F, 0.2F, true);
      this.right_leg2 = new AdvancedModelBox(this);
      this.right_leg2.setRotationPoint(0.0F, 4.0F, 1.55F);
      this.right_leg.addChild(this.right_leg2);
      this.right_leg2.setTextureOffset(46, 33).addBox(-1.0F, 0.0F, -1.05F, 2.0F, 4.0F, 2.0F, 0.0F, false);
      this.right_leg2.setTextureOffset(22, 17).addBox(-1.0F, 3.0F, -2.05F, 2.0F, 1.0F, 1.0F, 0.0F, false);
      this.right_leg_armor2 = new AdvancedModelBox(this);
      this.right_leg_armor2.setRotationPoint(0.0F, 1.5F, 0.95F);
      this.right_leg2.addChild(this.right_leg_armor2);
      this.setRotationAngle(this.right_leg_armor2, -0.48F, 0.0F, 0.0F);
      this.right_leg_armor2.setTextureOffset(24, 48).addBox(-0.5F, -2.5F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
      this.right_leg_armor3 = new AdvancedModelBox(this);
      this.right_leg_armor3.setRotationPoint(0.0F, 2.0F, -1.15F);
      this.right_leg2.addChild(this.right_leg_armor3);
      this.setRotationAngle(this.right_leg_armor3, 0.4363F, 0.0F, 0.0F);
      this.right_leg_armor3.setTextureOffset(0, 21).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
      this.left_leg = new AdvancedModelBox(this);
      this.left_leg.setRotationPoint(3.5F, -8.0F, -1.0F);
      this.root.addChild(this.left_leg);
      this.left_leg.setTextureOffset(17, 30).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F, 0.0F, true);
      this.left_leg_armor = new AdvancedModelBox(this);
      this.left_leg_armor.setRotationPoint(0.0F, 2.5F, -1.0F);
      this.left_leg.addChild(this.left_leg_armor);
      this.setRotationAngle(this.left_leg_armor, 0.3054F, 0.0F, 0.0F);
      this.left_leg_armor.setTextureOffset(43, 45).addBox(-1.0F, -3.5F, -1.3F, 2.0F, 4.0F, 2.0F, 0.2F, false);
      this.left_leg2 = new AdvancedModelBox(this);
      this.left_leg2.setRotationPoint(0.0F, 4.0F, 1.55F);
      this.left_leg.addChild(this.left_leg2);
      this.left_leg2.setTextureOffset(46, 33).addBox(-1.0F, 0.0F, -1.05F, 2.0F, 4.0F, 2.0F, 0.0F, true);
      this.left_leg2.setTextureOffset(22, 17).addBox(-1.0F, 3.0F, -2.05F, 2.0F, 1.0F, 1.0F, 0.0F, true);
      this.left_leg_armor2 = new AdvancedModelBox(this);
      this.left_leg_armor2.setRotationPoint(0.0F, 1.5F, 0.95F);
      this.left_leg2.addChild(this.left_leg_armor2);
      this.setRotationAngle(this.left_leg_armor2, -0.48F, 0.0F, 0.0F);
      this.left_leg_armor2.setTextureOffset(24, 48).addBox(-0.5F, -2.5F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, true);
      this.left_leg_armor3 = new AdvancedModelBox(this);
      this.left_leg_armor3.setRotationPoint(0.0F, 2.0F, -1.15F);
      this.left_leg2.addChild(this.left_leg_armor3);
      this.setRotationAngle(this.left_leg_armor3, 0.4363F, 0.0F, 0.0F);
      this.left_leg_armor3.setTextureOffset(0, 21).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, true);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void animate(Modern_Remnant_Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(Modern_Remnant_Entity.MODERN_REMNANT_BITE);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.root, 0.0F, (float)Math.toRadians(-12.5), 0.0F);
      this.animator.rotate(this.body, (float)Math.toRadians(-10.0), 0.0F, (float)Math.toRadians(7.5));
      this.animator.rotate(this.upper_body, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-22.5), (float)Math.toRadians(5.0), (float)Math.toRadians(20.0));
      this.animator.rotate(this.right_hand, (float)Math.toRadians(30.0), 0.0F, 0.0F);
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-25.0), (float)Math.toRadians(-5.0), (float)Math.toRadians(-20.0));
      this.animator.rotate(this.left_hand, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.neck, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(70.0), 0.0F, 0.0F);
      this.animator.rotate(this.tail, (float)Math.toRadians(-7.5), (float)Math.toRadians(-2.5), 0.0F);
      this.animator.rotate(this.tail2, 0.0F, (float)Math.toRadians(-12.5), 0.0F);
      this.animator.rotate(this.tail3, 0.0F, (float)Math.toRadians(-15.0), 0.0F);
      this.animator.move(this.left_leg, 0.0F, -1.5F, -1.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(3);
      this.animator.move(this.root, 0.0F, 0.2F, 0.0F);
      this.animator.rotate(this.root, 0.0F, (float)Math.toRadians(12.5), 0.0F);
      this.animator.rotate(this.body, 0.0F, (float)Math.toRadians(7.5), (float)Math.toRadians(-6.66F));
      this.animator.rotate(this.upper_body, (float)Math.toRadians(-5.0), (float)Math.toRadians(10.0), (float)Math.toRadians(-2.5));
      this.animator.rotate(this.right_arm, (float)Math.toRadians(-17.5), (float)Math.toRadians(5.0), (float)Math.toRadians(20.0));
      this.animator.rotate(this.right_hand, (float)Math.toRadians(22.5), 0.0F, 0.0F);
      this.animator.rotate(this.left_arm, (float)Math.toRadians(-15.0), (float)Math.toRadians(-5.0), (float)Math.toRadians(-20.0));
      this.animator.rotate(this.left_hand, (float)Math.toRadians(22.5), 0.0F, 0.0F);
      this.animator.rotate(this.neck, (float)Math.toRadians(27.5), (float)Math.toRadians(3.5), (float)Math.toRadians(10.0));
      this.animator.rotate(this.head, (float)Math.toRadians(-2.5), (float)Math.toRadians(50.0), (float)Math.toRadians(-5.0));
      this.animator.rotate(this.tail, (float)Math.toRadians(-20.0), (float)Math.toRadians(15.0), 0.0F);
      this.animator.rotate(this.tail2, 0.0F, (float)Math.toRadians(10.0), 0.0F);
      this.animator.rotate(this.tail3, 0.0F, (float)Math.toRadians(7.5), 0.0F);
      this.animator.rotate(this.right_leg, (float)Math.toRadians(5.0), 0.0F, 0.0F);
      this.animator.move(this.right_leg, 0.0F, 0.0F, 2.0F);
      this.animator.rotate(this.left_leg, 0.0F, (float)Math.toRadians(-22.5), 0.0F);
      this.animator.move(this.left_leg, 0.0F, 0.0F, -1.5F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(5);
   }

   public void setupAnim(Modern_Remnant_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float walkSpeed = 0.8F;
      float walkDegree = 0.85F;
      float idleSpeed = 0.1F;
      float idleDegree = 0.4F;
      float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      AdvancedModelBox[] tailBoxes = new AdvancedModelBox[]{this.tail, this.tail2, this.tail3};
      this.walk(this.root, walkSpeed * 2.0F, walkDegree * 0.05F, false, -2.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.root, -walkSpeed, walkDegree * -4.0F, true, limbSwing, limbSwingAmount);
      this.chainSwing(tailBoxes, idleSpeed, idleDegree * 0.3F, -2.0, ageInTicks, 1.0F);
      this.walk(this.left_leg, walkSpeed, walkDegree * 1.0F, true, 0.0F, 0.2F, limbSwing, limbSwingAmount);
      this.walk(this.left_leg2, walkSpeed, walkDegree * 0.5F, true, -1.0F, -0.2F, limbSwing, limbSwingAmount);
      this.swing(this.left_leg2, walkSpeed, walkDegree * -0.5F, true, -1.0F, 0.0F, limbSwing, limbSwingAmount);
      this.walk(this.right_leg, walkSpeed, walkDegree * 1.0F, false, 0.0F, 0.2F, limbSwing, limbSwingAmount);
      this.walk(this.right_leg2, walkSpeed, walkDegree * 0.5F, false, -1.0F, -0.2F, limbSwing, limbSwingAmount);
      this.swing(this.right_leg2, walkSpeed, walkDegree * -0.5F, false, -1.0F, 0.0F, limbSwing, limbSwingAmount);
      this.walk(this.neck, walkSpeed * 2.0F, walkDegree * 0.3F, false, -1.0F, -0.5F, limbSwing, limbSwingAmount);
      this.walk(this.head, walkSpeed * 2.0F, walkDegree * 0.3F, true, -1.0F, -0.5F, limbSwing, limbSwingAmount);
      this.walk(this.left_arm, walkSpeed * 2.0F, walkDegree * 0.3F, false, 1.0F, -0.1F, limbSwing, limbSwingAmount);
      this.walk(this.right_arm, walkSpeed * 2.0F, walkDegree * 0.3F, false, 1.0F, -0.1F, limbSwing, limbSwingAmount);
      this.walk(this.right_arm, 0.1F, 0.1F, false, 3.0F, 0.1F, ageInTicks, 1.0F);
      this.walk(this.left_arm, 0.1F, 0.1F, false, 3.0F, 0.1F, ageInTicks, 1.0F);
      this.swing(this.right_hand, 0.1F, 0.1F, true, 3.0F, 0.1F, ageInTicks, 1.0F);
      this.swing(this.left_hand, 0.1F, 0.1F, false, 3.0F, 0.1F, ageInTicks, 1.0F);
      this.walk(this.neck, 0.1F, 0.05F, false, 4.0F, 0.1F, ageInTicks, 1.0F);
      this.walk(this.head, 0.1F, 0.05F, true, 4.0F, 0.1F, ageInTicks, 1.0F);
      this.walk(this.jaw, 0.1F * idleSpeed, idleDegree * 0.25F, true, 0.2F, -idleDegree * 0.25F, ageInTicks, 1.0F);
      this.bob(this.body, 0.4F * idleSpeed, idleDegree * 2.0F, false, ageInTicks, 1.0F);
      float sitProgress = entityIn.prevSitProgress + (entityIn.sitProgress - entityIn.prevSitProgress) * partialTick;
      this.faceTarget(netHeadYaw, headPitch, 1.0F, new AdvancedModelBox[]{this.head, this.neck});
      this.progressPositionPrev(this.root, sitProgress, 0.0F, 5.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.neck, sitProgress, (float)Math.toRadians(30.0), 0.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.head, sitProgress, (float)Math.toRadians(-25.0), 0.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.tail, sitProgress, (float)Math.toRadians(-10.0), 0.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.tail2, sitProgress, (float)Math.toRadians(2.5), 0.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.tail3, sitProgress, (float)Math.toRadians(15.0), 0.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.right_leg, sitProgress, (float)Math.toRadians(-90.0), (float)Math.toRadians(15.0), 0.0F, 10.0F);
      this.progressPositionPrev(this.right_leg, sitProgress, 0.0F, 1.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.left_leg, sitProgress, (float)Math.toRadians(-90.0), (float)Math.toRadians(-15.0), 0.0F, 10.0F);
      this.progressPositionPrev(this.left_leg, sitProgress, 0.0F, 1.0F, 0.0F, 10.0F);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.body,
         this.upper_body,
         this.right_arm,
         this.right_hand,
         this.left_arm,
         this.left_hand,
         this.neck,
         this.bandage,
         this.headjoint,
         this.head,
         this.helmet,
         new AdvancedModelBox[]{this.helmet2, this.jaw, this.tail, this.tail2, this.tail3, this.right_leg, this.right_leg2, this.left_leg, this.left_leg2}
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
