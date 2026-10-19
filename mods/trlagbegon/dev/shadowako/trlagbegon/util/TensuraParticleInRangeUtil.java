package dev.shadowako.trlagbegon.util;

import java.util.function.Predicate;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public final class TensuraParticleInRangeUtil {
   public static final double DEFAULT_MAX_DIST = 128.0;
   private static final double DEFAULT_MAX_DIST_SQR = 16384.0;

   private TensuraParticleInRangeUtil() {
   }

   public static void sendToPlayersInRange(
      ServerLevel level,
      ParticleOptions particle,
      boolean force,
      double x,
      double y,
      double z,
      int count,
      double deltaX,
      double deltaY,
      double deltaZ,
      double speed,
      @Nullable Predicate<ServerPlayer> andPredicate
   ) {
      for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
         if (!(player.distanceToSqr(x, y, z) > 16384.0) && (andPredicate == null || andPredicate.test(player))) {
            level.sendParticles(player, particle, force, x, y, z, count, deltaX, deltaY, deltaZ, speed);
         }
      }
   }
}
