package twilightforest.block.entity.spawner;

import com.mojang.datafixers.util.Either;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFBlockEntities;

public class SinisterSpawnerBlockEntity extends BlockEntity implements Spawner {
   private final SinisterSpawnerLogic spawner = new SinisterSpawnerLogic() {
      public Either<BlockEntity, Entity> getOwner() {
         return Either.left(SinisterSpawnerBlockEntity.this);
      }
   };
   @Nullable
   private ResourceKey<LootTable> lootTable = null;

   public SinisterSpawnerBlockEntity(BlockPos pos, BlockState blockState) {
      super((BlockEntityType)TFBlockEntities.SINISTER_SPAWNER.value(), pos, blockState);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.spawner.load(this.level, this.worldPosition, tag);
      if (tag.contains("LootTable")) {
         this.lootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(tag.getString("LootTable")));
      } else {
         this.lootTable = null;
      }
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      this.spawner.save(tag);
      if (this.lootTable != null) {
         tag.putString("LootTable", this.lootTable.location().toString());
      }
   }

   public static void clientTick(Level level, BlockPos pos, BlockState state, SinisterSpawnerBlockEntity blockEntity) {
      blockEntity.spawner.clientTick(level, pos);
   }

   public static void serverTick(Level level, BlockPos pos, BlockState state, SinisterSpawnerBlockEntity blockEntity) {
      blockEntity.spawner.serverTick((ServerLevel)level, pos);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag compoundtag = this.saveCustomOnly(registries);
      compoundtag.remove("SpawnPotentials");
      return compoundtag;
   }

   public boolean triggerEvent(int id, int type) {
      return this.spawner.onEventTriggered(this.level, id) || super.triggerEvent(id, type);
   }

   public boolean onlyOpCanSetNbt() {
      return true;
   }

   public void setEntityId(EntityType<?> type, RandomSource random) {
      this.spawner.setEntityId(type, this.level, random, this.worldPosition);
      this.setChanged();
   }

   public SinisterSpawnerLogic getSpawner() {
      return this.spawner;
   }

   public boolean setParticles(List<ParticleOptions> particles, boolean sendUpdate) {
      return this.getSpawner().setParticles(particles, sendUpdate);
   }

   public boolean addParticle(ParticleOptions particle, boolean sendUpdate) {
      return this.getSpawner().addParticle(particle, sendUpdate);
   }

   public boolean removeParticle(ParticleOptions particle, boolean sendUpdate) {
      return this.getSpawner().removeParticle(particle, sendUpdate);
   }

   public void sendChanges() {
      this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
   }

   @Nullable
   public ResourceKey<LootTable> getLootTableId() {
      return this.lootTable;
   }

   @Nullable
   public LootTable getLootTable() {
      if (this.level != null && this.lootTable != null) {
         MinecraftServer server = this.level.getServer();
         return server == null ? null : server.reloadableRegistries().getLootTable(this.lootTable);
      } else {
         return null;
      }
   }

   public boolean setLootTable(@Nullable ResourceKey<LootTable> lootTable) {
      boolean updated = this.lootTable != lootTable;
      this.lootTable = lootTable;
      return updated;
   }
}
