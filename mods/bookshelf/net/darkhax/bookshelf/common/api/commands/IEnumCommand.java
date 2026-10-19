package net.darkhax.bookshelf.common.api.commands;

import com.mojang.brigadier.Command;
import net.minecraft.commands.CommandSourceStack;

public interface IEnumCommand extends Command<CommandSourceStack> {
   String getCommandName();

   default PermissionLevel requiredPermissionLevel() {
      return PermissionLevel.PLAYER;
   }
}
