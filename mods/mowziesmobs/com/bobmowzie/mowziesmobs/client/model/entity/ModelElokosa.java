package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosaHowler;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.data.EntityModelData;

public class ModelElokosa extends MowzieGeoModel<EntityElokosa> {
   public MowzieGeoBone[] tailOriginal;
   public MowzieGeoBone[] tailDynamic;

   public ResourceLocation getModelResource(EntityElokosa elokosa) {
      return MMCommon.resource("geo/elokosa.geo.json");
   }

   public ResourceLocation getTextureResource(EntityElokosa elokosa) {
      boolean isElite = elokosa instanceof EntityElokosaHowler;
      return MMCommon.resource("textures/entity/" + (isElite ? "elokosa_elite.png" : "elokosa.png"));
   }

   public ResourceLocation getAnimationResource(EntityElokosa elokosa) {
      return MMCommon.resource("animations/elokosa.animation.json");
   }

   public void setCustomAnimations(EntityElokosa entity, long instanceId, AnimationState<EntityElokosa> animationState) {
      super.setCustomAnimations(entity, instanceId, animationState);
      boolean isElite = entity instanceof EntityElokosaHowler;
      MowzieGeoBone rootNight = this.getMowzieBone("root");
      MowzieGeoBone rootDay = this.getMowzieBone("root_day");
      float whichForm = -this.getControllerValue("whichFormController");
      if (whichForm <= 0.1F) {
         if (entity.getNightForm()) {
            rootDay.setHidden(true);
            rootNight.setHidden(false);
            entity.tailChain.setHidden(false);
         } else {
            rootDay.setHidden(false);
            rootNight.setHidden(true);
            entity.tailChain.setHidden(true);
         }
      } else if (whichForm >= 1.0F) {
         rootDay.setHidden(true);
         rootNight.setHidden(false);
         entity.tailChain.setHidden(false);
      } else {
         rootDay.setHidden(false);
         rootNight.setHidden(false);
         entity.tailChain.setHidden(false);
      }

      MowzieGeoBone rootShared = this.getMowzieBone("root_shared");
      MowzieGeoBone scythe = this.getMowzieBone("scythe");
      MowzieGeoBone beard = this.getMowzieBone("beard");
      MowzieGeoBone tailTuft = this.getMowzieBone("tailTuft");
      if (isElite) {
         MowzieGeoBone tuft1 = this.getMowzieBone("tuft1");
         MowzieGeoBone tuft2 = this.getMowzieBone("tuft2");
         MowzieGeoBone tuft3 = this.getMowzieBone("tuft3");
         MowzieGeoBone tuft4 = this.getMowzieBone("tuft4");
         MowzieGeoBone tail1 = this.getMowzieBone("tail1");
         rootShared.multiplyScale(1.185F, 1.185F, 1.185F);
         tail1.multiplyScale(0.91561186F, 0.91561186F, 0.91561186F);
         tuft1.setScaleY(tuft1.getScaleY() * 1.18F);
         tuft2.setScaleY(tuft2.getScaleY() * 1.18F);
         tuft3.setScaleY(tuft3.getScaleY() * 1.18F);
         tuft4.setScaleY(tuft4.getScaleY() * 1.18F);
         scythe.setHidden(false);
         beard.setHidden(false);
         tailTuft.setHidden(true);
      } else {
         rootShared.multiplyScale(1.085F, 1.085F, 1.085F);
         scythe.setHidden(true);
         beard.setHidden(true);
         tailTuft.setHidden(false);
      }

      EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
      if (rootNight != null
         && !entity.onGround()
         && entity.getClingDirection() == Direction.DOWN
         && entity.getActiveAbilityType() == EntityElokosa.LEAP_ABILITY) {
         rootNight.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0) * 0.2F);
      }

      float frame = entity.frame + animationState.getPartialTick();
      float ticks = entity.tickCount;
      MowzieGeoBone headLook = this.getMowzieBone("headLook");
      MowzieGeoBone headLookDay = this.getMowzieBone("head_day");
      EntityModelData data = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
      float headYaw = Mth.wrapDegrees(data.netHeadYaw());
      float headPitch = Mth.wrapDegrees(data.headPitch());
      if (headLook != null) {
         headLook.addRotX(headPitch * Math.PI / 180.0);
         headLook.addRotY(headYaw * Math.PI / 180.0);
         headLookDay.addRotX(headPitch * Math.PI / 180.0);
         headLookDay.addRotY(headYaw * Math.PI / 180.0);
      }

      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      MowzieGeoBone rightArmDay = this.getMowzieBone("ArmR_Joint_day");
      MowzieGeoBone rightHandDay = this.getMowzieBone("HandR_day");
      float wallClingController = this.getControllerValue("wallClingController");
      float upperClingBlockMissing = entity.missingWallClingUpperBlock ? 1.0F : 0.0F;
      rightArm.addRotX(-0.75 * wallClingController * upperClingBlockMissing);
      rightArm.addRotY(0.3 * wallClingController * upperClingBlockMissing);
      rightHand.addRotZ(0.4 * wallClingController * upperClingBlockMissing);
      rightHand.addRotX(-0.3 * wallClingController * upperClingBlockMissing);
      rightArmDay.addRotX(-1.0F * wallClingController * upperClingBlockMissing);
      rightArmDay.addRotY(-0.2 * wallClingController * upperClingBlockMissing);
      rightHandDay.addRotX(-0.8 * wallClingController * upperClingBlockMissing);
      rightHandDay.addRotY(-0.8 * wallClingController * upperClingBlockMissing);
      float animSpeed = 0.65F;
      float limbSwing = animationState.getLimbSwing();
      float limbSwingAmount = animationState.getLimbSwingAmount();
      double forward = Mth.lerp(animationState.getPartialTick(), entity.prevMoveDirForward, entity.moveDirForward);
      double backward = Mth.lerp(animationState.getPartialTick(), entity.prevMoveDirBackward, entity.moveDirBackward);
      double left = Mth.lerp(animationState.getPartialTick(), entity.prevMoveDirLeft, entity.moveDirLeft);
      double right = Mth.lerp(animationState.getPartialTick(), entity.prevMoveDirRight, entity.moveDirRight);
      limbSwingAmount *= 2.0F;
      limbSwingAmount = Math.min(0.7F, limbSwingAmount);
      double locomotionAnimController = this.getControllerValue("locomotionAnimController");
      double runAnim = this.getControllerValue("walkRunSwitchController");
      double walkAnim = 1.0 - runAnim;
      this.walkForwardAnim(forward * locomotionAnimController * walkAnim, limbSwing, limbSwingAmount, animSpeed);
      this.walkBackwardAnim(backward * locomotionAnimController * walkAnim, limbSwing, limbSwingAmount, animSpeed);
      this.walkLeftAnim(left * locomotionAnimController * walkAnim, limbSwing, limbSwingAmount, animSpeed);
      this.walkRightAnim(right * locomotionAnimController * walkAnim, limbSwing, limbSwingAmount, animSpeed);
      this.runAnim(locomotionAnimController * runAnim, limbSwing, limbSwingAmount, animSpeed);
      this.walkAnimDay(locomotionAnimController * walkAnim, limbSwing, limbSwingAmount, animSpeed);
      this.runAnimDay(locomotionAnimController * runAnim, limbSwing, limbSwingAmount, animSpeed);
      if (!rootNight.isHidden()) {
         if (this.tailOriginal == null || this.tailDynamic == null) {
            this.tailOriginal = new MowzieGeoBone[]{
               this.getMowzieBone("tail1"),
               this.getMowzieBone("tail2"),
               this.getMowzieBone("tail3"),
               this.getMowzieBone("tail4"),
               this.getMowzieBone("tail5"),
               this.getMowzieBone("tail6")
            };
            this.tailDynamic = new MowzieGeoBone[this.tailOriginal.length];
         }

         if (entity.tailChain != null) {
            entity.tailChain.setChainArrays(this.tailOriginal, this.tailDynamic);
            double tailAlpha = this.getControllerValue("tailPhysicsBlendController");
            entity.tailChain.setAlpha((float)(1.0 - tailAlpha));
         }
      }
   }

   private void runAnim(double blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone headJoint = this.getMowzieBone("headJoint");
      MowzieGeoBone coM = this.getMowzieBone("CoM");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone upperBody = this.getMowzieBone("upperBody");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftCalf = this.getMowzieBone("leftCalf");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToes = this.getMowzieBone("leftToes");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightCalf = this.getMowzieBone("rightCalf");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToes = this.getMowzieBone("rightToes");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.0F;
      float globalDegree = 1.0F;
      speed *= 0.95F;
      coM.addPosY(blend * (Math.cos(limbSwing * speed) * 4.0 * globalHeight + 4.0F * globalHeight) * limbSwingAmount);
      coM.addPosZ(blend * (Math.cos(limbSwing * speed + 1.9) * 2.0 * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * (Math.cos(limbSwing * speed + 1.5) * -0.3 * globalHeight - 0.3F) * limbSwingAmount);
      upperBody.addRotX(blend * (Math.cos(limbSwing * speed - 0.4) * -0.4F * globalHeight + 0.15F) * limbSwingAmount);
      headJoint.addRotX(blend * (Math.cos(limbSwing * speed + 0.185) * 0.4 * globalHeight + 0.15 * globalHeight) * limbSwingAmount);
      headJoint.addPosY(blend * (Math.cos(limbSwing * speed + 1.0F) * 2.0 * globalHeight) * limbSwingAmount);
      headJoint.addPosZ(blend * (Math.cos(limbSwing * speed + 1.9) * -1.0 * globalHeight) * limbSwingAmount);
      headJoint.addRotZ(blend * (Math.cos(limbSwing * speed + 0.185) * 0.16 * globalHeight) * limbSwingAmount);
      headJoint.addPosX(blend * (Math.cos(limbSwing * speed - 0.9) * -0.65 * globalHeight) * limbSwingAmount);
      double legTimingOffset = 0.9;
      leftThigh.addRotX(blend * (-Math.cos(limbSwing * speed + 0.0F + legTimingOffset) * 1.1F * globalDegree - 0.1 * globalDegree) * limbSwingAmount);
      leftThigh.addPosY(blend * (-Math.cos(limbSwing * speed + 0.5 + legTimingOffset) * 2.0 * globalDegree) * limbSwingAmount);
      leftThigh.addRotY(blend * (-Math.cos(limbSwing * speed + 0.5 + legTimingOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      leftCalf.addRotX(blend * (Math.cos(limbSwing * speed + 1.2 + legTimingOffset) * 0.7F * globalDegree + 0.2 * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (Math.cos(limbSwing * speed + 1.2 + legTimingOffset) * -0.7F * globalDegree - 0.8F * globalDegree) * limbSwingAmount);
      leftToes.addRotX(blend * (-Math.cos(limbSwing * speed + 1.7 + legTimingOffset) * -1.7F * globalDegree - 1.0F * globalDegree) * limbSwingAmount);
      double rightLegOffset = 0.7;
      rightThigh.addRotX(
         blend * (-Math.cos(limbSwing * speed + 0.0F + legTimingOffset + rightLegOffset) * 1.1F * globalDegree - 0.1 * globalDegree) * limbSwingAmount
      );
      rightThigh.addPosY(blend * (-Math.cos(limbSwing * speed + 0.5 + legTimingOffset + rightLegOffset) * 2.0 * globalDegree) * limbSwingAmount);
      rightThigh.addRotY(blend * (Math.cos(limbSwing * speed + 0.5 + legTimingOffset + rightLegOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      rightCalf.addRotX(
         blend * (Math.cos(limbSwing * speed + 1.2 + legTimingOffset + rightLegOffset) * 0.7F * globalDegree + 0.2 * globalDegree) * limbSwingAmount
      );
      rightFoot.addRotX(
         blend * (Math.cos(limbSwing * speed + 1.2 + legTimingOffset + rightLegOffset) * -0.7F * globalDegree - 0.8F * globalDegree) * limbSwingAmount
      );
      rightToes.addRotX(
         blend * (-Math.cos(limbSwing * speed + 1.7 + legTimingOffset + rightLegOffset) * -1.7F * globalDegree - 1.0F * globalDegree) * limbSwingAmount
      );
      leftArm.addRotX(blend * -(Math.cos(limbSwing * speed + -0.8) * 0.5 * globalDegree + 0.5) * limbSwingAmount);
      leftArm.addRotZ(blend * (Math.cos(limbSwing * speed + 0.52) * 0.4 * globalDegree) * limbSwingAmount);
      leftForeArm.addRotX(blend * (Math.cos(limbSwing * speed - 1.65) * -0.5 * globalDegree) * limbSwingAmount);
      leftForeArm.addRotZ(blend * (Math.cos(limbSwing * speed - 1.65) * 0.9 * globalDegree) * limbSwingAmount);
      leftHand.addRotX(blend * (Math.cos(limbSwing * speed + 3.0F) * -0.8 * globalDegree - 0.1) * limbSwingAmount);
      leftHand.addRotZ(blend * (Math.cos(limbSwing * speed - 1.65) * -0.3 * globalDegree) * limbSwingAmount);
      leftHand.addRotY(blend * (Math.cos(limbSwing * speed - 1.65) * -0.3 * globalDegree + 0.3) * limbSwingAmount);
      double rightArmOffset = 1.3;
      rightArm.addRotX(blend * -(Math.cos(limbSwing * speed + -0.8 + rightArmOffset) * 0.5 * globalDegree - 0.5) * limbSwingAmount);
      rightArm.addRotZ(blend * -(Math.cos(limbSwing * speed + 0.52 + rightArmOffset) * 0.4 * globalDegree) * limbSwingAmount);
      rightForeArm.addRotX(blend * (Math.cos(limbSwing * speed - 1.65 + rightArmOffset) * -0.5 * globalDegree - 0.87) * limbSwingAmount);
      rightForeArm.addRotZ(blend * (Math.cos(limbSwing * speed - 0.3 + rightArmOffset) * 0.7 * globalDegree - 0.2) * limbSwingAmount);
      rightHand.addRotX(blend * (Math.cos(limbSwing * speed + 3.0F + rightArmOffset) * -0.8 * globalDegree - 0.1) * limbSwingAmount);
      rightHand.addRotY(blend * -(Math.cos(limbSwing * speed - 0.3 + rightArmOffset) * -0.3 * globalDegree - 0.3) * limbSwingAmount);
      MowzieGeoBone tail1 = this.getMowzieBone("tail1");
      MowzieGeoBone tail2 = this.getMowzieBone("tail2");
      MowzieGeoBone tail3 = this.getMowzieBone("tail3");
      MowzieGeoBone tail4 = this.getMowzieBone("tail4");
      MowzieGeoBone tail5 = this.getMowzieBone("tail5");
      tail1.addRotX(blend * (Math.cos(limbSwing * speed + 1.76) * 0.2F * globalHeight) * limbSwingAmount);
      tail2.addRotX(blend * (Math.cos(limbSwing * speed + 1.76 - 0.5) * 0.2F * globalHeight) * limbSwingAmount);
      tail3.addRotX(blend * (Math.cos(limbSwing * speed + 1.76 - 1.0) * 0.2F * globalHeight) * limbSwingAmount);
      tail4.addRotX(blend * (Math.cos(limbSwing * speed + 1.76 - 1.5) * 0.2F * globalHeight) * limbSwingAmount);
      tail5.addRotX(blend * (Math.cos(limbSwing * speed + 1.76 - 2.0) * 0.2F * globalHeight) * limbSwingAmount);
      MowzieGeoBone tuft1 = this.getMowzieBone("tuft1");
      MowzieGeoBone tuft2 = this.getMowzieBone("tuft2");
      MowzieGeoBone tuft3 = this.getMowzieBone("tuft3");
      MowzieGeoBone tuft4 = this.getMowzieBone("tuft4");
      tuft1.addRotX(blend * (Math.cos(limbSwing * speed + 1.76) * 0.1F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 1.76 - 0.25) * 0.02F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 1.76 - 0.5) * 0.1F * globalHeight) * limbSwingAmount);
      tuft4.addRotX(blend * (Math.cos(limbSwing * speed + 1.76 - 0.5) * 0.1F * globalHeight) * limbSwingAmount);
   }

   private void walkForwardAnim(double blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone headJoint = this.getMowzieBone("headJoint");
      MowzieGeoBone coM = this.getMowzieBone("CoM");
      MowzieGeoBone legs = this.getMowzieBone("legs");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone upperBody = this.getMowzieBone("upperBody");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftCalf = this.getMowzieBone("leftCalf");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToes = this.getMowzieBone("leftToes");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightCalf = this.getMowzieBone("rightCalf");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToes = this.getMowzieBone("rightToes");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      speed *= 2.1F;
      float globalHeight = 1.1F;
      float globalDegree = 0.9F;
      coM.addPosY(blend * (Math.cos(limbSwing * speed) * 0.3F * globalHeight + 0.1 * globalHeight) * limbSwingAmount);
      coM.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.2F * globalHeight) * limbSwingAmount);
      coM.addRotX(blend * (this.poweredWave(limbSwing, speed, -1.5, 3.0) * -0.12F * globalHeight - 0.075F * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * -0.1F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * 0.1F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      headJoint.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.2F * globalHeight) * limbSwingAmount);
      legs.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      leftArm.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      rightArm.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      upperBody.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3F * globalHeight) * limbSwingAmount);
      upperBody.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3F * globalHeight) * limbSwingAmount);
      legs.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.15F * globalHeight) * limbSwingAmount);
      legs.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.15F * globalHeight) * limbSwingAmount);
      upperBody.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      upperBody.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      leftArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.2F * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.2F * globalHeight) * limbSwingAmount);
      leftArm.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, 2.0, 3.0) * -3.0 * globalHeight * limbSwingAmount);
      leftArm.addPosY(blend * 1.0 * globalHeight * limbSwingAmount);
      rightArm.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, 5.141593F, 3.0) * -3.0 * globalHeight * limbSwingAmount);
      rightArm.addPosY(blend * 1.0 * globalHeight * limbSwingAmount);
      leftArm.addPosY(blend * (Math.cos(limbSwing * speed * 0.5F - 0.0F) * 1.0 * globalHeight + 0.9F * globalHeight) * limbSwingAmount);
      rightArm.addPosY(blend * (Math.cos(limbSwing * speed * 0.5F - 0.0F) * -1.0 * globalHeight + 0.9F * globalHeight) * limbSwingAmount);
      leftArm.addRotX(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.5 * globalDegree + 0.0F * globalDegree) * limbSwingAmount);
      leftArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.52) * 0.4 * globalDegree) * limbSwingAmount);
      leftForeArm.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 - 1.65) * -0.5 * globalDegree) * limbSwingAmount);
      leftForeArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 1.65) * 0.9 * globalDegree) * limbSwingAmount);
      leftForeArm.addRotY(blend * (Math.cos(limbSwing * speed * 0.5) * -0.7 * globalDegree) * limbSwingAmount);
      leftHand.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 + 3.0) * -0.8 * globalDegree - 0.1) * limbSwingAmount);
      leftHand.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 1.65) * -0.3 * globalDegree + -0.4 * globalDegree) * limbSwingAmount);
      leftHand.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 - 1.65) * -0.3 * globalDegree + 0.3) * limbSwingAmount);
      leftHand.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, 1.0, 2.5) * -0.9F * globalDegree + 0.3 * globalDegree) * limbSwingAmount);
      double rightArmOffset = Math.PI;
      rightArm.addRotX(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8 + rightArmOffset) * 0.5 * globalDegree - 1.0F * globalDegree) * limbSwingAmount);
      rightArm.addRotZ(blend * -(Math.cos(limbSwing * speed * 0.5 + 0.52 + rightArmOffset) * 0.4 * globalDegree) * limbSwingAmount);
      rightForeArm.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 - 1.65 + rightArmOffset) * -0.25 * globalDegree - 0.87 * globalDegree) * limbSwingAmount);
      rightForeArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 0.3 + rightArmOffset) * 0.7 * globalDegree - 0.2 * globalDegree) * limbSwingAmount);
      rightForeArm.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + rightArmOffset) * 0.4 * globalDegree) * limbSwingAmount);
      rightHand.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 + 3.0 + rightArmOffset) * -0.8 * globalDegree + 0.1 * globalDegree) * limbSwingAmount);
      rightHand.addRotZ(
         blend * (this.poweredWave(limbSwing, speed * 0.5F, 0.0 + rightArmOffset, 2.0) * -0.8 * globalDegree + 0.3 * globalDegree) * limbSwingAmount
      );
      rightHand.addRotY(blend * -(Math.cos(limbSwing * speed * 0.5 - 0.52 + rightArmOffset) * -0.6 * globalDegree + 0.3 * globalDegree) * limbSwingAmount);
      rightHand.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, 4.141593F, 2.5) * -0.9F * globalDegree + 0.3 * globalDegree) * limbSwingAmount);
      double legTimingOffset = 1.5;
      leftThigh.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.15F * globalHeight) * limbSwingAmount);
      leftThigh.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.35F * globalHeight) * limbSwingAmount);
      leftThigh.addRotX(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0 + legTimingOffset) * 0.8F * globalDegree + 0.1 * globalDegree) * limbSwingAmount);
      leftThigh.addPosY(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset) * 0.8F * globalHeight + 1.0F * globalHeight) * limbSwingAmount);
      leftThigh.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.5, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      leftThigh.addPosZ(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.5, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      leftThigh.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      leftCalf.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 + 2.2 + legTimingOffset) * 0.7F * globalDegree + 0.2 * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 + 2.2 + legTimingOffset) * -0.7F * globalDegree - 0.8F * globalDegree) * limbSwingAmount);
      leftToes.addRotX(blend * (-Math.cos(limbSwing * speed * 0.5 + 2.5 + legTimingOffset) * -1.7F * globalDegree - 1.0F * globalDegree) * limbSwingAmount);
      leftToes.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, -2.0, 4.0) * -1.0 * globalDegree + 0.3 * globalDegree) * limbSwingAmount);
      double rightLegOffset = Math.PI;
      rightThigh.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.15F * globalHeight) * limbSwingAmount);
      rightThigh.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.35F * globalHeight) * limbSwingAmount);
      rightThigh.addRotX(
         blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0 + legTimingOffset + rightLegOffset) * 0.8F * globalDegree + 0.4 * globalDegree) * limbSwingAmount
      );
      rightThigh.addPosY(
         blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset + rightLegOffset) * 0.8F * globalHeight + 1.0F * globalHeight) * limbSwingAmount
      );
      rightThigh.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, 1.6415927F, 3.0) * -0.5 * globalHeight * limbSwingAmount);
      rightThigh.addPosZ(blend * this.poweredWave(limbSwing, speed * 0.5F, 1.6415927F, 3.0) * -0.5 * globalHeight * limbSwingAmount);
      rightThigh.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 0.5 + legTimingOffset + rightLegOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      rightCalf.addRotX(
         blend * (Math.cos(limbSwing * speed * 0.5 + 2.2 + legTimingOffset + rightLegOffset) * 0.7F * globalDegree + 0.2 * globalDegree) * limbSwingAmount
      );
      rightFoot.addRotX(
         blend * (Math.cos(limbSwing * speed * 0.5 + 2.2 + legTimingOffset + rightLegOffset) * -0.7F * globalDegree - 0.8F * globalDegree) * limbSwingAmount
      );
      rightToes.addRotX(
         blend * (-Math.cos(limbSwing * speed * 0.5 + 2.5 + legTimingOffset + rightLegOffset) * -1.7F * globalDegree - 1.0F * globalDegree) * limbSwingAmount
      );
      rightToes.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, 1.1415927F, 4.0) * -1.0 * globalDegree + 0.3 * globalDegree) * limbSwingAmount);
      MowzieGeoBone tail1 = this.getMowzieBone("tail1");
      MowzieGeoBone tail2 = this.getMowzieBone("tail2");
      MowzieGeoBone tail3 = this.getMowzieBone("tail3");
      MowzieGeoBone tail4 = this.getMowzieBone("tail4");
      MowzieGeoBone tail5 = this.getMowzieBone("tail5");
      tail1.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46) * -0.13F * globalHeight) * limbSwingAmount);
      tail1.addRotX(blend * (Math.cos(limbSwing * speed + 3.46) * -0.13F * globalHeight) * limbSwingAmount);
      tail2.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46 - 0.5) * 0.13F * globalHeight) * limbSwingAmount);
      tail2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail3.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 1.0) * -0.13F * globalHeight) * limbSwingAmount);
      tail4.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 1.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail5.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 2.0) * -0.13F * globalHeight) * limbSwingAmount);
      MowzieGeoBone tuft1 = this.getMowzieBone("tuft1");
      MowzieGeoBone tuft2 = this.getMowzieBone("tuft2");
      MowzieGeoBone tuft3 = this.getMowzieBone("tuft3");
      MowzieGeoBone tuft4 = this.getMowzieBone("tuft4");
      tuft1.addRotX(blend * (Math.cos(limbSwing * speed + 3.46) * -0.065F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.25) * -0.02F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.065F * globalHeight) * limbSwingAmount);
      tuft4.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.065F * globalHeight) * limbSwingAmount);
   }

   private void walkBackwardAnim(double blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone headJoint = this.getMowzieBone("headJoint");
      MowzieGeoBone coM = this.getMowzieBone("CoM");
      MowzieGeoBone legs = this.getMowzieBone("legs");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone upperBody = this.getMowzieBone("upperBody");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftCalf = this.getMowzieBone("leftCalf");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToes = this.getMowzieBone("leftToes");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightCalf = this.getMowzieBone("rightCalf");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToes = this.getMowzieBone("rightToes");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      speed *= 2.1F;
      float globalHeight = 1.1F;
      float globalDegree = 0.9F;
      double sideFlipTimingOffset = 0.0;
      coM.addPosY(blend * (Math.cos(-limbSwing * speed + sideFlipTimingOffset) * 0.3F * globalHeight + 0.1 * globalHeight) * limbSwingAmount);
      coM.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 + -0.8 + sideFlipTimingOffset) * -0.2F * globalHeight) * limbSwingAmount);
      coM.addRotX(
         blend * (this.poweredWave(-limbSwing, speed, -1.5 + sideFlipTimingOffset, 3.0) * -0.15F * globalHeight - 0.075F * globalHeight) * limbSwingAmount
      );
      stomach.addRotX(blend * -0.1F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * 0.1F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * this.poweredWave(-limbSwing, speed, -1.5 + sideFlipTimingOffset, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      headJoint.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 + -0.8 + sideFlipTimingOffset) * -0.2F * globalHeight) * limbSwingAmount);
      legs.addRotX(blend * this.poweredWave(-limbSwing, speed, -sideFlipTimingOffset, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      leftArm.addRotX(blend * this.poweredWave(-limbSwing, speed, -1.5 + sideFlipTimingOffset, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      rightArm.addRotX(blend * this.poweredWave(-limbSwing, speed, -1.5 + sideFlipTimingOffset, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      upperBody.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 + -0.8 + sideFlipTimingOffset) * 0.3F * globalHeight) * limbSwingAmount);
      upperBody.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 + -0.8 + sideFlipTimingOffset) * 0.3F * globalHeight) * limbSwingAmount);
      legs.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 + -0.8 + sideFlipTimingOffset) * -0.15F * globalHeight) * limbSwingAmount);
      legs.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 + -0.8 + sideFlipTimingOffset) * -0.15F * globalHeight) * limbSwingAmount);
      upperBody.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 + sideFlipTimingOffset) * 0.1F * globalHeight) * limbSwingAmount);
      upperBody.addRotZ(blend * (-Math.cos(-limbSwing * speed * 0.5 + sideFlipTimingOffset) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotY(blend * (-Math.cos(-limbSwing * speed * 0.5 + sideFlipTimingOffset) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 + sideFlipTimingOffset) * 0.1F * globalHeight) * limbSwingAmount);
      leftArm.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 + sideFlipTimingOffset) * 0.2F * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 + sideFlipTimingOffset) * 0.2F * globalHeight) * limbSwingAmount);
      leftArm.addPosY(blend * this.poweredWave(-limbSwing, speed * 0.5F, 2.0 + sideFlipTimingOffset, 3.0) * -3.0 * globalHeight * limbSwingAmount);
      leftArm.addPosY(blend * 1.0 * globalHeight * limbSwingAmount);
      rightArm.addPosY(blend * this.poweredWave(-limbSwing, speed * 0.5F, 5.141593F + sideFlipTimingOffset, 3.0) * -3.0 * globalHeight * limbSwingAmount);
      rightArm.addPosY(blend * 1.0 * globalHeight * limbSwingAmount);
      leftArm.addPosY(blend * (Math.cos(-limbSwing * speed * 0.5F + sideFlipTimingOffset) * 1.0 * globalHeight + 0.9F * globalHeight) * limbSwingAmount);
      rightArm.addPosY(blend * (Math.cos(-limbSwing * speed * 0.5F + sideFlipTimingOffset) * -1.0 * globalHeight + 0.9F * globalHeight) * limbSwingAmount);
      leftArm.addRotX(blend * -(Math.cos(-limbSwing * speed * 0.5 + -0.8 + sideFlipTimingOffset) * 0.5 * globalDegree + 0.0F * globalDegree) * limbSwingAmount);
      leftArm.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 + 0.52 + sideFlipTimingOffset) * 0.4 * globalDegree) * limbSwingAmount);
      leftForeArm.addRotX(blend * (Math.cos(-limbSwing * speed * 0.5 - 1.65 + sideFlipTimingOffset) * -0.5 * globalDegree) * limbSwingAmount);
      leftForeArm.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 - 1.65 + sideFlipTimingOffset) * 0.9 * globalDegree) * limbSwingAmount);
      leftForeArm.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 + sideFlipTimingOffset) * -0.7 * globalDegree) * limbSwingAmount);
      leftHand.addRotX(blend * (Math.cos(-limbSwing * speed * 0.5 + 3.0 + sideFlipTimingOffset) * -0.8 * globalDegree - 0.1) * limbSwingAmount);
      leftHand.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 - 1.65 + sideFlipTimingOffset) * -0.3 * globalDegree + -0.4 * globalDegree) * limbSwingAmount);
      leftHand.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 - 1.65 + sideFlipTimingOffset) * -0.3 * globalDegree + 0.3) * limbSwingAmount);
      leftHand.addRotX(
         blend * (this.poweredWave(-limbSwing, speed * 0.5F, 1.0 + sideFlipTimingOffset, 2.5) * -0.9F * globalDegree + 0.3 * globalDegree) * limbSwingAmount
      );
      double rightArmOffset = Math.PI;
      rightArm.addRotX(
         blend
            * -(Math.cos(-limbSwing * speed * 0.5 + -0.8 + rightArmOffset + sideFlipTimingOffset) * 0.5 * globalDegree - 1.0F * globalDegree)
            * limbSwingAmount
      );
      rightArm.addRotZ(blend * -(Math.cos(-limbSwing * speed * 0.5 + 0.52 + rightArmOffset + sideFlipTimingOffset) * 0.4 * globalDegree) * limbSwingAmount);
      rightForeArm.addRotX(
         blend
            * (Math.cos(-limbSwing * speed * 0.5 - 1.65 + rightArmOffset + sideFlipTimingOffset) * -0.25 * globalDegree - 0.87 * globalDegree)
            * limbSwingAmount
      );
      rightForeArm.addRotZ(
         blend * (Math.cos(-limbSwing * speed * 0.5 - 0.3 + rightArmOffset + sideFlipTimingOffset) * 0.7 * globalDegree - 0.2 * globalDegree) * limbSwingAmount
      );
      rightForeArm.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 + rightArmOffset + sideFlipTimingOffset) * 0.4 * globalDegree) * limbSwingAmount);
      rightHand.addRotX(
         blend
            * (Math.cos(-limbSwing * speed * 0.5 + 3.0 + rightArmOffset + sideFlipTimingOffset) * -0.8 * globalDegree + 0.1 * globalDegree)
            * limbSwingAmount
      );
      rightHand.addRotZ(
         blend
            * (this.poweredWave(-limbSwing, speed * 0.5F, 0.0 + rightArmOffset + sideFlipTimingOffset, 2.0) * -0.8 * globalDegree + 0.3 * globalDegree)
            * limbSwingAmount
      );
      rightHand.addRotY(
         blend
            * -(Math.cos(-limbSwing * speed * 0.5 - 0.52 + rightArmOffset + sideFlipTimingOffset) * -0.6 * globalDegree + 0.3 * globalDegree)
            * limbSwingAmount
      );
      rightHand.addRotX(
         blend
            * (this.poweredWave(-limbSwing, speed * 0.5F, 4.141593F + sideFlipTimingOffset, 2.5) * -0.9F * globalDegree + 0.3 * globalDegree)
            * limbSwingAmount
      );
      leftArm.setHidden(false);
      rightArm.setHidden(false);
      double legTimingOffset = 1.5;
      leftThigh.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 + -0.8 + sideFlipTimingOffset) * 0.15F * globalHeight) * limbSwingAmount);
      leftThigh.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 + sideFlipTimingOffset) * 0.35F * globalHeight) * limbSwingAmount);
      leftThigh.addRotX(
         blend
            * (-Math.cos(-limbSwing * speed * 0.5 + 0.0 + legTimingOffset + sideFlipTimingOffset) * 0.8F * globalDegree + 0.1 * globalDegree)
            * limbSwingAmount
      );
      leftThigh.addPosY(
         blend
            * (-Math.cos(-limbSwing * speed * 0.5 + 1.5 + legTimingOffset + sideFlipTimingOffset) * 0.8F * globalHeight + 1.0F * globalHeight)
            * limbSwingAmount
      );
      leftThigh.addPosY(blend * this.poweredWave(-limbSwing, speed * 0.5F, 1.6415927F + sideFlipTimingOffset, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      leftThigh.addPosZ(blend * this.poweredWave(-limbSwing, speed * 0.5F, 1.6415927F + sideFlipTimingOffset, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      leftThigh.addRotY(
         blend * (-Math.cos(-limbSwing * speed * 0.5 + 1.5 + legTimingOffset + sideFlipTimingOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount
      );
      leftCalf.addRotX(
         blend
            * (Math.cos(-limbSwing * speed * 0.5 + 1.0 + legTimingOffset + sideFlipTimingOffset) * 0.7F * globalDegree + 0.2 * globalDegree)
            * limbSwingAmount
      );
      leftFoot.addRotX(
         blend
            * (Math.cos(-limbSwing * speed * 0.5 + 1.0 + legTimingOffset + sideFlipTimingOffset) * -0.7F * globalDegree - 0.8F * globalDegree)
            * limbSwingAmount
      );
      leftToes.addRotX(
         blend
            * (-Math.cos(-limbSwing * speed * 0.5 + 2.0 + legTimingOffset + sideFlipTimingOffset) * -1.2F * globalDegree - 1.0F * globalDegree)
            * limbSwingAmount
      );
      leftToes.addRotX(
         blend * (this.poweredWave(-limbSwing, speed * 0.5F, -5.0 + sideFlipTimingOffset, 4.0) * 2.5 * globalDegree + 0.3 * globalDegree) * limbSwingAmount
      );
      double rightLegOffset = Math.PI;
      rightThigh.addRotY(blend * (Math.cos(-limbSwing * speed * 0.5 + -0.8 + sideFlipTimingOffset) * 0.15F * globalHeight) * limbSwingAmount);
      rightThigh.addRotZ(blend * (Math.cos(-limbSwing * speed * 0.5 + sideFlipTimingOffset) * 0.35F * globalHeight) * limbSwingAmount);
      rightThigh.addRotX(
         blend
            * (-Math.cos(-limbSwing * speed * 0.5 + 0.0 + legTimingOffset + rightLegOffset + sideFlipTimingOffset) * 0.8F * globalDegree + 0.4 * globalDegree)
            * limbSwingAmount
      );
      rightThigh.addPosY(
         blend
            * (-Math.cos(-limbSwing * speed * 0.5 + 1.5 + legTimingOffset + rightLegOffset + sideFlipTimingOffset) * 0.8F * globalHeight + 1.0F * globalHeight)
            * limbSwingAmount
      );
      rightThigh.addPosY(blend * this.poweredWave(-limbSwing, speed * 0.5F, 1.6415927F + sideFlipTimingOffset, 3.0) * -0.5 * globalHeight * limbSwingAmount);
      rightThigh.addPosZ(blend * this.poweredWave(-limbSwing, speed * 0.5F, 1.6415927F + sideFlipTimingOffset, 3.0) * -0.5 * globalHeight * limbSwingAmount);
      rightThigh.addRotY(
         blend
            * (Math.cos(-limbSwing * speed * 0.5 + 0.5 + legTimingOffset + rightLegOffset + sideFlipTimingOffset) * 0.4F * globalDegree + 0.4)
            * limbSwingAmount
      );
      rightCalf.addRotX(
         blend
            * (Math.cos(-limbSwing * speed * 0.5 + 2.2 + legTimingOffset + rightLegOffset + sideFlipTimingOffset) * 0.7F * globalDegree + 0.2 * globalDegree)
            * limbSwingAmount
      );
      rightFoot.addRotX(
         blend
            * (Math.cos(-limbSwing * speed * 0.5 + 2.2 + legTimingOffset + rightLegOffset + sideFlipTimingOffset) * -0.7F * globalDegree - 0.8F * globalDegree)
            * limbSwingAmount
      );
      rightToes.addRotX(
         blend
            * (-Math.cos(-limbSwing * speed * 0.5 + 2.5 + legTimingOffset + rightLegOffset + sideFlipTimingOffset) * -1.7F * globalDegree - 1.0F * globalDegree)
            * limbSwingAmount
      );
      rightToes.addRotX(
         blend
            * (this.poweredWave(-limbSwing, speed * 0.5F, 1.1415927F + sideFlipTimingOffset, 4.0) * -1.0 * globalDegree + 0.3 * globalDegree)
            * limbSwingAmount
      );
      MowzieGeoBone tail1 = this.getMowzieBone("tail1");
      MowzieGeoBone tail2 = this.getMowzieBone("tail2");
      MowzieGeoBone tail3 = this.getMowzieBone("tail3");
      MowzieGeoBone tail4 = this.getMowzieBone("tail4");
      MowzieGeoBone tail5 = this.getMowzieBone("tail5");
      tail1.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46) * -0.13F * globalHeight) * limbSwingAmount);
      tail1.addRotX(blend * (Math.cos(limbSwing * speed + 3.46) * -0.13F * globalHeight) * limbSwingAmount);
      tail2.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46 - 0.5) * 0.13F * globalHeight) * limbSwingAmount);
      tail2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail3.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 1.0) * -0.13F * globalHeight) * limbSwingAmount);
      tail4.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 1.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail5.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 2.0) * -0.13F * globalHeight) * limbSwingAmount);
      MowzieGeoBone tuft1 = this.getMowzieBone("tuft1");
      MowzieGeoBone tuft2 = this.getMowzieBone("tuft2");
      MowzieGeoBone tuft3 = this.getMowzieBone("tuft3");
      MowzieGeoBone tuft4 = this.getMowzieBone("tuft4");
      tuft1.addRotX(blend * (Math.cos(limbSwing * speed + 3.46) * -0.065F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.25) * -0.02F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.065F * globalHeight) * limbSwingAmount);
      tuft4.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.065F * globalHeight) * limbSwingAmount);
   }

   private void walkLeftAnim(double blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone headJoint = this.getMowzieBone("headJoint");
      MowzieGeoBone coM = this.getMowzieBone("CoM");
      MowzieGeoBone legs = this.getMowzieBone("legs");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone upperBody = this.getMowzieBone("upperBody");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftCalf = this.getMowzieBone("leftCalf");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToes = this.getMowzieBone("leftToes");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightCalf = this.getMowzieBone("rightCalf");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToes = this.getMowzieBone("rightToes");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      speed *= 2.1F;
      float globalHeight = 1.1F;
      float globalDegree = 0.9F;
      coM.addPosY(blend * (Math.cos(limbSwing * speed) * 0.3F * globalHeight + 0.1 * globalHeight) * limbSwingAmount);
      coM.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.2F * globalHeight) * limbSwingAmount);
      coM.addRotX(blend * (this.poweredWave(limbSwing, speed, -1.5, 3.0) * -0.15F * globalHeight - 0.075F * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * -0.1F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * 0.1F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      headJoint.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.15F * globalHeight) * limbSwingAmount);
      legs.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      leftArm.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      rightArm.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      upperBody.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3F * globalHeight) * limbSwingAmount);
      upperBody.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3F * globalHeight) * limbSwingAmount);
      legs.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.15F * globalHeight) * limbSwingAmount);
      legs.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.15F * globalHeight) * limbSwingAmount);
      double rightArmOffset = 2.641592653589793;
      upperBody.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      upperBody.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      leftArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.2F * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.2F * globalHeight) * limbSwingAmount);
      leftArm.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, 2.0, 3.0) * -3.0 * globalHeight * limbSwingAmount);
      leftArm.addPosY(blend * 1.0 * globalHeight * limbSwingAmount);
      rightArm.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, 3.0 + rightArmOffset, 6.0) * -2.0 * globalHeight * limbSwingAmount);
      rightArm.addPosY(blend * 1.0 * globalHeight * limbSwingAmount);
      leftArm.addPosY(blend * (Math.cos(limbSwing * speed * 0.5F - 0.0F) * 2.0 * globalHeight + 0.9F * globalHeight) * limbSwingAmount);
      rightArm.addPosY(blend * (Math.cos(limbSwing * speed * 0.5F - 0.8F + rightArmOffset) * -2.0 * globalHeight + 0.9F * globalHeight) * limbSwingAmount);
      leftArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.5 * globalDegree + 0.0F * globalDegree) * limbSwingAmount);
      leftForeArm.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 - 2.0) * -0.9 * globalDegree + 0.2 * globalDegree) * limbSwingAmount);
      leftHand.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 - 1.0) * 1.2 * globalDegree - 0.1 * globalDegree) * limbSwingAmount);
      rightArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8 + rightArmOffset) * 0.5 * globalDegree - 0.0F * globalDegree) * limbSwingAmount);
      rightForeArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 2.0 + rightArmOffset) * -0.9 * globalDegree - 0.0 * globalDegree) * limbSwingAmount);
      rightForeArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 0.8 + rightArmOffset) * 0.6 * globalDegree - 0.0 * globalDegree) * limbSwingAmount);
      rightHand.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 3.2 + rightArmOffset) * 0.5 * globalDegree) * limbSwingAmount);
      rightHand.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 - 3.2 + rightArmOffset) * 0.8 * globalDegree) * limbSwingAmount);
      double legTimingOffset = 3.1;
      leftThigh.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.15F * globalHeight) * limbSwingAmount);
      leftThigh.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0 + legTimingOffset) * 0.8F * globalDegree + 0.1 * globalDegree) * limbSwingAmount);
      leftThigh.addPosY(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset) * 0.5 * globalHeight + 1.0F * globalHeight) * limbSwingAmount);
      leftThigh.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.5, 3.0) * -0.5 * globalHeight * limbSwingAmount);
      leftThigh.addPosZ(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.5, 3.0) * -0.5 * globalHeight * limbSwingAmount);
      leftThigh.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      leftCalf.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 + 1.7 + legTimingOffset) * 0.9F * globalDegree + 0.2 * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 + 1.7 + legTimingOffset) * -0.9F * globalDegree - 0.8F * globalDegree) * limbSwingAmount);
      leftToes.addRotX(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.0 + legTimingOffset) * -1.7F * globalDegree - 1.0F * globalDegree) * limbSwingAmount);
      leftToes.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, -3.5, 4.0) * -1.0 * globalDegree + 0.3 * globalDegree) * limbSwingAmount);
      double rightLegOffset = Math.PI;
      rightThigh.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.15F * globalHeight) * limbSwingAmount);
      rightThigh.addRotX(blend * 0.2F * globalDegree * limbSwingAmount);
      rightThigh.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0 + legTimingOffset + rightLegOffset) * 0.8F * globalDegree) * limbSwingAmount);
      rightThigh.addPosY(
         blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset + rightLegOffset) * 0.5 * globalHeight + 1.0F * globalHeight) * limbSwingAmount
      );
      rightThigh.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, 1.1415927F, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      rightThigh.addPosZ(blend * this.poweredWave(limbSwing, speed * 0.5F, 1.1415927F, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      rightThigh.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset + rightLegOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      rightCalf.addRotX(
         blend * (Math.cos(limbSwing * speed * 0.5 + 1.2 + legTimingOffset + rightLegOffset) * 0.9F * globalDegree + 0.2 * globalDegree) * limbSwingAmount
      );
      rightFoot.addRotX(
         blend * (Math.cos(limbSwing * speed * 0.5 + 1.2 + legTimingOffset + rightLegOffset) * -0.9F * globalDegree - 0.8F * globalDegree) * limbSwingAmount
      );
      rightToes.addRotX(
         blend * (-Math.cos(limbSwing * speed * 0.5 + 1.0 + legTimingOffset + rightLegOffset) * -1.7F * globalDegree - 1.0F * globalDegree) * limbSwingAmount
      );
      rightToes.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, -0.35840726F, 4.0) * -1.0 * globalDegree + 0.3 * globalDegree) * limbSwingAmount);
      MowzieGeoBone tail1 = this.getMowzieBone("tail1");
      MowzieGeoBone tail2 = this.getMowzieBone("tail2");
      MowzieGeoBone tail3 = this.getMowzieBone("tail3");
      MowzieGeoBone tail4 = this.getMowzieBone("tail4");
      MowzieGeoBone tail5 = this.getMowzieBone("tail5");
      tail1.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46) * 0.13F * globalHeight) * limbSwingAmount);
      tail1.addRotX(blend * (Math.cos(limbSwing * speed + 3.46) * -0.13F * globalHeight) * limbSwingAmount);
      tail2.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46 - 0.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail3.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 1.0) * -0.13F * globalHeight) * limbSwingAmount);
      tail4.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 1.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail5.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 2.0) * -0.13F * globalHeight) * limbSwingAmount);
      MowzieGeoBone tuft1 = this.getMowzieBone("tuft1");
      MowzieGeoBone tuft2 = this.getMowzieBone("tuft2");
      MowzieGeoBone tuft3 = this.getMowzieBone("tuft3");
      MowzieGeoBone tuft4 = this.getMowzieBone("tuft4");
      tuft1.addRotX(blend * (Math.cos(limbSwing * speed + 3.46) * -0.065F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.25) * -0.02F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.065F * globalHeight) * limbSwingAmount);
      tuft4.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.065F * globalHeight) * limbSwingAmount);
   }

   private void walkRightAnim(double blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone headJoint = this.getMowzieBone("headJoint");
      MowzieGeoBone coM = this.getMowzieBone("CoM");
      MowzieGeoBone legs = this.getMowzieBone("legs");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone upperBody = this.getMowzieBone("upperBody");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftCalf = this.getMowzieBone("leftCalf");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToes = this.getMowzieBone("leftToes");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightCalf = this.getMowzieBone("rightCalf");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToes = this.getMowzieBone("rightToes");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      speed *= 2.1F;
      float globalHeight = 1.1F;
      float globalDegree = 0.9F;
      coM.addPosY(blend * (Math.cos(limbSwing * speed) * 0.3F * globalHeight + 0.1 * globalHeight) * limbSwingAmount);
      coM.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.2F * globalHeight) * limbSwingAmount);
      coM.addRotX(blend * (this.poweredWave(limbSwing, speed, -1.5, 3.0) * -0.15F * globalHeight - 0.075F * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * -0.1F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * 0.1F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      headJoint.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.15F * globalHeight) * limbSwingAmount);
      legs.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      leftArm.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      rightArm.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      upperBody.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3F * globalHeight) * limbSwingAmount);
      upperBody.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3F * globalHeight) * limbSwingAmount);
      legs.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.15F * globalHeight) * limbSwingAmount);
      legs.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.15F * globalHeight) * limbSwingAmount);
      double leftArmOffset = 2.641592653589793;
      upperBody.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      upperBody.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5) * 0.2F * globalHeight) * limbSwingAmount);
      leftArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5) * 0.2F * globalHeight) * limbSwingAmount);
      rightArm.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, 4.5707965F, 4.0) * -1.0 * globalHeight * limbSwingAmount);
      leftArm.addPosY(
         blend * this.poweredWave(limbSwing, speed * 0.5F, 3.0 + leftArmOffset + (float) (Math.PI / 2), 6.0) * 2.0 * globalHeight * limbSwingAmount
      );
      leftArm.addPosY(blend * 1.0 * globalHeight * limbSwingAmount);
      rightArm.addPosY(blend * (Math.cos(limbSwing * speed * 0.5F) * -2.0 * globalHeight) * limbSwingAmount);
      leftArm.addPosY(blend * (Math.cos(limbSwing * speed * 0.5F - 0.8F + leftArmOffset) * -2.0 * globalHeight + 0.9F * globalHeight) * limbSwingAmount);
      rightArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.5 * globalDegree + 0.0F * globalDegree) * limbSwingAmount);
      rightForeArm.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 - 2.0) * -0.9 * globalDegree - 0.2 * globalDegree) * limbSwingAmount);
      rightHand.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 - 1.0) * 1.2 * globalDegree + 0.1 * globalDegree) * limbSwingAmount);
      leftArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8 + leftArmOffset) * 0.5 * globalDegree - 0.0F * globalDegree) * limbSwingAmount);
      leftForeArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 2.0 + leftArmOffset) * -0.9 * globalDegree - 0.0 * globalDegree) * limbSwingAmount);
      leftForeArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 0.8 + leftArmOffset) * 0.6 * globalDegree - 0.0 * globalDegree) * limbSwingAmount);
      leftHand.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 3.2 + leftArmOffset) * 0.5 * globalDegree) * limbSwingAmount);
      leftHand.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 - 3.2 + leftArmOffset) * 0.8 * globalDegree) * limbSwingAmount);
      double legTimingOffset = 3.1;
      double sideFlipTimingOffset = (float) Math.PI;
      leftThigh.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.15F * globalHeight) * limbSwingAmount);
      leftThigh.addRotZ(
         blend
            * (Math.cos(limbSwing * speed * 0.5 + 0.0 + legTimingOffset + sideFlipTimingOffset) * 0.8F * globalDegree + 0.25 * globalDegree)
            * limbSwingAmount
      );
      leftThigh.addPosY(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset) * 0.5 * globalHeight + 1.0F * globalHeight) * limbSwingAmount);
      leftThigh.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.5, 3.0) * -0.5 * globalHeight * limbSwingAmount);
      leftThigh.addPosZ(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.5, 3.0) * -0.5 * globalHeight * limbSwingAmount);
      leftThigh.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      leftCalf.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 + 1.7 + legTimingOffset) * 0.9F * globalDegree + 0.2 * globalDegree) * limbSwingAmount);
      leftFoot.addRotX(blend * (Math.cos(limbSwing * speed * 0.5 + 1.7 + legTimingOffset) * -0.9F * globalDegree - 0.8F * globalDegree) * limbSwingAmount);
      leftToes.addRotX(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.0 + legTimingOffset) * -1.7F * globalDegree - 1.0F * globalDegree) * limbSwingAmount);
      leftToes.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, -3.5, 4.0) * -1.0 * globalDegree + 0.3 * globalDegree) * limbSwingAmount);
      double rightLegOffset = Math.PI;
      rightThigh.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.15F * globalHeight) * limbSwingAmount);
      rightThigh.addRotX(blend * 0.2F * globalDegree * limbSwingAmount);
      rightThigh.addRotZ(
         blend
            * (Math.cos(limbSwing * speed * 0.5 + 0.0 + legTimingOffset + rightLegOffset + sideFlipTimingOffset) * 0.8F * globalDegree + 0.15 * globalDegree)
            * limbSwingAmount
      );
      rightThigh.addPosY(
         blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset + rightLegOffset) * 0.5 * globalHeight + 1.0F * globalHeight) * limbSwingAmount
      );
      rightThigh.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, 1.1415927F, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      rightThigh.addPosZ(blend * this.poweredWave(limbSwing, speed * 0.5F, 1.1415927F, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      rightThigh.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset + rightLegOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      rightCalf.addRotX(
         blend * (Math.cos(limbSwing * speed * 0.5 + 1.2 + legTimingOffset + rightLegOffset) * 0.9F * globalDegree + 0.2 * globalDegree) * limbSwingAmount
      );
      rightFoot.addRotX(
         blend * (Math.cos(limbSwing * speed * 0.5 + 1.2 + legTimingOffset + rightLegOffset) * -0.9F * globalDegree - 0.8F * globalDegree) * limbSwingAmount
      );
      rightToes.addRotX(
         blend * (-Math.cos(limbSwing * speed * 0.5 + 1.0 + legTimingOffset + rightLegOffset) * -1.7F * globalDegree - 1.0F * globalDegree) * limbSwingAmount
      );
      rightToes.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, -0.35840726F, 4.0) * -1.0 * globalDegree + 0.3 * globalDegree) * limbSwingAmount);
      MowzieGeoBone tail1 = this.getMowzieBone("tail1");
      MowzieGeoBone tail2 = this.getMowzieBone("tail2");
      MowzieGeoBone tail3 = this.getMowzieBone("tail3");
      MowzieGeoBone tail4 = this.getMowzieBone("tail4");
      MowzieGeoBone tail5 = this.getMowzieBone("tail5");
      tail1.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46) * 0.13F * globalHeight) * limbSwingAmount);
      tail1.addRotX(blend * (Math.cos(limbSwing * speed + 3.46) * -0.13F * globalHeight) * limbSwingAmount);
      tail2.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46 - 0.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail3.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 1.0) * -0.13F * globalHeight) * limbSwingAmount);
      tail4.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 1.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail5.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 2.0) * -0.13F * globalHeight) * limbSwingAmount);
      MowzieGeoBone tuft1 = this.getMowzieBone("tuft1");
      MowzieGeoBone tuft2 = this.getMowzieBone("tuft2");
      MowzieGeoBone tuft3 = this.getMowzieBone("tuft3");
      MowzieGeoBone tuft4 = this.getMowzieBone("tuft4");
      tuft1.addRotX(blend * (Math.cos(limbSwing * speed + 3.46) * -0.065F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.25) * -0.02F * globalHeight) * limbSwingAmount);
      tuft2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.065F * globalHeight) * limbSwingAmount);
      tuft4.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.065F * globalHeight) * limbSwingAmount);
   }

   private void runAnimDay(double blend, float limbSwing, float limbSwingAmount, float speed) {
      limbSwingAmount *= limbSwingAmount;
      MowzieGeoBone head = this.getMowzieBone("head_day");
      MowzieGeoBone headJoint = this.getMowzieBone("headJoint_day");
      MowzieGeoBone coM = this.getMowzieBone("CoM_day");
      MowzieGeoBone stomach = this.getMowzieBone("stomach_day");
      MowzieGeoBone leftThighJoint = this.getMowzieBone("LegL_Joint_day");
      MowzieGeoBone leftThigh = this.getMowzieBone("LegL_day");
      MowzieGeoBone leftCalf = this.getMowzieBone("LowerLegL_day");
      MowzieGeoBone rightThighJoint = this.getMowzieBone("LegR_Joint_day");
      MowzieGeoBone rightThigh = this.getMowzieBone("LegR_day");
      MowzieGeoBone rightCalf = this.getMowzieBone("LowerLegL_day");
      MowzieGeoBone leftArmJoint = this.getMowzieBone("ArmL_Joint_day");
      MowzieGeoBone leftArm = this.getMowzieBone("UpperArmL_day");
      MowzieGeoBone leftForeArm = this.getMowzieBone("LowerArmL_day");
      MowzieGeoBone leftHand = this.getMowzieBone("HandL_day");
      MowzieGeoBone rightArmJoint = this.getMowzieBone("ArmR_Joint_day");
      MowzieGeoBone rightArm = this.getMowzieBone("UpperArmR_day");
      MowzieGeoBone rightForeArm = this.getMowzieBone("LowerArmR_day");
      MowzieGeoBone rightHand = this.getMowzieBone("HandR_day");
      float globalHeight = 1.15F;
      float globalDegree = 1.8F;
      speed *= 1.0F;
      coM.addPosY(blend * (Math.cos(limbSwing * speed) * 2.7F * globalHeight + 4.0F * globalHeight) * limbSwingAmount);
      coM.addPosZ(blend * (Math.cos(limbSwing * speed + 1.9) * 2.0 * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * (Math.cos(limbSwing * speed + 1.5) * -0.3 * globalHeight - 0.5) * limbSwingAmount);
      headJoint.addRotX(blend * (Math.cos(limbSwing * speed - 0.8) * -0.4 * globalHeight + 0.45 * globalHeight) * limbSwingAmount);
      double legTimingOffset = 1.3;
      leftThighJoint.addRotX(blend * (-Math.cos(limbSwing * speed + 0.0F + legTimingOffset) * 1.1F * globalDegree - 0.1 * globalDegree) * limbSwingAmount);
      leftThigh.addRotY(blend * (-Math.cos(limbSwing * speed + 0.5 + legTimingOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      leftThigh.addPosY(blend * (-Math.cos(limbSwing * speed + 0.5 + legTimingOffset) * 1.0 * globalDegree) * limbSwingAmount);
      double rightLegOffset = 0.7;
      rightThighJoint.addRotX(
         blend * (-Math.cos(limbSwing * speed + 0.0F + legTimingOffset + rightLegOffset) * 1.1F * globalDegree - 0.1 * globalDegree) * limbSwingAmount
      );
      rightThigh.addRotY(blend * (Math.cos(limbSwing * speed + 0.5 + legTimingOffset + rightLegOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      rightThigh.addPosY(blend * (-Math.cos(limbSwing * speed + 0.5 + legTimingOffset + rightLegOffset) * 1.0 * globalDegree) * limbSwingAmount);
      double rightArmOffset = 1.3;
      double leftArmOffset = 0.4;
      leftArm.addPosY(blend * this.poweredWave(limbSwing, speed, 0.0 + leftArmOffset, 4.0) * -1.5 * globalHeight * limbSwingAmount);
      leftArmJoint.addPosY(blend * this.poweredWave(limbSwing, speed, 2.5 + leftArmOffset, 4.0) * 1.5 * globalHeight * limbSwingAmount);
      leftArmJoint.addRotX(blend * -(Math.cos(limbSwing * speed + -0.8 + leftArmOffset) * 0.7 * globalDegree - 1.4) * limbSwingAmount);
      leftArmJoint.addRotY(blend * -(Math.cos(limbSwing * speed + -0.8 + leftArmOffset) * -0.4 * globalDegree - 0.5) * limbSwingAmount);
      leftArmJoint.addRotZ(blend * -(Math.cos(limbSwing * speed + -0.8 + leftArmOffset) * 0.6 * globalDegree + 0.2F) * limbSwingAmount);
      leftForeArm.addRotX(blend * (Math.cos(limbSwing * speed - 1.65 + leftArmOffset) * 0.5 * globalDegree - 1.1) * limbSwingAmount);
      leftForeArm.addRotZ(blend * (Math.cos(limbSwing * speed - 0.3 + leftArmOffset) * 0.7 * globalDegree + 0.2) * limbSwingAmount);
      leftHand.addRotX(blend * (Math.cos(limbSwing * speed + 4.5 + leftArmOffset) * -1.0 * globalDegree - 0.7) * limbSwingAmount);
      leftHand.addRotY(blend * -(Math.cos(limbSwing * speed + 4.5 + leftArmOffset) * -1.0 * globalDegree - 0.8) * limbSwingAmount);
      rightArm.addPosY(blend * this.poweredWave(limbSwing, speed, 0.5, 4.0) * -1.5 * globalHeight * limbSwingAmount);
      rightArmJoint.addPosY(blend * this.poweredWave(limbSwing, speed, -1.0, 4.0) * 1.5 * globalHeight * limbSwingAmount);
      rightArmJoint.addRotX(blend * -(Math.cos(limbSwing * speed + -0.8 + rightArmOffset) * 0.7 * globalDegree - 1.0) * limbSwingAmount);
      rightArmJoint.addRotY(blend * -(Math.cos(limbSwing * speed + -0.8 + rightArmOffset) * 0.4 * globalDegree + 0.5) * limbSwingAmount);
      rightArmJoint.addRotZ(blend * -(Math.cos(limbSwing * speed + -0.8 + rightArmOffset) * -0.6 * globalDegree - 0.2F) * limbSwingAmount);
      rightForeArm.addRotX(blend * (Math.cos(limbSwing * speed - 1.65 + rightArmOffset) * 0.5 * globalDegree - 0.87) * limbSwingAmount);
      rightForeArm.addRotX(blend * this.poweredWave(limbSwing, speed, 2.3415928F, 3.0) * 0.5 * globalHeight * limbSwingAmount);
      rightForeArm.addRotZ(blend * (Math.cos(limbSwing * speed - 0.3 + rightArmOffset) * -0.7 * globalDegree - 0.2) * limbSwingAmount);
      rightHand.addRotX(blend * (Math.cos(limbSwing * speed + 4.5 + rightArmOffset) * -0.8 * globalDegree - 0.8) * limbSwingAmount);
      rightHand.addRotY(blend * -(Math.cos(limbSwing * speed + 4.5 + rightArmOffset) * 1.0 * globalDegree + 0.5) * limbSwingAmount);
      MowzieGeoBone tail1 = this.getMowzieBone("tail1_day");
      MowzieGeoBone tail2 = this.getMowzieBone("tail2_day");
      MowzieGeoBone tail3 = this.getMowzieBone("tail3_day");
      tail1.addRotX(blend * (Math.cos(limbSwing * speed + 0.9) * 0.3F * globalHeight) * limbSwingAmount);
      tail2.addRotX(blend * (Math.cos(limbSwing * speed + 0.9 - 1.0) * 0.2F * globalHeight) * limbSwingAmount);
      tail3.addRotX(blend * (Math.cos(limbSwing * speed + 0.9 - 2.0) * 0.2F * globalHeight) * limbSwingAmount);
   }

   private void walkAnimDay(double blend, float limbSwing, float limbSwingAmount, float speed) {
      limbSwingAmount = Math.min(limbSwingAmount, 0.5F);
      MowzieGeoBone head = this.getMowzieBone("head_day");
      MowzieGeoBone headJoint = this.getMowzieBone("headJoint_day");
      MowzieGeoBone coM = this.getMowzieBone("CoM_day");
      MowzieGeoBone stomach = this.getMowzieBone("stomach_day");
      MowzieGeoBone leftThigh = this.getMowzieBone("LegL_day");
      MowzieGeoBone leftCalf = this.getMowzieBone("LowerLegL_day");
      MowzieGeoBone rightThigh = this.getMowzieBone("LegR_day");
      MowzieGeoBone rightCalf = this.getMowzieBone("LowerLegL_day");
      MowzieGeoBone leftArmJoint = this.getMowzieBone("ArmL_Joint_day");
      MowzieGeoBone leftArm = this.getMowzieBone("UpperArmL_day");
      MowzieGeoBone leftForeArm = this.getMowzieBone("LowerArmL_day");
      MowzieGeoBone leftHand = this.getMowzieBone("HandL_day");
      MowzieGeoBone rightArmJoint = this.getMowzieBone("ArmR_Joint_day");
      MowzieGeoBone rightArm = this.getMowzieBone("UpperArmR_day");
      MowzieGeoBone rightForeArm = this.getMowzieBone("LowerArmR_day");
      MowzieGeoBone rightHand = this.getMowzieBone("HandR_day");
      speed *= 2.7F;
      float globalHeight = 1.3F;
      float globalDegree = 1.3F;
      coM.addPosY(blend * (Math.cos(limbSwing * speed) * 0.3F * globalHeight + 0.2 * globalHeight) * limbSwingAmount);
      coM.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.2F * globalHeight) * limbSwingAmount);
      coM.addRotX(blend * (this.poweredWave(limbSwing, speed, -1.5, 3.0) * -0.15F * globalHeight - 0.175F * globalHeight) * limbSwingAmount);
      stomach.addRotX(blend * -0.1F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * 0.4F * globalHeight * limbSwingAmount);
      headJoint.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      headJoint.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * -0.1F * globalHeight) * limbSwingAmount);
      leftArm.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      rightArm.addRotX(blend * this.poweredWave(limbSwing, speed, -1.5, 3.0) * 0.15F * globalHeight * limbSwingAmount);
      stomach.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3F * globalHeight) * limbSwingAmount);
      stomach.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3F * globalHeight) * limbSwingAmount);
      stomach.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      stomach.addRotZ(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.1F * globalHeight) * limbSwingAmount);
      headJoint.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 - 1.0) * -0.4F * globalHeight) * limbSwingAmount);
      leftArm.addPosY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 1.0 * globalHeight - 0.0F * globalHeight) * limbSwingAmount);
      leftArm.addRotX(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.5 * globalDegree + 0.1 * globalDegree) * limbSwingAmount);
      leftArm.addRotY(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3 * globalDegree + 0.5 * globalDegree) * limbSwingAmount);
      leftArm.addRotZ(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.4 * globalDegree + 0.65 * globalDegree) * limbSwingAmount);
      leftArm.addRotZ(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8 - 1.57) * 0.4 * globalDegree + 0.4 * globalDegree) * limbSwingAmount);
      leftArm.addRotZ(blend * (this.poweredWave(limbSwing, speed * 0.5F, -3.94F, 5.0) * 0.9F * globalHeight) * limbSwingAmount);
      leftForeArm.addRotX(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.4 * globalDegree + 0.0F * globalDegree) * limbSwingAmount);
      leftForeArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.55 * globalDegree - 0.2 * globalDegree) * limbSwingAmount);
      leftHand.addRotY(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.3 * globalDegree - 0.5 * globalDegree) * limbSwingAmount);
      leftHand.addRotX(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8 - 1.57) * 1.0 * globalDegree - 0.2 * globalDegree) * limbSwingAmount);
      leftHand.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, -3.3F, 10.0) * 1.7F * globalHeight) * limbSwingAmount);
      leftHand.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, -0.8F, 8.0) * 1.1F * globalHeight) * limbSwingAmount);
      double rightArmTimingOffset = (float) Math.PI;
      rightArm.addPosY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8 + rightArmTimingOffset) * 1.0 * globalHeight - 0.0F * globalHeight) * limbSwingAmount);
      rightArm.addRotX(blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8 + rightArmTimingOffset) * 0.5 * globalDegree + 0.1 * globalDegree) * limbSwingAmount);
      rightArm.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8 + rightArmTimingOffset) * 0.3 * globalDegree + 0.5 * globalDegree) * limbSwingAmount);
      rightArm.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8 + rightArmTimingOffset) * 0.4 * globalDegree + 0.65 * globalDegree) * limbSwingAmount);
      rightArm.addRotZ(
         blend * (Math.cos(limbSwing * speed * 0.5 + -0.8 - 1.57 + rightArmTimingOffset) * 0.4 * globalDegree + 0.4 * globalDegree) * limbSwingAmount
      );
      rightArm.addRotZ(blend * -(this.poweredWave(limbSwing, speed * 0.5F, -3.94F + rightArmTimingOffset, 5.0) * 0.9F * globalHeight) * limbSwingAmount);
      rightForeArm.addRotX(
         blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8 + rightArmTimingOffset) * 0.4 * globalDegree + 0.0F * globalDegree) * limbSwingAmount
      );
      rightForeArm.addRotZ(
         blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8 + rightArmTimingOffset) * 0.55 * globalDegree - 0.2 * globalDegree) * limbSwingAmount
      );
      rightHand.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8 + rightArmTimingOffset) * 0.3 * globalDegree - 0.5 * globalDegree) * limbSwingAmount);
      rightHand.addRotX(
         blend * -(Math.cos(limbSwing * speed * 0.5 + -0.8 - 1.57 + rightArmTimingOffset) * 1.0 * globalDegree - 0.2 * globalDegree) * limbSwingAmount
      );
      rightHand.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, -3.3F + rightArmTimingOffset, 10.0) * 1.7F * globalHeight) * limbSwingAmount);
      rightHand.addRotX(blend * (this.poweredWave(limbSwing, speed * 0.5F, -0.8F + rightArmTimingOffset, 8.0) * 1.1F * globalHeight) * limbSwingAmount);
      double legTimingOffset = 1.5;
      leftThigh.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8) * 0.15F * globalHeight) * limbSwingAmount);
      leftThigh.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0) * 0.35F * globalHeight) * limbSwingAmount);
      leftThigh.addRotX(blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0 + legTimingOffset) * 0.8F * globalDegree + 0.1 * globalDegree) * limbSwingAmount);
      leftThigh.addPosY(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset) * 0.8F * globalHeight - 0.2 * globalHeight) * limbSwingAmount);
      leftThigh.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.7F, 5.0) * -2.0 * globalHeight * limbSwingAmount);
      leftThigh.addPosZ(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.57F, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      leftThigh.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset) * 0.4F * globalDegree + 0.4) * limbSwingAmount);
      double rightLegOffset = Math.PI;
      rightThigh.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + -0.8 + rightLegOffset) * -0.15F * globalHeight) * limbSwingAmount);
      rightThigh.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 0.0 + rightLegOffset) * -0.35F * globalHeight) * limbSwingAmount);
      rightThigh.addRotX(
         blend * (-Math.cos(limbSwing * speed * 0.5 + 0.0 + legTimingOffset + rightLegOffset) * 0.8F * globalDegree + 0.1 * globalDegree) * limbSwingAmount
      );
      rightThigh.addPosY(
         blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset + rightLegOffset) * 0.8F * globalHeight - 0.2 * globalHeight) * limbSwingAmount
      );
      rightThigh.addPosY(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.7F + rightLegOffset, 5.0) * -2.0 * globalHeight * limbSwingAmount);
      rightThigh.addPosZ(blend * this.poweredWave(limbSwing, speed * 0.5F, -1.5 + rightLegOffset, 3.0) * -1.0 * globalHeight * limbSwingAmount);
      rightThigh.addRotY(blend * (-Math.cos(limbSwing * speed * 0.5 + 1.5 + legTimingOffset + rightLegOffset) * -0.4F * globalDegree - 0.4) * limbSwingAmount);
      MowzieGeoBone tail1 = this.getMowzieBone("tail1_day");
      MowzieGeoBone tail2 = this.getMowzieBone("tail2_day");
      MowzieGeoBone tail3 = this.getMowzieBone("tail3_day");
      tail1.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46) * 0.6F * globalHeight) * limbSwingAmount);
      tail1.addRotX(blend * (Math.cos(limbSwing * speed + 3.46) * -0.2F * globalHeight) * limbSwingAmount);
      tail2.addRotY(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46 - 0.5) * 0.6F * globalHeight) * limbSwingAmount);
      tail2.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 0.5) * -0.13F * globalHeight) * limbSwingAmount);
      tail3.addRotX(blend * (Math.cos(limbSwing * speed + 3.46 - 1.0) * -0.13F * globalHeight) * limbSwingAmount);
      tail3.addRotZ(blend * (Math.cos(limbSwing * speed * 0.5 + 3.46 - 1.0) * 0.6F * globalHeight) * limbSwingAmount);
   }
}
