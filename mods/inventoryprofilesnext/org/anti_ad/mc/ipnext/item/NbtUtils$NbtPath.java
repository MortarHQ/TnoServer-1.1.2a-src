package org.anti_ad.mc.ipnext.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.arguments.NbtPathArgument.NbtPath;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nNbtUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NbtUtils.kt\norg/anti_ad/mc/ipnext/item/NbtUtils$NbtPath\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,282:1\n1563#2:283\n1634#2,3:284\n*S KotlinDebug\n*F\n+ 1 NbtUtils.kt\norg/anti_ad/mc/ipnext/item/NbtUtils$NbtPath\n*L\n198#1:283\n198#1:284,3\n*E\n"
)
public final class NbtUtils$NbtPath {
   @NotNull
   public static final NbtUtils$NbtPath$Companion Companion = new NbtUtils$NbtPath$Companion(null);
   @NotNull
   private final NbtPath value;

   public NbtUtils$NbtPath(@NotNull NbtPath var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.value = var1;
   }

   @NotNull
   public final NbtPath getValue() {
      return this.value;
   }

   @NotNull
   public final List getTags(@NotNull ItemType var1, @Nullable DataComponentType var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      PatchedDataComponentMap var5;
      if ((var5 = var1.getTag()) == null) {
         return CollectionsKt.emptyList();
      }

      Tag var12;
      if (var2 != null) {
         ComponentUtils var10000 = ComponentUtils.INSTANCE;
         Optional var10002 = Optional.ofNullable(var5.get(var2));
         Intrinsics.checkNotNullExpressionValue(var10002, "");
         var12 = var10000.toFilteredNbtOrNull(var2, var10002);
      } else {
         var12 = null;
      }

      Tag var6 = var12;
      if (var12 == null) {
         return CollectionsKt.emptyList();
      }

      Iterable var7;
      Iterable var9 = var7 = NbtUtils.access$getTagsForPath(NbtUtils.INSTANCE, this.value, var6);
      var var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

      for (Tag var3 : var9) {
         var3 = var3;
         ArrayList var4 = var8;
         var4.add(new NbtUtils$WrappedTag(var3));
      }

      return var8;
   }
}
