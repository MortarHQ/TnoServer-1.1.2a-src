package net.mcreator.rpgstylemorebosses.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

public class PizdabobraRightclickedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof Mob _entity) {
            _entity.getNavigation().moveTo(entity.getX(), entity.getY() + 7.0, entity.getZ(), 10.0);
         }
      }
   }
}
