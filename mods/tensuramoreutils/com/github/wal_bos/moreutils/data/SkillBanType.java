package com.github.wal_bos.moreutils.data;

public enum SkillBanType {
   LEARN,
   OWN,
   FULL;

   public static SkillBanType parse(String value) {
      return switch (value.toLowerCase()) {
         case "learn" -> LEARN;
         case "own" -> OWN;
         case "full", "lockdown", "blacklist" -> FULL;
         default -> throw new IllegalArgumentException("Invalid skill ban type: " + value);
      };
   }

   public boolean blocksLearning() {
      return true;
   }

   public boolean blocksOwning() {
      return this == OWN || this == FULL;
   }

   public boolean blocksUse() {
      return this == FULL;
   }

   public boolean isBlacklist() {
      return this == FULL;
   }
}
