package dev.ftb.mods.ftbchunks;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.util.UndashedUuid;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbchunks.api.ChunkTeamData;
import dev.ftb.mods.ftbchunks.api.ClaimResult;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.FTBChunksProperties;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.data.ChunkTeamDataImpl;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkImpl;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import dev.ftb.mods.ftbchunks.net.AddWaypointPacket;
import dev.ftb.mods.ftbchunks.net.LoadedChunkViewPacket;
import dev.ftb.mods.ftbchunks.net.OpenClaimGUIPacket;
import dev.ftb.mods.ftbchunks.net.RequestBlockColorPacket;
import dev.ftb.mods.ftbchunks.net.SendGeneralDataPacket;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.data.TeamArgument;
import dev.ftb.mods.ftbteams.data.TeamArgumentProvider;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.ToIntBiFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FTBChunksCommands {
   public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, CommandSelection selection) {
      LiteralCommandNode<CommandSourceStack> command = dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                          "ftbchunks"
                                       )
                                       .then(
                                          ((LiteralArgumentBuilder)Commands.literal("claim")
                                                .executes(
                                                   context -> claim(
                                                      (CommandSourceStack)context.getSource(), selfTeam((CommandSourceStack)context.getSource()), 0
                                                   )
                                                ))
                                             .then(
                                                radiusArg()
                                                   .executes(
                                                      context -> claim(
                                                         (CommandSourceStack)context.getSource(),
                                                         selfTeam((CommandSourceStack)context.getSource()),
                                                         getRadiusArg(context)
                                                      )
                                                   )
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)Commands.literal("unclaim")
                                             .executes(
                                                context -> unclaim(
                                                   (CommandSourceStack)context.getSource(), selfTeam((CommandSourceStack)context.getSource()), 0
                                                )
                                             ))
                                          .then(
                                             radiusArg()
                                                .executes(
                                                   context -> unclaim(
                                                      (CommandSourceStack)context.getSource(),
                                                      selfTeam((CommandSourceStack)context.getSource()),
                                                      getRadiusArg(context)
                                                   )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)Commands.literal("load").executes(context -> load((CommandSourceStack)context.getSource(), 0)))
                                       .then(radiusArg().executes(context -> load((CommandSourceStack)context.getSource(), getRadiusArg(context))))
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)Commands.literal("unload").executes(context -> unload((CommandSourceStack)context.getSource(), 0)))
                                    .then(radiusArg().executes(context -> unload((CommandSourceStack)context.getSource(), getRadiusArg(context))))
                              ))
                           .then(
                              ((LiteralArgumentBuilder)Commands.literal("unclaim_all")
                                    .executes(context -> unclaimAll((CommandSourceStack)context.getSource(), selfTeam((CommandSourceStack)context.getSource()))))
                                 .then(forTeam(FTBChunksCommands::unclaimAll))
                           ))
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("unload_all")
                                 .executes(context -> unloadAll((CommandSourceStack)context.getSource(), selfTeam((CommandSourceStack)context.getSource()))))
                              .then(forTeam(FTBChunksCommands::unloadAll))
                        ))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("info")
                              .executes(
                                 context -> info(
                                    (CommandSourceStack)context.getSource(),
                                    new ChunkDimPos(
                                       ((CommandSourceStack)context.getSource()).getLevel(),
                                       BlockPos.containing(((CommandSourceStack)context.getSource()).getPosition())
                                    )
                                 )
                              ))
                           .then(
                              Commands.argument("x", IntegerArgumentType.integer())
                                 .then(
                                    ((RequiredArgumentBuilder)Commands.argument("z", IntegerArgumentType.integer())
                                          .executes(
                                             context -> info(
                                                (CommandSourceStack)context.getSource(),
                                                new ChunkDimPos(
                                                   ((CommandSourceStack)context.getSource()).getLevel(),
                                                   new BlockPos(IntegerArgumentType.getInteger(context, "x"), 0, IntegerArgumentType.getInteger(context, "z"))
                                                )
                                             )
                                          ))
                                       .then(
                                          dimArg()
                                             .executes(
                                                context -> info(
                                                   (CommandSourceStack)context.getSource(),
                                                   new ChunkDimPos(
                                                      getDimArg(context).dimension(),
                                                      IntegerArgumentType.getInteger(context, "x") >> 4,
                                                      IntegerArgumentType.getInteger(context, "z") >> 4
                                                   )
                                                )
                                             )
                                       )
                                 )
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                                         "admin"
                                                      )
                                                      .requires(source -> source.hasPermission(2)))
                                                   .then(
                                                      Commands.literal("bypass_protection")
                                                         .executes(context -> bypassProtection((CommandSourceStack)context.getSource()))
                                                   ))
                                                .then(
                                                   Commands.literal("extra_claim_chunks")
                                                      .then(
                                                         ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument(
                                                                     "player", EntityArgument.player()
                                                                  )
                                                                  .then(
                                                                     Commands.literal("get")
                                                                        .executes(
                                                                           context -> getExtraClaimChunks(
                                                                              (CommandSourceStack)context.getSource(),
                                                                              EntityArgument.getPlayer(context, "player")
                                                                           )
                                                                        )
                                                                  ))
                                                               .then(
                                                                  Commands.literal("set")
                                                                     .then(
                                                                        Commands.argument("number", IntegerArgumentType.integer(0))
                                                                           .executes(
                                                                              context -> setExtraClaimChunks(
                                                                                 (CommandSourceStack)context.getSource(),
                                                                                 EntityArgument.getPlayer(context, "player"),
                                                                                 IntegerArgumentType.getInteger(context, "number"),
                                                                                 false
                                                                              )
                                                                           )
                                                                     )
                                                               ))
                                                            .then(
                                                               Commands.literal("add")
                                                                  .then(
                                                                     Commands.argument("number", IntegerArgumentType.integer())
                                                                        .executes(
                                                                           context -> setExtraClaimChunks(
                                                                              (CommandSourceStack)context.getSource(),
                                                                              EntityArgument.getPlayer(context, "player"),
                                                                              IntegerArgumentType.getInteger(context, "number"),
                                                                              true
                                                                           )
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                ))
                                             .then(
                                                Commands.literal("extra_force_load_chunks")
                                                   .then(
                                                      ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("player", EntityArgument.player())
                                                               .then(
                                                                  Commands.literal("get")
                                                                     .executes(
                                                                        context -> getExtraForceLoadChunks(
                                                                           (CommandSourceStack)context.getSource(), EntityArgument.getPlayer(context, "player")
                                                                        )
                                                                     )
                                                               ))
                                                            .then(
                                                               Commands.literal("set")
                                                                  .then(
                                                                     Commands.argument("number", IntegerArgumentType.integer(0))
                                                                        .executes(
                                                                           context -> setExtraForceLoadChunks(
                                                                              (CommandSourceStack)context.getSource(),
                                                                              EntityArgument.getPlayer(context, "player"),
                                                                              IntegerArgumentType.getInteger(context, "number"),
                                                                              false
                                                                           )
                                                                        )
                                                                  )
                                                            ))
                                                         .then(
                                                            Commands.literal("add")
                                                               .then(
                                                                  Commands.argument("number", IntegerArgumentType.integer())
                                                                     .executes(
                                                                        context -> setExtraForceLoadChunks(
                                                                           (CommandSourceStack)context.getSource(),
                                                                           EntityArgument.getPlayer(context, "player"),
                                                                           IntegerArgumentType.getInteger(context, "number"),
                                                                           true
                                                                        )
                                                                     )
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             Commands.literal("claim_as")
                                                .then(
                                                   ((RequiredArgumentBuilder)Commands.argument("team", TeamArgument.create())
                                                         .executes(
                                                            context -> claim((CommandSourceStack)context.getSource(), TeamArgument.get(context, "team"), 0)
                                                         ))
                                                      .then(
                                                         ((RequiredArgumentBuilder)radiusArg()
                                                               .executes(
                                                                  context -> claim(
                                                                     (CommandSourceStack)context.getSource(),
                                                                     TeamArgument.get(context, "team"),
                                                                     getRadiusArg(context)
                                                                  )
                                                               ))
                                                            .then(
                                                               ((RequiredArgumentBuilder)anchorArg()
                                                                     .executes(
                                                                        context -> claim(
                                                                           (CommandSourceStack)context.getSource(),
                                                                           TeamArgument.get(context, "team"),
                                                                           getRadiusArg(context),
                                                                           getAnchorArg(context),
                                                                           ((CommandSourceStack)context.getSource()).getLevel()
                                                                        )
                                                                     ))
                                                                  .then(
                                                                     dimArg()
                                                                        .executes(
                                                                           context -> claim(
                                                                              (CommandSourceStack)context.getSource(),
                                                                              TeamArgument.get(context, "team"),
                                                                              getRadiusArg(context),
                                                                              getAnchorArg(context),
                                                                              getDimArg(context)
                                                                           )
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          Commands.literal("unclaim_as")
                                             .then(
                                                ((RequiredArgumentBuilder)Commands.argument("team", TeamArgument.create())
                                                      .executes(
                                                         context -> unclaim((CommandSourceStack)context.getSource(), TeamArgument.get(context, "team"), 0)
                                                      ))
                                                   .then(
                                                      ((RequiredArgumentBuilder)radiusArg()
                                                            .executes(
                                                               context -> unclaim(
                                                                  (CommandSourceStack)context.getSource(),
                                                                  TeamArgument.get(context, "team"),
                                                                  getRadiusArg(context)
                                                               )
                                                            ))
                                                         .then(
                                                            ((RequiredArgumentBuilder)anchorArg()
                                                                  .executes(
                                                                     context -> unclaim(
                                                                        (CommandSourceStack)context.getSource(),
                                                                        TeamArgument.get(context, "team"),
                                                                        getRadiusArg(context),
                                                                        getAnchorArg(context),
                                                                        ((CommandSourceStack)context.getSource()).getLevel()
                                                                     )
                                                                  ))
                                                               .then(
                                                                  dimArg()
                                                                     .executes(
                                                                        context -> unclaim(
                                                                           (CommandSourceStack)context.getSource(),
                                                                           TeamArgument.get(context, "team"),
                                                                           getRadiusArg(context),
                                                                           getAnchorArg(context),
                                                                           getDimArg(context)
                                                                        )
                                                                     )
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       Commands.literal("unclaim_everything").executes(context -> unclaimEverything((CommandSourceStack)context.getSource()))
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)Commands.literal("unclaim_dimension")
                                          .executes(context -> unclaimDimension((CommandSourceStack)context.getSource())))
                                       .then(
                                          Commands.argument("dimension", DimensionArgument.dimension())
                                             .executes(
                                                context -> unclaimDimension(
                                                   (CommandSourceStack)context.getSource(), DimensionArgument.getDimension(context, "dimension").dimension()
                                                )
                                             )
                                       )
                                 ))
                              .then(Commands.literal("unload_everything").executes(context -> unloadEverything((CommandSourceStack)context.getSource()))))
                           .then(
                              ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("view_loaded_chunks")
                                       .executes(
                                          context -> viewLoadedChunks(
                                             (CommandSourceStack)context.getSource(), ((CommandSourceStack)context.getSource()).getLevel()
                                          )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)Commands.literal("reset")
                                             .executes(
                                                context -> resetLoadedChunks(
                                                   (CommandSourceStack)context.getSource(), ((CommandSourceStack)context.getSource()).getLevel()
                                                )
                                             ))
                                          .then(
                                             Commands.argument("dimension", DimensionArgument.dimension())
                                                .executes(
                                                   context -> resetLoadedChunks(
                                                      (CommandSourceStack)context.getSource(), DimensionArgument.getDimension(context, "dimension")
                                                   )
                                                )
                                          )
                                    ))
                                 .then(
                                    Commands.argument("dimension", DimensionArgument.dimension())
                                       .executes(
                                          context -> viewLoadedChunks(
                                             (CommandSourceStack)context.getSource(), DimensionArgument.getDimension(context, "dimension")
                                          )
                                       )
                                 )
                           ))
                        .then(
                           Commands.literal("open_claim_gui_as")
                              .then(Commands.argument("team", TeamArgument.create()).executes(FTBChunksCommands::openClaimGuiAs))
                        )
                  ))
               .then(Commands.literal("block_color").executes(context -> {
                  NetworkManager.sendToPlayer(((CommandSourceStack)context.getSource()).getPlayerOrException(), new RequestBlockColorPacket());
                  return 1;
               })))
            .then(
               Commands.literal("waypoint")
                  .then(
                     Commands.literal("add")
                        .then(
                           Commands.argument("name", StringArgumentType.string())
                              .then(
                                 ((RequiredArgumentBuilder)Commands.argument("position", BlockPosArgument.blockPos())
                                       .executes(
                                          context -> addWaypoint(
                                             (CommandSourceStack)context.getSource(),
                                             StringArgumentType.getString(context, "name"),
                                             BlockPosArgument.getBlockPos(context, "position")
                                          )
                                       ))
                                    .then(
                                       ((RequiredArgumentBuilder)Commands.argument("dimension", DimensionArgument.dimension())
                                             .executes(
                                                context -> addWaypoint(
                                                   (CommandSourceStack)context.getSource(),
                                                   StringArgumentType.getString(context, "name"),
                                                   DimensionArgument.getDimension(context, "dimension"),
                                                   BlockPosArgument.getBlockPos(context, "position")
                                                )
                                             ))
                                          .then(
                                             ((RequiredArgumentBuilder)Commands.argument("color", ColorArgument.color())
                                                   .executes(
                                                      context -> addWaypoint(
                                                         (CommandSourceStack)context.getSource(),
                                                         StringArgumentType.getString(context, "name"),
                                                         DimensionArgument.getDimension(context, "dimension"),
                                                         BlockPosArgument.getBlockPos(context, "position"),
                                                         ColorArgument.getColor(context, "color")
                                                      )
                                                   ))
                                                .then(
                                                   Commands.argument("gui", BoolArgumentType.bool())
                                                      .executes(
                                                         context -> addWaypoint(
                                                            (CommandSourceStack)context.getSource(),
                                                            StringArgumentType.getString(context, "name"),
                                                            DimensionArgument.getDimension(context, "dimension"),
                                                            BlockPosArgument.getBlockPos(context, "position"),
                                                            ColorArgument.getColor(context, "color"),
                                                            BoolArgumentType.getBool(context, "gui")
                                                         )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
      if (FTBChunks.isDevMode()) {
         dispatcher.register((LiteralArgumentBuilder)Commands.literal("ftbchunks_dev").then(Commands.literal("create_gui").executes(context -> {
            if (((CommandSourceStack)context.getSource()).getServer() instanceof DedicatedServer) {
               ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("Not on a dedicted server!"));
               return 0;
            } else {
               FTBChunksClient.openIconSettingsScreen();
               return 1;
            }
         })));
      }

      dispatcher.register((LiteralArgumentBuilder)Commands.literal("chunks").redirect(command));
   }

   private static int addWaypoint(CommandSourceStack source, String name, ServerLevel level, BlockPos position, ChatFormatting color, boolean useGui) throws CommandSyntaxException {
      if (color.getColor() != null) {
         NetworkManager.sendToPlayer(
            source.getPlayerOrException(), new AddWaypointPacket(name, new GlobalPos(level.dimension(), position), color.getColor(), useGui)
         );
         source.sendSuccess(() -> Component.translatable("ftbchunks.command.waypoint_added", new Object[]{name}), true);
      }

      return 1;
   }

   private static int addWaypoint(CommandSourceStack source, String name, ServerLevel level, BlockPos position, ChatFormatting color) throws CommandSyntaxException {
      return addWaypoint(source, name, level, position, color, false);
   }

   private static int addWaypoint(CommandSourceStack source, String name, BlockPos position) throws CommandSyntaxException {
      int idx = source.getPlayerOrException().getRandom().nextInt(ChatFormatting.values().length);
      return addWaypoint(source, name, source.getLevel(), position, ChatFormatting.values()[idx], false);
   }

   private static int addWaypoint(CommandSourceStack source, String name, ServerLevel level, BlockPos position) throws CommandSyntaxException {
      int idx = source.getPlayerOrException().getRandom().nextInt(ChatFormatting.values().length);
      return addWaypoint(source, name, level, position, ChatFormatting.values()[idx], false);
   }

   private static int bypassProtection(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      ClaimedChunkManagerImpl manager = claimManager();
      manager.setBypassProtection(player.getUUID(), !manager.getBypassProtection(player.getUUID()));
      source.sendSuccess(() -> {
         boolean bypassProtection = manager.getBypassProtection(player.getUUID());
         return Component.translatable("ftbchunks.command.bypass_protection_" + (bypassProtection ? "enabled" : "disabled"));
      }, true);
      return 1;
   }

   private static int openClaimGuiAs(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      Team team = TeamArgument.get(context, "team");
      NetworkManager.sendToPlayer(((CommandSourceStack)context.getSource()).getPlayerOrException(), new OpenClaimGUIPacket(team.getTeamId()));
      return 1;
   }

   private static void forEachChunk(CommandSourceStack source, int r, FTBChunksCommands.ChunkCallback callback) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      Team team = (Team)FTBTeamsAPI.api().getManager().getTeamForPlayer(player).orElseThrow(() -> TeamArgument.TEAM_NOT_FOUND.create(player.getId()));
      forEachChunk(team, source.getLevel(), toColumn(source.getPosition()), r, callback);
   }

   private static void forEachChunk(Team team, Level level, ColumnPos anchor, int r, FTBChunksCommands.ChunkCallback callback) throws CommandSyntaxException {
      ChunkTeamDataImpl data = claimManager().getOrCreateData(team);
      ResourceKey<Level> dimId = level.dimension();
      int ox = Mth.floor(anchor.x()) >> 4;
      int oz = Mth.floor(anchor.z()) >> 4;
      List<ChunkDimPos> list = new ArrayList<>();
      r >>= 4;

      for (int z = -r; z <= r; z++) {
         for (int x = -r; x <= r; x++) {
            list.add(new ChunkDimPos(dimId, ox + x, oz + z));
         }
      }

      list.sort(Comparator.comparingDouble(cdp -> MathUtils.distSq(ox, oz, cdp.x(), cdp.z())));

      for (ChunkDimPos pos : list) {
         callback.accept(data, pos);
      }
   }

   private static int claim(CommandSourceStack source, Team team, int r) throws CommandSyntaxException {
      return claim(source, team, r, toColumn(source.getPosition()), source.getLevel());
   }

   private static int claim(CommandSourceStack source, Team team, int r, ColumnPos anchor, Level level) throws CommandSyntaxException {
      int[] success = new int[1];
      forEachChunk(team, level, anchor, r, (data, pos) -> {
         if (data.claim(source, pos, false).isSuccess()) {
            success[0]++;
         }
      });
      source.sendSuccess(() -> Component.translatable("ftbchunks.commands.claimed", new Object[]{success[0]}), false);
      ChunkDimPos origin = new ChunkDimPos(level.dimension(), anchor.x() >> 4, anchor.z() >> 4);
      FTBChunks.LOGGER.info(source.getTextName() + " claimed " + success[0] + " chunks around " + origin + "for team " + team.getShortName());
      return success[0];
   }

   private static int unclaim(CommandSourceStack source, Team team, int r) throws CommandSyntaxException {
      return unclaim(source, team, r, toColumn(source.getPosition()), source.getLevel());
   }

   private static int unclaim(CommandSourceStack source, Team team, int r, ColumnPos anchor, Level level) throws CommandSyntaxException {
      int[] success = new int[1];
      forEachChunk(team, level, anchor, r, (data, pos) -> {
         if (data.unclaim(source, pos, false).isSuccess()) {
            success[0]++;
         }
      });
      source.sendSuccess(() -> Component.translatable("ftbchunks.commands.unclaimed", new Object[]{success[0]}), false);
      ChunkDimPos origin = new ChunkDimPos(level.dimension(), anchor.x() >> 4, anchor.z() >> 4);
      FTBChunks.LOGGER.info(source.getTextName() + " unclaimed " + success[0] + " chunks around " + origin + "for team " + team.getShortName());
      return success[0];
   }

   private static int load(CommandSourceStack source, int r) throws CommandSyntaxException {
      int[] success = new int[1];
      forEachChunk(source, r, (data, pos) -> {
         ClaimResult result = data.forceLoad(source, pos, false);
         if (result.isSuccess()) {
            success[0]++;
         }
      });
      source.sendSuccess(() -> Component.translatable("ftbchunks.commands.force_loaded", new Object[]{success[0]}), false);
      FTBChunks.LOGGER.info(source.getTextName() + " loaded " + success[0] + " chunks at " + new ChunkDimPos(source.getPlayerOrException()));
      return success[0];
   }

   private static int unload(CommandSourceStack source, int r) throws CommandSyntaxException {
      int[] success = new int[1];
      forEachChunk(source, r, (data, pos) -> {
         if (data.unForceLoad(source, pos, false).isSuccess()) {
            success[0]++;
         }
      });
      source.sendSuccess(() -> Component.translatable("ftbchunks.command.unloaded", new Object[]{success[0]}), false);
      FTBChunks.LOGGER.info(source.getTextName() + " unloaded " + success[0] + " chunks at " + new ChunkDimPos(source.getPlayerOrException()));
      return success[0];
   }

   private static int unclaimAll(CommandSourceStack source, Team team) {
      ChunkTeamDataImpl data = claimManager().getOrCreateData(team);
      int unclaimed = 0;

      for (ClaimedChunkImpl c : new ArrayList<>(data.getClaimedChunks())) {
         ClaimResult unclaim = data.unclaim(source, c.getPos(), false);
         if (unclaim.isSuccess()) {
            unclaimed++;
         }
      }

      data.markDirty();
      int finalUnclaimed = unclaimed;
      source.sendSuccess(() -> Component.translatable("ftbchunks.commands.unclaimed", new Object[]{finalUnclaimed}), true);
      return 1;
   }

   private static int unloadAll(CommandSourceStack source, Team team) {
      ChunkTeamDataImpl data = claimManager().getOrCreateData(team);
      int unloaded = 0;

      for (ClaimedChunk c : new ArrayList<>(data.getClaimedChunks())) {
         ClaimResult claimResult = data.unForceLoad(source, c.getPos(), false);
         if (claimResult.isSuccess()) {
            unloaded++;
         }
      }

      data.markDirty();
      int finalUnloaded = unloaded;
      source.sendSuccess(() -> Component.translatable("ftbchunks.command.unloaded", new Object[]{finalUnloaded}), true);
      return 1;
   }

   private static int info(CommandSourceStack source, ChunkDimPos pos) throws CommandSyntaxException {
      ClaimedChunk chunk = claimManager().getChunk(pos);
      boolean canKnowChunkStatus = source.hasPermission(2)
         || source.isPlayer()
         || chunk != null && chunk.getTeamData().canPlayerUse(source.getPlayerOrException(), FTBChunksProperties.CLAIM_VISIBILITY);
      if (canKnowChunkStatus) {
         if (chunk == null) {
            source.sendSuccess(() -> Component.translatable("ftbchunks.commands.not_claimed"), false);
            return 1;
         } else {
            source.sendSuccess(
               () -> {
                  Component owner = chunk.getTeamData()
                     .getTeam()
                     .getColoredName()
                     .copy()
                     .append(" / " + UndashedUuid.toString(chunk.getTeamData().getTeam().getId()));
                  String location = pos.dimension().location().toString() + " [" + pos.x() + ", " + pos.z() + "]";
                  return Component.translatable("ftbchunks.commands.location", new Object[]{location})
                     .append(Component.literal("\n"))
                     .append(Component.translatable("ftbchunks.commands.owner").append(owner.getString()))
                     .append(Component.literal("\n"))
                     .append(Component.translatable("ftbchunks.commands.is_force_loaded", new Object[]{chunk.isForceLoaded()}));
               },
               true
            );
            return 1;
         }
      } else {
         source.sendSuccess(() -> Component.translatable("ftbchunks.commands.cant_determined"), false);
         return 1;
      }
   }

   private static int getExtraClaimChunks(CommandSourceStack source, ServerPlayer player) {
      ChunkTeamData personalData = claimManager().getPersonalData(player.getUUID());
      if (personalData == null) {
         source.sendFailure(Component.translatable("ftbchunks.commands.no_personal_info", new Object[]{player.getDisplayName()}));
         return 0;
      } else {
         source.sendSuccess(
            () -> Component.translatable("ftbchunks.command.extra_chunks_claimed", new Object[]{player.getDisplayName(), personalData.getExtraClaimChunks()}),
            true
         );
         return personalData.getExtraClaimChunks();
      }
   }

   private static int setExtraClaimChunks(CommandSourceStack source, ServerPlayer player, int extra, boolean adding) {
      ChunkTeamDataImpl personalData = claimManager().getPersonalData(player.getUUID());
      if (personalData == null) {
         source.sendFailure(Component.translatable("ftbchunks.commands.no_personal_info", new Object[]{player.getDisplayName()}));
         return 0;
      } else {
         personalData.setExtraClaimChunks(Math.max(0, extra + (adding ? personalData.getExtraClaimChunks() : 0)));
         personalData.markDirty();
         ChunkTeamDataImpl teamData = claimManager().getOrCreateData(player);
         teamData.updateLimits();
         SendGeneralDataPacket.send(teamData, player);
         source.sendSuccess(
            () -> Component.translatable(
               "ftbchunks.command.set_extra_chunks_claimed", new Object[]{personalData.getExtraClaimChunks(), player.getDisplayName()}
            ),
            true
         );
         return personalData.getExtraClaimChunks();
      }
   }

   private static int getExtraForceLoadChunks(CommandSourceStack source, ServerPlayer player) {
      ChunkTeamData personalData = claimManager().getPersonalData(player.getUUID());
      if (personalData == null) {
         source.sendFailure(Component.translatable("ftbchunks.commands.no_personal_info", new Object[]{player.getDisplayName()}));
         return 0;
      } else {
         source.sendSuccess(
            () -> Component.translatable(
               "ftbchunks.command.extra_forceload_chunks", new Object[]{player.getDisplayName(), personalData.getExtraForceLoadChunks()}
            ),
            true
         );
         return personalData.getExtraForceLoadChunks();
      }
   }

   private static int setExtraForceLoadChunks(CommandSourceStack source, ServerPlayer player, int extra, boolean adding) {
      ChunkTeamDataImpl personalData = claimManager().getPersonalData(player.getUUID());
      if (personalData == null) {
         source.sendFailure(Component.translatable("ftbchunks.commands.no_personal_info", new Object[]{player.getDisplayName()}));
         return 0;
      } else {
         personalData.setExtraForceLoadChunks(Math.max(0, extra + (adding ? personalData.getExtraForceLoadChunks() : 0)));
         personalData.markDirty();
         ChunkTeamDataImpl teamData = claimManager().getOrCreateData(player);
         teamData.updateLimits();
         SendGeneralDataPacket.send(teamData, player);
         source.sendSuccess(
            () -> Component.translatable(
               "ftbchunks.command.set_extra_forceload_chunks", new Object[]{personalData.getExtraForceLoadChunks(), player.getDisplayName()}
            ),
            true
         );
         return personalData.getExtraForceLoadChunks();
      }
   }

   private static int unclaimEverything(CommandSourceStack source) {
      int claimedChunks = 0;

      for (ClaimedChunk c : new ArrayList<>(claimManager().getAllClaimedChunks())) {
         c.getTeamData().unclaim(source, c.getPos(), false);
         claimedChunks++;
      }

      int finalClaimedChunks = claimedChunks;
      source.sendSuccess(() -> Component.translatable("ftbchunks.commands.unclaimed", new Object[]{finalClaimedChunks}), true);
      return 1;
   }

   private static int unclaimDimension(CommandSourceStack source, ResourceKey<Level> dim) {
      int claimedChunks = 0;

      for (ClaimedChunk c : new ArrayList<>(claimManager().getAllClaimedChunks())) {
         if (source.getLevel().dimension().equals(dim)) {
            c.getTeamData().unclaim(source, c.getPos(), false);
            claimedChunks++;
         }
      }

      int finalClaimedChunks = claimedChunks;
      source.sendSuccess(() -> Component.translatable("ftbchunks.commands.unclaimed", new Object[]{finalClaimedChunks}), true);
      return 1;
   }

   private static int unclaimDimension(CommandSourceStack source) {
      return unclaimDimension(source, source.getLevel().dimension());
   }

   private static int unloadEverything(CommandSourceStack source) {
      int unloadedChunks = 0;
      int totalChunks = 0;

      for (ClaimedChunk c : claimManager().getAllClaimedChunks()) {
         if (c.isForceLoaded()) {
            c.getTeamData().unForceLoad(source, c.getPos(), false);
            unloadedChunks++;
         }

         totalChunks++;
      }

      int finalUnloadedChunks = unloadedChunks;
      int finalTotalChunks = totalChunks;
      source.sendSuccess(() -> Component.translatable("ftbchunks.commands.unloaded", new Object[]{finalUnloadedChunks, finalTotalChunks}), true);
      return 1;
   }

   private static int viewLoadedChunks(CommandSourceStack source, ServerLevel level) throws CommandSyntaxException {
      Long2IntOpenHashMap chunks = new Long2IntOpenHashMap();
      ObjectIterator map = level.getChunkSource().chunkMap.updatingChunkMap.values().iterator();

      while (map.hasNext()) {
         ChunkHolder holder = (ChunkHolder)map.next();
         chunks.put(holder.getPos().toLong(), 1);
      }

      Long2ObjectMap<UUID> mapx = claimManager().getForceLoadedChunks(level.dimension());
      LongIterator var8 = mapx.keySet().iterator();

      while (var8.hasNext()) {
         long pos = (Long)var8.next();
         if (chunks.get(pos) == 1) {
            chunks.put(pos, 2);
         }
      }

      var8 = level.getForcedChunks().iterator();

      while (var8.hasNext()) {
         long pos = (Long)var8.next();
         if (chunks.get(pos) == 1) {
            chunks.put(pos, 2);
         }
      }

      source.sendSuccess(() -> Component.translatable("ftbchunks.command.view_loaded", new Object[]{chunks.size()}), false);
      NetworkManager.sendToPlayer(source.getPlayerOrException(), new LoadedChunkViewPacket(level.dimension(), chunks));
      return 1;
   }

   private static int resetLoadedChunks(CommandSourceStack source, ServerLevel level) throws CommandSyntaxException {
      NetworkManager.sendToPlayer(source.getPlayerOrException(), new LoadedChunkViewPacket(level.dimension(), Long2IntMaps.EMPTY_MAP));
      return 1;
   }

   private static RequiredArgumentBuilder<CommandSourceStack, Integer> radiusArg() {
      return Commands.argument("radius_in_blocks", IntegerArgumentType.integer(0, 512));
   }

   private static int getRadiusArg(CommandContext<CommandSourceStack> context) {
      return IntegerArgumentType.getInteger(context, "radius_in_blocks");
   }

   private static RequiredArgumentBuilder<CommandSourceStack, Coordinates> anchorArg() {
      return Commands.argument("anchor", new ColumnPosArgument());
   }

   private static ColumnPos getAnchorArg(CommandContext<CommandSourceStack> context) {
      return ColumnPosArgument.getColumnPos(context, "anchor");
   }

   private static RequiredArgumentBuilder<CommandSourceStack, ResourceLocation> dimArg() {
      return Commands.argument("dimension", DimensionArgument.dimension());
   }

   private static ServerLevel getDimArg(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      return DimensionArgument.getDimension(context, "dimension");
   }

   private static RequiredArgumentBuilder<CommandSourceStack, TeamArgumentProvider> forTeam(ToIntBiFunction<CommandSourceStack, Team> callback) {
      return (RequiredArgumentBuilder<CommandSourceStack, TeamArgumentProvider>)((RequiredArgumentBuilder)Commands.argument("team", TeamArgument.create())
            .requires(source -> source.hasPermission(2)))
         .executes(context -> callback.applyAsInt((CommandSourceStack)context.getSource(), TeamArgument.get(context, "team")));
   }

   private static ColumnPos toColumn(Vec3 pos) {
      BlockPos blockPos = BlockPos.containing(pos);
      return new ColumnPos(blockPos.getX(), blockPos.getZ());
   }

   private static Team selfTeam(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      return (Team)FTBTeamsAPI.api().getManager().getTeamForPlayer(player).orElseThrow(() -> TeamArgument.TEAM_NOT_FOUND.create(player.getId()));
   }

   private static ClaimedChunkManagerImpl claimManager() {
      return ClaimedChunkManagerImpl.getInstance();
   }

   private interface ChunkCallback {
      void accept(ChunkTeamDataImpl var1, ChunkDimPos var2) throws CommandSyntaxException;
   }
}
