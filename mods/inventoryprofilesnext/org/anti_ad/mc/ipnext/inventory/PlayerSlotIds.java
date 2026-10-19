package org.anti_ad.mc.ipnext.inventory;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nAreaTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/PlayerSlotIds\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,384:1\n127#2:385\n*S KotlinDebug\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/PlayerSlotIds\n*L\n51#1:385\n*E\n"
)
public final class PlayerSlotIds {
   @NotNull
   private final IntRange hotbarInvSlots;
   @NotNull
   private final IntRange storageInvSlots;
   private final int offhandInvSlot;
   @NotNull
   private final Function0 mainhandInvSlot;

   public PlayerSlotIds(@NotNull IntRange var1, @NotNull IntRange var2, int var3, @NotNull Function0 var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var4, "");
      super();
      this.hotbarInvSlots = var1;
      this.storageInvSlots = var2;
      this.offhandInvSlot = var3;
      this.mainhandInvSlot = var4;
   }

   @NotNull
   public final IntRange getHotbarInvSlots() {
      return this.hotbarInvSlots;
   }

   @NotNull
   public final IntRange getStorageInvSlots() {
      return this.storageInvSlots;
   }

   public final int getOffhandInvSlot() {
      return this.offhandInvSlot;
   }

   @NotNull
   public final Function0 getMainhandInvSlot() {
      return this.mainhandInvSlot;
   }

   @NotNull
   public final IntRange component1() {
      return this.hotbarInvSlots;
   }

   @NotNull
   public final IntRange component2() {
      return this.storageInvSlots;
   }

   public final int component3() {
      return this.offhandInvSlot;
   }

   @NotNull
   public final Function0 component4() {
      return this.mainhandInvSlot;
   }

   @NotNull
   public final PlayerSlotIds copy(@NotNull IntRange var1, @NotNull IntRange var2, int var3, @NotNull Function0 var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var4, "");
      return new PlayerSlotIds(var1, var2, var3, var4);
   }

   @NotNull
   @Override
   public final String toString() {
      return "PlayerSlotIds(hotbarInvSlots="
         + this.hotbarInvSlots
         + ", storageInvSlots="
         + this.storageInvSlots
         + ", offhandInvSlot="
         + this.offhandInvSlot
         + ", mainhandInvSlot="
         + this.mainhandInvSlot
         + ")";
   }

   @Override
   public final int hashCode() {
      return ((this.hotbarInvSlots.hashCode() * 31 + this.storageInvSlots.hashCode()) * 31 + Integer.hashCode(this.offhandInvSlot)) * 31
         + this.mainhandInvSlot.hashCode();
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof PlayerSlotIds var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.hotbarInvSlots, var2.hotbarInvSlots)) {
         return false;
      } else if (!Intrinsics.areEqual(this.storageInvSlots, var2.storageInvSlots)) {
         return false;
      } else {
         return this.offhandInvSlot != var2.offhandInvSlot ? false : Intrinsics.areEqual(this.mainhandInvSlot, var2.mainhandInvSlot);
      }
   }

   private static final int _init_$lambda$0() {
      return Vanilla.INSTANCE.playerInventory().selected;
   }

   public PlayerSlotIds() {
      this(null, null, 0, null, 15, null);
   }
}
