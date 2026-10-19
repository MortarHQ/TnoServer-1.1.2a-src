package twilightforest.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.entity.boss.Lich;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFEntities;
import twilightforest.init.TFParticleType;

public class LichSpawnerBlockEntity extends BossSpawnerBlockEntity<Lich> {
   public LichSpawnerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)TFBlockEntities.LICH_SPAWNER.get(), (EntityType<Lich>)TFEntities.LICH.get(), pos, state);
   }

   @Override
   public boolean anyPlayerInRange(Level level) {
      Player closestPlayer = level.getNearestPlayer(
         this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5, this.getRange(), false
      );
      return closestPlayer != null && closestPlayer.getY() > this.getBlockPos().getY() - 4;
   }

   @Override
   protected boolean spawnMyBoss(ServerLevelAccessor accessor) {
      Lich myCreature = this.makeMyCreature();
      MutableBlockPos mutableBlockPos = this.getBlockPos().mutable();

      while (accessor.getMinBuildHeight() < mutableBlockPos.getY() && accessor.getBlockState(mutableBlockPos.below()).isAir()) {
         mutableBlockPos.move(Direction.DOWN);
      }

      mutableBlockPos.move(Direction.UP);
      myCreature.moveTo(mutableBlockPos, accessor.getLevel().random.nextFloat() * 360.0F, 0.0F);
      EventHooks.finalizeMobSpawn(myCreature, accessor, accessor.getCurrentDifficultyAt(mutableBlockPos), MobSpawnType.SPAWNER, null);
      myCreature.setAttackCooldown(40);
      myCreature.setExtinguishTimer();
      myCreature.setRestrictionPoint(GlobalPos.of(myCreature.level().dimension(), mutableBlockPos));
      return accessor.addFreshEntity(myCreature);
   }

   @Override
   public ParticleOptions getSpawnerParticle() {
      return (ParticleOptions)TFParticleType.OMINOUS_FLAME.get();
   }
}
