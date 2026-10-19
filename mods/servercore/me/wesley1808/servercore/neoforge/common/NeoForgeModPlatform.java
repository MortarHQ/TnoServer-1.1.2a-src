package me.wesley1808.servercore.neoforge.common;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import me.wesley1808.servercore.common.services.platform.ModPlatform;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.LoadingModList;

public class NeoForgeModPlatform implements ModPlatform {
   private final Path configDir = FMLPaths.CONFIGDIR.get();
   private String version;

   @Override
   public boolean isClient() {
      return FMLEnvironment.dist.isClient();
   }

   @Override
   public boolean isModLoaded(String modId) {
      ModList modList = ModList.get();
      if (modList != null) {
         return modList.isLoaded(modId);
      }

      LoadingModList loadingModList = LoadingModList.get();
      return loadingModList != null && loadingModList.getModFileById(modId) != null;
   }

   @Override
   public Path getConfigDir() {
      return this.configDir;
   }

   @Override
   public String getVersion() {
      if (this.version == null) {
         ModList modList = ModList.get();
         Optional<? extends ModContainer> optional = modList != null ? modList.getModContainerById("servercore") : Optional.empty();
         this.version = optional.<String>map(container -> container.getModInfo().getVersion().getQualifier()).orElse(null);
      }

      return Objects.requireNonNullElse(this.version, "Unknown");
   }
}
