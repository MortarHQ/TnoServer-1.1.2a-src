package org.anti_ad.mc.ipnext;

import java.net.URI;
import java.net.URL;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.SharedConstants;
import org.anti_ad.mc.ipnext.access.IPNImpl;
import org.anti_ad.mc.ipnext.compat.integrations.Integrations;
import org.anti_ad.mc.ipnext.config.ConfigScreenSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.ClientInitHandler;
import org.anti_ad.mc.ipnext.gui.inject.InsertWidgetHandler;
import org.anti_ad.mc.ipnext.input.InputHandler;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nInventoryProfiles.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InventoryProfiles.kt\norg/anti_ad/mc/ipnext/InventoryProfilesKt\n+ 2 SmallHelpers.kt\norg/anti_ad/mc/ipnext/specific/SmallHelpersKt\n*L\n1#1,72:1\n51#2,3:73\n*S KotlinDebug\n*F\n+ 1 InventoryProfiles.kt\norg/anti_ad/mc/ipnext/InventoryProfilesKt\n*L\n56#1:73,3\n*E\n"
)
public final class InventoryProfilesKt {
   @NotNull
   private static Function0 initGlueProc = InventoryProfilesKt$initGlueProc$1.INSTANCE;
   private static final URL versionCheckUrl = new URI("https://ipn.anti-ad.org/ipn/ipnVersionCheckV3").toURL();

   @NotNull
   public static final Function0 getInitGlueProc() {
      return initGlueProc;
   }

   public static final void setInitGlueProc(@NotNull Function0 var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      initGlueProc = var0;
   }

   public static final URL getVersionCheckUrl() {
      return versionCheckUrl;
   }

   private static final void initGlues() {
      IPNImpl.Companion.init();
      initGlueProc = InventoryProfilesKt$initGlues$1.INSTANCE;
   }

   public static final void nop() {
   }

   public static final void init() {
      initGlueProc.invoke();
      PlatformSpecificInitKt.specificInit();
      ClientInitHandler.INSTANCE.register(InventoryProfilesKt::init$lambda$0);
   }

   private static final Unit init$lambda$0() {
      Integrations.INSTANCE.init();
      IPNInfoManager.INSTANCE.setLoader("neoforge");
      IPNInfoManager var10000 = IPNInfoManager.INSTANCE;
      String var10001 = SharedConstants.getCurrentVersion().getId();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      var10000.setMcVersion(var10001);
      InputHandler.INSTANCE.onClientInit();
      InsertWidgetHandler.INSTANCE.onClientInit();
      ConfigScreenSettings.INSTANCE.initMainConfig();
      if (ModSettings.INSTANCE.getFIRST_RUN().getBooleanValue()) {
         ModSettings.INSTANCE.getFIRST_RUN().setValue(false);
         ConfigScreenSettings.INSTANCE.getSaveLoadManager().save();
      }

      return Unit.INSTANCE;
   }
}
