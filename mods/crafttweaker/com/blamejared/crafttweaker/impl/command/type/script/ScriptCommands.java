package com.blamejared.crafttweaker.impl.command.type.script;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.util.PathUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public final class ScriptCommands {
   private ScriptCommands() {
   }

   public static void registerCommands(ICommandRegistrationHandler handler) {
      handler.registerRootCommand("log", Component.translatable("crafttweaker.command.description.log"), builder -> builder.executes(context -> {
         CommandUtilities.open((CommandSourceStack)context.getSource(), PathUtil.makeRelativeToGameDirectory("logs/crafttweaker.log").toString());
         return 1;
      }));
      handler.registerRootCommand("scripts", Component.translatable("crafttweaker.command.description.script"), builder -> builder.executes(context -> {
         CommandUtilities.open((CommandSourceStack)context.getSource(), PathUtil.makeRelativeToGameDirectory(CraftTweakerConstants.scriptsDir()).toString());
         return 1;
      }));
      handler.registerRootCommand(
         "syntax",
         Component.translatable("crafttweaker.command.description.syntax"),
         builder -> builder.executes(
            context -> {
               CommandSourceStack source = (CommandSourceStack)context.getSource();
               CraftTweakerAPI.getRegistry()
                  .getAllLoaders()
                  .stream()
                  .peek(
                     loader -> CommandUtilities.send(
                        source, Component.translatable("crafttweaker.script.load.start", new Object[]{CommandUtilities.makeNoticeable(loader.name())})
                     )
                  )
                  .map(
                     it -> new ScriptRunConfiguration(
                        it, IScriptLoadSource.find(CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID), ScriptRunConfiguration.RunKind.SYNTAX_CHECK
                     )
                  )
                  .map(it -> CraftTweakerAPI.getScriptRunManager().createScriptRun(it))
                  .forEach(it -> {
                     try {
                        it.execute();
                        CommandUtilities.send(source, Component.translatable("crafttweaker.script.load.end.noerror"));
                     } catch (Throwable e) {
                        CommandUtilities.COMMAND_LOGGER.error("Unable to check for syntax due to an error", e);
                        CommandUtilities.send(source, Component.translatable("crafttweaker.script.load.end.error"));
                     }
                  });
               return 1;
            }
         )
      );
   }
}
