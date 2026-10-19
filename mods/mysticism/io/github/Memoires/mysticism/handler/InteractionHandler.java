package io.github.Memoires.mysticism.handler;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.InteractionEvent.RightClickBlock;
import dev.architectury.event.events.common.InteractionEvent.RightClickItem;
import io.github.Memoires.mysticism.ability.skill.unique.StagnatorSkill;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;

public class InteractionHandler {
   public static void init() {
      InteractionEvent.RIGHT_CLICK_ITEM
         .register(
            (RightClickItem)(player, hand) -> !player.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.STAGNATE))
                  && !player.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION))
                  && !StagnatorSkill.hasInaction(player)
               ? CompoundEventResult.pass()
               : CompoundEventResult.interruptFalse(player.getItemInHand(hand))
         );
      InteractionEvent.RIGHT_CLICK_BLOCK
         .register(
            (RightClickBlock)(player, hand, pos, face) -> !player.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.STAGNATE))
                  && !player.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION))
                  && !StagnatorSkill.hasInaction(player)
               ? EventResult.pass()
               : EventResult.interruptFalse()
         );
   }
}
