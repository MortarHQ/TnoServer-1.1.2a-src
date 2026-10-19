package org.crypticdev.stextras.quest.schedule;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.server.MinecraftServer;

public final class QuestScheduleEvents {
   public static final Event<QuestScheduleEvents.Sync> SYNC = EventFactory.createLoop(QuestScheduleEvents.Sync.class);

   private QuestScheduleEvents() {
   }

   @FunctionalInterface
   public interface Sync {
      void onSync(MinecraftServer var1, QuestSchedule var2, boolean var3);
   }
}
