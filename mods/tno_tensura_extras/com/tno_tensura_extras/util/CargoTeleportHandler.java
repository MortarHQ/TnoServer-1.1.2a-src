package com.tno_tensura_extras.util;

import com.tno_tensura_extras.block.entity.CargoInTransitBlockEntity;
import com.tno_tensura_extras.compat.ProtectionCompat;
import com.tno_tensura_extras.data.BoundCargo;
import com.tno_tensura_extras.entity.TeleportPortalEntity;
import com.tno_tensura_extras.registry.ExtrasDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public final class CargoTeleportHandler {
   private static final TagKey<Block> LOOTR_CONTAINERS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("lootr", "containers"));
   private static final int PORTAL_LIFE = 160;
   private static final int PORTAL_OPEN_DELAY = 40;
   private static final int TRAVEL_DURATION = 60;

   private CargoTeleportHandler() {
   }

   public static boolean deliver(ItemEntity itemEntity, ItemStack stack) {
      if (!(itemEntity.level() instanceof ServerLevel targetLevel)) {
         return false;
      } else {
         BoundCargo bound = (BoundCargo)stack.get(ExtrasDataComponents.BOUND_CARGO.get());
         if (bound == null) {
            return false;
         }

         MinecraftServer server = targetLevel.getServer();
         if (server == null) {
            return false;
         }

         ServerLevel sourceLevel = server.getLevel(bound.dimension());
         if (sourceLevel == null) {
            return false;
         }

         BlockPos targetPos = resolveTarget(targetLevel, itemEntity);
         if (!hasRoom(targetLevel, targetPos)) {
            return false;
         }

         BlockPos sourcePos = bound.pos();
         if (!ProtectionCompat.mayRelocate(server, bound.binder(), bound.dimension(), sourcePos, targetLevel.dimension(), targetPos)) {
            return false;
         }

         int sourceChunkX = sourcePos.getX() >> 4;
         int sourceChunkZ = sourcePos.getZ() >> 4;
         if (!sourceLevel.hasChunk(sourceChunkX, sourceChunkZ)) {
            sourceLevel.getChunk(sourceChunkX, sourceChunkZ, ChunkStatus.FULL, true);
         }

         BlockState sourceState = sourceLevel.getBlockState(sourcePos);
         if (isCargoChest(sourceLevel, sourcePos) && sourceLevel.getBlockEntity(sourcePos) instanceof RandomizableContainerBlockEntity chest) {
            Direction pairing = pairingDirection(sourceLevel, sourcePos, sourceState);
            BlockPos partnerSourcePos = pairing == null ? null : sourcePos.relative(pairing);
            BlockPos partnerTargetPos = pairing == null ? null : targetPos.relative(pairing);
            if (partnerTargetPos == null
               || hasRoom(targetLevel, partnerTargetPos)
                  && ProtectionCompat.mayRelocate(server, bound.binder(), bound.dimension(), partnerSourcePos, targetLevel.dimension(), partnerTargetPos)) {
               ChestBlockEntity partner = partnerSourcePos != null && sourceLevel.getBlockEntity(partnerSourcePos) instanceof ChestBlockEntity found
                  ? found
                  : null;
               CompoundTag cargoData = chest.saveWithFullMetadata(sourceLevel.registryAccess());
               CompoundTag partnerData = partner == null ? null : partner.saveWithFullMetadata(sourceLevel.registryAccess());
               CargoInTransitBlockEntity first = CargoInTransitBlockEntity.place(
                  targetLevel, targetPos, deliveredState(sourceState, pairing != null), cargoData, 40, 60
               );
               if (first == null) {
                  return false;
               }

               if (partner != null) {
                  CargoInTransitBlockEntity second = CargoInTransitBlockEntity.place(
                     targetLevel, partnerTargetPos, deliveredState(sourceLevel.getBlockState(partnerSourcePos), true), partnerData, 40, 60
                  );
                  if (second == null) {
                     first.cancelBeforeDeparture();
                     targetLevel.removeBlock(targetPos, false);
                     return false;
                  }
               }

               spawnPortal(sourceLevel, sourcePos, partnerSourcePos, 160);
               BlockState sinkingState = sourceState;
               BlockState partnerSinkingState = partnerSourcePos == null ? null : sourceLevel.getBlockState(partnerSourcePos);
               clearAndRemove(sourceLevel, sourcePos, chest);
               if (partner != null) {
                  clearAndRemove(sourceLevel, partnerSourcePos, partner);
               }

               CargoInTransitBlockEntity.sink(sourceLevel, sourcePos, sinkingState, 40, 60);
               if (partnerSinkingState != null) {
                  CargoInTransitBlockEntity.sink(sourceLevel, partnerSourcePos, partnerSinkingState, 40, 60);
               }

               sourceLevel.playSound(null, sourcePos, SoundEvents.PLAYER_TELEPORT, SoundSource.BLOCKS, 1.0F, 1.0F);
               spawnPortal(targetLevel, targetPos, partnerTargetPos, 160);
               targetLevel.playSound(null, targetPos, SoundEvents.PLAYER_TELEPORT, SoundSource.BLOCKS, 1.0F, 1.0F);
               return true;
            } else {
               return false;
            }
         } else {
            stack.remove(ExtrasDataComponents.BOUND_CARGO.get());
            return false;
         }
      }
   }

   public static boolean isCargoChest(BlockGetter level, BlockPos pos) {
      BlockEntity blockEntity = level.getBlockEntity(pos);
      return blockEntity instanceof RandomizableContainerBlockEntity && blockEntity instanceof LidBlockEntity
         ? !level.getBlockState(pos).is(LOOTR_CONTAINERS)
         : false;
   }

   @Nullable
   private static Direction pairingDirection(ServerLevel level, BlockPos pos, BlockState state) {
      if (state.hasProperty(ChestBlock.TYPE) && state.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
         Direction connected = ChestBlock.getConnectedDirection(state);
         BlockPos partnerPos = pos.relative(connected);
         BlockState partnerState = level.getBlockState(partnerPos);
         boolean paired = partnerState.is(state.getBlock())
            && partnerState.hasProperty(ChestBlock.TYPE)
            && partnerState.getValue(ChestBlock.TYPE) == ((ChestType)state.getValue(ChestBlock.TYPE)).getOpposite()
            && partnerState.getValue(ChestBlock.FACING) == state.getValue(ChestBlock.FACING)
            && level.getBlockEntity(partnerPos) instanceof ChestBlockEntity;
         return paired ? connected : null;
      } else {
         return null;
      }
   }

   private static BlockState deliveredState(BlockState state, boolean paired) {
      BlockState result = state;
      if (!paired && result.hasProperty(ChestBlock.TYPE)) {
         result = (BlockState)result.setValue(ChestBlock.TYPE, ChestType.SINGLE);
      }

      if (result.hasProperty(ChestBlock.WATERLOGGED)) {
         result = (BlockState)result.setValue(ChestBlock.WATERLOGGED, false);
      }

      return result;
   }

   private static void clearAndRemove(ServerLevel level, BlockPos pos, RandomizableContainerBlockEntity chest) {
      chest.setLootTable(null);
      chest.clearContent();
      level.removeBlock(pos, false);
   }

   private static void spawnPortal(ServerLevel level, BlockPos pos, @Nullable BlockPos partner, int life) {
      double x = pos.getX() + 0.5;
      double z = pos.getZ() + 0.5;
      if (partner != null) {
         x = (x + partner.getX() + 0.5) / 2.0;
         z = (z + partner.getZ() + 0.5) / 2.0;
      }

      TeleportPortalEntity.spawn(level, x, pos.getY(), z, life);
   }

   private static BlockPos resolveTarget(ServerLevel level, ItemEntity itemEntity) {
      BlockPos pos = itemEntity.blockPosition();
      return !isFree(level, pos) && itemEntity.getY() - pos.getY() > 1.0E-4 ? pos.above() : pos;
   }

   private static boolean hasRoom(ServerLevel level, BlockPos pos) {
      if (level.isOutsideBuildHeight(pos)) {
         return false;
      } else if (isFree(level, pos) && isFree(level, pos.above())) {
         AABB area = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
         return level.getEntities((Entity)null, area, CargoTeleportHandler::blocksDelivery).isEmpty();
      } else {
         return false;
      }
   }

   private static boolean isFree(ServerLevel level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      if (state.isAir()) {
         return true;
      } else {
         return !state.getFluidState().isEmpty()
            ? false
            : state.canBeReplaced() || state.is(BlockTags.FLOWERS) || state.is(BlockTags.SAPLINGS) || state.is(BlockTags.CROPS);
      }
   }

   private static boolean blocksDelivery(Entity entity) {
      return entity instanceof HangingEntity || entity instanceof ArmorStand || entity instanceof Display;
   }
}
