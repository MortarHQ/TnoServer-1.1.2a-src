package io.github.Memoires.mysticism.effect.debuff;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EternalPermafrostEffect extends TensuraMobEffect {
   public static final ResourceLocation ETERNAL_PERMAFROST = ResourceLocation.fromNamespaceAndPath("mysticism", "eternal_permafrost");

   public EternalPermafrostEffect() {
      super(MobEffectCategory.HARMFUL, new Color(120, 220, 255).getRGB());
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ETERNAL_PERMAFROST, -0.15, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
      AttributeInstance movement = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
      if (movement != null) {
         movement.removeModifier(ETERNAL_PERMAFROST);
         double slowAmount = -0.3 - 0.15 * amplifier;
         slowAmount = Math.max(slowAmount, -0.95);
         movement.addOrReplacePermanentModifier(new AttributeModifier(ETERNAL_PERMAFROST, slowAmount, Operation.ADD_MULTIPLIED_TOTAL));
      }
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return duration % 40 == 0;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      MobEffectInstance effect = entity.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.ETERNAL_PERMAFROST));
      if (effect == null) {
         return true;
      }

      int amp = effect.getAmplifier();
      entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.ETERNAL_PERMAFROST));
      if (amp > 0) {
         entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.ETERNAL_PERMAFROST), effect.getDuration() - 1, amp - 1));
      }

      return true;
   }
}
