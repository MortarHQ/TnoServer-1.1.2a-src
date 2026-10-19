package com.chronodawn.worldgen.features;

import com.chronodawn.ChronoDawn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class NbtTemplateFeature extends Feature<NbtTemplateConfiguration> {
   public NbtTemplateFeature() {
      super(NbtTemplateConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<NbtTemplateConfiguration> context) {
      NbtTemplateConfiguration config = (NbtTemplateConfiguration)context.config();
      WorldGenLevel level = context.level();
      RandomSource random = context.random();
      BlockPos origin = context.origin();
      ServerLevel serverLevel = level.getLevel();
      StructureTemplateManager manager = serverLevel.getStructureManager();
      StructureTemplate template = (StructureTemplate)manager.get(config.template()).orElse(null);
      if (template == null) {
         ChronoDawn.LOGGER.warn("NbtTemplateFeature: template not found: {}", config.template());
         return false;
      }

      int surfaceY = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, origin.getX(), origin.getZ());
      BlockPos placePos = new BlockPos(origin.getX(), surfaceY + config.yOffset(), origin.getZ());
      Rotation rotation = config.randomRotate() ? Rotation.getRandom(random) : Rotation.NONE;
      StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).setIgnoreEntities(false).setRandom(random);
      Vec3i size = template.getSize();
      BlockPos pivot = new BlockPos(size.getX() / 2, 0, size.getZ() / 2);
      settings.setRotationPivot(pivot);
      if (!hasEnoughGroundContact(level, placePos, size, settings, config.minGroundContactRatio(), config.groundContactYOffset())) {
         return false;
      }

      if (config.clearReplaceableBlocks()) {
         clearReplaceableBlocks(level, placePos, size, settings);
      }

      return template.placeInWorld(level, placePos, placePos, settings, random, 2);
   }

   private static boolean hasEnoughGroundContact(
      WorldGenLevel level, BlockPos placePos, Vec3i templateSize, StructurePlaceSettings settings, double minGroundContactRatio, int groundContactYOffset
   ) {
      if (minGroundContactRatio <= 0.0) {
         return true;
      }

      int checked = 0;
      int supported = 0;

      for (int x = 0; x < templateSize.getX(); x++) {
         for (int z = 0; z < templateSize.getZ(); z++) {
            BlockPos groundPos = toWorldPos(placePos, settings, x, groundContactYOffset, z);
            checked++;
            BlockState groundState = level.getBlockState(groundPos);
            if (!groundState.canBeReplaced() && groundState.isFaceSturdy(level, groundPos, Direction.UP)) {
               supported++;
            }
         }
      }

      return checked > 0 && (double)supported / checked >= minGroundContactRatio;
   }

   private static void clearReplaceableBlocks(WorldGenLevel level, BlockPos placePos, Vec3i templateSize, StructurePlaceSettings settings) {
      for (int x = 0; x < templateSize.getX(); x++) {
         for (int z = 0; z < templateSize.getZ(); z++) {
            BlockPos pos = toWorldPos(placePos, settings, x, 0, z);
            BlockState state = level.getBlockState(pos);
            if (!state.isAir() && state.canBeReplaced() && state.getFluidState().isEmpty()) {
               level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
         }
      }
   }

   private static BlockPos toWorldPos(BlockPos placePos, StructurePlaceSettings settings, int x, int y, int z) {
      return placePos.offset(StructureTemplate.calculateRelativePosition(settings, new BlockPos(x, y, z)));
   }
}
