package io.github.Memoires.mysticism.ability.subclass;

import dev.architectury.networking.NetworkManager;
import io.github.Memoires.mysticism.network.s2c.OpenButcheringMenuPayload;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public interface IButchering<S extends ManasSkill & IButchering<S>> {
   default void openButcheringMenu(LivingEntity entity, ManasSkill skill, LivingEntity target) {
      if (entity instanceof ServerPlayer player) {
         player.closeContainer();
         this.openButcheringMenu(player, skill, target);
         player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
      }
   }

   default void openButcheringMenu(ServerPlayer player, ManasSkill skill, LivingEntity target) {
      NetworkManager.sendToPlayer(player, new OpenButcheringMenuPayload(player.getId(), skill.getRegistryName(), target.getId()));
   }
}
