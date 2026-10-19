package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import io.github.Memoires.mysticism.entity.golems.ClayGolemEntity;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.PossessionEvent;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;

public class MalleableHandler {
   public static void init() {
      TensuraEntityEvents.POSSESSION_EVENT.register((PossessionEvent)(livingEntity, entity) -> {
         if (entity instanceof ClayGolemEntity clayGolem && clayGolem.getOwnerPlayer() != livingEntity) {
            IExistence existence = TensuraStorages.getExistenceFrom(livingEntity);
            existence.setTemporaryOwner(clayGolem.getOwnerUuid().get());
         }

         return EventResult.pass();
      });
      SkillEvents.ACTIVATE_SKILL.register((SkillActivationEvent)(changeable, livingEntity, i, i1) -> {
         ManasSkillInstance skillInstance = (ManasSkillInstance)changeable.get();
         if (skillInstance.equals(IntrinsicSkills.POSSESSION)) {
            IExistence existence = TensuraStorages.getExistenceFrom(livingEntity);
            if (skillInstance.onCoolDown(i1) || SkillUtils.inSpiritualWorld(livingEntity.level().dimension())) {
               return EventResult.pass();
            }

            if (existence.isSpiritualForm()) {
               return EventResult.pass();
            }

            existence.setTemporaryOwner(null);
         }

         return EventResult.pass();
      });
   }
}
