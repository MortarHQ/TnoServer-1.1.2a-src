package xaero.common.platform.services;

import java.nio.file.Path;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.LoadingModList;

public class NeoForgePlatformHelper implements IPlatformHelper {
   @Override
   public String getPlatformName() {
      return "NeoForge";
   }

   @Override
   public boolean isModLoaded(String modId) {
      return ModList.get().isLoaded(modId);
   }

   @Override
   public boolean checkModForMixin(String modId) {
      return LoadingModList.get().getModFileById(modId) != null;
   }

   @Override
   public boolean isDevelopmentEnvironment() {
      return !FMLLoader.isProduction();
   }

   @Override
   public boolean isDedicatedServer() {
      return FMLLoader.getDist() == Dist.DEDICATED_SERVER;
   }

   @Override
   public Path getGameDir() {
      return FMLPaths.GAMEDIR.get();
   }

   @Override
   public Path getConfigDir() {
      return FMLPaths.CONFIGDIR.get();
   }

   @Override
   public Path getModFile(String modId) {
      return FMLLoader.getLoadingModList().getModFileById(modId).getFile().getFilePath();
   }
}
