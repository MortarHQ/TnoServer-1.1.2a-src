package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.data.EntityModelData;

public class ModelUmvuthana extends MowzieGeoModel<EntityUmvuthana> {
   public ResourceLocation getModelResource(EntityUmvuthana object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/umvuthana.geo.json");
   }

   public ResourceLocation getTextureResource(EntityUmvuthana entity) {
      boolean isElite = entity.getMaskType() == MaskType.FAITH || entity.getMaskType() == MaskType.FURY;
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", isElite ? "textures/entity/umvuthana_elite.png" : "textures/entity/umvuthana.png");
   }

   public ResourceLocation getAnimationResource(EntityUmvuthana object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/umvuthana.animation.json");
   }

   public void setCustomAnimations(EntityUmvuthana entity, long instanceId, AnimationState<EntityUmvuthana> animationState) {
      super.setCustomAnimations(entity, instanceId, animationState);
      boolean isRaptor = entity.getMaskType() == MaskType.FURY;
      boolean isElite = entity.getMaskType() == MaskType.FAITH || isRaptor;
      this.getMowzieBone("crestRight").setHidden(!isElite);
      this.getMowzieBone("crestLeft").setHidden(!isElite);
      this.getMowzieBone("crest1").setHidden(!isElite);
      this.getMowzieBone("leftIndexTalon").setHidden(!isRaptor);
      this.getMowzieBone("leftIndexClaw").setHidden(isRaptor);
      this.getMowzieBone("rightIndexTalon").setHidden(!isRaptor);
      this.getMowzieBone("rightIndexClaw").setHidden(isRaptor);
      MowzieGeoBone root = this.getMowzieBone("root");
      if (isElite) {
         root.multiplyScale(0.93F, 0.93F, 0.93F);
      } else {
         root.multiplyScale(0.83F, 0.83F, 0.83F);
      }

      MowzieGeoBone mask = this.getMowzieBone("mask");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      if (entity.getActiveAbilityType() != EntityUmvuthana.TELEPORT_ABILITY) {
         mask.setScale(1.0F / (float)hips.getScale().x, 1.0F / (float)hips.getScale().y, 1.0F / (float)hips.getScale().z);
      }

      if (entity.isAlive() && entity.active) {
         MowzieGeoBone head = this.getMowzieBone("head");
         MowzieGeoBone neck = this.getMowzieBone("neck");
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         float headYaw = Mth.wrapDegrees(entityData.netHeadYaw());
         float headPitch = Mth.wrapDegrees(entityData.headPitch());
         head.addRotX(headPitch * (float) (Math.PI / 180.0) / 2.0F);
         head.addRotY(headYaw * (float) (Math.PI / 180.0) / 2.0F);
         neck.addRotX(headPitch * (float) (Math.PI / 180.0) / 2.0F);
         neck.addRotY(headYaw * (float) (Math.PI / 180.0) / 2.0F);
      }

      MowzieGeoBone maskHand = this.getMowzieBone("maskHand");
      MowzieGeoBone maskTwitcher = this.getMowzieBone("maskTwitcher");
      float maskPlaceSwitch = this.getControllerValue("maskPlacementSwitchController");
      if (maskPlaceSwitch == 1.0) {
         maskTwitcher.setHidden(true);
         maskHand.setHidden(false);
      } else {
         maskTwitcher.setHidden(false);
         maskHand.setHidden(true);
      }

      float animSpeed = 1.4F;
      float limbSwing = animationState.getLimbSwing();
      float limbSwingAmount = animationState.getLimbSwingAmount();
      double forward = Mth.lerp(animationState.getPartialTick(), entity.prevMoveDirForward, entity.moveDirForward);
      double backward = Mth.lerp(animationState.getPartialTick(), entity.prevMoveDirBackward, entity.moveDirBackward);
      double left = Mth.lerp(animationState.getPartialTick(), entity.prevMoveDirLeft, entity.moveDirLeft);
      double right = Mth.lerp(animationState.getPartialTick(), entity.prevMoveDirRight, entity.moveDirRight);
      limbSwingAmount *= 2.0F;
      limbSwingAmount = Math.min(0.7F, limbSwingAmount);
      float locomotionAnimController = this.getControllerValue("locomotionAnimController");
      float runAnim = this.getControllerValue("walkRunSwitchController");
      float walkAnim = 1.0F - runAnim;
      this.walkForwardAnim((float)(forward * locomotionAnimController * walkAnim), limbSwing, limbSwingAmount, animSpeed);
      this.walkBackwardAnim((float)(backward * locomotionAnimController * walkAnim), limbSwing, limbSwingAmount, animSpeed);
      this.walkLeftAnim((float)(left * locomotionAnimController * walkAnim), limbSwing, limbSwingAmount, animSpeed);
      this.walkRightAnim((float)(right * locomotionAnimController * walkAnim), limbSwing, limbSwingAmount, animSpeed);
      this.runAnim(locomotionAnimController * runAnim, limbSwing, limbSwingAmount, animSpeed);
   }

   private void runAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.7F;
      speed = (float)(speed * 0.8);
      hips.addPosY(blend * (float)(Math.cos(limbSwing * speed - 1.7) * 2.0 * globalHeight + 4.0F * globalHeight) * limbSwingAmount);
      hips.addRotX(blend * -0.4F * limbSwingAmount * globalHeight);
      hips.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.1F * globalHeight) * limbSwingAmount);
      chest.addRotY(blend * (float)(-Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.2F * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * -((float)(Math.cos(limbSwing * speed + 1.4 - 1.7) * 0.025 * globalHeight)) * limbSwingAmount);
      neck.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.1F * globalHeight) * limbSwingAmount);
      neck.addRotX(blend * -((float)(Math.cos(limbSwing * speed - 1.5) * 0.25 * globalHeight - 0.2 * globalHeight)) * limbSwingAmount);
      head.addRotX(blend * (float)(Math.cos(limbSwing * speed + 0.175 - 1.7) * 0.25 * globalHeight + 0.2 * globalHeight) * limbSwingAmount);
      leftThigh.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.55F * globalDegree) * limbSwingAmount);
      leftThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.1F * globalDegree - 0.2F) * limbSwingAmount);
      leftShin.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -0.7F * globalDegree) * limbSwingAmount);
      leftAnkle.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * 1.1F * globalDegree + 0.1F * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 3.5) * -1.0 * globalDegree - 1.1F * globalDegree) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 3.1) * 1.6F * globalDegree + 1.8F * globalDegree) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F + 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      rightThigh.addRotX(blend * (float)(-Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.55F * globalDegree) * limbSwingAmount);
      rightThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.1F * globalDegree + 0.2F) * limbSwingAmount);
      rightShin.addRotX(blend * (float)(-Math.cos(limbSwing * speed * 0.5 + 2.5) * -0.7F * globalDegree) * limbSwingAmount);
      rightAnkle.addRotX(blend * (float)(-Math.cos(limbSwing * speed * 0.5 + 2.5) * 1.1F * globalDegree + 0.1F * globalDegree) * limbSwingAmount);
      rightFoot.addRotX(blend * (float)(-Math.cos(limbSwing * speed * 0.5 + 3.5) * -1.0 * globalDegree - 1.1F * globalDegree) * limbSwingAmount);
      rightToesBack.addRotX(blend * (float)(-Math.cos(limbSwing * speed * 0.5 + 3.1) * 1.6F * globalDegree + 1.8F * globalDegree) * limbSwingAmount);
      rightToesBack.addRotX(blend * (float)(-Math.cos(limbSwing * speed * 1.0F + 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      leftArm.addRotY(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.09 * globalHeight)) * limbSwingAmount);
      leftArm.addRotZ(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.09 * globalHeight) * limbSwingAmount);
      leftForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.05 * globalHeight) * limbSwingAmount);
      rightArm.addRotY(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.09 * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.09 * globalHeight)) * limbSwingAmount);
      rightForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.05 * globalHeight) * limbSwingAmount);
   }

   private void walkForwardAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.5F;
      hips.addPosY(blend * (float)(Math.cos(limbSwing * speed) * 1.5 * globalHeight) * limbSwingAmount);
      hips.addRotX(blend * -0.18F * limbSwingAmount * globalHeight);
      hips.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.1F * globalHeight) * limbSwingAmount);
      chest.addRotY(blend * (float)(-Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.2F * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * -((float)(Math.cos(limbSwing * speed + 1.4) * 0.025 * globalHeight)) * limbSwingAmount);
      neck.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.1F * globalHeight) * limbSwingAmount);
      neck.addRotX(blend * -((float)(Math.cos(limbSwing * speed) * 0.175 * globalHeight)) * limbSwingAmount);
      head.addRotX(blend * (float)(Math.cos(limbSwing * speed + 0.175) * 0.175 * globalHeight + 0.18 * globalHeight) * limbSwingAmount);
      leftThigh.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.55F * globalDegree) * limbSwingAmount);
      leftThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.1F * globalDegree - 0.15F) * limbSwingAmount);
      leftShin.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * -0.7F * globalDegree) * limbSwingAmount);
      leftAnkle.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * 1.1F * globalDegree + 0.1F * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -1.3F * globalDegree - 0.4F * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 3.1) * 1.6F * globalDegree + 1.4F * globalDegree) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F + 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      rightThigh.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.55F * globalDegree)) * limbSwingAmount);
      rightThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.1F * globalDegree + 0.15F) * limbSwingAmount);
      rightShin.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * -0.7F * globalDegree)) * limbSwingAmount);
      rightAnkle.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * 1.1F * globalDegree - 0.1F * globalDegree)) * limbSwingAmount);
      rightFoot.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -1.3F * globalDegree + 0.4F * globalDegree)) * limbSwingAmount);
      rightFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      rightToesBack.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 3.1) * 1.6F * globalDegree - 1.4F * globalDegree)) * limbSwingAmount);
      rightToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F + 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      leftArm.addRotY(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight)) * limbSwingAmount);
      leftArm.addRotZ(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight) * limbSwingAmount);
      leftForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.03 * globalHeight) * limbSwingAmount);
      rightArm.addRotY(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight)) * limbSwingAmount);
      rightForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.03 * globalHeight) * limbSwingAmount);
   }

   private void walkBackwardAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.5F;
      hips.addPosY(blend * (float)(Math.cos(limbSwing * speed) * 1.5 * globalHeight) * limbSwingAmount);
      hips.addRotX(blend * 0.18F * limbSwingAmount * globalHeight);
      hips.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * 0.1F * globalHeight) * limbSwingAmount);
      chest.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.2F * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * -((float)(Math.cos(limbSwing * speed + 1.4) * 0.025 * globalHeight)) * limbSwingAmount);
      neck.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * 0.1F * globalHeight) * limbSwingAmount);
      neck.addRotX(blend * -((float)(Math.cos(limbSwing * speed) * 0.175 * globalHeight)) * limbSwingAmount);
      head.addRotX(blend * (float)(Math.cos(limbSwing * speed + 0.175) * 0.175 * globalHeight - 0.18 * globalHeight) * limbSwingAmount);
      leftThigh.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 - 1.5) * 0.55F * globalDegree - 0.3 * globalDegree) * limbSwingAmount);
      leftThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 - 1.5) * 0.1F * globalDegree - 0.15F) * limbSwingAmount);
      leftShin.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 - 2.4) * -0.7F * globalDegree) * limbSwingAmount);
      leftAnkle.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 - 2.4) * 1.1F * globalDegree + 0.1F * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 - 2.5) * -1.3F * globalDegree - 0.4F * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F + 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 - 3.1) * 1.6F * globalDegree + 1.4F * globalDegree) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      rightThigh.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 - 1.5) * 0.55F * globalDegree + 0.3 * globalDegree)) * limbSwingAmount);
      rightThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 - 1.5) * 0.1F * globalDegree + 0.15F) * limbSwingAmount);
      rightShin.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 - 2.4) * -0.7F * globalDegree)) * limbSwingAmount);
      rightAnkle.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 - 2.4) * 1.1F * globalDegree - 0.1F * globalDegree)) * limbSwingAmount);
      rightFoot.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 - 2.5) * -1.3F * globalDegree + 0.4F * globalDegree)) * limbSwingAmount);
      rightFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F + 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      rightToesBack.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 - 3.1) * 1.6F * globalDegree - 1.4F * globalDegree)) * limbSwingAmount);
      rightToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      leftArm.addRotY(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight)) * limbSwingAmount);
      leftArm.addRotZ(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight) * limbSwingAmount);
      leftForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.03 * globalHeight) * limbSwingAmount);
      rightArm.addRotY(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight)) * limbSwingAmount);
      rightForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.03 * globalHeight) * limbSwingAmount);
   }

   private void walkLeftAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.5F;
      hips.addPosY(blend * (float)(Math.cos(limbSwing * speed) * 1.5 * globalHeight) * limbSwingAmount);
      hips.addRotX(blend * -0.1F * limbSwingAmount * globalHeight);
      hips.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.1F * globalHeight) * limbSwingAmount);
      hips.addRotZ(blend * 0.08F * limbSwingAmount * globalHeight);
      chest.addRotY(blend * (float)(-Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.2F * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * -((float)(Math.cos(limbSwing * speed + 1.4) * 0.025 * globalHeight)) * limbSwingAmount);
      stomach.addRotZ(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 1.4) * 0.02 * globalHeight)) * limbSwingAmount);
      stomach.addRotZ(blend * -((float)(Math.cos(limbSwing * speed - 0.5) * 0.02 * globalHeight)) * limbSwingAmount);
      neck.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.1F * globalHeight) * limbSwingAmount);
      neck.addRotX(blend * -((float)(Math.cos(limbSwing * speed) * 0.175 * globalHeight)) * limbSwingAmount);
      head.addRotX(blend * (float)(Math.cos(limbSwing * speed + 0.175) * 0.175 * globalHeight + 0.1 * globalHeight) * limbSwingAmount);
      head.addRotZ(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.4) * 0.02 * globalHeight) * limbSwingAmount);
      head.addRotZ(blend * (float)(Math.cos(limbSwing * speed - 0.5) * 0.02 * globalHeight) * limbSwingAmount);
      head.addRotZ(blend * -0.03F * limbSwingAmount * globalHeight);
      leftThigh.addRotX(blend * -0.05F * limbSwingAmount * globalHeight);
      leftThigh.addRotZ(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.55F * globalDegree + 0.05 * globalDegree)) * limbSwingAmount);
      leftThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.1F * globalDegree - 0.15) * limbSwingAmount);
      leftShin.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * -0.7F * globalDegree) * limbSwingAmount);
      leftAnkle.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * 1.1F * globalDegree + 0.1F * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -1.3F * globalDegree - 0.6F * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      leftFoot.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -0.4F * globalDegree) * limbSwingAmount);
      leftFoot.addRotY(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 3.1) * 1.6F * globalDegree + 1.4F * globalDegree) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F + 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      rightThigh.addRotX(blend * 0.05F * limbSwingAmount * globalHeight);
      rightThigh.addRotZ(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.55F * globalDegree - 0.05 * globalDegree) * limbSwingAmount);
      rightThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.1F * globalDegree + 0.15) * limbSwingAmount);
      rightShin.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * -0.7F * globalDegree)) * limbSwingAmount);
      rightAnkle.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * 1.1F * globalDegree - 0.1F * globalDegree)) * limbSwingAmount);
      rightFoot.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -1.3F * globalDegree + 0.6F * globalDegree)) * limbSwingAmount);
      rightFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      rightFoot.addRotY(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -0.4F * globalDegree)) * limbSwingAmount);
      rightFoot.addRotY(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      rightToesBack.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 3.1) * 1.6F * globalDegree - 1.4F * globalDegree)) * limbSwingAmount);
      rightToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F + 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      leftShin.addRotX(blend * -((float)(Math.pow(Math.cos(limbSwing * 0.25 * speed - 0.6), 12.0) * 0.6F * globalHeight)) * limbSwingAmount);
      leftAnkle.addRotX(blend * (float)(Math.pow(Math.cos(limbSwing * 0.25 * speed - 0.6), 12.0) * 0.6F * globalHeight) * limbSwingAmount);
      leftArm.addRotY(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight)) * limbSwingAmount);
      leftArm.addRotZ(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight) * limbSwingAmount);
      leftForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.03 * globalHeight) * limbSwingAmount);
      rightArm.addRotY(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight)) * limbSwingAmount);
      rightForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.03 * globalHeight) * limbSwingAmount);
   }

   private void walkRightAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.5F;
      hips.addPosY(blend * (float)(Math.cos(limbSwing * speed) * 1.5 * globalHeight) * limbSwingAmount);
      hips.addRotX(blend * -0.1F * limbSwingAmount * globalHeight);
      hips.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.1F * globalHeight) * limbSwingAmount);
      hips.addRotZ(blend * -0.08F * limbSwingAmount * globalHeight);
      chest.addRotY(blend * (float)(-Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.2F * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * -((float)(Math.cos(limbSwing * speed + 1.4) * 0.025 * globalHeight)) * limbSwingAmount);
      stomach.addRotZ(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.4) * 0.02 * globalHeight) * limbSwingAmount);
      stomach.addRotZ(blend * (float)(Math.cos(limbSwing * speed - 0.5) * 0.02 * globalHeight) * limbSwingAmount);
      neck.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.0) * -0.1F * globalHeight) * limbSwingAmount);
      neck.addRotX(blend * -((float)(Math.cos(limbSwing * speed) * 0.175 * globalHeight)) * limbSwingAmount);
      head.addRotX(blend * (float)(Math.cos(limbSwing * speed + 0.175) * 0.175 * globalHeight + 0.1 * globalHeight) * limbSwingAmount);
      head.addRotZ(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 1.4) * 0.02 * globalHeight)) * limbSwingAmount);
      head.addRotZ(blend * -((float)(Math.cos(limbSwing * speed - 0.5) * 0.02 * globalHeight)) * limbSwingAmount);
      head.addRotZ(blend * 0.03F * limbSwingAmount * globalHeight);
      leftThigh.addRotX(blend * 0.05F * limbSwingAmount * globalHeight);
      leftThigh.addRotZ(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.55F * globalDegree + 0.05 * globalDegree) * limbSwingAmount);
      leftThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.1F * globalDegree - 0.15) * limbSwingAmount);
      leftShin.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * -0.7F * globalDegree) * limbSwingAmount);
      leftAnkle.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * 1.1F * globalDegree + 0.1F * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -1.3F * globalDegree - 0.6F * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      leftFoot.addRotY(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -0.4F * globalDegree)) * limbSwingAmount);
      leftFoot.addRotY(blend * -((float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree)) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 3.1) * 1.6F * globalDegree + 1.4F * globalDegree) * limbSwingAmount);
      leftToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F + 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      rightThigh.addRotX(blend * -0.05F * limbSwingAmount * globalHeight);
      rightThigh.addRotZ(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.55F * globalDegree - 0.05 * globalDegree)) * limbSwingAmount);
      rightThigh.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 1.5) * 0.1F * globalDegree + 0.15) * limbSwingAmount);
      rightShin.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * -0.7F * globalDegree)) * limbSwingAmount);
      rightAnkle.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.4) * 1.1F * globalDegree - 0.1F * globalDegree)) * limbSwingAmount);
      rightFoot.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -1.3F * globalDegree + 0.6F * globalDegree)) * limbSwingAmount);
      rightFoot.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree) * limbSwingAmount);
      rightFoot.addRotY(blend * (float)(Math.cos(limbSwing * speed * 0.5 + 2.5) * -0.4F * globalDegree) * limbSwingAmount);
      rightFoot.addRotY(blend * -((float)(Math.cos(limbSwing * speed * 1.0F - 0.2) * -0.2F * globalDegree)) * limbSwingAmount);
      rightToesBack.addRotX(blend * -((float)(Math.cos(limbSwing * speed * 0.5 + 3.1) * 1.6F * globalDegree - 1.4F * globalDegree)) * limbSwingAmount);
      rightToesBack.addRotX(blend * (float)(Math.cos(limbSwing * speed * 1.0F + 0.1) * 0.3F * globalDegree) * limbSwingAmount);
      rightShin.addRotX(blend * -((float)(Math.pow(Math.cos(limbSwing * 0.25 * speed - 0.6 + (Math.PI / 2)), 12.0) * 0.6F * globalHeight)) * limbSwingAmount);
      rightAnkle.addRotX(blend * (float)(Math.pow(Math.cos(limbSwing * 0.25 * speed - 0.6 + (Math.PI / 2)), 12.0) * 0.6F * globalHeight) * limbSwingAmount);
      leftArm.addRotY(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight)) * limbSwingAmount);
      leftArm.addRotZ(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight) * limbSwingAmount);
      leftForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.03 * globalHeight) * limbSwingAmount);
      rightArm.addRotY(blend * (float)(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (float)(-(Math.cos(limbSwing * speed + 0.52) * 0.0707 * globalHeight)) * limbSwingAmount);
      rightForeArm.addRotX(blend * (float)(Math.cos(limbSwing * speed - 1.0) * 0.03 * globalHeight) * limbSwingAmount);
   }
}
