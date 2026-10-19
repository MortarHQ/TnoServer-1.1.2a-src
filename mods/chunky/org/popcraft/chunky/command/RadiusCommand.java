package org.popcraft.chunky.command;

import java.util.List;
import java.util.Optional;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.Selection;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.util.Formatting;
import org.popcraft.chunky.util.Input;

public class RadiusCommand implements ChunkyCommand {
   private final Chunky chunky;

   public RadiusCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      Optional<String> newX = arguments.next();
      Optional<Integer> signX = newX.flatMap(Input::trySign);
      Optional<Double> newRadiusX = newX.<String>map(x -> signX.isPresent() ? x.substring(1) : x).flatMap(Input::tryDoubleSuffixed);
      if (!newRadiusX.isEmpty() && !(newRadiusX.get() < 0.0) && !Input.isPastWorldLimit(newRadiusX.get())) {
         Selection current = this.chunky.getSelection().build();
         double radiusX = signX.<Double>map(sign -> current.radiusX() + sign.intValue() * newRadiusX.get()).orElseGet(newRadiusX::get);
         if (!(radiusX < 0.0) && !Input.isPastWorldLimit(radiusX)) {
            Optional<String> newZ = arguments.next();
            if (newZ.isPresent()) {
               Optional<Integer> signZ = newZ.flatMap(Input::trySign);
               Optional<Double> newRadiusZ = newZ.<String>map(z -> signZ.isPresent() ? z.substring(1) : z).flatMap(Input::tryDoubleSuffixed);
               if (newRadiusZ.isEmpty() || newRadiusZ.get() < 0.0 || Input.isPastWorldLimit(newRadiusZ.get())) {
                  sender.sendMessage("help_radius");
                  return;
               }

               double radiusZ = signZ.<Double>map(sign -> current.radiusZ() + sign.intValue() * newRadiusZ.get()).orElseGet(newRadiusZ::get);
               if (radiusZ < 0.0 || Input.isPastWorldLimit(radiusZ)) {
                  sender.sendMessage("help_radius");
                  return;
               }

               this.chunky.getSelection().radiusX(radiusX).radiusZ(radiusZ);
               sender.sendMessagePrefixed("format_radii", Formatting.number(radiusX), Formatting.number(radiusZ));
            } else {
               this.chunky.getSelection().radius(radiusX);
               sender.sendMessagePrefixed("format_radius", Formatting.number(radiusX));
            }
         } else {
            sender.sendMessage("help_radius");
         }
      } else {
         sender.sendMessage("help_radius");
      }
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return List.of();
   }
}
