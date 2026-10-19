package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.ability.skill.unique.CultivatorSkill;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffect.AttributeTemplate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class CultivatingEffect extends TensuraMobEffect implements DamageAction {
   private static final ResourceLocation CULTIVATING = ResourceLocation.fromNamespaceAndPath("mysticism", "cultivating");

   public CultivatingEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(0, 255, 170, 100).getRGB());
      this.addAttributeModifier(TensuraAttributes.MAGICULE_GAIN, CULTIVATING, 2.0, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public void onEffectAdded(LivingEntity livingEntity, int i) {
      livingEntity.addEffect(
         new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN), 9, CultivatorSkill.CONFIG.cultivationTimer, false, false, false),
         livingEntity
      );
      livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1, CultivatorSkill.CONFIG.cultivationTimer, false, false, false), livingEntity);
   }

   public void addAttributeModifiers(AttributeMap attributeMap, int i) {
      for (Entry<Holder<Attribute>, AttributeTemplate> entry : this.attributeModifiers.entrySet()) {
         AttributeInstance attributeInstance = attributeMap.getInstance(entry.getKey());
         if (attributeInstance != null) {
            attributeInstance.removeModifier(entry.getValue().id());
            int level = entry.getValue().id().equals(ResourceLocation.fromNamespaceAndPath("mysticism", "cultivating")) ? 0 : i;
            attributeInstance.addOrReplacePermanentModifier(entry.getValue().create(level));
         }
      }
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      if (instance.getDuration() <= 0) {
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN));
         entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);
         Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)UniqueMysticismSkills.CULTIVATOR.get());
         if (opt.isPresent()) {
            ManasSkillInstance skillInstance = opt.get();
            skillInstance.getOrCreateTag().putInt("CultivationLevel", skillInstance.getOrCreateTag().getInt("CultivationLevel") + 1);
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable(
                        "mysticism.skill.mode.cultivator.breakthrough.broken_through", new Object[]{skillInstance.getOrCreateTag().getInt("CultivationLevel")}
                     )
                     .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
                  false
               );
            }

            skillInstance.addMasteryPoint(entity, CultivatorSkill.CONFIG.breakthroughMasteryGain);
            skillInstance.setCoolDown(CultivatorSkill.CONFIG.breakthroughCooldown, 0);
         }
      }
   }
}
