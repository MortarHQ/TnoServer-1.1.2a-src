package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.argument.IItemStackArgument;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.natives.entity.type.player.ExpandPlayer;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class MiscCommands {
   private static final String DOCS = "https://docs.blamejared.com";
   private static final String DISCORD = "https://discord.blamejared.com";
   private static final String ISSUES = "https://github.com/CraftTweaker/CraftTweaker/issues";
   private static final String PATREON = "https://patreon.com/jaredlll08";

   private MiscCommands() {
   }

   public static void registerCommands(ICommandRegistrationHandler handler) {
      handler.registerRootCommand(
         "give",
         Component.translatable("crafttweaker.command.description.give"),
         builder -> ((LiteralArgumentBuilder)builder.requires(source -> source.hasPermission(2)))
            .then(Commands.argument("item", IItemStackArgument.get()).executes(context -> {
               ExpandPlayer.give(
                  ((CommandSourceStack)context.getSource()).getPlayerOrException(), (IItemStack)context.getArgument("item", IItemStack.class), -1
               );
               return 1;
            }))
      );
      handler.registerRootCommand(
         "reload",
         Component.translatable("crafttweaker.command.description.reload"),
         builder -> builder.executes(
            context -> {
               CommandUtilities.send(
                  (CommandSourceStack)context.getSource(),
                  CommandUtilities.run(Component.translatable("crafttweaker.command.misc.reload.info").withStyle(ChatFormatting.AQUA), "/reload")
               );
               return 1;
            }
         )
      );
      handler.registerRootCommand(
         "discord",
         Component.translatable("crafttweaker.command.description.discord"),
         builder -> builder.executes(
            context -> {
               CommandUtilities.send(
                  (CommandSourceStack)context.getSource(),
                  CommandUtilities.openingUrl(
                     Component.translatable("crafttweaker.command.misc.link", new Object[]{CommandUtilities.makeNoticeable("https://discord.blamejared.com")})
                        .withStyle(ChatFormatting.GREEN),
                     "https://discord.blamejared.com"
                  )
               );
               return 1;
            }
         )
      );
      handler.registerRootCommand(
         "issues",
         Component.translatable("crafttweaker.command.description.issues"),
         builder -> builder.executes(
            context -> {
               CommandUtilities.send(
                  (CommandSourceStack)context.getSource(),
                  CommandUtilities.openingUrl(
                     Component.translatable(
                           "crafttweaker.command.misc.link",
                           new Object[]{CommandUtilities.makeNoticeable("https://github.com/CraftTweaker/CraftTweaker/issues")}
                        )
                        .withStyle(ChatFormatting.GREEN),
                     "https://github.com/CraftTweaker/CraftTweaker/issues"
                  )
               );
               return 1;
            }
         )
      );
      handler.registerRootCommand(
         "patreon",
         Component.translatable("crafttweaker.command.description.patreon"),
         builder -> builder.executes(
            context -> {
               CommandUtilities.send(
                  (CommandSourceStack)context.getSource(),
                  CommandUtilities.openingUrl(
                     Component.translatable("crafttweaker.command.misc.link", new Object[]{CommandUtilities.makeNoticeable("https://patreon.com/jaredlll08")})
                        .withStyle(ChatFormatting.GREEN),
                     "https://patreon.com/jaredlll08"
                  )
               );
               return 1;
            }
         )
      );
      Stream.of("wiki", "docs", "examples")
         .forEach(
            id -> handler.registerRootCommand(
               id,
               Component.translatable("crafttweaker.command.description.docs"),
               builder -> builder.executes(
                  context -> {
                     CommandUtilities.send(
                        (CommandSourceStack)context.getSource(),
                        CommandUtilities.openingUrl(
                           Component.translatable(
                                 "crafttweaker.command.misc.link", new Object[]{CommandUtilities.makeNoticeable("https://docs.blamejared.com")}
                              )
                              .withStyle(ChatFormatting.GREEN),
                           "https://docs.blamejared.com"
                        )
                     );
                     return 1;
                  }
               )
            )
         );
      handler.registerRootCommand("ctgui", Component.translatable("crafttweaker.command.description.docs"), builder -> builder.executes(context -> {
         CommandUtilities.send((CommandSourceStack)context.getSource(), Component.translatable("crafttweaker.command.misc.ctgui"));
         return 1;
      }));
      handler.registerRootCommand("block_info", Component.translatable("crafttweaker.command.description.info.block"), builder -> builder.executes(context -> {
         CommandSourceStack source = (CommandSourceStack)context.getSource();
         ServerPlayer player = source.getPlayerOrException();
         if (IEventHelper.BLOCK_INFO_PLAYERS.contains(player)) {
            IEventHelper.BLOCK_INFO_PLAYERS.remove(player);
            CommandUtilities.send(source, Component.translatable("crafttweaker.command.info.block.deactivated"));
         } else {
            IEventHelper.BLOCK_INFO_PLAYERS.add(player);
            CommandUtilities.send(source, Component.translatable("crafttweaker.command.info.block.activated"));
         }

         return 1;
      }));
      handler.registerRootCommand(
         "entity_info", Component.translatable("crafttweaker.command.description.info.entity"), builder -> builder.executes(context -> {
            CommandSourceStack source = (CommandSourceStack)context.getSource();
            ServerPlayer player = source.getPlayerOrException();
            if (IEventHelper.ENTITY_INFO_PLAYERS.contains(player)) {
               IEventHelper.ENTITY_INFO_PLAYERS.remove(player);
               CommandUtilities.send(source, Component.translatable("crafttweaker.command.info.entity.deactivated"));
            } else {
               IEventHelper.ENTITY_INFO_PLAYERS.add(player);
               CommandUtilities.send(source, Component.translatable("crafttweaker.command.info.entity.activated"));
            }

            return 1;
         })
      );
   }
}
