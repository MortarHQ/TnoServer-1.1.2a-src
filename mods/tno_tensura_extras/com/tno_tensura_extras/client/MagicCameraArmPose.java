package com.tno_tensura_extras.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public final class MagicCameraArmPose {
   public static final EnumProxy<ArmPose> TWO_HANDED = new EnumProxy(ArmPose.class, new Object[]{true, MagicCameraArmPose::applyTwoHanded});
   public static final EnumProxy<ArmPose> ONE_HANDED = new EnumProxy(ArmPose.class, new Object[]{false, MagicCameraArmPose::applyOneHanded});

   private MagicCameraArmPose() {
   }

   public static ArmPose select(LivingEntity entity, InteractionHand cameraHand) {
      InteractionHand otherHand = cameraHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
      return entity.getItemInHand(otherHand).isEmpty() ? (ArmPose)TWO_HANDED.getValue() : (ArmPose)ONE_HANDED.getValue();
   }

   private static void applyTwoHanded(HumanoidModel<?> model, LivingEntity entity, HumanoidArm cameraArm) {
      float crouchingOffset = entity.isCrouching() ? (float) (Math.PI / 12) : 0.0F;
      float cameraPitch = Mth.clamp(model.head.xRot - 1.75F - crouchingOffset, -2.4F, 0.5F);
      float supportPitch = Mth.clamp(model.head.xRot - 1.55F - crouchingOffset, -2.4F, 0.5F);
      poseArm(model, cameraArm, cameraPitch, 0.3F, 0.08F);
      poseArm(model, cameraArm.getOpposite(), supportPitch, 0.42F, 0.12F);
   }

   private static void applyOneHanded(HumanoidModel<?> model, LivingEntity entity, HumanoidArm cameraArm) {
      float crouchingOffset = entity.isCrouching() ? (float) (Math.PI / 12) : 0.0F;
      float cameraPitch = Mth.clamp(model.head.xRot - 1.75F - crouchingOffset, -2.4F, 0.5F);
      poseArm(model, cameraArm, cameraPitch, 0.3F, 0.08F);
   }

   private static void poseArm(HumanoidModel<?> model, HumanoidArm arm, float pitch, float inwardYaw, float roll) {
      boolean right = arm == HumanoidArm.RIGHT;
      ModelPart modelArm = right ? model.rightArm : model.leftArm;
      modelArm.xRot = pitch;
      modelArm.yRot = model.head.yRot + (right ? -inwardYaw : inwardYaw);
      modelArm.zRot = right ? roll : -roll;
   }
}
