package org.popcraft.chunky.command;

import java.util.List;
import org.popcraft.chunky.platform.Sender;

public interface ChunkyCommand {
   void execute(Sender var1, CommandArguments var2);

   List<String> suggestions(CommandArguments var1);
}
