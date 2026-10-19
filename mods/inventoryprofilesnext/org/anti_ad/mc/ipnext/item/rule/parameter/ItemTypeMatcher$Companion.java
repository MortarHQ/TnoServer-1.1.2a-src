package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.alias.util.UtilExKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nItemTypeMatcher.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemTypeMatcher.kt\norg/anti_ad/mc/ipnext/item/rule/parameter/ItemTypeMatcher$Companion\n+ 2 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n*L\n1#1,70:1\n33#2:71\n89#2,4:72\n34#2:76\n33#2:77\n89#2,4:78\n34#2:82\n*S KotlinDebug\n*F\n+ 1 ItemTypeMatcher.kt\norg/anti_ad/mc/ipnext/item/rule/parameter/ItemTypeMatcher$Companion\n*L\n57#1:71\n57#1:72,4\n57#1:76\n63#1:77\n63#1:78,4\n63#1:82\n*E\n"
)
public final class ItemTypeMatcher$Companion {
   private ItemTypeMatcher$Companion() {
   }

   @Nullable
   public final ItemTypeMatcher forTag(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      try {
         var3 = UtilExKt.IdentifierOf(var1);
      } catch (Throwable var2) {
         var3 = null;
      }

      return var3 == null ? null : new ItemTypeMatcher$IsTag(var3);
   }

   @Nullable
   public final ItemTypeMatcher forItem(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      try {
         var3 = UtilExKt.IdentifierOf(var1);
      } catch (Throwable var2) {
         var3 = null;
      }

      return var3 == null ? null : new ItemTypeMatcher$IsItem(var3);
   }
}
