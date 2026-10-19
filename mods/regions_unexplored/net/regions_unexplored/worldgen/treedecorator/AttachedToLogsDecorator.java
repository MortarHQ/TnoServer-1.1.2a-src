package net.regions_unexplored.worldgen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;

public class AttachedToLogsDecorator extends TreeDecorator {
   public static final MapCodec<AttachedToLogsDecorator> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(AttachedToLogsDecorator::probability),
            BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(AttachedToLogsDecorator::blockProvider),
            ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter(AttachedToLogsDecorator::directions),
            Codec.BOOL.optionalFieldOf("check_all_directions", false).forGetter(AttachedToLogsDecorator::checkAllDirections)
         )
         .apply(i, AttachedToLogsDecorator::new)
   );
   public static final TreeDecoratorType<AttachedToLogsDecorator> TYPE = new TreeDecoratorType(CODEC);
   private final float probability;
   private final BlockStateProvider blockProvider;
   private final List<Direction> directions;
   private final boolean checkAllDirections;

   public AttachedToLogsDecorator(float probability, BlockStateProvider blockProvider, List<Direction> directions, boolean checkAllDirections) {
      this.probability = probability;
      this.blockProvider = blockProvider;
      this.directions = directions;
      this.checkAllDirections = checkAllDirections;
   }

   private float probability() {
      return this.probability;
   }

   private BlockStateProvider blockProvider() {
      return this.blockProvider;
   }

   private List<Direction> directions() {
      return this.directions;
   }

   private boolean checkAllDirections() {
      return this.checkAllDirections;
   }

   public void place(Context context) {
      RandomSource random = context.random();

      for (BlockPos logsPos : Util.shuffledCopy(context.logs(), random)) {
         for (Direction direction : this.checkAllDirections ? this.directions : List.of((Direction)Util.getRandom(this.directions, random))) {
            BlockPos placementPos = logsPos.relative(direction);
            if (random.nextFloat() <= this.probability && context.isAir(placementPos) && !context.isAir(placementPos.below())) {
               context.setBlock(placementPos, this.blockProvider.getState(random, placementPos));
            }
         }
      }
   }

   protected TreeDecoratorType<?> type() {
      return TYPE;
   }
}
