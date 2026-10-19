package dev.ftb.mods.ftbteams.api;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public interface TeamManager {
   MinecraftServer getServer();

   UUID getId();

   Collection<Team> getTeams();

   Optional<Team> getTeamForPlayerID(UUID var1);

   Optional<Team> getTeamForPlayer(ServerPlayer var1);

   Optional<Team> getPlayerTeamForPlayerID(UUID var1);

   Optional<Team> getTeamByName(String var1);

   Optional<Team> getTeamByID(UUID var1);

   boolean arePlayersInSameTeam(UUID var1, UUID var2);

   Map<UUID, ? extends Team> getKnownPlayerTeams();

   CompoundTag getExtraData();

   void markDirty();

   Team createPartyTeam(ServerPlayer var1, String var2, @Nullable String var3, @Nullable Color4I var4) throws CommandSyntaxException;

   Team createServerTeam(CommandSourceStack var1, String var2, @Nullable String var3, @Nullable Color4I var4, @Nullable UUID var5) throws CommandSyntaxException;

   default Team createServerTeam(CommandSourceStack commandSourceStack, String name, @Nullable String description, @Nullable Color4I color) throws CommandSyntaxException {
      return this.createServerTeam(commandSourceStack, name, description, color, null);
   }

   void setChatRedirected(ServerPlayer var1, boolean var2);

   boolean isChatRedirected(ServerPlayer var1);
}
