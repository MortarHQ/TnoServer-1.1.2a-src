package dev.ftb.mods.ftbchunks.api.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.api.Team;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

public class ChunksUpdatedFromServerEvent {
   public static final Event<Consumer<ChunksUpdatedFromServerEvent>> UPDATED = EventFactory.createLoop(new Consumer[0]);
   private final Set<ChunkDimPos> chunkDimPos;
   private final Team team;
   private final Date claimed;
   private final Date forceLoaded;
   private final Date expiry;

   public ChunksUpdatedFromServerEvent(
      Set<ChunkDimPos> chunkDimPos, @Nullable Team team, @Nullable Date claimed, @Nullable Date forceLoaded, @Nullable Date expiry
   ) {
      this.team = team;
      this.chunkDimPos = chunkDimPos;
      this.claimed = claimed;
      this.forceLoaded = forceLoaded;
      this.expiry = expiry;
   }

   public Optional<Team> getTeam() {
      return Optional.ofNullable(this.team);
   }

   public Collection<ChunkDimPos> getChunks() {
      return this.chunkDimPos;
   }

   public Optional<Date> getClaimTime() {
      return Optional.ofNullable(this.claimed);
   }

   public Optional<Date> getForceloadedTime() {
      return Optional.ofNullable(this.forceLoaded);
   }

   public Optional<Date> getForceLoadExpiryTime() {
      return Optional.ofNullable(this.expiry);
   }

   public boolean isClaimed() {
      return this.team != null;
   }
}
