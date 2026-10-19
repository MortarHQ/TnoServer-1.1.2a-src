package org.anti_ad.mc.ipnext.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import org.anti_ad.mc.alias.text.TextExKt;
import org.anti_ad.mc.common.Savable;
import org.anti_ad.mc.common.config.IConfigElement;
import org.anti_ad.mc.common.config.builder.ConfigDeclaration;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilderKt;
import org.anti_ad.mc.common.config.builder.ConfigSaveLoadManager;
import org.anti_ad.mc.common.config.options.ConfigHotkey;
import org.anti_ad.mc.common.gui.screen.BaseConfigScreenSettings;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.ModInfo;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nConfigScreenSettings.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigScreenSettings.kt\norg/anti_ad/mc/ipnext/config/ConfigScreenSettings\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,88:1\n774#2:89\n865#2,2:90\n*S KotlinDebug\n*F\n+ 1 ConfigScreenSettings.kt\norg/anti_ad/mc/ipnext/config/ConfigScreenSettings\n*L\n76#1:89\n76#1:90,2\n*E\n"
)
public final class ConfigScreenSettings extends BaseConfigScreenSettings {
   @NotNull
   public static final ConfigScreenSettings INSTANCE = new ConfigScreenSettings();
   @NotNull
   private static final String FILE_NAME = "inventoryprofiles.json";
   @NotNull
   private static final List configs;
   @NotNull
   private static final ConfigSaveLoadManager saveLoadManager;
   @NotNull
   private static final Savable saveManager = (Savable)(
      saveLoadManager = new ConfigSaveLoadManager("inventoryprofilesnext", "inventoryprofiles.json", ConfigScreenSettings::saveLoadManager$lambda$0)
   );
   @NotNull
   private static final String configLabelsPrefix = "inventoryprofiles.gui.config.";
   @NotNull
   private static final String configOptionsPrefix = "inventoryprofiles.config.";
   @NotNull
   private static final ConfigHotkey openConfigHotkey = Hotkeys.INSTANCE.getOPEN_CONFIG_MENU();
   @NotNull
   private static final Function0 onClosed = ConfigScreenSettings::onClosed$lambda$2;

   private ConfigScreenSettings() {
   }

   @NotNull
   public final List getConfigs() {
      return configs;
   }

   @NotNull
   public final ConfigSaveLoadManager getSaveLoadManager() {
      return saveLoadManager;
   }

   @NotNull
   public final Component getConfigScreenTitle() {
      Object[] var1;
      (var1 = new Object[1])[0] = ModInfo.INSTANCE.getMOD_VERSION();
      return TextExKt.getTranslatable("inventoryprofiles.gui.config.title", var1);
   }

   @NotNull
   public final Savable getSaveManager() {
      return saveManager;
   }

   @NotNull
   public final String getConfigLabelsPrefix() {
      return configLabelsPrefix;
   }

   @NotNull
   public final String getConfigOptionsPrefix() {
      return configOptionsPrefix;
   }

   @NotNull
   public final ConfigHotkey getOpenConfigHotkey() {
      return openConfigHotkey;
   }

   @NotNull
   public final List getConfigDeclarations() {
      boolean var1 = ModSettings.INSTANCE.getDEBUG().getValue();
      boolean var2 = ModSettings.INSTANCE.getFOR_MODPACK_DEVS().getValue();
      if (var1 && var2) {
         return configs;
      }

      Iterable var3 = configs;
      var var4 = new ArrayList();
      Iterator var7 = var3.iterator();

      while (var7.hasNext()) {
         Object var5;
         ConfigDeclaration var6;
         if (((var6 = (ConfigDeclaration)(var5 = var7.next())) != Debugs.INSTANCE || var1) && (var6 != Modpacks.INSTANCE || var2)) {
            var4.add(var5);
         }
      }

      return var4;
   }

   @NotNull
   public final Function0 getOnClosed() {
      return onClosed;
   }

   private static final IConfigElement saveLoadManager$lambda$0() {
      return (IConfigElement)ConfigDeclarationBuilderKt.toMultiConfig(configs);
   }

   private static final Unit onClosed$lambda$2() {
      if (VanillaUtil.INSTANCE.inGame()) {
         AutoRefillHandler.INSTANCE.init();
      }

      return Unit.INSTANCE;
   }

   static {
      ConfigDeclaration[] var0;
      (var0 = new ConfigDeclaration[10])[0] = ModSettings.INSTANCE;
      var0[1] = GuiSettings.INSTANCE;
      var0[2] = LockedSlotsSettings.INSTANCE;
      var0[3] = AutoRefillSettings.INSTANCE;
      var0[4] = EditProfiles.INSTANCE;
      var0[5] = ScrollSettings.INSTANCE;
      var0[6] = Hotkeys.INSTANCE;
      var0[7] = Tweaks.INSTANCE;
      var0[8] = Debugs.INSTANCE;
      var0[9] = Modpacks.INSTANCE;
      configs = CollectionsKt.listOf(var0);
   }
}
