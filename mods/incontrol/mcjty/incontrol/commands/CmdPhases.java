package mcjty.incontrol.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import mcjty.incontrol.data.DataStorage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CmdPhases {
   public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
      return ((LiteralArgumentBuilder)Commands.literal("phases").requires(cs -> cs.hasPermission(2))).executes(CmdPhases::showPhases);
   }

   private static int showPhases(CommandContext<CommandSourceStack> context) {
      DataStorage data = DataStorage.getData(((CommandSourceStack)context.getSource()).getLevel());
      String phases = "";

      for (String phase : data.getPhases()) {
         phases = phases + phase + " ";
      }

      String finalPhases = phases;
      ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Current phases: " + finalPhases), false);
      return 0;
   }
}
