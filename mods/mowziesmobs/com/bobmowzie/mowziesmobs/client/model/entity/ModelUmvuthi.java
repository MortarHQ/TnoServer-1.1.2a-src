package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthi;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Triple;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.data.EntityModelData;

public class ModelUmvuthi extends MowzieGeoModel<EntityUmvuthi> {
   public ResourceLocation getModelResource(EntityUmvuthi object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/umvuthi.geo.json");
   }

   public ResourceLocation getTextureResource(EntityUmvuthi object) {
      return RenderUmvuthi.TEXTURE;
   }

   public ResourceLocation getAnimationResource(EntityUmvuthi object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/umvuthi.animation.json");
   }

   public void setCustomAnimations(EntityUmvuthi entity, long instanceId, AnimationState<EntityUmvuthi> animationState) {
      float frame = entity.frame + animationState.getPartialTick();
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      float liftLegs = entity.legsUp.getAnimationProgressSinSqrt(animationState.getPartialTick());
      leftThigh.addRotX(1.0F * liftLegs);
      rightThigh.addRotX(1.0F * liftLegs);
      leftThigh.addRotZ(1.5F * liftLegs);
      rightThigh.addRotZ(-1.5F * liftLegs);
      leftThigh.addRotY(-0.5F * liftLegs);
      rightThigh.addRotY(0.5F * liftLegs);
      if (entity.isAlive() && entity.active) {
         MowzieGeoBone neck1 = this.getMowzieBone("neck");
         MowzieGeoBone neck2 = this.getMowzieBone("neck2");
         MowzieGeoBone head = this.getMowzieBone("head");
         MowzieGeoBone[] lookPieces = new MowzieGeoBone[]{neck1, neck2, head};
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         float headYaw = Mth.wrapDegrees(entityData.netHeadYaw());
         float headPitch = Mth.wrapDegrees(entityData.headPitch());
         float maxYaw = 140.0F;
         headYaw = Mth.clamp(headYaw, -maxYaw, maxYaw);

         for (MowzieGeoBone bone : lookPieces) {
            bone.addRotX(headPitch * (float) (Math.PI / 180.0) / lookPieces.length);
            bone.addRotY(headYaw * (float) (Math.PI / 180.0) / lookPieces.length);
         }

         MowzieGeoBone featherRaiseController = this.getMowzieBone("featherRaiseController");
         MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
         MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
         MowzieGeoBone rightCalf = this.getMowzieBone("rightCalf");
         MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
         MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
         MowzieGeoBone leftCalf = this.getMowzieBone("leftCalf");
         MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
         MowzieGeoBone rightLowerArm = this.getMowzieBone("rightLowerArm");
         MowzieGeoBone rightArmJoint = this.getMowzieBone("rightArmJoint");
         MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
         MowzieGeoBone leftLowerArm = this.getMowzieBone("leftLowerArm");
         MowzieGeoBone leftArmJoint = this.getMowzieBone("leftArmJoint");
         MowzieGeoBone chest = this.getMowzieBone("chest");
         MowzieGeoBone body = this.getMowzieBone("body");
         MowzieGeoBone headJoint = this.getMowzieBone("headJoint");
         float idleSpeed = 0.08F;
         featherRaiseController.addPosX((float)(Math.sin((frame - 1.2) * idleSpeed) * 0.1));
         body.addRotX((float)(Math.sin((frame - 0.0) * idleSpeed) * 0.035));
         chest.addPosY((float)(-0.7 + Math.sin((frame - 0.0) * idleSpeed) * 0.014));
         chest.addRotX((float)(Math.sin((frame - 0.0) * idleSpeed) * -0.017));
         neck1.addRotX((float)(Math.cos((frame - 0.3) * idleSpeed) * -0.052));
         neck2.addRotX((float)(Math.cos((frame - 0.5) * idleSpeed) * -0.052));
         headJoint.addRotX((float)(Math.cos((frame - 0.6) * idleSpeed) * 0.1));
         leftArmJoint.addRotX((float)(Math.sin((frame - 0.0) * idleSpeed) * 0.052));
         leftLowerArm.addRotY((float)(-Math.cos((frame - 0.0) * idleSpeed) * 0.035));
         leftHand.addRotY((float)(-Math.sin((frame - 0.0) * idleSpeed) * 0.087));
         rightArmJoint.addRotX((float)(Math.sin((frame - 0.0) * idleSpeed) * 0.052));
         rightLowerArm.addRotY((float)(Math.cos((frame - 0.0) * idleSpeed) * 0.035));
         rightHand.addRotY((float)(Math.sin((frame - 0.0) * idleSpeed) * 0.087));
         leftThigh.addRotY((float)(Math.cos((frame - 0.0) * idleSpeed) * -0.052) * (1.0F - liftLegs));
         leftThigh.addRotZ((float)(Math.sin((frame - 0.0) * idleSpeed) * -0.052) * (1.0F - liftLegs));
         leftCalf.addRotX((float)(Math.sin((frame - 0.2) * idleSpeed) * 0.087) * (1.0F - liftLegs));
         leftAnkle.addRotX((float)(Math.cos((frame - 0.2) * idleSpeed) * -0.087) * (1.0F - liftLegs));
         leftFoot.addRotX((float)(Math.cos((frame - 0.4) * idleSpeed) * -0.14));
         rightThigh.addRotY((float)(Math.cos((frame - 0.0) * idleSpeed) * 0.052) * (1.0F - liftLegs));
         rightThigh.addRotZ((float)(Math.sin((frame - 0.0) * idleSpeed) * 0.052) * (1.0F - liftLegs));
         rightCalf.addRotX((float)(Math.sin((frame - 0.2) * idleSpeed) * 0.087) * (1.0F - liftLegs));
         rightAnkle.addRotX((float)(Math.cos((frame - 0.2) * idleSpeed) * -0.087) * (1.0F - liftLegs));
         rightFoot.addRotX((float)(Math.cos((frame - 0.4) * idleSpeed) * -0.14));
         leftThigh.addRotY((float)(Math.sin((frame - 0.0) * idleSpeed) * 0.035) * liftLegs);
         rightThigh.addRotY((float)(-Math.sin((frame - 0.0) * idleSpeed) * 0.035) * liftLegs);
         float armAimControl = this.getControllerValue("armAimController");
         leftArmJoint.addRotX(headPitch * (float) (Math.PI / 180.0) * armAimControl);
         leftArmJoint.addRotY(headYaw * (float) (Math.PI / 180.0) * armAimControl);
      }

      float bellyBounceControl = this.getControllerValue("bellyBounceController");
      float jiggleSpeed = 2.5F;
      float jiggleScale = (float)(bellyBounceControl * 0.1 * Math.cos(jiggleSpeed * frame));
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone tail = this.getMowzieBone("tail");
      stomach.setScale(stomach.getScaleX() + jiggleScale, stomach.getScaleY() + jiggleScale, stomach.getScaleZ() + jiggleScale);
      chest.addPosY(jiggleScale * 3.0F);
      leftThigh.addPosX(-jiggleScale * 5.0F);
      rightThigh.addPosX(jiggleScale * 5.0F);
      tail.addPosZ(jiggleScale * 4.0F);
      float featherShakeControl = this.getControllerValue("featherShakeController");
      float featherRaiseControl = this.getControllerValue("featherRaiseController");
      List<Triple<MowzieGeoBone, Axis, Boolean>> feathers = new ArrayList<>();
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersFront1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersFront2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersFront3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersFront4"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack1"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack2"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack3"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack4"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack5"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft1"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft2"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft3"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft4"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft5"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight1"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight2"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight3"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight4"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight5"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersFront1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersFront2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersFront3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersLeft1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersLeft2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersLeft3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersRight1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersRight2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersRight3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersFront1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersFront2"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersFront3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight3"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight4"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight5"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft3"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft4"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft5"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestDraperyFront"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestDraperyRight"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("chestDraperyLeft"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("chestDraperyBack"), Axis.X, true));

      for (Triple<MowzieGeoBone, Axis, Boolean> feather : feathers) {
         MowzieGeoBone bone = (MowzieGeoBone)feather.getLeft();
         float oscillation = (float)(featherShakeControl * 0.13 * Math.cos(1.4 * frame + bone.getPivotY() * -0.15 + bone.getPivotZ() * -0.1))
            + featherRaiseControl;
         if ((Boolean)feather.getRight()) {
            oscillation *= -1.0F;
         }

         Axis axis = (Axis)feather.getMiddle();
         if (axis == Axis.X) {
            bone.addRotX(oscillation);
         } else if (axis == Axis.Y) {
            bone.addRotY(oscillation);
         } else {
            bone.addRotZ(oscillation);
         }
      }

      MowzieGeoBone mask = this.getMowzieBone("mask");
      MowzieGeoBone body = this.getMowzieBone("body");
      mask.setScale(1.0F / (float)body.getScale().x, 1.0F / (float)body.getScale().y, 1.0F / (float)body.getScale().z);
   }
}
