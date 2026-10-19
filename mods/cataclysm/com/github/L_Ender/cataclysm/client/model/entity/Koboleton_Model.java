package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.Koboleton_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class Koboleton_Model extends AdvancedEntityModel<Koboleton_Entity> {
   public final AdvancedModelBox root;
   private final AdvancedModelBox legs;
   private final AdvancedModelBox right_leg;
   private final AdvancedModelBox right_fore_leg;
   private final AdvancedModelBox right_foot;
   private final AdvancedModelBox left_leg;
   private final AdvancedModelBox left_fore_leg;
   private final AdvancedModelBox left_foot;
   public final AdvancedModelBox pelvis;
   private final AdvancedModelBox pelvis_cube;
   public final AdvancedModelBox lower_body;
   public final AdvancedModelBox body;
   public final AdvancedModelBox right_arm;
   public final AdvancedModelBox right_weapon;
   public final AdvancedModelBox left_arm;
   public final AdvancedModelBox left_weapon;
   private final AdvancedModelBox neck;
   private final AdvancedModelBox head;
   private final AdvancedModelBox nose;
   private final AdvancedModelBox right_eyebrow;
   private final AdvancedModelBox left_eyebrow;
   private final AdvancedModelBox skull;
   private final AdvancedModelBox jaw;
   private final AdvancedModelBox tail1;
   private final AdvancedModelBox tail2;
   private ModelAnimator animator;

   public Koboleton_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.legs = new AdvancedModelBox(this);
      this.legs.setRotationPoint(0.0F, -16.1231F, 2.3724F);
      this.root.addChild(this.legs);
      this.setRotationAngle(this.legs, -0.1745F, 0.0F, 0.0F);
      this.right_leg = new AdvancedModelBox(this);
      this.right_leg.setRotationPoint(-1.4F, 0.9227F, -1.2917F);
      this.legs.addChild(this.right_leg);
      this.setRotationAngle(this.right_leg, -0.7338F, 0.1309F, 0.0218F);
      this.right_leg.setTextureOffset(25, 28).addBox(-4.0F, -0.9024F, -1.4616F, 5.0F, 8.0F, 6.0F, 0.0F, false);
      this.right_fore_leg = new AdvancedModelBox(this);
      this.right_fore_leg.setRotationPoint(-1.5F, 4.0976F, 1.5384F);
      this.right_leg.addChild(this.right_fore_leg);
      this.setRotationAngle(this.right_fore_leg, 0.9425F, -1.0E-4F, 7.0E-4F);
      this.right_fore_leg.setTextureOffset(19, 52).addBox(-2.0027F, 2.8653F, -0.8643F, 4.0F, 8.0F, 3.0F, 0.0F, false);
      this.right_foot = new AdvancedModelBox(this);
      this.right_foot.setRotationPoint(0.0F, 11.0F, -1.0F);
      this.right_fore_leg.addChild(this.right_foot);
      this.setRotationAngle(this.right_foot, -0.1745F, 0.0F, 0.0F);
      this.right_foot.setTextureOffset(0, 0).addBox(1.5005F, -2.398F, -3.6296F, 0.0F, 4.0F, 5.0F, 0.0F, false);
      this.right_foot.setTextureOffset(0, 0).addBox(-1.4995F, -2.398F, -3.6296F, 0.0F, 4.0F, 5.0F, 0.0F, false);
      this.right_foot.setTextureOffset(0, 0).addBox(5.0E-4F, -2.398F, -3.6296F, 0.0F, 4.0F, 5.0F, 0.0F, false);
      this.left_leg = new AdvancedModelBox(this);
      this.left_leg.setRotationPoint(1.4F, 0.9227F, -1.2917F);
      this.legs.addChild(this.left_leg);
      this.setRotationAngle(this.left_leg, -0.7338F, -0.1309F, -0.0218F);
      this.left_leg.setTextureOffset(25, 28).addBox(-1.0F, -0.9024F, -1.4616F, 5.0F, 8.0F, 6.0F, 0.0F, true);
      this.left_fore_leg = new AdvancedModelBox(this);
      this.left_fore_leg.setRotationPoint(1.5F, 4.0976F, 1.5384F);
      this.left_leg.addChild(this.left_fore_leg);
      this.setRotationAngle(this.left_fore_leg, 0.9425F, 1.0E-4F, -7.0E-4F);
      this.left_fore_leg.setTextureOffset(19, 52).addBox(-1.9973F, 2.8653F, -0.8643F, 4.0F, 8.0F, 3.0F, 0.0F, true);
      this.left_foot = new AdvancedModelBox(this);
      this.left_foot.setRotationPoint(0.0F, 11.0F, -1.0F);
      this.left_fore_leg.addChild(this.left_foot);
      this.setRotationAngle(this.left_foot, -0.1745F, 0.0F, 0.0F);
      this.left_foot.setTextureOffset(0, 0).addBox(-1.5005F, -2.398F, -3.6296F, 0.0F, 4.0F, 5.0F, 0.0F, true);
      this.left_foot.setTextureOffset(0, 0).addBox(1.4995F, -2.398F, -3.6296F, 0.0F, 4.0F, 5.0F, 0.0F, true);
      this.left_foot.setTextureOffset(0, 0).addBox(-5.0E-4F, -2.398F, -3.6296F, 0.0F, 4.0F, 5.0F, 0.0F, true);
      this.pelvis = new AdvancedModelBox(this);
      this.pelvis.setRotationPoint(0.0F, -18.1231F, -1.6276F);
      this.root.addChild(this.pelvis);
      this.setRotationAngle(this.pelvis, -0.1745F, 0.0F, 0.0F);
      this.pelvis_cube = new AdvancedModelBox(this);
      this.pelvis_cube.setRotationPoint(-1.0F, 0.0F, 1.0F);
      this.pelvis.addChild(this.pelvis_cube);
      this.setRotationAngle(this.pelvis_cube, 0.0436F, 0.0F, 0.0F);
      this.pelvis_cube.setTextureOffset(21, 43).addBox(-3.0F, 0.6977F, -0.5221F, 8.0F, 4.0F, 4.0F, 0.0F, false);
      this.lower_body = new AdvancedModelBox(this);
      this.lower_body.setRotationPoint(0.0F, 0.9281F, 2.6476F);
      this.pelvis.addChild(this.lower_body);
      this.setRotationAngle(this.lower_body, 1.0036F, 0.0F, 0.0F);
      this.lower_body.setTextureOffset(0, 46).addBox(-3.0F, -4.9281F, -1.6476F, 6.0F, 7.0F, 3.0F, 0.0F, false);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0895F, -3.9281F, 1.7524F);
      this.lower_body.addChild(this.body);
      this.setRotationAngle(this.body, 0.2611F, 0.0F, 0.0F);
      this.body.setTextureOffset(0, 34).addBox(-3.5895F, -6.0F, -5.0F, 7.0F, 6.0F, 5.0F, 0.0F, false);
      this.right_arm = new AdvancedModelBox(this);
      this.right_arm.setRotationPoint(-5.2895F, -4.5769F, -0.7724F);
      this.body.addChild(this.right_arm);
      this.setRotationAngle(this.right_arm, 0.2618F, 0.0F, 0.3491F);
      this.right_arm.setTextureOffset(0, 0).addBox(-1.5F, -1.5F, -12.0F, 3.0F, 3.0F, 13.0F, 0.0F, false);
      this.right_arm.setTextureOffset(19, 21).addBox(-2.0F, -1.0F, -17.0F, 4.0F, 0.0F, 5.0F, 0.0F, false);
      this.right_arm.setTextureOffset(19, 21).addBox(-2.0F, 0.0F, -17.0F, 4.0F, 0.0F, 5.0F, 0.0F, false);
      this.right_arm.setTextureOffset(19, 21).addBox(-2.0F, 1.0F, -17.0F, 4.0F, 0.0F, 5.0F, 0.0F, false);
      this.right_weapon = new AdvancedModelBox(this);
      this.right_weapon.setRotationPoint(0.0F, 0.0F, -14.5F);
      this.right_arm.addChild(this.right_weapon);
      this.left_arm = new AdvancedModelBox(this);
      this.left_arm.setRotationPoint(5.1105F, -4.5769F, -0.7724F);
      this.body.addChild(this.left_arm);
      this.setRotationAngle(this.left_arm, 0.2182F, 0.0F, -0.3491F);
      this.left_arm.setTextureOffset(0, 0).addBox(-1.5F, -1.5F, -12.0F, 3.0F, 3.0F, 13.0F, 0.0F, true);
      this.left_arm.setTextureOffset(19, 21).addBox(-2.0F, -1.0F, -17.0F, 4.0F, 0.0F, 5.0F, 0.0F, true);
      this.left_arm.setTextureOffset(19, 21).addBox(-2.0F, 0.0F, -17.0F, 4.0F, 0.0F, 5.0F, 0.0F, true);
      this.left_arm.setTextureOffset(19, 21).addBox(-2.0F, 1.0F, -17.0F, 4.0F, 0.0F, 5.0F, 0.0F, true);
      this.left_weapon = new AdvancedModelBox(this);
      this.left_weapon.setRotationPoint(0.0F, 0.0F, -14.5F);
      this.left_arm.addChild(this.left_weapon);
      this.neck = new AdvancedModelBox(this);
      this.neck.setRotationPoint(0.0F, -8.0F, -3.5F);
      this.body.addChild(this.neck);
      this.setRotationAngle(this.neck, -1.1345F, 0.0F, 0.0F);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.neck.addChild(this.head);
      this.nose = new AdvancedModelBox(this);
      this.nose.setRotationPoint(-0.0895F, 7.0F, 0.0F);
      this.head.addChild(this.nose);
      this.setRotationAngle(this.nose, 0.3491F, 0.0F, 0.0F);
      this.nose.setTextureOffset(46, 46).addBox(-2.0F, -11.0F, -5.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);
      this.right_eyebrow = new AdvancedModelBox(this);
      this.right_eyebrow.setRotationPoint(-1.5895F, -1.0F, -6.0F);
      this.head.addChild(this.right_eyebrow);
      this.setRotationAngle(this.right_eyebrow, 0.3386F, -0.283F, 0.0405F);
      this.right_eyebrow.setTextureOffset(38, 0).addBox(-0.8137F, -1.9404F, 0.0575F, 3.0F, 2.0F, 8.0F, 0.0F, false);
      this.left_eyebrow = new AdvancedModelBox(this);
      this.left_eyebrow.setRotationPoint(1.4105F, -1.0F, -6.0F);
      this.head.addChild(this.left_eyebrow);
      this.setRotationAngle(this.left_eyebrow, 0.3386F, 0.283F, -0.0405F);
      this.left_eyebrow.setTextureOffset(38, 0).addBox(-2.1863F, -1.9404F, 0.0575F, 3.0F, 2.0F, 8.0F, 0.0F, true);
      this.skull = new AdvancedModelBox(this);
      this.skull.setRotationPoint(-0.5895F, 7.0F, 0.0F);
      this.head.addChild(this.skull);
      this.setRotationAngle(this.skull, 0.1745F, 0.0F, 0.0F);
      this.skull.setTextureOffset(42, 21).addBox(-2.0F, -10.9F, -3.0F, 5.0F, 4.0F, 6.0F, 0.0F, false);
      this.jaw = new AdvancedModelBox(this);
      this.jaw.setRotationPoint(0.0F, 0.0F, -1.0F);
      this.head.addChild(this.jaw);
      this.setRotationAngle(this.jaw, 0.3927F, 0.0F, 0.0F);
      this.jaw.setTextureOffset(42, 36).addBox(-1.5895F, -0.7753F, -6.2929F, 3.0F, 2.0F, 7.0F, 0.0F, false);
      this.tail1 = new AdvancedModelBox(this);
      this.tail1.setRotationPoint(1.0F, 1.0F, 4.0F);
      this.pelvis.addChild(this.tail1);
      this.tail1.setTextureOffset(0, 17).addBox(-2.5F, -2.0F, 0.0F, 3.0F, 4.0F, 12.0F, 0.0F, false);
      this.tail2 = new AdvancedModelBox(this);
      this.tail2.setRotationPoint(0.0F, -0.5F, 11.0F);
      this.tail1.addChild(this.tail2);
      this.setRotationAngle(this.tail2, 0.1745F, 0.0F, 0.0F);
      this.tail2.setTextureOffset(21, 5).addBox(-2.0F, -1.1888F, -1.1585F, 2.0F, 3.0F, 12.0F, 0.0F, false);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void animate(Koboleton_Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(Koboleton_Entity.COBOLETON_ATTACK);
      if (!entity.isAggressive()) {
         if (entity.isLeftHanded()) {
            this.animator.startKeyframe(8);
            this.animator.rotate(this.root, 0.0F, (float)Math.toRadians(-20.0), 0.0F);
            this.animator.rotate(this.left_leg, (float)Math.toRadians(10.0), (float)Math.toRadians(-7.5), (float)Math.toRadians(-5.0));
            this.animator.move(this.left_leg, 1.0F, -1.0F, 1.0F);
            this.animator.rotate(this.right_leg, 0.0F, (float)Math.toRadians(7.5), (float)Math.toRadians(5.0));
            this.animator.rotate(this.lower_body, (float)Math.toRadians(-5.0), (float)Math.toRadians(10.0), (float)Math.toRadians(10.0));
            this.animator.rotate(this.body, (float)Math.toRadians(10.0), (float)Math.toRadians(-2.5), (float)Math.toRadians(5.0));
            this.animator.rotate(this.left_arm, (float)Math.toRadians(115.0), (float)Math.toRadians(7.5), (float)Math.toRadians(-10.0));
            this.animator.rotate(this.right_arm, (float)Math.toRadians(20.0), (float)Math.toRadians(25.0), (float)Math.toRadians(12.5));
            this.animator.rotate(this.left_weapon, (float)Math.toRadians(90.0), (float)Math.toRadians(-2.5), (float)Math.toRadians(-90.0));
            this.animator.rotate(this.neck, 0.0F, (float)Math.toRadians(7.5), 0.0F);
            this.animator.rotate(this.jaw, (float)Math.toRadians(22.5), 0.0F, 0.0F);
            this.animator.rotate(this.tail1, (float)Math.toRadians(-2.5), (float)Math.toRadians(-17.5), 0.0F);
            this.animator.rotate(this.tail2, (float)Math.toRadians(17.5), (float)Math.toRadians(-7.5), (float)Math.toRadians(-2.5));
            this.animator.endKeyframe();
            this.animator.startKeyframe(4);
            this.animator.rotate(this.root, (float)Math.toRadians(22.5), (float)Math.toRadians(40.0), (float)Math.toRadians(20.0));
            this.animator.rotate(this.left_leg, (float)Math.toRadians(20.0), (float)Math.toRadians(-12.5), (float)Math.toRadians(-25.0));
            this.animator.move(this.left_leg, 1.0F, -2.0F, 1.0F);
            this.animator.rotate(this.right_leg, (float)Math.toRadians(-7.5), (float)Math.toRadians(12.5), 0.0F);
            this.animator.rotate(this.pelvis, 0.0F, (float)Math.toRadians(42.5), 0.0F);
            this.animator.rotate(this.lower_body, (float)Math.toRadians(-5.0), (float)Math.toRadians(17.5), (float)Math.toRadians(10.0));
            this.animator.rotate(this.body, (float)Math.toRadians(20.0), (float)Math.toRadians(35.0), (float)Math.toRadians(-7.5));
            this.animator.rotate(this.left_arm, (float)Math.toRadians(-5.0), (float)Math.toRadians(-35.0), (float)Math.toRadians(-90.0));
            this.animator.rotate(this.right_arm, (float)Math.toRadians(112.5), (float)Math.toRadians(12.5), (float)Math.toRadians(32.5));
            this.animator.rotate(this.left_weapon, (float)Math.toRadians(92.5), 0.0F, (float)Math.toRadians(-180.0));
            this.animator.rotate(this.neck, (float)Math.toRadians(-10.0), (float)Math.toRadians(5.0), (float)Math.toRadians(10.0));
            this.animator.rotate(this.jaw, (float)Math.toRadians(22.5), 0.0F, 0.0F);
            this.animator.rotate(this.tail1, (float)Math.toRadians(10.0), (float)Math.toRadians(30.0), (float)Math.toRadians(7.5));
            this.animator.rotate(this.tail2, (float)Math.toRadians(17.5), (float)Math.toRadians(-7.5), (float)Math.toRadians(-5.0));
            this.animator.endKeyframe();
            this.animator.resetKeyframe(7);
         } else {
            this.animator.startKeyframe(8);
            this.animator.rotate(this.root, 0.0F, (float)Math.toRadians(20.0), 0.0F);
            this.animator.rotate(this.right_leg, (float)Math.toRadians(10.0), (float)Math.toRadians(7.5), (float)Math.toRadians(5.0));
            this.animator.move(this.right_leg, -1.0F, -1.0F, 1.0F);
            this.animator.rotate(this.left_leg, 0.0F, (float)Math.toRadians(-7.5), (float)Math.toRadians(-5.0));
            this.animator.rotate(this.lower_body, (float)Math.toRadians(-5.0), (float)Math.toRadians(-10.0), (float)Math.toRadians(-10.0));
            this.animator.rotate(this.body, (float)Math.toRadians(10.0), (float)Math.toRadians(2.5), (float)Math.toRadians(-5.0));
            this.animator.rotate(this.right_arm, (float)Math.toRadians(115.0), (float)Math.toRadians(-7.5), (float)Math.toRadians(10.0));
            this.animator.rotate(this.right_weapon, (float)Math.toRadians(90.0), (float)Math.toRadians(2.5), (float)Math.toRadians(90.0));
            this.animator.rotate(this.left_arm, (float)Math.toRadians(20.0), (float)Math.toRadians(-25.0), (float)Math.toRadians(-12.5));
            this.animator.rotate(this.neck, 0.0F, (float)Math.toRadians(-7.5), 0.0F);
            this.animator.rotate(this.jaw, (float)Math.toRadians(22.5), 0.0F, 0.0F);
            this.animator.rotate(this.tail1, (float)Math.toRadians(-2.5), (float)Math.toRadians(17.5), 0.0F);
            this.animator.rotate(this.tail2, (float)Math.toRadians(17.5), (float)Math.toRadians(7.5), (float)Math.toRadians(2.5));
            this.animator.endKeyframe();
            this.animator.startKeyframe(4);
            this.animator.rotate(this.root, (float)Math.toRadians(22.5), (float)Math.toRadians(-40.0), (float)Math.toRadians(-20.0));
            this.animator.rotate(this.right_leg, (float)Math.toRadians(20.0), (float)Math.toRadians(12.5), (float)Math.toRadians(25.0));
            this.animator.move(this.right_leg, -1.0F, -2.0F, 1.0F);
            this.animator.rotate(this.left_leg, (float)Math.toRadians(-7.5), (float)Math.toRadians(-12.5), 0.0F);
            this.animator.rotate(this.pelvis, 0.0F, (float)Math.toRadians(-42.5), 0.0F);
            this.animator.rotate(this.lower_body, (float)Math.toRadians(-5.0), (float)Math.toRadians(-17.5), (float)Math.toRadians(-10.0));
            this.animator.rotate(this.body, (float)Math.toRadians(20.0), (float)Math.toRadians(-35.0), (float)Math.toRadians(7.5));
            this.animator.rotate(this.right_arm, (float)Math.toRadians(-5.0), (float)Math.toRadians(35.0), (float)Math.toRadians(90.0));
            this.animator.rotate(this.right_weapon, (float)Math.toRadians(92.5), 0.0F, (float)Math.toRadians(180.0));
            this.animator.rotate(this.left_arm, (float)Math.toRadians(112.5), (float)Math.toRadians(-12.5), (float)Math.toRadians(-32.5));
            this.animator.rotate(this.neck, (float)Math.toRadians(-10.0), (float)Math.toRadians(-5.0), (float)Math.toRadians(-10.0));
            this.animator.rotate(this.jaw, (float)Math.toRadians(22.5), 0.0F, 0.0F);
            this.animator.rotate(this.tail1, (float)Math.toRadians(10.0), (float)Math.toRadians(-30.0), (float)Math.toRadians(-7.5));
            this.animator.rotate(this.tail2, (float)Math.toRadians(17.5), (float)Math.toRadians(7.5), (float)Math.toRadians(5.0));
            this.animator.endKeyframe();
            this.animator.resetKeyframe(7);
         }
      } else if (entity.isLeftHanded()) {
         this.animator.startKeyframe(8);
         this.animator.rotate(this.root, 0.0F, (float)Math.toRadians(-20.0), 0.0F);
         this.animator.rotate(this.legs, (float)Math.toRadians(-7.5), 0.0F, 0.0F);
         this.animator.rotate(this.left_leg, (float)Math.toRadians(10.0), (float)Math.toRadians(-7.5), (float)Math.toRadians(-5.0));
         this.animator.move(this.left_leg, 1.0F, -1.0F, 1.0F);
         this.animator.rotate(this.right_leg, 0.0F, (float)Math.toRadians(7.5), (float)Math.toRadians(5.0));
         this.animator.rotate(this.lower_body, (float)Math.toRadians(-5.0), (float)Math.toRadians(10.0), (float)Math.toRadians(10.0));
         this.animator.rotate(this.body, (float)Math.toRadians(10.0), (float)Math.toRadians(-10.0), (float)Math.toRadians(5.0));
         this.animator.rotate(this.left_arm, (float)Math.toRadians(22.5), (float)Math.toRadians(2.5), (float)Math.toRadians(2.5));
         this.animator.rotate(this.left_weapon, (float)Math.toRadians(90.0), (float)Math.toRadians(-2.5), (float)Math.toRadians(-90.0));
         this.animator.rotate(this.right_arm, (float)Math.toRadians(-45.0), (float)Math.toRadians(25.0), (float)Math.toRadians(12.5));
         this.animator.rotate(this.neck, (float)Math.toRadians(-7.5), (float)Math.toRadians(7.5), 0.0F);
         this.animator.rotate(this.jaw, (float)Math.toRadians(15.0), 0.0F, 0.0F);
         this.animator.rotate(this.tail1, (float)Math.toRadians(-32.5), (float)Math.toRadians(-17.5), 0.0F);
         this.animator.rotate(this.tail2, (float)Math.toRadians(52.5), (float)Math.toRadians(-7.5), (float)Math.toRadians(-2.5));
         this.animator.endKeyframe();
         this.animator.startKeyframe(4);
         this.animator.rotate(this.root, (float)Math.toRadians(22.5), (float)Math.toRadians(40.0), (float)Math.toRadians(20.0));
         this.animator.rotate(this.legs, (float)Math.toRadians(-7.5), 0.0F, 0.0F);
         this.animator.rotate(this.left_leg, (float)Math.toRadians(20.0), (float)Math.toRadians(-12.5), (float)Math.toRadians(-25.0));
         this.animator.move(this.left_leg, 1.0F, -2.0F, 1.0F);
         this.animator.rotate(this.right_leg, (float)Math.toRadians(-7.5), (float)Math.toRadians(12.5), 0.0F);
         this.animator.rotate(this.pelvis, 0.0F, (float)Math.toRadians(42.5), 0.0F);
         this.animator.rotate(this.lower_body, (float)Math.toRadians(-5.0), (float)Math.toRadians(17.5), (float)Math.toRadians(10.0));
         this.animator.rotate(this.body, (float)Math.toRadians(20.0), (float)Math.toRadians(27.5), (float)Math.toRadians(-7.5));
         this.animator.rotate(this.left_arm, (float)Math.toRadians(-97.5), (float)Math.toRadians(-40.0), (float)Math.toRadians(-77.5));
         this.animator.rotate(this.left_weapon, (float)Math.toRadians(92.5), 0.0F, (float)Math.toRadians(-180.0));
         this.animator.rotate(this.right_arm, (float)Math.toRadians(47.5), (float)Math.toRadians(12.5), (float)Math.toRadians(32.5));
         this.animator.rotate(this.neck, (float)Math.toRadians(-17.5), (float)Math.toRadians(5.0), (float)Math.toRadians(10.0));
         this.animator.rotate(this.jaw, (float)Math.toRadians(25.0), 0.0F, 0.0F);
         this.animator.rotate(this.tail1, (float)Math.toRadians(-20.0), (float)Math.toRadians(30.0), (float)Math.toRadians(7.5));
         this.animator.rotate(this.tail2, (float)Math.toRadians(52.5), (float)Math.toRadians(-7.5), (float)Math.toRadians(-5.0));
         this.animator.endKeyframe();
         this.animator.resetKeyframe(7);
      } else {
         this.animator.startKeyframe(8);
         this.animator.rotate(this.root, 0.0F, (float)Math.toRadians(20.0), 0.0F);
         this.animator.rotate(this.legs, (float)Math.toRadians(-7.5), 0.0F, 0.0F);
         this.animator.rotate(this.right_leg, (float)Math.toRadians(10.0), (float)Math.toRadians(7.5), (float)Math.toRadians(5.0));
         this.animator.move(this.right_leg, -1.0F, -1.0F, 1.0F);
         this.animator.rotate(this.left_leg, 0.0F, (float)Math.toRadians(-7.5), (float)Math.toRadians(-5.0));
         this.animator.rotate(this.lower_body, (float)Math.toRadians(-5.0), (float)Math.toRadians(-10.0), (float)Math.toRadians(-10.0));
         this.animator.rotate(this.body, (float)Math.toRadians(10.0), (float)Math.toRadians(10.0), (float)Math.toRadians(-5.0));
         this.animator.rotate(this.right_arm, (float)Math.toRadians(22.5), (float)Math.toRadians(-2.5), (float)Math.toRadians(-2.5));
         this.animator.rotate(this.right_weapon, (float)Math.toRadians(90.0), (float)Math.toRadians(2.5), (float)Math.toRadians(90.0));
         this.animator.rotate(this.left_arm, (float)Math.toRadians(-45.0), (float)Math.toRadians(-25.0), (float)Math.toRadians(-12.5));
         this.animator.rotate(this.neck, (float)Math.toRadians(-7.5), (float)Math.toRadians(-7.5), 0.0F);
         this.animator.rotate(this.jaw, (float)Math.toRadians(15.0), 0.0F, 0.0F);
         this.animator.rotate(this.tail1, (float)Math.toRadians(-32.5), (float)Math.toRadians(17.5), 0.0F);
         this.animator.rotate(this.tail2, (float)Math.toRadians(52.5), (float)Math.toRadians(7.5), (float)Math.toRadians(2.5));
         this.animator.endKeyframe();
         this.animator.startKeyframe(4);
         this.animator.rotate(this.root, (float)Math.toRadians(22.5), (float)Math.toRadians(-40.0), (float)Math.toRadians(-20.0));
         this.animator.rotate(this.legs, (float)Math.toRadians(-7.5), 0.0F, 0.0F);
         this.animator.rotate(this.right_leg, (float)Math.toRadians(20.0), (float)Math.toRadians(12.5), (float)Math.toRadians(25.0));
         this.animator.move(this.right_leg, -1.0F, -2.0F, 1.0F);
         this.animator.rotate(this.left_leg, (float)Math.toRadians(-7.5), (float)Math.toRadians(-12.5), 0.0F);
         this.animator.rotate(this.pelvis, 0.0F, (float)Math.toRadians(-42.5), 0.0F);
         this.animator.rotate(this.lower_body, (float)Math.toRadians(-5.0), (float)Math.toRadians(-17.5), (float)Math.toRadians(-10.0));
         this.animator.rotate(this.body, (float)Math.toRadians(20.0), (float)Math.toRadians(-27.5), (float)Math.toRadians(7.5));
         this.animator.rotate(this.right_arm, (float)Math.toRadians(-97.5), (float)Math.toRadians(40.0), (float)Math.toRadians(77.5));
         this.animator.rotate(this.right_weapon, (float)Math.toRadians(92.5), 0.0F, (float)Math.toRadians(180.0));
         this.animator.rotate(this.left_arm, (float)Math.toRadians(47.5), (float)Math.toRadians(-12.5), (float)Math.toRadians(-32.5));
         this.animator.rotate(this.neck, (float)Math.toRadians(-17.5), (float)Math.toRadians(-5.0), (float)Math.toRadians(-10.0));
         this.animator.rotate(this.jaw, (float)Math.toRadians(25.0), 0.0F, 0.0F);
         this.animator.rotate(this.tail1, (float)Math.toRadians(-20.0), (float)Math.toRadians(-30.0), (float)Math.toRadians(-7.5));
         this.animator.rotate(this.tail2, (float)Math.toRadians(52.5), (float)Math.toRadians(7.5), (float)Math.toRadians(5.0));
         this.animator.endKeyframe();
         this.animator.resetKeyframe(7);
      }
   }

   public void setupAnim(Koboleton_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float walkSpeed = 0.5F;
      float runSpeed = 0.8F;
      float walkDegree = 0.5F;
      float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      float angryProgress = entityIn.prevangryProgress + (entityIn.angryProgress - entityIn.prevangryProgress) * partialTick;
      this.faceTarget(netHeadYaw, headPitch, 1.0F, new AdvancedModelBox[]{this.head});
      this.walk(this.lower_body, walkSpeed * 0.1F, walkDegree * 0.2F, false, 0.0F, walkDegree * 0.0625F, ageInTicks, 1.0F);
      this.walk(this.jaw, walkSpeed * 0.1F, walkDegree * 0.2F, false, 0.0F, walkDegree * 0.2F, ageInTicks, 1.0F);
      this.right_arm.rotationPointY = this.right_arm.rotationPointY - (Mth.cos(ageInTicks * walkSpeed * 0.1F + 1.0F) * 0.5F + 0.5F);
      this.left_arm.rotationPointY = this.left_arm.rotationPointY - (Mth.cos(ageInTicks * walkSpeed * 0.1F + 1.0F) * 0.5F + 0.5F);
      this.right_arm.rotationPointZ = this.right_arm.rotationPointZ + (Mth.cos(ageInTicks * walkSpeed * 0.1F) * 1.0F - 1.0F);
      this.left_arm.rotationPointZ = this.left_arm.rotationPointZ + (Mth.cos(ageInTicks * walkSpeed * 0.1F) * 1.0F - 1.0F);
      if (angryProgress <= 0.0F) {
         this.walk(this.left_leg, walkSpeed, walkDegree * 1.2F, true, 0.0F, -0.2F, limbSwing, limbSwingAmount);
         this.walk(this.left_arm, walkSpeed, walkDegree * 1.2F, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
         this.walk(this.left_fore_leg, walkSpeed, walkDegree * 0.5F, true, -1.0F, -0.2F, limbSwing, limbSwingAmount);
         this.walk(this.left_foot, walkSpeed, walkDegree * -1.0F, true, -1.5F, 0.0F, limbSwing, limbSwingAmount);
         this.left_foot.rotationPointY = this.left_foot.rotationPointY
            - Math.abs((float)(Math.cos(limbSwing * walkSpeed - 1.5F) * walkDegree * 1.5 * limbSwingAmount));
         this.walk(this.right_leg, walkSpeed, walkDegree * 1.2F, false, 0.0F, 0.2F, limbSwing, limbSwingAmount);
         this.walk(this.right_arm, walkSpeed, walkDegree * 1.2F, true, 0.0F, 0.0F, limbSwing, limbSwingAmount);
         this.walk(this.right_fore_leg, walkSpeed, walkDegree * 0.5F, false, -1.0F, -0.2F, limbSwing, limbSwingAmount);
         this.walk(this.right_foot, walkSpeed, walkDegree * -1.0F, false, -1.5F, 0.0F, limbSwing, limbSwingAmount);
         this.right_foot.rotationPointY = this.right_foot.rotationPointY
            - Math.abs((float)(Math.cos(limbSwing * walkSpeed - 1.5F) * walkDegree * 1.5 * limbSwingAmount));
         this.walk(this.tail1, walkSpeed * 0.1F, walkDegree * 0.8F, true, 0.0F, walkDegree * 0.35F, ageInTicks, 1.0F);
         this.walk(this.tail2, walkSpeed * 0.1F, walkDegree * 0.8F, false, 1.0F, 0.0F, ageInTicks, 1.0F);
      } else {
         this.walk(this.left_leg, runSpeed, walkDegree * 3.0F, true, 0.0F, -0.5F, limbSwing, limbSwingAmount);
         this.left_foot.rotationPointY = this.left_foot.rotationPointY
            - Math.abs((float)(Math.cos(limbSwing * walkSpeed - 1.5F) * walkDegree * 1.5 * limbSwingAmount));
         this.walk(this.right_leg, runSpeed, walkDegree * 3.0F, false, 0.0F, 0.5F, limbSwing, limbSwingAmount);
         if (entityIn.isLeftHanded()) {
            this.walk(this.right_arm, runSpeed, walkDegree * 1.6F, false, 0.0F, -walkDegree * 1.6F, limbSwing, limbSwingAmount);
            this.walk(this.left_arm, runSpeed, walkDegree * 0.6F, true, 0.0F, -walkDegree * 0.6F, limbSwing, limbSwingAmount);
         } else {
            this.walk(this.left_arm, runSpeed, walkDegree * 1.6F, false, 0.0F, -walkDegree * 1.6F, limbSwing, limbSwingAmount);
            this.walk(this.right_arm, runSpeed, walkDegree * 0.6F, true, 0.0F, -walkDegree * 0.6F, limbSwing, limbSwingAmount);
         }

         this.right_foot.rotationPointY = this.right_foot.rotationPointY
            - Math.abs((float)(Math.cos(limbSwing * walkSpeed - 1.5F) * walkDegree * 1.5 * limbSwingAmount));
         this.walk(this.tail1, runSpeed, walkDegree * 1.6F, true, 0.0F, walkDegree * 0.8F, limbSwing, limbSwingAmount);
         this.walk(this.tail2, runSpeed, walkDegree * 1.6F, false, 0.0F, walkDegree * 0.8F, limbSwing, limbSwingAmount);
         this.bob(this.root, -runSpeed, walkDegree * -6.0F, true, limbSwing, limbSwingAmount);
      }

      this.bob(this.pelvis, walkSpeed * 0.1F, walkDegree * 0.4F, false, ageInTicks, 1.0F);
      this.progressRotationPrev(this.legs, angryProgress, (float)Math.toRadians(7.5), 0.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.body, angryProgress, 0.0F, (float)Math.toRadians(-7.5), 0.0F, 10.0F);
      if (entityIn.isLeftHanded()) {
         this.progressRotationPrev(this.left_arm, angryProgress, (float)Math.toRadians(92.5), (float)Math.toRadians(5.0), (float)Math.toRadians(-12.5), 10.0F);
         this.progressRotationPrev(this.right_arm, angryProgress, (float)Math.toRadians(65.0), 0.0F, 0.0F, 10.0F);
      } else {
         this.progressRotationPrev(this.right_arm, angryProgress, (float)Math.toRadians(92.5), (float)Math.toRadians(-5.0), (float)Math.toRadians(12.5), 10.0F);
         this.progressRotationPrev(this.left_arm, angryProgress, (float)Math.toRadians(65.0), 0.0F, 0.0F, 10.0F);
      }

      this.progressRotationPrev(this.neck, angryProgress, (float)Math.toRadians(7.5), 0.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.jaw, angryProgress, (float)Math.toRadians(7.5), 0.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.tail1, angryProgress, (float)Math.toRadians(30.0), 0.0F, 0.0F, 10.0F);
      this.progressRotationPrev(this.tail2, angryProgress, (float)Math.toRadians(-35.0), 0.0F, 0.0F, 10.0F);
   }

   private float walkValue(float limbSwing, float limbSwingAmount, float speed, float offset, float degree, boolean inverse) {
      return (float)(Math.cos(limbSwing * speed + offset) * degree * limbSwingAmount * (inverse ? -1 : 1));
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.legs,
         this.right_leg,
         this.right_fore_leg,
         this.right_foot,
         this.left_leg,
         this.left_fore_leg,
         this.left_foot,
         this.pelvis,
         this.pelvis_cube,
         this.lower_body,
         this.body,
         new AdvancedModelBox[]{
            this.right_arm,
            this.right_weapon,
            this.left_arm,
            this.left_weapon,
            this.head,
            this.nose,
            this.right_eyebrow,
            this.left_eyebrow,
            this.skull,
            this.jaw,
            this.neck,
            this.tail1,
            this.tail2
         }
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
