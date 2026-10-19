package com.natamus.collective_common_fabric.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.natamus.collective_common_fabric.features.PlayerHeadCacheFeature;
import com.natamus.collective_common_fabric.functions.MessageFunctions;
import net.minecraft.class_124;
import net.minecraft.class_1657;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2561;

public class CommandCollective {
   public static void register(CommandDispatcher<class_2168> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247("collective")
                     .requires(commandSourceStack -> commandSourceStack.method_43737()))
                  .executes(command -> showCommandHelp((class_2168)command.getSource())))
               .then(class_2170.method_9247("help").executes(command -> showCommandHelp((class_2168)command.getSource()))))
            .then(class_2170.method_9247("reset").then(class_2170.method_9247("headcache").executes(command -> {
               class_2168 source = (class_2168)command.getSource();
               class_1657 player = source.method_44023();
               if (PlayerHeadCacheFeature.resetPlayerHeadCache()) {
                  MessageFunctions.sendMessage(player, "The player head cache has successfully been reset.", class_124.field_1077);
               }

               return 1;
            })))
      );
   }

   private static int showCommandHelp(class_2168 source) {
      if (source.method_9259(2)) {
         MessageFunctions.sendMessage(source, class_2561.method_43470("Collective Admin Usage:").method_27692(class_124.field_1065), true);
         MessageFunctions.sendMessage(source, " /collective reset headcache", class_124.field_1077);
         MessageFunctions.sendMessage(source, "     Resets Collective's cached player head data, to for example update skins.", class_124.field_1080);
      }

      return 1;
   }
}
