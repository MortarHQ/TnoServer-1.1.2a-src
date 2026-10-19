package team.creative.creativecore.common.util.mc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import net.minecraft.FileUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.resources.Resource;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.mixin.FilePackResourcesAccessor;
import team.creative.creativecore.mixin.PathPackResourcesAccessor;
import team.creative.creativecore.mixin.SharedZipFileAccessAccessor;
import team.creative.creativecore.mixin.VanillaPackResourcesAccessor;

public class ResourceUtils {
   public static long length(PackType type, Resource resource, ResourceLocation location) {
      return length(type, resource.source(), location);
   }

   public static long length(PackType type, PackResources source, ResourceLocation location) {
      if (source instanceof FilePackResourcesAccessor zip) {
         ZipEntry entry = ((SharedZipFileAccessAccessor)zip.getZipFileAccess())
            .callGetOrCreateZipFile()
            .getEntry(FilePackResourcesAccessor.callGetPathFromLocation(type, location));
         return entry != null ? entry.getSize() : 0L;
      } else {
         Path path = (Path)FileUtil.decomposePath(location.getPath())
            .mapOrElse(
               x -> {
                  if (source instanceof VanillaPackResourcesAccessor vanilla) {
                     return resolve(vanilla.getPathsForType().get(type), location, x);
                  } else {
                     return source instanceof PathPackResourcesAccessor pack
                        ? FileUtil.resolvePath(pack.getRoot().resolve(type.getDirectory()).resolve(location.getNamespace()), x)
                        : PlatformResourceUtils.resolvePath(type, source, location, x);
                  }
               },
               x -> {
                  CreativeCore.LOGGER.error("Invalid path {}: {}", location, x.message());
                  return null;
               }
            );
         return path != null ? PlatformResourceUtils.length(path) : 0L;
      }
   }

   private static Path resolve(List<Path> rootPaths, ResourceLocation location, List<String> parts) {
      for (Path path : rootPaths) {
         Path path1 = FileUtil.resolvePath(path.resolve(location.getNamespace()), parts);
         if (Files.exists(path1) && PathPackResources.validatePath(path1)) {
            return path1;
         }
      }

      return null;
   }
}
