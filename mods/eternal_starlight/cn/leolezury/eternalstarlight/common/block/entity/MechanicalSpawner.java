package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.MechanicalSpawnerBlock;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.SpawnData.CustomSpawnRules;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

public abstract class MechanicalSpawner {
   public static final String TAG_SPAWN_DATA = "spawn_data";
   public static final String TAG_SPAWN_POTENTIALS = "spawn_potentials";
   private static final String TAG_SPAWN_DELAY = "spawn_delay";
   private static final String TAG_MIN_SPAWN_DELAY = "min_spawn_delay";
   private static final String TAG_MAX_SPAWN_DELAY = "max_spawn_delay";
   private static final String TAG_SPAWN_COUNT = "spawn_count";
   private static final String TAG_MAX_NEARBY_ENTITIES = "max_nearby_entities";
   private static final String TAG_REQUIRED_PLAYER_RANGE = "required_player_range";
   private static final String TAG_SPAWN_RANGE = "spawn_range";
   private static final int EVENT_SPAWN = 1;
   private int spawnDelay = 20;
   private SimpleWeightedRandomList<SpawnData> spawnPotentials = SimpleWeightedRandomList.empty();
   @Nullable
   private SpawnData nextSpawnData;
   private double spin;
   private double oSpin;
   private float animationScale;
   private float oAnimationScale;
   private int minSpawnDelay = 20;
   private int maxSpawnDelay = 30;
   private int spawnCount = 1;
   @Nullable
   private Entity displayEntity;
   private int maxNearbyEntities = 6;
   private int requiredPlayerRange = 16;
   private int spawnRange = 4;

   public void setEntityId(EntityType<?> type, @Nullable Level level, RandomSource random, BlockPos pos) {
      this.getOrCreateNextSpawnData(level, random, pos).getEntityToSpawn().putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(type).toString());
   }

   private boolean isNearPlayer(Level level, BlockPos pos, int range) {
      return !level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(range))
         .stream()
         .filter(
            player -> (level.isClientSide || inLineOfSight(level, pos.getCenter(), player.getEyePosition())) && !player.isCreative() && !player.isSpectator()
         )
         .toList()
         .isEmpty();
   }

   private static boolean inLineOfSight(Level level, Vec3 block, Vec3 target) {
      BlockHitResult blockHitResult = level.clip(new ClipContext(target, block, Block.VISUAL, Fluid.NONE, CollisionContext.empty()));
      return blockHitResult.getBlockPos().equals(BlockPos.containing(block)) || blockHitResult.getType() == Type.MISS;
   }

   public void clientTick(Level level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      this.oSpin = this.spin;
      this.oAnimationScale = this.animationScale;
      if (this.isNearPlayer(level, pos, this.requiredPlayerRange) && (Integer)state.getValue(MechanicalSpawnerBlock.POWER) > 0) {
         RandomSource random = level.getRandom();
         double x = pos.getX() + random.nextDouble();
         double y = pos.getY() + random.nextDouble() * 2.0 - 1.0;
         double z = pos.getZ() + random.nextDouble();
         level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
         level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
         if (this.spawnDelay > 0) {
            this.spawnDelay--;
         }

         this.spin = (this.spin + 1000.0 / (this.spawnDelay + 200.0)) % 360.0;
         this.animationScale = Mth.clamp(this.animationScale + 0.5F, 0.0F, 1.0F);
      } else {
         this.animationScale = Mth.clamp(this.animationScale - 0.2F, 0.0F, 1.0F);
      }
   }

   public void serverTick(ServerLevel serverLevel, BlockPos pos) {
      BlockState state = serverLevel.getBlockState(pos);
      RandomSource random = serverLevel.getRandom();
      if (this.isNearPlayer(serverLevel, pos, this.requiredPlayerRange)) {
         if (this.spawnDelay == -1) {
            this.delay(serverLevel, pos);
         }

         if (this.spawnDelay > 0) {
            if ((Integer)state.getValue(MechanicalSpawnerBlock.POWER) > 0) {
               this.spawnDelay--;
            }
         } else if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
            boolean success = false;
            SpawnData spawnData = this.getOrCreateNextSpawnData(serverLevel, random, pos);

            for (int i = 0; i < this.spawnCount; i++) {
               CompoundTag entityToSpawn = spawnData.getEntityToSpawn();
               Optional<EntityType<?>> type = EntityType.by(entityToSpawn);
               if (type.isEmpty()) {
                  this.delay(serverLevel, pos);
                  return;
               }

               ListTag posTag = entityToSpawn.getList("Pos", 6);
               int posSize = posTag.size();
               double x = posSize >= 1 ? posTag.getDouble(0) : pos.getX() + (random.nextDouble() - random.nextDouble()) * this.spawnRange + 0.5;
               double y = posSize >= 2 ? posTag.getDouble(1) : pos.getY() + random.nextInt(3) - 1;
               double z = posSize >= 3 ? posTag.getDouble(2) : pos.getZ() + (random.nextDouble() - random.nextDouble()) * this.spawnRange + 0.5;
               if (serverLevel.noCollision(type.get().getSpawnAABB(x, y, z)) && inLineOfSight(serverLevel, pos.getCenter(), new Vec3(x, y, z))) {
                  BlockPos spawnPos = BlockPos.containing(x, y, z);
                  if (spawnData.getCustomSpawnRules().isPresent()) {
                     if (!type.get().getCategory().isFriendly() && serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
                        continue;
                     }

                     CustomSpawnRules rules = (CustomSpawnRules)spawnData.getCustomSpawnRules().get();
                     if (!rules.isValidPosition(spawnPos, serverLevel)) {
                        continue;
                     }
                  } else if (!SpawnPlacements.checkSpawnRules(type.get(), serverLevel, MobSpawnType.TRIAL_SPAWNER, spawnPos, serverLevel.getRandom())) {
                     continue;
                  }

                  Entity entity = EntityType.loadEntityRecursive(entityToSpawn, serverLevel, e -> {
                     e.moveTo(x, y, z, e.getYRot(), e.getXRot());
                     return e;
                  });
                  if (entity == null) {
                     this.delay(serverLevel, pos);
                     return;
                  }

                  int entitiesCount = serverLevel.getEntities(
                        EntityTypeTest.forExactClass(entity.getClass()),
                        new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).inflate(this.spawnRange),
                        EntitySelector.NO_SPECTATORS
                     )
                     .size();
                  if (entitiesCount >= this.maxNearbyEntities) {
                     this.delay(serverLevel, pos);
                     return;
                  }

                  entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), random.nextFloat() * 360.0F, 0.0F);
                  if (entity instanceof Mob mob) {
                     if (!mob.checkSpawnObstruction(serverLevel)) {
                        continue;
                     }

                     boolean shouldFinalize = spawnData.getEntityToSpawn().size() == 1 && spawnData.getEntityToSpawn().contains("id", 8);
                     if (shouldFinalize) {
                        mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.TRIAL_SPAWNER, null);
                     }

                     spawnData.getEquipment().ifPresent(mob::equip);
                  }

                  if (!serverLevel.tryAddFreshEntityWithPassengers(entity)) {
                     this.delay(serverLevel, pos);
                     return;
                  }

                  serverLevel.levelEvent(2004, pos, 0);
                  serverLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, spawnPos);
                  if (entity instanceof Mob mob) {
                     mob.spawnAnim();
                  }

                  for (int j = 0; j <= 10; j++) {
                     ESPlatform.INSTANCE
                        .sendToAllClients(
                           serverLevel,
                           new ParticlePacket(
                              ExplosionShockParticleOptions.ENERGY,
                              entity.getX() + (random.nextFloat() - 0.5F) * entity.getBbWidth() * 1.2F,
                              entity.getY(),
                              entity.getZ() + (random.nextFloat() - 0.5F) * entity.getBbWidth() * 1.2F,
                              0.0,
                              1.0,
                              0.0
                           )
                        );
                  }

                  success = true;
               }
            }

            if (success) {
               this.delay(serverLevel, pos);
            }
         }
      }
   }

   private void delay(Level level, BlockPos pos) {
      RandomSource random = level.getRandom();
      if (this.maxSpawnDelay <= this.minSpawnDelay) {
         this.spawnDelay = this.minSpawnDelay;
      } else {
         this.spawnDelay = this.minSpawnDelay + random.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
      }

      BlockState state = level.getBlockState(pos);
      this.spawnDelay = this.spawnDelay * (state.hasProperty(MechanicalSpawnerBlock.POWER) ? 16 - (Integer)state.getValue(MechanicalSpawnerBlock.POWER) : 1);
      this.spawnPotentials.getRandom(random).ifPresent(wrapper -> this.setNextSpawnData(level, pos, (SpawnData)wrapper.data()));
      this.broadcastEvent(level, pos, 1);
   }

   public void load(@Nullable Level level, BlockPos pos, CompoundTag tag) {
      this.spawnDelay = tag.getShort("spawn_delay");
      boolean hasSpawnData = tag.contains("spawn_data", 10);
      if (hasSpawnData) {
         SpawnData data = (SpawnData)SpawnData.CODEC
            .parse(NbtOps.INSTANCE, tag.getCompound("spawn_data"))
            .resultOrPartial(s -> EternalStarlight.LOGGER.warn("Invalid SpawnData: {}", s))
            .orElseGet(SpawnData::new);
         this.setNextSpawnData(level, pos, data);
      }

      boolean hasSpawnPotentials = tag.contains("spawn_potentials", 9);
      if (hasSpawnPotentials) {
         ListTag list = tag.getList("spawn_potentials", 10);
         this.spawnPotentials = (SimpleWeightedRandomList<SpawnData>)SpawnData.LIST_CODEC
            .parse(NbtOps.INSTANCE, list)
            .resultOrPartial(s -> EternalStarlight.LOGGER.warn("Invalid SpawnPotentials list: {}", s))
            .orElseGet(SimpleWeightedRandomList::empty);
      } else {
         this.spawnPotentials = SimpleWeightedRandomList.single(this.nextSpawnData != null ? this.nextSpawnData : new SpawnData());
      }

      if (tag.contains("min_spawn_delay", 99)) {
         this.minSpawnDelay = tag.getShort("min_spawn_delay");
      }

      if (tag.contains("max_spawn_delay", 99)) {
         this.maxSpawnDelay = tag.getShort("max_spawn_delay");
      }

      if (tag.contains("spawn_count", 99)) {
         this.spawnCount = tag.getShort("spawn_count");
      }

      if (tag.contains("max_nearby_entities", 99)) {
         this.maxNearbyEntities = tag.getShort("max_nearby_entities");
      }

      if (tag.contains("required_player_range", 99)) {
         this.requiredPlayerRange = tag.getShort("required_player_range");
      }

      if (tag.contains("spawn_range", 99)) {
         this.spawnRange = tag.getShort("spawn_range");
      }

      this.displayEntity = null;
   }

   public CompoundTag save(CompoundTag tag) {
      tag.putShort("spawn_delay", (short)this.spawnDelay);
      tag.putShort("min_spawn_delay", (short)this.minSpawnDelay);
      tag.putShort("max_spawn_delay", (short)this.maxSpawnDelay);
      tag.putShort("spawn_count", (short)this.spawnCount);
      tag.putShort("max_nearby_entities", (short)this.maxNearbyEntities);
      tag.putShort("required_player_range", (short)this.requiredPlayerRange);
      tag.putShort("spawn_range", (short)this.spawnRange);
      if (this.nextSpawnData != null) {
         tag.put(
            "spawn_data",
            (Tag)SpawnData.CODEC.encodeStart(NbtOps.INSTANCE, this.nextSpawnData).getOrThrow(s -> new IllegalStateException("Invalid SpawnData: " + s))
         );
      }

      tag.put("spawn_potentials", (Tag)SpawnData.LIST_CODEC.encodeStart(NbtOps.INSTANCE, this.spawnPotentials).getOrThrow());
      return tag;
   }

   @Nullable
   public Entity getOrCreateDisplayEntity(Level level, BlockPos pos) {
      if (this.displayEntity == null) {
         CompoundTag tag = this.getOrCreateNextSpawnData(level, level.getRandom(), pos).getEntityToSpawn();
         if (!tag.contains("id", 8)) {
            return null;
         }

         this.displayEntity = EntityType.loadEntityRecursive(tag, level, Function.identity());
      }

      return this.displayEntity;
   }

   public boolean onEventTriggered(Level level, BlockState state, int id) {
      if (id == 1) {
         if (level.isClientSide) {
            this.spawnDelay = this.minSpawnDelay
               * (state.hasProperty(MechanicalSpawnerBlock.POWER) ? 16 - (Integer)state.getValue(MechanicalSpawnerBlock.POWER) : 1);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void setNextSpawnData(@Nullable Level level, BlockPos pos, SpawnData nextSpawnData) {
      this.nextSpawnData = nextSpawnData;
   }

   private SpawnData getOrCreateNextSpawnData(@Nullable Level level, RandomSource random, BlockPos pos) {
      if (this.nextSpawnData == null) {
         this.setNextSpawnData(level, pos, this.spawnPotentials.getRandom(random).<SpawnData>map(Wrapper::data).orElseGet(SpawnData::new));
      }

      return this.nextSpawnData;
   }

   public abstract void broadcastEvent(Level var1, BlockPos var2, int var3);

   public double getSpin() {
      return this.spin;
   }

   public double getOSpin() {
      return this.oSpin;
   }

   public float getAnimationScale() {
      return this.animationScale;
   }

   public float getOAnimationScale() {
      return this.oAnimationScale;
   }
}
