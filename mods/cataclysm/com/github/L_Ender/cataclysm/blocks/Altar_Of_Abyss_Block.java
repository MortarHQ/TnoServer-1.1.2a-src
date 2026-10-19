package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.AltarOfAbyss_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Altar_Of_Abyss_Block extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final MapCodec<Altar_Of_Abyss_Block> CODEC = simpleCodec(Altar_Of_Abyss_Block::new);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   private static final VoxelShape BASE = Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
   private static final VoxelShape MID = Block.box(2.0, 3.0, 2.0, 14.0, 8.0, 14.0);
   private static final VoxelShape TOP = Block.box(0.0, 8.0, 0.0, 16.0, 11.0, 16.0);
   private static final VoxelShape AXIS_AABB = Shapes.or(BASE, new VoxelShape[]{MID, TOP});

   public MapCodec<Altar_Of_Abyss_Block> codec() {
      return CODEC;
   }

   public Altar_Of_Abyss_Block(Properties p_54257_) {
      super(p_54257_);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(WATERLOGGED, Boolean.FALSE)
      );
   }

   public ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit
   ) {
      ItemStack heldItem = player.getItemInHand(handIn);
      if (worldIn.getBlockEntity(pos) instanceof AltarOfAbyss_Block_Entity && !player.isShiftKeyDown() && heldItem.getItem() != this.asItem()) {
         AltarOfAbyss_Block_Entity aof = (AltarOfAbyss_Block_Entity)worldIn.getBlockEntity(pos);
         ItemStack copy = heldItem.copy();
         copy.setCount(1);
         if (aof.getItem(0).isEmpty()) {
            aof.placeItem(player, 0, copy);
            if (!player.isCreative()) {
               heldItem.shrink(1);
            }
         } else {
            popResource(worldIn, pos, aof.getItem(0).copy());
            aof.placeItem(player, 0, ItemStack.EMPTY);
         }

         return ItemInteractionResult.SUCCESS;
      } else {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }

   public BlockState getStateForPlacement(BlockPlaceContext p_48781_) {
      FluidState fluidstate = p_48781_.getLevel().getFluidState(p_48781_.getClickedPos());
      return (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, p_48781_.getHorizontalDirection().getClockWise()))
         .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
   }

   public FluidState getFluidState(BlockState p_51581_) {
      return p_51581_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51581_);
   }

   public BlockState rotate(BlockState p_48811_, Rotation p_48812_) {
      return (BlockState)p_48811_.setValue(FACING, p_48812_.rotate((Direction)p_48811_.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> p_48814_) {
      p_48814_.add(new Property[]{FACING, WATERLOGGED});
   }

   public VoxelShape getShape(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
      return AXIS_AABB;
   }

   public BlockState updateShape(BlockState p_51555_, Direction p_51556_, BlockState p_51557_, LevelAccessor p_51558_, BlockPos p_51559_, BlockPos p_51560_) {
      if ((Boolean)p_51555_.getValue(WATERLOGGED)) {
         p_51558_.scheduleTick(p_51559_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51558_));
      }

      return super.updateShape(p_51555_, p_51556_, p_51557_, p_51558_, p_51559_, p_51560_);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new AltarOfAbyss_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return createTickerHelper(p_152182_, (BlockEntityType)ModTileentites.ALTAR_OF_ABYSS.get(), AltarOfAbyss_Block_Entity::commonTick);
   }
}
