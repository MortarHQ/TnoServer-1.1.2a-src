package bigchadguys.sellingbin.init;

import bigchadguys.sellingbin.command.Command;
import bigchadguys.sellingbin.command.ReloadCommand;
import com.mojang.brigadier.CommandDispatcher;
import java.util.function.Supplier;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;

public class ModCommands {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext access, CommandSelection environment) {
      new ReloadCommand().register(dispatcher, access, environment);
   }

   private static <T extends Command> T register(
      Supplier<T> supplier, CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext access, CommandSelection environment
   ) {
      T command = (T)supplier.get();
      command.register(dispatcher, access, environment);
      return command;
   }
}
