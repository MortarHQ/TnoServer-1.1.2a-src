package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;

public class UnderworldKnightDisplayConditionProcedure {
   public static boolean execute(Entity entity) {
      return entity == null
         ? false
         : (entity instanceof UnderworldKnightEntity _datEntI ? (Integer)_datEntI.getEntityData().get(UnderworldKnightEntity.DATA_BOSS_PHASE) : 0) <= 2;
   }
}
