package com.faboslav.friendsandfoes.common.client.render.entity.model;

import com.faboslav.friendsandfoes.common.client.render.entity.model.animation.KeyframeModelAnimator;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.entity.animation.WildfireAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class WildfireEntityModel<T extends WildfireEntity> extends HierarchicalModel<T> {
   private static final String MODEL_PART_BODY = "body";
   private static final String MODEL_PART_HEAD = "head";
   private static final String MODEL_PART_HELMET = "helmet";
   private static final String MODEL_PART_SHIELDS = "shields";
   private static final String MODEL_PART_FRONT_SHIELD = "frontShield";
   private static final String MODEL_PART_RIGHT_SHIELD = "rightShield";
   private static final String MODEL_PART_BACK_SHIELD = "backShield";
   private static final String MODEL_PART_LEFT_SHIELD = "leftShield";
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart head;
   private final ModelPart helmet;
   private final ModelPart shields;
   private final ModelPart frontShield;
   private final ModelPart rightShield;
   private final ModelPart backShield;
   private final ModelPart leftShield;
   private final List<ModelPart> shieldsModelParts;

   public WildfireEntityModel(ModelPart root) {
      this.root = root;
      this.body = this.root.getChild("body");
      this.head = this.body.getChild("head");
      this.helmet = this.head.getChild("helmet");
      this.shields = this.root.getChild("shields");
      this.frontShield = this.shields.getChild("frontShield");
      this.rightShield = this.shields.getChild("rightShield");
      this.backShield = this.shields.getChild("backShield");
      this.leftShield = this.shields.getChild("leftShield");
      this.shieldsModelParts = this.shields.getAllParts().toList();
   }

   public static LayerDefinition getTexturedModelData() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition root = modelData.getRoot();
      PartDefinition body = root.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -21.0F, -2.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      PartDefinition head = body.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 26).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -24.0F, 0.0F)
      );
      head.addOrReplaceChild(
         "helmet",
         CubeListBuilder.create().texOffs(0, 43).addBox(-4.0F, -7.5F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.2F)),
         PartPose.offset(0.0F, 1.5F, 0.0F)
      );
      PartDefinition shields = root.addOrReplaceChild("shields", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      shields.addOrReplaceChild(
         "frontShield",
         CubeListBuilder.create().texOffs(17, 0).addBox(-5.0F, 3.5F, -9.5F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.2618F, 0.0F, 0.0F)
      );
      shields.addOrReplaceChild(
         "rightShield",
         CubeListBuilder.create().texOffs(17, 0).addBox(-5.0F, 3.5F, -9.5F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.2618F, 1.5708F, 0.0F)
      );
      shields.addOrReplaceChild(
         "backShield",
         CubeListBuilder.create().texOffs(17, 0).addBox(-5.0F, 3.5F, -9.5F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.2618F, 3.1416F, 0.0F)
      );
      shields.addOrReplaceChild(
         "leftShield",
         CubeListBuilder.create().texOffs(17, 0).addBox(-5.0F, 3.5F, -9.5F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -22.0F, 0.0F, -0.2618F, -1.5708F, 0.0F)
      );
      return LayerDefinition.create(modelData, 64, 64);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T wildfire, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      int activeShieldsCount = wildfire.getActiveShieldsCount();

      for (int i = 0; i < 4; i++) {
         this.shieldsModelParts.get(i).skipDraw = i > activeShieldsCount;
      }

      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.updateKeyframeAnimations(wildfire, limbAngle, limbDistance, animationProgress);
      this.head.yRot = headYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
   }

   public void updateKeyframeAnimations(WildfireEntity wildfire, float limbAngle, float limbDistance, float animationProgress) {
      AnimationHolder movementAnimation = wildfire.getMovementAnimation();
      ArrayList<AnimationHolder> animations = wildfire.getTrackedAnimations();
      AnimationContextTracker animationContextTracker = wildfire.getAnimationContextTracker();
      int currentTick = wildfire.tickCount;
      float animationSpeedModifier = wildfire.getAnimationSpeedModifier();
      KeyframeModelAnimator.updateMovementKeyframeAnimations(this, movementAnimation, limbAngle, limbDistance, 1.0F, 1.0F, animationSpeedModifier);
      KeyframeModelAnimator.updateStaticKeyframeAnimation(
         this, animationContextTracker, WildfireAnimations.SHIELD_ROTATION, currentTick, animationProgress, animationSpeedModifier
      );
      KeyframeModelAnimator.updateKeyframeAnimations(this, animationContextTracker, animations, currentTick, animationProgress, animationSpeedModifier);
   }
}
