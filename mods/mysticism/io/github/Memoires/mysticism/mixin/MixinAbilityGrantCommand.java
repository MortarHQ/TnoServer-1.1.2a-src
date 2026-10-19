package io.github.Memoires.mysticism.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.command.edit.ability.AbilityGrantCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbilityGrantCommand.class)
public class MixinAbilityGrantCommand {
   @WrapOperation(
      method = "grant(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/commands/arguments/selector/EntitySelector;Lnet/minecraft/core/Holder;)Z",
      at = @At(value = "NEW", target = "Lio/github/manasmods/tensura/ability/TensuraSkillInstance;")
   )
   private TensuraSkillInstance mysticism$grant(ManasSkill skill, Operation<TensuraSkillInstance> original) {
      TensuraSkillInstance skillInstance = (TensuraSkillInstance)original.call(new Object[]{skill});
      if (skill instanceof Skill) {
         skillInstance.getOrCreateTag().putBoolean("NoSoulEnergyCost", true);
         skillInstance.getOrCreateTag().putBoolean("GiftedInstance", true);
      }

      return skillInstance;
   }

   @WrapOperation(
      method = "grantAllAbility(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/commands/arguments/selector/EntitySelector;Ljava/util/function/Predicate;)V",
      at = @At(value = "NEW", target = "Lio/github/manasmods/tensura/ability/TensuraSkillInstance;")
   )
   private static TensuraSkillInstance mysticism$grantAllAbility(ManasSkill skill, Operation<TensuraSkillInstance> original) {
      TensuraSkillInstance skillInstance = (TensuraSkillInstance)original.call(new Object[]{skill});
      if (skill instanceof Skill) {
         skillInstance.getOrCreateTag().putBoolean("NoSoulEnergyCost", true);
         skillInstance.getOrCreateTag().putBoolean("GiftedInstance", true);
      }

      return skillInstance;
   }

   @WrapOperation(
      method = "grantRandomAbility(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/commands/arguments/selector/EntitySelector;Ljava/util/function/Predicate;)V",
      at = @At(value = "NEW", target = "Lio/github/manasmods/tensura/ability/TensuraSkillInstance;")
   )
   private static TensuraSkillInstance mysticism$grantRandomAbility(ManasSkill skill, Operation<TensuraSkillInstance> original) {
      TensuraSkillInstance skillInstance = (TensuraSkillInstance)original.call(new Object[]{skill});
      if (skill instanceof Skill) {
         skillInstance.getOrCreateTag().putBoolean("NoSoulEnergyCost", true);
         skillInstance.getOrCreateTag().putBoolean("GiftedInstance", true);
      }

      return skillInstance;
   }
}
