package org.anti_ad.mc.ipnext.inventory;

import java.util.Set;
import kotlin.collections.SetsKt;
import org.jetbrains.annotations.NotNull;

public final class ContainerTypesKt {
   @NotNull
   private static final Set nonStorage = SetsKt.setOf(ContainerType.TEMP_SLOTS);
   @NotNull
   private static final Set playerOnly;
   @NotNull
   private static final Set simple = SetsKt.setOf(ContainerType.PURE_BACKPACK);

   @NotNull
   public static final Set getNonStorage() {
      return nonStorage;
   }

   @NotNull
   public static final Set getPlayerOnly() {
      return playerOnly;
   }

   @NotNull
   public static final Set getSimple() {
      return simple;
   }

   static {
      ContainerType[] var0;
      (var0 = new ContainerType[3])[0] = ContainerType.PURE_BACKPACK;
      var0[1] = ContainerType.PLAYER;
      var0[2] = ContainerType.CRAFTING;
      playerOnly = SetsKt.setOf(var0);
   }
}
