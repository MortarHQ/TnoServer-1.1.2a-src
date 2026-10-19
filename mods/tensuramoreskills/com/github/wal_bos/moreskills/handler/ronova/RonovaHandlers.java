package com.github.wal_bos.moreskills.handler.ronova;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

public final class RonovaHandlers {
   private RonovaHandlers() {
   }

   public static void init(IEventBus modBus) {
      NeoForge.EVENT_BUS.addListener(RonovaDeathHandler::onDeath);
      NeoForge.EVENT_BUS.addListener(RonovaDeathHandler::onDrops);
      NeoForge.EVENT_BUS.addListener(RonovaPlayerHandler::onLogin);
      NeoForge.EVENT_BUS.addListener(RonovaPlayerHandler::onLogout);
      NeoForge.EVENT_BUS.addListener(RonovaPlayerHandler::onTick);
      NeoForge.EVENT_BUS.addListener(RonovaSoundHandler::onSound);
   }
}
