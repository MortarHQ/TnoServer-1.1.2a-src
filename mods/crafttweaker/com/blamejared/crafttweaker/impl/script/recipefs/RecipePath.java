package com.blamejared.crafttweaker.impl.script.recipefs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

final class RecipePath implements Path {
   private static final String[] EMPTY_COMPONENTS = new String[0];
   private static final String[] ROOT_COMPONENTS = new String[0];
   private final RecipeFileSystem fs;
   private final String[] components;
   private final int last;
   private final boolean absolute;
   private int hash;

   private RecipePath(RecipeFileSystem fs, String[] components, boolean absolute) {
      this.fs = fs;
      this.last = (this.components = components).length - 1;
      this.absolute = absolute;
      this.hash = 0;
   }

   static RecipePath of(RecipeFileSystem fs, String toParse) {
      Objects.requireNonNull(fs);
      Objects.requireNonNull(toParse);
      if (toParse.isEmpty()) {
         return emptyPath(fs);
      }

      boolean absolute = toParse.charAt(0) == '/';
      String path = absolute ? toParse.substring(1) : toParse;
      if (path.isEmpty()) {
         return rootPath(fs);
      }

      String slightlyNormalized = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
      String[] components = slightlyNormalized.split(Pattern.quote("/"));
      assert components.length != 0 : "Waku waku";

      for (String component : components) {
         if (component.isEmpty()) {
            throw new IllegalArgumentException("Invalid path " + toParse);
         }
      }

      return new RecipePath(fs, components, absolute);
   }

   private static RecipePath emptyPath(RecipeFileSystem fs) {
      return new RecipePath(fs, EMPTY_COMPONENTS, false);
   }

   private static RecipePath rootPath(RecipeFileSystem fs) {
      return new RecipePath(fs, ROOT_COMPONENTS, true);
   }

   @NotNull
   @Override
   public FileSystem getFileSystem() {
      return this.fs;
   }

   @Override
   public boolean isAbsolute() {
      return this.absolute;
   }

   @Override
   public Path getRoot() {
      return this.isAbsolute() ? rootPath(this.fs) : null;
   }

   @Override
   public Path getFileName() {
      if (this.isEmpty()) {
         return this;
      } else {
         return this.isRoot() ? null : of(this.fs, this.components[this.last]);
      }
   }

   @Override
   public Path getParent() {
      if (this.isRoot()) {
         return null;
      }

      String path = this.computeSubPath(0, this.getNameCount() - 1, true);
      return of(this.fs, path);
   }

   @Override
   public int getNameCount() {
      return this.isRoot() ? 0 : this.last + 1;
   }

   @NotNull
   @Override
   public Path getName(int index) {
      if (!this.isRoot() && index >= 0 && index <= this.last) {
         return of(this.fs, this.components[index]);
      } else {
         throw new IllegalArgumentException(Integer.toString(index));
      }
   }

   @NotNull
   @Override
   public Path subpath(int beginIndex, int endIndex) {
      if (this.isRoot()) {
         throw new IllegalArgumentException();
      } else if (beginIndex < 0 || beginIndex > this.last) {
         throw new IllegalArgumentException(Integer.toString(beginIndex));
      } else if (endIndex >= 0 && endIndex <= this.last && endIndex > beginIndex) {
         String sub = this.computeSubPath(beginIndex, endIndex, false);
         return of(this.fs, sub);
      } else {
         throw new IllegalArgumentException(Integer.toString(endIndex));
      }
   }

   @Override
   public boolean startsWith(@NotNull Path other) {
      Objects.requireNonNull(other);
      if (other instanceof RecipePath path) {
         if (this.absolute != path.absolute) {
            return false;
         }

         if (this.isEmpty()) {
            return path.isEmpty();
         }

         if (this.isRoot()) {
            return path.isRoot();
         }

         int pathCount = path.getNameCount();
         if (this.getNameCount() < pathCount) {
            return false;
         }

         for (int i = 0; i < pathCount; i++) {
            if (!this.components[i].equals(path.components[i])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean endsWith(@NotNull Path other) {
      Objects.requireNonNull(other);
      if (other instanceof RecipePath path) {
         if (this.absolute != path.absolute) {
            return false;
         }

         if (this.isEmpty()) {
            return path.isEmpty();
         }

         if (this.isRoot()) {
            return path.isRoot();
         }

         int ourCount = this.getNameCount();
         int pathCount = path.getNameCount();
         if (ourCount < pathCount) {
            return false;
         }

         for (int i = 0; i < pathCount; i++) {
            if (!this.components[ourCount - 1 - i].equals(path.components[pathCount - 1 - i])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @NotNull
   @Override
   public Path normalize() {
      return !this.isRoot() && !this.isEmpty() ? of(this.fs, this.normalizePath()) : this;
   }

   @NotNull
   @Override
   public Path resolve(@NotNull Path other) {
      Objects.requireNonNull(other);
      if (other instanceof RecipePath path) {
         if (path.isAbsolute()) {
            return path;
         }

         if (path.isEmpty()) {
            return this;
         }

         int thisLength = this.components.length;
         int theirLength = path.components.length;
         int newLength = thisLength + theirLength;
         String[] newComponents = new String[newLength];
         System.arraycopy(this.components, 0, newComponents, 0, thisLength);
         System.arraycopy(path.components, 0, newComponents, thisLength, theirLength);
         return of(this.fs, this.computeSubPath(0, newLength, this.absolute, newComponents));
      } else {
         throw new ProviderMismatchException();
      }
   }

   @NotNull
   @Override
   public Path relativize(@NotNull Path other) {
      Objects.requireNonNull(other);
      if (!(other instanceof RecipePath path)) {
         throw new ProviderMismatchException();
      } else {
         if (this.equals(other)) {
            return emptyPath(this.fs);
         }

         boolean thisAbsolute = this.isAbsolute();
         if (thisAbsolute != path.isAbsolute()) {
            throw new IllegalArgumentException("Cannot relativize with mismatching path types");
         }

         if (this.isEmpty()) {
            return path;
         }

         int divergenceIndex = -1;
         int ourComponents = this.getNameCount();
         int theirComponents = path.getNameCount();
         int min = Math.min(ourComponents, theirComponents);

         for (int i = 0; i < min; i++) {
            String ourComponent = this.components[i];
            String theirComponent = path.components[i];
            if (!ourComponent.equals(theirComponent)) {
               divergenceIndex = i;
               break;
            }
         }

         if (divergenceIndex == -1) {
            if (theirComponents < ourComponents) {
               int walkUpLength = ourComponents - theirComponents;
               String[] newComponents = new String[walkUpLength];
               Arrays.fill(newComponents, "..");
               return of(this.fs, this.computeSubPath(0, walkUpLength, false, newComponents));
            } else {
               return of(this.fs, path.computeSubPath(ourComponents, theirComponents, false));
            }
         } else {
            int upwardsWalkerLength = ourComponents - divergenceIndex;
            String[] subComponents = ((RecipePath)path.subpath(divergenceIndex, theirComponents)).components;
            int length = upwardsWalkerLength + subComponents.length;
            String[] newComponents = new String[length];

            for (int i = 0; i < upwardsWalkerLength; i++) {
               newComponents[i] = "..";
            }

            System.arraycopy(subComponents, 0, newComponents, upwardsWalkerLength, subComponents.length);
            return of(this.fs, this.computeSubPath(0, length, false, newComponents));
         }
      }
   }

   @NotNull
   @Override
   public URI toUri() {
      try {
         return new URI("%s:%s".formatted("crt-recipe-fs", this));
      } catch (URISyntaxException e) {
         throw new IllegalStateException(e);
      }
   }

   @NotNull
   @Override
   public Path toAbsolutePath() {
      if (this.isAbsolute()) {
         return this;
      } else {
         return this.isEmpty() ? rootPath(this.fs) : of(this.fs, this.computeSubPath(0, this.components.length, true));
      }
   }

   @NotNull
   @Override
   public Path toRealPath(@NotNull LinkOption... options) {
      Objects.requireNonNull(options);
      return this.toAbsolutePath();
   }

   @NotNull
   @Override
   public WatchKey register(@NotNull WatchService watcher, @NotNull Kind<?>[] events, Modifier... modifiers) {
      Objects.requireNonNull(watcher);
      Objects.requireNonNull(events);
      Objects.requireNonNull(modifiers);
      throw new UnsupportedOperationException("Watches not supported");
   }

   @Override
   public int compareTo(@NotNull Path other) {
      RecipePath path = Objects.requireNonNull(other);
      if (this.isEmpty()) {
         return path.isEmpty() ? 0 : 1;
      } else if (this.isRoot()) {
         return path.isRoot() ? 0 : -1;
      } else {
         return Arrays.compare(this.components, path.components);
      }
   }

   @Override
   public boolean equals(Object obj) {
      return obj == this || obj instanceof RecipePath path && this.compareTo(path) == 0;
   }

   @Override
   public int hashCode() {
      return this.hash != 0 ? this.hash : (this.hash = Objects.hash(this.absolute, Arrays.hashCode(this.components)));
   }

   @Override
   public String toString() {
      return this.computeSubPath(0, this.components.length, this.isAbsolute());
   }

   SeekableByteChannel seekableByteChannel(Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
      return this.fs.seekableByteChannel(this, options, attrs);
   }

   DirectoryStream<Path> directoryStream(Filter<? super Path> filter) throws IOException {
      return this.fs.directoryStream(this, filter);
   }

   void directory(FileAttribute<?>... attributes) throws IOException {
      this.fs.directory(this, attributes);
   }

   void yeet() throws IOException {
      this.fs.yeet(this);
   }

   void copyTo(Path destination, CopyOption... options) throws IOException {
      this.fs.copyTo(this, destination, options);
   }

   void moveTo(Path destination, CopyOption... options) throws IOException {
      this.fs.moveTo(this, destination, options);
   }

   boolean sameFile(Path other) throws IOException {
      return this.fs.sameFile(this, other);
   }

   boolean hidden() throws IOException {
      return this.fs.hidden(this);
   }

   FileStore fileStore() throws IOException {
      return this.fs.fileStore(this);
   }

   void access(AccessMode... modes) throws IOException {
      this.fs.access(this, modes);
   }

   <V extends FileAttributeView> V fileAttributeView(Class<V> type, LinkOption... options) {
      return this.fs.fileAttributeView(this, type, options);
   }

   <A extends BasicFileAttributes> A attributes(Class<A> type, LinkOption... options) throws IOException {
      return this.fs.attributes(this, type, options);
   }

   Map<String, Object> attributes(String attributes, LinkOption... options) throws IOException {
      return this.fs.attributes(this, attributes, options);
   }

   void attributes(String attribute, Object value, LinkOption... options) throws IOException {
      this.fs.attributes(this, attribute, value, options);
   }

   InputStream input(OpenOption... options) throws IOException {
      return this.fs.input(this, options);
   }

   OutputStream output(OpenOption... options) throws IOException {
      return this.fs.output(this, options);
   }

   FileChannel fileChannel(Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
      return this.fs.fileChannel(this, options, attrs);
   }

   AsynchronousFileChannel asyncFileChannel(Set<? extends OpenOption> options, Executor executor, FileAttribute<?>... attrs) throws IOException {
      return this.fs.asyncFileChannel(this, options, executor, attrs);
   }

   void symLink(Path target, FileAttribute<?>... attrs) throws IOException {
      this.fs.symLink(this, target, attrs);
   }

   void link(Path target) throws IOException {
      this.fs.link(this, target);
   }

   boolean yeetExisting() throws IOException {
      return this.fs.yeetExisting(this);
   }

   Path symLink() throws IOException {
      return this.fs.symLink(this);
   }

   private boolean isEmpty() {
      return this.components == EMPTY_COMPONENTS;
   }

   private boolean isRoot() {
      return this.components == ROOT_COMPONENTS;
   }

   private String normalizePath() {
      String[] oldComponents = this.components;
      List<String> components = new ArrayList<>();
      int idx = 0;

      for (String oldComponent : oldComponents) {
         if (!".".equals(oldComponent)) {
            if ("..".equals(oldComponent)) {
               components.remove(--idx);
            } else {
               components.add(oldComponent);
               idx++;
            }
         }
      }

      return this.computeSubPath(0, idx, this.absolute, components.toArray(String[]::new));
   }

   private String computeSubPath(int begin, int end, boolean absolute) {
      return this.computeSubPath(begin, end, absolute, this.components);
   }

   private String computeSubPath(int begin, int end, boolean absolute, String[] components) {
      StringBuilder builder = new StringBuilder(absolute ? "/" : "");

      for (int i = begin; i < end; i++) {
         builder.append(components[i]).append('/');
      }

      if (end - begin > 0) {
         builder.deleteCharAt(builder.length() - 1);
      }

      return builder.toString();
   }
}
