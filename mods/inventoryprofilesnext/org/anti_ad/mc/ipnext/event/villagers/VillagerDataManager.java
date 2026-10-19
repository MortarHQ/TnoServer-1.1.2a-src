package org.anti_ad.mc.ipnext.event.villagers;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.TimersKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonBuilder;
import kotlinx.serialization.json.JsonKt;
import kotlinx.serialization.json.JvmStreamsKt;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.NotificationManager;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nVillagerDataManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VillagerDataManager.kt\norg/anti_ad/mc/ipnext/event/villagers/VillagerDataManager\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 VillageEx.kt\norg/anti_ad/mc/alias/village/VillageExKt\n*L\n1#1,258:1\n384#2,7:259\n384#2,7:266\n25#3:273\n25#3:274\n25#3:275\n25#3:276\n25#3:277\n25#3:278\n25#3:279\n25#3:280\n25#3:281\n25#3:282\n25#3:283\n25#3:284\n25#3:285\n25#3:286\n25#3:287\n*S KotlinDebug\n*F\n+ 1 VillagerDataManager.kt\norg/anti_ad/mc/ipnext/event/villagers/VillagerDataManager\n*L\n64#1:259,7\n71#1:266,7\n172#1:273\n173#1:274\n174#1:275\n175#1:276\n176#1:277\n177#1:278\n178#1:279\n179#1:280\n180#1:281\n181#1:282\n182#1:283\n183#1:284\n184#1:285\n185#1:286\n186#1:287\n*E\n"
)
public final class VillagerDataManager {
   @NotNull
   public static final VillagerDataManager INSTANCE = new VillagerDataManager();
   @NotNull
   private static final Json json = JsonKt.Json$default(null, VillagerDataManager::json$lambda$0, 1, null);
   @NotNull
   private static final Config config = new Config(null, null, null, null, null, null, 63, null);
   private static Path path;
   private static Path configFile;
   @Nullable
   private static Timer saveTimer;
   @NotNull
   private static final Object timerSync = new Object();

   private VillagerDataManager() {
   }

   private final void add(Map var1, String var2, VillagerTradeData var3) {
      Object var4;
      if ((var4 = var1.get(var2)) == null) {
         var4 = new ArrayList();
         var1.put(var2, var4);
      }

      ((List)var4).add(var3);
      config.markDirty();
   }

   private final List get(Map var1, String var2) {
      Object var3;
      if ((var3 = var1.get(var2)) == null) {
         config.markDirty();
         var3 = new ArrayList();
         var1.put(var2, var3);
      }

      return CollectionsKt.toList((Iterable)var3);
   }

   private final void remove(Map var1, String var2, VillagerTradeData var3) {
      if (var2 != null) {
         var2 = var2;
         List var10000 = (List)var1.get(var2);
         if (var10000 != null) {
            var10000.remove(var3);
         }

         config.markDirty();
      }
   }

   public final void removeGlobal(@Nullable String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.remove(config.getGlobalBookmarks(), var1, var2);
   }

   public final void removeLocal(@Nullable String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.remove(config.getLocalBookmarks(), var1, var2);
   }

   public final void removeGlobal1(@Nullable String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.remove(config.getGlobalBookmarks1(), var1, var2);
   }

   public final void removeLocal1(@Nullable String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.remove(config.getLocalBookmarks1(), var1, var2);
   }

   public final void removeGlobal2(@Nullable String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.remove(config.getGlobalBookmarks2(), var1, var2);
   }

   public final void removeLocal2(@Nullable String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.remove(config.getLocalBookmarks2(), var1, var2);
   }

   public final void addGlobal(@NotNull String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.add(config.getGlobalBookmarks(), var1, var2);
   }

   @NotNull
   public final List getGlobal(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.get(config.getGlobalBookmarks(), var1);
   }

   public final void addGlobal1(@NotNull String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.add(config.getGlobalBookmarks1(), var1, var2);
   }

   @NotNull
   public final List getGlobal1(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.get(config.getGlobalBookmarks1(), var1);
   }

   public final void addGlobal2(@NotNull String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.add(config.getGlobalBookmarks2(), var1, var2);
   }

   @NotNull
   public final List getGlobal2(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.get(config.getGlobalBookmarks2(), var1);
   }

   public final void addLocal(@NotNull String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.add(config.getLocalBookmarks(), var1, var2);
   }

   @NotNull
   public final List getLocal(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.get(config.getLocalBookmarks(), var1);
   }

   public final void addLocal1(@NotNull String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.add(config.getLocalBookmarks1(), var1, var2);
   }

   @NotNull
   public final List getLocal1(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.get(config.getLocalBookmarks1(), var1);
   }

   public final void addLocal2(@NotNull String var1, @NotNull VillagerTradeData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.add(config.getLocalBookmarks2(), var1, var2);
   }

   @NotNull
   public final List getLocal2(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.get(config.getLocalBookmarks2(), var1);
   }

   public final void init(@NotNull Path var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      config.clear();
      Path var10000 = path = var1;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("");
         var10000 = null;
      }

      var10000 = var10000.resolve("villager-trading-config-v2.json");
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      configFile = var10000;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("");
         var10000 = null;
      }

      if (Java_ioKt.exists(var10000)) {
         try {
            var10000 = configFile;
            if (configFile == null) {
               Intrinsics.throwUninitializedPropertyAccessException("");
               var10000 = null;
            }

            var1 = var10000;
            OpenOption[] var9 = new OpenOption[0];
            InputStream var16 = Files.newInputStream(var1, Arrays.copyOf(var9, var9.length));
            Intrinsics.checkNotNullExpressionValue(var16, "");
            InputStream var10 = var16;
            config.copyFrom((Config)JvmStreamsKt.decodeFromStream(json, (DeserializationStrategy)Config.Companion.serializer(), var10));
            return;
         } catch (Throwable var6) {
            String var3 = System.currentTimeMillis() + "-BAD-villager-trading-config-v2.json";
            var10000 = configFile;
            if (configFile == null) {
               Intrinsics.throwUninitializedPropertyAccessException("");
               var10000 = null;
            }

            var1 = var10000;
            var10000 = path;
            if (path == null) {
               Intrinsics.throwUninitializedPropertyAccessException("");
               var10000 = null;
            }

            var10000 = var10000.resolve(var3);
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            Path var4 = var10000;
            CopyOption[] var5;
            (var5 = new CopyOption[1])[0] = StandardCopyOption.ATOMIC_MOVE;
            Intrinsics.checkNotNullExpressionValue(Files.move(var1, var4, Arrays.copyOf(var5, var5.length)), "");
            config.clear();
            this.createInitialFile();
            if (var2) {
               TellPlayer.INSTANCE.chat("Error while loading villager trading settings.");
               TellPlayer.INSTANCE.chat("Moving offending file to " + var3 + " and creating clean one.");
               return;
            }
         }
      } else {
         this.createInitialFile();
      }
   }

   public final boolean checkOldConfig() {
      Path var10000 = path;
      if (path == null) {
         Intrinsics.throwUninitializedPropertyAccessException("");
         var10000 = null;
      }

      var10000 = var10000.resolve("villager-trading-config.json");
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Path var1 = var10000;
      boolean var2;
      if (var2 = Java_ioKt.exists(var10000)) {
         NotificationManager.INSTANCE.addNotification("[IPN] - Found incompatible villager trading config");
         Log.INSTANCE.error("Found incompatible villager trading config at:");
         Log.INSTANCE.error("\t\t" + var1.toAbsolutePath());
      }

      return var2;
   }

   private final void addProfessionToAllGroups(String var1) {
      config.getGlobalBookmarks().put(var1, new ArrayList());
      config.getGlobalBookmarks1().put(var1, new ArrayList());
      config.getGlobalBookmarks2().put(var1, new ArrayList());
   }

   private final void createInitialFile() {
      VillagerProfession var10001 = VillagerProfession.ARMORER;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.ARMORER, "");
      String var1 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var1, "");
      this.addProfessionToAllGroups(var1);
      var10001 = VillagerProfession.BUTCHER;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.BUTCHER, "");
      String var3 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var3, "");
      this.addProfessionToAllGroups(var3);
      var10001 = VillagerProfession.CARTOGRAPHER;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.CARTOGRAPHER, "");
      String var5 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var5, "");
      this.addProfessionToAllGroups(var5);
      var10001 = VillagerProfession.CLERIC;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.CLERIC, "");
      String var7 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var7, "");
      this.addProfessionToAllGroups(var7);
      var10001 = VillagerProfession.FARMER;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.FARMER, "");
      String var9 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var9, "");
      this.addProfessionToAllGroups(var9);
      var10001 = VillagerProfession.FISHERMAN;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.FISHERMAN, "");
      String var11 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var11, "");
      this.addProfessionToAllGroups(var11);
      var10001 = VillagerProfession.FLETCHER;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.FLETCHER, "");
      String var13 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var13, "");
      this.addProfessionToAllGroups(var13);
      var10001 = VillagerProfession.LEATHERWORKER;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.LEATHERWORKER, "");
      String var15 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var15, "");
      this.addProfessionToAllGroups(var15);
      var10001 = VillagerProfession.LIBRARIAN;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.LIBRARIAN, "");
      String var17 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var17, "");
      this.addProfessionToAllGroups(var17);
      var10001 = VillagerProfession.MASON;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.MASON, "");
      String var19 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var19, "");
      this.addProfessionToAllGroups(var19);
      var10001 = VillagerProfession.SHEPHERD;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.SHEPHERD, "");
      String var21 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var21, "");
      this.addProfessionToAllGroups(var21);
      var10001 = VillagerProfession.TOOLSMITH;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.TOOLSMITH, "");
      String var23 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var23, "");
      this.addProfessionToAllGroups(var23);
      var10001 = VillagerProfession.WEAPONSMITH;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.WEAPONSMITH, "");
      String var25 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var25, "");
      this.addProfessionToAllGroups(var25);
      var10001 = VillagerProfession.NITWIT;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.NITWIT, "");
      String var27 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var27, "");
      this.addProfessionToAllGroups(var27);
      var10001 = VillagerProfession.NONE;
      Intrinsics.checkNotNullExpressionValue(VillagerProfession.NONE, "");
      String var29 = var10001.name();
      Intrinsics.checkNotNullExpressionValue(var29, "");
      this.addProfessionToAllGroups(var29);
      config.markDirty();
      this.saveIfDirty();
   }

   @Nullable
   public final Timer getSaveTimer() {
      return saveTimer;
   }

   public final void setSaveTimer(@Nullable Timer var1) {
      saveTimer = var1;
   }

   public final void saveIfDirty() {
      if (ModSettings.INSTANCE.getVILLAGER_TRADING_ENABLE().getBooleanValue()) {
         VillagerDataManager$saveIfDirty$task$1 var1 = new VillagerDataManager$saveIfDirty$task$1();
         synchronized (timerSync) {
            if (config.isDirty() && saveTimer == null) {
               Timer var3;
               Timer var4 = var3 = TimersKt.timer("IPN VillagerDataManager", false);
               var4.schedule(new VillagerDataManager$saveIfDirty$lambda$5$$inlined$timer$1(var1), 100L, 250L);
               saveTimer = var3;
            }
         }
      }
   }

   private final void save(Config var1) {
      Path var10000 = configFile;
      if (configFile == null) {
         Intrinsics.throwUninitializedPropertyAccessException("");
         var10000 = null;
      }

      if (Java_ioKt.exists(var10000)) {
         try {
            var10000 = configFile;
            if (configFile == null) {
               Intrinsics.throwUninitializedPropertyAccessException("");
               var10000 = null;
            }

            Path var2 = var10000;
            var10000 = path;
            if (path == null) {
               Intrinsics.throwUninitializedPropertyAccessException("");
               var10000 = null;
            }

            var10000 = var10000.resolve("prev-villager-trading-config-v2.json");
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            Path var3 = var10000;
            CopyOption[] var4;
            (var4 = new CopyOption[2])[0] = StandardCopyOption.ATOMIC_MOVE;
            var4[1] = StandardCopyOption.REPLACE_EXISTING;
            Intrinsics.checkNotNullExpressionValue(Files.move(var2, var3, Arrays.copyOf(var4, var4.length)), "");
         } catch (Throwable var5) {
            Log.INSTANCE.error("Backup of villagers data failed! This not a real problem but it will be very helpful if you can report it.", var5);
         }
      }

      var10000 = configFile;
      if (configFile == null) {
         Intrinsics.throwUninitializedPropertyAccessException("");
         var10000 = null;
      }

      Path var6 = var10000;
      OpenOption[] var7 = new OpenOption[0];
      OutputStream var13 = Files.newOutputStream(var6, Arrays.copyOf(var7, var7.length));
      Intrinsics.checkNotNullExpressionValue(var13, "");
      OutputStream var8 = var13;
      JvmStreamsKt.encodeToStream(json, (SerializationStrategy)Config.Companion.serializer(), var1, var8);
      var8.flush();
      var8.close();
   }

   private static final Unit json$lambda$0(JsonBuilder var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      var0.setIgnoreUnknownKeys(true);
      var0.setPrettyPrint(true);
      return Unit.INSTANCE;
   }
}
