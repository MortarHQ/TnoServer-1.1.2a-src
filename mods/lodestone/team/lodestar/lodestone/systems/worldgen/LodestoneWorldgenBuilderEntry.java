package team.lodestar.lodestone.systems.worldgen;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

public class LodestoneWorldgenBuilderEntry {
   protected BlockPos pos;
   protected BlockState state;
   protected PlacementCondition placementCondition;
   protected AdditionalPlacement additionalPlacement;
   protected boolean important;

   public LodestoneWorldgenBuilderEntry(BlockPos pos, BlockState state) {
      this.pos = pos;
      this.state = state;
   }

   public BlockPos position() {
      return this.pos;
   }

   public BlockState blockState() {
      return this.state;
   }

   public LodestoneWorldgenBuilderEntry changePos(Function<BlockPos, BlockPos> function) {
      return this.changePos(function.apply(this.pos));
   }

   public LodestoneWorldgenBuilderEntry changePos(BlockPos pos) {
      this.pos = pos;
      return this;
   }

   public LodestoneWorldgenBuilderEntry changeState(Function<BlockState, BlockState> function) {
      return this.changeState(function.apply(this.state));
   }

   public LodestoneWorldgenBuilderEntry changeState(BlockState state) {
      this.state = state;
      return this;
   }

   public LodestoneWorldgenBuilderEntry addPlacementCondition(PlacementCondition placementCondition) {
      this.placementCondition = placementCondition;
      return this;
   }

   public LodestoneWorldgenBuilderEntry addAdditionalPlacement(AdditionalPlacement additionalPlacement) {
      this.additionalPlacement = additionalPlacement;
      return this;
   }

   public LodestoneWorldgenBuilderEntry setImportant() {
      this.important = true;
      return this;
   }

   public boolean isImportant() {
      return this.important;
   }

   public boolean hasPlacementCondition() {
      return this.placementCondition != null;
   }

   public boolean hasAdditionalPlacement() {
      return this.additionalPlacement != null;
   }

   public boolean canPlace(WorldGenLevel level) {
      return this.placementCondition.canPlace(level, this);
   }

   public boolean tryPlace(WorldGenLevel level) {
      if (this.canPlace(level)) {
         this.place(level);
         this.additionalPlacement.place(level, this);
         return true;
      } else {
         return false;
      }
   }

   public void place(WorldGenLevel level) {
      this.place(level, this.position(), this.blockState());
   }

   public void place(WorldGenLevel level, BlockPos pos, BlockState state) {
      level.setBlock(pos, state, 19);
      if (level instanceof Level realLevel) {
         BlockStateHelper.updateState(realLevel, pos);
      }
   }
}
