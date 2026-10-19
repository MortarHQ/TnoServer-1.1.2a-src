package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator;

import cn.leolezury.eternalstarlight.common.block.TorreyaVinesBlock;
import cn.leolezury.eternalstarlight.common.block.TorreyaVinesPlantBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESTreeDecorators;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;

public class TorreyaVinesDecorator extends TreeDecorator {
   public static final MapCodec<TorreyaVinesDecorator> CODEC = MapCodec.unit(() -> TorreyaVinesDecorator.INSTANCE);
   public static final TorreyaVinesDecorator INSTANCE = new TorreyaVinesDecorator();

   protected TreeDecoratorType<?> type() {
      return ESTreeDecorators.TORREYA_VINES.get();
   }

   public void place(Context context) {
      RandomSource random = context.random();
      context.leaves()
         .forEach(
            pos -> {
               if (random.nextInt(8) == 0) {
                  int l = random.nextInt(6) + 5;

                  for (int i = 1; i <= l; i++) {
                     if (!context.isAir(pos.below(i))) {
                        if (i != 1) {
                           context.setBlock(pos.below(i - 1), ((TorreyaVinesBlock)ESBlocks.TORREYA_VINES.get()).defaultBlockState());
                        }
                        break;
                     }

                     context.setBlock(
                        pos.below(i),
                        i == 1
                           ? (BlockState)((TorreyaVinesPlantBlock)ESBlocks.TORREYA_VINES_PLANT.get())
                              .defaultBlockState()
                              .setValue(TorreyaVinesPlantBlock.TOP, true)
                           : ((TorreyaVinesPlantBlock)ESBlocks.TORREYA_VINES_PLANT.get()).defaultBlockState()
                     );
                     if (i == l) {
                        context.setBlock(pos.below(i), ((TorreyaVinesBlock)ESBlocks.TORREYA_VINES.get()).defaultBlockState());
                     }
                  }
               }
            }
         );
   }
}
