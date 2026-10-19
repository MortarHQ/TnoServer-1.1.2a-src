package org.popcraft.chunky.command;

import java.util.List;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.Selection;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.util.Formatting;

public class SpawnCommand implements ChunkyCommand {
   private final Chunky chunky;

   public SpawnCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      this.chunky.getSelection().spawn();
      Selection current = this.chunky.getSelection().build();
      sender.sendMessagePrefixed("format_center", Formatting.number(current.centerX()), Formatting.number(current.centerZ()));
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return List.of();
   }
}
