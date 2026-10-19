package com.teamresourceful.resourcefullib.common.fluid.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;

public interface FluidProperties {
   double motionScale();

   boolean canPushEntity();

   boolean canSwim();

   boolean canDrown();

   float fallDistanceModifier();

   boolean canExtinguish();

   boolean canConvertToSource();

   boolean supportsBoating();

   PathType pathType();

   PathType adjacentPathType();

   boolean canHydrate();

   int lightLevel();

   int density();

   int temperature();

   int viscosity();

   Rarity rarity();

   FluidSounds sounds();

   @Deprecated
   @ScheduledForRemoval(inVersion = "1.21.2")
   default ResourceLocation still() {
      return null;
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "1.21.2")
   default ResourceLocation flowing() {
      return null;
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "1.21.2")
   default ResourceLocation overlay() {
      return null;
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "1.21.2")
   default ResourceLocation screenOverlay() {
      return null;
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "1.21.2")
   default int tintColor() {
      return -1;
   }

   int tickDelay();

   int slopeFindDistance();

   int dropOff();

   float explosionResistance();

   boolean canPlace();

   static FluidProperties.Builder builder() {
      return new FluidProperties.Builder();
   }

   class Builder {
      private double motionScale = 0.014;
      private boolean canPushEntity = true;
      private boolean canSwim = false;
      private boolean canDrown = true;
      private float fallDistanceModifier = 0.5F;
      private boolean canExtinguish = false;
      private boolean canConvertToSource = true;
      private boolean supportsBoating = false;
      private PathType pathType = PathType.WATER;
      private PathType adjacentPathType = PathType.WATER_BORDER;
      private boolean canHydrate = true;
      private int lightLevel = 0;
      private int density = 1000;
      private int temperature = 300;
      private int viscosity = 1000;
      private Rarity rarity = Rarity.COMMON;
      private final FluidSounds sounds = new FluidSounds();
      private int tickRate = 5;
      private int slopeFindDistance = 4;
      private int dropOff = 1;
      private float explosionResistance = 100.0F;
      private boolean canPlace = true;

      private Builder() {
      }

      public FluidProperties.Builder motionScale(double motionScale) {
         this.motionScale = motionScale;
         return this;
      }

      public FluidProperties.Builder canPushEntity(boolean canPushEntity) {
         this.canPushEntity = canPushEntity;
         return this;
      }

      public FluidProperties.Builder canSwim(boolean canSwim) {
         this.canSwim = canSwim;
         return this;
      }

      public FluidProperties.Builder canDrown(boolean canDrown) {
         this.canDrown = canDrown;
         return this;
      }

      public FluidProperties.Builder fallDistanceModifier(float fallDistanceModifier) {
         this.fallDistanceModifier = fallDistanceModifier;
         return this;
      }

      public FluidProperties.Builder canExtinguish(boolean canExtinguish) {
         this.canExtinguish = canExtinguish;
         return this;
      }

      public FluidProperties.Builder canConvertToSource(boolean canConvertToSource) {
         this.canConvertToSource = canConvertToSource;
         return this;
      }

      public FluidProperties.Builder supportsBoating(boolean supportsBoating) {
         this.supportsBoating = supportsBoating;
         return this;
      }

      public FluidProperties.Builder pathType(PathType pathType) {
         this.pathType = pathType;
         return this;
      }

      public FluidProperties.Builder adjacentPathType(PathType adjacentPathType) {
         this.adjacentPathType = adjacentPathType;
         return this;
      }

      public FluidProperties.Builder canHydrate(boolean canHydrate) {
         this.canHydrate = canHydrate;
         return this;
      }

      public FluidProperties.Builder lightLevel(int lightLevel) {
         this.lightLevel = lightLevel;
         return this;
      }

      public FluidProperties.Builder density(int density) {
         this.density = density;
         return this;
      }

      public FluidProperties.Builder temperature(int temperature) {
         this.temperature = temperature;
         return this;
      }

      public FluidProperties.Builder viscosity(int viscosity) {
         this.viscosity = viscosity;
         return this;
      }

      public FluidProperties.Builder rarity(Rarity rarity) {
         this.rarity = rarity;
         return this;
      }

      public FluidProperties.Builder sounds(String sound, SoundEvent soundEvent) {
         this.sounds.add(sound, soundEvent);
         return this;
      }

      @Deprecated
      @ScheduledForRemoval(inVersion = "1.21.2")
      public FluidProperties.Builder still(ResourceLocation still) {
         return this;
      }

      @Deprecated
      @ScheduledForRemoval(inVersion = "1.21.2")
      public FluidProperties.Builder flowing(ResourceLocation flowing) {
         return this;
      }

      @Deprecated
      @ScheduledForRemoval(inVersion = "1.21.2")
      public FluidProperties.Builder overlay(ResourceLocation overlay) {
         return this;
      }

      @Deprecated
      @ScheduledForRemoval(inVersion = "1.21.2")
      public FluidProperties.Builder screenOverlay(ResourceLocation screenOverlay) {
         return this;
      }

      @Deprecated
      @ScheduledForRemoval(inVersion = "1.21.2")
      public FluidProperties.Builder tintColor(int tintColor) {
         return this;
      }

      public FluidProperties.Builder tickRate(int tickRate) {
         this.tickRate = tickRate;
         return this;
      }

      public FluidProperties.Builder slopeFindDistance(int slopeFindDistance) {
         this.slopeFindDistance = slopeFindDistance;
         return this;
      }

      public FluidProperties.Builder dropOff(int dropOff) {
         this.dropOff = dropOff;
         return this;
      }

      public FluidProperties.Builder explosionResistance(float explosionResistance) {
         this.explosionResistance = explosionResistance;
         return this;
      }

      public FluidProperties.Builder disablePlacing() {
         this.canPlace = false;
         return this;
      }

      public FluidProperties build() {
         return new ImmutableFluidProperties(
            this.motionScale,
            this.canPushEntity,
            this.canSwim,
            this.canDrown,
            this.fallDistanceModifier,
            this.canExtinguish,
            this.canConvertToSource,
            this.supportsBoating,
            this.pathType,
            this.adjacentPathType,
            this.canHydrate,
            this.lightLevel,
            this.density,
            this.temperature,
            this.viscosity,
            this.rarity,
            this.sounds,
            this.tickRate,
            this.slopeFindDistance,
            this.dropOff,
            this.explosionResistance,
            this.canPlace
         );
      }
   }
}
