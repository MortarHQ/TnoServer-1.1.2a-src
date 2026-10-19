package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonLawCrushingHeavenVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

final class AvalonLawOfCrushingHeavenHandler implements AvalonLawHandler {
   @Override
   public String name() {
      return "Law of Crushing Heaven";
   }

   @Override
   public void sendVfx(ServerLevel level, LivingEntity owner, float radius, int durationTicks, int variant) {
      AvalonLawCrushingHeavenVfxPayload.send(level, owner, null, owner.position(), owner.position(), radius, durationTicks, variant);
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.tickCount % 20 == 0) {
         double threshold = EnergyHelper.getMaxEP(owner) * 0.75;

         for (LivingEntity enemy : RoyalSkillSupport.enemiesInRange(owner, (Double)MoreSkillsConfigs.GRAND.avalon.royalLawRadius.get())) {
            RoyalSkillSupport.applyEffect(enemy, owner, TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 60, 3);
            if (EnergyHelper.getMaxEP(enemy) >= threshold) {
               RoyalSkillSupport.applyEffect(enemy, owner, TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), 60, 2);
               if (!enemy.onGround()) {
                  enemy.setDeltaMovement(enemy.getDeltaMovement().multiply(0.25, 0.0, 0.25).add(0.0, -1.1, 0.0));
               }
            } else {
               RoyalSkillSupport.applyEffect(enemy, owner, TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), 60, 2);
            }
         }
      }
   }
}
