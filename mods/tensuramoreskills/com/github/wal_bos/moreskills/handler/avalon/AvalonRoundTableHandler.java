package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonRoundTableVfxPayload;
import com.github.wal_bos.moreskills.world.entity.AvalonKnightEntity;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AvalonRoundTableHandler implements AvalonModeHandler {
   @Override
   public String modeId() {
      return "avalon_god_of_eternal_kingdom.round_table";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.avalon.roundTableCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            removeOwned(entity);
            AvalonKnightEntity.Virtue[] virtues = AvalonKnightEntity.Virtue.values();

            for (int i = 0; i < virtues.length; i++) {
               double angle = i * Math.PI * 2.0 / virtues.length;
               Vec3 position = entity.position().add(Math.cos(angle) * 8.0, 0.15, Math.sin(angle) * 8.0);
               AvalonKnightEntity knight = new AvalonKnightEntity(
                  level, entity, virtues[i], (Integer)MoreSkillsConfigs.GRAND.avalon.roundTableLifetimeTicks.get()
               );
               knight.setPos(position);
               knight.setYRot((float)Math.toDegrees(-angle) + 90.0F);
               level.addFreshEntity(knight);
            }

            AvalonRoundTableVfxPayload.send(level, entity, null, entity.position(), entity.position(), 16.0F, 120, virtues.length);
            level.playSound(null, entity.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 7.0F, 1.75F);
            instance.addMasteryPoint(entity);
            instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.avalon.roundTableCooldown.get(), mode);
         }
      }
   }

   public static void removeOwned(LivingEntity owner) {
      if (owner.level() instanceof ServerLevel source) {
         MinecraftServer server = source.getServer();

         for (ServerLevel level : server.getAllLevels()) {
            for (AvalonKnightEntity knight : level.getEntitiesOfClass(
               AvalonKnightEntity.class, new AABB(-3.0E7, level.getMinBuildHeight(), -3.0E7, 3.0E7, level.getMaxBuildHeight(), 3.0E7)
            )) {
               if (owner.getUUID().equals(knight.getOwnerUUID())) {
                  knight.discard();
               }
            }
         }
      }
   }

   @Override
   public void cleanup(LivingEntity entity) {
      removeOwned(entity);
   }
}
