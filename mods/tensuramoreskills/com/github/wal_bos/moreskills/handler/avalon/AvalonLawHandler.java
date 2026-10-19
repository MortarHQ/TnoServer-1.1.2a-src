package com.github.wal_bos.moreskills.handler.avalon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

interface AvalonLawHandler {
   String name();

   void sendVfx(ServerLevel var1, LivingEntity var2, float var3, int var4, int var5);

   default void onActivate(ManasSkillInstance instance, LivingEntity owner) {
   }

   default void tick(ManasSkillInstance instance, LivingEntity owner) {
   }

   default void onDeactivate(ManasSkillInstance instance, LivingEntity owner) {
   }
}
