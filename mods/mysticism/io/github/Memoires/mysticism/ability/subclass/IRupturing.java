package io.github.Memoires.mysticism.ability.subclass;

import dev.architectury.networking.NetworkManager;
import io.github.Memoires.mysticism.network.s2c.OpenRupturingMenuPayload;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public interface IRupturing<S extends ManasSkill & IRupturing<S>> {
   default void openRupturingMenu(LivingEntity entity, ManasSkill skill, List<Integer> list) {
      if (entity instanceof ServerPlayer player) {
         player.closeContainer();
         this.openRupturingMenu(player, skill, list);
         player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
      }
   }

   default void openRupturingMenu(ServerPlayer player, ManasSkill skill, List<Integer> list) {
      NetworkManager.sendToPlayer(player, new OpenRupturingMenuPayload(player.getId(), skill.getRegistryName(), list));
   }
}
