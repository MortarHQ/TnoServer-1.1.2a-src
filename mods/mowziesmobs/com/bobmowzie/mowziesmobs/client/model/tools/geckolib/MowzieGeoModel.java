package com.bobmowzie.mowziesmobs.client.model.tools.geckolib;

import java.util.Optional;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public abstract class MowzieGeoModel<T extends GeoAnimatable> extends GeoModel<T> {
   public MowzieGeoBone getMowzieBone(String boneName) {
      Optional<GeoBone> bone = this.getBone(boneName);
      return (MowzieGeoBone)bone.orElse(null);
   }

   public boolean isInitialized() {
      return !this.getAnimationProcessor().getRegisteredBones().isEmpty();
   }

   public void resetBoneToSnapshot(GeoBone bone) {
      BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
      bone.setRotX(initialSnapshot.getRotX());
      bone.setRotY(initialSnapshot.getRotY());
      bone.setRotZ(initialSnapshot.getRotZ());
      bone.setPosX(initialSnapshot.getOffsetX());
      bone.setPosY(initialSnapshot.getOffsetY());
      bone.setPosZ(initialSnapshot.getOffsetZ());
      bone.setScaleX(initialSnapshot.getScaleX());
      bone.setScaleY(initialSnapshot.getScaleY());
      bone.setScaleZ(initialSnapshot.getScaleZ());
   }

   public void applyMolangQueries(AnimationState<T> state, double animTime) {
      this.getAnimationProcessor().getRegisteredBones().forEach(this::resetBoneToSnapshot);
      super.applyMolangQueries(state, animTime);
   }

   public float getControllerValueInverted(String controllerName) {
      if (!this.isInitialized()) {
         return 1.0F;
      }

      Optional<GeoBone> bone = this.getBone(controllerName);
      return bone.isEmpty() ? 1.0F : 1.0F - bone.get().getPosX();
   }

   public float getControllerValue(String controllerName) {
      if (!this.isInitialized()) {
         return 0.0F;
      }

      Optional<GeoBone> bone = this.getBone(controllerName);
      return bone.isEmpty() ? 0.0F : bone.get().getPosX();
   }

   public double poweredWave(double x, double speed, double offset, double power) {
      return Math.pow((Math.cos(x * speed + offset) + 1.0) / 2.0, power);
   }

   public double rootWave(double x, double speed, double offset, double power) {
      double baseValue = Math.cos(x * speed + offset);
      return baseValue < 0.0 ? -Math.pow(-baseValue, power) : Math.pow(baseValue, power);
   }
}
