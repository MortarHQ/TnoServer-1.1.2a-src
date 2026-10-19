package org.anti_ad.mc.ipnext.debug;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.IInputHandler;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.config.Modpacks;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nBlocksWithScreensUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlocksWithScreensUtils.kt\norg/anti_ad/mc/ipnext/debug/ModpackInputHandler\n+ 2 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,209:1\n130#2:210\n1#3:211\n1869#4,2:212\n*S KotlinDebug\n*F\n+ 1 BlocksWithScreensUtils.kt\norg/anti_ad/mc/ipnext/debug/ModpackInputHandler\n*L\n181#1:210\n181#1:211\n195#1:212,2\n*E\n"
)
public final class ModpackInputHandler implements IInputHandler {
   @NotNull
   public static final ModpackInputHandler INSTANCE = new ModpackInputHandler();

   private ModpackInputHandler() {
   }

   public final boolean onInput(int var1, int var2) {
      if (ModSettings.INSTANCE.getFOR_MODPACK_DEVS().getValue()) {
         if (Modpacks.INSTANCE.getGEN_TEST_ARENA().isActivated()) {
            Vanilla.INSTANCE.mc().execute(ModpackInputHandler::onInput$lambda$2$lambda$1);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static final void onInput$lambda$2$lambda$1() {
      TellPlayer.INSTANCE.chat("Generating...");
      Map var0 = new LinkedHashMap();
      Map var1 = new LinkedHashMap();
      Map var2 = new LinkedHashMap();
      Map var3 = new LinkedHashMap();
      List var4 = new ArrayList();
      Set var5;
      BlocksWithScreensUtilsKt.extractBlockInfo(var5 = new LinkedHashSet(), var1, var0, var3, var2, var4);

      for (String var7 : BlocksWithScreensUtilsKt.generateCommands(var5, var1, var3, var0, true)) {
         Vanilla.INSTANCE._sendCommandMessage_/* $VF was: (sendCommandMessage) */(Vanilla.INSTANCE.player(), var7);
      }
   }
}
