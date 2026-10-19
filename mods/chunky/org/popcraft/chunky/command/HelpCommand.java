package org.popcraft.chunky.command;

import java.util.ArrayList;
import java.util.List;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.util.Input;
import org.popcraft.chunky.util.Translator;

public class HelpCommand implements ChunkyCommand {
   private final Chunky chunky;
   private final List<String> helpCommands = List.of(
      "start",
      "pause",
      "continue",
      "cancel",
      "world",
      "worldborder",
      "center",
      "spawn",
      "radius",
      "corners",
      "shape",
      "pattern",
      "silent",
      "quiet",
      "trim",
      "selection",
      "progress",
      "border",
      "reload"
   );

   public HelpCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      List<String> visibleCommands = new ArrayList<>();

      for (String command : this.helpCommands) {
         if (this.chunky.getCommands().containsKey(command)) {
            visibleCommands.add(command);
         }
      }

      int visibleCommandCount = visibleCommands.size();
      StringBuilder help = new StringBuilder();
      int pageIndexLast = visibleCommandCount / 8;
      int pageIndex = (arguments.size() < 1 ? 0 : Math.max(0, arguments.next().flatMap(Input::tryInteger).orElse(1) - 1)) % (pageIndexLast + 1);
      int helpIndexFirst;
      int helpIndexLast;
      if (sender.isPlayer()) {
         helpIndexFirst = 8 * pageIndex;
         helpIndexLast = Math.min(helpIndexFirst + 8, visibleCommandCount);
      } else {
         helpIndexFirst = 0;
         helpIndexLast = visibleCommandCount;
      }

      for (int i = helpIndexFirst; i < helpIndexLast; i++) {
         help.append('\n').append(Translator.translate("help_" + visibleCommands.get(i)));
      }

      if (sender.isPlayer() && pageIndex != pageIndexLast) {
         help.append('\n').append(Translator.translate("help_more", "/chunky help " + (pageIndex + 2)));
      }

      sender.sendMessage("help_menu", help.toString());
   }

   @Override
   public List<String> suggestions(CommandArguments arguments) {
      return List.of();
   }
}
