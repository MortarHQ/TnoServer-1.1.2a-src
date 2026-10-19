package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.skill.UncontrolledJinchurikiEntity;
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

public class TailedBeastCloakEffect extends TensuraMobEffect {
   protected static final UniqueMysticismSkillConfig.Jinchuriki CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class))
      .Jinchuriki;
   protected static final ResourceLocation TAILED_BEAST_CLOAK = ResourceLocation.fromNamespaceAndPath("mysticism", "tailed_beast_cloak");

   public TailedBeastCloakEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 130, 0, 255).getRGB());
   }

   public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
      super.addAttributeModifiers(attributeMap, amplifier);
      double damageBonus;
      double armorIncrease;
      double movementSpeedIncrease;
      switch (Math.max(0, amplifier)) {
         case 0:
            damageBonus = 9.0;
            armorIncrease = 5.0;
            movementSpeedIncrease = 0.04;
            break;
         case 1:
            damageBonus = 15.0;
            armorIncrease = 10.0;
            movementSpeedIncrease = 0.08;
            break;
         case 2:
            damageBonus = 30.0;
            armorIncrease = 20.0;
            movementSpeedIncrease = 0.12;
            break;
         case 3:
            damageBonus = 36.0;
            armorIncrease = 35.0;
            movementSpeedIncrease = 0.15;
            break;
         default:
            damageBonus = 45.0;
            armorIncrease = 60.0;
            movementSpeedIncrease = 0.18;
      }

      AttributeInstance attackDmg = attributeMap.getInstance(Attributes.ATTACK_DAMAGE);
      if (attackDmg != null) {
         attackDmg.removeModifier(TAILED_BEAST_CLOAK);
         attackDmg.addTransientModifier(new AttributeModifier(TAILED_BEAST_CLOAK, damageBonus, Operation.ADD_VALUE));
      }

      AttributeInstance armor = attributeMap.getInstance(Attributes.ARMOR);
      if (armor != null) {
         armor.removeModifier(TAILED_BEAST_CLOAK);
         armor.addTransientModifier(new AttributeModifier(TAILED_BEAST_CLOAK, armorIncrease, Operation.ADD_VALUE));
      }

      AttributeInstance speed = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(TAILED_BEAST_CLOAK);
         speed.addTransientModifier(new AttributeModifier(TAILED_BEAST_CLOAK, movementSpeedIncrease, Operation.ADD_VALUE));
      }
   }

   public void removeAttributeModifiers(AttributeMap attributeMap) {
      super.removeAttributeModifiers(attributeMap);
      AttributeInstance attackDmg = attributeMap.getInstance(Attributes.ATTACK_DAMAGE);
      if (attackDmg != null) {
         attackDmg.removeModifier(TAILED_BEAST_CLOAK);
      }

      AttributeInstance armor = attributeMap.getInstance(Attributes.ARMOR);
      if (armor != null) {
         armor.removeModifier(TAILED_BEAST_CLOAK);
      }

      AttributeInstance speed = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(TAILED_BEAST_CLOAK);
      }
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      return pDuration % 20 == 0 && pDuration != 0;
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      if (entity instanceof UncontrolledJinchurikiEntity jinch) {
         LivingEntity owner = jinch.getOwner();
         if (owner != null) {
            Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)UniqueMysticismSkills.JINCHURIKI.get());
            if (opt.isEmpty()) {
               return;
            }

            int cooldown = opt.get().isMastered(owner) ? CONFIG.tailedBeastCloakCooldownMastered : CONFIG.tailedBeastCloakCooldown;
            opt.get().setCoolDown(cooldown, 1);
            entity.level()
               .playSound(null, owner.getX(), owner.getY(), owner.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         }
      } else {
         Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)UniqueMysticismSkills.JINCHURIKI.get());
         if (opt.isEmpty()) {
            return;
         }

         int cooldown = opt.get().isMastered(entity) ? CONFIG.tailedBeastCloakCooldownMastered : CONFIG.tailedBeastCloakCooldown;
         opt.get().setCoolDown(cooldown, 1);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      }
   }

   public boolean applyEffectTick(LivingEntity livingEntity, int i) {
      if (livingEntity instanceof UncontrolledJinchurikiEntity) {
         return true;
      }

      Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(livingEntity).getSkill((ManasSkill)UniqueMysticismSkills.JINCHURIKI.get());
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
         livingEntity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.TAILED_BEAST_CLOAK));
      }

      return true;
   }
}
