package com.blamejared.crafttweaker.impl.command;

import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.impl.command.type.HelpCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.TreeMap;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.network.chat.MutableComponent;

public final class CtCommands {
   private static final CtCommands INSTANCE = new CtCommands();
   private final Map<String, CommandImpl> commands = new TreeMap<>(String::compareTo);
   private final Map<String, CommandImpl> commandsView = Collections.unmodifiableMap(this.commands);
   private final Queue<CtCommands.ChildData> lazyData = new LinkedList<>();

   private CtCommands() {
   }

   public static CtCommands get() {
      return INSTANCE;
   }

   public void registerCommand(String id, MutableComponent desc, ICommandRegistrationHandler.CommandBuilder builder) {
      if (this.commands.containsKey(id)) {
         throw new IllegalStateException("Duplicate root command " + id);
      }

      this.commands.put(id, new CommandImpl(id, desc, builder));
   }

   public void registerSubCommand(String parent, String id, MutableComponent desc, ICommandRegistrationHandler.CommandBuilder builder) {
      CommandImpl parentCommand = this.commands.get(parent);
      if (parentCommand == null) {
         this.lazyData.add(new CtCommands.ChildData(parent, id, desc, builder));
      } else {
         parentCommand.registerSubCommand(new CommandImpl(id, desc, builder));
      }
   }

   public void registerDump(String dumpId, MutableComponent description, ICommandRegistrationHandler.CommandBuilder builder) {
      this.lazyData.add(new CtCommands.ChildData("dump", dumpId, description, builder));
   }

   public void finalizeCommands() {
      this.computeLazyData(this.lazyData);
   }

   public void registerCommandsTo(CommandDispatcher<CommandSourceStack> dispatcher, CommandSelection environment) {
      LiteralCommandNode<CommandSourceStack> root = Commands.literal("ct").build();
      LiteralArgumentBuilder<CommandSourceStack> aliasedRoot = Commands.literal("crafttweaker");
      dispatcher.getRoot().addChild(root);
      dispatcher.getRoot().addChild(((LiteralArgumentBuilder)aliasedRoot.redirect(root)).build());
      HelpCommand.registerCommandIfRequired(this);
      this.commands.forEach((s, command) -> this.registerCommandTo(root, command));
   }

   private void computeLazyData(Queue<CtCommands.ChildData> dataQueue) {
      while (!dataQueue.isEmpty()) {
         CtCommands.ChildData data = dataQueue.remove();
         CommandImpl parent = Objects.requireNonNull(this.commands.get(data.parent()), "Unknown parent command " + data.parent());
         if (parent.subCommands().containsKey(data.id())) {
            throw new IllegalArgumentException("Duplicated subcommand for parent " + parent.name() + ": " + data.id());
         }

         parent.registerSubCommand(new CommandImpl(data.id(), data.desc(), data.builder()));
      }
   }

   private void registerCommandTo(LiteralCommandNode<CommandSourceStack> root, CommandImpl command) {
      LiteralArgumentBuilder<CommandSourceStack> literalCommand = Commands.literal(command.name());
      command.subCommands().forEach((name, subCommand) -> this.registerSubCommandTo(literalCommand, subCommand));
      command.register(literalCommand);
      root.addChild(literalCommand.build());
   }

   private void registerSubCommandTo(LiteralArgumentBuilder<CommandSourceStack> root, CommandImpl command) {
      LiteralArgumentBuilder<CommandSourceStack> literalCommand = Commands.literal(command.name());
      command.subCommands().forEach((name, subCommand) -> this.registerSubCommandTo(literalCommand, subCommand));
      command.register(literalCommand);
      root.then(literalCommand);
   }

   public Map<String, CommandImpl> commands() {
      return this.commandsView;
   }

   private record ChildData(String parent, String id, MutableComponent desc, ICommandRegistrationHandler.CommandBuilder builder) {
   }
}
