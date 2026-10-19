package io.github.Memoires.mysticism.effect.ability;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RevenantHorrorEffect extends TensuraMobEffect {
   public RevenantHorrorEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(89, 10, 126).getRGB());
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      if (!checkCanFly(entity)) {
         if (entity instanceof Player player) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
         }
      }
   }

   public static boolean checkCanFly(LivingEntity entity) {
      return SkillUtils.isSkillToggled(entity, (ManasSkill)ExtraSkills.GRAVITY_DOMINATION.get())
         || SkillUtils.isSkillToggled(entity, (ManasSkill)ExtraSkills.GRAVITY_DOMINATION.get());
   }
}
