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

public class SeveredArmEffect extends TensuraMobEffect {
   protected static final UniqueMysticismSkillConfig.Butcher CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Butcher;
   public static final ResourceLocation SEVERED_ARM = ResourceLocation.fromNamespaceAndPath("mysticism", "severed_arm");

   public SeveredArmEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 45, 100, 150).getRGB());
   }

   public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
      super.addAttributeModifiers(attributeMap, amplifier);

      double attributeMultiplier = switch (Math.max(0, amplifier)) {
         case 0 -> 0.5;
         case 1 -> 1.0;
         default -> 1.5;
      };
      AttributeInstance speed = attributeMap.getInstance(Attributes.ATTACK_SPEED);
      if (speed != null) {
         speed.removeModifier(SEVERED_ARM);
         speed.addTransientModifier(new AttributeModifier(SEVERED_ARM, -attributeMultiplier, Operation.ADD_MULTIPLIED_TOTAL));
      }

      AttributeInstance step = attributeMap.getInstance(Attributes.ATTACK_DAMAGE);
      if (step != null) {
         step.removeModifier(SEVERED_ARM);
         step.addTransientModifier(new AttributeModifier(SEVERED_ARM, -attributeMultiplier, Operation.ADD_MULTIPLIED_TOTAL));
      }
   }

   public void removeAttributeModifiers(AttributeMap attributeMap) {
      super.removeAttributeModifiers(attributeMap);
      AttributeInstance speed = attributeMap.getInstance(Attributes.ATTACK_SPEED);
      if (speed != null) {
         speed.removeModifier(SEVERED_ARM);
      }

      AttributeInstance step = attributeMap.getInstance(Attributes.ATTACK_DAMAGE);
      if (step != null) {
         step.removeModifier(SEVERED_ARM);
      }
   }
}
