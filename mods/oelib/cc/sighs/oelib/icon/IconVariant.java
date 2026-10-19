package cc.sighs.oelib.icon;

import java.util.Objects;

public final class IconVariant {
   private final String name;
   private final String path;
   private final int weight;
   private final IconVariant.Platform platform;

   private IconVariant(String name, String path, int weight, IconVariant.Platform platform) {
      this.name = name;
      this.path = path;
      this.weight = Math.max(1, weight);
      this.platform = platform;
   }

   public static IconVariant name(String name, int weight) {
      return new IconVariant(Objects.requireNonNull(name), null, weight, IconVariant.Platform.COMMON);
   }

   public static IconVariant path(String path, int weight) {
      return new IconVariant(null, Objects.requireNonNull(path), weight, IconVariant.Platform.COMMON);
   }

   public static IconVariant nameFabric(String name, int weight) {
      return new IconVariant(Objects.requireNonNull(name), null, weight, IconVariant.Platform.FABRIC);
   }

   public static IconVariant pathFabric(String path, int weight) {
      return new IconVariant(null, Objects.requireNonNull(path), weight, IconVariant.Platform.FABRIC);
   }

   public static IconVariant nameNeoForge(String name, int weight) {
      return new IconVariant(Objects.requireNonNull(name), null, weight, IconVariant.Platform.NeoForge);
   }

   public static IconVariant pathNeoForge(String path, int weight) {
      return new IconVariant(null, Objects.requireNonNull(path), weight, IconVariant.Platform.NeoForge);
   }

   public String getName() {
      return this.name;
   }

   public String getPath() {
      return this.path;
   }

   public int getWeight() {
      return this.weight;
   }

   public IconVariant.Platform getPlatform() {
      return this.platform;
   }

   boolean isName() {
      return this.name != null;
   }

   boolean isPath() {
      return this.path != null;
   }

   boolean appliesToFabric() {
      return this.platform == IconVariant.Platform.COMMON || this.platform == IconVariant.Platform.FABRIC;
   }

   boolean appliesToNeoForge() {
      return this.platform == IconVariant.Platform.COMMON || this.platform == IconVariant.Platform.NeoForge;
   }

   public enum Platform {
      COMMON,
      FABRIC,
      NeoForge;
   }
}
