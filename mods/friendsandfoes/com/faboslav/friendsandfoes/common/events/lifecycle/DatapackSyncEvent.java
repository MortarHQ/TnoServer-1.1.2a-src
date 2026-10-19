package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.server.level.ServerPlayer;

public record DatapackSyncEvent(ServerPlayer player) {
   public static final EventHandler<DatapackSyncEvent> EVENT = new EventHandler<>();
}
