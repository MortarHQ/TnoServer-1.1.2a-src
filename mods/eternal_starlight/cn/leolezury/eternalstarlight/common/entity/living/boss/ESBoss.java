package cn.leolezury.eternalstarlight.common.entity.living.boss;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.LootChestBlock;
import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.BossSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviorUser;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.GlobalVec3;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ESBoss extends Monster implements MultiBehaviorUser {
   private static final String TAG_INITIAL_POS = "initial_pos";
   private static final String TAG_SPAWNED = "spawned";
   private static final String TAG_PHASE = "phase";
   private static final String TAG_ACTIVATED = "activated";
   protected final List<UUID> fightParticipants = new ArrayList<>();
   protected static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> BEHAVIOR_STATE = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> BEHAVIOR_TICKS = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.BOOLEAN);
   private GlobalVec3 initialPos = GlobalVec3.of(Level.OVERWORLD, Vec3.ZERO);
   private boolean spawned = false;

   protected ESBoss(EntityType<? extends ESBoss> type, Level level) {
      super(type, level);
      if (level.isClientSide) {
         ESClientHandler.BOSSES.add(this);
      }
   }

   public int getPhase() {
      return (Integer)this.getEntityData().get(PHASE);
   }

   public void setPhase(int phase) {
      this.getEntityData().set(PHASE, phase);
   }

   @Override
   public int getBehaviorState() {
      return (Integer)this.getEntityData().get(BEHAVIOR_STATE);
   }

   @Override
   public void setBehaviorState(int behaviourState) {
      this.getEntityData().set(BEHAVIOR_STATE, behaviourState);
   }

   @Override
   public int getBehaviorTicks() {
      return (Integer)this.getEntityData().get(BEHAVIOR_TICKS);
   }

   @Override
   public void setBehaviorTicks(int behaviourTicks) {
      this.getEntityData().set(BEHAVIOR_TICKS, behaviourTicks);
   }

   public boolean isActivated() {
      return (Boolean)this.getEntityData().get(ACTIVATED);
   }

   public void setActivated(boolean activated) {
      this.getEntityData().set(ACTIVATED, activated);
      if (activated) {
         this.setBehaviorState(0);
         this.setBehaviorTicks(0);
      }
   }

   public GlobalVec3 getInitialPos() {
      return this.initialPos;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BEHAVIOR_STATE, 0).define(BEHAVIOR_TICKS, 0).define(PHASE, 0).define(ACTIVATED, true);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      spawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
      this.initializeBossOnFirstSpawn();
      return spawnGroupData;
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("initial_pos")) {
         GlobalVec3.CODEC
            .parse(NbtOps.INSTANCE, compoundTag.get("initial_pos"))
            .resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse boss initial pos: {}", s))
            .ifPresent(pos -> this.initialPos = pos);
      }

      this.spawned = compoundTag.getBoolean("spawned");
      this.setPhase(compoundTag.getInt("phase"));
      if (compoundTag.contains("activated", 3)) {
         this.setActivated(compoundTag.getBoolean("activated"));
      }
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.put("initial_pos", (Tag)GlobalVec3.CODEC.encodeStart(NbtOps.INSTANCE, this.initialPos).getOrThrow());
      compoundTag.putBoolean("spawned", this.spawned);
      compoundTag.putInt("phase", this.getPhase());
      compoundTag.putBoolean("activated", this.isActivated());
   }

   public boolean hurt(DamageSource source, float amount) {
      boolean success = super.hurt(source, amount);
      if (success && source.getEntity() instanceof ServerPlayer player && !this.fightParticipants.contains(player.getUUID())) {
         this.fightParticipants.add(player.getUUID());
      }

      return success;
   }

   public void die(DamageSource source) {
      if (!this.level().isClientSide) {
         for (UUID uuid : this.fightParticipants) {
            Player player = this.level().getPlayerByUUID(uuid);
            if (player instanceof ServerPlayer serverPlayer && player.isAlive() && player.level().dimension() == this.level().dimension()) {
               CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger(serverPlayer, this, source);
            }
         }
      }

      super.die(source);
   }

   public boolean removeWhenFarAway(double dist) {
      return false;
   }

   public boolean canChangeDimensions(Level level, Level level1) {
      return false;
   }

   public boolean startRiding(Entity entity, boolean bl) {
      return false;
   }

   public boolean canBossMove() {
      return true;
   }

   public void initializeBoss() {
      this.initialPos = GlobalVec3.of(this.level().dimension(), this.position());
   }

   public void initializeBossOnFirstSpawn() {
      if (!this.spawned) {
         this.initializeBoss();
         this.spawned = true;
      }
   }

   public boolean shouldPlayBossMusic() {
      return this.isAlive() && this.getBossMusic() != null;
   }

   public SoundEvent getBossMusic() {
      return ESSoundEvents.MUSIC_BOSS_GATEKEEPER.get();
   }

   public ResourceKey<LootTable> getBossLootTable() {
      return ResourceKey.create(Registries.LOOT_TABLE, BuiltInRegistries.ENTITY_TYPE.getKey(this.getType()).withPrefix("bosses/"));
   }

   public ItemStack getBossLootBag() {
      ItemStack lootBag = new ItemStack((ItemLike)ESItems.LOOT_BAG.get());
      lootBag.applyComponentsAndValidate(
         DataComponentPatch.builder()
            .set(DataComponents.LORE, new ItemLore(List.of(this.getDisplayName())))
            .set(ESDataComponents.LOOT_TABLE.get(), this.getBossLootTable())
            .build()
      );
      return lootBag;
   }

   public void remove(RemovalReason reason) {
      if (reason == RemovalReason.KILLED) {
         this.trySpawnLoot();
         if (ESConfig.INSTANCE.enableBossRespawn) {
            BlockState spawnerState = this.getBossSpawner();
            if (!spawnerState.isAir() && this.initialPos.dimension() == this.level().dimension()) {
               BlockPos spawnerPos = BlockPos.containing(this.initialPos.pos());
               if (this.canBossSpawnerReplace(spawnerPos, this.level().getBlockState(spawnerPos))) {
                  this.level().setBlockAndUpdate(spawnerPos, spawnerState);
                  if (this.level().getBlockEntity(spawnerPos) instanceof BossSpawnerBlockEntity<?> blockEntity) {
                     blockEntity.setSpawnCooldown(ESConfig.INSTANCE.bossRespawnCooldown);
                  }
               }
            }
         }
      }

      super.remove(reason);
   }

   protected BlockState getBossSpawner() {
      return Blocks.AIR.defaultBlockState();
   }

   protected boolean shouldSpawnLoot() {
      return this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);
   }

   protected boolean shouldSpawnLootChest() {
      return ESConfig.INSTANCE.enableLootChest && !this.fightParticipants.isEmpty();
   }

   protected void trySpawnLoot() {
      if (this.level() instanceof ServerLevel serverLevel && this.shouldSpawnLoot() && !this.spawnBossLootChest(serverLevel)) {
         ItemStack lootBag = this.getBossLootBag();
         if (this.fightParticipants.stream().noneMatch(uuid -> this.level().getPlayerByUUID(uuid) != null)) {
            ItemEntity item = this.spawnAtLocation(lootBag.copy());
            if (item != null) {
               ESDataAttachments.IMPORTANT_ITEM.setData(item, true);
               item.setGlowingTag(true);
               item.setExtendedLifetime();
            }
         }

         for (UUID uuid : this.fightParticipants) {
            Player player = this.level().getPlayerByUUID(uuid);
            if (player != null && player.isAlive() && player.level().dimension() == this.level().dimension()) {
               ItemEntity item = player.spawnAtLocation(lootBag.copy());
               if (item != null) {
                  ESDataAttachments.IMPORTANT_ITEM.setData(item, true);
                  item.setTarget(player.getUUID());
                  item.setGlowingTag(true);
                  item.setExtendedLifetime();
               }
            }
         }
      }

      for (UUID uuid : this.fightParticipants) {
         Player player = this.level().getPlayerByUUID(uuid);
         if (player instanceof ServerPlayer serverPlayer && player.isAlive() && player.level().dimension() == this.level().dimension()) {
            this.grantSpecialLoot(serverPlayer);
         }
      }
   }

   protected void grantSpecialLoot(ServerPlayer player) {
   }

   protected boolean canBossSpawnerReplace(BlockPos pos, BlockState state) {
      return state.isAir() || state.canBeReplaced() && ESPlatform.INSTANCE.postEntityDestroyBlockEvent(this.level(), pos, this);
   }

   protected boolean canBossLootChestReplace(BlockPos pos, BlockState state) {
      return state.isAir() || state.canBeReplaced() && ESPlatform.INSTANCE.postEntityDestroyBlockEvent(this.level(), pos, this);
   }

   protected Optional<BlockPos> getLootChestPos() {
      BlockPos chestPos = this.blockPosition();

      while (this.canBossLootChestReplace(chestPos, this.level().getBlockState(chestPos)) && chestPos.getY() > this.level().getMinBuildHeight()) {
         chestPos = chestPos.below();
      }

      chestPos = chestPos.above();
      return this.shouldSpawnLootChest() && this.canBossLootChestReplace(chestPos, this.level().getBlockState(chestPos))
         ? Optional.of(chestPos)
         : Optional.empty();
   }

   protected boolean spawnBossLootChest(ServerLevel serverLevel) {
      Optional<BlockPos> possibleChestPos = this.getLootChestPos();
      if (possibleChestPos.isPresent()) {
         BlockPos chestPos = possibleChestPos.get();
         serverLevel.setBlockAndUpdate(
            chestPos,
            (BlockState)((LootChestBlock)ESBlocks.LOOT_CHEST.get())
               .defaultBlockState()
               .setValue(LootChestBlock.FACING, Plane.HORIZONTAL.getRandomDirection(serverLevel.getRandom()))
         );
         if (serverLevel.getBlockEntity(chestPos) instanceof LootChestBlockEntity blockEntity) {
            blockEntity.setLootTable(this.getBossLootTable());

            for (UUID uuid : this.fightParticipants) {
               blockEntity.addRewardTarget(uuid);
            }

            this.modifyBossLootChest(blockEntity);
            return true;
         }
      }

      return false;
   }

   protected void modifyBossLootChest(LootChestBlockEntity blockEntity) {
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.initializeBossOnFirstSpawn();
         if (!this.canBossMove() && this.level().dimension() == this.initialPos.dimension()) {
            this.setPos(this.initialPos.pos().x, this.position().y, this.initialPos.pos().z);
         }

         this.fightParticipants.removeIf(uuid -> {
            Player player = this.level().getPlayerByUUID(uuid);
            return player == null || !player.isAlive();
         });
      }
   }
}
