package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.entity.decoration.PileOfBonesEntity;

public class PileOfBonesIsEntityModelShakingProcedure {
   public static boolean execute(Entity entity) {
      return entity == null
         ? false
         : (entity instanceof PileOfBonesEntity _datEntI ? (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_hit_animation_time) : 0) > 0;
   }
}
