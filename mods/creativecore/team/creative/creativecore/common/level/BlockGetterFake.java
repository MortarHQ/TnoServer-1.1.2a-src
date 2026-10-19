package team.creative.creativecore.common.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class BlockGetterFake implements BlockGetter {
   public BlockGetter parent;
   public BlockPos pos;
   public BlockState fakeState;

   public void set(BlockGetter world, BlockPos pos, BlockState fakeState) {
      this.parent = world;
      this.pos = pos;
      this.fakeState = fakeState;
   }

   public BlockEntity getBlockEntity(BlockPos pos) {
      return pos.equals(this.pos) ? null : this.parent.getBlockEntity(pos);
   }

   public BlockState getBlockState(BlockPos pos) {
      return pos.equals(this.pos) ? this.fakeState : this.parent.getBlockState(pos);
   }

   public FluidState getFluidState(BlockPos pos) {
      return pos.equals(this.pos) ? null : this.parent.getFluidState(pos);
   }

   public int getHeight() {
      return this.parent.getHeight();
   }

   public int getMinBuildHeight() {
      return this.parent.getMinBuildHeight();
   }
}
