package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class ProviderBoostEffect extends TensuraMobEffect implements DamageAction {
   protected static final UniqueMysticismSkillConfig.Provider CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Provider;
   public static final ResourceLocation PROVIDER_BOOST = ResourceLocation.fromNamespaceAndPath("mysticism", "provider_boost");

   public ProviderBoostEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 181, 0, 255).getRGB());
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, PROVIDER_BOOST, CONFIG.generosityEntitySpeedBoost, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.STEP_HEIGHT, PROVIDER_BOOST, CONFIG.generosityEntityStepHeightBoost, Operation.ADD_VALUE);
   }

   public boolean onDamagingEntity(LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      float damage = (Float)amount.get();
      amount.set((float)(damage * CONFIG.generosityEntityDamageBoost));
      return true;
   }
}
