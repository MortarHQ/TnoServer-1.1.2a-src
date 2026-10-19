package mcjty.incontrol.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import mcjty.incontrol.data.DataStorage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CmdSetNumber {
   public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
      return ((LiteralArgumentBuilder)Commands.literal("setnumber").requires(cs -> cs.hasPermission(2)))
         .then(
            Commands.argument("number", StringArgumentType.word())
               .then(Commands.argument("value", IntegerArgumentType.integer()).executes(CmdSetNumber::setNumber))
         );
   }

   private static int setNumber(CommandContext<CommandSourceStack> context) {
      String number = (String)context.getArgument("number", String.class);
      int value = (Integer)context.getArgument("value", Integer.class);
      DataStorage data = DataStorage.getData(((CommandSourceStack)context.getSource()).getLevel());
      data.setNumber(number, value);
      return 0;
   }
}
