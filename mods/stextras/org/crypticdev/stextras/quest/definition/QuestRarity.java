package org.crypticdev.stextras.quest.definition;

import java.util.Arrays;

public enum QuestRarity {
   NOVICE,
   ADEPT,
   INTERMEDIATE,
   EXPERT,
   MASTER;

   public static QuestRarity fromPath(String s) {
      return Arrays.stream(values())
         .filter(v -> v.name().equalsIgnoreCase(s))
         .findFirst()
         .orElseThrow(() -> new IllegalArgumentException("Unknown rarity in path: " + s));
   }
}
