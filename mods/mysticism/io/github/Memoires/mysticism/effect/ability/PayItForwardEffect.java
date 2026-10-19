package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class PayItForwardEffect extends TensuraMobEffect {
   protected static final UniqueMysticismSkillConfig.Provider CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Provider;
   public static final ResourceLocation PAY_IT_FORWARD = ResourceLocation.fromNamespaceAndPath("mysticism", "pay_it_forward");

   public PayItForwardEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 181, 0, 255).getRGB());
   }

   public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
      super.addAttributeModifiers(attributeMap, amplifier);
      double speedBonus;
      double stepBonus;
      switch (Math.max(0, amplifier)) {
         case 0:
            speedBonus = 0.1;
            stepBonus = 0.5;
            break;
         case 1:
            speedBonus = 0.15;
            stepBonus = 1.0;
            break;
         case 2:
            speedBonus = 0.2;
            stepBonus = 1.5;
            break;
         case 3:
            speedBonus = 0.35;
            stepBonus = 2.0;
            break;
         default:
            speedBonus = 0.5;
            stepBonus = 3.0;
      }

      AttributeInstance speed = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(PAY_IT_FORWARD);
         speed.addTransientModifier(new AttributeModifier(PAY_IT_FORWARD, speedBonus, Operation.ADD_VALUE));
      }

      AttributeInstance step = attributeMap.getInstance(Attributes.STEP_HEIGHT);
      if (step != null) {
         step.removeModifier(PAY_IT_FORWARD);
         step.addTransientModifier(new AttributeModifier(PAY_IT_FORWARD, stepBonus, Operation.ADD_VALUE));
      }
   }

   public void removeAttributeModifiers(AttributeMap attributeMap) {
      super.removeAttributeModifiers(attributeMap);
      AttributeInstance speed = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(PAY_IT_FORWARD);
      }

      AttributeInstance step = attributeMap.getInstance(Attributes.STEP_HEIGHT);
      if (step != null) {
         step.removeModifier(PAY_IT_FORWARD);
      }
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)UniqueMysticismSkills.PROVIDER.get());
      if (!opt.isEmpty()) {
         opt.get().addMasteryPoint(entity);
         int cooldown = opt.get().isMastered(entity) ? CONFIG.payItForwardCooldownMastered : CONFIG.payItForwardCooldown;
         opt.get().setCoolDown(cooldown, 1);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      }
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      return pDuration % 20 == 0;
   }

   public boolean applyEffectTick(LivingEntity livingEntity, int i) {
      Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(livingEntity).getSkill((ManasSkill)UniqueMysticismSkills.PROVIDER.get());
      if (opt.isEmpty()) {
         return true;
      }

      if (EnergyHelper.isOutOfEnergy(livingEntity, opt.get(), 1)) {
         if (livingEntity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensura.skill.lack_magicule.toggled_off", new Object[]{opt.get().getSkill().getName()})
                  .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)),
               false
            );
         }

         livingEntity.level()
            .playSound(
               null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
            );
         livingEntity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.PAY_IT_FORWARD));
      }

      return true;
   }
}
