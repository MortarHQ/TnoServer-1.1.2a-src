package com.github.wal_bos.moreskills.effect;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.awt.Color;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class OrganDecayEffect extends TensuraMobEffect {
   public OrganDecayEffect() {
      super(MobEffectCategory.HARMFUL, new Color(92, 52, 52).getRGB());
      this.addAttributeModifier(
         Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_health"), -0.75, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_damage"), -0.95, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_armor"), -0.5, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_speed"), -0.25, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_attack_speed"), -0.35, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         Attributes.KNOCKBACK_RESISTANCE,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_balance"),
         -0.65,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_mp_regen"),
         -0.65,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.AURA_REGENERATION_MULTIPLIER,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_aura_regen"),
         -0.65,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_spiritual_regen"),
         -0.75,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.MAGIC_COST_MULTIPLIER,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_magic_cost"),
         0.75,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.AUTO_MELEE_DODGE_CHANCE,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_melee_dodge"),
         -0.75,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_projectile_dodge"),
         -0.75,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.DODGE_STRENGTH,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_dodge_strength"),
         -0.8,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.DODGE_INVULNERABILITY,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "organ_decay_dodge_invulnerability"),
         -0.8,
         Operation.ADD_MULTIPLIED_TOTAL
      );
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return duration % 40 == 0;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      int level = amplifier + 1;
      float missing = Math.max(0.0F, entity.getMaxHealth() - entity.getHealth());
      float damage = 8.0F + level * 5.0F + missing * 0.04F;
      boolean criticalBodyFailure = entity.getHealth() <= entity.getMaxHealth() * 0.35F;
      boolean terminalRot = false;
      if (criticalBodyFailure) {
         damage += entity.getMaxHealth() * (0.03F + 0.015F * level);
         entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.35, 0.15, 0.35));
         entity.hurtMarked = true;
      }

      try {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         double spiritual = existence.getSpiritualHealth();
         double magicule = existence.getMagicule();
         double aura = existence.getAura();
         double spiritualLoss = Math.max(700.0 * level, spiritual * (0.02 + level * 0.01));
         double magiculeLoss = Math.max(700.0 * level, magicule * (0.02 + level * 0.01));
         double auraLoss = Math.max(500.0 * level, aura * (0.015 + level * 0.0075));
         if (criticalBodyFailure) {
            spiritualLoss *= 2.0;
            magiculeLoss *= 2.0;
            auraLoss *= 2.0;
         }

         if (spiritual <= spiritualLoss || magicule <= magiculeLoss) {
            terminalRot = true;
            damage += entity.getMaxHealth() * 0.08F;
         }

         existence.setSpiritualHealth(Math.max(0.0, spiritual - spiritualLoss));
         existence.setMagicule(Math.max(0.0, magicule - magiculeLoss));
         existence.setAura(Math.max(0.0, aura - auraLoss));
         if (amplifier >= 2 && existence.getSpiritualHealth() <= 0.0) {
            damage += entity.getMaxHealth() * 0.12F;
         }

         if (amplifier >= 3 && existence.getSpiritualHealth() <= 0.0 && existence.getMagicule() <= 0.0 && entity.getHealth() > 1.0F) {
            entity.setHealth(Math.max(1.0F, entity.getHealth() - entity.getMaxHealth() * 0.2F));
         }

         existence.markDirty();
      } catch (Throwable var21) {
      }

      entity.hurt(entity.damageSources().magic(), damage);
      if (entity.getHealth() > entity.getMaxHealth()) {
         entity.setHealth(entity.getMaxHealth());
      }

      if (entity.level() instanceof ServerLevel serverLevel) {
         int count = terminalRot ? 18 : 8;
         serverLevel.sendParticles(
            ParticleTypes.DAMAGE_INDICATOR,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5,
            entity.getZ(),
            count,
            entity.getBbWidth() * 0.45,
            entity.getBbHeight() * 0.35,
            entity.getBbWidth() * 0.45,
            0.02
         );
      }

      return true;
   }
}
