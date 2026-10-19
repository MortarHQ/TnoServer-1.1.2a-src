package io.github.Memoires.mysticism.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.MagicUtils;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MagicUtils.class)
public class MixinMagicUtils {
   @ModifyReturnValue(method = "hasChantAnnulment(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("RETURN"))
   private static boolean mysticism$hasChantAnnulment(boolean original, LivingEntity entity) {
      return original || SkillUtils.isSkillToggled(entity, (ManasSkill)UniqueMysticismSkills.SPIRITUALIST.get());
   }
}
