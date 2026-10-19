package org.anti_ad.mc.ipnext.parser;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.io.path.PathsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.level.storage.WorldData;
import org.anti_ad.mc.common.Savable;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.common.extensions.StringKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nCustomDataFileLoader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/LockSlotsLoader\n+ 2 SmallHelpers.kt\norg/anti_ad/mc/ipnext/specific/SmallHelpersKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,485:1\n31#2,18:486\n31#2,18:504\n1617#3,9:522\n1869#3:531\n1870#3:533\n1626#3:534\n1#4:532\n*S KotlinDebug\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/LockSlotsLoader\n*L\n208#1:486,18\n215#1:504,18\n251#1:522,9\n251#1:531\n251#1:533\n251#1:534\n251#1:532\n*E\n"
)
public final class LockSlotsLoader implements Savable, Loader {
   @NotNull
   public static final LockSlotsLoader INSTANCE = new LockSlotsLoader();
   @NotNull
   private static List cachedValue = CollectionsKt.emptyList();

   private LockSlotsLoader() {
   }

   @NotNull
   public final Path getFile() {
      String var3;
      label43: {
         if (ModSettings.INSTANCE.getENABLE_LOCK_SLOTS_PER_SERVER().getBooleanValue()) {
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
      return Java_ioKt.div(Java_ioKt.div(CustomDataFileLoaderKt.access$getConfigFolder$p(), var1), "lockSlots.txt");
   }

   @NotNull
   public final Path getFileOld() {
      Path var10000 = CustomDataFileLoaderKt.access$getConfigFolder$p();
      if (ModSettings.INSTANCE.getENABLE_LOCK_SLOTS_PER_SERVER().getBooleanValue()) {
         if (Vanilla.INSTANCE.mc().isLocalServer()) {
            IntegratedServer var10001 = Vanilla.INSTANCE.mc().getSingleplayerServer();
            if (var10001 != null) {
               WorldData var1 = var10001.getWorldData();
               if (var1 != null) {
                  String var2 = var1.getLevelName();
                  if (var2 != null) {
                     return Java_ioKt.div(var10000, "lockSlots" + StringKt.dashedSanitized(var2) + ".txt");
                  }
               }
            }
         } else {
            ServerData var3 = Vanilla.INSTANCE.mc().getCurrentServer();
            if (var3 != null ? var3.isRealm() : false) {
               return Java_ioKt.div(var10000, "lockSlots" + StringKt.dashedSanitized("@realms") + ".txt");
            }

            if (Vanilla.INSTANCE.mc().getCurrentServer() != null) {
               var3 = Vanilla.INSTANCE.mc().getCurrentServer();
               if (var3 != null && var3.ip != null) {
                  String var6 = StringsKt.replace$default(var3.ip, "/", "", false, 4, null);
                  if (var6 != null) {
                     String var7 = StringsKt.replace$default(var6, ":", "&", false, 4, null);
                     if (var7 != null) {
                        return Java_ioKt.div(var10000, "lockSlots" + StringKt.dashedSanitized(var7) + ".txt");
                     }
                  }
               }
            }
         }
      }

      return Java_ioKt.div(var10000, "lockSlots" + StringKt.dashedSanitized("") + ".txt");
   }

   public final void save() {
      try {
         List var1;
         if (!Intrinsics.areEqual(var1 = CollectionsKt.sorted(LockSlotsHandler.INSTANCE.getLockedInvSlotsStoredValue()), cachedValue)) {
            cachedValue = var1;
            Java_ioKt.writeToFile(CollectionsKt.joinToString$default(var1, "\n", null, null, 0, null, null, 62, null), this.getFile());
         }
      } catch (Exception var2) {
         Log.INSTANCE.error("Failed to write file " + Java_ioKt.getLoggingPath(this.getFile()));
      }
   }

   @Override
   public final void load() {
      this.reload(false);
   }

   @Override
   public final boolean doSanityCheck() {
      return false;
   }

   private final void internalLoad(boolean var1) {
      cachedValue = CollectionsKt.emptyList();

      try {
         if (Java_ioKt.exists(this.getFileOld())) {
            Path var10000 = this.getFile();
            LinkOption[] var10001 = new LinkOption[0];
            if (Files.notExists(var10000, Arrays.copyOf(var10001, var10001.length))) {
               Java_ioKt.writeToFile(PathsKt.readText$default(this.getFileOld(), null, 1, null), this.getFile());
               Files.delete(this.getFileOld());
            }
         }

         Path var9 = this.getFile();
         LinkOption[] var11 = new LinkOption[0];
         if (Files.notExists(var9, Arrays.copyOf(var11, var11.length))) {
            LockSlotsHandler.INSTANCE.getLockedInvSlotsStoredValue().clear();
         } else {
            Collection var2 = StringsKt.lines(PathsKt.readText$default(this.getFile(), null, 1, null));
            var var3 = new ArrayList();
            Iterator var6 = var2.iterator();

            while (var6.hasNext()) {
               Integer var10 = StringsKt.toIntOrNull(StringsKt.trim((String & CharSequence)var6.next()).toString());
               if (var10 != null) {
                  Integer var4 = var10;
                  var3.add(var4);
               }
            }

            var2 = var3;
            Set var8;
            (var8 = LockSlotsHandler.INSTANCE.getLockedInvSlotsStoredValue()).clear();
            var8.addAll(var2);
            cachedValue = var2;
         }
      } catch (Exception var5) {
         if (var1) {
            TellPlayer.INSTANCE.chat("Loading stored 'Locked Slots' failed: " + var5);
         }

         Log.INSTANCE.error("Failed to read file " + Java_ioKt.getLoggingPath(this.getFile()));
      }
   }

   @Override
   public final void reload(boolean var1) {
      this.internalLoad(var1);
   }
}
