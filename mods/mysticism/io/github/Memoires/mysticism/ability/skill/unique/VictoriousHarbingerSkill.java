package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.extra.HakiSkill;
import io.github.manasmods.tensura.entity.magic.field.haki.HakiField.HakiVariant;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class VictoriousHarbingerSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.VictoriousHarbinger CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(
         UniqueMysticismSkillConfig.class
      ))
      .VictoriousHarbinger;
   protected static final ResourceLocation VICTORIOUS_HARBINGER = ResourceLocation.fromNamespaceAndPath("mysticism", "victorious_harbinger");

   public VictoriousHarbingerSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/victorious_harbinger.png");
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode - 1 < 0 ? this.getModes(instance) - 1 : mode - 1;
      } else {
         return (mode + 1) % this.getModes(instance);
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "victorious_harbinger.might";
         case 1 -> "victorious_harbinger.tenacious_resolve";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.mightCost;
         case 1 -> CONFIG.tenaciousResolveCost;
         default -> 0.0;
      };
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      Holder<MobEffect> effectHolder = ((MobEffectInstance)Objects.requireNonNull((MobEffectInstance)effect.get())).getEffect();
      String id = effectHolder.unwrapKey().map(key -> key.location().toString()).orElse(null);
      return id == null || !CONFIG.immunityList.contains(id);
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance relentlessBarrage = entity.getAttribute(Attributes.ATTACK_SPEED);
      if (relentlessBarrage != null) {
         relentlessBarrage.addOrReplacePermanentModifier(new AttributeModifier(VICTORIOUS_HARBINGER, 0.1, Operation.ADD_MULTIPLIED_BASE));
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance relentlessBarrage = entity.getAttribute(Attributes.ATTACK_SPEED);
      if (relentlessBarrage != null) {
         relentlessBarrage.removeModifier(VICTORIOUS_HARBINGER);
      }

      super.onToggleOff(instance, entity);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 1) {
         for (MobEffectInstance effect : entity.getActiveEffects()) {
            MobEffect mobEffect = (MobEffect)effect.getEffect().value();
            if (mobEffect.getCategory() == MobEffectCategory.HARMFUL) {
               int oldAmplifier = effect.getAmplifier();
               int newAmplifier = Math.max(0, oldAmplifier - CONFIG.effectLevel);
               int oldDuration = effect.getDuration();
               int newDuration = (int)(oldDuration * 0.75F);
               entity.removeEffect(effect.getEffect());
               entity.addEffect(new MobEffectInstance(effect.getEffect(), newDuration, newAmplifier, effect.isAmbient(), effect.isVisible(), effect.showIcon()));
            }
         }

         instance.addMasteryPoint(entity);
         instance.setCoolDown(60, mode);
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      if (mode == 0) {
         if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(living, instance, mode)) {
            return false;
         }

         if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
            instance.addMasteryPoint(living);
         }

         HakiSkill.summonHaki(instance, living, mode, heldTicks, this, HakiVariant.MORTAL_FEAR, 0.5);
         return true;
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (TickingSkill.isTickingSkill(entity, this, 0)) {
         instance.setCoolDown(CONFIG.mightCooldown, mode);
      }
   }
}
