package com.github.wal_bos.moreskills.handler.phainon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public final class PhainonEraNova {
   private PhainonEraNova() {
   }

   public static void execute(LivingEntity entity, ManasSkillInstance instance) {
      PhainonProgress progress = PhainonProgress.get(instance);
      progress.clearCores();
      progress.addCycle();
      progress.setBlaze(0);
      PhainonDawnmaker.restore(entity, instance);
      PhainonDelivererActions.stop(entity, instance);
      AABB box = entity.getBoundingBox().inflate(128.0);

      for (LivingEntity target : entity.level()
         .getEntitiesOfClass(
            LivingEntity.class, box, targetx -> targetx != entity && targetx.isAlive() && targetx.isAlliedTo(entity) && !(targetx instanceof Player)
         )) {
         DamageSource source = TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, entity);
         target.hurt(source, Float.MAX_VALUE);
      }

      PhainonHandler.updateCoreAttributes(entity, instance);
      entity.sendSystemMessage(Component.literal("Era Nova begins. Cycle " + progress.getCycles() + ".").withStyle(ChatFormatting.DARK_RED));
   }
}
