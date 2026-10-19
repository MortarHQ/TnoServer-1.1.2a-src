package de.cristelknight.cristellib.neoforge;

import de.cristelknight.cristellib.CristelLib;
import java.util.Optional;
import javax.annotation.Nullable;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

public class ModLoadingUtilImpl {
   public static boolean isModLoaded(String modID) {
      ModList modList = ModList.get();
      return modList != null ? modList.isLoaded(modID) : isModPreLoaded(modID);
   }

   public static boolean isModPreLoaded(String modID) {
      return getPreLoadedModInfo(modID) != null;
   }

   @Nullable
   public static ModInfo getPreLoadedModInfo(String modID) {
      for (ModInfo info : LoadingModList.get().getMods()) {
         if (info.getModId().equals(modID)) {
            return info;
         }
      }

      return null;
   }

   public static Optional<Integer> compare(String modID, String version) {
      if (isModLoaded(modID)) {
         ModList modList = ModList.get();
         ArtifactVersion modVersion;
         if (modList != null) {
            modVersion = ((ModContainer)modList.getModContainerById(modID).get()).getModInfo().getVersion();
         } else {
            modVersion = getPreLoadedModVersion(modID);
         }

         ArtifactVersion min = new DefaultArtifactVersion(version);
         return Optional.of(modVersion.compareTo(min));
      } else {
         return Optional.empty();
      }
   }

   public static ArtifactVersion getPreLoadedModVersion(String modID) {
      ModInfo info = getPreLoadedModInfo(modID);
      if (info == null) {
         throw new RuntimeException(CristelLib.getWithPrefix("Couldn't find mod: " + modID));
      } else {
         return info.getVersion();
      }
   }
}
