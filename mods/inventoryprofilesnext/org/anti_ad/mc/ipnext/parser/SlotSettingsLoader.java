package org.anti_ad.mc.ipnext.parser;

import java.nio.file.Path;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.ipnext.integration.SlotIntegrationHints;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nCustomDataFileLoader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/SlotSettingsLoader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,485:1\n1#2:486\n*E\n"
)
public final class SlotSettingsLoader implements Loader {
   @NotNull
   public static final SlotSettingsLoader INSTANCE = new SlotSettingsLoader();
   private static boolean firstLoad;

   private SlotSettingsLoader() {
   }

   @Override
   public final void load() {
      if (!firstLoad) {
         firstLoad = true;
         this.reload(false);
      }
   }

   @Override
   public final boolean doSanityCheck() {
      return false;
   }

   @Override
   public final void reload(boolean var1) {
      if (var1) {
         TellPlayer.INSTANCE.chat("Loading Slot Hints configs...");
      }

      Path var2;
      Java_ioKt.createDirectories(var2 = Java_ioKt.div(CustomDataFileLoaderKt.access$getConfigFolder$p(), "integrationHints"));
      SlotIntegrationHints.INSTANCE.init(CustomDataFileLoaderKt.access$getConfigFolder$p(), var2, var1);
      if (var1) {
         TellPlayer.INSTANCE.chat("");
      }
   }
}
