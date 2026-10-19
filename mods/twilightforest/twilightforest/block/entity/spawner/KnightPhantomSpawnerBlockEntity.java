package twilightforest.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.entity.boss.KnightPhantom;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFParticleType;

public class KnightPhantomSpawnerBlockEntity extends BossSpawnerBlockEntity<KnightPhantom> {
   private static final int COUNT = 6;
   private int spawned = 0;

   public KnightPhantomSpawnerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)TFBlockEntities.KNIGHT_PHANTOM_SPAWNER.get(), (EntityType<KnightPhantom>)TFEntities.KNIGHT_PHANTOM.get(), pos, state);
   }

   @Override
   public boolean anyPlayerInRange(Level level) {
      Player closestPlayer = level.getNearestPlayer(
         this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5, this.getRange(), false
      );
      return closestPlayer != null && closestPlayer.getY() > this.getBlockPos().getY() - 2;
   }

   @Override
   protected boolean spawnMyBoss(ServerLevelAccessor accessor) {
      for (int i = this.spawned; i < 6; i++) {
         KnightPhantom myCreature = this.makeMyCreature();
         float angle = 60.0F * i;
         float distance = 4.0F;
         double rx = this.getBlockPos().getX() + 0.5 + Math.cos(angle * Math.PI / 180.0) * 4.0;
         double ry = this.getBlockPos().getY();
         double rz = this.getBlockPos().getZ() + 0.5 + Math.sin(angle * Math.PI / 180.0) * 4.0;
         myCreature.moveTo(rx, ry, rz, accessor.getLevel().getRandom().nextFloat() * 360.0F, 0.0F);
         EventHooks.finalizeMobSpawn(
            myCreature, accessor, accessor.getCurrentDifficultyAt(new BlockPos(myCreature.blockPosition())), MobSpawnType.SPAWNER, null
         );
         if (i == 5 && accessor.getDifficulty() == Difficulty.HARD) {
            myCreature.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack((ItemLike)TFItems.KNIGHTMETAL_SHIELD.get()));
         }

         this.initializeCreature(myCreature);
         myCreature.setNumber(i);
         if (accessor.addFreshEntity(myCreature)) {
            this.spawned++;
         }
      }

      return this.spawned == 6;
   }

   @Override
   public ParticleOptions getSpawnerParticle() {
      return (ParticleOptions)TFParticleType.OMINOUS_FLAME.get();
   }
}
