package io.github.manasmods.tensura_iaf.mixin;

import com.iafenvoy.iceandfire.entity.SirenEntity;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SirenEntity.class)
public class MixinSirenEntity {
   @Inject(method = "isWearingEarplugs(Lnet/minecraft/world/entity/LivingEntity;)Z", cancellable = true, at = @At("RETURN"))
   private static void isImmune(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
      if (!(Boolean)cir.getReturnValue()) {
         if (SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get())) {
            cir.setReturnValue(true);
         }
      }
   }
}
