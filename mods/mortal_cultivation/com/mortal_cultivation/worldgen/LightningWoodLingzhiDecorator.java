package com.mortal_cultivation.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.block.LightningWoodLingzhiBlock;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;

public final class LightningWoodLingzhiDecorator extends TreeDecorator {
   public static final MapCodec<LightningWoodLingzhiDecorator> CODEC = Codec.floatRange(0.0F, 1.0F)
      .fieldOf("probability")
      .xmap(LightningWoodLingzhiDecorator::new, decorator -> decorator.probability);
   private final float probability;

   public LightningWoodLingzhiDecorator(float probability) {
      this.probability = probability;
   }

   protected TreeDecoratorType<?> type() {
      return (TreeDecoratorType<?>)WorldgenRegistry.LIGHTNING_WOOD_LINGZHI_DECORATOR.get();
   }

   public void place(Context context) {
      RandomSource random = context.random();
      if (!context.logs().isEmpty() && !(random.nextFloat() >= this.probability)) {
         BlockPos selected = null;
         Direction selectedFacing = Direction.NORTH;
         int candidates = 0;
         ObjectListIterator var6 = context.logs().iterator();

         while (var6.hasNext()) {
            BlockPos log = (BlockPos)var6.next();
            if (context.level().isStateAtPosition(log, state -> state.is((Block)BlockRegistry.SPIRIT_LOG.get()))) {
               for (Direction direction : Plane.HORIZONTAL) {
                  BlockPos side = log.relative(direction);
                  if (context.isAir(side) && isExposed(context, side, direction)) {
                     if (random.nextInt(++candidates) == 0) {
                        selected = side;
                        selectedFacing = direction;
                     }
                  }
               }
            }
         }

         if (selected != null) {
            context.setBlock(
               selected,
               (BlockState)((LightningWoodLingzhiBlock)BlockRegistry.LIGHTNING_WOOD_LINGZHI.get())
                  .defaultBlockState()
                  .setValue(LightningWoodLingzhiBlock.FACING, selectedFacing)
            );
         }
      }
   }

   private static boolean isExposed(Context context, BlockPos pos, Direction facing) {
      for (Direction direction : Plane.HORIZONTAL) {
         if (direction != facing.getOpposite() && context.isAir(pos.relative(direction))) {
            return true;
         }
      }

      return false;
   }
}
