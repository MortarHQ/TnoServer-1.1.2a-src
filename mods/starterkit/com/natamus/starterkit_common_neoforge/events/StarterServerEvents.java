package com.natamus.starterkit_common_neoforge.events;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.natamus.starterkit_common_neoforge.config.ConfigHandler;
import com.natamus.starterkit_common_neoforge.functions.StarterDataFunctions;
import com.natamus.starterkit_common_neoforge.functions.StarterGearFunctions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class StarterServerEvents {
   public static void onServerStarting(MinecraftServer minecraftServer) {
      StarterDataFunctions.init(minecraftServer);
   }

   public static void onSpawn(Level level, Entity entity) {
      if (!level.isClientSide) {
         if (entity instanceof Player player) {
            if (!player.getTags().contains("collective.firstJoin.starterkit")) {
               StarterGearFunctions.initStarterKitHandle(level, player, null);
            }
         }
      }
   }

   public static void onCommand(String string, ParseResults<CommandSourceStack> parse) {
      if (ConfigHandler.enableFTBIslandCreateCompatibility) {
         CommandContextBuilder<CommandSourceStack> context = parse.getContext();
         Command<CommandSourceStack> command = context.getCommand();
         if (command != null) {
            String cmdstr = command.toString().toLowerCase();
            if (cmdstr.contains("ftbteamislands.commands.createislandcommand")) {
               CommandSourceStack source = (CommandSourceStack)context.getSource();
               if (source.getEntity() instanceof Player player) {
                  Level level = player.level();
                  if (!level.isClientSide) {
                     StarterGearFunctions.initStarterKitHandle(level, player, null);
                  }
               }
            }
         }
      }
   }
}
