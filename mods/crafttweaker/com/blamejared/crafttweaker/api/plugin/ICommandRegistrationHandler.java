package com.blamejared.crafttweaker.api.plugin;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.function.Consumer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

public interface ICommandRegistrationHandler {
   void registerRootCommand(String var1, MutableComponent var2, ICommandRegistrationHandler.CommandBuilder var3);

   void registerSubCommand(String var1, String var2, MutableComponent var3, ICommandRegistrationHandler.CommandBuilder var4);

   void registerDump(String var1, MutableComponent var2, ICommandRegistrationHandler.CommandBuilder var3);

   @FunctionalInterface
   interface CommandBuilder extends Consumer<LiteralArgumentBuilder<CommandSourceStack>> {
      default void buildCommand(LiteralArgumentBuilder<CommandSourceStack> builder) {
         this.accept(builder);
      }
   }
}
