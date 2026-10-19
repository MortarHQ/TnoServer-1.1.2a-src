package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.cache.object.GeoBone;

public class ModelGeckoBiped extends MowzieGeoModel<GeckoPlayer> {
   private ResourceLocation textureLocation;
   public boolean isSitting = false;
   public boolean isChild = true;
   public float swingProgress;
   public boolean isSneak;
   public float swimAnimation;
   public ArmPose leftArmPose = ArmPose.EMPTY;
   public ArmPose rightArmPose = ArmPose.EMPTY;
   protected boolean useSmallArms = false;

   public ResourceLocation getAnimationResource(GeckoPlayer animatable) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/animated_player.animation.json");
   }

   public ResourceLocation getModelResource(GeckoPlayer animatable) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/animated_player.geo.json");
   }

   public ResourceLocation getTextureResource(GeckoPlayer animatable) {
      return this.textureLocation;
   }

   public void setTextureFromPlayer(AbstractClientPlayer player) {
      this.textureLocation = player.getSkin().texture();
   }

   public void setUseSmallArms(boolean useSmallArms) {
      this.useSmallArms = useSmallArms;
   }

   public boolean isUsingSmallArms() {
      return this.useSmallArms;
   }

   public MowzieGeoBone bipedHead() {
      return this.getMowzieBone("Head");
   }

   public MowzieGeoBone bipedHeadwear() {
      return this.getMowzieBone("HatLayer");
   }

   public MowzieGeoBone bipedBody() {
      return this.getMowzieBone("Body");
   }

   public MowzieGeoBone bipedRightArm() {
      return this.getMowzieBone("RightArm");
   }

   public MowzieGeoBone bipedLeftArm() {
      return this.getMowzieBone("LeftArm");
   }

   public MowzieGeoBone bipedRightLeg() {
      return this.getMowzieBone("RightLeg");
   }

   public MowzieGeoBone bipedLeftLeg() {
      return this.getMowzieBone("LeftLeg");
   }

   public void setVisible(boolean visible) {
      this.bipedHead().setHidden(!visible);
      this.bipedHeadwear().setHidden(!visible);
      this.bipedBody().setHidden(!visible);
      this.bipedRightArm().setHidden(!visible);
      this.bipedLeftArm().setHidden(!visible);
      this.bipedRightLeg().setHidden(!visible);
      this.bipedLeftLeg().setHidden(!visible);
   }

   public void setRotationAngles() {
      MowzieGeoBone head = this.getMowzieBone("Head");
      MowzieGeoBone neck = this.getMowzieBone("Neck");
      float yaw = 0.0F;
      float pitch = 0.0F;
      float roll = 0.0F;

      for (GeoBone parent = neck.getParent(); parent != null; parent = parent.getParent()) {
         pitch += parent.getRotX();
         yaw += parent.getRotY();
         roll += parent.getRotZ();
      }

      neck.addRot(-yaw, -pitch, -roll);
   }

   public void setRotationAngles(
      Player entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick
   ) {
      if (this.isInitialized()) {
         if (!Minecraft.getInstance().isPaused()) {
            MowzieGeoBone rightArmClassic = this.getMowzieBone("RightArmClassic");
            MowzieGeoBone leftArmClassic = this.getMowzieBone("LeftArmClassic");
            MowzieGeoBone rightArmSlim = this.getMowzieBone("RightArmSlim");
            MowzieGeoBone leftArmSlim = this.getMowzieBone("LeftArmSlim");
            if (this.useSmallArms) {
               rightArmClassic.setHidden(true);
               leftArmClassic.setHidden(true);
               rightArmSlim.setHidden(false);
               leftArmSlim.setHidden(false);
            } else {
               rightArmSlim.setHidden(true);
               leftArmSlim.setHidden(true);
               rightArmClassic.setHidden(false);
               leftArmClassic.setHidden(false);
            }

            this.swimAnimation = entityIn.getSwimAmount(partialTick);
            float headLookAmount = this.getControllerValueInverted("HeadLookController");
            float armLookAmount = 1.0F - this.getControllerValueInverted("ArmPitchController");
            float armLookAmountRight = ((GeoBone)this.getBone("ArmPitchController").get()).getPosY();
            float armLookAmountLeft = ((GeoBone)this.getBone("ArmPitchController").get()).getPosZ();
            boolean flag = entityIn.getFallFlyingTicks() > 4;
            boolean flag1 = entityIn.isVisuallySwimming();
            this.bipedHead().addRotY(headLookAmount * -netHeadYaw * (float) (Math.PI / 180.0));
            this.getMowzieBone("LeftClavicle").addRotY(Math.min(armLookAmount + armLookAmountLeft, 1.0F) * -netHeadYaw * (float) (Math.PI / 180.0));
            this.getMowzieBone("RightClavicle").addRotY(Math.min(armLookAmount + armLookAmountRight, 1.0F) * -netHeadYaw * (float) (Math.PI / 180.0));
            if (flag) {
               this.bipedHead().addRotX((float) (-Math.PI / 4));
            } else if (this.swimAnimation > 0.0F) {
               if (flag1) {
                  this.bipedHead().addRotX(headLookAmount * this.rotLerpRad(this.swimAnimation, this.bipedHead().getRotX(), (float) (-Math.PI / 4)));
               } else {
                  this.bipedHead()
                     .addRotX(headLookAmount * this.rotLerpRad(this.swimAnimation, this.bipedHead().getRotX(), headPitch * (float) (Math.PI / 180.0)));
               }
            } else {
               this.bipedHead().addRotX(headLookAmount * -headPitch * (float) (Math.PI / 180.0));
               this.getMowzieBone("LeftClavicle").addRotX(Math.min(armLookAmount + armLookAmountLeft, 1.0F) * -headPitch * (float) (Math.PI / 180.0));
               this.getMowzieBone("RightClavicle").addRotX(Math.min(armLookAmount + armLookAmountRight, 1.0F) * -headPitch * (float) (Math.PI / 180.0));
            }

            float f = 1.0F;
            if (flag) {
               f = (float)entityIn.getDeltaMovement().lengthSqr();
               f /= 0.2F;
               f = f * f * f;
            }

            if (f < 1.0F) {
               f = 1.0F;
            }

            float legWalkAmount = this.getControllerValueInverted("LegWalkController");
            float armSwingAmount = this.getControllerValueInverted("ArmSwingController");
            float armSwingAmountRight = 1.0F - ((GeoBone)this.getBone("ArmSwingController").get()).getPosY();
            float armSwingAmountLeft = 1.0F - ((GeoBone)this.getBone("ArmSwingController").get()).getPosZ();
            this.bipedRightArm()
               .addRotX(armSwingAmount * armSwingAmountRight * Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f);
            this.bipedLeftArm().addRotX(armSwingAmount * armSwingAmountLeft * Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f);
            this.bipedRightLeg().addRotX(legWalkAmount * Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f);
            this.bipedLeftLeg().addRotX(legWalkAmount * Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f);
            if (this.isSitting) {
               this.bipedRightArm().setRotX(this.bipedRightArm().getRotX() + (float) (-Math.PI / 5));
               this.bipedLeftArm().setRotX(this.bipedRightArm().getRotX() + (float) (-Math.PI / 5));
               this.bipedRightLeg().setRotX(-1.4137167F);
               this.bipedRightLeg().setRotY((float) (Math.PI / 10));
               this.bipedRightLeg().setRotZ(0.07853982F);
               this.bipedLeftLeg().setRotX(-1.4137167F);
               this.bipedLeftLeg().setRotY((float) (-Math.PI / 10));
               this.bipedLeftLeg().setRotZ(-0.07853982F);
               this.getMowzieBone("Waist").setRot(0.0F, 0.0F, 0.0F);
               this.getMowzieBone("Root").setRot(0.0F, 0.0F, 0.0F);
            }

            boolean flag2 = entityIn.getMainArm() == HumanoidArm.RIGHT;
            boolean flag3 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
            if (flag2 != flag3) {
               this.poseLeftArm(entityIn);
               this.poseRightArm(entityIn);
            } else {
               this.poseRightArm(entityIn);
               this.poseLeftArm(entityIn);
            }

            float sneakController = this.getControllerValueInverted("CrouchController");
            if (this.isSneak) {
               this.bipedBody().addRotX(-0.5F * sneakController);
               this.getMowzieBone("Neck").addRotX(0.5F * sneakController);
               this.bipedRightArm().addRot(0.4F * sneakController, 0.0F, 0.0F);
               this.bipedLeftArm().addRot(0.4F * sneakController, 0.0F, 0.0F);
               this.bipedHead().addPosY(-1.0F * sneakController);
               this.bipedBody().addPos(0.0F, -1.5F * sneakController, 1.7F * sneakController);
               this.getMowzieBone("Waist").addPos(0.0F, -0.2F * sneakController, 4.0F * sneakController);
               this.bipedLeftArm().addRotX(-0.4F * sneakController);
               this.bipedLeftArm().addPos(0.0F, 0.2F * sneakController, -1.0F * sneakController);
               this.bipedRightArm().addRotX(-0.4F * sneakController);
               this.bipedRightArm().addPos(0.0F, 0.2F * sneakController, -1.0F * sneakController);
               this.getMowzieBone("Waist").addPosY(2.0F * (1.0F - sneakController));
            }

            float armBreathAmount = this.getControllerValueInverted("ArmBreathController");
            breathAnim(this.bipedRightArm(), this.bipedLeftArm(), ageInTicks, armBreathAmount);
            AbilityData data = DataHandler.getData(entityIn, DataHandler.ABILITY_DATA);
            if (data.getActiveAbility() != null) {
               data.codeAnimations(this, partialTick);
            }
         }
      }
   }

   protected MowzieGeoBone getArmForSide(HumanoidArm side) {
      return side == HumanoidArm.LEFT ? this.bipedLeftArm() : this.bipedRightArm();
   }

   protected float rotLerpRad(float angleIn, float maxAngleIn, float mulIn) {
      float f = (mulIn - maxAngleIn) % (float) (Math.PI * 2);
      if (f < (float) -Math.PI) {
         f += (float) (Math.PI * 2);
      }

      if (f >= (float) Math.PI) {
         f -= (float) (Math.PI * 2);
      }

      return maxAngleIn + angleIn * f;
   }

   private float getArmAngleSq(float limbSwing) {
      return -65.0F * limbSwing + limbSwing * limbSwing;
   }

   protected HumanoidArm getMainHand(Player entityIn) {
      HumanoidArm handside = entityIn.getMainArm();
      return entityIn.swingingArm == InteractionHand.MAIN_HAND ? handside : handside.getOpposite();
   }

   public static void breathAnim(MowzieGeoBone rightArm, MowzieGeoBone leftArm, float ageInTicks, float armBreathAmount) {
      rightArm.addRotZ(armBreathAmount * Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
      leftArm.addRotZ(armBreathAmount * -Mth.cos(ageInTicks * 0.09F) * 0.05F - 0.05F);
      rightArm.addRotX(armBreathAmount * Mth.sin(ageInTicks * 0.067F) * 0.05F);
      leftArm.addRotX(armBreathAmount * -Mth.sin(ageInTicks * 0.067F) * 0.05F);
   }

   private void poseRightArm(Player p_241654_1_) {
      float armSwingAmount = this.getControllerValueInverted("ArmSwingController");
      switch (this.rightArmPose) {
         case EMPTY:
         default:
            break;
         case BLOCK:
            this.bipedRightArm().addRotX(0.9424779F * armSwingAmount);
            break;
         case ITEM:
            this.bipedRightArm().addRotX((float) (Math.PI / 10) * armSwingAmount);
      }
   }

   private void poseLeftArm(Player p_241655_1_) {
      float armSwingAmount = this.getControllerValueInverted("ArmSwingController");
      switch (this.leftArmPose) {
         case EMPTY:
         default:
            break;
         case BLOCK:
            this.bipedLeftArm().addRotX(0.9424779F * armSwingAmount);
            break;
         case ITEM:
            this.bipedLeftArm().addRotX((float) (Math.PI / 10) * armSwingAmount);
      }
   }
}
