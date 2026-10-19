package com.github.wal_bos.moreskills.effect;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.awt.Color;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NaberiusWillEffect extends TensuraMobEffect {
   public NaberiusWillEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(120, 185, 120).getRGB());
      this.addAttributeModifier(
         Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will_health"), 0.75, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will_damage"), 0.45, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will_armor"), 40.0, Operation.ADD_VALUE);
      this.addAttributeModifier(
         Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will_knockback"), 0.5, Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will_mp_regen"),
         1.5,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.AURA_REGENERATION_MULTIPLIER,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will_aura_regen"),
         1.5,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will_spiritual_regen"),
         75.0,
         Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.DODGE_INVULNERABILITY,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will_dodge_invulnerability"),
         0.35,
         Operation.ADD_VALUE
      );
      this.addAttributeModifier(
         TensuraAttributes.DODGE_STRENGTH,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will_dodge_strength"),
         0.35,
         Operation.ADD_VALUE
      );
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return duration % 80 == 0;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      int level = amplifier + 1;
      entity.heal(Math.max(6.0F, entity.getMaxHealth() * 0.025F));
      if (entity.getHealth() <= entity.getMaxHealth() * 0.3F) {
         entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT), 100, 1 + amplifier, false, false, true));
         entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION), 100, amplifier, false, false, true));
         entity.heal(Math.max(12.0F, entity.getMaxHealth() * 0.08F));

         for (LivingEntity target : entity.level()
            .getEntitiesOfClass(
               LivingEntity.class,
               new AABB(entity.blockPosition()).inflate(5.0 + level),
               targetx -> targetx != entity && targetx.isAlive() && !targetx.isAlliedTo(entity)
            )) {
            Vec3 vec = target.position().subtract(entity.position());
            if (vec.lengthSqr() > 0.001) {
               Vec3 push = vec.normalize().scale(0.8 + level * 0.15);
               target.setDeltaMovement(target.getDeltaMovement().add(push.x, 0.35, push.z));
               target.hurtMarked = true;
            }
         }

         TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.SOUL_FIRE_FLAME, 2.0);
      }

      try {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         existence.setMagicule(existence.getMagicule() + 2500.0 * level);
         existence.setAura(existence.getAura() + 2500.0 * level);
         existence.setSpiritualHealth(existence.getSpiritualHealth() + 1200.0 * level);
         if (entity.getHealth() <= entity.getMaxHealth() * 0.15F) {
            existence.setMagicule(existence.getMagicule() + 6500.0 * level);
            existence.setAura(existence.getAura() + 6500.0 * level);
            existence.setSpiritualHealth(existence.getSpiritualHealth() + 4000.0 * level);
         }

         existence.markDirty();
      } catch (Throwable var8) {
      }

      return true;
   }
}
