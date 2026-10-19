package org.anti_ad.mc.ipnext.item.rule.file;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.extensions.Kt_commonKt;
import org.anti_ad.mc.common.extensions.Kt_numberKt;
import org.anti_ad.mc.ipnext.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class RuleFileRegister$RuleFinder {
   @NotNull
   private final String ruleName;
   private final boolean fromUserInput;

   public RuleFileRegister$RuleFinder(@NotNull String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.ruleName = var1;
      this.fromUserInput = var2;
   }

   @NotNull
   public final String getRuleName() {
      return this.ruleName;
   }

   public final boolean getFromUserInput() {
      return this.fromUserInput;
   }

   @Nullable
   public final RuleDefinition searchCustomRule() {
      Iterator var1 = CollectionsKt.reversed(RuleFileRegister.access$getRuleFiles$p()).iterator();

      while (var1.hasNext()) {
         RuleFile var2;
         Map var3;
         if ((var3 = (var2 = (RuleFile)var1.next()).getRulesMap()).containsKey(this.ruleName)) {
            String var5 = var2.getFileName();
            Log.INSTANCE.trace("Searching rule @" + this.ruleName + " at file " + var5);
            List var4 = (List)MapsKt.getValue(var3, this.ruleName);
            Log.INSTANCE.indent();
            RuleDefinition var6 = this.findUsableRule(CollectionsKt.asReversedMutable(var4), var5);
            Log.INSTANCE.unindent();
            var3.remove(this.ruleName);
            if (var6 != null) {
               Log.INSTANCE.trace("    > Found @" + this.ruleName + " at file " + var5);
               RuleFileRegister.INSTANCE.getLoadedFileNames().add(var5);
               return var6;
            }

            Log.INSTANCE.trace("    > None of @" + this.ruleName + " usable at file " + var5);
         }
      }

      Log.INSTANCE.trace(">> @" + this.ruleName + " not found in all files");
      return null;
   }

   private final RuleDefinition findUsableRule(List var1, String var2) {
      int var3 = var1.size();

      for (RuleDefinition var4 : var1) {
         Log.INSTANCE.trace("Instantiating rule @" + this.ruleName + "#" + var3);
         Log.INSTANCE.indent();

         label77: {
            try {
               var4.createCustomRule();
               if (var4.getStatus() != RuleDefinition$Status.SUCCESS) {
                  Log.INSTANCE.error("interesting rule @" + this.ruleName + "#" + var3 + " (at file " + var2 + ")");
                  break label77;
               }

               return var4;
            } catch (Exception var8) {
               if (this.fromUserInput) {
                  TellPlayer.INSTANCE.chat("Failed to process Rule '@" + this.ruleName + "'");
                  TellPlayer.INSTANCE.chat("  > " + Kt_commonKt.getUsefulName(var8.getClass()) + ": " + var8.getMessage());
               }

               Log.INSTANCE.warn("Error in " + Kt_numberKt.getOrdinalName(var3) + " '@" + this.ruleName + "' (at file " + var2 + ")");
               Log.INSTANCE.warn("  > " + Kt_commonKt.getUsefulName(var8.getClass()) + ": " + var8.getMessage());
               if (!(var8 instanceof NoSuchElementException) && !(var8 instanceof SelfReferenceException) && !(var8 instanceof MissingParameterException)) {
                  var8.printStackTrace();
               }
            } finally {
               Log.INSTANCE.unindent();
            }

            var3--;
            continue;
         }

         var3--;
      }

      return null;
   }
}
