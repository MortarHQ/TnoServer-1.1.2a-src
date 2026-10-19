package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import com.faboslav.friendsandfoes.common.entity.animation.MaulerAnimations;
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

public final class MaulerEntityModel<T extends MaulerEntity> extends HierarchicalModel<T> {
   private static final String MODEL_PART_HEAD = "head";
   private static final String MODEL_PART_UPPER_JAW = "upperJaw";
   private static final String MODEL_PART_LOWER_JAW = "lowerJaw";
   private static final String MODEL_PART_BODY = "body";
   private static final String MODEL_PART_FRONT_LEFT_LEG = "frontLeftLeg";
   private static final String MODEL_PART_FRONT_RIGHT_LEG = "frontRightLeg";
   private static final String MODEL_PART_BACK_LEFT_LEG = "backLeftLeg";
   private static final String MODEL_PART_BACK_RIGHT_LEG = "backRightLeg";
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart upperJaw;
   private final ModelPart lowerJaw;
   private final ModelPart body;
   private final ModelPart frontLeftLeg;
   private final ModelPart frontRightLeg;
   private final ModelPart backLeftLeg;
   private final ModelPart backRightLeg;

   public MaulerEntityModel(ModelPart root) {
      this.root = root;
      this.head = this.root.getChild("head");
      this.upperJaw = this.head.getChild("upperJaw");
      this.lowerJaw = this.head.getChild("lowerJaw");
      this.body = this.root.getChild("body");
      this.frontLeftLeg = this.root.getChild("frontLeftLeg");
      this.frontRightLeg = this.root.getChild("frontRightLeg");
      this.backLeftLeg = this.root.getChild("backLeftLeg");
      this.backRightLeg = this.root.getChild("backRightLeg");
   }

   public static LayerDefinition getTexturedModelData() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition root = modelData.getRoot();
      root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 4.0F));
      PartDefinition head = root.getChild("head");
      head.addOrReplaceChild(
         "upperJaw", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -3.0F, -10.0F, 9.0F, 3.0F, 10.0F), PartPose.offset(0.0F, -2.0F, 1.0F)
      );
      head.addOrReplaceChild(
         "lowerJaw", CubeListBuilder.create().texOffs(0, 13).addBox(-4.5F, 0.0F, -10.0F, 9.0F, 3.0F, 10.0F), PartPose.offset(0.0F, -2.0F, 1.0F)
      );
      root.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 26).addBox(-3.5F, 0.0F, -3.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.01F)),
         PartPose.offset(0.0F, 20.0F, 1.0F)
      );
      root.addOrReplaceChild(
         "frontLeftLeg", CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(2.5F, 21.0F, -1.0F)
      );
      root.addOrReplaceChild(
         "frontRightLeg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-2.5F, 21.0F, -1.0F)
      );
      root.addOrReplaceChild(
         "backLeftLeg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(2.5F, 21.0F, 3.0F)
      );
      root.addOrReplaceChild(
         "backRightLeg", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(-2.5F, 21.0F, 3.0F)
      );
      return LayerDefinition.create(modelData, 64, 64);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T mauler, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.updateKeyframeAnimations(mauler, limbAngle, limbDistance, animationProgress);
   }

   public void updateKeyframeAnimations(MaulerEntity mauler, float limbAngle, float limbDistance, float animationProgress) {
      AnimationHolder movementAnimation = mauler.getMovementAnimation();
      ArrayList<AnimationHolder> animations = mauler.getTrackedAnimations();
      AnimationContextTracker animationContextTracker = mauler.getAnimationContextTracker();
      int currentTick = mauler.tickCount;
      float animationSpeedModifier = mauler.getAnimationSpeedModifier();
      KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, 2.5F, 3.5F, animationSpeedModifier);
      if (mauler.isAngry()) {
         KeyframeModelAnimator.updateStaticKeyframeAnimation(
            this, animationContextTracker, MaulerAnimations.SNAP, currentTick, animationProgress, animationSpeedModifier
         );
      }

      KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
   }
}
