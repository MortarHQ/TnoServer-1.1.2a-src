package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonLawClosedPrisonVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

final class AvalonLawOfClosedPrisonHandler implements AvalonLawHandler {
   @Override
   public String name() {
      return "Law of the Closed Prison";
   }

   @Override
   public void sendVfx(ServerLevel level, LivingEntity owner, float radius, int durationTicks, int variant) {
      AvalonLawClosedPrisonVfxPayload.send(level, owner, null, owner.position(), owner.position(), radius, durationTicks, variant);
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.tickCount % 20 == 0) {
         for (LivingEntity enemy : RoyalSkillSupport.enemiesInRange(owner, (Double)MoreSkillsConfigs.GRAND.avalon.royalLawRadius.get())) {
            RoyalSkillSupport.applyEffect(enemy, owner, TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT), 80, 1);
         }
      }
   }
}
