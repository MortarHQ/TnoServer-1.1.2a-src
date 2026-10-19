package dev.ftb.mods.ftbchunks.api;

import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.api.Team;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ClaimedChunkManager {
   ChunkTeamData getOrCreateData(@NotNull Team var1);

   ChunkTeamData getOrCreateData(ServerPlayer var1);

   ChunkTeamData getPersonalData(UUID var1);

   @Nullable
   ClaimedChunk getChunk(ChunkDimPos var1);

   Collection<? extends ClaimedChunk> getAllClaimedChunks();

   Map<UUID, Collection<ClaimedChunk>> getClaimedChunksByTeam(Predicate<ClaimedChunk> var1);

   boolean getBypassProtection(UUID var1);

   void setBypassProtection(UUID var1, boolean var2);

   boolean shouldPreventInteraction(@Nullable Entity var1, InteractionHand var2, BlockPos var3, Protection var4, @Nullable Entity var5);

   Map<ResourceKey<Level>, Long2ObjectMap<UUID>> getForceLoadedChunks();

   @NotNull
   Long2ObjectMap<UUID> getForceLoadedChunks(ResourceKey<Level> var1);

   boolean isChunkForceLoaded(ChunkDimPos var1);
}
