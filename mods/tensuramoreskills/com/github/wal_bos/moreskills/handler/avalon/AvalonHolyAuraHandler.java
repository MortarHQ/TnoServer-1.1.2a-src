package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonHolyAuraVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class AvalonHolyAuraHandler implements AvalonModeHandler {
   private static final String ACTIVE = "AvalonHolyAuraActive";
   private static final ResourceLocation LIGHT_COATING = RoyalSkillSupport.id("avalon_holy_aura_light_coating");

   @Override
   public String modeId() {
      return "avalon_god_of_eternal_kingdom.holy_aura";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 0.0;
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         CompoundTag tag = instance.getOrCreateTag();
         boolean active = !tag.getBoolean("AvalonHolyAuraActive");
         tag.putBoolean("AvalonHolyAuraActive", active);
         if (active) {
            applyAura(entity);
            sendVfx(level, entity, 0);
            level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.8F, 1.65F);
            instance.addMasteryPoint(entity);
         } else {
            removeAura(entity);
            sendVfx(level, entity, -1);
            level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 2.8F, 0.65F);
         }

         instance.markDirty();
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("AvalonHolyAuraActive")) {
            if (!entity.isAlive() || entity.isRemoved()) {
               tag.putBoolean("AvalonHolyAuraActive", false);
               removeAura(entity);
               instance.markDirty();
            } else if (entity.tickCount % 10 == 0) {
               if (!RoyalSkillSupport.spendMagicule(entity, (Double)MoreSkillsConfigs.GRAND.avalon.holyAuraCostPerPulse.get())) {
                  tag.putBoolean("AvalonHolyAuraActive", false);
                  removeAura(entity);
                  sendVfx(level, entity, -1);
                  instance.markDirty();
               } else {
                  applyAura(entity);
                  double radius = (Double)MoreSkillsConfigs.GRAND.avalon.holyAuraRadius.get();
                  float damage = ((Double)MoreSkillsConfigs.GRAND.avalon.holyAuraDamage.get()).floatValue();
                  RoyalSkillSupport.damageArea(entity, instance, 6, entity.position(), radius, damage * 0.65F, TensuraDamageTypes.HOLY_DAMAGE);
                  RoyalSkillSupport.damageArea(entity, instance, 6, entity.position(), radius, damage * 0.35F, TensuraDamageTypes.LIGHT_ELEMENTAL);
                  sendVfx(level, entity, Math.max(1, entity.tickCount / 10));
                  if (entity.tickCount % 60 == 0) {
                     instance.addMasteryPoint(entity);
                  }
               }
            }
         }
      }
   }

   private static void applyAura(LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LIGHT_BOOST, LIGHT_COATING, 2.0, Operation.ADD_VALUE);
      RoyalSkillSupport.applyEffect(entity, entity, TensuraMobEffects.getReference(TensuraMobEffects.AURA_SWORD), 30, 2);
      RoyalSkillSupport.applyEffect(entity, entity, TensuraMobEffects.getReference(TensuraMobEffects.HOLY_DAMAGE), 30, 2);
   }

   private static void removeAura(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LIGHT_BOOST, LIGHT_COATING);
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.AURA_SWORD));
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.HOLY_DAMAGE));
   }

   private static void sendVfx(ServerLevel level, LivingEntity entity, int variant) {
      float radius = ((Double)MoreSkillsConfigs.GRAND.avalon.holyAuraRadius.get()).floatValue();
      AvalonHolyAuraVfxPayload.send(level, entity, null, entity.position().add(0.0, 1.0, 0.0), entity.position(), radius, 28, variant);
   }

   @Override
   public void cleanup(LivingEntity entity) {
      removeAura(entity);
      if (entity.level() instanceof ServerLevel level) {
         sendVfx(level, entity, -1);
      }
   }
}
