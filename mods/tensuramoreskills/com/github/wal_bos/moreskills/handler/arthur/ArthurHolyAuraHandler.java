package com.github.wal_bos.moreskills.handler.arthur;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class ArthurHolyAuraHandler implements ArthurModeHandler {
   private static final ResourceLocation LIGHT_COATING = RoyalSkillSupport.id("arthur_holy_aura_light_coating");
   private static final Set<UUID> ACTIVE = new HashSet<>();
   private static final int REFRESH_INTERVAL = 40;
   private static final int EFFECT_DURATION = 120;
   private static final int COST_INTERVAL = 80;

   @Override
   public String modeId() {
      return "arthur_once_and_future_king.holy_aura";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 0.0;
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         UUID id = entity.getUUID();
         if (ACTIVE.remove(id)) {
            removeAura(entity);
         } else {
            ACTIVE.add(id);
            applyAura(entity);
            instance.addMasteryPoint(entity);
         }

         instance.markDirty();
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         if (ACTIVE.contains(entity.getUUID())) {
            if (entity.isAlive() && !entity.isRemoved()) {
               if (entity.tickCount % 80 == 0) {
                  if (!RoyalSkillSupport.spendMagicule(entity, (Double)MoreSkillsConfigs.GRAND.arthur.holyAuraCostPerPulse.get())) {
                     ACTIVE.remove(entity.getUUID());
                     removeAura(entity);
                     instance.markDirty();
                     return;
                  }

                  instance.addMasteryPoint(entity);
               }

               if (entity.tickCount % 40 == 0) {
                  applyAura(entity);
               }
            } else {
               ACTIVE.remove(entity.getUUID());
               removeAura(entity);
            }
         }
      }
   }

   private static void applyAura(LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LIGHT_BOOST, LIGHT_COATING, 1.0, Operation.ADD_VALUE);
      RoyalSkillSupport.applyEffect(entity, entity, TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_AURA), 120, 0);
      RoyalSkillSupport.applyEffect(entity, entity, TensuraMobEffects.getReference(TensuraMobEffects.AURA_SWORD), 120, 1);
      RoyalSkillSupport.applyEffect(entity, entity, TensuraMobEffects.getReference(TensuraMobEffects.HOLY_DAMAGE), 120, 1);
   }

   private static void removeAura(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LIGHT_BOOST, LIGHT_COATING);
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_AURA));
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.AURA_SWORD));
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.HOLY_DAMAGE));
   }

   @Override
   public void cleanup(LivingEntity entity) {
      ACTIVE.remove(entity.getUUID());
      removeAura(entity);
   }
}
