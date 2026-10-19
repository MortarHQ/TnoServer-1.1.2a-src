package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import org.jetbrains.annotations.NotNull;

public final class RecipeFileSystemProvider extends FileSystemProvider {
   public static final String SCHEME = "crt-recipe-fs";
   private static final Object LOCK = new Object();
   public static final String FILE_SYSTEM_NAME = "recipes";
   private RecipeFileSystem fileSystem = null;

   @Override
   public String getScheme() {
      return "crt-recipe-fs";
   }

   @Override
   public FileSystem newFileSystem(URI uri, Map<String, ?> env) {
      Objects.requireNonNull(env);
      this.checkUri(uri, false);
      Collection<ScriptRecipe> recipes = GenericUtil.uncheck(env.get("recipes"));
      if (recipes == null) {
         throw new IllegalArgumentException("Missing \"recipes\" environment data");
      }

      synchronized (LOCK) {
         if (this.fileSystem != null) {
            throw new FileSystemAlreadyExistsException();
         } else {
            return this.fileSystem = new RecipeFileSystem(this, recipes);
         }
      }
   }

   @Override
   public FileSystem newFileSystem(Path path, Map<String, ?> env) {
      Objects.requireNonNull(path);
      Objects.requireNonNull(env);
      throw new UnsupportedOperationException("Unable to access " + path + " as RecipeFS");
   }

   @Override
   public FileSystem getFileSystem(URI uri) {
      this.checkUri(uri, false);
      return this.getFileSystemFrom(uri);
   }

   @NotNull
   @Override
   public Path getPath(@NotNull URI uri) {
      this.checkUri(uri, true);
      String path = uri.getRawSchemeSpecificPart();
      if (path.isEmpty()) {
         throw new IllegalArgumentException("URI " + uri + " does not contain path");
      } else {
         return this.getFileSystemFrom(uri).getPath(path);
      }
   }

   @Override
   public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
      Objects.requireNonNull(options);
      Objects.requireNonNull(attrs);
      return this.path(path).seekableByteChannel(options, attrs);
   }

   @Override
   public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
      Objects.requireNonNull(filter);
      return this.path(dir).directoryStream(filter);
   }

   @Override
   public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
      Objects.requireNonNull(attrs);
      this.path(dir).directory(attrs);
   }

   @Override
   public void delete(Path path) throws IOException {
      this.path(path).yeet();
   }

   @Override
   public void copy(Path source, Path target, CopyOption... options) throws IOException {
      Objects.requireNonNull(target);
      Objects.requireNonNull(options);
      this.path(source).copyTo(target, options);
   }

   @Override
   public void move(Path source, Path target, CopyOption... options) throws IOException {
      Objects.requireNonNull(target);
      Objects.requireNonNull(options);
      this.path(source).moveTo(target, options);
   }

   @Override
   public boolean isSameFile(Path path, Path path2) throws IOException {
      Objects.requireNonNull(path2);
      return this.path(path).sameFile(path2);
   }

   @Override
   public boolean isHidden(Path path) throws IOException {
      return this.path(path).hidden();
   }

   @Override
   public FileStore getFileStore(Path path) throws IOException {
      return this.path(path).fileStore();
   }

   @Override
   public void checkAccess(Path path, AccessMode... modes) throws IOException {
      Objects.requireNonNull(modes);
      this.path(path).access(modes);
   }

   @Override
   public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options) {
      Objects.requireNonNull(type);
      Objects.requireNonNull(options);
      return this.path(path).fileAttributeView(type, options);
   }

   @Override
   public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options) throws IOException {
      Objects.requireNonNull(type);
      Objects.requireNonNull(options);
      return this.path(path).attributes(type, options);
   }

   @Override
   public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
      Objects.requireNonNull(attributes);
      Objects.requireNonNull(options);
      return this.path(path).attributes(attributes, options);
   }

   @Override
   public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
      Objects.requireNonNull(attribute);
      Objects.requireNonNull(value);
      Objects.requireNonNull(options);
      this.path(path).attributes(attribute, value, options);
   }

   @Override
   public InputStream newInputStream(Path path, OpenOption... options) throws IOException {
      Objects.requireNonNull(options);
      return this.path(path).input(options);
   }

   @Override
   public OutputStream newOutputStream(Path path, OpenOption... options) throws IOException {
      Objects.requireNonNull(options);
      return this.path(path).output(options);
   }

   @Override
   public FileChannel newFileChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
      Objects.requireNonNull(options);
      Objects.requireNonNull(attrs);
      return this.path(path).fileChannel(options, attrs);
   }

   @Override
   public AsynchronousFileChannel newAsynchronousFileChannel(Path path, Set<? extends OpenOption> options, ExecutorService executor, FileAttribute<?>... attrs) throws IOException {
      Objects.requireNonNull(options);
      Objects.requireNonNull(attrs);
      return this.path(path).asyncFileChannel(options, executor, attrs);
   }

   @Override
   public void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs) throws IOException {
      Objects.requireNonNull(target);
      Objects.requireNonNull(attrs);
      this.path(link).symLink(target, attrs);
   }

   @Override
   public void createLink(Path link, Path existing) throws IOException {
      Objects.requireNonNull(existing);
      this.path(link).link(existing);
   }

   @Override
   public boolean deleteIfExists(Path path) throws IOException {
      return this.path(path).yeetExisting();
   }

   @Override
   public Path readSymbolicLink(Path link) throws IOException {
      return this.path(link).symLink();
   }

   void closing() {
      synchronized (LOCK) {
         this.fileSystem = null;
      }
   }

   private void checkUri(URI uri, boolean path) {
      if (!"crt-recipe-fs".equals(uri.getScheme())) {
         throw new IllegalArgumentException("Unable to create a file system with a non-" + this.getScheme() + " URI");
      }

      if (!path) {
         if (!"recipes".equals(uri.getRawSchemeSpecificPart())) {
            throw new IllegalArgumentException("Unable to create a file system with URI " + uri);
         }
      }
   }

   private FileSystem getFileSystemFrom(URI uri) {
      synchronized (LOCK) {
         return Objects.requireNonNull(this.fileSystem, () -> "No file system created with URI " + uri);
      }
   }

   private RecipePath path(Path path) {
      if (Objects.requireNonNull(path) instanceof RecipePath recipePath) {
         return recipePath;
      } else {
         throw new ProviderMismatchException("Mismatch with path " + path);
      }
   }
}
