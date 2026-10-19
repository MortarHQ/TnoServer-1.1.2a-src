package org.popcraft.chunky.command;

import java.util.List;
import java.util.Optional;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.util.Input;

public class QuietCommand implements ChunkyCommand {
   private final Chunky chunky;

   public QuietCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      Optional<Integer> newQuiet = arguments.next().flatMap(Input::tryInteger);
      if (newQuiet.isEmpty()) {
         sender.sendMessage("help_quiet");
      } else {
         int quietInterval = Math.max(0, newQuiet.get());
         this.chunky.getConfig().setUpdateInterval(quietInterval);
         sender.sendMessagePrefixed("format_quiet", quietInterval);
      }
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return List.of();
   }
}
