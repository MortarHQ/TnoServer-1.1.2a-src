package org.popcraft.chunky.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.GenerationTask;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.platform.World;
import org.popcraft.chunky.util.Input;

public class PauseCommand implements ChunkyCommand {
   private final Chunky chunky;

   public PauseCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      Map<String, GenerationTask> generationTasks = this.chunky.getGenerationTasks();
      if (generationTasks.isEmpty()) {
         sender.sendMessagePrefixed("format_pause_no_tasks");
      } else if (arguments.size() > 0) {
         Optional<World> world = Input.tryWorld(this.chunky, arguments.joined());
         if (!world.isEmpty() && generationTasks.containsKey(world.get().getName())) {
            generationTasks.get(world.get().getName()).stop(false);
            sender.sendMessagePrefixed("format_pause", world.get().getName());
         } else {
            sender.sendMessage("help_pause");
         }
      } else {
         for (GenerationTask generationTask : this.chunky.getGenerationTasks().values()) {
            generationTask.stop(false);
            sender.sendMessagePrefixed("format_pause", generationTask.getSelection().world().getName());
         }
      }
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      if (arguments.size() == 1) {
         List<String> suggestions = new ArrayList<>();
         this.chunky.getServer().getWorlds().forEach(world -> suggestions.add(world.getName()));
         return suggestions;
      } else {
         return List.of();
      }
   }
}
