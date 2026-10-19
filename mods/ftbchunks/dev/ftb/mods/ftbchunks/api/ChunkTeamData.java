package dev.ftb.mods.ftbchunks.api;

import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamManager;
import dev.ftb.mods.ftbteams.api.property.PrivacyProperty;
import java.util.Collection;
import java.util.UUID;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public interface ChunkTeamData {
   ClaimedChunkManager getManager();

   TeamManager getTeamManager();

   Team getTeam();

   Collection<? extends ClaimedChunk> getClaimedChunks();

   Collection<? extends ClaimedChunk> getForceLoadedChunks();

   ClaimResult claim(CommandSourceStack var1, ChunkDimPos var2, boolean var3);

   ClaimResult unclaim(CommandSourceStack var1, ChunkDimPos var2, boolean var3, boolean var4);

   default ClaimResult unclaim(CommandSourceStack source, ChunkDimPos pos, boolean checkOnly) {
      return this.unclaim(source, pos, checkOnly, true);
   }

   ClaimResult forceLoad(CommandSourceStack var1, ChunkDimPos var2, boolean var3, boolean var4);

   default ClaimResult forceLoad(CommandSourceStack source, ChunkDimPos pos, boolean checkOnly) {
      return this.forceLoad(source, pos, checkOnly, true);
   }

   ClaimResult unForceLoad(CommandSourceStack var1, ChunkDimPos var2, boolean var3, boolean var4);

   default ClaimResult unForceLoad(CommandSourceStack source, ChunkDimPos pos, boolean checkOnly) {
      return this.unForceLoad(source, pos, checkOnly, true);
   }

   boolean isTeamMember(UUID var1);

   boolean isAlly(UUID var1);

   boolean canPlayerUse(ServerPlayer var1, PrivacyProperty var2);

   int getExtraClaimChunks();

   void setExtraClaimChunks(int var1);

   boolean shouldHideClaims();

   int getMaxClaimChunks();

   boolean canDoOfflineForceLoading();

   int getExtraForceLoadChunks();

   void setExtraForceLoadChunks(int var1);

   int getMaxForceLoadChunks();

   boolean canExplosionsDamageTerrain();

   boolean allowMobGriefing();

   boolean allowPVP();

   long getLastLoginTime();

   void checkMemberForceLoading(UUID var1);
}
