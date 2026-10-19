package com.github.wal_bos.moreskills.effect;

import com.github.wal_bos.moreskills.ability.skill.shades.NaberiusSkill;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.awt.Color;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LifeSeizureEffect extends TensuraMobEffect {
   public LifeSeizureEffect() {
      super(MobEffectCategory.HARMFUL, new Color(56, 120, 72).getRGB());
      this.addAttributeModifier(
         Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "life_seizure_health"), -0.45, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "life_seizure_armor"), -0.55, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         Attributes.KNOCKBACK_RESISTANCE,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "life_seizure_grip"),
         -0.75,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.MAGIC_COST_MULTIPLIER,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "life_seizure_cost"),
         1.5,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "life_seizure_mp_regen"),
         -0.95,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.AURA_REGENERATION_MULTIPLIER,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "life_seizure_aura_regen"),
         -0.85,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "life_seizure_spiritual_regen"),
         -0.85,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.PRESENCE_CONCEALMENT,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "life_seizure_revealed"),
         -1.0,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      this.addAttributeModifier(
         TensuraAttributes.CHANT_SPEED,
         ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "life_seizure_chant_speed"),
         -1.0,
         Operation.ADD_MULTIPLIED_TOTAL
      );
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return duration % 20 == 0;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      int level = amplifier + 1;
      float max = entity.getMaxHealth();
      float damage = Math.min(max * 0.3F, 8.0F + level * 7.0F + max * 0.04F);
      double stolenMagicule = 0.0;
      double stolenSpiritual = 0.0;
      double stolenAura = 0.0;
      boolean hollowed = false;

      try {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         double magicule = existence.getMagicule();
         double spiritual = existence.getSpiritualHealth();
         double aura = existence.getAura();
         stolenMagicule = Math.max(1500.0 * level, magicule * (0.05 + 0.02 * level));
         stolenSpiritual = Math.max(1000.0 * level, spiritual * (0.04 + 0.015 * level));
         stolenAura = Math.max(1000.0 * level, aura * (0.04 + 0.015 * level));
         if (entity.getHealth() <= entity.getMaxHealth() * 0.35F) {
            stolenMagicule *= 1.75;
            stolenSpiritual *= 1.75;
            stolenAura *= 1.75;
            damage += entity.getMaxHealth() * 0.05F;
         }

         existence.setMagicule(Math.max(0.0, magicule - stolenMagicule));
         existence.setSpiritualHealth(Math.max(0.0, spiritual - stolenSpiritual));
         existence.setAura(Math.max(0.0, aura - stolenAura));
         hollowed = existence.getMagicule() <= 0.0 || existence.getSpiritualHealth() <= 0.0;
         if (hollowed) {
            damage += entity.getMaxHealth() * 0.1F;
         }

         existence.markDirty();
      } catch (Throwable var20) {
      }

      entity.hurt(entity.damageSources().magic(), damage);
      if (entity.level() instanceof ServerLevel levelWorld) {
         LivingEntity feeder = this.findNearestNaberius(entity, levelWorld);
         if (feeder != null) {
            this.pullToward(entity, feeder, level);
         }

         this.feedNearbyNaberius(entity, levelWorld, damage, stolenMagicule, stolenSpiritual, stolenAura, hollowed);
         int count = hollowed ? 24 : 12;
         levelWorld.sendParticles(
            ParticleTypes.SOUL,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5,
            entity.getZ(),
            count,
            entity.getBbWidth() * 0.6,
            entity.getBbHeight() * 0.4,
            entity.getBbWidth() * 0.6,
            0.035
         );
      }

      return true;
   }

   private LivingEntity findNearestNaberius(LivingEntity victim, ServerLevel level) {
      AABB box = victim.getBoundingBox().inflate(32.0);
      LivingEntity nearest = null;
      double best = Double.MAX_VALUE;

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && NaberiusSkill.isNaberiusSummon(e))) {
         double distance = living.distanceToSqr(victim);
         if (distance < best) {
            best = distance;
            nearest = living;
         }
      }

      return nearest;
   }

   private void pullToward(LivingEntity victim, LivingEntity feeder, int level) {
      Vec3 vec = feeder.position().subtract(victim.position());
      if (vec.lengthSqr() > 0.001) {
         Vec3 pull = vec.normalize().scale(0.18 + level * 0.05);
         victim.setDeltaMovement(victim.getDeltaMovement().multiply(0.35, 0.15, 0.35).add(pull.x, 0.04, pull.z));
         victim.hurtMarked = true;
      }
   }

   private void feedNearbyNaberius(LivingEntity victim, ServerLevel level, float damage, double magicule, double spiritual, double aura, boolean hollowed) {
      AABB box = victim.getBoundingBox().inflate(32.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && NaberiusSkill.isNaberiusSummon(e))) {
         living.heal(Math.max(10.0F, damage * (hollowed ? 2.5F : 1.5F)));

         try {
            IExistence ex = TensuraStorages.getExistenceFrom(living);
            ex.setMagicule(ex.getMagicule() + magicule * (hollowed ? 0.4 : 0.25));
            ex.setSpiritualHealth(ex.getSpiritualHealth() + spiritual * (hollowed ? 0.4 : 0.25));
            ex.setAura(ex.getAura() + aura * (hollowed ? 0.4 : 0.25));
            ex.markDirty();
         } catch (Throwable var18) {
         }

         UUID ownerId = NaberiusSkill.getNaberiusOwnerUUID(living);
         if (ownerId != null) {
            ServerPlayer owner = level.getServer().getPlayerList().getPlayer(ownerId);
            if (owner != null) {
               try {
                  IExistence ownerEx = TensuraStorages.getExistenceFrom(owner);
                  ownerEx.setMagicule(ownerEx.getMagicule() + magicule * (hollowed ? 0.18 : 0.1));
                  ownerEx.setSpiritualHealth(ownerEx.getSpiritualHealth() + spiritual * (hollowed ? 0.18 : 0.1));
                  ownerEx.setAura(ownerEx.getAura() + aura * (hollowed ? 0.18 : 0.1));
                  ownerEx.markDirty();
               } catch (Throwable var17) {
               }
            }
         }
      }
   }
}
