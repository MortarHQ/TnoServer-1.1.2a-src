package com.github.wal_bos.moreskills.handler.michael;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.world.entity.LivingEntity;

public interface MichaelAttack {
   String modeId();

   double magiculeCost(LivingEntity var1, ManasSkillInstance var2);

   void activate(ManasSkillInstance var1, LivingEntity var2, int var3, int var4);

   default boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      return false;
   }

   default void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
   }

   default void cleanup(LivingEntity entity) {
   }
}
