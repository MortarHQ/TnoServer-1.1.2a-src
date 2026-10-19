package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.ModelPartAnimator;
import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.ModelPartModelAnimator;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.util.animation.AnimationMath;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public final class GlareEntityModel<T extends GlareEntity> extends HierarchicalModel<T> {
   private static final String MODEL_PART_BODY = "body";
   private static final String MODEL_PART_HEAD = "head";
   private static final String MODEL_PART_EYES = "eyes";
   private static final String MODEL_TOP_AZALEA = "topAzalea";
   private static final String MODEL_BOTTOM_AZALEA = "bottomAzalea";
   private static final String MODEL_SECOND_LAYER = "secondLayer";
   private static final String MODEL_THIRD_LAYER = "thirdLayer";
   private static final String MODEL_FOURTH_LAYER = "fourthLayer";
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart head;
   private final ModelPart eyes;
   private final ModelPart topAzalea;
   private final ModelPart bottomAzalea;
   private final ModelPart secondLayer;
   private final ModelPart thirdLayer;
   private final ModelPart fourthLayer;
   private final ModelPart[] layers;

   public GlareEntityModel(ModelPart root) {
      this.root = root;
      this.body = this.root.getChild("body");
      this.head = this.body.getChild("head");
      this.eyes = this.head.getChild("eyes");
      this.topAzalea = this.head.getChild("topAzalea");
      this.bottomAzalea = this.head.getChild("bottomAzalea");
      this.secondLayer = this.bottomAzalea.getChild("secondLayer");
      this.thirdLayer = this.secondLayer.getChild("thirdLayer");
      this.fourthLayer = this.thirdLayer.getChild("fourthLayer");
      this.layers = new ModelPart[]{this.secondLayer, this.thirdLayer, this.fourthLayer};
   }

   public static LayerDefinition getTexturedModelData() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition root = modelData.getRoot();
      root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));
      PartDefinition body = root.getChild("body");
      body.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -3.0F, 12.0F, 9.0F, 9.0F, new CubeDeformation(-0.02F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition head = body.getChild("head");
      head.addOrReplaceChild(
         "eyes",
         CubeListBuilder.create()
            .texOffs(33, 0)
            .addBox(2.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
            .texOffs(33, 0)
            .addBox(-4.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.offset(0.0F, 5.0F, -3.0F)
      );
      head.addOrReplaceChild(
         "topAzalea",
         CubeListBuilder.create().texOffs(0, 18).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.01F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      head.addOrReplaceChild(
         "bottomAzalea",
         CubeListBuilder.create()
            .texOffs(18, 101)
            .mirror()
            .addBox(-7.0F, 0.75F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(-0.01F))
            .mirror(false)
            .texOffs(0, 40)
            .addBox(-7.0F, -4.0F, -7.0F, 14.0F, 10.0F, 14.0F, new CubeDeformation(0.01F)),
         PartPose.offset(0.0F, 8.0F, 0.0F)
      );
      PartDefinition bottomAzalea = head.getChild("bottomAzalea");
      bottomAzalea.addOrReplaceChild(
         "secondLayer",
         CubeListBuilder.create().texOffs(0, 64).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.01F)),
         PartPose.offset(0.0F, 1.0F, 0.0F)
      );
      PartDefinition secondLayer = bottomAzalea.getChild("secondLayer");
      secondLayer.addOrReplaceChild(
         "thirdLayer",
         CubeListBuilder.create().texOffs(0, 83).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 2.0F, 0.0F)
      );
      PartDefinition thirdLayer = secondLayer.getChild("thirdLayer");
      thirdLayer.addOrReplaceChild(
         "fourthLayer",
         CubeListBuilder.create().texOffs(0, 100).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 2.0F, 0.0F)
      );
      return LayerDefinition.create(modelData, 64, 128);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T glare, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.animateEyes(glare);
      this.animateFloating(glare, animationProgress);
      float movementForce = Mth.sin(limbAngle * 0.1F) * limbDistance * 0.75F;
      float absMovementForce = Math.abs(movementForce);
      if (absMovementForce >= 0.001F) {
         this.head.xRot = AnimationMath.toRadians(40.0F * absMovementForce);
         this.head.zRot = AnimationMath.toRadians(15.0F * movementForce);

         for (ModelPart layer : this.layers) {
            layer.xRot = AnimationMath.toRadians(30.0F * absMovementForce);
            layer.zRot = AnimationMath.toRadians(15.0F * movementForce);
         }
      } else {
         this.head.xRot = AnimationMath.toRadians(0.5F * AnimationMath.sin(animationProgress * 0.125F));
         this.head.zRot = AnimationMath.toRadians(0.5F * AnimationMath.cos(animationProgress * 0.125F));

         for (ModelPart layer : this.layers) {
            layer.xRot = AnimationMath.toRadians(0.75F * AnimationMath.sin(animationProgress * 0.1F));
            layer.zRot = AnimationMath.toRadians(0.75F * AnimationMath.cos(animationProgress * 0.1F));
         }
      }
   }

   private void animateFloating(GlareEntity glare, float animationProgress) {
      float verticalFloatingSpeed = glare.isGrumpy() ? 0.3F : 0.1F;
      float horizontalFloatingSpeed = glare.isGrumpy() ? 0.15F : 0.05F;
      float verticalFloatingOffset;
      float horizontalFloatingOffset;
      if (glare.isOrderedToSit()) {
         verticalFloatingOffset = 0.5F;
         horizontalFloatingOffset = 0.5F;
      } else {
         verticalFloatingOffset = 1.5F;
         horizontalFloatingOffset = 1.0F;
      }

      float targetPivotY = glare.isOrderedToSit() ? 3.0F : 0.11F;
      ModelPartModelAnimator.animateModelPartYPositionBasedOnTicks(glare.getAnimationContextTracker(), this.body, glare.tickCount, targetPivotY, 10);
      if (glare.isGrumpy()) {
         ModelPartAnimator.setXPosition(this.root, AnimationMath.sin(animationProgress, 0.5F));
         ModelPartAnimator.setYPosition(this.root, AnimationMath.absSin(animationProgress, 0.1F));
         ModelPartAnimator.setYRotation(this.root, AnimationMath.sin(animationProgress, 0.05F));
      }

      float verticalFloatingProgress = AnimationMath.sin(animationProgress * verticalFloatingSpeed) * verticalFloatingOffset;
      float horizontalFloatingProgress = AnimationMath.cos(animationProgress * horizontalFloatingSpeed) * horizontalFloatingOffset;
      this.head.y = verticalFloatingProgress;
      this.head.x = horizontalFloatingProgress;
   }

   private void animateEyes(GlareEntity glare) {
      Vec2 targetEyesPositionOffset = glare.getTargetEyesPositionOffset();
      ModelPartModelAnimator.animateModelPartPositionBasedOnTicks(
         glare.getAnimationContextTracker(),
         this.eyes,
         glare.tickCount,
         this.eyes.x + targetEyesPositionOffset.x,
         this.eyes.y + targetEyesPositionOffset.y,
         this.eyes.z,
         10
      );
   }
}
