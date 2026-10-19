package com.illusivesoulworks.polymorph.common;

import com.illusivesoulworks.polymorph.server.PolymorphCommands;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent.Open;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

public class CommonEventsListener {
   @SubscribeEvent
   public void registerCommands(RegisterCommandsEvent evt) {
      PolymorphCommands.register(evt.getDispatcher());
   }

   @SubscribeEvent
   public void playerLoggedOut(PlayerLoggedOutEvent evt) {
      if (evt.getEntity() instanceof ServerPlayer serverPlayer) {
         PolymorphCommonEvents.playerDisconnected(serverPlayer);
      }
   }

   @SubscribeEvent
   public void openContainer(Open evt) {
      PolymorphCommonEvents.openContainer(evt.getEntity(), evt.getContainer());
   }

   @SubscribeEvent
   public void levelTick(Post evt) {
      PolymorphCommonEvents.levelTick(evt.getLevel());
   }
}
