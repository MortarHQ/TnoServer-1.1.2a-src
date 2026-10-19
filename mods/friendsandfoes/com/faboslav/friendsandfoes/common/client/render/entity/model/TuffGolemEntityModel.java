package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
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

public final class TuffGolemEntityModel<T extends TuffGolemEntity> extends HierarchicalModel<T> {
   private static final String MODEL_PART_BODY = "body";
   private static final String MODEL_PART_NOSE = "nose";
   private static final String MODEL_PART_FRONT_CLOTH = "frontCloth";
   private static final String MODEL_PART_BACK_CLOTH = "backCloth";
   private static final String MODEL_PART_CLOTH_STAND = "clothStand";
   private static final String MODEL_PART_LEFT_ARM = "leftArm";
   private static final String MODEL_PART_RIGHT_ARM = "rightArm";
   private static final String MODEL_PART_LEFT_LEG = "leftLeg";
   private static final String MODEL_PART_RIGHT_LEG = "rightLeg";
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart nose;
   private final ModelPart frontCloth;
   private final ModelPart backCloth;
   private final ModelPart clothStand;
   private final ModelPart leftArm;
   private final ModelPart rightArm;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;

   public TuffGolemEntityModel(ModelPart root) {
      this.root = root;
      this.body = this.root.getChild("body");
      this.nose = this.body.getChild("nose");
      this.frontCloth = this.body.getChild("frontCloth");
      this.backCloth = this.body.getChild("backCloth");
      this.clothStand = this.body.getChild("clothStand");
      this.leftArm = this.body.getChild("leftArm");
      this.rightArm = this.body.getChild("rightArm");
      this.leftLeg = this.root.getChild("leftLeg");
      this.rightLeg = this.root.getChild("rightLeg");
   }

   public static LayerDefinition getTexturedModelData() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition root = modelData.getRoot();
      root.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.0F, -4.0F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 19.0F, 0.0F)
      );
      PartDefinition body = root.getChild("body");
      body.addOrReplaceChild(
         "nose",
         CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -7.0F, -4.0F)
      );
      body.addOrReplaceChild(
         "frontCloth",
         CubeListBuilder.create().texOffs(36, 4).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.02F)),
         PartPose.offset(0.0F, -4.0F, -4.0F)
      );
      body.addOrReplaceChild(
         "backCloth",
         CubeListBuilder.create().texOffs(40, 18).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.02F)),
         PartPose.offset(0.0F, -4.0F, 0.0F)
      );
      body.addOrReplaceChild(
         "clothStand",
         CubeListBuilder.create().texOffs(0, 35).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.01F)),
         PartPose.offset(0.0F, -4.0F, 0.0F)
      );
      body.addOrReplaceChild(
         "leftArm",
         CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-5.0F, -4.0F, 0.0F)
      );
      body.addOrReplaceChild(
         "rightArm",
         CubeListBuilder.create().texOffs(0, 21).mirror(true).addBox(0.0F, -2.0F, -2.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(4.0F, -4.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "leftLeg",
         CubeListBuilder.create().texOffs(12, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-5.0E-4F)),
         PartPose.offset(-2.0F, 19.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "rightLeg",
         CubeListBuilder.create().texOffs(12, 21).mirror(true).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-5.0E-4F)),
         PartPose.offset(2.0F, 19.0F, 0.0F)
      );
      return LayerDefinition.create(modelData, 64, 64);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T tuffGolem, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      AnimationHolder movementAnimation = tuffGolem.getMovementAnimation();
      ArrayList<AnimationHolder> animations = tuffGolem.getTrackedAnimations();
      AnimationContextTracker animationContextTracker = tuffGolem.getAnimationContextTracker();
      int currentTick = tuffGolem.tickCount;
      float animationSpeedModifier = 1.0F;
      this.root().getAllParts().forEach(ModelPart::resetPose);
      KeyframeModelAnimator.updateMovementKeyframeAnimations(
         this,
         movementAnimation,
         limbAngle,
         limbDistance,
         4.0F * tuffGolem.getMovementSpeedModifier(),
         4.0F * tuffGolem.getMovementSpeedModifier(),
         animationSpeedModifier
      );
      KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
   }
}
