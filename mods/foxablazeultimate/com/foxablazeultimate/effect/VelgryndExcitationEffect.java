package com.foxablazeultimate.effect;

import com.foxablazeultimate.config.VelgryndConfig;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class VelgryndExcitationEffect extends TensuraMobEffect {
   private static final ResourceLocation EXCITATION = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "velgrynd_excitation");

   public VelgryndExcitationEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(230, 60, 30).getRGB());
      VelgryndConfig cfg = VelgryndConfig.get();
      VelgryndConfig.VelgryndSettings c = cfg == null ? new VelgryndConfig.VelgryndSettings() : cfg.Velgrynd;
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, EXCITATION, c.excitationMovementSpeedBonus, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, EXCITATION, c.excitationAttackSpeedBonus, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.CHANT_SPEED, EXCITATION, c.excitationChantSpeedBonus, Operation.ADD_VALUE);
   }
}
