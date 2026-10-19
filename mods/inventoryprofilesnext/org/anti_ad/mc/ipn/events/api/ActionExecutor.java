package org.anti_ad.mc.ipn.events.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ActionExecutor {
   @NotNull
   private final List actions = new ArrayList();

   @NotNull
   protected final List getActions() {
      return this.actions;
   }

   private final void add(Action var1, Function0 var2) {
      this.actions.add(new Pair(var1, var2));
   }

   @Nullable
   public Object doIt(Object var1) {
      Iterator var2 = this.actions.iterator();

      while (var2.hasNext()) {
         Pair var3;
         Action var4 = (Action)(var3 = (Pair)var2.next()).component1();
         if ((Boolean)((Function0)var3.component2()).invoke()) {
            Object var10000 = var4.doIt(var1);
            if (var10000 != null) {
               Object var5 = var10000;
               if (!(var10000 instanceof Unit)) {
                  return var5;
               }
            }
         }
      }

      return null;
   }

   @NotNull
   public ActionExecutor$PredicateActionBuilder getAdder() {
      return new ActionExecutor$PredicateActionBuilder(this);
   }
}
