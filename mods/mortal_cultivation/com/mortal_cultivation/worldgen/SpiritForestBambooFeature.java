package com.mortal_cultivation.worldgen;

import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public final class SpiritForestBambooFeature extends RandomPatchFeature {
   public SpiritForestBambooFeature() {
      super(RandomPatchConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<RandomPatchConfiguration> context) {
      return !QilinBambooGroveStructure.inClearing(context.level(), context.origin()) && super.place(context);
   }
}
