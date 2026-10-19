package dev.shadowsoffire.placebo.config;

public class ConfigFlags {
   public enum Loadability {
      LOCKED("Locked"),
      RELOADABLE("Reloadable"),
      RESTARTABLE("Restartable");

      String name;

      Loadability(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }
   }

   public enum Type {
      COMMON("Common"),
      SYNCED("Synced"),
      SERVER("Server"),
      CLIENT("Client");

      String name;

      Type(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }
   }
}
