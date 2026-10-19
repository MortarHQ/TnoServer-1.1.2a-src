package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.anti_ad.mc.alias.util.UtilExKt;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.jetbrains.annotations.NotNull;

public final class IdentifierArgumentType implements ArgumentType {
   @NotNull
   public static final IdentifierArgumentType INSTANCE = new IdentifierArgumentType();

   private IdentifierArgumentType() {
   }

   @NotNull
   public final String toString(@NotNull ResourceLocation var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.getNamespace() + ":" + var1.getPath();
   }

   @NotNull
   public final ResourceLocation parse(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return UtilExKt.IdentifierOf(var1);
   }
}
