package net.mcreator.rpgstylemorebosses.procedures;

import net.mcreator.rpgstylemorebosses.entity.RunicSpiderEntity;
import net.mcreator.rpgstylemorebosses.network.RpgStyleMoreBossesModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class RunicSpiderOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         RpgStyleMoreBossesModVariables.MapVariables.get(world).BC++;
         RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
         if (RpgStyleMoreBossesModVariables.MapVariables.get(world).BC == 120.0) {
            if (entity instanceof RunicSpiderEntity) {
               ((RunicSpiderEntity)entity).setAnimation("animation.RunicSpider.aueattack");
            }

            RpgStyleMoreBossesModVariables.MapVariables.get(world).BC = 0.0;
            RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
         }
      }
   }
}
