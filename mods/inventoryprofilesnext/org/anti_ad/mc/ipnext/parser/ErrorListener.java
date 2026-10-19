package org.anti_ad.mc.ipnext.parser;

import java.util.BitSet;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.a.b.a.a.a.D;
import org.anti_ad.a.b.a.a.a.J;
import org.anti_ad.a.b.a.a.a.K;
import org.anti_ad.a.b.a.a.a.e;
import org.anti_ad.a.b.a.a.a.a.c;
import org.anti_ad.a.b.a.a.a.b.a;
import org.anti_ad.mc.ipnext.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ErrorListener extends e {
   @NotNull
   public static final ErrorListener INSTANCE = new ErrorListener();

   private ErrorListener() {
   }

   @Override
   public final void syntaxError(@Nullable K var1, @Nullable Object var2, int var3, int var4, @NotNull String var5, @Nullable J var6) {
      Intrinsics.checkNotNullParameter(var5, "");
      throw new SyntaxErrorException(var3, var4, var5);
   }

   @Override
   public final void reportAmbiguity(@Nullable D var1, @Nullable a var2, int var3, int var4, boolean var5, @Nullable BitSet var6, @Nullable c var7) {
      Log.INSTANCE.trace(ErrorListener::reportAmbiguity$lambda$0);
      super.reportAmbiguity(var1, var2, var3, var4, var5, var6, var7);
   }

   private static final String reportAmbiguity$lambda$0(int var0, int var1) {
      return "reportAmbiguity from: " + var0 + " to " + var1;
   }
}
