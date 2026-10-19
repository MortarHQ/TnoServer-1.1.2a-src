package org.anti_ad.mc.ipnext.profiles.config;

import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProfileItemData {
   @NotNull
   private final String itemId;
   @NotNull
   private final String customName;
   @Nullable
   private final List components;

   public ProfileItemData(@NotNull String var1, @NotNull String var2, @Nullable List var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.itemId = var1;
      this.customName = var2;
      this.components = var3;
   }

   @NotNull
   public final String getItemId() {
      return this.itemId;
   }

   @NotNull
   public final String getCustomName() {
      return this.customName;
   }

   @Nullable
   public final List getComponents() {
      return this.components;
   }

   @NotNull
   @Override
   public final String toString() {
      Collection var1;
      return (var1 = this.components) != null && !var1.isEmpty()
         ? this.itemIdString() + " -> [ " + CollectionsKt.joinToString$default(this.components, ", ", null, null, 0, null, null, 62, null) + " ]"
         : this.itemIdString();
   }

   private final String itemIdString() {
      return !StringsKt.isBlank(this.customName) ? "\"" + this.itemId + "\"(\"" + this.customName + "\")" : "\"" + this.itemId + "\"";
   }

   @NotNull
   public final String component1() {
      return this.itemId;
   }

   @NotNull
   public final String component2() {
      return this.customName;
   }

   @Nullable
   public final List component3() {
      return this.components;
   }

   @NotNull
   public final ProfileItemData copy(@NotNull String var1, @NotNull String var2, @Nullable List var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return new ProfileItemData(var1, var2, var3);
   }

   @Override
   public final int hashCode() {
      return (this.itemId.hashCode() * 31 + this.customName.hashCode()) * 31 + (this.components == null ? 0 : this.components.hashCode());
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ProfileItemData var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.itemId, var2.itemId)) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.customName, var2.customName) ? false : Intrinsics.areEqual(this.components, var2.components);
      }
   }
}
