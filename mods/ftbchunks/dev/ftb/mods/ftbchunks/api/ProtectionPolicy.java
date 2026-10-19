package dev.ftb.mods.ftbchunks.api;

public enum ProtectionPolicy {
   CHECK,
   DENY,
   ALLOW;

   public boolean isOverride() {
      return this != CHECK;
   }

   public boolean shouldPreventInteraction() {
      return this == DENY;
   }
}
