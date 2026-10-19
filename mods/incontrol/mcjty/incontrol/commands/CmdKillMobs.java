package mcjty.incontrol.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import mcjty.incontrol.InControl;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CmdKillMobs implements Command<CommandSourceStack> {
   private static final CmdKillMobs CMD = new CmdKillMobs();

   public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
      return ((LiteralArgumentBuilder)Commands.literal("kill").requires(cs -> cs.hasPermission(2)))
         .then(Commands.argument("type", StringArgumentType.word()).executes(CMD));
   }

   public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      String type = (String)context.getArgument("type", String.class);
      if (type != null && !type.trim().isEmpty()) {
         ResourceKey<Level> dimension = player.getCommandSenderWorld().dimension();
         boolean all = "all".equals(type);
         boolean passive = "passive".equals(type);
         boolean hostile = "hostile".equals(type);
         boolean entity = "entity".equals(type);
         ServerLevel worldServer = player.getCommandSenderWorld().getServer().getLevel(dimension);
         List<? extends Entity> entities = worldServer.getEntities(CmdList.ANY_TYPE, input -> {
            if (all) {
               return !(input instanceof Player);
            } else if (passive) {
               return input instanceof Animal && !(input instanceof Enemy);
            } else if (hostile) {
               return input instanceof Enemy;
            } else if (!entity) {
               String id = BuiltInRegistries.ENTITY_TYPE.getKey(input.getType()).toString();
               return type.equals(id);
            } else {
               return !(input instanceof Animal) && !(input instanceof Player);
            }
         });

         for (Entity e : entities) {
            e.setRemoved(RemovalReason.KILLED);
         }

         player.sendSystemMessage(Component.literal(ChatFormatting.YELLOW + "Removed " + entities.size() + " entities!"));
         return 0;
      } else {
         player.sendSystemMessage(
            Component.literal(ChatFormatting.RED + "Use 'all', 'passive', 'hostile' or name of the mob followed by optional dimension id")
         );
         InControl.setup.getLogger().error("Use 'all', 'passive', 'hostile', 'entity' or name of the mob followed by optional dimension id");
         return 0;
      }
   }
}
