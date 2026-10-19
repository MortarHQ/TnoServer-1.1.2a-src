package com.chronodawn.worldgen.decorators;

import com.chronodawn.blocks.FruitOfTimeBlock;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModTreeDecoratorTypes;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;

public class FruitDecorator extends TreeDecorator {
   public static final MapCodec<FruitDecorator> CODEC = MapCodec.unit(FruitDecorator::new);
   private static final float FRUIT_PROBABILITY = 0.05F;

   public void place(Context context) {
      RandomSource random = context.random();
      ObjectListIterator var3 = context.leaves().iterator();

      while (var3.hasNext()) {
         BlockPos leavesPos = (BlockPos)var3.next();
         if (!(random.nextFloat() >= 0.05F)) {
            BlockPos fruitPos = leavesPos.below();
            if (context.isAir(fruitPos)) {
               int age = random.nextInt(3);
               context.setBlock(fruitPos, (BlockState)((Block)ModBlocks.FRUIT_OF_TIME_BLOCK.get()).defaultBlockState().setValue(FruitOfTimeBlock.AGE, age));
            }
         }
      }
   }

   protected TreeDecoratorType<?> type() {
      return (TreeDecoratorType<?>)ModTreeDecoratorTypes.FRUIT_DECORATOR.get();
   }
}
