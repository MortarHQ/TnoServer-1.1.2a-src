package net.mcreator.rpgstylemorebosses.procedures;

import java.util.Comparator;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TheExoframeEntityIsHurtProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.1) {
            Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(30.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if ((entityiterator instanceof Player || entityiterator instanceof ServerPlayer) && world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                     .spawn(_level, BlockPos.containing(entityiterator.getX(), entity.getY(), entityiterator.getZ()), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                     entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                  }
               }
            }
         }
      }
   }
}
