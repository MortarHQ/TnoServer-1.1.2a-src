package org.anti_ad.mc.ipnext.parser;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.LogBase;
import org.anti_ad.mc.common.LogBase.LogLevel;
import org.anti_ad.mc.common.gui.widgets.ConfigButtonClickHandler;
import org.anti_ad.mc.libipn.Log;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nCustomDataFileLoader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/ReloadRuleFileButtonInfoDelegate\n+ 2 TellPlayer.kt\norg/anti_ad/mc/common/TellPlayer\n+ 3 Log.kt\norg/anti_ad/mc/common/LogBase\n*L\n1#1,485:1\n40#2:486\n43#2:493\n247#3:487\n239#3,4:488\n253#3:492\n*S KotlinDebug\n*F\n+ 1 CustomDataFileLoader.kt\norg/anti_ad/mc/ipnext/parser/ReloadRuleFileButtonInfoDelegate\n*L\n69#1:486\n69#1:493\n69#1:487\n69#1:488,4\n69#1:492\n*E\n"
)
public final class ReloadRuleFileButtonInfoDelegate extends ConfigButtonClickHandler {
   @NotNull
   public static final ReloadRuleFileButtonInfoDelegate INSTANCE = new ReloadRuleFileButtonInfoDelegate();

   private ReloadRuleFileButtonInfoDelegate() {
   }

   public final void onClick(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      LogLevel var2 = LogLevel.INFO;
      LogBase var3 = (LogBase)Log.INSTANCE;
      Function1 var4 = ReloadRuleFileButtonInfoDelegate$onClick$$inlined$listenLog$1.INSTANCE;
      Function1 var5 = new ReloadRuleFileButtonInfoDelegate$onClick$$inlined$listenLog$2(var2, var4);
      var3.addLogListener(var5);
      RuleLoader.INSTANCE.reload(true);
      var3.removeLogListener(var5);
      var1.invoke();
   }
}
