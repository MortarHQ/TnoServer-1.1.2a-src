package org.anti_ad.mc.ipnext.event;

import java.util.List;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.profiles.config.ProfileItemData;

// $VF: synthetic class
final class ProfileSwitchHandler$ProfileMonitor$findAndSwap$swapWith$1 extends FunctionReferenceImpl implements Function2 {
   ProfileSwitchHandler$ProfileMonitor$findAndSwap$swapWith$1(Object var1) {
      super(
         2,
         var1,
         ProfileSwitchHandler$ProfileMonitor.class,
         "bestMatch",
         "bestMatch(Lorg/anti_ad/mc/ipnext/profiles/config/ProfileItemData;Ljava/util/List;)Ljava/lang/Integer;",
         0
      );
   }

   public final Integer invoke(ProfileItemData var1, List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return ProfileSwitchHandler$ProfileMonitor.access$bestMatch((ProfileSwitchHandler$ProfileMonitor)this.receiver, var1, var2);
   }
}
