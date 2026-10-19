package net.regions_unexplored.worldgen.trunkplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class AspenTrunkPlacer extends RUTrunkPlacer {
   public static final MapCodec<AspenTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
      i -> heightField(i).and(Codec.floatRange(0.0F, 1.0F).fieldOf("bend_chance").forGetter(t -> t.bendChance)).apply(i, AspenTrunkPlacer::new)
   );
   public static final TrunkPlacerType<AspenTrunkPlacer> TYPE = new TrunkPlacerType(CODEC);
   protected final float bendChance;

   public AspenTrunkPlacer(IntProvider height, float bendChance) {
      super(height);
      this.bendChance = bendChance;
   }

   protected TrunkPlacerType<?> type() {
      return TYPE;
   }

   public List<FoliageAttachment> placeTrunk(
      LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, int treeHeight, BlockPos origin, TreeConfiguration config
   ) {
      List<FoliageAttachment> attachments = new ArrayList<>();
      MutableBlockPos pos = origin.mutable();

      for (int y = 0; y < treeHeight; y++) {
         if (y == treeHeight - 2 && random.nextFloat() < this.bendChance) {
            Direction bendDirection = Plane.HORIZONTAL.getRandomDirection(random);
            pos.move(bendDirection);
            if (random.nextBoolean()) {
               BlockPos branchPos = pos.immutable().relative(bendDirection.getOpposite(), 2);
               this.placeLog(level, trunkSetter, random, branchPos.below(), config, setAxis(bendDirection));
               attachments.add(attachment(branchPos));
            }
         }

         this.placeLog(level, trunkSetter, random, pos.immutable(), config);
         pos.move(Direction.UP);
      }

      attachments.add(attachment(pos.immutable()));
      return attachments;
   }
}
