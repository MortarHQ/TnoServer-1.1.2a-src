package net.witherspawnanimation.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public class AltarCounterUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.getPersistentData().putDouble("counter", entity.getPersistentData().getDouble("counter") + 1.0);
         if (entity.getPersistentData().getDouble("counter") >= 220.0) {
            if (!entity.level().isClientSide()) {
               entity.discard();
            }

            if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("wither_spawn_animation:altar_storm_general")))) {
               SpawnWitherStormProcedure.execute(world, x, y, z);
            } else if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = EntityType.WITHER.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
               }
            }
         }
      }
   }
}
