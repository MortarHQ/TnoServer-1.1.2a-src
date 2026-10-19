package de.cristelknight.cristellib;

import de.cristelknight.cristellib.neoforge.CristelLibExpectPlatformImpl;
import de.cristelknight.cristellib.util.Platform;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import org.jetbrains.annotations.Nullable;

public class CristelLibExpectPlatform {
   @ExpectPlatform
   @Transformed
   public static Path getConfigDirectory() {
      return CristelLibExpectPlatformImpl.getConfigDirectory();
   }

   @ExpectPlatform
   @Nullable
   @Transformed
   public static Path getResourceDirectory(String modid, String subPath) {
      return CristelLibExpectPlatformImpl.getResourceDirectory(modid, subPath);
   }

   @ExpectPlatform
   @Transformed
   public static PackResources registerBuiltinResourcePack(ResourceLocation id, Component displayName) {
      return CristelLibExpectPlatformImpl.registerBuiltinResourcePack(id, displayName);
   }

   @ExpectPlatform
   @Transformed
   public static List<Path> getRootPaths(String modId) {
      return CristelLibExpectPlatformImpl.getRootPaths(modId);
   }

   @ExpectPlatform
   @Transformed
   public static Map<String, Set<StructureConfig>> getConfigs(CristelLibRegistry registry) {
      return CristelLibExpectPlatformImpl.getConfigs(registry);
   }

   @ExpectPlatform
   @Transformed
   public static String getModDisplayName(String modID) {
      return CristelLibExpectPlatformImpl.getModDisplayName(modID);
   }

   @ExpectPlatform
   @Transformed
   public static Platform getPlatform() {
      return CristelLibExpectPlatformImpl.getPlatform();
   }

   @ExpectPlatform
   @Transformed
   public static boolean isClient() {
      return CristelLibExpectPlatformImpl.isClient();
   }

   @ExpectPlatform
   @Transformed
   public static List<String> getModIds() {
      return CristelLibExpectPlatformImpl.getModIds();
   }
}
