package twilightforest.block.entity.spawner;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.entity.EnforcedHomePoint;

public abstract class BossSpawnerBlockEntity<T extends Mob & EnforcedHomePoint> extends BlockEntity {
   protected static final int SHORT_RANGE = 9;
   protected static final int LONG_RANGE = 50;
   protected final EntityType<T> entityType;
   protected boolean spawnedBoss = false;

   protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
      super(type, pos, state);
      this.entityType = entityType;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, BossSpawnerBlockEntity<?> te) {
      if (!te.spawnedBoss && te.anyPlayerInRange(level)) {
         if (level.isClientSide()) {
            double rx = pos.getX() + level.getRandom().nextFloat();
            double ry = pos.getY() + level.getRandom().nextFloat();
            double rz = pos.getZ() + level.getRandom().nextFloat();
            level.addParticle(te.getSpawnerParticle(), rx, ry, rz, 0.0, 0.0, 0.0);
         } else if (level.getDifficulty() != Difficulty.PEACEFUL && te.spawnMyBoss((ServerLevel)level)) {
            level.destroyBlock(pos, false);
            te.spawnedBoss = true;
         }
      }
   }

   public boolean anyPlayerInRange(Level level) {
      return level.hasNearbyAlivePlayer(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5, this.getRange());
   }

   protected boolean spawnMyBoss(ServerLevelAccessor accessor) {
      T myCreature = this.makeMyCreature();
      BlockPos spawnPos = accessor.getBlockState(this.getBlockPos().below()).getCollisionShape(accessor, this.getBlockPos().below()).isEmpty()
         ? this.getBlockPos().below()
         : this.getBlockPos();
      myCreature.moveTo(spawnPos, accessor.getLevel().getRandom().nextFloat() * 360.0F, 0.0F);
      EventHooks.finalizeMobSpawn(myCreature, accessor, accessor.getCurrentDifficultyAt(spawnPos), MobSpawnType.SPAWNER, null);
      this.initializeCreature(myCreature);
      return accessor.addFreshEntity(myCreature);
   }

   public abstract ParticleOptions getSpawnerParticle();

   protected void initializeCreature(T myCreature) {
      myCreature.setRestrictionPoint(GlobalPos.of(myCreature.level().dimension(), this.getBlockPos()));
   }

   protected int getRange() {
      return 9;
   }

   protected T makeMyCreature() {
      return Objects.requireNonNull((T)this.entityType.create(Objects.requireNonNull(this.getLevel())));
   }
}
