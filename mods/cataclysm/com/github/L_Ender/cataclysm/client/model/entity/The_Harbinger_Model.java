package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;

public class The_Harbinger_Model extends AdvancedEntityModel<The_Harbinger_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox mid;
   private final AdvancedModelBox main_head;
   private final AdvancedModelBox head;
   private final AdvancedModelBox headgear;
   private final AdvancedModelBox eyebrows;
   private final AdvancedModelBox jaw;
   private final AdvancedModelBox righthead;
   private final AdvancedModelBox rightlaser;
   private final AdvancedModelBox rightlaser2;
   private final AdvancedModelBox right_guard;
   private final AdvancedModelBox right_upper_guard;
   private final AdvancedModelBox right_lower_guard;
   private final AdvancedModelBox lefthead;
   private final AdvancedModelBox left_side_guard;
   private final AdvancedModelBox left_upper_guard;
   private final AdvancedModelBox left_lower_guard;
   private final AdvancedModelBox leftlaser;
   private final AdvancedModelBox leftlaser2;
   private final AdvancedModelBox body;
   public final AdvancedModelBox nether_star;
   private final AdvancedModelBox tail;
   private final AdvancedModelBox tailbone;
   private final AdvancedModelBox jetpack;
   private final AdvancedModelBox rightside;
   private final AdvancedModelBox rightedge1;
   private final AdvancedModelBox rightedge2;
   private final AdvancedModelBox rightedge3;
   private final AdvancedModelBox right_jet;
   private final AdvancedModelBox leftside;
   private final AdvancedModelBox leftedge1;
   private final AdvancedModelBox leftedge2;
   private final AdvancedModelBox leftedge3;
   private final AdvancedModelBox left_jet;
   private ModelAnimator animator;

   public The_Harbinger_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.mid = new AdvancedModelBox(this);
      this.mid.setRotationPoint(0.0F, -15.0F, 0.0F);
      this.root.addChild(this.mid);
      this.main_head = new AdvancedModelBox(this);
      this.main_head.setRotationPoint(0.0F, -7.25F, 2.0F);
      this.mid.addChild(this.main_head);
      this.setRotationAngle(this.main_head, 0.2182F, 0.0F, 0.0F);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -1.0F, 0.0F);
      this.main_head.addChild(this.head);
      this.head.setTextureOffset(0, 42).addBox(-4.0F, -6.0F, -6.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
      this.headgear = new AdvancedModelBox(this);
      this.headgear.setRotationPoint(-0.5F, -5.0F, 1.0F);
      this.head.addChild(this.headgear);
      this.headgear.setTextureOffset(30, 11).addBox(-3.5F, 0.0F, -8.0F, 8.0F, 5.0F, 9.0F, 0.2F, false);
      this.headgear.setTextureOffset(51, 7).addBox(-1.5F, 2.0F, -8.0F, 4.0F, 2.0F, 1.0F, 0.15F, false);
      this.headgear.setTextureOffset(76, 73).addBox(-5.75F, -0.25F, -6.0F, 2.0F, 6.0F, 5.0F, 0.0F, false);
      this.headgear.setTextureOffset(76, 73).addBox(4.75F, -0.25F, -6.0F, 2.0F, 6.0F, 5.0F, 0.0F, true);
      this.eyebrows = new AdvancedModelBox(this);
      this.eyebrows.setRotationPoint(-0.5F, 3.0F, -2.0F);
      this.head.addChild(this.eyebrows);
      this.eyebrows.setTextureOffset(39, 5).addBox(-2.5F, -7.5F, -4.25F, 6.0F, 2.0F, 0.0F, 0.0F, false);
      this.jaw = new AdvancedModelBox(this);
      this.jaw.setRotationPoint(0.0F, 0.25F, 0.0F);
      this.main_head.addChild(this.jaw);
      this.jaw.setTextureOffset(58, 18).addBox(-4.0F, 1.0F, -6.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);
      this.jaw.setTextureOffset(33, 26).addBox(-4.0F, -6.0F, -6.0F, 8.0F, 8.0F, 8.0F, -0.01F, false);
      this.righthead = new AdvancedModelBox(this);
      this.righthead.setRotationPoint(-10.0F, -5.0F, 1.0F);
      this.mid.addChild(this.righthead);
      this.setRotationAngle(this.righthead, 0.3491F, 0.1309F, 0.0F);
      this.righthead.setTextureOffset(51, 73).addBox(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
      this.rightlaser = new AdvancedModelBox(this);
      this.rightlaser.setRotationPoint(-1.0F, -1.0F, 1.5F);
      this.righthead.addChild(this.rightlaser);
      this.rightlaser.setTextureOffset(77, 50).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 3.0F, 0.0F, false);
      this.rightlaser2 = new AdvancedModelBox(this);
      this.rightlaser2.setRotationPoint(0.0F, 0.0F, -0.5F);
      this.rightlaser.addChild(this.rightlaser2);
      this.rightlaser2.setTextureOffset(25, 43).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
      this.rightlaser2.setTextureOffset(50, 56).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 2.0F, 0.3F, false);
      this.right_guard = new AdvancedModelBox(this);
      this.right_guard.setRotationPoint(8.0F, 20.0F, 2.25F);
      this.righthead.addChild(this.right_guard);
      this.right_upper_guard = new AdvancedModelBox(this);
      this.right_upper_guard.setRotationPoint(-9.0F, -22.0F, -7.0F);
      this.right_guard.addChild(this.right_upper_guard);
      this.right_upper_guard.setTextureOffset(33, 43).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 4.0F, 8.0F, 0.0F, false);
      this.right_lower_guard = new AdvancedModelBox(this);
      this.right_lower_guard.setRotationPoint(-9.0F, -20.75F, -6.0F);
      this.right_guard.addChild(this.right_lower_guard);
      this.right_lower_guard.setTextureOffset(0, 60).addBox(-3.0F, -0.25F, -1.0F, 6.0F, 3.0F, 8.0F, 0.1F, false);
      this.lefthead = new AdvancedModelBox(this);
      this.lefthead.setRotationPoint(10.0F, -5.0F, 1.0F);
      this.mid.addChild(this.lefthead);
      this.setRotationAngle(this.lefthead, 0.3491F, -0.1309F, 0.0F);
      this.lefthead.setTextureOffset(51, 73).addBox(-2.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, 0.0F, true);
      this.left_side_guard = new AdvancedModelBox(this);
      this.left_side_guard.setRotationPoint(1.0F, -1.0F, -0.75F);
      this.lefthead.addChild(this.left_side_guard);
      this.left_upper_guard = new AdvancedModelBox(this);
      this.left_upper_guard.setRotationPoint(0.0F, -1.0F, -4.0F);
      this.left_side_guard.addChild(this.left_upper_guard);
      this.left_upper_guard.setTextureOffset(33, 43).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 4.0F, 8.0F, 0.0F, true);
      this.left_lower_guard = new AdvancedModelBox(this);
      this.left_lower_guard.setRotationPoint(0.0F, 0.25F, -3.0F);
      this.left_side_guard.addChild(this.left_lower_guard);
      this.left_lower_guard.setTextureOffset(0, 60).addBox(-3.0F, -0.25F, -1.0F, 6.0F, 3.0F, 8.0F, 0.1F, true);
      this.leftlaser = new AdvancedModelBox(this);
      this.leftlaser.setRotationPoint(1.0F, -1.0F, 1.5F);
      this.lefthead.addChild(this.leftlaser);
      this.leftlaser.setTextureOffset(77, 50).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 3.0F, 0.0F, false);
      this.leftlaser2 = new AdvancedModelBox(this);
      this.leftlaser2.setRotationPoint(0.0F, 0.0F, -2.5F);
      this.leftlaser.addChild(this.leftlaser2);
      this.leftlaser2.setTextureOffset(25, 43).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
      this.leftlaser2.setTextureOffset(50, 56).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.3F, false);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0F, -5.0F, 2.0F);
      this.mid.addChild(this.body);
      this.setRotationAngle(this.body, 0.3491F, 0.0F, 0.0F);
      this.body.setTextureOffset(32, 0).addBox(-9.0F, -0.4434F, -1.1553F, 18.0F, 2.0F, 2.0F, 0.0F, false);
      this.body.setTextureOffset(0, 0).addBox(-6.0F, 1.0566F, -7.1553F, 12.0F, 12.0F, 7.0F, 0.0F, false);
      this.body.setTextureOffset(17, 78).addBox(-1.0F, 0.5566F, -1.1553F, 2.0F, 13.0F, 2.0F, 0.0F, false);
      this.body.setTextureOffset(24, 68).addBox(-4.0F, 2.5566F, -5.6553F, 8.0F, 6.0F, 5.0F, 0.0F, false);
      this.body.setTextureOffset(26, 80).addBox(-1.0F, 0.5F, 0.75F, 2.0F, 4.0F, 4.0F, 0.0F, false);
      this.nether_star = new AdvancedModelBox(this);
      this.nether_star.setRotationPoint(0.0F, 3.0F, -2.75F);
      this.body.addChild(this.nether_star);
      this.tail = new AdvancedModelBox(this);
      this.tail.setRotationPoint(0.0F, 13.5566F, -0.1553F);
      this.body.addChild(this.tail);
      this.setRotationAngle(this.tail, 0.5236F, 0.0F, 0.0F);
      this.tail.setTextureOffset(81, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
      this.tail.setTextureOffset(60, 37).addBox(-4.0F, -2.0F, -6.0F, 8.0F, 6.0F, 6.0F, 0.0F, false);
      this.tailbone = new AdvancedModelBox(this);
      this.tailbone.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.tail.addChild(this.tailbone);
      this.setRotationAngle(this.tailbone, 0.5672F, 0.0F, 0.0F);
      this.tailbone.setTextureOffset(39, 80).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 4.0F, 0.0F, false);
      this.jetpack = new AdvancedModelBox(this);
      this.jetpack.setRotationPoint(0.0F, 3.0F, 4.5F);
      this.body.addChild(this.jetpack);
      this.setRotationAngle(this.jetpack, 0.2618F, 0.0F, 0.0F);
      this.rightside = new AdvancedModelBox(this);
      this.rightside.setRotationPoint(0.0F, 0.5846F, -0.1554F);
      this.jetpack.addChild(this.rightside);
      this.setRotationAngle(this.rightside, -0.0436F, -0.9163F, 0.1745F);
      this.rightside.setTextureOffset(0, 20).addBox(-3.8071F, -11.8715F, 8.293F, 8.0F, 13.0F, 8.0F, 0.0F, false);
      this.rightside.setTextureOffset(66, 27).addBox(-2.8071F, 1.1285F, 9.293F, 6.0F, 3.0F, 6.0F, 0.0F, false);
      this.rightside.setTextureOffset(25, 56).addBox(-3.8071F, -2.1215F, 8.293F, 8.0F, 3.0F, 8.0F, 0.5F, false);
      this.rightside.setTextureOffset(56, 5).addBox(-3.8071F, -6.1215F, 8.293F, 8.0F, 3.0F, 8.0F, 0.5F, false);
      this.rightside.setTextureOffset(0, 72).addBox(0.1929F, -4.8715F, 0.293F, 0.0F, 8.0F, 8.0F, 0.0F, false);
      this.rightedge1 = new AdvancedModelBox(this);
      this.rightedge1.setRotationPoint(-1.25F, 3.0F, 13.25F);
      this.rightside.addChild(this.rightedge1);
      this.setRotationAngle(this.rightedge1, 0.0F, 1.5708F, 0.0F);
      this.rightedge1.setTextureOffset(58, 56).addBox(1.0F, -5.0F, 1.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
      this.rightedge2 = new AdvancedModelBox(this);
      this.rightedge2.setRotationPoint(-1.25F, 3.0F, 11.25F);
      this.rightside.addChild(this.rightedge2);
      this.rightedge2.setTextureOffset(58, 56).addBox(1.0F, -5.0F, 1.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
      this.rightedge3 = new AdvancedModelBox(this);
      this.rightedge3.setRotationPoint(0.75F, 3.0F, 11.25F);
      this.rightside.addChild(this.rightedge3);
      this.setRotationAngle(this.rightedge3, 0.0F, -1.5708F, 0.0F);
      this.rightedge3.setTextureOffset(58, 56).addBox(1.0F, -5.0F, 1.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
      this.right_jet = new AdvancedModelBox(this);
      this.right_jet.setRotationPoint(2.1929F, 1.1285F, 14.293F);
      this.rightside.addChild(this.right_jet);
      this.leftside = new AdvancedModelBox(this);
      this.leftside.setRotationPoint(0.0F, 0.5846F, -0.1554F);
      this.jetpack.addChild(this.leftside);
      this.setRotationAngle(this.leftside, -0.0436F, 0.9163F, -0.1745F);
      this.leftside.setTextureOffset(0, 20).addBox(-4.1929F, -11.8715F, 8.293F, 8.0F, 13.0F, 8.0F, 0.0F, true);
      this.leftside.setTextureOffset(66, 27).addBox(-3.1929F, 1.1285F, 9.293F, 6.0F, 3.0F, 6.0F, 0.0F, true);
      this.leftside.setTextureOffset(25, 56).addBox(-4.1929F, -2.1215F, 8.293F, 8.0F, 3.0F, 8.0F, 0.5F, true);
      this.leftside.setTextureOffset(56, 5).addBox(-4.1929F, -6.1215F, 8.293F, 8.0F, 3.0F, 8.0F, 0.5F, true);
      this.leftside.setTextureOffset(0, 72).addBox(-0.1929F, -4.8715F, 0.293F, 0.0F, 8.0F, 8.0F, 0.0F, true);
      this.leftedge1 = new AdvancedModelBox(this);
      this.leftedge1.setRotationPoint(-1.25F, 3.0F, 11.25F);
      this.leftside.addChild(this.leftedge1);
      this.leftedge1.setTextureOffset(58, 56).addBox(1.0F, -5.0F, 1.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
      this.leftedge2 = new AdvancedModelBox(this);
      this.leftedge2.setRotationPoint(0.75F, 3.0F, 11.25F);
      this.leftside.addChild(this.leftedge2);
      this.setRotationAngle(this.leftedge2, 0.0F, -1.5708F, 0.0F);
      this.leftedge2.setTextureOffset(58, 56).addBox(1.0F, -5.0F, 1.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
      this.leftedge3 = new AdvancedModelBox(this);
      this.leftedge3.setRotationPoint(0.75F, 3.0F, 13.25F);
      this.leftside.addChild(this.leftedge3);
      this.setRotationAngle(this.leftedge3, 0.0F, 3.1416F, 0.0F);
      this.leftedge3.setTextureOffset(58, 56).addBox(1.0F, -5.0F, 1.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
      this.left_jet = new AdvancedModelBox(this);
      this.left_jet.setRotationPoint(-2.1929F, 1.1285F, 14.293F);
      this.leftside.addChild(this.left_jet);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void animate(The_Harbinger_Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(The_Harbinger_Entity.DEATHLASER_ANIMATION);
      this.animator.startKeyframe(25);
      this.animator.rotate(this.mid, (float)Math.toRadians(-37.5), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-17.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(-7.5), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(30.0), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(12.5), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(60);
      this.animator.startKeyframe(10);
      this.animator.rotate(this.mid, (float)Math.toRadians(-12.5), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.move(this.main_head, 0.0F, -1.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(30.0), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(-52.5), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(6);
      this.animator.resetKeyframe(10);
      this.animator.setAnimation(The_Harbinger_Entity.CHARGE_ANIMATION);
      this.animator.startKeyframe(12);
      this.animator.rotate(this.mid, (float)Math.toRadians(15.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-17.5), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
      this.animator.rotate(this.lefthead, (float)Math.toRadians(-32.5), 0.0F, 0.0F);
      this.animator.rotate(this.righthead, (float)Math.toRadians(-32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(5.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.mid, (float)Math.toRadians(30.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
      this.animator.rotate(this.lefthead, (float)Math.toRadians(-37.5), 0.0F, 0.0F);
      this.animator.rotate(this.righthead, (float)Math.toRadians(-37.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(10.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.resetKeyframe(10);
      this.animator.setAnimation(The_Harbinger_Entity.DEATH_ANIMATION);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-40.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(37.5), 0.0F, 0.0F);
      this.animator.rotate(this.righthead, 0.0F, (float)Math.toRadians(30.0), 0.0F);
      this.animator.rotate(this.lefthead, (float)Math.toRadians(2.5), (float)Math.toRadians(-35.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(5);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-47.5), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(37.5), 0.0F, 0.0F);
      this.animator.rotate(this.righthead, (float)Math.toRadians(55.0), (float)Math.toRadians(72.5), 0.0F);
      this.animator.rotate(this.lefthead, (float)Math.toRadians(55.0), (float)Math.toRadians(-85.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(129);
      this.animator.setAnimation(The_Harbinger_Entity.LAUNCH_ANIAMATION);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.mid, (float)Math.toRadians(-17.5), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-17.5), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.righthead, (float)Math.toRadians(-70.0), 0.0F, 0.0F);
      this.animator.rotate(this.lefthead, (float)Math.toRadians(-70.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(5);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.mid, (float)Math.toRadians(-17.5), 0.0F, (float)Math.toRadians(-5.0));
      this.animator.rotate(this.main_head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(20.0), 0.0F, 0.0F);
      this.animator.rotate(this.righthead, (float)Math.toRadians(-70.0), 0.0F, 0.0F);
      this.animator.move(this.righthead, 0.0F, 2.0F, 0.0F);
      this.animator.rotate(this.lefthead, (float)Math.toRadians(-70.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(3);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.mid, (float)Math.toRadians(-17.5), 0.0F, (float)Math.toRadians(5.0));
      this.animator.rotate(this.main_head, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(25.0), 0.0F, 0.0F);
      this.animator.rotate(this.righthead, (float)Math.toRadians(-70.0), 0.0F, 0.0F);
      this.animator.move(this.righthead, 0.0F, 2.0F, 0.0F);
      this.animator.rotate(this.lefthead, (float)Math.toRadians(-70.0), 0.0F, 0.0F);
      this.animator.move(this.lefthead, 0.0F, 3.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(5);
      this.animator.startKeyframe(10);
      this.animator.rotate(this.mid, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(25.0), 0.0F, 0.0F);
      this.animator.rotate(this.righthead, (float)Math.toRadians(-60.0), 0.0F, 0.0F);
      this.animator.move(this.righthead, 0.0F, 1.0F, 0.0F);
      this.animator.rotate(this.lefthead, (float)Math.toRadians(-60.0), 0.0F, 0.0F);
      this.animator.move(this.lefthead, 0.0F, 2.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(5);
      this.animator.resetKeyframe(10);
      this.animator.setAnimation(The_Harbinger_Entity.MISSILE_FIRE_ANIAMATION);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.mid, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(8);
      this.animator.rotate(this.mid, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(50.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(12);
      this.animator.rotate(this.mid, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(-17.5), 0.0F, 0.0F);
      this.animator.rotate(this.rightside, (float)Math.toRadians(40.0), (float)Math.toRadians(92.5), (float)Math.toRadians(-145.0));
      this.animator.rotate(this.leftside, (float)Math.toRadians(40.0), (float)Math.toRadians(-92.5), (float)Math.toRadians(145.0));
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(12);
      this.animator.rotate(this.mid, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(70.0), 0.0F, 0.0F);
      this.animator.rotate(this.rightside, (float)Math.toRadians(67.5), (float)Math.toRadians(72.5), (float)Math.toRadians(-145.0));
      this.animator.rotate(this.leftside, (float)Math.toRadians(67.5), (float)Math.toRadians(-72.5), (float)Math.toRadians(145.0));
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.mid, (float)Math.toRadians(35.0), (float)Math.toRadians(-7.5), 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(70.0), 0.0F, 0.0F);
      this.animator.rotate(this.rightside, (float)Math.toRadians(67.5), (float)Math.toRadians(72.5), (float)Math.toRadians(-145.0));
      this.animator.rotate(this.leftside, (float)Math.toRadians(67.5), (float)Math.toRadians(-72.5), (float)Math.toRadians(145.0));
      this.animator.move(this.rightside, 0.0F, 0.0F, 5.0F);
      this.animator.move(this.leftside, 0.0F, 0.0F, -3.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(15);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.mid, (float)Math.toRadians(35.0), (float)Math.toRadians(7.5), 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(70.0), 0.0F, 0.0F);
      this.animator.rotate(this.rightside, (float)Math.toRadians(67.5), (float)Math.toRadians(72.5), (float)Math.toRadians(-145.0));
      this.animator.rotate(this.leftside, (float)Math.toRadians(67.5), (float)Math.toRadians(-72.5), (float)Math.toRadians(145.0));
      this.animator.move(this.rightside, 0.0F, 0.0F, -3.0F);
      this.animator.move(this.leftside, 0.0F, 0.0F, 5.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.resetKeyframe(10);
      this.animator.setAnimation(The_Harbinger_Entity.STUN_ANIAMATION);
      this.animator.startKeyframe(15);
      this.animator.rotate(this.root, (float)Math.toRadians(20.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.righthead, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.rotate(this.lefthead, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(70);
      this.animator.resetKeyframe(20);
      this.animator.setAnimation(The_Harbinger_Entity.MISSILE_FIRE_FAST_ANIAMATION);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.mid, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(8);
      this.animator.rotate(this.mid, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(50.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(8);
      this.animator.rotate(this.mid, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(-17.5), 0.0F, 0.0F);
      this.animator.rotate(this.rightside, (float)Math.toRadians(40.0), (float)Math.toRadians(92.5), (float)Math.toRadians(-145.0));
      this.animator.rotate(this.leftside, (float)Math.toRadians(40.0), (float)Math.toRadians(-92.5), (float)Math.toRadians(145.0));
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(8);
      this.animator.rotate(this.mid, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(70.0), 0.0F, 0.0F);
      this.animator.rotate(this.rightside, (float)Math.toRadians(67.5), (float)Math.toRadians(72.5), (float)Math.toRadians(-145.0));
      this.animator.rotate(this.leftside, (float)Math.toRadians(67.5), (float)Math.toRadians(-72.5), (float)Math.toRadians(145.0));
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.mid, (float)Math.toRadians(35.0), 0.0F, 0.0F);
      this.animator.rotate(this.main_head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.jaw, (float)Math.toRadians(32.5), 0.0F, 0.0F);
      this.animator.rotate(this.jetpack, (float)Math.toRadians(70.0), 0.0F, 0.0F);
      this.animator.rotate(this.rightside, (float)Math.toRadians(67.5), (float)Math.toRadians(72.5), (float)Math.toRadians(-145.0));
      this.animator.rotate(this.leftside, (float)Math.toRadians(67.5), (float)Math.toRadians(-72.5), (float)Math.toRadians(145.0));
      this.animator.move(this.rightside, 0.0F, 0.0F, 5.0F);
      this.animator.move(this.leftside, 0.0F, 0.0F, 5.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(10);
      this.animator.resetKeyframe(10);
   }

   public void setupAnim(The_Harbinger_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float idleSpeed = 0.1F;
      float idleDegree = 0.1F;
      if (entityIn.deactivateProgress == 0.0F) {
         this.walk(this.body, idleSpeed * 0.75F, idleDegree * 0.5F, true, 0.0F, -0.05F, ageInTicks, 1.0F);
         this.walk(this.tail, idleSpeed * 0.75F, idleDegree * 0.35F, true, 1.0F, -0.05F, ageInTicks, 1.0F);
      }

      this.main_head.rotateAngleY += netHeadYaw * (float) (Math.PI / 180.0);
      this.main_head.rotateAngleX += headPitch * (float) (Math.PI / 180.0);
      if (entityIn.getAnimation() != The_Harbinger_Entity.MISSILE_FIRE_ANIAMATION
         && entityIn.getAnimation() != The_Harbinger_Entity.DEATH_ANIMATION
         && entityIn.getAnimation() != The_Harbinger_Entity.LAUNCH_ANIAMATION) {
         setupHeadRotation(entityIn, this.righthead, 0);
         setupHeadRotation(entityIn, this.lefthead, 1);
      }

      float partialTick = ageInTicks - entityIn.tickCount;
      float Laser_Mode_Progress = entityIn.prev_Laser_Mode_Progress + (entityIn.Laser_Mode_Progress - entityIn.prev_Laser_Mode_Progress) * partialTick;
      this.progressPositionPrev(this.left_upper_guard, Laser_Mode_Progress, 0.0F, 0.0F, 5.0F, 30.0F);
      this.progressPositionPrev(this.left_lower_guard, Laser_Mode_Progress, 0.0F, 0.0F, 5.0F, 30.0F);
      this.progressRotationPrev(this.left_upper_guard, Laser_Mode_Progress, (float)Math.toRadians(45.0), 0.0F, 0.0F, 30.0F);
      this.progressRotationPrev(this.left_lower_guard, Laser_Mode_Progress, (float)Math.toRadians(-45.0), 0.0F, 0.0F, 30.0F);
      this.progressPositionPrev(this.leftlaser, Laser_Mode_Progress, 0.0F, 0.0F, -6.0F, 30.0F);
      this.progressPositionPrev(this.leftlaser2, Laser_Mode_Progress, 0.0F, 0.0F, -2.0F, 30.0F);
      this.progressPositionPrev(this.right_upper_guard, Laser_Mode_Progress, 0.0F, 0.0F, 5.0F, 30.0F);
      this.progressPositionPrev(this.right_lower_guard, Laser_Mode_Progress, 0.0F, 0.0F, 5.0F, 30.0F);
      this.progressRotationPrev(this.right_upper_guard, Laser_Mode_Progress, (float)Math.toRadians(45.0), 0.0F, 0.0F, 30.0F);
      this.progressRotationPrev(this.right_lower_guard, Laser_Mode_Progress, (float)Math.toRadians(-45.0), 0.0F, 0.0F, 30.0F);
      this.progressPositionPrev(this.rightlaser, Laser_Mode_Progress, 0.0F, 0.0F, -6.0F, 30.0F);
      this.progressPositionPrev(this.rightlaser2, Laser_Mode_Progress, 0.0F, 0.0F, -2.0F, 30.0F);
      float deactivateProgress = entityIn.prevdeactivateProgress + (entityIn.deactivateProgress - entityIn.prevdeactivateProgress) * partialTick;
      this.progressRotationPrev(this.main_head, deactivateProgress, (float)Math.toRadians(45.0), 0.0F, 0.0F, 40.0F);
      this.progressRotationPrev(this.lefthead, deactivateProgress, (float)Math.toRadians(45.0), 0.0F, 0.0F, 40.0F);
      this.progressRotationPrev(this.righthead, deactivateProgress, (float)Math.toRadians(45.0), 0.0F, 0.0F, 40.0F);
   }

   private static void setupHeadRotation(The_Harbinger_Entity entityIn, AdvancedModelBox p_171073_, int p_171074_) {
      p_171073_.rotateAngleY = p_171073_.rotateAngleY + (entityIn.getHeadYRot(p_171074_) - entityIn.yBodyRot) * (float) (Math.PI / 180.0);
      p_171073_.rotateAngleX = p_171073_.rotateAngleX + entityIn.getHeadXRot(p_171074_) * (float) (Math.PI / 180.0);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.main_head,
         this.mid,
         this.head,
         this.headgear,
         this.eyebrows,
         this.jaw,
         this.righthead,
         this.rightlaser,
         this.rightlaser2,
         this.right_guard,
         this.right_upper_guard,
         new AdvancedModelBox[]{
            this.right_lower_guard,
            this.lefthead,
            this.left_side_guard,
            this.left_upper_guard,
            this.left_lower_guard,
            this.leftlaser,
            this.leftlaser2,
            this.body,
            this.tail,
            this.nether_star,
            this.tailbone,
            this.jetpack,
            this.rightside,
            this.rightedge1,
            this.rightedge2,
            this.rightedge3,
            this.leftside,
            this.leftedge1,
            this.leftedge2,
            this.leftedge3,
            this.left_jet,
            this.right_jet
         }
      );
   }

   public void translateToHand(PoseStack matrixStack, boolean right) {
      this.root.translateAndRotate(matrixStack);
      this.mid.translateAndRotate(matrixStack);
      this.body.translateAndRotate(matrixStack);
      this.jetpack.translateAndRotate(matrixStack);
      if (right) {
         this.rightside.translateAndRotate(matrixStack);
         this.right_jet.translateAndRotate(matrixStack);
      } else {
         this.leftside.translateAndRotate(matrixStack);
         this.left_jet.translateAndRotate(matrixStack);
      }
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
