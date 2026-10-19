package org.openzen.zencode.shared.logging;

public interface IZSLogger {
   void info(String var1);

   void debug(String var1);

   void trace(String var1);

   void warning(String var1);

   void error(String var1);

   void throwingErr(String var1, Throwable var2);

   void throwingWarn(String var1, Throwable var2);
}
