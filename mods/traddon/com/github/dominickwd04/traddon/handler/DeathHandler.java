package com.github.dominickwd04.traddon.handler;

import com.github.dominickwd04.traddon.registry.effect.TrAddonMobEffects;
import com.github.dominickwd04.traddon.registry.skill.TrAddonUniqueSkills;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class DeathHandler {
   public static void init() {
      EntityEvents.DEATH_EVENT_NORMAL
         .register(
            (LivingDeath)(entity, source) -> {
               if (source.getEntity() instanceof LivingEntity livingSource
                  && SkillUtils.isSkillToggled(livingSource, (ManasSkill)TrAddonUniqueSkills.DEVASTATOR.get())) {
                  int amplifier = livingSource.hasEffect(TrAddonMobEffects.getReference(TrAddonMobEffects.POST_MORTEM_SURGE))
                     ? livingSource.getEffect(TrAddonMobEffects.getReference(TrAddonMobEffects.POST_MORTEM_SURGE)).getAmplifier() + 1
                     : 0;
                  TensuraMobEffect.addEffect(
                     livingSource,
                     new MobEffectInstance(
                        TrAddonMobEffects.getReference(TrAddonMobEffects.POST_MORTEM_SURGE), 400, Math.min(amplifier, 9), false, false, false
                     ),
                     livingSource,
                     (ManasSkill)TrAddonUniqueSkills.DEVASTATOR.get()
                  );
               }

               return EventResult.pass();
            }
         );
   }
}
