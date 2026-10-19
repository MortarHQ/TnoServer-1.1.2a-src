package twilightforest.world.components.feature.trees.treeplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import twilightforest.init.TFFeatureModifiers;

public class DangleFromTreeDecorator extends TreeDecorator {
   public static final MapCodec<DangleFromTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Codec.intRange(0, 32).fieldOf("attempts_minimum").forGetter(o -> o.count),
            Codec.intRange(0, 32).fieldOf("random_add_attempts").orElse(0).forGetter(o -> o.randomAddCount),
            Codec.intRange(1, 24).fieldOf("minimum_required_length").forGetter(o -> o.minimumRequiredLength),
            Codec.intRange(1, 24).fieldOf("base_length").forGetter(o -> o.baseLength),
            Codec.intRange(0, 16).fieldOf("random_add_length").orElse(0).forGetter(o -> o.randomAddLength),
            WeightedStateProvider.CODEC.fieldOf("rope_provider").forGetter(o -> o.rope),
            WeightedStateProvider.CODEC.fieldOf("baggage_provider").forGetter(o -> o.baggage)
         )
         .apply(instance, DangleFromTreeDecorator::new)
   );
   private final int count;
   private final int randomAddCount;
   private final int minimumRequiredLength;
   private final int baseLength;
   private final int randomAddLength;
   private final WeightedStateProvider rope;
   private final WeightedStateProvider baggage;

   public DangleFromTreeDecorator(
      int count, int randomAddCount, int minimumRequiredLength, int baseLength, int randomAddLength, WeightedStateProvider rope, WeightedStateProvider baggage
   ) {
      this.count = count;
      this.randomAddCount = randomAddCount;
      this.minimumRequiredLength = minimumRequiredLength;
      this.baseLength = baseLength;
      this.randomAddLength = randomAddLength;
      this.rope = rope;
      this.baggage = baggage;
   }

   protected TreeDecoratorType<DangleFromTreeDecorator> type() {
      return (TreeDecoratorType<DangleFromTreeDecorator>)TFFeatureModifiers.DANGLING_DECORATOR.get();
   }

   public void place(Context context) {
      if (!context.leaves().isEmpty()) {
         int totalTries = this.count + context.random().nextInt(this.randomAddCount + 1);
         int leafTotal = context.leaves().size();
         totalTries = Math.min(totalTries, leafTotal);

         for (int attempt = 0; attempt < totalTries; attempt++) {
            boolean clearedOfPossibleLeaves = false;
            BlockPos pos = (BlockPos)context.leaves().get(context.random().nextInt(leafTotal));
            if (pos.getX() == ((BlockPos)context.logs().get(0)).getY() && pos.getZ() == ((BlockPos)context.logs().get(0)).getZ()) {
               return;
            }

            int cordLength = this.baseLength + context.random().nextInt(this.randomAddLength + 1);

            for (int ropeUnrolling = 1; ropeUnrolling <= cordLength; ropeUnrolling++) {
               boolean isAir = context.level().isStateAtPosition(pos.below(ropeUnrolling), BlockStateBase::isAir);
               if (!clearedOfPossibleLeaves && isAir) {
                  clearedOfPossibleLeaves = true;
               }

               if (clearedOfPossibleLeaves && !isAir) {
                  cordLength = ropeUnrolling - 1;
                  break;
               }
            }

            if (cordLength > this.minimumRequiredLength) {
               BlockState rope = this.rope.getState(context.random(), pos);

               for (int ropeUnrolling = 1; ropeUnrolling < cordLength; ropeUnrolling++) {
                  pos = pos.below(1);
                  if (context.level().isStateAtPosition(pos, BlockStateBase::isAir)) {
                     context.setBlock(pos, rope);
                  }
               }

               pos = pos.below(1);
               if (context.isAir(pos)) {
                  context.setBlock(pos, this.baggage.getState(context.random(), pos));
               }
            }
         }
      }
   }
}
