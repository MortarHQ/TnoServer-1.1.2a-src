package org.anti_ad.mc.ipnext.parser;

import java.nio.file.Path;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.io.path.PathsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.item.rule.file.RuleFile;
import org.anti_ad.mc.ipnext.item.rule.file.RuleFileRegister;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nCustomDataFileLoader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/RuleLoader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,485:1\n1#2:486\n*E\n"
)
public final class RuleLoader implements Loader {
   @NotNull
   public static final RuleLoader INSTANCE = new RuleLoader();
   @NotNull
   public static final String internalFileDisplayName = "<internal rules.txt>";
   @NotNull
   private static final String regex = "^rules-v2\\.(?:.*\\.)?txt$";
   @NotNull
   private static final String regexOld = "^rules\\.(?:.*\\.)?txt$";

   private RuleLoader() {
   }

   private final String getInternalRulesTxtContent() {
      String var10000 = VanillaUtil.INSTANCE.getResourceAsString("inventoryprofilesnext:config/rules.txt");
      if (var10000 == null) {
         String var1 = "";
         Log.INSTANCE.error("Failed to load in-jar file inventoryprofilesnext:config/rules.txt");
         var10000 = var1;
      }

      return var10000;
   }

   @Override
   public final boolean doSanityCheck() {
      return false;
   }

   @Override
   public final void load() {
      this.reload(false);
   }

   @Override
   public final void reload(boolean var1) {
      Log.INSTANCE.clearIndent();
      Log.INSTANCE.trace("[-] Rule reloading...");
      if (var1) {
         TellPlayer.INSTANCE.chat("Loading custom sort rules...");
      }

      List var2 = CustomDataFileLoaderKt.access$getFiles("^rules-v2\\.(?:.*\\.)?txt$");
      RuleFile[] var4;
      (var4 = new RuleFile[1])[0] = new RuleFile("<internal rules.txt>", this.getInternalRulesTxtContent(), var1);
      List var3 = CollectionsKt.mutableListOf(var4);

      for (Path var7 : var2) {
         try {
            Log.INSTANCE.trace("    Trying to read file " + Java_ioKt.getName(var7));
            String var5 = PathsKt.readText$default(var7, null, 1, null);
            var3.add(new RuleFile(Java_ioKt.getName(var7), var5, var1));
         } catch (Exception var6) {
            Log.INSTANCE.error("Failed to read file " + Java_ioKt.getLoggingPath(var7));
         }
      }

      Log.INSTANCE.trace("[-] Total " + var3.size() + " rule files (including <internal>)");
      RuleFileRegister.INSTANCE.reloadRuleFiles(var3, var1);
      Log.INSTANCE.trace("Rule reload end");
      TemporaryRuleParser.INSTANCE.onReload();
      if (var1) {
         TellPlayer.INSTANCE.chat("");
      }
   }
}
