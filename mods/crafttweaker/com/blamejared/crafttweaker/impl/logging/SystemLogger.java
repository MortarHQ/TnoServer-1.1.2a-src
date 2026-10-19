package com.blamejared.crafttweaker.impl.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.EntryMessage;
import org.apache.logging.log4j.message.FlowMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

final class SystemLogger implements Logger {
   private final String name;
   private final Logger mainLogger;
   private final Marker systemMarker;

   private SystemLogger(String name, Logger mainLogger, Marker systemMarker) {
      this.name = name;
      this.mainLogger = mainLogger;
      this.systemMarker = systemMarker;
   }

   static SystemLogger of(String name, Logger mainLogger, String systemName) {
      Marker marker = MarkerManager.getMarker("CT_SYS_MARK_" + systemName);
      Marker systemMarker = SystemMarker.of(systemName, marker);
      return new SystemLogger(name, mainLogger, systemMarker);
   }

   public void catching(Level level, Throwable throwable) {
      this.mainLogger.catching(level, throwable);
   }

   public void catching(Throwable throwable) {
      this.mainLogger.catching(throwable);
   }

   public void debug(Marker marker, Message message) {
      this.debug(message);
   }

   public void debug(Marker marker, Message message, Throwable throwable) {
      this.debug(message, throwable);
   }

   public void debug(Marker marker, MessageSupplier messageSupplier) {
      this.debug(messageSupplier);
   }

   public void debug(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      this.debug(messageSupplier, throwable);
   }

   public void debug(Marker marker, CharSequence message) {
      this.debug(message);
   }

   public void debug(Marker marker, CharSequence message, Throwable throwable) {
      this.debug(message, throwable);
   }

   public void debug(Marker marker, Object message) {
      this.debug(message);
   }

   public void debug(Marker marker, Object message, Throwable throwable) {
      this.debug(message, throwable);
   }

   public void debug(Marker marker, String message) {
      this.debug(message);
   }

   public void debug(Marker marker, String message, Object... params) {
      this.debug(message, params);
   }

   public void debug(Marker marker, String message, Supplier<?>... paramSuppliers) {
      this.debug(message, paramSuppliers);
   }

   public void debug(Marker marker, String message, Throwable throwable) {
      this.debug(message, throwable);
   }

   public void debug(Marker marker, Supplier<?> messageSupplier) {
      this.debug(messageSupplier);
   }

   public void debug(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      this.debug(messageSupplier, throwable);
   }

   public void debug(Message message) {
      this.mainLogger.debug(this.systemMarker, message);
   }

   public void debug(Message message, Throwable throwable) {
      this.mainLogger.debug(this.systemMarker, message, throwable);
   }

   public void debug(MessageSupplier messageSupplier) {
      this.mainLogger.debug(this.systemMarker, messageSupplier);
   }

   public void debug(MessageSupplier messageSupplier, Throwable throwable) {
      this.mainLogger.debug(this.systemMarker, messageSupplier, throwable);
   }

   public void debug(CharSequence message) {
      this.mainLogger.debug(this.systemMarker, message);
   }

   public void debug(CharSequence message, Throwable throwable) {
      this.mainLogger.debug(this.systemMarker, message, throwable);
   }

   public void debug(Object message) {
      this.mainLogger.debug(this.systemMarker, message);
   }

   public void debug(Object message, Throwable throwable) {
      this.mainLogger.debug(this.systemMarker, message, throwable);
   }

   public void debug(String message) {
      this.mainLogger.debug(this.systemMarker, message);
   }

   public void debug(String message, Object... params) {
      this.mainLogger.debug(this.systemMarker, message, params);
   }

   public void debug(String message, Supplier<?>... paramSuppliers) {
      this.mainLogger.debug(this.systemMarker, message, paramSuppliers);
   }

   public void debug(String message, Throwable throwable) {
      this.mainLogger.debug(this.systemMarker, message, throwable);
   }

   public void debug(Supplier<?> messageSupplier) {
      this.mainLogger.debug(this.systemMarker, messageSupplier);
   }

   public void debug(Supplier<?> messageSupplier, Throwable throwable) {
      this.mainLogger.debug(this.systemMarker, messageSupplier, throwable);
   }

   public void debug(Marker marker, String message, Object p0) {
      this.debug(message, p0);
   }

   public void debug(Marker marker, String message, Object p0, Object p1) {
      this.debug(message, p0, p1);
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2) {
      this.debug(message, p0, p1, p2);
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      this.debug(message, p0, p1, p2, p3);
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.debug(message, p0, p1, p2, p3, p4);
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.debug(message, p0, p1, p2, p3, p4, p5);
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.debug(message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.debug(message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void debug(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.debug(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void debug(
      Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      this.debug(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public void debug(String message, Object p0) {
      this.mainLogger.debug(this.systemMarker, message, p0);
   }

   public void debug(String message, Object p0, Object p1) {
      this.mainLogger.debug(this.systemMarker, message, p0, p1);
   }

   public void debug(String message, Object p0, Object p1, Object p2) {
      this.mainLogger.debug(this.systemMarker, message, p0, p1, p2);
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3) {
      this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3);
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4);
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   @Deprecated
   public void entry() {
      this.mainLogger.entry();
   }

   @Deprecated
   public void entry(Object... params) {
      this.mainLogger.entry(params);
   }

   public void error(Marker marker, Message message) {
      this.error(message);
   }

   public void error(Marker marker, Message message, Throwable throwable) {
      this.error(message, throwable);
   }

   public void error(Marker marker, MessageSupplier messageSupplier) {
      this.error(messageSupplier);
   }

   public void error(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      this.error(messageSupplier, throwable);
   }

   public void error(Marker marker, CharSequence message) {
      this.error(message);
   }

   public void error(Marker marker, CharSequence message, Throwable throwable) {
      this.error(message, throwable);
   }

   public void error(Marker marker, Object message) {
      this.error(message);
   }

   public void error(Marker marker, Object message, Throwable throwable) {
      this.error(message, throwable);
   }

   public void error(Marker marker, String message) {
      this.error(message);
   }

   public void error(Marker marker, String message, Object... params) {
      this.error(message, params);
   }

   public void error(Marker marker, String message, Supplier<?>... paramSuppliers) {
      this.error(message, paramSuppliers);
   }

   public void error(Marker marker, String message, Throwable throwable) {
      this.error(message, throwable);
   }

   public void error(Marker marker, Supplier<?> messageSupplier) {
      this.error(messageSupplier);
   }

   public void error(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      this.error(messageSupplier, throwable);
   }

   public void error(Message message) {
      this.mainLogger.error(this.systemMarker, message);
   }

   public void error(Message message, Throwable throwable) {
      this.mainLogger.error(this.systemMarker, message, throwable);
   }

   public void error(MessageSupplier messageSupplier) {
      this.mainLogger.error(this.systemMarker, messageSupplier);
   }

   public void error(MessageSupplier messageSupplier, Throwable throwable) {
      this.mainLogger.error(this.systemMarker, messageSupplier, throwable);
   }

   public void error(CharSequence message) {
      this.mainLogger.error(this.systemMarker, message);
   }

   public void error(CharSequence message, Throwable throwable) {
      this.mainLogger.error(this.systemMarker, message, throwable);
   }

   public void error(Object message) {
      this.mainLogger.error(this.systemMarker, message);
   }

   public void error(Object message, Throwable throwable) {
      this.mainLogger.error(this.systemMarker, message, throwable);
   }

   public void error(String message) {
      this.mainLogger.error(this.systemMarker, message);
   }

   public void error(String message, Object... params) {
      this.mainLogger.error(this.systemMarker, message, params);
   }

   public void error(String message, Supplier<?>... paramSuppliers) {
      this.mainLogger.error(this.systemMarker, message, paramSuppliers);
   }

   public void error(String message, Throwable throwable) {
      this.mainLogger.error(this.systemMarker, message, throwable);
   }

   public void error(Supplier<?> messageSupplier) {
      this.mainLogger.error(this.systemMarker, messageSupplier);
   }

   public void error(Supplier<?> messageSupplier, Throwable throwable) {
      this.mainLogger.error(this.systemMarker, messageSupplier, throwable);
   }

   public void error(Marker marker, String message, Object p0) {
      this.error(message, p0);
   }

   public void error(Marker marker, String message, Object p0, Object p1) {
      this.error(message, p0, p1);
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2) {
      this.error(message, p0, p1, p2);
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      this.error(message, p0, p1, p2, p3);
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.error(message, p0, p1, p2, p3, p4);
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.error(message, p0, p1, p2, p3, p4, p5);
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.error(message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.error(message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void error(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.error(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void error(
      Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      this.error(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public void error(String message, Object p0) {
      this.mainLogger.error(this.systemMarker, message, p0);
   }

   public void error(String message, Object p0, Object p1) {
      this.mainLogger.error(this.systemMarker, message, p0, p1);
   }

   public void error(String message, Object p0, Object p1, Object p2) {
      this.mainLogger.error(this.systemMarker, message, p0, p1, p2);
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3) {
      this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3);
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4);
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   @Deprecated
   public void exit() {
      this.mainLogger.exit();
   }

   @Deprecated
   public <R> R exit(R result) {
      return (R)this.mainLogger.exit(result);
   }

   public void fatal(Marker marker, Message message) {
      this.fatal(message);
   }

   public void fatal(Marker marker, Message message, Throwable throwable) {
      this.fatal(message, throwable);
   }

   public void fatal(Marker marker, MessageSupplier messageSupplier) {
      this.fatal(messageSupplier);
   }

   public void fatal(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      this.fatal(messageSupplier, throwable);
   }

   public void fatal(Marker marker, CharSequence message) {
      this.fatal(message);
   }

   public void fatal(Marker marker, CharSequence message, Throwable throwable) {
      this.fatal(message, throwable);
   }

   public void fatal(Marker marker, Object message) {
      this.fatal(message);
   }

   public void fatal(Marker marker, Object message, Throwable throwable) {
      this.fatal(message, throwable);
   }

   public void fatal(Marker marker, String message) {
      this.fatal(message);
   }

   public void fatal(Marker marker, String message, Object... params) {
      this.fatal(message, params);
   }

   public void fatal(Marker marker, String message, Supplier<?>... paramSuppliers) {
      this.fatal(message, paramSuppliers);
   }

   public void fatal(Marker marker, String message, Throwable throwable) {
      this.fatal(message, throwable);
   }

   public void fatal(Marker marker, Supplier<?> messageSupplier) {
      this.fatal(messageSupplier);
   }

   public void fatal(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      this.fatal(messageSupplier, throwable);
   }

   public void fatal(Message message) {
      this.mainLogger.fatal(this.systemMarker, message);
   }

   public void fatal(Message message, Throwable throwable) {
      this.mainLogger.fatal(this.systemMarker, message, throwable);
   }

   public void fatal(MessageSupplier messageSupplier) {
      this.mainLogger.fatal(this.systemMarker, messageSupplier);
   }

   public void fatal(MessageSupplier messageSupplier, Throwable throwable) {
      this.mainLogger.fatal(this.systemMarker, messageSupplier, throwable);
   }

   public void fatal(CharSequence message) {
      this.mainLogger.fatal(this.systemMarker, message);
   }

   public void fatal(CharSequence message, Throwable throwable) {
      this.mainLogger.fatal(this.systemMarker, message, throwable);
   }

   public void fatal(Object message) {
      this.mainLogger.fatal(this.systemMarker, message);
   }

   public void fatal(Object message, Throwable throwable) {
      this.mainLogger.fatal(this.systemMarker, message, throwable);
   }

   public void fatal(String message) {
      this.mainLogger.fatal(this.systemMarker, message);
   }

   public void fatal(String message, Object... params) {
      this.mainLogger.fatal(this.systemMarker, message, params);
   }

   public void fatal(String message, Supplier<?>... paramSuppliers) {
      this.mainLogger.fatal(this.systemMarker, message, paramSuppliers);
   }

   public void fatal(String message, Throwable throwable) {
      this.mainLogger.fatal(this.systemMarker, message, throwable);
   }

   public void fatal(Supplier<?> messageSupplier) {
      this.mainLogger.fatal(this.systemMarker, messageSupplier);
   }

   public void fatal(Supplier<?> messageSupplier, Throwable throwable) {
      this.mainLogger.fatal(this.systemMarker, messageSupplier, throwable);
   }

   public void fatal(Marker marker, String message, Object p0) {
      this.fatal(message, p0);
   }

   public void fatal(Marker marker, String message, Object p0, Object p1) {
      this.fatal(message, p0, p1);
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2) {
      this.fatal(message, p0, p1, p2);
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      this.fatal(message, p0, p1, p2, p3);
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.fatal(message, p0, p1, p2, p3, p4);
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.fatal(message, p0, p1, p2, p3, p4, p5);
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.fatal(message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void fatal(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void fatal(
      Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      this.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public void fatal(String message, Object p0) {
      this.mainLogger.fatal(this.systemMarker, message, p0);
   }

   public void fatal(String message, Object p0, Object p1) {
      this.mainLogger.fatal(this.systemMarker, message, p0, p1);
   }

   public void fatal(String message, Object p0, Object p1, Object p2) {
      this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2);
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3) {
      this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3);
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4);
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void fatal(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public Level getLevel() {
      return this.mainLogger.getLevel();
   }

   public <MF extends MessageFactory> MF getMessageFactory() {
      return (MF)this.mainLogger.getMessageFactory();
   }

   public FlowMessageFactory getFlowMessageFactory() {
      return this.mainLogger.getFlowMessageFactory();
   }

   public String getName() {
      return this.name;
   }

   public void info(Marker marker, Message message) {
      this.info(message);
   }

   public void info(Marker marker, Message message, Throwable throwable) {
      this.info(message, throwable);
   }

   public void info(Marker marker, MessageSupplier messageSupplier) {
      this.info(messageSupplier);
   }

   public void info(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      this.info(messageSupplier, throwable);
   }

   public void info(Marker marker, CharSequence message) {
      this.info(message);
   }

   public void info(Marker marker, CharSequence message, Throwable throwable) {
      this.info(message, throwable);
   }

   public void info(Marker marker, Object message) {
      this.info(message);
   }

   public void info(Marker marker, Object message, Throwable throwable) {
      this.info(message, throwable);
   }

   public void info(Marker marker, String message) {
      this.info(message);
   }

   public void info(Marker marker, String message, Object... params) {
      this.info(message, params);
   }

   public void info(Marker marker, String message, Supplier<?>... paramSuppliers) {
      this.info(message, paramSuppliers);
   }

   public void info(Marker marker, String message, Throwable throwable) {
      this.info(message, throwable);
   }

   public void info(Marker marker, Supplier<?> messageSupplier) {
      this.info(messageSupplier);
   }

   public void info(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      this.info(messageSupplier, throwable);
   }

   public void info(Message message) {
      this.mainLogger.info(this.systemMarker, message);
   }

   public void info(Message message, Throwable throwable) {
      this.mainLogger.info(this.systemMarker, message, throwable);
   }

   public void info(MessageSupplier messageSupplier) {
      this.mainLogger.info(this.systemMarker, messageSupplier);
   }

   public void info(MessageSupplier messageSupplier, Throwable throwable) {
      this.mainLogger.info(this.systemMarker, messageSupplier, throwable);
   }

   public void info(CharSequence message) {
      this.mainLogger.info(this.systemMarker, message);
   }

   public void info(CharSequence message, Throwable throwable) {
      this.mainLogger.info(this.systemMarker, message, throwable);
   }

   public void info(Object message) {
      this.mainLogger.info(this.systemMarker, message);
   }

   public void info(Object message, Throwable throwable) {
      this.mainLogger.info(this.systemMarker, message, throwable);
   }

   public void info(String message) {
      this.mainLogger.info(this.systemMarker, message);
   }

   public void info(String message, Object... params) {
      this.mainLogger.info(this.systemMarker, message, params);
   }

   public void info(String message, Supplier<?>... paramSuppliers) {
      this.mainLogger.info(this.systemMarker, message, paramSuppliers);
   }

   public void info(String message, Throwable throwable) {
      this.mainLogger.info(this.systemMarker, message, throwable);
   }

   public void info(Supplier<?> messageSupplier) {
      this.mainLogger.info(this.systemMarker, messageSupplier);
   }

   public void info(Supplier<?> messageSupplier, Throwable throwable) {
      this.mainLogger.info(this.systemMarker, messageSupplier, throwable);
   }

   public void info(Marker marker, String message, Object p0) {
      this.info(message, p0);
   }

   public void info(Marker marker, String message, Object p0, Object p1) {
      this.info(message, p0, p1);
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2) {
      this.info(message, p0, p1, p2);
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      this.info(message, p0, p1, p2, p3);
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.info(message, p0, p1, p2, p3, p4);
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.info(message, p0, p1, p2, p3, p4, p5);
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.info(message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.info(message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.info(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void info(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      this.info(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public void info(String message, Object p0) {
      this.mainLogger.info(this.systemMarker, message, p0);
   }

   public void info(String message, Object p0, Object p1) {
      this.mainLogger.info(this.systemMarker, message, p0, p1);
   }

   public void info(String message, Object p0, Object p1, Object p2) {
      this.mainLogger.info(this.systemMarker, message, p0, p1, p2);
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3) {
      this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3);
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4);
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public boolean isDebugEnabled() {
      return this.mainLogger.isDebugEnabled(this.systemMarker);
   }

   public boolean isDebugEnabled(Marker marker) {
      return this.isDebugEnabled();
   }

   public boolean isEnabled(Level level) {
      return this.mainLogger.isEnabled(level, this.systemMarker);
   }

   public boolean isEnabled(Level level, Marker marker) {
      return this.isEnabled(level);
   }

   public boolean isErrorEnabled() {
      return this.mainLogger.isErrorEnabled(this.systemMarker);
   }

   public boolean isErrorEnabled(Marker marker) {
      return this.isErrorEnabled();
   }

   public boolean isFatalEnabled() {
      return this.mainLogger.isFatalEnabled(this.systemMarker);
   }

   public boolean isFatalEnabled(Marker marker) {
      return this.isFatalEnabled();
   }

   public boolean isInfoEnabled() {
      return this.mainLogger.isInfoEnabled(this.systemMarker);
   }

   public boolean isInfoEnabled(Marker marker) {
      return this.isInfoEnabled();
   }

   public boolean isTraceEnabled() {
      return this.mainLogger.isTraceEnabled(this.systemMarker);
   }

   public boolean isTraceEnabled(Marker marker) {
      return this.isTraceEnabled();
   }

   public boolean isWarnEnabled() {
      return this.mainLogger.isWarnEnabled(this.systemMarker);
   }

   public boolean isWarnEnabled(Marker marker) {
      return this.isWarnEnabled();
   }

   public void log(Level level, Marker marker, Message message) {
      this.log(level, message);
   }

   public void log(Level level, Marker marker, Message message, Throwable throwable) {
      this.log(level, message, throwable);
   }

   public void log(Level level, Marker marker, MessageSupplier messageSupplier) {
      this.log(level, messageSupplier);
   }

   public void log(Level level, Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      this.log(level, messageSupplier, throwable);
   }

   public void log(Level level, Marker marker, CharSequence message) {
      this.log(level, message);
   }

   public void log(Level level, Marker marker, CharSequence message, Throwable throwable) {
      this.log(level, message, throwable);
   }

   public void log(Level level, Marker marker, Object message) {
      this.log(level, message);
   }

   public void log(Level level, Marker marker, Object message, Throwable throwable) {
      this.log(level, message, throwable);
   }

   public void log(Level level, Marker marker, String message) {
      this.log(level, message);
   }

   public void log(Level level, Marker marker, String message, Object... params) {
      this.log(level, message, params);
   }

   public void log(Level level, Marker marker, String message, Supplier<?>... paramSuppliers) {
      this.log(level, message, paramSuppliers);
   }

   public void log(Level level, Marker marker, String message, Throwable throwable) {
      this.log(level, message, throwable);
   }

   public void log(Level level, Marker marker, Supplier<?> messageSupplier) {
      this.log(level, messageSupplier);
   }

   public void log(Level level, Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      this.log(level, messageSupplier, throwable);
   }

   public void log(Level level, Message message) {
      this.mainLogger.log(level, this.systemMarker, message);
   }

   public void log(Level level, Message message, Throwable throwable) {
      this.mainLogger.log(level, this.systemMarker, message, throwable);
   }

   public void log(Level level, MessageSupplier messageSupplier) {
      this.mainLogger.log(level, this.systemMarker, messageSupplier);
   }

   public void log(Level level, MessageSupplier messageSupplier, Throwable throwable) {
      this.mainLogger.log(level, this.systemMarker, messageSupplier, throwable);
   }

   public void log(Level level, CharSequence message) {
      this.mainLogger.log(level, this.systemMarker, message);
   }

   public void log(Level level, CharSequence message, Throwable throwable) {
      this.mainLogger.log(level, this.systemMarker, message, throwable);
   }

   public void log(Level level, Object message) {
      this.mainLogger.log(level, this.systemMarker, message);
   }

   public void log(Level level, Object message, Throwable throwable) {
      this.mainLogger.log(level, this.systemMarker, message, throwable);
   }

   public void log(Level level, String message) {
      this.mainLogger.log(level, this.systemMarker, message);
   }

   public void log(Level level, String message, Object... params) {
      this.mainLogger.log(level, this.systemMarker, message, params);
   }

   public void log(Level level, String message, Supplier<?>... paramSuppliers) {
      this.mainLogger.log(level, this.systemMarker, message, paramSuppliers);
   }

   public void log(Level level, String message, Throwable throwable) {
      this.mainLogger.log(level, this.systemMarker, message, throwable);
   }

   public void log(Level level, Supplier<?> messageSupplier) {
      this.mainLogger.log(level, this.systemMarker, messageSupplier);
   }

   public void log(Level level, Supplier<?> messageSupplier, Throwable throwable) {
      this.mainLogger.log(level, this.systemMarker, messageSupplier, throwable);
   }

   public void log(Level level, Marker marker, String message, Object p0) {
      this.log(level, message, p0);
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1) {
      this.log(level, message, p0, p1);
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
      this.log(level, message, p0, p1, p2);
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      this.log(level, message, p0, p1, p2, p3);
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.log(level, message, p0, p1, p2, p3, p4);
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.log(level, message, p0, p1, p2, p3, p4, p5);
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.log(level, message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void log(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void log(
      Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8
   ) {
      this.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void log(
      Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      this.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public void log(Level level, String message, Object p0) {
      this.mainLogger.log(level, this.systemMarker, message, p0);
   }

   public void log(Level level, String message, Object p0, Object p1) {
      this.mainLogger.log(level, this.systemMarker, message, p0, p1);
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2) {
      this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2);
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3) {
      this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3);
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4);
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5);
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public void printf(Level level, Marker marker, String format, Object... params) {
      this.printf(level, format, params);
   }

   public void printf(Level level, String format, Object... params) {
      this.mainLogger.printf(level, this.systemMarker, format, params);
   }

   public <T extends Throwable> T throwing(Level level, T throwable) {
      return (T)this.mainLogger.throwing(level, throwable);
   }

   public <T extends Throwable> T throwing(T throwable) {
      return (T)this.mainLogger.throwing(throwable);
   }

   public void trace(Marker marker, Message message) {
      this.trace(message);
   }

   public void trace(Marker marker, Message message, Throwable throwable) {
      this.trace(message, throwable);
   }

   public void trace(Marker marker, MessageSupplier messageSupplier) {
      this.trace(messageSupplier);
   }

   public void trace(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      this.trace(messageSupplier, throwable);
   }

   public void trace(Marker marker, CharSequence message) {
      this.trace(message);
   }

   public void trace(Marker marker, CharSequence message, Throwable throwable) {
      this.trace(message, throwable);
   }

   public void trace(Marker marker, Object message) {
      this.trace(message);
   }

   public void trace(Marker marker, Object message, Throwable throwable) {
      this.trace(message, throwable);
   }

   public void trace(Marker marker, String message) {
      this.trace(message);
   }

   public void trace(Marker marker, String message, Object... params) {
      this.trace(message, params);
   }

   public void trace(Marker marker, String message, Supplier<?>... paramSuppliers) {
      this.trace(message, paramSuppliers);
   }

   public void trace(Marker marker, String message, Throwable throwable) {
      this.trace(message, throwable);
   }

   public void trace(Marker marker, Supplier<?> messageSupplier) {
      this.trace(messageSupplier);
   }

   public void trace(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      this.trace(messageSupplier, throwable);
   }

   public void trace(Message message) {
      this.mainLogger.trace(this.systemMarker, message);
   }

   public void trace(Message message, Throwable throwable) {
      this.mainLogger.trace(this.systemMarker, message, throwable);
   }

   public void trace(MessageSupplier messageSupplier) {
      this.mainLogger.trace(this.systemMarker, messageSupplier);
   }

   public void trace(MessageSupplier messageSupplier, Throwable throwable) {
      this.mainLogger.trace(this.systemMarker, messageSupplier, throwable);
   }

   public void trace(CharSequence message) {
      this.mainLogger.trace(this.systemMarker, message);
   }

   public void trace(CharSequence message, Throwable throwable) {
      this.mainLogger.trace(this.systemMarker, message, throwable);
   }

   public void trace(Object message) {
      this.mainLogger.trace(this.systemMarker, message);
   }

   public void trace(Object message, Throwable throwable) {
      this.mainLogger.trace(this.systemMarker, message, throwable);
   }

   public void trace(String message) {
      this.mainLogger.trace(this.systemMarker, message);
   }

   public void trace(String message, Object... params) {
      this.mainLogger.trace(this.systemMarker, message, params);
   }

   public void trace(String message, Supplier<?>... paramSuppliers) {
      this.mainLogger.trace(this.systemMarker, message, paramSuppliers);
   }

   public void trace(String message, Throwable throwable) {
      this.mainLogger.trace(this.systemMarker, message, throwable);
   }

   public void trace(Supplier<?> messageSupplier) {
      this.mainLogger.trace(this.systemMarker, messageSupplier);
   }

   public void trace(Supplier<?> messageSupplier, Throwable throwable) {
      this.mainLogger.trace(this.systemMarker, messageSupplier, throwable);
   }

   public void trace(Marker marker, String message, Object p0) {
      this.trace(message, p0);
   }

   public void trace(Marker marker, String message, Object p0, Object p1) {
      this.trace(message, p0, p1);
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2) {
      this.trace(message, p0, p1, p2);
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      this.trace(message, p0, p1, p2, p3);
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.trace(message, p0, p1, p2, p3, p4);
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.trace(message, p0, p1, p2, p3, p4, p5);
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.trace(message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.trace(message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void trace(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.trace(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void trace(
      Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9
   ) {
      this.trace(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public void trace(String message, Object p0) {
      this.mainLogger.trace(this.systemMarker, message, p0);
   }

   public void trace(String message, Object p0, Object p1) {
      this.mainLogger.trace(this.systemMarker, message, p0, p1);
   }

   public void trace(String message, Object p0, Object p1, Object p2) {
      this.mainLogger.trace(this.systemMarker, message, p0, p1, p2);
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3) {
      this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3);
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4);
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void trace(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public EntryMessage traceEntry() {
      return this.mainLogger.traceEntry();
   }

   public EntryMessage traceEntry(String format, Object... params) {
      return this.mainLogger.traceEntry(format, params);
   }

   public EntryMessage traceEntry(Supplier<?>... paramSuppliers) {
      return this.mainLogger.traceEntry(paramSuppliers);
   }

   public EntryMessage traceEntry(String format, Supplier<?>... paramSuppliers) {
      return this.mainLogger.traceEntry(format, paramSuppliers);
   }

   public EntryMessage traceEntry(Message message) {
      return this.mainLogger.traceEntry(message);
   }

   public void traceExit() {
      this.mainLogger.traceExit();
   }

   public <R> R traceExit(R result) {
      return (R)this.mainLogger.traceExit(result);
   }

   public <R> R traceExit(String format, R result) {
      return (R)this.mainLogger.traceExit(format, result);
   }

   public void traceExit(EntryMessage message) {
      this.mainLogger.traceExit(message);
   }

   public <R> R traceExit(EntryMessage message, R result) {
      return (R)this.mainLogger.traceExit(message, result);
   }

   public <R> R traceExit(Message message, R result) {
      return (R)this.mainLogger.traceExit(message, result);
   }

   public void warn(Marker marker, Message message) {
      this.warn(message);
   }

   public void warn(Marker marker, Message message, Throwable throwable) {
      this.warn(message, throwable);
   }

   public void warn(Marker marker, MessageSupplier messageSupplier) {
      this.warn(messageSupplier);
   }

   public void warn(Marker marker, MessageSupplier messageSupplier, Throwable throwable) {
      this.warn(messageSupplier, throwable);
   }

   public void warn(Marker marker, CharSequence message) {
      this.warn(message);
   }

   public void warn(Marker marker, CharSequence message, Throwable throwable) {
      this.warn(message, throwable);
   }

   public void warn(Marker marker, Object message) {
      this.warn(message);
   }

   public void warn(Marker marker, Object message, Throwable throwable) {
      this.warn(message, throwable);
   }

   public void warn(Marker marker, String message) {
      this.warn(message);
   }

   public void warn(Marker marker, String message, Object... params) {
      this.warn(message, params);
   }

   public void warn(Marker marker, String message, Supplier<?>... paramSuppliers) {
      this.warn(message, paramSuppliers);
   }

   public void warn(Marker marker, String message, Throwable throwable) {
      this.warn(message, throwable);
   }

   public void warn(Marker marker, Supplier<?> messageSupplier) {
      this.warn(messageSupplier);
   }

   public void warn(Marker marker, Supplier<?> messageSupplier, Throwable throwable) {
      this.warn(messageSupplier, throwable);
   }

   public void warn(Message message) {
      this.mainLogger.warn(this.systemMarker, message);
   }

   public void warn(Message message, Throwable throwable) {
      this.mainLogger.warn(this.systemMarker, message, throwable);
   }

   public void warn(MessageSupplier messageSupplier) {
      this.mainLogger.warn(this.systemMarker, messageSupplier);
   }

   public void warn(MessageSupplier messageSupplier, Throwable throwable) {
      this.mainLogger.warn(this.systemMarker, messageSupplier, throwable);
   }

   public void warn(CharSequence message) {
      this.mainLogger.warn(this.systemMarker, message);
   }

   public void warn(CharSequence message, Throwable throwable) {
      this.mainLogger.warn(this.systemMarker, message, throwable);
   }

   public void warn(Object message) {
      this.mainLogger.warn(this.systemMarker, message);
   }

   public void warn(Object message, Throwable throwable) {
      this.mainLogger.warn(this.systemMarker, message, throwable);
   }

   public void warn(String message) {
      this.mainLogger.warn(this.systemMarker, message);
   }

   public void warn(String message, Object... params) {
      this.mainLogger.warn(this.systemMarker, message, params);
   }

   public void warn(String message, Supplier<?>... paramSuppliers) {
      this.mainLogger.warn(this.systemMarker, message, paramSuppliers);
   }

   public void warn(String message, Throwable throwable) {
      this.mainLogger.warn(this.systemMarker, message, throwable);
   }

   public void warn(Supplier<?> messageSupplier) {
      this.mainLogger.warn(this.systemMarker, messageSupplier);
   }

   public void warn(Supplier<?> messageSupplier, Throwable throwable) {
      this.mainLogger.warn(this.systemMarker, messageSupplier, throwable);
   }

   public void warn(Marker marker, String message, Object p0) {
      this.warn(message, p0);
   }

   public void warn(Marker marker, String message, Object p0, Object p1) {
      this.warn(message, p0, p1);
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2) {
      this.warn(message, p0, p1, p2);
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
      this.warn(message, p0, p1, p2, p3);
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.warn(message, p0, p1, p2, p3, p4);
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.warn(message, p0, p1, p2, p3, p4, p5);
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.warn(message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.warn(message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.warn(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void warn(Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      this.warn(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public void warn(String message, Object p0) {
      this.mainLogger.warn(this.systemMarker, message, p0);
   }

   public void warn(String message, Object p0, Object p1) {
      this.mainLogger.warn(this.systemMarker, message, p0, p1);
   }

   public void warn(String message, Object p0, Object p1, Object p2) {
      this.mainLogger.warn(this.systemMarker, message, p0, p1, p2);
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3) {
      this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3);
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
      this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4);
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
      this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
      this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
      this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
      this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
   }

   public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
      this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
   }

   public void logMessage(Level level, Marker marker, String fqcn, StackTraceElement location, Message message, Throwable throwable) {
      this.mainLogger.logMessage(level, this.systemMarker, fqcn, location, message, throwable);
   }

   public LogBuilder atTrace() {
      return this.mainLogger.atTrace();
   }

   public LogBuilder atDebug() {
      return this.mainLogger.atDebug();
   }

   public LogBuilder atInfo() {
      return this.mainLogger.atInfo();
   }

   public LogBuilder atWarn() {
      return this.mainLogger.atWarn();
   }

   public LogBuilder atError() {
      return this.mainLogger.atError();
   }

   public LogBuilder atFatal() {
      return this.mainLogger.atFatal();
   }

   public LogBuilder always() {
      return this.mainLogger.always();
   }

   public LogBuilder atLevel(Level level) {
      return this.mainLogger.atLevel(level);
   }
}
