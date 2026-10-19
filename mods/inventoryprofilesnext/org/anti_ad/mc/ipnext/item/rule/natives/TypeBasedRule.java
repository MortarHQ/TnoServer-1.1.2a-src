package org.anti_ad.mc.ipnext.item.rule.natives;

import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;

public abstract class TypeBasedRule extends NativeRule {
   @NotNull
   public abstract Function2 getValueOf();

   public abstract void setValueOf(@NotNull Function2 var1);
}
