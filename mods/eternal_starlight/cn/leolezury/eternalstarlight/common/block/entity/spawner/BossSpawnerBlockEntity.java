package cn.leolezury.eternalstarlight.common.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class BossSpawnerBlockEntity<T extends Mob> extends BlockEntity {
   private static final String TAG_SPAWN_COOLDOWN = "spawn_cooldown";
   protected final EntityType<T> entityType;
   protected boolean spawnedBoss = false;
   protected int spawnCooldown = 0;

   protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
      super(type, pos, state);
      this.entityType = entityType;
   }

   public void setSpawnCooldown(int spawnCooldown) {
      this.spawnCooldown = spawnCooldown;
      this.setChanged();
   }

   public boolean anyPlayerInRange() {
      return this.getLevel() != null
         && this.getLevel()
            .hasNearbyAlivePlayer(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5, this.getRange());
   }

   public static void tick(Level level, BlockPos pos, BlockState state, BossSpawnerBlockEntity<?> entity) {
      if (entity.spawnCooldown > 0) {
         entity.spawnCooldown--;
         if (entity.spawnCooldown % 10 == 0) {
            entity.setChanged();
         }
      }

      if (!entity.spawnedBoss && entity.anyPlayerInRange()) {
         if (level.isClientSide) {
            RandomSource random = level.getRandom();
            if (random.nextFloat() < 0.3F) {
               Vec3 particlePos = pos.getCenter()
                  .add((random.nextDouble() - 0.5) * 1.25, (random.nextDouble() - 0.5) * 1.25, (random.nextDouble() - 0.5) * 1.25);
               level.addParticle(entity.getSpawnerParticle(), particlePos.x, particlePos.y, particlePos.z, 0.0, 0.0, 0.0);
            }
         } else if (level.getDifficulty() != Difficulty.PEACEFUL
            && entity.spawnCooldown <= 0
            && level instanceof ServerLevelAccessor serverLevel
            && entity.spawnBoss(serverLevel)) {
            level.destroyBlock(pos, false);
            entity.spawnedBoss = true;
         }
      }
   }

   protected boolean spawnBoss(ServerLevelAccessor accessor) {
      T mob = this.createBoss();
      if (mob == null) {
         return false;
      }

      mob.moveTo(this.getBlockPos(), accessor.getLevel().getRandom().nextFloat() * 360.0F, 0.0F);
      mob.finalizeSpawn(accessor, accessor.getCurrentDifficultyAt(this.getBlockPos()), MobSpawnType.SPAWNER, null);
      this.initializeBoss(mob);
      return accessor.addFreshEntity(mob);
   }

   protected int getRange() {
      return 50;
   }

   public abstract ParticleOptions getSpawnerParticle();

   protected void initializeBoss(T mob) {
      mob.restrictTo(this.getBlockPos(), 50);
      mob.setPersistenceRequired();
   }

   protected T createBoss() {
      return (T)(this.getLevel() == null ? null : this.entityType.create(this.getLevel()));
   }

   protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      compoundTag.putInt("spawn_cooldown", this.spawnCooldown);
   }

   protected void loadAdditional(CompoundTag compoundTag, Provider provider) {
      super.loadAdditional(compoundTag, provider);
      this.spawnCooldown = compoundTag.getInt("spawn_cooldown");
   }
}
