package org.anti_ad.mc.ipnext.gui;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.config.IConfigOption.Importance;
import org.anti_ad.mc.common.config.options.ConfigBoolean;
import org.jetbrains.annotations.NotNull;

public final class GUIDEEditorScreen$EditorConfigBoolean extends ConfigBoolean {
   @NotNull
   private String key;
   private boolean hidden;
   @NotNull
   private final Function1 valueSetEvent;
   private boolean value;

   public GUIDEEditorScreen$EditorConfigBoolean(@NotNull GUIDEEditorScreen var1, String var2, boolean var3, boolean var4, @NotNull boolean var5, Function1 var6) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var6, "");
      this.this$0 = var1;
      super(var4, false, 2, null);
      this.key = var2;
      this.hidden = var3;
      this.valueSetEvent = var6;
      this.value = var5;
      this.setValue(var5);
      this.setImportance(Importance.NORMAL);
   }

   @NotNull
   public final String getKey() {
      return this.key;
   }

   public final void setKey(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.key = var1;
   }

   public final boolean getHidden() {
      return this.hidden;
   }

   public final void setHidden(boolean var1) {
      this.hidden = var1;
   }

   @NotNull
   public final Function1 getValueSetEvent() {
      return this.valueSetEvent;
   }

   @NotNull
   public final Boolean getValue() {
      return this.value;
   }

   public final void setValue(boolean var1) {
      this.value = var1;
      this.valueSetEvent.invoke(var1);
   }
}
