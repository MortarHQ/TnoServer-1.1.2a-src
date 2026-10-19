package org.popcraft.chunky.command;

import java.util.List;
import java.util.Optional;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.platform.Sender;

public class ConfirmCommand implements ChunkyCommand {
   private final Chunky chunky;

   public ConfirmCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      Optional<Runnable> pendingAction = this.chunky.getPendingAction(sender);
      if (pendingAction.isEmpty()) {
         sender.sendMessagePrefixed("format_confirm");
      } else {
         pendingAction.get().run();
      }
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return List.of();
   }
}
