package mezz.jei.common.config;

import java.util.function.Supplier;
import mezz.jei.common.config.file.IConfigCategoryBuilder;
import mezz.jei.common.config.file.IConfigSchemaBuilder;
import org.jetbrains.annotations.Nullable;

public final class DebugConfig {
   @Nullable
   private static DebugConfig instance;
   private final Supplier<Boolean> debugModeEnabled;
   private final Supplier<Boolean> debugGuisEnabled;
   private final Supplier<Boolean> debugInputsEnabled;
   private final Supplier<Boolean> debugInfoTooltipsEnabled;
   private final Supplier<Boolean> crashingTestIngredientsEnabled;
   private final Supplier<Boolean> crashingTestRecipesEnabled;
   private final Supplier<Boolean> logSuffixTreeStats;

   public static void create(IConfigSchemaBuilder schema) {
      instance = new DebugConfig(schema);
   }

   private DebugConfig(IConfigSchemaBuilder schema) {
      IConfigCategoryBuilder advanced = schema.addCategory("debug");
      this.debugModeEnabled = advanced.addBoolean("debugMode", false);
      this.debugGuisEnabled = advanced.addBoolean("debugGuis", false);
      this.debugInputsEnabled = advanced.addBoolean("debugInputs", false);
      this.debugInfoTooltipsEnabled = advanced.addBoolean("debugInfoTooltipsEnabled", false);
      this.crashingTestIngredientsEnabled = advanced.addBoolean("crashingTestItemsEnabled", false);
      this.crashingTestRecipesEnabled = advanced.addBoolean("crashingTestRecipesEnabled", false);
      this.logSuffixTreeStats = advanced.addBoolean("logSuffixTreeStats", false);
   }

   public static boolean isDebugModeEnabled() {
      return instance == null ? false : instance.debugModeEnabled.get();
   }

   public static boolean isDebugGuisEnabled() {
      return instance == null ? false : instance.debugGuisEnabled.get();
   }

   public static boolean isDebugInputsEnabled() {
      return instance == null ? false : instance.debugInputsEnabled.get();
   }

   public static boolean isDebugInfoTooltipsEnabled() {
      return instance == null ? false : instance.debugInfoTooltipsEnabled.get();
   }

   public static boolean isCrashingTestIngredientsEnabled() {
      return instance == null ? false : instance.crashingTestIngredientsEnabled.get();
   }

   public static boolean isCrashingTestRecipesEnabled() {
      return instance == null ? false : instance.crashingTestRecipesEnabled.get();
   }

   public static boolean isLogSuffixTreeStatsEnabled() {
      return instance == null ? false : instance.logSuffixTreeStats.get();
   }
}
