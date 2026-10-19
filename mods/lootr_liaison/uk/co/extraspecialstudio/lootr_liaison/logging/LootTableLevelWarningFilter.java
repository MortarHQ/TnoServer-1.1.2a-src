package uk.co.extraspecialstudio.lootr_liaison.logging;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.filter.AbstractFilter;

public final class LootTableLevelWarningFilter extends AbstractFilter {
   private static final String SUPPRESSED_MESSAGE = "had its loot table set before its level was set";

   public LootTableLevelWarningFilter() {
      super(Result.DENY, Result.NEUTRAL);
   }

   public Result filter(LogEvent event) {
      if (event != null && event.getMessage() != null) {
         String msg = event.getMessage().getFormattedMessage();
         return msg != null && msg.contains("had its loot table set before its level was set") ? Result.DENY : Result.NEUTRAL;
      } else {
         return Result.NEUTRAL;
      }
   }
}
