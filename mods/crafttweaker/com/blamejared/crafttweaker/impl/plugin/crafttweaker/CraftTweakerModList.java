package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.mod.PlatformMod;
import com.google.common.collect.Collections2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;
import net.minecraft.Optionull;
import org.apache.logging.log4j.Logger;

final class CraftTweakerModList {
   private final SortedSet<CraftTweakerModList.DiscoveredMod> addedMods = new TreeSet<>(Comparator.comparing(CraftTweakerModList.DiscoveredMod::id));

   void add(PlatformMod info) {
      this.addedMods.add(this.toDiscoveredMod(info));
   }

   void printToLog() {
      Logger logger = CraftTweakerCommon.logger();
      logger.info("The following mods have explicit CraftTweaker support, provided through the CraftTweaker compatibility plugin:");
      Collections2.transform(this.addedMods, this::formatInfo).forEach(logger::info);
   }

   private CraftTweakerModList.DiscoveredMod toDiscoveredMod(PlatformMod info) {
      return new CraftTweakerModList.DiscoveredMod(info.id(), info.version(), this.discoverSupports(info));
   }

   private String[] discoverSupports(PlatformMod info) {
      Path supportFiles = info.file("/META-INF/crafttweaker/supports.list");

      try (Stream<String> lines = Files.lines(supportFiles)) {
         return lines.toArray(String[]::new);
      } catch (IOException e) {
         return null;
      }
   }

   private String formatInfo(CraftTweakerModList.DiscoveredMod info) {
      return "- '%s' version '%s'%s"
         .formatted(info.id(), info.version(), Optionull.mapOrElse(info.namedSupports(), it -> ", supporting mods " + String.join(", ", it), () -> ""));
   }

   private record DiscoveredMod(String id, String version, String[] namedSupports) {
   }
}
