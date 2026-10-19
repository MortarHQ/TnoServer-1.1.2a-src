package io.github.manasmods.tensura_iaf;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.entity.StoneStatueEntity;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingEffectAddedEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.EnergyDrainEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.SpiritualHurtEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import net.minecraft.world.effect.MobEffectInstance;

public class IafHandler {
   public static void init() {
      TensuraSkillEvents.SKILL_PLUNDER
         .register(
            (SkillPlunderEvent)(target, entity, steal, changeable) -> !(
                  target instanceof DragonBaseEntity dragonBase && (dragonBase.isModelDead() || dragonBase.getDeathStage() > 0)
               )
               ? EventResult.pass()
               : EventResult.interruptFalse()
         );
      TensuraEntityEvents.ENERGY_DRAIN_EVENT
         .register(
            (EnergyDrainEvent)(target, drainer, drainType, gainType, amount, percentage) -> !(
                  target instanceof DragonBaseEntity dragonBase && (dragonBase.isModelDead() || dragonBase.getDeathStage() > 0)
               )
               ? EventResult.pass()
               : EventResult.interruptFalse()
         );
      TensuraEntityEvents.SPIRITUAL_HURT_EVENT
         .register(
            (SpiritualHurtEvent)(target, drainer, drainType, gainType, amount, percentage) -> !(
                  target instanceof DragonBaseEntity dragonBase && (dragonBase.isModelDead() || dragonBase.getDeathStage() > 0)
               )
               ? EventResult.pass()
               : EventResult.interruptFalse()
         );
      EntityEvents.LIVING_EFFECT_ADDED.register((LivingEffectAddedEvent)(entity, source, changeableInstance) -> {
         MobEffectInstance instance = (MobEffectInstance)changeableInstance.get();
         if (instance == null) {
            return EventResult.pass();
         } else {
            return entity instanceof StoneStatueEntity ? EventResult.interruptFalse() : EventResult.pass();
         }
      });
   }
}
