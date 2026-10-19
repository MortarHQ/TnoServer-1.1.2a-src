package net.jadenxgamer.netherexp.core.block.entity;

import java.util.ArrayList;
import java.util.List;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.CiergeOfTreacheryBlock;
import net.jadenxgamer.netherexp.core.entity.Apparition;
import net.jadenxgamer.netherexp.core.entity.ai.AttackCiergeOfTreacheryGoal;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CiergeOfTreacheryBlockEntity extends BlockEntity {
   private int fireRewarded = 1;
   private int maximumWaves = 5;
   private int currentWave = 0;
   private int maximumWaveDelay = 650;
   private int currentWaveDelay = 80;
   private int spawnRadius = 8;
   private int mobsPerWave = 4;
   private int resetMobsPerWave = 4;
   private int increaseInMobsPerWave = 2;
   private int health = 20;
   private int maximumHealth = 20;
   private int completionCooldown = (Integer)JNEConfigs.CIERGE_OF_TREACHERY_COMPLETION_COOLDOWN.get() * 20;
   private int playersNearby = 1;
   private List<EntityType<?>> spawnableMobs = new ArrayList<>();
   private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(
         Component.translatable("cierge_of_treachery.health"), BossBarColor.RED, BossBarOverlay.PROGRESS
      )
      .setDarkenScreen(false);

   public CiergeOfTreacheryBlockEntity(BlockPos pos, BlockState state) {
      super(JNEBlockEntityType.CIERGE_OF_TREACHERY.get(), pos, state);
   }

   protected void loadAdditional(CompoundTag nbt, Provider registries) {
      super.loadAdditional(nbt, registries);
      this.fireRewarded = nbt.getInt("FireRewarded");
      this.maximumWaves = nbt.getInt("MaximumWaves");
      this.currentWave = nbt.getInt("CurrentWave");
      this.maximumWaveDelay = nbt.getInt("MaximumWaveDelay");
      this.currentWaveDelay = nbt.getInt("CurrentWaveDelay");
      this.spawnRadius = nbt.getInt("SpawnRadius");
      this.mobsPerWave = nbt.getInt("MobsPerWave");
      this.resetMobsPerWave = nbt.getInt("ResetMobsPerWave");
      this.increaseInMobsPerWave = nbt.getInt("IncreaseInMobsPerWave");
      this.health = nbt.getInt("Health");
      this.maximumHealth = nbt.getInt("MaximumHealth");
      this.completionCooldown = nbt.getInt("CompletionCooldown");
      this.playersNearby = nbt.getInt("PlayersNearby");
      this.spawnableMobs.clear();
      ListTag mobsListTag = nbt.getList("SpawnableMobs", 8);

      for (int i = 0; i < mobsListTag.size(); i++) {
         String mobTypeString = mobsListTag.getString(i);
         EntityType.byString(mobTypeString).ifPresent(entityType -> this.spawnableMobs.add((EntityType<?>)entityType));
      }
   }

   protected void saveAdditional(CompoundTag nbt, Provider registries) {
      super.saveAdditional(nbt, registries);
      nbt.putInt("FireRewarded", this.fireRewarded);
      nbt.putInt("MaximumWaves", this.maximumWaves);
      nbt.putInt("CurrentWave", this.currentWave);
      nbt.putInt("MaximumWaveDelay", this.maximumWaveDelay);
      nbt.putInt("CurrentWaveDelay", this.currentWaveDelay);
      nbt.putInt("MobsPerWave", this.mobsPerWave);
      nbt.putInt("ResetMobsPerWave", this.resetMobsPerWave);
      nbt.putInt("IncreaseInMobsPerWave", this.increaseInMobsPerWave);
      nbt.putInt("SpawnRadius", this.spawnRadius);
      nbt.putInt("Health", this.health);
      nbt.putInt("MaximumHealth", this.maximumHealth);
      nbt.putInt("CompletionCooldown", this.completionCooldown);
      nbt.putInt("PlayersNearby", this.playersNearby);
      ListTag mobsListTag = new ListTag();

      for (EntityType<?> entityType : this.spawnableMobs) {
         mobsListTag.add(StringTag.valueOf(EntityType.getKey(entityType).toString()));
      }

      nbt.put("SpawnableMobs", mobsListTag);
   }

   public int getHealth() {
      return this.health;
   }

   public void setHealth(int health) {
      this.health = health;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, CiergeOfTreacheryBlockEntity blockEntity) {
      boolean isCompleted = (Boolean)state.getValue(CiergeOfTreacheryBlock.COMPLETED);
      boolean isLit = (Boolean)state.getValue(CiergeOfTreacheryBlock.LIT);
      if (isCompleted) {
         blockEntity.completionCooldown--;
         clearBossBarPlayers(blockEntity);
         if (blockEntity.completionCooldown <= 0) {
            resetValues(blockEntity);
            level.setBlock(
               pos, (BlockState)((BlockState)state.setValue(CiergeOfTreacheryBlock.COMPLETED, false)).setValue(CiergeOfTreacheryBlock.LIT, false), 2
            );
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5F, 1.0F);
         }
      } else if (isLit) {
         updateHealth(blockEntity);
         List<ServerPlayer> playersInRadius = getPlayersInRadius(level, pos);
         updateBossBarPlayers(blockEntity, playersInRadius, level);
         blockEntity.currentWaveDelay--;
         if (blockEntity.currentWave < blockEntity.maximumWaves) {
            prepareWaves(level, pos, blockEntity);
         } else if (blockEntity.currentWaveDelay <= 0) {
            dropFire(level, pos.above(), blockEntity);
            level.playSound(null, pos, JNESoundEvents.CIERGE_OF_TREACHERY_VICTORY.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            level.setBlock(pos, (BlockState)state.cycle(CiergeOfTreacheryBlock.COMPLETED), 2);
         }
      } else {
         clearBossBarPlayers(blockEntity);
      }
   }

   private static void clearBossBarPlayers(CiergeOfTreacheryBlockEntity blockEntity) {
      for (ServerPlayer player : new ArrayList(blockEntity.bossEvent.getPlayers())) {
         player.removeEffect(JNEMobEffects.BETRAYED);
         blockEntity.bossEvent.removePlayer(player);
      }
   }

   private static void updateHealth(CiergeOfTreacheryBlockEntity blockEntity) {
      blockEntity.bossEvent.setProgress((float)blockEntity.health / blockEntity.maximumHealth);
   }

   private static List<ServerPlayer> getPlayersInRadius(Level level, BlockPos pos) {
      return level.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(16.0, 16.0, 16.0));
   }

   private static void updateBossBarPlayers(CiergeOfTreacheryBlockEntity blockEntity, List<ServerPlayer> playersInRadius, Level level) {
      for (ServerPlayer player : playersInRadius) {
         if (player.isAlive() && player.level() == level) {
            player.addEffect(new MobEffectInstance(JNEMobEffects.BETRAYED, 200, 0));
            if (!blockEntity.bossEvent.getPlayers().contains(player)) {
               blockEntity.bossEvent.addPlayer(player);
            }
         }
      }

      for (ServerPlayer player : new ArrayList(blockEntity.bossEvent.getPlayers())) {
         if (!playersInRadius.contains(player) || !player.isAlive() || player.level() != level) {
            if (player.isAlive()) {
               player.removeEffect(JNEMobEffects.BETRAYED);
            }

            blockEntity.bossEvent.removePlayer(player);
         }
      }
   }

   private static void prepareWaves(Level level, BlockPos pos, CiergeOfTreacheryBlockEntity blockEntity) {
      if (blockEntity.currentWaveDelay == 60) {
         level.playSound(null, pos, JNESoundEvents.CIERGE_OF_TREACHERY_ROUND.get(), SoundSource.BLOCKS, 0.7F, 1.0F);
      }

      if (blockEntity.currentWaveDelay == 1) {
         level.addParticle((ParticleOptions)JNEParticleTypes.CANDLE_BURST.get(), pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
      }

      if (blockEntity.currentWaveDelay <= 0) {
         blockEntity.currentWave++;
         blockEntity.bossEvent
            .setName(
               Component.translatable("cierge_of_treachery.health")
                  .append(blockEntity.currentWave >= blockEntity.maximumWaves ? " - FINAL WAVE" : " - WAVE " + blockEntity.currentWave)
            );
         blockEntity.spawnWave(level, pos);
         blockEntity.mobsPerWave = blockEntity.mobsPerWave + blockEntity.increaseInMobsPerWave;
         blockEntity.currentWaveDelay = blockEntity.maximumWaveDelay;
      }
   }

   private static void dropFire(Level level, BlockPos pos, CiergeOfTreacheryBlockEntity blockEntity) {
      ItemEntity item = new ItemEntity(
         level,
         pos.getX() + 0.5,
         pos.getY(),
         pos.getZ() + 0.5,
         new ItemStack((ItemLike)JNEItems.SPARK_OF_TREACHERY.get(), blockEntity.fireRewarded * blockEntity.playersNearby)
      );
      item.setDeltaMovement(0.0, 0.2, 0.0);
      item.setGlowingTag(true);
      level.addFreshEntity(item);
      level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
   }

   public static void resetValues(CiergeOfTreacheryBlockEntity blockEntity) {
      blockEntity.completionCooldown = (Integer)JNEConfigs.CIERGE_OF_TREACHERY_COMPLETION_COOLDOWN.get() * 20;
      blockEntity.currentWave = 0;
      blockEntity.health = blockEntity.maximumHealth;
      blockEntity.mobsPerWave = blockEntity.resetMobsPerWave;
      blockEntity.currentWaveDelay = 80;
      blockEntity.playersNearby = 1;
   }

   public static void findAllNearbyPlayers(CiergeOfTreacheryBlockEntity blockEntity, BlockPos pos, Level level) {
      if (level != null) {
         List<Player> nearbyPlayers = level.getEntitiesOfClass(
            Player.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ()).inflate(16.0, 16.0, 16.0)
         );
         blockEntity.playersNearby = nearbyPlayers.size();
      }
   }

   private void spawnWave(Level level, BlockPos pos) {
      RandomSource random = level.random;
      if (!this.spawnableMobs.isEmpty()) {
         int bonusSpawns = this.playersNearby > 1 ? this.playersNearby * 2 : 0;
         level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.CIERGE_OF_TREACHERY_SPAWN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

         for (int i = 0; i < this.mobsPerWave + bonusSpawns; i++) {
            BlockPos spawnPos = this.findValidSpawnPosition(level, pos, random);
            EntityType<?> entityType = this.spawnableMobs.get(random.nextInt(this.spawnableMobs.size()));
            Mob mob = (Mob)entityType.create(level);
            if (mob != null && level instanceof ServerLevel serverLevel) {
               mob.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
               mob.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null);
               if (mob instanceof Slime slime) {
                  slime.setSize(2, true);
               }

               if (mob instanceof Apparition apparition) {
                  apparition.setDropWisps(false);
               }

               if (mob instanceof PathfinderMob pathfinder && !pathfinder.getType().is(JNETags.EntityTypes.IGNORES_CIERGE_OF_TREACHERY)) {
                  mob.targetSelector.addGoal(2, new AttackCiergeOfTreacheryGoal(pathfinder, 32));
               }

               level.addFreshEntity(mob);
            }
         }
      }
   }

   private BlockPos findValidSpawnPosition(Level level, BlockPos pos, RandomSource random) {
      double angle = random.nextDouble() * 2.0 * Math.PI;
      double radius = 5.0 + random.nextDouble() * 2.0;
      double x = pos.getX() + Math.cos(angle) * radius;
      double z = pos.getZ() + Math.sin(angle) * radius;
      double y = pos.getY();
      BlockPos currentPos = new BlockPos((int)x, (int)y, (int)z);

      for (int retries = 0; retries < 10; retries++) {
         if (level.getBlockState(currentPos).isAir()) {
            return currentPos;
         }

         currentPos = new BlockPos((int)x, (int)(++y), (int)z);
      }

      return currentPos;
   }

   public void setRemoved() {
      super.setRemoved();
      assert this.level != null;
      if (!this.level.isClientSide() && this.bossEvent != null) {
         for (ServerPlayer player : new ArrayList(this.bossEvent.getPlayers())) {
            this.bossEvent.removePlayer(player);
         }
      }
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }
}
