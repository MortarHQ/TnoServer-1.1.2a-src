package cn.leolezury.eternalstarlight.common.world;

import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ESTeleporter {
   protected static Optional<BlockPos> getOrMakePortal(ServerLevel level, Entity entity, BlockPos entrancePos, BlockPos pos) {
      Optional<BlockPos> existingPortal = getExistingPortal(level, pos);
      if (existingPortal.isPresent()) {
         return existingPortal;
      }

      Axis portalAxis = entity.level().getBlockState(entrancePos).getOptionalValue(ESPortalBlock.AXIS).orElse(Axis.X);
      return makePortal(level, pos, portalAxis);
   }

   public static Optional<BlockPos> getExistingPortal(ServerLevel level, BlockPos pos) {
      int maxHeight = level.getMaxBuildHeight();
      int minHeight = level.getMinBuildHeight();
      WorldBorder border = level.getWorldBorder();
      MutableBlockPos blockPos = new MutableBlockPos();

      for (int x = pos.getX() - 16; x <= pos.getX() + 16; x++) {
         for (int z = pos.getZ() - 16; z <= pos.getZ() + 16; z++) {
            for (int y = minHeight; y <= maxHeight; y++) {
               blockPos.set(x, y, z);
               BlockState state = level.getBlockState(blockPos);
               if (border.isWithinBounds(blockPos) && state.is((Block)ESBlocks.STARLIGHT_PORTAL.get()) && (Boolean)state.getValue(ESPortalBlock.CENTER)) {
                  return Optional.of(blockPos);
               }
            }
         }
      }

      return Optional.empty();
   }

   public static Optional<BlockPos> makePortal(ServerLevel level, BlockPos blockPos, Axis axis) {
      int maxHeight = level.getMaxBuildHeight();
      int minHeight = level.getMinBuildHeight();
      WorldBorder border = level.getWorldBorder();

      for (MutableBlockPos pos : BlockPos.spiralAround(blockPos, 32, Direction.EAST, Direction.SOUTH)) {
         int worldSurface = level.getHeight(Types.WORLD_SURFACE, pos.getX(), pos.getZ());
         int from = Math.max(minHeight, worldSurface - 10);
         int to = Math.min(maxHeight, worldSurface + 10);
         if (from < to) {
            for (int height = from; height < to; height++) {
               pos.setY(height);
               if (border.isWithinBounds(pos) && ESPortalBlock.placePortal(level, pos, axis, 2)) {
                  return Optional.of(pos.relative(Direction.UP));
               }
            }
         }

         pos.setY(blockPos.getY());

         for (int height = blockPos.getY(); height < maxHeight; height++) {
            pos.setY(height);
            if (border.isWithinBounds(pos) && ESPortalBlock.placePortal(level, pos, axis, 2)) {
               return Optional.of(pos.relative(Direction.UP));
            }
         }

         pos.setY(blockPos.getY());

         for (int height = blockPos.getY(); height > minHeight; height--) {
            pos.setY(height);
            if (border.isWithinBounds(pos) && ESPortalBlock.placePortal(level, pos, axis, 2)) {
               return Optional.of(pos.relative(Direction.UP));
            }
         }
      }

      return Optional.empty();
   }

   @Nullable
   public static DimensionTransition getPortalInfo(Entity entity, BlockPos entrancePos, ServerLevel dest) {
      if (entity.level().dimension() != ESDimensions.STARLIGHT_KEY && dest.dimension() != ESDimensions.STARLIGHT_KEY) {
         return null;
      }

      WorldBorder border = dest.getWorldBorder();
      double coordinateDifference = DimensionType.getTeleportationScale(entity.level().dimensionType(), dest.dimensionType());
      BlockPos pos = border.clampToBounds(entity.getX() * coordinateDifference, entity.getY(), entity.getZ() * coordinateDifference);
      return getOrMakePortal(dest, entity, entrancePos, pos)
         .map(
            result -> new DimensionTransition(
               dest, Vec3.atCenterOf(result), Vec3.ZERO, entity.getYRot(), entity.getXRot(), DimensionTransition.PLACE_PORTAL_TICKET
            )
         )
         .orElse(null);
   }
}
