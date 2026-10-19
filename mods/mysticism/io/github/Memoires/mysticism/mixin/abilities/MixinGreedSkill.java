package io.github.Memoires.mysticism.mixin.abilities;

import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.unique.GreedSkill;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig.Greed;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GreedSkill.class)
public class MixinGreedSkill {
   private static final Greed CONFIG = ((UniqueSkillConfig)ConfigRegistry.getConfig(UniqueSkillConfig.class)).Greed;

   @Inject(
      method = "onHeld(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;II)Z",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void mysticism$onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode, CallbackInfoReturnable<Boolean> cir) {
      if (mode == 0
         && !entity.isShiftKeyDown()
         && SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get())
         && SkillUtils.isSkillMastered(entity, (ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get())) {
         LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(LivingEntity.class, entity, CONFIG.maxDistance, 0.5, true, true, false);
         if (target != null) {
            if (target instanceof Player) {
               instance.getOrCreateTag().putInt("heldSeconds", CONFIG.playerControl);
            } else {
               instance.getOrCreateTag().putInt("heldSeconds", CONFIG.entityControl);
            }
         }
      }
   }
}
