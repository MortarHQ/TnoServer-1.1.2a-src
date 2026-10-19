package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import java.nio.file.Path;
import java.util.function.Supplier;

public final class PathUtil {
   private static final Supplier<Path> GAME_DIRECTORY = Suppliers.memoize(Services.PLATFORM::getGameDirectory);

   private PathUtil() {
   }

   public static Path findFromGameDirectory(String other) {
      return findFromGameDirectory(makeSameFileSystemPath(GAME_DIRECTORY.get(), other));
   }

   public static Path findFromGameDirectory(Path other) {
      return GAME_DIRECTORY.get().resolve(other);
   }

   public static Path makeRelativeToGameDirectory(String other) {
      return makeRelativeToGameDirectory(makeSameFileSystemPath(GAME_DIRECTORY.get(), other));
   }

   public static Path makeRelativeToGameDirectory(Path other) {
      return GAME_DIRECTORY.get().toAbsolutePath().relativize(other.toAbsolutePath());
   }

   public static Path makeSameFileSystemPath(Path original, String other) {
      return original.getFileSystem().getPath(other);
   }
}
