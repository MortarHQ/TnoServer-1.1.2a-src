package com.mortal_cultivation.mixin;

import com.mortal_cultivation.ability.skill.extra.CultivatingTheMysticDaoSkill;
import com.mortal_cultivation.client.animation.FlightAnimationClientState;
import com.mortal_cultivation.entity.FlyingSwordEntity;
import com.mortal_cultivation.item.TenThousandSoulBannerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public class PlayerModelMixin {
   private static final Vec3 WAIST_PIVOT = new Vec3(0.0, 12.0, 0.0);
   private static final Vec3 TORSO_PIVOT = Vec3.ZERO;
   private static final Vec3 HEAD_PIVOT = Vec3.ZERO;
   private static final Vec3 RIGHT_ARM_PIVOT = new Vec3(-5.0, 2.0, 0.0);
   private static final Vec3 LEFT_ARM_PIVOT = new Vec3(5.0, 2.0, 0.0);
   private static final Vec3 RIGHT_LEG_PIVOT = new Vec3(-1.9, 12.0, 0.0);
   private static final Vec3 LEFT_LEG_PIVOT = new Vec3(1.9, 12.0, 0.0);

   @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"), remap = false)
   private void onSetupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
      if (entity instanceof AbstractClientPlayer player) {
         PlayerModel model = (PlayerModel)this;
         Minecraft minecraft = Minecraft.getInstance();
         boolean firstPersonLocalPlayer = player == minecraft.player && minecraft.options.getCameraType().isFirstPerson();
         if (!firstPersonLocalPlayer && player.getVehicle() instanceof FlyingSwordEntity) {
            this.applyFlyingSwordRiderPose(model);
         } else if (firstPersonLocalPlayer || !this.applyFlightAnimation(model, player, ageInTicks)) {
            if (CultivatingTheMysticDaoSkill.isMeditating(player)) {
               this.applyMysticDaoMeditationPose(model);
            } else {
               ItemStack mainHandItem = player.getMainHandItem();
               ItemStack offHandItem = player.getOffhandItem();
               boolean holdingInMainHand = mainHandItem.getItem() instanceof TenThousandSoulBannerItem;
               boolean holdingInOffHand = offHandItem.getItem() instanceof TenThousandSoulBannerItem;
               if (holdingInMainHand || holdingInOffHand) {
                  this.applyBannerAnimation(model, holdingInMainHand, holdingInOffHand);
               }
            }
         }
      }
   }

   private boolean applyFlightAnimation(PlayerModel<AbstractClientPlayer> model, AbstractClientPlayer player, float ageInTicks) {
      float partialTick = Mth.clamp(ageInTicks - player.tickCount, 0.0F, 1.0F);
      FlightAnimationClientState.AnimationSample waistSample = FlightAnimationClientState.sample(player.getUUID(), "body", partialTick);
      if (waistSample != null && !(waistSample.weight() <= 1.0E-4F)) {
         PlayerModelMixin.ModelTransform waist = this.toModelTransform(waistSample);
         this.applyChildPart(model.body, waist, FlightAnimationClientState.sample(player.getUUID(), "torso", partialTick), TORSO_PIVOT);
         this.applyChildPart(model.head, waist, FlightAnimationClientState.sample(player.getUUID(), "head", partialTick), HEAD_PIVOT);
         this.applyChildPart(model.rightArm, waist, FlightAnimationClientState.sample(player.getUUID(), "right_arm", partialTick), RIGHT_ARM_PIVOT);
         this.applyChildPart(model.leftArm, waist, FlightAnimationClientState.sample(player.getUUID(), "left_arm", partialTick), LEFT_ARM_PIVOT);
         this.applyRootPart(model.rightLeg, FlightAnimationClientState.sample(player.getUUID(), "right_leg", partialTick), RIGHT_LEG_PIVOT);
         this.applyRootPart(model.leftLeg, FlightAnimationClientState.sample(player.getUUID(), "left_leg", partialTick), LEFT_LEG_PIVOT);
         model.hat.copyFrom(model.head);
         model.jacket.copyFrom(model.body);
         model.leftSleeve.copyFrom(model.leftArm);
         model.rightSleeve.copyFrom(model.rightArm);
         model.leftPants.copyFrom(model.leftLeg);
         model.rightPants.copyFrom(model.rightLeg);
         return true;
      } else {
         return false;
      }
   }

   private void applyChildPart(ModelPart part, PlayerModelMixin.ModelTransform parent, FlightAnimationClientState.AnimationSample sample, Vec3 basePivot) {
      if (sample != null) {
         PlayerModelMixin.ModelTransform child = this.toModelTransform(sample);
         float weight = sample.weight();
         Quaternionf parentRotation = new Quaternionf().slerp(parent.rotation(), weight);
         Quaternionf childRotation = this.partRotation(part).slerp(child.rotation(), weight);
         Quaternionf rotation = new Quaternionf(parentRotation).mul(childRotation);
         Vec3 blendedBasePivot = new Vec3(Mth.lerp(weight, part.x, basePivot.x), Mth.lerp(weight, part.y, basePivot.y), Mth.lerp(weight, part.z, basePivot.z));
         Vec3 childPivot = blendedBasePivot.add(child.position().scale(weight));
         Vector3f relativePivot = new Vector3f(
            (float)(childPivot.x - WAIST_PIVOT.x), (float)(childPivot.y - WAIST_PIVOT.y), (float)(childPivot.z - WAIST_PIVOT.z)
         );
         parentRotation.transform(relativePivot);
         Vec3 parentPosition = parent.position().scale(weight);
         Vec3 targetPivot = new Vec3(
            WAIST_PIVOT.x + parentPosition.x + relativePivot.x,
            WAIST_PIVOT.y + parentPosition.y + relativePivot.y,
            WAIST_PIVOT.z + parentPosition.z + relativePivot.z
         );
         this.setPartTransform(part, rotation, targetPivot);
      }
   }

   private void applyRootPart(ModelPart part, FlightAnimationClientState.AnimationSample sample, Vec3 basePivot) {
      if (sample != null) {
         PlayerModelMixin.ModelTransform transform = this.toModelTransform(sample);
         float weight = sample.weight();
         Quaternionf rotation = this.partRotation(part).slerp(transform.rotation(), weight);
         Vec3 targetPosition = basePivot.add(transform.position());
         Vec3 position = new Vec3(
            Mth.lerp(weight, part.x, targetPosition.x), Mth.lerp(weight, part.y, targetPosition.y), Mth.lerp(weight, part.z, targetPosition.z)
         );
         this.setPartTransform(part, rotation, position);
      }
   }

   private PlayerModelMixin.ModelTransform toModelTransform(FlightAnimationClientState.AnimationSample sample) {
      Vec3 sourceRotation = sample.pose().rotationDegrees();
      float xRotation = (float)Math.toRadians(sourceRotation.x);
      float yRotation = (float)Math.toRadians(sourceRotation.y);
      float zRotation = (float)Math.toRadians(sourceRotation.z);
      Quaternionf rotation = new Quaternionf().rotationZYX(zRotation, yRotation, xRotation);
      Vec3 sourcePosition = sample.pose().position();
      Vec3 position = new Vec3(sourcePosition.x, -sourcePosition.y, sourcePosition.z);
      return new PlayerModelMixin.ModelTransform(rotation, position);
   }

   private Quaternionf partRotation(ModelPart part) {
      return new Quaternionf().rotationZYX(part.zRot, part.yRot, part.xRot);
   }

   private void setPartTransform(ModelPart part, Quaternionf rotation, Vec3 position) {
      Vector3f eulerAngles = rotation.getEulerAnglesZYX(new Vector3f());
      part.xRot = eulerAngles.x;
      part.yRot = eulerAngles.y;
      part.zRot = eulerAngles.z;
      part.x = (float)position.x;
      part.y = (float)position.y;
      part.z = (float)position.z;
   }

   private void applyBannerAnimation(PlayerModel<AbstractClientPlayer> model, boolean mainHand, boolean offHand) {
      if (mainHand) {
         model.rightArm.xRot = (float)Math.toRadians(-80.0);
         model.rightArm.yRot = (float)Math.toRadians(10.0);
         model.rightArm.zRot = 0.0F;
         model.rightArmPose = ArmPose.ITEM;
      }

      if (offHand) {
         model.leftArm.xRot = (float)Math.toRadians(-80.0);
         model.leftArm.yRot = (float)Math.toRadians(-10.0);
         model.leftArm.zRot = 0.0F;
         model.leftArmPose = ArmPose.ITEM;
      }
   }

   private void applyMysticDaoMeditationPose(PlayerModel<AbstractClientPlayer> model) {
      model.body.xRot = 0.2F;
      model.head.xRot = -0.15F;
      model.rightArm.xRot = -1.25F;
      model.rightArm.yRot = 0.2F;
      model.rightArm.zRot = 0.15F;
      model.leftArm.xRot = -1.25F;
      model.leftArm.yRot = -0.2F;
      model.leftArm.zRot = -0.15F;
      model.rightLeg.xRot = -1.35F;
      model.rightLeg.yRot = 0.35F;
      model.rightLeg.zRot = 0.08F;
      model.leftLeg.xRot = -1.35F;
      model.leftLeg.yRot = -0.35F;
      model.leftLeg.zRot = -0.08F;
      model.hat.copyFrom(model.head);
      model.jacket.copyFrom(model.body);
      model.leftSleeve.copyFrom(model.leftArm);
      model.rightSleeve.copyFrom(model.rightArm);
      model.leftPants.copyFrom(model.leftLeg);
      model.rightPants.copyFrom(model.rightLeg);
   }

   private void applyFlyingSwordRiderPose(PlayerModel<AbstractClientPlayer> model) {
      model.body.xRot = 0.12F;
      model.head.xRot -= 0.08F;
      model.rightArm.xRot = -0.35F;
      model.rightArm.yRot = -0.12F;
      model.rightArm.zRot = 0.14F;
      model.leftArm.xRot = 0.2F;
      model.leftArm.yRot = 0.12F;
      model.leftArm.zRot = -0.14F;
      model.rightLeg.xRot = -0.2F;
      model.rightLeg.yRot = -0.1F;
      model.rightLeg.zRot = 0.05F;
      model.leftLeg.xRot = 0.24F;
      model.leftLeg.yRot = 0.1F;
      model.leftLeg.zRot = -0.05F;
      model.hat.copyFrom(model.head);
      model.jacket.copyFrom(model.body);
      model.leftSleeve.copyFrom(model.leftArm);
      model.rightSleeve.copyFrom(model.rightArm);
      model.leftPants.copyFrom(model.leftLeg);
      model.rightPants.copyFrom(model.rightLeg);
   }

   private record ModelTransform(Quaternionf rotation, Vec3 position) {
   }
}
