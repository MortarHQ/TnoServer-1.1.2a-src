package dev.ftb.mods.ftbteams.data;

import dev.ftb.mods.ftbteams.api.Team;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;

public enum TeamType implements StringRepresentable {
   PLAYER("player", PlayerTeam::new, Team::isPlayerTeam, ChatFormatting.GRAY),
   PARTY("party", PartyTeam::new, Team::isPartyTeam, ChatFormatting.AQUA),
   SERVER("server", ServerTeam::new, Team::isServerTeam, ChatFormatting.LIGHT_PURPLE);

   private final String name;
   private final BiFunction<TeamManagerImpl, UUID, AbstractTeam> factory;
   private final Predicate<Team> matcher;
   private final ChatFormatting color;

   TeamType(String name, BiFunction<TeamManagerImpl, UUID, AbstractTeam> factory, Predicate<Team> matcher, ChatFormatting color) {
      this.name = name;
      this.factory = factory;
      this.matcher = matcher;
      this.color = color;
   }

   public AbstractTeam createTeam(TeamManagerImpl manager, UUID id) {
      return this.factory.apply(manager, id);
   }

   public String getSerializedName() {
      return this.name;
   }

   public ChatFormatting getColor() {
      return this.color;
   }

   public boolean matches(Team team) {
      return this.matcher.test(team);
   }
}
