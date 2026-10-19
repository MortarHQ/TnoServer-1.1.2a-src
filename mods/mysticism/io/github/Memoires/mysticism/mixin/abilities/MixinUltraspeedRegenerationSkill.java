package io.github.Memoires.mysticism.mixin.abilities;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.extra.UltraspeedRegenerationSkill;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(UltraspeedRegenerationSkill.class)
public class MixinUltraspeedRegenerationSkill {
   @ModifyReturnValue(
      method = "canBeToggled(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;)Z",
      at = @At("RETURN")
   )
   private static boolean mysticism$blockIfTenacity(boolean original, ManasSkillInstance instance, LivingEntity living) {
      return !SkillUtils.isSkillToggled(living, (ManasSkill)IntrinsicMysticismSkills.TENACITY.get());
   }
}
