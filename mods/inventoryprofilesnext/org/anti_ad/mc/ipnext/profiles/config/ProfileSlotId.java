package org.anti_ad.mc.ipnext.profiles.config;

import kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

public enum ProfileSlotId {
   NONE(-1),
   HOT1(36),
   HOT2(37),
   HOT3(38),
   HOT4(39),
   HOT5(40),
   HOT6(41),
   HOT7(42),
   HOT8(43),
   HOT9(44),
   FEET(8),
   LEGS(7),
   CHEST(6),
   HEAD(5),
   OFFHAND(45);

   @NotNull
   public static final ProfileSlotId$Companion Companion = new ProfileSlotId$Companion(null);
   private final int slotId;

   ProfileSlotId(int var3) {
      this.slotId = var3;
      ProfilesConfigParserKt.addSlotId(this, this.slotId);
   }

   public final int getSlotId() {
      return this.slotId;
   }

   public final int slotId() {
      return this.slotId;
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
