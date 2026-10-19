package org.popcraft.chunky.command;

import java.util.List;
import java.util.Optional;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.shape.ShapeType;
import org.popcraft.chunky.util.Input;
import org.popcraft.chunky.util.Translator;

public class ShapeCommand implements ChunkyCommand {
   private final Chunky chunky;

   public ShapeCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      Optional<String> inputShape = arguments.next().flatMap(Input::tryShape);
      if (inputShape.isEmpty()) {
         sender.sendMessage("help_shape");
      } else {
         String shape = inputShape.get();
         this.chunky.getSelection().shape(shape);
         sender.sendMessagePrefixed("format_shape", Translator.translate("shape_" + shape));
      }
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return arguments.size() == 1 ? ShapeType.all() : List.of();
   }
}
