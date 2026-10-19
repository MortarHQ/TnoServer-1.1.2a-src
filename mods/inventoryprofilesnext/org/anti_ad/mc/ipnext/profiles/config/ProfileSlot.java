package org.anti_ad.mc.ipnext.profiles.config;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProfileSlot {
   @NotNull
   private final ProfileSlotId id;
   @NotNull
   private final List items;

   public ProfileSlot(@NotNull ProfileSlotId var1, @NotNull List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.id = var1;
      this.items = var2;
   }

   @NotNull
   public final ProfileSlotId getId() {
      return this.id;
   }

   @NotNull
   public final List getItems() {
      return this.items;
   }

   @NotNull
   @Override
   public final String toString() {
      return this.id + "\n" + CollectionsKt.joinToString$default(this.items, "\n\t\t", "\t\t", null, 0, null, ProfileSlot::toString$lambda$0, 28, null);
   }

   @NotNull
   public final ProfileSlotId component1() {
      return this.id;
   }

   @NotNull
   public final List component2() {
      return this.items;
   }

   @NotNull
   public final ProfileSlot copy(@NotNull ProfileSlotId var1, @NotNull List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return new ProfileSlot(var1, var2);
   }

   @Override
   public final int hashCode() {
      return this.id.hashCode() * 31 + this.items.hashCode();
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ProfileSlot var2)) {
         return false;
      } else {
         return this.id != var2.id ? false : Intrinsics.areEqual(this.items, var2.items);
      }
   }

   private static final CharSequence toString$lambda$0(ProfileItemData var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.toString();
   }
}
