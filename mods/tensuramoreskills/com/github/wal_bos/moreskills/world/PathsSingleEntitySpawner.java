package com.github.wal_bos.moreskills.world;

import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.world.entity.TheCoordinateEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

public final class PathsSingleEntitySpawner {
   private static final int OFFSET_BLOCKS = 100;
   private static final ResourceKey<Level> PATHS = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "paths"));

   private PathsSingleEntitySpawner() {
   }

   public static void onLevelTick(Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         if (level.dimension().equals(PATHS)) {
            if (level.getGameTime() % 40L == 0L) {
               PathsBossSavedData data = PathsBossSavedData.get(level);
               if (!data.hasSpawnedEver()) {
                  BlockPos spawn = getBossSpawn(level);
                  TheCoordinateEntity ent = (TheCoordinateEntity)((EntityType)ModEntityTypes.THE_COORDINATE.get()).create(level);
                  if (ent != null) {
                     ent.moveTo(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5, 0.0F, 0.0F);
                     ent.setPersistenceRequired();
                     if (level.addFreshEntity(ent)) {
                        data.setEntityId(ent.getUUID());
                        data.setSpawnedEver(true);
                     }
                  }
               }
            }
         }
      }
   }

   private static BlockPos getBossSpawn(ServerLevel level) {
      BlockPos worldSpawn = level.getSharedSpawnPos();
      BlockPos raw = worldSpawn.offset(100, 0, 0);
      int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, raw.getX(), raw.getZ());
      return new BlockPos(raw.getX(), y, raw.getZ());
   }
}
