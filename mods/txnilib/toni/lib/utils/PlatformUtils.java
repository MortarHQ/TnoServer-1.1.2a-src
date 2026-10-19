package toni.lib.utils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModInfo;

public class PlatformUtils {
   public static boolean isDedicatedServer() {
      return FMLLoader.getDist() == Dist.DEDICATED_SERVER;
   }

   public static boolean isModLoaded(String modid) {
      return ModList.get() == null
         ? LoadingModList.get().getMods().stream().<String>map(ModInfo::getModId).anyMatch(modid::equals)
         : ModList.get().isLoaded(modid);
   }

   public static String getModName(String modId) {
      return ModList.get().getModContainerById(modId).map(container -> container.getModInfo().getDisplayName()).orElse(modId);
   }

   public static boolean isFabric() {
      return false;
   }

   public static boolean isForge() {
      return false;
   }

   public static boolean isNeo() {
      return true;
   }

   public static boolean isForgelike() {
      return true;
   }
}
