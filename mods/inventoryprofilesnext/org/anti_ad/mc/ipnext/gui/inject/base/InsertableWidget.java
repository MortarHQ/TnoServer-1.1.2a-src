package org.anti_ad.mc.ipnext.gui.inject.base;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.jetbrains.annotations.NotNull;

public abstract class InsertableWidget extends Widget {
   @NotNull
   private final List hintableList = new ArrayList();
   @NotNull
   private final List snapableList = new ArrayList();

   public abstract void postBackgroundRender(@NotNull NativeContext var1, int var2, int var3, float var4);

   @NotNull
   public abstract AbstractContainerScreen getScreen();

   @NotNull
   public abstract AbstractContainerMenu getContainer();

   @NotNull
   public final List getHintableList() {
      return this.hintableList;
   }

   public void render(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      super.render(var1, var2, var3, var4);
   }

   public abstract void postForegroundRender(@NotNull NativeContext var1, int var2, int var3, float var4);

   @NotNull
   public final List getSnapableList() {
      return this.snapableList;
   }
}
