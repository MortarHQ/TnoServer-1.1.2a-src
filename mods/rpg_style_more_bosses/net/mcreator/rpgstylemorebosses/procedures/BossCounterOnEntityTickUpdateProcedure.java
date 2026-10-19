package net.mcreator.rpgstylemorebosses.procedures;

import java.util.Comparator;
import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModEntities;
import net.mcreator.rpgstylemorebosses.network.RpgStyleMoreBossesModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BossCounterOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 30.0, 30.0, 30.0), e -> true).isEmpty()) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(150.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(Component.literal("The boss will be summoned within 20 seconds."), true);
               }
            }

            if (!entity.level().isClientSide()) {
               entity.discard();
            }

            RpgStyleMoreBossesMod.queueServerWork(
               200,
               () -> {
                  Vec3 _centerx = new Vec3(x, y, z);

                  for (Entity entityiteratorx : world.getEntitiesOfClass(Entity.class, new AABB(_centerx, _centerx).inflate(150.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_centerx)))
                     .toList()) {
                     if (entityiteratorx instanceof TheExoframeEntity && !entityiteratorx.level().isClientSide()) {
                        entityiteratorx.discard();
                     }
                  }

                  if (world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.THE_EXOFRAME_SPAWN.get())
                        .spawn(_level, new BlockPos(178, 92, 373), MobSpawnType.MOB_SUMMONED);
                     if (entityToSpawn != null) {
                        entityToSpawn.setYRot(world.getRandom().nextFloat() * 360.0F);
                     }
                  }

                  RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeBossCounterSpawnDebug = false;
                  RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                  _centerx = new Vec3(x, y, z);

                  for (Entity entityiteratorxx : world.getEntitiesOfClass(Entity.class, new AABB(_centerx, _centerx).inflate(150.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_centerx)))
                     .toList()) {
                     if (entityiteratorxx instanceof Player _playerx && !_playerx.level().isClientSide()) {
                        _playerx.displayClientMessage(Component.literal("The boss will be summoned within 20 seconds."), true);
                     }
                  }
               }
            );
         }
      }
   }
}
