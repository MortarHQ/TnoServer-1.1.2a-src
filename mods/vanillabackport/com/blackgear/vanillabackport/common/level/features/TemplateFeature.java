package com.blackgear.vanillabackport.common.level.features;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class TemplateFeature extends Feature<TemplateFeatureConfiguration> {
   public TemplateFeature(Codec<TemplateFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<TemplateFeatureConfiguration> context) {
      RandomSource random = context.random();
      WorldGenLevel level = context.level();
      TemplateFeatureConfiguration config = (TemplateFeatureConfiguration)context.config();
      TemplateFeatureConfiguration.TemplateEntry entry = (TemplateFeatureConfiguration.TemplateEntry)config.templates().getRandomValue(random).orElseThrow();
      Rotation rotation = (Rotation)Util.getRandom(entry.rotations(), random);
      StructureTemplateManager manager = level.getLevel().getStructureManager();
      StructureTemplate template = manager.getOrCreate(entry.template());
      Vec3i offsetX = this.getRotatedOffset(rotation, Axis.X, template);
      Vec3i offsetZ = this.getRotatedOffset(rotation, Axis.Z, template);
      BlockPos pos = context.origin().offset(offsetX).offset(offsetZ);
      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setRandom(random);
      return template.placeInWorld(level, pos, pos, settings, random, 3);
   }

   private Vec3i getRotatedOffset(Rotation rotation, Axis axis, StructureTemplate template) {
      return rotation.rotate(this.getNegative(axis)).getNormal().multiply(template.getSize().get(axis) / 2);
   }

   private Direction getNegative(Axis axis) {
      return switch (axis) {
         case X -> Direction.WEST;
         case Y -> Direction.DOWN;
         case Z -> Direction.NORTH;
         default -> throw new MatchException(null, null);
      };
   }
}
