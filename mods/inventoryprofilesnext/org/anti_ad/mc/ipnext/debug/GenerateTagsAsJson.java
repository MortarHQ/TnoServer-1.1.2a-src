package org.anti_ad.mc.ipnext.debug;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.common.gui.widgets.CustomButtonWidget;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nGenerateTagsAsJson.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GenerateTagsAsJson.kt\norg/anti_ad/mc/ipnext/debug/GenerateTagsAsJson\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,89:1\n1869#2,2:90\n1869#2,2:92\n1869#2,2:94\n*S KotlinDebug\n*F\n+ 1 GenerateTagsAsJson.kt\norg/anti_ad/mc/ipnext/debug/GenerateTagsAsJson\n*L\n65#1:90,2\n72#1:92,2\n80#1:94,2\n*E\n"
)
public final class GenerateTagsAsJson extends AbstractBlockScreenScriptGenerator {
   @NotNull
   public static final GenerateTagsAsJson INSTANCE = new GenerateTagsAsJson();

   private GenerateTagsAsJson() {
   }

   public final void onClick(@NotNull CustomButtonWidget var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Java_ioKt.createDirectories(Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "auto-screens"));
      Vanilla.INSTANCE.mc().execute(GenerateTagsAsJson::onClick$lambda$6);
   }

   private static final void onClick$lambda$6() {
      TellPlayer.INSTANCE.chat("Generating...");
      Map var0 = new LinkedHashMap();
      Map var1 = new LinkedHashMap();
      Map var2 = new LinkedHashMap();
      Map var3 = new LinkedHashMap();
      List var4 = new ArrayList();
      Set var5 = new LinkedHashSet();
      Map var6 = new LinkedHashMap();
      BlocksWithScreensUtilsKt.extractBlockInfo(var5, var1, var0, var3, var2, var4);
      BlocksWithScreensUtilsKt.extractAllItems(var6);
      TellPlayer.INSTANCE.chat("Generating items-with-screens.txt");
      GenerateTagsAsJson var10000 = INSTANCE;
      Path var7 = INSTANCE.getFileItems();
      OpenOption[] var9 = new OpenOption[0];
      Charset var8 = Charsets.UTF_8;
      var10000.store(var0, new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var7, Arrays.copyOf(var9, var9.length)), var8), 8192));
      TellPlayer.INSTANCE.chat("Generating blocks-with-screens.txt");
      var10000 = INSTANCE;
      var7 = INSTANCE.getFileBlocks();
      var9 = new OpenOption[0];
      var8 = Charsets.UTF_8;
      var10000.store(var1, new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var7, Arrays.copyOf(var9, var9.length)), var8), 8192));
      TellPlayer.INSTANCE.chat("Generating blocks-entities-with-screens.txt");
      var10000 = INSTANCE;
      var7 = INSTANCE.getFileEntities();
      var9 = new OpenOption[0];
      var8 = Charsets.UTF_8;
      var10000.store(var3, new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var7, Arrays.copyOf(var9, var9.length)), var8), 8192));
      TellPlayer.INSTANCE.chat("Generating unknown-screens.txt");
      var10000 = INSTANCE;
      var7 = INSTANCE.getFileUnknown();
      var9 = new OpenOption[0];
      var8 = Charsets.UTF_8;
      var10000.store(var2, new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var7, Arrays.copyOf(var9, var9.length)), var8), 8192));
      TellPlayer.INSTANCE.chat("Generating all-items.txt");
      var10000 = INSTANCE;
      var7 = INSTANCE.getFileAllItems();
      var9 = new OpenOption[0];
      var8 = Charsets.UTF_8;
      var10000.store(var6, new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var7, Arrays.copyOf(var9, var9.length)), var8), 8192));
      var7 = INSTANCE.getFileMulti();
      var9 = new OpenOption[0];
      var8 = Charsets.UTF_8;
      BufferedWriter var31 = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var7, Arrays.copyOf(var9, var9.length)), var8), 8192);

      for (String var17 : var4) {
         var31.append(var17).append("\n");
      }

      var31.close();
      var7 = INSTANCE.getFileNamespaces();
      var9 = new OpenOption[0];
      var8 = Charsets.UTF_8;
      BufferedWriter var33 = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var7, Arrays.copyOf(var9, var9.length)), var8), 8192);

      for (String var18 : var5) {
         var33.append(var18).append("\n");
      }

      var33.close();
      List var25 = BlocksWithScreensUtilsKt.generateCommands$default(var5, var1, var3, var0, false, 16, null);
      Path var34 = INSTANCE.getFileScript();
      OpenOption[] var12 = new OpenOption[0];
      Charset var10 = Charsets.UTF_8;
      BufferedWriter var11 = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var34, Arrays.copyOf(var12, var12.length)), var10), 8192);

      for (String var13 : var25) {
         var11.append(var13).append("\n");
      }

      var11.close();
   }
}
