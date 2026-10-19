package com.github.wal_bos.moreskills.ai;

public final class TensuraAiDebug {
   public static final boolean ENABLED = true;

   private TensuraAiDebug() {
   }

   public static void log(String message) {
      System.out.println("[RaphaelAI Debug] " + message);
   }

   public static void warn(String message) {
      System.out.println("[RaphaelAI Warning] " + message);
   }

   public static void error(String message, Throwable throwable) {
      System.out.println("[RaphaelAI Error] " + message);
      if (throwable != null) {
         System.out.println("[RaphaelAI Error] " + throwable.getClass().getName() + ": " + throwable.getMessage());
      }
   }

   public static String preview(String text, int maxLength) {
      if (text == null) {
         return "null";
      }

      String clean = text.replace("\n", "\\n").replace("\r", "\\r");
      return clean.length() <= maxLength ? clean : clean.substring(0, Math.max(0, maxLength)) + "...";
   }

   public static String apiKeyState(String key) {
      if (key != null && !key.isBlank()) {
         return key.length() < 10
            ? "present_but_too_short"
            : "present length=" + key.length() + " prefix=" + key.substring(0, Math.min(4, key.length())) + "****";
      } else {
         return "missing";
      }
   }
}
