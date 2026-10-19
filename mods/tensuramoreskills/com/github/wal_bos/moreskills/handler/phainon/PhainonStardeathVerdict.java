package com.github.wal_bos.moreskills.handler.phainon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public final class PhainonStardeathVerdict {
   private PhainonStardeathVerdict() {
   }

   public static void execute(LivingEntity entity, ManasSkillInstance instance) {
      PhainonProgress progress = PhainonProgress.get(instance);
      boolean giant = progress.consumeBlaze(4);
      double radius = giant ? 12.0 : 8.0;
      float damage = giant ? 1600.0F : 500.0F;

      for (LivingEntity target : entity.level()
         .getEntitiesOfClass(
            LivingEntity.class, entity.getBoundingBox().inflate(radius), targetx -> targetx != entity && targetx.isAlive() && !targetx.isAlliedTo(entity)
         )) {
         DamageSource source = TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, entity);
         target.hurt(source, damage + progress.getCoreFlameCount() * 100.0F);
         target.setRemainingFireTicks(160);
      }

      entity.sendSystemMessage(
         Component.literal(giant ? "Foundation: Stardeath Verdict descends." : "Foundation: Stardeath Verdict rains.").withStyle(ChatFormatting.GOLD)
      );
   }
}
