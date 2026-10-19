package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.impl.command.CtCommands;
import java.util.function.Consumer;
import net.minecraft.network.chat.MutableComponent;

final class CommandRegistrationHandler implements ICommandRegistrationHandler {
   private CommandRegistrationHandler() {
   }

   public static void gather(Consumer<ICommandRegistrationHandler> consumer) {
      CommandRegistrationHandler handler = new CommandRegistrationHandler();
      consumer.accept(handler);

      try {
         CtCommands.get().finalizeCommands();
      } catch (IllegalArgumentException e) {
         throw new IllegalStateException("Command finalization failed", e);
      }
   }

   @Override
   public void registerRootCommand(String commandId, MutableComponent description, ICommandRegistrationHandler.CommandBuilder builder) {
      CtCommands.get().registerCommand(commandId, description, builder);
   }

   @Override
   public void registerSubCommand(String parentCommand, String commandId, MutableComponent description, ICommandRegistrationHandler.CommandBuilder builder) {
      CtCommands.get().registerSubCommand(parentCommand, commandId, description, builder);
   }

   @Override
   public void registerDump(String dumpId, MutableComponent description, ICommandRegistrationHandler.CommandBuilder builder) {
      CtCommands.get().registerDump(dumpId, description, builder);
   }
}
