package com.chronodawn.version;

public enum MinecraftVersion {
   MC_1_20_1("1.20.1", 1, 20, 1),
   MC_1_21_1("1.21.1", 1, 21, 1),
   MC_1_21_2("1.21.2", 1, 21, 2),
   MC_1_21_7("1.21.7", 1, 21, 7),
   MC_1_21_8("1.21.8", 1, 21, 8),
   MC_1_21_9("1.21.9", 1, 21, 9),
   MC_1_21_10("1.21.10", 1, 21, 10);

   private final String displayName;
   private final int major;
   private final int minor;
   private final int patch;

   MinecraftVersion(String displayName, int major, int minor, int patch) {
      this.displayName = displayName;
      this.major = major;
      this.minor = minor;
      this.patch = patch;
   }

   public String displayName() {
      return this.displayName;
   }

   public int major() {
      return this.major;
   }

   public int minor() {
      return this.minor;
   }

   public int patch() {
      return this.patch;
   }

   public boolean isAtLeast(MinecraftVersion other) {
      if (this.major != other.major) {
         return this.major > other.major;
      } else {
         return this.minor != other.minor ? this.minor > other.minor : this.patch >= other.patch;
      }
   }

   public static MinecraftVersion oldest() {
      return MC_1_20_1;
   }

   public static MinecraftVersion newest() {
      return MC_1_21_10;
   }

   @Override
   public String toString() {
      return this.displayName;
   }
}
