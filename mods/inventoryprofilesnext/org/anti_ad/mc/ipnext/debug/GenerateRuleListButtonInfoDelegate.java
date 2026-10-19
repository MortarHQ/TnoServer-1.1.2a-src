package org.anti_ad.mc.ipnext.debug;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.extensions.Java_ioKt;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonClickHandler;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.item.rule.ArgumentType;
import org.anti_ad.mc.ipnext.item.rule.Parameter;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.item.rule.natives.NativeRules;
import org.anti_ad.mc.ipnext.item.rule.parameter.BooleanArgumentType;
import org.anti_ad.mc.ipnext.item.rule.parameter.EnumArgumentType;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParameters;
import org.jetbrains.annotations.NotNull;

public final class GenerateRuleListButtonInfoDelegate extends ConfigButtonClickHandler {
   @NotNull
   public static final GenerateRuleListButtonInfoDelegate INSTANCE = new GenerateRuleListButtonInfoDelegate();
   @NotNull
   private static final Path file = Java_ioKt.div(VanillaUtil.INSTANCE.configDirectory("inventoryprofilesnext"), "native_rules.txt");

   private GenerateRuleListButtonInfoDelegate() {
   }

   public final void onClick(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      String var5 = "Parameter:\n";
      Iterator var2 = NativeParameters.INSTANCE.getMap().entrySet().iterator();

      while (var2.hasNext()) {
         Entry var3;
         String var4 = (String)(var3 = (Entry)var2.next()).getKey();
         Parameter var9 = (Parameter)var3.getValue();
         ArgumentType var10;
         String var10002;
         if ((var10 = var9.getArgumentType()) instanceof BooleanArgumentType) {
            var10002 = "true/false";
         } else if (var10 instanceof EnumArgumentType) {
            Enum[] var15 = (Enum[])((EnumArgumentType)var10).getEnumClass().getEnumConstants();
            var10002 = var15 != null
               ? ArraysKt.joinToString$default(var15, "/", null, null, 0, null, GenerateRuleListButtonInfoDelegate::onClick$lambda$0, 30, null)
               : null;
         } else {
            var10002 = "[" + Kt_commonKt.getUsefulName(var10.getClass()) + "]";
         }

         var5 = var5 + "    " + var4 + ": " + var10002 + "\n";
      }

      String var6 = var5 + "\n" + "Native Rules:\n";
      var2 = NativeRules.INSTANCE.getMap().entrySet().iterator();

      while (var2.hasNext()) {
         Entry var11;
         String var14 = (String)(var11 = (Entry)var2.next()).getKey();
         Function0 var12 = (Function0)var11.getValue();
         String var7 = var6 + "    ::" + var14;
         List var13;
         if (!(var13 = ((Rule)var12.invoke()).getArguments().dumpAsPairList()).isEmpty()) {
            var7 = var7
               + "\n        ("
               + CollectionsKt.joinToString$default(var13, null, null, null, 0, null, GenerateRuleListButtonInfoDelegate::onClick$lambda$1, 31, null)
               + ")";
         }

         var6 = var7 + "\n";
      }

      Java_ioKt.writeToFile(var6, file);
   }

   private static final CharSequence onClick$lambda$0(Enum var0) {
      Enum var10000 = var0;
      if (var0 == null) {
         var10000 = null;
      }

      label14: {
         if (var10000 != null) {
            var1 = var10000.name();
            if (var1 != null) {
               break label14;
            }
         }

         var1 = var0.toString();
      }

      String var2 = var1.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var2, "");
      return var2;
   }

   private static final CharSequence onClick$lambda$1(Pair var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      String var1 = (String)var0.component1();
      String var2 = (String)var0.component2();
      return var1 + " = " + var2;
   }
}
