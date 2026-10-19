package io.github.Memoires.mysticism.effect.ability;

import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class CollectiveEffect extends TensuraMobEffect implements DamageAction {
   public static final ResourceLocation COLLECTIVE = ResourceLocation.fromNamespaceAndPath("mysticism", "collective");

   public CollectiveEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 181, 0, 255).getRGB());
      this.addAttributeModifier(Attributes.ATTACK_SPEED, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.MAX_HEALTH, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.STEP_HEIGHT, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.WATER_MOVEMENT_EFFICIENCY, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.MAX_AURA, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.MAX_MAGICULE, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.MAX_SPIRITUAL_HEALTH, COLLECTIVE, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
   }
}
