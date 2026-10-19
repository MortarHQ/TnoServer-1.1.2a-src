package net.blay09.mods.waystones.block;

import java.util.List;
import java.util.UUID;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.MutableWaystone;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneOrigin;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.blay09.mods.waystones.component.ModComponents;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneImpl;
import net.blay09.mods.waystones.core.WaystoneManagerImpl;
import net.blay09.mods.waystones.core.WaystoneProxy;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.blay09.mods.waystones.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class WaystoneBlockBase extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final EnumProperty<WaystoneOrigin> ORIGIN = EnumProperty.create("origin", WaystoneOrigin.class);

   public WaystoneBlockBase(Properties properties) {
      super(properties.pushReaction(PushReaction.BLOCK));
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(WATERLOGGED, false)).setValue(ORIGIN, WaystoneOrigin.UNKNOWN)
      );
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState directionState, LevelAccessor world, BlockPos pos, BlockPos directionPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
      }

      if (!this.isDoubleBlock(state)) {
         return state;
      }

      DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(HALF);
      return direction.getAxis() == Axis.Y
               && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)
               && (directionState.getBlock() != this || directionState.getValue(HALF) == half)
            || half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(world, pos)
         ? Blocks.AIR.defaultBlockState()
         : state;
   }

   public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
      if (this.isDoubleBlock(state)) {
         super.playerDestroy(world, player, pos, Blocks.AIR.defaultBlockState(), blockEntity, stack);
      } else {
         super.playerDestroy(world, player, pos, state, blockEntity, stack);
      }
   }

   private boolean isDoubleBlock(BlockState state) {
      return state.hasProperty(HALF);
   }

   protected boolean canSilkTouch() {
      return false;
   }

   public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      boolean isDoubleBlock = this.isDoubleBlock(state);
      DoubleBlockHalf half = isDoubleBlock ? (DoubleBlockHalf)state.getValue(HALF) : null;
      BlockPos offset = half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
      BlockEntity offsetTileEntity = isDoubleBlock ? world.getBlockEntity(offset) : null;
      Boolean hasSilkTouch = world.registryAccess()
         .registryOrThrow(Registries.ENCHANTMENT)
         .getHolder(Enchantments.SILK_TOUCH)
         .map(it -> EnchantmentHelper.getEnchantmentLevel(it, player) > 0)
         .orElse(false);
      if (hasSilkTouch && this.canSilkTouch()) {
         if (blockEntity instanceof WaystoneBlockEntityBase) {
            ((WaystoneBlockEntityBase)blockEntity).setSilkTouched(true);
         }

         if (isDoubleBlock && offsetTileEntity instanceof WaystoneBlockEntityBase) {
            ((WaystoneBlockEntityBase)offsetTileEntity).setSilkTouched(true);
         }
      }

      if (isDoubleBlock) {
         BlockState offsetState = world.getBlockState(offset);
         if (offsetState.getBlock() == this && offsetState.getValue(HALF) != half) {
            world.destroyBlock(half == DoubleBlockHalf.LOWER ? pos : offset, false, player);
            if (!world.isClientSide && !player.getAbilities().instabuild && player.hasCorrectToolForDrops(state)) {
               dropResources(state, world, pos, blockEntity, player, player.getMainHandItem());
               dropResources(offsetState, world, offset, offsetTileEntity, player, player.getMainHandItem());
            }
         }
      }

      if (blockEntity instanceof WaystoneBlockEntityBase waystoneBlockEntity && !player.getAbilities().instabuild) {
         for (int i = 0; i < waystoneBlockEntity.getContainer().getContainerSize(); i++) {
            ItemStack itemStack = waystoneBlockEntity.getContainer().getItem(i);
            popResource(world, pos, itemStack);
         }
      }

      return super.playerWillDestroy(world, pos, state, player);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, WATERLOGGED, ORIGIN});
   }

   public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
      if (!this.isDoubleBlock(state)) {
         return true;
      }

      if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
         return true;
      }

      BlockState below = world.getBlockState(pos.below());
      return below.getBlock() == this && below.getValue(HALF) == DoubleBlockHalf.LOWER;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Level world = context.getLevel();
      BlockPos pos = context.getClickedPos();
      FluidState fluidState = world.getFluidState(pos);
      return pos.getY() < world.getHeight() - 1 && world.getBlockState(pos.above()).canBeReplaced(context)
         ? (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()))
               .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER))
            .setValue(ORIGIN, WaystoneOrigin.PLAYER)
         : null;
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   protected void notifyObserversOfAction(Level world, BlockPos pos) {
      if (!world.isClientSide) {
         for (Direction direction : Direction.values()) {
            BlockPos offset = pos.relative(direction);
            BlockState neighbourState = world.getBlockState(offset);
            Block neighbourBlock = neighbourState.getBlock();
            if (neighbourBlock instanceof ObserverBlock
               && neighbourState.getValue(ObserverBlock.FACING) == direction.getOpposite()
               && !world.getBlockTicks().hasScheduledTick(offset, neighbourBlock)) {
               world.scheduleTick(offset, neighbourBlock, 2);
            }
         }
      }
   }

   @Nullable
   protected InteractionResult handleEditActions(Level world, Player player, WaystoneBlockEntityBase blockEntity, Waystone waystone) {
      if (player.isShiftKeyDown()) {
         if (!world.isClientSide) {
            blockEntity.getSettingsMenuProvider().ifPresent(menuProvider -> Balm.getNetworking().openGui(player, menuProvider));
         }

         return InteractionResult.SUCCESS;
      } else {
         return null;
      }
   }

   protected boolean shouldOpenMenuWhenPlaced() {
      return true;
   }

   @Nullable
   protected InteractionResult handleActivation(Level world, BlockPos pos, Player player, WaystoneBlockEntityBase tileEntity, Waystone waystone) {
      return null;
   }

   public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!state.is(newState.getBlock()) && world.getBlockEntity(pos) instanceof WaystoneBlockEntityBase waystoneBlockEntity) {
         Waystone waystone = waystoneBlockEntity.getWaystone();
         boolean wasNotSilkTouched = !this.canSilkTouch() || !waystoneBlockEntity.isSilkTouched();
         WaystoneSyncManager.sendWaystoneRemovalToAll(world.getServer(), waystone, wasNotSilkTouched);
         if (wasNotSilkTouched) {
            WaystoneManagerImpl.get(world.getServer()).removeWaystone(waystone);
            PlayerWaystoneManager.removeKnownWaystone(world.getServer(), waystone);
         } else if (waystone instanceof MutableWaystone mutableWaystone) {
            mutableWaystone.setTransient(true);
            WaystoneManagerImpl.get(world.getServer()).updateWaystone(waystone);
         }
      }

      super.onRemove(state, world, pos, newState, isMoving);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(stack, context, list, flag);
      UUID waystoneUid = (UUID)stack.get((DataComponentType)ModComponents.waystone.get());
      if (waystoneUid != null) {
         WaystoneProxy waystone = new WaystoneProxy(null, waystoneUid);
         if (waystone.isValid()) {
            this.addWaystoneNameToTooltip(list, waystone);
         }
      }
   }

   protected void addWaystoneNameToTooltip(List<Component> tooltip, WaystoneProxy waystone) {
      tooltip.add(waystone.getName().copy().withStyle(ChatFormatting.AQUA));
   }

   protected ItemInteractionResult useItemOn(
      ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult
   ) {
      return itemStack.is(ModItems.blankScroll)
         ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION
         : super.useItemOn(itemStack, state, level, pos, player, hand, blockHitResult);
   }

   public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult blockHitResult) {
      if (level.getBlockEntity(pos) instanceof WaystoneBlockEntityBase waystoneBlockEntity) {
         Waystone waystone = waystoneBlockEntity.getWaystone();
         InteractionResult result = this.handleEditActions(level, player, waystoneBlockEntity, waystone);
         if (result != null) {
            return result;
         }

         result = this.handleActivation(level, pos, player, waystoneBlockEntity, waystone);
         return result != null ? result : InteractionResult.FAIL;
      } else {
         return InteractionResult.FAIL;
      }
   }

   public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      BlockPos posAbove = pos.above();
      boolean isDoubleBlock = this.isDoubleBlock(state);
      if (isDoubleBlock) {
         FluidState fluidStateAbove = world.getFluidState(posAbove);
         world.setBlockAndUpdate(
            posAbove,
            (BlockState)((BlockState)((BlockState)state.setValue(HALF, DoubleBlockHalf.UPPER)).setValue(WATERLOGGED, fluidStateAbove.getType() == Fluids.WATER))
               .setValue(ORIGIN, WaystoneOrigin.PLAYER)
         );
      }

      if (blockEntity instanceof WaystoneBlockEntityBase) {
         if (!world.isClientSide) {
            UUID waystoneUid = (UUID)stack.get((DataComponentType)ModComponents.waystone.get());
            WaystoneProxy existingWaystone = null;
            if (waystoneUid != null) {
               existingWaystone = new WaystoneProxy(world.getServer(), waystoneUid);
            }

            if (existingWaystone != null && existingWaystone.isValid() && existingWaystone.getBackingWaystone() instanceof WaystoneImpl backingWaystone) {
               ((WaystoneBlockEntityBase)blockEntity).initializeFromExisting((ServerLevelAccessor)world, backingWaystone, stack);
            } else {
               ((WaystoneBlockEntityBase)blockEntity).initializeWaystone((ServerLevelAccessor)world, placer, WaystoneOrigin.PLAYER);
            }

            if (isDoubleBlock) {
               BlockEntity waystoneEntityAbove = world.getBlockEntity(posAbove);
               if (waystoneEntityAbove instanceof WaystoneBlockEntityBase) {
                  ((WaystoneBlockEntityBase)waystoneEntityAbove).initializeFromBase((WaystoneBlockEntityBase)blockEntity);
               }
            }
         }

         if (placer instanceof Player) {
            Waystone waystone = ((WaystoneBlockEntityBase)blockEntity).getWaystone();
            PlayerWaystoneManager.activateWaystone((Player)placer, waystone);
            if (!world.isClientSide) {
               WaystoneSyncManager.sendActivatedWaystones((Player)placer);
            }
         }

         if (!world.isClientSide && placer instanceof ServerPlayer player) {
            WaystoneBlockEntityBase waystoneTileEntity = (WaystoneBlockEntityBase)blockEntity;
            if (this.shouldOpenMenuWhenPlaced()) {
               waystoneTileEntity.getSettingsMenuProvider().ifPresent(it -> Balm.getNetworking().openGui(player, it));
            }
         }
      }
   }

   public RenderShape getRenderShape(BlockState blockState) {
      return RenderShape.MODEL;
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }
}
