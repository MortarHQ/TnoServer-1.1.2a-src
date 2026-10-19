package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import twilightforest.init.TFBlocks;
import twilightforest.util.landmarks.LandmarkUtil;

public class UndergroundPlantFeature extends Feature<BlockStateConfiguration> {
   int maxCount;
   boolean spawnInStructure;

   public UndergroundPlantFeature(Codec<BlockStateConfiguration> config, int maxCount) {
      super(config);
      this.maxCount = maxCount;
      this.spawnInStructure = false;
   }

   public UndergroundPlantFeature(Codec<BlockStateConfiguration> config) {
      super(config);
      this.maxCount = Integer.MAX_VALUE;
      this.spawnInStructure = false;
   }

   public UndergroundPlantFeature(Codec<BlockStateConfiguration> config, int maxCount, boolean spawnInStructure) {
      super(config);
      this.maxCount = maxCount;
      this.spawnInStructure = spawnInStructure;
   }

   public UndergroundPlantFeature(Codec<BlockStateConfiguration> config, boolean spawnInStructure) {
      super(config);
      this.maxCount = Integer.MAX_VALUE;
      this.spawnInStructure = spawnInStructure;
   }

   public boolean place(FeaturePlaceContext<BlockStateConfiguration> ctx) {
      WorldGenLevel world = ctx.level();
      BlockPos origin = ctx.origin();
      RandomSource random = ctx.random();
      int x = origin.getX();
      int z = origin.getZ();
      int placed = 0;

      for (int y = origin.getY(); y > world.getMinBuildHeight() && placed < this.maxCount; y--) {
         BlockPos pos = new BlockPos(x, y, z);
         if (world.isEmptyBlock(pos) && random.nextInt(6) != 0) {
            BlockState state = ((BlockStateConfiguration)ctx.config()).state;
            Optional<StructureStart> structureStart = LandmarkUtil.locateNearestLandmarkStart(
               world, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ())
            );
            if (state.is(TFBlocks.TROLLVIDR) && random.nextInt(10) == 0) {
               state = ((Block)TFBlocks.UNRIPE_TROLLBER.get()).defaultBlockState();
            }

            if (state.canSurvive(world, pos) && (this.spawnInStructure || structureStart.isEmpty() || !structureStart.get().getBoundingBox().isInside(pos))) {
               world.setBlock(pos, state, 18);
               placed++;
            }
         } else {
            x = origin.getX() + random.nextInt(4) - random.nextInt(4);
            z = origin.getZ() + random.nextInt(4) - random.nextInt(4);
         }
      }

      return placed > 0;
   }
}
