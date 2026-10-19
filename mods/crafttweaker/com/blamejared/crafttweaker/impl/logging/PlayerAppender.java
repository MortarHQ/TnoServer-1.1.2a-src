package com.blamejared.crafttweaker.impl.logging;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "PlayerAppender", category = "Core", elementType = "appender")
public final class PlayerAppender extends AbstractAppender {
   private static final Map<Level, PlayerAppender.ChatStyle> STYLES = Map.of(
      Level.FATAL,
      new PlayerAppender.ChatStyle(ChatFormatting.DARK_RED, ChatFormatting.RED),
      Level.ERROR,
      new PlayerAppender.ChatStyle(ChatFormatting.DARK_RED, ChatFormatting.RED),
      Level.WARN,
      new PlayerAppender.ChatStyle(ChatFormatting.GOLD, ChatFormatting.YELLOW),
      Level.INFO,
      new PlayerAppender.ChatStyle(ChatFormatting.DARK_GREEN, ChatFormatting.GREEN),
      Level.DEBUG,
      new PlayerAppender.ChatStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.LIGHT_PURPLE),
      Level.TRACE,
      new PlayerAppender.ChatStyle(ChatFormatting.DARK_GRAY, ChatFormatting.GRAY),
      Level.ALL,
      new PlayerAppender.ChatStyle(ChatFormatting.DARK_GREEN, ChatFormatting.GREEN),
      Level.OFF,
      new PlayerAppender.ChatStyle(ChatFormatting.BLACK, ChatFormatting.GREEN)
   );
   private final List<WeakReference<Player>> players = new ArrayList<>();
   private final List<PlayerAppender.LogMessage> previousMessages = new ArrayList<>();

   private PlayerAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
      super(name, filter, layout, ignoreExceptions, properties);
   }

   @PluginFactory
   public static PlayerAppender createAppender(
      @PluginAttribute("name") String name, @PluginElement("Filter") Filter filter, @Nullable @PluginElement("Layout") Layout<? extends Serializable> layout
   ) {
      return new PlayerAppender(name, filter, layout, true, Property.EMPTY_ARRAY);
   }

   public void append(LogEvent event) {
      String message = this.getLayout().toSerializable(event).toString().replaceAll("\r", "").replaceAll("\n\n", "\n");
      PlayerAppender.LogMessage logMessage = new PlayerAppender.LogMessage(event.getLevel(), message);
      Iterator<WeakReference<Player>> iterator = this.players.iterator();

      while (iterator.hasNext()) {
         Player player = iterator.next().get();
         if (player == null) {
            iterator.remove();
         } else {
            this.sendMessage(player, logMessage);
         }
      }

      this.previousMessages.add(logMessage);
   }

   void addPlayerLogger(Player player) {
      this.players.add(new WeakReference<>(player));
      this.previousMessages.forEach(event -> this.sendMessage(player, event));
   }

   void removePlayerLogger(Player player) {
      this.players.removeIf(p -> p.get() == player);
   }

   void clearPreviousMessages() {
      this.previousMessages.clear();
   }

   private void sendMessage(Player player, PlayerAppender.LogMessage message) {
      PlayerAppender.ChatStyle style = STYLES.get(message.level());
      MutableComponent header = Component.literal("[%s]: ".formatted(message.level().name())).setStyle(style.levelStyle());
      String[] split = message.message().split("\n");

      for (int i = 0; i < split.length; i++) {
         Component line = Component.literal(split[i]).setStyle(style.messageStyle());
         if (i == 0) {
            player.sendSystemMessage(header.append(line));
         } else {
            player.sendSystemMessage(line);
         }
      }
   }

   private record ChatStyle(Style levelStyle, Style messageStyle) {
      ChatStyle(ChatFormatting levelStyle, ChatFormatting messageStyle) {
         this(Style.EMPTY.withColor(levelStyle), Style.EMPTY.withColor(messageStyle));
      }
   }

   private record LogMessage(Level level, String message) {
   }
}
