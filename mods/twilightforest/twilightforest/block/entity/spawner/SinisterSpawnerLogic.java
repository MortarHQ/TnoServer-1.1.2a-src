package twilightforest.block.entity.spawner;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.SpawnData.CustomSpawnRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFBlocks;
import twilightforest.util.BoundingBoxUtils;

public abstract class SinisterSpawnerLogic extends BaseSpawner {
   private static final Codec<List<ParticleOptions>> PARTICLES_CODEC = ParticleTypes.CODEC.listOf();
   @Nullable
   private MutableBlockPos checkPos = null;
   private final List<BlockPos> spawnBuffer = new ArrayList<>();
   private int countNextToSpawn = 0;
   public int entityScanRange = 4;
   private Set<ParticleOptions> particleOptions = new HashSet<>();

   public void setChanged() {
      Either<BlockEntity, Entity> owner = this.getOwner();
      if (owner != null) {
         owner.ifLeft(blockEntity -> {
            blockEntity.setChanged();
            if (blockEntity instanceof SinisterSpawnerBlockEntity sinisterSpawnerBlockEntity) {
               sinisterSpawnerBlockEntity.sendChanges();
            }
         });
      }
   }

   public boolean setParticles(Collection<ParticleOptions> particleOptions, boolean sendUpdate) {
      this.particleOptions.clear();
      boolean changed = this.particleOptions.addAll(particleOptions);
      if (sendUpdate && changed) {
         this.setChanged();
      }

      return changed;
   }

   public boolean addParticle(ParticleOptions particle, boolean sendUpdate) {
      boolean changed = this.particleOptions.add(particle);
      if (sendUpdate && changed) {
         this.setChanged();
      }

      return changed;
   }

   public boolean removeParticle(ParticleOptions particle, boolean sendUpdate) {
      boolean changed = this.particleOptions.remove(particle);
      if (sendUpdate && changed) {
         this.setChanged();
      }

      return changed;
   }

   public void clientTick(Level level, BlockPos pos) {
      if (!this.isNearPlayer(level, pos)) {
         this.oSpin = this.spin;
      } else if (this.displayEntity != null) {
         RandomSource randomsource = level.getRandom();
         double pX = pos.getX() + randomsource.nextDouble();
         double pY = pos.getY() + randomsource.nextDouble();
         double pZ = pos.getZ() + randomsource.nextDouble();

         for (ParticleOptions particleOptions : this.particleOptions) {
            level.addParticle(particleOptions, pX, pY, pZ, 0.0, 0.0, 0.0);
         }

         if (this.spawnDelay > 0) {
            this.spawnDelay--;
         }

         this.oSpin = this.spin;
         this.spin = (this.spin + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0;
      }
   }

   public void serverTick(ServerLevel serverLevel, BlockPos blockEntityPos) {
      if (this.isNearPlayer(serverLevel, blockEntityPos)) {
         if (this.countNextToSpawn < 1) {
            this.countNextToSpawn = serverLevel.getRandom().nextInt(1 + Math.max(0, this.spawnCount));
         }

         if (this.spawnDelay <= -1) {
            this.delay(serverLevel, blockEntityPos);
         }

         this.scanSpawnPositions(serverLevel, blockEntityPos, serverLevel.getRandom());
         if (this.spawnDelay > 0) {
            this.spawnDelay--;
         } else if (this.spawnBuffer.isEmpty()) {
            if (this.spawnDelay == 0) {
               this.spawnDelay--;
            }
         } else {
            boolean spawned = false;
            RandomSource randomsource = serverLevel.getRandom();
            SpawnData spawndata = this.getOrCreateNextSpawnData(serverLevel, randomsource, blockEntityPos);

            for (BlockPos spawnAt : this.spawnBuffer) {
               CompoundTag entityData = spawndata.getEntityToSpawn();
               Optional<EntityType<?>> possibleEntityType = EntityType.by(entityData);
               if (possibleEntityType.isEmpty()) {
                  this.delay(serverLevel, blockEntityPos);
                  return;
               }

               double spawnX = spawnAt.getX() + 0.5;
               double spawnY = spawnAt.getY();
               double spawnZ = spawnAt.getZ() + 0.5;
               if (!serverLevel.noCollision(possibleEntityType.get().getSpawnAABB(spawnX, spawnY, spawnZ))) {
                  continue;
               }

               if (spawndata.getCustomSpawnRules().isPresent()) {
                  if (!possibleEntityType.get().getCategory().isFriendly() && serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
                     continue;
                  }

                  CustomSpawnRules spawndata$customspawnrules = (CustomSpawnRules)spawndata.getCustomSpawnRules().get();
                  if (!spawndata$customspawnrules.isValidPosition(spawnAt, serverLevel)) {
                     continue;
                  }
               } else if (!SpawnPlacements.checkSpawnRules(possibleEntityType.get(), serverLevel, MobSpawnType.SPAWNER, spawnAt, serverLevel.getRandom())) {
                  continue;
               }

               Entity entity = EntityType.loadEntityRecursive(entityData, serverLevel, spawnedEntity -> {
                  spawnedEntity.moveTo(spawnX, spawnY, spawnZ, spawnedEntity.getYRot(), spawnedEntity.getXRot());
                  return spawnedEntity;
               });
               if (entity == null) {
                  this.delay(serverLevel, blockEntityPos);
                  return;
               }

               int likeEntities = serverLevel.getEntities(
                     EntityTypeTest.forExactClass(entity.getClass()),
                     new AABB(
                           blockEntityPos.getX(),
                           blockEntityPos.getY(),
                           blockEntityPos.getZ(),
                           blockEntityPos.getX() + 1,
                           blockEntityPos.getY() + 1,
                           blockEntityPos.getZ() + 1
                        )
                        .inflate(this.entityScanRange),
                     EntitySelector.NO_SPECTATORS
                  )
                  .size();
               if (likeEntities >= this.maxNearbyEntities) {
                  this.delay(serverLevel, blockEntityPos);
                  return;
               }

               entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), randomsource.nextFloat() * 360.0F, 0.0F);
               if (entity instanceof Mob mob) {
                  if (!EventHooks.checkSpawnPositionSpawner(mob, serverLevel, MobSpawnType.SPAWNER, spawndata, this)) {
                     continue;
                  }

                  boolean flag1 = spawndata.getEntityToSpawn().size() == 1 && spawndata.getEntityToSpawn().contains("id", 8);
                  EventHooks.finalizeMobSpawnSpawner(
                     mob, serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER, null, this, flag1
                  );
                  spawndata.getEquipment().ifPresent(mob::equip);
                  this.checkPos = blockEntityPos.mutable();
               }

               if (!serverLevel.tryAddFreshEntityWithPassengers(entity)) {
                  this.delay(serverLevel, blockEntityPos);
                  return;
               }

               for (ParticleOptions particle : this.particleOptions) {
                  serverLevel.sendParticles(
                     particle,
                     entity.getX(),
                     entity.getY(0.5),
                     entity.getZ(),
                     10,
                     entity.getBbWidth() * 0.5F,
                     entity.getBbHeight() * 0.5F,
                     entity.getBbWidth() * 0.5F,
                     0.0
                  );
               }

               serverLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, spawnAt);
               if (entity instanceof Mob) {
                  ((Mob)entity).spawnAnim();
               }

               spawned = true;
               continue;

               this.spawnBuffer.clear();
               if (spawned) {
                  for (ParticleOptions particle : this.particleOptions) {
                     serverLevel.sendParticles(
                        particle, blockEntityPos.getX() + 0.5F, blockEntityPos.getY() + 0.5F, blockEntityPos.getZ() + 0.5F, 10, 1.0, 1.0, 1.0, 0.0
                     );
                  }

                  this.delay(serverLevel, blockEntityPos);
               }
               break;
            }
         }
      }
   }

   private void scanSpawnPositions(ServerLevel serverLevel, BlockPos blockEntityPos, RandomSource random) {
      if (this.spawnBuffer.size() < this.countNextToSpawn) {
         if (this.checkPos == null || !BoundingBoxUtils.isPosWithinBox(blockEntityPos, this.checkPos, this.spawnRange)) {
            MutableBlockPos posBelow = blockEntityPos.mutable().move(Direction.DOWN);
            if (serverLevel.getBlockState(posBelow).isAir()) {
               this.checkPos = posBelow;
            } else {
               this.checkPos = posBelow.move(Direction.UP, 2);
            }
         }

         BlockState blockBelow = serverLevel.getBlockState(this.checkPos.below());
         if (blockBelow.isAir()) {
            this.checkPos.move(Direction.DOWN);
         } else {
            if ((blockBelow.isSolid() || blockBelow.is((Block)TFBlocks.CORONATION_CARPET.get()))
               && random.nextBoolean()
               && !this.spawnBuffer.contains(this.checkPos)
               && serverLevel.getBlockState(this.checkPos).isAir()) {
               this.spawnBuffer.add(this.checkPos.immutable());
            }

            Direction randomDirection = Plane.HORIZONTAL.getRandomDirection(random);
            if (!serverLevel.getBlockState(this.checkPos.relative(randomDirection)).isSolid()) {
               this.checkPos.move(randomDirection);
            } else {
               this.checkPos = null;
            }
         }
      }
   }

   private void debugSeePosition(ServerLevel serverLevel) {
      serverLevel.sendParticles(
         ParticleTypes.ELECTRIC_SPARK, this.checkPos.getX() + 0.5, this.checkPos.getY() + 0.5, this.checkPos.getZ() + 0.5, 3, 0.1, 0.1, 0.1, 0.05F
      );
   }

   public void load(@Nullable Level level, BlockPos pos, CompoundTag tag) {
      super.load(level, pos, tag);
      this.particleOptions.clear();
      DataResult<List<ParticleOptions>> particleOptions = PARTICLES_CODEC.parse(NbtOps.INSTANCE, tag.get("ParticleOptions"));
      if (particleOptions.isSuccess()) {
         this.particleOptions.addAll((Collection<? extends ParticleOptions>)particleOptions.getPartialOrThrow());
      }

      if (tag.contains("EntityScanRange")) {
         this.entityScanRange = tag.getInt("EntityScanRange");
      } else {
         this.entityScanRange = this.spawnRange;
      }
   }

   public CompoundTag save(CompoundTag tag) {
      CompoundTag saved = super.save(tag);
      DataResult<Tag> encoded = PARTICLES_CODEC.encodeStart(NbtOps.INSTANCE, List.copyOf(this.particleOptions));
      if (encoded.isSuccess()) {
         saved.put("ParticleOptions", (Tag)encoded.getPartialOrThrow());
      }

      tag.putInt("EntityScanRange", this.entityScanRange);
      return saved;
   }

   public void broadcastEvent(Level level, BlockPos pos, int eventId) {
      level.blockEvent(pos, (Block)TFBlocks.SINISTER_SPAWNER.value(), eventId, 0);
   }

   public void setNextSpawnData(@Nullable Level level, BlockPos pos, SpawnData nextSpawnData) {
      super.setNextSpawnData(level, pos, nextSpawnData);
      if (level != null) {
         BlockState blockstate = level.getBlockState(pos);
         level.sendBlockUpdated(pos, blockstate, blockstate, 4);
      }
   }
}
