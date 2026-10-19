package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Guardian_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AltarOfVoid_Block_Entity extends BlockEntity {
   protected static final int SHORT_RANGE = 6;
   protected boolean spawnedBoss = false;

   public AltarOfVoid_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.ALTAR_OF_VOID.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, AltarOfVoid_Block_Entity entity) {
      entity.tick(level, pos, state, entity);
   }

   public boolean anyPlayerInRange(Level level, BlockPos pos) {
      return level.hasNearbyAlivePlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, this.getRange());
   }

   public void tick(Level level, BlockPos pos, BlockState state, AltarOfVoid_Block_Entity te) {
      if (!this.spawnedBoss && this.anyPlayerInRange(level, pos)) {
         if (level instanceof ServerLevel serverLevel && level.getDifficulty() != Difficulty.PEACEFUL && this.spawnMyBoss(serverLevel, pos)) {
            level.destroyBlock(pos, false);
            this.spawnedBoss = true;
         }
      }
   }

   protected boolean spawnMyBoss(ServerLevel serverLevel, BlockPos pos) {
      Vec3 vec3 = Vec3.atLowerCornerWithOffset(pos, 0.5, 0.0, 0.5);
      Ender_Guardian_Entity entity = (Ender_Guardian_Entity)((EntityType)ModEntities.ENDER_GUARDIAN.get()).create(serverLevel);
      if (entity != null) {
         entity.setPos(vec3);
         entity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.worldPosition), MobSpawnType.SPAWNER, null);
         entity.setUsedMassDestruction(false);
         entity.setHomePos(GlobalPos.of(serverLevel.dimension(), pos));
         return serverLevel.addFreshEntity(entity);
      } else {
         return false;
      }
   }

   protected int getRange() {
      return 6;
   }
}
