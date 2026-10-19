package com.tno_tensura_extras.skill.unique;

import com.tno_tensura_extras.util.LooterManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public final class LooterSkill extends Skill {
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "textures/skill/unique/looter.png");

   public LooterSkill() {
      super(SkillType.UNIQUE);
   }

   public ResourceLocation getSkillIcon() {
      return ICON;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "default";
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0 && entity instanceof ServerPlayer player && !entity.level().isClientSide()) {
         LooterManager.openSelection(player);
      }
   }
}
