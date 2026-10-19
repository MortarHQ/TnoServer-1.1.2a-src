package org.anti_ad.mc.ipnext.integration;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.internal.EnumsKt;
import kotlinx.serialization.internal.LinkedHashMapSerializer;
import kotlinx.serialization.internal.LinkedHashSetSerializer;
import kotlinx.serialization.internal.StringSerializer;
import org.anti_ad.mc.ipn.api.IPNButton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serializable
@SourceDebugExtension(
   "SMAP\nHintClassData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HintClassData.kt\norg/anti_ad/mc/ipnext/integration/HintClassData\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,108:1\n1#2:109\n216#3,2:110\n216#3,2:119\n506#4,7:112\n1869#5,2:121\n*S KotlinDebug\n*F\n+ 1 HintClassData.kt\norg/anti_ad/mc/ipnext/integration/HintClassData\n*L\n70#1:110,2\n86#1:119,2\n79#1:112,7\n95#1:121,2\n*E\n"
)
public final class HintClassData {
   @NotNull
   public static final HintClassData$Companion Companion = new HintClassData$Companion(null);
   private boolean ignore;
   private boolean playerSideOnly;
   private boolean disableFastSwipe;
   @NotNull
   private final Map buttonHints;
   @NotNull
   private final Set slotIgnoreInventoryTypes;
   private final boolean ignoreCraftingGrid;
   private final boolean avoidShiftClick;
   private boolean force;
   @Nullable
   private String id;
   private boolean dirty;
   @JvmField
   @NotNull
   private static final Lazy[] $childSerializers;

   public HintClassData(boolean var1, boolean var2, boolean var3, @NotNull Map var4, @NotNull Set var5, boolean var6, boolean var7, boolean var8) {
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      super();
      this.ignore = var1;
      this.playerSideOnly = var2;
      this.disableFastSwipe = var3;
      this.buttonHints = var4;
      this.slotIgnoreInventoryTypes = var5;
      this.ignoreCraftingGrid = var6;
      this.avoidShiftClick = var7;
      this.force = var8;
   }

   public final boolean getIgnore() {
      return this.ignore;
   }

   public final void setIgnore(boolean var1) {
      this.ignore = var1;
   }

   public final boolean getPlayerSideOnly() {
      return this.playerSideOnly;
   }

   public final void setPlayerSideOnly(boolean var1) {
      this.playerSideOnly = var1;
   }

   public final boolean getDisableFastSwipe() {
      return this.disableFastSwipe;
   }

   public final void setDisableFastSwipe(boolean var1) {
      this.disableFastSwipe = var1;
   }

   @NotNull
   public final Map getButtonHints() {
      return this.buttonHints;
   }

   @NotNull
   public final Set getSlotIgnoreInventoryTypes() {
      return this.slotIgnoreInventoryTypes;
   }

   public final boolean getIgnoreCraftingGrid() {
      return this.ignoreCraftingGrid;
   }

   public final boolean getAvoidShiftClick() {
      return this.avoidShiftClick;
   }

   public final boolean getForce() {
      return this.force;
   }

   public final void setForce(boolean var1) {
      this.force = var1;
   }

   public final void changeId(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.id = var1;
   }

   @Nullable
   public final String readId() {
      return this.id;
   }

   public final boolean dirty() {
      return this.dirty;
   }

   public final void markAsDirty() {
      this.dirty = true;
   }

   @NotNull
   public final ButtonPositionHint hintFor(@NotNull IPNButton var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      ButtonPositionHint var10000 = (ButtonPositionHint)this.buttonHints.get(var1);
      if (var10000 == null) {
         ButtonPositionHint var2;
         ButtonPositionHint var3 = var2 = new ButtonPositionHint(0, 0, 0, false, 15, null);
         this.buttonHints.put(var1, var3);
         var10000 = var2;
      }

      return var10000;
   }

   public final boolean areButtonsMoved() {
      Iterator var1 = this.buttonHints.entrySet().iterator();

      while (var1.hasNext()) {
         if (((ButtonPositionHint)((Entry)var1.next()).getValue()).getDirty()) {
            return true;
         }
      }

      return false;
   }

   public final boolean hasInfo() {
      if (!this.disableFastSwipe && !this.playerSideOnly && !this.ignore && !this.force && this.slotIgnoreInventoryTypes.isEmpty()) {
         Map var1 = this.buttonHints;
         LinkedHashMap var2 = new LinkedHashMap();
         Iterator var5 = var1.entrySet().iterator();

         while (var5.hasNext()) {
            Entry var3;
            ButtonPositionHint var4;
            if ((var4 = (ButtonPositionHint)(var3 = (Entry)var5.next()).getValue()).getTop() != 0
               || var4.getHorizontalOffset() != 0
               || var4.getBottom() != 0
               || var4.getHide()) {
               var2.put(var3.getKey(), var3.getValue());
            }
         }

         if (var2.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @NotNull
   public final Map copyOnlyChanged() {
      Map var1 = new LinkedHashMap();
      Iterator var2 = this.buttonHints.entrySet().iterator();

      while (var2.hasNext()) {
         Entry var3;
         IPNButton var4 = (IPNButton)(var3 = (Entry)var2.next()).getKey();
         ButtonPositionHint var5;
         if ((var5 = (ButtonPositionHint)var3.getValue()).getHide() || var5.getTop() != 0 || var5.getHorizontalOffset() != 0 || var5.getBottom() != 0) {
            var1.put(var4, ButtonPositionHint.copy$default(var5, 0, 0, 0, false, 15, null));
         }
      }

      return var1;
   }

   public final void fillMissingHints() {
      for (IPNButton var2 : (Iterable)HintClassData$EntriesMappings.entries$0) {
         this.buttonHints.putIfAbsent(var2, new ButtonPositionHint(0, 0, 0, false, 15, null));
      }
   }

   public final boolean component1() {
      return this.ignore;
   }

   public final boolean component2() {
      return this.playerSideOnly;
   }

   public final boolean component3() {
      return this.disableFastSwipe;
   }

   @NotNull
   public final Map component4() {
      return this.buttonHints;
   }

   @NotNull
   public final Set component5() {
      return this.slotIgnoreInventoryTypes;
   }

   public final boolean component6() {
      return this.ignoreCraftingGrid;
   }

   public final boolean component7() {
      return this.avoidShiftClick;
   }

   public final boolean component8() {
      return this.force;
   }

   @NotNull
   public final HintClassData copy(boolean var1, boolean var2, boolean var3, @NotNull Map var4, @NotNull Set var5, boolean var6, boolean var7, boolean var8) {
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      return new HintClassData(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @NotNull
   @Override
   public final String toString() {
      return "HintClassData(ignore="
         + this.ignore
         + ", playerSideOnly="
         + this.playerSideOnly
         + ", disableFastSwipe="
         + this.disableFastSwipe
         + ", buttonHints="
         + this.buttonHints
         + ", slotIgnoreInventoryTypes="
         + this.slotIgnoreInventoryTypes
         + ", ignoreCraftingGrid="
         + this.ignoreCraftingGrid
         + ", avoidShiftClick="
         + this.avoidShiftClick
         + ", force="
         + this.force
         + ")";
   }

   @Override
   public final int hashCode() {
      return (
               (
                        (
                                 (
                                          (
                                                   (Boolean.hashCode(this.ignore) * 31 + Boolean.hashCode(this.playerSideOnly)) * 31
                                                      + Boolean.hashCode(this.disableFastSwipe)
                                                )
                                                * 31
                                             + this.buttonHints.hashCode()
                                       )
                                       * 31
                                    + this.slotIgnoreInventoryTypes.hashCode()
                              )
                              * 31
                           + Boolean.hashCode(this.ignoreCraftingGrid)
                     )
                     * 31
                  + Boolean.hashCode(this.avoidShiftClick)
            )
            * 31
         + Boolean.hashCode(this.force);
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof HintClassData var2)) {
         return false;
      } else if (this.ignore != var2.ignore) {
         return false;
      } else if (this.playerSideOnly != var2.playerSideOnly) {
         return false;
      } else if (this.disableFastSwipe != var2.disableFastSwipe) {
         return false;
      } else if (!Intrinsics.areEqual(this.buttonHints, var2.buttonHints)) {
         return false;
      } else if (!Intrinsics.areEqual(this.slotIgnoreInventoryTypes, var2.slotIgnoreInventoryTypes)) {
         return false;
      } else if (this.ignoreCraftingGrid != var2.ignoreCraftingGrid) {
         return false;
      } else {
         return this.avoidShiftClick != var2.avoidShiftClick ? false : this.force == var2.force;
      }
   }

   public HintClassData() {
      this(false, false, false, null, null, false, false, false, 255, null);
   }

   static {
      Lazy[] var0;
      (var0 = new Lazy[8])[0] = null;
      var0[1] = null;
      var0[2] = null;
      var0[3] = LazyKt.lazy(
         LazyThreadSafetyMode.PUBLICATION,
         () -> (KSerializer)(
            new LinkedHashMapSerializer(
               EnumsKt.createSimpleEnumSerializer("org.anti_ad.mc.ipn.api.IPNButton", IPNButton.values()), (KSerializer)ButtonPositionHint$$serializer.INSTANCE
            )
         )
      );
      var0[4] = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, () -> (KSerializer)(new LinkedHashSetSerializer((KSerializer)StringSerializer.INSTANCE)));
      var0[5] = null;
      var0[6] = null;
      var0[7] = null;
      $childSerializers = var0;
   }
}
