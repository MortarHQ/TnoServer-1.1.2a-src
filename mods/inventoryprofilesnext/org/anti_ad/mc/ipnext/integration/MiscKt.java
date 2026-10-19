package org.anti_ad.mc.ipnext.integration;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonBuilder;
import kotlinx.serialization.json.JsonKt;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.ipnext.Log;
import org.jetbrains.annotations.NotNull;

public final class MiscKt {
   @NotNull
   private static final Json json = JsonKt.Json$default(null, MiscKt::json$lambda$0, 1, null);

   public static final void logError(@NotNull Throwable var0, @NotNull String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      if (var2) {
         TellPlayer.INSTANCE.chat("Error parsing hint file: '" + var1 + "'. Error: '" + var0.getMessage() + "'");
      }

      Log.INSTANCE.error("Unable to parse hint file: '" + var1 + "'. Error: " + var0.getMessage(), var0);
   }

   public static final Object tryLog(@NotNull String var0, boolean var1, @NotNull Function3 var2, @NotNull Function0 var3) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");

      try {
         var5 = var3.invoke();
      } catch (Throwable var4) {
         var5 = var2.invoke(var4, var0, var1);
      }

      return var5;
   }

   @NotNull
   public static final Json getJson() {
      return json;
   }

   private static final Unit json$lambda$0(JsonBuilder var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      var0.setIgnoreUnknownKeys(true);
      var0.setPrettyPrint(true);
      return Unit.INSTANCE;
   }
}
