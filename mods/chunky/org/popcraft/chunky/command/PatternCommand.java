package org.popcraft.chunky.command;

import java.util.List;
import java.util.Optional;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.iterator.PatternType;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.util.Input;
import org.popcraft.chunky.util.Parameter;
import org.popcraft.chunky.util.Translator;

public class PatternCommand implements ChunkyCommand {
   private final Chunky chunky;

   public PatternCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      Optional<String> optionalType = arguments.next().flatMap(Input::tryPattern);
      if (optionalType.isEmpty()) {
         sender.sendMessage("help_pattern");
      } else {
         String type = optionalType.get();
         Optional<String> value = arguments.next();
         if ("csv".equals(type) && value.isEmpty()) {
            sender.sendMessage("help_pattern");
         } else {
            Parameter pattern = Parameter.of(type, value.orElse(null));
            this.chunky.getSelection().pattern(pattern);
            sender.sendMessagePrefixed("format_pattern", Translator.translate("pattern_" + pattern.getType()));
         }
      }
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return arguments.size() == 1 ? PatternType.ALL : List.of();
   }
}
