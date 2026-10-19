package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.block.entity.RuneTableBlockEntity;
import com.mortal_cultivation.entity.RuneBurstEntity;
import com.mortal_cultivation.item.RuneTableItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public final class RuneTableBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final MapCodec<RuneTableBlock> CODEC = simpleCodec(RuneTableBlock::new);
   private static final VoxelShape NORTH_SHAPE = Shapes.or(
         Block.box(0.0, 0.0, 0.0, 16.0, 1.5, 16.0),
         new VoxelShape[]{
            Block.box(1.0, 1.5, 1.0, 15.0, 3.0, 15.0),
            Block.box(2.0, 3.0, 2.0, 14.0, 5.0, 14.0),
            Block.box(3.0, 5.0, 3.0, 13.0, 10.0, 13.0),
            Block.box(2.0, 10.0, 2.0, 14.0, 12.0, 14.0),
            Block.box(11.3, 4.5, 2.5, 13.5, 10.5, 4.7),
            Block.box(2.5, 4.5, 2.5, 4.7, 10.5, 4.7),
            Block.box(11.3, 4.5, 11.3, 13.5, 10.5, 13.5),
            Block.box(2.5, 4.5, 11.3, 4.7, 10.5, 13.5),
            Block.box(0.5, 12.0, 0.5, 15.5, 13.0, 15.5),
            Block.box(0.0, 13.0, 0.0, 16.0, 14.5, 16.0),
            Block.box(1.0, 15.4, 1.0, 15.0, 15.9, 15.0),
            Block.box(1.0, 14.5, 0.25, 15.0, 15.5, 1.0),
            Block.box(1.0, 14.5, 14.95, 15.0, 15.5, 15.7),
            Block.box(15.0, 14.5, 1.0, 15.75, 15.5, 15.0),
            Block.box(0.25, 14.5, 1.0, 1.0, 15.5, 15.0),
            Block.box(3.95, 15.0, 4.05, 11.45, 15.3, 11.55),
            Block.box(5.65, 15.3, 3.05, 10.35, 16.3, 3.55),
            Block.box(5.65, 15.3, 12.45, 10.35, 16.3, 12.95),
            Block.box(13.0, 15.3, 7.25, 13.5, 16.3, 8.75),
            Block.box(2.6, 15.3, 7.25, 3.1, 16.3, 8.75),
            Block.box(6.0, 15.8, 6.0, 10.0, 16.25, 10.0)
         }
      )
      .optimize();
   private static final Map<Direction, VoxelShape> SHAPES = Map.of(
      Direction.NORTH,
      NORTH_SHAPE,
      Direction.EAST,
      rotateNorthShape(Direction.EAST),
      Direction.SOUTH,
      rotateNorthShape(Direction.SOUTH),
      Direction.WEST,
      rotateNorthShape(Direction.WEST)
   );

   public RuneTableBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPES.get(state.getValue(FACING));
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPES.get(state.getValue(FACING));
   }

   public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
      return Shapes.empty();
   }

   public List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootParams.Builder builder) {
      List<ItemStack> drops = new ArrayList<>(super.getDrops(state, builder));
      ItemStack tool = (ItemStack)builder.getParameter(LootContextParams.TOOL);
      if (!hasSilkTouch(builder.getLevel(), tool)) {
         return drops;
      }

      BlockEntity blockEntity = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
      if (blockEntity instanceof RuneTableBlockEntity table && table.getStoredRuneCount() > 0) {
         for (ItemStack drop : drops) {
            if (drop.is(this.asItem())) {
               RuneTableItem.setStoredRuneCount(drop, table.getStoredRuneCount());
               break;
            }
         }

         return drops;
      } else {
         return drops;
      }
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      super.setPlacedBy(level, pos, state, placer, stack);
      if (!level.isClientSide && level.getBlockEntity(pos) instanceof RuneTableBlockEntity table) {
         table.setStoredRuneCount(RuneTableItem.getStoredRuneCount(stack));
      }
   }

   public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
      if (level instanceof ServerLevel serverLevel
         && blockEntity instanceof RuneTableBlockEntity table
         && table.getStoredRuneCount() > 0
         && !hasSilkTouch(serverLevel, tool)) {
         RuneBurstEntity.spawn(serverLevel, pos);
      }

      super.playerDestroy(level, player, pos, state, blockEntity, tool);
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if (level.getBlockEntity(pos) instanceof RuneTableBlockEntity table) {
         if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(table, buffer -> buffer.writeBlockPos(pos));
         }

         return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
      if (!state.is(newState.getBlock()) && !level.isClientSide && level.getBlockEntity(pos) instanceof RuneTableBlockEntity table) {
         table.dropContents(level);
      }

      super.onRemove(state, level, pos, newState, movedByPiston);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new RuneTableBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return (tickerLevel, pos, blockState, blockEntity) -> {
         if (blockEntity instanceof RuneTableBlockEntity table) {
            if (tickerLevel.isClientSide) {
               RuneTableBlockEntity.clientTick(tickerLevel, pos, blockState, table);
            } else {
               RuneTableBlockEntity.serverTick(tickerLevel, pos, blockState, table);
            }
         }
      };
   }

   private static boolean hasSilkTouch(ServerLevel level, ItemStack tool) {
      return !tool.isEmpty() && tool.getEnchantmentLevel(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH)) > 0;
   }

   private static VoxelShape rotateNorthShape(Direction facing) {
      VoxelShape rotated = Shapes.empty();

      for (AABB box : NORTH_SHAPE.toAabbs()) {
         double x1 = box.minX * 16.0;
         double y1 = box.minY * 16.0;
         double z1 = box.minZ * 16.0;
         double x2 = box.maxX * 16.0;
         double y2 = box.maxY * 16.0;
         double z2 = box.maxZ * 16.0;

         VoxelShape part = switch (facing) {
            case EAST -> Block.box(16.0 - z2, y1, x1, 16.0 - z1, y2, x2);
            case SOUTH -> Block.box(16.0 - x2, y1, 16.0 - z2, 16.0 - x1, y2, 16.0 - z1);
            case WEST -> Block.box(z1, y1, 16.0 - x2, z2, y2, 16.0 - x1);
            default -> NORTH_SHAPE;
         };
         rotated = Shapes.or(rotated, part);
      }

      return rotated.optimize();
   }
}
