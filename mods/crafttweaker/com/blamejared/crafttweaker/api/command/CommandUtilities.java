package com.blamejared.crafttweaker.api.command;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.impl.network.packet.ClientBoundCopyPacket;
import com.blamejared.crafttweaker.impl.network.packet.ClientBoundSendOpenFileMessagePacket;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;
import org.apache.logging.log4j.Logger;

public final class CommandUtilities {
   public static final Logger COMMAND_LOGGER = CommonLoggers.commands();

   private CommandUtilities() {
   }

   public static void sendCopyingAndCopy(CommandSourceStack source, MutableComponent component, String toCopy) {
      sendCopying(source, component, toCopy);
      copy(source, toCopy);
   }

   public static void sendCopying(CommandSourceStack source, MutableComponent component, String toCopy) {
      send(source, copy(component, toCopy));
   }

   public static void send(CommandSourceStack source, Component component) {
      source.sendSuccess(() -> component, true);
      if (!component.getString().isBlank()) {
         COMMAND_LOGGER.info(component.getString());
      }
   }

   public static void copy(CommandSourceStack source, String toCopy) {
      if (source.isPlayer()) {
         Services.NETWORK.sendPacket(source.getPlayer(), new ClientBoundCopyPacket(toCopy));
      }
   }

   public static void open(CommandSourceStack source, String path) {
      MutableComponent component = Component.translatable(
         "crafttweaker.command.click.open", new Object[]{Component.literal(path).withStyle(ChatFormatting.GOLD)}
      );
      open(source, component, component, path);
   }

   public static void openLogFile(CommandSourceStack source, Component chat, Component hover) {
      open(source, chat, hover, "logs/crafttweaker.log");
   }

   public static void openLogFile(CommandSourceStack source, Component chat) {
      MutableComponent hover = Component.translatable(
         "crafttweaker.command.click.open", new Object[]{Component.literal("logs/crafttweaker.log").withStyle(ChatFormatting.GOLD)}
      );
      open(source, chat, hover, "logs/crafttweaker.log");
   }

   public static void openLogfile(CommandSourceStack source) {
      open(source, "logs/crafttweaker.log");
   }

   public static void open(CommandSourceStack source, Component chat, Component hover, String path) {
      if (source.isPlayer()) {
         Services.NETWORK.sendPacket(source.getPlayer(), new ClientBoundSendOpenFileMessagePacket(chat, hover, path));
      } else {
         send(source, chat);
      }
   }

   public static Component copy(MutableComponent base, String toCopy) {
      Style style = base.getStyle();
      style = style.withHoverEvent(
         new HoverEvent(
            Action.SHOW_TEXT, Component.translatable("crafttweaker.command.click.copy", new Object[]{Component.literal(toCopy).withStyle(ChatFormatting.GOLD)})
         )
      );
      style = style.withClickEvent(new ClickEvent(net.minecraft.network.chat.ClickEvent.Action.COPY_TO_CLIPBOARD, toCopy));
      return base.setStyle(style);
   }

   public static Component run(MutableComponent base, String command) {
      Style style = base.getStyle();
      style = style.withHoverEvent(
         new HoverEvent(
            Action.SHOW_TEXT, Component.translatable("crafttweaker.command.click.run", new Object[]{Component.literal(command).withStyle(ChatFormatting.GOLD)})
         )
      );
      style = style.withClickEvent(new ClickEvent(net.minecraft.network.chat.ClickEvent.Action.RUN_COMMAND, command));
      return base.setStyle(style);
   }

   public static Component openingUrl(MutableComponent base, String url) {
      MutableComponent component = Component.translatable(
         "crafttweaker.command.click.goto", new Object[]{Component.literal(url).withStyle(ChatFormatting.GOLD)}
      );
      return base.withStyle(
         style -> style.withHoverEvent(new HoverEvent(Action.SHOW_TEXT, component))
            .withClickEvent(new ClickEvent(net.minecraft.network.chat.ClickEvent.Action.OPEN_URL, url))
      );
   }

   public static MutableComponent getFormattedLogFile() {
      return Component.literal("logs/crafttweaker.log").withStyle(ChatFormatting.AQUA);
   }

   public static MutableComponent makeNoticeable(MutableComponent text) {
      return text.withStyle(ChatFormatting.YELLOW);
   }

   public static MutableComponent makeNoticeable(String text) {
      return makeNoticeable(Component.literal(text));
   }
}
