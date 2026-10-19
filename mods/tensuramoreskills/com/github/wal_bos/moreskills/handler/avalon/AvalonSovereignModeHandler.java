package com.github.wal_bos.moreskills.handler.avalon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.world.entity.LivingEntity;

interface AvalonSovereignModeHandler {
   String name();

   double magiculeCost(LivingEntity var1, ManasSkillInstance var2);

   void activate(ManasSkillInstance var1, LivingEntity var2, int var3);

   default boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int topMode) {
      return false;
   }

   default void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int topMode) {
   }

   default boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int topMode) {
      return false;
   }

   default void tick(ManasSkillInstance instance, LivingEntity entity) {
   }

   default void cleanup(LivingEntity entity) {
   }
}
