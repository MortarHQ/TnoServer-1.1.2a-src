package com.blamejared.crafttweaker.impl.datamap;

import java.util.List;
import java.util.Map;
import net.neoforged.neoforge.registries.datamaps.DataMapFile;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public interface LoadResultGetter {
   Map<DataMapType<?, ?>, List<DataMapFile<?, ?>>> results();
}
