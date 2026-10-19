package org.anti_ad.mc.ipnext.access;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.ipnext.event.LockedSlotKeeper;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nIPNImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IPNImpl.kt\norg/anti_ad/mc/ipnext/access/IPNImpl$Companion\n+ 2 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,174:1\n130#2:175\n1#3:176\n1869#4,2:177\n*S KotlinDebug\n*F\n+ 1 IPNImpl.kt\norg/anti_ad/mc/ipnext/access/IPNImpl$Companion\n*L\n57#1:175\n57#1:176\n58#1:177,2\n*E\n"
)
public final class IPNImpl$Companion {
   private IPNImpl$Companion() {
   }

   public final void init() {
      IPNImpl.access$set_IPN$s72711(new IPNImpl());
   }

   public final void onTickInGame() {
      List var1 = new ArrayList();
      synchronized (IPNImpl.access$getActions$cp()) {
         var1.addAll(IPNImpl.access$getActions$cp());
         IPNImpl.access$getActions$cp().clear();
      }

      if (!var1.isEmpty()) {
         Iterator var2 = var1.iterator();

         while (var2.hasNext()) {
            ((Function0)var2.next()).invoke();
         }

         LockedSlotKeeper.INSTANCE.init();
      }

      var1.clear();
   }

   public final void addTickAction(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      synchronized (IPNImpl.access$getActions$cp()) {
         IPNImpl.access$getActions$cp().add(var1);
      }
   }
}
