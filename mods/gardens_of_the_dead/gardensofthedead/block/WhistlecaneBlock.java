package gardensofthedead.block;

import gardensofthedead.network.NetworkHandler;
import gardensofthedead.network.WhistleEffectPacket;
import gardensofthedead.platform.PlatformServices;
import gardensofthedead.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WhistlecaneBlock extends Block implements BonemealableBlock {
   public static final BooleanProperty GROWING = BooleanProperty.create("growing");
   public static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   public static final double GROW_CHANCE = 0.1;
   public static final double WHISTLE_CHANCE = 0.025;
   public static final int MAX_HEIGHT = 6;

   public WhistlecaneBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(GROWING, true));
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      Vec3 offset = state.getOffset(level, pos);
      return SHAPE.move(offset.x, offset.y, offset.z);
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
      return true;
   }

   public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
      return false;
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockState stateBelow = level.getBlockState(pos.below());
      return stateBelow.is(this) || stateBelow.is(BlockTags.NYLIUM) || stateBelow.is(Blocks.SOUL_SAND) || stateBelow.is(Blocks.SOUL_SOIL);
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      int heightAbove = this.getHeightAboveUpToMax(level, pos);
      int heightBelow = this.getHeightBelowUpToMax(level, pos);
      BlockState topState = level.getBlockState(pos.above(heightAbove));
      return heightAbove + heightBelow + 1 < 6 && (Boolean)topState.getValue(GROWING);
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
      int heightAbove = this.getHeightAboveUpToMax(level, pos);
      int heightBelow = this.getHeightBelowUpToMax(level, pos);
      int height = heightAbove + heightBelow + 1;
      int extraHeight = 1 + randomSource.nextInt(2);

      for (int blocksGrown = 0; blocksGrown < extraHeight; blocksGrown++) {
         BlockPos grownPos = pos.above(heightAbove);
         BlockState grownState = level.getBlockState(grownPos);
         if (height >= 6 || !(Boolean)grownState.getValue(GROWING) || !level.isEmptyBlock(grownPos.above())) {
            return;
         }

         this.growCane(level, grownPos, randomSource, height);
         heightAbove++;
         height++;
      }
   }

   public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
      return PlatformServices.platformHelper.isSword(player.getMainHandItem()) ? 1.0F : super.getDestroyProgress(state, player, level, pos);
   }

   protected void growCane(Level level, BlockPos pos, RandomSource randomSource, int currentHeight) {
      boolean continueGrowing = currentHeight == 0 || !(randomSource.nextFloat() < 1.0F / (6 - currentHeight));
      BlockState newState = (BlockState)this.defaultBlockState().setValue(GROWING, continueGrowing);
      level.setBlock(pos.above(), newState, 3);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos abovePos = context.getClickedPos().above();
      BlockState aboveState = context.getLevel().getBlockState(abovePos);
      return (BlockState)this.defaultBlockState().setValue(GROWING, !aboveState.is(this));
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
      if (!state.canSurvive(level, pos)) {
         level.destroyBlock(pos, true);
      }
   }

   protected int getHeightAboveUpToMax(BlockGetter level, BlockPos pos) {
      int height = 0;

      while (height < 6 && level.getBlockState(pos.above(height + 1)).is(this)) {
         height++;
      }

      return height;
   }

   protected int getHeightBelowUpToMax(BlockGetter level, BlockPos pos) {
      int height = 0;

      while (height < 6 && level.getBlockState(pos.below(height + 1)).is(this)) {
         height++;
      }

      return height;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{GROWING});
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos updatedPos) {
      if (!state.canSurvive(level, pos)) {
         level.scheduleTick(pos, this, 1);
      }

      return direction == Direction.UP
         ? (BlockState)this.defaultBlockState().setValue(GROWING, !newState.is(this))
         : super.updateShape(state, direction, newState, level, pos, updatedPos);
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
      if ((Boolean)state.getValue(GROWING)) {
         int height = this.getHeightBelowUpToMax(level, pos) + 1;
         if (level.isEmptyBlock(pos.above()) && height < 6 && randomSource.nextFloat() < 0.1) {
            this.growCane(level, pos, randomSource, height);
         }
      } else if (randomSource.nextFloat() < 0.025 && level.isEmptyBlock(pos.above())) {
         this.whistle(state, level, pos, randomSource);
      }
   }

   private void whistle(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
      Vec3 offset = state.getOffset(level, pos);
      double x = pos.getX() + offset.x + 0.5;
      double z = pos.getZ() + offset.z + 0.5;
      double y = pos.getY() + offset.y + 1.0;
      float volume = 1.0F;
      float pitch = randomSource.nextFloat() * 0.3F + 0.85F;
      level.playSound(null, x, y, z, (SoundEvent)ModSoundEvents.WHISTLECANE_WHISTLE.get(), SoundSource.BLOCKS, volume, pitch);
      this.sendWhistlePacket(level, pos);
   }

   private void sendWhistlePacket(ServerLevel level, BlockPos pos) {
      NetworkHandler.sendToTrackingPlayers(level, pos, new WhistleEffectPacket(pos, level.dimension()));
   }
}
