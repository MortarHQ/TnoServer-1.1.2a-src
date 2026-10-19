package org.crypticdev.stextras.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.crypticdev.stextras.command.player.PlayerCommands;
import org.crypticdev.stextras.command.quest.QuestCommands;
import org.crypticdev.stextras.command.quest.RacePrestigeCommands;
import org.crypticdev.stextras.command.quest.RequiredCommands;
import org.crypticdev.stextras.command.system.DebugCommands;
import org.crypticdev.stextras.command.system.ScheduleCommands;
import org.crypticdev.stextras.command.system.WikiCommands;

public final class BaseCommands {
   public static void register() {
      CommandRegistrationEvent.EVENT.register((CommandRegistrationEvent)(dispatcher, registryAccess, selection) -> registerCommands(dispatcher));
   }

   public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                 "stextras"
                              )
                              .then(ScheduleCommands.build()))
                           .then(RequiredCommands.build()))
                        .then(PlayerCommands.build()))
                     .then(QuestCommands.build()))
                  .then(RacePrestigeCommands.build()))
               .then(DebugCommands.build()))
            .then(WikiCommands.build())
      );
   }

   private BaseCommands() {
   }
}
