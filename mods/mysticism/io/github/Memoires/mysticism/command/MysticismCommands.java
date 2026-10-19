package io.github.Memoires.mysticism.command;

import io.github.Memoires.mysticism.command.edit.MysticEditStatCommand;
import io.github.Memoires.mysticism.command.edit.MysticResetCommand;
import io.github.Memoires.mysticism.command.get.MysticGetStatCommand;
import io.github.manasmods.manascore.command.api.Command;
import io.github.manasmods.manascore.command.api.CommandRegistry;
import java.text.DecimalFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;

public class MysticismCommands {
   public static void init() {
      CommandRegistry.registerCommand(MysticismCommands.MysticismCommand.class);
   }

   public static void sendSuccess(CommandSourceStack stack, MutableComponent component) {
      stack.sendSuccess(() -> component, false);
   }

   public static void sendSuccess(CommandSourceStack stack, MutableComponent component, ChatFormatting formatting) {
      stack.sendSuccess(() -> component.withStyle(formatting), false);
   }

   public static void sendSuccess(CommandSourceStack stack, Entity entity, MutableComponent component) {
      sendSuccess(stack, entity, component, ChatFormatting.DARK_GREEN);
   }

   public static void sendSuccess(CommandSourceStack stack, Entity entity, MutableComponent component, ChatFormatting formatting) {
      if (stack.getPlayer() != entity) {
         entity.sendSystemMessage(component);
      }

      stack.sendSuccess(() -> component.withStyle(formatting), true);
   }

   public static Component getShortenedNumber(double value) {
      if (value < 1000000.0) {
         return Component.literal(new DecimalFormat("#").format(value));
      }

      DecimalFormat decimal = new DecimalFormat("#.##");
      return value < 1.0E9
         ? Component.translatable("tensura.main_menu.million_index", new Object[]{decimal.format(value / 1000000.0)})
         : Component.translatable("tensura.main_menu.billion_index", new Object[]{decimal.format(value / 1.0E9)});
   }

   @Command(
      value = "mysticism",
      subCommands = {
            MysticismCommands.MysticismCommand.MysticEditCommand.class, MysticismCommands.MysticismCommand.MysticGetCommand.class, MysticResetCommand.class
      }
   )
   public static class MysticismCommand {
      @Command(value = "edit", subCommands = MysticEditStatCommand.class)
      public static class MysticEditCommand {
      }

      @Command(value = "get", subCommands = MysticGetStatCommand.class)
      public static class MysticGetCommand {
      }
   }
}
