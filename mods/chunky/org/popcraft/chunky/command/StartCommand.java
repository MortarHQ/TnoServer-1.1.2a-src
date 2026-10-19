package org.popcraft.chunky.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.GenerationTask;
import org.popcraft.chunky.Selection;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.platform.World;
import org.popcraft.chunky.shape.ShapeType;
import org.popcraft.chunky.util.Formatting;
import org.popcraft.chunky.util.Input;
import org.popcraft.chunky.util.Translator;

public class StartCommand implements ChunkyCommand {
   private final Chunky chunky;

   public StartCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      if (arguments.size() > 0) {
         Optional<World> world = arguments.next().flatMap(arg -> Input.tryWorld(this.chunky, arg));
         if (!world.isPresent()) {
            sender.sendMessage("help_start");
            return;
         }

         this.chunky.getSelection().world(world.get());
      }

      if (arguments.size() > 1) {
         Optional<String> shape = arguments.next().flatMap(Input::tryShape);
         if (!shape.isPresent()) {
            sender.sendMessage("help_start");
            return;
         }

         this.chunky.getSelection().shape(shape.get());
      }

      if (arguments.size() > 2) {
         Optional<Double> centerX = arguments.next().flatMap(Input::tryDoubleSuffixed).filter(c -> !Input.isPastWorldLimit(c));
         Optional<Double> centerZ = arguments.next().flatMap(Input::tryDoubleSuffixed).filter(c -> !Input.isPastWorldLimit(c));
         if (!centerX.isPresent() || !centerZ.isPresent()) {
            sender.sendMessage("help_start");
            return;
         }

         this.chunky.getSelection().center(centerX.get(), centerZ.get());
      }

      if (arguments.size() > 4) {
         Optional<Double> radiusX = arguments.next().flatMap(Input::tryDoubleSuffixed).filter(r -> r >= 0.0 && !Input.isPastWorldLimit(r));
         if (!radiusX.isPresent()) {
            sender.sendMessage("help_start");
            return;
         }

         this.chunky.getSelection().radius(radiusX.get());
      }

      if (arguments.size() > 5) {
         Optional<Double> radiusZ = arguments.next().flatMap(Input::tryDoubleSuffixed).filter(r -> r >= 0.0 && !Input.isPastWorldLimit(r));
         if (!radiusZ.isPresent()) {
            sender.sendMessage("help_start");
            return;
         }

         this.chunky.getSelection().radiusZ(radiusZ.get());
      }

      Selection current = this.chunky.getSelection().build();
      if (this.chunky.getGenerationTasks().containsKey(current.world().getName())) {
         sender.sendMessagePrefixed("format_started_already", current.world().getName());
      } else if (current.radiusX() > this.chunky.getLimit()) {
         sender.sendMessagePrefixed("format_start_limit", Formatting.number(this.chunky.getLimit()));
      } else {
         Runnable startAction = () -> {
            GenerationTask generationTask = new GenerationTask(this.chunky, current);
            this.chunky.getGenerationTasks().put(current.world().getName(), generationTask);
            this.chunky.getScheduler().runTask(generationTask);
            sender.sendMessagePrefixed(
               "format_start",
               current.world().getName(),
               Translator.translate("shape_" + current.shape()),
               Formatting.number(current.centerX()),
               Formatting.number(current.centerZ()),
               Formatting.radius(current)
            );
         };
         if (this.chunky.getTaskLoader().loadTask(current.world()).filter(task -> !task.isCancelled()).isPresent()) {
            this.chunky.setPendingAction(sender, startAction);
            sender.sendMessagePrefixed("format_start_confirm", "/chunky continue", "/chunky confirm");
         } else {
            startAction.run();
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
         return arguments.size() == 2 ? ShapeType.all() : List.of();
      }
   }
}
