package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.Tag;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.jetbrains.annotations.NotNull;

public final class NbtArgumentType implements ArgumentType {
   @NotNull
   public static final NbtArgumentType INSTANCE = new NbtArgumentType();

   private NbtArgumentType() {
   }

   @NotNull
   public final String toString(@NotNull Tag var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.toString();
   }

   @NotNull
   public final Tag parse(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return NbtUtils.INSTANCE.parseNbtOrEmpty(var1);
   }
}
