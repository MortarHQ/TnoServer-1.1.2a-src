package org.popcraft.chunky.command;

import java.util.ArrayList;
import java.util.List;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.platform.Player;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.platform.World;
import org.popcraft.chunky.util.Input;

public class WorldCommand implements ChunkyCommand {
   private final Chunky chunky;

   public WorldCommand(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public void execute(Sender sender, CommandArguments arguments) {
      World world;
      if (arguments.size() == 0 && sender instanceof Player player) {
         world = player.getWorld();
      } else {
         world = Input.tryWorld(this.chunky, arguments.joined()).orElse(null);
      }

      if (world == null) {
         sender.sendMessage("help_world");
      } else {
         this.chunky.getSelection().world(world);
         sender.sendMessagePrefixed("format_world", world.getName());
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
