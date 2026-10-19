package net.jadenxgamer.netherexp.core.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class JNEPathBlock extends DirtPathBlock {
   private final Supplier<Block> turnsTo;

   public JNEPathBlock(Supplier<Block> turnsTo, Properties properties) {
      super(properties);
      this.turnsTo = turnsTo;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos())
         ? Block.pushEntitiesUp(this.defaultBlockState(), this.turnsTo.get().defaultBlockState(), context.getLevel(), context.getClickedPos())
         : super.getStateForPlacement(context);
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      BlockState newState = pushEntitiesUp(state, this.turnsTo.get().defaultBlockState(), level, pos);
      level.setBlockAndUpdate(pos, newState);
   }
}
