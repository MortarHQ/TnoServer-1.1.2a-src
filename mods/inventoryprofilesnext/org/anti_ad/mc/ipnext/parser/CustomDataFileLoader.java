package org.anti_ad.mc.ipnext.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.ipnext.NotificationManager;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nCustomDataFileLoader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/CustomDataFileLoader\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,485:1\n1869#2,2:486\n1869#2,2:488\n1869#2,2:490\n*S KotlinDebug\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/CustomDataFileLoader\n*L\n170#1:486,2\n177#1:488,2\n182#1:490,2\n*E\n"
)
public final class CustomDataFileLoader {
   @NotNull
   public static final CustomDataFileLoader INSTANCE = new CustomDataFileLoader();
   @NotNull
   private static final List loaders;

   private CustomDataFileLoader() {
   }

   public final void load() {
      Iterator var1 = loaders.iterator();

      while (var1.hasNext()) {
         ((Loader)var1.next()).load();
      }
   }

   public final void reload(boolean var1) {
      if (var1) {
         TellPlayer.INSTANCE.chat("IPN - Reloading configuration...\n");
      }

      Iterator var2 = loaders.iterator();

      while (var2.hasNext()) {
         ((Loader)var2.next()).reload(var1);
      }
   }

   public final boolean doSanityCheck() {
      boolean var1 = false;
      Iterator var2 = loaders.iterator();

      while (var2.hasNext()) {
         if (((Loader)var2.next()).doSanityCheck()) {
            var1 = true;
         }
      }

      if (var1) {
         NotificationManager.INSTANCE
            .addNotification(
               "[IPN] - Check the logs for file locations.\n         This message will appear as long old config files exist.\n         It's safe to delete them. The new files have different name."
            );
      }

      return var1;
   }

   static {
      (loaders = new ArrayList()).addAll(CustomDataFileLoaderKt.access$getDefinedLoaders$p());
   }
}
