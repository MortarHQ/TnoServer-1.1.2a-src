package net.regions_unexplored.worldgen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.registry.tag.RUBlockTags;

public class WillowTrunkDecorator extends TreeDecorator {
   public static final MapCodec<WillowTrunkDecorator> CODEC = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("chance")
      .xmap(WillowTrunkDecorator::new, WillowTrunkDecorator::chance);
   public static TreeDecoratorType<WillowTrunkDecorator> TYPE = new TreeDecoratorType(CODEC);
   private final float chance;

   public WillowTrunkDecorator(float chance) {
      this.chance = chance;
   }

   public static WillowTrunkDecorator of(float chance) {
      return new WillowTrunkDecorator(chance);
   }

   public float chance() {
      return this.chance;
   }

   protected TreeDecoratorType<?> type() {
      return TYPE;
   }

   public void place(Context context) {
      if (!(context.random().nextFloat() > this.chance)) {
         BlockPos newpos = (BlockPos)context.logs().getFirst();
         if (context.level().isStateAtPosition(newpos, WillowTrunkDecorator::isGrass)) {
            newpos = newpos.above();
         }

         this.placeRoot(context, newpos.north());
         this.placeRoot(context, newpos.east());
         this.placeRoot(context, newpos.south());
         this.placeRoot(context, newpos.west());
         this.placeLog(context, newpos.north().above());
         this.placeLog(context, newpos.east().above());
         this.placeLog(context, newpos.south().above());
         this.placeLog(context, newpos.west().above());
         this.placeLog(context, newpos.north().above(2));
         this.placeLog(context, newpos.east().above(2));
         this.placeLog(context, newpos.south().above(2));
         this.placeLog(context, newpos.west().above(2));
      }
   }

   public void placeRoot(Context context, BlockPos pos) {
      LevelSimulatedReader level = context.level();
      Random random = new Random();
      int rd = random.nextInt(2) + 4;
      int i = 0;
      MutableBlockPos placePos = pos.mutable();

      while (i <= rd) {
         if (isReplaceable(level, placePos) && level.isStateAtPosition(placePos.above(), WillowTrunkDecorator::isDirt)) {
            context.setBlock(placePos, Blocks.HANGING_ROOTS.defaultBlockState());
            break;
         }

         this.placeLog(context, placePos);
         placePos.move(Direction.DOWN);
         i++;
      }
   }

   public void placeLog(Context context, BlockPos pos) {
      LevelSimulatedReader level = context.level();
      if (level.isStateAtPosition(pos, WillowTrunkDecorator::isForestGrass)) {
         context.setBlock(pos, RUBlocks.PEAT_DIRT.get().defaultBlockState());
      } else if (level.isStateAtPosition(pos, WillowTrunkDecorator::isPlainsGrass)) {
         context.setBlock(pos, RUBlocks.SILT_DIRT.get().defaultBlockState());
      } else if (level.isStateAtPosition(pos, WillowTrunkDecorator::isGrass)) {
         context.setBlock(pos, Blocks.DIRT.defaultBlockState());
      } else {
         if (!isReplaceable(level, pos)) {
            return;
         }

         context.setBlock(pos, RUBlocks.WILLOW_WOOD_SET.getLog().defaultBlockState());
      }

      if (level.isStateAtPosition(pos, WillowTrunkDecorator::isForestGrass)) {
         context.setBlock(pos.below(), RUBlocks.PEAT_DIRT.get().defaultBlockState());
      } else if (level.isStateAtPosition(pos, WillowTrunkDecorator::isPlainsGrass)) {
         context.setBlock(pos.below(), RUBlocks.SILT_DIRT.get().defaultBlockState());
      } else if (level.isStateAtPosition(pos, WillowTrunkDecorator::isGrass)) {
         context.setBlock(pos.below(), Blocks.DIRT.defaultBlockState());
      }
   }

   public static boolean isGrass(BlockState state) {
      return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.MYCELIUM) || state.is(Blocks.DIRT) || state.is(RUBlocks.ALPHA_GRASS_BLOCK.get());
   }

   public static boolean isForestGrass(BlockState state) {
      return state.is(RUBlocks.PEAT_GRASS_BLOCK.get()) || state.is(RUBlocks.PEAT_DIRT.get());
   }

   public static boolean isPlainsGrass(BlockState state) {
      return state.is(RUBlocks.SILT_GRASS_BLOCK.get()) || state.is(RUBlocks.SILT_DIRT.get());
   }

   public static boolean isDirt(BlockState state) {
      return state.is(BlockTags.DIRT);
   }

   public static boolean isAllGrass(BlockState state) {
      return state.is(Blocks.GRASS_BLOCK)
         || state.is(Blocks.MYCELIUM)
         || state.is(Blocks.DIRT)
         || state.is(RUBlocks.PEAT_GRASS_BLOCK.get())
         || state.is(RUBlocks.SILT_GRASS_BLOCK.get())
         || state.is(RUBlocks.ALPHA_GRASS_BLOCK.get())
         || state.is(RUBlocks.PEAT_DIRT.get())
         || state.is(RUBlocks.SILT_DIRT.get());
   }

   public static boolean isReplaceableBlock(BlockState state) {
      return state.is(RUBlockTags.REPLACEABLE_BLOCKS);
   }

   public static boolean isReplaceable(LevelSimulatedReader reader, BlockPos pos) {
      return reader.isStateAtPosition(pos, WillowTrunkDecorator::isReplaceableBlock);
   }
}
