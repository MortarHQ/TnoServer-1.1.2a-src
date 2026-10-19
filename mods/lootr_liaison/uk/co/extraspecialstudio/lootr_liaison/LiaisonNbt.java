package uk.co.extraspecialstudio.lootr_liaison;

import net.minecraft.nbt.CompoundTag;

public final class LiaisonNbt {
   public static final String PREFIX = "lootr_liaison:";
   public static final String FINALIZED = "lootr_liaison:finalized";
   public static final String MIMIC_CHECKED = "lootr_liaison:mimic_checked";
   public static final String MIMIC_RESULT = "lootr_liaison:mimic_result";
   public static final String MIMIC_NONE = "none";
   public static final String MIMIC_ARMED = "armed";
   public static final String MIMIC_SPAWNED = "spawned";
   private static final String LEGACY_PREFIX = "lootr_liason:";

   private LiaisonNbt() {
   }

   public static boolean isFinalized(CompoundTag tag) {
      return tag.getBoolean("lootr_liaison:finalized") || tag.getBoolean("lootr_liason:finalized");
   }

   public static boolean isMimicChecked(CompoundTag tag) {
      return tag.getBoolean("lootr_liaison:mimic_checked") || tag.getBoolean("lootr_liason:mimic_checked");
   }

   public static String mimicResult(CompoundTag tag) {
      if (tag.contains("lootr_liaison:mimic_result")) {
         return tag.getString("lootr_liaison:mimic_result");
      }

      String legacy = "lootr_liason:mimic_result";
      return tag.contains(legacy) ? tag.getString(legacy) : "";
   }

   public static void clearMimicStamps(CompoundTag tag) {
      tag.remove("lootr_liaison:mimic_checked");
      tag.remove("lootr_liaison:mimic_result");
      tag.remove("lootr_liason:mimic_checked");
      tag.remove("lootr_liason:mimic_result");
   }
}
