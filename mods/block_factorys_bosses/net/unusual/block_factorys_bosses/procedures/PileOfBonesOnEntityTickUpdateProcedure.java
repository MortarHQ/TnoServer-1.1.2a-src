package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.entity.decoration.PileOfBonesEntity;

public class PileOfBonesOnEntityTickUpdateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (0 < (entity instanceof PileOfBonesEntity _datEntI ? (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_hit_animation_time) : 0)
            && entity instanceof PileOfBonesEntity _datEntSetI) {
            _datEntSetI.getEntityData()
               .set(
                  PileOfBonesEntity.DATA_hit_animation_time,
                  (entity instanceof PileOfBonesEntity _datEntI ? (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_hit_animation_time) : 0) - 1
               );
         }
      }
   }
}
