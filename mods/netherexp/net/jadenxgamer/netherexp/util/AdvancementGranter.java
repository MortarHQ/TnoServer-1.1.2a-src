package net.jadenxgamer.netherexp.util;

import java.util.function.Supplier;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class AdvancementGranter {
   public static void grantPlayersInRadius(Level level, BlockPos pos, Supplier<PlayerTrigger> criteriaTrigger) {
      grantPlayersInRadius(level, pos, 8.0, 8.0, 8.0, criteriaTrigger);
   }

   public static void grantPlayersInRadius(Level level, BlockPos pos, double radius, Supplier<PlayerTrigger> criteriaTrigger) {
      grantPlayersInRadius(level, pos, radius, radius, radius, criteriaTrigger);
   }

   public static void grantPlayersInRadius(Level level, BlockPos pos, double x, double y, double z, Supplier<PlayerTrigger> criteriaTrigger) {
      for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(x, y, z))) {
         criteriaTrigger.get().trigger(serverPlayer);
      }
   }
}
