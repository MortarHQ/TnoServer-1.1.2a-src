package com.natamus.starterkit_common_fabric.events;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.natamus.starterkit_common_fabric.config.ConfigHandler;
import com.natamus.starterkit_common_fabric.functions.StarterDataFunctions;
import com.natamus.starterkit_common_fabric.functions.StarterGearFunctions;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2168;
import net.minecraft.server.MinecraftServer;

public class StarterServerEvents {
   public static void onServerStarting(MinecraftServer minecraftServer) {
      StarterDataFunctions.init(minecraftServer);
   }

   public static void onSpawn(class_1937 level, class_1297 entity) {
      if (!level.field_9236) {
         if (entity instanceof class_1657 player) {
            if (!player.method_5752().contains("collective.firstJoin.starterkit")) {
               StarterGearFunctions.initStarterKitHandle(level, player, null);
            }
         }
      }
   }

   public static void onCommand(String string, ParseResults<class_2168> parse) {
      if (ConfigHandler.enableFTBIslandCreateCompatibility) {
         CommandContextBuilder<class_2168> context = parse.getContext();
         Command<class_2168> command = context.getCommand();
         if (command != null) {
            String cmdstr = command.toString().toLowerCase();
            if (cmdstr.contains("ftbteamislands.commands.createislandcommand")) {
               class_2168 source = (class_2168)context.getSource();
               if (source.method_9228() instanceof class_1657 player) {
                  class_1937 level = player.method_37908();
                  if (!level.field_9236) {
                     StarterGearFunctions.initStarterKitHandle(level, player, null);
                  }
               }
            }
         }
      }
   }
}
