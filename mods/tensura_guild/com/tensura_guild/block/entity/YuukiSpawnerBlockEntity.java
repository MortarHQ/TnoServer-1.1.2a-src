package com.tensura_guild.block.entity;

import com.tensura_guild.entity.YuukiKagurazakaEntity;
import com.tensura_guild.registry.GuildBlockEntities;
import com.tensura_guild.registry.GuildEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class YuukiSpawnerBlockEntity extends BlockEntity {
   private static final int DETECTION_RANGE = 60;
   private boolean hasSpawned = false;

   public YuukiSpawnerBlockEntity(BlockPos pos, BlockState state) {
      super(GuildBlockEntities.YUUKI_SPAWNER.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, YuukiSpawnerBlockEntity blockEntity) {
      if (!blockEntity.hasSpawned
         && !level.isClientSide()
         && level.getDifficulty() != Difficulty.PEACEFUL
         && level.hasNearbyAlivePlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 60.0)) {
         if (blockEntity.spawnYuuki((ServerLevel)level, pos) != null) {
            blockEntity.hasSpawned = true;
            level.destroyBlock(pos, false);
         }
      }
   }

   private YuukiKagurazakaEntity spawnYuuki(ServerLevel level, BlockPos pos) {
      YuukiKagurazakaEntity yuuki = (YuukiKagurazakaEntity)GuildEntityTypes.YUUKI_KAGURAZAKA.get().create(level);
      if (yuuki == null) {
         return null;
      }

      BlockPos spawnPos = pos.below();
      yuuki.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, level.getRandom().nextFloat() * 360.0F, 0.0F);
      yuuki.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.SPAWNER, null);
      yuuki.restrictTo(pos, 50);
      yuuki.setSpawnerDormant(true);
      return level.addFreshEntity(yuuki) ? yuuki : null;
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.putBoolean("HasSpawned", this.hasSpawned);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.hasSpawned = tag.getBoolean("HasSpawned");
   }
}
