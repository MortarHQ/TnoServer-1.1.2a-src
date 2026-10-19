package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import cn.leolezury.eternalstarlight.common.block.HangingAlgaleavesBlock;
import cn.leolezury.eternalstarlight.common.block.HangingAlgaleavesPlantBlock;
import cn.leolezury.eternalstarlight.common.block.JinglestemLogBlock;
import cn.leolezury.eternalstarlight.common.block.SimpleMultifaceBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.phys.Vec3;

public class JinglestemFeature extends Feature<JinglestemFeature.Configuration> {
   private static final List<Direction> ATTACHMENT_FACES = List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

   public JinglestemFeature(Codec<JinglestemFeature.Configuration> codec) {
      super(codec);
   }

   private void placeBlockLine(BlockPos from, BlockPos to, Consumer<BlockPos> placer) {
      for (int[] point : ESMathUtil.getBresenham3DPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ())) {
         BlockPos trunkPos = new BlockPos(point[0], point[1], point[2]);
         placer.accept(trunkPos);
      }
   }

   private void placeBranches(BlockPos pos, int num, int len, Consumer<BlockPos> placer) {
      for (int i = 0; i < num; i++) {
         Vec3 endVec = ESMathUtil.rotationToPosition(pos.getCenter(), len, 40.0F, 360.0F / num * i);
         BlockPos endPos = new BlockPos((int)endVec.x, (int)endVec.y, (int)endVec.z);
         this.placeBlockLine(pos, endPos, placer);
      }
   }

   public boolean place(FeaturePlaceContext<JinglestemFeature.Configuration> context) {
      WorldGenLevel level = context.level();
      BlockPos pos = context.origin();
      RandomSource random = context.random();
      List<BlockPos> trunkPositions = new ArrayList<>();
      JinglestemFeature.Configuration config = (JinglestemFeature.Configuration)context.config();
      BlockState attachmentState = (BlockState)((SimpleMultifaceBlock)ESBlocks.ALGALEAVES.get())
         .defaultBlockState()
         .setValue(BlockStateProperties.WATERLOGGED, true);
      int height = config.trunkHeight().sample(random);
      BlockPos topPos = pos.offset(random.nextInt(5) - 2, height, random.nextInt(5) - 2);
      this.placeBlockLine(pos, topPos, trunkPositions::add);
      int num = config.branchAmount().sample(random);
      int len = config.branchLength().sample(random);
      this.placeBranches(topPos, num, len, trunkPositions::add);

      for (BlockPos blockPos : trunkPositions) {
         if (!level.getBlockState(blockPos).is(Blocks.WATER)) {
            return false;
         }
      }

      for (BlockPos blockPos : trunkPositions) {
         this.setBlock(level, blockPos, ((JinglestemLogBlock)ESBlocks.JINGLESTEM_LOG.get()).defaultBlockState());
      }

      if (config.hasLeaves()) {
         for (BlockPos blockPos : trunkPositions) {
            if (random.nextInt(10) != 0) {
               int l = config.leavesHeight().sample(random);

               for (int i = 1; i <= l; i++) {
                  if (!level.getBlockState(blockPos.below(i)).is(Blocks.WATER)) {
                     if (i != 1) {
                        this.setBlock(level, blockPos.below(i - 1), ((HangingAlgaleavesBlock)ESBlocks.HANGING_ALGALEAVES.get()).defaultBlockState());
                     }
                     break;
                  }

                  this.setBlock(level, blockPos.below(i), ((HangingAlgaleavesPlantBlock)ESBlocks.HANGING_ALGALEAVES_PLANT.get()).defaultBlockState());
                  if (i == l) {
                     this.setBlock(level, blockPos.below(i), ((HangingAlgaleavesBlock)ESBlocks.HANGING_ALGALEAVES.get()).defaultBlockState());
                  }
               }
            }

            float chance = 1.25F * (blockPos.getY() - pos.getY()) / height;
            if (random.nextFloat() < chance) {
               for (Direction attachDir : ATTACHMENT_FACES) {
                  BlockPos placePos = blockPos.relative(attachDir);
                  if (level.getBlockState(placePos).is(Blocks.WATER)) {
                     List<Direction> possibleDirs = new ArrayList<>();

                     for (Direction direction : Direction.values()) {
                        BlockPos relativePos = placePos.relative(direction);
                        if (level.getBlockState(relativePos).is((Block)ESBlocks.JINGLESTEM_LOG.get())) {
                           possibleDirs.add(direction);
                        }
                     }

                     for (Direction direction : Direction.values()) {
                        if (possibleDirs.contains(direction)) {
                           attachmentState = (BlockState)attachmentState.setValue(
                              (Property)PipeBlock.PROPERTY_BY_DIRECTION.get(direction), random.nextBoolean()
                           );
                        } else {
                           attachmentState = (BlockState)attachmentState.setValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(direction), false);
                        }
                     }

                     BlockState finalAttachmentState = attachmentState;
                     if (Arrays.stream(Direction.values())
                        .anyMatch(directionx -> (Boolean)finalAttachmentState.getValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(directionx)))) {
                        this.setBlock(level, placePos, finalAttachmentState);
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   public record Configuration(IntProvider trunkHeight, IntProvider branchAmount, IntProvider branchLength, IntProvider leavesHeight, boolean hasLeaves)
      implements FeatureConfiguration {
      public static final Codec<JinglestemFeature.Configuration> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               IntProvider.CODEC.fieldOf("trunk_height").forGetter(JinglestemFeature.Configuration::trunkHeight),
               IntProvider.CODEC.fieldOf("branch_amount").forGetter(JinglestemFeature.Configuration::branchAmount),
               IntProvider.CODEC.fieldOf("branch_length").forGetter(JinglestemFeature.Configuration::branchLength),
               IntProvider.CODEC.fieldOf("leaves_height").forGetter(JinglestemFeature.Configuration::leavesHeight),
               Codec.BOOL.fieldOf("has_leaves").forGetter(JinglestemFeature.Configuration::hasLeaves)
            )
            .apply(instance, JinglestemFeature.Configuration::new)
      );
   }
}
