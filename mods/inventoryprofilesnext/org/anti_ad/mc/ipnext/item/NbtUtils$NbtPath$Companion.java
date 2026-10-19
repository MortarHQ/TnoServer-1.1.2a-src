package org.anti_ad.mc.ipnext.item;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.arguments.NbtPathArgument.NbtPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nNbtUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NbtUtils.kt\norg/anti_ad/mc/ipnext/item/NbtUtils$NbtPath$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,282:1\n1#2:283\n*E\n"
)
public final class NbtUtils$NbtPath$Companion {
   private NbtUtils$NbtPath$Companion() {
   }

   @Nullable
   public final NbtUtils$NbtPath of(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      NbtPath var10000 = NbtUtils.access$getNbtPath(NbtUtils.INSTANCE, var1);
      if (var10000 != null) {
         NbtPath var2 = var10000;
         return new NbtUtils$NbtPath(var2);
      } else {
         return null;
      }
   }
}
