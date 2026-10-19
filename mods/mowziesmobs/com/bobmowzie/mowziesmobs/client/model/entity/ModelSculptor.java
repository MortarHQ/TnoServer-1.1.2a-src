package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.RigUtils;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.data.EntityModelData;

public class ModelSculptor extends MowzieGeoModel<EntitySculptor> {
   public MowzieGeoBone[] beardOriginal;
   public MowzieGeoBone[] beardDynamic;
   private static final String[] GAUNTLET_ASSEMBLE_ORDER = new String[]{
      "centerRock", "sleeve", "rightRock1", "wrist", "pinky", "index", "thumb", "leftRock1", "leftRock2", "rightRock2"
   };

   public ResourceLocation getModelResource(EntitySculptor object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/sculptor.geo.json");
   }

   public ResourceLocation getTextureResource(EntitySculptor object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/sculptor.png");
   }

   public ResourceLocation getAnimationResource(EntitySculptor object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/sculptor.animation.json");
   }

   public void setCustomAnimations(EntitySculptor entity, long instanceId, AnimationState<EntitySculptor> animationState) {
      GeoBone head = (GeoBone)this.getBone("head").get();
      GeoBone chestJoint = (GeoBone)this.getBone("chestJoint").get();
      GeoBone handClosedL = (GeoBone)this.getBone("handClosedLeft").get();
      GeoBone handClosedR = (GeoBone)this.getBone("handClosedRight").get();
      GeoBone handOpenL = (GeoBone)this.getBone("handOpenLeft").get();
      GeoBone handOpenR = (GeoBone)this.getBone("handOpenRight").get();
      GeoBone backCloth = (GeoBone)this.getBone("clothBack").get();
      EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
      float torsoAimController = this.getControllerValue("torsoAimController");
      head.setRotX(head.getRotX() + entityData.headPitch() * (float) (Math.PI / 180.0) * (1.0F - torsoAimController));
      head.setRotY(head.getRotY() + entityData.netHeadYaw() * (float) (Math.PI / 180.0) * (1.0F - torsoAimController));
      chestJoint.setRotX(chestJoint.getRotX() + entityData.headPitch() * (float) (Math.PI / 180.0) * torsoAimController);
      chestJoint.setRotY(chestJoint.getRotY() + entityData.netHeadYaw() * (float) (Math.PI / 180.0) * torsoAimController);
      if (entity.isAlive()) {
         this.idleAnim(entity, animationState);
      }

      float handControllerLeft = this.getControllerValue("handControllerLeft");
      float handControllerRight = this.getControllerValue("handControllerRight");
      boolean handLOpen = handControllerLeft == 0.0F;
      boolean handROpen = handControllerRight == 0.0F;
      handClosedL.setHidden(handLOpen);
      handClosedR.setHidden(handROpen);
      handOpenL.setHidden(!handLOpen);
      handOpenR.setHidden(!handROpen);
      backCloth.setHidden(false);
      this.beadsCorrections(entity);
      this.skirtCorrections(entity);
      this.staffRendering(entity);
      this.gauntletVisibility();
      if (this.beardOriginal == null || this.beardDynamic == null) {
         this.beardOriginal = new MowzieGeoBone[]{
            this.getMowzieBone("beard3"), this.getMowzieBone("beard4"), this.getMowzieBone("beard5Rot"), this.getMowzieBone("beard6")
         };
         this.beardDynamic = new MowzieGeoBone[this.beardOriginal.length];
      }

      if (entity.beardChain != null) {
         entity.beardChain.setChainArrays(this.beardOriginal, this.beardDynamic);
      }
   }

   private void beadsCorrections(EntitySculptor entity) {
      Map<MowzieGeoBone, Vec3> beadsDirections = new HashMap<>();
      beadsDirections.put(this.getMowzieBone("bead1"), new Vec3(0.0, -1.0, 0.0));
      beadsDirections.put(this.getMowzieBone("bead2"), new Vec3(0.0, -1.0, 0.0));
      beadsDirections.put(this.getMowzieBone("bead3"), new Vec3(0.0, -1.0, 0.0));
      beadsDirections.put(this.getMowzieBone("bead4"), new Vec3(0.0, -0.5, 0.25));
      beadsDirections.put(this.getMowzieBone("bead5"), new Vec3(0.0, -0.5, 0.25));
      beadsDirections.put(this.getMowzieBone("bead6"), new Vec3(0.0, -0.5, 1.0));
      beadsDirections.put(this.getMowzieBone("bead7"), new Vec3(0.0, -0.5, 1.0));
      beadsDirections.put(this.getMowzieBone("bead8"), new Vec3(0.0, -0.25, 0.75));
      beadsDirections.put(this.getMowzieBone("bead9"), new Vec3(0.0, -0.25, 0.75));
      GeoBone headJoint = (GeoBone)this.getBone("headJoint").get();
      Vec3 headPos = new Vec3(headJoint.getPivotX(), headJoint.getPivotY(), headJoint.getPivotZ());
      GeoBone head = (GeoBone)this.getBone("head").get();
      Vec3 headDir = new Vec3(0.0, 0.0, -1.0).normalize();
      headDir = headDir.yRot(head.getRotY()).xRot(head.getRotX());

      for (Entry<MowzieGeoBone, Vec3> beadDir : beadsDirections.entrySet()) {
         MowzieGeoBone bead = beadDir.getKey();
         Vec3 beadPos = new Vec3(bead.getPivotX(), bead.getPivotY(), bead.getPivotZ());
         Vec3 dir = beadPos.subtract(headPos).normalize().multiply(1.0, -1.0, 1.0);
         double dot = dir.dot(headDir);
         dot = Math.pow(Math.max(dot, 0.0), 3.5);
         Vec3 moveDir = beadDir.getValue().normalize();
         bead.addPos(moveDir.scale(dot * 3.0));
      }
   }

   private void skirtCorrections(EntitySculptor entity) {
      if (entity.calfRPos != null
         && entity.calfLPos != null
         && entity.thighRPos != null
         && entity.thighLPos != null
         && entity.skirtEndRPos != null
         && entity.skirtEndLPos != null
         && entity.skirtLocFrontRPos != null
         && entity.skirtLocFrontLPos != null
         && entity.skirtLocBackRPos != null
         && entity.skirtLocBackLPos != null
         && entity.frontClothRot != null) {
         MowzieGeoBone headJoint = this.getMowzieBone("headJoint");
         MowzieGeoBone thighR = this.getMowzieBone("thighRight");
         MowzieGeoBone thighJointR = this.getMowzieBone("thighJointRight");
         MowzieGeoBone thighJointL = this.getMowzieBone("thighJointLeft");
         MowzieGeoBone calfR = this.getMowzieBone("calfRight");
         MowzieGeoBone thighL = this.getMowzieBone("thighLeft");
         MowzieGeoBone calfL = this.getMowzieBone("calfLeft");
         MowzieGeoBone footR = this.getMowzieBone("footRight");
         MowzieGeoBone skirtBack = this.getMowzieBone("skirtBack");
         MowzieGeoBone skirtFront = this.getMowzieBone("skirtFront");
         MowzieGeoBone skirtL = this.getMowzieBone("skirtLeft");
         MowzieGeoBone skirtR = this.getMowzieBone("skirtRight");
         MowzieGeoBone skirtJointL = this.getMowzieBone("skirtJointLeft");
         MowzieGeoBone skirtJointR = this.getMowzieBone("skirtJointRight");
         MowzieGeoBone skirtJoint2L = this.getMowzieBone("skirtJoint2Left");
         MowzieGeoBone skirtJoint2R = this.getMowzieBone("skirtJoint2Right");
         MowzieGeoBone skirtEndR = this.getMowzieBone("skirtEndRight");
         MowzieGeoBone skirtEndL = this.getMowzieBone("skirtEndLeft");
         MowzieGeoBone skirtLocFrontR = this.getMowzieBone("skirtFrontLocRight");
         MowzieGeoBone skirtLocFrontL = this.getMowzieBone("skirtFrontLocLeft");
         MowzieGeoBone skirtLocBackR = this.getMowzieBone("skirtBackLocRight");
         MowzieGeoBone skirtLocBackL = this.getMowzieBone("skirtBackLocLeft");
         headJoint.setHidden(false);
         Vec3 thighToKneeR = Vec3(entity.calfRPos).subtract(Vec3(entity.thighRPos)).normalize();
         Vec3 thighToKneeL = Vec3(entity.calfLPos).subtract(Vec3(entity.thighLPos)).normalize();
         skirtEndL.addPos(-0.2F, -1.5F, 0.0F);
         skirtEndR.addPos(0.2F, -1.5F, 0.0F);
         Vec3 thighToSkirtEndR = Vec3(entity.skirtEndRPos).subtract(Vec3(entity.thighRPos)).normalize();
         Vec3 thighToSkirtEndL = Vec3(entity.skirtEndLPos).subtract(Vec3(entity.thighLPos)).normalize();
         float rightDot = (float)thighToKneeR.dot(new Vec3(0.0, -1.0, 0.0));
         rightDot = (float)Math.pow(Math.max(rightDot, 0.0F), 3.0);
         float leftDot = (float)thighToKneeL.dot(new Vec3(0.0, -1.0, 0.0));
         leftDot = (float)Math.pow(Math.max(leftDot, 0.0F), 3.0);
         skirtJointR.addPos(Math.max(-0.9F * rightDot, -0.7F), 0.0F, Math.max(-0.7F * rightDot, -0.5F));
         skirtJointL.addPos(-Math.max(-0.9F * leftDot, -0.7F), 0.0F, Math.max(-0.7F * leftDot, -0.5F));
         Quaternionf rightRot = RigUtils.betweenVectors(thighToSkirtEndR, thighToKneeR);
         Quaternionf leftRot = RigUtils.betweenVectors(thighToSkirtEndL, thighToKneeL);
         Matrix4f rightMat = new Matrix4f().rotate(rightRot);
         Matrix4f leftMat = new Matrix4f().rotate(leftRot);
         skirtJoint2R.setModelXformOverride(rightMat);
         skirtJoint2L.setModelXformOverride(leftMat);
         Vec3 average = thighToKneeL.add(thighToKneeR).scale(2.0).multiply(0.0, 1.0, 1.0).normalize();
         float angleAv = (float)Mth.atan2(average.y(), average.z());
         skirtBack.setRotX(skirtBack.getRotX() - angleAv + 3.48F);
         skirtFront.setRotX(skirtFront.getRotX() - Math.min(angleAv, -2.0F) + 3.48F);
         Vec3 skirtFrontDiff = Vec3(entity.skirtLocFrontLPos).subtract(Vec3(entity.skirtLocFrontRPos));
         skirtFront.setScaleX(Math.max((float)(0.3F + 0.15F * skirtFrontDiff.length()), 0.4F));
         Vec3 skirtBackDiff = Vec3(entity.skirtLocBackLPos).subtract(Vec3(entity.skirtLocBackRPos));
         skirtBack.setScaleX((float)(0.15F + 0.1F * skirtBackDiff.length()));
         float angleF = (float)Mth.atan2(skirtFrontDiff.normalize().z(), skirtFrontDiff.normalize().x());
         if (angleF < 0.001 || angleF > 3.141) {
            angleF = 0.0F;
         }

         skirtFront.setRotY(angleF * 0.6F);
         skirtFront.addPos(0.5F * (float)Vec3(entity.skirtLocFrontRPos).add(skirtFrontDiff.scale(0.5)).x(), 0.0F, 0.0F);
         float angleB = (float)Mth.atan2(skirtBackDiff.normalize().z(), skirtBackDiff.normalize().x());
         skirtBack.setRotY(angleB * 0.5F);
         skirtBack.addPos(0.5F * (float)Vec3(entity.skirtLocBackRPos).add(skirtBackDiff.scale(0.5)).x(), 0.0F, 0.0F);
         float bothDots = (float)Math.pow(rightDot * leftDot, 1.0);
         float f = Math.min(1.0F, bothDots * 2.0F);
         skirtR.addRot(0.0F, Mth.clamp(angleF, -0.5F, 0.5F) * (1.0F - f) - bothDots * 0.4F, 0.0F);
         skirtL.addRot(0.0F, Mth.clamp(angleF, -0.5F, 0.5F) * (1.0F - f) + bothDots * 0.4F, 0.0F);
         MowzieGeoBone frontCloth = this.getMowzieBone("clothFront");
         MowzieGeoBone frontCloth2 = this.getMowzieBone("clothFront2");
         frontCloth.setRot(skirtFront.getRot());
         Matrix4f mat = entity.frontClothRot;
         mat.invert();
         frontCloth2.setModelXformOverride(mat);
      }
   }

   private void staffRendering(EntitySculptor entity) {
      MowzieGeoBone itemHandLeft = this.getMowzieBone("itemHandLeft");
      MowzieGeoBone itemHandRight = this.getMowzieBone("itemHandRight");
      MowzieGeoBone backItem = this.getMowzieBone("backItem");
      itemHandLeft.setHidden(true);
      itemHandRight.setHidden(true);
      backItem.setHidden(true);
      MowzieGeoBone staffController = this.getMowzieBone("staffController");
      switch ((int)staffController.getPosX()) {
         case -1:
            itemHandRight.setHidden(false);
            break;
         case 0:
            backItem.setHidden(false);
            break;
         case 1:
            itemHandLeft.setHidden(false);
      }

      itemHandLeft.setScale(1.2F);
      itemHandRight.setScale(1.2F);
      backItem.setScale(1.2F);
   }

   private void idleAnim(EntitySculptor entity, AnimationState<?> animationState) {
      float frame = entity.frame + animationState.getPartialTick();
      MowzieGeoBone eyebrowRight = this.getMowzieBone("eyebrowRight");
      MowzieGeoBone eyebrowLeft = this.getMowzieBone("eyebrowLeft");
      MowzieGeoBone clothFront2 = this.getMowzieBone("clothFront2");
      MowzieGeoBone clothBack = this.getMowzieBone("clothBack");
      MowzieGeoBone footLeft = this.getMowzieBone("footLeft");
      MowzieGeoBone calfLeft = this.getMowzieBone("calfLeft");
      MowzieGeoBone thighLeft = this.getMowzieBone("thighLeft");
      MowzieGeoBone headJoint = this.getMowzieBone("headJoint");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone footRight = this.getMowzieBone("footRight");
      MowzieGeoBone calfRight = this.getMowzieBone("calfRight");
      MowzieGeoBone thighRight = this.getMowzieBone("thighRight");
      MowzieGeoBone mouth = this.getMowzieBone("mouth");
      MowzieGeoBone handLeft = this.getMowzieBone("handLeft");
      MowzieGeoBone lowerArmLeft = this.getMowzieBone("lowerArmLeft");
      MowzieGeoBone shoulderLeft = this.getMowzieBone("shoulderLeft");
      MowzieGeoBone handRight = this.getMowzieBone("handRight");
      MowzieGeoBone lowerArmRight = this.getMowzieBone("lowerArmRight");
      MowzieGeoBone shoulderRight = this.getMowzieBone("shoulderRight");
      MowzieGeoBone body = this.getMowzieBone("body");
      float idleAnim = this.getControllerValue("idleAnimController");
      float idleAnimDisableArms = this.getControllerValueInverted("idleAnimDisableArmsController");
      float idleSpeed = 0.08F;
      eyebrowRight.addPosY(idleAnim * (float)(Math.sin(frame * idleSpeed + 0.4) * 0.1));
      eyebrowLeft.addPosY(idleAnim * (float)(Math.sin(frame * idleSpeed + 0.4) * 0.1));
      clothFront2.addRotX(idleAnim * (float)(Math.sin(frame * idleSpeed - 1.0F) * 0.05));
      clothBack.addRotX(idleAnim * (float)(-Math.sin(frame * idleSpeed - 1.0F) * 0.05));
      footLeft.addRotZ(idleAnim * (float)(-0.175 - Math.sin(frame * idleSpeed + 0.5) * 0.035));
      calfLeft.addRotZ(idleAnim * (float)(0.175 + Math.sin(frame * idleSpeed + 0.5) * 0.035));
      thighLeft.addRotZ(idleAnim * (float)(0.035 - Math.sin(frame * idleSpeed + 1.5) * 0.035));
      headJoint.addRotX(idleAnim * (float)(Math.sin(frame * idleSpeed + 1.5) * 0.017));
      chest.addRotX(idleAnim * (float)(-Math.sin((frame * idleSpeed + 1.0F) * idleSpeed) * 0.017));
      footRight.addRotY(-0.1309F);
      footRight.addRotZ(idleAnim * (float)(-0.35 + Math.sin(frame * idleSpeed + 0.5) * 0.035));
      calfRight.addRotZ(idleAnim * (float)(0.567 - Math.sin(frame * idleSpeed + 0.5) * 0.035));
      thighRight.addRotZ(idleAnim * (float)(-0.297 + Math.sin(frame * idleSpeed + 1.5) * 0.035));
      mouth.addPosY(idleAnim * (float)(-Math.sin(frame * idleSpeed + 0.5) * 0.1F));
      mouth.setScaleY((float)(mouth.getScaleZ() + idleAnim * Math.sin(frame * idleSpeed + 0.5) * 0.05F));
      handLeft.addRotY(idleAnim * idleAnimDisableArms * (float)(-Math.sin(frame * idleSpeed + 1.0F) * 0.05));
      lowerArmLeft.addRotX(idleAnim * idleAnimDisableArms * (float)(-Math.sin(frame * idleSpeed + 0.5) * 0.05));
      lowerArmLeft.addRotY(idleAnim * idleAnimDisableArms * (float)(-Math.sin(frame * idleSpeed + 0.5) * 0.05));
      shoulderLeft.addRotZ(idleAnim * idleAnimDisableArms * (float)(Math.sin(frame * idleSpeed - 0.5) * 0.05));
      handRight.addRotY(idleAnim * idleAnimDisableArms * (float)(Math.sin(frame * idleSpeed + 1.0F) * 0.05));
      lowerArmRight.addRotX(idleAnim * idleAnimDisableArms * (float)(-Math.sin(frame * idleSpeed + 0.5) * 0.05));
      lowerArmRight.addRotY(idleAnim * idleAnimDisableArms * (float)(Math.sin(frame * idleSpeed + 0.5) * 0.05));
      shoulderRight.addRotZ(idleAnim * idleAnimDisableArms * (float)(-Math.sin(frame * idleSpeed - 0.5) * 0.05));
      body.addRotX(idleAnim * (float)(-Math.cos(frame * idleSpeed + 0.5) * 0.017));
      body.addPosY(idleAnim * (float)(Math.sin(frame * idleSpeed) * 1.0));
   }

   private void gauntletVisibility() {
      float gauntletProgress = this.getControllerValue("gauntletProgressController");
      MowzieGeoBone gauntlet = this.getMowzieBone("gauntlet");
      MowzieGeoBone gauntletUnparented = this.getMowzieBone("gauntletUnparented");
      if (!(gauntletProgress <= 0.0) && !(gauntletProgress > 1.15)) {
         gauntlet.setHidden(false);
         gauntletUnparented.setHidden(false);

         for (int i = 0; i < GAUNTLET_ASSEMBLE_ORDER.length; i++) {
            String boneName = GAUNTLET_ASSEMBLE_ORDER[i];
            Optional<GeoBone> bone = this.getBone(boneName);
            float waitForControllerValue = (float)i / GAUNTLET_ASSEMBLE_ORDER.length + 0.2F;
            if (bone.isPresent()) {
               bone.get().setHidden(gauntletProgress < waitForControllerValue);
            } else {
               System.out.println("Missing bone " + boneName);
            }

            Optional<GeoBone> boneUnparented = this.getBone(boneName + "Unparented");
            if (boneUnparented.isPresent()) {
               boneUnparented.get().setHidden(gauntletProgress >= waitForControllerValue || gauntletProgress < waitForControllerValue - 0.1);
            } else {
               System.out.println("Missing bone " + boneName + "Unparented");
            }
         }
      } else {
         gauntlet.setHidden(true);
         gauntletUnparented.setHidden(true);
      }
   }

   private static Vec3 Vec3(Vector3d vec) {
      return new Vec3(vec.x, vec.y, vec.z);
   }
}
