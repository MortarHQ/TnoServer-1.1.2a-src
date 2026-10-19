package io.github.Memoires.mysticism.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public interface MysticismPlayerEvent {
   Event<MysticismPlayerEvent.PlayerCrouch> PLAYER_CROUCH = EventFactory.createEventResult(new MysticismPlayerEvent.PlayerCrouch[0]);

   interface PlayerCrouch {
      void onCrouch(Player var1);
   }
}
