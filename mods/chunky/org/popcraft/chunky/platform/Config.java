package org.popcraft.chunky.platform;

import java.nio.file.Path;

public interface Config {
   Path getDirectory();

   int getVersion();

   String getLanguage();

   boolean getContinueOnRestart();

   boolean isForceLoadExistingChunks();

   boolean isSilent();

   void setSilent(boolean var1);

   int getUpdateInterval();

   void setUpdateInterval(int var1);

   void reload();
}
