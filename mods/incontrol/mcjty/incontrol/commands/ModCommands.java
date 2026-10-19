package mcjty.incontrol.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ModCommands {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      LiteralCommandNode<CommandSourceStack> commands = dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                                         "incontrol"
                                                      )
                                                      .then(CmdDebug.register(dispatcher)))
                                                   .then(CmdKillMobs.register(dispatcher)))
                                                .then(CmdReload.register(dispatcher)))
                                             .then(CmdShowMobs.register(dispatcher)))
                                          .then(CmdShowStats.register(dispatcher)))
                                       .then(CmdClearStats.register(dispatcher)))
                                    .then(CmdList.register(dispatcher)))
                                 .then(CmdInfo.register(dispatcher)))
                              .then(CmdDays.register(dispatcher)))
                           .then(CmdPhases.register(dispatcher)))
                        .then(CmdNumbers.register(dispatcher)))
                     .then(CmdArea.register(dispatcher)))
                  .then(CmdSetPhase.register(dispatcher)))
               .then(CmdSetNumber.register(dispatcher)))
            .then(CmdClearPhase.register(dispatcher))
      );
      dispatcher.register((LiteralArgumentBuilder)Commands.literal("ctrl").redirect(commands));
   }
}
