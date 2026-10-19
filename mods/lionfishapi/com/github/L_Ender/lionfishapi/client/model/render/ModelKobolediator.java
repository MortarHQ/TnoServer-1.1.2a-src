package com.github.L_Ender.lionfishapi.client.model.render;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.github.L_Ender.lionfishapi.server.entity.Kobolediator_Entity;
import com.google.common.collect.ImmutableList;

public class ModelKobolediator extends AdvancedEntityModel<Kobolediator_Entity> {
   private final AdvancedModelBox everything;
   private final AdvancedModelBox mid_root;
   private final AdvancedModelBox pelvis;
   private final AdvancedModelBox lower_body;
   private final AdvancedModelBox body;
   private final AdvancedModelBox right_shoulder;
   private final AdvancedModelBox right_arm;
   private final AdvancedModelBox right_front_arm;
   private final AdvancedModelBox golden_greatsword;
   private final AdvancedModelBox left_shoulder;
   private final AdvancedModelBox left_arm;
   private final AdvancedModelBox left_front_arm;
   private final AdvancedModelBox head;
   private final AdvancedModelBox head_cube1;
   private final AdvancedModelBox head_cube2;
   private final AdvancedModelBox head_cube3;
   private final AdvancedModelBox head_cube4;
   private final AdvancedModelBox right_horn;
   private final AdvancedModelBox left_horn;
   private final AdvancedModelBox jaw;
   private final AdvancedModelBox tail1;
   private final AdvancedModelBox tail2;
   private final AdvancedModelBox legs;
   private final AdvancedModelBox right_leg;
   private final AdvancedModelBox right_front_leg;
   private final AdvancedModelBox left_leg;
   private final AdvancedModelBox left_front_leg;

   public ModelKobolediator() {
      this.textureWidth = 256;
      this.textureHeight = 256;
      this.everything = new AdvancedModelBox(this);
      this.everything.setRotationPoint(0.0F, 25.0F, -1.0F);
      this.mid_root = new AdvancedModelBox(this, "mid_root");
      this.mid_root.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.everything.addChild(this.mid_root);
      this.pelvis = new AdvancedModelBox(this, "pelvis");
      this.pelvis.setRotationPoint(0.0F, -22.0F, 0.0F);
      this.mid_root.addChild(this.pelvis);
      this.setRotationAngle(this.pelvis, 0.1745F, 0.1309F, 0.0F);
      this.pelvis.setTextureOffset(94, 72).addBox(-9.0F, -3.0F, -6.0513F, 18.0F, 6.0F, 15.0F, 0.0F, false);
      this.lower_body = new AdvancedModelBox(this, "lower_body");
      this.lower_body.setRotationPoint(-0.7832F, 0.0F, 8.9487F);
      this.pelvis.addChild(this.lower_body);
      this.lower_body.setTextureOffset(86, 0).addBox(-6.2168F, -14.0F, -15.0F, 14.0F, 14.0F, 15.0F, 0.0F, false);
      this.lower_body.setTextureOffset(56, 146).addBox(-2.2168F, -14.0F, -3.0F, 6.0F, 14.0F, 6.0F, 0.0F, false);
      this.body = new AdvancedModelBox(this, "body");
      this.body.setRotationPoint(1.0F, -14.0F, 0.0F);
      this.lower_body.addChild(this.body);
      this.setRotationAngle(this.body, 0.1372F, -0.3027F, -0.0411F);
      this.body.setTextureOffset(0, 0).addBox(-12.2168F, -21.0F, -19.0F, 24.0F, 21.0F, 19.0F, 0.0F, false);
      this.body.setTextureOffset(126, 130).addBox(-3.2168F, -21.0F, -3.0F, 6.0F, 21.0F, 6.0F, 0.0F, false);
      this.body.setTextureOffset(0, 40).addBox(-10.2168F, -21.0F, -21.0F, 4.0F, 16.0F, 2.0F, 0.0F, false);
      this.body.setTextureOffset(0, 0).addBox(5.7832F, -21.0F, -21.0F, 4.0F, 16.0F, 2.0F, 0.0F, false);
      this.body.setTextureOffset(0, 40).addBox(-13.2168F, -21.0F, -19.0F, 26.0F, 7.0F, 19.0F, 0.5F, false);
      this.right_shoulder = new AdvancedModelBox(this, "right_shoulder");
      this.right_shoulder.setRotationPoint(-13.0F, -20.0F, -8.0F);
      this.body.addChild(this.right_shoulder);
      this.setRotationAngle(this.right_shoulder, -0.6109F, 0.2618F, 0.2182F);
      this.right_shoulder.setTextureOffset(0, 66).addBox(-16.0F, -4.0F, -9.0F, 17.0F, 15.0F, 19.0F, 0.0F, false);
      this.right_arm = new AdvancedModelBox(this, "right_arm");
      this.right_arm.setRotationPoint(-5.0F, 9.0F, 2.0F);
      this.right_shoulder.addChild(this.right_arm);
      this.setRotationAngle(this.right_arm, -0.4904F, 0.0334F, 0.0769F);
      this.right_arm.setTextureOffset(94, 124).addBox(-5.0F, -1.0F, -4.0F, 8.0F, 14.0F, 8.0F, 0.0F, false);
      this.right_front_arm = new AdvancedModelBox(this, "right_front_arm");
      this.right_front_arm.setRotationPoint(0.0F, 13.0F, 2.0F);
      this.right_arm.addChild(this.right_front_arm);
      this.setRotationAngle(this.right_front_arm, -1.5708F, 0.0F, 0.0F);
      this.right_front_arm.setTextureOffset(0, 100).addBox(-6.025F, 0.0F, -6.5F, 9.0F, 22.0F, 9.0F, 0.0F, false);
      this.right_front_arm.setTextureOffset(94, 93).addBox(-8.025F, 2.0F, -8.5F, 8.0F, 18.0F, 13.0F, 0.0F, false);
      this.right_front_arm.setTextureOffset(67, 0).addBox(1.0F, 16.0F, -1.5F, 4.0F, 7.0F, 0.0F, 0.0F, false);
      this.right_front_arm.setTextureOffset(53, 69).addBox(1.0F, 17.0F, 1.5F, 4.0F, 6.0F, 0.0F, 0.0F, false);
      this.right_front_arm.setTextureOffset(53, 69).addBox(1.0F, 17.0F, -4.5F, 4.0F, 6.0F, 0.0F, 0.0F, false);
      this.golden_greatsword = new AdvancedModelBox(this, "golden_greatsword");
      this.golden_greatsword.setRotationPoint(-1.0F, 18.9F, 1.0F);
      this.right_front_arm.addChild(this.golden_greatsword);
      this.setRotationAngle(this.golden_greatsword, 1.3963F, 0.0F, 0.0F);
      this.golden_greatsword.setTextureOffset(80, 146).addBox(-2.2168F, -12.0F, -2.1F, 4.0F, 21.0F, 4.0F, 0.0F, false);
      this.golden_greatsword.setTextureOffset(72, 72).addBox(-1.7168F, -78.0F, -4.1F, 3.0F, 66.0F, 8.0F, 0.0F, false);
      this.golden_greatsword.setTextureOffset(71, 40).addBox(-2.2168F, -12.0F, 1.9F, 4.0F, 5.0F, 5.0F, 0.0F, false);
      this.golden_greatsword.setTextureOffset(0, 66).addBox(-2.2168F, -12.0F, -7.1F, 4.0F, 5.0F, 5.0F, 0.0F, false);
      this.left_shoulder = new AdvancedModelBox(this, "left_shoulder");
      this.left_shoulder.setRotationPoint(12.5663F, -20.0F, -8.0F);
      this.body.addChild(this.left_shoulder);
      this.setRotationAngle(this.left_shoulder, 0.0436F, -0.0873F, -0.1309F);
      this.left_shoulder.setTextureOffset(0, 66).addBox(-1.0F, -4.0F, -9.0F, 17.0F, 15.0F, 19.0F, 0.0F, true);
      this.left_arm = new AdvancedModelBox(this, "left_arm");
      this.left_arm.setRotationPoint(5.0F, 9.0F, 2.0F);
      this.left_shoulder.addChild(this.left_arm);
      this.setRotationAngle(this.left_arm, 0.0873F, 0.0F, 0.0F);
      this.left_arm.setTextureOffset(94, 124).addBox(-3.0F, -1.0F, -4.0F, 8.0F, 14.0F, 8.0F, 0.0F, true);
      this.left_front_arm = new AdvancedModelBox(this, "left_front_arm");
      this.left_front_arm.setRotationPoint(0.0F, 13.0F, 2.0F);
      this.left_arm.addChild(this.left_front_arm);
      this.setRotationAngle(this.left_front_arm, -0.829F, 0.0F, 0.0F);
      this.left_front_arm.setTextureOffset(0, 100).addBox(-2.975F, 0.0F, -6.5F, 9.0F, 22.0F, 9.0F, 0.0F, true);
      this.left_front_arm.setTextureOffset(94, 93).addBox(0.025F, 2.0F, -8.5F, 8.0F, 18.0F, 13.0F, 0.0F, true);
      this.left_front_arm.setTextureOffset(53, 69).addBox(-5.0F, 17.0F, -4.5F, 4.0F, 6.0F, 0.0F, 0.0F, true);
      this.left_front_arm.setTextureOffset(67, 0).addBox(-5.0F, 16.0F, -1.5F, 4.0F, 7.0F, 0.0F, 0.0F, true);
      this.left_front_arm.setTextureOffset(53, 69).addBox(-5.0F, 17.0F, 1.5F, 4.0F, 6.0F, 0.0F, 0.0F, true);
      this.head = new AdvancedModelBox(this, "head");
      this.head.setRotationPoint(-1.0F, -30.0F, -9.0F);
      this.body.addChild(this.head);
      this.setRotationAngle(this.head, 0.0F, 0.1309F, 0.0F);
      this.head.setTextureOffset(24, 119).addBox(-4.2168F, -3.0F, -10.0F, 10.0F, 7.0F, 12.0F, 0.0F, false);
      this.head_cube1 = new AdvancedModelBox(this);
      this.head_cube1.setRotationPoint(1.5663F, -2.0F, -2.0F);
      this.head.addChild(this.head_cube1);
      this.setRotationAngle(this.head_cube1, 0.1616F, 0.1866F, -0.0568F);
      this.head_cube1.setTextureOffset(36, 100).addBox(0.8F, -5.0F, -8.0F, 6.0F, 6.0F, 11.0F, 0.0F, false);
      this.head_cube2 = new AdvancedModelBox(this);
      this.head_cube2.setRotationPoint(-3.2168F, -3.0F, -2.0F);
      this.head.addChild(this.head_cube2);
      this.setRotationAngle(this.head_cube2, 0.48F, 0.0F, 0.0F);
      this.head_cube2.setTextureOffset(62, 38).addBox(1.0F, -6.0F, -12.0F, 6.0F, 6.0F, 28.0F, 0.0F, false);
      this.head_cube3 = new AdvancedModelBox(this);
      this.head_cube3.setRotationPoint(0.0F, -2.0F, -2.0F);
      this.head.addChild(this.head_cube3);
      this.setRotationAngle(this.head_cube3, 0.1616F, -0.1866F, 0.0568F);
      this.head_cube3.setTextureOffset(125, 113).addBox(-6.8F, -5.0F, -8.0F, 6.0F, 6.0F, 11.0F, 0.0F, false);
      this.head_cube4 = new AdvancedModelBox(this);
      this.head_cube4.setRotationPoint(-0.7168F, 30.0F, 5.0F);
      this.head.addChild(this.head_cube4);
      this.setRotationAngle(this.head_cube4, 0.0436F, 0.0F, 0.0F);
      this.head_cube4.setTextureOffset(102, 49).addBox(-3.0F, -34.0F, -23.0F, 9.0F, 7.0F, 10.0F, -0.01F, false);
      this.right_horn = new AdvancedModelBox(this, "right_horn");
      this.right_horn.setRotationPoint(-2.0F, -5.0F, -3.0F);
      this.head.addChild(this.right_horn);
      this.right_horn.setTextureOffset(148, 105).addBox(-9.2168F, -9.0F, 4.0513F, 5.0F, 5.0F, 6.0F, 0.0F, false);
      this.right_horn.setTextureOffset(148, 40).addBox(-9.2168F, -9.0F, -1.9487F, 6.0F, 6.0F, 6.0F, 0.0F, false);
      this.right_horn.setTextureOffset(129, 0).addBox(-9.2168F, -3.0F, -1.9487F, 12.0F, 6.0F, 6.0F, 0.0F, false);
      this.left_horn = new AdvancedModelBox(this, "left_horn");
      this.left_horn.setRotationPoint(3.5663F, -5.0F, -3.0F);
      this.head.addChild(this.left_horn);
      this.left_horn.setTextureOffset(148, 52).addBox(4.2168F, -9.0F, 4.0513F, 5.0F, 5.0F, 6.0F, 0.0F, false);
      this.left_horn.setTextureOffset(96, 146).addBox(3.2168F, -9.0F, -1.9487F, 6.0F, 6.0F, 6.0F, 0.0F, false);
      this.left_horn.setTextureOffset(123, 93).addBox(-2.7832F, -3.0F, -1.9487F, 12.0F, 6.0F, 6.0F, 0.0F, false);
      this.jaw = new AdvancedModelBox(this, "jaw");
      this.jaw.setRotationPoint(0.0F, 6.0F, -6.0F);
      this.head.addChild(this.jaw);
      this.setRotationAngle(this.jaw, 0.1309F, 0.0F, 0.0F);
      this.jaw.setTextureOffset(102, 29).addBox(-2.7168F, -4.0F, -12.0F, 7.0F, 4.0F, 16.0F, 0.0F, false);
      this.tail1 = new AdvancedModelBox(this, "tail1");
      this.tail1.setRotationPoint(0.0F, -0.1231F, 11.3724F);
      this.pelvis.addChild(this.tail1);
      this.setRotationAngle(this.tail1, -0.3927F, 0.0F, 0.0F);
      this.tail1.setTextureOffset(130, 54).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 4.0F, 12.0F, 0.0F, false);
      this.tail2 = new AdvancedModelBox(this, "tail2");
      this.tail2.setRotationPoint(1.0F, -0.5F, 10.0F);
      this.tail1.addChild(this.tail2);
      this.setRotationAngle(this.tail2, 0.4799F, 0.0F, 0.0F);
      this.tail2.setTextureOffset(67, 0).addBox(-2.0F, -1.1888F, -1.1585F, 2.0F, 3.0F, 12.0F, 0.0F, false);
      this.legs = new AdvancedModelBox(this, "legs");
      this.legs.setRotationPoint(0.0F, -22.0F, 0.0F);
      this.mid_root.addChild(this.legs);
      this.right_leg = new AdvancedModelBox(this, "right_leg");
      this.right_leg.setRotationPoint(-6.7832F, 4.0F, 0.9487F);
      this.legs.addChild(this.right_leg);
      this.setRotationAngle(this.right_leg, -0.1295F, 0.5275F, 0.3306F);
      this.right_leg.setTextureOffset(145, 70).addBox(-3.2168F, -2.0F, -3.0F, 7.0F, 10.0F, 7.0F, 0.0F, false);
      this.right_front_leg = new AdvancedModelBox(this, "right_front_leg");
      this.right_front_leg.setRotationPoint(-0.975F, 8.0F, -2.0F);
      this.right_leg.addChild(this.right_front_leg);
      this.setRotationAngle(this.right_front_leg, 0.3927F, 0.0F, 0.0F);
      this.right_front_leg.setTextureOffset(137, 22).addBox(-2.2168F, 0.0F, -1.0F, 7.0F, 11.0F, 7.0F, 0.0F, false);
      this.right_front_leg.setTextureOffset(53, 62).addBox(-1.2168F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, 0.0F, false);
      this.right_front_leg.setTextureOffset(53, 62).addBox(3.7832F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, 0.0F, false);
      this.right_front_leg.setTextureOffset(53, 62).addBox(1.2832F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, 0.0F, false);
      this.left_leg = new AdvancedModelBox(this, "left_leg");
      this.left_leg.setRotationPoint(6.7832F, 3.0F, 0.9487F);
      this.legs.addChild(this.left_leg);
      this.setRotationAngle(this.left_leg, -0.1295F, -0.5275F, -0.3306F);
      this.left_leg.setTextureOffset(28, 138).addBox(-3.7832F, -2.0F, -3.0F, 7.0F, 10.0F, 7.0F, 0.0F, false);
      this.left_front_leg = new AdvancedModelBox(this, "left_front_leg");
      this.left_front_leg.setRotationPoint(0.975F, 8.0F, -2.0F);
      this.left_leg.addChild(this.left_front_leg);
      this.setRotationAngle(this.left_front_leg, 0.3927F, 0.0F, 0.0F);
      this.left_front_leg.setTextureOffset(0, 131).addBox(-4.7832F, 0.0F, -1.0F, 7.0F, 11.0F, 7.0F, 0.0F, false);
      this.left_front_leg.setTextureOffset(53, 62).addBox(1.2168F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, 0.0F, true);
      this.left_front_leg.setTextureOffset(53, 62).addBox(-1.2832F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, 0.0F, true);
      this.left_front_leg.setTextureOffset(53, 62).addBox(-3.7832F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, 0.0F, true);
      this.updateDefaultPose();
   }

   public void setupAnim(Kobolediator_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
      if (entity.getAttackState() != 6 && !entity.isSleep()) {
         this.animateWalk(Kobolediator_Animation.WALK, limbSwing, limbSwingAmount, 1.0F, 4.0F);
      }

      this.animate(entity.getAnimationState("idle"), Kobolediator_Animation.IDLE, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("sleep"), Kobolediator_Animation.SLEEP, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("awake"), Kobolediator_Animation.AWAKE, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("sword1"), Kobolediator_Animation.SWORD1, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("sword2"), Kobolediator_Animation.SWORD2, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("charge"), Kobolediator_Animation.CHARGE, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("charge_prepare"), Kobolediator_Animation.CHARGE_PREPARE, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("charge_end"), Kobolediator_Animation.CHARGE_END, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("death"), Kobolediator_Animation.DEATH, ageInTicks, 1.0F);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   @Override
   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.everything);
   }

   @Override
   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.everything,
         this.mid_root,
         this.pelvis,
         this.lower_body,
         this.body,
         this.right_shoulder,
         this.right_arm,
         this.right_front_arm,
         this.golden_greatsword,
         this.left_shoulder,
         this.left_arm,
         this.left_front_arm,
         new AdvancedModelBox[]{
            this.head,
            this.head_cube1,
            this.head_cube2,
            this.head_cube3,
            this.head_cube4,
            this.right_horn,
            this.left_horn,
            this.jaw,
            this.tail1,
            this.tail2,
            this.legs,
            this.right_leg,
            this.right_front_leg,
            this.left_leg,
            this.left_front_leg
         }
      );
   }
}
