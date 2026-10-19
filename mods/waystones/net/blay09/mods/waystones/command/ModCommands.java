package net.blay09.mods.waystones.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import net.blay09.mods.balm.api.command.BalmCommands;
import net.blay09.mods.waystones.api.MutableWaystone;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneStyle;
import net.blay09.mods.waystones.api.WaystoneStyles;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.comparator.WaystoneComparators;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ModCommands {
   private static final SimpleCommandExceptionType ERROR_WAYSTONE_NOT_FOUND = new SimpleCommandExceptionType(
      Component.translatable("commands.waystones.waystone_not_found")
   );
   private static final ResourceLocation PERMISSION_WAYSTONES_ACTIVATE = ResourceLocation.fromNamespaceAndPath("waystones", "command.waystones.activate");
   private static final ResourceLocation PERMISSION_WAYSTONES_FORGET = ResourceLocation.fromNamespaceAndPath("waystones", "command.waystones.forget");
   private static final ResourceLocation PERMISSION_WAYSTONES_COUNT = ResourceLocation.fromNamespaceAndPath("waystones", "command.waystones.count");
   private static final ResourceLocation PERMISSION_WAYSTONES_LIST = ResourceLocation.fromNamespaceAndPath("waystones", "command.waystones.list");
   private static final ResourceLocation PERMISSION_WAYSTONES_PLACE = ResourceLocation.fromNamespaceAndPath("waystones", "command.waystones.place");
   private static final ResourceLocation PERMISSION_WAYSTONES_GUI = ResourceLocation.fromNamespaceAndPath("waystones", "command.waystones.gui");
   private static final ResourceLocation PERMISSION_WAYSTONES_COOLDOWN = ResourceLocation.fromNamespaceAndPath("waystones", "command.waystones.cooldown");

   public static void initialize(BalmCommands commands) {
      BalmCommands.registerPermission(PERMISSION_WAYSTONES_ACTIVATE, 2);
      BalmCommands.registerPermission(PERMISSION_WAYSTONES_FORGET, 2);
      BalmCommands.registerPermission(PERMISSION_WAYSTONES_COUNT, 2);
      BalmCommands.registerPermission(PERMISSION_WAYSTONES_PLACE, 2);
      BalmCommands.registerPermission(PERMISSION_WAYSTONES_LIST, 2);
      BalmCommands.registerPermission(PERMISSION_WAYSTONES_GUI, 2);
      BalmCommands.registerPermission(PERMISSION_WAYSTONES_COOLDOWN, 2);
      commands.register(
         dispatcher -> dispatcher.register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                       "waystones"
                                    )
                                    .requires(
                                       BalmCommands.requireAnyPermission(
                                          new ResourceLocation[]{
                                             PERMISSION_WAYSTONES_ACTIVATE,
                                             PERMISSION_WAYSTONES_FORGET,
                                             PERMISSION_WAYSTONES_COUNT,
                                             PERMISSION_WAYSTONES_LIST,
                                             PERMISSION_WAYSTONES_GUI,
                                             PERMISSION_WAYSTONES_PLACE,
                                             PERMISSION_WAYSTONES_COOLDOWN
                                          }
                                       )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)Commands.literal("activate")
                                          .requires(BalmCommands.requirePermission(PERMISSION_WAYSTONES_ACTIVATE)))
                                       .then(
                                          Commands.argument("targets", EntityArgument.players())
                                             .then(
                                                Commands.argument("pos", BlockPosArgument.blockPos())
                                                   .executes(
                                                      context -> {
                                                         Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
                                                         BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, "pos");
                                                         Optional<Waystone> foundWaystone = WaystonesAPI.getWaystoneAt(
                                                            ((CommandSourceStack)context.getSource()).getLevel(), pos
                                                         );
                                                         if (!foundWaystone.isPresent()) {
                                                            throw ERROR_WAYSTONE_NOT_FOUND.create();
                                                         }

                                                         Waystone waystone = foundWaystone.get();

                                                         for (ServerPlayer player : targets) {
                                                            WaystonesAPI.activateWaystone(player, waystone);
                                                         }

                                                         if (targets.size() == 1) {
                                                            ((CommandSourceStack)context.getSource())
                                                               .sendSuccess(
                                                                  () -> Component.translatable(
                                                                     "commands.waystones.activate.success.single",
                                                                     new Object[]{waystone.getName(), targets.iterator().next().getDisplayName()}
                                                                  ),
                                                                  true
                                                               );
                                                         } else {
                                                            ((CommandSourceStack)context.getSource())
                                                               .sendSuccess(
                                                                  () -> Component.translatable(
                                                                     "commands.waystones.activate.success.multiple",
                                                                     new Object[]{waystone.getName(), targets.size()}
                                                                  ),
                                                                  true
                                                               );
                                                         }

                                                         return targets.size();
                                                      }
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)Commands.literal("forget").requires(BalmCommands.requirePermission(PERMISSION_WAYSTONES_FORGET)))
                                    .then(
                                       ((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players())
                                             .then(
                                                Commands.argument("pos", BlockPosArgument.blockPos())
                                                   .executes(
                                                      context -> {
                                                         Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
                                                         BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, "pos");
                                                         Optional<Waystone> foundWaystone = WaystonesAPI.getWaystoneAt(
                                                            ((CommandSourceStack)context.getSource()).getLevel(), pos
                                                         );
                                                         if (!foundWaystone.isPresent()) {
                                                            throw ERROR_WAYSTONE_NOT_FOUND.create();
                                                         }

                                                         Waystone waystone = foundWaystone.get();

                                                         for (ServerPlayer player : targets) {
                                                            WaystonesAPI.deactivateWaystone(player, waystone);
                                                         }

                                                         if (targets.size() == 1) {
                                                            ((CommandSourceStack)context.getSource())
                                                               .sendSuccess(
                                                                  () -> Component.translatable(
                                                                     "commands.waystones.forget.success.single",
                                                                     new Object[]{waystone.getName(), targets.iterator().next().getDisplayName()}
                                                                  ),
                                                                  true
                                                               );
                                                         } else {
                                                            ((CommandSourceStack)context.getSource())
                                                               .sendSuccess(
                                                                  () -> Component.translatable(
                                                                     "commands.waystones.forget.success.multiple",
                                                                     new Object[]{waystone.getName(), targets.size()}
                                                                  ),
                                                                  true
                                                               );
                                                         }

                                                         return targets.size();
                                                      }
                                                   )
                                             ))
                                          .then(
                                             Commands.literal("all")
                                                .executes(
                                                   context -> {
                                                      Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
                                                      int totalDeactivated = 0;

                                                      for (ServerPlayer player : targets) {
                                                         Collection<Waystone> waystones = PlayerWaystoneManager.getActivatedWaystones(player);

                                                         for (Waystone waystone : waystones) {
                                                            WaystonesAPI.deactivateWaystone(player, waystone);
                                                         }

                                                         totalDeactivated += waystones.size();
                                                      }

                                                      if (targets.size() == 1) {
                                                         ((CommandSourceStack)context.getSource())
                                                            .sendSuccess(
                                                               () -> Component.translatable(
                                                                  "commands.waystones.forget.all.success.single",
                                                                  new Object[]{targets.iterator().next().getDisplayName()}
                                                               ),
                                                               true
                                                            );
                                                      } else {
                                                         ((CommandSourceStack)context.getSource())
                                                            .sendSuccess(
                                                               () -> Component.translatable(
                                                                  "commands.waystones.forget.all.success.multiple", new Object[]{targets.size()}
                                                               ),
                                                               true
                                                            );
                                                      }

                                                      return totalDeactivated;
                                                   }
                                                )
                                          )
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)Commands.literal("place").requires(BalmCommands.requirePermission(PERMISSION_WAYSTONES_PLACE)))
                                 .then(
                                    Commands.argument("pos", BlockPosArgument.blockPos())
                                       .then(
                                          Commands.argument("style", StringArgumentType.word())
                                             .suggests(
                                                (context, builder) -> SharedSuggestionProvider.suggest(
                                                   WaystoneStyles.getRegisteredKeys()
                                                      .stream()
                                                      .map(id -> id.getNamespace().equals("waystones") ? id.getPath() : id.toString()),
                                                   builder
                                                )
                                             )
                                             .then(Commands.argument("name", StringArgumentType.greedyString()).executes(context -> {
                                                BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, "pos");
                                                String styleKey = StringArgumentType.getString(context, "style");
                                                String name = StringArgumentType.getString(context, "name");
                                                ServerLevel level = ((CommandSourceStack)context.getSource()).getLevel();
                                                ResourceLocation styleId;
                                                if (!styleKey.contains(":")) {
                                                   styleId = ResourceLocation.fromNamespaceAndPath("waystones", styleKey);
                                                } else {
                                                   styleId = ResourceLocation.tryParse(styleKey);
                                                }

                                                WaystoneStyle style = styleId != null ? WaystoneStyles.getStyle(styleId) : null;
                                                if (style == null) {
                                                   ((CommandSourceStack)context.getSource())
                                                      .sendFailure(Component.literal("Unknown waystone style: " + styleKey));
                                                   return 0;
                                                } else {
                                                   WaystonesAPI.placeWaystone(level, pos, style)
                                                      .ifPresent(waystone -> ((MutableWaystone)waystone).setName(Component.literal(name)));
                                                   return 1;
                                                }
                                             }))
                                       )
                                 )
                           ))
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("count").requires(BalmCommands.requirePermission(PERMISSION_WAYSTONES_COUNT)))
                              .then(Commands.argument("player", EntityArgument.player()).executes(new CountWaystonesCommand()))
                        ))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("list").requires(BalmCommands.requirePermission(PERMISSION_WAYSTONES_LIST)))
                           .then(
                              ((RequiredArgumentBuilder)Commands.argument("player", EntityArgument.player())
                                    .executes(
                                       ctx -> {
                                          ServerPlayer caller = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
                                          ServerPlayer target = ((EntitySelector)ctx.getArgument("player", EntitySelector.class))
                                             .findSinglePlayer((CommandSourceStack)ctx.getSource());
                                          List<Waystone> waystones = PlayerWaystoneManager.getActivatedWaystones(target)
                                             .stream()
                                             .filter(it -> it.isOwner(target))
                                             .sorted(WaystoneComparators.forAdminInspection(caller, target))
                                             .toList();
                                          ((CommandSourceStack)ctx.getSource())
                                             .sendSystemMessage(
                                                Component.translatable("commands.waystones.list.header", new Object[]{target.getScoreboardName()})
                                             );

                                          for (Waystone waystone : waystones) {
                                             ((CommandSourceStack)ctx.getSource()).sendSystemMessage(componentForWaystoneList(caller, target, waystone));
                                          }

                                          MutableComponent result = Component.translatable("commands.waystones.list.footer", new Object[]{waystones.size()});
                                          ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> result, false);
                                          return waystones.size();
                                       }
                                    ))
                                 .then(
                                    Commands.literal("all")
                                       .executes(
                                          ctx -> {
                                             ServerPlayer caller = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
                                             ServerPlayer target = ((EntitySelector)ctx.getArgument("player", EntitySelector.class))
                                                .findSinglePlayer((CommandSourceStack)ctx.getSource());
                                             List<Waystone> waystones = PlayerWaystoneManager.getActivatedWaystones(target)
                                                .stream()
                                                .sorted(WaystoneComparators.forAdminInspection(caller, target))
                                                .toList();
                                             ((CommandSourceStack)ctx.getSource())
                                                .sendSystemMessage(
                                                   Component.translatable("commands.waystones.list.all.header", new Object[]{target.getScoreboardName()})
                                                );

                                             for (Waystone waystone : waystones) {
                                                ((CommandSourceStack)ctx.getSource()).sendSystemMessage(componentForWaystoneList(caller, target, waystone));
                                             }

                                             long ownedCount = waystones.stream().filter(it -> it.isOwner(target)).count();
                                             MutableComponent result = Component.translatable(
                                                "commands.waystones.list.all.footer", new Object[]{waystones.size(), ownedCount}
                                             );
                                             ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> result, false);
                                             return waystones.size();
                                          }
                                       )
                                 )
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("gui").requires(BalmCommands.requirePermission(PERMISSION_WAYSTONES_GUI)))
                        .then(Commands.argument("player", EntityArgument.player()).executes(new OpenPlayerWaystonesGuiCommand()))
                  ))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("cooldown").requires(BalmCommands.requirePermission(PERMISSION_WAYSTONES_COOLDOWN)))
                     .then(
                        Commands.argument("targets", EntityArgument.players())
                           .then(
                              ((LiteralArgumentBuilder)Commands.literal("reset")
                                    .then(
                                       Commands.literal("all")
                                          .executes(
                                             context -> {
                                                Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");

                                                for (ServerPlayer player : targets) {
                                                   PlayerWaystoneManager.resetCooldowns(player);
                                                   WaystoneSyncManager.sendWaystoneCooldowns(player);
                                                }

                                                if (targets.size() == 1) {
                                                   ((CommandSourceStack)context.getSource())
                                                      .sendSuccess(
                                                         () -> Component.translatable(
                                                            "commands.waystones.cooldown.reset.all.success.single",
                                                            new Object[]{targets.iterator().next().getDisplayName()}
                                                         ),
                                                         true
                                                      );
                                                } else {
                                                   ((CommandSourceStack)context.getSource())
                                                      .sendSuccess(
                                                         () -> Component.translatable(
                                                            "commands.waystones.cooldown.reset.all.success.multiple", new Object[]{targets.size()}
                                                         ),
                                                         true
                                                      );
                                                }

                                                return targets.size();
                                             }
                                          )
                                    ))
                                 .then(
                                    Commands.argument("identifier", StringArgumentType.word())
                                       .suggests((context, builder) -> {
                                          try {
                                             Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
                                             HashSet<String> keys = new HashSet<>();

                                             for (ServerPlayer player : targets) {
                                                for (ResourceLocation key : PlayerWaystoneManager.getCooldowns(player).keySet()) {
                                                   keys.add(key.toString());
                                                }
                                             }

                                             return SharedSuggestionProvider.suggest(keys, builder);
                                          } catch (Exception e) {
                                             return builder.buildFuture();
                                          }
                                       })
                                       .executes(
                                          context -> {
                                             Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
                                             String identifierStr = StringArgumentType.getString(context, "identifier");
                                             ResourceLocation cooldownKey = ResourceLocation.tryParse(identifierStr);
                                             if (cooldownKey == null) {
                                                ((CommandSourceStack)context.getSource())
                                                   .sendFailure(
                                                      Component.translatable(
                                                         "commands.waystones.cooldown.reset.invalid_identifier", new Object[]{identifierStr}
                                                      )
                                                   );
                                                return 0;
                                             }

                                             for (ServerPlayer player : targets) {
                                                PlayerWaystoneManager.setCooldownUntil(player, cooldownKey, 0L);
                                                WaystoneSyncManager.sendWaystoneCooldowns(player);
                                             }

                                             if (targets.size() == 1) {
                                                ((CommandSourceStack)context.getSource())
                                                   .sendSuccess(
                                                      () -> Component.translatable(
                                                         "commands.waystones.cooldown.reset.success.single",
                                                         new Object[]{cooldownKey.toString(), targets.iterator().next().getDisplayName()}
                                                      ),
                                                      true
                                                   );
                                             } else {
                                                ((CommandSourceStack)context.getSource())
                                                   .sendSuccess(
                                                      () -> Component.translatable(
                                                         "commands.waystones.cooldown.reset.success.multiple",
                                                         new Object[]{cooldownKey.toString(), targets.size()}
                                                      ),
                                                      true
                                                   );
                                             }

                                             return targets.size();
                                          }
                                       )
                                 )
                           )
                     )
               )
         )
      );
   }

   private static Component componentForWaystoneList(ServerPlayer caller, ServerPlayer target, Waystone waystone) {
      ResourceLocation waystoneDimensionId = waystone.getDimension().location();
      BlockPos waystonePos = waystone.getPos();
      Component location;
      if (waystone.getDimension() != caller.level().dimension()) {
         location = Component.translatable("commands.waystones.list.in_dimension", new Object[]{waystoneDimensionId.toString()});
      } else {
         int distance = (int)caller.position().distanceTo(waystonePos.getCenter());
         location = Component.translatable("commands.waystones.list.at_distance", new Object[]{distance});
      }

      String suggestedCommand = String.format(
         "/execute in %s run teleport %d %d %d", waystoneDimensionId, waystonePos.getX(), waystonePos.getY(), waystonePos.getZ()
      );
      MutableComponent coordinates = Component.translatable(
            "commands.waystones.list.coordinates", new Object[]{waystonePos.getX(), waystonePos.getY(), waystonePos.getZ()}
         )
         .withStyle(ChatFormatting.YELLOW)
         .withStyle(Style.EMPTY.withClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, suggestedCommand)));
      MutableComponent waystoneName = waystone.getName()
         .copy()
         .withStyle(ChatFormatting.GREEN)
         .withStyle(Style.EMPTY.withClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, suggestedCommand)));
      String entryKey = waystone.isOwner(target) ? "commands.waystones.list.entry.owned" : "commands.waystones.list.entry.activated";
      return Component.translatable(entryKey, new Object[]{location, coordinates, waystoneName});
   }
}
