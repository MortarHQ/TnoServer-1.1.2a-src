package org.anti_ad.mc.ipnext.item.rule.file;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.util.IndentedData;
import org.anti_ad.mc.common.util.IndentedDataFileParser;
import org.anti_ad.mc.common.util.IndentedDataFileParser.Companion;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.parser.RuleParser;
import org.anti_ad.mc.ipnext.parser.SyntaxErrorException;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nRuleFile.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RuleFile.kt\norg/anti_ad/mc/ipnext/item/rule/file/RuleFile\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,72:1\n384#2,7:73\n126#3:80\n153#3,3:81\n*S KotlinDebug\n*F\n+ 1 RuleFile.kt\norg/anti_ad/mc/ipnext/item/rule/file/RuleFile\n*L\n52#1:73,7\n67#1:80\n67#1:81,3\n*E\n"
)
public final class RuleFile {
   @NotNull
   private final String fileName;
   @NotNull
   private final String content;
   private final boolean fromUserInput;
   @NotNull
   private final Map rulesMap;

   public RuleFile(@NotNull String var1, @NotNull String var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.fileName = var1;
      this.content = var2;
      this.fromUserInput = var3;
      this.rulesMap = new LinkedHashMap();
   }

   @NotNull
   public final String getFileName() {
      return this.fileName;
   }

   @NotNull
   public final Map getRulesMap() {
      return this.rulesMap;
   }

   public final void parseContent() {
      Log.INSTANCE.trace("[-] Parsing file " + this.fileName);

      for (IndentedData var2 : Companion.parse$default(
            IndentedDataFileParser.Companion, RuleFilePreprocessManagerKt.preprocessRules(StringsKt.lines(this.content)), this.fileName, 0, 4, null
         )
         .getSubData()) {
         Log.INSTANCE.trace("    - parsing rule: " + var2.getText());

         try {
            RuleDefinition var3 = RuleParser.INSTANCE.parseRuleDefinition(var2);
            Map var4 = this.rulesMap;
            String var5 = var3.getRuleName();
            Object var6;
            if ((var6 = var4.get(var5)) == null) {
               var6 = new ArrayList();
               var4.put(var5, var6);
            }

            ((List)var6).add(var3);
         } catch (SyntaxErrorException var7) {
            TellPlayer.INSTANCE.chat("Loading sort rules failed!");
            TellPlayer.INSTANCE.chat("Syntax error in '" + this.fileName + "' (" + var2.getText() + ")");
            TellPlayer.INSTANCE.chat("  > at: " + var7.getLine() + ":" + var7.getPos() + " " + var7.getMsg());
            TellPlayer.INSTANCE.chat("");
            TellPlayer.INSTANCE.chat("For more information see logs.");
            Log.INSTANCE.warn("Syntax error in '" + this.fileName + "' (" + var2.getText() + ")");
            Log.INSTANCE.warn("  > at: " + var7.getLine() + ":" + var7.getPos() + " " + var7.getMsg());
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

      Log.INSTANCE.trace(RuleFile::parseContent$lambda$2);
      Log.INSTANCE.trace("    " + this.fileName + " parse finished");
   }

   private static final String parseContent$lambda$2(RuleFile var0) {
      int var10000 = CollectionsKt.flatten(var0.rulesMap.values()).size();
      Map var6 = var0.rulesMap;
      int var4 = var10000;
      Map var1 = var6;
      var var7 = new ArrayList(var6.size());

      for (Entry var2 : var1.entrySet()) {
         ArrayList var5 = var7;
         String var3 = (String)var2.getKey();
         List var9;
         var5.add((var9 = (List)var2.getValue()).size() == 1 ? var3 : var3 + " x" + var9.size());
      }

      List var10 = var7;
      return "    Added " + var4 + " rules: " + var10;
   }
}
