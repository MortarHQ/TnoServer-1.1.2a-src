package com.github.wal_bos.moreskills.handler.michael;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class MichaelLataibSpatialStorage implements MichaelAttack {
   @Override
   public String modeId() {
      return "michael_angel_of_locks.lataib_storage";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return ((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.spatialStorageCost.get()).intValue();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            if (instance.getSkill() instanceof ISpatialStorage storage) {
               storage.openSpatialStorage(entity, instance);
               entity.swing(InteractionHand.MAIN_HAND, true);
               entity.level()
                  .playSound(
                     null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(), SoundSource.PLAYERS, 1.0F, 0.9F
                  );
               instance.addMasteryPoint(entity);
               instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.spatialStorageCooldownTicks.get(), mode);
            } else {
               if (entity instanceof Player player) {
                  player.displayClientMessage(Component.literal("Michael's Spatial Storage is not bound to the skill.").withStyle(ChatFormatting.RED), true);
               }
            }
         }
      }
   }
}
