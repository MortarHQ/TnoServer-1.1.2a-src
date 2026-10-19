package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class SeveredLegEffect extends TensuraMobEffect {
   protected static final UniqueMysticismSkillConfig.Butcher CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Butcher;
   public static final ResourceLocation SEVERED_LEG = ResourceLocation.fromNamespaceAndPath("mysticism", "severed_leg");

   public SeveredLegEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 45, 100, 150).getRGB());
   }

   public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
      super.addAttributeModifiers(attributeMap, amplifier);

      double attributeMultiplier = switch (Math.max(0, amplifier)) {
         case 0 -> 0.5;
         case 1 -> 1.0;
         default -> 1.5;
      };
      AttributeInstance speed = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(SEVERED_LEG);
         speed.addTransientModifier(new AttributeModifier(SEVERED_LEG, -attributeMultiplier, Operation.ADD_MULTIPLIED_TOTAL));
      }

      AttributeInstance step = attributeMap.getInstance(Attributes.STEP_HEIGHT);
      if (step != null) {
         step.removeModifier(SEVERED_LEG);
         step.addTransientModifier(new AttributeModifier(SEVERED_LEG, -attributeMultiplier, Operation.ADD_MULTIPLIED_TOTAL));
      }

      AttributeInstance jump = attributeMap.getInstance(Attributes.JUMP_STRENGTH);
      if (jump != null) {
         jump.removeModifier(SEVERED_LEG);
         jump.addTransientModifier(new AttributeModifier(SEVERED_LEG, -attributeMultiplier, Operation.ADD_MULTIPLIED_TOTAL));
      }
   }

   public void removeAttributeModifiers(AttributeMap attributeMap) {
      super.removeAttributeModifiers(attributeMap);
      AttributeInstance speed = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(SEVERED_LEG);
      }

      AttributeInstance step = attributeMap.getInstance(Attributes.STEP_HEIGHT);
      if (step != null) {
         step.removeModifier(SEVERED_LEG);
      }

      AttributeInstance jump = attributeMap.getInstance(Attributes.JUMP_STRENGTH);
      if (jump != null) {
         jump.removeModifier(SEVERED_LEG);
      }
   }
}
