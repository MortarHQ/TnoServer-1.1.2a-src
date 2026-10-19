package cc.sighs.oelib.config.spi;

import cc.sighs.oelib.config.api.ConfigChangedEvent;
import cc.sighs.oelib.config.api.ConfigLoadEvent;
import cc.sighs.oelib.config.api.ConfigSaveEvent;
import cc.sighs.oelib.config.api.ConfigSyncEvent;

public interface IConfigEventDispatcher {
   <T> void fireLoad(ConfigLoadEvent<T> var1);

   <T> void fireBeforeSave(ConfigSaveEvent<T> var1);

   <T> void fireAfterSave(ConfigSaveEvent<T> var1);

   <T> void fireChanged(ConfigChangedEvent<T> var1);

   <T> void fireSync(ConfigSyncEvent<T> var1);
}
