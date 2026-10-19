package dev.ftb.mods.ftbteams.data;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.architectury.platform.Platform;
import dev.ftb.mods.ftblibrary.FTBLibraryCommands.InfoBuilder;
import dev.ftb.mods.ftblibrary.net.EditNBTPacket;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbteams.FTBTeamsAPIImpl;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamManager;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.TeamStagesHelper;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamInfoEvent;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyArgument;
import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.server.level.ServerPlayer;

public class FTBTeamsCommands {
   public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                             "ftbteams"
                                          )
                                          .then(
                                             ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                                                                 "party"
                                                                              )
                                                                              .then(
                                                                                 ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("create")
                                                                                          .requires(this::hasNoParty))
                                                                                       .then(
                                                                                          Commands.argument("name", StringArgumentType.greedyString())
                                                                                             .executes(
                                                                                                ctx -> tryCreateParty(
                                                                                                   (CommandSourceStack)ctx.getSource(),
                                                                                                   StringArgumentType.getString(ctx, "name")
                                                                                                )
                                                                                             )
                                                                                       ))
                                                                                    .executes(ctx -> tryCreateParty((CommandSourceStack)ctx.getSource(), ""))
                                                                              ))
                                                                           .then(
                                                                              ((LiteralArgumentBuilder)Commands.literal("join").requires(this::hasNoParty))
                                                                                 .then(
                                                                                    createTeamArg(TeamType.PARTY)
                                                                                       .executes(
                                                                                          ctx -> partyTeamArg(ctx, TeamRank.INVITED)
                                                                                             .join(((CommandSourceStack)ctx.getSource()).getPlayerOrException())
                                                                                       )
                                                                                 )
                                                                           ))
                                                                        .then(
                                                                           ((LiteralArgumentBuilder)Commands.literal("decline").requires(this::hasNoParty))
                                                                              .then(
                                                                                 createTeamArg(TeamType.PARTY)
                                                                                    .executes(
                                                                                       ctx -> partyTeamArg(ctx, TeamRank.INVITED)
                                                                                          .declineInvitation((CommandSourceStack)ctx.getSource())
                                                                                    )
                                                                              )
                                                                        ))
                                                                     .then(
                                                                        ((LiteralArgumentBuilder)Commands.literal("leave")
                                                                              .requires(source -> this.hasParty(source, TeamRank.MEMBER)))
                                                                           .executes(
                                                                              ctx -> getPartyTeam(ctx, TeamRank.MEMBER)
                                                                                 .leave(((CommandSourceStack)ctx.getSource()).getPlayerOrException().getUUID())
                                                                           )
                                                                     ))
                                                                  .then(
                                                                     ((LiteralArgumentBuilder)Commands.literal("invite")
                                                                           .requires(source -> this.hasParty(source, TeamRank.OFFICER)))
                                                                        .then(
                                                                           Commands.argument("players", GameProfileArgument.gameProfile())
                                                                              .executes(
                                                                                 ctx -> getPartyTeam(ctx, TeamRank.OFFICER)
                                                                                    .invite(
                                                                                       ((CommandSourceStack)ctx.getSource()).getPlayerOrException(),
                                                                                       GameProfileArgument.getGameProfiles(ctx, "players")
                                                                                    )
                                                                              )
                                                                        )
                                                                  ))
                                                               .then(
                                                                  ((LiteralArgumentBuilder)Commands.literal("kick")
                                                                        .requires(source -> this.hasParty(source, TeamRank.OFFICER)))
                                                                     .then(
                                                                        Commands.argument("players", GameProfileArgument.gameProfile())
                                                                           .executes(
                                                                              ctx -> getPartyTeam(ctx, TeamRank.OFFICER)
                                                                                 .kick(
                                                                                    (CommandSourceStack)ctx.getSource(),
                                                                                    GameProfileArgument.getGameProfiles(ctx, "players")
                                                                                 )
                                                                           )
                                                                     )
                                                               ))
                                                            .then(
                                                               ((LiteralArgumentBuilder)Commands.literal("transfer_ownership")
                                                                     .requires(source -> this.hasParty(source, TeamRank.OWNER)))
                                                                  .then(
                                                                     Commands.argument("player_id", GameProfileArgument.gameProfile())
                                                                        .executes(
                                                                           ctx -> getPartyTeam(ctx, TeamRank.OWNER)
                                                                              .transferOwnership(
                                                                                 (CommandSourceStack)ctx.getSource(),
                                                                                 GameProfileArgument.getGameProfiles(ctx, "player_id")
                                                                              )
                                                                        )
                                                                  )
                                                            ))
                                                         .then(
                                                            ((LiteralArgumentBuilder)Commands.literal("transfer_ownership_for").requires(requiresOPorSP()))
                                                               .then(
                                                                  createTeamArg(TeamType.PARTY)
                                                                     .then(
                                                                        Commands.argument("player_id", GameProfileArgument.gameProfile())
                                                                           .executes(
                                                                              ctx -> partyTeamArg(ctx, TeamRank.NONE)
                                                                                 .transferOwnership(
                                                                                    (CommandSourceStack)ctx.getSource(),
                                                                                    GameProfileArgument.getGameProfiles(ctx, "player_id")
                                                                                 )
                                                                           )
                                                                     )
                                                               )
                                                         ))
                                                      .then(
                                                         ((LiteralArgumentBuilder)Commands.literal("settings")
                                                               .requires(source -> this.hasParty(source, TeamRank.OWNER)))
                                                            .then(
                                                               ((RequiredArgumentBuilder)Commands.argument("key", TeamPropertyArgument.create())
                                                                     .then(
                                                                        Commands.argument("value", StringArgumentType.greedyString())
                                                                           .executes(
                                                                              ctx -> getPartyTeam(ctx, TeamRank.OWNER)
                                                                                 .settings(
                                                                                    (CommandSourceStack)ctx.getSource(),
                                                                                    TeamPropertyArgument.get(ctx, "key"),
                                                                                    string(ctx, "value")
                                                                                 )
                                                                           )
                                                                     ))
                                                                  .executes(
                                                                     ctx -> getPartyTeam(ctx, TeamRank.OWNER)
                                                                        .settings((CommandSourceStack)ctx.getSource(), TeamPropertyArgument.get(ctx, "key"), "")
                                                                  )
                                                            )
                                                      ))
                                                   .then(
                                                      ((LiteralArgumentBuilder)Commands.literal("settings_for").requires(requiresOPorSP()))
                                                         .then(
                                                            createTeamArg(TeamType.PARTY)
                                                               .then(
                                                                  ((RequiredArgumentBuilder)Commands.argument("key", TeamPropertyArgument.create())
                                                                        .then(
                                                                           Commands.argument("value", StringArgumentType.greedyString())
                                                                              .executes(
                                                                                 ctx -> partyTeamArg(ctx, TeamRank.NONE)
                                                                                    .settings(
                                                                                       (CommandSourceStack)ctx.getSource(),
                                                                                       TeamPropertyArgument.get(ctx, "key"),
                                                                                       string(ctx, "value")
                                                                                    )
                                                                              )
                                                                        ))
                                                                     .executes(
                                                                        ctx -> partyTeamArg(ctx, TeamRank.NONE)
                                                                           .settings(
                                                                              (CommandSourceStack)ctx.getSource(), TeamPropertyArgument.get(ctx, "key"), ""
                                                                           )
                                                                     )
                                                               )
                                                         )
                                                   ))
                                                .then(
                                                   ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("allies")
                                                               .requires(source -> this.hasParty(source, TeamRank.MEMBER)))
                                                            .then(
                                                               ((LiteralArgumentBuilder)Commands.literal("add")
                                                                     .requires(source -> this.hasParty(source, TeamRank.OFFICER)))
                                                                  .then(
                                                                     Commands.argument("player", GameProfileArgument.gameProfile())
                                                                        .executes(
                                                                           ctx -> getPartyTeam(ctx, TeamRank.OFFICER)
                                                                              .addAlly(
                                                                                 (CommandSourceStack)ctx.getSource(),
                                                                                 GameProfileArgument.getGameProfiles(ctx, "player")
                                                                              )
                                                                        )
                                                                  )
                                                            ))
                                                         .then(
                                                            ((LiteralArgumentBuilder)Commands.literal("remove")
                                                                  .requires(source -> this.hasParty(source, TeamRank.OFFICER)))
                                                               .then(
                                                                  Commands.argument("player", GameProfileArgument.gameProfile())
                                                                     .executes(
                                                                        ctx -> getPartyTeam(ctx, TeamRank.OFFICER)
                                                                           .removeAlly(
                                                                              (CommandSourceStack)ctx.getSource(),
                                                                              GameProfileArgument.getGameProfiles(ctx, "player")
                                                                           )
                                                                     )
                                                               )
                                                         ))
                                                      .then(
                                                         ((LiteralArgumentBuilder)Commands.literal("list")
                                                               .requires(source -> this.hasParty(source, TeamRank.MEMBER)))
                                                            .executes(ctx -> getPartyTeam(ctx, TeamRank.MEMBER).listAllies((CommandSourceStack)ctx.getSource()))
                                                      )
                                                )
                                          ))
                                       .then(
                                          ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("server")
                                                      .requires(requiresOPorSP()))
                                                   .then(
                                                      Commands.literal("create")
                                                         .then(
                                                            Commands.argument("name", StringArgumentType.greedyString())
                                                               .executes(FTBTeamsCommands::tryCreateServerTeam)
                                                         )
                                                   ))
                                                .then(
                                                   Commands.literal("delete")
                                                      .then(
                                                         createTeamArg(TeamType.SERVER)
                                                            .executes(ctx -> serverTeamArg(ctx).delete((CommandSourceStack)ctx.getSource()))
                                                      )
                                                ))
                                             .then(
                                                Commands.literal("settings")
                                                   .then(
                                                      createTeamArg(TeamType.SERVER)
                                                         .then(
                                                            ((RequiredArgumentBuilder)Commands.argument("key", TeamPropertyArgument.create())
                                                                  .then(
                                                                     Commands.argument("value", StringArgumentType.greedyString())
                                                                        .executes(
                                                                           ctx -> serverTeamArg(ctx)
                                                                              .settings(
                                                                                 (CommandSourceStack)ctx.getSource(),
                                                                                 TeamPropertyArgument.get(ctx, "key"),
                                                                                 string(ctx, "value")
                                                                              )
                                                                        )
                                                                  ))
                                                               .executes(
                                                                  ctx -> serverTeamArg(ctx)
                                                                     .settings((CommandSourceStack)ctx.getSource(), TeamPropertyArgument.get(ctx, "key"), "")
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(Commands.literal("msg").then(Commands.argument("text", StringArgumentType.greedyString()).executes(ctx -> {
                                       getTeam(ctx)
                                          .sendMessage(
                                             ((CommandSourceStack)ctx.getSource()).getPlayerOrException().getUUID(), StringArgumentType.getString(ctx, "text")
                                          );
                                       return 1;
                                    }))))
                                 .then(
                                    ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("info")
                                             .then(Commands.literal("server_id").executes(ctx -> serverId((CommandSourceStack)ctx.getSource()))))
                                          .then(createTeamArg().executes(ctx -> info((CommandSourceStack)ctx.getSource(), teamArg(ctx)))))
                                       .executes(ctx -> info((CommandSourceStack)ctx.getSource(), getTeam(ctx)))
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("list")
                                             .executes(ctx -> this.list((CommandSourceStack)ctx.getSource(), t -> true)))
                                          .then(Commands.literal("parties").executes(ctx -> this.list((CommandSourceStack)ctx.getSource(), Team::isPartyTeam))))
                                       .then(
                                          Commands.literal("server_teams").executes(ctx -> this.list((CommandSourceStack)ctx.getSource(), Team::isServerTeam))
                                       ))
                                    .then(Commands.literal("players").executes(ctx -> this.list((CommandSourceStack)ctx.getSource(), Team::isPlayerTeam)))
                              ))
                           .then(
                              ((LiteralArgumentBuilder)Commands.literal("force-disband").requires(source -> source.hasPermission(2)))
                                 .then(
                                    createTeamArg(TeamType.PARTY)
                                       .executes(ctx -> partyTeamArg(ctx, TeamRank.NONE).forceDisband((CommandSourceStack)ctx.getSource()))
                                 )
                           ))
                        .then(Commands.literal("redirect_chat").executes(FTBTeamsCommands::redirectChatToggle)))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("force-add").requires(requiresOPorSP()))
                           .then(
                              createTeamArg(TeamType.PARTY)
                                 .then(Commands.argument("players", GameProfileArgument.gameProfile()).executes(FTBTeamsCommands::forceAddPlayers))
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("force-remove").requires(requiresOPorSP()))
                        .then(
                           createTeamArg(TeamType.PARTY)
                              .then(Commands.argument("players", GameProfileArgument.gameProfile()).executes(FTBTeamsCommands::forceRemovePlayers))
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("nbtedit").requires(requiresOPorSP())).executes(this::editPlayerTeamNBT))
                     .then(createTeamArg().executes(FTBTeamsCommands::editTeamNBT))
               ))
            .then(
               ((LiteralArgumentBuilder)Commands.literal("teamstage").requires(requiresOPorSP()))
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("player", EntityArgument.player())
                              .then(
                                 Commands.literal("add")
                                    .then(
                                       Commands.argument("stage", StringArgumentType.string())
                                          .executes(
                                             ctx -> updateTeamStage(
                                                ctx, EntityArgument.getPlayer(ctx, "player"), StringArgumentType.getString(ctx, "stage"), true
                                             )
                                          )
                                    )
                              ))
                           .then(
                              Commands.literal("remove")
                                 .then(
                                    Commands.argument("stage", StringArgumentType.string())
                                       .executes(
                                          ctx -> updateTeamStage(
                                             ctx, EntityArgument.getPlayer(ctx, "player"), StringArgumentType.getString(ctx, "stage"), false
                                          )
                                       )
                                 )
                           ))
                        .then(Commands.literal("list").executes(ctx -> listTeamStages(ctx, EntityArgument.getPlayer(ctx, "player"))))
                  )
            )
      );
      if (Platform.isDevelopmentEnvironment()) {
         dispatcher.register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ftbteams_add_fake_player").requires(source -> source.hasPermission(2)))
               .then(
                  Commands.argument("profile", GameProfileArgument.gameProfile())
                     .executes(ctx -> this.addFakePlayer(GameProfileArgument.getGameProfiles(ctx, "profile")))
               )
         );
      }
   }

   private static Predicate<CommandSourceStack> requiresOPorSP() {
      return source -> source.getServer().isSingleplayer() || source.hasPermission(2);
   }

   private static RequiredArgumentBuilder<CommandSourceStack, TeamArgumentProvider> createTeamArg() {
      return createTeamArg(null);
   }

   private static RequiredArgumentBuilder<CommandSourceStack, TeamArgumentProvider> createTeamArg(TeamType type) {
      return Commands.argument("team", TeamArgument.create(type));
   }

   private static String string(CommandContext<?> context, String name) {
      return StringArgumentType.getString(context, name);
   }

   private boolean hasNoParty(CommandSourceStack source) {
      return source.getEntity() instanceof ServerPlayer
         ? FTBTeamsAPI.api().getManager().getTeamForPlayerID(source.getEntity().getUUID()).map(team -> !team.isPartyTeam()).orElse(false)
         : false;
   }

   private boolean hasParty(CommandSourceStack source, TeamRank rank) {
      if (source.getEntity() instanceof ServerPlayer) {
         UUID playerId = source.getEntity().getUUID();
         return FTBTeamsAPI.api()
            .getManager()
            .getTeamForPlayerID(playerId)
            .map(team -> team.isPartyTeam() && team.getRankForPlayer(playerId).isAtLeast(rank))
            .orElse(false);
      } else {
         return false;
      }
   }

   private static Team getTeam(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      return FTBTeamsAPI.api().getManager().getTeamForPlayer(player).orElseThrow(() -> TeamArgument.TEAM_NOT_FOUND.create(player.getUUID()));
   }

   private static PartyTeam getPartyTeam(CommandContext<CommandSourceStack> context, TeamRank minRank) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      Team team = FTBTeamsAPI.api().getManager().getTeamForPlayer(player).orElseThrow(() -> TeamArgument.TEAM_NOT_FOUND.create(player.getUUID()));
      if (team instanceof PartyTeam partyTeam) {
         if (!partyTeam.getRankForPlayer(player.getUUID()).isAtLeast(minRank)) {
            throw TeamArgument.CANT_EDIT.create(team.getName());
         } else {
            return partyTeam;
         }
      } else {
         throw TeamArgument.NOT_IN_PARTY.create();
      }
   }

   private static Team teamArg(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      return TeamArgument.get(context, "team");
   }

   private static Team teamArg(CommandContext<CommandSourceStack> context, Predicate<Team> predicate) throws CommandSyntaxException {
      Team team = teamArg(context);
      if (!predicate.test(team)) {
         throw TeamArgument.TEAM_NOT_FOUND.create(team.getName());
      } else {
         return team;
      }
   }

   private static ServerTeam serverTeamArg(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      return (ServerTeam)teamArg(context, Team::isServerTeam);
   }

   private static PartyTeam partyTeamArg(CommandContext<CommandSourceStack> context, TeamRank rank) throws CommandSyntaxException {
      PartyTeam team = (PartyTeam)teamArg(context, Team::isPartyTeam);
      if (rank != TeamRank.NONE && !team.getRankForPlayer(((CommandSourceStack)context.getSource()).getPlayerOrException().getUUID()).isAtLeast(rank)) {
         throw TeamArgument.NOT_INVITED.create(team.getName());
      } else {
         return team;
      }
   }

   private static int tryCreateParty(CommandSourceStack source, String partyName) throws CommandSyntaxException {
      if (FTBTeamsAPIImpl.INSTANCE.isPartyCreationFromAPIOnly()) {
         throw TeamArgument.API_OVERRIDE.create();
      }

      TeamManagerImpl.INSTANCE.createParty(source.getPlayerOrException(), partyName);
      return 1;
   }

   private static int tryCreateServerTeam(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      FTBTeamsAPI.api().getManager().createServerTeam((CommandSourceStack)ctx.getSource(), string(ctx, "name"), null, null);
      return 1;
   }

   private static int info(CommandSourceStack source, Team team) {
      team.getTeamInfo().forEach(line -> source.sendSuccess(() -> line, false));
      ((Consumer)TeamEvent.INFO.invoker()).accept(new TeamInfoEvent(team, source));
      return 1;
   }

   private static int serverId(CommandSourceStack source) {
      UUID managerId = FTBTeamsAPI.api().getManager().getId();
      source.sendSuccess(
         () -> Component.literal("Server ID: ").append(FTBTUtils.makeCopyableComponent(managerId.toString()).withStyle(ChatFormatting.YELLOW)), false
      );
      return 1;
   }

   private int list(CommandSourceStack source, Predicate<Team> predicate) {
      Component teams = FTBTeamsAPI.api()
         .getManager()
         .getTeams()
         .stream()
         .filter(predicate)
         .sorted(Comparator.comparing(Team::getShortName))
         .map(Team::getName)
         .reduce((c1, c2) -> c1.copy().append(", ").append(c2))
         .orElse(Component.literal("<none>"));
      source.sendSuccess(() -> Component.translatable("ftbteams.list", new Object[]{teams}), false);
      return 1;
   }

   private static int redirectChatToggle(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
      TeamManager mgr = FTBTeamsAPI.api().getManager();
      mgr.setChatRedirected(player, !mgr.isChatRedirected(player));
      String key = "ftbteams.message.chat_redirected." + (mgr.isChatRedirected(player) ? "on" : "off");
      ((CommandSourceStack)ctx.getSource())
         .sendSuccess(() -> Component.translatable(key).withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GOLD}), false);
      return 1;
   }

   private static int forceAddPlayers(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      int res = 0;
      PartyTeam party = partyTeamArg(ctx, TeamRank.NONE);

      for (GameProfile profile : GameProfileArgument.getGameProfiles(ctx, "players")) {
         res += party.join(null, profile);
      }

      return res;
   }

   private static int forceRemovePlayers(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      return partyTeamArg(ctx, TeamRank.NONE).kick((CommandSourceStack)ctx.getSource(), GameProfileArgument.getGameProfiles(ctx, "players"));
   }

   private int addFakePlayer(Collection<GameProfile> profiles) {
      for (GameProfile profile : profiles) {
         TeamManagerImpl.INSTANCE.playerLoggedIn(null, profile.getId(), profile.getName());
      }

      return 1;
   }

   private int editPlayerTeamNBT(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      return doTeamEdit(ctx, ((CommandSourceStack)ctx.getSource()).getPlayerOrException(), getTeam(ctx));
   }

   private static int editTeamNBT(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      return doTeamEdit(ctx, ((CommandSourceStack)ctx.getSource()).getPlayerOrException(), TeamArgument.get(ctx, "team"));
   }

   private static int doTeamEdit(CommandContext<CommandSourceStack> ctx, ServerPlayer editor, Team team) {
      if (team instanceof AbstractTeam abstractTeam) {
         CompoundTag info = (CompoundTag)Util.make(
            new CompoundTag(),
            t -> {
               t.putString("title", Serializer.toJson(abstractTeam.getColoredName(), editor.registryAccess()));
               t.putString("type", "ftbteams:team");
               t.putUUID("id", team.getTeamId());
               t.putString("team_type", abstractTeam.getType().getSerializedName());
               t.put(
                  "text",
                  InfoBuilder.create(ctx)
                     .add("Team Type", Component.translatable(team.getTypeTranslationKey()))
                     .add("Owner", Component.literal(team.getOwner().toString()))
                     .add("Members", Component.literal(String.valueOf(team.getMembers().size())))
                     .build()
               );
            }
         );
         CompoundTag tag = abstractTeam.serializeNBT(((CommandSourceStack)ctx.getSource()).getServer().registryAccess());
         NetworkHelper.sendTo(editor, new EditNBTPacket(info, tag));
         return 1;
      } else {
         return 0;
      }
   }

   private static int updateTeamStage(CommandContext<CommandSourceStack> ctx, ServerPlayer player, String stage, boolean adding) throws CommandSyntaxException {
      Team team = FTBTeamsAPI.api().getManager().getTeamForPlayer(player).orElseThrow(() -> TeamArgument.TEAM_NOT_FOUND.create(player.getDisplayName()));
      if (adding) {
         if (TeamStagesHelper.addTeamStage(team, stage)) {
            ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.translatable("ftbteams.message.added_stage", new Object[]{stage}), false);
         }
      } else if (TeamStagesHelper.removeTeamStage(team, stage)) {
         ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.translatable("ftbteams.message.removed_stage", new Object[]{stage}), false);
      }

      return 1;
   }

   private static int listTeamStages(CommandContext<CommandSourceStack> ctx, ServerPlayer player) throws CommandSyntaxException {
      Team team = FTBTeamsAPI.api().getManager().getTeamForPlayer(player).orElseThrow(() -> TeamArgument.TEAM_NOT_FOUND.create(player.getDisplayName()));
      Collection<String> stages = TeamStagesHelper.getStages(team);
      if (stages.isEmpty()) {
         ((CommandSourceStack)ctx.getSource())
            .sendSuccess(() -> Component.translatable("ftbteams.message.no_team_stages").withStyle(ChatFormatting.GOLD), false);
      } else {
         ((CommandSourceStack)ctx.getSource())
            .sendSuccess(
               () -> Component.translatable("ftbteams.message.team_stages_header", new Object[]{stages.size()}).withStyle(ChatFormatting.YELLOW), false
            );
         stages.forEach(stage -> ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.literal("• ").append(stage), false));
      }

      return 0;
   }
}
