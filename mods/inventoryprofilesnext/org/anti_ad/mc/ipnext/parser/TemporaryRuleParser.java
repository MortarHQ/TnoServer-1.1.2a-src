package org.anti_ad.mc.ipnext.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.item.rule.EmptyRule;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.item.rule.file.CustomRule;
import org.anti_ad.mc.ipnext.item.rule.file.MissingParameterException;
import org.anti_ad.mc.ipnext.item.rule.file.SelfReferenceException;
import org.anti_ad.mc.ipnext.item.rule.file.SubRuleDefinition;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nTemporaryRuleParser.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TemporaryRuleParser.kt\norg/anti_ad/mc/ipnext/parser/TemporaryRuleParser\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,77:1\n1563#2:78\n1634#2,3:79\n*S KotlinDebug\n*F\n+ 1 TemporaryRuleParser.kt\norg/anti_ad/mc/ipnext/parser/TemporaryRuleParser\n*L\n56#1:78\n56#1:79,3\n*E\n"
)
public final class TemporaryRuleParser {
   @NotNull
   public static final TemporaryRuleParser INSTANCE = new TemporaryRuleParser();
   @NotNull
   private static final Map cachedMap = new LinkedHashMap();

   private TemporaryRuleParser() {
   }

   @NotNull
   public final Rule parse(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Object var10000 = cachedMap.get(var1);
      if (var10000 == null) {
         var10000 = INSTANCE.innerParse(var1);
      }

      return (Rule)var10000;
   }

   private final Rule innerParse(String var1) {
      List var2;
      try {
         var2 = RuleParser.INSTANCE.parseSubRule(var1);
      } catch (SyntaxErrorException var6) {
         Log.INSTANCE.warn("Syntax error in '" + var1 + "'");
         Log.INSTANCE.warn("  > at: " + var6.getLine() + ":" + var6.getPos() + " " + var6.getMsg());
         return EmptyRule.INSTANCE;
      } catch (Exception var7) {
         var7.printStackTrace();
         return EmptyRule.INSTANCE;
      }

      try {
         Iterable var10;
         Iterable var3 = var10 = var2;
         var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10, 10));

         for (SubRuleDefinition var4 : var3) {
            var4 = var4;
            ArrayList var5 = var2;
            var5.add(var4.toRule());
         }

         var2 = var2;
         CustomRule var13 = new CustomRule(var2);
         cachedMap.put(var1, var13);
         var9 = var13;
      } catch (Exception var8) {
         Log.INSTANCE.warn("Error in '" + var1 + "'");
         Log.INSTANCE.warn("  > " + Kt_commonKt.getUsefulName(var8.getClass()) + ": " + var8.getMessage());
         if (!(var8 instanceof NoSuchElementException) && !(var8 instanceof SelfReferenceException) && !(var8 instanceof MissingParameterException)) {
            var8.printStackTrace();
         }

         var9 = EmptyRule.INSTANCE;
      }

      return var9;
   }

   public final void onReload() {
      cachedMap.clear();
   }
}
