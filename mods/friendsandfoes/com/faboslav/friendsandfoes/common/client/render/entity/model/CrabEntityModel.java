package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.ModelPartModelAnimator;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import java.util.ArrayList;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class CrabEntityModel<T extends CrabEntity> extends HierarchicalModel<T> {
   private static final String MAIN = "main";
   private static final String BODY = "body";
   private static final String LEFT_FEELER = "leftFeeler";
   private static final String RIGHT_FEELER = "rightFeeler";
   private static final String LEFT_CLAW = "leftClaw";
   private static final String LEFT_CLAW_PARTS = "leftClawParts";
   private static final String RIGHT_CLAW = "rightClaw";
   private static final String LEFT_FRONT_LEG_JOINT = "leftFrontLegJoint";
   private static final String LEFT_FRONT_LEG = "leftFrontLeg";
   private static final String LEFT_MIDDLE_LEG_JOINT = "leftMiddleLegJoint";
   private static final String LEFT_MIDDLE_LEG = "leftMiddleLeg";
   private static final String LEFT_BACK_LEG_JOINT = "leftBackLegJoint";
   private static final String LEFT_BACK_LEG = "leftBackLeg";
   private static final String RIGHT_FRONT_LEG_JOINT = "rightFrontLegJoint";
   private static final String RIGHT_FRONT_LEG = "rightFrontLeg";
   private static final String RIGHT_MIDDLE_LEG_JOINT = "rightMiddleLegJoint";
   private static final String RIGHT_MIDDLE_LEG = "rightMiddleLeg";
   private static final String RIGHT_BACK_LEG_JOINT = "rightBackLegJoint";
   private static final String RIGHT_BACK_LEG = "rightBackLeg";
   private final ModelPart root;
   private final ModelPart main;
   private final ModelPart body;
   private final ModelPart leftFeeler;
   private final ModelPart rightFeeler;
   private final ModelPart leftClaw;
   private final ModelPart leftClawParts;
   private final ModelPart rightClaw;
   private final ModelPart leftFrontLegJoint;
   private final ModelPart leftFrontLeg;
   private final ModelPart leftMiddleLegJoint;
   private final ModelPart leftMiddleLeg;
   private final ModelPart leftBackLegJoint;
   private final ModelPart leftBackLeg;
   private final ModelPart rightFrontLegJoint;
   private final ModelPart rightFrontLeg;
   private final ModelPart rightMiddleLegJoint;
   private final ModelPart rightMiddleLeg;
   private final ModelPart rightBackLegJoint;
   private final ModelPart rightBackLeg;

   public CrabEntityModel(ModelPart root) {
      this.root = root;
      this.main = this.root.getChild("main");
      this.body = this.main.getChild("body");
      this.leftFeeler = this.body.getChild("leftFeeler");
      this.rightFeeler = this.body.getChild("rightFeeler");
      this.leftClaw = this.body.getChild("leftClaw");
      this.leftClawParts = this.leftClaw.getChild("leftClawParts");
      this.rightClaw = this.body.getChild("rightClaw");
      this.leftFrontLegJoint = this.main.getChild("leftFrontLegJoint");
      this.leftFrontLeg = this.leftFrontLegJoint.getChild("leftFrontLeg");
      this.leftMiddleLegJoint = this.main.getChild("leftMiddleLegJoint");
      this.leftMiddleLeg = this.leftMiddleLegJoint.getChild("leftMiddleLeg");
      this.leftBackLegJoint = this.main.getChild("leftBackLegJoint");
      this.leftBackLeg = this.leftBackLegJoint.getChild("leftBackLeg");
      this.rightFrontLegJoint = this.main.getChild("rightFrontLegJoint");
      this.rightFrontLeg = this.rightFrontLegJoint.getChild("rightFrontLeg");
      this.rightMiddleLegJoint = this.main.getChild("rightMiddleLegJoint");
      this.rightMiddleLeg = this.rightMiddleLegJoint.getChild("rightMiddleLeg");
      this.rightBackLegJoint = this.main.getChild("rightBackLegJoint");
      this.rightBackLeg = this.rightBackLegJoint.getChild("rightBackLeg");
   }

   public static LayerDefinition getTexturedModelData() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition root = modelData.getRoot();
      PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -1.5708F, -1.5708F, 1.5708F));
      PartDefinition body = main.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-7.0F, -6.0F, -5.0F, 14.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(38, 0)
            .addBox(-6.0F, -7.0F, -5.0F, 12.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -3.0F, 0.0F)
      );
      PartDefinition leftFeeler = body.addOrReplaceChild(
         "leftFeeler",
         CubeListBuilder.create().texOffs(38, 1).mirror().addBox(-1.0F, 0.0F, -1.05F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-2.0F, -6.0F, -4.25F, -0.3491F, 0.0F, 0.0F)
      );
      PartDefinition rightFeeler = body.addOrReplaceChild(
         "rightFeeler",
         CubeListBuilder.create().texOffs(38, 1).addBox(-1.0F, 0.0F, -1.05F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.0F, -6.0F, -4.25F, -0.3491F, 0.0F, 0.0F)
      );
      PartDefinition leftClaw = body.addOrReplaceChild(
         "leftClaw", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -3.0F, -5.0F, 0.0F, -1.0472F, -0.1309F)
      );
      PartDefinition leftClawParts = leftClaw.addOrReplaceChild(
         "leftClawParts",
         CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-11.75F, -3.5F, -2.5F, 12.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(0, 28)
            .addBox(-10.75F, 0.5F, -1.5F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(4.0F, 1.0F, -2.5F)
      );
      PartDefinition rightClaw = body.addOrReplaceChild(
         "rightClaw",
         CubeListBuilder.create().texOffs(44, 16).mirror().addBox(-2.0F, -1.5F, -4.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-7.0F, -2.0F, -5.0F, 0.0F, 0.829F, 0.2182F)
      );
      PartDefinition leftFrontLegJoint = main.addOrReplaceChild(
         "leftFrontLegJoint", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -3.5F, -1.0F, 0.0F, 0.6109F, 0.0F)
      );
      PartDefinition leftFrontLeg = leftFrontLegJoint.addOrReplaceChild(
         "leftFrontLeg",
         CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F)
      );
      PartDefinition leftMiddleLegJoint = main.addOrReplaceChild(
         "leftMiddleLegJoint", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -3.5F, 1.0F, 0.0F, 0.3054F, 0.0F)
      );
      PartDefinition leftMiddleLeg = leftMiddleLegJoint.addOrReplaceChild(
         "leftMiddleLeg",
         CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition leftBackLegJoint = main.addOrReplaceChild("leftBackLegJoint", CubeListBuilder.create(), PartPose.offset(7.0F, -3.5F, 3.0F));
      PartDefinition leftBackLeg = leftBackLegJoint.addOrReplaceChild(
         "leftBackLeg",
         CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition rightFrontLegJoint = main.addOrReplaceChild(
         "rightFrontLegJoint", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -3.5F, -1.0F, 0.0F, -0.6109F, 0.0F)
      );
      PartDefinition rightFrontLeg = rightFrontLegJoint.addOrReplaceChild(
         "rightFrontLeg",
         CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition rightMiddleLegJoint = main.addOrReplaceChild(
         "rightMiddleLegJoint", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -3.5F, 1.0F, 0.0F, -0.3054F, 0.0F)
      );
      PartDefinition rightMiddleLeg = rightMiddleLegJoint.addOrReplaceChild(
         "rightMiddleLeg",
         CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition rightBackLegJoint = main.addOrReplaceChild("rightBackLegJoint", CubeListBuilder.create(), PartPose.offset(-7.0F, -3.5F, 3.0F));
      PartDefinition rightBackLeg = rightBackLegJoint.addOrReplaceChild(
         "rightBackLeg",
         CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      return LayerDefinition.create(modelData, 64, 64);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T crab, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.updateKeyframeAnimations(crab, limbAngle, limbDistance, animationProgress);
      this.updateModelPartAnimations(crab);
   }

   public void updateKeyframeAnimations(CrabEntity crab, float limbAngle, float limbDistance, float animationProgress) {
      AnimationHolder movementAnimation = crab.getMovementAnimation();
      ArrayList<AnimationHolder> animations = crab.getTrackedAnimations();
      AnimationContextTracker animationContextTracker = crab.getAnimationContextTracker();
      int currentTick = crab.tickCount;
      float animationSpeedModifier = 1.0F;
      KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, 2.5F, 4.5F, animationSpeedModifier);
      KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
   }

   public void updateModelPartAnimations(CrabEntity crab) {
      float scaleModifier = crab.getSize().getScaleModifier();
      float pivotX = 0.0F;
      float pivotY = crab.onClimbable() ? 17.0F * scaleModifier : 24.0F;
      float pivotZ = crab.onClimbable() ? -9.0F * scaleModifier : 0.0F;
      float pitch = -1.5708F;
      float yaw = crab.onClimbable() ? 0.0F : -1.5708F;
      float roll = 1.5708F;
      ModelPartModelAnimator.animateModelPartPositionBasedOnTicks(crab.getAnimationContextTracker(), this.main, crab.tickCount, pivotX, pivotY, pivotZ, 20);
      ModelPartModelAnimator.animateModelPartRotationBasedOnTicks(crab.getAnimationContextTracker(), this.main, crab.tickCount, pitch, yaw, roll, 20);
   }
}
