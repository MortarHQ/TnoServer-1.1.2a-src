package org.anti_ad.mc.ipnext.debug;

import java.io.BufferedWriter;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonInfo;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nAbstractBlockScreenScriptGenerator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbstractBlockScreenScriptGenerator.kt\norg/anti_ad/mc/ipnext/debug/AbstractBlockScreenScriptGenerator\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,54:1\n216#2:55\n217#2:58\n1869#3,2:56\n*S KotlinDebug\n*F\n+ 1 AbstractBlockScreenScriptGenerator.kt\norg/anti_ad/mc/ipnext/debug/AbstractBlockScreenScriptGenerator\n*L\n45#1:55\n45#1:58\n47#1:56,2\n*E\n"
)
public abstract class AbstractBlockScreenScriptGenerator extends ConfigButtonInfo {
   @NotNull
   private final Path fileEntities = Java_ioKt.div(
      Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "auto-screens"), "blocks-entities-with-screens.txt"
   );
   @NotNull
   private final Path fileBlocks = Java_ioKt.div(
      Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "auto-screens"), "blocks-with-screens.txt"
   );
   @NotNull
   private final Path fileItems = Java_ioKt.div(
      Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "auto-screens"), "item-with-screens.txt"
   );
   @NotNull
   private final Path fileAllItems = Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "all-items.txt");
   @NotNull
   private final Path fileMulti = Java_ioKt.div(
      Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "auto-screens"), "multi-screens.txt"
   );
   @NotNull
   private final Path fileUnknown = Java_ioKt.div(
      Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "auto-screens"), "unknown-screens.txt"
   );
   @NotNull
   private final Path fileNamespaces = Java_ioKt.div(
      Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "auto-screens"), "namespaces.txt"
   );
   @NotNull
   private final Path fileScript = Java_ioKt.div(Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "auto-screens"), "script.txt");

   @NotNull
   public final Path getFileEntities() {
      return this.fileEntities;
   }

   @NotNull
   public final Path getFileBlocks() {
      return this.fileBlocks;
   }

   @NotNull
   public final Path getFileItems() {
      return this.fileItems;
   }

   @NotNull
   public final Path getFileAllItems() {
      return this.fileAllItems;
   }

   @NotNull
   public final Path getFileMulti() {
      return this.fileMulti;
   }

   @NotNull
   public final Path getFileUnknown() {
      return this.fileUnknown;
   }

   @NotNull
   public final Path getFileNamespaces() {
      return this.fileNamespaces;
   }

   @NotNull
   public final Path getFileScript() {
      return this.fileScript;
   }

   @NotNull
   public String getButtonText() {
      return "Make Generator Script";
   }

   protected final void store(@NotNull Map var1, @NotNull BufferedWriter var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Iterator var6 = var1.entrySet().iterator();

      while (var6.hasNext()) {
         Entry var3;
         String var4 = (String)(var3 = (Entry)var6.next()).getKey();
         List var7 = (List)var3.getValue();
         var2.append("\n# " + var4 + "\n");

         for (String var5 : var7) {
            var2.append("\t").append(var4).append(":").append(var5).append("\n");
         }
      }

      var2.close();
   }
}
