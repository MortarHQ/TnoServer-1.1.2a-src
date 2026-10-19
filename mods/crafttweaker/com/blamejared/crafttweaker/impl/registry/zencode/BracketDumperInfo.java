package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.type.IBracketDumperInfo;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

final class BracketDumperInfo implements IBracketDumperInfo {
   private final String bepHandlerName;
   private final String dumpedFileName;
   private final String subCommandName;
   private final Supplier<Stream<String>> data;

   BracketDumperInfo(String bepHandlerName, String subCommandName, String dumpedFileName, Supplier<Stream<String>> data) {
      this.bepHandlerName = bepHandlerName;
      this.subCommandName = Optional.ofNullable(subCommandName).orElseGet(() -> makePlural(this.bepHandlerName));
      this.dumpedFileName = Optional.ofNullable(dumpedFileName).orElseGet(() -> this.bepHandlerName.toLowerCase(Locale.ENGLISH));
      this.data = data;
   }

   BracketDumperInfo(String name, IBracketParserRegistrationHandler.DumperData data) {
      this(name, data.subCommandName(), data.outputFileName(), data.data());
   }

   private static String makePlural(String s) {
      return !s.endsWith("s") && !s.endsWith("x") ? s + "s" : s;
   }

   @Override
   public String subCommandName() {
      return this.subCommandName;
   }

   @Override
   public MutableComponent description() {
      return Component.translatable("crafttweaker.command.description.dump.info", new Object[]{CommandUtilities.makeNoticeable(this.bepHandlerName)});
   }

   @Override
   public String dumpedFileName() {
      return this.dumpedFileName;
   }

   @Override
   public Stream<String> values() {
      return this.data.get();
   }

   public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      this.values().forEach(bepCall -> CommandUtilities.COMMAND_LOGGER.info("- " + bepCall));
      CommandUtilities.send(
         source,
         Component.translatable("crafttweaker.command.dump.generated", new Object[]{CommandUtilities.makeNoticeable(this.bepHandlerName)})
            .withStyle(ChatFormatting.GREEN)
      );
      CommandUtilities.openLogFile(
         source, Component.translatable("crafttweaker.command.check.log", new Object[]{CommandUtilities.getFormattedLogFile()}).withStyle(ChatFormatting.GREEN)
      );
      return 1;
   }
}
