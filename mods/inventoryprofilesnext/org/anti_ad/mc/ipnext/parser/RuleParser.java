package org.anti_ad.mc.ipnext.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.a.b.a.a.a.d.i;
import org.anti_ad.mc.common.annotation.MayThrow;
import org.anti_ad.mc.common.gen.RulesParser;
import org.anti_ad.mc.common.gen.RulesParser$ArgumentsContext;
import org.anti_ad.mc.common.gen.RulesParser$CustomRuleEOFContext;
import org.anti_ad.mc.common.gen.RulesParser$PairContext;
import org.anti_ad.mc.common.gen.RulesParser$SubRuleContext;
import org.anti_ad.mc.common.gen.RulesParser$SubRuleIdentifierContext;
import org.anti_ad.mc.common.util.IndentedData;
import org.anti_ad.mc.common.util.IndentedLine;
import org.anti_ad.mc.ipnext.item.rule.file.RuleDefinition;
import org.anti_ad.mc.ipnext.item.rule.file.SubRuleDefinition;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nRuleParser.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RuleParser.kt\norg/anti_ad/mc/ipnext/parser/RuleParser\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,94:1\n1563#2:95\n1634#2,3:96\n1563#2:99\n1634#2,3:100\n1869#2,2:104\n1#3:103\n*S KotlinDebug\n*F\n+ 1 RuleParser.kt\norg/anti_ad/mc/ipnext/parser/RuleParser\n*L\n53#1:95\n53#1:96,3\n66#1:99\n66#1:100,3\n84#1:104,2\n*E\n"
)
public final class RuleParser {
   @NotNull
   public static final RuleParser INSTANCE = new RuleParser();

   private RuleParser() {
   }

   @MayThrow
   @NotNull
   public final RuleDefinition parseRuleDefinition(@NotNull IndentedData var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var4;
      String var2 = CollectionsKt.joinToString$default(var4 = var1.getLines(), "\n", null, null, 0, null, RuleParser::parseRuleDefinition$lambda$0, 30, null);

      try {
         return this.parseRuleDefinition(var2);
      } catch (SyntaxErrorException var3) {
         IndentedLine var10001 = (IndentedLine)CollectionsKt.getOrNull(var4, var3.getLine() - 1);
         throw SyntaxErrorException.copy$default(var3, var10001 != null ? var10001.getLineNumber() : -1, var3.getPos() + 1, null, 4, null);
      }
   }

   @MayThrow
   @NotNull
   public final List parseSubRule(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var10000 = ((RulesParser)AntlrExtensionsKt.parseBy(var1, RuleParser$parseSubRule$1.INSTANCE, RuleParser$parseSubRule$2.INSTANCE, 2))
         .subRuleEOF()
         .subRule();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Iterable var5;
      Iterable var2 = var5 = var10000;
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

      for (RulesParser$SubRuleContext var3 : var2) {
         var3 = var3;
         ArrayList var4 = var6;
         RuleParser var9 = INSTANCE;
         Intrinsics.checkNotNull(var3);
         var4.add(var9.toSubRuleDefinition(var3));
      }

      return var6;
   }

   private final RuleDefinition parseRuleDefinition(String var1) {
      RulesParser var2 = (RulesParser)AntlrExtensionsKt.parseBy$default(
         var1, RuleParser$parseRuleDefinition$parser$1.INSTANCE, RuleParser$parseRuleDefinition$parser$2.INSTANCE, 0, 4, null
      );
      RulesParser$CustomRuleEOFContext var10001 = var2.customRuleEOF();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      return this.toRuleDefinition(var10001);
   }

   private final RuleDefinition toRuleDefinition(RulesParser$CustomRuleEOFContext var1) {
      String var10000 = var1.head().RuleName().getText();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      List var10001 = var1.subRuleEOF().subRule();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      Iterable var6 = var10001;
      String var4 = var10000;
      Iterable var2 = var6;
      var var7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));

      for (RulesParser$SubRuleContext var3 : var2) {
         var3 = var3;
         ArrayList var5 = var7;
         RuleParser var12 = INSTANCE;
         Intrinsics.checkNotNull(var3);
         var5.add(var12.toSubRuleDefinition(var3));
      }

      List var8 = var7;
      String var10 = var4;
      return new RuleDefinition(var10, var8);
   }

   private final SubRuleDefinition toSubRuleDefinition(RulesParser$SubRuleContext var1) {
      RulesParser$SubRuleIdentifierContext var2 = var1.subRuleIdentifier();
      LinkedHashMap var3 = new LinkedHashMap();
      if (var1.REVERSE() != null) {
         var3.put("reverse", "true");
      }

      String var5 = var2.AT() != null ? "@" : "::";
      String var10000;
      if (var2.AT() == null && var2.DOUBLE_COLON() == null) {
         String var6 = var2.HASHTAG() != null ? "tag" : "item";
         i var10 = var2.NBT();
         if (var10 != null) {
            var10000 = var10.getText();
            if (var10000 != null) {
               String var4 = var10000;
               var3.put("require_nbt", "required");
               var3.put("nbt", var4);
            }
         }

         var3.put(var6 + "_name", var2.NamespacedId().getText());
         var10000 = "is_" + var6;
      } else {
         var10000 = var2.RuleName().getText();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
      }

      String var8 = var10000;
      RulesParser$ArgumentsContext var12 = var1.arguments();
      if (var12 != null) {
         List var13 = var12.pair();
         if (var13 != null) {
            for (RulesParser$PairContext var9 : var13) {
               var3.put(var9.Parameter().getText(), var9.Argument().getText());
            }
         }
      }

      return new SubRuleDefinition(var5, var8, MapsKt.toList(var3));
   }

   private static final CharSequence parseRuleDefinition$lambda$0(IndentedLine var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getRawText();
   }
}
