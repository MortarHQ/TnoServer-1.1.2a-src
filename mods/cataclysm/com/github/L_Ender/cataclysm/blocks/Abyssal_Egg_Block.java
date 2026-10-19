package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.Abyssal_Egg_Block_Entity;
import com.github.L_Ender.cataclysm.entity.Pet.The_Baby_Leviathan_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Abyssal_Egg_Block extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final MapCodec<Abyssal_Egg_Block> CODEC = simpleCodec(Abyssal_Egg_Block::new);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final int MAX_HATCH_LEVEL = 2;
   public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
   private static final int REGULAR_HATCH_TIME_TICKS = 12000;
   private static final int RANDOM_HATCH_OFFSET_TICKS = 300;
   private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public MapCodec<Abyssal_Egg_Block> codec() {
      return CODEC;
   }

   public Abyssal_Egg_Block(Properties p_54257_) {
      super(p_54257_);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(HATCH, 0)).setValue(WATERLOGGED, Boolean.FALSE));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> p_277441_) {
      p_277441_.add(new Property[]{HATCH, WATERLOGGED});
   }

   public VoxelShape getShape(BlockState p_277872_, BlockGetter p_278090_, BlockPos p_277364_, CollisionContext p_278016_) {
      return SHAPE;
   }

   public FluidState getFluidState(BlockState p_51581_) {
      return p_51581_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51581_);
   }

   public BlockState getStateForPlacement(BlockPlaceContext p_48781_) {
      FluidState fluidstate = p_48781_.getLevel().getFluidState(p_48781_.getClickedPos());
      return (BlockState)this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
   }

   public BlockState updateShape(BlockState p_51555_, Direction p_51556_, BlockState p_51557_, LevelAccessor p_51558_, BlockPos p_51559_, BlockPos p_51560_) {
      if ((Boolean)p_51555_.getValue(WATERLOGGED)) {
         p_51558_.scheduleTick(p_51559_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51558_));
      }

      return super.updateShape(p_51555_, p_51556_, p_51557_, p_51558_, p_51559_, p_51560_);
   }

   public int getHatchLevel(BlockState p_279125_) {
      return (Integer)p_279125_.getValue(HATCH);
   }

   private boolean isReadyToHatch(BlockState p_278021_) {
      return this.getHatchLevel(p_278021_) == 2;
   }

   public void tick(BlockState p_277841_, ServerLevel p_277739_, BlockPos p_277692_, RandomSource p_277973_) {
      if (!this.isReadyToHatch(p_277841_)) {
         p_277739_.playSound((Player)null, p_277692_, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + p_277973_.nextFloat() * 0.2F);
         p_277739_.setBlock(p_277692_, (BlockState)p_277841_.setValue(HATCH, this.getHatchLevel(p_277841_) + 1), 2);
      } else {
         p_277739_.playSound((Player)null, p_277692_, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + p_277973_.nextFloat() * 0.2F);
         p_277739_.destroyBlock(p_277692_, false);
         The_Baby_Leviathan_Entity levia = (The_Baby_Leviathan_Entity)((EntityType)ModEntities.THE_BABY_LEVIATHAN.get()).create(p_277739_);
         if (levia != null) {
            levia.moveTo(p_277692_.getX() + 0.5, p_277692_.getY() + 0.5, p_277692_.getZ() + 0.5, Mth.wrapDegrees(p_277739_.random.nextFloat() * 360.0F), 0.0F);
            p_277739_.addFreshEntity(levia);
         }
      }
   }

   public void onPlace(BlockState p_277964_, Level p_277827_, BlockPos p_277526_, BlockState p_277618_, boolean p_277819_) {
      int j = 4000;
      p_277827_.gameEvent(GameEvent.BLOCK_PLACE, p_277526_, Context.of(p_277964_));
      p_277827_.scheduleTick(p_277526_, this, j + p_277827_.random.nextInt(300));
   }

   protected boolean isPathfindable(BlockState p_51023_, PathComputationType p_51026_) {
      return false;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new Abyssal_Egg_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return createTickerHelper(p_152182_, (BlockEntityType)ModTileentites.ABYSSAL_EGG.get(), Abyssal_Egg_Block_Entity::commonTick);
   }
}
