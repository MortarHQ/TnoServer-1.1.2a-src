package net.caffeinemc.mods.lithium.mixin.math.fast_blockpos;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockPos.class)
public abstract class BlockPosMixin extends Vec3i {
   public BlockPosMixin(int x, int y, int z) {
      super(x, y, z);
   }

   @Overwrite
   public BlockPos above() {
      return new BlockPos(this.getX(), this.getY() + 1, this.getZ());
   }

   @Overwrite
   public BlockPos above(int distance) {
      return new BlockPos(this.getX(), this.getY() + distance, this.getZ());
   }

   @Overwrite
   public BlockPos below() {
      return new BlockPos(this.getX(), this.getY() - 1, this.getZ());
   }

   @Overwrite
   public BlockPos below(int distance) {
      return new BlockPos(this.getX(), this.getY() - distance, this.getZ());
   }

   @Overwrite
   public BlockPos north() {
      return new BlockPos(this.getX(), this.getY(), this.getZ() - 1);
   }

   @Overwrite
   public BlockPos north(int distance) {
      return new BlockPos(this.getX(), this.getY(), this.getZ() - distance);
   }

   @Overwrite
   public BlockPos south() {
      return new BlockPos(this.getX(), this.getY(), this.getZ() + 1);
   }

   @Overwrite
   public BlockPos south(int distance) {
      return new BlockPos(this.getX(), this.getY(), this.getZ() + distance);
   }

   @Overwrite
   public BlockPos west() {
      return new BlockPos(this.getX() - 1, this.getY(), this.getZ());
   }

   @Overwrite
   public BlockPos west(int distance) {
      return new BlockPos(this.getX() - distance, this.getY(), this.getZ());
   }

   @Overwrite
   public BlockPos east() {
      return new BlockPos(this.getX() + 1, this.getY(), this.getZ());
   }

   @Overwrite
   public BlockPos east(int distance) {
      return new BlockPos(this.getX() + distance, this.getY(), this.getZ());
   }
}
