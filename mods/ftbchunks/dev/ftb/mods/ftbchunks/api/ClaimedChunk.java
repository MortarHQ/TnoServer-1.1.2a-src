package dev.ftb.mods.ftbchunks.api;

import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.minecraft.commands.CommandSourceStack;

public interface ClaimedChunk extends ClaimResult {
   ChunkTeamData getTeamData();

   ChunkDimPos getPos();

   long getTimeClaimed();

   long getForceLoadedTime();

   boolean isForceLoaded();

   void unclaim(CommandSourceStack var1, boolean var2);

   boolean isActuallyForceLoaded();

   void unload(CommandSourceStack var1);

   long getForceLoadExpiryTime();

   void setForceLoadExpiryTime(long var1);

   boolean hasForceLoadExpired(long var1);
}
