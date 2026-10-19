package dev.ftb.mods.ftbteams.neoforge;

import dev.ftb.mods.ftbteams.FTBTeams;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("ftbteams")
public class FTBTeamsNeoForge {
   public FTBTeamsNeoForge(IEventBus modEventBus) {
      ArgumentTypes.COMMAND_ARGUMENT_TYPES.register(modEventBus);
      new FTBTeams();
   }
}
