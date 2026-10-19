package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Size;
import org.anti_ad.mc.common.vanilla.render.ScreenKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nScreenEventHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ScreenEventHandler.kt\norg/anti_ad/mc/ipnext/gui/inject/ScreenEventHandler\n+ 2 PropertyDelegates.kt\norg/anti_ad/mc/common/extensions/PropertyDelegatesKt\n+ 3 Delegates.kt\nkotlin/properties/Delegates\n*L\n1#1,61:1\n35#2:62\n38#2:66\n33#3,3:63\n*S KotlinDebug\n*F\n+ 1 ScreenEventHandler.kt\norg/anti_ad/mc/ipnext/gui/inject/ScreenEventHandler\n*L\n47#1:62\n47#1:66\n47#1:63,3\n*E\n"
)
public final class ScreenEventHandler {
   @NotNull
   public static final ScreenEventHandler INSTANCE = new ScreenEventHandler();
   @NotNull
   private static final ReadWriteProperty trackedScreenSize$delegate;

   private ScreenEventHandler() {
   }

   public final void onScreenInit(@NotNull Screen var1, @NotNull Function1 var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (var1 instanceof AbstractContainerScreen) {
         ContainerScreenEventHandler.INSTANCE.onScreenInit((AbstractContainerScreen)var1, var2);
      }
   }

   public final void onScreenRemoved(@NotNull Screen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (var1 instanceof AbstractContainerScreen) {
         ContainerScreenEventHandler.INSTANCE.onScreenRemoved((AbstractContainerScreen)var1);
      }
   }

   private final Size getTrackedScreenSize() {
      return (Size)trackedScreenSize$delegate.getValue(this, $$delegatedProperties[0]);
   }

   private final void setTrackedScreenSize(Size var1) {
      trackedScreenSize$delegate.setValue(this, $$delegatedProperties[0], var1);
   }

   public final void preRender(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      InsertWidgetHandler.INSTANCE.preScreenRender();
      this.setTrackedScreenSize(ScreenKt.getRScreenSize());
      ContainerScreenEventHandler.INSTANCE.preRender();
   }

   public final void postRender(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      ContainerScreenEventHandler.INSTANCE.postRender(var1);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[1])[0] = Reflection.mutableProperty1(
         (MutablePropertyReference1)(
            new MutablePropertyReference1Impl(ScreenEventHandler.class, "trackedScreenSize", "getTrackedScreenSize()Lorg/anti_ad/mc/common/math2d/Size;", 0)
         )
      );
      $$delegatedProperties = var0;
      Size var1 = new Size(0, 0);
      trackedScreenSize$delegate = (ReadWriteProperty)(new ScreenEventHandler$special$$inlined$detectable$1(var1));
   }
}
