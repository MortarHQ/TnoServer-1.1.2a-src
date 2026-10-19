package org.anti_ad.mc.ipnext.event.autorefill;

import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AutoRefillHandler$WatchIds {
   @NotNull
   public static final AutoRefillHandler$WatchIds INSTANCE = new AutoRefillHandler$WatchIds();
   public static final int MAIN_HAND_OFFSET = 36;
   @NotNull
   private static final AutoRefillHandler$IdAndIndex mainHandSelected = new AutoRefillHandler$IdAndIndex(
      AutoRefillHandler$WatchIds::mainHandSelected$lambda$0, AutoRefillHandler$WatchIds::mainHandSelected$lambda$1
   );
   @NotNull
   private static final AutoRefillHandler$IdAndIndex offHand = new AutoRefillHandler$IdAndIndex(
      AutoRefillHandler$WatchIds::offHand$lambda$2, AutoRefillHandler$WatchIds::offHand$lambda$3
   );
   @NotNull
   private static final AutoRefillHandler$IdAndIndex head = new AutoRefillHandler$IdAndIndex(
      AutoRefillHandler$WatchIds::head$lambda$4, AutoRefillHandler$WatchIds::head$lambda$5
   );
   @NotNull
   private static final AutoRefillHandler$IdAndIndex chest = new AutoRefillHandler$IdAndIndex(
      AutoRefillHandler$WatchIds::chest$lambda$6, AutoRefillHandler$WatchIds::chest$lambda$7
   );
   @NotNull
   private static final AutoRefillHandler$IdAndIndex legs = new AutoRefillHandler$IdAndIndex(
      AutoRefillHandler$WatchIds::legs$lambda$8, AutoRefillHandler$WatchIds::legs$lambda$9
   );
   @NotNull
   private static final AutoRefillHandler$IdAndIndex feet = new AutoRefillHandler$IdAndIndex(
      AutoRefillHandler$WatchIds::feet$lambda$10, AutoRefillHandler$WatchIds::feet$lambda$11
   );

   private AutoRefillHandler$WatchIds() {
   }

   @NotNull
   public final AutoRefillHandler$IdAndIndex getMainHandSelected() {
      return mainHandSelected;
   }

   @NotNull
   public final AutoRefillHandler$IdAndIndex getOffHand() {
      return offHand;
   }

   @NotNull
   public final AutoRefillHandler$IdAndIndex getHead() {
      return head;
   }

   @NotNull
   public final AutoRefillHandler$IdAndIndex getChest() {
      return chest;
   }

   @NotNull
   public final AutoRefillHandler$IdAndIndex getLegs() {
      return legs;
   }

   @NotNull
   public final AutoRefillHandler$IdAndIndex getFeet() {
      return feet;
   }

   @NotNull
   @Override
   public final String toString() {
      return "WatchIds";
   }

   @Override
   public final int hashCode() {
      return 2103198835;
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      return this == var1 ? true : var1 instanceof AutoRefillHandler$WatchIds;
   }

   private static final int mainHandSelected$lambda$0() {
      return 36 + (VanillaUtil.INSTANCE.inGame() ? InventoryKt.vMainhandIndex() : 0);
   }

   private static final int mainHandSelected$lambda$1() {
      return InventoryKt.vMainhandIndex();
   }

   private static final int offHand$lambda$2() {
      return 45;
   }

   private static final int offHand$lambda$3() {
      return 40;
   }

   private static final int head$lambda$4() {
      return 5;
   }

   private static final int head$lambda$5() {
      return 39;
   }

   private static final int chest$lambda$6() {
      return 6;
   }

   private static final int chest$lambda$7() {
      return 38;
   }

   private static final int legs$lambda$8() {
      return 7;
   }

   private static final int legs$lambda$9() {
      return 37;
   }

   private static final int feet$lambda$10() {
      return 8;
   }

   private static final int feet$lambda$11() {
      return 36;
   }
}
