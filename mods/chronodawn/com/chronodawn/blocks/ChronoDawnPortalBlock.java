package com.chronodawn.blocks;

import com.chronodawn.ChronoDawn;
import com.chronodawn.core.portal.PortalTeleportHandler;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModParticles;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChronoDawnPortalBlock extends Block {
   public static final EnumProperty<Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
   protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
   private static final int PORTAL_TIME_THRESHOLD = 80;
   private static final Map<UUID, Integer> ENTITY_PORTAL_STATES = new HashMap<>();
   private static final Map<UUID, BlockPos> LAST_PORTAL_POSITION = new HashMap<>();
   private static final Map<UUID, Long> LAST_INSIDE_TICK = new HashMap<>();
   private static final Map<UUID, Long> LAST_COUNTER_INCREMENT_TICK = new HashMap<>();
   private static final int PORTAL_COLOR_R = 219;
   private static final int PORTAL_COLOR_G = 136;
   private static final int PORTAL_COLOR_B = 19;

   public ChronoDawnPortalBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(AXIS, Axis.X));
   }

   public static Properties createProperties() {
      return Properties.of().noCollission().randomTicks().strength(-1.0F).lightLevel(state -> 11).noLootTable();
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch ((Axis)state.getValue(AXIS)) {
         case Z -> Z_AXIS_AABB;
         default -> X_AXIS_AABB;
      };
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!this.isValidPortalPosition(level, pos, (Axis)state.getValue(AXIS))) {
         this.destroyPortalWithSound(level, pos);
      }

      if (random.nextInt(200) == 0) {
         cleanupStalePortalStates(level);
      }
   }

   private static void cleanupStalePortalStates(ServerLevel level) {
      ENTITY_PORTAL_STATES.entrySet().removeIf(entry -> {
         UUID entityId = entry.getKey();
         Entity entity = findEntityInAllDimensions(level.getServer(), entityId);
         if (entity == null) {
            LAST_PORTAL_POSITION.remove(entityId);
            LAST_INSIDE_TICK.remove(entityId);
            LAST_COUNTER_INCREMENT_TICK.remove(entityId);
            PortalTeleportHandler.clearArrivalDimension(entityId);
            return true;
         } else {
            return false;
         }
      });
      LAST_PORTAL_POSITION.entrySet().removeIf(entry -> {
         Entity entity = findEntityInAllDimensions(level.getServer(), entry.getKey());
         return entity == null;
      });
      LAST_INSIDE_TICK.entrySet().removeIf(entry -> {
         Entity entity = findEntityInAllDimensions(level.getServer(), entry.getKey());
         return entity == null;
      });
      LAST_COUNTER_INCREMENT_TICK.entrySet().removeIf(entry -> {
         Entity entity = findEntityInAllDimensions(level.getServer(), entry.getKey());
         return entity == null;
      });
      PortalTeleportHandler.cleanupStaleArrivalRecords(level.getServer());
   }

   private static Entity findEntityInAllDimensions(MinecraftServer server, UUID entityId) {
      for (ServerLevel level : server.getAllLevels()) {
         Entity entity = level.getEntity(entityId);
         if (entity != null) {
            return entity;
         }
      }

      return null;
   }

   private boolean isValidPortalPosition(LevelAccessor level, BlockPos pos, Axis axis) {
      Axis otherAxis = axis == Axis.X ? Axis.Z : Axis.X;
      Direction horizontalNeg = Direction.get(AxisDirection.NEGATIVE, otherAxis);
      Direction horizontalPos = Direction.get(AxisDirection.POSITIVE, otherAxis);
      Direction up = Direction.UP;
      Direction down = Direction.DOWN;
      BlockState stateHorizontalNeg = level.getBlockState(pos.relative(horizontalNeg));
      BlockState stateHorizontalPos = level.getBlockState(pos.relative(horizontalPos));
      BlockState stateUp = level.getBlockState(pos.relative(up));
      BlockState stateDown = level.getBlockState(pos.relative(down));
      boolean validHorizontalNeg = this.isFrameBlock(stateHorizontalNeg) || stateHorizontalNeg.is(this) && stateHorizontalNeg.getValue(AXIS) == axis;
      boolean validHorizontalPos = this.isFrameBlock(stateHorizontalPos) || stateHorizontalPos.is(this) && stateHorizontalPos.getValue(AXIS) == axis;
      boolean validUp = this.isFrameBlock(stateUp) || stateUp.is(this) && stateUp.getValue(AXIS) == axis;
      boolean validDown = this.isFrameBlock(stateDown) || stateDown.is(this) && stateDown.getValue(AXIS) == axis;
      return validHorizontalNeg || validHorizontalPos || validUp || validDown;
   }

   private boolean isFrameBlock(BlockState state) {
      return state.is((Block)ModBlocks.CLOCKSTONE_BLOCK.get());
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (!level.isClientSide()) {
         if (!entity.isPassenger() && !entity.isVehicle()) {
            UUID entityId = entity.getUUID();
            if (entity instanceof ServerPlayer player) {
               int currentState = ENTITY_PORTAL_STATES.getOrDefault(entityId, 0);
               if (currentState == 0 || currentState % 20 == 0) {
                  ChronoDawn.LOGGER.debug("Player {} inside portal at {} (current state: {})", new Object[]{player.getName().getString(), pos, currentState});
               }
            }

            long currentTick = level.getGameTime();
            BlockPos lastPortalPos = LAST_PORTAL_POSITION.get(entityId);
            Long lastTick = LAST_INSIDE_TICK.get(entityId);
            boolean exitedAndReentered = false;
            if (lastPortalPos != null && lastPortalPos.distSqr(pos) > 25.0) {
               PortalTeleportHandler.clearArrivalDimension(entityId);
               int oldState = ENTITY_PORTAL_STATES.getOrDefault(entityId, 0);
               ENTITY_PORTAL_STATES.remove(entityId);
               LAST_COUNTER_INCREMENT_TICK.remove(entityId);
               exitedAndReentered = true;
               if (entity instanceof ServerPlayer player) {
                  ChronoDawn.LOGGER.debug("Player {} moved to different portal (distance > 25), reset counter from {}", player.getName().getString(), oldState);
               }
            } else if (lastPortalPos != null && lastTick != null && currentTick - lastTick > 20L) {
               PortalTeleportHandler.clearArrivalDimension(entityId);
               int oldState = ENTITY_PORTAL_STATES.getOrDefault(entityId, 0);
               ENTITY_PORTAL_STATES.remove(entityId);
               LAST_COUNTER_INCREMENT_TICK.remove(entityId);
               exitedAndReentered = true;
               if (entity instanceof ServerPlayer player) {
                  ChronoDawn.LOGGER
                     .debug("Player {} exited and re-entered portal (tick gap > 20), reset counter from {}", player.getName().getString(), oldState);
               }
            }

            LAST_PORTAL_POSITION.put(entityId, pos.immutable());
            LAST_INSIDE_TICK.put(entityId, currentTick);
            boolean canTeleport = PortalTeleportHandler.canTeleportFromDimension(entityId, level.dimension());
            if (!canTeleport) {
               int currentState = ENTITY_PORTAL_STATES.getOrDefault(entityId, 0);
               if (entity instanceof ServerPlayer player && currentState == 0) {
                  ChronoDawn.LOGGER
                     .debug(
                        "Player {} cannot teleport from dimension {} (still in arrival dimension, state: {})",
                        new Object[]{player.getName().getString(), level.dimension().location(), currentState}
                     );
               }

               if (currentState == -1) {
                  ENTITY_PORTAL_STATES.put(entityId, 1);
               } else {
                  ENTITY_PORTAL_STATES.put(entityId, 1);
               }
            } else {
               Long lastIncrementTick = LAST_COUNTER_INCREMENT_TICK.get(entityId);
               if (lastIncrementTick == null || lastIncrementTick != currentTick) {
                  int stateValue = ENTITY_PORTAL_STATES.getOrDefault(entityId, 0);
                  ENTITY_PORTAL_STATES.put(entityId, ++stateValue);
                  LAST_COUNTER_INCREMENT_TICK.put(entityId, currentTick);
                  if (stateValue == 1 && entity instanceof ServerPlayer) {
                     level.playSound(null, pos, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0F, 1.0F);
                  }

                  boolean shouldTeleport = false;
                  if (entity instanceof ServerPlayer player) {
                     if (player.getAbilities().invulnerable) {
                        shouldTeleport = true;
                        ChronoDawn.LOGGER.debug("Portal counter for {}: {} (Creative mode, teleporting immediately)", player.getName().getString(), stateValue);
                     } else {
                        shouldTeleport = stateValue >= 80;
                        if (stateValue % 20 == 0 || shouldTeleport) {
                           ChronoDawn.LOGGER
                              .debug(
                                 "Portal counter for {}: {}/{} (Survival mode, shouldTeleport={})",
                                 new Object[]{player.getName().getString(), stateValue, 80, shouldTeleport}
                              );
                        }
                     }
                  } else {
                     shouldTeleport = stateValue >= 80;
                  }

                  if (shouldTeleport) {
                     if (entity instanceof ServerPlayer player) {
                        ChronoDawn.LOGGER.debug("Teleporting player {} after {} ticks in portal", player.getName().getString(), stateValue);
                     }

                     boolean success = PortalTeleportHandler.teleportThroughPortal(entity, pos);
                     if (success) {
                        ENTITY_PORTAL_STATES.put(entityId, -1);
                        LAST_COUNTER_INCREMENT_TICK.remove(entityId);
                        if (entity instanceof ServerPlayer player) {
                           ChronoDawn.LOGGER.debug("Player {} teleported successfully", player.getName().getString());
                        }
                     } else {
                        ENTITY_PORTAL_STATES.remove(entityId);
                        LAST_COUNTER_INCREMENT_TICK.remove(entityId);
                        if (entity instanceof ServerPlayer player) {
                           ChronoDawn.LOGGER.warn("Teleportation failed for player {}", player.getName().getString());
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (random.nextInt(40) == 0) {
         level.playLocalSound(
            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false
         );
      }

      Axis axis = (Axis)state.getValue(AXIS);
      double x = pos.getX() + random.nextDouble();
      double y = pos.getY() + random.nextDouble();
      double z = pos.getZ() + random.nextDouble();
      double speedX = (random.nextDouble() - 0.5) * 0.1;
      double speedY = (random.nextDouble() - 0.5) * 0.1;
      double speedZ = (random.nextDouble() - 0.5) * 0.1;
      int j = random.nextInt(2) * 2 - 1;
      if (axis == Axis.X) {
         z = pos.getZ() + 0.5 + 0.25 * j;
         speedZ = random.nextFloat() * 0.3F * j;
      } else {
         x = pos.getX() + 0.5 + 0.25 * j;
         speedX = random.nextFloat() * 0.3F * j;
      }

      level.addParticle((ParticleOptions)ModParticles.CHRONO_DAWN_PORTAL.get(), x, y, z, speedX, speedY, speedZ);
   }

   public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
      return ItemStack.EMPTY;
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return switch (rotation) {
         case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> {
            switch ((Axis)state.getValue(AXIS)) {
               case Z:
                  yield (BlockState)state.setValue(AXIS, Axis.X);
               case X:
                  yield (BlockState)state.setValue(AXIS, Axis.Z);
               default:
                  yield state;
            }
         }
         default -> state;
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AXIS});
   }

   public BlockState updateShape(
      BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos
   ) {
      Axis axis = (Axis)state.getValue(AXIS);
      Axis directionAxis = direction.getAxis();
      boolean isRelevantDirection = directionAxis != axis;
      if (isRelevantDirection && !this.isValidPortalPosition(level, currentPos, axis)) {
         if (!level.isClientSide()) {
            this.destroyPortalWithSound((ServerLevel)level, currentPos);
         }

         return Blocks.AIR.defaultBlockState();
      } else {
         return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
      }
   }

   private void destroyPortalWithSound(ServerLevel level, BlockPos startPos) {
      if (level.getBlockState(startPos).is(this)) {
         Set<BlockPos> portalBlocks = new HashSet<>();
         Queue<BlockPos> toCheck = new LinkedList<>();
         toCheck.add(startPos);
         portalBlocks.add(startPos);

         while (!toCheck.isEmpty()) {
            BlockPos current = toCheck.poll();

            for (Direction direction : Direction.values()) {
               BlockPos neighbor = current.relative(direction);
               if (!portalBlocks.contains(neighbor) && level.getBlockState(neighbor).is(this)) {
                  portalBlocks.add(neighbor);
                  toCheck.add(neighbor);
               }
            }
         }

         BlockState portalState = this.defaultBlockState();

         for (BlockPos pos : portalBlocks) {
            level.sendParticles(
               new BlockParticleOption(ParticleTypes.BLOCK, portalState), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 15, 0.3, 0.3, 0.3, 0.1
            );
            level.removeBlock(pos, false);
         }

         if (!portalBlocks.isEmpty()) {
            level.playSound(null, startPos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
         }
      }
   }
}
