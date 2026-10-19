package org.anti_ad.mc.ipn.events.api;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PredicateActionGroup extends ActionExecutor implements Action {
   @NotNull
   private final Function0 predicate;

   public PredicateActionGroup(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.predicate = var1;
   }

   @NotNull
   public final Function0 getPredicate() {
      return this.predicate;
   }

   @Nullable
   @Override
   public final Object doIt(Object var1) {
      return this.predicate.invoke() ? super.doIt(var1) : null;
   }
}
