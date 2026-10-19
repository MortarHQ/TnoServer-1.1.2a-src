package com.blamejared.crafttweaker.gametest.logging.appender;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.gametest.framework.GameTestAssertException;
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
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "PlayerAppender", category = "Core", elementType = "appender")
public class GameTestLoggerAppender extends AbstractAppender {
   private final List<GameTestLoggerAppender.LogMessage> messages = new LinkedList<>();

   protected GameTestLoggerAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
      super(name, filter, layout, ignoreExceptions, properties);
   }

   public void append(LogEvent event) {
      String originalMessage = ((PatternLayout)this.getLayout()).toSerializable(event);
      String message = originalMessage.replaceAll(System.lineSeparator(), "");
      this.messages.add(new GameTestLoggerAppender.LogMessage(message, originalMessage, event.getLevel()));
   }

   @PluginFactory
   public static GameTestLoggerAppender createAppender(
      @PluginAttribute("name") String name, @PluginElement("Filter") Filter filter, @Nullable @PluginElement("Layout") Layout<? extends Serializable> layout
   ) {
      return new GameTestLoggerAppender(name, filter, layout, true, Property.EMPTY_ARRAY);
   }

   public void claim() {
      this.messages.clear();
   }

   public GameTestLoggerAppender.QueryableLog query() {
      return new GameTestLoggerAppender.QueryableLog(List.copyOf(this.messages));
   }

   private record LogMessage(String message, String actualMessage, Level level) {
      static GameTestLoggerAppender.LogMessage mock(String message) {
         return new GameTestLoggerAppender.LogMessage(message, message, Level.INFO);
      }
   }

   public static final class QueryableLog {
      private final List<GameTestLoggerAppender.LogMessage> log;

      QueryableLog(List<GameTestLoggerAppender.LogMessage> log) {
         this.log = log;
      }

      public static GameTestLoggerAppender.QueryableLog mock(List<String> mockMessages) {
         return new GameTestLoggerAppender.QueryableLog(Lists.transform(mockMessages, GameTestLoggerAppender.LogMessage::mock));
      }

      public void assertNoErrors() {
         if (this.log.stream().anyMatch(logMessage -> logMessage.level() == Level.ERROR)) {
            this.dump();
            throw new GameTestAssertException("Expected no errors but errors were found!");
         }
      }

      public void assertNoWarnings() {
         if (this.log.stream().anyMatch(logMessage -> logMessage.level() == Level.WARN)) {
            this.dump();
            throw new GameTestAssertException("Expected no warnings but errors were found!");
         }
      }

      public void assertOutput(int index, String message) {
         this.assertOutputFor(index, () -> "equal '" + message + "'", Objects.requireNonNull(message)::equals);
      }

      public void assertOutputContains(int index, String message) {
         this.assertOutputFor(index, () -> "contain '" + message + "'", it -> it.contains(message));
      }

      public void dump() {
         for (int i = 0; i < this.log.size(); i++) {
            CraftTweakerCommon.logger().info("{}: {} '{}'", i, this.log.get(i).level, this.log.get(i).actualMessage);
         }
      }

      private void assertOutputFor(int index, Supplier<String> what, Predicate<String> checker) {
         if (index >= this.log.size()) {
            throw new GameTestAssertException("Expected line " + index + " to " + what.get() + ", but the line does not exist");
         }

         GameTestLoggerAppender.LogMessage logMessage = this.log.get(index);
         if (logMessage == null) {
            throw new GameTestAssertException("Expected line " + index + " to " + what.get() + ", but the line was logged as 'null': this is critical!");
         }

         String message = logMessage.message();
         if (!checker.test(message)) {
            throw new GameTestAssertException("Expected line " + index + " to " + what.get() + ", but found '" + message + "'");
         }
      }
   }
}
