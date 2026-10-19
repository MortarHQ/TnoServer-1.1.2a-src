package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.Cursed_tombstone_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Cursed_Tombstone_Block extends BaseEntityBlock {
   public static final MapCodec<Cursed_Tombstone_Block> CODEC = simpleCodec(Cursed_Tombstone_Block::new);
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty LIT = BlockStateProperties.LIT;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   private static final VoxelShape X_BASE = Block.box(5.0, 0.0, 0.0, 11.0, 2.0, 16.0);
   private static final VoxelShape Z_BASE = Block.box(0.0, 0.0, 5.0, 16.0, 2.0, 11.0);
   private static final VoxelShape X_MID = Block.box(6.0, 2.0, 1.0, 10.0, 24.0, 15.0);
   private static final VoxelShape Z_MID = Block.box(1.0, 2.0, 6.0, 15.0, 24.0, 10.0);
   private static final VoxelShape X_AXIS_AABB = Shapes.or(X_BASE, X_MID);
   private static final VoxelShape Z_AXIS_AABB = Shapes.or(Z_BASE, Z_MID);

   public MapCodec<Cursed_Tombstone_Block> codec() {
      return CODEC;
   }

   public Cursed_Tombstone_Block(Properties p_54257_) {
      super(p_54257_);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(LIT, false))
            .setValue(POWERED, false)
      );
   }

   public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
      return (BlockState)this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection().getOpposite());
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   protected ItemInteractionResult useItemOn(
      ItemStack p_316383_, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand p_316216_, BlockHitResult p_316827_
   ) {
      if ((Boolean)state.getValue(POWERED)) {
         if (!(Boolean)state.getValue(LIT)) {
            state = (BlockState)state.setValue(LIT, true);
            worldIn.setBlock(pos, state, 10);
            return ItemInteractionResult.SUCCESS;
         } else {
            return ItemInteractionResult.FAIL;
         }
      } else {
         player.displayClientMessage(Component.translatable("block.cataclysm.cursed_tombstone.message"), true);
         return ItemInteractionResult.FAIL;
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> p_48814_) {
      p_48814_.add(new Property[]{FACING, LIT, POWERED});
   }

   public VoxelShape getShape(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
      Direction direction = (Direction)p_48816_.getValue(FACING);
      return direction.getAxis() == Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new Cursed_tombstone_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return createTickerHelper(p_152182_, (BlockEntityType)ModTileentites.CURSED_TOMBSTONE.get(), Cursed_tombstone_Entity::commonTick);
   }
}
