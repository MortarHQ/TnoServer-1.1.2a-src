package com.bobmowzie.mowziesmobs.server.entity.grottol;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Blocks;

public final class BlackPinkRailLine implements Consumer<AbstractMinecart> {
   private final BlackPinkInYourArea action;
   private BlackPinkRailLine.State state = new BlackPinkRailLine.StateAcquireVertex();

   private BlackPinkRailLine(BlackPinkInYourArea action) {
      this.action = action;
   }

   public void accept(AbstractMinecart minecart) {
      this.state = this.next(minecart.level(), minecart);
   }

   private BlackPinkRailLine.State next(Level world, AbstractMinecart minecart) {
      BlockPos pos = getRailPosition(world, BlockPos.containing(minecart.position()));
      return !BaseRailBlock.isRail(world.getBlockState(pos)) && world.getBlockState(pos) != Blocks.AIR.defaultBlockState()
         ? this.state.derail()
         : this.state.apply(world, minecart, pos);
   }

   private static BlockPos getRailPosition(Level world, BlockPos pos) {
      BlockPos below = pos.below();
      return BaseRailBlock.isRail(world, below) ? below : pos;
   }

   public static BlackPinkRailLine create() {
      return new BlackPinkRailLine(BlackPinkInYourArea.create());
   }

   private abstract class State {
      abstract BlackPinkRailLine.State apply(Level var1, AbstractMinecart var2, BlockPos var3);

      abstract BlackPinkRailLine.State derail();
   }

   private final class StateAcquireEdge extends BlackPinkRailLine.State {
      private final BlockPos vertex;

      private StateAcquireEdge(BlockPos vertex) {
         this.vertex = vertex;
      }

      @Override
      public BlackPinkRailLine.State apply(Level world, AbstractMinecart minecart, BlockPos vertex) {
         return BlackPinkRailLine.this.new StateSearch(vertex.subtract(this.vertex), vertex);
      }

      @Override
      BlackPinkRailLine.State derail() {
         return BlackPinkRailLine.this.new StateAcquireVertex();
      }
   }

   private final class StateAcquireVertex extends BlackPinkRailLine.State {
      @Override
      public BlackPinkRailLine.State apply(Level world, AbstractMinecart minecart, BlockPos vertex) {
         return BlackPinkRailLine.this.new StateAcquireEdge(vertex);
      }

      @Override
      BlackPinkRailLine.State derail() {
         return this;
      }
   }

   private final class StateSearch extends BlackPinkRailLine.State {
      private final long[] mask = new long[]{1151706154984090261L, 1149919144941647226L, 1152921487426969583L, 1152635631447310335L};
      private final long test = 4503599627370496L;
      private Vec3i edge;
      private BlockPos vertex;
      private int ordinal;
      private long state = 4503599627370494L;

      private StateSearch(Vec3i edge, BlockPos vertex) {
         this.edge = edge;
         this.vertex = vertex;
      }

      @Override
      public BlackPinkRailLine.State apply(Level world, AbstractMinecart minecart, BlockPos vertex) {
         if (!this.vertex.equals(vertex)) {
            Vec3i edge = vertex.subtract(this.vertex);
            int ordinal = this.getOrdinal(this.edge, edge);
            if (ordinal >= 0
               && ordinal < 4
               && (ordinal != 1 || ordinal != this.ordinal)
               && ((this.state = (this.state | this.mask[ordinal]) << 1) & 4503599627370496L) == 0L) {
               BlackPinkRailLine.this.action.accept(world, minecart);
            }

            this.ordinal = ordinal;
            this.vertex = vertex;
            this.edge = edge;
         }

         return this;
      }

      private int getOrdinal(Vec3i v0, Vec3i v1) {
         return 1 + (v1.getZ() * v0.getX() - v1.getX() * v0.getZ()) + (v0.getX() * v1.getX() + v0.getZ() * v1.getZ() & 2);
      }

      @Override
      BlackPinkRailLine.State derail() {
         return BlackPinkRailLine.this.new StateAcquireVertex();
      }
   }
}
