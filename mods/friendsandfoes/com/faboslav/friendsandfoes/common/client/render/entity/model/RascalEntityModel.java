package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.entity.RascalEntity;
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

public final class RascalEntityModel<T extends RascalEntity> extends HierarchicalModel<T> {
   private static final String MODEL_PART_HEAD = "head";
   private static final String MODEL_PART_BODY = "body";
   private static final String MODEL_PART_BAG = "bag";
   private static final String MODEL_PART_LEFT_ARM = "leftArm";
   private static final String MODEL_PART_RIGHT_ARM = "rightArm";
   private static final String MODEL_PART_LEFT_LEG = "leftLeg";
   private static final String MODEL_PART_RIGHT_LEG = "rightLeg";
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart bag;
   private final ModelPart leftArm;
   private final ModelPart rightArm;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;

   public RascalEntityModel(ModelPart root) {
      this.root = root;
      this.head = this.root.getChild("head");
      this.body = this.root.getChild("body");
      this.bag = this.root.getChild("bag");
      this.leftArm = this.root.getChild("leftArm");
      this.rightArm = this.root.getChild("rightArm");
      this.leftLeg = this.root.getChild("leftLeg");
      this.rightLeg = this.root.getChild("rightLeg");
   }

   public static LayerDefinition getTexturedModelData() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition root = modelData.getRoot();
      root.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 52)
            .addBox(-4.0F, -2.0F, -5.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(28, 36)
            .addBox(-4.0F, -3.0F, -5.0F, 8.0F, 9.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, 9.0F, -5.0F)
      );
      root.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-6.0F, 0.0F, -4.0F, 12.0F, 15.0F, 8.0F, new CubeDeformation(-0.01F))
            .texOffs(0, 23)
            .addBox(-6.0F, 12.0F, -4.0F, 12.0F, 5.0F, 8.0F, new CubeDeformation(-0.5F))
            .texOffs(44, 0)
            .addBox(3.0F, 0.0F, -4.0F, 2.0F, 15.0F, 8.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, 4.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "bag",
         CubeListBuilder.create().texOffs(0, 36).addBox(-4.0F, -0.5F, -0.5F, 8.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(3.0F, 7.5F, 4.5F)
      );
      root.addOrReplaceChild(
         "leftArm",
         CubeListBuilder.create().texOffs(50, 28).mirror().addBox(-3.0F, -2.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(-6.0F, 11.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "rightArm",
         CubeListBuilder.create().texOffs(50, 28).addBox(0.0F, -2.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(6.0F, 11.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "leftLeg",
         CubeListBuilder.create().texOffs(28, 54).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(-3.0F, 18.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "rightLeg",
         CubeListBuilder.create().texOffs(28, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(3.0F, 18.0F, 0.0F)
      );
      return LayerDefinition.create(modelData, 64, 64);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T rascal, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.updateAnimations(rascal, limbAngle, limbDistance, animationProgress);
   }

   public void updateAnimations(RascalEntity rascal, float limbAngle, float limbDistance, float animationProgress) {
      AnimationHolder movementAnimation = rascal.getMovementAnimation();
      ArrayList<AnimationHolder> animations = rascal.getTrackedAnimations();
      AnimationContextTracker animationContextTracker = rascal.getAnimationContextTracker();
      int currentTick = rascal.tickCount;
      float animationSpeedModifier = 1.0F;
      KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, 1.5F, 2.5F, animationSpeedModifier);
      KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
   }
}
