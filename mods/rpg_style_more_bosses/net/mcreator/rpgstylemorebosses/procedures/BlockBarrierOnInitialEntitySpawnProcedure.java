package net.mcreator.rpgstylemorebosses.procedures;

import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class BlockBarrierOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         RpgStyleMoreBossesMod.queueServerWork(60, () -> {
            if (!entity.level().isClientSide()) {
               entity.discard();
            }
         });
      }
   }
}
