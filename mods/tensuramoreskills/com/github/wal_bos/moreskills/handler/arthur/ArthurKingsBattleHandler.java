package com.github.wal_bos.moreskills.handler.arthur;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.ArthurKingsBattleVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public final class ArthurKingsBattleHandler implements ArthurModeHandler {
   @Override
   public String modeId() {
      return "arthur_once_and_future_king.kings_battle";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 0.0;
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return true;
      } else if (!(entity.level() instanceof ServerLevel level)) {
         return true;
      } else {
         if (heldTicks % 10 != 0) {
            return true;
         }

         if (!RoyalSkillSupport.spendMagicule(entity, (Double)MoreSkillsConfigs.GRAND.arthur.kingsBattleCostPerPulse.get())) {
            return false;
         }

         double radius = (Double)MoreSkillsConfigs.GRAND.arthur.kingsBattleRadius.get();

         for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius))) {
            RoyalSkillSupport.applyEffect(living, entity, TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 30, 0);
            RoyalSkillSupport.applyEffect(living, entity, TensuraMobEffects.getReference(TensuraMobEffects.SILENCE), 30, 0);
            RoyalSkillSupport.applyEffect(living, entity, TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), 30, 1);
            RoyalSkillSupport.forceUntoggleAndDelay(living, 30);
         }

         ArthurKingsBattleVfxPayload.send(level, entity, null, entity.position(), entity.position(), (float)radius, 24, heldTicks / 10);
         if (heldTicks % 20 == 0) {
            level.playSound(null, entity.blockPosition(), SoundEvents.IRON_GOLEM_REPAIR, SoundSource.PLAYERS, 2.0F, 0.55F);
         }

         if (heldTicks % 80 == 0) {
            instance.addMasteryPoint(entity);
         }

         return true;
      }
   }
}
