package org.popcraft.chunky.command;

import java.util.List;
import java.util.Map;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.GenerationTask;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.platform.World;

public class ProgressCommand implements ChunkyCommand {
   private final Chunky chunky;

   public ProgressCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      Map<String, GenerationTask> generationTasks = this.chunky.getGenerationTasks();
      if (generationTasks.isEmpty()) {
         sender.sendMessagePrefixed("format_progress_no_tasks");
      } else {
         for (World world : this.chunky.getServer().getWorlds()) {
            if (generationTasks.containsKey(world.getName())) {
               generationTasks.get(world.getName()).getProgress().sendUpdate(sender);
            }
         }
      }
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return List.of();
   }
}
