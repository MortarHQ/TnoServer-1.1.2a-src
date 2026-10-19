package org.anti_ad.mc.ipnext.item.rule.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.item.rule.EmptyRule;
import org.anti_ad.mc.ipnext.item.rule.Parameter;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.anti_ad.mc.ipnext.item.rule.natives.NativeRules;
import org.anti_ad.mc.ipnext.item.rule.parameter.NativeParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nRuleFileRegister.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RuleFileRegister.kt\norg/anti_ad/mc/ipnext/item/rule/file/RuleFileRegister\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n*L\n1#1,209:1\n1869#2,2:210\n1#3:212\n1#3:214\n130#4:213\n*S KotlinDebug\n*F\n+ 1 RuleFileRegister.kt\norg/anti_ad/mc/ipnext/item/rule/file/RuleFileRegister\n*L\n53#1:210,2\n141#1:214\n141#1:213\n*E\n"
)
public final class RuleFileRegister {
   @NotNull
   public static final RuleFileRegister INSTANCE = new RuleFileRegister();
   @NotNull
   private static final Set loadedFileNames = new LinkedHashSet();
   @NotNull
   private static final List ruleFiles = new ArrayList();
   @NotNull
   private static final Map cachedRules = new LinkedHashMap();
   @NotNull
   private static final Set names = new LinkedHashSet();

   private RuleFileRegister() {
   }

   @NotNull
   public final Set getLoadedFileNames() {
      return loadedFileNames;
   }

   public final void reloadRuleFiles(@NotNull List var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Log.INSTANCE.trace("[-] Rule file parsing...");
      Log.INSTANCE.trace("    step: (1) parse indent -> (2) parse rule -> syntax ok");
      Log.INSTANCE.indent();

      for (RuleFile var4 : var1) {
         if (var2) {
            TellPlayer.INSTANCE.chat("Parsing custom rules file " + var4.getFileName());
         }

         var4.parseContent();
      }

      Log.INSTANCE.unindent();
      this.clear();
      ruleFiles.addAll(var1);
      this.checkOverrides(var2);
      this.validateRules(var2);
   }

   private final void clear() {
      loadedFileNames.clear();
      ruleFiles.clear();
      cachedRules.clear();
      names.clear();
   }

   private final void checkOverrides(boolean var1) {
      Log.INSTANCE.trace("[-] Check overrides...");
      Iterator var2 = ruleFiles.iterator();

      while (var2.hasNext()) {
         RuleFile var3;
         for (String var5 : (var3 = (RuleFile)var2.next()).getRulesMap().keySet()) {
            if (names.contains(var5)) {
               if (var1) {
                  TellPlayer.INSTANCE.chat("Rule @" + var5 + " already defined in another file. Overriding with file " + var3.getFileName());
               }

               Log.INSTANCE.info("Rule @" + var5 + " overrode by file " + var3.getFileName());
            }

            names.add(var5);
         }
      }
   }

   private final void validateRules(boolean var1) {
      Log.INSTANCE.trace("[-] Validate rules...");
      Log.INSTANCE.indent();

      for (String var3 : names) {
         Log.INSTANCE.trace("[-] Validating rule @" + var3);
         Log.INSTANCE.indent();
         if (this.getCustomRule(var3, var1) == null) {
            RuleFileRegister::validateRules$lambda$1;
         }

         Log.INSTANCE.unindent();
      }

      Log.INSTANCE.unindent();
   }

   @NotNull
   public final Rule getCustomRuleOrEmpty(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      CustomRule var10000 = this.getCustomRule(var1, false);
      if (var10000 != null) {
         return var10000;
      }

      EmptyRule var2 = EmptyRule.INSTANCE;
      Log.INSTANCE.warn("Rule @" + var1 + " not found");
      return var2;
   }

   @Nullable
   public final CustomRule getCustomRule(@NotNull String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      RuleDefinition var10000 = cachedRules.containsKey(var1) ? (RuleDefinition)MapsKt.getValue(cachedRules, var1) : this.searchAndPutCustomRule(var1, var2);
      return var10000 != null ? var10000.createCustomRule() : null;
   }

   @Nullable
   public final Rule getNativeRule(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Function0 var10000 = (Function0)NativeRules.INSTANCE.getMap().get(var1);
      return var10000 != null ? (Rule)var10000.invoke() : null;
   }

   @Nullable
   public final Parameter getParameter(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return (Parameter)NativeParameters.INSTANCE.getMap().get(var1);
   }

   private final RuleDefinition searchAndPutCustomRule(String var1, boolean var2) {
      Log.INSTANCE.trace("[-] Searching rule @" + var1 + "...");
      Log.INSTANCE.indent();
      RuleDefinition var3 = new RuleFileRegister$RuleFinder(var1, var2).searchCustomRule();
      Log.INSTANCE.unindent();
      if (cachedRules.containsKey(var1)) {
         Log.INSTANCE.trace(">> rule " + var1 + " already exist in cached map... skip putting");
      } else {
         cachedRules.put(var1, var3);
      }

      CollectionsKt.removeAll(ruleFiles, RuleFileRegister::searchAndPutCustomRule$lambda$4);
      return var3;
   }

   private static final Unit validateRules$lambda$1(boolean var0, String var1) {
      if (var0) {
         TellPlayer.INSTANCE.chat("Failed to process Rule '@" + var1 + "'");
      }

      Log.INSTANCE.debug("rule @" + var1 + " failed to parse");
      return Unit.INSTANCE;
   }

   private static final boolean searchAndPutCustomRule$lambda$4(RuleFile var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      boolean var1;
      boolean var2 = var1 = var0.getRulesMap().isEmpty();
      if (var1) {
         Log.INSTANCE.trace("Remove validated file " + var0.getFileName());
      }

      return var2;
   }
}
