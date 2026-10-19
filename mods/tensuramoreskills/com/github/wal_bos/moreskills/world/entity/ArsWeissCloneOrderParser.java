package com.github.wal_bos.moreskills.world.entity;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ArsWeissCloneOrderParser {
   private static final Pattern NUMBER_AT_END = Pattern.compile("^(.*?)(?:\\s+|:|x)(\\d{1,6})\\s*$", 2);
   private static final Pattern NUMBER_AT_START = Pattern.compile("^\\s*(\\d{1,6})\\s*(?:x|of)?\\s+(.+)$", 2);

   private ArsWeissCloneOrderParser() {
   }

   public static ArsWeissCloneOrderParser.Order parse(String raw) {
      String text = raw == null ? "" : raw.trim();
      if (text.isBlank()) {
         return new ArsWeissCloneOrderParser.Order("", "", 1);
      }

      text = text.replace('§', ' ').replace('_', ' ').replace('-', ' ').replaceAll("\\s+", " ").trim();
      int amount = 1;
      if (!looksLikeSendCoordinateOrder(text)) {
         Matcher start = NUMBER_AT_START.matcher(text);
         if (start.matches()) {
            amount = safeAmount(start.group(1));
            text = start.group(2).trim();
         } else {
            Matcher end = NUMBER_AT_END.matcher(text);
            if (end.matches() && !looksLikeRegistryId(end.group(1))) {
               amount = safeAmount(end.group(2));
               text = end.group(1).trim();
            }
         }
      }

      String mode = "";
      String query = text;
      int colon = text.indexOf(58);
      if (colon > 0) {
         mode = canonical(text.substring(0, colon));
         query = text.substring(colon + 1).trim();
      } else {
         String[] split = text.split("\\s+", 2);
         if (split.length == 2) {
            mode = canonical(split[0]);
            if (!mode.isBlank()) {
               query = split[1].trim();
            }
         } else {
            mode = canonical(text);
            if (!mode.isBlank()) {
               query = "";
            }
         }
      }

      query = stripNoise(query);
      return new ArsWeissCloneOrderParser.Order(mode, query, amount);
   }

   private static String canonical(String word) {
      String w = normalize(word).replace(" ", "");
      if (fuzzy(w, "send") || fuzzy(w, "goto") || fuzzy(w, "move") || fuzzy(w, "travel")) {
         return "send";
      } else if (fuzzy(w, "dismiss") || fuzzy(w, "despawn") || fuzzy(w, "remove") || fuzzy(w, "delete") || fuzzy(w, "shatter")) {
         return "dismiss";
      } else if (fuzzy(w, "return") || fuzzy(w, "recall") || fuzzy(w, "come") || fuzzy(w, "back")) {
         return "return";
      } else if (fuzzy(w, "status") || fuzzy(w, "report") || fuzzy(w, "progress") || fuzzy(w, "info")) {
         return "status";
      } else if (fuzzy(w, "idle") || fuzzy(w, "wait") || fuzzy(w, "stop") || fuzzy(w, "cancel")) {
         return "idle";
      } else if (fuzzy(w, "guard") || fuzzy(w, "defend") || fuzzy(w, "protect") || fuzzy(w, "escort") || fuzzy(w, "patrol")) {
         return "guard";
      } else if (fuzzy(w, "locate") || fuzzy(w, "structure") || fuzzy(w, "village") || fuzzy(w, "dungeon")) {
         return "locate";
      } else if (fuzzy(w, "loot") || fuzzy(w, "chest")) {
         return "loot";
      } else if (fuzzy(w, "mine") || fuzzy(w, "dig") || fuzzy(w, "excavate") || fuzzy(w, "quarry")) {
         return "mine";
      } else if (fuzzy(w, "hunt") || fuzzy(w, "kill") || fuzzy(w, "attack") || fuzzy(w, "target") || fuzzy(w, "fight")) {
         return "hunt";
      } else if (fuzzy(w, "command") || fuzzy(w, "commander") || fuzzy(w, "delegate") || fuzzy(w, "leader")) {
         return "command";
      } else if (fuzzy(w, "army") || fuzzy(w, "tame") || fuzzy(w, "recruit") || fuzzy(w, "subordinate") || fuzzy(w, "dominate")) {
         return "army";
      } else {
         return !fuzzy(w, "gather") && !fuzzy(w, "collect") && !fuzzy(w, "get") && !fuzzy(w, "farm") && !fuzzy(w, "bring") && !fuzzy(w, "find") ? "" : "gather";
      }
   }

   private static String stripNoise(String query) {
      String q = query == null ? "" : query.trim();
      q = q.replaceAll("(?i)\\b(please|pls|plz|go|and|then|for|me|some|a|an|the|to|from|nearby|around|of)\\b", " ");
      return q.replaceAll("\\s+", " ").trim();
   }

   private static boolean looksLikeRegistryId(String value) {
      return value != null && value.contains(":") && value.substring(value.lastIndexOf(58) + 1).matches(".*[a-zA-Z_].*");
   }

   private static boolean looksLikeSendCoordinateOrder(String value) {
      String normalized = normalize(value);
      if (!normalized.startsWith("send ") && !normalized.startsWith("goto ") && !normalized.startsWith("move ") && !normalized.startsWith("travel ")) {
         return false;
      }

      int coords = 0;

      for (String token : normalized.split(" ")) {
         if (token.matches("~?-?\\d{1,9}(?:\\.\\d+)?") || token.equals("~")) {
            coords++;
         }
      }

      return coords >= 3;
   }

   private static int safeAmount(String number) {
      try {
         return Math.max(1, Math.min(100000, Integer.parseInt(number)));
      } catch (Exception ignored) {
         return 1;
      }
   }

   private static String normalize(String value) {
      return value == null
         ? ""
         : value.toLowerCase(Locale.ROOT).replace('_', ' ').replace('-', ' ').replaceAll("[^a-z0-9 ]", " ").replaceAll("\\s+", " ").trim();
   }

   private static boolean fuzzy(String input, String target) {
      if (input.equals(target)) {
         return true;
      } else {
         return input.length() >= 3 && target.contains(input) ? true : distance(input, target) <= Math.max(1, target.length() / 3);
      }
   }

   private static int distance(String a, String b) {
      if (a != null && b != null) {
         int[] prev = new int[b.length() + 1];
         int[] curr = new int[b.length() + 1];
         int j = 0;

         while (j <= b.length()) {
            prev[j] = j++;
         }

         for (int i = 1; i <= a.length(); i++) {
            curr[0] = i;

            for (int jx = 1; jx <= b.length(); jx++) {
               int cost = a.charAt(i - 1) == b.charAt(jx - 1) ? 0 : 1;
               curr[jx] = Math.min(Math.min(curr[jx - 1] + 1, prev[jx] + 1), prev[jx - 1] + cost);
            }

            int[] tmp = prev;
            prev = curr;
            curr = tmp;
         }

         return prev[b.length()];
      } else {
         return 999;
      }
   }

   public record Order(String mode, String query, int amount) {
   }
}
