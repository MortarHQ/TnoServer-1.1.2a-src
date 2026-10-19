package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.EMP_Block_Entity;
import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
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

public class EMP_Block extends BaseEntityBlock {
   public static final MapCodec<EMP_Block> CODEC = simpleCodec(EMP_Block::new);
   public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final BooleanProperty OVERLOAD = BooleanProperty.create("overload");

   public MapCodec<EMP_Block> codec() {
      return CODEC;
   }

   public EMP_Block(Properties p_54257_) {
      super(p_54257_);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(TIP_DIRECTION, Direction.UP)).setValue(POWERED, false))
            .setValue(OVERLOAD, false)
      );
   }

   public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (!worldIn.isClientSide) {
         this.updateState(state, worldIn, pos, blockIn);
      }
   }

   public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
      if (!worldIn.isClientSide) {
         this.updateState(state, worldIn, pos, state.getBlock());
      }
   }

   public void updateState(BlockState state, Level worldIn, BlockPos pos, Block blockIn) {
      boolean flag = (Boolean)state.getValue(POWERED);
      boolean flag1 = worldIn.hasNeighborSignal(pos);
      if (flag1 != flag) {
         worldIn.setBlock(pos, (BlockState)state.setValue(POWERED, flag1), 3);
         worldIn.updateNeighborsAt(pos.below(), this);
      }
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(TIP_DIRECTION, context.getNearestLookingVerticalDirection().getOpposite()))
            .setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos())))
         .setValue(OVERLOAD, false);
   }

   public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
      Direction direction = Direction.getRandom(rand);
      double d0 = 0.5625;
      double d = rand.nextFloat() - 0.5F;
      if (direction != Direction.UP) {
         BlockPos blockpos = pos.relative(direction);
         BlockState blockstate = worldIn.getBlockState(blockpos);
         if (!stateIn.canOcclude() || !blockstate.isFaceSturdy(worldIn, blockpos, direction.getOpposite())) {
            Axis direction$axis = direction.getAxis();
            double d1 = direction$axis == Axis.X ? 0.5 + d0 * direction.getStepX() : rand.nextFloat();
            double d3 = direction$axis == Axis.Z ? 0.5 + d0 * direction.getStepZ() : rand.nextFloat();
            if ((Boolean)stateIn.getValue(OVERLOAD)) {
               for (int i1 = 0; i1 < 20; i1++) {
                  worldIn.addParticle(DustParticleOptions.REDSTONE, pos.getX() + d1, pos.getY() + 0.75, pos.getZ() + d3, 0.0, 0.0, 0.0);
               }
            } else {
               worldIn.addParticle(new LightningParticleOptions(255, 51, 0), pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5, d * 2.0, d, d * 2.0);
            }
         }
      }
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new EMP_Block_Entity(pos, state);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{TIP_DIRECTION, POWERED, OVERLOAD});
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return createTickerHelper(p_152182_, (BlockEntityType)ModTileentites.EMP.get(), EMP_Block_Entity::commonTick);
   }
}
