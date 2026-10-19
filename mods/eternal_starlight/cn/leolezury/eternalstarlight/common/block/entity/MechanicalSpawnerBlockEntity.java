package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MechanicalSpawnerBlockEntity extends BlockEntity implements Spawner {
   private final MechanicalSpawner spawner = new MechanicalSpawner() {
      @Override
      public void broadcastEvent(Level level, BlockPos pos, int id) {
         level.blockEvent(pos, (Block)ESBlocks.MECHANICAL_SPAWNER.get(), id, 0);
      }

      @Override
      public void setNextSpawnData(@Nullable Level level, BlockPos pos, SpawnData data) {
         super.setNextSpawnData(level, pos, data);
         if (level != null) {
            BlockState blockstate = level.getBlockState(pos);
            level.sendBlockUpdated(pos, blockstate, blockstate, 4);
         }
      }
   };
   public AnimationState idleAnimationState = new AnimationState();
   public int clientTickCount = 0;

   public MechanicalSpawnerBlockEntity(BlockPos pos, BlockState blockState) {
      super(ESBlockEntities.MECHANICAL_SPAWNER.get(), pos, blockState);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.spawner.load(this.level, this.worldPosition, tag);
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      this.spawner.save(tag);
   }

   public static void clientTick(Level level, BlockPos pos, BlockState state, MechanicalSpawnerBlockEntity blockEntity) {
      blockEntity.spawner.clientTick(level, pos);
      blockEntity.clientTickCount++;
      blockEntity.idleAnimationState.startIfStopped(blockEntity.clientTickCount);
   }

   public static void serverTick(Level level, BlockPos pos, BlockState state, MechanicalSpawnerBlockEntity blockEntity) {
      if (level instanceof ServerLevel serverLevel) {
         blockEntity.spawner.serverTick(serverLevel, pos);
      }
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag tag = this.saveCustomOnly(registries);
      tag.remove("spawn_potentials");
      return tag;
   }

   public boolean triggerEvent(int id, int type) {
      return this.level == null ? false : this.spawner.onEventTriggered(this.level, this.getBlockState(), id) || super.triggerEvent(id, type);
   }

   public boolean onlyOpCanSetNbt() {
      return true;
   }

   public void setEntityId(EntityType<?> type, RandomSource random) {
      this.spawner.setEntityId(type, this.level, random, this.worldPosition);
      this.setChanged();
   }

   public MechanicalSpawner getSpawner() {
      return this.spawner;
   }
}
