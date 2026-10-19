package com.chronodawn.core.portal;

import com.chronodawn.ChronoDawn;
import com.chronodawn.blocks.ChronoDawnPortalBlock;
import com.chronodawn.data.ChronoDawnGlobalState;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PortalTeleportHandler {
   private static final Map<UUID, ResourceKey<Level>> LAST_ARRIVAL_DIMENSION = new HashMap<>();
   private static final int PORTAL_SEARCH_RADIUS = 16;
   private static final int MIN_PORTAL_Y = 70;
   private static final int MAX_PORTAL_Y = 100;

   public static boolean canTeleportFromDimension(UUID entityId, ResourceKey<Level> currentDimension) {
      ResourceKey<Level> lastArrivalDim = LAST_ARRIVAL_DIMENSION.get(entityId);
      return lastArrivalDim == null ? true : !lastArrivalDim.equals(currentDimension);
   }

   private static void recordArrivalDimension(UUID entityId, ResourceKey<Level> arrivalDimension) {
      LAST_ARRIVAL_DIMENSION.put(entityId, arrivalDimension);
   }

   public static void clearArrivalDimension(UUID entityId) {
      LAST_ARRIVAL_DIMENSION.remove(entityId);
   }

   public static void cleanupStaleArrivalRecords(MinecraftServer server) {
      LAST_ARRIVAL_DIMENSION.entrySet().removeIf(entry -> {
         UUID entityId = entry.getKey();
         Entity entity = null;

         for (ServerLevel level : server.getAllLevels()) {
            entity = level.getEntity(entityId);
            if (entity != null) {
               break;
            }
         }

         if (entity == null) {
            return true;
         }

         BlockPos entityPos = entity.blockPosition();
         BlockState stateAtEntity = entity.level().getBlockState(entityPos);
         return !stateAtEntity.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get());
      });
   }

   public static boolean teleportThroughPortal(Entity entity, BlockPos sourcePortalPos) {
      if (entity instanceof ServerPlayer player) {
         ServerLevel sourceLevel = (ServerLevel)player.level();
         MinecraftServer server = sourceLevel.getServer();
         BlockState sourcePortalState = sourceLevel.getBlockState(sourcePortalPos);
         Axis sourceAxis = Axis.X;
         if (sourcePortalState.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())) {
            sourceAxis = (Axis)sourcePortalState.getValue(ChronoDawnPortalBlock.AXIS);
         }

         BlockPos sourceFramePos = findSourcePortalFrame(sourceLevel, sourcePortalPos);
         if (sourceFramePos == null) {
            ChronoDawn.LOGGER.warn("Could not find portal frame for portal block at {}, using block position as fallback", sourcePortalPos);
            sourceFramePos = sourcePortalPos;
         }

         ResourceKey<Level> destDimensionKey = getDestinationDimension(sourceLevel.dimension());
         ServerLevel destLevel = server.getLevel(destDimensionKey);
         if (destLevel == null) {
            ChronoDawn.LOGGER.error("Destination dimension {} not found", destDimensionKey.location());
            return false;
         }

         BlockPos destCoords = calculateDestinationCoords(sourceFramePos);
         Optional<BlockPos> existingPortal = findNearbyPortal(destLevel, destCoords);
         BlockPos destPortalPos;
         if (existingPortal.isPresent()) {
            destPortalPos = existingPortal.get();
         } else {
            Optional<BlockPos> reusableFrame = findReusablePortalFrame(destLevel, destCoords, sourceAxis);
            BlockPos framePos = reusableFrame.orElse(null);
            if (framePos == null) {
               framePos = generatePortal(destLevel, destCoords, sourceAxis);
            }

            if (framePos == null) {
               ChronoDawn.LOGGER.error("Failed to generate portal at {} in dimension {}", destCoords, destDimensionKey.location());
               return false;
            }

            destPortalPos = sourceAxis == Axis.X ? framePos.offset(1, 1, 0) : framePos.offset(0, 1, 1);
         }

         sourceLevel.playSound(null, sourcePortalPos, SoundEvents.PORTAL_TRAVEL, SoundSource.BLOCKS, 0.25F, sourceLevel.getRandom().nextFloat() * 0.4F + 0.8F);
         Vec3 destPosition = calculateSafeTeleportPosition(destPortalPos, sourceAxis);
         float destYRot = calculateTeleportRotation(sourceAxis);
         player.teleportTo(destLevel, destPosition.x, destPosition.y, destPosition.z, destYRot, player.getXRot());
         destLevel.playSound(null, destPortalPos, SoundEvents.PORTAL_TRAVEL, SoundSource.BLOCKS, 0.25F, destLevel.getRandom().nextFloat() * 0.4F + 0.8F);
         PortalStateMachine sourcePortal = PortalRegistry.getInstance().getPortalAt(sourcePortalPos);
         if (sourcePortal != null && sourcePortal.getCurrentState() == PortalState.ACTIVATED) {
            sourcePortal.deactivate();
         }

         boolean portalWasDestroyed = false;
         if (destDimensionKey.equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
            ChronoDawnGlobalState globalState = ChronoDawnGlobalState.get(server);
            globalState.markChronoDawnEntered();
            if (globalState.arePortalsUnstable()) {
               destroyUnstablePortal(destLevel, destPortalPos);
               portalWasDestroyed = true;
            }
         }

         if (!portalWasDestroyed) {
            recordArrivalDimension(player.getUUID(), destDimensionKey);
         }

         return true;
      } else {
         return false;
      }
   }

   private static Vec3 calculateSafeTeleportPosition(BlockPos portalBlockPos, Axis axis) {
      return axis == Axis.X
         ? new Vec3(portalBlockPos.getX() + 0.5, portalBlockPos.getY() + 0.1, portalBlockPos.getZ() + 0.5)
         : new Vec3(portalBlockPos.getX() + 0.5, portalBlockPos.getY() + 0.1, portalBlockPos.getZ() + 0.5);
   }

   private static float calculateTeleportRotation(Axis axis) {
      return axis == Axis.X ? 180.0F : 90.0F;
   }

   private static BlockPos findSourcePortalFrame(ServerLevel level, BlockPos portalBlockPos) {
      for (UUID portalId : PortalRegistry.getInstance().getPortalsInDimension(level.dimension())) {
         PortalStateMachine portal = PortalRegistry.getInstance().getPortal(portalId);
         if (portal != null) {
            BlockPos framePos = portal.getPosition();

            for (int dx = 0; dx <= 5; dx++) {
               for (int dy = 0; dy <= 5; dy++) {
                  for (int dz = 0; dz <= 5; dz++) {
                     BlockPos checkPos = framePos.offset(dx, dy, dz);
                     if (checkPos.equals(portalBlockPos)) {
                        return framePos;
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private static ResourceKey<Level> getDestinationDimension(ResourceKey<Level> sourceDimension) {
      return sourceDimension.equals(ModDimensions.CHRONO_DAWN_DIMENSION) ? Level.OVERWORLD : ModDimensions.CHRONO_DAWN_DIMENSION;
   }

   private static BlockPos calculateDestinationCoords(BlockPos sourceFramePos) {
      return new BlockPos(sourceFramePos.getX(), 150, sourceFramePos.getZ());
   }

   private static Optional<BlockPos> findNearbyPortal(ServerLevel level, BlockPos coords) {
      Set<UUID> portalsInDimension = PortalRegistry.getInstance().getPortalsInDimension(level.dimension());
      BlockPos nearestPortal = null;
      double nearestDistance = Double.MAX_VALUE;

      for (UUID portalId : portalsInDimension) {
         PortalStateMachine portal = PortalRegistry.getInstance().getPortal(portalId);
         if (portal != null) {
            BlockPos portalPos = portal.getPosition();
            double distance = Math.sqrt(Math.pow(portalPos.getX() - coords.getX(), 2.0) + Math.pow(portalPos.getZ() - coords.getZ(), 2.0));
            if (distance < 128.0 && distance < nearestDistance) {
               nearestDistance = distance;
               nearestPortal = portalPos;
            }
         }
      }

      if (nearestPortal != null) {
         for (int dx = 0; dx <= 5; dx++) {
            for (int dy = 0; dy <= 5; dy++) {
               for (int dz = 0; dz <= 5; dz++) {
                  BlockPos checkPos = nearestPortal.offset(dx, dy, dz);
                  if (level.getBlockState(checkPos).is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())) {
                     return Optional.of(checkPos);
                  }
               }
            }
         }

         ChronoDawn.LOGGER.warn("Portal frame at {} exists in registry but portal blocks not found", nearestPortal);
      }

      for (int x = -16; x <= 16; x++) {
         for (int y = -16; y <= 16; y++) {
            for (int z = -16; z <= 16; z++) {
               BlockPos checkPos = coords.offset(x, y, z);
               BlockState state = level.getBlockState(checkPos);
               if (state.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())) {
                  return Optional.of(checkPos);
               }
            }
         }
      }

      return Optional.empty();
   }

   private static Optional<BlockPos> findReusablePortalFrame(ServerLevel level, BlockPos coords, Axis axis) {
      Set<UUID> portalsInDimension = PortalRegistry.getInstance().getPortalsInDimension(level.dimension());
      BlockPos nearestFrame = null;
      double nearestDistance = Double.MAX_VALUE;

      for (UUID portalId : portalsInDimension) {
         PortalStateMachine portal = PortalRegistry.getInstance().getPortal(portalId);
         if (portal != null && portal.getCurrentState() != PortalState.STABILIZED) {
            BlockPos framePos = portal.getPosition();
            double distance = Math.sqrt(Math.pow(framePos.getX() - coords.getX(), 2.0) + Math.pow(framePos.getZ() - coords.getZ(), 2.0));
            if (distance < 128.0 && distance < nearestDistance && hasPortalFrame(level, framePos, axis)) {
               nearestDistance = distance;
               nearestFrame = framePos;
            }
         }
      }

      if (nearestFrame != null) {
         generatePortalStructure(level, nearestFrame, axis);
         return Optional.of(nearestFrame);
      } else {
         return Optional.empty();
      }
   }

   private static boolean hasPortalFrame(ServerLevel level, BlockPos pos, Axis axis) {
      int width = 4;
      int height = 5;
      Direction horizontal = axis == Axis.X ? Direction.EAST : Direction.SOUTH;
      Direction vertical = Direction.UP;

      for (int x = 0; x < width; x++) {
         if (!level.getBlockState(pos.relative(horizontal, x)).is((Block)ModBlocks.CLOCKSTONE_BLOCK.get())) {
            return false;
         }

         if (!level.getBlockState(pos.relative(horizontal, x).relative(vertical, height - 1)).is((Block)ModBlocks.CLOCKSTONE_BLOCK.get())) {
            return false;
         }
      }

      for (int y = 0; y < height; y++) {
         if (!level.getBlockState(pos.relative(vertical, y)).is((Block)ModBlocks.CLOCKSTONE_BLOCK.get())) {
            return false;
         }

         if (!level.getBlockState(pos.relative(horizontal, width - 1).relative(vertical, y)).is((Block)ModBlocks.CLOCKSTONE_BLOCK.get())) {
            return false;
         }
      }

      return true;
   }

   private static BlockPos generatePortal(ServerLevel level, BlockPos coords, Axis axis) {
      BlockPos groundPos = findGroundLevel(level, coords);
      generatePortalStructure(level, groundPos, axis);
      return groundPos;
   }

   private static BlockPos findGroundLevel(ServerLevel level, BlockPos start) {
      int surfaceY = level.getHeight(Types.WORLD_SURFACE, start.getX(), start.getZ());
      int upwardCeiling = 250;
      int interiorHeight = 3;
      MutableBlockPos cursor = new MutableBlockPos(start.getX(), 0, start.getZ());
      int frameY = surfaceY;
      cursor.setY(surfaceY - 1);
      BlockState topBlock = level.getBlockState(cursor);
      if (!topBlock.getFluidState().isEmpty() || topBlock.canBeReplaced()) {
         frameY = surfaceY - 1;
      }

      for (int y = frameY; y <= 250; y++) {
         boolean interiorClear = true;

         for (int dy = 1; dy <= 3; dy++) {
            cursor.setY(y + dy);
            if (!isClearForPortalSpace(level.getBlockState(cursor))) {
               interiorClear = false;
               break;
            }
         }

         if (interiorClear) {
            return new BlockPos(start.getX(), y, start.getZ());
         }
      }

      return new BlockPos(start.getX(), 120, start.getZ());
   }

   private static boolean isClearForPortalSpace(BlockState state) {
      return (state.isAir() || state.canBeReplaced()) && state.getFluidState().isEmpty();
   }

   private static void generatePortalStructure(ServerLevel level, BlockPos pos, Axis axis) {
      int width = 4;
      int height = 5;
      Direction horizontal = axis == Axis.X ? Direction.EAST : Direction.SOUTH;
      Direction vertical = Direction.UP;
      BlockState clockstoneState = ((Block)ModBlocks.CLOCKSTONE_BLOCK.get()).defaultBlockState();

      for (int x = 0; x < width; x++) {
         level.setBlock(pos.relative(horizontal, x), clockstoneState, 3);
      }

      for (int x = 0; x < width; x++) {
         level.setBlock(pos.relative(horizontal, x).relative(vertical, height - 1), clockstoneState, 3);
      }

      for (int y = 0; y < height; y++) {
         level.setBlock(pos.relative(vertical, y), clockstoneState, 3);
      }

      for (int y = 0; y < height; y++) {
         level.setBlock(pos.relative(horizontal, width - 1).relative(vertical, y), clockstoneState, 3);
      }

      BlockState portalState = (BlockState)((Block)ModBlocks.CHRONO_DAWN_PORTAL.get()).defaultBlockState().setValue(ChronoDawnPortalBlock.AXIS, axis);

      for (int x = 1; x < width - 1; x++) {
         for (int y = 1; y < height - 1; y++) {
            level.setBlock(pos.relative(horizontal, x).relative(vertical, y), portalState, 3);
         }
      }

      PortalStateMachine portal = PortalRegistry.getInstance().getPortalAt(pos);
      if (portal == null) {
         UUID portalId = UUID.randomUUID();
         portal = new PortalStateMachine(portalId, level.dimension(), pos);
         PortalRegistry.getInstance().registerPortal(portal);
         portal.activate();
      } else if (portal.getCurrentState() == PortalState.INACTIVE) {
         portal.activate();
      } else if (portal.getCurrentState() == PortalState.DEACTIVATED) {
         portal.setState(PortalState.ACTIVATED);
         PortalRegistry.getInstance().markDirtyForPortal(portal.getPortalId());
      }
   }

   private static void destroyUnstablePortal(ServerLevel level, BlockPos portalPos) {
      Set<BlockPos> visited = new HashSet<>();
      Queue<BlockPos> queue = new LinkedList<>();
      if (level.getBlockState(portalPos).is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())) {
         queue.add(portalPos);
         visited.add(portalPos);
      }

      while (!queue.isEmpty()) {
         BlockPos current = queue.poll();
         level.removeBlock(current, false);

         for (Direction direction : Direction.values()) {
            BlockPos adjacent = current.relative(direction);
            BlockState adjacentState = level.getBlockState(adjacent);
            if (adjacentState.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get()) && !visited.contains(adjacent)) {
               queue.add(adjacent);
               visited.add(adjacent);
            }
         }
      }

      if (!visited.isEmpty()) {
         level.playSound(null, portalPos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
         double searchRadius = 10.0;

         for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(portalPos).inflate(searchRadius))) {
            if (entity instanceof ServerPlayer) {
               clearArrivalDimension(entity.getUUID());
            }
         }
      }
   }
}
