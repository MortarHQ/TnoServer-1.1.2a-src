package org.crypticdev.stextras.quest.definition;

public enum QuestCategory {
   DAILY,
   WEEKLY,
   REQUIRED,
   REPEATABLE,
   RACE_PRESTIGE;

   public static QuestCategory fromPath(String s) {
      return switch (s.toLowerCase()) {
         case "daily" -> DAILY;
         case "weekly" -> WEEKLY;
         case "required" -> REQUIRED;
         case "repeatable" -> REPEATABLE;
         case "race_prestige" -> RACE_PRESTIGE;
         default -> throw new IllegalArgumentException("Unknown category in path: " + s);
      };
   }
}
