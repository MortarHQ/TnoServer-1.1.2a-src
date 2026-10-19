package com.foxablazeultimate.ability.skill.velgrynd;

import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class VelgryndChargeHandler {
   private VelgryndChargeHandler() {
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      LivingEntity target = event.getEntity();
      if (!target.level().isClientSide()) {
         DamageSource source = event.getSource();
         if (source.getEntity() instanceof LivingEntity attacker && source.getDirectEntity() == attacker) {
            if (attacker != target && VelgryndSkill.isChargedBy(attacker.getMainHandItem(), attacker)) {
               VelgryndSkill skill = (VelgryndSkill)FoxAblazeUltimateSkills.VELGRYND.get();
               ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(attacker).getSkill(skill).orElse(null);
               if (instance != null) {
                  event.setCanceled(true);
                  skill.releaseCharge(instance, attacker, target);
               }
            }
         }
      }
   }
}
