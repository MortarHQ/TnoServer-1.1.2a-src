package com.tom.storagemod.block;

import com.mojang.serialization.MapCodec;
import com.tom.storagemod.block.entity.LevelEmitterBlockEntity;
import com.tom.storagemod.client.ClientUtil;
import com.tom.storagemod.inventory.InventoryCableNetwork;
import com.tom.storagemod.util.BlockFace;
import com.tom.storagemod.util.TickerUtil;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LevelEmitterBlock extends BaseEntityBlock implements IInventoryCable, NeoForgeBlock {
   public static final DirectionProperty FACING = BlockStateProperties.FACING;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final MapCodec<LevelEmitterBlock> CODEC = ChestBlock.simpleCodec(properties -> new LevelEmitterBlock());

   public LevelEmitterBlock() {
      super(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(3.0F).noOcclusion());
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, Direction.DOWN)).setValue(POWERED, false));
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      ClientUtil.tooltip("level_emitter", tooltip);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new LevelEmitterBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
      return TickerUtil.createTicker(world, false, true);
   }

   public boolean isSignalSource(BlockState state) {
      return true;
   }

   public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
      if (!(Boolean)blockState.getValue(POWERED)) {
         return 0;
      } else {
         return ((Direction)blockState.getValue(FACING)).getOpposite() == side ? 15 : 0;
      }
   }

   public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
      return !blockState.getValue(POWERED) ? 0 : 15;
   }

   public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
      return state.getValue(FACING) != side;
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, POWERED});
   }

   public RenderShape getRenderShape(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   @Override
   public List<BlockFace> nextScan(Level world, BlockState state, BlockPos pos) {
      Direction d = (Direction)state.getValue(FACING);
      return Collections.singletonList(new BlockFace(pos.relative(d.getOpposite()), d));
   }

   @Override
   public boolean canConnectFrom(BlockState state, Direction dir) {
      return ((Direction)state.getValue(FACING)).getOpposite() == dir;
   }

   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      switch ((Direction)state.getValue(FACING)) {
         case DOWN:
            return Shapes.or(box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0), box(3.0, 6.0, 3.0, 13.0, 16.0, 13.0));
         case EAST:
            return Shapes.or(box(10.0, 5.0, 5.0, 16.0, 11.0, 11.0), box(0.0, 3.0, 3.0, 10.0, 13.0, 13.0));
         case NORTH:
            return Shapes.or(box(5.0, 5.0, 0.0, 11.0, 11.0, 6.0), box(3.0, 3.0, 6.0, 13.0, 13.0, 16.0));
         case SOUTH:
            return Shapes.or(box(5.0, 5.0, 10.0, 11.0, 11.0, 16.0), box(3.0, 3.0, 0.0, 13.0, 13.0, 10.0));
         case UP:
            return Shapes.or(box(5.0, 10.0, 5.0, 11.0, 16.0, 11.0), box(3.0, 0.0, 3.0, 13.0, 10.0, 13.0));
         case WEST:
            return Shapes.or(box(0.0, 5.0, 5.0, 6.0, 11.0, 11.0), box(6.0, 3.0, 3.0, 16.0, 13.0, 13.0));
         default:
            return Shapes.block();
      }
   }

   public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
      if ((Boolean)stateIn.getValue(POWERED)) {
         Direction direction = ((Direction)stateIn.getValue(FACING)).getOpposite();
         double d0 = pos.getX() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;
         double d1 = pos.getY() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;
         double d2 = pos.getZ() + 0.5 + (rand.nextDouble() - 0.5) * 0.2;
         float f = -7.0F;
         f /= 16.0F;
         double d3 = f * direction.getStepX();
         double d4 = f * direction.getStepZ();
         worldIn.addParticle(DustParticleOptions.REDSTONE, d0 + d3, d1, d2 + d4, 0.0, 0.0, 0.0);
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      if (world.isClientSide) {
         return InteractionResult.SUCCESS;
      }

      if (world.getBlockEntity(pos) instanceof LevelEmitterBlockEntity be) {
         player.openMenu(be);
      }

      return InteractionResult.SUCCESS;
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public void destroy(LevelAccessor p_49860_, BlockPos p_49861_, BlockState p_49862_) {
      if (p_49860_ instanceof ServerLevel l) {
         InventoryCableNetwork.getNetwork(l).markNodeInvalid(p_49861_);
      }
   }

   public void neighborChanged(BlockState p_60509_, Level p_60510_, BlockPos p_60511_, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
      super.neighborChanged(p_60509_, p_60510_, p_60511_, p_60512_, p_60513_, p_60514_);
      if (!p_60510_.isClientSide) {
         InventoryCableNetwork n = InventoryCableNetwork.getNetwork(p_60510_);
         n.markNodeInvalid(p_60511_);
         n.markNodeInvalid(p_60513_);
      }
   }

   public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
      if (level instanceof ServerLevel l) {
         InventoryCableNetwork n = InventoryCableNetwork.getNetwork(l);
         n.markNodeInvalid(pos);
         n.markNodeInvalid(neighbor);
      }
   }
}
