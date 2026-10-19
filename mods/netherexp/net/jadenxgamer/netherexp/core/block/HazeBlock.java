package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class HazeBlock extends TransparentBlock {
   public HazeBlock(Properties properties) {
      super(properties);
   }

   protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
      return true;
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      level.levelEvent(2001, pos, getId(this.defaultBlockState()));
      level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
   }

   @NotNull
   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return context instanceof EntityCollisionContext entityContext && entityContext.getEntity() instanceof Player
         ? super.getCollisionShape(state, level, pos, context)
         : Shapes.empty();
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      level.scheduleTick(pos, this, (Integer)JNEConfigs.HAZE_BLOCK_COOLDOWN.get() * 20);
      super.onPlace(state, level, pos, oldState, movedByPiston);
   }

   @NotNull
   public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }
}
