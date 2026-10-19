package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect.AttributeTemplate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class CounteringEffect extends TensuraMobEffect implements DamageAction {
   private static final UniqueMysticismSkillConfig.Restricted CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Restricted;
   public static final ResourceLocation COUNTERING = ResourceLocation.fromNamespaceAndPath("mysticism", "countering");
   private static final String FORCE_REMOVE_TAG = "RestrictedForceRemove";

   public CounteringEffect() {
      super(MobEffectCategory.NEUTRAL, new Color(255, 255, 255, 0).getRGB());
      this.addAttributeModifier(Attributes.ATTACK_SPEED, COUNTERING, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, COUNTERING, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public void addAttributeModifiers(AttributeMap attributeMap, int i) {
      for (Entry<Holder<Attribute>, AttributeTemplate> entry : this.attributeModifiers.entrySet()) {
         AttributeInstance attributeInstance = attributeMap.getInstance(entry.getKey());
         if (attributeInstance != null) {
            attributeInstance.removeModifier(entry.getValue().id());
            int level = entry.getValue().id().equals(ResourceLocation.fromNamespaceAndPath("mysticism", "countering")) ? 0 : i;
            attributeInstance.addOrReplacePermanentModifier(entry.getValue().create(level));
         }
      }
   }

   public boolean onBeingDamaged(LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      if (!TensuraDamageHelper.isPhysicalAttack(source)) {
         return true;
      } else if (source.getEntity() instanceof LivingEntity living) {
         entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.COUNTERING));
         living.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.IMBALANCED), CONFIG.counterImbalanceDuration, 0, false, false, false),
            entity
         );
         return false;
      } else {
         return true;
      }
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      super.onEffectRemoved(entity, instance);
      if (!entity.level().isClientSide()) {
         SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)UniqueMysticismSkills.RESTRICTED.get()).ifPresent(msi -> {
            boolean incurCD = msi.getOrCreateTag().getBoolean("shouldRemoveWithoutCooldown");
            if (!incurCD) {
               int cooldown = msi.isMastered(entity) ? CONFIG.counterCooldownMastered : CONFIG.counterCooldown;
               msi.setCoolDown(cooldown, 1);
            }
         });
      }
   }
}
