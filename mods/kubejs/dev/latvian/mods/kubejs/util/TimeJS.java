package dev.latvian.mods.kubejs.util;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TimeJS {
   Pattern TEMPORAL_AMOUNT_PATTERN = Pattern.compile("(\\d+)\\s*(y|M|d|w|h|m|s|ms|ns|t)\\b");

   static TemporalAmount wrapTemporalAmount(Object o) {
      return switch (o) {
         case TemporalAmount d -> d;
         case Number n -> Duration.ofMillis(n.longValue());
         case CharSequence cs -> {
            Matcher matcher = TEMPORAL_AMOUNT_PATTERN.matcher(cs.toString());
            double millis = 0.0;
            double nanos = 0.0;
            double ticks = Double.NaN;

            while (matcher.find()) {
               double amount = Double.parseDouble(matcher.group(1));
               switch (matcher.group(2)) {
                  case "t":
                     if (Double.isNaN(ticks)) {
                        ticks = 0.0;
                     }

                     ticks += amount;
                     break;
                  case "ns":
                     nanos += amount;
                     break;
                  case "ms":
                     millis += amount;
                     break;
                  case "s":
                     millis = amount * 1000.0;
                     break;
                  case "m":
                     millis = amount * 60000.0;
                     break;
                  case "h":
                     millis = amount * 60000.0 * 60.0;
                     break;
                  case "d":
                     millis = amount * 86400.0 * 1000.0;
                     break;
                  case "w":
                     millis = amount * 86400.0 * 7000.0;
                     break;
                  case "M":
                     millis = amount * 3.1556952E7 / 12.0 * 1000.0;
                     break;
                  case "y":
                     millis = amount * 3.1556952E7 * 1000.0;
                     break;
                  default:
                     throw new IllegalArgumentException("Invalid temporal unit: " + matcher.group(2));
               }
            }

            yield !Double.isNaN(ticks) ? TickDuration.of((long)(ticks + millis / 50.0)) : Duration.ofMillis((long)millis).plusNanos((long)nanos);
         }
         case null, default -> throw new IllegalArgumentException("Invalid temporal amount: " + o);
      };
   }

   static Duration wrapDuration(Object o) {
      TemporalAmount t = wrapTemporalAmount(o);

      return switch (t) {
         case Duration d -> d;
         case TickDuration(long ticks) -> Duration.ofMillis(ticks * 50L);
         default -> {
            Duration d = Duration.ZERO;

            for (TemporalUnit unit : t.getUnits()) {
               d = d.plus(t.get(unit), unit);
            }

            yield d;
         }
      };
   }

   static void appendTimestamp(StringBuilder builder, Calendar calendar) {
      int h = calendar.get(11);
      int m = calendar.get(12);
      int s = calendar.get(13);
      if (h < 10) {
         builder.append('0');
      }

      builder.append(h);
      builder.append(':');
      if (m < 10) {
         builder.append('0');
      }

      builder.append(m);
      builder.append(':');
      if (s < 10) {
         builder.append('0');
      }

      builder.append(s);
   }

   static String msToString(long ms) {
      return ms < 1000L ? ms + " ms" : "%.3f".formatted((float)ms / 1000.0F) + " s";
   }
}
