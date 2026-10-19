package com.benji.netherman.common.block;

import com.benji.netherman.common.block.entity.FacePuzzleBlockEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FacePuzzleBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final MapCodec<FacePuzzleBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(propertiesCodec(), Codec.INT.fieldOf("max_states").forGetter(block -> block.maxStates))
         .apply(instance, (properties, maxStates) -> new FacePuzzleBlock(properties, maxStates, () -> null))
   );
   public static final IntegerProperty MAX_STATES = IntegerProperty.create("max_states", 2, 3);
   private static final VoxelShape SHAPE_NORTH = Block.box(1.0, 1.0, 14.0, 15.0, 15.0, 16.0);
   private static final VoxelShape SHAPE_SOUTH = Block.box(1.0, 1.0, 0.0, 15.0, 15.0, 2.0);
   private static final VoxelShape SHAPE_WEST = Block.box(14.0, 1.0, 1.0, 16.0, 15.0, 15.0);
   private static final VoxelShape SHAPE_EAST = Block.box(0.0, 1.0, 1.0, 2.0, 15.0, 15.0);
   private final Supplier<BlockEntityType<FacePuzzleBlockEntity>> blockEntityTypeSupplier;
   private final int maxStates;

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public FacePuzzleBlock(Properties properties, int maxStates, Supplier<BlockEntityType<FacePuzzleBlockEntity>> beSupplier) {
      super(properties);
      this.maxStates = maxStates;
      this.blockEntityTypeSupplier = beSupplier;
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(MAX_STATES, maxStates)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, MAX_STATES});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()))
         .setValue(MAX_STATES, this.maxStates);
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case SOUTH -> SHAPE_SOUTH;
         case WEST -> SHAPE_WEST;
         case EAST -> SHAPE_EAST;
         default -> SHAPE_NORTH;
      };
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if (!level.isClientSide() && level.getBlockEntity(pos) instanceof FacePuzzleBlockEntity entity && !entity.isSolved()) {
         entity.advanceState();
      }

      return InteractionResult.sidedSuccess(level.isClientSide());
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return this.blockEntityTypeSupplier.get().create(pos, state);
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }
}
