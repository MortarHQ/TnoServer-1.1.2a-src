package com.mortal_cultivation.event;

import com.mortal_cultivation.dimension.MindscapeDimension;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillToggleEvent;

public class MindscapeSkillRestrictionHandler {
   public static void init() {
      SkillEvents.ACTIVATE_SKILL
         .register(
            (SkillActivationEvent)(skillInstance, owner, keyNumber, mode) -> owner.level().dimension() == MindscapeDimension.MINDSCAPE_LEVEL
               ? EventResult.interruptFalse()
               : EventResult.pass()
         );
      SkillEvents.TOGGLE_SKILL
         .register(
            (SkillToggleEvent)(skillInstance, owner) -> owner.level().dimension() == MindscapeDimension.MINDSCAPE_LEVEL
               ? EventResult.interruptFalse()
               : EventResult.pass()
         );
   }
}
