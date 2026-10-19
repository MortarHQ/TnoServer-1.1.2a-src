package com.github.wal_bos.moreskills.handler.ronova;

import com.github.wal_bos.moreskills.ability.skill.shades.ronova.RonovaConfigAccess;
import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.PlayLevelSoundEvent.AtEntity;

public final class RonovaSoundHandler {
   private RonovaSoundHandler() {
   }

   public static void onSound(AtEntity event) {
      if ((Boolean)RonovaConfigAccess.cfg().silentDeathEnabled.get()) {
         if (event.getEntity() instanceof LivingEntity living) {
            SkillAPI.getSkillsFrom(living).getSkill((ManasSkill)ShadeSkills.RONOVA.get()).ifPresent(instance -> {
               if (SkillUtils.isSkillToggled(instance)) {
                  event.setCanceled(true);
               }
            });
         }
      }
   }
}
