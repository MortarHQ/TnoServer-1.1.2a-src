package net.blay09.mods.waystones.core;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.TeleportDestination;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.error.WaystoneTeleportError;
import net.blay09.mods.waystones.api.event.WaystoneTeleportEvent;
import net.blay09.mods.waystones.block.WaystoneBlock;
import net.blay09.mods.waystones.block.entity.WarpPlateBlockEntity;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.config.WaystonesConfigData;
import net.blay09.mods.waystones.network.message.TeleportEffectMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WaystoneTeleportManager {
   public static Collection<? extends Entity> findPets(Entity entity) {
      return entity.level()
         .getEntitiesOfClass(
            TamableAnimal.class,
            new AABB(entity.blockPosition()).inflate(10.0),
            pet -> entity.getUUID().equals(pet.getOwnerUUID())
               && !pet.isOrderedToSit()
               && !pet.isLeashed()
               && !WaystonePermissionManager.isEntityDeniedTeleports(pet)
         );
   }

   public static List<Mob> findLeashedAnimals(Entity player) {
      return player.level().getEntitiesOfClass(Mob.class, new AABB(player.blockPosition()).inflate(10.0), e -> player.equals(e.getLeashHolder()));
   }

   public static Either<List<Entity>, WaystoneTeleportError> doTeleport(WaystoneTeleportContext context) {
      MinecraftServer server = context.getEntity().getServer();
      return server == null
         ? Either.right(new WaystoneTeleportError.NotOnServer())
         : resolveDestination(server, context.getTargetWaystone()).flatMap(it -> doTeleport(context, it));
   }

   public static Either<List<Entity>, WaystoneTeleportError> doTeleport(WaystoneTeleportContext context, TeleportDestination destination) {
      ServerLevel sourceLevel = (ServerLevel)context.getEntity().level();
      List<Entity> teleportedEntities = teleportEntityAndAttached(context.getEntity(), context, destination);
      context.getAdditionalEntities().forEach(additionalEntity -> teleportedEntities.addAll(teleportEntityAndAttached(additionalEntity, context, destination)));
      BlockPos sourcePos = context.getEntity().blockPosition();
      ServerLevel targetLevel = (ServerLevel)destination.level();
      BlockPos targetPos = BlockPos.containing(destination.location());
      BlockEntity targetTileEntity = targetLevel.getBlockEntity(targetPos);
      if (targetTileEntity instanceof WarpPlateBlockEntity warpPlate) {
         teleportedEntities.forEach(warpPlate::markEntityForCooldown);
      }

      if (context.playsSound()) {
         sourceLevel.playSound(context.getEntity(), sourcePos, SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 0.01F, 1.0F);
         targetLevel.playSound(null, targetPos, SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 0.05F, 1.0F);
      }

      if (context.playsEffect()) {
         teleportedEntities.forEach(additionalEntity -> Balm.getNetworking().sendToTracking(sourceLevel, sourcePos, new TeleportEffectMessage(sourcePos)));
         Balm.getNetworking().sendToTracking(targetLevel, targetPos, new TeleportEffectMessage(targetPos));
      }

      if (context.appliesModifiers() && targetTileEntity instanceof WaystoneBlockEntityBase waystoneBlockEntity) {
         teleportedEntities.forEach(waystoneBlockEntity::applyModifierEffects);
      }

      return Either.left(teleportedEntities);
   }

   private static List<Entity> teleportEntityAndAttached(Entity entity, WaystoneTeleportContext context, TeleportDestination destination) {
      ArrayList<Entity> teleportedEntities = new ArrayList<>();
      ServerLevel targetLevel = (ServerLevel)destination.level();
      Vec3 targetLocation = destination.location();
      Direction targetDirection = destination.direction();
      Entity mount = entity.getVehicle();
      Entity teleportedMount = null;
      if (mount != null) {
         teleportedMount = teleportEntity(mount, targetLevel, targetLocation, targetDirection);
         teleportedEntities.add(teleportedMount);
      }

      List<Mob> leashedEntities = context.getLeashedEntities();
      List<Entity> teleportedLeashedEntities = new ArrayList<>();
      leashedEntities.forEach(leashedEntity -> {
         Entity teleportedLeashedEntity = teleportEntity(leashedEntity, targetLevel, targetLocation, targetDirection);
         teleportedEntities.add(teleportedLeashedEntity);
         teleportedLeashedEntities.add(teleportedLeashedEntity);
      });
      Entity teleportedEntity = teleportEntity(entity, targetLevel, targetLocation, targetDirection);
      teleportedEntities.add(teleportedEntity);
      teleportedLeashedEntities.forEach(teleportedLeashedEntity -> {
         if (teleportedLeashedEntity instanceof Mob teleportedLeashedMob) {
            teleportedLeashedMob.setLeashedTo(teleportedEntity, true);
         }
      });
      if (teleportedMount != null) {
      }

      return teleportedEntities;
   }

   private static Entity teleportEntity(Entity entity, ServerLevel targetWorld, Vec3 targetPos3d, Direction direction) {
      float yaw = direction.toYRot();
      double x = targetPos3d.x;
      double y = targetPos3d.y;
      double z = targetPos3d.z;
      if (entity instanceof ServerPlayer) {
         ChunkPos chunkPos = new ChunkPos(BlockPos.containing(x, y, z));
         targetWorld.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 1, entity.getId());
         entity.stopRiding();
         if (((ServerPlayer)entity).isSleeping()) {
            ((ServerPlayer)entity).stopSleepInBed(true, true);
         }

         if (targetWorld == entity.level()) {
            ((ServerPlayer)entity).connection.teleport(x, y, z, yaw, entity.getXRot(), Collections.emptySet());
         } else {
            ((ServerPlayer)entity).teleportTo(targetWorld, x, y, z, yaw, entity.getXRot());
         }

         entity.setYHeadRot(yaw);
      } else {
         float pitch = Mth.clamp(entity.getXRot(), -90.0F, 90.0F);
         if (targetWorld == entity.level()) {
            entity.moveTo(x, y, z, yaw, pitch);
            entity.setYHeadRot(yaw);
         } else {
            entity.unRide();
            Entity oldEntity = entity;
            entity = entity.getType().create(targetWorld);
            if (entity == null) {
               return oldEntity;
            }

            entity.restoreFrom(oldEntity);
            entity.moveTo(x, y, z, yaw, pitch);
            entity.setYHeadRot(yaw);
            oldEntity.setRemoved(RemovalReason.CHANGED_DIMENSION);
            targetWorld.addDuringTeleport(entity);
         }
      }

      if (!(entity instanceof LivingEntity) || !((LivingEntity)entity).isFallFlying()) {
         entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
         entity.setOnGround(true);
      }

      if (entity instanceof PathfinderMob) {
         ((PathfinderMob)entity).getNavigation().stop();
      }

      sendHackySyncPacketsAfterTeleport(entity);
      return entity;
   }

   private static Either<TeleportDestination, WaystoneTeleportError> resolveDestination(MinecraftServer server, Waystone waystone) {
      ServerLevel level = server.getLevel(waystone.getDimension());
      if (level == null) {
         return Either.right(new WaystoneTeleportError.InvalidDimension(waystone.getDimension()));
      }

      BlockPos pos = waystone.getPos();
      BlockState state = level.getBlockState(pos);
      Direction direction = state.hasProperty(WaystoneBlock.FACING) ? (Direction)state.getValue(WaystoneBlock.FACING) : Direction.NORTH;

      for (Direction candidate : Lists.newArrayList(new Direction[]{direction, Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH})) {
         BlockPos offsetPos = pos.relative(candidate);
         BlockPos offsetPosUp = offsetPos.above();
         if (!level.getBlockState(offsetPos).isSuffocating(level, offsetPos) && !level.getBlockState(offsetPosUp).isSuffocating(level, offsetPosUp)) {
            direction = candidate;
            break;
         }
      }

      ResourceLocation waystoneType = waystone.getWaystoneType();
      boolean shouldOffsetFacing = !waystoneType.equals(WaystoneTypes.WARP_PLATE);
      BlockPos targetPos = shouldOffsetFacing ? pos.relative(direction) : pos;
      Vec3 location = new Vec3(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5);
      return Either.left(new TeleportDestination(level, location, direction));
   }

   private static void sendHackySyncPacketsAfterTeleport(Entity entity) {
      if (entity instanceof ServerPlayer player) {
         player.connection.send(new ClientboundSetExperiencePacket(player.experienceProgress, player.totalExperience, player.experienceLevel));
      }
   }

   public static Either<List<Entity>, WaystoneTeleportError> tryTeleport(WaystoneTeleportContext context) {
      WaystoneTeleportEvent.Pre event = new WaystoneTeleportEvent.Pre(context);
      Balm.getEvents().fireEvent(event);
      if (event.isCanceled()) {
         return Either.right(new WaystoneTeleportError.CancelledByEvent());
      }

      Entity entity = context.getEntity();
      if (!context.getLeashedEntities().isEmpty()) {
         if (WaystonesConfig.getActive().teleports.transportLeashed == WaystonesConfigData.TransportMobs.DISABLED) {
            return Either.right(new WaystoneTeleportError.LeashedWarpDenied());
         }

         for (Mob leashedEntity : context.getLeashedEntities()) {
            if (WaystonePermissionManager.isEntityDeniedTeleports(leashedEntity)) {
               return Either.right(new WaystoneTeleportError.SpecificLeashedWarpDenied(leashedEntity));
            }
         }

         if (context.isDimensionalTeleport() && WaystonesConfig.getActive().teleports.transportLeashed == WaystonesConfigData.TransportMobs.SAME_DIMENSION) {
            return Either.right(new WaystoneTeleportError.LeashedDimensionalWarpDenied());
         }
      }

      if (entity instanceof Player player && !context.getRequirements().canAfford(player) && !player.getAbilities().instabuild) {
         return Either.right(new WaystoneTeleportError.NotEnoughXp());
      } else {
         if (entity instanceof Player player) {
            context.getRequirements().consume(player);
         }

         return doTeleport(context).ifLeft(teleportedEntities -> Balm.getEvents().fireEvent(new WaystoneTeleportEvent.Post(context, teleportedEntities)));
      }
   }

   public static Collection<Entity> findPassengers(Entity entity) {
      List<Entity> passengers = entity.getPassengers();
      ArrayList<Entity> result = new ArrayList<>(passengers);
      Entity vehicle = entity.getVehicle();
      if (vehicle != null) {
         result.addAll(vehicle.getPassengers());
      }

      result.remove(entity);
      return result;
   }
}
