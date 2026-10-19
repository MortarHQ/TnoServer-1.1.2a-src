package io.github.Memoires.mysticism.ability.subclass;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public interface ITactics<S extends ManasSkill & ITactics<S>> {
   default void openTacticsMenu(LivingEntity entity, ManasSkill skill) {
      if (entity instanceof ServerPlayer player) {
         player.closeContainer();
         this.openTacticsMenu(player, skill);
         player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
      }
   }

   default void openTacticsMenu(ServerPlayer player, ManasSkill skill) {
   }
}
