package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;

public class ModelGeckoPlayerFirstPerson extends MowzieGeoModel<GeckoPlayer> {
   private ResourceLocation textureLocation;
   public ArmPose leftArmPose = ArmPose.EMPTY;
   public ArmPose rightArmPose = ArmPose.EMPTY;
   protected boolean useSmallArms;

   public ResourceLocation getAnimationResource(GeckoPlayer animatable) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/animated_player_first_person.animation.json");
   }

   public ResourceLocation getModelResource(GeckoPlayer animatable) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/animated_player_first_person.geo.json");
   }

   public ResourceLocation getTextureResource(GeckoPlayer animatable) {
      return this.textureLocation;
   }

   public void setUseSmallArms(boolean useSmallArms) {
      this.useSmallArms = useSmallArms;
   }

   public boolean isUsingSmallArms() {
      return this.useSmallArms;
   }

   public void setCustomAnimations(GeckoPlayer animatable, long instanceId, AnimationState<GeckoPlayer> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);
      if (this.isInitialized()) {
         MowzieGeoBone rightArmLayerClassic = this.getMowzieBone("RightArmLayerClassic");
         MowzieGeoBone leftArmLayerClassic = this.getMowzieBone("LeftArmLayerClassic");
         MowzieGeoBone rightArmLayerSlim = this.getMowzieBone("RightArmLayerSlim");
         MowzieGeoBone leftArmLayerSlim = this.getMowzieBone("LeftArmLayerSlim");
         MowzieGeoBone rightArmClassic = this.getMowzieBone("RightArmClassic");
         MowzieGeoBone leftArmClassic = this.getMowzieBone("LeftArmClassic");
         MowzieGeoBone rightArmSlim = this.getMowzieBone("RightArmSlim");
         MowzieGeoBone leftArmSlim = this.getMowzieBone("LeftArmSlim");
         this.getMowzieBone("LeftHeldItem").setHidden(true);
         this.getMowzieBone("RightHeldItem").setHidden(true);
         rightArmClassic.setHidden(true);
         leftArmClassic.setHidden(true);
         rightArmLayerClassic.setHidden(true);
         leftArmLayerClassic.setHidden(true);
         rightArmSlim.setHidden(true);
         leftArmSlim.setHidden(true);
         rightArmLayerSlim.setHidden(true);
         leftArmLayerSlim.setHidden(true);
      }
   }

   public void setTextureFromPlayer(AbstractClientPlayer player) {
      this.textureLocation = player.getSkin().texture();
   }
}
