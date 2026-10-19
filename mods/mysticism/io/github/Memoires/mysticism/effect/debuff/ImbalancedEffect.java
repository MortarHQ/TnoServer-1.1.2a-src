package io.github.Memoires.mysticism.effect.debuff;

import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect.AttributeTemplate;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class ImbalancedEffect extends TensuraMobEffect implements DamageAction {
   public static final ResourceLocation IMBALANCED = ResourceLocation.fromNamespaceAndPath("mysticism", "imbalanced");

   public ImbalancedEffect() {
      super(MobEffectCategory.NEUTRAL, new Color(255, 255, 255, 0).getRGB());
      this.addAttributeModifier(Attributes.ATTACK_SPEED, IMBALANCED, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public void addAttributeModifiers(AttributeMap attributeMap, int i) {
      for (Entry<Holder<Attribute>, AttributeTemplate> entry : this.attributeModifiers.entrySet()) {
         AttributeInstance attributeInstance = attributeMap.getInstance(entry.getKey());
         if (attributeInstance != null) {
            attributeInstance.removeModifier(entry.getValue().id());
            int level = entry.getValue().id().equals(ResourceLocation.fromNamespaceAndPath("mysticism", "imbalanced")) ? 0 : i;
            attributeInstance.addOrReplacePermanentModifier(entry.getValue().create(level));
         }
      }
   }
}
