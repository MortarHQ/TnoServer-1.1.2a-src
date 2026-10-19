package dev.aika.smsn.api;

import architectury_inject_smsn_common_1f6de3d3e22c42338b3d6c0aacf1d9c4_1fabca28a437e057e79abf64373893b7954ff35090bc6f138e77d03b042b6b4fsmsncommon12101211devjar.PlatformMethods;

public enum LoaderType {
   NEOFORGE,
   FORGE,
   FABRIC,
   QUILT;

   public static LoaderType getCurrentLoader() {
      String currentTarget = PlatformMethods.getCurrentTarget();

      return switch (currentTarget) {
         case "fabric" -> FABRIC;
         case "forge" -> FORGE;
         case "neoforge" -> NEOFORGE;
         case "quilt" -> QUILT;
         default -> null;
      };
   }
}
