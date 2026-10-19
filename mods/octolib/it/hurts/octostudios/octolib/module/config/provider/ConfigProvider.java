package it.hurts.octostudios.octolib.module.config.provider;

import it.hurts.octostudios.octolib.module.config.cfgbuilder.CompoundEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.ConfigEntry;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public interface ConfigProvider {
   ConfigEntry createPattern(Object var1);

   void save(Writer var1, Object var2);

   void saveAll(Writer var1, Iterator<?> var2);

   Object load(Reader var1, CompoundEntry var2);

   <T> List<T> loadAll(Reader var1, Iterator<CompoundEntry> var2);

   <T> T insert2ndStep(T var1, T var2);
}
