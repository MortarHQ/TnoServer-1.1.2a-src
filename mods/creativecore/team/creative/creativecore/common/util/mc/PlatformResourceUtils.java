package team.creative.creativecore.common.util.mc;

import cpw.mods.niofs.union.UnionFileSystem;
import cpw.mods.niofs.union.UnionPath;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import team.creative.creativecore.mixin.PathPackResourcesAccessor;

public class PlatformResourceUtils {
   public static long length(Path path) {
      if (path.getFileSystem() instanceof UnionFileSystem u) {
         try {
            return u.readAttributes((UnionPath)path, BasicFileAttributes.class, new LinkOption[0]).size();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      return path.toFile().length();
   }

   public static Path resolvePath(PackType type, PackResources source, ResourceLocation location, List<String> parts) {
      if (!(source instanceof PathPackResourcesAccessor forge)) {
         return null;
      } else {
         Path path = forge.getRoot().resolve(type.getDirectory()).resolve(location.getNamespace());

         for (String name : parts) {
            path = path.resolve(name);
         }

         return path;
      }
   }
}
