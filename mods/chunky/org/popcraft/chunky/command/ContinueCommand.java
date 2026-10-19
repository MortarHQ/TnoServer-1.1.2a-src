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

public class ContinueCommand implements ChunkyCommand {
   private final Chunky chunky;

   public ContinueCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      List<GenerationTask> loadTasks;
      if (arguments.size() > 0) {
         Optional<World> world = Input.tryWorld(this.chunky, arguments.joined());
         if (world.isEmpty()) {
            sender.sendMessage("help_continue");
            return;
         }

         loadTasks = this.chunky.getTaskLoader().loadTask(world.get()).map(List::of).orElse(List.of());
      } else {
         loadTasks = this.chunky.getTaskLoader().loadTasks();
      }

      if (loadTasks.stream().allMatch(GenerationTask::isCancelled)) {
         sender.sendMessagePrefixed("format_continue_no_tasks");
      } else {
         Map<String, GenerationTask> generationTasks = this.chunky.getGenerationTasks();
         loadTasks.stream().filter(task -> !task.isCancelled()).forEach(generationTask -> {
            World world = generationTask.getSelection().world();
            if (!generationTasks.containsKey(world.getName())) {
               generationTasks.put(world.getName(), generationTask);
               this.chunky.getScheduler().runTask(generationTask);
               sender.sendMessagePrefixed("format_continue", world.getName());
            } else {
               sender.sendMessagePrefixed("format_started_already", world.getName());
            }
         });
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
