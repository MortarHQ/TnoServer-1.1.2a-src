package org.anti_ad.mc.ipnext.parser;

import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.io.path.PathsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.level.storage.WorldData;
import org.anti_ad.mc.common.Savable;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.annotation.MayThrow;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.common.extensions.StringKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.ProfileSwitchHandler;
import org.anti_ad.mc.ipnext.profiles.config.ProfileData;
import org.anti_ad.mc.ipnext.profiles.config.ProfilesConfig;
import org.anti_ad.mc.ipnext.profiles.config.ProfilesConfigParserKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nCustomDataFileLoader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/ProfilesLoader\n+ 2 SmallHelpers.kt\norg/anti_ad/mc/ipnext/specific/SmallHelpersKt\n+ 3 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,485:1\n31#2,18:486\n31#2,18:504\n42#3,9:522\n51#3,3:532\n1#4:531\n774#5:535\n865#5,2:536\n774#5:538\n865#5,2:539\n*S KotlinDebug\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/ProfilesLoader\n*L\n96#1:486,18\n103#1:504,18\n138#1:522,9\n138#1:532,3\n147#1:535\n147#1:536,2\n148#1:538\n148#1:539,2\n*E\n"
)
public final class ProfilesLoader implements Savable, Loader {
   @NotNull
   public static final ProfilesLoader INSTANCE = new ProfilesLoader();
   @NotNull
   private static final List profiles = new ArrayList();
   @NotNull
   private static final List savedProfiles = new ArrayList();

   private ProfilesLoader() {
   }

   @NotNull
   public final Path getFile() {
      String var3;
      label43: {
         if (ModSettings.INSTANCE.getPROFILES_PER_SERVER().getBooleanValue()) {
            if (Vanilla.INSTANCE.mc().isLocalServer()) {
               IntegratedServer var10000 = Vanilla.INSTANCE.mc().getSingleplayerServer();
               if (var10000 != null) {
                  WorldData var2 = var10000.getWorldData();
                  if (var2 != null) {
                     var3 = var2.getLevelName();
                     if (var3 != null) {
                        break label43;
                     }
                  }
               }
            } else {
               ServerData var4 = Vanilla.INSTANCE.mc().getCurrentServer();
               if (var4 != null ? var4.isRealm() : false) {
                  var3 = "@realms";
                  break label43;
               }

               if (Vanilla.INSTANCE.mc().getCurrentServer() != null) {
                  ServerData var5 = Vanilla.INSTANCE.mc().getCurrentServer();
                  if (var5 != null && var5.ip != null) {
                     var3 = StringsKt.replace$default(var5.ip, "/", "", false, 4, null);
                     if (var3 != null) {
                        var3 = StringsKt.replace$default(var3, ":", "&", false, 4, null);
                        if (var3 != null) {
                           break label43;
                        }
                     }
                  }
               }
            }
         }

         var3 = "";
      }

      String var1 = StringKt.sanitized(var3);
      Java_ioKt.createDirectories(Java_ioKt.div(CustomDataFileLoaderKt.access$getConfigFolder$p(), var1));
      return Java_ioKt.div(Java_ioKt.div(CustomDataFileLoaderKt.access$getConfigFolder$p(), var1), "profiles-V2.txt");
   }

   @NotNull
   public final Path getOldfile() {
      String var3;
      label43: {
         if (ModSettings.INSTANCE.getPROFILES_PER_SERVER().getBooleanValue()) {
            if (Vanilla.INSTANCE.mc().isLocalServer()) {
               IntegratedServer var10000 = Vanilla.INSTANCE.mc().getSingleplayerServer();
               if (var10000 != null) {
                  WorldData var2 = var10000.getWorldData();
                  if (var2 != null) {
                     var3 = var2.getLevelName();
                     if (var3 != null) {
                        break label43;
                     }
                  }
               }
            } else {
               ServerData var4 = Vanilla.INSTANCE.mc().getCurrentServer();
               if (var4 != null ? var4.isRealm() : false) {
                  var3 = "@realms";
                  break label43;
               }

               if (Vanilla.INSTANCE.mc().getCurrentServer() != null) {
                  ServerData var5 = Vanilla.INSTANCE.mc().getCurrentServer();
                  if (var5 != null && var5.ip != null) {
                     var3 = StringsKt.replace$default(var5.ip, "/", "", false, 4, null);
                     if (var3 != null) {
                        var3 = StringsKt.replace$default(var3, ":", "&", false, 4, null);
                        if (var3 != null) {
                           break label43;
                        }
                     }
                  }
               }
            }
         }

         var3 = "";
      }

      String var1 = StringKt.sanitized(var3);
      Java_ioKt.createDirectories(Java_ioKt.div(CustomDataFileLoaderKt.access$getConfigFolder$p(), var1));
      return Java_ioKt.div(Java_ioKt.div(CustomDataFileLoaderKt.access$getConfigFolder$p(), var1), "profiles.txt");
   }

   @NotNull
   public final List getProfiles() {
      return profiles;
   }

   @NotNull
   public final List getSavedProfiles() {
      return savedProfiles;
   }

   @MayThrow
   public final void save() {
      PathsKt.writeText$default(
         this.getFile(),
         ProfilesConfig.INSTANCE.asString(profiles) + "\n\n\n\n" + ProfilesConfig.INSTANCE.asString(savedProfiles),
         null,
         new OpenOption[0],
         2,
         null
      );
   }

   @Override
   public final void load() {
      this.reload(false);
   }

   @Override
   public final boolean doSanityCheck() {
      return false;
   }

   @Override
   public final void reload(boolean var1) {
      profiles.clear();
      savedProfiles.clear();
      List var2 = new ArrayList();
      if (var1) {
         TellPlayer.INSTANCE.chat("Loading profiles...");
      }

      if (Java_ioKt.exists(this.getFile())) {
         try {
            List var3;
            List var5 = var3 = ProfilesConfig.INSTANCE.getProfiles(PathsKt.readText$default(INSTANCE.getFile(), null, 1, null));
            List var6 = var2;
            ProfilesConfigParserKt.dump(var5);
            var6.addAll(var3);
            if (var1) {
               TellPlayer.INSTANCE.chat("Found " + var2.size() + " profiles");
            }
         } catch (Throwable var8) {
            String var4 = var8.toString();
            Log.INSTANCE.warn(var4);
            TellPlayer.INSTANCE.chat("Loading Profile settings failed: " + var4);
         }
      }

      Iterable var9 = var2;
      List var7 = savedProfiles;
      var var11 = new ArrayList();
      Iterator var13 = var9.iterator();

      while (var13.hasNext()) {
         Object var15;
         String var10000 = ((ProfileData)(var15 = var13.next())).getName().toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         if (Intrinsics.areEqual(var10000, "SAVED")) {
            var11.add(var15);
         }
      }

      var7.addAll(var11);
      var9 = var2;
      var7 = profiles;
      var11 = new ArrayList();
      var13 = var9.iterator();

      while (var13.hasNext()) {
         Object var16;
         String var18 = ((ProfileData)(var16 = var13.next())).getName().toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var18, "");
         if (!Intrinsics.areEqual(var18, "SAVED")) {
            var11.add(var16);
         }
      }

      var7.addAll(var11);
      if (var1) {
         TellPlayer.INSTANCE.chat("");
      }

      ProfileSwitchHandler.INSTANCE.reloadActiveProfile();
   }
}
