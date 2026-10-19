package dev.ftb.mods.ftbteams.data;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class TeamArgument implements ArgumentType<TeamArgumentProvider> {
   public static final SimpleCommandExceptionType ALREADY_IN_PARTY = new SimpleCommandExceptionType(Component.translatable("ftbteams.already_in_party"));
   public static final DynamicCommandExceptionType PLAYER_IN_PARTY = new DynamicCommandExceptionType(
      object -> Component.translatable("ftbteams.player_already_in_party", new Object[]{object})
   );
   public static final SimpleCommandExceptionType NOT_IN_PARTY = new SimpleCommandExceptionType(Component.translatable("ftbteams.not_in_party"));
   public static final DynamicCommandExceptionType TEAM_NOT_FOUND = new DynamicCommandExceptionType(
      object -> Component.translatable("ftbteams.team_not_found", new Object[]{object})
   );
   public static final DynamicCommandExceptionType CANT_EDIT = new DynamicCommandExceptionType(
      object -> Component.translatable("ftbteams.cant_edit", new Object[]{object})
   );
   public static final Dynamic2CommandExceptionType NOT_MEMBER = new Dynamic2CommandExceptionType(
      (a, b) -> Component.translatable("ftbteams.not_member", new Object[]{a, b})
   );
   public static final Dynamic2CommandExceptionType NOT_OFFICER = new Dynamic2CommandExceptionType(
      (a, b) -> Component.translatable("ftbteams.not_officer", new Object[]{a, b})
   );
   public static final DynamicCommandExceptionType NOT_INVITED = new DynamicCommandExceptionType(
      object -> Component.translatable("ftbteams.not_invited", new Object[]{object})
   );
   public static final SimpleCommandExceptionType OWNER_CANT_LEAVE = new SimpleCommandExceptionType(Component.translatable("ftbteams.owner_cant_leave"));
   public static final SimpleCommandExceptionType CANT_KICK_OWNER = new SimpleCommandExceptionType(Component.translatable("ftbteams.cant_kick_owner"));
   public static final SimpleCommandExceptionType API_OVERRIDE = new SimpleCommandExceptionType(Component.translatable("ftbteams.party_api_only"));
   public static final SimpleCommandExceptionType NAME_TOO_SHORT = new SimpleCommandExceptionType(Component.translatable("ftbteams.name_too_short"));
   public static final SimpleCommandExceptionType NO_PERMISSION = new SimpleCommandExceptionType(Component.translatable("ftbteams.server_permissions_prevent"));
   public static final DynamicCommandExceptionType TEAM_ALREADY_EXISTS = new DynamicCommandExceptionType(
      object -> Component.translatable("ftbteams.team_already_exists", new Object[]{object})
   );
   public static final SimpleCommandExceptionType OUT_OF_LIVES = new SimpleCommandExceptionType(Component.translatable("ftbteams.out_of_lives"));
   private final TeamType type;

   public static TeamArgument create() {
      return new TeamArgument(null);
   }

   public static TeamArgument create(TeamType type) {
      return new TeamArgument(type);
   }

   public static Team get(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
      return ((TeamArgumentProvider)context.getArgument(name, TeamArgumentProvider.class)).getTeam((CommandSourceStack)context.getSource());
   }

   private TeamArgument(@Nullable TeamType type) {
      this.type = type;
   }

   public TeamArgumentProvider parse(StringReader reader) throws CommandSyntaxException {
      if (reader.canRead() && reader.peek() == '@') {
         EntitySelector selector = new EntitySelectorParser(reader, true).parse();
         if (selector.includesEntities()) {
            throw EntityArgument.ERROR_ONLY_PLAYERS_ALLOWED.create();
         } else {
            return new TeamArgument.SelectorProvider(selector);
         }
      } else {
         int i = reader.getCursor();

         while (reader.canRead() && reader.peek() != ' ') {
            reader.skip();
         }

         return new TeamArgument.IDProvider(reader.getString().substring(i, reader.getCursor()));
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder builder) {
      if (commandContext.getSource() instanceof SharedSuggestionProvider) {
         Stream<String> list = this.getTeams(commandContext).stream().filter(t -> this.type == null || this.type.matches(t)).map(Team::getShortName).sorted();
         return SharedSuggestionProvider.suggest(list, builder);
      } else {
         return Suggestions.empty();
      }
   }

   private Collection<Team> getTeams(CommandContext<?> context) {
      FTBTeamsAPI.API api = FTBTeamsAPI.api();
      if (context.getSource() instanceof CommandSourceStack && api.isManagerLoaded()) {
         return api.getManager().getTeams();
      } else {
         return api.isClientManagerLoaded() ? api.getClientManager().getTeams() : List.of();
      }
   }

   private static class IDProvider implements TeamArgumentProvider {
      private final String id;

      private IDProvider(String s) {
         this.id = s;
      }

      private CommandSyntaxException error() {
         return TeamArgument.TEAM_NOT_FOUND.create(this.id);
      }

      @Override
      public Team getTeam(CommandSourceStack source) throws CommandSyntaxException {
         Optional<Team> t = FTBTeamsAPI.api().getManager().getTeamByName(this.id);
         return t.isPresent()
            ? t.get()
            : source.getServer()
               .getProfileCache()
               .get(this.id)
               .<UUID>map(GameProfile::getId)
               .map(FTBTeamsAPI.api().getManager()::getTeamForPlayerID)
               .orElseThrow()
               .orElseThrow(this::error);
      }
   }

   public static class Info implements ArgumentTypeInfo<TeamArgument, TeamArgument.Info.Template> {
      public void serializeToNetwork(TeamArgument.Info.Template template, FriendlyByteBuf buf) {
         buf.writeNullable(template.teamType, FriendlyByteBuf::writeEnum);
      }

      public TeamArgument.Info.Template deserializeFromNetwork(FriendlyByteBuf buf) {
         return new TeamArgument.Info.Template((TeamType)buf.readNullable(b -> (TeamType)b.readEnum(TeamType.class)));
      }

      public void serializeToJson(TeamArgument.Info.Template template, JsonObject jsonObject) {
         if (template.teamType != null) {
            jsonObject.addProperty("type", template.teamType.name());
         }
      }

      public TeamArgument.Info.Template unpack(TeamArgument argumentType) {
         return new TeamArgument.Info.Template(argumentType.type);
      }

      public final class Template implements net.minecraft.commands.synchronization.ArgumentTypeInfo.Template<TeamArgument> {
         private final TeamType teamType;

         public Template(TeamType teamType) {
            this.teamType = teamType;
         }

         public TeamArgument instantiate(CommandBuildContext commandBuildContext) {
            return TeamArgument.create(this.teamType);
         }

         public ArgumentTypeInfo<TeamArgument, ?> type() {
            return Info.this;
         }
      }
   }

   private static class SelectorProvider implements TeamArgumentProvider {
      private final EntitySelector selector;

      private SelectorProvider(EntitySelector s) {
         this.selector = s;
      }

      @Override
      public Team getTeam(CommandSourceStack source) throws CommandSyntaxException {
         ServerPlayer player = this.selector.findSinglePlayer(source);
         return FTBTeamsAPI.api().getManager().getTeamForPlayer(player).orElseThrow(() -> TeamArgument.TEAM_NOT_FOUND.create(player.getUUID()));
      }
   }
}
