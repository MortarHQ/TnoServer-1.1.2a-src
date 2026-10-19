package org.anti_ad.mc.ipnext.parser;

import java.nio.file.Path;
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
import org.anti_ad.mc.ipnext.event.villagers.VillagerDataManager;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nCustomDataFileLoader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/VillagerBookmarksLoader\n+ 2 SmallHelpers.kt\norg/anti_ad/mc/ipnext/specific/SmallHelpersKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,485:1\n31#2,18:486\n1#3:504\n*S KotlinDebug\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/VillagerBookmarksLoader\n*L\n394#1:486,18\n*E\n"
)
public final class VillagerBookmarksLoader implements Savable, Loader {
   @NotNull
   public static final VillagerBookmarksLoader INSTANCE = new VillagerBookmarksLoader();

   private VillagerBookmarksLoader() {
   }

   @NotNull
   public final Path getPath() {
      String var4;
      label41: {
         if (Vanilla.INSTANCE.mc().isLocalServer()) {
            IntegratedServer var10000 = Vanilla.INSTANCE.mc().getSingleplayerServer();
            if (var10000 != null) {
               WorldData var3 = var10000.getWorldData();
               if (var3 != null) {
                  var4 = var3.getLevelName();
                  if (var4 != null) {
                     break label41;
                  }
               }
            }
         } else {
            ServerData var5 = Vanilla.INSTANCE.mc().getCurrentServer();
            if (var5 != null ? var5.isRealm() : false) {
               var4 = "@realms";
               break label41;
            }

            if (Vanilla.INSTANCE.mc().getCurrentServer() != null) {
               ServerData var6 = Vanilla.INSTANCE.mc().getCurrentServer();
               if (var6 != null && var6.ip != null) {
                  var4 = StringsKt.replace$default(var6.ip, "/", "", false, 4, null);
                  if (var4 != null) {
                     var4 = StringsKt.replace$default(var4, ":", "&", false, 4, null);
                     if (var4 != null) {
                        break label41;
                     }
                  }
               }
            }
         }

         var4 = "";
      }

      String var1 = StringKt.sanitized(var4);
      Path var2;
      Java_ioKt.createDirectories(var2 = Java_ioKt.div(CustomDataFileLoaderKt.access$getConfigFolder$p(), var1));
      return var2;
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
      if (var1) {
         TellPlayer.INSTANCE.chat("Loading Villager trading config...");
      }

      VillagerDataManager.INSTANCE.init(this.getPath(), var1);
      if (var1) {
         TellPlayer.INSTANCE.chat("");
      }
   }

   public final void save() {
      VillagerDataManager.INSTANCE.saveIfDirty();
   }
}
