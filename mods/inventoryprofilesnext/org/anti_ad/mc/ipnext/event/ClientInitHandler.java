package org.anti_ad.mc.ipnext.event;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nClientInitHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientInitHandler.kt\norg/anti_ad/mc/ipnext/event/ClientInitHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n1869#2,2:50\n*S KotlinDebug\n*F\n+ 1 ClientInitHandler.kt\norg/anti_ad/mc/ipnext/event/ClientInitHandler\n*L\n26#1:50,2\n*E\n"
)
public final class ClientInitHandler {
   @NotNull
   public static final ClientInitHandler INSTANCE = new ClientInitHandler();
   private static boolean onInit;
   @NotNull
   private static final Set registered = new LinkedHashSet();

   private ClientInitHandler() {
   }

   private final void onInit() {
      Iterator var1 = registered.iterator();

      while (var1.hasNext()) {
         ((Function0)var1.next()).invoke();
      }

      registered.clear();
   }

   public final void onTickPre() {
      if (!onInit) {
         onInit = true;
         this.onInit();
      }
   }

   public final boolean register(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return registered.add(var1);
   }

   public final boolean unregister(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return registered.remove(var1);
   }
}
