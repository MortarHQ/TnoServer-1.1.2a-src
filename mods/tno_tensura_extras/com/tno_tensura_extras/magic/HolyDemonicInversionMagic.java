package com.tno_tensura_extras.magic;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public final class HolyDemonicInversionMagic extends DayNightInversionMagic {
   private static final int COOLDOWN_SECONDS = 600;
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "textures/magic/holy_demonic_inversion.png");

   @Override
   public ResourceLocation getSkillIcon() {
      return ICON;
   }

   @Override
   protected int getInversionCooldown() {
      return 600;
   }

   @Override
   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      resetAlignment(entity);
   }
}
