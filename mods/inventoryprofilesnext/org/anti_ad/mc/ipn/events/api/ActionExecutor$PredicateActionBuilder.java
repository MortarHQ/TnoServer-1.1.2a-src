package org.anti_ad.mc.ipn.events.api;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public class ActionExecutor$PredicateActionBuilder {
   public Function0 condition;
   public Action action;

   public ActionExecutor$PredicateActionBuilder(ActionExecutor var1) {
      this.this$0 = var1;
   }

   @NotNull
   public Function0 getCondition() {
      if (this.condition != null) {
         return this.condition;
      }

      Intrinsics.throwUninitializedPropertyAccessException("");
      return null;
   }

   public void setCondition(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.condition = var1;
   }

   @NotNull
   public final Action getAction() {
      if (this.action != null) {
         return this.action;
      }

      Intrinsics.throwUninitializedPropertyAccessException("");
      return null;
   }

   public final void setAction(@NotNull Action var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.action = var1;
   }

   @NotNull
   public ActionExecutor$PredicateActionBuilder action(@NotNull Action var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.setAction(var1);
      return this;
   }

   @NotNull
   public ActionExecutor$PredicateActionBuilder predicate(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.setCondition(var1);
      return this;
   }

   public final void build() {
      ActionExecutor.access$add(this.this$0, this.getAction(), this.getCondition());
   }
}
