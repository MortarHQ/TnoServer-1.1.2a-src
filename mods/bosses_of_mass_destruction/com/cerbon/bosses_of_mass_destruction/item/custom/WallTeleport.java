package com.cerbon.bosses_of_mass_destruction.item.custom;

import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WallTeleport {
   private final ServerLevel level;
   private final Entity entity;

   public WallTeleport(ServerLevel level, Entity entity) {
      this.level = level;
      this.entity = entity;
   }

   public boolean tryTeleport(Vec3 direction, Vec3 position) {
      return this.tryTeleport(direction, position, this::teleportTo);
   }

   public boolean tryTeleport(Vec3 direction, Vec3 position, Consumer<BlockPos> action) {
      WallTeleport.Context context = new WallTeleport.Context(direction, position);
      BlockPos teleportStart = this.getTeleportStart(context);
      if (teleportStart != null) {
         BlockPos teleportEnd = this.getTeleportEnd(context, teleportStart);
         if (teleportEnd != null) {
            action.accept(teleportEnd);
            return true;
         }
      }

      return false;
   }

   private BlockPos getTeleportStart(WallTeleport.Context context) {
      BlockPos startPos = BlockPos.containing(context.position);
      double startRange = 3.0;
      BlockPos endPos = BlockPos.containing(context.position.add(context.direction.scale(startRange)));

      for (BlockPos pos : MathUtils.getBlocksInLine(startPos, endPos)) {
         if (this.level.getBlockState(pos).isRedstoneConductor(this.level, pos)) {
            return pos;
         }
      }

      return null;
   }

   private BlockPos getTeleportEnd(WallTeleport.Context context, BlockPos startPos) {
      double endRange = 20.0;
      BlockPos endPos = startPos.offset(BlockPos.containing(context.direction.scale(endRange)));

      for (BlockPos pos : MathUtils.getBlocksInLine(startPos, endPos)) {
         BlockState blockState = this.level.getBlockState(pos);
         if (blockState.isAir() && this.level.getBlockState(pos.above()).isAir()) {
            return pos;
         }

         if (blockState.getBlock().defaultDestroyTime() < 0.0F) {
            return null;
         }
      }

      return null;
   }

   private void teleportTo(BlockPos teleportPos) {
      Vec3 pos = VecUtils.asVec3(teleportPos).add(new Vec3(0.5, 0.0, 0.5));
      this.entity.teleportTo(pos.x, pos.y, pos.z);
   }

   private record Context(Vec3 direction, Vec3 position) {
   }
}
