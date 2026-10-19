package io.github.Memoires.mysticism.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface MysticismEntityEvent {
   Event<MysticismEntityEvent.EntityForceDismount> ENTITY_FORCE_DISMOUNT = EventFactory.createEventResult(new MysticismEntityEvent.EntityForceDismount[0]);

   interface EntityForceDismount {
      void onForceDismount(Player var1, List<Entity> var2);
   }
}
