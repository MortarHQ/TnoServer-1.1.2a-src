package org.anti_ad.mc.ipnext.item;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nItemTypeExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/PotionEffect\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,494:1\n1#2:495\n*E\n"
)
public final class PotionEffect implements Comparable {
   @NotNull
   private final String effect;
   private final int amplifier;
   private final int duration;

   public PotionEffect(@NotNull String var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.effect = var1;
      this.amplifier = var2;
      this.duration = var3;
   }

   @NotNull
   public final String getEffect() {
      return this.effect;
   }

   public final int getAmplifier() {
      return this.amplifier;
   }

   public final int getDuration() {
      return this.duration;
   }

   public final int compareTo(@NotNull PotionEffect var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      int var2;
      if ((var2 = this.effect.compareTo(var1.effect)) != 0) {
         return var2;
      } else if ((var2 = Intrinsics.compare(var1.amplifier, this.amplifier)) != 0) {
         return var2;
      } else {
         return (var2 = Intrinsics.compare(var1.duration, this.duration)) != 0 ? var2 : 0;
      }
   }

   @NotNull
   public final String component1() {
      return this.effect;
   }

   public final int component2() {
      return this.amplifier;
   }

   public final int component3() {
      return this.duration;
   }

   @NotNull
   public final PotionEffect copy(@NotNull String var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      return new PotionEffect(var1, var2, var3);
   }

   @NotNull
   @Override
   public final String toString() {
      return "PotionEffect(effect=" + this.effect + ", amplifier=" + this.amplifier + ", duration=" + this.duration + ")";
   }

   @Override
   public final int hashCode() {
      return (this.effect.hashCode() * 31 + Integer.hashCode(this.amplifier)) * 31 + Integer.hashCode(this.duration);
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof PotionEffect var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.effect, var2.effect)) {
         return false;
      } else {
         return this.amplifier != var2.amplifier ? false : this.duration == var2.duration;
      }
   }
}
