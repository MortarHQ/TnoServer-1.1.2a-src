package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Amethyst_Crab_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;

public class Amethyst_Crab_Model extends AdvancedEntityModel<Amethyst_Crab_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox body;
   private final AdvancedModelBox big_amethyst;
   private final AdvancedModelBox small_amethyst;
   private final AdvancedModelBox small_amethyst2;
   private final AdvancedModelBox amethyst_1;
   private final AdvancedModelBox small_amethyst3;
   private final AdvancedModelBox small_amethyst4;
   private final AdvancedModelBox left_body;
   private final AdvancedModelBox big_amethyst2;
   private final AdvancedModelBox right_body;
   private final AdvancedModelBox stomach;
   private final AdvancedModelBox head;
   private final AdvancedModelBox left_eye;
   private final AdvancedModelBox right_eye;
   private final AdvancedModelBox small_amethyst5;
   private final AdvancedModelBox small_amethyst6;
   private final AdvancedModelBox left_leg;
   private final AdvancedModelBox left_joint;
   private final AdvancedModelBox right_leg;
   private final AdvancedModelBox right_joint;
   private final AdvancedModelBox left_leg2;
   private final AdvancedModelBox left_joint2;
   private final AdvancedModelBox right_leg2;
   private final AdvancedModelBox right_joint2;
   private final AdvancedModelBox left_leg3;
   private final AdvancedModelBox left_joint3;
   private final AdvancedModelBox right_leg3;
   private final AdvancedModelBox right_joint3;
   private final AdvancedModelBox right_leg4;
   private final AdvancedModelBox left_leg4;
   private final AdvancedModelBox right_tong_leg;
   private final AdvancedModelBox right_Tong;
   private final AdvancedModelBox upper_claw2;
   private final AdvancedModelBox under_claw2;
   private final AdvancedModelBox left_tong_leg;
   private final AdvancedModelBox left_Tong;
   private final AdvancedModelBox upper_claw;
   private final AdvancedModelBox under_claw;
   private ModelAnimator animator;

   public Amethyst_Crab_Model() {
      this.texWidth = 256;
      this.texHeight = 256;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(-0.4889F, -19.1765F, 3.4113F);
      this.root.addChild(this.body);
      this.setRotationAngle(this.body, -0.48F, 0.0F, 0.0F);
      this.body.setTextureOffset(0, 0).addBox(-7.5111F, -8.8235F, -18.4113F, 16.0F, 10.0F, 32.0F, 0.0F, false);
      this.body.setTextureOffset(0, 139).addBox(-6.5111F, -22.8235F, 0.5887F, 14.0F, 14.0F, 13.0F, 0.0F, false);
      this.big_amethyst = new AdvancedModelBox(this);
      this.big_amethyst.setRotationPoint(-8.0111F, -19.3235F, 5.0887F);
      this.body.addChild(this.big_amethyst);
      this.setRotationAngle(this.big_amethyst, 0.0F, 0.2182F, 0.0F);
      this.big_amethyst.setTextureOffset(0, 138).addBox(-6.5F, -11.5F, -6.5F, 13.0F, 24.0F, 14.0F, 0.0F, false);
      this.small_amethyst = new AdvancedModelBox(this);
      this.small_amethyst.setRotationPoint(0.0F, -11.5F, 0.5F);
      this.big_amethyst.addChild(this.small_amethyst);
      this.setRotationAngle(this.small_amethyst, 0.0F, 0.7854F, 0.0F);
      this.small_amethyst.setTextureOffset(71, 90).addBox(-5.5F, -10.0F, 0.0F, 11.0F, 10.0F, 0.0F, 0.0F, false);
      this.small_amethyst2 = new AdvancedModelBox(this);
      this.small_amethyst2.setRotationPoint(0.0F, -11.5F, 0.5F);
      this.big_amethyst.addChild(this.small_amethyst2);
      this.setRotationAngle(this.small_amethyst2, 0.0F, -0.7854F, 0.0F);
      this.small_amethyst2.setTextureOffset(132, 60).addBox(-5.5F, -10.0F, 0.0F, 11.0F, 10.0F, 0.0F, 0.0F, false);
      this.amethyst_1 = new AdvancedModelBox(this);
      this.amethyst_1.setRotationPoint(-8.0111F, -10.3235F, 0.5887F);
      this.body.addChild(this.amethyst_1);
      this.setRotationAngle(this.amethyst_1, 0.0F, 0.2182F, 0.0F);
      this.amethyst_1.setTextureOffset(102, 126).addBox(-7.5F, -5.5F, -11.0F, 9.0F, 9.0F, 9.0F, 0.0F, false);
      this.small_amethyst3 = new AdvancedModelBox(this);
      this.small_amethyst3.setRotationPoint(-3.0F, -5.5F, -7.0F);
      this.amethyst_1.addChild(this.small_amethyst3);
      this.setRotationAngle(this.small_amethyst3, 0.0F, -0.7854F, 0.0F);
      this.small_amethyst3.setTextureOffset(144, 29).addBox(-5.5F, -10.0F, 0.0F, 11.0F, 10.0F, 0.0F, 0.0F, false);
      this.small_amethyst4 = new AdvancedModelBox(this);
      this.small_amethyst4.setRotationPoint(-3.0F, -5.5F, -7.0F);
      this.amethyst_1.addChild(this.small_amethyst4);
      this.setRotationAngle(this.small_amethyst4, 0.0F, 0.7854F, 0.0F);
      this.small_amethyst4.setTextureOffset(144, 29).addBox(-5.5F, -10.0F, 0.0F, 11.0F, 10.0F, 0.0F, 0.0F, false);
      this.left_body = new AdvancedModelBox(this);
      this.left_body.setRotationPoint(8.9889F, -0.3235F, 0.0887F);
      this.body.addChild(this.left_body);
      this.setRotationAngle(this.left_body, -0.0289F, -0.2163F, 0.134F);
      this.left_body.setTextureOffset(0, 73).addBox(-5.5F, -3.5F, -16.5F, 21.0F, 0.0F, 28.0F, 0.0F, false);
      this.left_body.setTextureOffset(0, 102).addBox(-4.5F, -7.5F, -15.5F, 13.0F, 8.0F, 27.0F, 0.0F, false);
      this.big_amethyst2 = new AdvancedModelBox(this);
      this.big_amethyst2.setRotationPoint(6.5F, -7.5F, 4.0F);
      this.left_body.addChild(this.big_amethyst2);
      this.setRotationAngle(this.big_amethyst2, 0.0F, 0.0F, -0.1309F);
      this.big_amethyst2.setTextureOffset(101, 60).addBox(-8.0F, -4.0F, -10.5F, 7.0F, 4.0F, 8.0F, 0.0F, false);
      this.big_amethyst2.setTextureOffset(92, 159).addBox(-8.0F, -8.0F, -2.5F, 8.0F, 8.0F, 9.0F, 0.0F, false);
      this.right_body = new AdvancedModelBox(this);
      this.right_body.setRotationPoint(-8.0111F, -0.3235F, 0.0887F);
      this.body.addChild(this.right_body);
      this.setRotationAngle(this.right_body, -0.0289F, 0.2163F, -0.134F);
      this.right_body.setTextureOffset(65, 0).addBox(-16.5F, -3.5F, -16.5F, 21.0F, 0.0F, 28.0F, 0.0F, false);
      this.right_body.setTextureOffset(72, 90).addBox(-8.5F, -7.5F, -15.5F, 13.0F, 8.0F, 27.0F, 0.0F, false);
      this.stomach = new AdvancedModelBox(this);
      this.stomach.setRotationPoint(0.4889F, 2.6765F, 0.0887F);
      this.body.addChild(this.stomach);
      this.setRotationAngle(this.stomach, 0.0873F, 0.0F, 0.0F);
      this.stomach.setTextureOffset(0, 43).addBox(-14.0F, -4.5F, -13.5F, 28.0F, 7.0F, 22.0F, 0.0F, false);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.4889F, 7.1765F, -19.4113F);
      this.body.addChild(this.head);
      this.left_eye = new AdvancedModelBox(this);
      this.left_eye.setRotationPoint(4.5F, -11.5F, 1.0F);
      this.head.addChild(this.left_eye);
      this.setRotationAngle(this.left_eye, 0.5672F, -0.2618F, 0.3491F);
      this.left_eye.setTextureOffset(71, 73).addBox(-0.5F, -9.5F, -2.0F, 3.0F, 9.0F, 2.0F, 0.0F, false);
      this.right_eye = new AdvancedModelBox(this);
      this.right_eye.setRotationPoint(-4.5F, -13.5F, 1.0F);
      this.head.addChild(this.right_eye);
      this.setRotationAngle(this.right_eye, 0.5672F, 0.2618F, -0.3491F);
      this.right_eye.setTextureOffset(14, 73).addBox(-2.5F, -7.5F, -2.0F, 3.0F, 9.0F, 2.0F, 0.0F, false);
      this.small_amethyst5 = new AdvancedModelBox(this);
      this.small_amethyst5.setRotationPoint(2.9889F, -26.8235F, 5.5887F);
      this.body.addChild(this.small_amethyst5);
      this.setRotationAngle(this.small_amethyst5, 0.0F, 0.7854F, 0.0F);
      this.small_amethyst5.setTextureOffset(0, 118).addBox(-5.5F, -6.0F, 0.0F, 11.0F, 10.0F, 0.0F, 0.0F, false);
      this.small_amethyst6 = new AdvancedModelBox(this);
      this.small_amethyst6.setRotationPoint(2.9889F, -21.8235F, 5.5887F);
      this.body.addChild(this.small_amethyst6);
      this.setRotationAngle(this.small_amethyst6, 0.0F, -0.7854F, 0.0F);
      this.small_amethyst6.setTextureOffset(71, 90).addBox(-5.5F, -10.0F, 0.0F, 11.0F, 10.0F, 0.0F, 0.0F, false);
      this.left_tong_leg = new AdvancedModelBox(this);
      this.left_tong_leg.setRotationPoint(14.4889F, 4.1765F, -13.4113F);
      this.body.addChild(this.left_tong_leg);
      this.setRotationAngle(this.left_tong_leg, 0.6545F, -0.7418F, 0.0F);
      this.left_tong_leg.setTextureOffset(136, 0).addBox(-4.0F, -3.0F, -16.0F, 8.0F, 7.0F, 20.0F, 0.0F, false);
      this.left_tong_leg.setTextureOffset(153, 90).addBox(0.0F, -9.0F, -16.0F, 0.0F, 13.0F, 20.0F, 0.0F, false);
      this.left_Tong = new AdvancedModelBox(this);
      this.left_Tong.setRotationPoint(0.0F, 0.5F, -14.5F);
      this.left_tong_leg.addChild(this.left_Tong);
      this.setRotationAngle(this.left_Tong, 0.0F, 1.1781F, 0.0F);
      this.left_Tong.setTextureOffset(55, 159).addBox(-3.0F, -6.5F, -11.0F, 6.0F, 13.0F, 12.0F, 0.0F, false);
      this.upper_claw = new AdvancedModelBox(this);
      this.upper_claw.setRotationPoint(0.0F, 1.0F, -11.0F);
      this.left_Tong.addChild(this.upper_claw);
      this.setRotationAngle(this.upper_claw, -0.4363F, 0.0F, 0.0F);
      this.upper_claw.setTextureOffset(0, 43).addBox(-2.0F, -21.5F, -6.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
      this.upper_claw.setTextureOffset(54, 114).addBox(-3.0F, -14.5F, -5.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
      this.upper_claw.setTextureOffset(0, 73).addBox(0.0F, -17.5F, -7.0F, 3.0F, 6.0F, 3.0F, 0.0F, false);
      this.upper_claw.setTextureOffset(79, 102).addBox(-2.0F, -16.5F, -12.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
      this.upper_claw.setTextureOffset(122, 126).addBox(-5.0F, -11.5F, -20.0F, 10.0F, 12.0F, 20.0F, 0.3F, false);
      this.upper_claw.setTextureOffset(61, 126).addBox(-5.0F, -11.5F, -20.0F, 10.0F, 12.0F, 20.0F, 0.0F, false);
      this.under_claw = new AdvancedModelBox(this);
      this.under_claw.setRotationPoint(0.0F, 1.5F, -11.0F);
      this.left_Tong.addChild(this.under_claw);
      this.setRotationAngle(this.under_claw, 0.5236F, 0.0F, 0.0F);
      this.under_claw.setTextureOffset(158, 60).addBox(-4.0F, 0.0F, -17.0F, 8.0F, 5.0F, 17.0F, 0.0F, false);
      this.right_tong_leg = new AdvancedModelBox(this);
      this.right_tong_leg.setRotationPoint(-10.5111F, 6.1765F, -11.4113F);
      this.body.addChild(this.right_tong_leg);
      this.setRotationAngle(this.right_tong_leg, 0.3927F, 0.5236F, -0.0873F);
      this.right_tong_leg.setTextureOffset(126, 90).addBox(-3.8F, -2.5F, -13.0F, 6.0F, 4.0F, 13.0F, 0.0F, false);
      this.right_tong_leg.setTextureOffset(0, 73).addBox(-0.8F, -6.5F, -13.0F, 0.0F, 8.0F, 13.0F, 0.0F, false);
      this.right_Tong = new AdvancedModelBox(this);
      this.right_Tong.setRotationPoint(-1.0F, 0.0F, -13.0F);
      this.right_tong_leg.addChild(this.right_Tong);
      this.setRotationAngle(this.right_Tong, 0.0F, -1.1781F, 0.0F);
      this.right_Tong.setTextureOffset(0, 102).addBox(-1.8F, -3.5F, -4.0F, 4.0F, 6.0F, 9.0F, 0.0F, false);
      this.upper_claw2 = new AdvancedModelBox(this);
      this.upper_claw2.setRotationPoint(0.1F, 1.0F, -4.0F);
      this.right_Tong.addChild(this.upper_claw2);
      this.setRotationAngle(this.upper_claw2, -0.4363F, 0.0F, 0.0F);
      this.upper_claw2.setTextureOffset(127, 159).addBox(-2.9F, -6.5F, -10.0F, 6.0F, 7.0F, 10.0F, 0.0F, false);
      this.under_claw2 = new AdvancedModelBox(this);
      this.under_claw2.setRotationPoint(0.2F, 1.5F, -4.0F);
      this.right_Tong.addChild(this.under_claw2);
      this.setRotationAngle(this.under_claw2, 0.5672F, 0.0F, 0.0F);
      this.under_claw2.setTextureOffset(54, 102).addBox(-2.0F, 0.0F, -8.0F, 4.0F, 3.0F, 8.0F, 0.0F, false);
      this.left_leg = new AdvancedModelBox(this);
      this.left_leg.setRotationPoint(12.0F, -17.0F, -2.5F);
      this.root.addChild(this.left_leg);
      this.setRotationAngle(this.left_leg, -0.1309F, 0.6109F, -0.0873F);
      this.left_leg.setTextureOffset(97, 29).addBox(0.0F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, 0.0F, false);
      this.left_joint = new AdvancedModelBox(this);
      this.left_joint.setRotationPoint(18.3F, -0.1F, 0.0F);
      this.left_leg.addChild(this.left_joint);
      this.setRotationAngle(this.left_joint, -0.1309F, 0.0F, 0.0F);
      this.left_joint.setTextureOffset(187, 28).addBox(-0.3F, -8.4F, 0.0F, 12.0F, 27.0F, 0.0F, 0.0F, false);
      this.left_joint.setTextureOffset(65, 0).addBox(-0.3F, -2.4F, -3.0F, 6.0F, 21.0F, 6.0F, 0.0F, false);
      this.right_leg = new AdvancedModelBox(this);
      this.right_leg.setRotationPoint(-12.0F, -17.0F, -2.5F);
      this.root.addChild(this.right_leg);
      this.setRotationAngle(this.right_leg, -0.1309F, -0.6109F, 0.0873F);
      this.right_leg.setTextureOffset(97, 29).addBox(-19.0F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, 0.0F, true);
      this.right_joint = new AdvancedModelBox(this);
      this.right_joint.setRotationPoint(-18.3F, -0.1F, 0.0F);
      this.right_leg.addChild(this.right_joint);
      this.setRotationAngle(this.right_joint, -0.1309F, 0.0F, 0.0F);
      this.right_joint.setTextureOffset(0, 0).addBox(-11.7F, -8.4F, 0.0F, 12.0F, 27.0F, 0.0F, 0.0F, true);
      this.right_joint.setTextureOffset(65, 0).addBox(-5.7F, -2.4F, -3.0F, 6.0F, 21.0F, 6.0F, 0.0F, true);
      this.left_leg2 = new AdvancedModelBox(this);
      this.left_leg2.setRotationPoint(12.0F, -16.0F, 2.5F);
      this.root.addChild(this.left_leg2);
      this.setRotationAngle(this.left_leg2, 0.0F, 0.0F, -0.0873F);
      this.left_leg2.setTextureOffset(97, 29).addBox(0.0F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, 0.0F, false);
      this.left_joint2 = new AdvancedModelBox(this);
      this.left_joint2.setRotationPoint(14.3F, 0.9F, 0.0F);
      this.left_leg2.addChild(this.left_joint2);
      this.setRotationAngle(this.left_joint2, -0.1309F, -0.0873F, -0.4363F);
      this.left_joint2.setTextureOffset(0, 0).addBox(-0.3F, -8.4F, 0.0F, 12.0F, 27.0F, 0.0F, 0.0F, false);
      this.left_joint2.setTextureOffset(66, 0).addBox(-0.3F, -2.4F, -3.0F, 6.0F, 21.0F, 6.0F, 0.0F, false);
      this.right_leg2 = new AdvancedModelBox(this);
      this.right_leg2.setRotationPoint(-12.0F, -16.0F, 2.5F);
      this.root.addChild(this.right_leg2);
      this.setRotationAngle(this.right_leg2, 0.0F, 0.0F, 0.0873F);
      this.right_leg2.setTextureOffset(97, 29).addBox(-19.0F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, 0.0F, true);
      this.right_joint2 = new AdvancedModelBox(this);
      this.right_joint2.setRotationPoint(-14.3F, 0.9F, 0.0F);
      this.right_leg2.addChild(this.right_joint2);
      this.setRotationAngle(this.right_joint2, -0.1309F, 0.0873F, 0.4363F);
      this.right_joint2.setTextureOffset(187, 28).addBox(-11.7F, -8.4F, 0.0F, 12.0F, 27.0F, 0.0F, 0.0F, true);
      this.right_joint2.setTextureOffset(65, 0).addBox(-5.7F, -2.4F, -3.0F, 6.0F, 21.0F, 6.0F, 0.0F, true);
      this.left_leg3 = new AdvancedModelBox(this);
      this.left_leg3.setRotationPoint(12.0F, -13.0F, 9.5F);
      this.root.addChild(this.left_leg3);
      this.setRotationAngle(this.left_leg3, 0.2618F, -0.3927F, -0.2182F);
      this.left_leg3.setTextureOffset(97, 29).addBox(0.0F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, 0.0F, false);
      this.left_joint3 = new AdvancedModelBox(this);
      this.left_joint3.setRotationPoint(14.3F, 0.9F, 0.0F);
      this.left_leg3.addChild(this.left_joint3);
      this.setRotationAngle(this.left_joint3, -0.1309F, -0.0873F, -0.3927F);
      this.left_joint3.setTextureOffset(187, 28).addBox(-0.3F, -8.4F, 0.0F, 12.0F, 27.0F, 0.0F, 0.0F, false);
      this.left_joint3.setTextureOffset(65, 0).addBox(-0.3F, -2.4F, -3.0F, 6.0F, 21.0F, 6.0F, 0.0F, false);
      this.right_leg3 = new AdvancedModelBox(this);
      this.right_leg3.setRotationPoint(-12.0F, -13.0F, 9.5F);
      this.root.addChild(this.right_leg3);
      this.setRotationAngle(this.right_leg3, 0.2618F, 0.3927F, 0.2182F);
      this.right_leg3.setTextureOffset(97, 29).addBox(-19.0F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, 0.0F, true);
      this.right_joint3 = new AdvancedModelBox(this);
      this.right_joint3.setRotationPoint(-14.3F, 0.9F, 0.0F);
      this.right_leg3.addChild(this.right_joint3);
      this.setRotationAngle(this.right_joint3, -0.1309F, 0.0873F, 0.3927F);
      this.right_joint3.setTextureOffset(0, 0).addBox(-11.7F, -8.4F, 0.0F, 12.0F, 27.0F, 0.0F, 0.0F, true);
      this.right_joint3.setTextureOffset(65, 0).addBox(-5.7F, -2.4F, -3.0F, 6.0F, 21.0F, 6.0F, 0.0F, true);
      this.right_leg4 = new AdvancedModelBox(this);
      this.right_leg4.setRotationPoint(-10.5F, -15.0F, 14.5F);
      this.root.addChild(this.right_leg4);
      this.setRotationAngle(this.right_leg4, -0.576F, 0.6981F, -0.6109F);
      this.right_leg4.setTextureOffset(79, 43).addBox(-31.5F, 1.0F, -7.5F, 35.0F, 0.0F, 16.0F, 0.0F, false);
      this.left_leg4 = new AdvancedModelBox(this);
      this.left_leg4.setRotationPoint(10.5F, -15.0F, 14.5F);
      this.root.addChild(this.left_leg4);
      this.setRotationAngle(this.left_leg4, -0.6109F, -0.7418F, 0.6109F);
      this.left_leg4.setTextureOffset(71, 73).addBox(-3.5F, 1.0F, -7.5F, 35.0F, 0.0F, 16.0F, 0.0F, false);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.body,
         this.big_amethyst,
         this.small_amethyst,
         this.small_amethyst2,
         this.amethyst_1,
         this.small_amethyst3,
         this.small_amethyst4,
         this.left_body,
         this.big_amethyst2,
         this.right_body,
         this.stomach,
         new AdvancedModelBox[]{
            this.head,
            this.left_eye,
            this.right_eye,
            this.small_amethyst5,
            this.small_amethyst6,
            this.left_leg,
            this.left_joint,
            this.right_leg,
            this.left_joint2,
            this.left_leg2,
            this.right_joint,
            this.left_leg2,
            this.left_joint2,
            this.right_leg2,
            this.right_joint2,
            this.left_leg3,
            this.left_joint3,
            this.right_leg3,
            this.right_joint3,
            this.right_leg4,
            this.left_leg4,
            this.right_tong_leg,
            this.right_Tong,
            this.upper_claw2,
            this.under_claw2,
            this.left_tong_leg,
            this.left_Tong,
            this.upper_claw,
            this.under_claw
         }
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void animate(Amethyst_Crab_Entity param1, float param2, float param3, float param4, float param5, float param6) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: aload 0
      // 0001: invokevirtual com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.resetToDefaultPose ()V
      // 0004: aload 0
      // 0005: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0008: aload 1
      // 0009: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.update (Lcom/github/L_Ender/lionfishapi/server/animation/IAnimatedEntity;)V
      // 000c: aload 0
      // 000d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0010: getstatic com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Amethyst_Crab_Entity.CRAB_SMASH Lcom/github/L_Ender/lionfishapi/server/animation/Animation;
      // 0013: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setAnimation (Lcom/github/L_Ender/lionfishapi/server/animation/Animation;)Z
      // 0016: pop
      // 0017: aload 0
      // 0018: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 001b: bipush 12
      // 001d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 0020: aload 0
      // 0021: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0024: aload 0
      // 0025: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0028: ldc2_w 5.0
      // 002b: invokestatic java/lang/Math.toRadians (D)D
      // 002e: d2f
      // 002f: fconst_0
      // 0030: ldc2_w -5.0
      // 0033: invokestatic java/lang/Math.toRadians (D)D
      // 0036: d2f
      // 0037: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 003a: aload 0
      // 003b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 003e: aload 0
      // 003f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0042: fconst_0
      // 0043: fconst_0
      // 0044: ldc2_w -5.0
      // 0047: invokestatic java/lang/Math.toRadians (D)D
      // 004a: d2f
      // 004b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 004e: aload 0
      // 004f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0052: aload 0
      // 0053: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0056: fconst_0
      // 0057: fconst_0
      // 0058: ldc2_w 5.0
      // 005b: invokestatic java/lang/Math.toRadians (D)D
      // 005e: d2f
      // 005f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0062: aload 0
      // 0063: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0066: aload 0
      // 0067: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 006a: fconst_0
      // 006b: fconst_0
      // 006c: ldc2_w 2.5
      // 006f: invokestatic java/lang/Math.toRadians (D)D
      // 0072: d2f
      // 0073: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0076: aload 0
      // 0077: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 007a: aload 0
      // 007b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 007e: fconst_0
      // 007f: fconst_0
      // 0080: ldc2_w -7.5
      // 0083: invokestatic java/lang/Math.toRadians (D)D
      // 0086: d2f
      // 0087: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 008a: aload 0
      // 008b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 008e: aload 0
      // 008f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0092: fconst_0
      // 0093: ldc2_w 7.5
      // 0096: invokestatic java/lang/Math.toRadians (D)D
      // 0099: d2f
      // 009a: fconst_0
      // 009b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 009e: aload 0
      // 009f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 00a2: aload 0
      // 00a3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 00a6: fconst_0
      // 00a7: ldc2_w -5.0
      // 00aa: invokestatic java/lang/Math.toRadians (D)D
      // 00ad: d2f
      // 00ae: fconst_0
      // 00af: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 00b2: aload 0
      // 00b3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 00b6: aload 0
      // 00b7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 00ba: fconst_0
      // 00bb: fconst_0
      // 00bc: ldc2_w 10.0
      // 00bf: invokestatic java/lang/Math.toRadians (D)D
      // 00c2: d2f
      // 00c3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 00c6: aload 0
      // 00c7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 00ca: aload 0
      // 00cb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 00ce: fconst_0
      // 00cf: fconst_0
      // 00d0: ldc2_w -7.5
      // 00d3: invokestatic java/lang/Math.toRadians (D)D
      // 00d6: d2f
      // 00d7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 00da: aload 0
      // 00db: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 00de: aload 0
      // 00df: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 00e2: fconst_0
      // 00e3: fconst_0
      // 00e4: ldc2_w -2.5
      // 00e7: invokestatic java/lang/Math.toRadians (D)D
      // 00ea: d2f
      // 00eb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 00ee: aload 0
      // 00ef: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 00f2: aload 0
      // 00f3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 00f6: fconst_0
      // 00f7: fconst_0
      // 00f8: ldc2_w 5.0
      // 00fb: invokestatic java/lang/Math.toRadians (D)D
      // 00fe: d2f
      // 00ff: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0102: aload 0
      // 0103: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0106: aload 0
      // 0107: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 010a: fconst_0
      // 010b: fconst_0
      // 010c: ldc2_w -10.0
      // 010f: invokestatic java/lang/Math.toRadians (D)D
      // 0112: d2f
      // 0113: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0116: aload 0
      // 0117: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 011a: aload 0
      // 011b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 011e: ldc2_w -15.0
      // 0121: invokestatic java/lang/Math.toRadians (D)D
      // 0124: d2f
      // 0125: ldc2_w 10.0
      // 0128: invokestatic java/lang/Math.toRadians (D)D
      // 012b: d2f
      // 012c: ldc2_w -12.5
      // 012f: invokestatic java/lang/Math.toRadians (D)D
      // 0132: d2f
      // 0133: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0136: aload 0
      // 0137: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 013a: aload 0
      // 013b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 013e: fconst_0
      // 013f: ldc2_w 5.0
      // 0142: invokestatic java/lang/Math.toRadians (D)D
      // 0145: d2f
      // 0146: fconst_0
      // 0147: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 014a: aload 0
      // 014b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 014e: aload 0
      // 014f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0152: fconst_0
      // 0153: ldc2_w 5.0
      // 0156: invokestatic java/lang/Math.toRadians (D)D
      // 0159: d2f
      // 015a: fconst_0
      // 015b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 015e: aload 0
      // 015f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0162: aload 0
      // 0163: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0166: ldc2_w 32.5
      // 0169: invokestatic java/lang/Math.toRadians (D)D
      // 016c: d2f
      // 016d: fconst_0
      // 016e: fconst_0
      // 016f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0172: aload 0
      // 0173: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0176: aload 0
      // 0177: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 017a: ldc2_w -27.5
      // 017d: invokestatic java/lang/Math.toRadians (D)D
      // 0180: d2f
      // 0181: fconst_0
      // 0182: fconst_0
      // 0183: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0186: aload 0
      // 0187: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 018a: aload 0
      // 018b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 018e: ldc2_w -60.0
      // 0191: invokestatic java/lang/Math.toRadians (D)D
      // 0194: d2f
      // 0195: ldc2_w 22.5
      // 0198: invokestatic java/lang/Math.toRadians (D)D
      // 019b: d2f
      // 019c: ldc2_w 20.0
      // 019f: invokestatic java/lang/Math.toRadians (D)D
      // 01a2: d2f
      // 01a3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 01a6: aload 0
      // 01a7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 01aa: aload 0
      // 01ab: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 01ae: fconst_0
      // 01af: ldc -4.0
      // 01b1: fconst_0
      // 01b2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 01b5: aload 0
      // 01b6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 01b9: aload 0
      // 01ba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 01bd: ldc2_w -67.5
      // 01c0: invokestatic java/lang/Math.toRadians (D)D
      // 01c3: d2f
      // 01c4: ldc2_w -32.5
      // 01c7: invokestatic java/lang/Math.toRadians (D)D
      // 01ca: d2f
      // 01cb: ldc2_w -25.0
      // 01ce: invokestatic java/lang/Math.toRadians (D)D
      // 01d1: d2f
      // 01d2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 01d5: aload 0
      // 01d6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 01d9: aload 0
      // 01da: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 01dd: ldc2_w 16.5
      // 01e0: invokestatic java/lang/Math.toRadians (D)D
      // 01e3: d2f
      // 01e4: fconst_0
      // 01e5: fconst_0
      // 01e6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 01e9: aload 0
      // 01ea: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 01ed: aload 0
      // 01ee: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 01f1: ldc2_w -57.5
      // 01f4: invokestatic java/lang/Math.toRadians (D)D
      // 01f7: d2f
      // 01f8: fconst_0
      // 01f9: fconst_0
      // 01fa: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 01fd: aload 0
      // 01fe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0201: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 0204: aload 0
      // 0205: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0208: bipush 8
      // 020a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 020d: aload 0
      // 020e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0211: bipush 3
      // 0212: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 0215: aload 0
      // 0216: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0219: aload 0
      // 021a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 021d: fconst_0
      // 021e: fconst_2
      // 021f: fconst_0
      // 0220: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0223: aload 0
      // 0224: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0227: aload 0
      // 0228: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 022b: ldc2_w 40.0
      // 022e: invokestatic java/lang/Math.toRadians (D)D
      // 0231: d2f
      // 0232: ldc2_w 20.0
      // 0235: invokestatic java/lang/Math.toRadians (D)D
      // 0238: d2f
      // 0239: fconst_0
      // 023a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 023d: aload 0
      // 023e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0241: aload 0
      // 0242: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0245: fconst_0
      // 0246: fconst_0
      // 0247: ldc2_w -5.0
      // 024a: invokestatic java/lang/Math.toRadians (D)D
      // 024d: d2f
      // 024e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0251: aload 0
      // 0252: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0255: aload 0
      // 0256: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0259: fconst_0
      // 025a: fconst_0
      // 025b: ldc2_w 5.0
      // 025e: invokestatic java/lang/Math.toRadians (D)D
      // 0261: d2f
      // 0262: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0265: aload 0
      // 0266: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0269: aload 0
      // 026a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 026d: fconst_0
      // 026e: fconst_0
      // 026f: ldc2_w 10.0
      // 0272: invokestatic java/lang/Math.toRadians (D)D
      // 0275: d2f
      // 0276: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0279: aload 0
      // 027a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 027d: aload 0
      // 027e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0281: fconst_0
      // 0282: fconst_0
      // 0283: ldc2_w -17.5
      // 0286: invokestatic java/lang/Math.toRadians (D)D
      // 0289: d2f
      // 028a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 028d: aload 0
      // 028e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0291: aload 0
      // 0292: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0295: ldc2_w -12.5
      // 0298: invokestatic java/lang/Math.toRadians (D)D
      // 029b: d2f
      // 029c: fconst_0
      // 029d: ldc2_w 17.5
      // 02a0: invokestatic java/lang/Math.toRadians (D)D
      // 02a3: d2f
      // 02a4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 02a7: aload 0
      // 02a8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 02ab: aload 0
      // 02ac: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 02af: fconst_0
      // 02b0: ldc -6.0
      // 02b2: fconst_0
      // 02b3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 02b6: aload 0
      // 02b7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 02ba: aload 0
      // 02bb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 02be: ldc2_w -20.0
      // 02c1: invokestatic java/lang/Math.toRadians (D)D
      // 02c4: d2f
      // 02c5: ldc2_w -5.0
      // 02c8: invokestatic java/lang/Math.toRadians (D)D
      // 02cb: d2f
      // 02cc: ldc2_w -30.0
      // 02cf: invokestatic java/lang/Math.toRadians (D)D
      // 02d2: d2f
      // 02d3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 02d6: aload 0
      // 02d7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 02da: aload 0
      // 02db: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 02de: fconst_0
      // 02df: fconst_0
      // 02e0: ldc2_w 57.5
      // 02e3: invokestatic java/lang/Math.toRadians (D)D
      // 02e6: d2f
      // 02e7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 02ea: aload 0
      // 02eb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 02ee: aload 0
      // 02ef: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 02f2: fconst_0
      // 02f3: fconst_0
      // 02f4: ldc2_w -7.5
      // 02f7: invokestatic java/lang/Math.toRadians (D)D
      // 02fa: d2f
      // 02fb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 02fe: aload 0
      // 02ff: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0302: aload 0
      // 0303: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0306: fconst_0
      // 0307: fconst_0
      // 0308: ldc2_w -20.0
      // 030b: invokestatic java/lang/Math.toRadians (D)D
      // 030e: d2f
      // 030f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0312: aload 0
      // 0313: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0316: aload 0
      // 0317: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 031a: fconst_0
      // 031b: fconst_0
      // 031c: ldc2_w 5.0
      // 031f: invokestatic java/lang/Math.toRadians (D)D
      // 0322: d2f
      // 0323: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0326: aload 0
      // 0327: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 032a: aload 0
      // 032b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 032e: fconst_0
      // 032f: fconst_0
      // 0330: ldc2_w -7.5
      // 0333: invokestatic java/lang/Math.toRadians (D)D
      // 0336: d2f
      // 0337: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 033a: aload 0
      // 033b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 033e: aload 0
      // 033f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0342: ldc2_w -25.0
      // 0345: invokestatic java/lang/Math.toRadians (D)D
      // 0348: d2f
      // 0349: fconst_0
      // 034a: ldc2_w -22.5
      // 034d: invokestatic java/lang/Math.toRadians (D)D
      // 0350: d2f
      // 0351: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0354: aload 0
      // 0355: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0358: aload 0
      // 0359: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 035c: ldc -3.0
      // 035e: ldc -7.0
      // 0360: fconst_0
      // 0361: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0364: aload 0
      // 0365: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0368: aload 0
      // 0369: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 036c: ldc2_w -15.0
      // 036f: invokestatic java/lang/Math.toRadians (D)D
      // 0372: d2f
      // 0373: ldc2_w -20.0
      // 0376: invokestatic java/lang/Math.toRadians (D)D
      // 0379: d2f
      // 037a: fconst_0
      // 037b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 037e: aload 0
      // 037f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0382: aload 0
      // 0383: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0386: fconst_0
      // 0387: ldc2_w 30.0
      // 038a: invokestatic java/lang/Math.toRadians (D)D
      // 038d: d2f
      // 038e: fconst_0
      // 038f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0392: aload 0
      // 0393: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0396: aload 0
      // 0397: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 039a: ldc2_w 32.5
      // 039d: invokestatic java/lang/Math.toRadians (D)D
      // 03a0: d2f
      // 03a1: fconst_0
      // 03a2: fconst_0
      // 03a3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 03a6: aload 0
      // 03a7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 03aa: aload 0
      // 03ab: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 03ae: ldc2_w -27.5
      // 03b1: invokestatic java/lang/Math.toRadians (D)D
      // 03b4: d2f
      // 03b5: fconst_0
      // 03b6: fconst_0
      // 03b7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 03ba: aload 0
      // 03bb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 03be: aload 0
      // 03bf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 03c2: ldc2_w -47.5
      // 03c5: invokestatic java/lang/Math.toRadians (D)D
      // 03c8: d2f
      // 03c9: ldc2_w 10.0
      // 03cc: invokestatic java/lang/Math.toRadians (D)D
      // 03cf: d2f
      // 03d0: ldc2_w 10.0
      // 03d3: invokestatic java/lang/Math.toRadians (D)D
      // 03d6: d2f
      // 03d7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 03da: aload 0
      // 03db: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 03de: aload 0
      // 03df: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 03e2: fconst_0
      // 03e3: ldc -4.0
      // 03e5: fconst_0
      // 03e6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 03e9: aload 0
      // 03ea: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 03ed: aload 0
      // 03ee: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 03f1: ldc2_w 80.0
      // 03f4: invokestatic java/lang/Math.toRadians (D)D
      // 03f7: d2f
      // 03f8: ldc2_w -57.5
      // 03fb: invokestatic java/lang/Math.toRadians (D)D
      // 03fe: d2f
      // 03ff: ldc2_w 67.5
      // 0402: invokestatic java/lang/Math.toRadians (D)D
      // 0405: d2f
      // 0406: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0409: aload 0
      // 040a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 040d: aload 0
      // 040e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0411: ldc2_w 25.0
      // 0414: invokestatic java/lang/Math.toRadians (D)D
      // 0417: d2f
      // 0418: fconst_0
      // 0419: fconst_0
      // 041a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 041d: aload 0
      // 041e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0421: aload 0
      // 0422: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0425: ldc2_w -32.5
      // 0428: invokestatic java/lang/Math.toRadians (D)D
      // 042b: d2f
      // 042c: fconst_0
      // 042d: fconst_0
      // 042e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0431: aload 0
      // 0432: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0435: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 0438: aload 0
      // 0439: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 043c: bipush 20
      // 043e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 0441: aload 0
      // 0442: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0445: bipush 10
      // 0447: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.resetKeyframe (I)V
      // 044a: aload 0
      // 044b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 044e: getstatic com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Amethyst_Crab_Entity.CRAB_SMASH_THREE Lcom/github/L_Ender/lionfishapi/server/animation/Animation;
      // 0451: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setAnimation (Lcom/github/L_Ender/lionfishapi/server/animation/Animation;)Z
      // 0454: pop
      // 0455: aload 0
      // 0456: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0459: bipush 10
      // 045b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 045e: aload 0
      // 045f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0462: aload 0
      // 0463: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0466: ldc2_w -27.5
      // 0469: invokestatic java/lang/Math.toRadians (D)D
      // 046c: d2f
      // 046d: fconst_0
      // 046e: fconst_0
      // 046f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0472: aload 0
      // 0473: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0476: aload 0
      // 0477: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 047a: fconst_0
      // 047b: fconst_0
      // 047c: ldc2_w -10.0
      // 047f: invokestatic java/lang/Math.toRadians (D)D
      // 0482: d2f
      // 0483: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0486: aload 0
      // 0487: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 048a: aload 0
      // 048b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 048e: fconst_0
      // 048f: fconst_0
      // 0490: ldc2_w 10.0
      // 0493: invokestatic java/lang/Math.toRadians (D)D
      // 0496: d2f
      // 0497: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 049a: aload 0
      // 049b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 049e: aload 0
      // 049f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 04a2: fconst_0
      // 04a3: fconst_0
      // 04a4: ldc2_w -15.0
      // 04a7: invokestatic java/lang/Math.toRadians (D)D
      // 04aa: d2f
      // 04ab: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 04ae: aload 0
      // 04af: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 04b2: aload 0
      // 04b3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 04b6: ldc2_w 12.5
      // 04b9: invokestatic java/lang/Math.toRadians (D)D
      // 04bc: d2f
      // 04bd: fconst_0
      // 04be: ldc2_w -12.5
      // 04c1: invokestatic java/lang/Math.toRadians (D)D
      // 04c4: d2f
      // 04c5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 04c8: aload 0
      // 04c9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 04cc: aload 0
      // 04cd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 04d0: fconst_0
      // 04d1: fconst_0
      // 04d2: ldc2_w 2.5
      // 04d5: invokestatic java/lang/Math.toRadians (D)D
      // 04d8: d2f
      // 04d9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 04dc: aload 0
      // 04dd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 04e0: aload 0
      // 04e1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 04e4: fconst_0
      // 04e5: fconst_0
      // 04e6: ldc2_w 17.5
      // 04e9: invokestatic java/lang/Math.toRadians (D)D
      // 04ec: d2f
      // 04ed: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 04f0: aload 0
      // 04f1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 04f4: aload 0
      // 04f5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 04f8: fconst_0
      // 04f9: fconst_0
      // 04fa: ldc2_w 5.0
      // 04fd: invokestatic java/lang/Math.toRadians (D)D
      // 0500: d2f
      // 0501: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0504: aload 0
      // 0505: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0508: aload 0
      // 0509: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 050c: fconst_0
      // 050d: fconst_0
      // 050e: ldc2_w 2.5
      // 0511: invokestatic java/lang/Math.toRadians (D)D
      // 0514: d2f
      // 0515: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0518: aload 0
      // 0519: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 051c: aload 0
      // 051d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0520: fconst_0
      // 0521: fconst_0
      // 0522: ldc2_w 5.0
      // 0525: invokestatic java/lang/Math.toRadians (D)D
      // 0528: d2f
      // 0529: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 052c: aload 0
      // 052d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0530: aload 0
      // 0531: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0534: fconst_0
      // 0535: fconst_0
      // 0536: ldc2_w -12.5
      // 0539: invokestatic java/lang/Math.toRadians (D)D
      // 053c: d2f
      // 053d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0540: aload 0
      // 0541: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0544: aload 0
      // 0545: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0548: fconst_0
      // 0549: fconst_0
      // 054a: ldc2_w -10.0
      // 054d: invokestatic java/lang/Math.toRadians (D)D
      // 0550: d2f
      // 0551: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0554: aload 0
      // 0555: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0558: aload 0
      // 0559: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 055c: fconst_0
      // 055d: fconst_0
      // 055e: ldc2_w 22.5
      // 0561: invokestatic java/lang/Math.toRadians (D)D
      // 0564: d2f
      // 0565: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0568: aload 0
      // 0569: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 056c: aload 0
      // 056d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0570: fconst_0
      // 0571: fconst_0
      // 0572: ldc2_w 7.5
      // 0575: invokestatic java/lang/Math.toRadians (D)D
      // 0578: d2f
      // 0579: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 057c: aload 0
      // 057d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0580: aload 0
      // 0581: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0584: fconst_0
      // 0585: fconst_0
      // 0586: ldc2_w -22.5
      // 0589: invokestatic java/lang/Math.toRadians (D)D
      // 058c: d2f
      // 058d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0590: aload 0
      // 0591: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0594: aload 0
      // 0595: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0598: ldc2_w 10.0
      // 059b: invokestatic java/lang/Math.toRadians (D)D
      // 059e: d2f
      // 059f: ldc2_w -2.5
      // 05a2: invokestatic java/lang/Math.toRadians (D)D
      // 05a5: d2f
      // 05a6: ldc2_w -7.5
      // 05a9: invokestatic java/lang/Math.toRadians (D)D
      // 05ac: d2f
      // 05ad: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 05b0: aload 0
      // 05b1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 05b4: aload 0
      // 05b5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 05b8: ldc2_w 20.0
      // 05bb: invokestatic java/lang/Math.toRadians (D)D
      // 05be: d2f
      // 05bf: ldc2_w 15.0
      // 05c2: invokestatic java/lang/Math.toRadians (D)D
      // 05c5: d2f
      // 05c6: ldc2_w 12.5
      // 05c9: invokestatic java/lang/Math.toRadians (D)D
      // 05cc: d2f
      // 05cd: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 05d0: aload 0
      // 05d1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 05d4: aload 0
      // 05d5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 05d8: fconst_0
      // 05d9: ldc 6.0
      // 05db: fconst_0
      // 05dc: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 05df: aload 0
      // 05e0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 05e3: aload 0
      // 05e4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 05e7: ldc2_w -32.5
      // 05ea: invokestatic java/lang/Math.toRadians (D)D
      // 05ed: d2f
      // 05ee: ldc2_w -25.0
      // 05f1: invokestatic java/lang/Math.toRadians (D)D
      // 05f4: d2f
      // 05f5: ldc2_w -30.0
      // 05f8: invokestatic java/lang/Math.toRadians (D)D
      // 05fb: d2f
      // 05fc: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 05ff: aload 0
      // 0600: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0603: aload 0
      // 0604: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0607: ldc2_w 12.5
      // 060a: invokestatic java/lang/Math.toRadians (D)D
      // 060d: d2f
      // 060e: ldc2_w 37.5
      // 0611: invokestatic java/lang/Math.toRadians (D)D
      // 0614: d2f
      // 0615: ldc2_w -60.0
      // 0618: invokestatic java/lang/Math.toRadians (D)D
      // 061b: d2f
      // 061c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 061f: aload 0
      // 0620: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0623: aload 0
      // 0624: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0627: ldc2_w 22.5
      // 062a: invokestatic java/lang/Math.toRadians (D)D
      // 062d: d2f
      // 062e: fconst_0
      // 062f: fconst_0
      // 0630: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0633: aload 0
      // 0634: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0637: aload 0
      // 0638: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 063b: ldc2_w -22.5
      // 063e: invokestatic java/lang/Math.toRadians (D)D
      // 0641: d2f
      // 0642: fconst_0
      // 0643: fconst_0
      // 0644: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0647: aload 0
      // 0648: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 064b: aload 0
      // 064c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 064f: ldc2_w -62.5
      // 0652: invokestatic java/lang/Math.toRadians (D)D
      // 0655: d2f
      // 0656: ldc2_w 25.0
      // 0659: invokestatic java/lang/Math.toRadians (D)D
      // 065c: d2f
      // 065d: ldc2_w 20.0
      // 0660: invokestatic java/lang/Math.toRadians (D)D
      // 0663: d2f
      // 0664: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0667: aload 0
      // 0668: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 066b: aload 0
      // 066c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 066f: ldc2_w 42.5
      // 0672: invokestatic java/lang/Math.toRadians (D)D
      // 0675: d2f
      // 0676: ldc2_w -20.0
      // 0679: invokestatic java/lang/Math.toRadians (D)D
      // 067c: d2f
      // 067d: ldc2_w 62.5
      // 0680: invokestatic java/lang/Math.toRadians (D)D
      // 0683: d2f
      // 0684: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0687: aload 0
      // 0688: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 068b: aload 0
      // 068c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 068f: ldc2_w 17.5
      // 0692: invokestatic java/lang/Math.toRadians (D)D
      // 0695: d2f
      // 0696: fconst_0
      // 0697: fconst_0
      // 0698: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 069b: aload 0
      // 069c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 069f: aload 0
      // 06a0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 06a3: ldc2_w -25.0
      // 06a6: invokestatic java/lang/Math.toRadians (D)D
      // 06a9: d2f
      // 06aa: fconst_0
      // 06ab: fconst_0
      // 06ac: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 06af: aload 0
      // 06b0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 06b3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 06b6: aload 0
      // 06b7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 06ba: bipush 4
      // 06bb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 06be: aload 0
      // 06bf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 06c2: bipush 3
      // 06c3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 06c6: aload 0
      // 06c7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 06ca: aload 0
      // 06cb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 06ce: fconst_0
      // 06cf: fconst_2
      // 06d0: fconst_0
      // 06d1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 06d4: aload 0
      // 06d5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 06d8: aload 0
      // 06d9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 06dc: ldc2_w 55.0
      // 06df: invokestatic java/lang/Math.toRadians (D)D
      // 06e2: d2f
      // 06e3: fconst_0
      // 06e4: fconst_0
      // 06e5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 06e8: aload 0
      // 06e9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 06ec: aload 0
      // 06ed: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 06f0: fconst_0
      // 06f1: fconst_0
      // 06f2: ldc2_w -2.5
      // 06f5: invokestatic java/lang/Math.toRadians (D)D
      // 06f8: d2f
      // 06f9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 06fc: aload 0
      // 06fd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0700: aload 0
      // 0701: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0704: fconst_0
      // 0705: fconst_0
      // 0706: ldc2_w 2.5
      // 0709: invokestatic java/lang/Math.toRadians (D)D
      // 070c: d2f
      // 070d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0710: aload 0
      // 0711: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0714: aload 0
      // 0715: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0718: fconst_0
      // 0719: fconst_0
      // 071a: ldc2_w 7.5
      // 071d: invokestatic java/lang/Math.toRadians (D)D
      // 0720: d2f
      // 0721: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0724: aload 0
      // 0725: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0728: aload 0
      // 0729: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 072c: ldc2_w 17.5
      // 072f: invokestatic java/lang/Math.toRadians (D)D
      // 0732: d2f
      // 0733: fconst_0
      // 0734: ldc2_w -17.5
      // 0737: invokestatic java/lang/Math.toRadians (D)D
      // 073a: d2f
      // 073b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 073e: aload 0
      // 073f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0742: aload 0
      // 0743: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0746: fconst_0
      // 0747: ldc2_w -7.5
      // 074a: invokestatic java/lang/Math.toRadians (D)D
      // 074d: d2f
      // 074e: ldc2_w 20.0
      // 0751: invokestatic java/lang/Math.toRadians (D)D
      // 0754: d2f
      // 0755: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0758: aload 0
      // 0759: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 075c: aload 0
      // 075d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0760: fconst_0
      // 0761: ldc 7.0
      // 0763: fconst_0
      // 0764: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0767: aload 0
      // 0768: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 076b: aload 0
      // 076c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 076f: ldc2_w 12.5
      // 0772: invokestatic java/lang/Math.toRadians (D)D
      // 0775: d2f
      // 0776: fconst_0
      // 0777: ldc2_w 5.0
      // 077a: invokestatic java/lang/Math.toRadians (D)D
      // 077d: d2f
      // 077e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0781: aload 0
      // 0782: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0785: aload 0
      // 0786: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0789: fconst_0
      // 078a: fconst_0
      // 078b: ldc2_w 12.5
      // 078e: invokestatic java/lang/Math.toRadians (D)D
      // 0791: d2f
      // 0792: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0795: aload 0
      // 0796: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0799: aload 0
      // 079a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 079d: fconst_0
      // 079e: fconst_0
      // 079f: ldc2_w -17.5
      // 07a2: invokestatic java/lang/Math.toRadians (D)D
      // 07a5: d2f
      // 07a6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 07a9: aload 0
      // 07aa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 07ad: aload 0
      // 07ae: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 07b1: fconst_0
      // 07b2: fconst_0
      // 07b3: ldc2_w -5.0
      // 07b6: invokestatic java/lang/Math.toRadians (D)D
      // 07b9: d2f
      // 07ba: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 07bd: aload 0
      // 07be: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 07c1: aload 0
      // 07c2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 07c5: fconst_0
      // 07c6: fconst_0
      // 07c7: ldc2_w 25.0
      // 07ca: invokestatic java/lang/Math.toRadians (D)D
      // 07cd: d2f
      // 07ce: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 07d1: aload 0
      // 07d2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 07d5: aload 0
      // 07d6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 07d9: fconst_0
      // 07da: ldc -7.0
      // 07dc: fconst_0
      // 07dd: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 07e0: aload 0
      // 07e1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 07e4: aload 0
      // 07e5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 07e8: fconst_0
      // 07e9: fconst_0
      // 07ea: ldc2_w -7.5
      // 07ed: invokestatic java/lang/Math.toRadians (D)D
      // 07f0: d2f
      // 07f1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 07f4: aload 0
      // 07f5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 07f8: aload 0
      // 07f9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 07fc: fconst_0
      // 07fd: fconst_0
      // 07fe: ldc2_w -27.5
      // 0801: invokestatic java/lang/Math.toRadians (D)D
      // 0804: d2f
      // 0805: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0808: aload 0
      // 0809: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 080c: aload 0
      // 080d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0810: fconst_0
      // 0811: ldc -7.0
      // 0813: fconst_0
      // 0814: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0817: aload 0
      // 0818: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 081b: aload 0
      // 081c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 081f: fconst_0
      // 0820: fconst_0
      // 0821: ldc2_w 10.0
      // 0824: invokestatic java/lang/Math.toRadians (D)D
      // 0827: d2f
      // 0828: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 082b: aload 0
      // 082c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 082f: aload 0
      // 0830: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0833: fconst_0
      // 0834: fconst_0
      // 0835: ldc2_w 22.5
      // 0838: invokestatic java/lang/Math.toRadians (D)D
      // 083b: d2f
      // 083c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 083f: aload 0
      // 0840: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0843: aload 0
      // 0844: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0847: fconst_0
      // 0848: ldc -8.0
      // 084a: fconst_0
      // 084b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 084e: aload 0
      // 084f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0852: aload 0
      // 0853: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0856: fconst_0
      // 0857: fconst_0
      // 0858: ldc2_w -25.0
      // 085b: invokestatic java/lang/Math.toRadians (D)D
      // 085e: d2f
      // 085f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0862: aload 0
      // 0863: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0866: aload 0
      // 0867: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 086a: fconst_0
      // 086b: ldc -7.0
      // 086d: fconst_0
      // 086e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0871: aload 0
      // 0872: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0875: aload 0
      // 0876: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0879: ldc2_w -25.0
      // 087c: invokestatic java/lang/Math.toRadians (D)D
      // 087f: d2f
      // 0880: ldc2_w -20.0
      // 0883: invokestatic java/lang/Math.toRadians (D)D
      // 0886: d2f
      // 0887: ldc2_w -5.0
      // 088a: invokestatic java/lang/Math.toRadians (D)D
      // 088d: d2f
      // 088e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0891: aload 0
      // 0892: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0895: aload 0
      // 0896: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0899: ldc2_w 17.5
      // 089c: invokestatic java/lang/Math.toRadians (D)D
      // 089f: d2f
      // 08a0: ldc2_w 55.0
      // 08a3: invokestatic java/lang/Math.toRadians (D)D
      // 08a6: d2f
      // 08a7: ldc2_w -92.5
      // 08aa: invokestatic java/lang/Math.toRadians (D)D
      // 08ad: d2f
      // 08ae: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 08b1: aload 0
      // 08b2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 08b5: aload 0
      // 08b6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 08b9: ldc2_w 32.5
      // 08bc: invokestatic java/lang/Math.toRadians (D)D
      // 08bf: d2f
      // 08c0: fconst_0
      // 08c1: fconst_0
      // 08c2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 08c5: aload 0
      // 08c6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 08c9: aload 0
      // 08ca: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 08cd: ldc2_w -35.0
      // 08d0: invokestatic java/lang/Math.toRadians (D)D
      // 08d3: d2f
      // 08d4: fconst_0
      // 08d5: fconst_0
      // 08d6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 08d9: aload 0
      // 08da: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 08dd: aload 0
      // 08de: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 08e1: ldc2_w -32.5
      // 08e4: invokestatic java/lang/Math.toRadians (D)D
      // 08e7: d2f
      // 08e8: ldc2_w 30.0
      // 08eb: invokestatic java/lang/Math.toRadians (D)D
      // 08ee: d2f
      // 08ef: ldc2_w 5.0
      // 08f2: invokestatic java/lang/Math.toRadians (D)D
      // 08f5: d2f
      // 08f6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 08f9: aload 0
      // 08fa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 08fd: aload 0
      // 08fe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0901: ldc2_w 65.0
      // 0904: invokestatic java/lang/Math.toRadians (D)D
      // 0907: d2f
      // 0908: ldc2_w -30.0
      // 090b: invokestatic java/lang/Math.toRadians (D)D
      // 090e: d2f
      // 090f: ldc2_w 92.5
      // 0912: invokestatic java/lang/Math.toRadians (D)D
      // 0915: d2f
      // 0916: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0919: aload 0
      // 091a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 091d: aload 0
      // 091e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0921: ldc2_w 27.5
      // 0924: invokestatic java/lang/Math.toRadians (D)D
      // 0927: d2f
      // 0928: fconst_0
      // 0929: fconst_0
      // 092a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 092d: aload 0
      // 092e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0931: aload 0
      // 0932: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0935: ldc2_w -37.5
      // 0938: invokestatic java/lang/Math.toRadians (D)D
      // 093b: d2f
      // 093c: fconst_0
      // 093d: fconst_0
      // 093e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0941: aload 0
      // 0942: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0945: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 0948: aload 0
      // 0949: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 094c: bipush 3
      // 094d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 0950: aload 0
      // 0951: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0954: bipush 10
      // 0956: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 0959: aload 0
      // 095a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 095d: aload 0
      // 095e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0961: fconst_0
      // 0962: ldc2_w 32.5
      // 0965: invokestatic java/lang/Math.toRadians (D)D
      // 0968: d2f
      // 0969: fconst_0
      // 096a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 096d: aload 0
      // 096e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0971: aload 0
      // 0972: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0975: ldc2_w -27.5
      // 0978: invokestatic java/lang/Math.toRadians (D)D
      // 097b: d2f
      // 097c: fconst_0
      // 097d: fconst_0
      // 097e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0981: aload 0
      // 0982: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0985: aload 0
      // 0986: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0989: fconst_0
      // 098a: fconst_0
      // 098b: ldc2_w -10.0
      // 098e: invokestatic java/lang/Math.toRadians (D)D
      // 0991: d2f
      // 0992: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0995: aload 0
      // 0996: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0999: aload 0
      // 099a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 099d: fconst_0
      // 099e: fconst_0
      // 099f: ldc2_w 10.0
      // 09a2: invokestatic java/lang/Math.toRadians (D)D
      // 09a5: d2f
      // 09a6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 09a9: aload 0
      // 09aa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 09ad: aload 0
      // 09ae: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 09b1: fconst_0
      // 09b2: fconst_0
      // 09b3: ldc2_w -15.0
      // 09b6: invokestatic java/lang/Math.toRadians (D)D
      // 09b9: d2f
      // 09ba: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 09bd: aload 0
      // 09be: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 09c1: aload 0
      // 09c2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 09c5: ldc2_w 17.5
      // 09c8: invokestatic java/lang/Math.toRadians (D)D
      // 09cb: d2f
      // 09cc: fconst_0
      // 09cd: ldc2_w -17.5
      // 09d0: invokestatic java/lang/Math.toRadians (D)D
      // 09d3: d2f
      // 09d4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 09d7: aload 0
      // 09d8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 09db: aload 0
      // 09dc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 09df: fconst_0
      // 09e0: fconst_0
      // 09e1: ldc2_w 2.5
      // 09e4: invokestatic java/lang/Math.toRadians (D)D
      // 09e7: d2f
      // 09e8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 09eb: aload 0
      // 09ec: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 09ef: aload 0
      // 09f0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 09f3: fconst_0
      // 09f4: fconst_0
      // 09f5: ldc2_w 17.5
      // 09f8: invokestatic java/lang/Math.toRadians (D)D
      // 09fb: d2f
      // 09fc: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 09ff: aload 0
      // 0a00: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0a03: aload 0
      // 0a04: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0a07: fconst_0
      // 0a08: fconst_0
      // 0a09: ldc2_w 5.0
      // 0a0c: invokestatic java/lang/Math.toRadians (D)D
      // 0a0f: d2f
      // 0a10: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0a13: aload 0
      // 0a14: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0a17: aload 0
      // 0a18: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0a1b: fconst_0
      // 0a1c: fconst_0
      // 0a1d: ldc2_w 2.5
      // 0a20: invokestatic java/lang/Math.toRadians (D)D
      // 0a23: d2f
      // 0a24: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0a27: aload 0
      // 0a28: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0a2b: aload 0
      // 0a2c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0a2f: fconst_0
      // 0a30: fconst_0
      // 0a31: ldc2_w 5.0
      // 0a34: invokestatic java/lang/Math.toRadians (D)D
      // 0a37: d2f
      // 0a38: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0a3b: aload 0
      // 0a3c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0a3f: aload 0
      // 0a40: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0a43: fconst_0
      // 0a44: fconst_0
      // 0a45: ldc2_w -12.5
      // 0a48: invokestatic java/lang/Math.toRadians (D)D
      // 0a4b: d2f
      // 0a4c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0a4f: aload 0
      // 0a50: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0a53: aload 0
      // 0a54: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0a57: fconst_0
      // 0a58: fconst_0
      // 0a59: ldc2_w -10.0
      // 0a5c: invokestatic java/lang/Math.toRadians (D)D
      // 0a5f: d2f
      // 0a60: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0a63: aload 0
      // 0a64: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0a67: aload 0
      // 0a68: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0a6b: fconst_0
      // 0a6c: fconst_0
      // 0a6d: ldc2_w 22.5
      // 0a70: invokestatic java/lang/Math.toRadians (D)D
      // 0a73: d2f
      // 0a74: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0a77: aload 0
      // 0a78: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0a7b: aload 0
      // 0a7c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0a7f: fconst_0
      // 0a80: fconst_0
      // 0a81: ldc2_w 7.5
      // 0a84: invokestatic java/lang/Math.toRadians (D)D
      // 0a87: d2f
      // 0a88: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0a8b: aload 0
      // 0a8c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0a8f: aload 0
      // 0a90: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0a93: fconst_0
      // 0a94: fconst_0
      // 0a95: ldc2_w -22.5
      // 0a98: invokestatic java/lang/Math.toRadians (D)D
      // 0a9b: d2f
      // 0a9c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0a9f: aload 0
      // 0aa0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0aa3: aload 0
      // 0aa4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0aa7: ldc2_w 10.0
      // 0aaa: invokestatic java/lang/Math.toRadians (D)D
      // 0aad: d2f
      // 0aae: ldc2_w -2.5
      // 0ab1: invokestatic java/lang/Math.toRadians (D)D
      // 0ab4: d2f
      // 0ab5: ldc2_w -7.5
      // 0ab8: invokestatic java/lang/Math.toRadians (D)D
      // 0abb: d2f
      // 0abc: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0abf: aload 0
      // 0ac0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0ac3: aload 0
      // 0ac4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ac7: fconst_0
      // 0ac8: ldc 4.0
      // 0aca: fconst_0
      // 0acb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0ace: aload 0
      // 0acf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0ad2: aload 0
      // 0ad3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ad6: ldc2_w 20.0
      // 0ad9: invokestatic java/lang/Math.toRadians (D)D
      // 0adc: d2f
      // 0add: ldc2_w 15.0
      // 0ae0: invokestatic java/lang/Math.toRadians (D)D
      // 0ae3: d2f
      // 0ae4: ldc2_w 12.5
      // 0ae7: invokestatic java/lang/Math.toRadians (D)D
      // 0aea: d2f
      // 0aeb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0aee: aload 0
      // 0aef: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0af2: aload 0
      // 0af3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0af6: fconst_0
      // 0af7: ldc 6.0
      // 0af9: fconst_0
      // 0afa: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0afd: aload 0
      // 0afe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0b01: aload 0
      // 0b02: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0b05: ldc2_w -32.5
      // 0b08: invokestatic java/lang/Math.toRadians (D)D
      // 0b0b: d2f
      // 0b0c: ldc2_w -25.0
      // 0b0f: invokestatic java/lang/Math.toRadians (D)D
      // 0b12: d2f
      // 0b13: ldc2_w -30.0
      // 0b16: invokestatic java/lang/Math.toRadians (D)D
      // 0b19: d2f
      // 0b1a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0b1d: aload 0
      // 0b1e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0b21: aload 0
      // 0b22: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0b25: ldc2_w 17.5
      // 0b28: invokestatic java/lang/Math.toRadians (D)D
      // 0b2b: d2f
      // 0b2c: ldc2_w 55.0
      // 0b2f: invokestatic java/lang/Math.toRadians (D)D
      // 0b32: d2f
      // 0b33: ldc2_w -92.5
      // 0b36: invokestatic java/lang/Math.toRadians (D)D
      // 0b39: d2f
      // 0b3a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0b3d: aload 0
      // 0b3e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0b41: aload 0
      // 0b42: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0b45: ldc2_w 32.5
      // 0b48: invokestatic java/lang/Math.toRadians (D)D
      // 0b4b: d2f
      // 0b4c: fconst_0
      // 0b4d: fconst_0
      // 0b4e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0b51: aload 0
      // 0b52: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0b55: aload 0
      // 0b56: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0b59: ldc2_w -35.0
      // 0b5c: invokestatic java/lang/Math.toRadians (D)D
      // 0b5f: d2f
      // 0b60: fconst_0
      // 0b61: fconst_0
      // 0b62: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0b65: aload 0
      // 0b66: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0b69: aload 0
      // 0b6a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0b6d: ldc2_w -62.5
      // 0b70: invokestatic java/lang/Math.toRadians (D)D
      // 0b73: d2f
      // 0b74: ldc2_w 25.0
      // 0b77: invokestatic java/lang/Math.toRadians (D)D
      // 0b7a: d2f
      // 0b7b: ldc2_w 20.0
      // 0b7e: invokestatic java/lang/Math.toRadians (D)D
      // 0b81: d2f
      // 0b82: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0b85: aload 0
      // 0b86: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0b89: aload 0
      // 0b8a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0b8d: ldc2_w 65.0
      // 0b90: invokestatic java/lang/Math.toRadians (D)D
      // 0b93: d2f
      // 0b94: ldc2_w -30.0
      // 0b97: invokestatic java/lang/Math.toRadians (D)D
      // 0b9a: d2f
      // 0b9b: ldc2_w 92.5
      // 0b9e: invokestatic java/lang/Math.toRadians (D)D
      // 0ba1: d2f
      // 0ba2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0ba5: aload 0
      // 0ba6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0ba9: aload 0
      // 0baa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0bad: ldc2_w 27.5
      // 0bb0: invokestatic java/lang/Math.toRadians (D)D
      // 0bb3: d2f
      // 0bb4: fconst_0
      // 0bb5: fconst_0
      // 0bb6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0bb9: aload 0
      // 0bba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0bbd: aload 0
      // 0bbe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0bc1: ldc2_w -37.5
      // 0bc4: invokestatic java/lang/Math.toRadians (D)D
      // 0bc7: d2f
      // 0bc8: fconst_0
      // 0bc9: fconst_0
      // 0bca: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0bcd: aload 0
      // 0bce: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0bd1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 0bd4: aload 0
      // 0bd5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0bd8: bipush 4
      // 0bd9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 0bdc: aload 0
      // 0bdd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0be0: bipush 3
      // 0be1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 0be4: aload 0
      // 0be5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0be8: aload 0
      // 0be9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0bec: fconst_0
      // 0bed: ldc2_w 45.0
      // 0bf0: invokestatic java/lang/Math.toRadians (D)D
      // 0bf3: d2f
      // 0bf4: fconst_0
      // 0bf5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0bf8: aload 0
      // 0bf9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0bfc: aload 0
      // 0bfd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0c00: fconst_0
      // 0c01: fconst_2
      // 0c02: fconst_0
      // 0c03: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0c06: aload 0
      // 0c07: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0c0a: aload 0
      // 0c0b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0c0e: ldc2_w 55.0
      // 0c11: invokestatic java/lang/Math.toRadians (D)D
      // 0c14: d2f
      // 0c15: fconst_0
      // 0c16: fconst_0
      // 0c17: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0c1a: aload 0
      // 0c1b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0c1e: aload 0
      // 0c1f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0c22: fconst_0
      // 0c23: fconst_0
      // 0c24: ldc2_w 2.5
      // 0c27: invokestatic java/lang/Math.toRadians (D)D
      // 0c2a: d2f
      // 0c2b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0c2e: aload 0
      // 0c2f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0c32: aload 0
      // 0c33: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0c36: fconst_0
      // 0c37: fconst_0
      // 0c38: ldc2_w 2.5
      // 0c3b: invokestatic java/lang/Math.toRadians (D)D
      // 0c3e: d2f
      // 0c3f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0c42: aload 0
      // 0c43: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0c46: aload 0
      // 0c47: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0c4a: fconst_0
      // 0c4b: fconst_0
      // 0c4c: ldc2_w -7.5
      // 0c4f: invokestatic java/lang/Math.toRadians (D)D
      // 0c52: d2f
      // 0c53: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0c56: aload 0
      // 0c57: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0c5a: aload 0
      // 0c5b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0c5e: ldc2_w 17.5
      // 0c61: invokestatic java/lang/Math.toRadians (D)D
      // 0c64: d2f
      // 0c65: fconst_0
      // 0c66: ldc2_w -17.5
      // 0c69: invokestatic java/lang/Math.toRadians (D)D
      // 0c6c: d2f
      // 0c6d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0c70: aload 0
      // 0c71: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0c74: aload 0
      // 0c75: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0c78: fconst_0
      // 0c79: ldc2_w -7.5
      // 0c7c: invokestatic java/lang/Math.toRadians (D)D
      // 0c7f: d2f
      // 0c80: ldc2_w 20.0
      // 0c83: invokestatic java/lang/Math.toRadians (D)D
      // 0c86: d2f
      // 0c87: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0c8a: aload 0
      // 0c8b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0c8e: aload 0
      // 0c8f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0c92: fconst_0
      // 0c93: ldc 7.0
      // 0c95: fconst_0
      // 0c96: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0c99: aload 0
      // 0c9a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0c9d: aload 0
      // 0c9e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ca1: ldc2_w 12.5
      // 0ca4: invokestatic java/lang/Math.toRadians (D)D
      // 0ca7: d2f
      // 0ca8: fconst_0
      // 0ca9: ldc2_w 5.0
      // 0cac: invokestatic java/lang/Math.toRadians (D)D
      // 0caf: d2f
      // 0cb0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0cb3: aload 0
      // 0cb4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0cb7: aload 0
      // 0cb8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0cbb: fconst_0
      // 0cbc: fconst_0
      // 0cbd: ldc2_w 12.5
      // 0cc0: invokestatic java/lang/Math.toRadians (D)D
      // 0cc3: d2f
      // 0cc4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0cc7: aload 0
      // 0cc8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0ccb: aload 0
      // 0ccc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ccf: fconst_0
      // 0cd0: fconst_0
      // 0cd1: ldc2_w -17.5
      // 0cd4: invokestatic java/lang/Math.toRadians (D)D
      // 0cd7: d2f
      // 0cd8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0cdb: aload 0
      // 0cdc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0cdf: aload 0
      // 0ce0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ce3: fconst_0
      // 0ce4: fconst_0
      // 0ce5: ldc2_w -5.0
      // 0ce8: invokestatic java/lang/Math.toRadians (D)D
      // 0ceb: d2f
      // 0cec: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0cef: aload 0
      // 0cf0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0cf3: aload 0
      // 0cf4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0cf7: fconst_0
      // 0cf8: fconst_0
      // 0cf9: ldc2_w 25.0
      // 0cfc: invokestatic java/lang/Math.toRadians (D)D
      // 0cff: d2f
      // 0d00: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0d03: aload 0
      // 0d04: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0d07: aload 0
      // 0d08: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0d0b: fconst_0
      // 0d0c: ldc -7.0
      // 0d0e: fconst_0
      // 0d0f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0d12: aload 0
      // 0d13: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0d16: aload 0
      // 0d17: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0d1a: fconst_0
      // 0d1b: fconst_0
      // 0d1c: ldc2_w -7.5
      // 0d1f: invokestatic java/lang/Math.toRadians (D)D
      // 0d22: d2f
      // 0d23: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0d26: aload 0
      // 0d27: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0d2a: aload 0
      // 0d2b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0d2e: fconst_0
      // 0d2f: fconst_0
      // 0d30: ldc2_w -27.5
      // 0d33: invokestatic java/lang/Math.toRadians (D)D
      // 0d36: d2f
      // 0d37: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0d3a: aload 0
      // 0d3b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0d3e: aload 0
      // 0d3f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0d42: fconst_0
      // 0d43: ldc -7.0
      // 0d45: fconst_0
      // 0d46: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0d49: aload 0
      // 0d4a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0d4d: aload 0
      // 0d4e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0d51: fconst_0
      // 0d52: fconst_0
      // 0d53: ldc2_w 10.0
      // 0d56: invokestatic java/lang/Math.toRadians (D)D
      // 0d59: d2f
      // 0d5a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0d5d: aload 0
      // 0d5e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0d61: aload 0
      // 0d62: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0d65: fconst_0
      // 0d66: fconst_0
      // 0d67: ldc2_w 22.5
      // 0d6a: invokestatic java/lang/Math.toRadians (D)D
      // 0d6d: d2f
      // 0d6e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0d71: aload 0
      // 0d72: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0d75: aload 0
      // 0d76: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0d79: fconst_0
      // 0d7a: ldc -8.0
      // 0d7c: fconst_0
      // 0d7d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0d80: aload 0
      // 0d81: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0d84: aload 0
      // 0d85: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0d88: fconst_0
      // 0d89: fconst_0
      // 0d8a: ldc2_w -25.0
      // 0d8d: invokestatic java/lang/Math.toRadians (D)D
      // 0d90: d2f
      // 0d91: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0d94: aload 0
      // 0d95: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0d98: aload 0
      // 0d99: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0d9c: fconst_0
      // 0d9d: ldc -7.0
      // 0d9f: fconst_0
      // 0da0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0da3: aload 0
      // 0da4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0da7: aload 0
      // 0da8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0dab: ldc2_w -25.0
      // 0dae: invokestatic java/lang/Math.toRadians (D)D
      // 0db1: d2f
      // 0db2: ldc2_w -20.0
      // 0db5: invokestatic java/lang/Math.toRadians (D)D
      // 0db8: d2f
      // 0db9: ldc2_w -5.0
      // 0dbc: invokestatic java/lang/Math.toRadians (D)D
      // 0dbf: d2f
      // 0dc0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0dc3: aload 0
      // 0dc4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0dc7: aload 0
      // 0dc8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0dcb: ldc2_w 20.0
      // 0dce: invokestatic java/lang/Math.toRadians (D)D
      // 0dd1: d2f
      // 0dd2: ldc2_w 55.0
      // 0dd5: invokestatic java/lang/Math.toRadians (D)D
      // 0dd8: d2f
      // 0dd9: ldc2_w -92.5
      // 0ddc: invokestatic java/lang/Math.toRadians (D)D
      // 0ddf: d2f
      // 0de0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0de3: aload 0
      // 0de4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0de7: aload 0
      // 0de8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0deb: ldc2_w 32.5
      // 0dee: invokestatic java/lang/Math.toRadians (D)D
      // 0df1: d2f
      // 0df2: fconst_0
      // 0df3: fconst_0
      // 0df4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0df7: aload 0
      // 0df8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0dfb: aload 0
      // 0dfc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0dff: ldc2_w -35.0
      // 0e02: invokestatic java/lang/Math.toRadians (D)D
      // 0e05: d2f
      // 0e06: fconst_0
      // 0e07: fconst_0
      // 0e08: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0e0b: aload 0
      // 0e0c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0e0f: aload 0
      // 0e10: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0e13: ldc2_w -32.5
      // 0e16: invokestatic java/lang/Math.toRadians (D)D
      // 0e19: d2f
      // 0e1a: ldc2_w 30.0
      // 0e1d: invokestatic java/lang/Math.toRadians (D)D
      // 0e20: d2f
      // 0e21: ldc2_w 5.0
      // 0e24: invokestatic java/lang/Math.toRadians (D)D
      // 0e27: d2f
      // 0e28: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0e2b: aload 0
      // 0e2c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0e2f: aload 0
      // 0e30: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0e33: ldc2_w 65.0
      // 0e36: invokestatic java/lang/Math.toRadians (D)D
      // 0e39: d2f
      // 0e3a: ldc2_w -30.0
      // 0e3d: invokestatic java/lang/Math.toRadians (D)D
      // 0e40: d2f
      // 0e41: ldc2_w 92.5
      // 0e44: invokestatic java/lang/Math.toRadians (D)D
      // 0e47: d2f
      // 0e48: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0e4b: aload 0
      // 0e4c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0e4f: aload 0
      // 0e50: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0e53: ldc2_w 27.5
      // 0e56: invokestatic java/lang/Math.toRadians (D)D
      // 0e59: d2f
      // 0e5a: fconst_0
      // 0e5b: fconst_0
      // 0e5c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0e5f: aload 0
      // 0e60: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0e63: aload 0
      // 0e64: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0e67: ldc2_w -37.5
      // 0e6a: invokestatic java/lang/Math.toRadians (D)D
      // 0e6d: d2f
      // 0e6e: fconst_0
      // 0e6f: fconst_0
      // 0e70: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0e73: aload 0
      // 0e74: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0e77: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 0e7a: aload 0
      // 0e7b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0e7e: bipush 3
      // 0e7f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 0e82: aload 0
      // 0e83: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0e86: bipush 10
      // 0e88: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 0e8b: aload 0
      // 0e8c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0e8f: aload 0
      // 0e90: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0e93: ldc2_w -27.5
      // 0e96: invokestatic java/lang/Math.toRadians (D)D
      // 0e99: d2f
      // 0e9a: fconst_0
      // 0e9b: fconst_0
      // 0e9c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0e9f: aload 0
      // 0ea0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0ea3: aload 0
      // 0ea4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ea7: fconst_0
      // 0ea8: fconst_0
      // 0ea9: ldc2_w -10.0
      // 0eac: invokestatic java/lang/Math.toRadians (D)D
      // 0eaf: d2f
      // 0eb0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0eb3: aload 0
      // 0eb4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0eb7: aload 0
      // 0eb8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ebb: fconst_0
      // 0ebc: fconst_0
      // 0ebd: ldc2_w 10.0
      // 0ec0: invokestatic java/lang/Math.toRadians (D)D
      // 0ec3: d2f
      // 0ec4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0ec7: aload 0
      // 0ec8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0ecb: aload 0
      // 0ecc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ecf: fconst_0
      // 0ed0: fconst_0
      // 0ed1: ldc2_w -15.0
      // 0ed4: invokestatic java/lang/Math.toRadians (D)D
      // 0ed7: d2f
      // 0ed8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0edb: aload 0
      // 0edc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0edf: aload 0
      // 0ee0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ee3: ldc2_w 17.5
      // 0ee6: invokestatic java/lang/Math.toRadians (D)D
      // 0ee9: d2f
      // 0eea: fconst_0
      // 0eeb: ldc2_w -17.5
      // 0eee: invokestatic java/lang/Math.toRadians (D)D
      // 0ef1: d2f
      // 0ef2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0ef5: aload 0
      // 0ef6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0ef9: aload 0
      // 0efa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0efd: fconst_0
      // 0efe: fconst_0
      // 0eff: ldc2_w 2.5
      // 0f02: invokestatic java/lang/Math.toRadians (D)D
      // 0f05: d2f
      // 0f06: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0f09: aload 0
      // 0f0a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0f0d: aload 0
      // 0f0e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0f11: fconst_0
      // 0f12: fconst_0
      // 0f13: ldc2_w 17.5
      // 0f16: invokestatic java/lang/Math.toRadians (D)D
      // 0f19: d2f
      // 0f1a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0f1d: aload 0
      // 0f1e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0f21: aload 0
      // 0f22: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0f25: fconst_0
      // 0f26: fconst_0
      // 0f27: ldc2_w 5.0
      // 0f2a: invokestatic java/lang/Math.toRadians (D)D
      // 0f2d: d2f
      // 0f2e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0f31: aload 0
      // 0f32: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0f35: aload 0
      // 0f36: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0f39: fconst_0
      // 0f3a: fconst_0
      // 0f3b: ldc2_w 2.5
      // 0f3e: invokestatic java/lang/Math.toRadians (D)D
      // 0f41: d2f
      // 0f42: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0f45: aload 0
      // 0f46: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0f49: aload 0
      // 0f4a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0f4d: fconst_0
      // 0f4e: fconst_0
      // 0f4f: ldc2_w 5.0
      // 0f52: invokestatic java/lang/Math.toRadians (D)D
      // 0f55: d2f
      // 0f56: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0f59: aload 0
      // 0f5a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0f5d: aload 0
      // 0f5e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0f61: fconst_0
      // 0f62: fconst_0
      // 0f63: ldc2_w -12.5
      // 0f66: invokestatic java/lang/Math.toRadians (D)D
      // 0f69: d2f
      // 0f6a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0f6d: aload 0
      // 0f6e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0f71: aload 0
      // 0f72: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0f75: fconst_0
      // 0f76: fconst_0
      // 0f77: ldc2_w -10.0
      // 0f7a: invokestatic java/lang/Math.toRadians (D)D
      // 0f7d: d2f
      // 0f7e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0f81: aload 0
      // 0f82: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0f85: aload 0
      // 0f86: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0f89: fconst_0
      // 0f8a: fconst_0
      // 0f8b: ldc2_w 22.5
      // 0f8e: invokestatic java/lang/Math.toRadians (D)D
      // 0f91: d2f
      // 0f92: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0f95: aload 0
      // 0f96: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0f99: aload 0
      // 0f9a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0f9d: fconst_0
      // 0f9e: fconst_0
      // 0f9f: ldc2_w 7.5
      // 0fa2: invokestatic java/lang/Math.toRadians (D)D
      // 0fa5: d2f
      // 0fa6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0fa9: aload 0
      // 0faa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0fad: aload 0
      // 0fae: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0fb1: fconst_0
      // 0fb2: fconst_0
      // 0fb3: ldc2_w -22.5
      // 0fb6: invokestatic java/lang/Math.toRadians (D)D
      // 0fb9: d2f
      // 0fba: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0fbd: aload 0
      // 0fbe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0fc1: aload 0
      // 0fc2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0fc5: ldc2_w 10.0
      // 0fc8: invokestatic java/lang/Math.toRadians (D)D
      // 0fcb: d2f
      // 0fcc: ldc2_w -2.5
      // 0fcf: invokestatic java/lang/Math.toRadians (D)D
      // 0fd2: d2f
      // 0fd3: ldc2_w -7.5
      // 0fd6: invokestatic java/lang/Math.toRadians (D)D
      // 0fd9: d2f
      // 0fda: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0fdd: aload 0
      // 0fde: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0fe1: aload 0
      // 0fe2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0fe5: fconst_0
      // 0fe6: ldc 4.0
      // 0fe8: fconst_0
      // 0fe9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 0fec: aload 0
      // 0fed: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 0ff0: aload 0
      // 0ff1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 0ff4: ldc2_w 20.0
      // 0ff7: invokestatic java/lang/Math.toRadians (D)D
      // 0ffa: d2f
      // 0ffb: ldc2_w 15.0
      // 0ffe: invokestatic java/lang/Math.toRadians (D)D
      // 1001: d2f
      // 1002: ldc2_w 12.5
      // 1005: invokestatic java/lang/Math.toRadians (D)D
      // 1008: d2f
      // 1009: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 100c: aload 0
      // 100d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1010: aload 0
      // 1011: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1014: fconst_0
      // 1015: ldc 6.0
      // 1017: fconst_0
      // 1018: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 101b: aload 0
      // 101c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 101f: aload 0
      // 1020: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1023: ldc2_w -35.0
      // 1026: invokestatic java/lang/Math.toRadians (D)D
      // 1029: d2f
      // 102a: ldc2_w -25.0
      // 102d: invokestatic java/lang/Math.toRadians (D)D
      // 1030: d2f
      // 1031: ldc2_w -30.0
      // 1034: invokestatic java/lang/Math.toRadians (D)D
      // 1037: d2f
      // 1038: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 103b: aload 0
      // 103c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 103f: aload 0
      // 1040: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1043: ldc2_w 17.5
      // 1046: invokestatic java/lang/Math.toRadians (D)D
      // 1049: d2f
      // 104a: ldc2_w 55.0
      // 104d: invokestatic java/lang/Math.toRadians (D)D
      // 1050: d2f
      // 1051: ldc2_w -92.5
      // 1054: invokestatic java/lang/Math.toRadians (D)D
      // 1057: d2f
      // 1058: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 105b: aload 0
      // 105c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 105f: aload 0
      // 1060: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1063: ldc2_w 32.5
      // 1066: invokestatic java/lang/Math.toRadians (D)D
      // 1069: d2f
      // 106a: fconst_0
      // 106b: fconst_0
      // 106c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 106f: aload 0
      // 1070: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1073: aload 0
      // 1074: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1077: ldc2_w -35.0
      // 107a: invokestatic java/lang/Math.toRadians (D)D
      // 107d: d2f
      // 107e: fconst_0
      // 107f: fconst_0
      // 1080: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1083: aload 0
      // 1084: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1087: aload 0
      // 1088: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 108b: ldc2_w -62.5
      // 108e: invokestatic java/lang/Math.toRadians (D)D
      // 1091: d2f
      // 1092: ldc2_w 25.0
      // 1095: invokestatic java/lang/Math.toRadians (D)D
      // 1098: d2f
      // 1099: ldc2_w 20.0
      // 109c: invokestatic java/lang/Math.toRadians (D)D
      // 109f: d2f
      // 10a0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 10a3: aload 0
      // 10a4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 10a7: aload 0
      // 10a8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 10ab: ldc2_w 65.0
      // 10ae: invokestatic java/lang/Math.toRadians (D)D
      // 10b1: d2f
      // 10b2: ldc2_w -30.0
      // 10b5: invokestatic java/lang/Math.toRadians (D)D
      // 10b8: d2f
      // 10b9: ldc2_w 92.5
      // 10bc: invokestatic java/lang/Math.toRadians (D)D
      // 10bf: d2f
      // 10c0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 10c3: aload 0
      // 10c4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 10c7: aload 0
      // 10c8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 10cb: ldc2_w 27.5
      // 10ce: invokestatic java/lang/Math.toRadians (D)D
      // 10d1: d2f
      // 10d2: fconst_0
      // 10d3: fconst_0
      // 10d4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 10d7: aload 0
      // 10d8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 10db: aload 0
      // 10dc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 10df: ldc2_w -37.5
      // 10e2: invokestatic java/lang/Math.toRadians (D)D
      // 10e5: d2f
      // 10e6: fconst_0
      // 10e7: fconst_0
      // 10e8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 10eb: aload 0
      // 10ec: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 10ef: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 10f2: aload 0
      // 10f3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 10f6: bipush 4
      // 10f7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 10fa: aload 0
      // 10fb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 10fe: bipush 3
      // 10ff: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 1102: aload 0
      // 1103: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1106: aload 0
      // 1107: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 110a: fconst_0
      // 110b: ldc2_w -25.0
      // 110e: invokestatic java/lang/Math.toRadians (D)D
      // 1111: d2f
      // 1112: fconst_0
      // 1113: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1116: aload 0
      // 1117: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 111a: aload 0
      // 111b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 111e: fconst_0
      // 111f: fconst_2
      // 1120: fconst_0
      // 1121: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1124: aload 0
      // 1125: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1128: aload 0
      // 1129: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 112c: ldc2_w 55.0
      // 112f: invokestatic java/lang/Math.toRadians (D)D
      // 1132: d2f
      // 1133: fconst_0
      // 1134: fconst_0
      // 1135: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1138: aload 0
      // 1139: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 113c: aload 0
      // 113d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1140: fconst_0
      // 1141: fconst_0
      // 1142: ldc2_w 2.5
      // 1145: invokestatic java/lang/Math.toRadians (D)D
      // 1148: d2f
      // 1149: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 114c: aload 0
      // 114d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1150: aload 0
      // 1151: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1154: fconst_0
      // 1155: fconst_0
      // 1156: ldc2_w 2.5
      // 1159: invokestatic java/lang/Math.toRadians (D)D
      // 115c: d2f
      // 115d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1160: aload 0
      // 1161: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1164: aload 0
      // 1165: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1168: fconst_0
      // 1169: fconst_0
      // 116a: ldc2_w 7.5
      // 116d: invokestatic java/lang/Math.toRadians (D)D
      // 1170: d2f
      // 1171: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1174: aload 0
      // 1175: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1178: aload 0
      // 1179: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 117c: ldc2_w 17.5
      // 117f: invokestatic java/lang/Math.toRadians (D)D
      // 1182: d2f
      // 1183: fconst_0
      // 1184: ldc2_w -17.5
      // 1187: invokestatic java/lang/Math.toRadians (D)D
      // 118a: d2f
      // 118b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 118e: aload 0
      // 118f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1192: aload 0
      // 1193: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1196: fconst_0
      // 1197: ldc2_w -7.5
      // 119a: invokestatic java/lang/Math.toRadians (D)D
      // 119d: d2f
      // 119e: ldc2_w 20.0
      // 11a1: invokestatic java/lang/Math.toRadians (D)D
      // 11a4: d2f
      // 11a5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 11a8: aload 0
      // 11a9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 11ac: aload 0
      // 11ad: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 11b0: fconst_0
      // 11b1: ldc 7.0
      // 11b3: fconst_0
      // 11b4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 11b7: aload 0
      // 11b8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 11bb: aload 0
      // 11bc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 11bf: ldc2_w 12.5
      // 11c2: invokestatic java/lang/Math.toRadians (D)D
      // 11c5: d2f
      // 11c6: fconst_0
      // 11c7: ldc2_w 5.0
      // 11ca: invokestatic java/lang/Math.toRadians (D)D
      // 11cd: d2f
      // 11ce: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 11d1: aload 0
      // 11d2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 11d5: aload 0
      // 11d6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 11d9: fconst_0
      // 11da: fconst_0
      // 11db: ldc2_w 12.5
      // 11de: invokestatic java/lang/Math.toRadians (D)D
      // 11e1: d2f
      // 11e2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 11e5: aload 0
      // 11e6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 11e9: aload 0
      // 11ea: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 11ed: fconst_0
      // 11ee: fconst_0
      // 11ef: ldc2_w -17.5
      // 11f2: invokestatic java/lang/Math.toRadians (D)D
      // 11f5: d2f
      // 11f6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 11f9: aload 0
      // 11fa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 11fd: aload 0
      // 11fe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1201: fconst_0
      // 1202: fconst_0
      // 1203: ldc2_w -5.0
      // 1206: invokestatic java/lang/Math.toRadians (D)D
      // 1209: d2f
      // 120a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 120d: aload 0
      // 120e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1211: aload 0
      // 1212: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1215: fconst_0
      // 1216: fconst_0
      // 1217: ldc2_w 25.0
      // 121a: invokestatic java/lang/Math.toRadians (D)D
      // 121d: d2f
      // 121e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1221: aload 0
      // 1222: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1225: aload 0
      // 1226: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1229: fconst_0
      // 122a: ldc -7.0
      // 122c: fconst_0
      // 122d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1230: aload 0
      // 1231: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1234: aload 0
      // 1235: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1238: fconst_0
      // 1239: fconst_0
      // 123a: ldc2_w -7.5
      // 123d: invokestatic java/lang/Math.toRadians (D)D
      // 1240: d2f
      // 1241: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1244: aload 0
      // 1245: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1248: aload 0
      // 1249: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 124c: fconst_0
      // 124d: fconst_0
      // 124e: ldc2_w -27.5
      // 1251: invokestatic java/lang/Math.toRadians (D)D
      // 1254: d2f
      // 1255: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1258: aload 0
      // 1259: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 125c: aload 0
      // 125d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1260: fconst_0
      // 1261: ldc -7.0
      // 1263: fconst_0
      // 1264: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1267: aload 0
      // 1268: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 126b: aload 0
      // 126c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 126f: fconst_0
      // 1270: fconst_0
      // 1271: ldc2_w 10.0
      // 1274: invokestatic java/lang/Math.toRadians (D)D
      // 1277: d2f
      // 1278: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 127b: aload 0
      // 127c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 127f: aload 0
      // 1280: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1283: fconst_0
      // 1284: fconst_0
      // 1285: ldc2_w 22.5
      // 1288: invokestatic java/lang/Math.toRadians (D)D
      // 128b: d2f
      // 128c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 128f: aload 0
      // 1290: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1293: aload 0
      // 1294: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1297: fconst_0
      // 1298: ldc -8.0
      // 129a: fconst_0
      // 129b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 129e: aload 0
      // 129f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 12a2: aload 0
      // 12a3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 12a6: fconst_0
      // 12a7: fconst_0
      // 12a8: ldc2_w -25.0
      // 12ab: invokestatic java/lang/Math.toRadians (D)D
      // 12ae: d2f
      // 12af: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 12b2: aload 0
      // 12b3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 12b6: aload 0
      // 12b7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 12ba: fconst_0
      // 12bb: ldc -7.0
      // 12bd: fconst_0
      // 12be: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 12c1: aload 0
      // 12c2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 12c5: aload 0
      // 12c6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 12c9: ldc2_w -25.0
      // 12cc: invokestatic java/lang/Math.toRadians (D)D
      // 12cf: d2f
      // 12d0: ldc2_w -20.0
      // 12d3: invokestatic java/lang/Math.toRadians (D)D
      // 12d6: d2f
      // 12d7: ldc2_w -5.0
      // 12da: invokestatic java/lang/Math.toRadians (D)D
      // 12dd: d2f
      // 12de: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 12e1: aload 0
      // 12e2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 12e5: aload 0
      // 12e6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 12e9: ldc2_w 20.0
      // 12ec: invokestatic java/lang/Math.toRadians (D)D
      // 12ef: d2f
      // 12f0: ldc2_w 55.0
      // 12f3: invokestatic java/lang/Math.toRadians (D)D
      // 12f6: d2f
      // 12f7: ldc2_w -92.5
      // 12fa: invokestatic java/lang/Math.toRadians (D)D
      // 12fd: d2f
      // 12fe: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1301: aload 0
      // 1302: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1305: aload 0
      // 1306: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1309: ldc2_w 32.5
      // 130c: invokestatic java/lang/Math.toRadians (D)D
      // 130f: d2f
      // 1310: fconst_0
      // 1311: fconst_0
      // 1312: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1315: aload 0
      // 1316: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1319: aload 0
      // 131a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 131d: ldc2_w -35.0
      // 1320: invokestatic java/lang/Math.toRadians (D)D
      // 1323: d2f
      // 1324: fconst_0
      // 1325: fconst_0
      // 1326: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1329: aload 0
      // 132a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 132d: aload 0
      // 132e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1331: ldc2_w -32.5
      // 1334: invokestatic java/lang/Math.toRadians (D)D
      // 1337: d2f
      // 1338: ldc2_w 30.0
      // 133b: invokestatic java/lang/Math.toRadians (D)D
      // 133e: d2f
      // 133f: ldc2_w 5.0
      // 1342: invokestatic java/lang/Math.toRadians (D)D
      // 1345: d2f
      // 1346: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1349: aload 0
      // 134a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 134d: aload 0
      // 134e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1351: ldc2_w 65.0
      // 1354: invokestatic java/lang/Math.toRadians (D)D
      // 1357: d2f
      // 1358: ldc2_w -30.0
      // 135b: invokestatic java/lang/Math.toRadians (D)D
      // 135e: d2f
      // 135f: ldc2_w 92.5
      // 1362: invokestatic java/lang/Math.toRadians (D)D
      // 1365: d2f
      // 1366: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1369: aload 0
      // 136a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 136d: aload 0
      // 136e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1371: ldc2_w 27.5
      // 1374: invokestatic java/lang/Math.toRadians (D)D
      // 1377: d2f
      // 1378: fconst_0
      // 1379: fconst_0
      // 137a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 137d: aload 0
      // 137e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1381: aload 0
      // 1382: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1385: ldc2_w -37.5
      // 1388: invokestatic java/lang/Math.toRadians (D)D
      // 138b: d2f
      // 138c: fconst_0
      // 138d: fconst_0
      // 138e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1391: aload 0
      // 1392: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1395: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 1398: aload 0
      // 1399: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 139c: bipush 10
      // 139e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 13a1: aload 0
      // 13a2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 13a5: bipush 10
      // 13a7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.resetKeyframe (I)V
      // 13aa: aload 0
      // 13ab: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 13ae: getstatic com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Amethyst_Crab_Entity.CRAB_DEATH Lcom/github/L_Ender/lionfishapi/server/animation/Animation;
      // 13b1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setAnimation (Lcom/github/L_Ender/lionfishapi/server/animation/Animation;)Z
      // 13b4: pop
      // 13b5: aload 0
      // 13b6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 13b9: bipush 8
      // 13bb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 13be: aload 0
      // 13bf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 13c2: aload 0
      // 13c3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 13c6: ldc2_w -150.0
      // 13c9: invokestatic java/lang/Math.toRadians (D)D
      // 13cc: d2f
      // 13cd: fconst_0
      // 13ce: fconst_0
      // 13cf: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 13d2: aload 0
      // 13d3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 13d6: aload 0
      // 13d7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 13da: fconst_0
      // 13db: ldc_w -26.0
      // 13de: ldc -9.0
      // 13e0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 13e3: aload 0
      // 13e4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 13e7: aload 0
      // 13e8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 13eb: ldc2_w -10.0
      // 13ee: invokestatic java/lang/Math.toRadians (D)D
      // 13f1: d2f
      // 13f2: fconst_0
      // 13f3: fconst_0
      // 13f4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 13f7: aload 0
      // 13f8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 13fb: aload 0
      // 13fc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 13ff: fconst_0
      // 1400: fconst_0
      // 1401: ldc2_w 55.0
      // 1404: invokestatic java/lang/Math.toRadians (D)D
      // 1407: d2f
      // 1408: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 140b: aload 0
      // 140c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 140f: aload 0
      // 1410: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1413: fconst_0
      // 1414: fconst_0
      // 1415: ldc2_w -62.5
      // 1418: invokestatic java/lang/Math.toRadians (D)D
      // 141b: d2f
      // 141c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 141f: aload 0
      // 1420: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1423: aload 0
      // 1424: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1427: fconst_0
      // 1428: fconst_0
      // 1429: ldc2_w 45.0
      // 142c: invokestatic java/lang/Math.toRadians (D)D
      // 142f: d2f
      // 1430: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1433: aload 0
      // 1434: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1437: aload 0
      // 1438: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 143b: fconst_0
      // 143c: fconst_0
      // 143d: ldc2_w -7.5
      // 1440: invokestatic java/lang/Math.toRadians (D)D
      // 1443: d2f
      // 1444: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1447: aload 0
      // 1448: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 144b: aload 0
      // 144c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 144f: fconst_0
      // 1450: fconst_0
      // 1451: ldc2_w 32.5
      // 1454: invokestatic java/lang/Math.toRadians (D)D
      // 1457: d2f
      // 1458: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 145b: aload 0
      // 145c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 145f: aload 0
      // 1460: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1463: fconst_0
      // 1464: fconst_0
      // 1465: ldc2_w -32.5
      // 1468: invokestatic java/lang/Math.toRadians (D)D
      // 146b: d2f
      // 146c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 146f: aload 0
      // 1470: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1473: aload 0
      // 1474: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1477: fconst_0
      // 1478: fconst_0
      // 1479: ldc2_w 30.0
      // 147c: invokestatic java/lang/Math.toRadians (D)D
      // 147f: d2f
      // 1480: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1483: aload 0
      // 1484: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1487: aload 0
      // 1488: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 148b: fconst_0
      // 148c: fconst_0
      // 148d: ldc2_w -30.0
      // 1490: invokestatic java/lang/Math.toRadians (D)D
      // 1493: d2f
      // 1494: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1497: aload 0
      // 1498: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 149b: aload 0
      // 149c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 149f: fconst_0
      // 14a0: fconst_0
      // 14a1: ldc2_w -20.0
      // 14a4: invokestatic java/lang/Math.toRadians (D)D
      // 14a7: d2f
      // 14a8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 14ab: aload 0
      // 14ac: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 14af: aload 0
      // 14b0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 14b3: ldc2_w -30.0
      // 14b6: invokestatic java/lang/Math.toRadians (D)D
      // 14b9: d2f
      // 14ba: ldc2_w 27.5
      // 14bd: invokestatic java/lang/Math.toRadians (D)D
      // 14c0: d2f
      // 14c1: ldc2_w 45.0
      // 14c4: invokestatic java/lang/Math.toRadians (D)D
      // 14c7: d2f
      // 14c8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 14cb: aload 0
      // 14cc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 14cf: aload 0
      // 14d0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 14d3: ldc2_w -25.0
      // 14d6: invokestatic java/lang/Math.toRadians (D)D
      // 14d9: d2f
      // 14da: fconst_0
      // 14db: fconst_0
      // 14dc: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 14df: aload 0
      // 14e0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 14e3: aload 0
      // 14e4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 14e7: ldc2_w 32.5
      // 14ea: invokestatic java/lang/Math.toRadians (D)D
      // 14ed: d2f
      // 14ee: fconst_0
      // 14ef: fconst_0
      // 14f0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 14f3: aload 0
      // 14f4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 14f7: aload 0
      // 14f8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 14fb: ldc2_w -32.5
      // 14fe: invokestatic java/lang/Math.toRadians (D)D
      // 1501: d2f
      // 1502: fconst_0
      // 1503: fconst_0
      // 1504: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1507: aload 0
      // 1508: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 150b: aload 0
      // 150c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 150f: fconst_0
      // 1510: ldc2_w -17.5
      // 1513: invokestatic java/lang/Math.toRadians (D)D
      // 1516: d2f
      // 1517: fconst_0
      // 1518: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 151b: aload 0
      // 151c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 151f: aload 0
      // 1520: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1523: ldc2_w -37.5
      // 1526: invokestatic java/lang/Math.toRadians (D)D
      // 1529: d2f
      // 152a: ldc2_w -47.5
      // 152d: invokestatic java/lang/Math.toRadians (D)D
      // 1530: d2f
      // 1531: ldc2_w -67.5
      // 1534: invokestatic java/lang/Math.toRadians (D)D
      // 1537: d2f
      // 1538: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 153b: aload 0
      // 153c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 153f: aload 0
      // 1540: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1543: ldc2_w 17.5
      // 1546: invokestatic java/lang/Math.toRadians (D)D
      // 1549: d2f
      // 154a: fconst_0
      // 154b: fconst_0
      // 154c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 154f: aload 0
      // 1550: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1553: aload 0
      // 1554: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1557: ldc2_w -22.5
      // 155a: invokestatic java/lang/Math.toRadians (D)D
      // 155d: d2f
      // 155e: fconst_0
      // 155f: fconst_0
      // 1560: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1563: aload 0
      // 1564: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1567: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 156a: aload 0
      // 156b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 156e: bipush 3
      // 156f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 1572: aload 0
      // 1573: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1576: aload 0
      // 1577: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 157a: ldc2_w -150.0
      // 157d: invokestatic java/lang/Math.toRadians (D)D
      // 1580: d2f
      // 1581: fconst_0
      // 1582: fconst_0
      // 1583: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1586: aload 0
      // 1587: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 158a: aload 0
      // 158b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 158e: fconst_0
      // 158f: ldc_w -26.0
      // 1592: ldc -9.0
      // 1594: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1597: aload 0
      // 1598: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 159b: aload 0
      // 159c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 159f: ldc2_w -2.5
      // 15a2: invokestatic java/lang/Math.toRadians (D)D
      // 15a5: d2f
      // 15a6: fconst_0
      // 15a7: fconst_0
      // 15a8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 15ab: aload 0
      // 15ac: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 15af: aload 0
      // 15b0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 15b3: fconst_0
      // 15b4: fconst_0
      // 15b5: ldc2_w 55.0
      // 15b8: invokestatic java/lang/Math.toRadians (D)D
      // 15bb: d2f
      // 15bc: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 15bf: aload 0
      // 15c0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 15c3: aload 0
      // 15c4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 15c7: fconst_0
      // 15c8: fconst_0
      // 15c9: ldc2_w -62.5
      // 15cc: invokestatic java/lang/Math.toRadians (D)D
      // 15cf: d2f
      // 15d0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 15d3: aload 0
      // 15d4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 15d7: aload 0
      // 15d8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 15db: fconst_0
      // 15dc: fconst_0
      // 15dd: ldc2_w 27.5
      // 15e0: invokestatic java/lang/Math.toRadians (D)D
      // 15e3: d2f
      // 15e4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 15e7: aload 0
      // 15e8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 15eb: aload 0
      // 15ec: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 15ef: fconst_0
      // 15f0: fconst_0
      // 15f1: ldc2_w 15.0
      // 15f4: invokestatic java/lang/Math.toRadians (D)D
      // 15f7: d2f
      // 15f8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 15fb: aload 0
      // 15fc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 15ff: aload 0
      // 1600: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1603: fconst_0
      // 1604: fconst_0
      // 1605: ldc2_w 15.0
      // 1608: invokestatic java/lang/Math.toRadians (D)D
      // 160b: d2f
      // 160c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 160f: aload 0
      // 1610: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1613: aload 0
      // 1614: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1617: fconst_0
      // 1618: fconst_0
      // 1619: ldc2_w -22.5
      // 161c: invokestatic java/lang/Math.toRadians (D)D
      // 161f: d2f
      // 1620: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1623: aload 0
      // 1624: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1627: aload 0
      // 1628: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 162b: fconst_0
      // 162c: fconst_0
      // 162d: ldc2_w 17.5
      // 1630: invokestatic java/lang/Math.toRadians (D)D
      // 1633: d2f
      // 1634: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1637: aload 0
      // 1638: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 163b: aload 0
      // 163c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 163f: fconst_0
      // 1640: fconst_0
      // 1641: ldc2_w -22.5
      // 1644: invokestatic java/lang/Math.toRadians (D)D
      // 1647: d2f
      // 1648: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 164b: aload 0
      // 164c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 164f: aload 0
      // 1650: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1653: fconst_0
      // 1654: ldc2_w 2.5
      // 1657: invokestatic java/lang/Math.toRadians (D)D
      // 165a: d2f
      // 165b: ldc2_w -7.5
      // 165e: invokestatic java/lang/Math.toRadians (D)D
      // 1661: d2f
      // 1662: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1665: aload 0
      // 1666: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1669: aload 0
      // 166a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 166d: ldc2_w -22.5
      // 1670: invokestatic java/lang/Math.toRadians (D)D
      // 1673: d2f
      // 1674: ldc2_w 15.0
      // 1677: invokestatic java/lang/Math.toRadians (D)D
      // 167a: d2f
      // 167b: ldc2_w 35.0
      // 167e: invokestatic java/lang/Math.toRadians (D)D
      // 1681: d2f
      // 1682: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1685: aload 0
      // 1686: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1689: aload 0
      // 168a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 168d: ldc2_w -17.5
      // 1690: invokestatic java/lang/Math.toRadians (D)D
      // 1693: d2f
      // 1694: fconst_0
      // 1695: fconst_0
      // 1696: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1699: aload 0
      // 169a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 169d: aload 0
      // 169e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 16a1: ldc2_w 17.5
      // 16a4: invokestatic java/lang/Math.toRadians (D)D
      // 16a7: d2f
      // 16a8: fconst_0
      // 16a9: fconst_0
      // 16aa: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 16ad: aload 0
      // 16ae: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 16b1: aload 0
      // 16b2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 16b5: ldc2_w -25.0
      // 16b8: invokestatic java/lang/Math.toRadians (D)D
      // 16bb: d2f
      // 16bc: fconst_0
      // 16bd: fconst_0
      // 16be: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 16c1: aload 0
      // 16c2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 16c5: aload 0
      // 16c6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 16c9: fconst_0
      // 16ca: ldc2_w -7.5
      // 16cd: invokestatic java/lang/Math.toRadians (D)D
      // 16d0: d2f
      // 16d1: fconst_0
      // 16d2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 16d5: aload 0
      // 16d6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 16d9: aload 0
      // 16da: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 16dd: ldc2_w -37.5
      // 16e0: invokestatic java/lang/Math.toRadians (D)D
      // 16e3: d2f
      // 16e4: ldc2_w -47.5
      // 16e7: invokestatic java/lang/Math.toRadians (D)D
      // 16ea: d2f
      // 16eb: ldc2_w -67.5
      // 16ee: invokestatic java/lang/Math.toRadians (D)D
      // 16f1: d2f
      // 16f2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 16f5: aload 0
      // 16f6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 16f9: aload 0
      // 16fa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 16fd: ldc2_w 17.5
      // 1700: invokestatic java/lang/Math.toRadians (D)D
      // 1703: d2f
      // 1704: fconst_0
      // 1705: fconst_0
      // 1706: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1709: aload 0
      // 170a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 170d: aload 0
      // 170e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1711: ldc2_w -25.0
      // 1714: invokestatic java/lang/Math.toRadians (D)D
      // 1717: d2f
      // 1718: fconst_0
      // 1719: fconst_0
      // 171a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 171d: aload 0
      // 171e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1721: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 1724: aload 0
      // 1725: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1728: bipush 3
      // 1729: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 172c: aload 0
      // 172d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1730: aload 0
      // 1731: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1734: ldc2_w -150.0
      // 1737: invokestatic java/lang/Math.toRadians (D)D
      // 173a: d2f
      // 173b: ldc2_w 2.5
      // 173e: invokestatic java/lang/Math.toRadians (D)D
      // 1741: d2f
      // 1742: ldc2_w 1.25
      // 1745: invokestatic java/lang/Math.toRadians (D)D
      // 1748: d2f
      // 1749: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 174c: aload 0
      // 174d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1750: aload 0
      // 1751: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1754: fconst_0
      // 1755: ldc_w -25.0
      // 1758: ldc -9.0
      // 175a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 175d: aload 0
      // 175e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1761: aload 0
      // 1762: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1765: ldc2_w -2.5
      // 1768: invokestatic java/lang/Math.toRadians (D)D
      // 176b: d2f
      // 176c: fconst_0
      // 176d: fconst_0
      // 176e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1771: aload 0
      // 1772: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1775: aload 0
      // 1776: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1779: fconst_0
      // 177a: fconst_0
      // 177b: ldc2_w 55.0
      // 177e: invokestatic java/lang/Math.toRadians (D)D
      // 1781: d2f
      // 1782: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1785: aload 0
      // 1786: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1789: aload 0
      // 178a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 178d: fconst_0
      // 178e: fconst_0
      // 178f: ldc2_w -62.5
      // 1792: invokestatic java/lang/Math.toRadians (D)D
      // 1795: d2f
      // 1796: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1799: aload 0
      // 179a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 179d: aload 0
      // 179e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 17a1: fconst_0
      // 17a2: fconst_0
      // 17a3: ldc2_w 62.5
      // 17a6: invokestatic java/lang/Math.toRadians (D)D
      // 17a9: d2f
      // 17aa: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 17ad: aload 0
      // 17ae: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 17b1: aload 0
      // 17b2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 17b5: fconst_0
      // 17b6: fconst_0
      // 17b7: ldc2_w -32.5
      // 17ba: invokestatic java/lang/Math.toRadians (D)D
      // 17bd: d2f
      // 17be: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 17c1: aload 0
      // 17c2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 17c5: aload 0
      // 17c6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 17c9: fconst_0
      // 17ca: fconst_0
      // 17cb: ldc2_w 47.5
      // 17ce: invokestatic java/lang/Math.toRadians (D)D
      // 17d1: d2f
      // 17d2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 17d5: aload 0
      // 17d6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 17d9: aload 0
      // 17da: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 17dd: fconst_0
      // 17de: fconst_0
      // 17df: ldc2_w -42.5
      // 17e2: invokestatic java/lang/Math.toRadians (D)D
      // 17e5: d2f
      // 17e6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 17e9: aload 0
      // 17ea: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 17ed: aload 0
      // 17ee: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 17f1: fconst_0
      // 17f2: fconst_0
      // 17f3: ldc2_w 42.5
      // 17f6: invokestatic java/lang/Math.toRadians (D)D
      // 17f9: d2f
      // 17fa: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 17fd: aload 0
      // 17fe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1801: aload 0
      // 1802: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1805: fconst_0
      // 1806: fconst_0
      // 1807: ldc2_w -37.5
      // 180a: invokestatic java/lang/Math.toRadians (D)D
      // 180d: d2f
      // 180e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1811: aload 0
      // 1812: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1815: aload 0
      // 1816: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1819: fconst_0
      // 181a: ldc2_w 7.5
      // 181d: invokestatic java/lang/Math.toRadians (D)D
      // 1820: d2f
      // 1821: ldc2_w 15.0
      // 1824: invokestatic java/lang/Math.toRadians (D)D
      // 1827: d2f
      // 1828: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 182b: aload 0
      // 182c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 182f: aload 0
      // 1830: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1833: ldc2_w -7.5
      // 1836: invokestatic java/lang/Math.toRadians (D)D
      // 1839: d2f
      // 183a: ldc2_w -12.5
      // 183d: invokestatic java/lang/Math.toRadians (D)D
      // 1840: d2f
      // 1841: ldc2_w 10.0
      // 1844: invokestatic java/lang/Math.toRadians (D)D
      // 1847: d2f
      // 1848: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 184b: aload 0
      // 184c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 184f: aload 0
      // 1850: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1853: ldc2_w -10.0
      // 1856: invokestatic java/lang/Math.toRadians (D)D
      // 1859: d2f
      // 185a: fconst_0
      // 185b: fconst_0
      // 185c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 185f: aload 0
      // 1860: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1863: aload 0
      // 1864: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1867: ldc2_w -7.5
      // 186a: invokestatic java/lang/Math.toRadians (D)D
      // 186d: d2f
      // 186e: fconst_0
      // 186f: fconst_0
      // 1870: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1873: aload 0
      // 1874: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1877: aload 0
      // 1878: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 187b: fconst_0
      // 187c: ldc2_w 10.0
      // 187f: invokestatic java/lang/Math.toRadians (D)D
      // 1882: d2f
      // 1883: fconst_0
      // 1884: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1887: aload 0
      // 1888: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 188b: aload 0
      // 188c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 188f: ldc2_w -37.5
      // 1892: invokestatic java/lang/Math.toRadians (D)D
      // 1895: d2f
      // 1896: ldc2_w -47.5
      // 1899: invokestatic java/lang/Math.toRadians (D)D
      // 189c: d2f
      // 189d: ldc2_w -67.5
      // 18a0: invokestatic java/lang/Math.toRadians (D)D
      // 18a3: d2f
      // 18a4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 18a7: aload 0
      // 18a8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 18ab: aload 0
      // 18ac: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 18af: ldc2_w 17.5
      // 18b2: invokestatic java/lang/Math.toRadians (D)D
      // 18b5: d2f
      // 18b6: fconst_0
      // 18b7: fconst_0
      // 18b8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 18bb: aload 0
      // 18bc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 18bf: aload 0
      // 18c0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 18c3: ldc2_w -32.5
      // 18c6: invokestatic java/lang/Math.toRadians (D)D
      // 18c9: d2f
      // 18ca: fconst_0
      // 18cb: fconst_0
      // 18cc: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 18cf: aload 0
      // 18d0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 18d3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 18d6: aload 0
      // 18d7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 18da: bipush 3
      // 18db: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 18de: aload 0
      // 18df: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 18e2: aload 0
      // 18e3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 18e6: ldc2_w -150.0
      // 18e9: invokestatic java/lang/Math.toRadians (D)D
      // 18ec: d2f
      // 18ed: ldc2_w 2.5
      // 18f0: invokestatic java/lang/Math.toRadians (D)D
      // 18f3: d2f
      // 18f4: ldc2_w 1.25
      // 18f7: invokestatic java/lang/Math.toRadians (D)D
      // 18fa: d2f
      // 18fb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 18fe: aload 0
      // 18ff: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1902: aload 0
      // 1903: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1906: fconst_0
      // 1907: ldc_w -25.0
      // 190a: ldc -9.0
      // 190c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 190f: aload 0
      // 1910: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1913: aload 0
      // 1914: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1917: fconst_0
      // 1918: fconst_0
      // 1919: ldc2_w 55.0
      // 191c: invokestatic java/lang/Math.toRadians (D)D
      // 191f: d2f
      // 1920: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1923: aload 0
      // 1924: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1927: aload 0
      // 1928: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 192b: fconst_0
      // 192c: fconst_0
      // 192d: ldc2_w -62.5
      // 1930: invokestatic java/lang/Math.toRadians (D)D
      // 1933: d2f
      // 1934: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1937: aload 0
      // 1938: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 193b: aload 0
      // 193c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 193f: fconst_0
      // 1940: fconst_0
      // 1941: ldc2_w 62.5
      // 1944: invokestatic java/lang/Math.toRadians (D)D
      // 1947: d2f
      // 1948: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 194b: aload 0
      // 194c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 194f: aload 0
      // 1950: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1953: fconst_0
      // 1954: fconst_0
      // 1955: ldc2_w -32.5
      // 1958: invokestatic java/lang/Math.toRadians (D)D
      // 195b: d2f
      // 195c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 195f: aload 0
      // 1960: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1963: aload 0
      // 1964: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1967: fconst_0
      // 1968: fconst_0
      // 1969: ldc2_w 47.5
      // 196c: invokestatic java/lang/Math.toRadians (D)D
      // 196f: d2f
      // 1970: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1973: aload 0
      // 1974: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1977: aload 0
      // 1978: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 197b: fconst_0
      // 197c: fconst_0
      // 197d: ldc2_w -42.5
      // 1980: invokestatic java/lang/Math.toRadians (D)D
      // 1983: d2f
      // 1984: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1987: aload 0
      // 1988: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 198b: aload 0
      // 198c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 198f: fconst_0
      // 1990: fconst_0
      // 1991: ldc2_w 42.5
      // 1994: invokestatic java/lang/Math.toRadians (D)D
      // 1997: d2f
      // 1998: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 199b: aload 0
      // 199c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 199f: aload 0
      // 19a0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 19a3: fconst_0
      // 19a4: fconst_0
      // 19a5: ldc2_w -37.5
      // 19a8: invokestatic java/lang/Math.toRadians (D)D
      // 19ab: d2f
      // 19ac: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 19af: aload 0
      // 19b0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 19b3: aload 0
      // 19b4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 19b7: fconst_0
      // 19b8: ldc2_w 2.5
      // 19bb: invokestatic java/lang/Math.toRadians (D)D
      // 19be: d2f
      // 19bf: ldc2_w -7.5
      // 19c2: invokestatic java/lang/Math.toRadians (D)D
      // 19c5: d2f
      // 19c6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 19c9: aload 0
      // 19ca: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 19cd: aload 0
      // 19ce: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 19d1: ldc2_w -22.5
      // 19d4: invokestatic java/lang/Math.toRadians (D)D
      // 19d7: d2f
      // 19d8: ldc2_w 15.0
      // 19db: invokestatic java/lang/Math.toRadians (D)D
      // 19de: d2f
      // 19df: ldc2_w 35.0
      // 19e2: invokestatic java/lang/Math.toRadians (D)D
      // 19e5: d2f
      // 19e6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 19e9: aload 0
      // 19ea: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 19ed: aload 0
      // 19ee: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 19f1: ldc2_w -17.5
      // 19f4: invokestatic java/lang/Math.toRadians (D)D
      // 19f7: d2f
      // 19f8: fconst_0
      // 19f9: fconst_0
      // 19fa: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 19fd: aload 0
      // 19fe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1a01: aload 0
      // 1a02: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1a05: ldc2_w 17.5
      // 1a08: invokestatic java/lang/Math.toRadians (D)D
      // 1a0b: d2f
      // 1a0c: fconst_0
      // 1a0d: fconst_0
      // 1a0e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1a11: aload 0
      // 1a12: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1a15: aload 0
      // 1a16: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1a19: ldc2_w -25.0
      // 1a1c: invokestatic java/lang/Math.toRadians (D)D
      // 1a1f: d2f
      // 1a20: fconst_0
      // 1a21: fconst_0
      // 1a22: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1a25: aload 0
      // 1a26: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1a29: aload 0
      // 1a2a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1a2d: fconst_0
      // 1a2e: ldc2_w 10.0
      // 1a31: invokestatic java/lang/Math.toRadians (D)D
      // 1a34: d2f
      // 1a35: fconst_0
      // 1a36: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1a39: aload 0
      // 1a3a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1a3d: aload 0
      // 1a3e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1a41: ldc2_w -37.5
      // 1a44: invokestatic java/lang/Math.toRadians (D)D
      // 1a47: d2f
      // 1a48: ldc2_w -47.5
      // 1a4b: invokestatic java/lang/Math.toRadians (D)D
      // 1a4e: d2f
      // 1a4f: ldc2_w -67.5
      // 1a52: invokestatic java/lang/Math.toRadians (D)D
      // 1a55: d2f
      // 1a56: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1a59: aload 0
      // 1a5a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1a5d: aload 0
      // 1a5e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1a61: ldc2_w 17.5
      // 1a64: invokestatic java/lang/Math.toRadians (D)D
      // 1a67: d2f
      // 1a68: fconst_0
      // 1a69: fconst_0
      // 1a6a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1a6d: aload 0
      // 1a6e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1a71: aload 0
      // 1a72: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1a75: ldc2_w -5.0
      // 1a78: invokestatic java/lang/Math.toRadians (D)D
      // 1a7b: d2f
      // 1a7c: fconst_0
      // 1a7d: fconst_0
      // 1a7e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1a81: aload 0
      // 1a82: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1a85: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 1a88: aload 0
      // 1a89: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1a8c: bipush 3
      // 1a8d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 1a90: aload 0
      // 1a91: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1a94: aload 0
      // 1a95: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1a98: ldc2_w -150.0
      // 1a9b: invokestatic java/lang/Math.toRadians (D)D
      // 1a9e: d2f
      // 1a9f: ldc2_w 2.5
      // 1aa2: invokestatic java/lang/Math.toRadians (D)D
      // 1aa5: d2f
      // 1aa6: ldc2_w 1.25
      // 1aa9: invokestatic java/lang/Math.toRadians (D)D
      // 1aac: d2f
      // 1aad: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1ab0: aload 0
      // 1ab1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1ab4: aload 0
      // 1ab5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ab8: fconst_0
      // 1ab9: ldc_w -25.0
      // 1abc: ldc -9.0
      // 1abe: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1ac1: aload 0
      // 1ac2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1ac5: aload 0
      // 1ac6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ac9: fconst_0
      // 1aca: fconst_0
      // 1acb: ldc2_w 55.0
      // 1ace: invokestatic java/lang/Math.toRadians (D)D
      // 1ad1: d2f
      // 1ad2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1ad5: aload 0
      // 1ad6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1ad9: aload 0
      // 1ada: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1add: fconst_0
      // 1ade: fconst_0
      // 1adf: ldc2_w -62.5
      // 1ae2: invokestatic java/lang/Math.toRadians (D)D
      // 1ae5: d2f
      // 1ae6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1ae9: aload 0
      // 1aea: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1aed: aload 0
      // 1aee: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1af1: fconst_0
      // 1af2: fconst_0
      // 1af3: ldc2_w 27.5
      // 1af6: invokestatic java/lang/Math.toRadians (D)D
      // 1af9: d2f
      // 1afa: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1afd: aload 0
      // 1afe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1b01: aload 0
      // 1b02: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1b05: fconst_0
      // 1b06: fconst_0
      // 1b07: ldc2_w 15.0
      // 1b0a: invokestatic java/lang/Math.toRadians (D)D
      // 1b0d: d2f
      // 1b0e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1b11: aload 0
      // 1b12: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1b15: aload 0
      // 1b16: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1b19: fconst_0
      // 1b1a: fconst_0
      // 1b1b: ldc2_w 15.0
      // 1b1e: invokestatic java/lang/Math.toRadians (D)D
      // 1b21: d2f
      // 1b22: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1b25: aload 0
      // 1b26: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1b29: aload 0
      // 1b2a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1b2d: fconst_0
      // 1b2e: fconst_0
      // 1b2f: ldc2_w -22.5
      // 1b32: invokestatic java/lang/Math.toRadians (D)D
      // 1b35: d2f
      // 1b36: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1b39: aload 0
      // 1b3a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1b3d: aload 0
      // 1b3e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1b41: fconst_0
      // 1b42: fconst_0
      // 1b43: ldc2_w 17.5
      // 1b46: invokestatic java/lang/Math.toRadians (D)D
      // 1b49: d2f
      // 1b4a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1b4d: aload 0
      // 1b4e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1b51: aload 0
      // 1b52: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1b55: fconst_0
      // 1b56: fconst_0
      // 1b57: ldc2_w -22.5
      // 1b5a: invokestatic java/lang/Math.toRadians (D)D
      // 1b5d: d2f
      // 1b5e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1b61: aload 0
      // 1b62: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1b65: aload 0
      // 1b66: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1b69: fconst_0
      // 1b6a: ldc2_w 7.5
      // 1b6d: invokestatic java/lang/Math.toRadians (D)D
      // 1b70: d2f
      // 1b71: ldc2_w 15.0
      // 1b74: invokestatic java/lang/Math.toRadians (D)D
      // 1b77: d2f
      // 1b78: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1b7b: aload 0
      // 1b7c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1b7f: aload 0
      // 1b80: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1b83: ldc2_w -7.5
      // 1b86: invokestatic java/lang/Math.toRadians (D)D
      // 1b89: d2f
      // 1b8a: ldc2_w -12.5
      // 1b8d: invokestatic java/lang/Math.toRadians (D)D
      // 1b90: d2f
      // 1b91: ldc2_w 10.0
      // 1b94: invokestatic java/lang/Math.toRadians (D)D
      // 1b97: d2f
      // 1b98: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1b9b: aload 0
      // 1b9c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1b9f: aload 0
      // 1ba0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ba3: ldc2_w -10.0
      // 1ba6: invokestatic java/lang/Math.toRadians (D)D
      // 1ba9: d2f
      // 1baa: fconst_0
      // 1bab: fconst_0
      // 1bac: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1baf: aload 0
      // 1bb0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1bb3: aload 0
      // 1bb4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1bb7: ldc2_w -7.5
      // 1bba: invokestatic java/lang/Math.toRadians (D)D
      // 1bbd: d2f
      // 1bbe: fconst_0
      // 1bbf: fconst_0
      // 1bc0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1bc3: aload 0
      // 1bc4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1bc7: aload 0
      // 1bc8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1bcb: fconst_0
      // 1bcc: ldc2_w -17.5
      // 1bcf: invokestatic java/lang/Math.toRadians (D)D
      // 1bd2: d2f
      // 1bd3: fconst_0
      // 1bd4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1bd7: aload 0
      // 1bd8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1bdb: aload 0
      // 1bdc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1bdf: ldc2_w -37.5
      // 1be2: invokestatic java/lang/Math.toRadians (D)D
      // 1be5: d2f
      // 1be6: ldc2_w -47.5
      // 1be9: invokestatic java/lang/Math.toRadians (D)D
      // 1bec: d2f
      // 1bed: ldc2_w -67.5
      // 1bf0: invokestatic java/lang/Math.toRadians (D)D
      // 1bf3: d2f
      // 1bf4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1bf7: aload 0
      // 1bf8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1bfb: aload 0
      // 1bfc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1bff: ldc2_w 17.5
      // 1c02: invokestatic java/lang/Math.toRadians (D)D
      // 1c05: d2f
      // 1c06: fconst_0
      // 1c07: fconst_0
      // 1c08: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1c0b: aload 0
      // 1c0c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1c0f: aload 0
      // 1c10: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1c13: ldc2_w -32.5
      // 1c16: invokestatic java/lang/Math.toRadians (D)D
      // 1c19: d2f
      // 1c1a: fconst_0
      // 1c1b: fconst_0
      // 1c1c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1c1f: aload 0
      // 1c20: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1c23: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 1c26: aload 0
      // 1c27: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1c2a: bipush 3
      // 1c2b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 1c2e: aload 0
      // 1c2f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1c32: aload 0
      // 1c33: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1c36: ldc2_w -150.0
      // 1c39: invokestatic java/lang/Math.toRadians (D)D
      // 1c3c: d2f
      // 1c3d: ldc2_w 2.5
      // 1c40: invokestatic java/lang/Math.toRadians (D)D
      // 1c43: d2f
      // 1c44: ldc2_w 1.25
      // 1c47: invokestatic java/lang/Math.toRadians (D)D
      // 1c4a: d2f
      // 1c4b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1c4e: aload 0
      // 1c4f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1c52: aload 0
      // 1c53: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1c56: fconst_0
      // 1c57: ldc_w -25.0
      // 1c5a: ldc -9.0
      // 1c5c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1c5f: aload 0
      // 1c60: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1c63: aload 0
      // 1c64: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1c67: fconst_0
      // 1c68: fconst_0
      // 1c69: ldc2_w 55.0
      // 1c6c: invokestatic java/lang/Math.toRadians (D)D
      // 1c6f: d2f
      // 1c70: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1c73: aload 0
      // 1c74: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1c77: aload 0
      // 1c78: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1c7b: fconst_0
      // 1c7c: fconst_0
      // 1c7d: ldc2_w -62.5
      // 1c80: invokestatic java/lang/Math.toRadians (D)D
      // 1c83: d2f
      // 1c84: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1c87: aload 0
      // 1c88: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1c8b: aload 0
      // 1c8c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1c8f: fconst_0
      // 1c90: fconst_0
      // 1c91: ldc2_w 50.0
      // 1c94: invokestatic java/lang/Math.toRadians (D)D
      // 1c97: d2f
      // 1c98: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1c9b: aload 0
      // 1c9c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1c9f: aload 0
      // 1ca0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ca3: fconst_0
      // 1ca4: fconst_0
      // 1ca5: ldc2_w -2.5
      // 1ca8: invokestatic java/lang/Math.toRadians (D)D
      // 1cab: d2f
      // 1cac: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1caf: aload 0
      // 1cb0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1cb3: aload 0
      // 1cb4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1cb7: fconst_0
      // 1cb8: fconst_0
      // 1cb9: ldc2_w 47.5
      // 1cbc: invokestatic java/lang/Math.toRadians (D)D
      // 1cbf: d2f
      // 1cc0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1cc3: aload 0
      // 1cc4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1cc7: aload 0
      // 1cc8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ccb: fconst_0
      // 1ccc: fconst_0
      // 1ccd: ldc2_w -45.0
      // 1cd0: invokestatic java/lang/Math.toRadians (D)D
      // 1cd3: d2f
      // 1cd4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1cd7: aload 0
      // 1cd8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1cdb: aload 0
      // 1cdc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1cdf: fconst_0
      // 1ce0: fconst_0
      // 1ce1: ldc2_w 42.5
      // 1ce4: invokestatic java/lang/Math.toRadians (D)D
      // 1ce7: d2f
      // 1ce8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1ceb: aload 0
      // 1cec: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1cef: aload 0
      // 1cf0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1cf3: fconst_0
      // 1cf4: fconst_0
      // 1cf5: ldc2_w -42.5
      // 1cf8: invokestatic java/lang/Math.toRadians (D)D
      // 1cfb: d2f
      // 1cfc: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1cff: aload 0
      // 1d00: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1d03: aload 0
      // 1d04: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1d07: fconst_0
      // 1d08: fconst_0
      // 1d09: ldc2_w -20.0
      // 1d0c: invokestatic java/lang/Math.toRadians (D)D
      // 1d0f: d2f
      // 1d10: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1d13: aload 0
      // 1d14: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1d17: aload 0
      // 1d18: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1d1b: ldc2_w -30.0
      // 1d1e: invokestatic java/lang/Math.toRadians (D)D
      // 1d21: d2f
      // 1d22: ldc2_w 27.5
      // 1d25: invokestatic java/lang/Math.toRadians (D)D
      // 1d28: d2f
      // 1d29: ldc2_w 45.0
      // 1d2c: invokestatic java/lang/Math.toRadians (D)D
      // 1d2f: d2f
      // 1d30: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1d33: aload 0
      // 1d34: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1d37: aload 0
      // 1d38: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1d3b: ldc2_w -25.0
      // 1d3e: invokestatic java/lang/Math.toRadians (D)D
      // 1d41: d2f
      // 1d42: fconst_0
      // 1d43: fconst_0
      // 1d44: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1d47: aload 0
      // 1d48: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1d4b: aload 0
      // 1d4c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1d4f: ldc2_w 7.5
      // 1d52: invokestatic java/lang/Math.toRadians (D)D
      // 1d55: d2f
      // 1d56: ldc2_w 57.5
      // 1d59: invokestatic java/lang/Math.toRadians (D)D
      // 1d5c: d2f
      // 1d5d: ldc2_w -27.5
      // 1d60: invokestatic java/lang/Math.toRadians (D)D
      // 1d63: d2f
      // 1d64: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1d67: aload 0
      // 1d68: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1d6b: aload 0
      // 1d6c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1d6f: ldc2_w 32.5
      // 1d72: invokestatic java/lang/Math.toRadians (D)D
      // 1d75: d2f
      // 1d76: fconst_0
      // 1d77: fconst_0
      // 1d78: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1d7b: aload 0
      // 1d7c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1d7f: aload 0
      // 1d80: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1d83: ldc2_w -32.5
      // 1d86: invokestatic java/lang/Math.toRadians (D)D
      // 1d89: d2f
      // 1d8a: fconst_0
      // 1d8b: fconst_0
      // 1d8c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1d8f: aload 0
      // 1d90: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1d93: aload 0
      // 1d94: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1d97: fconst_0
      // 1d98: ldc2_w 5.0
      // 1d9b: invokestatic java/lang/Math.toRadians (D)D
      // 1d9e: d2f
      // 1d9f: fconst_0
      // 1da0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1da3: aload 0
      // 1da4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1da7: aload 0
      // 1da8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1dab: ldc2_w -37.5
      // 1dae: invokestatic java/lang/Math.toRadians (D)D
      // 1db1: d2f
      // 1db2: ldc2_w -47.5
      // 1db5: invokestatic java/lang/Math.toRadians (D)D
      // 1db8: d2f
      // 1db9: ldc2_w -67.5
      // 1dbc: invokestatic java/lang/Math.toRadians (D)D
      // 1dbf: d2f
      // 1dc0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1dc3: aload 0
      // 1dc4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1dc7: aload 0
      // 1dc8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1dcb: ldc2_w 17.5
      // 1dce: invokestatic java/lang/Math.toRadians (D)D
      // 1dd1: d2f
      // 1dd2: fconst_0
      // 1dd3: fconst_0
      // 1dd4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1dd7: aload 0
      // 1dd8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1ddb: aload 0
      // 1ddc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ddf: ldc2_w 10.0
      // 1de2: invokestatic java/lang/Math.toRadians (D)D
      // 1de5: d2f
      // 1de6: fconst_0
      // 1de7: fconst_0
      // 1de8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1deb: aload 0
      // 1dec: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1def: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 1df2: aload 0
      // 1df3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1df6: bipush 15
      // 1df8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 1dfb: aload 0
      // 1dfc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1dff: aload 0
      // 1e00: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1e03: ldc2_w -150.0
      // 1e06: invokestatic java/lang/Math.toRadians (D)D
      // 1e09: d2f
      // 1e0a: fconst_0
      // 1e0b: fconst_0
      // 1e0c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1e0f: aload 0
      // 1e10: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1e13: aload 0
      // 1e14: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1e17: fconst_0
      // 1e18: ldc_w -25.0
      // 1e1b: ldc -9.0
      // 1e1d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1e20: aload 0
      // 1e21: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1e24: aload 0
      // 1e25: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1e28: fconst_0
      // 1e29: fconst_0
      // 1e2a: ldc2_w 55.0
      // 1e2d: invokestatic java/lang/Math.toRadians (D)D
      // 1e30: d2f
      // 1e31: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1e34: aload 0
      // 1e35: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1e38: aload 0
      // 1e39: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1e3c: fconst_0
      // 1e3d: fconst_0
      // 1e3e: ldc2_w -62.5
      // 1e41: invokestatic java/lang/Math.toRadians (D)D
      // 1e44: d2f
      // 1e45: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1e48: aload 0
      // 1e49: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1e4c: aload 0
      // 1e4d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1e50: ldc2_w -25.0
      // 1e53: invokestatic java/lang/Math.toRadians (D)D
      // 1e56: d2f
      // 1e57: ldc2_w -30.0
      // 1e5a: invokestatic java/lang/Math.toRadians (D)D
      // 1e5d: d2f
      // 1e5e: ldc2_w -30.0
      // 1e61: invokestatic java/lang/Math.toRadians (D)D
      // 1e64: d2f
      // 1e65: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1e68: aload 0
      // 1e69: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1e6c: aload 0
      // 1e6d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1e70: fconst_0
      // 1e71: fconst_0
      // 1e72: ldc2_w -60.0
      // 1e75: invokestatic java/lang/Math.toRadians (D)D
      // 1e78: d2f
      // 1e79: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1e7c: aload 0
      // 1e7d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1e80: aload 0
      // 1e81: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1e84: ldc2_w -25.0
      // 1e87: invokestatic java/lang/Math.toRadians (D)D
      // 1e8a: d2f
      // 1e8b: ldc2_w 17.5
      // 1e8e: invokestatic java/lang/Math.toRadians (D)D
      // 1e91: d2f
      // 1e92: ldc2_w 35.0
      // 1e95: invokestatic java/lang/Math.toRadians (D)D
      // 1e98: d2f
      // 1e99: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1e9c: aload 0
      // 1e9d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1ea0: aload 0
      // 1ea1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ea4: fconst_0
      // 1ea5: fconst_0
      // 1ea6: ldc2_w 77.5
      // 1ea9: invokestatic java/lang/Math.toRadians (D)D
      // 1eac: d2f
      // 1ead: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1eb0: aload 0
      // 1eb1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1eb4: aload 0
      // 1eb5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1eb8: ldc2_w -12.5
      // 1ebb: invokestatic java/lang/Math.toRadians (D)D
      // 1ebe: d2f
      // 1ebf: fconst_0
      // 1ec0: ldc2_w -12.5
      // 1ec3: invokestatic java/lang/Math.toRadians (D)D
      // 1ec6: d2f
      // 1ec7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1eca: aload 0
      // 1ecb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1ece: aload 0
      // 1ecf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ed2: fconst_0
      // 1ed3: fconst_0
      // 1ed4: ldc2_w -65.0
      // 1ed7: invokestatic java/lang/Math.toRadians (D)D
      // 1eda: d2f
      // 1edb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1ede: aload 0
      // 1edf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1ee2: aload 0
      // 1ee3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ee6: fconst_0
      // 1ee7: fconst_0
      // 1ee8: ldc2_w 27.5
      // 1eeb: invokestatic java/lang/Math.toRadians (D)D
      // 1eee: d2f
      // 1eef: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1ef2: aload 0
      // 1ef3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1ef6: aload 0
      // 1ef7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1efa: fconst_0
      // 1efb: fconst_0
      // 1efc: ldc2_w 27.5
      // 1eff: invokestatic java/lang/Math.toRadians (D)D
      // 1f02: d2f
      // 1f03: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1f06: aload 0
      // 1f07: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1f0a: aload 0
      // 1f0b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1f0e: ldc2_w -22.5
      // 1f11: invokestatic java/lang/Math.toRadians (D)D
      // 1f14: d2f
      // 1f15: ldc2_w -10.0
      // 1f18: invokestatic java/lang/Math.toRadians (D)D
      // 1f1b: d2f
      // 1f1c: ldc2_w 7.5
      // 1f1f: invokestatic java/lang/Math.toRadians (D)D
      // 1f22: d2f
      // 1f23: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1f26: aload 0
      // 1f27: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1f2a: aload 0
      // 1f2b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1f2e: fconst_0
      // 1f2f: fconst_0
      // 1f30: ldc2_w -50.0
      // 1f33: invokestatic java/lang/Math.toRadians (D)D
      // 1f36: d2f
      // 1f37: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1f3a: aload 0
      // 1f3b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1f3e: aload 0
      // 1f3f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1f42: fconst_0
      // 1f43: ldc2_w 10.0
      // 1f46: invokestatic java/lang/Math.toRadians (D)D
      // 1f49: d2f
      // 1f4a: ldc2_w -10.0
      // 1f4d: invokestatic java/lang/Math.toRadians (D)D
      // 1f50: d2f
      // 1f51: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1f54: aload 0
      // 1f55: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1f58: aload 0
      // 1f59: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1f5c: fconst_0
      // 1f5d: fconst_0
      // 1f5e: ldc2_w 40.0
      // 1f61: invokestatic java/lang/Math.toRadians (D)D
      // 1f64: d2f
      // 1f65: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1f68: aload 0
      // 1f69: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1f6c: aload 0
      // 1f6d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1f70: ldc2_w 2.5
      // 1f73: invokestatic java/lang/Math.toRadians (D)D
      // 1f76: d2f
      // 1f77: ldc2_w 10.0
      // 1f7a: invokestatic java/lang/Math.toRadians (D)D
      // 1f7d: d2f
      // 1f7e: ldc2_w 17.5
      // 1f81: invokestatic java/lang/Math.toRadians (D)D
      // 1f84: d2f
      // 1f85: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1f88: aload 0
      // 1f89: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1f8c: aload 0
      // 1f8d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1f90: ldc2_w 2.5
      // 1f93: invokestatic java/lang/Math.toRadians (D)D
      // 1f96: d2f
      // 1f97: ldc2_w -17.5
      // 1f9a: invokestatic java/lang/Math.toRadians (D)D
      // 1f9d: d2f
      // 1f9e: ldc2_w -2.5
      // 1fa1: invokestatic java/lang/Math.toRadians (D)D
      // 1fa4: d2f
      // 1fa5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1fa8: aload 0
      // 1fa9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1fac: aload 0
      // 1fad: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1fb0: ldc2_w -80.0
      // 1fb3: invokestatic java/lang/Math.toRadians (D)D
      // 1fb6: d2f
      // 1fb7: ldc2_w 2.5
      // 1fba: invokestatic java/lang/Math.toRadians (D)D
      // 1fbd: d2f
      // 1fbe: ldc2_w 2.5
      // 1fc1: invokestatic java/lang/Math.toRadians (D)D
      // 1fc4: d2f
      // 1fc5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1fc8: aload 0
      // 1fc9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1fcc: aload 0
      // 1fcd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1fd0: ldc2_w 17.5
      // 1fd3: invokestatic java/lang/Math.toRadians (D)D
      // 1fd6: d2f
      // 1fd7: ldc2_w 112.5
      // 1fda: invokestatic java/lang/Math.toRadians (D)D
      // 1fdd: d2f
      // 1fde: ldc2_w -55.0
      // 1fe1: invokestatic java/lang/Math.toRadians (D)D
      // 1fe4: d2f
      // 1fe5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1fe8: aload 0
      // 1fe9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 1fec: aload 0
      // 1fed: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 1ff0: ldc2_w 32.5
      // 1ff3: invokestatic java/lang/Math.toRadians (D)D
      // 1ff6: d2f
      // 1ff7: fconst_0
      // 1ff8: fconst_0
      // 1ff9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 1ffc: aload 0
      // 1ffd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2000: aload 0
      // 2001: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2004: ldc2_w -32.5
      // 2007: invokestatic java/lang/Math.toRadians (D)D
      // 200a: d2f
      // 200b: fconst_0
      // 200c: fconst_0
      // 200d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2010: aload 0
      // 2011: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2014: aload 0
      // 2015: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2018: ldc2_w 2.5
      // 201b: invokestatic java/lang/Math.toRadians (D)D
      // 201e: d2f
      // 201f: ldc2_w -32.5
      // 2022: invokestatic java/lang/Math.toRadians (D)D
      // 2025: d2f
      // 2026: ldc2_w -72.5
      // 2029: invokestatic java/lang/Math.toRadians (D)D
      // 202c: d2f
      // 202d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2030: aload 0
      // 2031: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2034: aload 0
      // 2035: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2038: ldc2_w -30.0
      // 203b: invokestatic java/lang/Math.toRadians (D)D
      // 203e: d2f
      // 203f: ldc2_w -47.5
      // 2042: invokestatic java/lang/Math.toRadians (D)D
      // 2045: d2f
      // 2046: ldc2_w -67.5
      // 2049: invokestatic java/lang/Math.toRadians (D)D
      // 204c: d2f
      // 204d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2050: aload 0
      // 2051: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2054: aload 0
      // 2055: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2058: ldc2_w 17.5
      // 205b: invokestatic java/lang/Math.toRadians (D)D
      // 205e: d2f
      // 205f: fconst_0
      // 2060: fconst_0
      // 2061: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2064: aload 0
      // 2065: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2068: aload 0
      // 2069: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 206c: ldc2_w 7.5
      // 206f: invokestatic java/lang/Math.toRadians (D)D
      // 2072: d2f
      // 2073: ldc2_w 12.5
      // 2076: invokestatic java/lang/Math.toRadians (D)D
      // 2079: d2f
      // 207a: ldc2_w -15.0
      // 207d: invokestatic java/lang/Math.toRadians (D)D
      // 2080: d2f
      // 2081: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2084: aload 0
      // 2085: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2088: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 208b: aload 0
      // 208c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 208f: bipush 80
      // 2091: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 2094: aload 0
      // 2095: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2098: getstatic com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Amethyst_Crab_Entity.CRAB_BURROW Lcom/github/L_Ender/lionfishapi/server/animation/Animation;
      // 209b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setAnimation (Lcom/github/L_Ender/lionfishapi/server/animation/Animation;)Z
      // 209e: pop
      // 209f: aload 0
      // 20a0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 20a3: bipush 1
      // 20a4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 20a7: aload 0
      // 20a8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 20ab: aload 0
      // 20ac: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 20af: ldc2_w 5.0
      // 20b2: invokestatic java/lang/Math.toRadians (D)D
      // 20b5: d2f
      // 20b6: fconst_0
      // 20b7: fconst_0
      // 20b8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 20bb: aload 0
      // 20bc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 20bf: aload 0
      // 20c0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 20c3: ldc -2.0
      // 20c5: ldc 4.0
      // 20c7: fconst_0
      // 20c8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 20cb: aload 0
      // 20cc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 20cf: aload 0
      // 20d0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 20d3: fconst_0
      // 20d4: fconst_0
      // 20d5: ldc2_w -2.5
      // 20d8: invokestatic java/lang/Math.toRadians (D)D
      // 20db: d2f
      // 20dc: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 20df: aload 0
      // 20e0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 20e3: aload 0
      // 20e4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 20e7: ldc2_w 5.0
      // 20ea: invokestatic java/lang/Math.toRadians (D)D
      // 20ed: d2f
      // 20ee: fconst_0
      // 20ef: fconst_0
      // 20f0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 20f3: aload 0
      // 20f4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 20f7: aload 0
      // 20f8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 20fb: fconst_0
      // 20fc: ldc2_w 12.5
      // 20ff: invokestatic java/lang/Math.toRadians (D)D
      // 2102: d2f
      // 2103: fconst_0
      // 2104: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2107: aload 0
      // 2108: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 210b: aload 0
      // 210c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 210f: ldc2_w 10.0
      // 2112: invokestatic java/lang/Math.toRadians (D)D
      // 2115: d2f
      // 2116: fconst_0
      // 2117: ldc2_w -12.5
      // 211a: invokestatic java/lang/Math.toRadians (D)D
      // 211d: d2f
      // 211e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2121: aload 0
      // 2122: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2125: aload 0
      // 2126: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2129: ldc2_w 17.5
      // 212c: invokestatic java/lang/Math.toRadians (D)D
      // 212f: d2f
      // 2130: fconst_0
      // 2131: fconst_0
      // 2132: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2135: aload 0
      // 2136: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2139: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 213c: aload 0
      // 213d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2140: bipush 1
      // 2141: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 2144: aload 0
      // 2145: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2148: aload 0
      // 2149: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 214c: ldc2_w 10.0
      // 214f: invokestatic java/lang/Math.toRadians (D)D
      // 2152: d2f
      // 2153: fconst_0
      // 2154: fconst_0
      // 2155: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2158: aload 0
      // 2159: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 215c: aload 0
      // 215d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2160: ldc 1.5
      // 2162: ldc_w 7.5
      // 2165: fconst_0
      // 2166: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2169: aload 0
      // 216a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 216d: aload 0
      // 216e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2171: fconst_0
      // 2172: fconst_0
      // 2173: ldc2_w 2.5
      // 2176: invokestatic java/lang/Math.toRadians (D)D
      // 2179: d2f
      // 217a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 217d: aload 0
      // 217e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2181: aload 0
      // 2182: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2185: fconst_0
      // 2186: fconst_0
      // 2187: ldc2_w 2.5
      // 218a: invokestatic java/lang/Math.toRadians (D)D
      // 218d: d2f
      // 218e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2191: aload 0
      // 2192: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2195: aload 0
      // 2196: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2199: fconst_0
      // 219a: fconst_0
      // 219b: ldc2_w -5.0
      // 219e: invokestatic java/lang/Math.toRadians (D)D
      // 21a1: d2f
      // 21a2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 21a5: aload 0
      // 21a6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 21a9: aload 0
      // 21aa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 21ad: ldc2_w 12.5
      // 21b0: invokestatic java/lang/Math.toRadians (D)D
      // 21b3: d2f
      // 21b4: fconst_0
      // 21b5: fconst_0
      // 21b6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 21b9: aload 0
      // 21ba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 21bd: aload 0
      // 21be: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 21c1: fconst_0
      // 21c2: ldc2_w 22.5
      // 21c5: invokestatic java/lang/Math.toRadians (D)D
      // 21c8: d2f
      // 21c9: fconst_0
      // 21ca: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 21cd: aload 0
      // 21ce: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 21d1: aload 0
      // 21d2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 21d5: ldc2_w 20.0
      // 21d8: invokestatic java/lang/Math.toRadians (D)D
      // 21db: d2f
      // 21dc: fconst_0
      // 21dd: ldc2_w -25.0
      // 21e0: invokestatic java/lang/Math.toRadians (D)D
      // 21e3: d2f
      // 21e4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 21e7: aload 0
      // 21e8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 21eb: aload 0
      // 21ec: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 21ef: ldc2_w 32.5
      // 21f2: invokestatic java/lang/Math.toRadians (D)D
      // 21f5: d2f
      // 21f6: fconst_0
      // 21f7: fconst_0
      // 21f8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 21fb: aload 0
      // 21fc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 21ff: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2202: aload 0
      // 2203: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2206: bipush 1
      // 2207: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 220a: aload 0
      // 220b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 220e: aload 0
      // 220f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2212: ldc2_w 15.0
      // 2215: invokestatic java/lang/Math.toRadians (D)D
      // 2218: d2f
      // 2219: fconst_0
      // 221a: fconst_0
      // 221b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 221e: aload 0
      // 221f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2222: aload 0
      // 2223: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2226: ldc -2.0
      // 2228: ldc 9.0
      // 222a: fconst_0
      // 222b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 222e: aload 0
      // 222f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2232: aload 0
      // 2233: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2236: fconst_0
      // 2237: fconst_0
      // 2238: ldc2_w 2.5
      // 223b: invokestatic java/lang/Math.toRadians (D)D
      // 223e: d2f
      // 223f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2242: aload 0
      // 2243: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2246: aload 0
      // 2247: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 224a: fconst_0
      // 224b: fconst_0
      // 224c: ldc2_w 5.0
      // 224f: invokestatic java/lang/Math.toRadians (D)D
      // 2252: d2f
      // 2253: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2256: aload 0
      // 2257: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 225a: aload 0
      // 225b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 225e: fconst_0
      // 225f: fconst_0
      // 2260: ldc2_w -7.5
      // 2263: invokestatic java/lang/Math.toRadians (D)D
      // 2266: d2f
      // 2267: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 226a: aload 0
      // 226b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 226e: aload 0
      // 226f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2272: ldc2_w 17.5
      // 2275: invokestatic java/lang/Math.toRadians (D)D
      // 2278: d2f
      // 2279: fconst_0
      // 227a: fconst_0
      // 227b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 227e: aload 0
      // 227f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2282: aload 0
      // 2283: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2286: fconst_0
      // 2287: ldc2_w 35.0
      // 228a: invokestatic java/lang/Math.toRadians (D)D
      // 228d: d2f
      // 228e: fconst_0
      // 228f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2292: aload 0
      // 2293: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2296: aload 0
      // 2297: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 229a: ldc2_w 30.0
      // 229d: invokestatic java/lang/Math.toRadians (D)D
      // 22a0: d2f
      // 22a1: ldc2_w 2.5
      // 22a4: invokestatic java/lang/Math.toRadians (D)D
      // 22a7: d2f
      // 22a8: ldc2_w -37.5
      // 22ab: invokestatic java/lang/Math.toRadians (D)D
      // 22ae: d2f
      // 22af: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 22b2: aload 0
      // 22b3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 22b6: aload 0
      // 22b7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 22ba: ldc2_w 50.0
      // 22bd: invokestatic java/lang/Math.toRadians (D)D
      // 22c0: d2f
      // 22c1: fconst_0
      // 22c2: fconst_0
      // 22c3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 22c6: aload 0
      // 22c7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 22ca: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 22cd: aload 0
      // 22ce: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 22d1: bipush 1
      // 22d2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 22d5: aload 0
      // 22d6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 22d9: aload 0
      // 22da: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 22dd: ldc2_w 20.0
      // 22e0: invokestatic java/lang/Math.toRadians (D)D
      // 22e3: d2f
      // 22e4: fconst_0
      // 22e5: fconst_0
      // 22e6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 22e9: aload 0
      // 22ea: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 22ed: aload 0
      // 22ee: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 22f1: ldc 1.5
      // 22f3: ldc_w 12.5
      // 22f6: fconst_0
      // 22f7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 22fa: aload 0
      // 22fb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 22fe: aload 0
      // 22ff: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2302: fconst_0
      // 2303: fconst_0
      // 2304: ldc2_w 5.0
      // 2307: invokestatic java/lang/Math.toRadians (D)D
      // 230a: d2f
      // 230b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 230e: aload 0
      // 230f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2312: aload 0
      // 2313: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2316: fconst_0
      // 2317: fconst_0
      // 2318: ldc2_w 5.0
      // 231b: invokestatic java/lang/Math.toRadians (D)D
      // 231e: d2f
      // 231f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2322: aload 0
      // 2323: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2326: aload 0
      // 2327: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 232a: fconst_0
      // 232b: fconst_0
      // 232c: ldc2_w -7.5
      // 232f: invokestatic java/lang/Math.toRadians (D)D
      // 2332: d2f
      // 2333: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2336: aload 0
      // 2337: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 233a: aload 0
      // 233b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 233e: ldc2_w 17.5
      // 2341: invokestatic java/lang/Math.toRadians (D)D
      // 2344: d2f
      // 2345: fconst_0
      // 2346: fconst_0
      // 2347: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 234a: aload 0
      // 234b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 234e: aload 0
      // 234f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2352: fconst_0
      // 2353: ldc2_w 35.0
      // 2356: invokestatic java/lang/Math.toRadians (D)D
      // 2359: d2f
      // 235a: fconst_0
      // 235b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 235e: aload 0
      // 235f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2362: aload 0
      // 2363: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2366: ldc2_w 30.0
      // 2369: invokestatic java/lang/Math.toRadians (D)D
      // 236c: d2f
      // 236d: ldc2_w 2.5
      // 2370: invokestatic java/lang/Math.toRadians (D)D
      // 2373: d2f
      // 2374: ldc2_w -37.5
      // 2377: invokestatic java/lang/Math.toRadians (D)D
      // 237a: d2f
      // 237b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 237e: aload 0
      // 237f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2382: aload 0
      // 2383: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2386: ldc2_w 50.0
      // 2389: invokestatic java/lang/Math.toRadians (D)D
      // 238c: d2f
      // 238d: fconst_0
      // 238e: fconst_0
      // 238f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2392: aload 0
      // 2393: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2396: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2399: aload 0
      // 239a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 239d: bipush 1
      // 239e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 23a1: aload 0
      // 23a2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 23a5: aload 0
      // 23a6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 23a9: ldc2_w 25.0
      // 23ac: invokestatic java/lang/Math.toRadians (D)D
      // 23af: d2f
      // 23b0: fconst_0
      // 23b1: fconst_0
      // 23b2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 23b5: aload 0
      // 23b6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 23b9: aload 0
      // 23ba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 23bd: ldc -2.0
      // 23bf: ldc_w 17.5
      // 23c2: fconst_0
      // 23c3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 23c6: aload 0
      // 23c7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 23ca: aload 0
      // 23cb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 23ce: fconst_0
      // 23cf: fconst_0
      // 23d0: ldc2_w 5.0
      // 23d3: invokestatic java/lang/Math.toRadians (D)D
      // 23d6: d2f
      // 23d7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 23da: aload 0
      // 23db: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 23de: aload 0
      // 23df: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 23e2: fconst_0
      // 23e3: fconst_0
      // 23e4: ldc2_w 7.5
      // 23e7: invokestatic java/lang/Math.toRadians (D)D
      // 23ea: d2f
      // 23eb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 23ee: aload 0
      // 23ef: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 23f2: aload 0
      // 23f3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 23f6: fconst_0
      // 23f7: fconst_0
      // 23f8: ldc2_w -10.0
      // 23fb: invokestatic java/lang/Math.toRadians (D)D
      // 23fe: d2f
      // 23ff: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2402: aload 0
      // 2403: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2406: aload 0
      // 2407: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 240a: ldc2_w 17.5
      // 240d: invokestatic java/lang/Math.toRadians (D)D
      // 2410: d2f
      // 2411: fconst_0
      // 2412: fconst_0
      // 2413: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2416: aload 0
      // 2417: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 241a: aload 0
      // 241b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 241e: fconst_0
      // 241f: ldc2_w 35.0
      // 2422: invokestatic java/lang/Math.toRadians (D)D
      // 2425: d2f
      // 2426: fconst_0
      // 2427: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 242a: aload 0
      // 242b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 242e: aload 0
      // 242f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2432: ldc2_w 30.0
      // 2435: invokestatic java/lang/Math.toRadians (D)D
      // 2438: d2f
      // 2439: ldc2_w 2.5
      // 243c: invokestatic java/lang/Math.toRadians (D)D
      // 243f: d2f
      // 2440: ldc2_w -35.0
      // 2443: invokestatic java/lang/Math.toRadians (D)D
      // 2446: d2f
      // 2447: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 244a: aload 0
      // 244b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 244e: aload 0
      // 244f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2452: ldc2_w 50.0
      // 2455: invokestatic java/lang/Math.toRadians (D)D
      // 2458: d2f
      // 2459: fconst_0
      // 245a: fconst_0
      // 245b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 245e: aload 0
      // 245f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2462: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2465: aload 0
      // 2466: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2469: bipush 1
      // 246a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 246d: aload 0
      // 246e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2471: aload 0
      // 2472: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2475: ldc2_w 30.0
      // 2478: invokestatic java/lang/Math.toRadians (D)D
      // 247b: d2f
      // 247c: fconst_0
      // 247d: fconst_0
      // 247e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2481: aload 0
      // 2482: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2485: aload 0
      // 2486: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2489: fconst_0
      // 248a: ldc_w 23.0
      // 248d: fconst_0
      // 248e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2491: aload 0
      // 2492: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2495: aload 0
      // 2496: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2499: fconst_0
      // 249a: fconst_0
      // 249b: ldc2_w 7.5
      // 249e: invokestatic java/lang/Math.toRadians (D)D
      // 24a1: d2f
      // 24a2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 24a5: aload 0
      // 24a6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 24a9: aload 0
      // 24aa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 24ad: fconst_0
      // 24ae: fconst_0
      // 24af: ldc2_w 7.5
      // 24b2: invokestatic java/lang/Math.toRadians (D)D
      // 24b5: d2f
      // 24b6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 24b9: aload 0
      // 24ba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 24bd: aload 0
      // 24be: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 24c1: fconst_0
      // 24c2: fconst_0
      // 24c3: ldc2_w -12.5
      // 24c6: invokestatic java/lang/Math.toRadians (D)D
      // 24c9: d2f
      // 24ca: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 24cd: aload 0
      // 24ce: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 24d1: aload 0
      // 24d2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 24d5: ldc2_w 17.5
      // 24d8: invokestatic java/lang/Math.toRadians (D)D
      // 24db: d2f
      // 24dc: fconst_0
      // 24dd: fconst_0
      // 24de: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 24e1: aload 0
      // 24e2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 24e5: aload 0
      // 24e6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 24e9: fconst_0
      // 24ea: ldc2_w 32.5
      // 24ed: invokestatic java/lang/Math.toRadians (D)D
      // 24f0: d2f
      // 24f1: fconst_0
      // 24f2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 24f5: aload 0
      // 24f6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 24f9: aload 0
      // 24fa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 24fd: ldc2_w 27.5
      // 2500: invokestatic java/lang/Math.toRadians (D)D
      // 2503: d2f
      // 2504: ldc2_w 2.5
      // 2507: invokestatic java/lang/Math.toRadians (D)D
      // 250a: d2f
      // 250b: ldc2_w -35.0
      // 250e: invokestatic java/lang/Math.toRadians (D)D
      // 2511: d2f
      // 2512: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2515: aload 0
      // 2516: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2519: aload 0
      // 251a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 251d: ldc2_w 50.0
      // 2520: invokestatic java/lang/Math.toRadians (D)D
      // 2523: d2f
      // 2524: fconst_0
      // 2525: fconst_0
      // 2526: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2529: aload 0
      // 252a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 252d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2530: aload 0
      // 2531: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2534: bipush 3
      // 2535: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 2538: aload 0
      // 2539: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 253c: aload 0
      // 253d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2540: ldc2_w 30.0
      // 2543: invokestatic java/lang/Math.toRadians (D)D
      // 2546: d2f
      // 2547: fconst_0
      // 2548: fconst_0
      // 2549: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 254c: aload 0
      // 254d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2550: aload 0
      // 2551: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2554: fconst_0
      // 2555: ldc_w 23.0
      // 2558: fconst_0
      // 2559: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 255c: aload 0
      // 255d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2560: aload 0
      // 2561: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2564: fconst_0
      // 2565: fconst_0
      // 2566: ldc2_w 17.5
      // 2569: invokestatic java/lang/Math.toRadians (D)D
      // 256c: d2f
      // 256d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2570: aload 0
      // 2571: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2574: aload 0
      // 2575: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2578: fconst_0
      // 2579: fconst_0
      // 257a: ldc2_w 27.5
      // 257d: invokestatic java/lang/Math.toRadians (D)D
      // 2580: d2f
      // 2581: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2584: aload 0
      // 2585: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2588: aload 0
      // 2589: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 258c: fconst_0
      // 258d: fconst_0
      // 258e: ldc2_w -35.0
      // 2591: invokestatic java/lang/Math.toRadians (D)D
      // 2594: d2f
      // 2595: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2598: aload 0
      // 2599: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 259c: aload 0
      // 259d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 25a0: ldc2_w 7.5
      // 25a3: invokestatic java/lang/Math.toRadians (D)D
      // 25a6: d2f
      // 25a7: fconst_0
      // 25a8: fconst_0
      // 25a9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 25ac: aload 0
      // 25ad: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 25b0: aload 0
      // 25b1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 25b4: fconst_0
      // 25b5: ldc2_w 12.5
      // 25b8: invokestatic java/lang/Math.toRadians (D)D
      // 25bb: d2f
      // 25bc: fconst_0
      // 25bd: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 25c0: aload 0
      // 25c1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 25c4: aload 0
      // 25c5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 25c8: ldc2_w 10.0
      // 25cb: invokestatic java/lang/Math.toRadians (D)D
      // 25ce: d2f
      // 25cf: fconst_0
      // 25d0: ldc2_w -12.5
      // 25d3: invokestatic java/lang/Math.toRadians (D)D
      // 25d6: d2f
      // 25d7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 25da: aload 0
      // 25db: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 25de: aload 0
      // 25df: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 25e2: ldc2_w 50.0
      // 25e5: invokestatic java/lang/Math.toRadians (D)D
      // 25e8: d2f
      // 25e9: fconst_0
      // 25ea: fconst_0
      // 25eb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 25ee: aload 0
      // 25ef: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 25f2: aload 0
      // 25f3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 25f6: ldc2_w -7.5
      // 25f9: invokestatic java/lang/Math.toRadians (D)D
      // 25fc: d2f
      // 25fd: fconst_0
      // 25fe: fconst_0
      // 25ff: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2602: aload 0
      // 2603: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2606: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2609: aload 0
      // 260a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 260d: bipush 30
      // 260f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 2612: aload 0
      // 2613: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2616: bipush 1
      // 2617: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 261a: aload 0
      // 261b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 261e: aload 0
      // 261f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2622: ldc2_w 30.0
      // 2625: invokestatic java/lang/Math.toRadians (D)D
      // 2628: d2f
      // 2629: fconst_0
      // 262a: fconst_0
      // 262b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 262e: aload 0
      // 262f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2632: aload 0
      // 2633: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2636: fconst_0
      // 2637: ldc_w 23.0
      // 263a: fconst_0
      // 263b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 263e: aload 0
      // 263f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2642: aload 0
      // 2643: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2646: fconst_0
      // 2647: fconst_0
      // 2648: ldc2_w 17.5
      // 264b: invokestatic java/lang/Math.toRadians (D)D
      // 264e: d2f
      // 264f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2652: aload 0
      // 2653: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2656: aload 0
      // 2657: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 265a: fconst_0
      // 265b: fconst_0
      // 265c: ldc2_w 30.0
      // 265f: invokestatic java/lang/Math.toRadians (D)D
      // 2662: d2f
      // 2663: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2666: aload 0
      // 2667: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 266a: aload 0
      // 266b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 266e: fconst_0
      // 266f: fconst_0
      // 2670: ldc2_w -35.0
      // 2673: invokestatic java/lang/Math.toRadians (D)D
      // 2676: d2f
      // 2677: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 267a: aload 0
      // 267b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 267e: aload 0
      // 267f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2682: ldc2_w 5.0
      // 2685: invokestatic java/lang/Math.toRadians (D)D
      // 2688: d2f
      // 2689: fconst_0
      // 268a: fconst_0
      // 268b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 268e: aload 0
      // 268f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2692: aload 0
      // 2693: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2696: fconst_0
      // 2697: ldc2_w 10.0
      // 269a: invokestatic java/lang/Math.toRadians (D)D
      // 269d: d2f
      // 269e: fconst_0
      // 269f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 26a2: aload 0
      // 26a3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 26a6: aload 0
      // 26a7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 26aa: ldc2_w 7.5
      // 26ad: invokestatic java/lang/Math.toRadians (D)D
      // 26b0: d2f
      // 26b1: fconst_0
      // 26b2: ldc2_w -10.0
      // 26b5: invokestatic java/lang/Math.toRadians (D)D
      // 26b8: d2f
      // 26b9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 26bc: aload 0
      // 26bd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 26c0: aload 0
      // 26c1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 26c4: ldc2_w 50.0
      // 26c7: invokestatic java/lang/Math.toRadians (D)D
      // 26ca: d2f
      // 26cb: fconst_0
      // 26cc: fconst_0
      // 26cd: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 26d0: aload 0
      // 26d1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 26d4: aload 0
      // 26d5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 26d8: ldc2_w -7.5
      // 26db: invokestatic java/lang/Math.toRadians (D)D
      // 26de: d2f
      // 26df: fconst_0
      // 26e0: fconst_0
      // 26e1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 26e4: aload 0
      // 26e5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 26e8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 26eb: aload 0
      // 26ec: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 26ef: bipush 1
      // 26f0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 26f3: aload 0
      // 26f4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 26f7: aload 0
      // 26f8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 26fb: ldc2_w 30.0
      // 26fe: invokestatic java/lang/Math.toRadians (D)D
      // 2701: d2f
      // 2702: ldc2_w 2.5
      // 2705: invokestatic java/lang/Math.toRadians (D)D
      // 2708: d2f
      // 2709: ldc2_w 1.25
      // 270c: invokestatic java/lang/Math.toRadians (D)D
      // 270f: d2f
      // 2710: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2713: aload 0
      // 2714: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2717: aload 0
      // 2718: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 271b: fconst_0
      // 271c: ldc_w 23.0
      // 271f: fconst_0
      // 2720: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2723: aload 0
      // 2724: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2727: aload 0
      // 2728: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 272b: fconst_0
      // 272c: fconst_0
      // 272d: ldc2_w 17.5
      // 2730: invokestatic java/lang/Math.toRadians (D)D
      // 2733: d2f
      // 2734: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2737: aload 0
      // 2738: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 273b: aload 0
      // 273c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 273f: fconst_0
      // 2740: fconst_0
      // 2741: ldc2_w 30.0
      // 2744: invokestatic java/lang/Math.toRadians (D)D
      // 2747: d2f
      // 2748: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 274b: aload 0
      // 274c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 274f: aload 0
      // 2750: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2753: fconst_0
      // 2754: fconst_0
      // 2755: ldc2_w -35.0
      // 2758: invokestatic java/lang/Math.toRadians (D)D
      // 275b: d2f
      // 275c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 275f: aload 0
      // 2760: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2763: aload 0
      // 2764: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2767: ldc2_w 5.0
      // 276a: invokestatic java/lang/Math.toRadians (D)D
      // 276d: d2f
      // 276e: fconst_0
      // 276f: fconst_0
      // 2770: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2773: aload 0
      // 2774: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2777: aload 0
      // 2778: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 277b: fconst_0
      // 277c: ldc2_w 10.0
      // 277f: invokestatic java/lang/Math.toRadians (D)D
      // 2782: d2f
      // 2783: fconst_0
      // 2784: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2787: aload 0
      // 2788: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 278b: aload 0
      // 278c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 278f: ldc2_w 7.5
      // 2792: invokestatic java/lang/Math.toRadians (D)D
      // 2795: d2f
      // 2796: fconst_0
      // 2797: ldc2_w -10.0
      // 279a: invokestatic java/lang/Math.toRadians (D)D
      // 279d: d2f
      // 279e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 27a1: aload 0
      // 27a2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 27a5: aload 0
      // 27a6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 27a9: ldc2_w 50.0
      // 27ac: invokestatic java/lang/Math.toRadians (D)D
      // 27af: d2f
      // 27b0: fconst_0
      // 27b1: fconst_0
      // 27b2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 27b5: aload 0
      // 27b6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 27b9: aload 0
      // 27ba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 27bd: ldc2_w -7.5
      // 27c0: invokestatic java/lang/Math.toRadians (D)D
      // 27c3: d2f
      // 27c4: fconst_0
      // 27c5: fconst_0
      // 27c6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 27c9: aload 0
      // 27ca: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 27cd: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 27d0: aload 0
      // 27d1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 27d4: bipush 1
      // 27d5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 27d8: aload 0
      // 27d9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 27dc: aload 0
      // 27dd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 27e0: ldc2_w 30.0
      // 27e3: invokestatic java/lang/Math.toRadians (D)D
      // 27e6: d2f
      // 27e7: ldc2_w -2.5
      // 27ea: invokestatic java/lang/Math.toRadians (D)D
      // 27ed: d2f
      // 27ee: ldc2_w -1.25
      // 27f1: invokestatic java/lang/Math.toRadians (D)D
      // 27f4: d2f
      // 27f5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 27f8: aload 0
      // 27f9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 27fc: aload 0
      // 27fd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2800: fconst_0
      // 2801: ldc_w 22.5
      // 2804: fconst_0
      // 2805: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2808: aload 0
      // 2809: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 280c: aload 0
      // 280d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2810: fconst_0
      // 2811: fconst_0
      // 2812: ldc2_w 17.5
      // 2815: invokestatic java/lang/Math.toRadians (D)D
      // 2818: d2f
      // 2819: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 281c: aload 0
      // 281d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2820: aload 0
      // 2821: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2824: fconst_0
      // 2825: fconst_0
      // 2826: ldc2_w 30.0
      // 2829: invokestatic java/lang/Math.toRadians (D)D
      // 282c: d2f
      // 282d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2830: aload 0
      // 2831: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2834: aload 0
      // 2835: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2838: fconst_0
      // 2839: fconst_0
      // 283a: ldc2_w -35.0
      // 283d: invokestatic java/lang/Math.toRadians (D)D
      // 2840: d2f
      // 2841: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2844: aload 0
      // 2845: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2848: aload 0
      // 2849: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 284c: ldc2_w 5.0
      // 284f: invokestatic java/lang/Math.toRadians (D)D
      // 2852: d2f
      // 2853: fconst_0
      // 2854: fconst_0
      // 2855: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2858: aload 0
      // 2859: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 285c: aload 0
      // 285d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2860: fconst_0
      // 2861: ldc2_w 10.0
      // 2864: invokestatic java/lang/Math.toRadians (D)D
      // 2867: d2f
      // 2868: fconst_0
      // 2869: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 286c: aload 0
      // 286d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2870: aload 0
      // 2871: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2874: ldc2_w 7.5
      // 2877: invokestatic java/lang/Math.toRadians (D)D
      // 287a: d2f
      // 287b: fconst_0
      // 287c: ldc2_w -10.0
      // 287f: invokestatic java/lang/Math.toRadians (D)D
      // 2882: d2f
      // 2883: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2886: aload 0
      // 2887: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 288a: aload 0
      // 288b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 288e: ldc2_w 50.0
      // 2891: invokestatic java/lang/Math.toRadians (D)D
      // 2894: d2f
      // 2895: fconst_0
      // 2896: fconst_0
      // 2897: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 289a: aload 0
      // 289b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 289e: aload 0
      // 289f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 28a2: ldc2_w -7.5
      // 28a5: invokestatic java/lang/Math.toRadians (D)D
      // 28a8: d2f
      // 28a9: fconst_0
      // 28aa: fconst_0
      // 28ab: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 28ae: aload 0
      // 28af: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 28b2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 28b5: aload 0
      // 28b6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 28b9: bipush 1
      // 28ba: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 28bd: aload 0
      // 28be: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 28c1: aload 0
      // 28c2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 28c5: ldc2_w 30.0
      // 28c8: invokestatic java/lang/Math.toRadians (D)D
      // 28cb: d2f
      // 28cc: ldc2_w 2.5
      // 28cf: invokestatic java/lang/Math.toRadians (D)D
      // 28d2: d2f
      // 28d3: ldc2_w 1.25
      // 28d6: invokestatic java/lang/Math.toRadians (D)D
      // 28d9: d2f
      // 28da: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 28dd: aload 0
      // 28de: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 28e1: aload 0
      // 28e2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 28e5: fconst_0
      // 28e6: ldc 22.0
      // 28e8: fconst_0
      // 28e9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 28ec: aload 0
      // 28ed: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 28f0: aload 0
      // 28f1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 28f4: fconst_0
      // 28f5: fconst_0
      // 28f6: ldc2_w 17.5
      // 28f9: invokestatic java/lang/Math.toRadians (D)D
      // 28fc: d2f
      // 28fd: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2900: aload 0
      // 2901: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2904: aload 0
      // 2905: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2908: fconst_0
      // 2909: fconst_0
      // 290a: ldc2_w 30.0
      // 290d: invokestatic java/lang/Math.toRadians (D)D
      // 2910: d2f
      // 2911: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2914: aload 0
      // 2915: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2918: aload 0
      // 2919: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 291c: fconst_0
      // 291d: fconst_0
      // 291e: ldc2_w -35.0
      // 2921: invokestatic java/lang/Math.toRadians (D)D
      // 2924: d2f
      // 2925: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2928: aload 0
      // 2929: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 292c: aload 0
      // 292d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2930: ldc2_w 5.0
      // 2933: invokestatic java/lang/Math.toRadians (D)D
      // 2936: d2f
      // 2937: fconst_0
      // 2938: fconst_0
      // 2939: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 293c: aload 0
      // 293d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2940: aload 0
      // 2941: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2944: fconst_0
      // 2945: ldc2_w 10.0
      // 2948: invokestatic java/lang/Math.toRadians (D)D
      // 294b: d2f
      // 294c: fconst_0
      // 294d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2950: aload 0
      // 2951: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2954: aload 0
      // 2955: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2958: ldc2_w 7.5
      // 295b: invokestatic java/lang/Math.toRadians (D)D
      // 295e: d2f
      // 295f: fconst_0
      // 2960: ldc2_w -10.0
      // 2963: invokestatic java/lang/Math.toRadians (D)D
      // 2966: d2f
      // 2967: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 296a: aload 0
      // 296b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 296e: aload 0
      // 296f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2972: ldc2_w 50.0
      // 2975: invokestatic java/lang/Math.toRadians (D)D
      // 2978: d2f
      // 2979: fconst_0
      // 297a: fconst_0
      // 297b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 297e: aload 0
      // 297f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2982: aload 0
      // 2983: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2986: ldc2_w -7.5
      // 2989: invokestatic java/lang/Math.toRadians (D)D
      // 298c: d2f
      // 298d: fconst_0
      // 298e: fconst_0
      // 298f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2992: aload 0
      // 2993: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2996: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2999: aload 0
      // 299a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 299d: bipush 1
      // 299e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 29a1: aload 0
      // 29a2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 29a5: aload 0
      // 29a6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 29a9: ldc2_w 30.0
      // 29ac: invokestatic java/lang/Math.toRadians (D)D
      // 29af: d2f
      // 29b0: ldc2_w -2.5
      // 29b3: invokestatic java/lang/Math.toRadians (D)D
      // 29b6: d2f
      // 29b7: ldc2_w -1.25
      // 29ba: invokestatic java/lang/Math.toRadians (D)D
      // 29bd: d2f
      // 29be: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 29c1: aload 0
      // 29c2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 29c5: aload 0
      // 29c6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 29c9: fconst_0
      // 29ca: ldc_w 21.5
      // 29cd: fconst_0
      // 29ce: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 29d1: aload 0
      // 29d2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 29d5: aload 0
      // 29d6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 29d9: fconst_0
      // 29da: fconst_0
      // 29db: ldc2_w 17.5
      // 29de: invokestatic java/lang/Math.toRadians (D)D
      // 29e1: d2f
      // 29e2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 29e5: aload 0
      // 29e6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 29e9: aload 0
      // 29ea: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 29ed: fconst_0
      // 29ee: fconst_0
      // 29ef: ldc2_w 30.0
      // 29f2: invokestatic java/lang/Math.toRadians (D)D
      // 29f5: d2f
      // 29f6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 29f9: aload 0
      // 29fa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 29fd: aload 0
      // 29fe: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2a01: fconst_0
      // 2a02: fconst_0
      // 2a03: ldc2_w -35.0
      // 2a06: invokestatic java/lang/Math.toRadians (D)D
      // 2a09: d2f
      // 2a0a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2a0d: aload 0
      // 2a0e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2a11: aload 0
      // 2a12: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2a15: ldc2_w 5.0
      // 2a18: invokestatic java/lang/Math.toRadians (D)D
      // 2a1b: d2f
      // 2a1c: fconst_0
      // 2a1d: fconst_0
      // 2a1e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2a21: aload 0
      // 2a22: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2a25: aload 0
      // 2a26: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2a29: fconst_0
      // 2a2a: ldc2_w 10.0
      // 2a2d: invokestatic java/lang/Math.toRadians (D)D
      // 2a30: d2f
      // 2a31: fconst_0
      // 2a32: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2a35: aload 0
      // 2a36: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2a39: aload 0
      // 2a3a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2a3d: ldc2_w 7.5
      // 2a40: invokestatic java/lang/Math.toRadians (D)D
      // 2a43: d2f
      // 2a44: fconst_0
      // 2a45: ldc2_w -10.0
      // 2a48: invokestatic java/lang/Math.toRadians (D)D
      // 2a4b: d2f
      // 2a4c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2a4f: aload 0
      // 2a50: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2a53: aload 0
      // 2a54: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2a57: ldc2_w 50.0
      // 2a5a: invokestatic java/lang/Math.toRadians (D)D
      // 2a5d: d2f
      // 2a5e: fconst_0
      // 2a5f: fconst_0
      // 2a60: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2a63: aload 0
      // 2a64: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2a67: aload 0
      // 2a68: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2a6b: ldc2_w -7.5
      // 2a6e: invokestatic java/lang/Math.toRadians (D)D
      // 2a71: d2f
      // 2a72: fconst_0
      // 2a73: fconst_0
      // 2a74: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2a77: aload 0
      // 2a78: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2a7b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2a7e: aload 0
      // 2a7f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2a82: bipush 1
      // 2a83: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 2a86: aload 0
      // 2a87: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2a8a: aload 0
      // 2a8b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2a8e: ldc2_w 30.0
      // 2a91: invokestatic java/lang/Math.toRadians (D)D
      // 2a94: d2f
      // 2a95: ldc2_w 2.5
      // 2a98: invokestatic java/lang/Math.toRadians (D)D
      // 2a9b: d2f
      // 2a9c: ldc2_w 1.25
      // 2a9f: invokestatic java/lang/Math.toRadians (D)D
      // 2aa2: d2f
      // 2aa3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2aa6: aload 0
      // 2aa7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2aaa: aload 0
      // 2aab: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2aae: fconst_0
      // 2aaf: ldc 21.0
      // 2ab1: fconst_0
      // 2ab2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2ab5: aload 0
      // 2ab6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2ab9: aload 0
      // 2aba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2abd: fconst_0
      // 2abe: fconst_0
      // 2abf: ldc2_w 17.5
      // 2ac2: invokestatic java/lang/Math.toRadians (D)D
      // 2ac5: d2f
      // 2ac6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2ac9: aload 0
      // 2aca: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2acd: aload 0
      // 2ace: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2ad1: fconst_0
      // 2ad2: fconst_0
      // 2ad3: ldc2_w 30.0
      // 2ad6: invokestatic java/lang/Math.toRadians (D)D
      // 2ad9: d2f
      // 2ada: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2add: aload 0
      // 2ade: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2ae1: aload 0
      // 2ae2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2ae5: fconst_0
      // 2ae6: fconst_0
      // 2ae7: ldc2_w -35.0
      // 2aea: invokestatic java/lang/Math.toRadians (D)D
      // 2aed: d2f
      // 2aee: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2af1: aload 0
      // 2af2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2af5: aload 0
      // 2af6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2af9: ldc2_w 5.0
      // 2afc: invokestatic java/lang/Math.toRadians (D)D
      // 2aff: d2f
      // 2b00: fconst_0
      // 2b01: fconst_0
      // 2b02: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2b05: aload 0
      // 2b06: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2b09: aload 0
      // 2b0a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2b0d: fconst_0
      // 2b0e: ldc2_w 10.0
      // 2b11: invokestatic java/lang/Math.toRadians (D)D
      // 2b14: d2f
      // 2b15: fconst_0
      // 2b16: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2b19: aload 0
      // 2b1a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2b1d: aload 0
      // 2b1e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2b21: ldc2_w 7.5
      // 2b24: invokestatic java/lang/Math.toRadians (D)D
      // 2b27: d2f
      // 2b28: fconst_0
      // 2b29: ldc2_w -10.0
      // 2b2c: invokestatic java/lang/Math.toRadians (D)D
      // 2b2f: d2f
      // 2b30: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2b33: aload 0
      // 2b34: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2b37: aload 0
      // 2b38: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2b3b: ldc2_w 50.0
      // 2b3e: invokestatic java/lang/Math.toRadians (D)D
      // 2b41: d2f
      // 2b42: fconst_0
      // 2b43: fconst_0
      // 2b44: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2b47: aload 0
      // 2b48: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2b4b: aload 0
      // 2b4c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2b4f: ldc2_w -7.5
      // 2b52: invokestatic java/lang/Math.toRadians (D)D
      // 2b55: d2f
      // 2b56: fconst_0
      // 2b57: fconst_0
      // 2b58: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2b5b: aload 0
      // 2b5c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2b5f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2b62: aload 0
      // 2b63: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2b66: bipush 1
      // 2b67: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 2b6a: aload 0
      // 2b6b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2b6e: aload 0
      // 2b6f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2b72: ldc2_w 30.0
      // 2b75: invokestatic java/lang/Math.toRadians (D)D
      // 2b78: d2f
      // 2b79: ldc2_w -2.5
      // 2b7c: invokestatic java/lang/Math.toRadians (D)D
      // 2b7f: d2f
      // 2b80: ldc2_w -1.25
      // 2b83: invokestatic java/lang/Math.toRadians (D)D
      // 2b86: d2f
      // 2b87: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2b8a: aload 0
      // 2b8b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2b8e: aload 0
      // 2b8f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2b92: fconst_0
      // 2b93: ldc_w 20.5
      // 2b96: fconst_0
      // 2b97: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2b9a: aload 0
      // 2b9b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2b9e: aload 0
      // 2b9f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2ba2: fconst_0
      // 2ba3: fconst_0
      // 2ba4: ldc2_w 17.5
      // 2ba7: invokestatic java/lang/Math.toRadians (D)D
      // 2baa: d2f
      // 2bab: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2bae: aload 0
      // 2baf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2bb2: aload 0
      // 2bb3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2bb6: fconst_0
      // 2bb7: fconst_0
      // 2bb8: ldc2_w 30.0
      // 2bbb: invokestatic java/lang/Math.toRadians (D)D
      // 2bbe: d2f
      // 2bbf: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2bc2: aload 0
      // 2bc3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2bc6: aload 0
      // 2bc7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2bca: fconst_0
      // 2bcb: fconst_0
      // 2bcc: ldc2_w -35.0
      // 2bcf: invokestatic java/lang/Math.toRadians (D)D
      // 2bd2: d2f
      // 2bd3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2bd6: aload 0
      // 2bd7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2bda: aload 0
      // 2bdb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2bde: ldc2_w 5.0
      // 2be1: invokestatic java/lang/Math.toRadians (D)D
      // 2be4: d2f
      // 2be5: fconst_0
      // 2be6: fconst_0
      // 2be7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2bea: aload 0
      // 2beb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2bee: aload 0
      // 2bef: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2bf2: fconst_0
      // 2bf3: ldc2_w 10.0
      // 2bf6: invokestatic java/lang/Math.toRadians (D)D
      // 2bf9: d2f
      // 2bfa: fconst_0
      // 2bfb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2bfe: aload 0
      // 2bff: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2c02: aload 0
      // 2c03: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2c06: ldc2_w 7.5
      // 2c09: invokestatic java/lang/Math.toRadians (D)D
      // 2c0c: d2f
      // 2c0d: fconst_0
      // 2c0e: ldc2_w -10.0
      // 2c11: invokestatic java/lang/Math.toRadians (D)D
      // 2c14: d2f
      // 2c15: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2c18: aload 0
      // 2c19: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2c1c: aload 0
      // 2c1d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2c20: ldc2_w 50.0
      // 2c23: invokestatic java/lang/Math.toRadians (D)D
      // 2c26: d2f
      // 2c27: fconst_0
      // 2c28: fconst_0
      // 2c29: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2c2c: aload 0
      // 2c2d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2c30: aload 0
      // 2c31: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2c34: ldc2_w -7.5
      // 2c37: invokestatic java/lang/Math.toRadians (D)D
      // 2c3a: d2f
      // 2c3b: fconst_0
      // 2c3c: fconst_0
      // 2c3d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2c40: aload 0
      // 2c41: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2c44: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2c47: aload 0
      // 2c48: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2c4b: bipush 1
      // 2c4c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 2c4f: aload 0
      // 2c50: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2c53: aload 0
      // 2c54: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2c57: ldc2_w 30.0
      // 2c5a: invokestatic java/lang/Math.toRadians (D)D
      // 2c5d: d2f
      // 2c5e: ldc2_w 2.5
      // 2c61: invokestatic java/lang/Math.toRadians (D)D
      // 2c64: d2f
      // 2c65: ldc2_w 1.25
      // 2c68: invokestatic java/lang/Math.toRadians (D)D
      // 2c6b: d2f
      // 2c6c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2c6f: aload 0
      // 2c70: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2c73: aload 0
      // 2c74: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2c77: fconst_0
      // 2c78: ldc 20.0
      // 2c7a: fconst_0
      // 2c7b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2c7e: aload 0
      // 2c7f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2c82: aload 0
      // 2c83: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2c86: fconst_0
      // 2c87: fconst_0
      // 2c88: ldc2_w 17.5
      // 2c8b: invokestatic java/lang/Math.toRadians (D)D
      // 2c8e: d2f
      // 2c8f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2c92: aload 0
      // 2c93: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2c96: aload 0
      // 2c97: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2c9a: fconst_0
      // 2c9b: fconst_0
      // 2c9c: ldc2_w 30.0
      // 2c9f: invokestatic java/lang/Math.toRadians (D)D
      // 2ca2: d2f
      // 2ca3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2ca6: aload 0
      // 2ca7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2caa: aload 0
      // 2cab: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2cae: fconst_0
      // 2caf: fconst_0
      // 2cb0: ldc2_w -35.0
      // 2cb3: invokestatic java/lang/Math.toRadians (D)D
      // 2cb6: d2f
      // 2cb7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2cba: aload 0
      // 2cbb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2cbe: aload 0
      // 2cbf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2cc2: ldc2_w 5.0
      // 2cc5: invokestatic java/lang/Math.toRadians (D)D
      // 2cc8: d2f
      // 2cc9: fconst_0
      // 2cca: fconst_0
      // 2ccb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2cce: aload 0
      // 2ccf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2cd2: aload 0
      // 2cd3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2cd6: fconst_0
      // 2cd7: ldc2_w 10.0
      // 2cda: invokestatic java/lang/Math.toRadians (D)D
      // 2cdd: d2f
      // 2cde: fconst_0
      // 2cdf: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2ce2: aload 0
      // 2ce3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2ce6: aload 0
      // 2ce7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2cea: ldc2_w 7.5
      // 2ced: invokestatic java/lang/Math.toRadians (D)D
      // 2cf0: d2f
      // 2cf1: fconst_0
      // 2cf2: ldc2_w -10.0
      // 2cf5: invokestatic java/lang/Math.toRadians (D)D
      // 2cf8: d2f
      // 2cf9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2cfc: aload 0
      // 2cfd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2d00: aload 0
      // 2d01: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2d04: ldc2_w 50.0
      // 2d07: invokestatic java/lang/Math.toRadians (D)D
      // 2d0a: d2f
      // 2d0b: fconst_0
      // 2d0c: fconst_0
      // 2d0d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2d10: aload 0
      // 2d11: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2d14: aload 0
      // 2d15: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2d18: ldc2_w -7.5
      // 2d1b: invokestatic java/lang/Math.toRadians (D)D
      // 2d1e: d2f
      // 2d1f: fconst_0
      // 2d20: fconst_0
      // 2d21: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2d24: aload 0
      // 2d25: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2d28: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2d2b: aload 0
      // 2d2c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2d2f: bipush 1
      // 2d30: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 2d33: aload 0
      // 2d34: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2d37: aload 0
      // 2d38: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2d3b: ldc2_w 30.0
      // 2d3e: invokestatic java/lang/Math.toRadians (D)D
      // 2d41: d2f
      // 2d42: ldc2_w -2.5
      // 2d45: invokestatic java/lang/Math.toRadians (D)D
      // 2d48: d2f
      // 2d49: ldc2_w -1.25
      // 2d4c: invokestatic java/lang/Math.toRadians (D)D
      // 2d4f: d2f
      // 2d50: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2d53: aload 0
      // 2d54: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2d57: aload 0
      // 2d58: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2d5b: fconst_0
      // 2d5c: ldc_w 19.5
      // 2d5f: fconst_0
      // 2d60: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2d63: aload 0
      // 2d64: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2d67: aload 0
      // 2d68: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2d6b: fconst_0
      // 2d6c: fconst_0
      // 2d6d: ldc2_w 17.5
      // 2d70: invokestatic java/lang/Math.toRadians (D)D
      // 2d73: d2f
      // 2d74: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2d77: aload 0
      // 2d78: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2d7b: aload 0
      // 2d7c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2d7f: fconst_0
      // 2d80: fconst_0
      // 2d81: ldc2_w 30.0
      // 2d84: invokestatic java/lang/Math.toRadians (D)D
      // 2d87: d2f
      // 2d88: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2d8b: aload 0
      // 2d8c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2d8f: aload 0
      // 2d90: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2d93: fconst_0
      // 2d94: fconst_0
      // 2d95: ldc2_w -35.0
      // 2d98: invokestatic java/lang/Math.toRadians (D)D
      // 2d9b: d2f
      // 2d9c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2d9f: aload 0
      // 2da0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2da3: aload 0
      // 2da4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2da7: ldc2_w 5.0
      // 2daa: invokestatic java/lang/Math.toRadians (D)D
      // 2dad: d2f
      // 2dae: fconst_0
      // 2daf: fconst_0
      // 2db0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2db3: aload 0
      // 2db4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2db7: aload 0
      // 2db8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2dbb: fconst_0
      // 2dbc: ldc2_w 10.0
      // 2dbf: invokestatic java/lang/Math.toRadians (D)D
      // 2dc2: d2f
      // 2dc3: fconst_0
      // 2dc4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2dc7: aload 0
      // 2dc8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2dcb: aload 0
      // 2dcc: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2dcf: ldc2_w 7.5
      // 2dd2: invokestatic java/lang/Math.toRadians (D)D
      // 2dd5: d2f
      // 2dd6: fconst_0
      // 2dd7: ldc2_w -10.0
      // 2dda: invokestatic java/lang/Math.toRadians (D)D
      // 2ddd: d2f
      // 2dde: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2de1: aload 0
      // 2de2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2de5: aload 0
      // 2de6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2de9: ldc2_w 50.0
      // 2dec: invokestatic java/lang/Math.toRadians (D)D
      // 2def: d2f
      // 2df0: fconst_0
      // 2df1: fconst_0
      // 2df2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2df5: aload 0
      // 2df6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2df9: aload 0
      // 2dfa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2dfd: ldc2_w -7.5
      // 2e00: invokestatic java/lang/Math.toRadians (D)D
      // 2e03: d2f
      // 2e04: fconst_0
      // 2e05: fconst_0
      // 2e06: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2e09: aload 0
      // 2e0a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2e0d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2e10: aload 0
      // 2e11: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2e14: bipush 1
      // 2e15: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 2e18: aload 0
      // 2e19: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2e1c: aload 0
      // 2e1d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2e20: ldc2_w 30.0
      // 2e23: invokestatic java/lang/Math.toRadians (D)D
      // 2e26: d2f
      // 2e27: ldc2_w 2.5
      // 2e2a: invokestatic java/lang/Math.toRadians (D)D
      // 2e2d: d2f
      // 2e2e: ldc2_w 1.25
      // 2e31: invokestatic java/lang/Math.toRadians (D)D
      // 2e34: d2f
      // 2e35: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2e38: aload 0
      // 2e39: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2e3c: aload 0
      // 2e3d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2e40: fconst_0
      // 2e41: ldc 19.0
      // 2e43: fconst_0
      // 2e44: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2e47: aload 0
      // 2e48: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2e4b: aload 0
      // 2e4c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2e4f: fconst_0
      // 2e50: fconst_0
      // 2e51: ldc2_w 17.5
      // 2e54: invokestatic java/lang/Math.toRadians (D)D
      // 2e57: d2f
      // 2e58: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2e5b: aload 0
      // 2e5c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2e5f: aload 0
      // 2e60: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2e63: fconst_0
      // 2e64: fconst_0
      // 2e65: ldc2_w 30.0
      // 2e68: invokestatic java/lang/Math.toRadians (D)D
      // 2e6b: d2f
      // 2e6c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2e6f: aload 0
      // 2e70: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2e73: aload 0
      // 2e74: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2e77: fconst_0
      // 2e78: fconst_0
      // 2e79: ldc2_w -35.0
      // 2e7c: invokestatic java/lang/Math.toRadians (D)D
      // 2e7f: d2f
      // 2e80: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2e83: aload 0
      // 2e84: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2e87: aload 0
      // 2e88: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2e8b: ldc2_w 5.0
      // 2e8e: invokestatic java/lang/Math.toRadians (D)D
      // 2e91: d2f
      // 2e92: fconst_0
      // 2e93: fconst_0
      // 2e94: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2e97: aload 0
      // 2e98: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2e9b: aload 0
      // 2e9c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2e9f: fconst_0
      // 2ea0: ldc2_w 10.0
      // 2ea3: invokestatic java/lang/Math.toRadians (D)D
      // 2ea6: d2f
      // 2ea7: fconst_0
      // 2ea8: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2eab: aload 0
      // 2eac: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2eaf: aload 0
      // 2eb0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2eb3: ldc2_w 7.5
      // 2eb6: invokestatic java/lang/Math.toRadians (D)D
      // 2eb9: d2f
      // 2eba: fconst_0
      // 2ebb: ldc2_w -10.0
      // 2ebe: invokestatic java/lang/Math.toRadians (D)D
      // 2ec1: d2f
      // 2ec2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2ec5: aload 0
      // 2ec6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2ec9: aload 0
      // 2eca: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2ecd: ldc2_w 50.0
      // 2ed0: invokestatic java/lang/Math.toRadians (D)D
      // 2ed3: d2f
      // 2ed4: fconst_0
      // 2ed5: fconst_0
      // 2ed6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2ed9: aload 0
      // 2eda: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2edd: aload 0
      // 2ede: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2ee1: ldc2_w -7.5
      // 2ee4: invokestatic java/lang/Math.toRadians (D)D
      // 2ee7: d2f
      // 2ee8: fconst_0
      // 2ee9: fconst_0
      // 2eea: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2eed: aload 0
      // 2eee: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2ef1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2ef4: aload 0
      // 2ef5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2ef8: bipush 1
      // 2ef9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 2efc: aload 0
      // 2efd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2f00: aload 0
      // 2f01: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2f04: ldc2_w 30.0
      // 2f07: invokestatic java/lang/Math.toRadians (D)D
      // 2f0a: d2f
      // 2f0b: ldc2_w -2.5
      // 2f0e: invokestatic java/lang/Math.toRadians (D)D
      // 2f11: d2f
      // 2f12: ldc2_w -1.25
      // 2f15: invokestatic java/lang/Math.toRadians (D)D
      // 2f18: d2f
      // 2f19: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2f1c: aload 0
      // 2f1d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2f20: aload 0
      // 2f21: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2f24: fconst_0
      // 2f25: ldc_w 18.5
      // 2f28: fconst_0
      // 2f29: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2f2c: aload 0
      // 2f2d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2f30: aload 0
      // 2f31: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2f34: fconst_0
      // 2f35: fconst_0
      // 2f36: ldc2_w 17.5
      // 2f39: invokestatic java/lang/Math.toRadians (D)D
      // 2f3c: d2f
      // 2f3d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2f40: aload 0
      // 2f41: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2f44: aload 0
      // 2f45: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2f48: fconst_0
      // 2f49: fconst_0
      // 2f4a: ldc2_w 30.0
      // 2f4d: invokestatic java/lang/Math.toRadians (D)D
      // 2f50: d2f
      // 2f51: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2f54: aload 0
      // 2f55: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2f58: aload 0
      // 2f59: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2f5c: fconst_0
      // 2f5d: fconst_0
      // 2f5e: ldc2_w -35.0
      // 2f61: invokestatic java/lang/Math.toRadians (D)D
      // 2f64: d2f
      // 2f65: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2f68: aload 0
      // 2f69: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2f6c: aload 0
      // 2f6d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2f70: ldc2_w 5.0
      // 2f73: invokestatic java/lang/Math.toRadians (D)D
      // 2f76: d2f
      // 2f77: fconst_0
      // 2f78: fconst_0
      // 2f79: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2f7c: aload 0
      // 2f7d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2f80: aload 0
      // 2f81: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2f84: fconst_0
      // 2f85: ldc2_w 10.0
      // 2f88: invokestatic java/lang/Math.toRadians (D)D
      // 2f8b: d2f
      // 2f8c: fconst_0
      // 2f8d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2f90: aload 0
      // 2f91: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2f94: aload 0
      // 2f95: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2f98: ldc2_w 7.5
      // 2f9b: invokestatic java/lang/Math.toRadians (D)D
      // 2f9e: d2f
      // 2f9f: fconst_0
      // 2fa0: ldc2_w -10.0
      // 2fa3: invokestatic java/lang/Math.toRadians (D)D
      // 2fa6: d2f
      // 2fa7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2faa: aload 0
      // 2fab: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2fae: aload 0
      // 2faf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2fb2: ldc2_w 50.0
      // 2fb5: invokestatic java/lang/Math.toRadians (D)D
      // 2fb8: d2f
      // 2fb9: fconst_0
      // 2fba: fconst_0
      // 2fbb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2fbe: aload 0
      // 2fbf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2fc2: aload 0
      // 2fc3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2fc6: ldc2_w -7.5
      // 2fc9: invokestatic java/lang/Math.toRadians (D)D
      // 2fcc: d2f
      // 2fcd: fconst_0
      // 2fce: fconst_0
      // 2fcf: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 2fd2: aload 0
      // 2fd3: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2fd6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 2fd9: aload 0
      // 2fda: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2fdd: bipush 1
      // 2fde: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 2fe1: aload 0
      // 2fe2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 2fe5: aload 0
      // 2fe6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 2fe9: ldc2_w 30.0
      // 2fec: invokestatic java/lang/Math.toRadians (D)D
      // 2fef: d2f
      // 2ff0: ldc2_w 2.5
      // 2ff3: invokestatic java/lang/Math.toRadians (D)D
      // 2ff6: d2f
      // 2ff7: ldc2_w 1.25
      // 2ffa: invokestatic java/lang/Math.toRadians (D)D
      // 2ffd: d2f
      // 2ffe: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3001: aload 0
      // 3002: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3005: aload 0
      // 3006: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3009: fconst_0
      // 300a: ldc_w 18.0
      // 300d: fconst_0
      // 300e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3011: aload 0
      // 3012: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3015: aload 0
      // 3016: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3019: fconst_0
      // 301a: fconst_0
      // 301b: ldc2_w 17.5
      // 301e: invokestatic java/lang/Math.toRadians (D)D
      // 3021: d2f
      // 3022: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3025: aload 0
      // 3026: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3029: aload 0
      // 302a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 302d: fconst_0
      // 302e: fconst_0
      // 302f: ldc2_w 30.0
      // 3032: invokestatic java/lang/Math.toRadians (D)D
      // 3035: d2f
      // 3036: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3039: aload 0
      // 303a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 303d: aload 0
      // 303e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3041: fconst_0
      // 3042: fconst_0
      // 3043: ldc2_w -35.0
      // 3046: invokestatic java/lang/Math.toRadians (D)D
      // 3049: d2f
      // 304a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 304d: aload 0
      // 304e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3051: aload 0
      // 3052: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3055: ldc2_w 5.0
      // 3058: invokestatic java/lang/Math.toRadians (D)D
      // 305b: d2f
      // 305c: fconst_0
      // 305d: fconst_0
      // 305e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3061: aload 0
      // 3062: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3065: aload 0
      // 3066: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3069: fconst_0
      // 306a: ldc2_w 10.0
      // 306d: invokestatic java/lang/Math.toRadians (D)D
      // 3070: d2f
      // 3071: fconst_0
      // 3072: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3075: aload 0
      // 3076: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3079: aload 0
      // 307a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 307d: ldc2_w 7.5
      // 3080: invokestatic java/lang/Math.toRadians (D)D
      // 3083: d2f
      // 3084: fconst_0
      // 3085: ldc2_w -10.0
      // 3088: invokestatic java/lang/Math.toRadians (D)D
      // 308b: d2f
      // 308c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 308f: aload 0
      // 3090: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3093: aload 0
      // 3094: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3097: ldc2_w 50.0
      // 309a: invokestatic java/lang/Math.toRadians (D)D
      // 309d: d2f
      // 309e: fconst_0
      // 309f: fconst_0
      // 30a0: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 30a3: aload 0
      // 30a4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 30a7: aload 0
      // 30a8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 30ab: ldc2_w -7.5
      // 30ae: invokestatic java/lang/Math.toRadians (D)D
      // 30b1: d2f
      // 30b2: fconst_0
      // 30b3: fconst_0
      // 30b4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 30b7: aload 0
      // 30b8: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 30bb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 30be: aload 0
      // 30bf: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 30c2: bipush 1
      // 30c3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 30c6: aload 0
      // 30c7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 30ca: aload 0
      // 30cb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 30ce: ldc2_w 30.0
      // 30d1: invokestatic java/lang/Math.toRadians (D)D
      // 30d4: d2f
      // 30d5: fconst_0
      // 30d6: fconst_0
      // 30d7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 30da: aload 0
      // 30db: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 30de: aload 0
      // 30df: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 30e2: fconst_0
      // 30e3: ldc_w 17.5
      // 30e6: fconst_0
      // 30e7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 30ea: aload 0
      // 30eb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 30ee: aload 0
      // 30ef: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 30f2: fconst_0
      // 30f3: fconst_0
      // 30f4: ldc2_w 17.5
      // 30f7: invokestatic java/lang/Math.toRadians (D)D
      // 30fa: d2f
      // 30fb: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 30fe: aload 0
      // 30ff: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3102: aload 0
      // 3103: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3106: fconst_0
      // 3107: fconst_0
      // 3108: ldc2_w 30.0
      // 310b: invokestatic java/lang/Math.toRadians (D)D
      // 310e: d2f
      // 310f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3112: aload 0
      // 3113: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3116: aload 0
      // 3117: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 311a: fconst_0
      // 311b: fconst_0
      // 311c: ldc2_w -35.0
      // 311f: invokestatic java/lang/Math.toRadians (D)D
      // 3122: d2f
      // 3123: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3126: aload 0
      // 3127: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 312a: aload 0
      // 312b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 312e: ldc2_w 5.0
      // 3131: invokestatic java/lang/Math.toRadians (D)D
      // 3134: d2f
      // 3135: fconst_0
      // 3136: fconst_0
      // 3137: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 313a: aload 0
      // 313b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 313e: aload 0
      // 313f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3142: fconst_0
      // 3143: ldc2_w 10.0
      // 3146: invokestatic java/lang/Math.toRadians (D)D
      // 3149: d2f
      // 314a: fconst_0
      // 314b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 314e: aload 0
      // 314f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3152: aload 0
      // 3153: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3156: ldc2_w 7.5
      // 3159: invokestatic java/lang/Math.toRadians (D)D
      // 315c: d2f
      // 315d: fconst_0
      // 315e: ldc2_w -10.0
      // 3161: invokestatic java/lang/Math.toRadians (D)D
      // 3164: d2f
      // 3165: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3168: aload 0
      // 3169: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 316c: aload 0
      // 316d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3170: ldc2_w 50.0
      // 3173: invokestatic java/lang/Math.toRadians (D)D
      // 3176: d2f
      // 3177: fconst_0
      // 3178: fconst_0
      // 3179: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 317c: aload 0
      // 317d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3180: aload 0
      // 3181: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3184: ldc2_w -7.5
      // 3187: invokestatic java/lang/Math.toRadians (D)D
      // 318a: d2f
      // 318b: fconst_0
      // 318c: fconst_0
      // 318d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3190: aload 0
      // 3191: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3194: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 3197: aload 0
      // 3198: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 319b: bipush 3
      // 319c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 319f: aload 0
      // 31a0: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 31a3: aload 0
      // 31a4: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 31a7: ldc2_w 5.0
      // 31aa: invokestatic java/lang/Math.toRadians (D)D
      // 31ad: d2f
      // 31ae: fconst_0
      // 31af: ldc2_w 1.5
      // 31b2: invokestatic java/lang/Math.toRadians (D)D
      // 31b5: d2f
      // 31b6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 31b9: aload 0
      // 31ba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 31bd: aload 0
      // 31be: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 31c1: fconst_0
      // 31c2: ldc_w -30.0
      // 31c5: fconst_0
      // 31c6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.move (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 31c9: aload 0
      // 31ca: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 31cd: aload 0
      // 31ce: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 31d1: fconst_0
      // 31d2: fconst_0
      // 31d3: ldc2_w 32.5
      // 31d6: invokestatic java/lang/Math.toRadians (D)D
      // 31d9: d2f
      // 31da: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 31dd: aload 0
      // 31de: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 31e1: aload 0
      // 31e2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 31e5: fconst_0
      // 31e6: fconst_0
      // 31e7: ldc2_w -40.0
      // 31ea: invokestatic java/lang/Math.toRadians (D)D
      // 31ed: d2f
      // 31ee: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 31f1: aload 0
      // 31f2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 31f5: aload 0
      // 31f6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 31f9: fconst_0
      // 31fa: fconst_0
      // 31fb: ldc2_w 35.0
      // 31fe: invokestatic java/lang/Math.toRadians (D)D
      // 3201: d2f
      // 3202: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3205: aload 0
      // 3206: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3209: aload 0
      // 320a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 320d: fconst_0
      // 320e: fconst_0
      // 320f: ldc2_w 2.5
      // 3212: invokestatic java/lang/Math.toRadians (D)D
      // 3215: d2f
      // 3216: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3219: aload 0
      // 321a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 321d: aload 0
      // 321e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3221: fconst_0
      // 3222: fconst_0
      // 3223: ldc2_w -7.5
      // 3226: invokestatic java/lang/Math.toRadians (D)D
      // 3229: d2f
      // 322a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 322d: aload 0
      // 322e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3231: aload 0
      // 3232: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3235: fconst_0
      // 3236: fconst_0
      // 3237: ldc2_w -20.0
      // 323a: invokestatic java/lang/Math.toRadians (D)D
      // 323d: d2f
      // 323e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3241: aload 0
      // 3242: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3245: aload 0
      // 3246: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3249: fconst_0
      // 324a: fconst_0
      // 324b: ldc2_w -42.5
      // 324e: invokestatic java/lang/Math.toRadians (D)D
      // 3251: d2f
      // 3252: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3255: aload 0
      // 3256: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3259: aload 0
      // 325a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 325d: ldc2_w -47.5
      // 3260: invokestatic java/lang/Math.toRadians (D)D
      // 3263: d2f
      // 3264: fconst_0
      // 3265: fconst_0
      // 3266: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3269: aload 0
      // 326a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 326d: aload 0
      // 326e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3271: fconst_0
      // 3272: ldc2_w 32.5
      // 3275: invokestatic java/lang/Math.toRadians (D)D
      // 3278: d2f
      // 3279: fconst_0
      // 327a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 327d: aload 0
      // 327e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3281: aload 0
      // 3282: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3285: ldc2_w 55.0
      // 3288: invokestatic java/lang/Math.toRadians (D)D
      // 328b: d2f
      // 328c: fconst_0
      // 328d: fconst_0
      // 328e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3291: aload 0
      // 3292: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3295: aload 0
      // 3296: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3299: ldc2_w 37.5
      // 329c: invokestatic java/lang/Math.toRadians (D)D
      // 329f: d2f
      // 32a0: fconst_0
      // 32a1: fconst_0
      // 32a2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 32a5: aload 0
      // 32a6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 32a9: aload 0
      // 32aa: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 32ad: ldc2_w 50.0
      // 32b0: invokestatic java/lang/Math.toRadians (D)D
      // 32b3: d2f
      // 32b4: fconst_0
      // 32b5: fconst_0
      // 32b6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 32b9: aload 0
      // 32ba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 32bd: aload 0
      // 32be: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 32c1: ldc2_w -7.5
      // 32c4: invokestatic java/lang/Math.toRadians (D)D
      // 32c7: d2f
      // 32c8: fconst_0
      // 32c9: fconst_0
      // 32ca: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 32cd: aload 0
      // 32ce: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 32d1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 32d4: aload 0
      // 32d5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 32d8: bipush 10
      // 32da: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.resetKeyframe (I)V
      // 32dd: aload 0
      // 32de: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 32e1: getstatic com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Amethyst_Crab_Entity.CRAB_BITE Lcom/github/L_Ender/lionfishapi/server/animation/Animation;
      // 32e4: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setAnimation (Lcom/github/L_Ender/lionfishapi/server/animation/Animation;)Z
      // 32e7: pop
      // 32e8: aload 0
      // 32e9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 32ec: bipush 12
      // 32ee: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 32f1: aload 0
      // 32f2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 32f5: aload 0
      // 32f6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 32f9: ldc2_w 7.5
      // 32fc: invokestatic java/lang/Math.toRadians (D)D
      // 32ff: d2f
      // 3300: ldc2_w -25.0
      // 3303: invokestatic java/lang/Math.toRadians (D)D
      // 3306: d2f
      // 3307: fconst_0
      // 3308: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 330b: aload 0
      // 330c: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 330f: aload 0
      // 3310: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3313: ldc2_w 15.0
      // 3316: invokestatic java/lang/Math.toRadians (D)D
      // 3319: d2f
      // 331a: fconst_0
      // 331b: ldc2_w 7.5
      // 331e: invokestatic java/lang/Math.toRadians (D)D
      // 3321: d2f
      // 3322: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3325: aload 0
      // 3326: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3329: aload 0
      // 332a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 332d: fconst_0
      // 332e: fconst_0
      // 332f: ldc2_w -10.0
      // 3332: invokestatic java/lang/Math.toRadians (D)D
      // 3335: d2f
      // 3336: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3339: aload 0
      // 333a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 333d: aload 0
      // 333e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3341: fconst_0
      // 3342: fconst_0
      // 3343: ldc2_w -15.0
      // 3346: invokestatic java/lang/Math.toRadians (D)D
      // 3349: d2f
      // 334a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 334d: aload 0
      // 334e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3351: aload 0
      // 3352: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3355: ldc2_w 12.5
      // 3358: invokestatic java/lang/Math.toRadians (D)D
      // 335b: d2f
      // 335c: ldc2_w -27.5
      // 335f: invokestatic java/lang/Math.toRadians (D)D
      // 3362: d2f
      // 3363: ldc2_w 2.5
      // 3366: invokestatic java/lang/Math.toRadians (D)D
      // 3369: d2f
      // 336a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 336d: aload 0
      // 336e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3371: aload 0
      // 3372: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3375: fconst_0
      // 3376: fconst_0
      // 3377: ldc2_w -25.0
      // 337a: invokestatic java/lang/Math.toRadians (D)D
      // 337d: d2f
      // 337e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3381: aload 0
      // 3382: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3385: aload 0
      // 3386: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3389: ldc2_w -17.5
      // 338c: invokestatic java/lang/Math.toRadians (D)D
      // 338f: d2f
      // 3390: ldc2_w 5.0
      // 3393: invokestatic java/lang/Math.toRadians (D)D
      // 3396: d2f
      // 3397: ldc2_w 27.5
      // 339a: invokestatic java/lang/Math.toRadians (D)D
      // 339d: d2f
      // 339e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 33a1: aload 0
      // 33a2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 33a5: aload 0
      // 33a6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 33a9: fconst_0
      // 33aa: fconst_0
      // 33ab: ldc2_w 17.5
      // 33ae: invokestatic java/lang/Math.toRadians (D)D
      // 33b1: d2f
      // 33b2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 33b5: aload 0
      // 33b6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 33b9: aload 0
      // 33ba: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 33bd: ldc2_w 7.5
      // 33c0: invokestatic java/lang/Math.toRadians (D)D
      // 33c3: d2f
      // 33c4: ldc2_w -12.5
      // 33c7: invokestatic java/lang/Math.toRadians (D)D
      // 33ca: d2f
      // 33cb: ldc2_w -15.0
      // 33ce: invokestatic java/lang/Math.toRadians (D)D
      // 33d1: d2f
      // 33d2: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 33d5: aload 0
      // 33d6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 33d9: aload 0
      // 33da: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 33dd: fconst_0
      // 33de: fconst_0
      // 33df: ldc2_w -2.5
      // 33e2: invokestatic java/lang/Math.toRadians (D)D
      // 33e5: d2f
      // 33e6: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 33e9: aload 0
      // 33ea: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 33ed: aload 0
      // 33ee: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 33f1: ldc2_w -2.5
      // 33f4: invokestatic java/lang/Math.toRadians (D)D
      // 33f7: d2f
      // 33f8: ldc2_w -7.5
      // 33fb: invokestatic java/lang/Math.toRadians (D)D
      // 33fe: d2f
      // 33ff: ldc2_w -12.5
      // 3402: invokestatic java/lang/Math.toRadians (D)D
      // 3405: d2f
      // 3406: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3409: aload 0
      // 340a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 340d: aload 0
      // 340e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3411: fconst_0
      // 3412: fconst_0
      // 3413: ldc2_w 22.5
      // 3416: invokestatic java/lang/Math.toRadians (D)D
      // 3419: d2f
      // 341a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 341d: aload 0
      // 341e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3421: aload 0
      // 3422: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3425: ldc2_w 20.0
      // 3428: invokestatic java/lang/Math.toRadians (D)D
      // 342b: d2f
      // 342c: ldc2_w -12.5
      // 342f: invokestatic java/lang/Math.toRadians (D)D
      // 3432: d2f
      // 3433: ldc2_w -27.5
      // 3436: invokestatic java/lang/Math.toRadians (D)D
      // 3439: d2f
      // 343a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 343d: aload 0
      // 343e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3441: aload 0
      // 3442: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3445: ldc2_w -2.5
      // 3448: invokestatic java/lang/Math.toRadians (D)D
      // 344b: d2f
      // 344c: ldc2_w -15.0
      // 344f: invokestatic java/lang/Math.toRadians (D)D
      // 3452: d2f
      // 3453: ldc2_w -7.5
      // 3456: invokestatic java/lang/Math.toRadians (D)D
      // 3459: d2f
      // 345a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 345d: aload 0
      // 345e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3461: aload 0
      // 3462: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3465: fconst_0
      // 3466: fconst_0
      // 3467: ldc2_w -17.5
      // 346a: invokestatic java/lang/Math.toRadians (D)D
      // 346d: d2f
      // 346e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3471: aload 0
      // 3472: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3475: aload 0
      // 3476: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3479: ldc2_w -22.5
      // 347c: invokestatic java/lang/Math.toRadians (D)D
      // 347f: d2f
      // 3480: ldc2_w -17.5
      // 3483: invokestatic java/lang/Math.toRadians (D)D
      // 3486: d2f
      // 3487: ldc2_w 25.0
      // 348a: invokestatic java/lang/Math.toRadians (D)D
      // 348d: d2f
      // 348e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3491: aload 0
      // 3492: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3495: aload 0
      // 3496: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3499: ldc2_w -10.0
      // 349c: invokestatic java/lang/Math.toRadians (D)D
      // 349f: d2f
      // 34a0: ldc2_w 10.0
      // 34a3: invokestatic java/lang/Math.toRadians (D)D
      // 34a6: d2f
      // 34a7: ldc2_w -12.5
      // 34aa: invokestatic java/lang/Math.toRadians (D)D
      // 34ad: d2f
      // 34ae: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 34b1: aload 0
      // 34b2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 34b5: aload 0
      // 34b6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 34b9: ldc2_w 7.5
      // 34bc: invokestatic java/lang/Math.toRadians (D)D
      // 34bf: d2f
      // 34c0: ldc2_w -52.5
      // 34c3: invokestatic java/lang/Math.toRadians (D)D
      // 34c6: d2f
      // 34c7: ldc2_w -27.5
      // 34ca: invokestatic java/lang/Math.toRadians (D)D
      // 34cd: d2f
      // 34ce: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 34d1: aload 0
      // 34d2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 34d5: aload 0
      // 34d6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 34d9: ldc2_w -2.5
      // 34dc: invokestatic java/lang/Math.toRadians (D)D
      // 34df: d2f
      // 34e0: ldc2_w 2.5
      // 34e3: invokestatic java/lang/Math.toRadians (D)D
      // 34e6: d2f
      // 34e7: ldc2_w 5.0
      // 34ea: invokestatic java/lang/Math.toRadians (D)D
      // 34ed: d2f
      // 34ee: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 34f1: aload 0
      // 34f2: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 34f5: aload 0
      // 34f6: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 34f9: ldc2_w 45.0
      // 34fc: invokestatic java/lang/Math.toRadians (D)D
      // 34ff: d2f
      // 3500: fconst_0
      // 3501: fconst_0
      // 3502: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3505: aload 0
      // 3506: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3509: aload 0
      // 350a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 350d: ldc2_w -17.5
      // 3510: invokestatic java/lang/Math.toRadians (D)D
      // 3513: d2f
      // 3514: fconst_0
      // 3515: fconst_0
      // 3516: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3519: aload 0
      // 351a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 351d: aload 0
      // 351e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3521: ldc2_w -52.5
      // 3524: invokestatic java/lang/Math.toRadians (D)D
      // 3527: d2f
      // 3528: ldc2_w -40.0
      // 352b: invokestatic java/lang/Math.toRadians (D)D
      // 352e: d2f
      // 352f: ldc2_w 27.5
      // 3532: invokestatic java/lang/Math.toRadians (D)D
      // 3535: d2f
      // 3536: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3539: aload 0
      // 353a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 353d: aload 0
      // 353e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3541: fconst_0
      // 3542: ldc2_w 12.5
      // 3545: invokestatic java/lang/Math.toRadians (D)D
      // 3548: d2f
      // 3549: fconst_0
      // 354a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 354d: aload 0
      // 354e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3551: aload 0
      // 3552: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3555: ldc2_w 7.5
      // 3558: invokestatic java/lang/Math.toRadians (D)D
      // 355b: d2f
      // 355c: fconst_0
      // 355d: fconst_0
      // 355e: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3561: aload 0
      // 3562: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3565: aload 0
      // 3566: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3569: fconst_0
      // 356a: ldc2_w -5.0
      // 356d: invokestatic java/lang/Math.toRadians (D)D
      // 3570: d2f
      // 3571: fconst_0
      // 3572: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3575: aload 0
      // 3576: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3579: aload 0
      // 357a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 357d: fconst_0
      // 357e: ldc2_w -7.5
      // 3581: invokestatic java/lang/Math.toRadians (D)D
      // 3584: d2f
      // 3585: fconst_0
      // 3586: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3589: aload 0
      // 358a: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 358d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 3590: aload 0
      // 3591: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3594: bipush 3
      // 3595: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 3598: aload 0
      // 3599: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 359c: bipush 3
      // 359d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.startKeyframe (I)V
      // 35a0: aload 0
      // 35a1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 35a4: aload 0
      // 35a5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.root Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 35a8: ldc2_w 5.0
      // 35ab: invokestatic java/lang/Math.toRadians (D)D
      // 35ae: d2f
      // 35af: ldc2_w 32.5
      // 35b2: invokestatic java/lang/Math.toRadians (D)D
      // 35b5: d2f
      // 35b6: ldc2_w -5.0
      // 35b9: invokestatic java/lang/Math.toRadians (D)D
      // 35bc: d2f
      // 35bd: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 35c0: aload 0
      // 35c1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 35c4: aload 0
      // 35c5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 35c8: fconst_0
      // 35c9: ldc2_w 5.0
      // 35cc: invokestatic java/lang/Math.toRadians (D)D
      // 35cf: d2f
      // 35d0: ldc2_w 2.5
      // 35d3: invokestatic java/lang/Math.toRadians (D)D
      // 35d6: d2f
      // 35d7: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 35da: aload 0
      // 35db: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 35de: aload 0
      // 35df: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 35e2: fconst_0
      // 35e3: ldc2_w 5.0
      // 35e6: invokestatic java/lang/Math.toRadians (D)D
      // 35e9: d2f
      // 35ea: ldc2_w 17.5
      // 35ed: invokestatic java/lang/Math.toRadians (D)D
      // 35f0: d2f
      // 35f1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 35f4: aload 0
      // 35f5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 35f8: aload 0
      // 35f9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_eye Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 35fc: fconst_0
      // 35fd: fconst_0
      // 35fe: ldc2_w 12.5
      // 3601: invokestatic java/lang/Math.toRadians (D)D
      // 3604: d2f
      // 3605: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3608: aload 0
      // 3609: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 360c: aload 0
      // 360d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3610: ldc2_w -17.5
      // 3613: invokestatic java/lang/Math.toRadians (D)D
      // 3616: d2f
      // 3617: ldc2_w 20.0
      // 361a: invokestatic java/lang/Math.toRadians (D)D
      // 361d: d2f
      // 361e: ldc2_w -37.5
      // 3621: invokestatic java/lang/Math.toRadians (D)D
      // 3624: d2f
      // 3625: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3628: aload 0
      // 3629: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 362c: aload 0
      // 362d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3630: fconst_0
      // 3631: fconst_0
      // 3632: ldc2_w -20.0
      // 3635: invokestatic java/lang/Math.toRadians (D)D
      // 3638: d2f
      // 3639: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 363c: aload 0
      // 363d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3640: aload 0
      // 3641: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3644: ldc2_w -2.5
      // 3647: invokestatic java/lang/Math.toRadians (D)D
      // 364a: d2f
      // 364b: ldc2_w -17.5
      // 364e: invokestatic java/lang/Math.toRadians (D)D
      // 3651: d2f
      // 3652: ldc2_w 7.5
      // 3655: invokestatic java/lang/Math.toRadians (D)D
      // 3658: d2f
      // 3659: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 365c: aload 0
      // 365d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3660: aload 0
      // 3661: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3664: fconst_0
      // 3665: fconst_0
      // 3666: ldc2_w 32.5
      // 3669: invokestatic java/lang/Math.toRadians (D)D
      // 366c: d2f
      // 366d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3670: aload 0
      // 3671: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3674: aload 0
      // 3675: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3678: ldc2_w 2.5
      // 367b: invokestatic java/lang/Math.toRadians (D)D
      // 367e: d2f
      // 367f: ldc2_w 17.5
      // 3682: invokestatic java/lang/Math.toRadians (D)D
      // 3685: d2f
      // 3686: ldc2_w 2.5
      // 3689: invokestatic java/lang/Math.toRadians (D)D
      // 368c: d2f
      // 368d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3690: aload 0
      // 3691: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3694: aload 0
      // 3695: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3698: fconst_0
      // 3699: fconst_0
      // 369a: ldc2_w 7.5
      // 369d: invokestatic java/lang/Math.toRadians (D)D
      // 36a0: d2f
      // 36a1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 36a4: aload 0
      // 36a5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 36a8: aload 0
      // 36a9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 36ac: ldc2_w -2.5
      // 36af: invokestatic java/lang/Math.toRadians (D)D
      // 36b2: d2f
      // 36b3: ldc2_w -20.0
      // 36b6: invokestatic java/lang/Math.toRadians (D)D
      // 36b9: d2f
      // 36ba: ldc2_w 2.5
      // 36bd: invokestatic java/lang/Math.toRadians (D)D
      // 36c0: d2f
      // 36c1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 36c4: aload 0
      // 36c5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 36c8: aload 0
      // 36c9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 36cc: fconst_0
      // 36cd: fconst_0
      // 36ce: ldc2_w 7.5
      // 36d1: invokestatic java/lang/Math.toRadians (D)D
      // 36d4: d2f
      // 36d5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 36d8: aload 0
      // 36d9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 36dc: aload 0
      // 36dd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 36e0: ldc2_w 2.5
      // 36e3: invokestatic java/lang/Math.toRadians (D)D
      // 36e6: d2f
      // 36e7: ldc2_w 7.5
      // 36ea: invokestatic java/lang/Math.toRadians (D)D
      // 36ed: d2f
      // 36ee: ldc2_w -15.0
      // 36f1: invokestatic java/lang/Math.toRadians (D)D
      // 36f4: d2f
      // 36f5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 36f8: aload 0
      // 36f9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 36fc: aload 0
      // 36fd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3700: fconst_0
      // 3701: fconst_0
      // 3702: ldc2_w 35.0
      // 3705: invokestatic java/lang/Math.toRadians (D)D
      // 3708: d2f
      // 3709: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 370c: aload 0
      // 370d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3710: aload 0
      // 3711: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3714: ldc2_w -7.5
      // 3717: invokestatic java/lang/Math.toRadians (D)D
      // 371a: d2f
      // 371b: ldc2_w 5.0
      // 371e: invokestatic java/lang/Math.toRadians (D)D
      // 3721: d2f
      // 3722: ldc2_w 7.5
      // 3725: invokestatic java/lang/Math.toRadians (D)D
      // 3728: d2f
      // 3729: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 372c: aload 0
      // 372d: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3730: aload 0
      // 3731: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_joint3 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3734: fconst_0
      // 3735: fconst_0
      // 3736: ldc2_w 5.0
      // 3739: invokestatic java/lang/Math.toRadians (D)D
      // 373c: d2f
      // 373d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3740: aload 0
      // 3741: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3744: aload 0
      // 3745: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3748: ldc2_w -36.5
      // 374b: invokestatic java/lang/Math.toRadians (D)D
      // 374e: d2f
      // 374f: ldc2_w -17.5
      // 3752: invokestatic java/lang/Math.toRadians (D)D
      // 3755: d2f
      // 3756: ldc2_w 37.5
      // 3759: invokestatic java/lang/Math.toRadians (D)D
      // 375c: d2f
      // 375d: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3760: aload 0
      // 3761: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3764: aload 0
      // 3765: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_leg4 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3768: fconst_0
      // 3769: ldc2_w 7.5
      // 376c: invokestatic java/lang/Math.toRadians (D)D
      // 376f: d2f
      // 3770: fconst_0
      // 3771: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3774: aload 0
      // 3775: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3778: aload 0
      // 3779: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 377c: ldc2_w 7.5
      // 377f: invokestatic java/lang/Math.toRadians (D)D
      // 3782: d2f
      // 3783: ldc2_w -5.0
      // 3786: invokestatic java/lang/Math.toRadians (D)D
      // 3789: d2f
      // 378a: ldc2_w -2.5
      // 378d: invokestatic java/lang/Math.toRadians (D)D
      // 3790: d2f
      // 3791: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3794: aload 0
      // 3795: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3798: aload 0
      // 3799: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 379c: ldc2_w -2.5
      // 379f: invokestatic java/lang/Math.toRadians (D)D
      // 37a2: d2f
      // 37a3: ldc2_w 35.0
      // 37a6: invokestatic java/lang/Math.toRadians (D)D
      // 37a9: d2f
      // 37aa: ldc2_w 7.5
      // 37ad: invokestatic java/lang/Math.toRadians (D)D
      // 37b0: d2f
      // 37b1: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 37b4: aload 0
      // 37b5: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 37b8: aload 0
      // 37b9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 37bc: ldc2_w 30.0
      // 37bf: invokestatic java/lang/Math.toRadians (D)D
      // 37c2: d2f
      // 37c3: fconst_0
      // 37c4: fconst_0
      // 37c5: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 37c8: aload 0
      // 37c9: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 37cc: aload 0
      // 37cd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw2 Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 37d0: ldc2_w -27.5
      // 37d3: invokestatic java/lang/Math.toRadians (D)D
      // 37d6: d2f
      // 37d7: fconst_0
      // 37d8: fconst_0
      // 37d9: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 37dc: aload 0
      // 37dd: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 37e0: aload 0
      // 37e1: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_tong_leg Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 37e4: fconst_0
      // 37e5: ldc2_w 50.0
      // 37e8: invokestatic java/lang/Math.toRadians (D)D
      // 37eb: d2f
      // 37ec: ldc2_w 22.5
      // 37ef: invokestatic java/lang/Math.toRadians (D)D
      // 37f2: d2f
      // 37f3: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 37f6: aload 0
      // 37f7: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 37fa: aload 0
      // 37fb: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_Tong Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 37fe: ldc2_w 50.0
      // 3801: invokestatic java/lang/Math.toRadians (D)D
      // 3804: d2f
      // 3805: ldc2_w -12.5
      // 3808: invokestatic java/lang/Math.toRadians (D)D
      // 380b: d2f
      // 380c: ldc2_w 72.5
      // 380f: invokestatic java/lang/Math.toRadians (D)D
      // 3812: d2f
      // 3813: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3816: aload 0
      // 3817: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 381a: aload 0
      // 381b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.upper_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 381e: ldc2_w 32.5
      // 3821: invokestatic java/lang/Math.toRadians (D)D
      // 3824: d2f
      // 3825: fconst_0
      // 3826: fconst_0
      // 3827: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 382a: aload 0
      // 382b: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 382e: aload 0
      // 382f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.under_claw Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3832: ldc2_w -35.0
      // 3835: invokestatic java/lang/Math.toRadians (D)D
      // 3838: d2f
      // 3839: fconst_0
      // 383a: fconst_0
      // 383b: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 383e: aload 0
      // 383f: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3842: aload 0
      // 3843: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.left_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 3846: fconst_0
      // 3847: ldc2_w 5.0
      // 384a: invokestatic java/lang/Math.toRadians (D)D
      // 384d: d2f
      // 384e: fconst_0
      // 384f: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3852: aload 0
      // 3853: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3856: aload 0
      // 3857: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.right_body Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;
      // 385a: fconst_0
      // 385b: ldc2_w 2.5
      // 385e: invokestatic java/lang/Math.toRadians (D)D
      // 3861: d2f
      // 3862: fconst_0
      // 3863: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.rotate (Lcom/github/L_Ender/lionfishapi/client/model/tools/AdvancedModelBox;FFF)V
      // 3866: aload 0
      // 3867: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 386a: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.endKeyframe ()V
      // 386d: aload 0
      // 386e: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 3871: bipush 15
      // 3873: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.setStaticKeyframe (I)V
      // 3876: aload 0
      // 3877: getfield com/github/L_Ender/cataclysm/client/model/entity/Amethyst_Crab_Model.animator Lcom/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator;
      // 387a: bipush 15
      // 387c: invokevirtual com/github/L_Ender/lionfishapi/client/model/Animations/ModelAnimator.resetKeyframe (I)V
      // 387f: return
   }

   public void setupAnim(Amethyst_Crab_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float globalSpeed = 0.35F;
      float Speed = 0.1F;
      float globalDegree = 0.5F;
      float Degree = 0.1F;
      this.bob(this.body, globalSpeed * 0.2F, globalDegree, false, ageInTicks, 1.0F);
      this.bob(this.body, globalSpeed, globalDegree * 1.6F, false, limbSwing, limbSwingAmount);
      this.flap(this.body, globalSpeed, globalDegree * 0.2F, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.swing(this.right_tong_leg, Speed * 3.0F, Degree, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.swing(this.left_tong_leg, Speed * 3.0F, Degree, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.swing(this.right_Tong, Speed * 3.0F, globalDegree, true, 0.0F, 0.5F, limbSwing, limbSwingAmount);
      this.swing(this.left_Tong, Speed * 3.0F, Degree * 0.5F, false, 0.0F, 0.05F, limbSwing, limbSwingAmount);
      this.walk(this.upper_claw, globalSpeed * 0.1F, Degree * 2.0F, false, 0.0F, 0.1F, ageInTicks, 1.0F);
      this.walk(this.under_claw, globalSpeed * 0.1F, Degree * 2.0F, true, 0.0F, 0.1F, ageInTicks, 1.0F);
      this.walk(this.upper_claw2, globalSpeed * 0.1F, Degree * 2.0F, false, 0.0F, 0.1F, ageInTicks, 1.0F);
      this.walk(this.under_claw2, globalSpeed * 0.1F, Degree * 2.0F, true, 0.0F, 0.1F, ageInTicks, 1.0F);
      this.flap(this.left_eye, Speed, globalDegree * 0.4F, false, 0.0F, -0.1F, ageInTicks, 1.0F);
      this.flap(this.right_eye, Speed, globalDegree * 0.4F, true, 0.0F, -0.1F, ageInTicks, 1.0F);
      this.swing(this.left_leg, globalSpeed, globalDegree * 1.6F, false, 0.0F, 0.1F, limbSwing, limbSwingAmount);
      this.flap(this.left_leg, globalSpeed, globalDegree * 0.6F, false, 0.0F, -0.25F, limbSwing, limbSwingAmount);
      this.swing(this.left_leg2, globalSpeed, globalDegree * 1.6F, false, 1.57F, 0.1F, limbSwing, limbSwingAmount);
      this.flap(this.left_leg2, globalSpeed, globalDegree * 0.6F, false, 1.57F, -0.25F, limbSwing, limbSwingAmount);
      this.swing(this.left_leg3, globalSpeed, globalDegree * 1.6F, false, 3.14F, 0.1F, limbSwing, limbSwingAmount);
      this.flap(this.left_leg3, globalSpeed, globalDegree * 0.6F, false, 3.14F, -0.25F, limbSwing, limbSwingAmount);
      this.swing(this.left_leg4, globalSpeed, globalDegree * 1.6F, false, 4.71F, 0.1F, limbSwing, limbSwingAmount);
      this.swing(this.right_leg, globalSpeed, globalDegree * 1.6F, false, 0.0F, -0.1F, limbSwing, limbSwingAmount);
      this.flap(this.right_leg, globalSpeed, globalDegree * 0.6F, false, 0.0F, 0.25F, limbSwing, limbSwingAmount);
      this.swing(this.right_leg2, globalSpeed, globalDegree * 1.6F, false, 1.57F, -0.1F, limbSwing, limbSwingAmount);
      this.flap(this.right_leg2, globalSpeed, globalDegree * 0.6F, false, 1.57F, 0.25F, limbSwing, limbSwingAmount);
      this.swing(this.right_leg3, globalSpeed, globalDegree * 1.6F, false, 3.14F, -0.1F, limbSwing, limbSwingAmount);
      this.flap(this.right_leg3, globalSpeed, globalDegree * 0.6F, false, 3.14F, 0.25F, limbSwing, limbSwingAmount);
      this.swing(this.right_leg4, globalSpeed, globalDegree * 1.6F, false, 4.71F, -0.1F, limbSwing, limbSwingAmount);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
