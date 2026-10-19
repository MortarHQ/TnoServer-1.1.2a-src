package dev.ftb.mods.ftbchunks.api.event;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.ftb.mods.ftbchunks.api.ClaimResult;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import net.minecraft.commands.CommandSourceStack;

public interface ClaimedChunkEvent {
   Event<ClaimedChunkEvent.Before> BEFORE_CLAIM = EventFactory.createCompoundEventResult(new ClaimedChunkEvent.Before[0]);
   Event<ClaimedChunkEvent.Before> BEFORE_LOAD = EventFactory.createCompoundEventResult(new ClaimedChunkEvent.Before[0]);
   Event<ClaimedChunkEvent.Before> BEFORE_UNCLAIM = EventFactory.createCompoundEventResult(new ClaimedChunkEvent.Before[0]);
   Event<ClaimedChunkEvent.Before> BEFORE_UNLOAD = EventFactory.createCompoundEventResult(new ClaimedChunkEvent.Before[0]);
   Event<ClaimedChunkEvent.After> AFTER_CLAIM = EventFactory.createLoop(new ClaimedChunkEvent.After[0]);
   Event<ClaimedChunkEvent.After> AFTER_LOAD = EventFactory.createLoop(new ClaimedChunkEvent.After[0]);
   Event<ClaimedChunkEvent.After> AFTER_UNCLAIM = EventFactory.createLoop(new ClaimedChunkEvent.After[0]);
   Event<ClaimedChunkEvent.After> AFTER_UNLOAD = EventFactory.createLoop(new ClaimedChunkEvent.After[0]);

   interface After {
      void after(CommandSourceStack var1, ClaimedChunk var2);
   }

   interface Before {
      CompoundEventResult<ClaimResult> before(CommandSourceStack var1, ClaimedChunk var2);
   }
}
