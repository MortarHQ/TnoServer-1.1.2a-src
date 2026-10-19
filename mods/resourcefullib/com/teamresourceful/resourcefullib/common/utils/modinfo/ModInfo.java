package com.teamresourceful.resourcefullib.common.utils.modinfo;

import java.nio.file.Path;
import java.util.List;

public interface ModInfo {
   String displayName();

   String id();

   String version();

   List<Path> getPaths();
}
