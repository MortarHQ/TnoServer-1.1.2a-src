package org.anti_ad.mc.ipnext.profiles.config;

import java.util.Arrays;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProfileData {
   @NotNull
   private final String name;
   @NotNull
   private final ProfileSlotId active;
   @NotNull
   private final List slots;
   private final boolean valid;

   public ProfileData(@NotNull String var1, @NotNull ProfileSlotId var2, @NotNull List var3, boolean var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      super();
      this.name = var1;
      this.active = var2;
      this.slots = var3;
      this.valid = var4;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   @NotNull
   public final ProfileSlotId getActive() {
      return this.active;
   }

   @NotNull
   public final List getSlots() {
      return this.slots;
   }

   public final boolean getValid() {
      return this.valid;
   }

   @NotNull
   @Override
   public final String toString() {
      String var1 = (this.active != ProfileSlotId.NONE ? "profile " + this.name + " activate " + this.active.name() : "profile " + this.name) + "\n%s\n";
      Object[] var2;
      (var2 = new Object[1])[0] = CollectionsKt.joinToString$default(this.slots, "\n\t", "\t", null, 0, null, ProfileData::toString$lambda$0, 28, null);
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   @NotNull
   public final String component1() {
      return this.name;
   }

   @NotNull
   public final ProfileSlotId component2() {
      return this.active;
   }

   @NotNull
   public final List component3() {
      return this.slots;
   }

   public final boolean component4() {
      return this.valid;
   }

   @NotNull
   public final ProfileData copy(@NotNull String var1, @NotNull ProfileSlotId var2, @NotNull List var3, boolean var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      return new ProfileData(var1, var2, var3, var4);
   }

   @Override
   public final int hashCode() {
      return ((this.name.hashCode() * 31 + this.active.hashCode()) * 31 + this.slots.hashCode()) * 31 + Boolean.hashCode(this.valid);
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ProfileData var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.name, var2.name)) {
         return false;
      } else if (this.active != var2.active) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.slots, var2.slots) ? false : this.valid == var2.valid;
      }
   }

   private static final CharSequence toString$lambda$0(ProfileSlot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.toString();
   }
}
