package net.unusual.block_factorys_bosses.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager.Occupancy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.unusual.block_factorys_bosses.block.KrakenSpawnerBlock;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseBlockEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRisePOI;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KrakenSpawnerBlockEntity extends BlockEntity {
   private static final int PLAYER_CHECK_RANGE = 32;
   private static final int PIRATE_DEATH_RANGE = 64;
   private static final Vec3 KRAKEN_POS_OFFSET = new Vec3(-24.0, -3.5, 4.0);
   public static final KrakenSpawnerBlockEntity.PiratePoint<?>[] PIRATE_POINTS = new KrakenSpawnerBlockEntity.PiratePoint[]{
      new KrakenSpawnerBlockEntity.PiratePoint(
         BossesRiseEntities.CROSSBOW_PIRATE, new Vec3(-7.0, 1.5, 21.5), new Vec3(5.0, 0.0, -7.0), new Vec3(-1.0, 3.0, 5.0)
      ),
      new KrakenSpawnerBlockEntity.PiratePoint(BossesRiseEntities.PIRATE_ROOK, new Vec3(6.0, 0.0, 15.0), new Vec3(-5.0, 0.0, -7.0), new Vec3(2.0, 0.0, 6.0)),
      new KrakenSpawnerBlockEntity.PiratePoint(BossesRiseEntities.PIRATE_CAPTAIN, new Vec3(0.0, 3.0, 24.0), new Vec3(0.0, 2.0, -14.0), new Vec3(0.0, 0.0, 0.0))
   };
   private List<UUID> pirateList = new ArrayList<>();
   private boolean spawnedPiratesFlag = false;

   public KrakenSpawnerBlockEntity(BlockPos position, BlockState state) {
      super((BlockEntityType)BossesRiseBlockEntities.KRAKEN_SPAWNER.get(), position, state);
   }

   public static Vec3 rotatedPoint(Vec3 vec3, Direction facing) {
      return switch (facing) {
         case EAST -> new Vec3(-vec3.z(), vec3.y(), vec3.x());
         case SOUTH -> new Vec3(-vec3.x(), vec3.y(), -vec3.z());
         case WEST -> new Vec3(vec3.z(), vec3.y(), -vec3.x());
         default -> vec3;
      };
   }

   public static float rotatedRot(Direction facing) {
      return switch (facing) {
         case EAST -> -90.0F;
         case SOUTH -> 0.0F;
         case WEST -> 90.0F;
         default -> 180.0F;
      };
   }

   public void tick(ServerLevel level, BlockPos pos, BlockState state) {
      Vec3 center = Vec3.atBottomCenterOf(pos);
      if (this.isNearPlayer(level, center)) {
         if (!this.spawnedPiratesFlag) {
            if (level.getDifficulty() == Difficulty.PEACEFUL) {
               return;
            }

            for (KrakenSpawnerBlockEntity.PiratePoint<?> point : PIRATE_POINTS) {
               UUID id = point.trySummon(level, center, (Direction)state.getValue(KrakenSpawnerBlock.FACING));
               if (id != null) {
                  this.pirateList.add(id);
               }
            }

            this.spawnedPiratesFlag = true;
         } else if (!this.pirateList.isEmpty()) {
            if (level.getDifficulty() == Difficulty.PEACEFUL) {
               this.spawnedPiratesFlag = false;
               this.pirateList.clear();
            }
         } else {
            level.setWeatherParameters(0, ServerLevel.THUNDER_DURATION.sample(level.getRandom()), true, true);
            KrakenEntity kraken = (KrakenEntity)((EntityType)BossesRiseEntities.KRAKEN.get()).create(level);
            if (kraken == null) {
               return;
            }

            Direction facing = (Direction)state.getValue(KrakenSpawnerBlock.FACING);
            Vec3 offset = rotatedPoint(KRAKEN_POS_OFFSET, facing);
            float rot = rotatedRot(facing.getClockWise());
            kraken.setYRot(rot);
            kraken.yRotO = rot;
            kraken.setYBodyRot(rot);
            kraken.yBodyRotO = rot;
            kraken.setYHeadRot(rot);
            kraken.yHeadRotO = rot;
            kraken.setShipPosition(pos, facing);
            kraken.setPos(center.add(offset));
            kraken.setBossPhase(-1);
            level.addFreshEntity(kraken);
            level.removeBlock(pos, false);
         }
      }
   }

   private boolean isNearPlayer(ServerLevel level, Vec3 pos) {
      return level.hasNearbyAlivePlayer(pos.x, pos.y, pos.z, 32.0);
   }

   public void loadAdditional(CompoundTag tag, Provider lookupProvider) {
      super.loadAdditional(tag, lookupProvider);
      this.spawnedPiratesFlag = tag.getBoolean("SpawnedPirates");
      if (this.spawnedPiratesFlag) {
         this.pirateList.clear();

         for (Tag pirateUuidTag : tag.getList("Pirates", 11)) {
            this.pirateList.add(NbtUtils.loadUUID(pirateUuidTag));
         }
      }
   }

   public void saveAdditional(CompoundTag tag, Provider lookupProvider) {
      super.saveAdditional(tag, lookupProvider);
      tag.putBoolean("SpawnedPirates", this.spawnedPiratesFlag);
      if (this.spawnedPiratesFlag) {
         ListTag pirateUuidsTag = new ListTag(this.pirateList.size());

         for (UUID pirateUuid : this.pirateList) {
            pirateUuidsTag.addLast(NbtUtils.createUUID(pirateUuid));
         }

         tag.put("Pirates", pirateUuidsTag);
      }
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider lookupProvider) {
      return this.saveWithFullMetadata(lookupProvider);
   }

   @EventBusSubscriber
   public record PiratePoint<T extends Monster>(DeferredHolder<EntityType<?>, EntityType<T>> type, Vec3... offset) {
      @Nullable
      public UUID trySummon(ServerLevel level, Vec3 center, Direction facing) {
         Monster pirate = (Monster)((EntityType)this.type().get()).create(level);
         if (pirate == null) {
            return null;
         }

         Vec3 offset = KrakenSpawnerBlockEntity.rotatedPoint(this.offset()[level.random.nextInt(this.offset().length)], facing);
         float rot = KrakenSpawnerBlockEntity.rotatedRot(Direction.getRandom(level.random));
         pirate.setYRot(rot);
         pirate.yRotO = rot;
         pirate.setYBodyRot(rot);
         pirate.yBodyRotO = rot;
         pirate.setYHeadRot(rot);
         pirate.yHeadRotO = rot;
         pirate.setPos(center.add(offset));
         EventHooks.finalizeMobSpawn(pirate, level, level.getCurrentDifficultyAt(pirate.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
         return level.addFreshEntity(pirate) ? pirate.getUUID() : null;
      }

      @SubscribeEvent
      private static void onDied(LivingDeathEvent event) {
         LivingEntity living = event.getEntity();
         if (living instanceof KrakenSpawnerBlockEntity.SpawnerTied && living.level() instanceof ServerLevel level) {
            level.getPoiManager()
               .findClosestWithType(holder -> holder.is(BossesRisePOI.KRAKEN_SPAWNER.getKey()), living.blockPosition(), 64, Occupancy.ANY)
               .ifPresent(pair -> {
                  if (level.getBlockEntity((BlockPos)pair.getSecond()) instanceof KrakenSpawnerBlockEntity krakenSpawner) {
                     krakenSpawner.pirateList.removeIf(uuid -> uuid.equals(living.getUUID()));
                     RandomSource random = living.getRandom();
                     Vec3 pos = living.position().add((random.nextFloat() - 0.5F) * 5.0F, -5.0, (random.nextFloat() - 0.5F) * 5.0F);
                     level.playSound(null, pos.x, pos.y, pos.z, (SoundEvent)BossesRiseSounds.KRAKEN_WARNING.value(), SoundSource.HOSTILE, 2.0F, 1.0F);
                  }
               });
         }
      }
   }

   public interface SpawnerTied {
   }
}
