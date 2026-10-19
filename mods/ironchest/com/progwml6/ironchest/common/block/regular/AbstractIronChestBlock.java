package com.progwml6.ironchest.common.block.regular;

import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.regular.entity.AbstractIronChestBlockEntity;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner.BlockType;
import net.minecraft.world.level.block.DoubleBlockCombiner.Combiner;
import net.minecraft.world.level.block.DoubleBlockCombiner.NeighborCombineResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractIronChestBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final VoxelShape AABB = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   private static final Combiner<AbstractIronChestBlockEntity, Optional<Container>> CHEST_COMBINER = new Combiner<AbstractIronChestBlockEntity, Optional<Container>>() {
      public Optional<Container> acceptDouble(AbstractIronChestBlockEntity blockEntityOne, AbstractIronChestBlockEntity blockEntityTwo) {
         return Optional.empty();
      }

      public Optional<Container> acceptSingle(AbstractIronChestBlockEntity blockEntity) {
         return Optional.of(blockEntity);
      }

      public Optional<Container> acceptNone() {
         return Optional.empty();
      }
   };
   private static final Combiner<AbstractIronChestBlockEntity, Optional<MenuProvider>> MENU_PROVIDER_COMBINER = new Combiner<AbstractIronChestBlockEntity, Optional<MenuProvider>>() {
      public Optional<MenuProvider> acceptDouble(AbstractIronChestBlockEntity blockEntityOne, AbstractIronChestBlockEntity blockEntityTwo) {
         return Optional.empty();
      }

      public Optional<MenuProvider> acceptSingle(AbstractIronChestBlockEntity blockEntity) {
         return Optional.of(blockEntity);
      }

      public Optional<MenuProvider> acceptNone() {
         return Optional.empty();
      }
   };
   private final IronChestsTypes type;
   protected final Supplier<BlockEntityType<? extends AbstractIronChestBlockEntity>> blockEntityType;

   public AbstractIronChestBlock(Properties properties, Supplier<BlockEntityType<? extends AbstractIronChestBlockEntity>> blockEntityType, IronChestsTypes type) {
      super(properties);
      this.type = type;
      this.blockEntityType = blockEntityType;
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(WATERLOGGED, Boolean.FALSE)
      );
   }

   public static BlockType getBlockType(BlockState blockState) {
      return BlockType.SINGLE;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Deprecated
   public BlockState updateShape(
      BlockState blockState, Direction direction, BlockState facingState, LevelAccessor levelAccessor, BlockPos currentPos, BlockPos facingPos
   ) {
      if ((Boolean)blockState.getValue(WATERLOGGED)) {
         levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
      }

      return super.updateShape(blockState, direction, facingState, levelAccessor, currentPos, facingPos);
   }

   @Deprecated
   public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
      return AABB;
   }

   public static Direction getConnectedDirection(BlockState blockState) {
      return ((Direction)blockState.getValue(FACING)).getCounterClockWise();
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction direction = context.getHorizontalDirection().getOpposite();
      FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
      return (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, direction)).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
   }

   @Deprecated
   public FluidState getFluidState(BlockState blockState) {
      return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
   }

   @Deprecated
   public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
      if (!blockState.is(newState.getBlock())) {
         BlockEntity blockEntity = level.getBlockEntity(blockPos);
         if (blockEntity instanceof AbstractIronChestBlockEntity) {
            ((AbstractIronChestBlockEntity)blockEntity).removeAdornments();
            Containers.dropContents(level, blockPos, (AbstractIronChestBlockEntity)blockEntity);
            level.updateNeighbourForOutputSignal(blockPos, this);
         }

         super.onRemove(blockState, level, blockPos, newState, isMoving);
      }
   }

   @Deprecated
   public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
      if (pLevel.isClientSide) {
         return InteractionResult.SUCCESS;
      }

      MenuProvider menuProvider = this.getMenuProvider(pState, pLevel, pPos);
      if (menuProvider != null) {
         pPlayer.openMenu(menuProvider);
         pPlayer.awardStat(this.getOpenChestStat());
      }

      return InteractionResult.CONSUME;
   }

   protected Stat<ResourceLocation> getOpenChestStat() {
      return Stats.CUSTOM.get(Stats.OPEN_CHEST);
   }

   public BlockEntityType<? extends AbstractIronChestBlockEntity> blockEntityType() {
      return this.blockEntityType.get();
   }

   @Nullable
   public static Container getContainer(AbstractIronChestBlock chestBlock, BlockState blockState, Level level, BlockPos blockPos, boolean ignoreBlockedChest) {
      return (Container)((Optional)chestBlock.combine(blockState, level, blockPos, ignoreBlockedChest).apply(CHEST_COMBINER)).orElse(null);
   }

   public NeighborCombineResult<? extends AbstractIronChestBlockEntity> combine(
      BlockState blockState, Level level, BlockPos blockPos, boolean ignoreBlockedChest
   ) {
      BiPredicate<LevelAccessor, BlockPos> biPredicate;
      if (ignoreBlockedChest) {
         biPredicate = (levelAccessor, blockPos1) -> false;
      } else {
         biPredicate = AbstractIronChestBlock::isChestBlockedAt;
      }

      return DoubleBlockCombiner.combineWithNeigbour(
         this.blockEntityType.get(),
         AbstractIronChestBlock::getBlockType,
         AbstractIronChestBlock::getConnectedDirection,
         FACING,
         blockState,
         level,
         blockPos,
         biPredicate
      );
   }

   @Nullable
   public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
      return (MenuProvider)((Optional)this.combine(blockState, level, blockPos, false).apply(MENU_PROVIDER_COMBINER)).orElse(null);
   }

   public static Combiner<AbstractIronChestBlockEntity, Float2FloatFunction> opennessCombiner(final LidBlockEntity lidBlockEntity) {
      return new Combiner<AbstractIronChestBlockEntity, Float2FloatFunction>() {
         public Float2FloatFunction acceptDouble(AbstractIronChestBlockEntity blockEntityOne, AbstractIronChestBlockEntity blockEntityTwo) {
            return lidBlockEntityxx -> Math.max(blockEntityOne.getOpenNess(lidBlockEntityxx), blockEntityTwo.getOpenNess(lidBlockEntityxx));
         }

         public Float2FloatFunction acceptSingle(AbstractIronChestBlockEntity blockEntity) {
            return blockEntity::getOpenNess;
         }

         public Float2FloatFunction acceptNone() {
            return lidBlockEntity::getOpenNess;
         }
      };
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
      return level.isClientSide ? createTickerHelper(blockEntityType, this.blockEntityType(), AbstractIronChestBlockEntity::lidAnimateTick) : null;
   }

   public static boolean isChestBlockedAt(LevelAccessor levelAccessor, BlockPos blockPos) {
      return isBlockedChestByBlock(levelAccessor, blockPos) || isCatSittingOnChest(levelAccessor, blockPos);
   }

   private static boolean isBlockedChestByBlock(BlockGetter blockGetter, BlockPos blockPos) {
      BlockPos above = blockPos.above();
      return blockGetter.getBlockState(above).isRedstoneConductor(blockGetter, above);
   }

   private static boolean isCatSittingOnChest(LevelAccessor levelAccessor, BlockPos blockPos) {
      List<Cat> list = levelAccessor.getEntitiesOfClass(
         Cat.class, new AABB(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 2, blockPos.getZ() + 1)
      );
      if (!list.isEmpty()) {
         for (Cat cat : list) {
            if (cat.isInSittingPose()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean hasAnalogOutputSignal(BlockState blockState) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
      return AbstractContainerMenu.getRedstoneSignalFromContainer(getContainer(this, blockState, level, blockPos, false));
   }

   public BlockState rotate(BlockState blockState, Rotation rotation) {
      return (BlockState)blockState.setValue(FACING, rotation.rotate((Direction)blockState.getValue(FACING)));
   }

   public BlockState mirror(BlockState blockState, Mirror mirror) {
      return blockState.rotate(mirror.getRotation((Direction)blockState.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> blockBlockStateBuilder) {
      blockBlockStateBuilder.add(new Property[]{FACING, WATERLOGGED});
   }

   protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
      return false;
   }

   public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
      BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
      if (blockEntity instanceof AbstractIronChestBlockEntity) {
         ((AbstractIronChestBlockEntity)blockEntity).recheckOpen();
      }
   }

   @Nullable
   public static IronChestsTypes getTypeFromItem(Item itemIn) {
      return getTypeFromBlock(Block.byItem(itemIn));
   }

   @Nullable
   public static IronChestsTypes getTypeFromBlock(Block block) {
      return block instanceof AbstractIronChestBlock ? ((AbstractIronChestBlock)block).getType() : null;
   }

   public IronChestsTypes getType() {
      return this.type;
   }
}
