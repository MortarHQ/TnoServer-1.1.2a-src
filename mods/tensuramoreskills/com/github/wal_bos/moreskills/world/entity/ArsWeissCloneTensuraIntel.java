package com.github.wal_bos.moreskills.world.entity;

public final class ArsWeissCloneTensuraIntel {
   private ArsWeissCloneTensuraIntel() {
   }

   public static String bestLootPlace(String query) {
      String q = clean(query);
      if (q.isBlank()) {
         return "";
      } else if (has(q, "magic tome", "unbound tome", "grimoire", "magic stone", "low quality magic crystal", "spell", "magic manual")) {
         return "dwarf magic trainer or wizard tower";
      } else if (has(q, "battlewill", "manual", "battle will")) {
         return "dwarf dojo";
      } else if (has(q, "schematic", "ingot", "silver", "magisteel", "mithril", "orichalcum", "sword", "spear", "gear")) {
         return "dwarf smithy";
      } else if (has(q, "paper", "book", "ink", "bookshelf", "scroll", "reset")) {
         return "dwarf library or wizard tower";
      } else if (has(q, "wheat", "carrot", "potato", "beetroot", "melon", "pumpkin", "seed")) {
         return "dwarf farm";
      } else if (has(q, "log", "wood", "sapling", "apple")) {
         return "dwarf lumberjack";
      } else if (has(q, "coin", "bronze", "merchant")) {
         return "dwarf merchant";
      } else if (has(q, "spider", "thread", "fang", "carapace")) {
         return "spider nest";
      } else if (has(q, "daemon", "demon", "hell", "netherite")) {
         return "hell ruins";
      } else if (has(q, "goblin")) {
         return "goblin village or goblin tower";
      } else if (has(q, "orc")) {
         return "orc storage or orc tent";
      } else {
         return has(q, "lizard", "serpent") ? "lizardman storage or lizardman smithy" : "";
      }
   }

   public static String recipeHint(String query) {
      String q = clean(query);
      if (has(q, "reset scroll", "character reset")) {
         return "paper, magic stone, monster leather A and high-tier magisteel chain";
      } else if (has(q, "low magisteel")) {
         return "magic ore plus iron";
      } else if (has(q, "pure magisteel")) {
         return "magic ore refined or molten magisteel";
      } else if (has(q, "mithril")) {
         return "magic ore plus silver";
      } else if (has(q, "orichalcum")) {
         return "magic ore plus gold";
      } else {
         return has(q, "magic stone") ? "low magisteel and low quality magic crystals" : "";
      }
   }

   private static boolean has(String text, String... values) {
      for (String value : values) {
         if (text.contains(clean(value))) {
            return true;
         }
      }

      return false;
   }

   private static String clean(String value) {
      return value == null ? "" : value.toLowerCase().replace('_', ' ').replace('-', ' ').replaceAll("[^a-z0-9: ]", " ").replaceAll("\\s+", " ").trim();
   }
}
