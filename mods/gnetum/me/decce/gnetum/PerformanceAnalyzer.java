package me.decce.gnetum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;

public class PerformanceAnalyzer {
   private static final double DURATION_TOO_LONG_CRITERIA = 1.6;
   public static PerformanceAnalyzer.Result latestAnalysisResult;

   public static PerformanceAnalyzer.Result analyze() {
      PerformanceAnalyzer.Result result = new PerformanceAnalyzer.Result();
      analyzePass(result);
      analyzeDurations(result);
      latestAnalysisResult = result;
      return result;
   }

   private static void analyzePass(PerformanceAnalyzer.Result result) {
      boolean[] anyElement = new boolean[Gnetum.config.numberOfPasses + 1];
      Gnetum.config.mapVanillaElements.forEach((key, value) -> {
         if (value.enabled.get()) {
            anyElement[value.pass] = true;
         }
      });
      Gnetum.config.mapModdedElementsPre.forEach((key, value) -> {
         if (value.enabled.get()) {
            anyElement[value.pass] = true;
         }
      });
      Gnetum.config.mapModdedElementsPost.forEach((key, value) -> {
         if (value.enabled.get()) {
            anyElement[value.pass] = true;
         }
      });

      for (int i = 1; i < anyElement.length; i++) {
         if (!anyElement[i]) {
            result.messages.add(I18n.get("gnetum.config.analysis.noElement", new Object[]{i}));
         }
      }
   }

   private static void analyzeDurations(PerformanceAnalyzer.Result result) {
      long[] durations = Gnetum.passManager.getDurations();
      if (durations != null) {
         long total = Arrays.stream(durations).skip(1L).sum();
         double avg = total * 1.0 / Gnetum.config.numberOfPasses;

         for (int i = 1; i < durations.length; i++) {
            if (durations[i] * 1.0 >= avg * 1.6) {
               long count = 0L;
               int temp = i;
               count += Gnetum.config
                  .mapModdedElementsPre
                  .entrySet()
                  .stream()
                  .filter(entry -> entry.getValue().enabled.get() && entry.getValue().pass == temp)
                  .count();
               if (count <= 1L) {
                  count += Gnetum.config
                     .mapVanillaElements
                     .entrySet()
                     .stream()
                     .filter(entry -> entry.getValue().enabled.get() && entry.getValue().pass == temp)
                     .count();
               }

               if (count <= 1L) {
                  count += Gnetum.config
                     .mapModdedElementsPost
                     .entrySet()
                     .stream()
                     .filter(entry -> entry.getValue().enabled.get() && entry.getValue().pass == temp)
                     .count();
               }

               if (count > 1L) {
                  result.messages.add(I18n.get("gnetum.config.analysis.longDuration", new Object[]{i}));
               }
            }
         }

         result.durations = durations;
      }
   }

   public static class Result {
      private final List<String> messages = new ArrayList<>(3);
      private long[] durations;
      private boolean outdated;

      private Result() {
      }

      public PerformanceAnalyzer.ResultIcon getIcon() {
         return this.outdated
            ? PerformanceAnalyzer.ResultIcon.QUESTION
            : (this.messages.isEmpty() ? PerformanceAnalyzer.ResultIcon.TICK : PerformanceAnalyzer.ResultIcon.EXCLAMATION);
      }

      public List<String> getMessages() {
         return this.messages;
      }

      public long[] getDurations() {
         return this.durations;
      }

      public boolean isOutdated() {
         return this.outdated;
      }

      public void markOutdated() {
         this.outdated = true;
      }
   }

   public enum ResultIcon {
      TICK,
      EXCLAMATION,
      QUESTION;

      public ResourceLocation icon() {
         return switch (this) {
            case TICK -> Icons.TICK;
            case EXCLAMATION -> Icons.EXCLAMATION;
            case QUESTION -> Icons.QUESTION;
         };
      }
   }
}
