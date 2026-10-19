package com.github.wal_bos.moreskills.effect;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.AABB;

public class NaberiusVisionEffect extends TensuraMobEffect {
   public NaberiusVisionEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(180, 255, 180).getRGB());
      this.addAttributeModifier(
         TensuraAttributes.PRESENCE_SENSE, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision_sense"), 1.0, Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.PRESENCE_SENSE_RADIUS,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision_sense_radius"),
         10.0,
         Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.HEAT_SENSE_RADIUS,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision_heat_radius"),
         4.0,
         Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.ANALYSIS_LEVEL, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision_analysis"), 100.0, Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.ANALYSIS_DISTANCE,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision_analysis_distance"),
         2.0,
         Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.CHANT_SPEED, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision_chant"), 1.0, Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.RESISTANCE_DEGRADATION,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision_pierce"),
         0.25,
         Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.DODGE_NEGATE_CHANCE,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision_dodge_negate"),
         20.0,
         Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.MAGIC_COST_MULTIPLIER,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision_cost"),
         -0.2,
         Operation.ADD_MULTIPLIED_TOTAL
      );
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return duration % 100 == 0;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      int level = amplifier + 1;
      entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FUTURE_VISION), 120, Math.min(2, amplifier), false, false, true));
      int sensed = entity.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            new AABB(entity.blockPosition()).inflate(24.0 + level * 8.0),
            target -> target != entity && target.isAlive() && !target.isAlliedTo(entity)
         )
         .size();
      if (sensed > 0) {
         try {
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            existence.setMagicule(existence.getMagicule() + Math.min(5000.0 * level, 350.0 * sensed * level));
            existence.setAura(existence.getAura() + Math.min(5000.0 * level, 350.0 * sensed * level));
            existence.markDirty();
         } catch (Throwable var6) {
         }
      }

      return true;
   }
}
