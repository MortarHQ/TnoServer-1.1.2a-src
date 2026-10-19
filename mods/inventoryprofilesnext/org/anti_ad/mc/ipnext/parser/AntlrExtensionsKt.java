package org.anti_ad.mc.ipnext.parser;

import java.nio.Buffer;
import java.nio.CharBuffer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.a.b.a.a.a.D;
import org.anti_ad.a.b.a.a.a.h;
import org.anti_ad.a.b.a.a.a.j;
import org.anti_ad.a.b.a.a.a.l;
import org.anti_ad.a.b.a.a.a.s;
import org.anti_ad.a.b.a.a.a.z;
import org.jetbrains.annotations.NotNull;

public final class AntlrExtensionsKt {
   @NotNull
   public static final D parseBy(@NotNull String var0, @NotNull Function1 var1, @NotNull Function1 var2, int var3) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      String var9 = "<unknown>";
      String var6 = var0;
      j var4 = h.a(var0.length());
      CharBuffer var5;
      (var5 = CharBuffer.allocate(var6.length())).put(var6);
      ((Buffer)var5).flip();
      var4.a(var5);
      l var14 = l.a(var4.a(), var9);
      Intrinsics.checkNotNullExpressionValue(var14, "");
      Object var7;
      z var10;
      (var10 = (z)(var7 = var1.invoke(var14))).removeErrorListeners();
      var10.addErrorListener(ErrorListener.INSTANCE);
      var10.mode(var3);
      z var11 = (z)var7;
      s var12 = new s(var11);
      D var8;
      D var13;
      (var13 = var8 = (D)var2.invoke(var12)).removeErrorListeners();
      var13.addErrorListener(ErrorListener.INSTANCE);
      return var8;
   }
}
