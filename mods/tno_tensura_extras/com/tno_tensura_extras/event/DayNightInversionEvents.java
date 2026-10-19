package com.tno_tensura_extras.event;

import com.tno_tensura_extras.registry.ExtrasSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public final class DayNightInversionEvents {
   private static final ResourceLocation LUMINOUS_VALENTINE = ResourceLocation.fromNamespaceAndPath("tensura_neb", "luminous_valentine");

   private DayNightInversionEvents() {
   }

   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity victim = event.getEntity();
      if (!victim.level().isClientSide()) {
         ResourceLocation victimId = victim.getType().builtInRegistryHolder().key().location();
         if (LUMINOUS_VALENTINE.equals(victimId)) {
            ServerPlayer killer = resolvePlayer(event.getSource().getEntity());
            if (killer == null) {
               killer = resolvePlayer(event.getSource().getDirectEntity());
            }

            if (killer == null && victim.getKillCredit() instanceof ServerPlayer credited) {
               killer = credited;
            }

            if (killer != null) {
               SkillHelper.learnSkill(killer, (ManasSkill)ExtrasSkills.DAY_NIGHT_INVERSION.get());
            }
         }
      }
   }

   private static ServerPlayer resolvePlayer(Entity entity) {
      if (entity instanceof ServerPlayer player) {
         return player;
      } else {
         return entity != null && entity.getControllingPassenger() instanceof ServerPlayer rider ? rider : null;
      }
   }
}
