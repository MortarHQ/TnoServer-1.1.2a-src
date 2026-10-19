package dev.ftb.mods.ftbteams.api.client;

import dev.ftb.mods.ftbteams.api.Team;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public interface ClientTeamManager {
   UUID getManagerId();

   boolean isValid();

   Collection<KnownClientPlayer> knownClientPlayers();

   Collection<Team> getTeams();

   Optional<Team> getTeamByID(UUID var1);

   Optional<Team> getTeamForPlayer(Player var1);

   Team selfTeam();

   KnownClientPlayer self();

   Optional<KnownClientPlayer> getKnownPlayer(UUID var1);

   Component formatName(@Nullable UUID var1);
}
