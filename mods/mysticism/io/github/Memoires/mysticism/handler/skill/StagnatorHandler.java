package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import io.github.Memoires.mysticism.ability.skill.unique.StagnatorSkill;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingTickEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.Objects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class StagnatorHandler {
   public static void init() {
      EntityEvents.LIVING_POST_TICK.register((LivingTickEvent)entity -> {
         if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.STAGNATE))) {
            if (entity instanceof Player player) {
               player.setDeltaMovement(Vec3.ZERO);
               player.resetFallDistance();
               Vec3 pos = MysticismStorages.getPlayerDataFrom(player).getPosition();
               if (pos == null) {
                  return;
               }

               player.teleportTo(pos.x, pos.y, pos.z);
            } else if (entity instanceof Mob mob) {
               mob.setNoAi(true);
            }
         }
      });
      SkillEvents.ACTIVATE_SKILL
         .register(
            (SkillActivationEvent)(skillInstance, owner, keyNumber, mode) -> StagnatorSkill.hasInaction(owner)
                  && ((ManasSkillInstance)Objects.requireNonNull((ManasSkillInstance)skillInstance.get())).is(TensuraSkillTags.BATTLEWILL)
               ? EventResult.interruptFalse()
               : EventResult.pass()
         );
   }
}
