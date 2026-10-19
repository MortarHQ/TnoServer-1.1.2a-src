package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.block.CrystalfleurVineBlock;
import cn.leolezury.eternalstarlight.common.block.DesertFlowerBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.MergedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.RotatedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.SpikeProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.ValueMapGenerator;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

public class StarlightCrystalFeature extends ESFeature<NoneFeatureConfiguration> {
   public StarlightCrystalFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      RandomSource random = context.random();
      boolean isRed = random.nextBoolean();
      BlockState crystalState = isRed
         ? ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState()
         : ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState();
      BlockState carpetState = isRed
         ? ((CarpetBlock)ESBlocks.RED_CRYSTAL_MOSS_CARPET.get()).defaultBlockState()
         : ((CarpetBlock)ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get()).defaultBlockState();
      BlockState clusterState = isRed
         ? ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState()
         : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState();
      BlockState bloomingClusterState = isRed
         ? ESBlocks.BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState()
         : ESBlocks.BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState();
      BlockState flowerState = isRed
         ? ((DesertFlowerBlock)ESBlocks.RED_CRYSTALFLEUR.get()).defaultBlockState()
         : ((DesertFlowerBlock)ESBlocks.BLUE_CRYSTALFLEUR.get()).defaultBlockState();
      BlockState vineState = isRed
         ? ((CrystalfleurVineBlock)ESBlocks.RED_CRYSTALFLEUR_VINE.get()).defaultBlockState()
         : ((CrystalfleurVineBlock)ESBlocks.BLUE_CRYSTALFLEUR_VINE.get()).defaultBlockState();
      List<MergedProvider.Entry> entries = new ArrayList<>();
      int count = random.nextInt(3, 7);
      if (count == 4 && random.nextBoolean()) {
         count = 3;
      }

      for (int i = 0; i < count; i++) {
         entries.add(
            new MergedProvider.Entry(
               new RotatedProvider(new SpikeProvider(1.5F + random.nextFloat(), 10.0F + random.nextFloat() * 3.0F), random.nextInt(40, 60), 360.0F / count * i),
               Vec3.ZERO
            )
         );
      }

      entries.add(new MergedProvider.Entry(new SpikeProvider(2.0F + random.nextFloat(), 16.0F + random.nextFloat() * 5.0F), Vec3.ZERO));
      ValueMapGenerator.place(new MergedProvider(entries), (pos, value) -> this.setBlockIfEmpty(level, pos.offset(origin), crystalState));

      for (int x = -5; x <= 5; x++) {
         for (int y = -4; y <= 4; y++) {
            for (int z = -5; z <= 5; z++) {
               if (x * x + z * z < 25) {
                  BlockPos placePos = origin.offset(x, y, z);
                  if (!random.nextBoolean()) {
                     if (level.getBlockState(placePos.below()).is(ESTags.Blocks.BASE_STONE_STARLIGHT)) {
                        this.setBlockIfEmpty(level, placePos, carpetState);
                     }
                  } else if (random.nextInt(8) == 0) {
                     List<Direction> possibleDirs = new ArrayList<>();

                     for (Direction direction : Direction.values()) {
                        BlockPos relativePos = placePos.relative(direction);
                        if (level.getBlockState(relativePos).is(crystalState.getBlock())) {
                           possibleDirs.add(direction);
                        }
                     }

                     if (!possibleDirs.isEmpty()) {
                        if (random.nextInt(6) == 0) {
                           for (Direction direction : Direction.values()) {
                              if (possibleDirs.contains(direction)) {
                                 vineState = (BlockState)vineState.setValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(direction), random.nextBoolean());
                              } else {
                                 vineState = (BlockState)vineState.setValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(direction), false);
                              }
                           }

                           BlockState finalVineState = vineState;
                           if (Arrays.stream(Direction.values())
                              .anyMatch(directionx -> (Boolean)finalVineState.getValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(directionx)))) {
                              this.setBlockIfEmpty(level, placePos, finalVineState);
                           }
                        } else {
                           Direction direction = possibleDirs.get(random.nextInt(possibleDirs.size())).getOpposite();
                           this.setBlockIfEmpty(
                              level,
                              placePos,
                              (BlockState)(random.nextInt(3) == 0 ? bloomingClusterState : clusterState).setValue(BlockStateProperties.FACING, direction)
                           );
                        }
                     }

                     BlockPos belowPos = placePos.below();
                     if (level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP)) {
                        if (random.nextInt(12) == 0 && flowerState.canSurvive(level, placePos)) {
                           this.setBlockIfEmpty(
                              level,
                              placePos,
                              random.nextBoolean()
                                 ? ((DesertFlowerBlock)ESBlocks.RED_CRYSTALFLEUR.get()).defaultBlockState()
                                 : ((DesertFlowerBlock)ESBlocks.BLUE_CRYSTALFLEUR.get()).defaultBlockState()
                           );
                        } else {
                           this.setBlockIfEmpty(
                              level,
                              placePos,
                              random.nextBoolean()
                                 ? (
                                    random.nextInt(3) == 0
                                       ? ESBlocks.BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState()
                                       : ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState()
                                 )
                                 : (
                                    random.nextInt(3) == 0
                                       ? ESBlocks.BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState()
                                       : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState()
                                 )
                           );
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
