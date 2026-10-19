package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.entity.projectile.Poison_Dart_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

public class Sandstone_Poison_Dart_Trap extends Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty LIT = BlockStateProperties.LIT;

   public Sandstone_Poison_Dart_Trap(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(LIT, false));
   }

   public static Vec3 getDispensePosition(BlockPos coords, Direction dir) {
      double d0 = coords.getX() + 0.5 + 0.7 * dir.getStepX();
      double d1 = coords.getY() + 0.15 + 0.7 * dir.getStepY();
      double d2 = coords.getZ() + 0.5 + 0.7 * dir.getStepZ();
      return new Vec3(d0, d1, d2);
   }

   public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      this.tickGustmaker(state, worldIn, pos, false);
   }

   public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
      this.tickGustmaker(state, worldIn, pos, true);
   }

   public void tickGustmaker(BlockState state, Level worldIn, BlockPos pos, boolean tickOff) {
      boolean flag = worldIn.hasNeighborSignal(pos) || worldIn.hasNeighborSignal(pos.below()) || worldIn.hasNeighborSignal(pos.above());
      boolean flag1 = (Boolean)state.getValue(LIT);
      if (flag && !flag1) {
         if (worldIn.isLoaded(pos)) {
            Vec3 dispensePosition = getDispensePosition(pos, (Direction)state.getValue(FACING));
            Direction direction = (Direction)state.getValue(FACING);
            Poison_Dart_Entity dart = new Poison_Dart_Entity(
               (EntityType)ModEntities.POISON_DART.get(), dispensePosition.x, (float)dispensePosition.y + 0.25F, (float)dispensePosition.z, worldIn
            );
            dart.pickup = Pickup.DISALLOWED;
            dart.shoot(direction.getStepX(), direction.getStepY() + 0.1F, direction.getStepZ(), 2.5F, 1.0F);
            worldIn.addFreshEntity(dart);
         }

         worldIn.setBlock(pos, (BlockState)state.setValue(LIT, true), 2);
         worldIn.scheduleTick(pos, this, 20);
      } else if (flag1 && tickOff) {
         worldIn.scheduleTick(pos, this, 20);
         worldIn.setBlock(pos, (BlockState)state.setValue(LIT, false), 2);
      }
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

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, LIT});
   }
}
