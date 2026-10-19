package org.anti_ad.mc.ipnext.specific;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.SharedConstants;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.level.storage.WorldData;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.IPNInfoManager;
import org.jetbrains.annotations.NotNull;

public final class SmallHelpersKt {
   @NotNull
   public static final String serverIdentifier(boolean var0) {
      if (var0) {
         if (Vanilla.INSTANCE.mc().isLocalServer()) {
            IntegratedServer var10000 = Vanilla.INSTANCE.mc().getSingleplayerServer();
            if (var10000 != null) {
               WorldData var1 = var10000.getWorldData();
               if (var1 != null) {
                  String var2 = var1.getLevelName();
                  if (var2 != null) {
                     return var2;
                  }
               }
            }
         } else {
            ServerData var3 = Vanilla.INSTANCE.mc().getCurrentServer();
            if (var3 != null ? var3.isRealm() : false) {
               return "@realms";
            }

            if (Vanilla.INSTANCE.mc().getCurrentServer() != null) {
               var3 = Vanilla.INSTANCE.mc().getCurrentServer();
               if (var3 != null && var3.ip != null) {
                  String var6 = StringsKt.replace$default(var3.ip, "/", "", false, 4, null);
                  if (var6 != null) {
                     String var7 = StringsKt.replace$default(var6, ":", "&", false, 4, null);
                     if (var7 != null) {
                        return var7;
                     }
                  }
               }
            }
         }
      }

      return "";
   }

   public static final void initInfoManager() {
      IPNInfoManager.INSTANCE.setLoader("neoforge");
      IPNInfoManager var10000 = IPNInfoManager.INSTANCE;
      String var10001 = SharedConstants.getCurrentVersion().getId();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      var10000.setMcVersion(var10001);
   }

   @NotNull
   public static final NativeContext getAsOverlayContext(@NotNull NativeContext var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      NativeContext var1;
      (var1 = new NativeContext(var0.getNative(), null)).setOverlay(true);
      return var1;
   }
}
