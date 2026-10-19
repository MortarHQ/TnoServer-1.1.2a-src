package com.blamejared.crafttweaker.impl.logging;

import java.util.Arrays;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.EntryMessage;
import org.apache.logging.log4j.message.FlowMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

final class ForwardingLogger implements Logger {
   private final String name;
   private final Logger[] loggers;

   private ForwardingLogger(String name, Logger[] loggers) {
      this.name = name;
      this.loggers = loggers;
   }

   static Logger of(String name, Logger... delegates) {
      if (delegates.length == 0) {
         throw new IllegalArgumentException("Not enough delegates");
      } else {
         return delegates.length == 1 ? delegates[0] : new ForwardingLogger(name, Arrays.copyOf(delegates, delegates.length));
      }
   }

   public void catching(Level level, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.catching(level, throwable);
      }
   }

   public void catching(Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.catching(throwable);
      }
   }

   public void debug(Marker marker, Message message) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message);
      }
   }

   public void debug(Marker marker, Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, throwable);
      }
   }

   public void debug(Marker marker, MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, messageSupplier);
      }
   }

   public void debug(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, messageSupplier, throwable);
      }
   }

   public void debug(Marker marker, CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message);
      }
   }

   public void debug(Marker marker, CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, throwable);
      }
   }

   public void debug(Marker marker, Object message) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message);
      }
   }

   public void debug(Marker marker, Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, throwable);
      }
   }

   public void debug(Marker marker, String message) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message);
      }
   }

   public void debug(Marker marker, String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, params);
      }
   }

   public void debug(Marker marker, String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, paramSuppliers);
      }
   }

   public void debug(Marker marker, String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, throwable);
      }
   }

   public void debug(Marker marker, Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, messageSupplier);
      }
   }

   public void debug(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, messageSupplier, throwable);
      }
   }

   public void debug(Message message) {
      for (Logger logger : this.loggers) {
         logger.debug(message);
      }
   }

   public void debug(Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(message, throwable);
      }
   }

   public void debug(MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.debug(messageSupplier);
      }
   }

   public void debug(MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(messageSupplier, throwable);
      }
   }

   public void debug(CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.debug(message);
      }
   }

   public void debug(CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(message, throwable);
      }
   }

   public void debug(Object message) {
      for (Logger logger : this.loggers) {
         logger.debug(message);
      }
   }

   public void debug(Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(message, throwable);
      }
   }

   public void debug(String message) {
      for (Logger logger : this.loggers) {
         logger.debug(message);
      }
   }

   public void debug(String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.debug(message, params);
      }
   }

   public void debug(String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.debug(message, paramSuppliers);
      }
   }

   public void debug(String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(message, throwable);
      }
   }

   public void debug(Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.debug(messageSupplier);
      }
   }

   public void debug(Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.debug(messageSupplier, throwable);
      }
   }

   public void debug(Marker marker, String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0);
      }
   }

   public void debug(Marker marker, String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0, p1);
      }
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0, p1, p2);
      }
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0, p1, p2, p3);
      }
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0, p1, p2, p3, p4);
      }
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void debug(
      Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      for (Logger logger : this.loggers) {
         logger.debug(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public void debug(String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0);
      }
   }

   public void debug(String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0, p1);
      }
   }

   public void debug(String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0, p1, p2);
      }
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0, p1, p2, p3);
      }
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0, p1, p2, p3, p4);
      }
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      for (Logger logger : this.loggers) {
         logger.debug(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   @Deprecated
   public void entry() {
      for (Logger logger : this.loggers) {
         logger.entry();
      }
   }

   @Deprecated
   public void entry(Object... params) {
      for (Logger logger : this.loggers) {
         logger.entry(params);
      }
   }

   public void error(Marker marker, Message message) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message);
      }
   }

   public void error(Marker marker, Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, throwable);
      }
   }

   public void error(Marker marker, MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.error(marker, messageSupplier);
      }
   }

   public void error(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(marker, messageSupplier, throwable);
      }
   }

   public void error(Marker marker, CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message);
      }
   }

   public void error(Marker marker, CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, throwable);
      }
   }

   public void error(Marker marker, Object message) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message);
      }
   }

   public void error(Marker marker, Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, throwable);
      }
   }

   public void error(Marker marker, String message) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message);
      }
   }

   public void error(Marker marker, String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, params);
      }
   }

   public void error(Marker marker, String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, paramSuppliers);
      }
   }

   public void error(Marker marker, String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, throwable);
      }
   }

   public void error(Marker marker, Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.error(marker, messageSupplier);
      }
   }

   public void error(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(marker, messageSupplier, throwable);
      }
   }

   public void error(Message message) {
      for (Logger logger : this.loggers) {
         logger.error(message);
      }
   }

   public void error(Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(message, throwable);
      }
   }

   public void error(MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.error(messageSupplier);
      }
   }

   public void error(MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(messageSupplier, throwable);
      }
   }

   public void error(CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.error(message);
      }
   }

   public void error(CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(message, throwable);
      }
   }

   public void error(Object message) {
      for (Logger logger : this.loggers) {
         logger.error(message);
      }
   }

   public void error(Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(message, throwable);
      }
   }

   public void error(String message) {
      for (Logger logger : this.loggers) {
         logger.error(message);
      }
   }

   public void error(String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.error(message, params);
      }
   }

   public void error(String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.error(message, paramSuppliers);
      }
   }

   public void error(String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(message, throwable);
      }
   }

   public void error(Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.error(messageSupplier);
      }
   }

   public void error(Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.error(messageSupplier, throwable);
      }
   }

   public void error(Marker marker, String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0);
      }
   }

   public void error(Marker marker, String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0, p1);
      }
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0, p1, p2);
      }
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0, p1, p2, p3);
      }
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0, p1, p2, p3, p4);
      }
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void error(
      Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      for (Logger logger : this.loggers) {
         logger.error(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public void error(String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0);
      }
   }

   public void error(String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0, p1);
      }
   }

   public void error(String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0, p1, p2);
      }
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0, p1, p2, p3);
      }
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0, p1, p2, p3, p4);
      }
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      for (Logger logger : this.loggers) {
         logger.error(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   @Deprecated
   public void exit() {
      for (Logger logger : this.loggers) {
         logger.exit();
      }
   }

   @Deprecated
   public <R> R exit(R result) {
      for (Logger logger : this.loggers) {
         logger.exit(result);
      }

      return result;
   }

   public void fatal(Marker marker, Message message) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message);
      }
   }

   public void fatal(Marker marker, Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, throwable);
      }
   }

   public void fatal(Marker marker, MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, messageSupplier);
      }
   }

   public void fatal(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, messageSupplier, throwable);
      }
   }

   public void fatal(Marker marker, CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message);
      }
   }

   public void fatal(Marker marker, CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, throwable);
      }
   }

   public void fatal(Marker marker, Object message) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message);
      }
   }

   public void fatal(Marker marker, Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, throwable);
      }
   }

   public void fatal(Marker marker, String message) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message);
      }
   }

   public void fatal(Marker marker, String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, params);
      }
   }

   public void fatal(Marker marker, String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, paramSuppliers);
      }
   }

   public void fatal(Marker marker, String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, throwable);
      }
   }

   public void fatal(Marker marker, Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, messageSupplier);
      }
   }

   public void fatal(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, messageSupplier, throwable);
      }
   }

   public void fatal(Message message) {
      for (Logger logger : this.loggers) {
         logger.fatal(message);
      }
   }

   public void fatal(Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, throwable);
      }
   }

   public void fatal(MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.fatal(messageSupplier);
      }
   }

   public void fatal(MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(messageSupplier, throwable);
      }
   }

   public void fatal(CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.fatal(message);
      }
   }

   public void fatal(CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, throwable);
      }
   }

   public void fatal(Object message) {
      for (Logger logger : this.loggers) {
         logger.fatal(message);
      }
   }

   public void fatal(Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, throwable);
      }
   }

   public void fatal(String message) {
      for (Logger logger : this.loggers) {
         logger.fatal(message);
      }
   }

   public void fatal(String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, params);
      }
   }

   public void fatal(String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, paramSuppliers);
      }
   }

   public void fatal(String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, throwable);
      }
   }

   public void fatal(Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.fatal(messageSupplier);
      }
   }

   public void fatal(Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.fatal(messageSupplier, throwable);
      }
   }

   public void fatal(Marker marker, String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0);
      }
   }

   public void fatal(Marker marker, String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0, p1);
      }
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0, p1, p2);
      }
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0, p1, p2, p3);
      }
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0, p1, p2, p3, p4);
      }
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void fatal(
      Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      for (Logger logger : this.loggers) {
         logger.fatal(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public void fatal(String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0);
      }
   }

   public void fatal(String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0, p1);
      }
   }

   public void fatal(String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0, p1, p2);
      }
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0, p1, p2, p3);
      }
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0, p1, p2, p3, p4);
      }
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      for (Logger logger : this.loggers) {
         logger.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public Level getLevel() {
      Level maxLevel = Level.OFF;

      for (Logger logger : this.loggers) {
         Level level = logger.getLevel();
         if (level.intLevel() > maxLevel.intLevel()) {
            maxLevel = level;
         }
      }

      return maxLevel;
   }

   public <MF extends MessageFactory> MF getMessageFactory() {
      return (MF)this.loggers[0].getMessageFactory();
   }

   public FlowMessageFactory getFlowMessageFactory() {
      return this.loggers[0].getFlowMessageFactory();
   }

   public String getName() {
      return this.name;
   }

   public void info(Marker marker, Message message) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message);
      }
   }

   public void info(Marker marker, Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, throwable);
      }
   }

   public void info(Marker marker, MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.info(marker, messageSupplier);
      }
   }

   public void info(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(marker, messageSupplier, throwable);
      }
   }

   public void info(Marker marker, CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message);
      }
   }

   public void info(Marker marker, CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, throwable);
      }
   }

   public void info(Marker marker, Object message) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message);
      }
   }

   public void info(Marker marker, Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, throwable);
      }
   }

   public void info(Marker marker, String message) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message);
      }
   }

   public void info(Marker marker, String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, params);
      }
   }

   public void info(Marker marker, String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, paramSuppliers);
      }
   }

   public void info(Marker marker, String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, throwable);
      }
   }

   public void info(Marker marker, Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.info(marker, messageSupplier);
      }
   }

   public void info(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(marker, messageSupplier, throwable);
      }
   }

   public void info(Message message) {
      for (Logger logger : this.loggers) {
         logger.info(message);
      }
   }

   public void info(Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(message, throwable);
      }
   }

   public void info(MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.info(messageSupplier);
      }
   }

   public void info(MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(messageSupplier, throwable);
      }
   }

   public void info(CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.info(message);
      }
   }

   public void info(CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(message, throwable);
      }
   }

   public void info(Object message) {
      for (Logger logger : this.loggers) {
         logger.info(message);
      }
   }

   public void info(Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(message, throwable);
      }
   }

   public void info(String message) {
      for (Logger logger : this.loggers) {
         logger.info(message);
      }
   }

   public void info(String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.info(message, params);
      }
   }

   public void info(String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.info(message, paramSuppliers);
      }
   }

   public void info(String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(message, throwable);
      }
   }

   public void info(Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.info(messageSupplier);
      }
   }

   public void info(Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.info(messageSupplier, throwable);
      }
   }

   public void info(Marker marker, String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0);
      }
   }

   public void info(Marker marker, String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0, p1);
      }
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0, p1, p2);
      }
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0, p1, p2, p3);
      }
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0, p1, p2, p3, p4);
      }
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      for (Logger logger : this.loggers) {
         logger.info(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public void info(String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0);
      }
   }

   public void info(String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0, p1);
      }
   }

   public void info(String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0, p1, p2);
      }
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0, p1, p2, p3);
      }
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0, p1, p2, p3, p4);
      }
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      for (Logger logger : this.loggers) {
         logger.info(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public boolean isDebugEnabled() {
      for (Logger logger : this.loggers) {
         if (logger.isDebugEnabled()) {
            return true;
         }
      }

      return false;
   }

   public boolean isDebugEnabled(Marker marker) {
      for (Logger logger : this.loggers) {
         if (logger.isDebugEnabled(marker)) {
            return true;
         }
      }

      return false;
   }

   public boolean isEnabled(Level level) {
      for (Logger logger : this.loggers) {
         if (logger.isEnabled(level)) {
            return true;
         }
      }

      return false;
   }

   public boolean isEnabled(Level level, Marker marker) {
      for (Logger logger : this.loggers) {
         if (logger.isEnabled(level, marker)) {
            return true;
         }
      }

      return false;
   }

   public boolean isErrorEnabled() {
      for (Logger logger : this.loggers) {
         if (logger.isErrorEnabled()) {
            return true;
         }
      }

      return false;
   }

   public boolean isErrorEnabled(Marker marker) {
      for (Logger logger : this.loggers) {
         if (logger.isErrorEnabled(marker)) {
            return true;
         }
      }

      return false;
   }

   public boolean isFatalEnabled() {
      for (Logger logger : this.loggers) {
         if (logger.isFatalEnabled()) {
            return true;
         }
      }

      return false;
   }

   public boolean isFatalEnabled(Marker marker) {
      for (Logger logger : this.loggers) {
         if (logger.isFatalEnabled(marker)) {
            return true;
         }
      }

      return false;
   }

   public boolean isInfoEnabled() {
      for (Logger logger : this.loggers) {
         if (logger.isInfoEnabled()) {
            return true;
         }
      }

      return false;
   }

   public boolean isInfoEnabled(Marker marker) {
      for (Logger logger : this.loggers) {
         if (logger.isInfoEnabled(marker)) {
            return true;
         }
      }

      return false;
   }

   public boolean isTraceEnabled() {
      for (Logger logger : this.loggers) {
         if (logger.isTraceEnabled()) {
            return true;
         }
      }

      return false;
   }

   public boolean isTraceEnabled(Marker marker) {
      for (Logger logger : this.loggers) {
         if (logger.isTraceEnabled(marker)) {
            return true;
         }
      }

      return false;
   }

   public boolean isWarnEnabled() {
      for (Logger logger : this.loggers) {
         if (logger.isWarnEnabled()) {
            return true;
         }
      }

      return false;
   }

   public boolean isWarnEnabled(Marker marker) {
      for (Logger logger : this.loggers) {
         if (logger.isWarnEnabled(marker)) {
            return true;
         }
      }

      return false;
   }

   public void log(Level level, Marker marker, Message message) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message);
      }
   }

   public void log(Level level, Marker marker, Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, throwable);
      }
   }

   public void log(Level level, Marker marker, MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, messageSupplier);
      }
   }

   public void log(Level level, Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, messageSupplier, throwable);
      }
   }

   public void log(Level level, Marker marker, CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message);
      }
   }

   public void log(Level level, Marker marker, CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, throwable);
      }
   }

   public void log(Level level, Marker marker, Object message) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message);
      }
   }

   public void log(Level level, Marker marker, Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, throwable);
      }
   }

   public void log(Level level, Marker marker, String message) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message);
      }
   }

   public void log(Level level, Marker marker, String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, params);
      }
   }

   public void log(Level level, Marker marker, String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, paramSuppliers);
      }
   }

   public void log(Level level, Marker marker, String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, throwable);
      }
   }

   public void log(Level level, Marker marker, Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, messageSupplier);
      }
   }

   public void log(Level level, Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, messageSupplier, throwable);
      }
   }

   public void log(Level level, Message message) {
      for (Logger logger : this.loggers) {
         logger.log(level, message);
      }
   }

   public void log(Level level, Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, throwable);
      }
   }

   public void log(Level level, MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.log(level, messageSupplier);
      }
   }

   public void log(Level level, MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, messageSupplier, throwable);
      }
   }

   public void log(Level level, CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.log(level, message);
      }
   }

   public void log(Level level, CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, throwable);
      }
   }

   public void log(Level level, Object message) {
      for (Logger logger : this.loggers) {
         logger.log(level, message);
      }
   }

   public void log(Level level, Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, throwable);
      }
   }

   public void log(Level level, String message) {
      for (Logger logger : this.loggers) {
         logger.log(level, message);
      }
   }

   public void log(Level level, String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, params);
      }
   }

   public void log(Level level, String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, paramSuppliers);
      }
   }

   public void log(Level level, String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, throwable);
      }
   }

   public void log(Level level, Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.log(level, messageSupplier);
      }
   }

   public void log(Level level, Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.log(level, messageSupplier, throwable);
      }
   }

   public void log(Level level, Marker marker, String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0);
      }
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0, p1);
      }
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0, p1, p2);
      }
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0, p1, p2, p3);
      }
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0, p1, p2, p3, p4);
      }
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void log(
      Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8
   ) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void log(
      Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      for (Logger logger : this.loggers) {
         logger.log(level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public void log(Level level, String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0);
      }
   }

   public void log(Level level, String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0, p1);
      }
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0, p1, p2);
      }
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0, p1, p2, p3);
      }
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0, p1, p2, p3, p4);
      }
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      for (Logger logger : this.loggers) {
         logger.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public void printf(Level level, Marker marker, String format, Object... params) {
      for (Logger logger : this.loggers) {
         logger.printf(level, marker, format, params);
      }
   }

   public void printf(Level level, String format, Object... params) {
      for (Logger logger : this.loggers) {
         logger.printf(level, format, params);
      }
   }

   public <T extends Throwable> T throwing(Level level, T throwable) {
      for (Logger logger : this.loggers) {
         logger.throwing(level, throwable);
      }

      return throwable;
   }

   public <T extends Throwable> T throwing(T throwable) {
      for (Logger logger : this.loggers) {
         logger.throwing(throwable);
      }

      return throwable;
   }

   public void trace(Marker marker, Message message) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message);
      }
   }

   public void trace(Marker marker, Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, throwable);
      }
   }

   public void trace(Marker marker, MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, messageSupplier);
      }
   }

   public void trace(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, messageSupplier, throwable);
      }
   }

   public void trace(Marker marker, CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message);
      }
   }

   public void trace(Marker marker, CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, throwable);
      }
   }

   public void trace(Marker marker, Object message) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message);
      }
   }

   public void trace(Marker marker, Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, throwable);
      }
   }

   public void trace(Marker marker, String message) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message);
      }
   }

   public void trace(Marker marker, String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, params);
      }
   }

   public void trace(Marker marker, String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, paramSuppliers);
      }
   }

   public void trace(Marker marker, String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, throwable);
      }
   }

   public void trace(Marker marker, Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, messageSupplier);
      }
   }

   public void trace(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, messageSupplier, throwable);
      }
   }

   public void trace(Message message) {
      for (Logger logger : this.loggers) {
         logger.trace(message);
      }
   }

   public void trace(Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(message, throwable);
      }
   }

   public void trace(MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.trace(messageSupplier);
      }
   }

   public void trace(MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(messageSupplier, throwable);
      }
   }

   public void trace(CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.trace(message);
      }
   }

   public void trace(CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(message, throwable);
      }
   }

   public void trace(Object message) {
      for (Logger logger : this.loggers) {
         logger.trace(message);
      }
   }

   public void trace(Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(message, throwable);
      }
   }

   public void trace(String message) {
      for (Logger logger : this.loggers) {
         logger.trace(message);
      }
   }

   public void trace(String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.trace(message, params);
      }
   }

   public void trace(String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.trace(message, paramSuppliers);
      }
   }

   public void trace(String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(message, throwable);
      }
   }

   public void trace(Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.trace(messageSupplier);
      }
   }

   public void trace(Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.trace(messageSupplier, throwable);
      }
   }

   public void trace(Marker marker, String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0);
      }
   }

   public void trace(Marker marker, String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0, p1);
      }
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0, p1, p2);
      }
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0, p1, p2, p3);
      }
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0, p1, p2, p3, p4);
      }
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void trace(
      Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      for (Logger logger : this.loggers) {
         logger.trace(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public void trace(String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0);
      }
   }

   public void trace(String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0, p1);
      }
   }

   public void trace(String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0, p1, p2);
      }
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0, p1, p2, p3);
      }
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0, p1, p2, p3, p4);
      }
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      for (Logger logger : this.loggers) {
         logger.trace(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public EntryMessage traceEntry() {
      EntryMessage message = null;

      for (Logger logger : this.loggers) {
         EntryMessage m = logger.traceEntry();
         if (message == null) {
            message = m;
         }
      }

      return message;
   }

   public EntryMessage traceEntry(String format, Object... params) {
      EntryMessage message = null;

      for (Logger logger : this.loggers) {
         EntryMessage m = logger.traceEntry(format, params);
         if (message == null) {
            message = m;
         }
      }

      return message;
   }

   public EntryMessage traceEntry(Supplier<?>... paramSuppliers) {
      EntryMessage message = null;

      for (Logger logger : this.loggers) {
         EntryMessage m = logger.traceEntry(paramSuppliers);
         if (message == null) {
            message = m;
         }
      }

      return message;
   }

   public EntryMessage traceEntry(String format, Supplier<?>... paramSuppliers) {
      EntryMessage message = null;

      for (Logger logger : this.loggers) {
         EntryMessage m = logger.traceEntry(format, paramSuppliers);
         if (message == null) {
            message = m;
         }
      }

      return message;
   }

   public EntryMessage traceEntry(Message message) {
      EntryMessage msg = null;

      for (Logger logger : this.loggers) {
         EntryMessage m = logger.traceEntry(message);
         if (msg == null) {
            msg = m;
         }
      }

      return msg;
   }

   public void traceExit() {
      for (Logger logger : this.loggers) {
         logger.traceExit();
      }
   }

   public <R> R traceExit(R result) {
      for (Logger logger : this.loggers) {
         logger.traceExit(result);
      }

      return result;
   }

   public <R> R traceExit(String format, R result) {
      for (Logger logger : this.loggers) {
         logger.traceExit(format, result);
      }

      return result;
   }

   public void traceExit(EntryMessage message) {
      for (Logger logger : this.loggers) {
         logger.traceExit(message);
      }
   }

   public <R> R traceExit(EntryMessage message, R result) {
      for (Logger logger : this.loggers) {
         logger.traceExit(message, result);
      }

      return result;
   }

   public <R> R traceExit(Message message, R result) {
      for (Logger logger : this.loggers) {
         logger.traceExit(message, result);
      }

      return result;
   }

   public void warn(Marker marker, Message message) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message);
      }
   }

   public void warn(Marker marker, Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, throwable);
      }
   }

   public void warn(Marker marker, MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, messageSupplier);
      }
   }

   public void warn(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, messageSupplier, throwable);
      }
   }

   public void warn(Marker marker, CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message);
      }
   }

   public void warn(Marker marker, CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, throwable);
      }
   }

   public void warn(Marker marker, Object message) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message);
      }
   }

   public void warn(Marker marker, Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, throwable);
      }
   }

   public void warn(Marker marker, String message) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message);
      }
   }

   public void warn(Marker marker, String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, params);
      }
   }

   public void warn(Marker marker, String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, paramSuppliers);
      }
   }

   public void warn(Marker marker, String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, throwable);
      }
   }

   public void warn(Marker marker, Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, messageSupplier);
      }
   }

   public void warn(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, messageSupplier, throwable);
      }
   }

   public void warn(Message message) {
      for (Logger logger : this.loggers) {
         logger.warn(message);
      }
   }

   public void warn(Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(message, throwable);
      }
   }

   public void warn(MessageSupplier messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.warn(messageSupplier);
      }
   }

   public void warn(MessageSupplier messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(messageSupplier, throwable);
      }
   }

   public void warn(CharSequence message) {
      for (Logger logger : this.loggers) {
         logger.warn(message);
      }
   }

   public void warn(CharSequence message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(message, throwable);
      }
   }

   public void warn(Object message) {
      for (Logger logger : this.loggers) {
         logger.warn(message);
      }
   }

   public void warn(Object message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(message, throwable);
      }
   }

   public void warn(String message) {
      for (Logger logger : this.loggers) {
         logger.warn(message);
      }
   }

   public void warn(String message, Object... params) {
      for (Logger logger : this.loggers) {
         logger.warn(message, params);
      }
   }

   public void warn(String message, Supplier<?>... paramSuppliers) {
      for (Logger logger : this.loggers) {
         logger.warn(message, paramSuppliers);
      }
   }

   public void warn(String message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(message, throwable);
      }
   }

   public void warn(Supplier<?> messageSupplier) {
      for (Logger logger : this.loggers) {
         logger.warn(messageSupplier);
      }
   }

   public void warn(Supplier<?> messageSupplier, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.warn(messageSupplier, throwable);
      }
   }

   public void warn(Marker marker, String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0);
      }
   }

   public void warn(Marker marker, String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0, p1);
      }
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0, p1, p2);
      }
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0, p1, p2, p3);
      }
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0, p1, p2, p3, p4);
      }
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      for (Logger logger : this.loggers) {
         logger.warn(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public void warn(String message, Object p0) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0);
      }
   }

   public void warn(String message, Object p0, Object p1) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0, p1);
      }
   }

   public void warn(String message, Object p0, Object p1, Object p2) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0, p1, p2);
      }
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0, p1, p2, p3);
      }
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0, p1, p2, p3, p4);
      }
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0, p1, p2, p3, p4, p5);
      }
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0, p1, p2, p3, p4, p5, p6);
      }
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0, p1, p2, p3, p4, p5, p6, p7);
      }
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
      }
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      for (Logger logger : this.loggers) {
         logger.warn(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
      }
   }

   public void logMessage(Level level, Marker marker, String fqcn, StackTraceElement location, Message message, Throwable throwable) {
      for (Logger logger : this.loggers) {
         logger.logMessage(level, marker, fqcn, location, message, throwable);
      }
   }

   public LogBuilder atTrace() {
      for (Logger logger : this.loggers) {
         LogBuilder b = logger.atTrace();
         if (b != LogBuilder.NOOP) {
            return b;
         }
      }

      return LogBuilder.NOOP;
   }

   public LogBuilder atDebug() {
      for (Logger logger : this.loggers) {
         LogBuilder b = logger.atDebug();
         if (b != LogBuilder.NOOP) {
            return b;
         }
      }

      return LogBuilder.NOOP;
   }

   public LogBuilder atInfo() {
      for (Logger logger : this.loggers) {
         LogBuilder b = logger.atInfo();
         if (b != LogBuilder.NOOP) {
            return b;
         }
      }

      return LogBuilder.NOOP;
   }

   public LogBuilder atWarn() {
      for (Logger logger : this.loggers) {
         LogBuilder b = logger.atWarn();
         if (b != LogBuilder.NOOP) {
            return b;
         }
      }

      return LogBuilder.NOOP;
   }

   public LogBuilder atError() {
      for (Logger logger : this.loggers) {
         LogBuilder b = logger.atError();
         if (b != LogBuilder.NOOP) {
            return b;
         }
      }

      return LogBuilder.NOOP;
   }

   public LogBuilder atFatal() {
      for (Logger logger : this.loggers) {
         LogBuilder b = logger.atFatal();
         if (b != LogBuilder.NOOP) {
            return b;
         }
      }

      return LogBuilder.NOOP;
   }

   public LogBuilder always() {
      for (Logger logger : this.loggers) {
         LogBuilder b = logger.always();
         if (b != LogBuilder.NOOP) {
            return b;
         }
      }

      return LogBuilder.NOOP;
   }

   public LogBuilder atLevel(Level level) {
      for (Logger logger : this.loggers) {
         LogBuilder b = logger.atLevel(level);
         if (b != LogBuilder.NOOP) {
            return b;
         }
      }

      return LogBuilder.NOOP;
   }
}
