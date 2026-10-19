package org.popcraft.chunky.command;

import java.util.List;
import java.util.Optional;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.platform.util.Location;
import org.popcraft.chunky.util.Formatting;
import org.popcraft.chunky.util.Input;

public class CenterCommand implements ChunkyCommand {
   private final Chunky chunky;

   public CenterCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      Optional<Double> newX = arguments.next().flatMap(Input::tryDoubleSuffixed);
      Optional<Double> newZ = arguments.next().flatMap(Input::tryDoubleSuffixed);
      double centerX;
      double centerZ;
      if (newX.isEmpty() && newZ.isEmpty()) {
         Location coordinate = sender.getLocation();
         centerX = coordinate.getX();
         centerZ = coordinate.getZ();
      } else {
         if (!newX.isPresent() || !newZ.isPresent()) {
            sender.sendMessage("help_center");
            return;
         }

         centerX = newX.get();
         centerZ = newZ.get();
      }

      if (!Input.isPastWorldLimit(centerX) && !Input.isPastWorldLimit(centerZ)) {
         this.chunky.getSelection().center(centerX, centerZ);
         sender.sendMessagePrefixed("format_center", Formatting.number(centerX), Formatting.number(centerZ));
      } else {
         sender.sendMessage("help_center");
      }
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return List.of();
   }
}
