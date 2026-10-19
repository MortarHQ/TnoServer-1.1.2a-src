package org.anti_ad.mc.ipnext;

import com.mojang.authlib.GameProfile;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.concurrent.TimersKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.uuid.Uuid;
import net.minecraft.client.player.LocalPlayer;
import org.anti_ad.mc.common.moreinfo.InfoManagerBase;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.jetbrains.annotations.NotNull;

public final class IPNInfoManager extends InfoManagerBase {
   @NotNull
   public static final IPNInfoManager INSTANCE = new IPNInfoManager();
   @NotNull
   private static String mcVersion = "game-version-missing";
   @NotNull
   private static String modId = "inventoryprofilesnext";
   @NotNull
   private static String modName = "Inventory Profiles Next";
   @NotNull
   private static String loader = "loader-missing";
   @NotNull
   private static final Map defaultRequest;
   @NotNull
   private static final URL target;
   @NotNull
   private static Function0 isEnabled = IPNInfoManager::isEnabled$lambda$0;
   @NotNull
   private static final String session = INSTANCE.sha256(Uuid.Companion.random().toString());
   @NotNull
   private static final Function0 playerId = IPNInfoManager::playerId$lambda$1;

   private IPNInfoManager() {
   }

   @NotNull
   public final String getMcVersion() {
      return mcVersion;
   }

   public final void setMcVersion(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      mcVersion = var1;
   }

   @NotNull
   public final String getVersion() {
      return ModInfo.INSTANCE.getMOD_VERSION();
   }

   public final void setVersion(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @NotNull
   public final String getModId() {
      return modId;
   }

   public final void setModId(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      modId = var1;
   }

   @NotNull
   public final String getModName() {
      return modName;
   }

   public final void setModName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      modName = var1;
   }

   @NotNull
   public final String getLoader() {
      return loader;
   }

   public final void setLoader(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      loader = var1;
   }

   @NotNull
   protected final Map getDefaultRequest() {
      return defaultRequest;
   }

   @NotNull
   protected final URL getTarget() {
      return target;
   }

   @NotNull
   public final Function0 isEnabled() {
      return isEnabled;
   }

   public final void setEnabled(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      isEnabled = var1;
   }

   public final void doCheckVersion() {
      Timer var1 = TimersKt.timer("versionCheck", false);
      var1.schedule(new IPNInfoManager$doCheckVersion$$inlined$timer$default$1(), 5000L, 10000L);
   }

   public final void checkVersion(@NotNull URL var1, @NotNull String var2, @NotNull String var3, @NotNull Function3 var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      this.getExecutor().execute(IPNInfoManager::checkVersion$lambda$3);
   }

   public final void doSessionKeepAlive() {
      Timer var1 = TimersKt.timer("ipnPeriodicalTasks", false);
      var1.schedule(new IPNInfoManager$doSessionKeepAlive$$inlined$timer$default$1(), 300000L, 1200000L);
   }

   private static final boolean isEnabled$lambda$0() {
      return false;
   }

   private static final String playerId$lambda$1() {
      LocalPlayer var10000 = Vanilla.INSTANCE.playerNullable();
      if (var10000 != null) {
         GameProfile var0 = var10000.getGameProfile();
         if (var0 != null) {
            UUID var1 = var0.getId();
            if (var1 != null) {
               String var2 = var1.toString();
               if (var2 != null) {
                  String var3 = INSTANCE.sha256(var2);
                  if (var3 != null) {
                     return var3;
                  }
               }
            }
         }
      }

      return "InvalidPlayer";
   }

   private static final void checkVersion$lambda$3(URL var0, String var1, String var2, Function3 var3) {
      try {
         INSTANCE.doCheckVersion(var0, var1, var2, var3);
      } catch (Throwable var4) {
         Log.INSTANCE.warn("Update check failed with message - " + var4.getMessage());
      }
   }

   static {
      Pair[] var0;
      (var0 = new Pair[2])[0] = TuplesKt.to("domain", "ipn-stats.anti-ad.org");
      var0[1] = TuplesKt.to("name", "pageview");
      defaultRequest = MapsKt.mapOf(var0);
      URL var10000 = new URI("https://p.anti-ad.org/api/event").toURL();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      target = var10000;
   }
}
