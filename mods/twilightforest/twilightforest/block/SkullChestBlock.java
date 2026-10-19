package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.block.entity.SkullChestBlockEntity;
import twilightforest.enums.BlockLoggingEnum;
import twilightforest.init.TFBlockEntities;

public class SkullChestBlock extends BaseEntityBlock implements BlockLoggingEnum.IMultiLoggable {
   public static final DirectionProperty FACING = TFHorizontalBlock.FACING;
   public static final MapCodec<SkullChestBlock> CODEC = simpleCodec(SkullChestBlock::new);
   private static final VoxelShape BOTTOM_X = Block.box(2.0, 0.0, 1.0, 14.0, 6.0, 15.0);
   private static final VoxelShape TOP_X = Block.box(1.0, 6.0, 0.0, 15.0, 14.0, 16.0);
   private static final VoxelShape BOTTOM_Z = Block.box(1.0, 0.0, 2.0, 15.0, 6.0, 14.0);
   private static final VoxelShape TOP_Z = Block.box(0.0, 6.0, 1.0, 16.0, 14.0, 15.0);
   private static final VoxelShape CASKET_X = Shapes.or(BOTTOM_X, TOP_X);
   private static final VoxelShape CASKET_Z = Shapes.or(BOTTOM_Z, TOP_Z);
   private static final VoxelShape SOLID = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
   private static final VoxelShape TOPPER_X = Block.box(1.0, 12.0, 0.0, 15.0, 14.0, 16.0);
   private static final VoxelShape TOPPER_Z = Block.box(0.0, 12.0, 1.0, 16.0, 14.0, 15.0);
   private static final VoxelShape SOLID_X = Shapes.or(SOLID, TOPPER_X);
   private static final VoxelShape SOLID_Z = Shapes.or(SOLID, TOPPER_Z);

   public SkullChestBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends SkullChestBlock> codec() {
      return CODEC;
   }

   public RenderShape getRenderShape(BlockState state) {
      return ((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getBlock() == Blocks.AIR ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      Direction direction = (Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING);
      if (((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getBlock() != Blocks.AIR
         && ((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getFluid() == Fluids.EMPTY) {
         return direction.getAxis() == Axis.X ? SOLID_X : SOLID_Z;
      } else {
         return direction.getAxis() == Axis.X ? CASKET_X : CASKET_Z;
      }
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new SkullChestBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TFBlockEntities.SKULL_CHEST.get(), SkullChestBlockEntity::tick);
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!state.is(newState.getBlock())) {
         BlockEntity tileentity = level.getBlockEntity(pos);
         if (tileentity instanceof Container) {
            Containers.dropContents(level, pos, (Container)tileentity);
            level.updateNeighbourForOutputSignal(pos, this);
         }

         super.onRemove(state, level, pos, newState, isMoving);
      }
   }

   public float getExplosionResistance(BlockState state, BlockGetter getter, BlockPos pos, Explosion explosion) {
      return Float.MAX_VALUE;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result
   ) {
      if (((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getBlock() != Blocks.AIR
         && ((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getFluid() == Fluids.EMPTY) {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }

      if (level.isClientSide()) {
         return ItemInteractionResult.SUCCESS;
      }

      MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
      if (menuProvider != null) {
         player.openMenu(menuProvider);
      }

      return ItemInteractionResult.sidedSuccess(level.isClientSide());
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide()
         && !player.isCreative()
         && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)
         && level.getBlockEntity(pos) instanceof SkullChestBlockEntity chest) {
         ItemStack stack = new ItemStack(this);
         ItemEntity itementity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
         this.modifyDrop(state, stack);
         if (chest.hasCustomName()) {
            if (chest.owner != null) {
               itementity.setCustomName(chest.getDisplayName());
            } else {
               itementity.setCustomName(chest.getCustomName());
            }
         }

         if (((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getFluid() == Fluids.EMPTY) {
            Block block = ((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getBlock();
            if (block != Blocks.AIR) {
               ItemStack blockstack = new ItemStack(block);
               ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), blockstack);
               item.setDefaultPickUpDelay();
               level.addFreshEntity(item);
            }
         }

         itementity.setDefaultPickUpDelay();
         level.addFreshEntity(itementity);
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   protected void modifyDrop(BlockState state, ItemStack stack) {
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      this.reactWithNeighbors(level, pos, state);
      super.neighborChanged(state, level, pos, block, fromPos, isMoving);
   }

   private void reactWithNeighbors(Level level, BlockPos pos, BlockState state) {
      if (state.getValue(BlockLoggingEnum.MULTILOGGED) == BlockLoggingEnum.LAVA) {
         boolean flag = level.getBlockState(pos.below()).is(Blocks.SOUL_SOIL);

         for (Direction direction : Direction.values()) {
            if (direction != Direction.DOWN) {
               BlockPos blockpos = pos.relative(direction);
               if (level.getFluidState(blockpos).is(FluidTags.WATER)) {
                  level.setBlockAndUpdate(pos, (BlockState)state.setValue(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.OBSIDIAN));
                  level.levelEvent(1501, pos, 0);
               }

               if (flag && level.getBlockState(blockpos).is(Blocks.BLUE_ICE)) {
                  level.setBlockAndUpdate(pos, (BlockState)state.setValue(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.BASALT));
                  level.levelEvent(1501, pos, 0);
               }
            }
         }
      } else if (state.getValue(BlockLoggingEnum.MULTILOGGED) == BlockLoggingEnum.WATER) {
         for (Direction direction : Direction.values()) {
            if (direction != Direction.DOWN) {
               BlockPos blockpos = pos.relative(direction);
               if (level.getFluidState(blockpos).is(FluidTags.LAVA)) {
                  level.setBlockAndUpdate(pos, (BlockState)state.setValue(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.STONE));
                  level.levelEvent(1501, pos, 0);
               }
            }
         }
      }
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BlockLoggingEnum.MULTILOGGED, FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)((BlockState)super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()))
         .setValue(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.getFromFluid(context.getLevel().getFluidState(context.getClickedPos()).getType()));
   }

   public FluidState getFluidState(BlockState state) {
      return ((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getFluid().defaultFluidState();
   }

   public boolean canEntityDestroy(BlockState state, BlockGetter getter, BlockPos pos, Entity entity) {
      return false;
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return (BlockState)state.setValue(FACING, mirror.mirror((Direction)state.getValue(FACING)));
   }
}
