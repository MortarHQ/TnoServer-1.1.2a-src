package twilightforest.world.components.feature.trees.treeplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFFeatureModifiers;

public class TrunkSideDecorator extends TreeDecorator {
   public static final MapCodec<TrunkSideDecorator> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Codec.intRange(0, 64).fieldOf("placement_count").forGetter(o -> o.count),
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability_of_placement").forGetter(o -> o.probability),
            BlockStateProvider.CODEC.fieldOf("deco_provider").forGetter(o -> o.decoration)
         )
         .apply(instance, TrunkSideDecorator::new)
   );
   private final int count;
   private final float probability;
   private final BlockStateProvider decoration;

   public TrunkSideDecorator(int count, float probability, BlockStateProvider decorator) {
      this.count = count;
      this.probability = probability;
      this.decoration = decorator;
   }

   protected TreeDecoratorType<TrunkSideDecorator> type() {
      return (TreeDecoratorType<TrunkSideDecorator>)TFFeatureModifiers.TRUNKSIDE_DECORATOR.get();
   }

   public void place(Context context) {
      int blockCount = context.logs().size();
      if (blockCount == 0) {
         TwilightForestMod.LOGGER.error("[TrunkSideDecorator] Trunk Blocks were empty! Why?");
      } else {
         for (int attempt = 0; attempt < this.count; attempt++) {
            if (!(context.random().nextFloat() >= this.probability)) {
               BlockPos logPos = (BlockPos)context.logs().get(context.random().nextInt(blockCount));
               Direction direction = Plane.HORIZONTAL.getRandomDirection(context.random());
               BlockPos newPos = logPos.offset(direction.getStepX(), 0, direction.getStepZ());
               if (context.isAir(newPos)) {
                  if (this.decoration.getState(context.random(), newPos).hasProperty(BlockStateProperties.FACING)) {
                     context.setBlock(newPos, (BlockState)this.decoration.getState(context.random(), newPos).setValue(BlockStateProperties.FACING, direction));
                  } else {
                     context.setBlock(newPos, this.decoration.getState(context.random(), newPos));
                  }
               }
            }
         }
      }
   }
}
