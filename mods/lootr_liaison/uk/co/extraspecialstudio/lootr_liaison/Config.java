package uk.co.extraspecialstudio.lootr_liaison;

import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

@EventBusSubscriber(modid = "lootr_liaison", bus = Bus.MOD)
public class Config {
   private static final Builder BUILDER = new Builder();
   private static final IntValue FINALIZATION_DELAY_TICKS = BUILDER.comment(
         new String[]{
            "----- START HERE: FINALIZATION -----",
            "Ticks to wait after detecting a container before finalizing",
            "(gives worldgen time to finish). Default: 20."
         }
      )
      .defineInRange("finalization_delay_ticks", 20, 0, 400);
   private static final IntValue MAX_FINALIZATIONS_PER_TICK = BUILDER.comment(new String[]{"Maximum containers to finalize per server tick.", "Default: 8."})
      .defineInRange("max_finalizations_per_tick", 8, 1, 64);
   private static final BooleanValue ENABLE_ARTIFACTS_MIMIC_COMPAT = BUILDER.comment(
         new String[]{"Enable mimic compatibility for the Artifacts mod.", "Default: true."}
      )
      .define("enable_artifacts_mimic_compat", true);
   private static final ConfigValue<String> MIMIC_ROLL_TIMING = BUILDER.comment(
         new String[]{"When to roll for a mimic: \"finalization\" or \"first_interact\".", "Default: finalization."}
      )
      .define("mimic_roll_timing", "finalization");
   private static final ConfigValue<List<? extends String>> EXCLUDE_CONTAINER_TYPES = BUILDER.comment(
         new String[]{"Container block ids excluded from mimic rolls.", "Example: minecraft:barrel. Default: minecraft:barrel only."}
      )
      .defineListAllowEmpty("exclude_container_types", List.of("minecraft:barrel"), Config::validateResourceId);
   private static final BooleanValue RESPECT_ARTIFACTS_CONFIG = BUILDER.comment(
         new String[]{"true: honour Artifacts' own mimic config when available.", "Default: true."}
      )
      .define("respect_artifacts_config", true);
   private static final IntValue MAX_MIMIC_CHECKS_PER_TICK = BUILDER.comment(
         new String[]{"Max mimic evaluations per tick when using first_interact timing.", "Default: 8."}
      )
      .defineInRange("max_mimic_checks_per_tick", 8, 1, 64);
   static final ModConfigSpec SPEC = BUILDER.build();
   public static int finalizationDelayTicks;
   public static int maxFinalizationsPerTick;
   public static boolean enableArtifactsMimicCompat;
   public static String mimicRollTiming;
   public static List<String> excludeContainerTypes;
   public static boolean respectArtifactsConfig;
   public static int maxMimicChecksPerTick;

   private static boolean validateResourceId(Object obj) {
      if (obj instanceof String s) {
         try {
            ResourceLocation.parse(s);
            return true;
         } catch (Exception e) {
            return false;
         }
      } else {
         return false;
      }
   }

   @SubscribeEvent
   static void onLoad(ModConfigEvent event) {
      finalizationDelayTicks = (Integer)FINALIZATION_DELAY_TICKS.get();
      maxFinalizationsPerTick = (Integer)MAX_FINALIZATIONS_PER_TICK.get();
      enableArtifactsMimicCompat = (Boolean)ENABLE_ARTIFACTS_MIMIC_COMPAT.get();
      mimicRollTiming = (String)MIMIC_ROLL_TIMING.get();
      excludeContainerTypes = ((List)EXCLUDE_CONTAINER_TYPES.get()).stream().map(String::toString).toList();
      respectArtifactsConfig = (Boolean)RESPECT_ARTIFACTS_CONFIG.get();
      maxMimicChecksPerTick = (Integer)MAX_MIMIC_CHECKS_PER_TICK.get();
   }

   static {
      BUILDER.comment(
            new String[]{
               "============================================================",
               "LOOTR FINALIZATION",
               "How long Lootr Liaison waits after spotting a container before",
               "finalizing it, and how many finalize per tick.",
               "============================================================"
            }
         )
         .push("general");
      BUILDER.pop();
      BUILDER.comment(
            new String[]{
               "============================================================",
               "MIMICS (ARTIFACTS)",
               "Compatibility with the Artifacts mod mimic chests.",
               "When Artifacts is absent these options do nothing.",
               "============================================================"
            }
         )
         .push("mimics");
      BUILDER.pop();
   }
}
