package com.tensurabossstructure.block.entity;

import com.tensurabossstructure.compat.ElementalColossusSpawnerSupport;
import com.tensurabossstructure.config.BossConfig;
import com.tensurabossstructure.event.SpawnerRespawnHandler;
import com.tensurabossstructure.registry.ModBlockEntities;
import com.tensurabossstructure.world.SpawnerRespawnData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BossSpawnerBlockEntity extends BlockEntity {
   private static final int DEFAULT_DETECTION_RANGE = 16;
   private static final int HINATA_DETECTION_RANGE = 25;
   private static final int ORC_DISASTER_DETECTION_RANGE = 36;
   private static final int LUMINOUS_DETECTION_RANGE = 30;
   private static final int CARRION_DETECTION_RANGE = 128;
   private static final int ELEMENTAL_COLOSSUS_DETECTION_RANGE = 30;
   private static final int OTHERWORLDER_DETECTION_RANGE = 36;
   private static final int SHIZU_DETECTION_RANGE = 30;
   private static final int CHILD_OTHERWORLDER_DETECTION_RANGE = 64;
   private boolean hasSpawned = false;
   private String entityId;

   public BossSpawnerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.BOSS_SPAWNER.get(), pos, state);
      this.entityId = "";
   }

   public BossSpawnerBlockEntity(BlockPos pos, BlockState state, String entityId) {
      super((BlockEntityType)ModBlockEntities.BOSS_SPAWNER.get(), pos, state);
      this.entityId = entityId;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, BossSpawnerBlockEntity blockEntity) {
      if (!blockEntity.hasSpawned) {
         int detectionRange = blockEntity.getDetectionRange();
         boolean playerNearby = level.hasNearbyAlivePlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, detectionRange);
         if (playerNearby) {
            if (!level.isClientSide() && level.getDifficulty() != Difficulty.PEACEFUL) {
               ServerLevel serverLevel = (ServerLevel)level;
               Entity boss = blockEntity.spawnBoss(serverLevel, pos);
               if (boss != null) {
                  if ((Boolean)BossConfig.SPAWNER_RECOVERY_ENABLED.get()
                     && !"random_otherworlder".equals(blockEntity.entityId)
                     && !"random_child_otherworlder".equals(blockEntity.entityId)) {
                     SpawnerRespawnHandler.markBoss(boss);
                     ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
                     int cooldownTicks = (Integer)BossConfig.SPAWNER_RECOVERY_COOLDOWN_MINUTES.get() * 60 * 20;
                     SpawnerRespawnData data = SpawnerRespawnData.get(serverLevel);
                     data.addEntry(pos, blockId, serverLevel.dimension().location(), cooldownTicks, boss.getUUID());
                  }

                  level.destroyBlock(pos, false);
                  blockEntity.hasSpawned = true;
               }
            }
         }
      }
   }

   private int getDetectionRange() {
      if (this.entityId != null && this.entityId.contains("hinata")) {
         return 25;
      } else if (this.entityId != null && this.entityId.contains("orc_disaster")) {
         return 36;
      } else if (this.entityId != null && this.entityId.contains("luminous")) {
         return 30;
      } else if (this.entityId != null && this.entityId.contains("elemental_colossus")) {
         return 30;
      } else if ("random_otherworlder".equals(this.entityId)) {
         return 36;
      } else if (this.entityId != null && this.entityId.contains("carrion")) {
         return 128;
      } else if (this.entityId != null && this.entityId.contains("shizu")) {
         return 30;
      } else {
         return "random_child_otherworlder".equals(this.entityId) ? 64 : 16;
      }
   }

   private Entity spawnBoss(ServerLevel level, BlockPos pos) {
      if (this.entityId != null && !this.entityId.isEmpty()) {
         String actualEntityId = this.entityId;
         if ("random_otherworlder".equals(this.entityId)) {
            String[] otherworlders = new String[]{
               "tensura:kirara_mizutani",
               "tensura:kyoya_tachibana",
               "tensura:mai_furuki",
               "tensura:mark_lauren",
               "tensura:shinji_tanimura",
               "tensura:shin_ryusei",
               "tensura:shizu",
               "tensura:shogo_taguchi",
               "tensura:folgen"
            };
            actualEntityId = otherworlders[level.getRandom().nextInt(otherworlders.length)];
         }

         if ("random_child_otherworlder".equals(this.entityId)) {
            String[] childOtherworlders = new String[]{
               "tensura:shin_ryusei",
               "tensura:kirara_mizutani",
               "tensura:kyoya_tachibana",
               "tensura:mai_furuki",
               "tensura:shogo_taguchi",
               "tensura:mark_lauren",
               "tensura:shinji_tanimura"
            };
            actualEntityId = childOtherworlders[level.getRandom().nextInt(childOtherworlders.length)];
         }

         EntityType<?> entityType = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(actualEntityId));
         if (entityType == null) {
            return null;
         }

         if (entityType.create(level) instanceof Mob mob) {
            BlockPos spawnPos = pos.below();
            mob.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, level.getRandom().nextFloat() * 360.0F, 0.0F);
            mob.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.SPAWNER, null);
            ElementalColossusSpawnerSupport.prepareSpawnedMob(mob, actualEntityId);
            mob.restrictTo(pos, 50);
            if ("random_child_otherworlder".equals(this.entityId) && mob instanceof AgeableMob ageable) {
               ageable.setBaby(true);
            }

            return level.addFreshEntity(mob) ? mob : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.putBoolean("HasSpawned", this.hasSpawned);
      tag.putString("EntityId", this.entityId);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.hasSpawned = tag.getBoolean("HasSpawned");
      this.entityId = tag.getString("EntityId");
   }
}
