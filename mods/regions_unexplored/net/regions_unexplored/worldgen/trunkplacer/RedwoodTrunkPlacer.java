package net.regions_unexplored.worldgen.trunkplacer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.regions_unexplored.registry.tag.RUBlockTags;

public class RedwoodTrunkPlacer extends RUTrunkPlacer {
   public static final MapCodec<RedwoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
      i -> heightField(i)
         .and(
            i.group(
               IntProvider.NON_NEGATIVE_CODEC.listOf().fieldOf("branch_counts").forGetter(p -> p.branchCounts),
               ExtraCodecs.POSITIVE_INT.fieldOf("branch_offset").forGetter(p -> p.branchOffset),
               IntProvider.NON_NEGATIVE_CODEC.listOf(0, 4).fieldOf("base_trunk_heights").forGetter(p -> p.baseTrunkHeights)
            )
         )
         .apply(i, RedwoodTrunkPlacer::new)
   );
   public static final TrunkPlacerType<RedwoodTrunkPlacer> TYPE = new TrunkPlacerType(CODEC);
   protected final List<IntProvider> branchCounts;
   protected final int branchOffset;
   protected final List<IntProvider> baseTrunkHeights;

   public RedwoodTrunkPlacer(IntProvider height, List<IntProvider> branchCounts, int branchOffset, List<IntProvider> baseTrunkHeights) {
      super(height);
      this.branchCounts = branchCounts;
      this.branchOffset = branchOffset;
      this.baseTrunkHeights = baseTrunkHeights;
   }

   protected TrunkPlacerType<?> type() {
      return TYPE;
   }

   public List<FoliageAttachment> placeTrunk(
      LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, int treeHeight, BlockPos origin, TreeConfiguration config
   ) {
      List<FoliageAttachment> attachments = new ArrayList<>();
      this.placeLogColumn(level, trunkSetter, random, origin, treeHeight, config);

      for (Direction direction : Plane.HORIZONTAL) {
         this.placeLogColumn(level, trunkSetter, random, origin.relative(direction, 1), this.getTrunkHeight(random, 0), config);
         this.placeLogColumn(level, trunkSetter, random, origin.relative(direction).relative(direction.getClockWise()), this.getTrunkHeight(random, 1), config);
         this.placeLogColumn(level, trunkSetter, random, origin.relative(direction, 2), this.getTrunkHeight(random, 2), config);
         this.placeLogColumn(
            level, trunkSetter, random, origin.relative(direction, 2).relative(direction.getClockWise()), this.getTrunkHeight(random, 3), config
         );
         this.placeLogColumn(
            level, trunkSetter, random, origin.relative(direction).relative(direction.getClockWise(), 2), this.getTrunkHeight(random, 3), config
         );
      }

      MutableBlockPos placePos = origin.mutable().move(Direction.UP, treeHeight);
      attachments.add(attachment(placePos.immutable()));
      int length = 1;

      for (IntProvider branchLength : this.branchCounts) {
         for (int i = 0; i < branchLength.sample(random); i++) {
            placePos.move(Direction.DOWN, this.branchOffset);
            this.placeBranch(level, trunkSetter, random, length, placePos, config, attachments);
            if (length > 2) {
               this.placeBlobsAround(attachments, placePos);
            }
         }

         length++;
      }

      return attachments;
   }

   private int getTrunkHeight(RandomSource random, int index) {
      return this.baseTrunkHeights.size() <= index ? 0 : this.baseTrunkHeights.get(index).sample(random);
   }

   private void placeBlobsAround(List<FoliageAttachment> foliageSetter, BlockPos pos) {
      for (Direction direction : Plane.HORIZONTAL) {
         foliageSetter.add(attachment(pos.relative(direction).relative(direction.getClockWise())));
      }
   }

   private void placeBranch(
      LevelSimulatedReader level,
      BiConsumer<BlockPos, BlockState> trunkSetter,
      RandomSource random,
      int length,
      BlockPos pos,
      TreeConfiguration config,
      List<FoliageAttachment> foliageSetter
   ) {
      Axis axis = Plane.HORIZONTAL.getRandomAxis(random);

      for (Direction direction : Plane.HORIZONTAL) {
         MutableBlockPos currentPos = pos.mutable();
         if (axis != direction.getAxis()) {
            currentPos.move(Direction.DOWN);
         }

         for (int i = 0; i < length; i++) {
            currentPos.move(direction);
            this.placeLog(level, trunkSetter, random, currentPos.immutable(), config, setAxis(direction));
         }

         foliageSetter.add(attachment(currentPos.immutable()));
      }
   }

   private void placeLogColumn(
      LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, BlockPos pos, int columnHeight, TreeConfiguration config
   ) {
      if (columnHeight != 0) {
         RUTrunkPlacer.placeDirtBelow(level, trunkSetter, pos);
         MutableBlockPos currentPos = pos.mutable();

         for (int y = 0; y < columnHeight; y++) {
            if (!this.placeLog(level, trunkSetter, random, currentPos.immutable(), config)) {
               return;
            }

            currentPos.move(Direction.UP);
         }
      }
   }

   protected boolean validTreePos(LevelSimulatedReader level, BlockPos pos) {
      return TreeFeature.validTreePos(level, pos) || level.isStateAtPosition(pos, state -> state.is(BlockTags.LOGS) || state.is(RUBlockTags.BRANCHES));
   }
}
