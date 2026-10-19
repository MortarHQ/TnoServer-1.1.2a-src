package org.popcraft.chunky.command;

import java.util.List;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.event.command.ReloadCommandEvent;
import org.popcraft.chunky.platform.Config;
import org.popcraft.chunky.platform.Sender;

public class ReloadCommand implements ChunkyCommand {
   private final Chunky chunky;

   public ReloadCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      String type = arguments.next().orElse(null);
      if ("tasks".equals(type)) {
         if (!this.chunky.getGenerationTasks().isEmpty()) {
            sender.sendMessagePrefixed("format_reload_tasks_running");
            return;
         }

         this.chunky.getTaskLoader().reload();
      } else {
         Config config = this.chunky.getServer().getConfig();
         config.reload();
         this.chunky.setLanguage(config.getLanguage());
         this.chunky.getEventBus().call(new ReloadCommandEvent());
      }

      sender.sendMessagePrefixed("format_reload");
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return arguments.size() == 1 ? List.of("config", "tasks") : List.of();
   }
}
