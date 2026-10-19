package net.unusual.block_factorys_bosses.procedures;

import java.util.Comparator;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.projectile.BlazingFireBallEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

public class BlazingFireBallWhileProjectileFlyingTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.BLAZING_FLAME.get(), x, y, z, 2, 0.1, 0.1, 0.1, 0.1);
      }

      if (world instanceof ServerLevel _level) {
         _level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.FANCY_SMOKE.get(), x, y, z, 3, 0.1, 0.1, 0.1, 0.1);
      }

      Vec3 _center = new Vec3(x, y, z);

      for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(0.5), e -> true)
         .stream()
         .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
         .toList()) {
         if (!(entityiterator instanceof BlazingFireBallEntity)) {
            entityiterator.igniteForSeconds(3.0F);
         }
      }
   }
}
