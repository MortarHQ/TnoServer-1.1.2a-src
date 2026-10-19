package com.iafenvoy.iceandfire.item.block.entity;

import com.iafenvoy.iceandfire.entity.util.DreadSpawnerBaseLogic;
import com.iafenvoy.iceandfire.registry.IafBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DreadSpawnerBlockEntity extends BlockEntity implements Spawner {
   private final DreadSpawnerBaseLogic spawner = new DreadSpawnerBaseLogic() {
      public void broadcastEvent(Level world, BlockPos pos, int status) {
         world.blockEvent(pos, Blocks.SPAWNER, status, 0);
      }

      public void setNextSpawnData(Level world, BlockPos pos, SpawnData spawnEntry) {
         super.setNextSpawnData(world, pos, spawnEntry);
         if (world != null) {
            BlockState blockstate = world.getBlockState(pos);
            world.sendBlockUpdated(pos, blockstate, blockstate, 4);
         }
      }
   };

   public DreadSpawnerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafBlockEntities.DREAD_SPAWNER.get(), pos, state);
   }

   public void loadAdditional(CompoundTag nbt, Provider registryLookup) {
      super.loadAdditional(nbt, registryLookup);
      this.spawner.load(this.level, this.worldPosition, nbt);
   }

   public CompoundTag save(CompoundTag nbt, Provider registryLookup) {
      super.saveAdditional(nbt, registryLookup);
      this.spawner.save(nbt);
      return nbt;
   }

   public ClientboundBlockEntityDataPacket toUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider registryLookup) {
      CompoundTag compoundtag = this.save(new CompoundTag(), registryLookup);
      compoundtag.remove("SpawnPotentials");
      return compoundtag;
   }

   public boolean triggerEvent(int p_59797_, int p_59798_) {
      return this.spawner.onEventTriggered(this.level, p_59797_) || super.triggerEvent(p_59797_, p_59798_);
   }

   public boolean onlyOpCanSetNbt() {
      return true;
   }

   public BaseSpawner getLogic() {
      return this.spawner;
   }

   public static void clientTick(Level world, BlockPos pos, BlockState state, DreadSpawnerBlockEntity blockEntity) {
      blockEntity.spawner.clientTick(world, pos);
   }

   public static void serverTick(Level world, BlockPos pos, BlockState state, DreadSpawnerBlockEntity blockEntity) {
      blockEntity.spawner.serverTick((ServerLevel)world, pos);
   }

   public void setEntityId(EntityType<?> type, RandomSource random) {
      this.spawner.setEntityId(type, this.level, random, this.worldPosition);
      this.setChanged();
   }
}
