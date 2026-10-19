package org.anti_ad.mc.ipnext.neoforge;

import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.anti_ad.mc.common.gui.screen.ConfigScreenBase;
import org.anti_ad.mc.ipnext.InventoryProfilesKt;
import org.anti_ad.mc.ipnext.config.ConfigScreenSettings;

public final class KotlinClientInit implements Runnable {
   @Override
   public final void run() {
      ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, KotlinClientInit::run$lambda$2);
      NeoForge.EVENT_BUS.register(new ForgeEventHandler());
      InventoryProfilesKt.init();
   }

   private static final Screen run$lambda$2$lambda$1(ModContainer var0, Screen var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      ConfigScreenBase var2;
      ConfigScreenBase var3;
      (var2 = var3 = new ConfigScreenBase(ConfigScreenSettings.INSTANCE)).setParent(var1);
      var2.dumpWidgetTree();
      return (Screen)var3;
   }

   private static final IConfigScreenFactory run$lambda$2() {
      return KotlinClientInit::run$lambda$2$lambda$1;
   }
}
