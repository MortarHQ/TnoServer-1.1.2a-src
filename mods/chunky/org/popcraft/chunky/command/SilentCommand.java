package org.popcraft.chunky.command;

import java.util.List;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.util.Translator;

public class SilentCommand implements ChunkyCommand {
   private final Chunky chunky;

   public SilentCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      this.chunky.getConfig().setSilent(!this.chunky.getConfig().isSilent());
      String status = Translator.translate(this.chunky.getConfig().isSilent() ? "enabled" : "disabled");
      sender.sendMessagePrefixed("format_silent", status);
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return List.of();
   }
}
