package org.popcraft.chunky.command;

import java.util.List;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.Selection;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.util.Formatting;
import org.popcraft.chunky.util.Translator;

public class SelectionCommand implements ChunkyCommand {
   private final Chunky chunky;

   public SelectionCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      Selection current = this.chunky.getSelection().build();
      sender.sendMessagePrefixed("format_selection");
      sender.sendMessage("format_selection_world", current.world().getName());
      sender.sendMessage("format_selection_shape", Translator.translate("shape_" + current.shape()));
      sender.sendMessage("format_selection_center", Formatting.number(current.centerX()), Formatting.number(current.centerZ()));
      double radiusX = current.radiusX();
      double radiusZ = current.radiusZ();
      if (radiusX == radiusZ) {
         sender.sendMessage("format_selection_radius", Formatting.number(radiusX));
      } else {
         sender.sendMessage("format_selection_radii", Formatting.number(radiusX), Formatting.number(radiusZ));
      }
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return List.of();
   }
}
