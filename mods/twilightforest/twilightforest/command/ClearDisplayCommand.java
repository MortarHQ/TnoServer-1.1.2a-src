package twilightforest.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.Component;
import twilightforest.util.DisplayUtil;

@Component
public class ClearDisplayCommand {
   @Autowired
   private DisplayUtil displayUtil;

   public LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("clear_display").executes(this::clearDisplayPieces);
   }

   private int clearDisplayPieces(CommandContext<CommandSourceStack> context) {
      MinecraftServer server = ((CommandSourceStack)context.getSource()).getServer();

      for (String command : List.of(
         "kill @e[type=minecraft:text_display,tag=" + "twilightforest_debug_display" + "]",
         "kill @e[type=minecraft:block_display,tag=" + "twilightforest_debug_display" + "]"
      )) {
         server.getCommands().performCommand(server.getCommands().getDispatcher().parse(command, (CommandSourceStack)context.getSource()), command);
      }

      return 0;
   }
}
