package org.anti_ad.mc.ipnext.event.villagers;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.Serializable;
import net.minecraft.nbt.Tag;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serializable
@SourceDebugExtension(
   "SMAP\nVillagerTradeData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VillagerTradeData.kt\norg/anti_ad/mc/ipnext/event/villagers/VillagerTradeData\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,43:1\n1#2:44\n*E\n"
)
public final class VillagerTradeData {
   @NotNull
   public static final VillagerTradeData$Companion Companion = new VillagerTradeData$Companion(null);
   @NotNull
   private final String resultItem;
   @NotNull
   private final String priceItem1;
   @Nullable
   private final String priceItem2;
   @Nullable
   private final String resultItemNBT;
   @Nullable
   private final String priceItem1NBT;
   @Nullable
   private final String priceItem2NBT;
   @Nullable
   private final Tag resultNbt;
   @Nullable
   private final Tag price1Nbt;
   @Nullable
   private final Tag price2Nbt;

   public VillagerTradeData(
      @NotNull String var1, @NotNull String var2, @Nullable String var3, @Nullable String var4, @Nullable String var5, @Nullable String var6
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.resultItem = var1;
      this.priceItem1 = var2;
      this.priceItem2 = var3;
      this.resultItemNBT = var4;
      this.priceItem1NBT = var5;
      this.priceItem2NBT = var6;
      VillagerTradeData var10000 = this;
      Tag var10001;
      if (this.resultItemNBT != null) {
         var1 = this.resultItemNBT;
         VillagerTradeData var10 = this;
         var10001 = NbtUtils.INSTANCE.nullIfEmpty((Tag)NbtUtils.INSTANCE.parseNbt(var1));
         var10000 = var10;
      } else {
         var10001 = null;
      }

      var10000.resultNbt = var10001;
      var10000 = this;
      if (this.priceItem1NBT != null) {
         var1 = this.priceItem1NBT;
         VillagerTradeData var11 = this;
         var10001 = NbtUtils.INSTANCE.nullIfEmpty((Tag)NbtUtils.INSTANCE.parseNbt(var1));
         var10000 = var11;
      } else {
         var10001 = null;
      }

      var10000.price1Nbt = var10001;
      var10000 = this;
      if (this.priceItem2NBT != null) {
         var1 = this.priceItem2NBT;
         VillagerTradeData var12 = this;
         var10001 = NbtUtils.INSTANCE.nullIfEmpty((Tag)NbtUtils.INSTANCE.parseNbt(var1));
         var10000 = var12;
      } else {
         var10001 = null;
      }

      var10000.price2Nbt = var10001;
   }

   @NotNull
   public final String getResultItem() {
      return this.resultItem;
   }

   @NotNull
   public final String getPriceItem1() {
      return this.priceItem1;
   }

   @Nullable
   public final String getPriceItem2() {
      return this.priceItem2;
   }

   @Nullable
   public final String getResultItemNBT() {
      return this.resultItemNBT;
   }

   @Nullable
   public final String getPriceItem1NBT() {
      return this.priceItem1NBT;
   }

   @Nullable
   public final String getPriceItem2NBT() {
      return this.priceItem2NBT;
   }

   @Nullable
   public final Tag getResultNbt() {
      return this.resultNbt;
   }

   @Nullable
   public final Tag getPrice1Nbt() {
      return this.price1Nbt;
   }

   @Nullable
   public final Tag getPrice2Nbt() {
      return this.price2Nbt;
   }

   @NotNull
   public final String component1() {
      return this.resultItem;
   }

   @NotNull
   public final String component2() {
      return this.priceItem1;
   }

   @Nullable
   public final String component3() {
      return this.priceItem2;
   }

   @Nullable
   public final String component4() {
      return this.resultItemNBT;
   }

   @Nullable
   public final String component5() {
      return this.priceItem1NBT;
   }

   @Nullable
   public final String component6() {
      return this.priceItem2NBT;
   }

   @NotNull
   public final VillagerTradeData copy(
      @NotNull String var1, @NotNull String var2, @Nullable String var3, @Nullable String var4, @Nullable String var5, @Nullable String var6
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return new VillagerTradeData(var1, var2, var3, var4, var5, var6);
   }

   @NotNull
   @Override
   public final String toString() {
      return "VillagerTradeData(resultItem="
         + this.resultItem
         + ", priceItem1="
         + this.priceItem1
         + ", priceItem2="
         + this.priceItem2
         + ", resultItemNBT="
         + this.resultItemNBT
         + ", priceItem1NBT="
         + this.priceItem1NBT
         + ", priceItem2NBT="
         + this.priceItem2NBT
         + ")";
   }

   @Override
   public final int hashCode() {
      return (
               (
                        ((this.resultItem.hashCode() * 31 + this.priceItem1.hashCode()) * 31 + (this.priceItem2 == null ? 0 : this.priceItem2.hashCode())) * 31
                           + (this.resultItemNBT == null ? 0 : this.resultItemNBT.hashCode())
                     )
                     * 31
                  + (this.priceItem1NBT == null ? 0 : this.priceItem1NBT.hashCode())
            )
            * 31
         + (this.priceItem2NBT == null ? 0 : this.priceItem2NBT.hashCode());
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof VillagerTradeData var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.resultItem, var2.resultItem)) {
         return false;
      } else if (!Intrinsics.areEqual(this.priceItem1, var2.priceItem1)) {
         return false;
      } else if (!Intrinsics.areEqual(this.priceItem2, var2.priceItem2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.resultItemNBT, var2.resultItemNBT)) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.priceItem1NBT, var2.priceItem1NBT) ? false : Intrinsics.areEqual(this.priceItem2NBT, var2.priceItem2NBT);
      }
   }
}
