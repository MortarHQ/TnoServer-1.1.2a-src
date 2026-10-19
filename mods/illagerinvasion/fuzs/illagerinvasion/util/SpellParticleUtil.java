package fuzs.illagerinvasion.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class SpellParticleUtil {
   public static void sendSpellParticles(LivingEntity entity, ServerLevel level, ParticleOptions particleTypes, int count, double speed) {
      float g = entity.yBodyRot * (float) (Math.PI / 180.0) + Mth.cos(entity.tickCount * 0.6662F) * 0.25F;
      float h = Mth.cos(g);
      float i = Mth.sin(g);
      level.sendParticles(particleTypes, entity.getX() + h * 0.6, entity.getY() + 1.8, entity.getZ() + i, count, 0.0, 0.0, 0.0, speed);
      level.sendParticles(particleTypes, entity.getX() - h * 0.6, entity.getY() + 1.8, entity.getZ() - i, count, 0.0, 0.0, 0.0, speed);
   }
}
