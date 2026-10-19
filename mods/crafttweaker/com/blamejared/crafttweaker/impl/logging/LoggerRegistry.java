package com.blamejared.crafttweaker.impl.logging;

import com.blamejared.crafttweaker.api.logging.ILoggerRegistry;
import com.google.common.base.Suppliers;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LoggerRegistry implements ILoggerRegistry {
   private static final Supplier<LoggerRegistry> INSTANCE = Suppliers.memoize(LoggerRegistry::new);
   private final Logger mainLogger;
   private final Map<String, Logger> loggers;

   private LoggerRegistry() {
      boolean forward = Boolean.parseBoolean(System.getenv("crafttweaker.logger.forward_to_latest_log"));
      Logger crtLog = LogManager.getLogger("CRT_LOG_FILE");
      this.mainLogger = forward ? ForwardingLogger.of("CT_FORWARDING_LOGGER", crtLog, LogManager.getLogger("CraftTweaker")) : crtLog;
      this.loggers = new ConcurrentHashMap<>();
   }

   public static LoggerRegistry get() {
      return INSTANCE.get();
   }

   @Override
   public Logger getLoggerFor(String system) {
      return this.loggers.computeIfAbsent(system, it -> SystemLogger.of("CT_SYSTEM_LOG_" + it, this.mainLogger, it));
   }
}
