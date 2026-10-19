package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class CoalescenceHandler {
   public static void init() {
      SkillEvents.ACTIVATE_SKILL.register((SkillActivationEvent)(changeable, livingEntity, i, i1) -> {
         if (livingEntity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION))) {
            if (livingEntity instanceof Player p) {
               p.displayClientMessage(Component.translatable("mysticism.skill.mode.coalescence.fixation.being_fixated").withStyle(ChatFormatting.RED), false);
               p.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            return EventResult.interruptFalse();
         } else {
            return EventResult.pass();
         }
      });
   }
}
