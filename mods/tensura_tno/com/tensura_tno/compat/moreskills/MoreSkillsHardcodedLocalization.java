package com.tensura_tno.compat.moreskills;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.StackWalker.Option;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import org.jetbrains.annotations.Nullable;

public final class MoreSkillsHardcodedLocalization {
   private static final String INDEX_RESOURCE = "/assets/tensura_tno_moreskills_compat/moreskills_hardcoded_index.json";
   private static final String MORE_SKILLS_PACKAGE = "com.github.wal_bos.moreskills.";
   private static final Map<String, String> EXACT = loadIndex();
   private static final List<Entry<String, String>> FRAGMENTS = EXACT.entrySet()
      .stream()
      .filter(entry -> isSafeFragment(entry.getKey()))
      .sorted(Entry.<String, String>comparingByKey(Comparator.comparingInt(String::length)).reversed())
      .toList();

   private MoreSkillsHardcodedLocalization() {
   }

   public static boolean hasCandidate(String text) {
      if (text != null && !text.isEmpty()) {
         if (EXACT.containsKey(text)) {
            return true;
         }

         for (Entry<String, String> entry : FRAGMENTS) {
            if (text.contains(entry.getKey())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Nullable
   public static String findExactKey(String text) {
      return EXACT.get(text);
   }

   public static boolean isCalledFromMoreSkills() {
      return StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE)
         .walk(frames -> frames.limit(16L).anyMatch(frame -> frame.getClassName().startsWith("com.github.wal_bos.moreskills.")));
   }

   @Nullable
   public static MutableComponent createComponent(String text) {
      String exactKey = EXACT.get(text);
      if (exactKey != null) {
         return Component.translatableWithFallback(exactKey, text);
      }

      List<MoreSkillsHardcodedLocalization.Segment> segments = split(text);
      if (segments == null) {
         return null;
      }

      MutableComponent result = MutableComponent.create(PlainTextContents.EMPTY);

      for (MoreSkillsHardcodedLocalization.Segment segment : segments) {
         if (segment.translationKey() == null) {
            result.append(MutableComponent.create(PlainTextContents.create(segment.text())));
         } else {
            result.append(Component.translatableWithFallback(segment.translationKey(), segment.text()));
         }
      }

      return result;
   }

   @Nullable
   public static List<MoreSkillsHardcodedLocalization.Segment> split(String text) {
      if (text != null && !text.isEmpty()) {
         List<MoreSkillsHardcodedLocalization.Segment> result = new ArrayList<>();
         int cursor = 0;

         boolean matched;
         for (matched = false; cursor < text.length(); matched = true) {
            Entry<String, String> best = null;
            int bestStart = text.length();

            for (Entry<String, String> entry : FRAGMENTS) {
               int start = text.indexOf(entry.getKey(), cursor);
               if (start >= 0 && (start < bestStart || start == bestStart && (best == null || entry.getKey().length() > best.getKey().length()))) {
                  best = entry;
                  bestStart = start;
               }
            }

            if (best == null) {
               break;
            }

            if (bestStart > cursor) {
               result.add(new MoreSkillsHardcodedLocalization.Segment(text.substring(cursor, bestStart), null));
            }

            result.add(new MoreSkillsHardcodedLocalization.Segment(best.getKey(), best.getValue()));
            cursor = bestStart + best.getKey().length();
         }

         if (!matched) {
            return null;
         }

         if (cursor < text.length()) {
            result.add(new MoreSkillsHardcodedLocalization.Segment(text.substring(cursor), null));
         }

         return result;
      } else {
         return null;
      }
   }

   private static boolean isSafeFragment(String text) {
      if (text.length() >= 12) {
         return true;
      } else {
         return !Character.isWhitespace(text.charAt(0)) && !Character.isWhitespace(text.charAt(text.length() - 1))
            ? text.contains("|") || text.endsWith(":") || text.endsWith(": ")
            : true;
      }
   }

   private static Map<String, String> loadIndex() {
      try (InputStream stream = MoreSkillsHardcodedLocalization.class
            .getResourceAsStream("/assets/tensura_tno_moreskills_compat/moreskills_hardcoded_index.json")) {
         if (stream == null) {
            return Map.of();
         }

         Type type = (new TypeToken<LinkedHashMap<String, String>>() {}).getType();
         Map<String, String> result = (Map<String, String>)new Gson().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), type);
         return result == null ? Map.of() : Map.copyOf(result);
      } catch (Exception ignored) {
         return Map.of();
      }
   }

   public record Segment(String text, @Nullable String translationKey) {
   }
}
