package org.anti_ad.mc.ipnext.gui;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.input.MainKeybind;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nGUIDEEditorScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GUIDEEditorScreen.kt\norg/anti_ad/mc/ipnext/gui/GUIDEEditorScreen$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,473:1\n1#2:474\n*E\n"
)
public final class GUIDEEditorScreen$Companion {
   private GUIDEEditorScreen$Companion() {
   }

   private final void whenAtLeastOneVisible(List var1, Function0 var2) {
      Iterator var5 = var1.iterator();

      Object var10000;
      while (true) {
         if (var5.hasNext()) {
            Object var3;
            Hintable var4 = (Hintable)(var3 = var5.next());
            if (!GUIDEEditorScreen.Companion.isVisible(var4)) {
               continue;
            }

            var10000 = var3;
            break;
         }

         var10000 = null;
         break;
      }

      if (var10000 != null) {
         var2.invoke();
      }
   }

   private final boolean isVisible(Hintable var1) {
      return var1 != null && var1 instanceof Widget ? ((Widget)var1).getVisible() : false;
   }

   @NotNull
   public final MainKeybind getSHIFT() {
      return GUIDEEditorScreen.access$getSHIFT$cp();
   }
}
