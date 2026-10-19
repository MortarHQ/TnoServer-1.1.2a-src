package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ZombieHorseEntityAccess;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedGameRulesProvider;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.village.poi.PoiManager.Occupancy;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin extends Level implements WorldGenLevel {
   protected ServerWorldMixin(
      WritableLevelData properties,
      ResourceKey<Level> registryRef,
      RegistryAccess registryManager,
      Holder<DimensionType> dimensionEntry,
      Supplier<ProfilerFiller> profiler,
      boolean isClient,
      boolean debugWorld,
      long biomeAccess,
      int maxChainedNeighborUpdates
   ) {
      super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
   }

   @Shadow
   protected abstract BlockPos findLightningTargetAround(BlockPos var1);

   @WrapMethod(method = "tickChunk")
   public void friendsandfoes$addZombieHorseSpawnEvent(LevelChunk chunk, int randomTickSpeed, Operation<Void> original) {
      original.call(new Object[]{chunk, randomTickSpeed});
      if (FriendsAndFoes.getConfig().enableZombieHorseTrap) {
         ChunkPos chunkPos = chunk.getPos();
         int i = chunkPos.getMinBlockX();
         int j = chunkPos.getMinBlockZ();
         ProfilerFiller profiler = this.getProfiler();
         profiler.push("thunder2");
         BlockPos blockPos;
         if (this.isRaining()
            && this.isThundering()
            && this.getRandom().nextInt(100000) == 0
            && this.isRainingAt(blockPos = this.findLightningTargetAround(this.getBlockRandomPos(i, 0, j, 15)))) {
            DifficultyInstance localDifficulty = this.getCurrentDifficultyAt(blockPos);
            boolean canZombieHorseSpawn = VersionedGameRulesProvider.getBoolean((ServerLevel)this, VersionedGameRulesProvider.SPAWN_MOBS)
               && this.random.nextDouble() < localDifficulty.getEffectiveDifficulty() * 0.01;
            canZombieHorseSpawn = canZombieHorseSpawn && !this.getBlockState(blockPos.below()).is(FriendsAndFoesTags.LIGHTNING_RODS);
            if (canZombieHorseSpawn) {
               ZombieHorse zombieHorse = (ZombieHorse)EntityType.ZOMBIE_HORSE.create(this);
               ((ZombieHorseEntityAccess)zombieHorse).friendsandfoes_setTrapped(true);
               zombieHorse.setAge(0);
               zombieHorse.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
               this.addFreshEntity(zombieHorse);
            }

            LightningBolt lightningBolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(this);
            lightningBolt.moveTo(Vec3.atBottomCenterOf(blockPos));
            lightningBolt.setVisualOnly(canZombieHorseSpawn);
            this.addFreshEntity(lightningBolt);
         }

         profiler.pop();
      }
   }

   @WrapMethod(method = "findLightningRod")
   protected Optional<BlockPos> friendsandfoes$getLightningRodPos(BlockPos pos, Operation<Optional<BlockPos>> original) {
      Optional<BlockPos> originalLightningRodPos = (Optional<BlockPos>)original.call(new Object[]{pos});
      if (originalLightningRodPos.isPresent()) {
         return originalLightningRodPos;
      }

      ServerLevel serverWorld = (ServerLevel)this;
      Optional<BlockPos> optional = serverWorld.getPoiManager()
         .findClosest(
            registryEntry -> registryEntry.is(FriendsAndFoesTags.LIGHTNING_ROD_POI),
            posx -> posx.getY() == this.getHeight(Types.WORLD_SURFACE, posx.getX(), posx.getZ()) - 1,
            pos,
            128,
            Occupancy.ANY
         );
      return optional.map(posx -> posx.above(1));
   }
}
