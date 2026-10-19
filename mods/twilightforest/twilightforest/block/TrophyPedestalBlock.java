package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.TwilightForestMod;
import twilightforest.advancements.SimpleAdvancementTrigger;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFAdvancements;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;
import twilightforest.util.PlayerHelper;
import twilightforest.util.landmarks.LandmarkUtil;

public class TrophyPedestalBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final VoxelShape BOTTOM = Block.box(1.0, 0.0, 1.0, 15.0, 3.0, 15.0);
   private static final VoxelShape MID = Block.box(2.0, 3.0, 2.0, 14.0, 13.0, 14.0);
   private static final VoxelShape TOP = Block.box(1.0, 13.0, 1.0, 15.0, 16.0, 15.0);
   private static final VoxelShape CORNER1 = Block.box(1.0, 12.0, 1.0, 4.0, 13.0, 4.0);
   private static final VoxelShape CORNER2 = Block.box(12.0, 12.0, 1.0, 15.0, 13.0, 4.0);
   private static final VoxelShape CORNER3 = Block.box(1.0, 12.0, 12.0, 4.0, 13.0, 15.0);
   private static final VoxelShape CORNER4 = Block.box(12.0, 12.0, 12.0, 15.0, 13.0, 15.0);
   private static final VoxelShape FINAL = Shapes.or(BOTTOM, new VoxelShape[]{MID, TOP, CORNER1, CORNER2, CORNER3, CORNER4});

   public TrophyPedestalBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ACTIVE, false)).setValue(WATERLOGGED, false));
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      boolean flag = fluidstate.getType() == Fluids.WATER;
      return (BlockState)super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         accessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return super.updateShape(state, facing, facingState, accessor, currentPos, facingPos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ACTIVE, WATERLOGGED});
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return FINAL;
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      level.updateNeighbourForOutputSignal(pos, this);
      if (!level.isClientSide() && !(Boolean)state.getValue(ACTIVE) && this.isTrophyOnTop(level, pos)) {
         if (LandmarkUtil.isProgressionEnforced(level)) {
            if (this.areNearbyPlayersEligible(level, pos)) {
               this.doPedestalEffect(level, pos, state);
            }

            this.warnIneligiblePlayers(level, pos);
         } else {
            this.doPedestalEffect(level, pos, state);
         }

         this.rewardNearbyPlayers(level, pos);
      }
   }

   private boolean isTrophyOnTop(Level level, BlockPos pos) {
      return level.getBlockState(pos.above()).is(BlockTagGenerator.TROPHY_PEDESTAL_ACTIVATION_BLOCKS);
   }

   private void warnIneligiblePlayers(Level level, BlockPos pos) {
      for (Player player : level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(16.0))) {
         if (!this.isPlayerEligible(player)) {
            player.displayClientMessage(Component.translatable("misc.twilightforest.pedestal_ineligible"), true);
         }
      }
   }

   private boolean areNearbyPlayersEligible(Level level, BlockPos pos) {
      for (Player player : level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(16.0))) {
         if (this.isPlayerEligible(player)) {
            return true;
         }
      }

      return false;
   }

   private boolean isPlayerEligible(Player player) {
      return PlayerHelper.doesPlayerHaveRequiredAdvancements(player, TwilightForestMod.prefix("progress_lich")) || player.getAbilities().instabuild;
   }

   private void doPedestalEffect(Level level, BlockPos pos, BlockState state) {
      level.setBlockAndUpdate(pos, (BlockState)state.setValue(ACTIVE, true));
      this.removeNearbyShields(level, pos);
      level.playSound(null, pos, (SoundEvent)TFSounds.PEDESTAL_ACTIVATE.get(), SoundSource.BLOCKS, 4.0F, 0.1F);
   }

   private void rewardNearbyPlayers(Level level, BlockPos pos) {
      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(16.0))) {
         ((SimpleAdvancementTrigger)TFAdvancements.PLACED_TROPHY_ON_PEDESTAL.get()).trigger(player);
         player.awardStat((ResourceLocation)TFStats.TROPHY_PEDESTALS_ACTIVATED.get());
      }
   }

   private void removeNearbyShields(Level level, BlockPos pos) {
      for (int sx = -5; sx <= 5; sx++) {
         for (int sy = -5; sy <= 5; sy++) {
            for (int sz = -5; sz <= 5; sz++) {
               if (level.getBlockState(pos.offset(sx, sy, sz)).getBlock() == TFBlocks.STRONGHOLD_SHIELD.get()) {
                  level.destroyBlock(pos.offset(sx, sy, sz), false);
               }
            }
         }
      }
   }

   public float getDestroyProgress(BlockState state, Player player, BlockGetter getter, BlockPos pos) {
      return state.getValue(ACTIVE) ? super.getDestroyProgress(state, player, getter, pos) : -1.0F;
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
      return level.getBlockState(pos.above()).getBlock() instanceof TrophyBlock value ? value.getComparatorValue() : 0;
   }

   public PushReaction getPistonPushReaction(BlockState state) {
      return state.getValue(ACTIVE) ? PushReaction.NORMAL : PushReaction.BLOCK;
   }
}
