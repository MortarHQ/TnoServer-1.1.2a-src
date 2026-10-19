package bigchadguys.sellingbin.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;

public abstract class Command {
   public abstract void register(CommandDispatcher<CommandSourceStack> var1, CommandBuildContext var2, CommandSelection var3);
}
