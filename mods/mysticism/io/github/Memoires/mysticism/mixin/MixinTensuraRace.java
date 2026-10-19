package io.github.Memoires.mysticism.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.race.TensuraRace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TensuraRace.class)
public class MixinTensuraRace {
   @WrapOperation(
      method = "learnIntrinsicSkills(Lio/github/manasmods/manascore/race/api/ManasRaceInstance;Lnet/minecraft/world/entity/LivingEntity;)V",
      at = @At(value = "NEW", target = "Lio/github/manasmods/tensura/ability/TensuraSkillInstance;")
   )
   private TensuraSkillInstance mysticism$learnIntrinsicSkills(ManasSkill skill, Operation<TensuraSkillInstance> original) {
      TensuraSkillInstance skillInstance = (TensuraSkillInstance)original.call(new Object[]{skill});
      if (skill instanceof Skill) {
         skillInstance.getOrCreateTag().putBoolean("NoSoulEnergyCost", true);
         skillInstance.getOrCreateTag().putBoolean("GiftedInstance", true);
      }

      return skillInstance;
   }
}
