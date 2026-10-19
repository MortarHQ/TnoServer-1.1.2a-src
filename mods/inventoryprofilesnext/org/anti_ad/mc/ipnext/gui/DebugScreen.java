package org.anti_ad.mc.ipnext.gui;

import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.anti_ad.mc.common.gui.debug.BaseDebugScreen;
import org.anti_ad.mc.common.gui.debug.DebugInfos;
import org.anti_ad.mc.common.gui.widgets.Page;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,223:1\n119#2,4:224\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen\n*L\n185#1:224,4\n*E\n"
)
public final class DebugScreen extends BaseDebugScreen {
   @NotNull
   public static final DebugScreen$Companion Companion = new DebugScreen$Companion(null);
   private static int storedPageIndex;

   public DebugScreen() {
      Screen var1;
      if ((var1 = this.getParent()) instanceof AbstractContainerScreen) {
         Page var2 = this.addContent(DebugScreen::_init_$lambda$1, (Page)this.getPages().get(0));
         this.getPages().set(0, var2);
         this.getPages().add(new DebugScreen$PageContainer(this));
         this.getPages().add(new DebugScreen$PageScreenInfo(this));
      }

      this.switchPage(storedPageIndex);
   }

   @NotNull
   public final Page addContent(@NotNull Function1 var1, @NotNull Page var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      String var3 = var2.getName();
      return new DebugScreen$addContent$1(var2, var1, var3);
   }

   public final void closeScreen() {
      storedPageIndex = this.getPageIndex();
      super.closeScreen();
   }

   public final void renderBackground(@NotNull GuiGraphics var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.renderDarkening(var1);
   }

   private static final List _init_$lambda$1(Screen var0, Page var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      AbstractContainerScreen var2 = (AbstractContainerScreen)var0;
      Rectangle var3;
      return StringsKt.lines(
         StringsKt.trimMargin$default(
            "\n                    |\n                    |container\n                    |x: "
               + (var3 = new Rectangle(var2.getGuiLeft(), var2.getGuiTop(), var2.getXSize(), var2.getYSize())).getX()
               + " y: "
               + var3.getY()
               + "\n                    |width: "
               + var3.getWidth()
               + " height: "
               + var3.getHeight()
               + "\n                    |relative mouse\n                    |x: "
               + (DebugInfos.INSTANCE.getMouseX() - var3.getX())
               + " y: "
               + (DebugInfos.INSTANCE.getMouseY() - var3.getY())
               + "\n                    |",
            null,
            1,
            null
         )
      );
   }
}
