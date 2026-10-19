package net.witherspawnanimation.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class SpawnWitherStormAtCoordinatesProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, double spawnX, double spawnY, double spawnZ) {
      String compat = "";
      compat = "" + spawnX + spawnY + spawnZ;
      long finalTime = 50L;
      if (world instanceof ServerLevel _level) {
         _level.getServer()
            .getCommands()
            .performPrefixedCommand(
               new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null)
                  .withSuppressedOutput(),
               "summon witherstormmod:wither_storm " + spawnX + " " + (spawnY - 1.0) + " " + spawnZ + " {Invul: " + Long.toString(finalTime) + "}"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.getServer()
            .getCommands()
            .performPrefixedCommand(
               new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null)
                  .withSuppressedOutput(),
               "execute as @e[type=witherstormmod:wither_storm,limit=1,sort=nearest] at @s run tp @s ~ ~ ~ facing entity @p"
            );
      }
   }
}
